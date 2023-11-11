package com.example.blogchipo.controller;

import com.example.blogchipo.entity.KhoEntity;
import com.example.blogchipo.model.response.KhoResponse;
import com.example.blogchipo.repository.KhoRepository;
import com.example.blogchipo.response.Response;
import com.example.blogchipo.service.AllServices;
import com.example.blogchipo.until.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RequestMapping("/kho")
@RestController
public class KhoController {
    @Autowired
    private KhoRepository repository;

    @Autowired
    AllServices allServices;

    @Autowired
    CommonUtils commonUtils;

    @GetMapping("")
    public ResponseEntity<Response> getAll() {
        List<KhoResponse> dsKho = allServices.getAllKho();
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(new Response("thành công", 200, dsKho));
    }
    @PostMapping("/insert")
    public ResponseEntity<Response> insert(@RequestBody KhoEntity kho) {
        if (kho.getMakho() == null) {
            kho.setMakho(commonUtils.genRandomId("KHO"));
        }
        kho.setMaCN("CN0001");
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(new Response("Thêm thành công", 200, repository.save(kho)));
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response> insert(@PathVariable  String id) {
        repository.deleteById(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(new Response("Xóa thành công", 200, repository.findById(id)));
    }
    @PutMapping("/edit/{id}")
    public ResponseEntity<Response> updateCategory(@PathVariable String id, @RequestBody KhoEntity data) {
        KhoEntity kho = repository.findById(id).get();
        kho.setTenKho(data.getTenKho());
        kho.setDiaChi(data.getDiaChi());
        repository.save(kho);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(new Response("Sửa thành công", 200, kho));
    }

}
