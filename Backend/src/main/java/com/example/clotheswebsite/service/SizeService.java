package com.example.clotheswebsite.service;

import com.example.clotheswebsite.entity.Size;

import java.util.List;

public interface SizeService {
    List<Size> getAllSizes();
    Size getSizeById(long sizeId);
    Size addSize(Size size);
    void deleteSize(long sizeId);
}
