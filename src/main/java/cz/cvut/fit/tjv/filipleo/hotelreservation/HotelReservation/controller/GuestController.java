package cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.controller;

import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.domain.Customer;
import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.domain.Guest;
import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.domain.Hotel;
import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.dto.GuestDTO;
import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.dto.HotelDTO;
import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.exceptions.CustomerWithThisEmailAlreadyExistsException;
import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.exceptions.EntityDoesNotExistException;
import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.exceptions.GuestWithThisEmailAlreadyExistsException;
import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.mappers.GuestMapper;
import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.service.GuestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/guests")
@RequiredArgsConstructor
public class GuestController {
    private final GuestService service;
    private final GuestMapper guestMapper;

    @GetMapping
    public List<GuestDTO> findAll() {
        List<Guest> guests = service.readAll();
        return guests.stream()
                .map(guestMapper::toDto)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<GuestDTO> returnById(@PathVariable Long id) {
        try {
            Guest readGuest = service.readById(id);
            return new ResponseEntity<>(guestMapper.toDto(readGuest), HttpStatus.OK);
        } catch (EntityDoesNotExistException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<GuestDTO> create(@RequestBody GuestDTO guestDTO) {
        try {
            Guest newGuest = service.create(guestDTO);
            return new ResponseEntity<>(guestMapper.toDto(newGuest), HttpStatus.OK);
        } catch (GuestWithThisEmailAlreadyExistsException e) {
            Guest existingGuest = service.readByGuestEmail(guestDTO.getEmail());
            return new ResponseEntity<>(guestMapper.toDto(existingGuest), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<GuestDTO> update(@PathVariable Long id, @RequestBody GuestDTO guestDTO) {
        try {
            Guest updatedGuest = service.update(id, guestDTO);
            return ResponseEntity.ok(guestMapper.toDto(updatedGuest));
        } catch (EntityDoesNotExistException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            service.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
