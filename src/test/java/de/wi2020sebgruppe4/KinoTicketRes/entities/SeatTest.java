package de.wi2020sebgruppe4.KinoTicketRes.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.sql.Date;
import java.sql.Time;
import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import de.wi2020sebgruppe4.KinoTicketRes.model.Layout;
import de.wi2020sebgruppe4.KinoTicketRes.model.Movie;
import de.wi2020sebgruppe4.KinoTicketRes.model.Room;
import de.wi2020sebgruppe4.KinoTicketRes.model.Seat;
import de.wi2020sebgruppe4.KinoTicketRes.model.Show;

@SpringBootTest
@TestPropertySource(locations="classpath:test.properties")
public class SeatTest {
	static UUID uuid;
	static Layout layout;
	static Show show;
	
	@BeforeAll
	static void beforeAll() {
		uuid = new UUID(2, 2);
		layout = new Layout();
		show = new Show();
	}
	
    @Test
    @DisplayName("constructor test")
    public void constructorTest() {
	    Seat s = new Seat(2, 2, false, false, layout, show);
	   
	    assertEquals(2, s.getRowLocation());
	    assertEquals(2, s.getSeatLocation());
	    assertEquals(false, s.isPremium());
	    assertEquals(false, s.isBlocked());
	    assertEquals(layout, s.getLayout());
	    assertEquals(show, s.getShow());
    }

    @Test
    @DisplayName("getter/setter Test")
    public void getterSetterTest() {
	    Seat s = new Seat();
	   
	    s.setId(uuid);
	    s.setRowLocation(2);
	    s.setSeatLocation(2);
	    s.setPremium(false);
	    s.setBlocked(false);
	    s.setLayout(layout);
	    s.setShow(show);
	   
	    assertEquals(2, s.getRowLocation());
	    assertEquals(2, s.getSeatLocation());
	    assertEquals(false, s.isPremium());
	    assertEquals(false, s.isBlocked());
	    assertEquals(layout, s.getLayout());
	    assertEquals(show, s.getShow());
	    assertEquals(uuid, s.getId());
    }

    @Test
    @DisplayName("Test hashCode")
    public void hashCodeTest() {
	    Seat s = new Seat(2, 2, false, false, layout, show);
	    Seat s2 = new Seat(2, 2, false, false, layout, show);
	    Seat s3 = new Seat();
	    s3.setBlocked(true);
	    s3.setPremium(true);
	    s3.setId(uuid);
	    assertEquals(s.hashCode(), s.hashCode());
	    assertNotEquals(s.hashCode(), s3.hashCode());
    }
    
    @Test
    @DisplayName("Test equals")
    public void equalsTest() {
    	Room r = new Room(true);
    	Layout l2 = new Layout(30, 3, r);
    	Show show2 = new Show();
    	show2.setId(uuid);
    	Seat s = new Seat(2, 2, false, false, layout, show);
    	Seat s2 = new Seat(2, 2, false, false, layout, show);
    	Seat s3 = new Seat(2, 2, false, false, layout, show);
    	s3.setId(uuid);
    	Seat s15 = new Seat(2, 2, false, false, layout, show);
    	s15.setId(uuid);
    	Seat s4 = new Seat(2, 2, false, false, layout, show);
    	s4.setId(new UUID(2, 12));
    	Seat s5 = new Seat(2, 2, true, false, layout, show);
    	Seat s6 = new Seat(2, 2, false, true, layout, show);
    	Seat s7 = new Seat(2, 2, false, false, null, show);
    	Seat s8 = new Seat(2, 2, false, false, null, show);
    	Seat s9 = new Seat(2, 2, false, false, l2, show);
    	Seat s10 = new Seat(5, 2, false, false, layout, show);
    	Seat s11 = new Seat(2, 1, false, false, layout, show);
    	Seat s12 = new Seat(2, 2, false, false, layout, null);
    	Seat s13 = new Seat(2, 2, false, false, layout, null);
    	Seat s14 = new Seat(2, 2, false, false, layout, show2);
    	
    	assertEquals(true, s.equals(s));
    	assertEquals(false, s.equals(null));
    	assertEquals(false, s.equals(layout));
    	assertEquals(false, s3.equals(s));
    	assertEquals(false, s.equals(s3));
    	assertEquals(false, s3.equals(s4));
    	assertEquals(true, s3.equals(s15));
    	assertEquals(false, s.equals(s5));
    	assertEquals(false, s.equals(s6));
    	assertEquals(false, s7.equals(s));
    	assertEquals(true, s7.equals(s8));
    	assertEquals(false, s.equals(s9));
    	assertEquals(false, s.equals(s10));
    	assertEquals(false, s.equals(s11));
    	assertEquals(false, s12.equals(s));
    	assertEquals(true, s12.equals(s13));
    	assertEquals(false, s.equals(s13));
    	assertEquals(true, s.equals(s2));
    }
}
