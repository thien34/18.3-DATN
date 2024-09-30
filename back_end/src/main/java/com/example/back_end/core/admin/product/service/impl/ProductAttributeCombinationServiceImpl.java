package com.example.back_end.core.admin.product.service.impl;

import com.example.back_end.core.admin.product.mapper.ProductAttributeCombinationMapper;
import com.example.back_end.core.admin.product.payload.request.AttributeItemRequest;
import com.example.back_end.core.admin.product.payload.request.ProductAttributeCombinationRequest;
import com.example.back_end.core.admin.product.payload.request.ProductAttributeCombinationRequestV2;
import com.example.back_end.core.admin.product.payload.response.AttributeItemResponse;
import com.example.back_end.core.admin.product.payload.response.ProductAttributeCombinationResponse;
import com.example.back_end.core.admin.product.service.AttributeItemService;
import com.example.back_end.core.admin.product.service.ProductAttributeCombinationService;
import com.example.back_end.entity.AttributeItem;
import com.example.back_end.entity.Picture;
import com.example.back_end.entity.ProductAttributeCombination;
import com.example.back_end.entity.ProductAttributeCombinationPicture;
import com.example.back_end.infrastructure.exception.ExistsByNameException;
import com.example.back_end.infrastructure.exception.ResourceNotFoundException;
import com.example.back_end.repository.AttributeItemRepository;
import com.example.back_end.repository.PictureRepository;
import com.example.back_end.repository.ProductAttributeCombinationPictureRepository;
import com.example.back_end.repository.ProductAttributeCombinationRepository;
import com.example.back_end.repository.ProductAttributeRepository;
import com.example.back_end.repository.ProductAttributeValueRepository;
import com.example.back_end.repository.ProductProductAttributeMappingRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ProductAttributeCombinationServiceImpl implements ProductAttributeCombinationService {

    private final ProductAttributeCombinationRepository productAttributeCombinationRepository;
    private final ProductAttributeCombinationMapper productAttributeCombinationMapper;
    private final PictureRepository pictureRepository;
    private final ProductAttributeCombinationPictureRepository combinationPictureRepository;
    private final AttributeItemRepository attributeItemRepository;
    private final AttributeItemService attributeItemService;
    private final ProductAttributeRepository productAttributeRepository;
    private final ProductAttributeValueRepository productAttributeValueRepository;
    private final ProductProductAttributeMappingRepository productProductAttributeMappingRepository;

    @Override
    @Transactional
    public void saveOrUpdateProductAttributeCombination(ProductAttributeCombinationRequest request) {
        ProductAttributeCombination existingCombination = null;
        if (request.getId() != null) {
            existingCombination = productAttributeCombinationRepository.findById(request.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Combination not found with id: " + request.getId()));
        }

        List<ProductAttributeCombination> productAttributeCombinations = productAttributeCombinationRepository
                .findByProductId(request.getProductId());

        if (productAttributeCombinations != null) {
            for (ProductAttributeCombination combination : productAttributeCombinations) {
                if (existingCombination == null || !combination.getId().equals(existingCombination.getId())) {
                    if (combination.getSku().equals(request.getSku())) {
                        throw new ExistsByNameException(Messages.SKU_ALREADY_EXISTS.getMessage());
                    }

                    if (combination.getAttributesXml().equals(request.getAttributesXml())) {
                        throw new ExistsByNameException(Messages.COMBINATION_ALREADY_EXISTS.getMessage());
                    }
                }
            }
        }

        ProductAttributeCombination entity;
        if (existingCombination != null) {
            productAttributeCombinationMapper.updateEntityFromRequest(existingCombination, request);
            entity = existingCombination;
        } else {
            entity = productAttributeCombinationMapper.toEntity(request);
        }

        ProductAttributeCombination entitySave = productAttributeCombinationRepository.save(entity);

        List<Picture> pictures = pictureRepository.findAllById(request.getPictureIds());

        if (pictures.size() != request.getPictureIds().size()) {
            List<Long> foundIds = pictures.stream().map(Picture::getId).toList();
            List<Long> notFoundIds = request.getPictureIds().stream()
                    .filter(pId -> !foundIds.contains(pId))
                    .toList();
            throw new ResourceNotFoundException("Pictures not found with ids: " + notFoundIds);
        }

        if (existingCombination != null) {
            combinationPictureRepository.deleteByProductAttributeCombinationId(request.getId());
        }

        List<ProductAttributeCombinationPicture> attributeCombinationPictures = new ArrayList<>();
        for (Picture picture : pictures) {
            ProductAttributeCombinationPicture combinationPicture = new ProductAttributeCombinationPicture();
            combinationPicture.setPicture(picture);
            combinationPicture.setProductAttributeCombination(entitySave);
            attributeCombinationPictures.add(combinationPicture);
        }

        combinationPictureRepository.saveAll(attributeCombinationPictures);
    }


    @Override
    public List<ProductAttributeCombinationResponse> getByProductId(Long productId) {
        List<ProductAttributeCombinationResponse> responses = new ArrayList<>();
        List<ProductAttributeCombination> combinations = productAttributeCombinationRepository.findByProductId(productId);
        List<AttributeItem> attributeItems = attributeItemRepository.findByProductId(productId);

        combinations.forEach(atb -> {
            List<AttributeItemResponse> attributeItemResponses = new ArrayList<>();
            ProductAttributeCombinationResponse combinationResponse = productAttributeCombinationMapper.toDto(atb);

            attributeItems.stream()
                    .filter(attributeItem -> Objects.equals(attributeItem.getCombinationId(), atb.getId()))
                    .forEach(attributeItem -> {
                        AttributeItemResponse attributeItemResponse = new AttributeItemResponse();
                        attributeItemResponse.setId(attributeItem.getId());

                        productProductAttributeMappingRepository.findById(attributeItem.getAttributeId()).ifPresent(productAttribute -> {
                            attributeItemResponse.setProductId(productId);
                            attributeItemResponse.setAttributeId(productAttribute.getProductAttribute().getId());
                            attributeItemResponse.setAttributeName(productAttribute.getProductAttribute().getName());
                        });

                        productAttributeValueRepository.findById(attributeItem.getItemId()).ifPresent(value -> {
                            attributeItemResponse.setItemId(value.getId());
                            attributeItemResponse.setItemName(value.getName());
                        });

                        attributeItemResponses.add(attributeItemResponse);
                    });

            combinationResponse.setAttributeItemResponses(attributeItemResponses);
            responses.add(combinationResponse);
        });

        return responses;
    }



    @Override
    public void delete(Long id) {

        ProductAttributeCombination combination = productAttributeCombinationRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Messages.COMBINATION_IS_NOT_EXISTS.getMessage()));

        productAttributeCombinationRepository.delete(combination);
    }

    @Override
    @Transactional
    public void insertV2(ProductAttributeCombinationRequestV2 request) {
        List<AttributeItem> attributeItems = attributeItemRepository.findAll();
        List<ProductAttributeCombination> combinations = productAttributeCombinationRepository.findByProductId(request.getProductId());

        if (combinations != null) {

            for (ProductAttributeCombination combination : combinations) {
                if (combination.getSku().equals(request.getSku()) && !request.getSku().isEmpty()) {
                    throw new ExistsByNameException(Messages.SKU_ALREADY_EXISTS.getMessage());
                }
            }

            attributeItems.stream()
                    .filter(attributeItem -> attributeItem.getProductId().equals(request.getProductId()))
                    .forEach(attributeItem -> {
                        request.getAttributeItemRequests().forEach(attributeItemRequest -> {
                            if (request.getId() == null && Objects.equals(attributeItem.getAttributeId(), attributeItemRequest.getAttributeId())
                                    && Objects.equals(attributeItem.getItemId(), attributeItemRequest.getItemId())) {
                                throw new ExistsByNameException(Messages.COMBINATION_IS_NOT_EXISTS.getMessage());
                            }
                        });
                    });

            ProductAttributeCombination entity = productAttributeCombinationMapper.toEntity(request);

            ProductAttributeCombination entitySave = productAttributeCombinationRepository.save(entity);

            List<Picture> pictures = pictureRepository.findAllById(request.getPictureIds());

            if (pictures.size() != request.getPictureIds().size()) {
                List<Long> foundIds = pictures.stream().map(Picture::getId).toList();
                List<Long> notFoundIds = request.getPictureIds().stream()
                        .filter(pId -> !foundIds.contains(pId))
                        .toList();
                throw new ResourceNotFoundException("Pictures not found with ids: " + notFoundIds);
            }

            List<ProductAttributeCombinationPicture> attributeCombinationPictures = new ArrayList<>();
            for (Picture picture : pictures) {
                ProductAttributeCombinationPicture combinationPicture = new ProductAttributeCombinationPicture();
                combinationPicture.setPicture(picture);
                combinationPicture.setProductAttributeCombination(entitySave);
                attributeCombinationPictures.add(combinationPicture);
            }

            combinationPictureRepository.saveAll(attributeCombinationPictures);


            List<AttributeItemRequest> attributeItemRequestList = request.getAttributeItemRequests();
            attributeItemRepository.deleteByCombinationId(entitySave.getId());

            attributeItemService.insert(attributeItemRequestList, entitySave.getId());

        }


    }

    private AttributeItemResponse toDto(AttributeItem attributeItem) {
        AttributeItemResponse attributeItemResponse = new AttributeItemResponse();
        attributeItemResponse.setId(attributeItem.getId());
        attributeItemResponse.setProductId(attributeItem.getProductId());
        attributeItemResponse.setAttributeId(attributeItem.getAttributeId());
        attributeItemResponse.setItemId(attributeItem.getItemId());
        return attributeItemResponse;

    }


    @Getter
    @AllArgsConstructor
    private enum Messages {
        SKU_ALREADY_EXISTS("SKU already exists"),
        COMBINATION_ALREADY_EXISTS("The same combination already exists"),
        COMBINATION_IS_NOT_EXISTS("The same combination is not exists"),

        INVALID_ID_LIST("Invalid ID list provided");
        private final String message;
    }

}
