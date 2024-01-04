package cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hotel")
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String address;
    private String description;
    private Integer stars;
    private String country;
    private String city;

    @OneToMany(mappedBy = "hotel", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Room> rooms;
}
