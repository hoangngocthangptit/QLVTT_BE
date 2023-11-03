package com.example.blogchipo.model.request;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class NewPhieuNhapRequest {
    @JsonProperty("ngay")
    private String ngay;
    @JsonProperty("maKho")
    private String maKho;
    @JsonProperty("maNV")
    private String maNV;
    @JsonProperty("ctpn")
    private List<NewCTPNRequest> ctpn;
}
