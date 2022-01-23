package de.wi2020sebgruppe4.KinoTicketRes.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.sql.Date;
import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import de.wi2020sebgruppe4.KinoTicketRes.model.Movie;
import de.wi2020sebgruppe4.KinoTicketRes.model.Review;
import de.wi2020sebgruppe4.KinoTicketRes.model.User;

@SpringBootTest
@TestPropertySource(locations="classpath:test.properties")
public class ReviewTest {
	static UUID uuid;
	static User user;
	static Movie movie;
	static java.sql.Date date;
	
	@BeforeAll
	static void beforeAll() {
		uuid = new UUID(2,2);
		user = new User();
		movie = new Movie();
		date = new Date(2015-03-31);
		
		user.setId(uuid);
		movie.setId(uuid);
	}
	
    @Test
    @DisplayName("constructor test")
    public void constructorTest() {
        Review review = new Review("Titel", "content", user, movie, date, 5);
        
        assertEquals("Titel", review.getTitel());
        assertEquals("content", review.getContent());
        assertEquals(user, review.getUser());
        assertEquals(movie, review.getMovie());
        assertEquals(date, review.getDate());
        assertEquals(5, review.getStars());
    }

    @Test
    @DisplayName("getter/setter Test")
    public void getterSetterTest() {
	    Review review = new Review();
	   
	    review.setId(uuid);
	    review.setTitel("Titel");
	    review.setContent("content");
	    review.setUser(user);
	    review.setMovie(movie);
	    review.setDate(date);
	    review.setStars(5);
	  
	    assertEquals("Titel", review.getTitel());
	    assertEquals("content", review.getContent());
	    assertEquals(user, review.getUser());
	    assertEquals(movie, review.getMovie());
	    assertEquals(date, review.getDate());
	    assertEquals(uuid, review.getId());    
	    assertEquals(5, review.getStars());
    }

    @Test
    @DisplayName("Test hashCode")
    public void hashCodeTest() {
    	Review review = new Review("Titel", "content", user, movie, date, 5);
    	Review review2 = new Review("Titel", "content", user, movie, date, 5);
        Review review3 = new Review();
        review3.setId(uuid);
        assertEquals(review.hashCode(), review2.hashCode());
        assertNotEquals(review.hashCode(), review3.hashCode());
    }
    
    @Test
    @DisplayName("Test equals")
    public void equalsTest() {
    	Review review = new Review("Titel", "content", user, movie, date, 5);
    	Review review2 = new Review("Titel", "content", user, movie, date, 5);
    	Review review3 = new Review("Titel", null, user, movie, date, 5);
    	Review review4 = new Review("Titel", null, user, movie, date, 5);
    	Review review5 = new Review("Titel", "OtherContent", user, movie, date, 5);
    	Review review6 = new Review("Titel", "content", user, movie, null, 5);
    	Review review7 = new Review("Titel", "content", user, movie, null, 5);
    	Review review8 = new Review("Titel", "content", user, movie, new Date(2017-03-31), 5);
    	Review review9 = new Review("Titel", "content", user, movie, date, 5);
    	review9.setId(uuid);
    	Review review10 = new Review("Titel", "content", user, movie, date, 5);
    	review10.setId(uuid);
    	Review review11 = new Review("Titel", "content", user, null, date, 5);
    	Review review12 = new Review("Titel", "content", user, null, date, 5);
    	Review review13 = new Review("Titel", "content", user, new Movie(), date, 5);
    	Review review14 = new Review("Titel", "content", user, movie, date, 3);
    	Review review15 = new Review(null, "content", user, movie, date, 5);
    	Review review16 = new Review(null, "content", user, movie, date, 5);
    	Review review17 = new Review("OtherTitel", "content", user, movie, date, 5);
    	Review review18 = new Review("Titel", "content", null, movie, date, 5);
    	Review review19 = new Review("Titel", "content", null, movie, date, 5);
    	Review review20 = new Review("Titel", "content", new User(), movie, date, 5);
    	
    	assertEquals(true, review.equals(review));
    	assertEquals(false, review.equals(null));
    	assertEquals(false, review.equals(user));
    	assertEquals(false, review3.equals(review));
    	assertEquals(true, review3.equals(review4));
    	assertEquals(false, review.equals(review5));
    	assertEquals(false, review6.equals(review));
    	assertEquals(true, review6.equals(review7));
    	assertEquals(false, review.equals(review8));
    	assertEquals(false, review.equals(review9));
    	assertEquals(true, review9.equals(review10));
    	review10.setId(new UUID(2, 3));
    	assertEquals(false, review9.equals(review10));
    	assertEquals(false, review11.equals(review));
    	assertEquals(true, review11.equals(review12));
    	assertEquals(false, review.equals(review13));
    	assertEquals(false, review.equals(review14));
    	assertEquals(false, review15.equals(review));
    	assertEquals(true, review15.equals(review16));
    	assertEquals(false, review.equals(review17));
    	assertEquals(false, review18.equals(review));
    	assertEquals(true, review18.equals(review19));
    	assertEquals(false, review.equals(review20));
    	assertEquals(true, review.equals(review2));
    }
}
