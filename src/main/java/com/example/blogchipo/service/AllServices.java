package com.example.blogchipo.service;

import com.example.blogchipo.entity.*;
import com.example.blogchipo.model.request.NewCTPNRequest;
import com.example.blogchipo.model.request.NewCTPXRequest;
import com.example.blogchipo.model.request.NewPhieuNhapRequest;
import com.example.blogchipo.model.request.NewPhieuXuatRequest;
import com.example.blogchipo.model.response.*;
import com.example.blogchipo.repository.*;
import com.example.blogchipo.until.CommonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

@Service
public class AllServices {
    @Autowired
    PhieuNhapRepository phieuNhapRepository;

    @Autowired
    PhieuXuatRepository phieuXuatRepository;

    @Autowired
    VatTuRepository vatTuRepository;

    @Autowired
    ChiNhanhRepository chiNhanhRepository;

    @Autowired
    KhoRepository khoRepository;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    CommonUtils commonUtils;

    public List<PhieuNhapResponse> getAllPhieuNhap(String maPN, String maNV, String maKho, String ngay) {
        List<PhieuNhapResponse> dsPhieuNhap = new ArrayList<>();
        List<PhieuNhapEntity> phieuNhapEntities = null;
        if (!StringUtils.isBlank(maPN)) {
            phieuNhapEntities = phieuNhapRepository.findAllById(Collections.singleton(maPN));
        }
        if (!StringUtils.isBlank(maNV)) {
            phieuNhapEntities = phieuNhapRepository.findByMaNVIgnoreCase(maNV);
        }
        if (!StringUtils.isBlank(maKho)) {
            phieuNhapEntities = phieuNhapRepository.findByMaKhoIgnoreCase(maKho);
        }
        if (!StringUtils.isBlank(ngay)) {
            phieuNhapEntities = phieuNhapRepository.findByNgay(LocalDate.parse(ngay));
        }
        if (phieuNhapEntities == null) {
            phieuNhapEntities = phieuNhapRepository.findAll();
        }
        for (PhieuNhapEntity phieuNhapEntity: phieuNhapEntities) {
            String tenKho = khoRepository.findById(phieuNhapEntity.getMaKho()).get().getTenKho();
            dsPhieuNhap.add(new PhieuNhapResponse(phieuNhapEntity, tenKho));
        }
        return dsPhieuNhap;
    }

    public PhieuNhapResponse addPhieuNhap(NewPhieuNhapRequest newPhieuNhapRequest) throws Exception {
        PhieuNhapEntity phieuNhapEntity = new PhieuNhapEntity();
        String maPN = commonUtils.genRandomId("PN");
        phieuNhapEntity.setMaPN(maPN);
        phieuNhapEntity.setNgay(LocalDate.parse(newPhieuNhapRequest.getNgay()));
        phieuNhapEntity.setMaKho(newPhieuNhapRequest.getMaKho());
        phieuNhapEntity.setMaNV(newPhieuNhapRequest.getMaNV());
        Set<CtpnEntity> ctpnEntities = new LinkedHashSet<>();
        for (NewCTPNRequest newCTPNRequest: newPhieuNhapRequest.getCtpn()) {
            String maVT = newCTPNRequest.getMaVT();
            Optional<VatTuEntity> vatTuEntity = vatTuRepository.findById(maVT);
            if (vatTuEntity.isEmpty()) {
                throw new RuntimeException("Vật tư không tồn tại");
            }
            CtpnEntity ctpnEntity = new CtpnEntity();
            ctpnEntity.setDongia(newCTPNRequest.getDonGia());
            ctpnEntity.setSoluong(newCTPNRequest.getSoLuong());
            CtpnEntityId ctpnEntityId = new CtpnEntityId();
            ctpnEntityId.setMaPN(maPN);
            ctpnEntityId.setMaVT(maVT);
            ctpnEntity.setId(ctpnEntityId);

            ctpnEntity.setPhieuNhap(phieuNhapEntity);
            ctpnEntity.setVatTu(vatTuEntity.get());
            ctpnEntities.add(ctpnEntity);
        }
        phieuNhapEntity.setCtpns(ctpnEntities);
        phieuNhapRepository.save(phieuNhapEntity);
        return new PhieuNhapResponse(phieuNhapEntity, khoRepository.findById(phieuNhapEntity.getMaKho()).get().getTenKho());
    }

    public PhieuNhapResponse updatePhieuNhap(String maPN, NewPhieuNhapRequest newPhieuNhapRequest) {
        PhieuNhapEntity phieuNhapEntity = phieuNhapRepository.findById(maPN).get();
        phieuNhapEntity.setNgay(LocalDate.parse(newPhieuNhapRequest.getNgay()));
        phieuNhapEntity.setMaKho(newPhieuNhapRequest.getMaKho());
        phieuNhapEntity.setMaNV(newPhieuNhapRequest.getMaNV());
        Set<CtpnEntity> ctpnEntities = new LinkedHashSet<>();
        for (NewCTPNRequest newCTPNRequest: newPhieuNhapRequest.getCtpn()) {
            String maVT = newCTPNRequest.getMaVT();
            Optional<VatTuEntity> vatTuEntity = vatTuRepository.findById(maVT);
            if (vatTuEntity.isEmpty()) {
                throw new RuntimeException("Vật tư không tồn tại");
            }

            CtpnEntity ctpnEntity = new CtpnEntity();
            ctpnEntity.setDongia(newCTPNRequest.getDonGia());
            ctpnEntity.setSoluong(newCTPNRequest.getSoLuong());
            CtpnEntityId ctpnEntityId = new CtpnEntityId();
            ctpnEntityId.setMaPN(maPN);
            ctpnEntityId.setMaVT(maVT);
            ctpnEntity.setId(ctpnEntityId);

            ctpnEntity.setPhieuNhap(phieuNhapEntity);
            ctpnEntity.setVatTu(vatTuEntity.get());
            ctpnEntities.add(ctpnEntity);
        }
        phieuNhapEntity.setCtpns(ctpnEntities);
        phieuNhapRepository.save(phieuNhapEntity);
        String tenKho = khoRepository.findById(phieuNhapEntity.getMaKho()).get().getTenKho();
        return new PhieuNhapResponse(phieuNhapEntity, tenKho);
    }

    public List<PhieuXuatResponse> getAllPhieuXuat(String maPN, String maNV, String maKho, String ngay) {
        List<PhieuXuatResponse> dsPhieuXuat = new ArrayList<>();
        List<PhieuXuatEntity> phieuXuatEntities = null;
        if (!StringUtils.isBlank(maPN)) {
            phieuXuatEntities = phieuXuatRepository.findAllById(Collections.singleton(maPN));
        }
        if (!StringUtils.isBlank(maNV)) {
            phieuXuatEntities = phieuXuatRepository.findByMaNVIgnoreCase(maNV);
        }
        if (!StringUtils.isBlank(maKho)) {
            phieuXuatEntities = phieuXuatRepository.findByMaKhoIgnoreCase(maKho);
        }
        if (!StringUtils.isBlank(ngay)) {
            phieuXuatEntities = phieuXuatRepository.findByNgay(LocalDate.parse(ngay));
        }
        if (phieuXuatEntities == null) {
            phieuXuatEntities = phieuXuatRepository.findAll();
        }
        for (PhieuXuatEntity phieuXuatEntity: phieuXuatEntities) {
            dsPhieuXuat.add(new PhieuXuatResponse(phieuXuatEntity, khoRepository.findById(phieuXuatEntity.getMaKho()).get().getTenKho()));
        }
        return dsPhieuXuat;
    }

    public PhieuXuatResponse addPhieuXuat(NewPhieuXuatRequest newPhieuXuatRequest) {
        PhieuXuatEntity phieuXuatEntity = new PhieuXuatEntity();
        String maPX = commonUtils.genRandomId("PX");
        phieuXuatEntity.setMaPX(maPX);
        phieuXuatEntity.setNgay(LocalDate.parse(newPhieuXuatRequest.getNgay()));
        phieuXuatEntity.setMaKho(newPhieuXuatRequest.getMaKho());
        phieuXuatEntity.setMaNV(newPhieuXuatRequest.getMaNV());
        Set<CtpxEntity> ctpxEntities = new LinkedHashSet<>();
        for (NewCTPXRequest newCTPXRequest: newPhieuXuatRequest.getCtpx()) {
            String maVT = newCTPXRequest.getMaVT();
            Optional<VatTuEntity> vatTuEntity = vatTuRepository.findById(maVT);
            if (vatTuEntity.isEmpty()) {
                throw new RuntimeException("Vật tư không tồn tại");
            }

            CtpxEntity ctpxEntity = new CtpxEntity();
            ctpxEntity.setDongia(newCTPXRequest.getDonGia());
            ctpxEntity.setSoluong(newCTPXRequest.getSoLuong());
            CtpxEntityId ctpxEntityId = new CtpxEntityId();
            ctpxEntityId.setMaPX(maPX);
            ctpxEntityId.setMaVT(maVT);
            ctpxEntity.setId(ctpxEntityId);

            ctpxEntity.setPhieuXuat(phieuXuatEntity);
            ctpxEntity.setVatTu(vatTuEntity.get());
            ctpxEntities.add(ctpxEntity);
        }
        phieuXuatEntity.setCtpx(ctpxEntities);
        phieuXuatRepository.save(phieuXuatEntity);
        return new PhieuXuatResponse(phieuXuatEntity, khoRepository.findById(phieuXuatEntity.getMaKho()).get().getTenKho());
    }

    public PhieuXuatResponse updatePhieuXuat(String id, NewPhieuXuatRequest data) {
        PhieuXuatEntity phieuXuatEntity = phieuXuatRepository.findById(id).get();
        phieuXuatEntity.setNgay(LocalDate.parse(data.getNgay()));
        phieuXuatEntity.setMaKho(data.getMaKho());
        phieuXuatEntity.setMaNV(data.getMaNV());
        Set<CtpxEntity> ctpxEntities = new LinkedHashSet<>();
        for (NewCTPXRequest newCTPXRequest: data.getCtpx()) {
            String maVT = newCTPXRequest.getMaVT();
            Optional<VatTuEntity> vatTuEntity = vatTuRepository.findById(maVT);
            if (vatTuEntity.isEmpty()) {
                throw new RuntimeException("Vật tư không tồn tại");
            }

            CtpxEntity ctpxEntity = new CtpxEntity();
            ctpxEntity.setDongia(newCTPXRequest.getDonGia());
            ctpxEntity.setSoluong(newCTPXRequest.getSoLuong());
            CtpxEntityId ctpxEntityId = new CtpxEntityId();
            ctpxEntityId.setMaPX(id);
            ctpxEntityId.setMaVT(maVT);
            ctpxEntity.setId(ctpxEntityId);

            ctpxEntity.setPhieuXuat(phieuXuatEntity);
            ctpxEntity.setVatTu(vatTuEntity.get());
            ctpxEntities.add(ctpxEntity);
        }
        phieuXuatEntity.setCtpx(ctpxEntities);
        phieuXuatRepository.save(phieuXuatEntity);
        return new PhieuXuatResponse(phieuXuatEntity, khoRepository.findById(phieuXuatEntity.getMaKho()).get().getTenKho());
    }

    public void deleteChiNhanh(String maCN) {
        // if any NhanVien or Kho is using this ChiNhanh, throw exception
//        Optional<Users> users = usersRepository.findByMaCN(maCN);
//        if (users.isPresent()) {
//            throw new RuntimeException("Chi nhánh đang được sử dụng");
//        }
        Optional<KhoEntity> kho = khoRepository.findByMaCN(maCN);
        if (kho.isPresent()) {
            throw new RuntimeException("Chi nhánh đang được sử dụng");
        }
        chiNhanhRepository.deleteById(maCN);
    }

    public List<KhoResponse> getAllKho() {
        List<KhoResponse> dsKho = new ArrayList<>();
        List<KhoEntity> khoEntities = (List<KhoEntity>) khoRepository.findAll();
        for (KhoEntity khoEntity: khoEntities) {
            dsKho.add(new KhoResponse(khoEntity, chiNhanhRepository.findById(khoEntity.getMaCN()).get().getChiNhanh()));
        }
        return dsKho;
    }

    public void deleteKho(String maKho) throws Exception {
        List<PhieuNhapEntity> phieuNhap = phieuNhapRepository.findByMaKho(maKho);
        if (!phieuNhap.isEmpty()) {
            throw new RuntimeException("Kho đang được sử dụng (trong phiếu nhập)");
        }
        List<PhieuXuatEntity> phieuXuat = phieuXuatRepository.findByMaKho(maKho);
        if (!phieuXuat.isEmpty()) {
            throw new RuntimeException("Kho đang được sử dụng (trong phiếu xuất)");
        }
        khoRepository.deleteById(maKho);
    }

    public void deleteVatTu(String id) throws Exception {
        VatTuEntity vatTuEntity = vatTuRepository.findById(id).get();
        if (!vatTuEntity.getCtpns().isEmpty()) {
            throw new RuntimeException("Vật tư đang được sử dụng (trong phiếu nhập)");
        }
        if (!vatTuEntity.getCtpxs().isEmpty()) {
            throw new RuntimeException("Vật tư đang được sử dụng (trong phiếu xuất)");
        }
        vatTuRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<ThongKeNhapXuatResponse> thongKeNhapXuat() {
        List<ThongKeNhapXuatResponse> thongKeNhapXuatResponse = new ArrayList<>();
        List<Object[]> result = phieuNhapRepository.thongKeNhapXuat();
        for (Object[] objects: result) {
            String maVT = (String) objects[0];
            Integer soLuongNhap = (Integer) objects[1];
            Integer soLuongXuat = (Integer) objects[2];
            Integer tongDonGiaNhap = (Integer) objects[3];
            Integer tongDonGiaXuat = (Integer) objects[4];
            String tenVT = (String) objects[5];
            thongKeNhapXuatResponse.add(new ThongKeNhapXuatResponse(maVT, tenVT, soLuongNhap, soLuongXuat, tongDonGiaNhap, tongDonGiaXuat));
        }
        return thongKeNhapXuatResponse;
    }

    @Transactional(readOnly = true)
    public List<ThongKeNhapXuatResponse> thongKeNhapXuatChiNhanh(String maCN) {
        List<ThongKeNhapXuatResponse> thongKeNhapXuatResponse = new ArrayList<>();
        List<Object[]> result = phieuNhapRepository.thongKeNhapXuatChiNhanh(maCN);
        for (Object[] objects: result) {
            String maVT = (String) objects[0];
            Integer soLuongNhap = (Integer) objects[1];
            Integer soLuongXuat = (Integer) objects[2];
            Integer tongDonGiaNhap = (Integer) objects[3];
            Integer tongDonGiaXuat = (Integer) objects[4];
            String tenVT = (String) objects[5];
            thongKeNhapXuatResponse.add(new ThongKeNhapXuatResponse(maVT, tenVT, soLuongNhap, soLuongXuat, tongDonGiaNhap, tongDonGiaXuat));
        }
        return thongKeNhapXuatResponse;
    }

    @Transactional(readOnly = true)
    public List<ThongKeNhapXuatKhoTheoNgayResponse> thongKeNhapXuatKhoTheoNgay() {
        List<ThongKeNhapXuatKhoTheoNgayResponse> thongKeNhapXuatKhoTheoNgayResponses = new ArrayList<>();
        List<Object[]> result = phieuNhapRepository.thongKeNhapXuatKhoTheoNgay();
        for (Object[] objects: result) {
            String maKho = (String) objects[0];
            String tenKho = (String) objects[1];
            java.sql.Date ngay = (java.sql.Date) objects[2];
            Integer tongDonGiaNhap = (Integer) objects[3];
            Integer tongSoLuongNhap = (Integer) objects[4];
            Integer tongDonGiaXuat = (Integer) objects[5];
            Integer tongSoLuongXuat = (Integer) objects[6];
            String chiNhanh = (String) objects[7];
            thongKeNhapXuatKhoTheoNgayResponses.add(new ThongKeNhapXuatKhoTheoNgayResponse(maKho, tenKho, ngay,
                    tongDonGiaNhap, tongSoLuongNhap, tongDonGiaXuat, tongSoLuongXuat, chiNhanh));
        }
        return thongKeNhapXuatKhoTheoNgayResponses;
    }

    @Transactional(readOnly = true)
    public List<ThongKeNhapTheoThangResponse> thongKeNhapTheoThang() {
        List<ThongKeNhapTheoThangResponse> thongKeNhapTheoThangResponses = new ArrayList<>();
        List<Object[]> result = phieuNhapRepository.thongKeNhapTheoThang();
        for (Object[] objects: result) {
            Integer soLuongNhap = (Integer) objects[0];
            Integer tongDonGiaNhap = (Integer) objects[1];
            Integer thang = (Integer) objects[2];
            Integer nam = (Integer) objects[3];
            thongKeNhapTheoThangResponses.add(new ThongKeNhapTheoThangResponse(soLuongNhap, tongDonGiaNhap, thang, nam));
        }
        return thongKeNhapTheoThangResponses;
    }

    @Transactional(readOnly = true)
    public List<ThongKeXuatTheoThangResponse> thongKeXuatTheoThang() {
        List<ThongKeXuatTheoThangResponse> thongKeXuatTheoThangResponses = new ArrayList<>();
        List<Object[]> result = phieuNhapRepository.thongKeXuatTheoThang();
        for (Object[] objects: result) {
            Integer soLuongXuat = (Integer) objects[0];
            Integer tongDonGiaXuat = (Integer) objects[1];
            Integer thang = (Integer) objects[2];
            Integer nam = (Integer) objects[3];
            thongKeXuatTheoThangResponses.add(new ThongKeXuatTheoThangResponse(soLuongXuat, tongDonGiaXuat, thang, nam));
        }
        return thongKeXuatTheoThangResponses;
    }
}
