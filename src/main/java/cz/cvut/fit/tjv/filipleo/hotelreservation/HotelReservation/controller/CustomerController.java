package cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.controller;

import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.domain.Customer;
import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.dto.CustomerDTO;
import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.exceptions.CustomerWithThisEmailAlreadyExistsException;
import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.exceptions.EntityNotFoundException;
import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.mappers.CustomerMapper;
import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService service;
    private final CustomerMapper customerMapper;
    @GetMapping
    public Iterable<Customer> returnAll() {
        return service.readAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> returnById(@PathVariable Long id) {
        try {
            Customer readCustomer = service.readById(id);
            return new ResponseEntity<>(customerMapper.toDto(readCustomer), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerDTO> update(@PathVariable Long id, @RequestBody CustomerDTO customerDTo) {
        try {
            Customer updatedCustomer = service.update(id, customerDTo);
            return new ResponseEntity<>(customerMapper.toDto(updatedCustomer), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable Long id) {
        try {
            service.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<Customer> create(@RequestBody CustomerDTO customerDTO) {
        try {
            Customer newCustomer = service.create(customerDTO);
            return new ResponseEntity<>(newCustomer, HttpStatus.OK);
        } catch (CustomerWithThisEmailAlreadyExistsException e) {
            Customer existingCustomer = service.readByCustomerEmail(customerDTO.getEmail());
            return new ResponseEntity<>(existingCustomer, HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
