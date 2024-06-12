package com.example.back_end.core.service.product.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
public class ProductTagDtoResponse {

    private Long id;

    private String name;

    private Long productId;

}

