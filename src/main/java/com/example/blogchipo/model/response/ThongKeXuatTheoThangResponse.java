package com.example.blogchipo.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
public class ThongKeXuatTheoThangResponse {
    @JsonProperty("soLuongXuat")
    private Integer soLuongXuat;
    @JsonProperty("tongDonGiaXuat")
    private Integer tongDonGiaXuat;
    @JsonProperty("thang")
    private Integer thang;
    @JsonProperty("nam")
    private Integer nam;
}
