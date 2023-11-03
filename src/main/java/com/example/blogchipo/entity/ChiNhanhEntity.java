package com.example.blogchipo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

@Getter
@Setter
@Entity
@Table(name = "ChiNhanh")
public class ChiNhanhEntity {
    @Id
    @Nationalized
    @Column(name = "MaCN", nullable = false)
    private String maCN;

    @Nationalized
    @Column(name = "ChiNhanh")
    private String chiNhanh;

    @Nationalized
    @Column(name = "DiaChi")
    private String diaChi;

    @Column(name = "SDT", length = 15)
    private String sdt;

}