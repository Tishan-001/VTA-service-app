package com.vta.app.services;

import com.cloudimpl.error.core.ErrorBuilder;
import com.vta.app.documents.Hotel;
import com.vta.app.dto.CreateHotelStep1Request;
import com.vta.app.repositories.HotelRepository;
import com.vta.app.error.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.security.Principal;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class HotelService {
    private final HotelRepository hotelRepository;

    public Mono<Hotel> createHotelStep1(CreateHotelStep1Request request, Principal principal) {
        Hotel hotel = Hotel.builder()
                .id(UUID.randomUUID().toString())
                .name(request.name())
                .address(request.address())
                .district(request.district())
                .country(request.country())
                .hotline(request.hotline())
                .mobile(request.mobileNo())
                .email(request.email())
                .whatsapp(request.whatsapp())
                .type(request.type())
                .description(request.description())
                .facilities(request.facilities())
                .starRating(request.starRating())
                .pricePerNight(request.pricePerNight())
                .build();
        return hotelRepository.existsByUserId(principal.getName())
                .filter(e -> !e)
                .switchIfEmpty(Mono.error(() -> ApiException.SOMETHING_WENT_WRONG(ErrorBuilder::build)))
                .flatMap(e -> hotelRepository.save(hotel));
    }

    public Flux<Hotel> getHotel() {
        return hotelRepository.findAll();
    }


}
