package com.vta.vtabackend.repositories;

import com.vta.vtabackend.documents.TourPackage;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TourPackageRepository extends MongoRepository<TourPackage, String> {
}
