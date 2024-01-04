package cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.mappers;

import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.domain.Hotel;
import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.domain.Reservation;
import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.domain.Room;
import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.dto.RoomDTO;
import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.service.HotelService;
import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomMapperImpl implements RoomMapper{
    private final HotelService hotelService;
    private final ReservationService reservationService;
    @Override
    public Room toEntity(RoomDTO dto) {
        Room room = new Room();
        room.setId(dto.getId());
        room.setGuestsCount(dto.getGuestsCount());
        room.setNumber(dto.getNumber());
        room.setType(dto.getType());
        room.setPricePerNight(dto.getPricePerNight());
        room.setAvailability(dto.getAvailability());

        if (dto.getHotelId() != null) {
            Hotel hotel = hotelService.readById(dto.getHotelId());
            room.setHotel(hotel);
        }

        if (dto.getReservationIds() != null && !dto.getReservationIds().isEmpty()) {
            List<Reservation> reservations = dto.getReservationIds().stream()
                    .map(reservationService::readById)
                    .collect(Collectors.toList());
            room.setReservations(reservations);
        }

        return room;
    }

    @Override
    public RoomDTO toDto(Room entity) {
        RoomDTO dto = new RoomDTO();
        dto.setId(entity.getId());
        dto.setGuestsCount(entity.getGuestsCount());
        dto.setNumber(entity.getNumber());
        dto.setType(entity.getType());
        dto.setPricePerNight(entity.getPricePerNight());
        dto.setAvailability(entity.getAvailability());

        if (entity.getHotel() != null) {
            dto.setHotelId(entity.getHotel().getId());
        }

        if (entity.getReservations() != null && !entity.getReservations().isEmpty()) {
            List<Long> reservationIds = entity.getReservations().stream()
                    .map(Reservation::getId)
                    .collect(Collectors.toList());
            dto.setReservationIds(reservationIds);
        }

        return dto;
    }
}
