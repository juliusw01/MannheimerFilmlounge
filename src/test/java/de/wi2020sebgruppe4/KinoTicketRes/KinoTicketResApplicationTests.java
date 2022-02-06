package de.wi2020sebgruppe4.KinoTicketRes;

import java.io.IOException;

import javax.servlet.ServletException;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import de.wi2020sebgruppe4.KinoTicketRes.config.SecurityConfig;

@SpringBootTest
@TestPropertySource(locations="classpath:test.properties")
class KinoTicketResApplicationTests {
	
	@Test
	void mainTest() {
		KinoTicketResApplication.main(new String[] {"localhost"});
	}
	
	/*
	@Test
	void testSecurityConfig() {
		SecurityConfig s = new SecurityConfig();
		try {
			s.doFilter(null, null, null);
		} catch (IOException | ServletException e) {
			e.printStackTrace();
		}
	}
	*/
}
