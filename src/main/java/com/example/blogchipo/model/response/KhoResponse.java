package com.example.blogchipo.model.response;

import com.example.blogchipo.entity.KhoEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
public class KhoResponse {
    @JsonProperty("maKho")
    private String maKho;
    @JsonProperty("tenKho")
    private String tenKho;
    @JsonProperty("diaChi")
    private String diaChi;
    @JsonProperty("maCN")
    private String maCN;
    @JsonProperty("tenCN")
    private String tenCN;

    public KhoResponse(KhoEntity khoEntity, String tenCN) {
        this.maKho = khoEntity.getMakho();
        this.tenKho = khoEntity.getTenKho();
        this.diaChi = khoEntity.getDiaChi();
        this.maCN = khoEntity.getMaCN();
        this.tenCN = tenCN;
    }
}
