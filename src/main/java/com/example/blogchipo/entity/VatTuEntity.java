package com.example.blogchipo.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

@Getter
@Setter
@Entity
@Data
@Table(name = "VatTu")
public class VatTuEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "MaVT", nullable = false)
    private Integer id;

    @Nationalized
    @Column(name = "TenVT")
    private String tenVT;

    @Column(name = "DVT", length = 10)
    private String dvt;

    @Column(name = "SoLuongTon")
    private Integer soLuongTon;

}