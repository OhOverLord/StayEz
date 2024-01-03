package cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.service;

import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.domain.Guest;
import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.exceptions.EntityDoesNotExistException;
import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.exceptions.EntityNotFoundException;
import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.repository.GuestRepository;
import org.springframework.stereotype.Service;

@Service
public class GuestService {
    private final GuestRepository repository;
    public GuestService(GuestRepository repository) {
        this.repository = repository;
    }
    public Guest create(Guest guest) {
        return repository.save(guest);
    }
    public Iterable<Guest> readAll() {
        return repository.findAll();
    }
    public Guest readById(Long id) {
        return repository.findById(id).orElseThrow(() -> new EntityDoesNotExistException("Guest not found with ID: " + id));
    }
    public Guest update(Long id, Guest guestDetails) {
        Guest existingGuest = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Guest not found with ID: " + id));
        existingGuest.setFirstName(guestDetails.getFirstName());
        existingGuest.setLastName(guestDetails.getLastName());
        existingGuest.setEmail(guestDetails.getEmail());
        return repository.save(existingGuest);
    }
    public void delete(Long id) {
        Guest guest = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Guest not found with ID: " + id));
        repository.delete(guest);
    }
}

