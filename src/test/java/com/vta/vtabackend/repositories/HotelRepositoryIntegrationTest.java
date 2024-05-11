package com.vta.vtabackend.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import com.vta.vtabackend.documents.Hotel;

import static org.assertj.core.api.Assertions.*;

@DataMongoTest
public class HotelRepositoryIntegrationTest {

    @Autowired
    private HotelRepository hotelRepository;

    @Test
    public void existsByEmail_whenHotelExists_returnsTrue() {
        // Arrange
        Hotel hotel = Hotel.builder()
                .id("hotelId")
                .name("Sunset Resort")
                .email("contact@sunsetresort.com")
                .build();
        hotelRepository.save(hotel);

        // Act
        boolean exists = hotelRepository.existsByEmail("contact@sunsetresort.com");

        // Assert
        assertThat(exists).isTrue();
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
