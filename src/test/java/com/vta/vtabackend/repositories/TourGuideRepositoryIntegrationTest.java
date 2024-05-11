package com.vta.vtabackend.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import com.vta.vtabackend.documents.TourGuide;
import com.vta.vtabackend.enums.Role;

import static org.assertj.core.api.Assertions.*;

@DataMongoTest
public class TourGuideRepositoryIntegrationTest {

    @Autowired
    private TourGuideRepository tourGuideRepository;

    @Test
    public void existsByEmail_whenTourGuideExists_returnsTrue() {
        // Arrange
        TourGuide tourGuide = TourGuide.builder()
                .id("tourGuideId")
                .name("John Trekker")
                .email("john.trekker@example.com")
                .mobile("1234567890")
                .password("securepassword")
                .address("123 Trek St, Mountain View")
                .role(Role.SERVICE_PROVIDER)
                .price("200 per day")
                .starRating(5)
                .description("Experienced mountain guide")
                .build();
        tourGuideRepository.save(tourGuide);

        // Act
        boolean exists = tourGuideRepository.existsByEmail("john.trekker@example.com");

        // Assert
        assertThat(exists).isTrue();
    }

    @Test
    public void existsByEmail_whenTourGuideDoesNotExist_returnsFalse() {
        // Act
        boolean exists = tourGuideRepository.existsByEmail("noemail@tourguide.com");

        // Assert
        assertThat(exists).isFalse();
    }

    @Test
    public void getTourguideByEmail_returnsTourGuideDetails() {
        // Arrange
        TourGuide tourGuide = TourGuide.builder()
                .id("tourGuideId")
                .name("Sara Peak")
                .email("sara.peak@example.com")
                .mobile("9876543210")
                .password("securepassword")
                .address("456 Climb Ave, Hilltown")
                .role(Role.SERVICE_PROVIDER)
                .price("150 per day")
                .starRating(4)
                .description("Friendly and informative city tours")
                .build();
        tourGuideRepository.save(tourGuide);

        // Act
        TourGuide foundTourGuide = tourGuideRepository.getTourguideByEmail("sara.peak@example.com");

        // Assert
        assertThat(foundTourGuide).isNotNull();
        assertThat(foundTourGuide.getEmail()).isEqualTo("sara.peak@example.com");
        assertThat(foundTourGuide.getName()).isEqualTo("Sara Peak");
    }

    @Test
    public void deleteByEmail_deletesTourGuideSuccessfully() {
        // Arrange
        TourGuide tourGuide = TourGuide.builder()
                .id("tg001")
                .name("Guide One")
                .email("guideone@example.com")
                .mobile("1111111111")
                .password("securepassword")
                .address("123 Main St")
                .role(Role.SERVICE_PROVIDER)
                .media("media_url")
                .price("100")
                .starRating(3)
                .description("Experienced in local tours")
                .build();
        tourGuideRepository.save(tourGuide);
        assertThat(tourGuideRepository.existsByEmail("guideone@example.com")).isTrue();

        // Act
        tourGuideRepository.deleteByEmail("guideone@example.com");

        // Assert
        assertThat(tourGuideRepository.existsByEmail("guideone@example.com")).isFalse();
    }

    @Test
    public void deleteByEmail_whenEmailDoesNotExist_doesNotThrowException() {
        // Arrange - no need to arrange anything since we are testing non-existence

        // Act & Assert
        assertThatCode(() -> tourGuideRepository.deleteByEmail("nonexistingemail@example.com"))
                .doesNotThrowAnyException();
    }
}

