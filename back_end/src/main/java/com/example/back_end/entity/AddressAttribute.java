package com.example.back_end.entity;

import com.example.back_end.infrastructure.constant.AttributeControlType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "address_attribute", schema = "public", catalog = "store_db")
public class AddressAttribute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = true, length = 255)
    private String name;

    @Column(name = "is_required", nullable = true)
    private Boolean isRequired;

    @Column(name = "attribute_control_type_id", nullable = true)
    private AttributeControlType attributeControlType;

    @Column(name = "display_order", nullable = true)
    private Integer displayOrder;

}
