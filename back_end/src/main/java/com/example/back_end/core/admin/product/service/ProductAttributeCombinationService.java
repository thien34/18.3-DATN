package com.example.back_end.core.admin.product.service;

import com.example.back_end.core.admin.product.payload.request.ProductAttributeCombinationRequest;
import com.example.back_end.core.admin.product.payload.request.ProductAttributeCombinationRequestV2;
import com.example.back_end.core.admin.product.payload.response.ProductAttributeCombinationResponse;

import java.util.List;

public interface ProductAttributeCombinationService {

    void saveOrUpdateProductAttributeCombination(ProductAttributeCombinationRequest request);

    List<ProductAttributeCombinationResponse> getByProductId(Long productId);

    void delete(Long id);

    void insertV2(ProductAttributeCombinationRequestV2 request);

}
