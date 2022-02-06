package de.wi2020sebgruppe4.KinoTicketRes;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(locations="classpath:test.properties")
class KinoTicketResApplicationTests {
	
	@Test
	void mainTest() {
		KinoTicketResApplication.main(new String[] {}); //not working :(
	}

}
