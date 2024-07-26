package com.example.back_end.core.admin.product.service;

import com.example.back_end.core.admin.product.payload.request.ProductSpecificationAttributeMappingRequest;
import com.example.back_end.core.admin.product.payload.response.ProductSpecificationAttributeMappingResponse;
import com.example.back_end.core.common.PageResponse;

import java.util.List;

public interface ProductSpecificationAttributeMappingService {
    PageResponse<?> getAllProductSpecificationAttributeMapping(String name, int pageNo, int pageSize);
    ProductSpecificationAttributeMappingResponse createProductSpecificationAttributeMapping(ProductSpecificationAttributeMappingRequest request);
    ProductSpecificationAttributeMappingResponse getProductSpecificationAttributeMappingById(Long id);
    void deleteProductSpecificationAttribute(List<Long> ids);
}
