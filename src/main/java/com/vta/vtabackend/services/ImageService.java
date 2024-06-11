package com.vta.vtabackend.services;



import com.vta.vtabackend.documents.Image;
import com.vta.vtabackend.dto.CloudinaryResponse;
import com.vta.vtabackend.repositories.ImageRepository;
import com.vta.vtabackend.utils.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ImageService {

    @Autowired
    private CloudinaryService cloudinaryService;

    @Transactional
    public String uploadImage(MultipartFile file) {
        FileUploadUtil.assertAllowed(file, FileUploadUtil.IMAGE_PATTERN);
        final String fileName = FileUploadUtil.getFileName(file.getOriginalFilename());
        final CloudinaryResponse response = this.cloudinaryService.uploadFile(file, fileName);
        return response.getUrl();
    }
}
