package de.wi2020sebgruppe4.KinoTicketRes.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.wi2020sebgruppe4.KinoTicketRes.model.Layout;
import de.wi2020sebgruppe4.KinoTicketRes.model.Movie;
import de.wi2020sebgruppe4.KinoTicketRes.model.Room;
import de.wi2020sebgruppe4.KinoTicketRes.model.Seat;
import de.wi2020sebgruppe4.KinoTicketRes.model.Show;
import de.wi2020sebgruppe4.KinoTicketRes.model.ShowRequestObject;
import de.wi2020sebgruppe4.KinoTicketRes.repositories.LayoutRepository;
import de.wi2020sebgruppe4.KinoTicketRes.repositories.MovieRepository;
import de.wi2020sebgruppe4.KinoTicketRes.repositories.RoomRepository;
import de.wi2020sebgruppe4.KinoTicketRes.repositories.SeatRepository;
import de.wi2020sebgruppe4.KinoTicketRes.repositories.ShowRepository;

@SpringBootTest
@TestPropertySource(locations="classpath:test.properties")
@AutoConfigureMockMvc
public class ShowControllerTest {
	MockMvc mvc;
	
	@MockBean
	ShowRepository repo;
	
	@MockBean
	MovieRepository movieRepository;
	
	@MockBean
	RoomRepository roomRepository;
	
	@MockBean
	SeatRepository seatRepository;
	
	@MockBean
	LayoutRepository layoutRepository;
	
	@Autowired
    WebApplicationContext wac;
	
	JacksonTester<Show> jt;
	JacksonTester<ShowRequestObject> jtco;	
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
	
	Show getShow() {
		List<Seat> seats = new ArrayList<>();
		seats.add(getSeat());
		seats.add(getSeat());
		Show s = new Show(null, new Time(12-12-12), new Movie(), new Room(), seats);
		s.setId(uuid);
		return s;
	}
	
	Movie getMovie() {
		Movie m = new Movie("Star Wars2", "German", 120, "George Lucas", "Weltraum ist cool", null, "https://picute.link", "https://trailer-link", false, 12);
    	m.setId(uuid);
    	return m;
	}
	
	Seat getSeat() {
		Seat s = new Seat(2, 2, true, false, null, null);
		s.setId(uuid);
		return s;
	}
	
	Room getRoom() {
		Layout l = new Layout(50, 10, new Room());
		l.setId(uuid);
		Room r = new Room(false, l);
		r.setId(uuid);
		return r;
	}
	
	Layout getLayout() {
		Layout l = new Layout();
		l.setId(uuid);
		return l;
	}
	
	List<Seat> getSeatList() {
		List<Seat> seats = new ArrayList<>();
		seats.add(getSeat());
		seats.add(getSeat());
		seats.add(getSeat());
		return seats;
	}
	
	Optional<Show> getOptionalShow() {
		Show s = getShow();
		return Optional.of(s);
	}
	
	Optional<Movie> getOptionalMovie() {
		Movie m = getMovie();
		return Optional.of(m);
	}
	
	Optional<Seat> getOptionalSeat() {
		Seat s = getSeat();
		return Optional.of(s);
	}
	
	Optional<Room> getOptionalRoom() {
		Room r = getRoom();
		return Optional.of(r);
	}
	
	Optional<Room> getOptionalRoomNoLayout() {
		Room r = getRoom();
		r.setLayout(new Layout());
		return Optional.of(r);
	}
	
	Optional<List<Seat>> getOptionalSeatList() {
		List<Seat> seatList = getSeatList();
		return Optional.of(seatList);
	}
	
	Optional<Layout> getOptionalLayout() {
		Layout l = getLayout();
		return Optional.of(l);
	}
	@Test
	void testGetAll() throws Exception {
		when(repo.findAll()).thenReturn(new ArrayList<Show>());
		mvc.perform(get("/shows"))
				.andExpect(status().isOk());
	}
	
	@Test
	void testGetById() throws Exception {
		when(repo.findById(uuid)).thenReturn(getOptionalShow());
		MockHttpServletResponse response = mvc.perform(get("/shows/" + uuid)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn().getResponse();
		assertEquals(jt.write(getShow()).getJson(), response.getContentAsString());
	}
	
	@Test
	void testGetByIdNoShow() throws Exception {
		when(repo.findById(new UUID(0, 0))).thenReturn(getOptionalShow());
		mvc.perform(get("/shows/" + uuid)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}
	
	@Test
	void testAddShow() throws Exception {
		
		when(movieRepository.findById(uuid)).thenReturn(getOptionalMovie());
		when(roomRepository.findById(uuid)).thenReturn(getOptionalRoom());
		when(seatRepository.findById(uuid)).thenReturn(getOptionalSeat());
		
		mvc.perform(put("/shows/add/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jtco.write(new ShowRequestObject(new Date(2021-12-30), new Time(12-12-12), uuid, uuid)).getJson()))
				.andExpect(status().isCreated());
	}
	
	@Test
	void testAddShowBadRequest() throws Exception {
		
		when(repo.findById(uuid)).thenReturn(getOptionalShow());
		when(movieRepository.findById(uuid)).thenReturn(getOptionalMovie());
		when(roomRepository.findById(uuid)).thenReturn(getOptionalRoomNoLayout());
		when(seatRepository.findById(uuid)).thenReturn(getOptionalSeat());
		
		mvc.perform(put("/shows/add/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jtco.write(new ShowRequestObject(new Date(2021-12-30), new Time(12-12-12), uuid, uuid)).getJson()))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	void testAddShowNoMovie() throws Exception {
		
		when(repo.findById(uuid)).thenReturn(getOptionalShow());
		when(movieRepository.findById(uuid)).thenReturn(getOptionalMovie());
		when(roomRepository.findById(uuid)).thenReturn(getOptionalRoom());
		when(seatRepository.findById(uuid)).thenReturn(getOptionalSeat());
		
		mvc.perform(put("/shows/add/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jtco.write(new ShowRequestObject(new Date(2021-12-30), new Time(12-12-12), null, uuid)).getJson()))
				.andExpect(status().isCreated());
	}
	
	@Test
	void testAddShowNoMovieException() throws Exception {
		
		when(repo.findById(uuid)).thenReturn(getOptionalShow());
		when(movieRepository.findById(new UUID(0, 0))).thenReturn(getOptionalMovie());
		when(roomRepository.findById(uuid)).thenReturn(getOptionalRoom());
		when(seatRepository.findById(uuid)).thenReturn(getOptionalSeat());
		
		mvc.perform(put("/shows/add/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jtco.write(new ShowRequestObject(new Date(2021-12-30), new Time(12-12-12), uuid, uuid)).getJson()))
				.andExpect(status().isNotFound());
	}
	
	@Test
	void testAddShowNoRoomException() throws Exception {
		
		when(repo.findById(uuid)).thenReturn(getOptionalShow());
		when(movieRepository.findById(uuid)).thenReturn(getOptionalMovie());
		when(roomRepository.findById(new UUID(0, 0))).thenReturn(getOptionalRoom());
		when(seatRepository.findById(uuid)).thenReturn(getOptionalSeat());
		
		mvc.perform(put("/shows/add/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jtco.write(new ShowRequestObject(new Date(2021-12-30), new Time(12-12-12), uuid, uuid)).getJson()))
				.andExpect(status().isNotFound());
	}
	
	@Test
	void testGetSeatsForShow() throws Exception {
		when(repo.findById(uuid)).thenReturn(getOptionalShow());
		when(seatRepository.findAllByShow(getShow())).thenReturn(getOptionalSeatList());
		
		mvc.perform(get("/shows/"+uuid+"/seats")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
	
	@Test
	void testGetSeatsForNoShow() throws Exception {
		when(repo.findById(new UUID(0, 0))).thenReturn(getOptionalShow());
		when(seatRepository.findAllByShow(getShow())).thenReturn(getOptionalSeatList());
		mvc.perform(get("/shows/"+uuid+"/seats")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}
	
	@Test
	void testGetSeatsForNoSeats() throws Exception {
		when(repo.findById(uuid)).thenReturn(getOptionalShow());
		when(seatRepository.findAllByShow(new Show())).thenReturn(getOptionalSeatList());
		mvc.perform(get("/shows/"+uuid+"/seats")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}
	
	@Test
	void testDeleteShow() throws Exception {
		when(repo.findById(uuid)).thenReturn(getOptionalShow());
		mvc.perform(delete("/shows/"+uuid))
		.andExpect(status().isOk());
	}
	
	@Test
	void testDeleteNoShow() throws Exception {
		when(repo.findById(new UUID(0, 0))).thenReturn(getOptionalShow());
		mvc.perform(delete("/shows/"+uuid))
		.andExpect(status().isNotFound());
	}
}
