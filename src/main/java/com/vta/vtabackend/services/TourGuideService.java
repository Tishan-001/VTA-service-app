package com.vta.vtabackend.services;

import com.vta.vtabackend.documents.TourGuide;
import com.vta.vtabackend.documents.Users;
import com.vta.vtabackend.dto.RegisterTourGuideRequest;
import com.vta.vtabackend.enums.Role;
import com.vta.vtabackend.exceptions.VTAException;
import com.vta.vtabackend.repositories.TourGuideRepository;
import com.vta.vtabackend.repositories.UserRepository;
import com.vta.vtabackend.utils.ErrorStatusCodes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TourGuideService {
    private final TourGuideRepository tourGuideRepository;
    private final TokenService tokenService;
    private final UserRepository userRepository;

    public String saveTourGuide(RegisterTourGuideRequest request, String token){
        String userEmail = tokenService.extractEmail(token);
        Users user = userRepository.getByEmail(userEmail);
        boolean exists = tourGuideRepository.existsByEmail(request.email());
        if (exists) {
            return  "Email already exists: " + request.email();
        } else {
            TourGuide tourGuide = TourGuide.builder()
                    .id(UUID.randomUUID().toString())
                    .name(request.name())
                    .email(request.email())
                    .address(request.address())
                    .mobile(request.mobile())
                    .media(request.media())
                    .address(request.address())
                    .role(Role.TOURGUIDE)
                    .price(request.price())
                    .starRating(request.starRating())
                    .description(request.description())
                    .userId(user.getId())
                    .build();

            tourGuideRepository.save(tourGuide);

            return "Your Details Successfully saved";
        }
    }

    public List<TourGuide> getTourGuides() {return tourGuideRepository.findAll();}

    public TourGuide getTourguide(String id) {
        boolean exists = tourGuideRepository.existsById(id);

        if (!exists) {
            throw new VTAException(VTAException.Type.NOT_FOUND,
                    ErrorStatusCodes.TOURGUIDE_NOT_FOUND.getMessage(),
                    ErrorStatusCodes.TOURGUIDE_NOT_FOUND.getCode());
        } else {
            return tourGuideRepository.getTourguideById(id);
        }
    }

    public String updateTourGuide(RegisterTourGuideRequest request, String token) {
        String userEmail = tokenService.extractEmail(token);
        Users user = userRepository.getByEmail(userEmail);
        TourGuide tourGuide = tourGuideRepository.getTourguideByEmail(request.email());

        if (tourGuide == null) {
            return "Tour guide not found with email: " + request.email();
        } else if (!Objects.equals(user.getId(), tourGuide.getId())) {
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
        String userEmail = tokenService.extractEmail(token);
        Users user = userRepository.getByEmail(userEmail);
        boolean exists = tourGuideRepository.existsByEmail(email);

        if (exists) {
            if(Objects.equals(user.getId(), tourGuideRepository.getTourguideByEmail(email).getId())) {
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

    public TourGuide getTourguideByEmail(String email) {
        boolean exists = tourGuideRepository.existsByEmail(email);
        if (!exists) {
            throw new VTAException(VTAException.Type.NOT_FOUND,
                    ErrorStatusCodes.TOURGUIDE_NOT_FOUND.getMessage(),
                    ErrorStatusCodes.TOURGUIDE_NOT_FOUND.getCode());
        }
        return tourGuideRepository.getTourguideByEmail(email);
    }
}