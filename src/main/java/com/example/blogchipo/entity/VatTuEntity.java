package com.example.blogchipo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "VatTu")
public class VatTuEntity {
    @Id
    @Nationalized
    @Column(name = "MaVT", nullable = false)
    private String maVT;

    @Nationalized
    @Column(name = "TenVT")
    private String tenVT;

    @Column(name = "DVT", length = 10)
    private String dvt;

    @Column(name = "SoLuongTon")
    private Integer soLuongTon;

    @Nationalized
    @Column(name = "MaNCC")
    private String maNCC;

    @OneToMany(mappedBy = "vatTu")
    private Set<CtpnEntity> ctpns;

    @OneToMany(mappedBy = "vatTu")
    private Set<CtpxEntity> ctpxs;
}