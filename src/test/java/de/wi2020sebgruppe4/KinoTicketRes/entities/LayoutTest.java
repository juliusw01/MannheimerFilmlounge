package de.wi2020sebgruppe4.KinoTicketRes.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import de.wi2020sebgruppe4.KinoTicketRes.model.Layout;
import de.wi2020sebgruppe4.KinoTicketRes.model.Room;

@SpringBootTest
@TestPropertySource(locations="classpath:test.properties")
public class LayoutTest {
	static Room r;
	static UUID uuid;
	
	@BeforeAll
	static void beforeAll() {
		uuid = new UUID(2,2);
		r = new Room();
	}
	
    @Test
    @DisplayName("constructor test")
    public void constructorTest() {
        Room room = new Room(true);
        Layout l = new Layout(30, 3, room);
        assertEquals(room, l.getRoom());
        assertEquals(30, l.getTotalSeats());
        assertEquals(3, l.getRowCount());
    }

    @Test
    @DisplayName("getter/setter Test")
    public void getterSetterTest() {
        Layout l = new Layout();
             
        l.setId(uuid);
        assertEquals(uuid, l.getId());
        
        l.setRoom(r);
        assertEquals(r, l.getRoom());
        
        l.setRowCount(3);
        assertEquals(3, l.getRowCount());

        l.setTotalSeats(30);
        assertEquals(30, l.getTotalSeats());
    }

    @Test
    @DisplayName("Test hashCode")
    public void hashCodeTest() {
        Room r = new Room();;
        Layout l1 = new Layout(30, 3, r);
        Layout l2 = new Layout(30, 3, r);
        l1.setId(uuid);

        assertEquals(l1.hashCode(), l2.hashCode());
        assertEquals(l1.equals(l2), false);

        // Test equals method
        Layout l3 = new Layout(20, 2, r);
        Layout l4 = new Layout(50, 2, r);

        assertNotEquals(l1.hashCode(), l3.hashCode());
        assertEquals(l2.equals(l4), false);
    }
    
    @Test
    @DisplayName("Test equals")
    public void equalsTest() {
    	Layout l = new Layout(30, 3, r);
    	Layout l2 = new Layout(30, 3, r);
    	Layout l3 = new Layout(30, 3, null);
    	Layout l4 = new Layout(10, 3, r);
    	l4.setId(uuid);
    	//run equals - methods
    	assertEquals(true, l.equals(l));
    	assertEquals(true, l.equals(l2));
    	assertEquals(false, l.equals(null));
    	assertEquals(false, l.equals(r));
    	assertEquals(false, l3.equals(l));
    	assertEquals(false, l3.equals(l));
    	l.setRoom(null);
    	assertEquals(true, l.equals(l3));
    	l.setRoom(r);
    	l.setId(uuid);
    	assertEquals(false, l.equals(l2));
    	assertEquals(false, l2.equals(l));
    	l2.setId(uuid);
    	assertEquals(true, l.equals(l2));
    	assertEquals(false, l.equals(l4));
    	
    }
}