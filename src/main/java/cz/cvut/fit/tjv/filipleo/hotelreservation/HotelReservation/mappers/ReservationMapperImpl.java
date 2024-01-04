package cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.mappers;

import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.domain.Guest;
import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.domain.Reservation;
import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.dto.ReservationDTO;
import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.service.CustomerService;
import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.service.GuestService;
import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationMapperImpl implements ReservationMapper{
    private final RoomService roomService;
    private final CustomerService customerService;
    private final GuestService guestService;

    @Override
    public Reservation toEntity(ReservationDTO dto) {
        Reservation reservation = new Reservation();
        reservation.setId(dto.getId());
        reservation.setCheckInDate(dto.getCheckInDate());
        reservation.setCheckOutDate(dto.getCheckOutDate());
        reservation.setNumberOfGuests(dto.getNumberOfGuests());
        reservation.setStatus(dto.getStatus());

        if (dto.getRoomId() != null) {
            reservation.setRoom(roomService.readById(dto.getRoomId()));
        }

        if (dto.getCustomerId() != null) {
            reservation.setCustomer(customerService.readById(dto.getCustomerId()));
        }

        if (dto.getGuestIds() != null && !dto.getGuestIds().isEmpty()) {
            List<Guest> guests = dto.getGuestIds().stream()
                    .map(guestService::readById)
                    .collect(Collectors.toList());
            reservation.setGuests(guests);
        }

        return reservation;
    }
    @Override
    public ReservationDTO toDto(Reservation entity) {
        ReservationDTO dto = new ReservationDTO();
        dto.setId(entity.getId());
        dto.setCheckInDate(entity.getCheckInDate());
        dto.setCheckOutDate(entity.getCheckOutDate());
        dto.setNumberOfGuests(entity.getNumberOfGuests());
        dto.setStatus(entity.getStatus());

        if (entity.getRoom() != null) {
            dto.setRoomId(entity.getRoom().getId());
        }

        if (entity.getCustomer() != null) {
            dto.setCustomerId(entity.getCustomer().getId());
        }

        if (entity.getGuests() != null && !entity.getGuests().isEmpty()) {
            List<Long> guestIds = entity.getGuests().stream()
                    .map(Guest::getId)
                    .collect(Collectors.toList());
            dto.setGuestIds(guestIds);
        }

        return dto;
    }
}
