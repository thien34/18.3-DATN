package com.example.back_end.core.admin.product.service;

import com.example.back_end.core.admin.product.payload.request.ProductAttributeValueRequest;

import java.util.List;

public interface ProductAttributeValueService {

    void createProductAttributeValue(List<ProductAttributeValueRequest> requests, Long productAttributeMappingId);

}