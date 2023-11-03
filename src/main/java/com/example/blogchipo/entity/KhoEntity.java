package com.example.blogchipo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

@Getter
@Setter
@Entity
@Table(name = "Kho")
public class KhoEntity {
    @Id
    @Nationalized
    @Column(name = "Makho", nullable = false)
    private String makho;

    @Nationalized
    @Column(name = "TenKho")
    private String tenKho;

    @Nationalized
    @Column(name = "DiaChi")
    private String diaChi;

    @Nationalized
    @Column(name = "MaCN")
    private String maCN;

}