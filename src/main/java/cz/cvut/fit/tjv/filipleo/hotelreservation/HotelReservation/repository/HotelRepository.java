package cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.repository;

import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.domain.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {
}
