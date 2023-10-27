package com.example.blogchipo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

@Getter
@Setter
@Entity
@Table(name = "NhaCungCap")
public class NhaCungCapEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "MaNCC", nullable = false)
    private Integer id;

    @Nationalized
    @Column(name = "TenNCC")
    private String tenNCC;

    @Nationalized
    @Column(name = "DiaChi")
    private String diaChi;

    @Column(name = "SDT", length = 15)
    private String sdt;

}