package com.example.blogchipo.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
public class ThongKeNhapXuatKhoTheoNgayResponse {
    @JsonProperty("maKho")
    private String maKho;
    @JsonProperty("tenKho")
    private String tenKho;
    @JsonProperty("ngay")
    private Date ngay;
    @JsonProperty("tongDonGiaNhap")
    private Integer tongDonGiaNhap;
    @JsonProperty("tongSoLuongNhap")
    private Integer tongSoLuongNhap;
    @JsonProperty("tongDonGiaXuat")
    private Integer tongDonGiaXuat;
    @JsonProperty("tongSoLuongXuat")
    private Integer tongSoLuongXuat;
    @JsonProperty("chiNhanh")
    private String chiNhanh;
}
