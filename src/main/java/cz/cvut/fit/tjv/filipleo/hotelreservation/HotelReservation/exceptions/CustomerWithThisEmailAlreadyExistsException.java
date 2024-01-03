package cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.exceptions;

public class CustomerWithThisEmailAlreadyExistsException extends RuntimeException{
    public CustomerWithThisEmailAlreadyExistsException(String customerEmail) {
        super("Customer with this email already exist: " + customerEmail);
    }
}
