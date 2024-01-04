package cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.service;

import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.domain.Guest;
import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.domain.Reservation;
import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.domain.Room;
import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.dto.ReservationDTO;
import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.exceptions.EntityDoesNotExistException;
import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.exceptions.EntityNotFoundException;
import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.exceptions.RoomNotAvailableException;
import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.mappers.ReservationMapper;
import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.repository.CustomerRepository;
import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.repository.GuestRepository;
import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.repository.ReservationRepository;
import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class ReservationService {
    private final ReservationRepository repository;
    private final RoomRepository roomRepository;
    private final ReservationMapper reservationMapper;

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

    public Reservation create(ReservationDTO reservationDTO) throws RoomNotAvailableException {
        if (!isRoomAvailable(reservationDTO.getRoomId(), reservationDTO.getCheckInDate(), reservationDTO.getCheckOutDate())) {
            throw new RoomNotAvailableException("Room is not available for the selected dates");
        }
        Reservation reservation = reservationMapper.toEntity(reservationDTO);
        return repository.save(reservation);
    }

    public Reservation update(Long reservationId, ReservationDTO reservationDTO) {
        Reservation existingReservation = repository.findById(reservationId)
                .orElseThrow(() -> new EntityDoesNotExistException("Reservation not found with ID: " + reservationId));

        Long roomId = reservationDTO.getRoomId();
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new EntityDoesNotExistException("Room not found with ID: " + roomId));

        existingReservation.setCheckInDate(reservationDTO.getCheckInDate());
        existingReservation.setCheckOutDate(reservationDTO.getCheckOutDate());
        existingReservation.setNumberOfGuests(reservationDTO.getNumberOfGuests());
        existingReservation.setStatus(reservationDTO.getStatus());

        existingReservation.setRoom(room);
        return repository.save(existingReservation);
    }

    public List<Reservation> findReservationsByCustomerAndHotel(Long customerId, Long hotelId) {
        return repository.findReservationsByCustomerAndHotel(customerId, hotelId);
    }
}
