package com.example.blogchipo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

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
    @OneToMany(mappedBy = "maCN", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Collection<Users> listUsers = new LinkedHashSet<>();

}
