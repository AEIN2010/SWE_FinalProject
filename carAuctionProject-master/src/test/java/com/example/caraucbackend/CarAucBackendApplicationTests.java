package com.example.caraucbackend;

import com.example.caraucbackend.DTOs.GeneralResponse;
import com.example.caraucbackend.DTOs.Requests.NewCarRequest;
import com.example.caraucbackend.entities.Car;
import com.example.caraucbackend.entities.CarStatus;
import com.example.caraucbackend.entities.User;
import com.example.caraucbackend.repos.CarRepo;
import com.example.caraucbackend.services.CarServices;
import com.example.caraucbackend.services.UserServices;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class CarAucBackendApplicationTests {

	@InjectMocks
	private CarServices carServices;

	@Mock
	private CarRepo carRepo;

	@Mock
	private UserServices userServices;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testAddCar_Success() {
		// Arrange
		NewCarRequest carRequest = new NewCarRequest(
				"VIN12345",
				"Toyota",
				"Camry",
				"2022",
				25000L,
				"image.jpg",
				"10000",
				CarStatus.ACTIVE,
				"M1"
		);

		User lister = new User();
		lister.setUsername("M1");

		when(userServices.getUserByUserName("M1")).thenReturn(lister);
		when(carRepo.findCarByVinIs("VIN12345")).thenReturn(null);

		// Act
		GeneralResponse response = carServices.addCar(carRequest);

		// Assert
		assertEquals(HttpStatus.ACCEPTED, response.getHttpStatus());
		assertEquals("car added successfully", response.getMessage());

	}

	@Test
	void testAddCar_VinAlreadyExists() {
		// Arrange
		NewCarRequest carRequest = new NewCarRequest(
				"VIN12345",
				"Toyota",
				"Camry",
				"2022",
				25000L,
				"image.jpg",
				"10000",
				CarStatus.ACTIVE,
				"testUser"
		);

		Car existingCar = new Car();
		existingCar.setVin("");
		when(carRepo.findCarByVinIs("VIN12345")).thenReturn(existingCar);

		// Act
		GeneralResponse response = carServices.addCar(carRequest);

		// Assert
		assertEquals(HttpStatus.NOT_ACCEPTABLE, response.getHttpStatus());
		assertEquals("car with this VIN already exists!", response.getMessage());
	}

}
