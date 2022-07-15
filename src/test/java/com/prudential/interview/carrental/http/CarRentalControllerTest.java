package com.prudential.interview.carrental.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prudential.interview.carrental.handler.CarRentalHandler;
import com.prudential.interview.domain.CarRental;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@AutoConfigureMockMvc
@WebMvcTest(controllers = CarRentalController.class)
class CarRentalControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    CarRentalHandler carRentalHandler;

    @Test
    void contextLoads() {

    }

    @SneakyThrows
    @Test
    void testTooMuchRequest() {
        Mockito.when(carRentalHandler.rentCar("test", 1, 1)).thenReturn(0);
        CarRental carRental = new CarRental("test", 1, 1);
        mockMvc.perform(MockMvcRequestBuilders.post("/cars/rent")
                .content(new ObjectMapper().writeValueAsString(carRental))
                .contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().is(503));

    }
}
