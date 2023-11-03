package com.example.blogchipo.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "PhieuNhap")
public class PhieuNhapEntity {
    @Id
    @Nationalized
    @Column(name = "MaPN", nullable = false)
    private String maPN;

    @Column(name = "Ngay")
    private LocalDate ngay;

    @Nationalized
    @Column(name = "MaKho")
    private String maKho;

    @Nationalized
    @Column(name = "MaNV")
    private String maNV;

    @OneToMany(mappedBy = "phieuNhap", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<CtpnEntity> ctpns = new LinkedHashSet<>();
}