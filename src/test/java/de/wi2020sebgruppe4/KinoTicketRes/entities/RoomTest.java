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
public class RoomTest {
	static UUID uuid;
	static Layout l;
	
	@BeforeAll
	static void beforeAll() {
		uuid = new UUID(2,2);
		l = new Layout();
	}
	
	@Test
	@DisplayName("constructor test")
	public void constructorTest() {
		Room room = new Room(true, l);
		
		assertEquals(true, room.isCanShow3D());
		assertEquals(l, room.getLayout());
	}
	
	@Test
	@DisplayName("getter/setter Test")
	public void getterSetterTest() {
		Room room = new Room();
		
		room.setCanShow3D(true);
		room.setId(uuid);
		room.setLayout(l);
		
		assertEquals(true, room.isCanShow3D());
		assertEquals(uuid, room.getId());
		assertEquals(l, room.getLayout());
	}
	
	@Test
	@DisplayName("Test hashCode")
	public void hashCodeTest() {
		Room r = new Room();
		Room r2 = new Room();
		Room r3 = new Room(true, l);
		r3.setId(uuid);
		assertEquals(r.hashCode(), r2.hashCode());
		assertNotEquals(r.hashCode(), r3.hashCode());
	}
	
	@Test
	@DisplayName("Test equals")
	public void equalsTest() {
		Room r = new Room(true, l);
		Room r2 = new Room(true, l);
		Room r3 = new Room(false, l);
		Room r4 = new Room(true, l);
		Room r5 = new Room(true, l);
		Layout l2 = new Layout(30, 3, r);
		r4.setId(uuid);
		r5.setId(uuid);
		assertEquals(true, r.equals(r));
		assertEquals(false, r.equals(null));
		assertEquals(false, r.equals(l));
		assertEquals(false, r.equals(r3));
		assertEquals(false, r.equals(r4));
		assertEquals(true, r4.equals(r5));
		r5.setId(new UUID(2,3));
		assertEquals(false, r4.equals(r5));
		r5.setId(uuid);
		r4.setLayout(l2);
		assertEquals(false, r4.equals(r5));
		r4.setLayout(null);
		assertEquals(false, r4.equals(r5));
		r5.setLayout(null);
		assertEquals(true, r4.equals(r5));
		assertEquals(true, r.equals(r2));
		
	}
	
}
