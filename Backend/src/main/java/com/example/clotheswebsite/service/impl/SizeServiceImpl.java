package com.example.clotheswebsite.service.impl;

import com.example.clotheswebsite.entity.Size;
import com.example.clotheswebsite.repository.SizeRepository;
import com.example.clotheswebsite.service.SizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SizeServiceImpl implements SizeService {

    @Autowired
    private SizeRepository sizeRepository;

    @Override
    public List<Size> getAllSizes() {
        return sizeRepository.findAll();
    }

    @Override
    public Size getSizeById(long sizeId){
        return sizeRepository.findById(sizeId).orElseThrow();
    }

    @Override
    public Size addSize(Size size) {
        return sizeRepository.save(size);
    }

    @Override
    public void deleteSize(long sizeId) {
        sizeRepository.deleteById(sizeId);
    }
}
