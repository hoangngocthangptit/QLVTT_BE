package com.example.blogchipo.repository;

import com.example.blogchipo.entity.PhieuXuatEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface PhieuXuatRepository extends JpaRepository<PhieuXuatEntity, String> {
    List<PhieuXuatEntity> findByMaNVIgnoreCase(String maNV);

    List<PhieuXuatEntity> findByMaKhoIgnoreCase(String maKho);

    List<PhieuXuatEntity> findByNgay(LocalDate ngay);
}