package com.vta.vtabackend.services;

import com.vta.vtabackend.documents.TourGuide;
import com.vta.vtabackend.dto.RegisterTourGuideRequest;
import com.vta.vtabackend.enums.Role;
import com.vta.vtabackend.exceptions.CustomException;
import com.vta.vtabackend.repositories.TourGuideRepository;
import com.vta.vtabackend.utils.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TourGuideService {
    private final TourGuideRepository tourGuideRepository;
    private final JWTUtil jwtUtil;

    public String saveTourGuide(RegisterTourGuideRequest request){
        boolean exists = tourGuideRepository.existsByEmail(request.email());
        if (exists) {
            return  "Email already exists: " + request.email();
        } else {
            TourGuide tourGuide = TourGuide.builder()
                    .id(UUID.randomUUID().toString())
                    .name(request.name())
                    .email(request.email())
                    .address(request.address())
                    .password(request.password())
                    .mobile(request.mobile())
                    .media(request.media())
                    .address(request.address())
                    .role(Role.TOURGUIDE)
                    .price(request.price())
                    .starRating(request.starRating())
                    .description(request.description())
                    .build();

            tourGuideRepository.save(tourGuide);

            return "Your Details Successfully saved";
        }
    }

    public List<TourGuide> getTourGuides() {return tourGuideRepository.findAll();}

    public TourGuide getTourguide(String id) {
        boolean exists = tourGuideRepository.existsById(id);

        if (!exists) {
            throw new CustomException("This id does not exist");
        } else {
            return tourGuideRepository.getTourguideById(id);
        }
    }

    public String updateTourGuide(RegisterTourGuideRequest request, String token) {
        String userId = jwtUtil.getUserIdFromToken(token.substring(7));
        TourGuide tourGuide = tourGuideRepository.getTourguideByEmail(request.email());

        if (tourGuide == null) {
            return "Tour guide not found with email: " + request.email();
        } else if (!Objects.equals(userId, tourGuide.getId())) {
            return "Unauthorized access: You cannot update this tour guide's details.";
        } else {
            tourGuide.setName(request.name() != null ? request.name() : tourGuide.getName());
            tourGuide.setAddress(request.address() != null ? request.address() : tourGuide.getAddress());
            tourGuide.setMobile(request.mobile() != null ? request.mobile() : tourGuide.getMobile());
            tourGuide.setPrice(request.price() != null ? request.price() : tourGuide.getPrice());
            tourGuide.setMedia(request.media() != null ? request.media() : tourGuide.getMedia());
            tourGuide.setStarRating(request.starRating() != null ? request.starRating() : tourGuide.getStarRating());
            tourGuide.setDescription(request.description() != null ? request.description() : tourGuide.getDescription());

            tourGuideRepository.save(tourGuide);
            return "Tour guide updated successfully.";
        }
    }

    public String deleteTourGuide(String email, String token) {
        String userId = jwtUtil.getUserIdFromToken(token.substring(7));
        boolean exists = tourGuideRepository.existsByEmail(email);

        if (exists) {
            if(Objects.equals(userId, tourGuideRepository.getTourguideByEmail(email).getId())) {
                tourGuideRepository.deleteById(email);
                return "Successfully deleted tour guide";
            } else {
                return "You can't delete this tour guide";
            }
        } else {
            return "Tour Guide does not exist";
        }
    }

    public String getTourGuidesCount() {
        return String.valueOf(tourGuideRepository.count());
    }
}
