package com.vta.vtabackend.services;

import com.vta.vtabackend.documents.Hotel;
import com.vta.vtabackend.dto.CreateHotelRequest;
import com.vta.vtabackend.repositories.HotelRepository;
import com.vta.vtabackend.utils.JWTUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class HotelServiceTest {

    @Mock
    private HotelRepository hotelRepository;

    @Mock
    private JWTUtil jwtUtil;

    @InjectMocks
    private HotelService hotelService;

    @Captor
    private ArgumentCaptor<Hotel> hotelCaptor;

    private CreateHotelRequest createHotelRequest;
    private Hotel hotel;
    private final String token = "Bearer-validtoken-123";
    private final String userId = UUID.randomUUID().toString();

    @BeforeEach
    public void setup() {
        // Mock the JWTUtil to always return our dummy userId
        when(jwtUtil.getUserIdFromToken(token.substring(7))).thenReturn(userId);

        // Setup data for tests
        createHotelRequest = new CreateHotelRequest(
                "Lagoon Resort", "123 Ocean Drive", "Seaside", "Tropical", "123456789",
                "987654321", "reservation@lagoonresort.com", "987654321", "A nice place to relax", "Resort",
                new ArrayList<>(Arrays.asList("Pool", "Spa")), 300.0, 5
        );

        hotel = Hotel.builder()
                .id("hotel-id")
                .name(createHotelRequest.name())
                .address(createHotelRequest.address())
                .district(createHotelRequest.district())
                .country(createHotelRequest.country())
                .hotline(createHotelRequest.hotline())
                .mobile(createHotelRequest.mobileNo())
                .email(createHotelRequest.email())
                .whatsapp(createHotelRequest.whatsapp())
                .type(createHotelRequest.type())
                .description(createHotelRequest.description())
                .facilities(createHotelRequest.facilities())
                .starRating(createHotelRequest.starRating())
                .pricePerNight(createHotelRequest.pricePerNight())
                .userId(userId)
                .build();
    }

    @Test
    public void createHotel_HotelExists_ReturnsAlreadyRegistered() {
        // Arrange
        when(hotelRepository.existsByEmail(anyString())).thenReturn(true);

        // Act
        String result = hotelService.createHotel(createHotelRequest, token);

        // Assert
        assertThat(result).isEqualTo("Hotel already registered");
    }

    @Test
    public void createHotel_HotelDoesNotExist_ReturnsSuccess() {
        // Arrange
        when(hotelRepository.existsByEmail(anyString())).thenReturn(false);
        when(hotelRepository.save(any(Hotel.class))).thenReturn(hotel);

        // Act
        String result = hotelService.createHotel(createHotelRequest, token);

        // Assert
        assertThat(result).isEqualTo("Hotel profile create successfully");
    }

    @Test
    public void deleteHotel_UserHasAccess_DeletesHotel() {
        // Arrange
        when(hotelRepository.existsByEmail(anyString())).thenReturn(true);
        when(hotelRepository.getHotelByEmail(anyString())).thenReturn(hotel);
        doNothing().when(hotelRepository).deleteByEmail(anyString());

        // Act
        String result = hotelService.deleteHotel("email@example.com", token);

        // Assert
        assertThat(result).isEqualTo("Delete hotel profile");
    }

    @Test
    public void deleteHotel_UserHasNoAccess_ReturnsAccessError() {
        // Arrange
        Hotel originalHotel = Hotel.builder()
                .id(UUID.randomUUID().toString())
                .name("Sample Hotel")
                .userId(userId) // original user ID
                .build();

        Hotel otherHotel = originalHotel.toBuilder()
                .userId(UUID.randomUUID().toString()) // different user ID
                .build();

        when(hotelRepository.existsByEmail(anyString())).thenReturn(true);
        when(hotelRepository.getHotelByEmail(anyString())).thenReturn(otherHotel);

        // Act
        String result = hotelService.deleteHotel("email@example.com", token);

        // Assert
        assertThat(result).isEqualTo("You can't access to this profile");
    }

    @Test
    public void getHotel_HotelExists_ReturnsHotel() {
        // Arrange
        String hotelId = hotel.getId();
        when(hotelRepository.findById(hotelId)).thenReturn(Optional.of(hotel));

        // Act
        Hotel result = hotelService.getHotel(hotelId);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(hotel);
    }


    @Test
    public void getHotel_HotelDoesNotExist_ReturnsEmpty() {
        // Arrange
        String hotelId = "hotel-id";
        when(hotelRepository.findById(hotelId)).thenReturn(Optional.empty());

        // Act
        Optional<Hotel> result = Optional.ofNullable(hotelService.getHotel(hotelId));

        // Assert
        assertThat(result.isPresent()).isFalse();
    }

    @Test
    public void getHotels_ReturnsListOfHotels() {
        // Arrange
        List<Hotel> expectedHotels = Arrays.asList(hotel, new Hotel());  // Assume there is another hotel object.
        when(hotelRepository.findAll()).thenReturn(expectedHotels);

        // Act
        List<Hotel> result = hotelService.getHotels();

        // Assert
        assertThat(result).hasSize(expectedHotels.size()).containsAll(expectedHotels);
    }

    @Test
    public void updateHotel_ValidUpdate_ReturnsUpdatedHotel() {
        // Arrange
        Hotel updatedHotel = hotel.toBuilder()
                .name("Updated Name")
                .build();

        when(hotelRepository.findById(hotel.getId())).thenReturn(Optional.of(hotel));
        when(hotelRepository.save(any(Hotel.class))).thenReturn(updatedHotel);

        CreateHotelRequest updateRequest = new CreateHotelRequest(
                "Updated Name", hotel.getAddress(), hotel.getDistrict(), hotel.getCountry(),
                hotel.getHotline(), hotel.getMobile(), hotel.getEmail(), hotel.getWhatsapp(),
                hotel.getDescription(), hotel.getType(), hotel.getFacilities(),
                hotel.getPricePerNight(), hotel.getStarRating()
        );

        // Act
        String result = hotelService.updateHotel(updateRequest, hotel.getId());

        // Assert
        assertThat(result).isEqualTo("Hotel updated successfully");

        // Verifying that save was called with the correct properties
        verify(hotelRepository).save(hotelCaptor.capture());
        Hotel capturedHotel = hotelCaptor.getValue();
        assertThat(capturedHotel.getName()).isEqualTo("Updated Name");
    }

}

