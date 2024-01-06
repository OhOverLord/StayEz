package cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.service;

import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.domain.Customer;
import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.domain.Guest;
import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.domain.Reservation;
import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.domain.Room;
import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.repository.ReservationRepository;
import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.repository.HotelRepository;
import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.repository.CustomerRepository;
import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.exceptions.EntityDoesNotExistException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ReservationServiceTest {

    @Autowired
    private ReservationService reservationService;

    @MockBean
    private ReservationRepository reservationRepository;

    @MockBean
    private CustomerRepository customerRepository;

    @MockBean
    private HotelRepository hotelRepository;

    @Test
    public void testFindReservationsByCustomerAndHotelWithReservations() {
        Long customerId = 1L;
        Long hotelId = 1L;

        when(customerRepository.existsById(customerId)).thenReturn(true);
        when(hotelRepository.existsById(hotelId)).thenReturn(true);

        Room mockRoom = new Room();
        mockRoom.setId(1L);

        Customer mockCustomer = new Customer();
        mockCustomer.setId(customerId);

        List<Guest> mockGuests = Collections.emptyList();

        Reservation mockReservation = Reservation.builder()
                .id(1L)
                .checkInDate(LocalDate.now())
                .checkOutDate(LocalDate.now().plusDays(1))
                .numberOfGuests(2)
                .status("Booked")
                .room(mockRoom)
                .customer(mockCustomer)
                .guests(mockGuests)
                .build();

        List<Reservation> mockReservations = Collections.singletonList(mockReservation);

        when(reservationRepository.findReservationsByCustomerAndHotel(customerId, hotelId))
                .thenReturn(mockReservations);

        List<Reservation> foundReservations = reservationService.findReservationsByCustomerAndHotel(customerId, hotelId);

        assertFalse(foundReservations.isEmpty());
        assertEquals(mockReservations, foundReservations);
    }

    @Test
    public void testFindReservationsByCustomerAndHotelWithNonExistentCustomerOrHotel() {
        Long customerId = 2L;
        Long hotelId = 2L;

        when(customerRepository.existsById(customerId)).thenReturn(false);

        assertThrows(EntityDoesNotExistException.class, () -> {
            reservationService.findReservationsByCustomerAndHotel(customerId, hotelId);
        });
    }
}