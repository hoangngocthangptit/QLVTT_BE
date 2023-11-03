package com.example.blogchipo.controller;

import com.example.blogchipo.model.request.NewPhieuNhapRequest;
import com.example.blogchipo.model.request.NewPhieuXuatRequest;
import com.example.blogchipo.model.response.PhieuNhapResponse;
import com.example.blogchipo.model.response.PhieuXuatResponse;
import com.example.blogchipo.repository.PhieuXuatRepository;
import com.example.blogchipo.response.Response;
import com.example.blogchipo.service.AllServices;
import com.example.blogchipo.until.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RequestMapping("/phieu-xuat")
@RestController
public class PhieuXuatController {
    @Autowired
    PhieuXuatRepository phieuXuatRepository;

    @Autowired
    AllServices allServices;

    @Autowired
    CommonUtils commonUtils;

    @GetMapping("")
    public ResponseEntity<Response> getAll(
            @RequestParam(name = "maPN", required = false) String maPN,
            @RequestParam(name = "maNV", required = false) String maNV,
            @RequestParam(name = "maKho", required = false) String maKho,
            @RequestParam(name = "ngay", required = false) String ngay
    ) {
        List<PhieuXuatResponse> dsPhieuXuat = allServices.getAllPhieuXuat(maPN, maNV, maKho, ngay);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(new Response("thành công", 200, dsPhieuXuat));
    }

    @PostMapping("/insert")
    public ResponseEntity<Response> insertPhieuXuat(@RequestBody NewPhieuXuatRequest newPhieuXuatRequest) throws Exception {
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(new Response("Thêm thành công", 200, allServices.addPhieuXuat(newPhieuXuatRequest)));
    }

    @DeleteMapping("/delete/{maPX}")
    public ResponseEntity<Response> deletePhieuXuat(@PathVariable String maPX) {
        phieuXuatRepository.deleteById(maPX);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(new Response("Xóa thành công", 200, phieuXuatRepository.findById(maPX)));
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Response> updatePhieuXuat(@PathVariable String id, @RequestBody NewPhieuXuatRequest data) {
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(new Response("Sửa thành công", 200, allServices.updatePhieuXuat(id, data)));
    }
}
