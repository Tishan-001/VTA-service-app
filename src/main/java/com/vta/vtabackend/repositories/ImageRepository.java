package com.vta.vtabackend.repositories;

import com.vta.vtabackend.documents.Image;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ImageRepository extends MongoRepository<Image, String> {
    List<Image> findByOrderById();
}