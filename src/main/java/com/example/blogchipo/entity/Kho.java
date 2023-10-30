package com.example.blogchipo.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Nationalized;

@Entity
@Data
public class Kho {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long maKho;
    @Nationalized
    private String diaChi;
    private long maCN;
    @Nationalized
    private String tenKho;
}
