package cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.repository;

import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Collection<Customer> findAllByEmail(String email);
    Customer getByEmail(String email);
}
