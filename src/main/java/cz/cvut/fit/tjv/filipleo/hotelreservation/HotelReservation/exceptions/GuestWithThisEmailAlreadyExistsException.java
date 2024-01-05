package cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.exceptions;

public class GuestWithThisEmailAlreadyExistsException  extends RuntimeException{
    public GuestWithThisEmailAlreadyExistsException(String guestEmail) {
        super("Customer with this email already exist: " + guestEmail);
    }
}
