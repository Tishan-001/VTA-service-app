package com.vta.app.repositories;

import com.vta.app.documents.Booking;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface BookingRepository extends ReactiveMongoRepository<Booking, String> {
}
