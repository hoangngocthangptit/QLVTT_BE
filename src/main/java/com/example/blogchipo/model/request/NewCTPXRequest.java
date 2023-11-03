package com.example.blogchipo.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class NewCTPXRequest {
    @JsonProperty("maVT")
    private String maVT;
    @JsonProperty("soLuong")
    private Integer soLuong;
    @JsonProperty("donGia")
    private Double donGia;
}
