package com.example.back_end.core.admin.product.controller;

import com.example.back_end.core.admin.product.payload.request.SpecificationAttributeGroupRequest;
import com.example.back_end.core.admin.product.payload.response.SpecificationAttributeGroupResponse;
import com.example.back_end.core.admin.product.service.SpecificationAttributeGroupService;
import com.example.back_end.core.common.PageResponse;
import com.example.back_end.core.common.ResponseData;
import com.example.back_end.core.common.ResponseError;
import com.example.back_end.infrastructure.constant.SuccessCode;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/specification-attribute-groups")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class SpecificationAttributeGroupController {
    SpecificationAttributeGroupService specificationAttributeGroupService;
    @Operation(method = "POST", summary = "Add new specification attribute group",
            description = "Send a request via this API to create new specification attribute group")
    @PostMapping
    public ResponseData<SpecificationAttributeGroupResponse> createSpecificationAttributeOption(
            @Valid @RequestBody SpecificationAttributeGroupRequest dto) {
        try {
            SpecificationAttributeGroupResponse response = specificationAttributeGroupService.createSpecificationAttributeGroup(dto);

            return ResponseData.<SpecificationAttributeGroupResponse>builder()
                    .status(HttpStatus.OK.value())
                    .message(SuccessCode.SPECIFICATION_ATTRIBUTE_CREATED.getMessage())
                    .data(response)
                    .build();
        } catch (Exception e) {
            log.error("Error creating specification attribute options", e);
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }
    @Operation(method = "GET", summary = "Get all specification attribute groups",
            description = "Send a request via this API to get all specification attribute groups")
    @GetMapping
    public ResponseData<?> getAll(@RequestParam(value = "name", defaultValue = "") String name,
                                  @RequestParam(value = "pageNo", defaultValue = "0") int pageNo,
                                  @RequestParam(value = "pageSize", defaultValue = "6") int pageSize) {
        try {
            PageResponse<?> response = specificationAttributeGroupService.getAllSpecificationAttributeGroup(name, pageNo, pageSize);
            return new ResponseData<>(HttpStatus.OK.value(), "Get specification attribute groups success", response);
        } catch (Exception e) {
            log.error("Error getting specification attribute group", e);
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }
}
