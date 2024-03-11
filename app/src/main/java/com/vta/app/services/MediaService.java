package com.vta.app.services;

import com.cloudinary.Cloudinary;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MediaService {

    private final Cloudinary cloudinary;

    public String uploadFile(MultipartFile multipartFile) throws IOException {
        if (multipartFile.isEmpty()) {
            throw new IOException("The file is empty and cannot be uploaded.");
        }

        try (var inputStream = multipartFile.getInputStream()) {
            Map uploadResult = cloudinary.uploader().upload(inputStream,
                    Map.of("public_id", UUID.randomUUID().toString()));
            return uploadResult.get("url").toString();
        }
    }
}
