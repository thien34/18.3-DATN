package com.example.back_end.core.admin.product.service.impl;

import com.example.back_end.core.admin.product.mapper.ProductAttributeValueMapper;
import com.example.back_end.core.admin.product.mapper.ProductProductAttributeMappingMapper;
import com.example.back_end.core.admin.product.payload.request.ProductProductAttributeMappingRequest;
import com.example.back_end.core.admin.product.payload.response.ProductAttributeValueResponse;
import com.example.back_end.core.admin.product.payload.response.ProductProductAttributeMappingDetailResponse;
import com.example.back_end.core.admin.product.payload.response.ProductProductAttributeMappingResponse;
import com.example.back_end.core.admin.product.service.ProductAttributeValueService;
import com.example.back_end.core.admin.product.service.ProductProductAttributeMappingService;
import com.example.back_end.core.common.PageResponse;
import com.example.back_end.entity.Product;
import com.example.back_end.entity.ProductAttribute;
import com.example.back_end.entity.ProductAttributeCombination;
import com.example.back_end.entity.ProductAttributeValue;
import com.example.back_end.entity.ProductAttributeValuePicture;
import com.example.back_end.entity.ProductProductAttributeMapping;
import com.example.back_end.infrastructure.constant.SortType;
import com.example.back_end.infrastructure.exception.ResourceNotFoundException;
import com.example.back_end.infrastructure.utils.PageUtils;
import com.example.back_end.repository.ProductAttributeCombinationRepository;
import com.example.back_end.repository.ProductAttributeRepository;
import com.example.back_end.repository.ProductAttributeValuePictureRepository;
import com.example.back_end.repository.ProductAttributeValueRepository;
import com.example.back_end.repository.ProductProductAttributeMappingRepository;
import com.example.back_end.repository.ProductRepository;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductProductAttributeMappingServiceImpl implements ProductProductAttributeMappingService {

    private final ProductRepository productRepository;
    private final ProductAttributeRepository productAttributeRepository;
    private final ProductAttributeValueRepository productAttributeValueRepository;
    private final ProductProductAttributeMappingRepository productProductAttributeMappingRepository;
    private final ProductProductAttributeMappingMapper productProductAttributeMappingMapper;
    private final ProductAttributeValuePictureRepository productAttributeValuePictureRepository;
    private final ProductAttributeValueService productAttributeValueService;
    private final ProductAttributeValueMapper productAttributeValueMapper;
    private final ProductAttributeCombinationRepository productAttributeCombinationRepository;

    @Override
    public PageResponse<List<ProductProductAttributeMappingResponse>> getProductProductAttributeMappings(Long productId, int pageNo, int pageSize) {

        getProductOrThrow(productId);

        Pageable pageable = PageUtils.createPageable(pageNo, pageSize, "id", SortType.DESC.getValue());
        Page<ProductProductAttributeMapping> page = productProductAttributeMappingRepository
                .findAllByProductId(productId, pageable);
        List<ProductProductAttributeMappingResponse> responses = productProductAttributeMappingMapper
                .toDtos(page.getContent());

        return PageResponse.<List<ProductProductAttributeMappingResponse>>builder()
                .page(page.getNumber())
                .size(page.getSize())
                .totalPage(page.getTotalPages())
                .items(responses)
                .build();
    }

    @Override
    public ProductProductAttributeMappingDetailResponse getProductProductAttributeMapping(Long id) {

        ProductProductAttributeMapping attributeMapping = getMappingOrThrow(id);

        List<ProductAttributeValue> productAttributeValue = productAttributeValueRepository
                .findAllByProductAttributeMapping(attributeMapping)
                .orElseThrow(() -> new ResourceNotFoundException("Product attribute value with id not found: " + id));

        List<ProductAttributeValueResponse> productAttributeValueResponses = productAttributeValueMapper
                .toDtos(productAttributeValue);

        productAttributeValueResponses.forEach(productAttributeValueResponse -> {
            List<ProductAttributeValuePicture> productAttributeValuePictures = productAttributeValuePictureRepository
                    .findAllByProductAttributeValueId(productAttributeValueResponse.getId());
            List<String> imageUrls = productAttributeValuePictures.stream()
                    .map(picture -> picture.getPicture().getLinkImg())
                    .toList();

            productAttributeValueResponse.setImageUrl(imageUrls);
        });

        return ProductProductAttributeMappingDetailResponse.builder()
                .id(attributeMapping.getId())
                .productId(attributeMapping.getProduct().getId())
                .productAttributeId(attributeMapping.getProductAttribute().getId())
                .textPrompt(attributeMapping.getTextPrompt())
                .isRequired(attributeMapping.getIsRequired())
                .attributeControlTypeId(attributeMapping.getAttributeControlTypeId().getLabel())
                .displayOrder(attributeMapping.getDisplayOrder())
                .productAttributeValueResponses(productAttributeValueResponses)
                .build();
    }

    @Override
    @Transactional
    public void addProductProductAttributeMapping(ProductProductAttributeMappingRequest request) {

        getProductOrThrow(request.getProductId());
        validateProductAttribute(request.getProductAttributeId());
        checkProductAttributeExits(request.getProductAttributeId(), request.getProductId());

        ProductProductAttributeMapping attributeMapping = productProductAttributeMappingMapper.toEntity(request);
        ProductProductAttributeMapping attributeMappingSaved =
                productProductAttributeMappingRepository.save(attributeMapping);

        if (request.getProductAttributeValueRequests() != null) {
            productAttributeValueService.createProductAttributeValues(
                    request.getProductAttributeValueRequests(),
                    attributeMappingSaved.getId()
            );
        }
    }

    @Override
    @Transactional
    public void updateProductProductAttributeMapping(Long id, ProductProductAttributeMappingRequest request) {

        ProductProductAttributeMapping existingEntity = getMappingOrThrow(id);
        getProductOrThrow(request.getProductId());
        validateProductAttribute(request.getProductAttributeId());

        // Kiểm tra nếu ProductAttribute thay đổi
        if (!existingEntity.getProductAttribute().getId().equals(request.getProductAttributeId())) {
            checkProductAttributeExits(request.getProductAttributeId(), request.getProductId());
        }

        Optional<Product> productOptional = productRepository.findById(request.getProductId());
        if (!productOptional.isPresent()) {
            throw new IllegalArgumentException("Product not found with id: " + request.getProductId());
        }

        Product product = productOptional.get();
        List<ProductAttributeCombination> productAttributeCombination = productAttributeCombinationRepository
                .findByProduct(product);

        Optional<ProductAttribute> productAttributeOptional = productAttributeRepository.findById(request.getProductAttributeId());
        if (!productAttributeOptional.isPresent()) {
            throw new IllegalArgumentException("Product attribute not found with id: " + request.getProductAttributeId());
        }

        ProductAttribute productAttribute = productAttributeOptional.get();
        String attributeName = productAttribute.getName();

        for (ProductAttributeCombination combination : productAttributeCombination) {
            String attributesXml = combination.getAttributesXml();
            JsonObject jsonObject = JsonParser.parseString(attributesXml).getAsJsonObject();
            JsonArray attributes = jsonObject.getAsJsonArray("attributes");

            if (containsAttribute(attributes, attributeName)) {
                attributesXml = updateAttributeNameJson(attributesXml, attributeName, productAttribute.getName());
            }

            combination.setAttributesXml(attributesXml);
            productAttributeCombinationRepository.save(combination);
        }

        ProductProductAttributeMapping attributeMapping = productProductAttributeMappingMapper.toEntity(request);
        productProductAttributeMappingRepository.save(attributeMapping);

        if (request.getProductAttributeValueRequests() != null) {
            productAttributeValueService.createProductAttributeValues(request.getProductAttributeValueRequests(), id);
        }
    }

    private String updateAttributeNameJson(String currentXml, String oldAttributeName, String newAttributeName) {
        JsonObject jsonObject = JsonParser.parseString(currentXml).getAsJsonObject();
        JsonArray attributes = jsonObject.getAsJsonArray("attributes");

        String lowerCaseOldAttributeName = oldAttributeName.toLowerCase();
        String lowerCaseNewAttributeName = newAttributeName.toLowerCase();

        for (JsonElement element : attributes) {
            if (element.isJsonObject()) {
                JsonObject attribute = element.getAsJsonObject();

                for (String key : attribute.keySet()) {
                    if (key.toLowerCase().equals(lowerCaseOldAttributeName)) {
                        String currentValue = attribute.get(key).getAsString();
                        attribute.remove(key);
                        attribute.addProperty(newAttributeName, currentValue);
                        break;
                    }
                }
            }
        }

        return jsonObject.toString();
    }

    private boolean containsAttribute(JsonArray attributes, String attributeName) {
        String lowerCaseAttributeName = attributeName.toLowerCase();
        for (JsonElement element : attributes) {
            if (element.isJsonObject()) {
                JsonObject attribute = element.getAsJsonObject();

                for (String key : attribute.keySet()) {
                    if (key.toLowerCase().equals(lowerCaseAttributeName)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public void deleteProductProductAttributeMapping(Long id) {
        if (!productProductAttributeMappingRepository.existsById(id)) {
            throw new ResourceNotFoundException("Product product attribute mapping with id not found: " + id);
        }
        productProductAttributeMappingRepository.deleteById(id);
    }

    private void getProductOrThrow(Long productId) {
        boolean exists = productRepository.existsById(productId);
        if (!exists) {
            throw new ResourceNotFoundException("Product with id not found: " + productId);
        }
    }

    private ProductProductAttributeMapping getMappingOrThrow(Long id) {
        return productProductAttributeMappingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product product attribute mapping with id not found: " + id));
    }

    public void validateProductAttribute(Long productAttributeId) {
        if (productAttributeId != null && !productAttributeRepository.existsById(productAttributeId)) {
            throw new ResourceNotFoundException("Product attribute with id not found: " + productAttributeId);
        }
    }

    public void checkProductAttributeExits(Long productAttributeId, Long productId) {

        ProductAttribute productAttribute = productAttributeRepository.findById(productAttributeId)
                .orElseThrow(() -> new ResourceNotFoundException("Product attribute with id not found: " + productAttributeId));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id not found: " + productId));

        if (productProductAttributeMappingRepository.existsByProductAttributeAndProduct(productAttribute, product)) {
            throw new IllegalArgumentException("Product attribute " + productAttribute.getName() + " already exists in product");
        }
    }

    @Override
    public List<ProductProductAttributeMappingDetailResponse> getProductProductAttributeMappingByproductId(Long productId) {

        return productProductAttributeMappingRepository.findByProductId(productId).stream()
                .map(mapping -> ProductProductAttributeMappingDetailResponse.builder()
                        .id(mapping.getId())
                        .attName(mapping.getProductAttribute().getName())
                        .productId(mapping.getProduct().getId())
                        .productAttributeId(mapping.getProductAttribute().getId())
                        .textPrompt(mapping.getTextPrompt())
                        .isRequired(mapping.getIsRequired())
                        .attributeControlTypeId(mapping.getAttributeControlTypeId() != null ? mapping.getAttributeControlTypeId().name() : null)
                        .displayOrder(mapping.getDisplayOrder())
                        .productAttributeValueResponses(fetchProductAttributeValues(mapping.getId()))
                        .build())
                .toList();
    }

    private List<ProductAttributeValueResponse> fetchProductAttributeValues(Long productAttributeMappingId) {
        List<ProductAttributeValue> values = productAttributeValueRepository
                .findByProductAttributeMappingId(productAttributeMappingId);

        return values.stream()
                .map(productAttributeValueMapper::toDto)
                .toList();
    }

}
