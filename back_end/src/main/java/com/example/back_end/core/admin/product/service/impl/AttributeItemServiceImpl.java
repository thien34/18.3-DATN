package com.example.back_end.core.admin.product.service.impl;

import com.example.back_end.core.admin.product.payload.request.AttributeItemRequest;
import com.example.back_end.core.admin.product.service.AttributeItemService;
import com.example.back_end.entity.AttributeItem;
import com.example.back_end.repository.AttributeItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class AttributeItemServiceImpl implements AttributeItemService {

    private final AttributeItemRepository attributeItemRepository;


    @Override
    public void insert(List<AttributeItemRequest> attributeItemRequests, Long combinationId) {
        List<AttributeItem> attributeItems = attributeItemRequests
                .stream().map(request -> toEntity(request, combinationId))
                        .toList();

        attributeItemRepository.saveAll(attributeItems);
    }

    private AttributeItem toEntity(AttributeItemRequest request, Long combinationId) {
        AttributeItem attributeItem = new AttributeItem();
        attributeItem.setId(request.getId());
        attributeItem.setProductId(request.getProductId());
        attributeItem.setAttributeId(request.getAttributeId());
        attributeItem.setItemId(request.getItemId());
        attributeItem.setCombinationId(combinationId);
        return attributeItem;
    }
}
