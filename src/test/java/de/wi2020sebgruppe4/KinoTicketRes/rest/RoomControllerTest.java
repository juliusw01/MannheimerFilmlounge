package de.wi2020sebgruppe4.KinoTicketRes.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
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
import de.wi2020sebgruppe4.KinoTicketRes.model.Room;
import de.wi2020sebgruppe4.KinoTicketRes.model.RoomRequestObject;
import de.wi2020sebgruppe4.KinoTicketRes.repositories.LayoutRepository;
import de.wi2020sebgruppe4.KinoTicketRes.repositories.RoomRepository;

@SpringBootTest
@TestPropertySource(locations="classpath:test.properties")
@AutoConfigureMockMvc
public class RoomControllerTest {
	MockMvc mvc;
	
	@MockBean
	RoomRepository repo;
	
	@MockBean
	LayoutRepository layoutRepository;
	
	@Autowired
    WebApplicationContext wac;
	
	JacksonTester<Room> jt;
	JacksonTester<RoomRequestObject> jtco;
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
	
	Room getRoom() {
		Layout l = new Layout();
		l.setId(uuid);
		Room r = new Room(false, l);
		r.setId(uuid);
		return r;
	}
	
	Layout getLayout() {
		Room r = new Room(true);
		Layout l = new Layout(30, 3, r);
		l.setId(uuid);
		return l;
	}
	
	Optional<Room> getOptionalRoom() {
		Room r = getRoom();
		return Optional.of(r);
	}
	
	Optional<Layout> getOptionalLayout() {
		Layout l = getLayout();
		return Optional.of(l);
	}
	
	@Test
	void testGetAll() throws Exception {
		when(repo.findAll()).thenReturn(new ArrayList<Room>());
		mvc.perform(get("/rooms"))
				.andExpect(status().isOk());
	}
	
	@Test
	void testGetById() throws Exception {
		when(repo.findById(uuid)).thenReturn(getOptionalRoom());
		MockHttpServletResponse response = mvc.perform(get("/rooms/" + uuid)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn().getResponse();
		assertEquals(jt.write(getRoom()).getJson(), response.getContentAsString());
	}
	
	@Test
	void testGetByIdNoRoom() throws Exception {
		when(repo.findById(new UUID(0, 0))).thenReturn(getOptionalRoom());
		mvc.perform(get("/rooms/" + uuid)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}
	
	@Test
	void testAddRoom() throws Exception {
		when(repo.findById(uuid)).thenReturn(getOptionalRoom());
		when(layoutRepository.findById(uuid)).thenReturn(getOptionalLayout());
		mvc.perform(put("/rooms/add/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jtco.write(new RoomRequestObject(true, uuid)).getJson()))
				.andExpect(status().isCreated());
	}
	
	@Test
	void testAddRoomNoLayout() throws Exception {
		when(repo.findById(uuid)).thenReturn(getOptionalRoom());
		when(layoutRepository.findById(new UUID(0, 0))).thenReturn(getOptionalLayout());
		mvc.perform(put("/rooms/add/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jtco.write(new RoomRequestObject(true, uuid)).getJson()))
				.andExpect(status().isNotFound());
	}
	
	@Test
	void testAddRoomNoLayoutId() throws Exception {
		when(repo.findById(uuid)).thenReturn(getOptionalRoom());
		when(layoutRepository.findById(uuid)).thenReturn(getOptionalLayout());
		mvc.perform(put("/rooms/add/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jtco.write(new RoomRequestObject(true, null)).getJson()))
				.andExpect(status().isCreated());
	}
	
	@Test
	void testUpdateRoom() throws Exception {
		when(repo.findById(uuid)).thenReturn(getOptionalRoom());
		when(layoutRepository.findById(uuid)).thenReturn(getOptionalLayout());
		mvc.perform(put("/rooms/update/"+uuid)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jtco.write(new RoomRequestObject(true, uuid)).getJson()))
				.andExpect(status().isOk());
	}
	
	@Test
	void testUpdateRoomNoLayout() throws Exception {
		when(repo.findById(uuid)).thenReturn(getOptionalRoom());
		when(layoutRepository.findById(new UUID(0, 0))).thenReturn(getOptionalLayout());
		mvc.perform(put("/rooms/update/"+uuid)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jtco.write(new RoomRequestObject(true, uuid)).getJson()))
				.andExpect(status().isNotFound());
	}
	
	@Test
	void testUpdateRoomNoRoom() throws Exception {
		when(repo.findById(new UUID(0, 0))).thenReturn(getOptionalRoom());
		when(layoutRepository.findById(uuid)).thenReturn(getOptionalLayout());
		mvc.perform(put("/rooms/update/"+uuid)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jtco.write(new RoomRequestObject(true, uuid)).getJson()))
				.andExpect(status().isNotFound());
	}
	
	@Test
	void testUpdateRoomNoLayoutId() throws Exception {
		when(repo.findById(uuid)).thenReturn(getOptionalRoom());
		when(layoutRepository.findById(uuid)).thenReturn(getOptionalLayout());
		mvc.perform(put("/rooms/update/"+uuid)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jtco.write(new RoomRequestObject(true, null)).getJson()))
				.andExpect(status().isOk());
	}
	
	@Test
	void testDeleteRoom() throws Exception {
		when(repo.findById(uuid)).thenReturn(getOptionalRoom());
		mvc.perform(delete("/rooms/"+uuid))
				.andExpect(status().isOk());
	}
	
	@Test
	void testDeleteRoomNoRoom() throws Exception {
		when(repo.findById(new UUID(0, 0))).thenReturn(getOptionalRoom());
		mvc.perform(delete("/rooms/"+uuid))
				.andExpect(status().isNotFound());
	}
	
}
