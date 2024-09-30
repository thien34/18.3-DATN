package com.example.back_end.core.admin.product.payload.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AttributeItemResponse {
    private Long id;
    private Long productId;
    private Long attributeId;
    private String attributeName;
    private Long itemId;
    private String itemName;
}
