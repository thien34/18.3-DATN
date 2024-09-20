package com.example.back_end.repository;

import com.example.back_end.core.admin.stockquantityhistory.payload.response.StockQuantityHistoryResponse;
import com.example.back_end.entity.StockQuantityHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StockQuantityHistoryRepository extends JpaRepository<StockQuantityHistory, Long> {

    @Query(value = "select  new com.example.back_end.core.admin.stockquantityhistory.payload.response." +
            "StockQuantityHistoryResponse( sqh.id, pac.attributesXml " +
            ",sqh.quantityAdjustment,sqh.stockQuantity," +
            " sqh.message,sqh.createdDate) from StockQuantityHistory sqh" +
            " left join ProductAttributeCombination pac on pac.id=sqh.productAttributeCombination.id " +
            " where  sqh.product.id = :productId ")
    Page<StockQuantityHistoryResponse> findAll(Long productId, Pageable pageable);

}
