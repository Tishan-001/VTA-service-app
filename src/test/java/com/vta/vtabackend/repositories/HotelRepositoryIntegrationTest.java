package com.vta.vtabackend.repositories;

import com.mongodb.DuplicateKeyException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import com.vta.vtabackend.documents.Hotel;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

@DataMongoTest
public class HotelRepositoryIntegrationTest {

    @Autowired
    private HotelRepository hotelRepository;

    @Test
    public void saveHotel_whenNewHotel_savesSuccessfully() {
        // Arrange
        Hotel hotel = Hotel.builder()
                .id("hotel-id")
                .name("Lagoon Resort")
                .address("123 Ocean Drive")
                .district("Seaside")
                .country("Tropical")
                .hotline("123456789")
                .mobile("987654321")
                .email("reservation@lagoonresort.com")
                .whatsapp("987654321")
                .description("A nice place to relax")
                .type("Resort")
                .facilities(List.of("Pool", "Spa"))
                .starRating(5)
                .pricePerNight(300.0)
                .userId("userId")
                .build();

        // Act
        Hotel savedHotel = hotelRepository.save(hotel);

        // Assert
        assertThat(savedHotel).isNotNull();
        assertThat(savedHotel.getId()).isEqualTo(hotel.getId());
        assertThat(savedHotel.getName()).isEqualTo("Lagoon Resort");
        assertThat(savedHotel.getAddress()).isEqualTo("123 Ocean Drive");
        assertThat(savedHotel.getDistrict()).isEqualTo("Seaside");
        assertThat(savedHotel.getCountry()).isEqualTo("Tropical");
        assertThat(savedHotel.getHotline()).isEqualTo("123456789");
        assertThat(savedHotel.getMobile()).isEqualTo("987654321");
        assertThat(savedHotel.getEmail()).isEqualTo("reservation@lagoonresort.com");
        assertThat(savedHotel.getWhatsapp()).isEqualTo("987654321");
        assertThat(savedHotel.getDescription()).isEqualTo("A nice place to relax");
        assertThat(savedHotel.getType()).isEqualTo("Resort");
        assertThat(savedHotel.getFacilities()).containsExactlyInAnyOrder("Pool", "Spa");
        assertThat(savedHotel.getStarRating()).isEqualTo(5);
        assertThat(savedHotel.getPricePerNight()).isEqualTo(300.0);
        assertThat(savedHotel.getUserId()).isEqualTo("userId");
    }

    @Test
    public void saveHotel_whenDuplicateEmail_throwsException() {
        // Arrange
        Hotel hotel = Hotel.builder()
                .id("1")
                .name("Beachside Hotel")
                .email("info@beachside.com")
                .build();
        hotelRepository.save(hotel);

        Hotel anotherHotel = Hotel.builder()
                .id("2")
                .name("Beachside Bungalow")
                .email("info@beachside.com")
                .build();

        // Act & Assert
        assertThatThrownBy(() -> hotelRepository.save(anotherHotel))
                .isInstanceOf(DuplicateKeyException.class)
                .hasMessageContaining("E11000 duplicate key error");
    }

    @Test
    public void existsByEmail_whenHotelDoesNotExist_returnsFalse() {
        // Act
        boolean exists = hotelRepository.existsByEmail("noemail@hotel.com");

        // Assert
        assertThat(exists).isFalse();
    }

    @Test
    public void getHotelByEmail_returnsHotelDetails() {
        // Arrange
        Hotel hotel = Hotel.builder()
                .id("hotelId")
                .name("Beachside Hotel")
                .email("info@beachside.com")
                .build();
        hotelRepository.save(hotel);

        // Act
        Hotel foundHotel = hotelRepository.getHotelByEmail("info@beachside.com");

        // Assert
        assertThat(foundHotel).isNotNull();
        assertThat(foundHotel.getEmail()).isEqualTo("info@beachside.com");
    }

    @Test
    public void deleteByEmail_removesHotelSuccessfully() {
        // Arrange
        Hotel hotel = Hotel.builder()
                .id("hotelId")
                .name("Mountain Retreat")
                .email("stay@mountainretreat.com")
                .build();
        hotelRepository.save(hotel);
        assertThat(hotelRepository.existsByEmail("stay@mountainretreat.com")).isTrue();

        // Act
        hotelRepository.deleteByEmail("stay@mountainretreat.com");

        // Assert
        assertThat(hotelRepository.existsByEmail("stay@mountainretreat.com")).isFalse();
    }
}
