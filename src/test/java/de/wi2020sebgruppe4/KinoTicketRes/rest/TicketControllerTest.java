package de.wi2020sebgruppe4.KinoTicketRes.rest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.wi2020sebgruppe4.KinoTicketRes.model.Layout;
import de.wi2020sebgruppe4.KinoTicketRes.model.Movie;
import de.wi2020sebgruppe4.KinoTicketRes.model.Room;
import de.wi2020sebgruppe4.KinoTicketRes.model.Seat;
import de.wi2020sebgruppe4.KinoTicketRes.model.Show;
import de.wi2020sebgruppe4.KinoTicketRes.model.Ticket;
import de.wi2020sebgruppe4.KinoTicketRes.model.TicketRequestObject;
import de.wi2020sebgruppe4.KinoTicketRes.model.User;
import de.wi2020sebgruppe4.KinoTicketRes.repositories.SeatRepository;
import de.wi2020sebgruppe4.KinoTicketRes.repositories.ShowRepository;
import de.wi2020sebgruppe4.KinoTicketRes.repositories.TicketRepository;
import de.wi2020sebgruppe4.KinoTicketRes.repositories.UserRepository;

@SpringBootTest
@RunWith(SpringRunner.class)
@TestPropertySource(locations="classpath:test.properties")
@AutoConfigureMockMvc
public class TicketControllerTest {
	
	MockMvc mvc;
	
	@MockBean
	TicketRepository repo;
	
	@MockBean
	ShowRepository showRepository;
	
	@MockBean
	UserRepository userRepository;
	
	@MockBean
	SeatRepository seatRepository;
	
	@Autowired
    WebApplicationContext wac;
	
	JacksonTester<Ticket> jt;
	JacksonTester<TicketRequestObject> jtco;
	static UUID uuid;
	
	@BeforeAll
	static void beforeAll() {
		uuid = new UUID(2, 2);
	}
	
	@BeforeEach
    void beforeEach() {
        JacksonTester.initFields(this, new ObjectMapper());
        mvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }
	
	Ticket getTicket() {
		User user = new User("Username", "Name", "FirstName", "email@email.com", "password");
		Show show = new Show();
		Seat seat = new Seat();
		show.setId(uuid);
		seat.setId(uuid);
		Ticket t = new Ticket(true, 2.0, 2, user, show, seat);
		t.setId(uuid);
		return t;
		}
	
	User getUser() {
		User u = new User("NewUser", "Name", "FirstName", "email@email.com", "password");
		u.setId(uuid);
		return u;
	}
	
	Seat getSeat(boolean blocked) {
		Layout layout = new Layout();
		Show show = new Show();
		layout.setId(uuid);
		show.setId(uuid);
		Seat s = new Seat(2, 2, false, false, layout, show);
		s.setId(uuid);
		s.setBlocked(blocked);
		return s;
	}
	
	Show getShow() {
		Show s = new Show(new Date(2015-03-12), new Time(12-12-12), new Movie(), new Room(), null);
		s.setId(uuid);
		return s;
	}
	
	Optional<Ticket> getOptionalTicket() {
		Ticket t = getTicket();
		return Optional.of(t);
	}
	
	Optional<User> getOptionalUser() {
		User u = getUser();
		return Optional.of(u);
	}
	
	Optional<Seat> getOptionalSeat(boolean blocked) {
		Seat s = getSeat(blocked);
		return Optional.of(s);
	}
	
	Optional<Show> getOptionalShow() {
		Show s = getShow();
		return Optional.of(s);
	}
	
	@Test
	void testGetAll() throws Exception {
		when(repo.findAll()).thenReturn(new ArrayList<Ticket>());
		mvc.perform(get("/tickets"))
        		.andExpect(status().isOk());
	}
	
	@Test
	void testGetById() throws Exception {
		when(repo.findById(uuid)).thenReturn(getOptionalTicket());
		MockHttpServletResponse response = mvc.perform(get("/tickets/" + uuid)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn().getResponse();
		assertEquals(jt.write(getTicket()).getJson(), response.getContentAsString());
	}
	
	@Test
	void testGetByIdException() throws Exception {
		when(repo.findById(new UUID(0, 0))).thenReturn(getOptionalTicket());
		mvc.perform(get("/tickets/" + uuid)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}
	
	
	@Test
	void testAddTicket() throws Exception {
		when(repo.findById(uuid)).thenReturn(getOptionalTicket());
		when(userRepository.findById(uuid)).thenReturn(getOptionalUser());
		when(seatRepository.findById(uuid)).thenReturn(getOptionalSeat(false));
		when(showRepository.findById(uuid)).thenReturn(getOptionalShow());
		mvc.perform(put("/tickets/add")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jtco.write(new TicketRequestObject(uuid, uuid, 2.0, uuid, 4)).getJson()))
				.andExpect(status().isCreated());
	}
	
	@Test
	void testAddTicketNoShow() throws Exception {
		when(repo.findById(uuid)).thenReturn(getOptionalTicket());
		when(seatRepository.findById(uuid)).thenReturn(getOptionalSeat(false));
		when(userRepository.findById(uuid)).thenReturn(getOptionalUser());
		when(showRepository.findById(uuid)).thenReturn(getOptionalShow());
		mvc.perform(put("/tickets/add")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jtco.write(new TicketRequestObject(uuid, uuid, 2.0, null, 2)).getJson()))
				.andExpect(status().isNotFound());
	}
	
	@Test
	void testAddTicketNoUser() throws Exception {
		when(repo.findById(uuid)).thenReturn(getOptionalTicket());
		when(seatRepository.findById(uuid)).thenReturn(getOptionalSeat(false));
		when(userRepository.findById(uuid)).thenReturn(getOptionalUser());
		when(showRepository.findById(uuid)).thenReturn(getOptionalShow());
		mvc.perform(put("/tickets/add")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jtco.write(new TicketRequestObject(null, uuid, 2.0, uuid, 2)).getJson()))
				.andExpect(status().isNotFound());
	}
	
	@Test
	void testAddTicketAlreadyBooked() throws Exception {
		when(repo.findById(uuid)).thenReturn(getOptionalTicket());
		when(seatRepository.findById(uuid)).thenReturn(getOptionalSeat(true));
		when(userRepository.findById(uuid)).thenReturn(getOptionalUser());
		when(showRepository.findById(uuid)).thenReturn(getOptionalShow());
		mvc.perform(put("/tickets/add")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jtco.write(new TicketRequestObject(uuid, uuid, 2.0, uuid, 2)).getJson()))
				.andExpect(status().isNotAcceptable());
	}
	
	@Test
	void testAddTicketNoSeat() throws Exception {
		when(repo.findById(uuid)).thenReturn(getOptionalTicket());
		when(seatRepository.findById(uuid)).thenReturn(getOptionalSeat(false));
		when(userRepository.findById(uuid)).thenReturn(getOptionalUser());
		when(showRepository.findById(uuid)).thenReturn(getOptionalShow());
		mvc.perform(put("/tickets/add")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jtco.write(new TicketRequestObject(uuid, null, 2.0, uuid, 2)).getJson()))
				.andExpect(status().isNotFound());
	}
	
	@Test
	void testDeleteTicket() throws Exception {
		when(repo.findById(uuid)).thenReturn(getOptionalTicket());
		when(seatRepository.findById(uuid)).thenReturn(getOptionalSeat(true));
		mvc.perform(delete("/tickets/"+uuid))
				.andExpect(status().isOk());
	}
	
	@Test
	void testDeleteTicketNoSeat() throws Exception {
		when(repo.findById(uuid)).thenReturn(getOptionalTicket());
		when(seatRepository.findById(new UUID(0, 0))).thenReturn(getOptionalSeat(false));
		mvc.perform(delete("/tickets/"+uuid))
		.andExpect(status().isNotFound());
	}
	
	@Test
	void testDeleteTicketNoTicket() throws Exception {
		when(repo.findById(new UUID(0, 0))).thenReturn(getOptionalTicket());
		when(seatRepository.findById(uuid)).thenReturn(getOptionalSeat(false));
		mvc.perform(delete("/tickets/"+uuid))
		.andExpect(status().isNotFound());
	}
}
