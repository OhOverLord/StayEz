package cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.controller;

import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.domain.Guest;
import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.domain.Hotel;
import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.dto.HotelDTO;
import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.exceptions.EntityDoesNotExistException;
import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.mappers.HotelMapper;
import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.service.HotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hotels")
@RequiredArgsConstructor
public class HotelController {
    private final HotelService service;
    private final HotelMapper hotelMapper;

    @GetMapping
    public Iterable<Hotel> returnAll() {
        return service.readAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<HotelDTO> returnById(@PathVariable Long id) {
        try {
            Hotel readHotel = service.readById(id);
            return new ResponseEntity<>(hotelMapper.toDto(readHotel), HttpStatus.OK);
        } catch (EntityDoesNotExistException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<HotelDTO> create(@RequestBody HotelDTO hotelDTO) {
        try {
            Hotel createdHotel = service.create(hotelDTO);
            return new ResponseEntity<>(hotelMapper.toDto(createdHotel), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<HotelDTO> update(@PathVariable Long id, @RequestBody HotelDTO hotelDTO) {
        try {
            Hotel updatedHotel = service.update(id, hotelDTO);
            return ResponseEntity.ok(hotelMapper.toDto(updatedHotel));
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
