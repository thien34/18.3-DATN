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
@Table(name = "video", schema = "public", catalog = "datn")
public class VideoEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    @Basic
    @Column(name = "video_url", nullable = true, length = 255)
    private String videoUrl;

}