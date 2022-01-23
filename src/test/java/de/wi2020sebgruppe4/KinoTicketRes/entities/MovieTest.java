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

import de.wi2020sebgruppe4.KinoTicketRes.model.Layout;
import de.wi2020sebgruppe4.KinoTicketRes.model.Movie;

@SpringBootTest
@TestPropertySource(locations="classpath:test.properties")
public class MovieTest {
	static UUID uuid;
	
	@BeforeAll
	static void beforeAll() {
		uuid = new UUID(2,2);
	}
    
    @Test
    @DisplayName("constructor test")
    public void constructorTest() {
        Date d = new Date(2015-03-31);
        Movie m = new Movie("Star Wars", "German", 120, "George Lucas", "Weltraum ist cool", d, "https://picute.link", "https://trailer-link", false, 12);
        assertEquals("Star Wars", m.getTitel());
        assertEquals("German", m.getLanguage());
        assertEquals(120, m.getDuration());
        assertEquals("George Lucas", m.getDirector());
        assertEquals("Weltraum ist cool", m.getDescription());
        assertEquals(d, m.getRelease());
        assertEquals("https://picute.link", m.getPictureLink());
        assertEquals("https://trailer-link", m.getTrailerLink());
        assertEquals(false, m.isAvailableIn3D());
        assertEquals(12, m.getFSK());
        
    }

    @Test
    @DisplayName("getter/setter Test")
    public void getterSetterTest() {
        Movie m = new Movie();
        Date d = new Date(2015-03-31);
        m.setTitel("Star Wars");
        m.setLanguage("German");
        m.setDuration(120);
        m.setDirector("George Lucas");
        m.setDescription("Weltraum ist cool");
        m.setRelease(d);
        m.setPictureLink("https://picute.link");
        m.setTrailerLink("https://trailer-link");
        m.setAvailableIn3D(true);
        m.setFSK(12);
        m.setId(uuid);

        assertEquals("Star Wars", m.getTitel());
        assertEquals("German", m.getLanguage());
        assertEquals(120, m.getDuration());
        assertEquals("George Lucas", m.getDirector());
        assertEquals("Weltraum ist cool", m.getDescription());
        assertEquals(d, m.getRelease());
        assertEquals("https://picute.link", m.getPictureLink());
        assertEquals("https://trailer-link", m.getTrailerLink());
        assertEquals(true, m.isAvailableIn3D());
        assertEquals(12, m.getFSK());
        assertEquals(uuid, m.getId());
    }

    @Test
    @DisplayName("Test hashCode")
    public void hashCodeTest() {
        Date d = new Date(2015-03-31);
        Movie m = new Movie("Star Wars", "German", 120, "George Lucas", "Weltraum ist cool", d, "https://picute.link", "https://trailer-link", false, 12);
        Movie m2 = new Movie("Star Wars", "German", 120, "George Lucas", "Weltraum ist cool", d, "https://picute.link", "https://trailer-link", false, 12);
        assertEquals(m.hashCode(), m2.hashCode());
        assertEquals(m.equals(m2), true);
        Movie m3 = new Movie("Star Wars", "German", 120, "George Lucas", null, d, "https://picute.link", "https://trailer-link", false, 12);
        Movie m4 = new Movie("Star Wars", "German", 120, null, "Weltraum ist cool", d, "https://picute.link", "https://trailer-link", false, 12);
        assertNotEquals(m.hashCode(), m3.hashCode());
        assertNotEquals(m.hashCode(), m4.hashCode());
        Movie m11 = new Movie("Star Wars", "German", 120, "George Lucas", null, d, "https://picute.link", "https://trailer-link", false, 12);
        m11.setId(uuid);
        assertNotEquals(m.hashCode(), m11.hashCode());
        Movie m5 = new Movie("Star Wars", "German", 120, "George Lucas", null, d, "https://picute.link", "https://trailer-link", true, 12);
        assertNotEquals(m.hashCode(), m5.hashCode());
        Movie m6 = new Movie("Star Wars", "Spain but the S is silent", 120, "George Lucas", null, d, "https://picute.link", "https://trailer-link", true, 12);
        assertNotEquals(m.hashCode(), m6.hashCode());
        Movie m7 = new Movie("Star Wars", null, 120, "George Lucas", null, d, null, "https://trailer-link", true, 12);
        assertNotEquals(m.hashCode(), m7.hashCode());
        Movie m8 = new Movie("Star Wars", "Spain but the S is silent", 120, "George Lucas", null, null, "https://picute.link", "https://trailer-link", true, 12);
        assertNotEquals(m.hashCode(), m8.hashCode());
        Movie m9 = new Movie(null, "Spain but the S is silent", 120, "George Lucas", null, d, "https://picute.link", "https://trailer-link", true, 12);
        assertNotEquals(m.hashCode(), m9.hashCode());
        Movie m10 = new Movie(null, "Spain but the S is silent", 120, "George Lucas", null, d, "https://picute.link", null, true, 12);
        assertNotEquals(m.hashCode(), m10.hashCode());
    }
    
    @Test
    @DisplayName("Test equals")
    public void equalsTest() {
    	Movie m = new Movie("Star Wars", "German", 120, "George Lucas", "Weltraum ist cool", null, "https://picute.link", "https://trailer-link", false, 12);
    	Movie m2 = new Movie("Star Wars", "German", 120, "George Lucas", "Weltraum ist cool", null, "https://picute.link", "https://trailer-link", false, 12);
    	Layout l = new Layout();
    	Movie m3 = new Movie("Star Wars", "German", 120, "George Lucas", "Weltraum ist cool", null, "https://picute.link", "https://trailer-link", false, 10);
    	// compare m4 to m5 to get if (other.description != null) then compare m to m5
    	Movie m4 = new Movie("Star Wars", "German", 120, "George Lucas", null, null, "https://picute.link", "https://trailer-link", false, 12);
    	Movie m24 = new Movie("Star Wars", "German", 120, "George Lucas", null, null, "https://picute.link", "https://trailer-link", false, 12);
    	Movie m5 = new Movie("Star Wars", "German", 120, "George Lucas", "Weltraum ist nicht cool", null, "https://picute.link", "https://trailer-link", false, 12);
    	// compare m6 and m7 to get if (other.director != null) then compare m to m7
    	Movie m6 = new Movie("Star Wars", "German", 120, null, "Weltraum ist cool", null, "https://picute.link", "https://trailer-link", false, 12);
    	Movie m25 = new Movie("Star Wars", "German", 120, null, "Weltraum ist cool", null, "https://picute.link", "https://trailer-link", false, 12);
    	Movie m7 = new Movie("Star Wars", "German", 120, "not George Lucas", "Weltraum ist cool", null, "https://picute.link", "https://trailer-link", false, 12);
    	//compare for duration
    	Movie m8 = new Movie("Star Wars", "German", 20, "George Lucas", "Weltraum ist cool", null, "https://picute.link", "https://trailer-link", false, 12);
    	//compare m9 to m to get ID then compare m9 to m10
    	Movie m9 = new Movie("Star Wars", "German", 120, "George Lucas", "Weltraum ist cool", null, "https://picute.link", "https://trailer-link", false, 12);
    	m9.setId(uuid);
    	Movie m30 = new Movie("Star Wars", "German", 120, "George Lucas", "Weltraum ist cool", null, "https://picute.link", "https://trailer-link", false, 12);
    	m30.setId(uuid);
    	Movie m23 = new Movie("Star Wars", "German", 120, "George Lucas", "Weltraum ist cool", null, "https://picute.link", "https://trailer-link", false, 12);
    	Movie m10 = new Movie("Star Wars", "German", 120, "George Lucas", "Weltraum ist cool", null, "https://picute.link", "https://trailer-link", false, 12);
    	m10.setId(new UUID(2,3));
    	// compare isAviableIn3D
    	Movie m11 = new Movie("Star Wars", "German", 120, "George Lucas", "Weltraum ist cool", null, "https://picute.link", "https://trailer-link", true, 12);
    	// compare m12 to m13 to get if (other.language != null) then compare m to m13
    	Movie m12 = new Movie("Star Wars", null, 120, "George Lucas", "Weltraum ist cool", null, "https://picute.link", "https://trailer-link", false, 12);
    	Movie m26 = new Movie("Star Wars", null, 120, "George Lucas", "Weltraum ist cool", null, "https://picute.link", "https://trailer-link", false, 12);
    	Movie m13 = new Movie("Star Wars", "English", 120, "George Lucas", "Weltraum ist cool", null, "https://picute.link", "https://trailer-link", false, 12);
    	// compare m14 to m15 to get if (other.pictureLink != null) then compare m to m15
    	Movie m14 = new Movie("Star Wars", "German", 120, "George Lucas", "Weltraum ist cool", null, null, "https://trailer-link", false, 12);
    	Movie m27 = new Movie("Star Wars", "German", 120, "George Lucas", "Weltraum ist cool", null, null, "https://trailer-link", false, 12);
    	Movie m15 = new Movie("Star Wars", "German", 120, "George Lucas", "Weltraum ist cool", null, "https://picute.link2", "https://trailer-link", false, 12);
    	// compare m16 to m18 to get (!release.equals(other.release)) then compare m17 to m18
    	Movie m16 = new Movie("Star Wars", "German", 120, "George Lucas", "Weltraum ist cool", null, "https://picute.link", "https://trailer-link", false, 12);
    	Movie m17 = new Movie("Star Wars", "German", 120, "George Lucas", "Weltraum ist cool", new Date(2015-03-31), "https://picute.link", "https://trailer-link", false, 12);
    	Movie m18 = new Movie("Star Wars", "German", 120, "George Lucas", "Weltraum ist cool", new Date(2015-03-32), "https://picute.link", "https://trailer-link", false, 12);
    	// compare m19 to m20 to get if (other.titel != null) then compare m to m20
    	Movie m19 = new Movie(null, "German", 120, "George Lucas", "Weltraum ist cool", null, "https://picute.link", "https://trailer-link", false, 12);
    	Movie m28 = new Movie(null, "German", 120, "George Lucas", "Weltraum ist cool", null, "https://picute.link", "https://trailer-link", false, 12);
    	Movie m20 = new Movie("Barbie", "German", 120, "George Lucas", "Weltraum ist cool", null, "https://picute.link", "https://trailer-link", false, 12);
    	// compare m21 to m22 to get if (other.trailerLink != null) then compare m to m22
    	Movie m21 = new Movie("Star Wars", "German", 120, "George Lucas", "Weltraum ist cool", null, "https://picute.link", null, false, 12);
    	Movie m29 = new Movie("Star Wars", "German", 120, "George Lucas", "Weltraum ist cool", null, "https://picute.link", null, false, 12);
    	Movie m22 = new Movie("Star Wars", "German", 120, "George Lucas", "Weltraum ist cool", null, "https://picute.link", "https://trailer-link2", false, 12);
    	
    	//run equals - methods
    	assertEquals(true, m.equals(m));
    	assertEquals(false, m.equals(null));
    	assertEquals(false, m.equals(l));
    	assertEquals(false, m.equals(m3));
    	assertEquals(false, m4.equals(m5));
    	assertEquals(true, m4.equals(m24));
    	assertEquals(false, m.equals(m5));
    	assertEquals(false, m6.equals(m7));
    	assertEquals(true, m6.equals(m25));
    	assertEquals(false, m.equals(m7));
    	assertEquals(false, m.equals(m8));
    	assertEquals(false, m9.equals(m));
    	assertEquals(true, m9.equals(m30));
    	assertEquals(true, m9.equals(m9));
    	assertEquals(false, m9.equals(m10));
    	assertEquals(false, m.equals(m9));
    	assertEquals(false, m.equals(m11));
    	assertEquals(false, m12.equals(m13));
    	assertEquals(true, m12.equals(m26));
    	assertEquals(false, m.equals(m13));
    	assertEquals(false, m14.equals(m15));
    	assertEquals(true, m14.equals(m27));
    	assertEquals(false, m.equals(m15));
    	assertEquals(false, m16.equals(m18));
    	assertEquals(false, m17.equals(m18));
    	assertEquals(false, m19.equals(m20));
    	assertEquals(true, m19.equals(m28));
    	assertEquals(false, m.equals(m20));
    	assertEquals(false, m21.equals(m22));
    	assertEquals(true, m21.equals(m29));
    	assertEquals(false, m.equals(m22));
    	assertEquals(true, m.equals(m2));
    }
}

