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
@Table(name = "specification_attribute_option", schema = "public", catalog = "datn")
public class SpecificationAttributeOptionEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "specification_attribute_id", nullable = true)
    private SpecificationAttributeEntity specificationAttribute;
    @Basic
    @Column(name = "name", nullable = true, length = 255)
    private String name;
    @Basic
    @Column(name = "color_squares_rgb", nullable = true, length = 255)
    private String colorSquaresRgb;
    @Basic
    @Column(name = "display_order", nullable = true)
    private Integer displayOrder;

}