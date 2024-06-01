package com.example.back_end.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "customer_attribute_value", schema = "public", catalog = "store_db")
public class CustomerAttributeValue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "customer_attribute_id", nullable = true)
    private CustomerAttribute customerAttribute;

    @Column(name = "name", nullable = true, length = 255)
    private String name;

    @Column(name = "is_pre_selected", nullable = true)
    private Boolean isPreSelected;

    @Column(name = "display_order", nullable = true)
    private Integer displayOrder;

}
