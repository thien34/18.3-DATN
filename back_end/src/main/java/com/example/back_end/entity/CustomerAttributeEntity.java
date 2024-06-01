package com.example.back_end.entity;


import jakarta.persistence.*;

import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Entity
@Table(name = "customer_attribute", schema = "public", catalog = "datn")
public class CustomerAttributeEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    @Basic
    @Column(name = "name", nullable = true, length = 255)
    private String name;
    @Basic
    @Column(name = "is_required", nullable = true)
    private Boolean isRequired;
    @Basic
    @Column(name = "attribute_control_type_id", nullable = true)
    private Integer attributeControlTypeId;
    @Basic
    @Column(name = "display_order", nullable = true)
    private Integer displayOrder;

}