package com.example.back_end.repository;

import com.example.back_end.entity.AttributeItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AttributeItemRepository extends JpaRepository<AttributeItem, Long> {
    List<AttributeItem> findByProductId(Long productId);

    @Modifying
    @Query(value = "DELETE FROM attribute_item WHERE combination_id = :combinationId", nativeQuery = true)
    void deleteByCombinationId(@Param("combinationId") Long combinationId);
}
