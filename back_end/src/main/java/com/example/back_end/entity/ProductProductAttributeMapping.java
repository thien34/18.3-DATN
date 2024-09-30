package com.example.back_end.entity;

import com.example.back_end.infrastructure.constant.AttributeControlType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "product_product_attribute_mapping")
public class ProductProductAttributeMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne()
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "product_attribute_id")
    private ProductAttribute productAttribute;

    @Column(name = "text_prompt", length = Integer.MAX_VALUE)
    private String textPrompt;

    @Column(name = "is_required")
    private Boolean isRequired;

    @Enumerated
    @Column(name = "attribute_control_type_id")
    private AttributeControlType attributeControlTypeId;

    @Column(name = "display_order")
    private Integer displayOrder;

    @OneToMany
    private List<ProductAttributeValue> productAttributeValues;
}