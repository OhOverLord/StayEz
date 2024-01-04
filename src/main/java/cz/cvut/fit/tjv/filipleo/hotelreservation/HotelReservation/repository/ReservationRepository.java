package cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.repository;

import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByCustomerId(Long customerId);
    List<Reservation> findByRoomId(Long roomId);
    @Query("SELECT r FROM Reservation r JOIN r.guests g WHERE g.id = :guestId")
    List<Reservation> findReservationsByGuestId(@Param("guestId") Long guestId);
    @Query("SELECT r FROM Reservation r WHERE r.customer.id = :customerId AND r.room.hotel.id = :hotelId ORDER BY r.checkInDate")
    List<Reservation> findReservationsByCustomerAndHotel(
            @Param("customerId") Long customerId,
            @Param("hotelId") Long hotelId
    );
    @Query("SELECT r FROM Reservation r WHERE r.room.id = :roomId AND ((r.checkInDate <= :checkOutDate AND r.checkOutDate >= :checkInDate))")
    List<Reservation> findOverlappingReservations(@Param("roomId") Long roomId, @Param("checkInDate") Date checkInDate, @Param("checkOutDate") Date checkOutDate);
}
