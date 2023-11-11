package com.example.blogchipo.model.response;

import com.example.blogchipo.entity.CtpxEntity;
import com.example.blogchipo.entity.PhieuNhapEntity;
import com.example.blogchipo.entity.PhieuXuatEntity;
import com.example.blogchipo.model.request.NewCTPXRequest;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PhieuXuatResponse {
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
    @JsonProperty("vatTu")
    private String vatTu;
    @JsonProperty("totalValue")
    private Double totalValue;

    @JsonProperty("total")
    private Integer total;
    @JsonProperty("ctpx")
    private List<NewCTPXRequest> ctpx = new ArrayList<>();

    public PhieuXuatResponse(PhieuXuatEntity phieuXuatEntity, String tenKho) {
        this.maPN = phieuXuatEntity.getMaPX();
        this.ngay = phieuXuatEntity.getNgay().toString();
        this.maKho = phieuXuatEntity.getMaKho();
        this.tenKho = tenKho;
        this.maNV = phieuXuatEntity.getMaNV();
        Double sum = 0.0;
        Integer total = 0;
        for (CtpxEntity ctpxEntity: phieuXuatEntity.getCtpx()) {
            NewCTPXRequest newCTPXRequest = new NewCTPXRequest();
            newCTPXRequest.setMaVT(ctpxEntity.getId().getMaVT());
            newCTPXRequest.setSoLuong(ctpxEntity.getSoluong());
            newCTPXRequest.setDonGia(ctpxEntity.getDongia());
            this.ctpx.add(newCTPXRequest);
            sum += ctpxEntity.getDongia();
            total += ctpxEntity.getSoluong();
        }
        this.totalValue = sum;
        this.vatTu = this.ctpx.stream().map(a->a.getMaVT()).collect(Collectors.joining(","));
        this.total = total;

    }
}
