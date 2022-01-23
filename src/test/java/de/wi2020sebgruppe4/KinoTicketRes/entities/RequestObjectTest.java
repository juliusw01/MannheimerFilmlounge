package de.wi2020sebgruppe4.KinoTicketRes.entities;

import java.sql.Date;
import java.sql.Time;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import de.wi2020sebgruppe4.KinoTicketRes.model.LayoutRequestObject;
import de.wi2020sebgruppe4.KinoTicketRes.model.MovieRequestObject;
import de.wi2020sebgruppe4.KinoTicketRes.model.ReviewRequestObject;
import de.wi2020sebgruppe4.KinoTicketRes.model.RoomRequestObject;
import de.wi2020sebgruppe4.KinoTicketRes.model.ShowRequestObject;
import de.wi2020sebgruppe4.KinoTicketRes.model.TicketRequestObject;
import de.wi2020sebgruppe4.KinoTicketRes.model.UserRequestObject;

@SpringBootTest
@TestPropertySource(locations="classpath:test.properties")
public class RequestObjectTest {
	
	
	@Test
	@DisplayName("MovieRequestObject Test")
	public void movieRequestObjectTest() {
		MovieRequestObject m = new MovieRequestObject("Titel", "Language", 2.0, "director", "description", 
						new Date(2015-02-04), "pictureLink", "trailerLink", false, 12);
	}
	
	@Test
	@DisplayName("ShowRequestObject Test")
	public void showRequestObjectTest() {
		ShowRequestObject s = new ShowRequestObject(new Date(2020-12-02), new Time(20-04-02), new UUID(2, 2), new UUID(2, 3));
		ShowRequestObject s2 = new ShowRequestObject(new Date(2020-12-02), new Time(20-04-02), null, null);
	}
	
	@Test
	@DisplayName("LayoutRequestObject Test")
	public void layoutRequestObjectTest() {
		LayoutRequestObject l = new LayoutRequestObject(2, 2, new UUID(2, 2));
		LayoutRequestObject l2 = new LayoutRequestObject(2, 2, null);
	}
	
	@Test
	@DisplayName("ReviewRequestObject Test")
	public void reviewRequestObjectTest() {
		ReviewRequestObject r = new ReviewRequestObject("Titel", "content", new UUID(2, 2), new UUID(2, 3), 3, new Date(2015-02-01));
	}
	
	@Test
	@DisplayName("RoomRequestObject Test")
	public void roomRequestObjectTest() {
		RoomRequestObject r = new RoomRequestObject(true, new UUID(2, 2));
		RoomRequestObject r2 = new RoomRequestObject(true, null);
	}
	
	@Test
	@DisplayName("TicketRequestObject Test")
	public void ticketRequestObjectTest() {
		TicketRequestObject t = new TicketRequestObject(new UUID(2, 2), new UUID(2, 2), 2.0, new UUID(2, 2), 2);
	}
	
	@Test
	@DisplayName("UserRequestObject Test")
	public void userRequestObjectTest() {
		UserRequestObject u = new UserRequestObject("userName", "name", "firstName", "email", "password");
	}
}
