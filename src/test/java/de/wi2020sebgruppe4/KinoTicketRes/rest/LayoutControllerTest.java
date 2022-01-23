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
import de.wi2020sebgruppe4.KinoTicketRes.model.LayoutRequestObject;
import de.wi2020sebgruppe4.KinoTicketRes.model.Room;
import de.wi2020sebgruppe4.KinoTicketRes.repositories.LayoutRepository;
import de.wi2020sebgruppe4.KinoTicketRes.repositories.RoomRepository;

@SpringBootTest
@TestPropertySource(locations="classpath:test.properties")
@AutoConfigureMockMvc
public class LayoutControllerTest {
	
	MockMvc mvc;
	
	@MockBean
	LayoutRepository repo;
	
	@MockBean
	RoomRepository roomRepository;
	
	@Autowired
    WebApplicationContext wac;
	
	JacksonTester<Layout> jt;
	JacksonTester<LayoutRequestObject> jtco;
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
	
	Layout getLayout() {
		Layout l = new Layout(50, 10, getRoom());
		l.setId(uuid);
		return l;
	}
	
	Room getRoom() {
		Room r = new Room();
		r.setId(uuid);
		return r;
	}
	
	Optional<Layout> getOptionalLayout() {
		Layout l = getLayout();
		return Optional.of(l);
	}
	
	Optional<Room> getOptionalRoom() {
		Room r = getRoom();
		return Optional.of(r);
	}
	
	@Test
	void testGetAll() throws Exception {
		when(repo.findAll()).thenReturn(new ArrayList<Layout>());
		mvc.perform(get("/layouts"))
				.andExpect(status().isOk());
	}
	
	@Test
	void testGetById() throws Exception {
		when(repo.findById(uuid)).thenReturn(getOptionalLayout());
		MockHttpServletResponse response = mvc.perform(get("/layouts/" + uuid)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn().getResponse();
		assertEquals(jt.write(getLayout()).getJson(), response.getContentAsString());
	}
	
	@Test
	void testGetByIdException() throws Exception {
		when(repo.findById(new UUID(0, 0))).thenReturn(getOptionalLayout());
		MockHttpServletResponse response = mvc.perform(get("/layouts/" + uuid)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andReturn().getResponse();
	}
	
	@Test
	void testAddLayout() throws Exception {
		when(repo.findById(uuid)).thenReturn(getOptionalLayout());
		when(roomRepository.findById(uuid)).thenReturn(getOptionalRoom());
		mvc.perform(put("/layouts/add/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jtco.write(new LayoutRequestObject(2, 2, uuid)).getJson()))
				.andExpect(status().isCreated());
	}
	
	@Test
	void testAddLayoutException() throws Exception {
		when(repo.findById(uuid)).thenReturn(getOptionalLayout());
		when(roomRepository.findById(new UUID(0, 0))).thenReturn(getOptionalRoom());
		mvc.perform(put("/layouts/add/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jtco.write(new LayoutRequestObject(2, 2, uuid)).getJson()))
				.andExpect(status().isNotFound());
	}
	
	@Test
	void testAddLayoutNoRoom() throws Exception {
		when(repo.findById(uuid)).thenReturn(getOptionalLayout());
		when(roomRepository.findById(uuid)).thenReturn(getOptionalRoom());
		mvc.perform(put("/layouts/add/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jtco.write(new LayoutRequestObject(2, 2, null)).getJson()))
				.andExpect(status().isCreated());
	}
	
	@Test
	void testUpdateLayout() throws Exception {
		when(repo.findById(uuid)).thenReturn(getOptionalLayout());
		when(roomRepository.findById(uuid)).thenReturn(getOptionalRoom());
		mvc.perform(put("/layouts/update/"+uuid)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jtco.write(new LayoutRequestObject(2, 2, uuid)).getJson()))
				.andExpect(status().isOk());
	}
	
	@Test 
	void testUpdateNoLayoutException() throws Exception {
		when(repo.findById(uuid)).thenReturn(getOptionalLayout());
		when(roomRepository.findById(new UUID(0, 0))).thenReturn(getOptionalRoom());
		mvc.perform(put("/layouts/update/"+uuid)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jtco.write(new LayoutRequestObject(2, 2, uuid)).getJson()))
				.andExpect(status().isNotFound());
	}
	
	@Test
	void testUpdateNoRoomException() throws Exception {
		when(repo.findById(new UUID(0, 0))).thenReturn(getOptionalLayout());
		when(roomRepository.findById(uuid)).thenReturn(getOptionalRoom());
		mvc.perform(put("/layouts/update/"+uuid)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jtco.write(new LayoutRequestObject(2, 2, uuid)).getJson()))
				.andExpect(status().isNotFound());
	}
	
	@Test
	void testUpdateNoRoom() throws Exception {
		when(repo.findById(uuid)).thenReturn(getOptionalLayout());
		when(roomRepository.findById(uuid)).thenReturn(getOptionalRoom());
		mvc.perform(put("/layouts/update/"+uuid)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jtco.write(new LayoutRequestObject(2, 2, null)).getJson()))
				.andExpect(status().isOk());
	}
	
	@Test
	void testDeleteLayout() throws Exception {
		when(repo.findById(uuid)).thenReturn(getOptionalLayout());
		mvc.perform(delete("/layouts/"+uuid))
		.andExpect(status().isOk());
	}
	
	@Test
	void testDeleteLayoutException() throws Exception {
		when(repo.findById(new UUID(0, 0))).thenReturn(getOptionalLayout());
		mvc.perform(delete("/layouts/"+uuid))
		.andExpect(status().isNotFound());
	}
}
