package com.vta.vtabackend.services;

import com.cloudinary.Cloudinary;
import com.vta.vtabackend.dto.CloudinaryResponse;
import com.vta.vtabackend.exceptions.VTAException;
import com.vta.vtabackend.utils.ErrorStatusCodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service
public class CloudinaryService {

    @Autowired
    private final Cloudinary cloudinary;

    public CloudinaryService( Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public CloudinaryResponse uploadFile(MultipartFile file, String fileName) {
        try {
            final Map result = cloudinary.uploader().upload(file.getBytes(), Map.of("public_id", "vta" + fileName));
            final String url = (String) result.get("url");
            final String publicId = (String) result.get("public_id");
            return CloudinaryResponse.builder()
                    .id(publicId)
                    .url(url)
                    .build();
        } catch (Exception e) {
            throw new VTAException(VTAException.Type.EXTERNAL_SYSTEM_ERROR,
                    ErrorStatusCodes.FILE_UPLOAD_FAILED.getMessage(),
                    ErrorStatusCodes.FILE_UPLOAD_FAILED.getCode());
        }
    }
}
