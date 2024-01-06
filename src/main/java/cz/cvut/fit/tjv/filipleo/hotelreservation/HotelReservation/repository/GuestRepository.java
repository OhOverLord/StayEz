package cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.repository;

import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.domain.Guest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface GuestRepository extends JpaRepository<Guest, Long> {
    Guest getByEmail(String email);
    Collection<Guest> findAllByEmail(String email);
}
