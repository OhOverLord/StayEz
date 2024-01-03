package cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.service;

import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.domain.Hotel;
import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.domain.Room;
import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.exceptions.EntityNotFoundException;
import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.repository.HotelRepository;
import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelService {
    private final HotelRepository repository;
    private final RoomRepository roomRepository;
    public HotelService(HotelRepository repository, RoomRepository roomRepository) {
        this.repository = repository;
        this.roomRepository = roomRepository;
    }
    public Hotel create(Hotel hotel) {
        return repository.save(hotel);
    }
    public Hotel readById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Hotel not found with ID: " + id));
    }
    public List<Hotel> readAll() {
        return repository.findAll();
    }
    public Hotel update(Long hotelId, Hotel hotelDetails) {
        Hotel existingHotel = repository.findById(hotelId)
                .orElseThrow(() -> new EntityNotFoundException("Hotel not found with ID: " + hotelId));

        existingHotel.setName(hotelDetails.getName());
        existingHotel.setAddress(hotelDetails.getAddress());
        existingHotel.setDescription(hotelDetails.getDescription());
        existingHotel.setStars(hotelDetails.getStars());
        existingHotel.setCountry(hotelDetails.getCountry());
        existingHotel.setCity(hotelDetails.getCity());
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