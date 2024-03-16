package com.example.clotheswebsite.service;

import com.example.clotheswebsite.entity.Supplier;

import java.util.List;

public interface SupplierService {
    List<Supplier> getAllSuppliers();
    Supplier getSupplierById(long supplierId);
    Supplier addSupplier(Supplier supplier);
    Supplier updateSupplier(Supplier updatedSupplier, long supplierId);
    void deleteSupplier(long supplierId);
}
