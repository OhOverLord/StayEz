package cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String address;

    @OneToMany(mappedBy = "customer")
    private List<Reservation> reservations;
}
