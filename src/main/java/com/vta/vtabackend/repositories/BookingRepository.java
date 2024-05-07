package com.vta.vtabackend.repositories;

import com.vta.vtabackend.documents.Booking;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BookingRepository extends MongoRepository<Booking, String> {
}
