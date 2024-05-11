package com.vta.vtabackend.services;

import com.vta.vtabackend.components.PBKDF2Encoder;
import com.vta.vtabackend.documents.TourGuide;
import com.vta.vtabackend.dto.LoginWithEmailRequest;
import com.vta.vtabackend.dto.RegisterTourGuideRequest;
import com.vta.vtabackend.exceptions.CustomException;
import com.vta.vtabackend.repositories.TourGuideRepository;
import com.vta.vtabackend.utils.JWTUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TourGuideServiceTest {

    @Mock
    private TourGuideRepository tourGuideRepository;
    @Mock
    private JWTUtil jwtUtil;
    @Mock
    private PBKDF2Encoder passwordEncoder;

    @InjectMocks
    private TourGuideService tourGuideService;

    @Test
    void saveTourGuide_EmailExists_ReturnsError() {
        // Arrange
        RegisterTourGuideRequest request = new RegisterTourGuideRequest("John Doe", "john@example.com", "1234567890", "password", "123 Main St", "100", 5, "Description");
        when(tourGuideRepository.existsByEmail(request.email())).thenReturn(true);

        // Act
        String result = tourGuideService.saveTourGuide(request);

        assertThat(result).isEqualTo("Email already exists: john@example.com");
    }

    @Test
    void saveTourGuide_NewEmail_ReturnsSuccess() {
        // Arrange
        RegisterTourGuideRequest request = new RegisterTourGuideRequest("John Doe", "john@example.com", "1234567890", "password", "123 Main St", "100", 5, "Description");
        when(tourGuideRepository.existsByEmail(request.email())).thenReturn(false);
        when(passwordEncoder.encode(request.password())).thenReturn("encodedPassword");
        when(tourGuideRepository.save(any(TourGuide.class))).thenReturn(any(TourGuide.class));

        // Act
        String result = tourGuideService.saveTourGuide(request);

        // Assert
        assertThat(result).isEqualTo("Your Details Successfully saved");
    }

    @Test
    void loginWithEmail_InvalidPassword_ThrowsCustomException() {
        // Arrange
        LoginWithEmailRequest request = new LoginWithEmailRequest("john@example.com", "wrongpassword");
        TourGuide mockGuide = new TourGuide();
        mockGuide.setEmail("john@example.com");
        mockGuide.setPassword("encodedCorrectPassword");

        // Act
        when(tourGuideRepository.getTourguideByEmail(request.email())).thenReturn(mockGuide);
        when(passwordEncoder.matches(request.password(), mockGuide.getPassword())).thenReturn(false);

        // Assert
        assertThatThrownBy(() -> tourGuideService.loginWithEmail(request))
                .isInstanceOf(CustomException.class)
                .hasMessageContaining("Incorrect password");
    }

    @Test
    void getTourGuides_ReturnsListOfTourGuides() {
        // Arrange
        List<TourGuide> expectedTourGuides = Arrays.asList(new TourGuide(), new TourGuide());
        when(tourGuideRepository.findAll()).thenReturn(expectedTourGuides);

        // Act
        List<TourGuide> result = tourGuideService.getTourGuides();

        // Assert
        assertThat(result).hasSize(2).containsAll(expectedTourGuides);
    }

    @Test
    void getTourguide_EmailExists_ReturnsTourGuide() {
        // Arrange
        String email = "john@example.com";
        TourGuide expectedTourGuide = new TourGuide();
        when(tourGuideRepository.existsByEmail(email)).thenReturn(true);
        when(tourGuideRepository.getTourguideByEmail(email)).thenReturn(expectedTourGuide);

        // Act
        TourGuide result = tourGuideService.getTourguide(email);

        // Assert
        assertThat(result).isEqualTo(expectedTourGuide);
    }

    @Test
    void getTourguide_EmailNotExists_ThrowsCustomException() {
        // Arrange
        String email = "john@example.com";

        // Act
        when(tourGuideRepository.existsByEmail(email)).thenReturn(false);

        // Assert
        assertThatThrownBy(() -> tourGuideService.getTourguide(email))
                .isInstanceOf(CustomException.class)
                .hasMessageContaining("Email does not exist");
    }

    @Test
    void updateTourGuide_ValidUpdate_ReturnsSuccess() {
        RegisterTourGuideRequest request = new RegisterTourGuideRequest("John Doe", "john@example.com", "1234567890", "password", "123 Main St", "100", 5, "Description");
        TourGuide existingGuide = new TourGuide();
        existingGuide.setId("123");
        String token = "Bearer validToken123";
        when(jwtUtil.getUserIdFromToken("validToken123")).thenReturn("123");
        when(tourGuideRepository.getTourguideByEmail(request.email())).thenReturn(existingGuide);
        when(tourGuideRepository.save(any(TourGuide.class))).thenReturn(existingGuide);

        String result = tourGuideService.updateTourGuide(request, token);

        assertThat(result).isEqualTo("Tour guide updated successfully.");
    }

    @Test
    void updateTourGuide_UnauthorizedUpdate_ReturnsError() {
        RegisterTourGuideRequest request = new RegisterTourGuideRequest("John Doe", "john@example.com", "1234567890", "password", "123 Main St", "100", 5, "Description");
        TourGuide existingGuide = new TourGuide();
        existingGuide.setId("123");
        String token = "Bearer validToken123";
        when(jwtUtil.getUserIdFromToken("validToken123")).thenReturn("124"); // Different ID
        when(tourGuideRepository.getTourguideByEmail(request.email())).thenReturn(existingGuide);

        String result = tourGuideService.updateTourGuide(request, token);

        assertThat(result).isEqualTo("Unauthorized access: You cannot update this tour guide's details.");
    }

    @Test
    void deleteTourGuide_ValidDeletion_ReturnsSuccess() {
        String email = "john@example.com";
        String token = "Bearer validToken123";
        TourGuide tourGuide = new TourGuide();
        tourGuide.setId("123");
        when(jwtUtil.getUserIdFromToken("validToken123")).thenReturn("123");
        when(tourGuideRepository.existsByEmail(email)).thenReturn(true);
        when(tourGuideRepository.getTourguideByEmail(email)).thenReturn(tourGuide);

        String result = tourGuideService.deleteTourGuide(email, token);

        assertThat(result).isEqualTo("Successfully deleted tour guide");
    }

    @Test
    void deleteTourGuide_UnauthorizedDeletion_ReturnsError() {
        String email = "john@example.com";
        String token = "Bearer validToken123";
        TourGuide tourGuide = new TourGuide();
        tourGuide.setId("123");
        when(jwtUtil.getUserIdFromToken("validToken123")).thenReturn("124"); // Different ID
        when(tourGuideRepository.existsByEmail(email)).thenReturn(true);
        when(tourGuideRepository.getTourguideByEmail(email)).thenReturn(tourGuide);

        String result = tourGuideService.deleteTourGuide(email, token);

        assertThat(result).isEqualTo("You can't delete this tour guide");
    }

    @Test
    void deleteTourGuide_NonExistentEmail_ReturnsError() {
        String email = "john@example.com";
        String token = "Bearer validToken123";
        when(tourGuideRepository.existsByEmail(email)).thenReturn(false);

        String result = tourGuideService.deleteTourGuide(email, token);

        assertThat(result).isEqualTo("Tour Guide does not exist");
    }

}
