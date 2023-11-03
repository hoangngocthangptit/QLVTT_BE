package com.example.blogchipo.model.response;

import com.example.blogchipo.entity.VatTuEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class VatTuResponse {
    @JsonProperty("maVT")
    private String maVT;
    @JsonProperty("tenVT")
    private String tenVT;
    @JsonProperty("dvt")
    private String dvt;
    @JsonProperty("soLuongTon")
    private int soLuongTon;
    @JsonProperty("maNCC")
    private String maNCC;

    public VatTuResponse(VatTuEntity vatTuEntity) {
        this.maVT = vatTuEntity.getMaVT();
        this.tenVT = vatTuEntity.getTenVT();
        this.dvt = vatTuEntity.getDvt();
        this.soLuongTon = vatTuEntity.getSoLuongTon();
        this.maNCC = vatTuEntity.getMaNCC();
    }
}
