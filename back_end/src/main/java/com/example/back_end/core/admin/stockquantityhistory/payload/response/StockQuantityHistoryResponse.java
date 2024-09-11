package com.example.back_end.core.admin.stockquantityhistory.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StockQuantityHistoryResponse {

    private Long id;

    private String attributesXml;

    private Integer quantityAdjustment;

    private Integer stockQuantity;

    private String message;

    private LocalDateTime createdDate;

}

