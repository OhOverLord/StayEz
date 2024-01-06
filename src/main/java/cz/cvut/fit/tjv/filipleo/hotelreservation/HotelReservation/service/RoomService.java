package cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.service;

import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.domain.Hotel;
import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.domain.Room;
import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.dto.RoomDTO;
import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.exceptions.EntityDoesNotExistException;
import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.exceptions.EntityNotFoundException;
import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.mappers.RoomMapper;
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
    private final RoomMapper roomMapper;

    public Room create(RoomDTO roomDTO) {
        Room room = roomMapper.toEntity(roomDTO);
        return repository.save(room);
    }
    public Room readById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Room not found with ID: " + id));
    }
    public List<Room> readAll() {
        return repository.findAll();
    }
    public Room update(Long id, RoomDTO roomDTO) {
        Room room = repository.findById(id)
                .orElseThrow(() -> new EntityDoesNotExistException("Room not found with ID: " + id));

        room.setGuestsCount(roomDTO.getGuestsCount());
        room.setNumber(roomDTO.getNumber());
        room.setType(roomDTO.getType());
        room.setPricePerNight(roomDTO.getPricePerNight());
        room.setAvailability(roomDTO.getAvailability());

        if (roomDTO.getHotelId() != null) {
            Hotel hotel = hotelRepository.findById(roomDTO.getHotelId())
                    .orElseThrow(() -> new EntityDoesNotExistException("Hotel not found with ID: " + roomDTO.getHotelId()));
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