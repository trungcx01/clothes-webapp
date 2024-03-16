package com.example.clotheswebsite.service.impl;

import com.example.clotheswebsite.entity.Supplier;
import com.example.clotheswebsite.repository.SupplierRepository;
import com.example.clotheswebsite.service.SupplierService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierServiceImpl implements SupplierService {

    @Autowired
    public SupplierRepository supplierRepository;

    @Override
    public List<Supplier> getAllSuppliers() {
        return supplierRepository.findAll();
    }

    @Override
    public Supplier getSupplierById(long supplierId) {
        return supplierRepository.findById(supplierId).orElseThrow();
    }

    @Override
    public Supplier addSupplier(Supplier supplier) {
        return supplierRepository.save(supplier);
    }

    @Override
    public Supplier updateSupplier(Supplier updatedSupplier, long supplierId) {
        Supplier supplier = supplierRepository.findById(supplierId).orElseThrow(() -> new EntityNotFoundException("Supplier not found with id: " + supplierId));
        supplier.setSupplierName(updatedSupplier.getSupplierName());
        supplier.setAddress(updatedSupplier.getAddress());
        supplier.setPhoneNumber(updatedSupplier.getPhoneNumber());
        supplier.setDescription(updatedSupplier.getDescription());
        supplier.setAllProducts(updatedSupplier.getAllProducts());
        supplierRepository.save(supplier);
        return supplier;
    }

    @Override
    public void deleteSupplier(long supplierId) {
        supplierRepository.deleteById(supplierId);
    }
}
