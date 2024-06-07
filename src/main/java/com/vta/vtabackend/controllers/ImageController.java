package com.vta.vtabackend.controllers;

import com.vta.vtabackend.services.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("images")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestPart MultipartFile file) {
        return new ResponseEntity<>(imageService.uploadImage(file), HttpStatus.OK);
    }

}
