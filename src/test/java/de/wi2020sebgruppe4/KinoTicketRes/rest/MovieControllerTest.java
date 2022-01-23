package de.wi2020sebgruppe4.KinoTicketRes.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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

import de.wi2020sebgruppe4.KinoTicketRes.model.Movie;
import de.wi2020sebgruppe4.KinoTicketRes.model.Room;
import de.wi2020sebgruppe4.KinoTicketRes.model.Show;
import de.wi2020sebgruppe4.KinoTicketRes.repositories.MovieRepository;
import de.wi2020sebgruppe4.KinoTicketRes.repositories.ShowRepository;

@SpringBootTest
@TestPropertySource(locations="classpath:test.properties")
@AutoConfigureMockMvc
public class MovieControllerTest {
	
	MockMvc mvc;
	
	@MockBean
	MovieRepository repo;
	
	@MockBean
	ShowRepository showRepository;
	
	@Autowired
    WebApplicationContext wac;
	
	JacksonTester<Movie> jt;
	
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
	
	 Movie getMovie() {
		    Movie m = new Movie("Star Wars2", "German", 120, "George Lucas", "Weltraum ist cool", null, "https://picute.link", "https://trailer-link", false, 12);
	    	m.setId(uuid);
	    	return m;
	}
	 
	 Show getShow() {
		 	Show s = new Show(new Date(2015-02-01), new Time(02-02-02), new Movie(), new Room(), new ArrayList<>());
		 	s.setId(uuid);
		 	return s;
	 }
	 
	 Optional<Movie> getOptionalMovie() {
	    	Movie m = getMovie();
	    	return Optional.of(m);
	 }
	 
	 Optional<List<Show>> getOptionalShows() {
		 	Show s = getShow();
		 	Show s2 = getShow();
		 	List<Show> list = new ArrayList<>();
		 	list.add(s);
		 	list.add(s2);
		 	return Optional.of(list);
	 }
	@Test
    void testGetAll() throws Exception {
    	when(repo.findAll()).thenReturn(new ArrayList<Movie>());
        mvc.perform(get("/movies"))
                .andExpect(status().isOk());
    }
	
	@Test
	void testGetById() throws Exception {
		when(repo.findById(uuid)).thenReturn(getOptionalMovie());
		MockHttpServletResponse response = mvc.perform(get("/movies/" + uuid)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn().getResponse();
		assertEquals(jt.write(getMovie()).getJson(), response.getContentAsString());
	}
	
	@Test 
	void testGetByIdException() throws Exception {
		mvc.perform(get("/movies/" + new UUID(0, 0))
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}
	
	@Test
	void testGetShowsById() throws Exception {
		when(repo.findById(uuid)).thenReturn(getOptionalMovie());
		when(showRepository.findAllByMovie(any())).thenReturn(getOptionalShows());
		mvc.perform(get("/movies/"+uuid+"/shows")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
	
	@Test 
	void testGetShowsByIdException() throws Exception {
		when(repo.findById(new UUID(0, 0))).thenReturn(getOptionalMovie());
		mvc.perform(get("/movies/"+uuid+"/shows")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}
		
	@Test
	void testUpdateMovie() throws Exception {
		when(repo.findById(uuid)).thenReturn(getOptionalMovie());
		mvc.perform(put("/movies/update/"+uuid)
		        .contentType(MediaType.APPLICATION_JSON).content(jt.write(getMovie()).getJson()))
		       	.andExpect(status().isOk());
	}
	
	@Test
	void testUpdateMovieException() throws Exception {
		when(repo.findById(new UUID(0, 0))).thenReturn(getOptionalMovie());
		mvc.perform(put("/movies/update/"+uuid)
		        .contentType(MediaType.APPLICATION_JSON)
		        .content(jt.write(getMovie()).getJson()))
		        .andExpect(status().isNotFound());
	}
	
	@Test
	void testAddMovie() throws Exception {
		mvc.perform(put("/movies/add/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jt.write(getMovie()).getJson()))
				.andExpect(status().isCreated());
	}
	
	@Test
	void testDeleteMovie() throws Exception {
		when(repo.findById(uuid)).thenReturn(getOptionalMovie());
		mvc.perform(delete("/movies/"+uuid))
				.andExpect(status().isOk());
	}
	
	@Test
	void testDeleteMovieException() throws Exception {
		when(repo.findById(new UUID(0, 0))).thenReturn(getOptionalMovie());
		mvc.perform(delete("/movies/"+uuid))
				.andExpect(status().isNotFound());
	}
}