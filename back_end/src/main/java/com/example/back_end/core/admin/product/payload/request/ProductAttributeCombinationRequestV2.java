package com.example.back_end.core.admin.product.payload.request;

import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class ProductAttributeCombinationRequestV2 {
    private Long id;

    @NotBlank
    private String sku;

    private String gtin;

    private Integer stockQuantity;

    private Boolean allowOutOfStockOrders;

    private String attributesXml;

    private BigDecimal overriddenPrice;

    private Integer minStockQuantity;

    private Long productId;

    private List<Long> pictureIds;

    private String manufacturerPartNumber;
    private List<AttributeItemRequest> attributeItemRequests;

}
