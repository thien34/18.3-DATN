package com.example.back_end.core.admin.product.controller;

import com.example.back_end.core.admin.product.payload.request.SpecificationAttributeGroupRequest;
import com.example.back_end.core.admin.product.payload.response.SpecificationAttributeGroupResponse;
import com.example.back_end.core.admin.product.service.SpecificationAttributeGroupService;
import com.example.back_end.core.common.PageResponse;
import com.example.back_end.core.common.ResponseData;
import com.example.back_end.infrastructure.constant.SuccessCode;
import io.swagger.v3.oas.annotations.Operation;
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
@RequestMapping("/admin/specification-attribute-groups")
public class SpecificationAttributeGroupController {

    private final SpecificationAttributeGroupService specificationAttributeGroupService;

    @Operation(method = "POST", summary = "Add new specification attribute group",
            description = "Send a request via this API to create new specification attribute group")
    @PostMapping
    public ResponseData<SpecificationAttributeGroupResponse> createSpecificationAttributeOption(
            @Valid @RequestBody SpecificationAttributeGroupRequest dto) {

        SpecificationAttributeGroupResponse response = specificationAttributeGroupService
                .createSpecificationAttributeGroup(dto);

        return ResponseData.<SpecificationAttributeGroupResponse>builder()
                .status(HttpStatus.OK.value())
                .message(SuccessCode.SPECIFICATION_ATTRIBUTE_CREATED.getMessage())
                .data(response)
                .build();
    }

    @Operation(method = "GET", summary = "Get all specification attribute groups",
            description = "Send a request via this API to get all specification attribute groups")
    @GetMapping
    public ResponseData<PageResponse<List<SpecificationAttributeGroupResponse>>> getAll(
            @RequestParam(value = "name", defaultValue = "") String name,
            @RequestParam(value = "pageNo", defaultValue = "0") int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "6") int pageSize) {

        PageResponse<List<SpecificationAttributeGroupResponse>> response = specificationAttributeGroupService
                .getAllSpecificationAttributeGroup(name, pageNo, pageSize);

        return ResponseData.<PageResponse<List<SpecificationAttributeGroupResponse>>>builder()
                .status(HttpStatus.OK.value())
                .message("Get specification attribute groups success")
                .data(response)
                .build();
    }

    @Operation(method = "GET", summary = "Get specification attribute group by ID",
            description = "Send a request via this API to get a specification attribute group by ID")
    @GetMapping("/{id}")
    public ResponseData<SpecificationAttributeGroupResponse> getSpecificationAttributeGroupById(@PathVariable Long id) {

        SpecificationAttributeGroupResponse response = specificationAttributeGroupService
                .getSpecificationAttributeGroupById(id);

        return ResponseData.<SpecificationAttributeGroupResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Get specification attribute group success")
                .data(response)
                .build();
    }

    @Operation(method = "PUT", summary = "Update specification attribute group",
            description = "Send a request via this API to update an existing specification attribute group")
    @PutMapping("/{id}")
    public ResponseData<SpecificationAttributeGroupResponse> updateSpecificationAttributeGroup(
            @PathVariable Long id,
            @Valid @RequestBody SpecificationAttributeGroupRequest request) {

        SpecificationAttributeGroupResponse response = specificationAttributeGroupService
                .updateSpecificationAttributeGroup(id, request);

        return ResponseData.<SpecificationAttributeGroupResponse>builder()
                .status(HttpStatus.OK.value())
                .message(SuccessCode.SPECIFICATION_ATTRIBUTE_UPDATED.getMessage())
                .data(response)
                .build();
    }

    @Operation(method = "DELETE", summary = "Delete specification attribute groups",
            description = "Send a request via this API to delete specification attribute groups")
    @DeleteMapping
    public ResponseData<Void> deleteSpecificationAttributeGroups(@RequestBody List<Long> ids) {

        specificationAttributeGroupService.deleteSpecificationAttributeGroup(ids);

        return ResponseData.<Void>builder()
                .status(HttpStatus.OK.value())
                .message("Delete specification attribute groups success")
                .build();
    }

    @Operation(method = "DELETE", summary = "Delete specification attribute group by ID",
            description = "Send a request via this API to delete a specification attribute group by ID")
    @DeleteMapping("/{id}")
    public ResponseData<Void> deleteSpecificationAttributeGroupById(@PathVariable Long id) {

        specificationAttributeGroupService.deleteSpecificationAttributeGroupById(id);

        return ResponseData.<Void>builder()
                .status(HttpStatus.OK.value())
                .message("Delete specification attribute group success")
                .build();
    }

}
