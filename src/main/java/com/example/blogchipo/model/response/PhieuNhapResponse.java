package com.example.blogchipo.model.response;

import com.example.blogchipo.entity.CtpnEntity;
import com.example.blogchipo.entity.PhieuNhapEntity;
import com.example.blogchipo.model.request.NewCTPNRequest;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    @JsonProperty("vatTu")
    private String vatTu;
    @JsonProperty("totalValue")
    private Double totalValue;
    @JsonProperty("total")
    private Integer total;
    @JsonProperty("ctpn")
    private List<NewCTPNRequest> ctpn = new ArrayList<>();

    public PhieuNhapResponse(PhieuNhapEntity phieuNhapEntity, String tenKho) {
        this.maPN = phieuNhapEntity.getMaPN();
        this.ngay = phieuNhapEntity.getNgay().toString();
        this.maKho = phieuNhapEntity.getMaKho();
        this.tenKho = tenKho;
        Double sumPrice = 0.0;
        Integer total = 0;
        this.maNV = phieuNhapEntity.getMaNV();
        for (CtpnEntity ctpnEntity: phieuNhapEntity.getCtpns()) {
            NewCTPNRequest newCTPNRequest1 = new NewCTPNRequest();
            newCTPNRequest1.setMaVT(ctpnEntity.getId().getMaVT());
            newCTPNRequest1.setSoLuong(ctpnEntity.getSoluong());
            newCTPNRequest1.setDonGia(ctpnEntity.getDongia());
            this.ctpn.add(newCTPNRequest1);
            sumPrice += ctpnEntity.getDongia()*ctpnEntity.getSoluong();
            total += ctpnEntity.getSoluong();
        }
        this.totalValue = sumPrice;
        this.vatTu = this.ctpn.stream().map(a->a.getMaVT()).collect(Collectors.joining(","));
        this.total = total;
    }
}
