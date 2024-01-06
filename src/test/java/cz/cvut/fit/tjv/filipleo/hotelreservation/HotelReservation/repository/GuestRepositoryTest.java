package cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.repository;

import cz.cvut.fit.tjv.filipleo.hotelreservation.HotelReservation.domain.Guest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class GuestRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private GuestRepository guestRepository;

    @Test
    public void testFindByEmailFound() {
        Guest guest = new Guest(null, "John", "Doe", "john.doe@example.com", null);
        entityManager.persist(guest);
        entityManager.flush();

        Guest found = guestRepository.getByEmail("john.doe@example.com");

        assertNotNull(found);
        assertEquals("John", found.getFirstName());
        assertEquals("Doe", found.getLastName());
        assertEquals("john.doe@example.com", found.getEmail());
    }
    @Test
    public void testFindByEmailNotFound() {
        Guest found = guestRepository.getByEmail("unknown@example.com");

        assertNull(found);
    }
}
