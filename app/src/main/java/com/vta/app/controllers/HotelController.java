package com.vta.app.controllers;

import com.vta.app.documents.Hotel;
import com.vta.app.dto.CreateHotelStep1Request;
import com.vta.app.services.HotelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.security.Principal;


@RestController
@RequestMapping("/hotels")
@RequiredArgsConstructor
public class HotelController {
    private final HotelService hotelService;

    @PostMapping("/step1")
    public Mono<Hotel> createHotelStep1(@Valid @RequestBody CreateHotelStep1Request request, Principal principal) {
        return hotelService.createHotelStep1(request, principal);
    }

    @GetMapping
    public Flux<Hotel> getHotel() {
        return hotelService.getHotel();
    }

}
