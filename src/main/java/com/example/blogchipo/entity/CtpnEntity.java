package com.example.blogchipo.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "CTPN")
public class CtpnEntity {
    @EmbeddedId
    private CtpnEntityId id;

    @Column(name = "SOLUONG")
    private Integer soluong;

    @Column(name = "DONGIA")
    private Double dongia;

    @ManyToOne()
    @MapsId("maPN")
    @JoinColumn(name = "MaPN")
    private PhieuNhapEntity phieuNhap;

    @ManyToOne()
    @MapsId("maVT")
    @JoinColumn(name = "MaVT")
    private VatTuEntity vatTu;
}