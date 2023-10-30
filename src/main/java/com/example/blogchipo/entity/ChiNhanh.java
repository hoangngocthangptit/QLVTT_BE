package com.example.blogchipo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.Nationalized;

@Entity
@Data
public class ChiNhanh {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long maCN;
    @Nationalized
    private String chiNhanh;
    @Nationalized
    private String diaChi;
    private String SDT;
}
