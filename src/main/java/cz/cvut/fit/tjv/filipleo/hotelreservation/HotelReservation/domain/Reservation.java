package cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "reservation")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private Integer numberOfGuests;
    private String status;
    @ManyToOne
    @JsonBackReference("room-reservation")
    private Room room;
    @ManyToOne
    @JsonBackReference("customer-reservation")
    private Customer customer;
    @ManyToMany(mappedBy = "reservations")
    private Set<Guest> guests = new HashSet<>();
}
