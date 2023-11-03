package com.example.blogchipo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "CTPX")
public class CtpxEntity {
    @EmbeddedId
    private CtpxEntityId id;

    @Column(name = "SOLUONG")
    private Integer soluong;

    @Column(name = "DONGIA")
    private Double dongia;

    @ManyToOne()
    @MapsId("maPX")
    @JoinColumn(name = "MaPX")
    private PhieuXuatEntity phieuXuat;

    @ManyToOne()
    @MapsId("maVT")
    @JoinColumn(name = "MaVT")
    private VatTuEntity vatTu;

}