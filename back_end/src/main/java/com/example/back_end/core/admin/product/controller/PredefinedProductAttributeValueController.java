package com.example.back_end.core.admin.product.controller;

import com.example.back_end.core.admin.product.payload.request.PredefinedProductAttributeValueRequest;
import com.example.back_end.core.admin.product.payload.response.PredefinedProductAttributeValueResponse;
import com.example.back_end.core.admin.product.service.PredefinedProductAttributeValueService;
import com.example.back_end.core.common.PageResponse;
import com.example.back_end.core.common.ResponseData;
import com.example.back_end.infrastructure.constant.SuccessCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/predefined-product-attribute-values")
public class PredefinedProductAttributeValueController {

    private final PredefinedProductAttributeValueService predefinedProductAttributeValueService;

    @GetMapping
    public ResponseData<PageResponse<List<PredefinedProductAttributeValueResponse>>> getAll(
            @RequestParam(value = "name", defaultValue = "") String name,
            @RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo,
            @RequestParam(value = "pageSize", defaultValue = "6") Integer pageSize) {

        PageResponse<List<PredefinedProductAttributeValueResponse>> response = predefinedProductAttributeValueService
                .getAllPredefinedProductAttributeValue(name, pageNo, pageSize);

        return ResponseData.<PageResponse<List<PredefinedProductAttributeValueResponse>>>builder()
                .status(SuccessCode.PREDEFINED_PRODUCT_ATTRIBUTE_VALUE_GET_ALL.getStatusCode().value())
                .message(SuccessCode.PREDEFINED_PRODUCT_ATTRIBUTE_VALUE_GET_ALL.getMessage())
                .data(response)
                .build();
    }

    @GetMapping("/{id}")
    public ResponseData<PredefinedProductAttributeValueResponse> getPredefinedAttributeValueById(@PathVariable Long id) {

        PredefinedProductAttributeValueResponse response = predefinedProductAttributeValueService
                .getPredefinedAttributeValueById(id);

        return ResponseData.<PredefinedProductAttributeValueResponse>builder()
                .data(response)
                .build();
    }

    @PostMapping
    public ResponseData<Void> createPredefinedProductAttributeValue(
            @Valid @RequestBody PredefinedProductAttributeValueRequest request) {

        predefinedProductAttributeValueService.createProductAttributeValue(request);

        return ResponseData.<Void>builder()
                .status(HttpStatus.OK.value())
                .message(SuccessCode.PREDEFINED_PRODUCT_ATTRIBUTE_VALUE_CREATED.getMessage())
                .build();
    }

    @PutMapping("/{id}")
    public ResponseData<Void> updatePredefinedProductAttributeValue(
            @PathVariable Long id,
            @Valid @RequestBody PredefinedProductAttributeValueRequest request) {

        predefinedProductAttributeValueService.updatePredefinedAttributeValue(id, request);

        return ResponseData.<Void>builder()
                .status(SuccessCode.PREDEFINED_PRODUCT_ATTRIBUTE_VALUE_UPDATED.getStatusCode().value())
                .message(SuccessCode.PREDEFINED_PRODUCT_ATTRIBUTE_VALUE_UPDATED.getMessage())
                .build();
    }

    @DeleteMapping("/{id}")
    public ResponseData<Void> deletePredefinedProductAttributeValue(@PathVariable Long id) {

        predefinedProductAttributeValueService.deletePredefinedAttributeValue(id);

        return ResponseData.<Void>builder()
                .status(SuccessCode.PREDEFINED_PRODUCT_ATTRIBUTE_VALUE_DELETED.getStatusCode().value())
                .message(SuccessCode.PREDEFINED_PRODUCT_ATTRIBUTE_VALUE_DELETED.getMessage())
                .build();
    }

}
