package cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.exceptions;

public class EntityDoesNotExistException extends RuntimeException {
    public EntityDoesNotExistException(String s) {
        super(s);
    }
}
