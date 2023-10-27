package com.example.blogchipo.controller;

import com.example.blogchipo.entity.ChiNhanh;
import com.example.blogchipo.entity.Kho;
import com.example.blogchipo.repository.KhoRepository;
import com.example.blogchipo.response.Response;
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

    @GetMapping("")
    public ResponseEntity<Response> getAll() {
        List<Kho> dsKho = (List<Kho>) repository.findAll();
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(new Response("thành công", 200, dsKho));
    }
    @PostMapping("/insert")
    public ResponseEntity<Response> insert(@RequestBody Kho kho) {
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(new Response("Thêm thành công", 200, repository.save(kho)));
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response> insert(@PathVariable  Long id) {
        repository.deleteById(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(new Response("Xóa thành công", 200, repository.findById(id)));
    }
    @PutMapping("/edit/{id}")
    public ResponseEntity<Response> updateCategory(@PathVariable Long id, @RequestBody Kho data) {
        Kho kho = repository.findById(id).get();
        kho.setTenKho(data.getTenKho());
        kho.setDiaChi(data.getDiaChi());
        repository.save(kho);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(new Response("Sửa thành công", 200, kho));
    }

}
