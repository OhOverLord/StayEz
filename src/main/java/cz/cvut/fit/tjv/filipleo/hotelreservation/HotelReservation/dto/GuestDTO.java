package cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GuestDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private List<Long> reservationIds;
}
