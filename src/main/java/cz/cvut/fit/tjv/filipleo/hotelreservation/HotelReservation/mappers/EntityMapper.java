package cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.mappers;

public interface EntityMapper<E, D> {
    E toEntity(D dto);
    D toDto(E entity);
}
