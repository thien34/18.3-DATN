package com.example.back_end.core.service.category.payload.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryParentResponse {

    private Long id;

    private String name;

    private CategoryParentResponse categoryParent;

}
