package com.vta.vtabackend.services;

import com.vta.vtabackend.documents.Booking;
import com.vta.vtabackend.repositories.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final BookingRepository bookingRepository;

    public String createBooking(Booking booking) {
        bookingRepository.save(booking);
        return "Booking created";
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }
}
