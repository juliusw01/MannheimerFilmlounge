package de.wi2020sebgruppe4.KinoTicketRes.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import de.wi2020sebgruppe4.KinoTicketRes.model.Seat;
import de.wi2020sebgruppe4.KinoTicketRes.model.Show;
import de.wi2020sebgruppe4.KinoTicketRes.model.Ticket;
import de.wi2020sebgruppe4.KinoTicketRes.model.User;

@SpringBootTest
@TestPropertySource(locations="classpath:test.properties")
public class TicketTest {
	static UUID uuid;
	static User user;
	static Show show;
	static Seat seat;
	
	@BeforeAll
	static void beforeAll() {
		uuid = new UUID(2, 2);
		user = new User("Username", "Name", "FirstName", "email@email.com", "password");
		show = new Show();
		seat = new Seat();
		
		user.setId(uuid);
		show.setId(uuid);
		seat.setId(uuid);
	}
	
    @Test
    @DisplayName("constructor test")
    public void constructorTest() {
       Ticket ticket = new Ticket(true, 2.0, 2, user, show, seat);
       
       assertEquals(true, ticket.isPaid());
       assertEquals(2.0, ticket.getPrice());
       assertEquals(2, ticket.getPaymentMethod());
       assertEquals(user, ticket.getUser());
       assertEquals(show, ticket.getShow());
       assertEquals(seat, ticket.getSeat());
    }

    @Test
    @DisplayName("getter/setter Test")
    public void getterSetterTest() {
       Ticket ticket = new Ticket();
       ticket.setId(uuid);
       ticket.setPaid(true);
       ticket.setPrice(2.0);
       ticket.setPaymentMethod(2);
       ticket.setUser(user);
       ticket.setShow(show);
       ticket.setSeat(seat);
       
       assertEquals(true, ticket.isPaid());
       assertEquals(2.0, ticket.getPrice());
       assertEquals(2, ticket.getPaymentMethod());
       assertEquals(user, ticket.getUser());
       assertEquals(show, ticket.getShow());
       assertEquals(seat, ticket.getSeat());
       assertEquals(uuid, ticket.getId());
    }

    @Test
    @DisplayName("Test hashCode")
    public void hashCodeTest() {
    	Ticket ticket = new Ticket(true, 2.0, 2, user, show, seat);
    	Ticket ticket2 = new Ticket(true, 2.0, 2, user, show, seat);
    	Ticket ticket3 = new Ticket();
    	
    	assertEquals(ticket.hashCode(), ticket2.hashCode());
    	ticket.setId(uuid);
    	assertNotEquals(ticket.hashCode(), ticket3.hashCode());
    }
    
    @Test
    @DisplayName("Test equals")
    public void equalsTest() {
    	Ticket ticket = new Ticket(true, 2.0, 2, user, show, seat);
    	Ticket ticket2 = new Ticket(true, 2.0, 2, user, show, seat);
    	Ticket ticket3 = new Ticket(true, 2.0, 2, user, show, seat);
    	ticket3.setId(uuid);
    	Ticket ticket4 = new Ticket(true, 2.0, 2, user, show, seat);
    	ticket4.setId(new UUID(2, 2));
    	Ticket ticket5 = new Ticket(true, 2.0, 1, user, show, seat);
    	Ticket ticket6 = new Ticket(true, 5.0, 2, user, show, seat);
    	Ticket ticket7 = new Ticket(true, 2.0, 2, user, show, null);
    	Ticket ticket8 = new Ticket(true, 2.0, 2, user, show, null);
    	Ticket ticket9 = new Ticket(true, 2.0, 2, user, show, new Seat());
    	Ticket ticket10 = new Ticket(true, 2.0, 2, user, null, seat);
    	Ticket ticket11 = new Ticket(true, 2.0, 2, user, null, seat);
    	Ticket ticket12 = new Ticket(true, 2.0, 2, user, new Show(), seat);
    	Ticket ticket13 = new Ticket(true, 2.0, 2, null, show, seat);
    	Ticket ticket14 = new Ticket(true, 2.0, 2, null, show, seat);
    	Ticket ticket15 = new Ticket(true, 2.0, 2, new User(), show, seat);
    	
    	assertEquals(true, ticket.equals(ticket));
    	assertEquals(false, ticket.equals(null));
    	assertEquals(false, ticket.equals(show));
    	assertEquals(false, ticket.equals(ticket3));
    	assertEquals(true, ticket3.equals(ticket4));
    	ticket4.setId(new UUID(2, 3));
    	assertEquals(false, ticket3.equals(ticket4));
    	assertEquals(false, ticket.equals(ticket5));
    	assertEquals(false, ticket.equals(ticket6));
    	assertEquals(false, ticket7.equals(ticket));
    	assertEquals(true, ticket7.equals(ticket8));
    	assertEquals(false, ticket.equals(ticket9));
    	assertEquals(false, ticket10.equals(ticket));
    	assertEquals(true, ticket10.equals(ticket11));
    	assertEquals(false, ticket.equals(ticket12));
    	assertEquals(false, ticket13.equals(ticket));
    	assertEquals(true, ticket13.equals(ticket14));
    	assertEquals(false, ticket.equals(ticket15));
    	assertEquals(true, ticket.equals(ticket2));
    }
}
