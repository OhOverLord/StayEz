package cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.service;

import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.domain.Customer;
import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.domain.Hotel;
import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.domain.Room;
import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.dto.HotelDTO;
import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.exceptions.EntityNotFoundException;
import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.mappers.HotelMapper;
import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.repository.HotelRepository;
import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class HotelService {
    private final HotelRepository repository;
    private final RoomRepository roomRepository;
    private final HotelMapper hotelMapper;

    public Hotel create(HotelDTO hotelDTO) {
        Hotel hotel = hotelMapper.toEntity(hotelDTO);
        return repository.save(hotel);
    }
    public Hotel getById(Long hotelId) {
        return repository.findById(hotelId).orElse(null);
    }
    public Hotel readById(Long hotelId) {
        Hotel hotel = getById(hotelId);
        if (hotel == null) {
            throw new EntityNotFoundException("Hotel not found with ID: " + hotelId);
        }
        return hotel;
    }

    public List<Hotel> readAll() {
        return repository.findAll();
    }
    public Hotel update(Long hotelId, HotelDTO hotelDTO) {
        Hotel existingHotel = repository.findById(hotelId)
                .orElseThrow(() -> new EntityNotFoundException("Hotel not found with ID: " + hotelId));

        existingHotel.setName(hotelDTO.getName());
        existingHotel.setAddress(hotelDTO.getAddress());
        existingHotel.setDescription(hotelDTO.getDescription());
        existingHotel.setStars(hotelDTO.getStars());
        existingHotel.setCountry(hotelDTO.getCountry());
        existingHotel.setCity(hotelDTO.getCity());
        return repository.save(existingHotel);
    }
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Hotel not found with ID: " + id);
        }
        repository.deleteById(id);
    }
    public List<Room> findAllRoomsInHotel(Long hotelId) {
        return roomRepository.findByHotelId(hotelId);
    }
}