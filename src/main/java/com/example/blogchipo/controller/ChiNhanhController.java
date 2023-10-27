package com.example.blogchipo.controller;

import com.example.blogchipo.entity.ChiNhanh;
import com.example.blogchipo.repository.ChiNhanhRepository;
import com.example.blogchipo.response.Response;
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

    @GetMapping("")
    public ResponseEntity<Response> getAll() {
        List<ChiNhanh> users = (List<ChiNhanh>) repository.findAll();
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(new Response("thành công", 200, users));
    }
    @PostMapping("/insert")
    public ResponseEntity<Response> insert(@RequestBody ChiNhanh chiNhanh) {
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(new Response("Thêm thành công", 200, repository.save(chiNhanh)));
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response> insert(@PathVariable  Long id) {
        repository.deleteById(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(new Response("Xóa thành công", 200, repository.findById(id)));
    }
}
