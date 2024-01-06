package cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.service;

import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.domain.Hotel;
import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.domain.Reservation;
import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.domain.Room;
import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.exceptions.EntityDoesNotExistException;
import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.repository.RoomRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class RoomServiceTest {

    @Autowired
    private RoomService roomService;

    @MockBean
    private RoomRepository roomRepository;

    @Test
    public void testFindRoomByIdFound() {
        Long roomId = 1L;
        Integer guestsCount = 2;
        String number = "101";
        String type = "Deluxe";
        Double pricePerNight = 100.0;
        Boolean availability = true;
        Hotel hotel = null;
        List<Reservation> reservations = null;

        Room room = new Room(roomId, guestsCount, number, type, pricePerNight, availability, hotel, reservations);

        when(roomRepository.findById(roomId)).thenReturn(Optional.of(room));

        Room foundRoom = roomService.readById(roomId);

        assertNotNull(foundRoom);
        assertEquals(roomId, foundRoom.getId());
        assertEquals(guestsCount, foundRoom.getGuestsCount());
        assertEquals(number, foundRoom.getNumber());
        assertEquals(type, foundRoom.getType());
        assertEquals(pricePerNight, foundRoom.getPricePerNight());
        assertEquals(availability, foundRoom.getAvailability());
    }

    @Test
    public void testRoomByIdNotFound() {
        Long roomId = 2L;

        when(roomRepository.findById(roomId)).thenReturn(Optional.empty());

        assertThrows(EntityDoesNotExistException.class, () -> {
            roomService.readById(roomId);
        });
    }
}
