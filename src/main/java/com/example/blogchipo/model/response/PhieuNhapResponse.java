package com.example.blogchipo.model.response;

import com.example.blogchipo.entity.CtpnEntity;
import com.example.blogchipo.entity.PhieuNhapEntity;
import com.example.blogchipo.model.request.NewCTPNRequest;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PhieuNhapResponse {
    @JsonProperty("maPN")
    private String maPN;
    @JsonProperty("ngay")
    private String ngay;
    @JsonProperty("maKho")
    private String maKho;
    @JsonProperty("tenKho")
    private String tenKho;
    @JsonProperty("maNV")
    private String maNV;
    @JsonProperty("ctpn")
    private List<NewCTPNRequest> ctpn = new ArrayList<>();

    public PhieuNhapResponse(PhieuNhapEntity phieuNhapEntity, String tenKho) {
        this.maPN = phieuNhapEntity.getMaPN();
        this.ngay = phieuNhapEntity.getNgay().toString();
        this.maKho = phieuNhapEntity.getMaKho();
        this.tenKho = tenKho;
        this.maNV = phieuNhapEntity.getMaNV();
        for (CtpnEntity ctpnEntity: phieuNhapEntity.getCtpns()) {
            NewCTPNRequest newCTPNRequest1 = new NewCTPNRequest();
            newCTPNRequest1.setMaVT(ctpnEntity.getId().getMaVT());
            newCTPNRequest1.setSoLuong(ctpnEntity.getSoluong());
            newCTPNRequest1.setDonGia(ctpnEntity.getDongia());
            this.ctpn.add(newCTPNRequest1);
        }
    }
}
