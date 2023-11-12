package com.example.blogchipo.repository;

import com.example.blogchipo.entity.PhieuNhapEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.Nullable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PhieuNhapRepository extends JpaRepository<PhieuNhapEntity, String> {
    List<PhieuNhapEntity> findByMaNVIgnoreCase(String maNV);

    List<PhieuNhapEntity> findByMaKhoIgnoreCase(String maKho);

    List<PhieuNhapEntity> findByNgay(LocalDate ngay);

    List<PhieuNhapEntity> findByMaKho(String maKho);
}