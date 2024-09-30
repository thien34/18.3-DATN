package com.example.back_end.core.admin.product.service;

import com.example.back_end.core.admin.product.payload.request.AttributeItemRequest;

import java.util.List;

public interface AttributeItemService {

    void insert(List<AttributeItemRequest> attributeItemRequests, Long combinationId);
}
