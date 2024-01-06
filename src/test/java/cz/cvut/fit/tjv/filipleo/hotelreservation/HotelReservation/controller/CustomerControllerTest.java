package cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.controller;

import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.HotelReservationApplication;
import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.domain.Reservation;
import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.dto.CustomerDTO;
import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.mappers.CustomerMapper;
import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.service.CustomerService;
import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.domain.Customer;
import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.exceptions.EntityDoesNotExistException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.hasSize;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {HotelReservationApplication.class})
@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @MockBean
    private CustomerMapper customerMapper;

    @Test
    public void testCreateCustomer() throws Exception {
        CustomerDTO customerDTO = new CustomerDTO(1L, "John", "Doe", "1234567890", "john.doe@example.com", "123 Main St", null);
        Customer customer = new Customer(1L, "John", "Doe", "1234567890", "john.doe@example.com", "123 Main St", new ArrayList<>());

        when(customerMapper.toEntity(any(CustomerDTO.class))).thenReturn(customer);
        when(customerService.create(any(CustomerDTO.class))).thenReturn(customer);
        when(customerMapper.toDto(any(Customer.class))).thenReturn(customerDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1,\"firstName\":\"John\",\"lastName\":\"Doe\",\"phoneNumber\":\"1234567890\",\"email\":\"john.doe@example.com\",\"address\":\"123 Main St\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("John"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Doe"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("john.doe@example.com"));
        Mockito.verify(customerService).create(customerDTO);
    }

    @Test
    public void testCustomerWithReservations() throws Exception {
        Reservation reservation1 = Reservation.builder()
                .id(1L)
                .checkInDate(LocalDate.of(2024, 1, 10))
                .checkOutDate(LocalDate.of(2024, 1, 15))
                .numberOfGuests(2)
                .status("Booked")
                .build();

        Reservation reservation2 = Reservation.builder()
                .id(2L)
                .checkInDate(LocalDate.of(2024, 2, 5))
                .checkOutDate(LocalDate.of(2024, 2, 10))
                .numberOfGuests(3)
                .status("Confirmed")
                .build();

        List<Reservation> reservations = Arrays.asList(reservation1, reservation2);

        Customer customerWithReservations = new Customer(1L, "John", "Doe", "1234567890", "john.doe@example.com", "123 Main St", reservations);

        List<Long> reservationIds = Arrays.asList(1L, 2L);
        CustomerDTO customerDTOWithReservations = new CustomerDTO(1L, "John", "Doe", "1234567890", "john.doe@example.com", "123 Main St", reservationIds);

        when(customerService.readById(1L)).thenReturn(customerWithReservations);
        when(customerMapper.toDto(customerWithReservations)).thenReturn(customerDTOWithReservations);

        mockMvc.perform(MockMvcRequestBuilders.get("/customers/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.reservationIds").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.reservationIds", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.reservationIds[0]").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.reservationIds[1]").value(2L));
    }

    @Test
    public void testCustomerWithoutReservations() throws Exception {
        CustomerDTO customerDTOWitoutReservations = new CustomerDTO(2L, "Jane", "Doe", "0987654321", "jane.doe@example.com", "456 Main St", Collections.emptyList());
        Customer customerWithoutReservations = new Customer(2L, "Jane", "Doe", "0987654321", "jane.doe@example.com", "456 Main St", Collections.emptyList());

        when(customerService.readById(2L)).thenReturn(customerWithoutReservations);
        when(customerMapper.toDto(customerWithoutReservations)).thenReturn(customerDTOWitoutReservations);

        mockMvc.perform(MockMvcRequestBuilders.get("/customers/2"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.reservationIds").isEmpty());
    }

    @Test
    public void testFindCustomerByEmailSuccess() throws Exception {
        String email = "john.doe@example.com";
        CustomerDTO customerDTO = new CustomerDTO(1L, "John", "Doe", "1234567890", email, "123 Main St", null);
        Customer customer = new Customer(1L, "John", "Doe", "1234567890", email, "123 Main St", new ArrayList<>());

        when(customerService.readByCustomerEmail(email)).thenReturn(customer);
        when(customerMapper.toDto(customer)).thenReturn(customerDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/customers/email/" + email))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(email));
    }

    @Test
    public void testFindCustomerByEmailNotFound() throws Exception {
        String email = "unknown@example.com";

        when(customerService.readByCustomerEmail(email)).thenThrow(new EntityDoesNotExistException(""));

        mockMvc.perform(MockMvcRequestBuilders.get("/customers/email/" + email))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
