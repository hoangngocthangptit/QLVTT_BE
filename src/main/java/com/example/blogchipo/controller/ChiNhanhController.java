package com.example.blogchipo.controller;

import com.example.blogchipo.entity.ChiNhanhEntity;
import com.example.blogchipo.repository.ChiNhanhRepository;
import com.example.blogchipo.response.Response;
import com.example.blogchipo.service.AllServices;
import com.example.blogchipo.until.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RequestMapping("/chi-nhanh")
@RestController
public class ChiNhanhController {
    @Autowired
    private ChiNhanhRepository repository;

    @Autowired
    AllServices allServices;

    @Autowired
    CommonUtils commonUtils;

    @GetMapping("")
    public ResponseEntity<Response> getAll() {
        List<ChiNhanhEntity> users = (List<ChiNhanhEntity>) repository.findAll();
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(new Response("thành công", 200, users));
    }
    @PostMapping("/insert")
    public ResponseEntity<Response> insert(@RequestBody ChiNhanhEntity chiNhanhEntity) {
        if (chiNhanhEntity.getMaCN() == null) {
            chiNhanhEntity.setMaCN(commonUtils.genRandomId("CN"));
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(new Response("Thêm thành công", 200, repository.save(chiNhanhEntity)));
    }
    @DeleteMapping("/delete/{maCN}")
    public ResponseEntity<Response> deleteChiNhanh(@PathVariable  String maCN) {
//        repository.deleteById(maCN);
        allServices.deleteChiNhanh(maCN);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(new Response("Xóa thành công", 200, null));
    }
    @PutMapping("/edit/{id}")
    public ResponseEntity<Response> updateCategory(@PathVariable String id, @RequestBody ChiNhanhEntity data) {
        ChiNhanhEntity chiNhanh = repository.findById(id).get();
        chiNhanh.setChiNhanh(data.getChiNhanh());
        chiNhanh.setDiaChi(data.getDiaChi());
        chiNhanh.setSdt(data.getSdt());
        repository.save(chiNhanh);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(new Response("Sửa thành công", 200, chiNhanh));
    }
}
