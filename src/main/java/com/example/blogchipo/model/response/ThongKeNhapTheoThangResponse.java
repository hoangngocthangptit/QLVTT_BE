package com.example.blogchipo.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
public class ThongKeNhapTheoThangResponse {
    @JsonProperty("soLuongNhap")
    private Integer soLuongNhap;
    @JsonProperty("tongDonGiaNhap")
    private Integer tongDonGiaNhap;
    @JsonProperty("thang")
    private Integer thang;
    @JsonProperty("nam")
    private Integer nam;
}
