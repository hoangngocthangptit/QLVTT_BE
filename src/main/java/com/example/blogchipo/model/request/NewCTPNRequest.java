package com.example.blogchipo.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class NewCTPNRequest {
    @JsonProperty("maVT")
    private String maVT;
    @JsonProperty("soLuong")
    private String soLuong;
    @JsonProperty("donGia")
    private String donGia;
}
