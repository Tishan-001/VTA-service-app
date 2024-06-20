package com.vta.vtabackend.repositories;

import com.vta.vtabackend.documents.HotelBooking;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface HotelBookingRepository extends MongoRepository<HotelBooking,String> {
    List<HotelBooking> findByHotelId(String hotelId);
}