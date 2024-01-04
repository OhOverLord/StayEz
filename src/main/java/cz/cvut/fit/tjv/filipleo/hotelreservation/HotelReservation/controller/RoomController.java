package cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.controller;

import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.domain.Reservation;
import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.domain.Room;
import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.dto.ReservationDTO;
import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.dto.RoomDTO;
import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.exceptions.EntityDoesNotExistException;
import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.mappers.RoomMapper;
import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rooms")
@RequiredArgsConstructor
public class RoomController {
    private final RoomService service;
    private final RoomMapper roomMapper;

    @GetMapping
    public List<RoomDTO> findAll() {
        List<Room> rooms = service.readAll();
        return rooms.stream()
                .map(roomMapper::toDto)
                .toList();
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

    @GetMapping("/{id}")
    public ResponseEntity<RoomDTO> returnById(@PathVariable Long id) {
        try {
            Room readRoom = service.readById(id);
            return new ResponseEntity<>(roomMapper.toDto(readRoom), HttpStatus.OK);
        } catch (EntityDoesNotExistException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<RoomDTO> create(@RequestBody RoomDTO roomDTO) {
        try {
            Room createdRoom = service.create(roomDTO);
            return new ResponseEntity<>(roomMapper.toDto(createdRoom), HttpStatus.CREATED);
        } catch (EntityDoesNotExistException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoomDTO> update(@PathVariable Long id, @RequestBody RoomDTO roomDTO) {
        try {
            Room updatedRoom = service.update(id, roomDTO);
            return ResponseEntity.ok(roomMapper.toDto(updatedRoom));
        } catch (EntityDoesNotExistException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
