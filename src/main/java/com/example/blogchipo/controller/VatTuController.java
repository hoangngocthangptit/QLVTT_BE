package com.example.blogchipo.controller;

import com.example.blogchipo.entity.VatTuEntity;
import com.example.blogchipo.model.response.VatTuResponse;
import com.example.blogchipo.repository.VatTuRepository;
import com.example.blogchipo.response.Response;
import com.example.blogchipo.until.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RequestMapping("/vat-tu")
@RestController
public class VatTuController {
    @Autowired
    private VatTuRepository vatTuRepository;

    @Autowired
    CommonUtils commonUtils;

    @GetMapping("")
    public ResponseEntity<Response> getAll() {
        List<VatTuEntity> dsVatTu = (List<VatTuEntity>) vatTuRepository.findAll();
        List<VatTuResponse> vatTuResponses = new ArrayList<>();
        for (VatTuEntity vatTuEntity : dsVatTu) {
            vatTuResponses.add(new VatTuResponse(vatTuEntity));
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(new Response("OK", 200, vatTuResponses));
    }

    @PostMapping("/insert")
    public ResponseEntity<Response> insert(@RequestBody VatTuEntity vatTuEntity) {
        if (vatTuEntity.getMaVT() == null) {
            vatTuEntity.setMaVT(commonUtils.genRandomId("VT"));
        }
        vatTuRepository.save(vatTuEntity);
        VatTuResponse vatTuResponse = new VatTuResponse(vatTuEntity);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(new Response("Thêm thành công", 200, vatTuResponse));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response> insert(@PathVariable String id) {
        vatTuRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(new Response("Xóa thành công", 200, new VatTuEntity()));
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Response> updateCategory(@PathVariable String id, @RequestBody VatTuEntity data) {
        VatTuEntity vatTu = vatTuRepository.findById(id).get();
        vatTu.setTenVT(data.getTenVT());
        vatTu.setDvt(data.getDvt());
        vatTu.setSoLuongTon(data.getSoLuongTon());
        vatTuRepository.save(vatTu);
        VatTuResponse vatTuResponse = new VatTuResponse(vatTu);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(new Response("Sửa thành công", 200, vatTuResponse));
    }
}
