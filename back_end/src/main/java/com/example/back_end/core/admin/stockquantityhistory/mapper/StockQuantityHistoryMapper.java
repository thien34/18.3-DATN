package com.example.back_end.core.admin.stockquantityhistory.mapper;

import com.example.back_end.core.admin.stockquantityhistory.payload.request.StockQuantityHistoryRequest;
import com.example.back_end.entity.StockQuantityHistory;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface StockQuantityHistoryMapper {

    StockQuantityHistory mapToEntity(StockQuantityHistoryRequest stockQuantityHistoryRequest);

    void updateStockQuantityHistory(StockQuantityHistoryRequest stockQuantityHistoryRequest, @MappingTarget StockQuantityHistory stockQuantityHistory);

}
