package cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.mappers;

import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.domain.Guest;
import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.domain.Reservation;
import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.dto.GuestDTO;
import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GuestMapperImpl implements GuestMapper{
    private final ReservationService reservationService;
    @Override
    public Guest toEntity(GuestDTO dto) {
        Guest guest = new Guest();
        guest.setId(dto.getId());
        guest.setFirstName(dto.getFirstName());
        guest.setLastName(dto.getLastName());
        guest.setEmail(dto.getEmail());
        if (dto.getReservationIds() != null && !dto.getReservationIds().isEmpty()) {
            List<Reservation> reservations = dto.getReservationIds().stream()
                    .map(reservationService::readById)
                    .collect(Collectors.toList());
            guest.setReservations(reservations);
        }
        return guest;
    }
    @Override
    public GuestDTO toDto(Guest entity) {
        GuestDTO dto = new GuestDTO();
        dto.setId(entity.getId());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setEmail(entity.getEmail());

        if (entity.getReservations() != null && !entity.getReservations().isEmpty()) {
            List<Long> reservationIds = entity.getReservations().stream()
                    .map(Reservation::getId)
                    .collect(Collectors.toList());
            dto.setReservationIds(reservationIds);
        }

        return dto;
    }
}
