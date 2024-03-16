package com.example.clotheswebsite.controller;

import com.example.clotheswebsite.entity.Supplier;
import com.example.clotheswebsite.message.ResponseMessage;
import com.example.clotheswebsite.repository.SupplierRepository;
import com.example.clotheswebsite.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/suppliers")
public class SupplierController {
    @Autowired
    private SupplierService supplierService;

    @GetMapping
    public ResponseEntity<List<Supplier>> getAllSuppliers(){
        List<Supplier> suppliers = supplierService.getAllSuppliers();
        return ResponseEntity.ok(suppliers);
    }

    @PostMapping
    public ResponseEntity<Supplier> addSupplier(@RequestBody Supplier supplier){
        return ResponseEntity.ok(supplierService.addSupplier(supplier));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Supplier> updateSupllier(@RequestBody Supplier updatedSupplier, @PathVariable("id") long supplierId){
        return ResponseEntity.ok(supplierService.updateSupplier(updatedSupplier, supplierId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMessage> deleteSupplier(@PathVariable("id") long supplierId){
        supplierService.deleteSupplier(supplierId);
        ResponseMessage mess = new ResponseMessage("Delete supplier with id: " + supplierId + " successfully!");
        return ResponseEntity.ok(mess);
    }

}
