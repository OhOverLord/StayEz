package cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.dto;

import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.domain.Customer;
import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.domain.Guest;
import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.domain.Room;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDTO {
    private Long id;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private Integer numberOfGuests;
    private String status;
    private Long roomId;
    private Long customerId;
    private List<Long> guestIds;
}
