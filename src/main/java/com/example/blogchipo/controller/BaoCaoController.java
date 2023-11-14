package com.example.blogchipo.controller;

import com.example.blogchipo.response.Response;
import com.example.blogchipo.service.AllServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RequestMapping("/bao-cao")
@RestController
public class BaoCaoController {
    @Autowired
    AllServices allServices;

    @GetMapping("/thong-ke-nhap-xuat")
    public ResponseEntity<Response> thongKeNhapXuat() {
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(new Response("thành công", 200, allServices.thongKeNhapXuat()));
    }

    @GetMapping("/thong-ke-nhap-xuat-chi-nhanh/{maCN}")
    public ResponseEntity<Response> thongKeNhapXuatChiNhanh(@PathVariable String maCN) {
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(new Response("thành công", 200, allServices.thongKeNhapXuatChiNhanh(maCN)));
    }

    @GetMapping("/thong-ke-nhap-xuat-kho-theo-ngay")
    public ResponseEntity<Response> thongKeNhapXuatKhoTheoNgay() {
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(new Response("thành công", 200, allServices.thongKeNhapXuatKhoTheoNgay()));
    }

    @GetMapping("/thong-ke-nhap-theo-thang")
    public ResponseEntity<Response> thongKeNhapTheoThang() {
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(new Response("thành công", 200, allServices.thongKeNhapTheoThang()));
    }

    @GetMapping("/thong-ke-xuat-theo-thang")
    public ResponseEntity<Response> thongKeXuatTheoThang() {
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(new Response("thành công", 200, allServices.thongKeXuatTheoThang()));
    }
}
