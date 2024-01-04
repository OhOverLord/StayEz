package cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.domain.Hotel;
import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.domain.Reservation;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomDTO {
    private Long id;
    private Integer guestsCount;
    private String number;
    private String type;
    private Double pricePerNight;
    private Boolean availability;
    private Long hotelId;
    private List<Long> reservationIds;
}
