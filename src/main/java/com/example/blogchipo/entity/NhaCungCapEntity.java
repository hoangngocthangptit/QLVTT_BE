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
@Table(name = "NhaCungCap")
public class NhaCungCapEntity {
    @Id
    @Nationalized
    @Column(name = "MaNCC", nullable = false)
    private String maNCC;

    @Nationalized
    @Column(name = "TenNCC")
    private String tenNCC;

    @Nationalized
    @Column(name = "DiaChi")
    private String diaChi;

    @Nationalized
    @Column(name = "SDT", length = 15)
    private String sdt;
}