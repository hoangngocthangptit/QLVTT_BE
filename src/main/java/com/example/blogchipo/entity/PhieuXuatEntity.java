package com.example.blogchipo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "PhieuXuat")
public class PhieuXuatEntity {
    @Id
    @Nationalized
    @Column(name = "MaPX", nullable = false)
    private String maPX;

    @Column(name = "Ngay")
    private LocalDate ngay;

    @Nationalized
    @Column(name = "MaKho")
    private String maKho;

    @Nationalized
    @Column(name = "MaNV")
    private String maNV;

    @OneToMany(mappedBy = "phieuXuat", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<CtpxEntity> ctpx = new LinkedHashSet<>();
}