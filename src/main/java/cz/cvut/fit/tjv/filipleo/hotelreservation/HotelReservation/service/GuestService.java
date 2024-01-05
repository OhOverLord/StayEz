package cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.service;

import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.domain.Customer;
import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.domain.Guest;
import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.dto.GuestDTO;
import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.exceptions.CustomerWithThisEmailAlreadyExistsException;
import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.exceptions.EntityDoesNotExistException;
import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.exceptions.EntityNotFoundException;
import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.exceptions.GuestWithThisEmailAlreadyExistsException;
import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.mappers.GuestMapper;
import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.repository.GuestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class GuestService {
    private final GuestRepository repository;
    private final GuestMapper guestMapper;
    public Guest create(GuestDTO guestDTO) {
        Collection<Guest> guestsWithEmail = repository.findAllByEmail(guestDTO.getEmail());
        if (!guestsWithEmail.isEmpty()) {
            throw new GuestWithThisEmailAlreadyExistsException(guestDTO.getEmail());
        }
        Guest guest = guestMapper.toEntity(guestDTO);
        return repository.save(guest);
    }
    public List<Guest> readAll() {
        return repository.findAll();
    }
    public Guest readById(Long id) {
        return repository.findById(id).orElseThrow(() -> new EntityDoesNotExistException("Guest not found with ID: " + id));
    }
    public Guest update(Long id, GuestDTO guestDTO) {
        Guest existingGuest = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Guest not found with ID: " + id));
        existingGuest.setFirstName(guestDTO.getFirstName());
        existingGuest.setLastName(guestDTO.getLastName());
        existingGuest.setEmail(guestDTO.getEmail());
        return repository.save(existingGuest);
    }
    public void delete(Long id) {
        Guest guest = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Guest not found with ID: " + id));
        repository.delete(guest);
    }

    public Guest readByGuestEmail(String email) {
        Guest guest = repository.getByEmail(email);
        if(guest == null) {
            throw new EntityDoesNotExistException("Guest with this email doesn't exist");
        }
        return guest;
    }
}

