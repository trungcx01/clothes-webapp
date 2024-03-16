package com.example.clotheswebsite.controller;

import com.example.clotheswebsite.entity.Size;
import com.example.clotheswebsite.message.ResponseMessage;
import com.example.clotheswebsite.service.SizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sizes")
public class SizeController {
    @Autowired
    private SizeService sizeService;

    @GetMapping
    public ResponseEntity<List<Size>> getAllSizes(){
        return ResponseEntity.ok(sizeService.getAllSizes());
    }

    @PostMapping
    public ResponseEntity<Size> addSize(@RequestBody Size size){
        return ResponseEntity.ok(sizeService.addSize(size));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMessage> deleteSize(@PathVariable("id") long sizeId){
        sizeService.deleteSize(sizeId);
        ResponseMessage mess = new ResponseMessage("Delete successfully!");
        return ResponseEntity.ok(mess);
    }
}
