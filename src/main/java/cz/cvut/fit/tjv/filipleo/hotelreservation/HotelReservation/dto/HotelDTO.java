package cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotelDTO {
    private Long id;
    private String name;
    private String address;
    private String description;
    private Integer stars;
    private String country;
    private String city;
    private List<Long> roomIds;
}
