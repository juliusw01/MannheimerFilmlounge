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
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.wi2020sebgruppe4.KinoTicketRes.model.PasswordResetObject;
import de.wi2020sebgruppe4.KinoTicketRes.model.Token;
import de.wi2020sebgruppe4.KinoTicketRes.model.TokenRequestObject;
import de.wi2020sebgruppe4.KinoTicketRes.model.User;
import de.wi2020sebgruppe4.KinoTicketRes.repositories.TokenRepository;
import de.wi2020sebgruppe4.KinoTicketRes.repositories.UserRepository;

@SpringBootTest
@TestPropertySource(locations="classpath:test.properties")
@AutoConfigureMockMvc
public class TokenControllerTest {
	
	MockMvc mvc;
	
	@MockBean
	TokenRepository repo;
	
	@MockBean
	UserRepository userRepository;
	
	@Autowired
    WebApplicationContext wac;
	
	JacksonTester<Token> jt;
	JacksonTester<TokenRequestObject> jtco;
	JacksonTester<PasswordResetObject> jtro;
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
	
	Token getToken() {
		Token t = new Token(true, getUser());
		t.setId(uuid);
		return t;
	}
	
	User getUser() {
		User u = new User("userName", "name", "firstName", "email", "password");
		u.setId(uuid);
		return u;
	}
	
	Optional<Token> getOptionalToken() {
		return Optional.of(getToken());
	}
	
	Optional<User> getOptionalUser() {
		return Optional.of(getUser());
	}
	
	@Test
	void testGetAll() throws Exception {
		when(repo.findAll()).thenReturn(new ArrayList<Token>());
		mvc.perform(get("/tokens"))
				.andExpect(status().isOk());
	}
	
	@Test
	void checkToken() throws Exception {
		when(repo.findById(uuid)).thenReturn(getOptionalToken());
		mvc.perform(get("/tokens/check/"+uuid))
			.andExpect(status().isOk());
	}
	
	@Test
	void checkTokenException() throws Exception {
		when(repo.findById(new UUID(0, 0))).thenReturn(getOptionalToken());
		mvc.perform(get("/tokens/check/"+uuid))
			.andExpect(status().isNotFound());
	}
	
	@Test
	void resetToken() throws Exception {
		when(repo.findById(uuid)).thenReturn(getOptionalToken());
		when(userRepository.findById(uuid)).thenReturn(getOptionalUser());
		
		mvc.perform(put("/tokens/reset")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jtco.write(new TokenRequestObject(true, uuid)).getJson()))
				.andExpect(status().isCreated());
	}
	
	@Test
	void resetTokenNoUser() throws Exception {
		when(repo.findById(uuid)).thenReturn(getOptionalToken());
		when(userRepository.findById(uuid)).thenReturn(getOptionalUser());
		
		mvc.perform(put("/tokens/reset")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jtco.write(new TokenRequestObject(true, null)).getJson()))
				.andExpect(status().isNotAcceptable());
	}
	
	@Test
	void resetTokenWrongUser() throws Exception {
		when(repo.findById(uuid)).thenReturn(getOptionalToken());
		when(userRepository.findById(new UUID(0, 0))).thenReturn(getOptionalUser());
		
		mvc.perform(put("/tokens/reset")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jtco.write(new TokenRequestObject(true, uuid)).getJson()))
				.andExpect(status().isNotFound());
	}
	
	@Test
	void resetWithLink() throws Exception {
		when(repo.findById(uuid)).thenReturn(getOptionalToken());
		when(userRepository.findById(uuid)).thenReturn(getOptionalUser());
		
		mvc.perform(get("/tokens/resetWithLink/"+uuid)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jtro.write(new PasswordResetObject("password", uuid, uuid)).getJson()))
				.andExpect(status().isOk());
	}
	
	@Test
	void resetConfirm() throws Exception {
		when(repo.findById(uuid)).thenReturn(getOptionalToken());
		when(userRepository.findById(uuid)).thenReturn(getOptionalUser());
		
		mvc.perform(put("/tokens/reset/confirm")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jtro.write(new PasswordResetObject("password", uuid, uuid)).getJson()))
				.andExpect(status().isOk());
	}
	
	@Test
	void resetConfirmException() throws Exception {
		when(repo.findById(new UUID(0, 0))).thenReturn(getOptionalToken());
		when(userRepository.findById(uuid)).thenReturn(getOptionalUser());
		
		mvc.perform(put("/tokens/reset/confirm")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jtro.write(new PasswordResetObject("password", uuid, uuid)).getJson()))
				.andExpect(status().isNotFound());
	}
	
	@Test
	void resetConfirmException2() throws Exception {
		when(repo.findById(uuid)).thenReturn(getOptionalToken());
		when(userRepository.findById(new UUID(0, 0))).thenReturn(getOptionalUser());
		
		mvc.perform(put("/tokens/reset/confirm")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jtro.write(new PasswordResetObject("password", uuid, uuid)).getJson()))
				.andExpect(status().isNotFound());
	}
	
	@Test
	void deleteAll() throws Exception {
		mvc.perform(delete("/tokens/deleteAll"))
			.andExpect(status().isOk());
	}
	
}
