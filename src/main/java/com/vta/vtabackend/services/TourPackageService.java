package com.vta.vtabackend.services;

import com.vta.vtabackend.documents.TourPackage;
import com.vta.vtabackend.documents.Users;
import com.vta.vtabackend.dto.TourPackageRequest;
import com.vta.vtabackend.enums.Role;
import com.vta.vtabackend.exceptions.VTAException;
import com.vta.vtabackend.repositories.TourPackageRepository;
import com.vta.vtabackend.repositories.UserRepository;
import com.vta.vtabackend.utils.ErrorStatusCodes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TourPackageService {
    private final TourPackageRepository tourPackageRepository;
    private final UserRepository userRepository;
    private final TokenService tokenService;

    public String createPackage(TourPackageRequest request, String token) {
        String userEmail = tokenService.extractEmail(token);
        Users user = userRepository.getByEmail(userEmail);
        assert user != null;
        if (user.getRole() != Role.ADMIN) {
            throw new VTAException(VTAException.Type.UNAUTHORIZED,
                    ErrorStatusCodes.YOU_ARE_NOT_ADMIN.getMessage(),
                    ErrorStatusCodes.YOU_ARE_NOT_ADMIN.getCode());
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
            throw new VTAException(VTAException.Type.NOT_FOUND,
                    ErrorStatusCodes.TOURPACKAGE_NOT_FOUND.getMessage(),
                    ErrorStatusCodes.TOURPACKAGE_NOT_FOUND.getCode());
        }
        return tourPackageRepository.findById(id).orElse(null);
    }
}
