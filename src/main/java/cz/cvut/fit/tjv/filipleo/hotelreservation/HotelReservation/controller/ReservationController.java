package cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.controller;

import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.domain.Reservation;
import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.dto.ReservationDTO;
import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.exceptions.EntityDoesNotExistException;
import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.exceptions.RoomNotAvailableException;
import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.mappers.ReservationMapper;
import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationService service;
    private final ReservationMapper reservationMapper;

    @GetMapping
    public List<ReservationDTO> findAll() {
        List<Reservation> reservations = service.readAll();
        return reservations.stream()
                .map(reservationMapper::toDto)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationDTO> returnById(@PathVariable Long id) {
        try {
            Reservation readReservation = service.readById(id);
            return new ResponseEntity<>(reservationMapper.toDto(readReservation), HttpStatus.OK);
        } catch (EntityDoesNotExistException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody ReservationDTO reservationDTO) {
        try {
            Reservation createdReservation = service.create(reservationDTO);
            return new ResponseEntity<>(reservationMapper.toDto(createdReservation), HttpStatus.CREATED);
        } catch (RoomNotAvailableException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Error: " + e.getMessage());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReservationDTO> update(@PathVariable Long id, @RequestBody ReservationDTO reservationDTO) {
        try {
            Reservation updatedReservation = service.update(id, reservationDTO);
            return ResponseEntity.ok(reservationMapper.toDto(updatedReservation));
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

    @GetMapping("/customer/{customerId}/hotel/{hotelId}")
    public ResponseEntity<List<ReservationDTO>> findReservationsByCustomerAndHotel(
            @PathVariable Long customerId,
            @PathVariable Long hotelId) {
        try {
            List<Reservation> reservations = service.findReservationsByCustomerAndHotel(customerId, hotelId);
            List<ReservationDTO> reservationDTOS = reservations.stream()
                    .map(reservationMapper::toDto)
                    .toList();
            return new ResponseEntity<>(reservationDTOS, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
