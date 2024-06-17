package com.example.clotheswebsite.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryService {
    public final Cloudinary cloudinary;

    @Autowired
    public CloudinaryService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public Map upload(MultipartFile file) {
        try{
            Map params1 = ObjectUtils.asMap(
                    "use_filename", true,  //su dung file name của file upload
                    "unique_filename", false, //neu trung ten thi se tien hanh overwrite
                    "overwrite", true  // enable overwrite(ghi de)
            );
            Map data = this.cloudinary.uploader().upload(file.getBytes(), params1);
            return data;
        }catch (IOException io){
            throw new RuntimeException("Image upload fail");
        }
    }
}
