package cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.mappers;

import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.domain.Customer;
import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.domain.Reservation;
import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.dto.CustomerDTO;
import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerMapperImpl implements CustomerMapper {
    private final ReservationService reservationService;
    @Override
    public Customer toEntity(CustomerDTO dto) {
        Customer customer = new Customer();
        customer.setId(dto.getId());
        customer.setFirstName(dto.getFirstName());
        customer.setLastName(dto.getLastName());
        customer.setPhoneNumber(dto.getPhoneNumber());
        customer.setEmail(dto.getEmail());
        customer.setAddress(dto.getAddress());

        if (dto.getReservationIds() != null && !dto.getReservationIds().isEmpty()) {
            List<Reservation> reservations = dto.getReservationIds().stream()
                    .map(reservationService::readById)
                    .collect(Collectors.toList());
            customer.setReservations(reservations);
        }

        return customer;
    }
    @Override
    public CustomerDTO toDto(Customer entity) {
        CustomerDTO dto = new CustomerDTO();
        dto.setId(entity.getId());
        dto.setFirstName(entity.getFirstName());
        dto.setPhoneNumber(entity.getPhoneNumber());
        dto.setEmail(entity.getEmail());
        dto.setAddress(entity.getAddress());
        dto.setReservationIds(entity.getReservations().stream()
                .map(Reservation::getId)
                .toList());
        return dto;
    }
}