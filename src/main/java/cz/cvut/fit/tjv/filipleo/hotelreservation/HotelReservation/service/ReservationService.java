package cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.service;

import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.domain.Guest;
import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.domain.Reservation;
import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.domain.Room;
import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.exceptions.EntityDoesNotExistException;
import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.exceptions.EntityNotFoundException;
import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.exceptions.RoomNotAvailableException;
import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.repository.CustomerRepository;
import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.repository.GuestRepository;
import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.repository.ReservationRepository;
import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class ReservationService {
    private final ReservationRepository repository;
    private final RoomRepository roomRepository;

    public ReservationService(ReservationRepository repository, RoomRepository roomRepository) {
        this.repository = repository;
        this.roomRepository = roomRepository;
    }
    public Reservation readById(Long reservationId) {
        return repository.findById(reservationId)
                .orElseThrow(() -> new EntityNotFoundException("Reservation not found with ID: " + reservationId));
    }
    public List<Reservation> readAll() {
        return repository.findAll();
    }
    public Collection<Guest> findAllGuestsInReservation(Long reservationId) {
        return repository.findById(reservationId)
                .map(Reservation::getGuests)
                .orElseThrow(() -> new EntityDoesNotExistException("Invalid reservation ID"));
    }
    public void delete(Long reservationId) {
        Reservation reservation = repository.findById(reservationId)
                .orElseThrow(() -> new EntityDoesNotExistException("Reservation not found with ID: " + reservationId));
        repository.delete(reservation);
    }
    public boolean isRoomAvailable(Long roomId, LocalDate checkInDate, LocalDate checkOutDate) {
        List<Reservation> overlappingReservations = repository.findOverlappingReservations(roomId, checkInDate, checkOutDate);
        return overlappingReservations.isEmpty();
    }

    public Reservation create(Reservation reservation) throws RoomNotAvailableException {
        if (!isRoomAvailable(reservation.getRoom().getId(), reservation.getCheckInDate(), reservation.getCheckOutDate())) {
            throw new RoomNotAvailableException("Room is not available for the selected dates");
        }
        return repository.save(reservation);
    }

    public Reservation update(Long reservationId, Reservation reservationDetails) {
        Reservation existingReservation = repository.findById(reservationId)
                .orElseThrow(() -> new EntityDoesNotExistException("Reservation not found with ID: " + reservationId));

        Long roomId = reservationDetails.getRoom().getId();
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new EntityDoesNotExistException("Room not found with ID: " + roomId));

        existingReservation.setCheckInDate(reservationDetails.getCheckInDate());
        existingReservation.setCheckOutDate(reservationDetails.getCheckOutDate());
        existingReservation.setNumberOfGuests(reservationDetails.getNumberOfGuests());
        existingReservation.setStatus(reservationDetails.getStatus());

        existingReservation.setRoom(room);
        return repository.save(existingReservation);
    }

    public List<Reservation> findReservationsByCustomerAndHotel(Long customerId, Long hotelId) {
        return repository.findReservationsByCustomerAndHotel(customerId, hotelId);
    }
}
