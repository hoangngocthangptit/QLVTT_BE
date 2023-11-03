package com.example.blogchipo.service;

import com.example.blogchipo.entity.*;
import com.example.blogchipo.model.request.NewCTPNRequest;
import com.example.blogchipo.model.request.NewCTPXRequest;
import com.example.blogchipo.model.request.NewPhieuNhapRequest;
import com.example.blogchipo.model.request.NewPhieuXuatRequest;
import com.example.blogchipo.model.response.PhieuNhapResponse;
import com.example.blogchipo.model.response.PhieuXuatResponse;
import com.example.blogchipo.repository.PhieuNhapRepository;
import com.example.blogchipo.repository.PhieuXuatRepository;
import com.example.blogchipo.repository.VatTuRepository;
import com.example.blogchipo.until.CommonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
            dsPhieuNhap.add(new PhieuNhapResponse(phieuNhapEntity));
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
        return new PhieuNhapResponse(phieuNhapEntity);
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
        return new PhieuNhapResponse(phieuNhapEntity);
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
            dsPhieuXuat.add(new PhieuXuatResponse(phieuXuatEntity));
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
        return new PhieuXuatResponse(phieuXuatEntity);
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
        return new PhieuXuatResponse(phieuXuatEntity);
    }
}
