package de.wi2020sebgruppe4.KinoTicketRes.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Date;
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

import de.wi2020sebgruppe4.KinoTicketRes.model.Movie;
import de.wi2020sebgruppe4.KinoTicketRes.model.Review;
import de.wi2020sebgruppe4.KinoTicketRes.model.ReviewRequestObject;
import de.wi2020sebgruppe4.KinoTicketRes.model.User;
import de.wi2020sebgruppe4.KinoTicketRes.repositories.MovieRepository;
import de.wi2020sebgruppe4.KinoTicketRes.repositories.ReviewRepository;
import de.wi2020sebgruppe4.KinoTicketRes.repositories.UserRepository;

@SpringBootTest
@TestPropertySource(locations="classpath:test.properties")
@AutoConfigureMockMvc
public class ReviewControllerTest {
	MockMvc mvc;
	
	@MockBean
	ReviewRepository repo;
	
	@MockBean
	UserRepository userRepository;
	
	@MockBean
	MovieRepository movieRepository;
	
	@Autowired
    WebApplicationContext wac;
	
	JacksonTester<Review> jt;
	JacksonTester<ReviewRequestObject> jtco;
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
	
	Review getReview() {
		User u = new User("Username", "Name", "FirstName", "email@email.com", "password");
		Movie m = new Movie("Star Wars", "German", 120, "George Lucas", "Weltraum ist cool", null, "https://picute.link", "https://trailer-link", false, 12);
		Review r = new Review("Titel", "content", u, m, null, 4);
		r.setId(uuid);
		return r;
	}
	
	User getUser() {
		User u = new User("Username", "Name", "FirstName", "email@email.com", "password");
		u.setId(uuid);
		return u;
	}
	
	Movie getMovie() {
		Movie m = new Movie("Star Wars2", "German", 120, "George Lucas", "Weltraum ist cool", null, "https://picute.link", "https://trailer-link", false, 12);
    	m.setId(uuid);
    	return m;
	}
	
	Optional<Review> getOptionalReview() {
		Review r = getReview();
		return Optional.of(r);
	}
	
	Optional<User> getOptionalUser() {
		User u = getUser();
		return Optional.of(u);
	}
	
	Optional<Movie> getOptionalMovie() {
		Movie m = getMovie();
		return Optional.of(m);
	}
	
	@Test
	void testGetAll() throws Exception {
		when(repo.findAll()).thenReturn(new ArrayList<Review>());
		mvc.perform(get("/reviews"))
				.andExpect(status().isOk());
	}
	
	@Test
	void testGetById() throws Exception {
		when(repo.findById(uuid)).thenReturn(getOptionalReview());
		MockHttpServletResponse response = mvc.perform(get("/reviews/" + uuid)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn().getResponse();
		assertEquals(jt.write(getReview()).getJson(), response.getContentAsString());
	}
	
	@Test
	void testGetByIdException() throws Exception {
		when(repo.findById(new UUID(0, 0))).thenReturn(getOptionalReview());
		mvc.perform(get("/reviews/" + uuid)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}
	
	@Test
	void testAddReview() throws Exception {
		when(repo.findById(uuid)).thenReturn(getOptionalReview());
		when(userRepository.findById(uuid)).thenReturn(getOptionalUser());
		when(movieRepository.findById(uuid)).thenReturn(getOptionalMovie());
		
		mvc.perform(put("/reviews/add")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jtco.write(new ReviewRequestObject("Titel", "content", uuid, uuid, 4, new Date(2021-12-29))).getJson()))
				.andExpect(status().isCreated());
	}
	
	@Test
	void testAddReviewNoUser() throws Exception {
		when(repo.findById(uuid)).thenReturn(getOptionalReview());
		when(userRepository.findById(uuid)).thenReturn(getOptionalUser());
		when(movieRepository.findById(uuid)).thenReturn(getOptionalMovie());
		
		mvc.perform(put("/reviews/add")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jtco.write(new ReviewRequestObject("Titel", "content", null, uuid, 4, new Date(2021-12-29))).getJson()))
				.andExpect(status().isCreated());
	}
	
	@Test
	void testAddReviewNoMovie() throws Exception {
		when(repo.findById(uuid)).thenReturn(getOptionalReview());
		when(userRepository.findById(uuid)).thenReturn(getOptionalUser());
		when(movieRepository.findById(uuid)).thenReturn(getOptionalMovie());
		
		mvc.perform(put("/reviews/add")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jtco.write(new ReviewRequestObject("Titel", "content", uuid, null, 4, new Date(2021-12-29))).getJson()))
				.andExpect(status().isCreated());
	}
	
	@Test
	void testAddReviewNoUserException() throws Exception {
		when(repo.findById(uuid)).thenReturn(getOptionalReview());
		when(userRepository.findById(new UUID(0, 0))).thenReturn(getOptionalUser());
		when(movieRepository.findById(uuid)).thenReturn(getOptionalMovie());
		
		mvc.perform(put("/reviews/add")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jtco.write(new ReviewRequestObject("Titel", "content", uuid, uuid, 4, new Date(2021-12-29))).getJson()))
				.andExpect(status().isNotFound());
	}
	
	@Test
	void testAddReviewNoReviewException() throws Exception {
		when(repo.findById(uuid)).thenReturn(getOptionalReview());
		when(userRepository.findById(uuid)).thenReturn(getOptionalUser());
		when(movieRepository.findById(new UUID(0, 0))).thenReturn(getOptionalMovie());
		
		mvc.perform(put("/reviews/add")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jtco.write(new ReviewRequestObject("Titel", "content", uuid, uuid, 4, new Date(2021-12-29))).getJson()))
				.andExpect(status().isNotFound());
	}
	
	@Test
	void testUpdateReview() throws Exception {
		when(repo.findById(uuid)).thenReturn(getOptionalReview());
		when(userRepository.findById(uuid)).thenReturn(getOptionalUser());
		when(movieRepository.findById(uuid)).thenReturn(getOptionalMovie());
		mvc.perform(put("/reviews/update/"+uuid)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jtco.write(new ReviewRequestObject("Titel", "content", uuid, uuid, 4, new Date(2021-12-29))).getJson()))
				.andExpect(status().isOk());
	}
	
	@Test
	void testUpdateReviewNoUser() throws Exception {
		when(repo.findById(uuid)).thenReturn(getOptionalReview());
		when(userRepository.findById(uuid)).thenReturn(getOptionalUser());
		when(movieRepository.findById(uuid)).thenReturn(getOptionalMovie());
		mvc.perform(put("/reviews/update/"+uuid)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jtco.write(new ReviewRequestObject("Titel", "content", null, uuid, 4, new Date(2021-12-29))).getJson()))
				.andExpect(status().isOk());
	}
	
	@Test
	void testUpdateReviewNoMovie() throws Exception {
		when(repo.findById(uuid)).thenReturn(getOptionalReview());
		when(userRepository.findById(uuid)).thenReturn(getOptionalUser());
		when(movieRepository.findById(uuid)).thenReturn(getOptionalMovie());
		mvc.perform(put("/reviews/update/"+uuid)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jtco.write(new ReviewRequestObject("Titel", "content", uuid, null, 4, new Date(2021-12-29))).getJson()))
				.andExpect(status().isOk());
	}
	
	@Test
	void testUpdateReviewNoUserException() throws Exception {
		when(repo.findById(uuid)).thenReturn(getOptionalReview());
		when(userRepository.findById(new UUID(0, 0))).thenReturn(getOptionalUser());
		when(movieRepository.findById(uuid)).thenReturn(getOptionalMovie());
		mvc.perform(put("/reviews/update/"+uuid)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jtco.write(new ReviewRequestObject("Titel", "content", uuid, uuid, 4, new Date(2021-12-29))).getJson()))
				.andExpect(status().isNotFound());
	}
	
	@Test
	void testUpdateReviewNoMovieException() throws Exception {
		when(repo.findById(uuid)).thenReturn(getOptionalReview());
		when(userRepository.findById(uuid)).thenReturn(getOptionalUser());
		when(movieRepository.findById(new UUID(0, 0))).thenReturn(getOptionalMovie());
		mvc.perform(put("/reviews/update/"+uuid)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jtco.write(new ReviewRequestObject("Titel", "content", uuid, uuid, 4, new Date(2021-12-29))).getJson()))
				.andExpect(status().isNotFound());
	}
	
	@Test
	void testDeleteReview() throws Exception {
		when(repo.findById(uuid)).thenReturn(getOptionalReview());
		mvc.perform(delete("/reviews/"+uuid))
		.andExpect(status().isOk());
	}
	
	@Test
	void testDeleteReviewException() throws Exception {
		when(repo.findById(new UUID(0, 0))).thenReturn(getOptionalReview());
		mvc.perform(delete("/reviews/"+uuid))
		.andExpect(status().isNotFound());
	}
	
}
