package cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.service;

import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.domain.Hotel;
import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.domain.Room;
import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.exceptions.EntityDoesNotExistException;
import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.repository.HotelRepository;
import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class RoomService {
    private final RoomRepository repository;
    private final HotelRepository hotelRepository;

    public Room create(Room room) {
        return repository.save(room);
    }
    public Room readById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityDoesNotExistException("Room not found with ID: " + id));
    }
    public List<Room> readAll() {
        return repository.findAll();
    }
    public Room update(Long id, Room roomDetails) {
        Room room = repository.findById(id)
                .orElseThrow(() -> new EntityDoesNotExistException("Room not found with ID: " + id));

        room.setGuestsCount(roomDetails.getGuestsCount());
        room.setNumber(roomDetails.getNumber());
        room.setType(roomDetails.getType());
        room.setPricePerNight(roomDetails.getPricePerNight());
        room.setAvailability(roomDetails.getAvailability());

        if (roomDetails.getHotel() != null) {
            Hotel hotel = hotelRepository.findById(roomDetails.getHotel().getId())
                    .orElseThrow(() -> new EntityDoesNotExistException("Hotel not found with ID: " + roomDetails.getHotel().getId()));
            room.setHotel(hotel);
        }

        return repository.save(room);
    }
    public void delete(Long id) {
        Room room = repository.findById(id)
                .orElseThrow(() -> new EntityDoesNotExistException("Room not found with ID: " + id));
        repository.delete(room);
    }
}