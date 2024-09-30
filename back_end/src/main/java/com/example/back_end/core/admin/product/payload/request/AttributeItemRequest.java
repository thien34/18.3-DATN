package com.example.back_end.core.admin.product.payload.request;

import lombok.Getter;

@Getter
public class AttributeItemRequest {
    private Long id;
    private Long productId;
    private Long attributeId;
    private Long itemId;
}
