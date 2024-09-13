package com.example.back_end.core.admin.discount.mapper;

import com.example.back_end.core.admin.discount.payload.request.DiscountRequest;
import com.example.back_end.core.admin.discount.payload.response.DiscountFullResponse;
import com.example.back_end.core.admin.discount.payload.response.DiscountResponse;
import com.example.back_end.entity.Discount;
import com.example.back_end.infrastructure.constant.DiscountLimitationType;
import com.example.back_end.infrastructure.constant.DiscountType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface DiscountMapper {

    @Mapping(target = "discountTypeName", source = "discountTypeId", qualifiedByName = "mapDiscountTypeName")
    DiscountResponse toResponse(Discount discount);

    @Mapping(target = "discountTypeId", source = "discountTypeId", qualifiedByName = "mapDiscountTypeId")
    @Mapping(target = "discountLimitationId", source = "discountLimitationId", qualifiedByName = "mapDiscountLimitationTypeId")
    DiscountFullResponse toGetOneResponse(Discount discount);

    @Mapping(target = "discountLimitationId", source = "discountLimitationId", qualifiedByName = "mapDiscountLimitationType")
    Discount toEntity(DiscountRequest request);

    @Mapping(target = "discountLimitationId", source = "discountLimitationId", qualifiedByName = "mapDiscountLimitationType")
    void updateEntityFromRequest(DiscountRequest discountRequest, @MappingTarget Discount discount);

    @Named("mapDiscountTypeName")
    default String mapDiscountTypeName(DiscountType discountType) {
        return discountType != null ? discountType.name() : null;
    }

    @Named("mapDiscountLimitationTypeName")
    default String mapDiscountLimitationTypeName(DiscountLimitationType discountLimitationType) {
        return discountLimitationType != null ? discountLimitationType.name() : null;
    }
    @Named("mapDiscountTypeId")
    default Integer mapDiscountTypeId(DiscountType discountType) {
        return discountType != null ? discountType.getId() : null;
    }

    @Named("mapDiscountLimitationTypeId")
    default Integer mapDiscountLimitationTypeId(DiscountLimitationType discountLimitationType) {
        return discountLimitationType != null ? discountLimitationType.getId() : null;
    }

    @Named("mapDiscountLimitationType")
    default DiscountLimitationType mapDiscountLimitationType(Integer discountLimitationId) {
        if (discountLimitationId == null) {
            return null;
        }
        return DiscountLimitationType.getById(discountLimitationId);
    }
}
