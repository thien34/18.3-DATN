package com.example.back_end.core.service.product.payload.response;

public interface ProductTagCountProjection {
    
    Long getProductTagId();

    Integer getTaggedProductCount();
}
