package de.wi2020sebgruppe4.KinoTicketRes.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import de.wi2020sebgruppe4.KinoTicketRes.model.Room;
import de.wi2020sebgruppe4.KinoTicketRes.model.User;

@SpringBootTest
@TestPropertySource(locations="classpath:test.properties")
public class UserTest {
	static UUID uuid;
	
	@BeforeAll
	static void beforeAll() {
		uuid = new UUID(2,2);
	}
	
	
	@Test
    @DisplayName("constructor test")
    public void constructorTest() {
		User user = new User("Username", "Name", "FirstName", "email@email.com", "password");
		
		assertEquals("Username", user.getUserName());
		assertEquals("Name", user.getName());
		assertEquals("FirstName", user.getFirstName());
		assertEquals("email@email.com", user.getEmail());
		assertEquals("password", user.getPassword());
    }
	
	@Test
    @DisplayName("getter/setter Test")
    public void getterSetterTest() {
       User user = new User();
       user.setEmail("email@email.com");
       user.setFirstName("FirstName");
       user.setId(uuid);
       user.setName("Name");
       user.setUserName("Username");
       user.setPassword("password");
       
       assertEquals("Username", user.getUserName());
       assertEquals("Name", user.getName());
       assertEquals("FirstName", user.getFirstName());
       assertEquals("email@email.com", user.getEmail());
       assertEquals("password", user.getPassword());
       assertEquals(uuid, user.getId());
    }
	
	@Test
	@DisplayName("Test hashCode")
	public void hashCodeTest() {
	   User user = new User("Username", "Name", "FirstName", "email@email.com", "password");
	   user.setId(uuid);
	   User user2 = new User("Username", "Name", "FirstName", "email@email.com", "password");
	   
	   assertEquals(user.hashCode(), user2.hashCode());
       user.setEmail(null);
       user.setFirstName(null);
       user.setId(null);
       user.setName(null);
       user.setUserName(null);
       user.setPassword(null);
       assertNotEquals(user.hashCode(), user2.hashCode());
	   
	}
	
	 
	 @Test
	 @DisplayName("Test equals")
	 public void equalsTest() {
		 User u = new User("Username", "Name", "FirstName", "email@email.com", "password");
		 User u2 = new User("Username", "Name", "FirstName", "email@email.com", "password");
		 User u3 = new User("Username", "Name", "FirstName", null, "password");
		 User u14 = new User("Username", "Name", "FirstName", null, "password");
		 User u4 = new User("Username", "Name", "FirstName", "Noemail@email.com", "password");
		 User u5 = new User("Username", "Name", null, "email@email.com", "password");
		 User u15 = new User("Username", "Name", null, "email@email.com", "password");
		 User u6 = new User("Username", "Name", "NotFirstName", "email@email.com", "password");
		 User u7 = new User("Username", "Name", "FirstName", "email@email.com", "password");
		 User u17 = new User("Username", "Name", "FirstName", "email@email.com", "password");
		 User u22 = new User("Username", "Name", "FirstName", "email@email.com", "password");
		 User u8 = new User("Username", null, "FirstName", "email@email.com", "password");
		 User u18 = new User("Username", null, "FirstName", "email@email.com", "password");
		 User u9 = new User("Username", "OtherName", "FirstName", "email@email.com", "password");
		 User u10 = new User("Username", "Name", "FirstName", "email@email.com", null);
		 User u20 = new User("Username", "Name", "FirstName", "email@email.com", null);
		 User u11 = new User("Username", "Name", "FirstName", "email@email.com", "NotAPassword");
		 User u12 = new User(null, "Name", "FirstName", "email@email.com", "password");
		 User u21 = new User(null, "Name", "FirstName", "email@email.com", "password");
		 User u13 = new User("NotAUsername", "Name", "FirstName", "email@email.com", "password");
		 u7.setId(uuid);
		 u22.setId(new UUID(2,3));
		 Room r = new Room();
		 assertEquals(true, u.equals(u));
		 assertEquals(false, u.equals(null));
		 assertEquals(false, u.equals(r));
		 assertEquals(false, u3.equals(u));
		 assertEquals(true, u3.equals(u14));
		 assertEquals(false, u3.equals(u4));
		 assertEquals(false, u5.equals(u6));
		 assertEquals(true, u5.equals(u15));
		 assertEquals(false, u.equals(u6));
		 assertEquals(false, u.equals(u7));
		 assertEquals(false, u7.equals(u17));
		 assertEquals(false, u7.equals(u22));
		 u22.setId(uuid);
		 assertEquals(true, u7.equals(u22));
		 assertEquals(false, u8.equals(u7));
		 assertEquals(true, u8.equals(u18));
		 assertEquals(false, u8.equals(u9));
		 assertEquals(false, u.equals(u9));
		 assertEquals(false, u10.equals(u));
		 assertEquals(true, u10.equals(u20));
		 assertEquals(false, u10.equals(u11));
		 assertEquals(false, u.equals(u11));
		 assertEquals(false, u12.equals(u));
		 assertEquals(true, u12.equals(u21));
		 assertEquals(false, u.equals(u13));
	 }
}
