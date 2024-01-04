package cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.mappers;

import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.domain.Hotel;
import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.domain.Room;
import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.dto.HotelDTO;
import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HotelMapperImpl implements HotelMapper{
    private final RoomService roomService;
    @Override
    public Hotel toEntity(HotelDTO dto) {
        Hotel hotel = new Hotel();
        hotel.setId(dto.getId());
        hotel.setName(dto.getName());
        hotel.setAddress(dto.getAddress());
        hotel.setDescription(dto.getDescription());
        hotel.setStars(dto.getStars());
        hotel.setCountry(dto.getCountry());
        hotel.setCity(dto.getCity());

        if (dto.getRoomIds() != null && !dto.getRoomIds().isEmpty()) {
            List<Room> rooms = dto.getRoomIds().stream()
                    .map(roomService::readById)
                    .collect(Collectors.toList());
            hotel.setRooms(rooms);
        }

        return hotel;
    }
    @Override
    public HotelDTO toDto(Hotel entity) {
        HotelDTO dto = new HotelDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setAddress(entity.getAddress());
        dto.setDescription(entity.getDescription());
        dto.setStars(entity.getStars());
        dto.setCountry(entity.getCountry());
        dto.setCity(entity.getCity());

        if (entity.getRooms() != null && !entity.getRooms().isEmpty()) {
            List<Long> roomIds = entity.getRooms().stream()
                    .map(Room::getId)
                    .collect(Collectors.toList());
            dto.setRoomIds(roomIds);
        }

        return dto;
    }
}
