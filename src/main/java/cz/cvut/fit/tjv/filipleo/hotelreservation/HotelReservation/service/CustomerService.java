package cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.service;

import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.domain.Customer;
import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.domain.Reservation;
import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.exceptions.CustomerWithThisEmailAlreadyExistsException;
import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.exceptions.EntityDoesNotExistException;
import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.exceptions.EntityNotFoundException;
import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class CustomerService {
    private final CustomerRepository repository;
    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }
    public Customer getById(Long customerId) {
        return repository.findById(customerId).orElse(null);
    }
    public Iterable<Customer> readAll() {
        return repository.findAll();
    }
    public Customer readById(Long customerId) {
        Customer customer = getById(customerId);
        if (customer == null) {
            throw new EntityNotFoundException("Customer not found with ID: " + customerId);
        }
        return customer;
    }
    public Customer create(Customer customer) {
        Collection<Customer> customersWithEmail = repository.findAllByEmail(customer.getEmail());
        if (!customersWithEmail.isEmpty()) {
            throw new CustomerWithThisEmailAlreadyExistsException(customer.getEmail());
        }
        return repository.save(customer);
    }
    public Customer update(Long customerId, Customer customerDetails) {
        Customer existingCustomer = repository.findById(customerId)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with ID: " + customerId));

        existingCustomer.setFirstName(customerDetails.getFirstName());
        existingCustomer.setLastName(customerDetails.getLastName());
        existingCustomer.setEmail(customerDetails.getEmail());
        existingCustomer.setPhoneNumber(customerDetails.getPhoneNumber());
        existingCustomer.setAddress(customerDetails.getAddress());

        Collection<Customer> customersWithEmail = repository.findAllByEmail(existingCustomer.getEmail());
        if (!customersWithEmail.isEmpty() && customersWithEmail.stream().noneMatch(c -> c.getId().equals(customerId))) {
            throw new CustomerWithThisEmailAlreadyExistsException(existingCustomer.getEmail());
        }

        return repository.save(existingCustomer);
    }
    public void delete(Long customerId) {
        if (getById(customerId) == null) {
            throw new EntityNotFoundException("Customer not found with ID: " + customerId);
        }
        repository.deleteById(customerId);
    }
    public Collection<Reservation> findAllReservationsByCustomerId(Long customerId) {
        Customer customer = repository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found with ID: " + customerId));
        return customer.getReservations();
    }

    public Customer readByCustomerEmail(String email) {
        Customer customer = repository.getByEmail(email);
        if(customer == null) {
            throw new EntityDoesNotExistException("Customer with this email doesn't exist");
        }
        return customer;
    }
}
