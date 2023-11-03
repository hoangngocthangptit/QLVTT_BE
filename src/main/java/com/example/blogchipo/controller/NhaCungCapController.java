package com.example.blogchipo.controller;

import com.example.blogchipo.entity.NhaCungCapEntity;
import com.example.blogchipo.repository.NhaCungCapRepository;
import com.example.blogchipo.response.Response;
import com.example.blogchipo.until.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RequestMapping("/nha-cung-cap")
@RestController
public class NhaCungCapController {
    @Autowired
    private NhaCungCapRepository nhaCungCapRepository;

    @Autowired
    CommonUtils commonUtils;

    @GetMapping("")
    public ResponseEntity<Response> getAll() {
        List<NhaCungCapEntity> dsNhaCungCap = (List<NhaCungCapEntity>) nhaCungCapRepository.findAll();
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(new Response("OK", 200, dsNhaCungCap));
    }

    @PostMapping("/insert")
    public ResponseEntity<Response> insert(@RequestBody NhaCungCapEntity nhaCungCapEntity) {
        if (nhaCungCapEntity.getMaNCC() == null) {
            nhaCungCapEntity.setMaNCC(commonUtils.genRandomId("NCC"));
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(new Response("Thêm thành công", 200, nhaCungCapRepository.save(nhaCungCapEntity)));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response> insert(@PathVariable String maNCC) {
        nhaCungCapRepository.deleteById(maNCC);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(new Response("Xóa thành công", 200, nhaCungCapRepository.findById(maNCC)));
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Response> updateCategory(@PathVariable String id, @RequestBody NhaCungCapEntity data) {
        NhaCungCapEntity nhaCungCapEntity = nhaCungCapRepository.findById(id).get();
        nhaCungCapEntity.setSdt(data.getSdt());
        nhaCungCapEntity.setDiaChi(data.getDiaChi());
        nhaCungCapEntity.setTenNCC(data.getTenNCC());
        nhaCungCapRepository.save(nhaCungCapEntity);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(new Response("Sửa thành công", 200, nhaCungCapEntity));
    }
}
