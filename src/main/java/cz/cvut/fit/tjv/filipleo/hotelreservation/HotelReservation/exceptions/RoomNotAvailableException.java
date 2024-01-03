package cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.exceptions;

public class RoomNotAvailableException extends RuntimeException {
    public RoomNotAvailableException(String message) {
        super(message);
    }
}