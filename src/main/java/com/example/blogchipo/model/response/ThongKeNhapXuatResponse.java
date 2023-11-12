package com.example.blogchipo.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
public class ThongKeNhapXuatResponse {
    @JsonProperty("maVT")
    private String maVT;
    @JsonProperty("tenVT")
    private String tenVT;
    @JsonProperty("soLuongNhap")
    private Integer soLuongNhap;
    @JsonProperty("soLuongXuat")
    private Integer soLuongXuat;
    @JsonProperty("donGiaNhap")
    private Integer tongDonGiaNhap;
    @JsonProperty("donGiaXuat")
    private Integer tongDonGiaXuat;

}
