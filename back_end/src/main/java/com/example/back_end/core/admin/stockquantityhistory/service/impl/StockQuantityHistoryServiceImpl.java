package com.example.back_end.core.admin.stockquantityhistory.service.impl;

import com.example.back_end.core.admin.stockquantityhistory.mapper.StockQuantityHistoryMapper;
import com.example.back_end.core.admin.stockquantityhistory.payload.request.StockQuantityHistoryRequest;
import com.example.back_end.core.admin.stockquantityhistory.payload.response.StockQuantityHistoryResponse;
import com.example.back_end.core.admin.stockquantityhistory.service.StockQuantityHistoryServices;
import com.example.back_end.core.common.PageResponse;
import com.example.back_end.core.validator.ValidateUtils;
import com.example.back_end.entity.StockQuantityHistory;
import com.example.back_end.infrastructure.constant.SortType;
import com.example.back_end.infrastructure.utils.PageUtils;
import com.example.back_end.repository.StockQuantityHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StockQuantityHistoryServiceImpl implements StockQuantityHistoryServices {

    private final StockQuantityHistoryMapper stockQuantityHistoryMapper;
    private final StockQuantityHistoryRepository stockQuantityHistoryRepository;

    @Override
    public void createStockQuantityHistory(StockQuantityHistoryRequest stockQuantityHistoryRequest) {

        StockQuantityHistory stockQuantityHistory = stockQuantityHistoryMapper
                .mapToEntity(stockQuantityHistoryRequest);

        stockQuantityHistoryRepository.save(stockQuantityHistory);
    }

    @Override
    public PageResponse<List<StockQuantityHistoryResponse>> getAllHistoryOfProduct(
            Long productId,
            Integer pageNo,
            Integer pageSize) {

        ValidateUtils.validatePageable(pageNo, pageSize);

        Pageable pageable = PageUtils.createPageable(pageNo, pageSize, "id", SortType.DESC.getValue());
        Page<StockQuantityHistoryResponse> stockQuantityHistoryPage = stockQuantityHistoryRepository
                .findAll(productId, pageable);
        return PageResponse.<List<StockQuantityHistoryResponse>>builder()
                .page(stockQuantityHistoryPage.getNumber())
                .size(stockQuantityHistoryPage.getSize())
                .totalPage(stockQuantityHistoryPage.getTotalPages())
                .items(stockQuantityHistoryPage.getContent().stream().toList())
                .build();
    }

}
