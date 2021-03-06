package de.wi2020sebgruppe4.KinoTicketRes.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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

import de.wi2020sebgruppe4.KinoTicketRes.model.User;
import de.wi2020sebgruppe4.KinoTicketRes.model.UserRegistrationObject;
import de.wi2020sebgruppe4.KinoTicketRes.repositories.UserRepository;

@SpringBootTest
@TestPropertySource(locations="classpath:test.properties")
@AutoConfigureMockMvc
public class UserControllerTest {
	
	MockMvc mvc;
	
	@MockBean
	UserRepository repo;
	
	@Autowired
    WebApplicationContext wac;
	
	JacksonTester<User> jt;
	JacksonTester<UserRegistrationObject> jturo;
	
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
	
	User getUser() {
		User u = new User("Username", "Name", "FirstName", "email@email.com", "password");
		u.setId(uuid);
		return u;
	}
	
	Optional<User> getOptionalUser() {
		User u = getUser();
		return Optional.of(u);
	}
	
	@Test
	void testGetAll() throws Exception {
		when(repo.findAll()).thenReturn(new ArrayList<User>());
		mvc.perform(get("/users"))
				.andExpect(status().isOk());
	}
	
	@Test 
	void testGetById() throws Exception {
		when(repo.findById(uuid)).thenReturn(getOptionalUser());
		MockHttpServletResponse response = mvc.perform(get("/users/" + uuid)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn().getResponse();
		assertEquals(jt.write(getUser()).getJson(), response.getContentAsString());
	}
	
	@Test
	void testGetByIdException() throws Exception {
		when(repo.findById(new UUID(0, 0))).thenReturn(getOptionalUser());
		MockHttpServletResponse response = mvc.perform(get("/users/" + uuid)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andReturn().getResponse();
	}
	
	@Test
	void testGetUsersReviews() throws Exception {
		when(repo.findById(uuid)).thenReturn(getOptionalUser());
		MockHttpServletResponse response = mvc.perform(get("/users/" + uuid + "/reviews")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn().getResponse();
	}
	
	@Test
	void testGetUsersReviewsException() throws Exception {
		when(repo.findById(new UUID(0, 0))).thenReturn(getOptionalUser());
		MockHttpServletResponse response = mvc.perform(get("/users/" + uuid + "/reviews")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andReturn().getResponse();
	}
	
	@Test
	void testGetUsersTickets() throws Exception {
		when(repo.findById(uuid)).thenReturn(getOptionalUser());
		MockHttpServletResponse response = mvc.perform(get("/users/" + uuid + "/tickets")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn().getResponse();
	}
	
	@Test
	void testGetUsersTicketsException() throws Exception {
		when(repo.findById(new UUID(0, 0))).thenReturn(getOptionalUser());
		MockHttpServletResponse response = mvc.perform(get("/users/" + uuid + "/tickets")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andReturn().getResponse();
	}
	
	@Test
	void testRegisterUser() throws Exception {
		mvc.perform(put("/users/register")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jturo.write(new UserRegistrationObject("Username", "Name", "FirstName", "email@email.com", "password", "password")).getJson()))
				.andExpect(status().isCreated());
	}
	
	@Test
	void testRegisterUserWrongPw() throws Exception {
		mvc.perform(put("/users/register")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jturo.write(new UserRegistrationObject("Username", "Name", "FirstName", "email@email.com", "password", "WRONGpassword")).getJson()))
				.andExpect(status().isNotAcceptable());
	}
	
	@Test
	void testRegisterUserUsernameAlreadyTaken() throws Exception {
		when(repo.findById(uuid)).thenReturn(getOptionalUser());
		when(repo.findByuserName("Username")).thenReturn(getOptionalUser());
		
		mvc.perform(put("/users/register")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jturo.write(new UserRegistrationObject("Username", "Name", "FirstName", "email@email.com", "password", "password")).getJson()))
				.andExpect(status().isNotAcceptable());
	}
	
	@Test
	void testRegisterUserEmailAlreadyTaken() throws Exception {
		when(repo.findById(uuid)).thenReturn(getOptionalUser());
		when(repo.findByemail("email@email.com")).thenReturn(getOptionalUser());
		
		mvc.perform(put("/users/register")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jturo.write(new UserRegistrationObject("Username", "Name", "FirstName", "email@email.com", "password", "password")).getJson()))
				.andExpect(status().isNotAcceptable());
	}
	
	@Test
	void testSetAdmin() throws Exception {
		when(repo.findById(uuid)).thenReturn(getOptionalUser());
		mvc.perform(put("/users/setAdmin/"+uuid))
		.andExpect(status().isOk());
	}
	
	@Test
	void testSetAdminException() throws Exception {
		when(repo.findById(new UUID(0, 0))).thenReturn(getOptionalUser());
		mvc.perform(put("/users/setAdmin/"+uuid))
		.andExpect(status().isNotFound());
	}
	
	@Test
	void testLogin() throws Exception {
		when(repo.findById(uuid)).thenReturn(getOptionalUser());
		when(repo.findByemail("email@email.com")).thenReturn(getOptionalUser());
		when(repo.findByuserName("Username")).thenReturn(getOptionalUser());
		mvc.perform(put("/users/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jt.write(getUser()).getJson()))
				.andExpect(status().isAccepted());
	}
	
	@Test
	void testLoginException() throws Exception {
		when(repo.findById(uuid)).thenReturn(getOptionalUser());
		when(repo.findByemail("email@email.com")).thenReturn(getOptionalUser());
		when(repo.findByuserName("Username")).thenReturn(getOptionalUser());
		mvc.perform(put("/users/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jt.write(new User("Username", "Name", "FirstName", null, "password")).getJson()))
				.andExpect(status().isAccepted());
	}
	
	@Test
	void testLoginException2() throws Exception {
		when(repo.findById(uuid)).thenReturn(getOptionalUser());
		when(repo.findByemail("email@email.com")).thenReturn(getOptionalUser());
		when(repo.findByuserName("Username")).thenReturn(getOptionalUser());
		mvc.perform(put("/users/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jt.write(new User(null, "Name", "FirstName",null, "password")).getJson()))
				.andExpect(status().isNotAcceptable());
	}
	
	@Test
	void testLoginException3() throws Exception {
		when(repo.findById(uuid)).thenReturn(getOptionalUser());
		when(repo.findByemail("email@email.com")).thenReturn(getOptionalUser());
		when(repo.findByuserName("Username")).thenReturn(getOptionalUser());
		mvc.perform(put("/users/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jt.write(new User(null, "Name", "FirstName", "emailfalse@email.com", "password")).getJson()))
				.andExpect(status().isNotFound());
	}
	
	@Test
	void testLoginException4() throws Exception {
		when(repo.findById(uuid)).thenReturn(getOptionalUser());
		when(repo.findByemail("email@email.com")).thenReturn(getOptionalUser());
		when(repo.findByuserName("Username")).thenReturn(getOptionalUser());
		mvc.perform(put("/users/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jt.write(new User("Username", "Name", "FirstName", "email@email.com", "WRONGpassword")).getJson()))
				.andExpect(status().isUnauthorized());
	}

	
	@Test
	void testLoginException5() throws Exception {
		when(repo.findById(uuid)).thenReturn(getOptionalUser());
		when(repo.findByemail("email@email.com")).thenReturn(getOptionalUser());
		when(repo.findByuserName("Username")).thenReturn(getOptionalUser());
		mvc.perform(put("/users/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jt.write(new User("wrongusername", "Name", "FirstName", null, "password")).getJson()))
				.andExpect(status().isNotFound());
	}
	
	@Test
	void testAddUser() throws Exception {
		mvc.perform(put("/users/add")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jt.write(getUser()).getJson()))
				.andExpect(status().isCreated());
	}
	
	@Test
	void testDeleteUser() throws Exception {
		when(repo.findById(uuid)).thenReturn(getOptionalUser());
		mvc.perform(delete("/users/"+uuid))
				.andExpect(status().isOk());
	}
	
	@Test
	void testDeleteUserException() throws Exception {
		when(repo.findById(new UUID(0, 0))).thenReturn(getOptionalUser());
		mvc.perform(delete("/users/"+uuid))
				.andExpect(status().isNotFound());
	}
	
}
