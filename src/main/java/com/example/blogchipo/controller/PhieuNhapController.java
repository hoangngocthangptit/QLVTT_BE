package com.example.blogchipo.controller;

import com.example.blogchipo.entity.ChiNhanhEntity;
import com.example.blogchipo.entity.PhieuNhapEntity;
import com.example.blogchipo.model.request.NewPhieuNhapRequest;
import com.example.blogchipo.model.response.PhieuNhapResponse;
import com.example.blogchipo.repository.PhieuNhapRepository;
import com.example.blogchipo.response.Response;
import com.example.blogchipo.service.AllServices;
import com.example.blogchipo.until.CommonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RequestMapping("/phieu-nhap")
@RestController
public class PhieuNhapController {
    @Autowired
    PhieuNhapRepository phieuNhapRepository;

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
        List<PhieuNhapResponse> dsPhieuNhap = allServices.getAllPhieuNhap(maPN, maNV, maKho, ngay);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(new Response("thành công", 200, dsPhieuNhap));
    }

    @PostMapping("/insert")
    public ResponseEntity<Response> insertPhieuNhap(@RequestBody NewPhieuNhapRequest newPhieuNhapRequest) throws Exception {
        try {
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body(new Response("Thêm thành công", 200, allServices.addPhieuNhap(newPhieuNhapRequest)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new Response("Không được chọn 1 vật tư 2 lần", 400, null));
        }
    }

    @DeleteMapping("/delete/{maPN}")
    public ResponseEntity<Response> deletePhieuNhap(@PathVariable String maPN) {
        phieuNhapRepository.deleteById(maPN);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(new Response("Xóa thành công", 200, phieuNhapRepository.findById(maPN)));
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Response> updatePhieuNhap(@PathVariable String id, @RequestBody NewPhieuNhapRequest data) {
        try {
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body(new Response("Sửa thành công", 200, allServices.updatePhieuNhap(id, data)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new Response("Không được chọn 1 vật tư 2 lần", 400, null));
        }
    }
}
