package com.example.back_end.core.service.product.service;

import com.example.back_end.core.common.PageResponse;
import com.example.back_end.core.service.product.payload.request.ProductTagRequestDto;
import com.example.back_end.core.service.product.payload.response.ProductTagDtoResponse;

import java.util.List;

public interface ProductTagService {

    void createProductTag(ProductTagRequestDto request);

    void updateProductTag(ProductTagRequestDto request, Long id);

    PageResponse<?> getAll(String name, int pageNo, int pageSize);

    ProductTagDtoResponse getProductTag(Long id);

    void delete(List<Long> ids);

}
