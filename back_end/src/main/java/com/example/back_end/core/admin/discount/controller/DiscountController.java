package com.example.back_end.core.admin.discount.controller;

import com.example.back_end.core.admin.discount.payload.request.DiscountRequest;
import com.example.back_end.core.admin.discount.payload.response.DiscountResponse;
import com.example.back_end.core.admin.discount.service.DiscountService;
import com.example.back_end.core.common.PageResponse;
import com.example.back_end.core.common.ResponseData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
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
@RequestMapping("/admin/discounts")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DiscountController {
    DiscountService discountService;

    @GetMapping
    public ResponseData<PageResponse<List<DiscountResponse>>> getAllDiscounts(
            @RequestParam(value = "pageNo", defaultValue = "1") int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "6") int pageSize) {

        PageResponse<List<DiscountResponse>> response = discountService.getAllDiscounts(pageNo, pageSize);

        return ResponseData.<PageResponse<List<DiscountResponse>>>builder()
                .status(HttpStatus.OK.value())
                .message("Get all discounts successfully")
                .data(response)
                .build();
    }

    @PostMapping
    @Operation(summary = "Create a new discount", description = "Create a new discount with the provided details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Discount created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request payload")
    })
    public ResponseData<DiscountResponse> createDiscount(@Valid @RequestBody DiscountRequest discountRequest) {
        DiscountResponse createdDiscount = discountService.createDiscount(discountRequest);
        return ResponseData.<DiscountResponse>builder()
                .status(HttpStatus.CREATED.value())
                .message("Discount created successfully")
                .data(createdDiscount)
                .build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update discount by ID", description = "Update an existing discount using the provided ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Discount updated successfully"),
            @ApiResponse(responseCode = "404", description = "Discount not found")
    })
    public ResponseData<DiscountResponse> updateDiscount(
            @PathVariable Long id,
            @Valid @RequestBody DiscountRequest discountRequest) {

        DiscountResponse updatedDiscount = discountService.updateDiscount(id, discountRequest);

        return ResponseData.<DiscountResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Discount updated successfully")
                .data(updatedDiscount)
                .build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a discount by ID", description = "Delete an existing discount using the provided ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Discount deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Discount not found")
    })
    public ResponseData<Void> deleteDiscount(@PathVariable Long id) {
        discountService.deleteDiscount(id);

        return ResponseData.<Void>builder()
                .status(HttpStatus.NO_CONTENT.value())
                .message("Discount deleted successfully")
                .build();
    }

}
