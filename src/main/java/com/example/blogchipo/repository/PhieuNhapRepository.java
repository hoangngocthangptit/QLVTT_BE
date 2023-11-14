package com.example.blogchipo.repository;

import com.example.blogchipo.entity.PhieuNhapEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PhieuNhapRepository extends JpaRepository<PhieuNhapEntity, String> {
    List<PhieuNhapEntity> findByMaNVIgnoreCase(String maNV);

    List<PhieuNhapEntity> findByMaKhoIgnoreCase(String maKho);

    List<PhieuNhapEntity> findByNgay(LocalDate ngay);

    List<PhieuNhapEntity> findByMaKho(String maKho);
    @Procedure(procedureName = "sp_ThongKeNhapXuat")
    List<Object[]> thongKeNhapXuat();

    @Procedure(procedureName = "sp_ThongKeNhapXuatChiNhanh")
    List<Object[]> thongKeNhapXuatChiNhanh(String maCN);

    @Procedure(procedureName = "sp_ThongKeNhapXuatKhoTheoNgay")
    List<Object[]> thongKeNhapXuatKhoTheoNgay();

    @Procedure(procedureName = "sp_ThongKeNhapTheoThang")
    List<Object[]> thongKeNhapTheoThang();

    @Procedure(procedureName = "sp_ThongKeXuatTheoThang")
    List<Object[]> thongKeXuatTheoThang();
}