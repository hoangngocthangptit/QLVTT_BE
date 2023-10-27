package com.example.blogchipo.controller;

import com.example.blogchipo.entity.VatTuEntity;
import com.example.blogchipo.repository.VatTuRepository;
import com.example.blogchipo.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RequestMapping("/vat-tu")
@RestController
public class VatTuController {
    @Autowired
    private VatTuRepository vatTuRepository;

    @GetMapping("")
    public ResponseEntity<Response> getAll() {
        List<VatTuEntity> dsVatTu = (List<VatTuEntity>) vatTuRepository.findAll();
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(new Response("OK", 200, dsVatTu));
    }

    @PostMapping("/insert")
    public ResponseEntity<Response> insert(@RequestBody VatTuEntity vatTuEntity) {
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(new Response("Thêm thành công", 200, vatTuRepository.save(vatTuEntity)));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response> insert(@PathVariable Integer id) {
        vatTuRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(new Response("Xóa thành công", 200, vatTuRepository.findById(id)));
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Response> updateCategory(@PathVariable Integer id, @RequestBody VatTuEntity data) {
        VatTuEntity vatTu = vatTuRepository.findById(id).get();
        vatTu.setTenVT(data.getTenVT());
        vatTu.setDvt(data.getDvt());
        vatTu.setSoLuongTon(data.getSoLuongTon());
        vatTuRepository.save(vatTu);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(new Response("Sửa thành công", 200, vatTu));
    }
}
