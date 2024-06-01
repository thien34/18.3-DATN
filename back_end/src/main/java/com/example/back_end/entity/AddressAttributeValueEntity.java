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
@Table(name = "address_attribute_value", schema = "public", catalog = "datn")
public class AddressAttributeValueEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "address_attribute_id", nullable = true)
    private AddressAttributeEntity addressAttributeEntity;
    @Basic
    @Column(name = "name", nullable = true, length = 255)
    private String name;
    @Basic
    @Column(name = "is_pre_selected", nullable = true)
    private Boolean isPreSelected;
    @Basic
    @Column(name = "display_order", nullable = true)
    private Integer displayOrder;

}
