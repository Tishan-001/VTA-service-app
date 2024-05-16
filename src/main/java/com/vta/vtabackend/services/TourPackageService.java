package com.vta.vtabackend.services;

import com.vta.vtabackend.documents.TourPackage;
import com.vta.vtabackend.documents.UserDetails;
import com.vta.vtabackend.dto.TourPackageRequest;
import com.vta.vtabackend.enums.Role;
import com.vta.vtabackend.exceptions.CustomException;
import com.vta.vtabackend.repositories.TourPackageRepository;
import com.vta.vtabackend.repositories.UserRepository;
import com.vta.vtabackend.utils.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TourPackageService {
    private final TourPackageRepository tourPackageRepository;
    private final UserRepository userRepository;
    private final JWTUtil jwtUtil;

    public String createPackage(TourPackageRequest request, String token) {
        String userId = jwtUtil.getUserIdFromToken(token.substring(7));
        UserDetails userDetails = userRepository.findById(userId).orElse(null);
        assert userDetails != null;
        if (userDetails.getRole() != Role.ADMIN) {
            throw new CustomException ("You are not admin");
        }
        TourPackage tourPackage = TourPackage.builder()
                .id(UUID.randomUUID().toString())
                .name(request.name())
                .description(request.description())
                .price(request.price())
                .image(request.image())
                .duration(request.duration())
                .endDate(request.endDate())
                .startDate(request.startDate())
                .gallery(request.gallery())
                .ratting(request.ratting())
                .timePlaneList(request.timePlaneList())
                .build();
        tourPackageRepository.save(tourPackage);
        return "TourPackage created successfully";
    }

    public List<TourPackage> getAll() {
        return tourPackageRepository.findAll();
    }

    public TourPackage getTourPackage(String id) {
        boolean exists = tourPackageRepository.existsById(id);
        if (!exists) {
            throw new CustomException ("TourPackage not found");
        }
        return tourPackageRepository.findById(id).orElse(null);
    }
}
