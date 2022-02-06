package de.wi2020sebgruppe4.KinoTicketRes.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import de.wi2020sebgruppe4.KinoTicketRes.model.Token;
import de.wi2020sebgruppe4.KinoTicketRes.model.User;

@SpringBootTest
@TestPropertySource(locations="classpath:test.properties")
public class TokenTests {
	
static UUID uuid;
	
	@BeforeAll
	static void beforeAll() {
		uuid = new UUID(2,2);
	}
	
	@Test
	public void testHashCode() {
		Token t = new Token(true, new User());
		t.setId(uuid);
		Token t2 = new Token(false, null);
		assertNotEquals(t.hashCode(), t2.hashCode());
		
	}
	
	@Test
	public void testEquals() {
		User u = new User();
		Token t = new Token(true, u);
		t.setId(uuid);
		Token t2 = new Token(true, u);
		t2.setId(uuid);
		Token t3 = new Token(false, u);
		t3.setId(uuid);
		t3.setValid(false);
		Token t4 = new Token(true, null);
		Token t5 = new Token(false, u);
		
		assertEquals(true, t.equals(t));
		assertEquals(true, t.equals(t2));
		assertEquals(false, t.equals(t3));
		assertEquals(false, t.equals(t4));
		assertEquals(false, t.equals(null));
		assertEquals(false, t.equals(new User()));
	}
	
}
