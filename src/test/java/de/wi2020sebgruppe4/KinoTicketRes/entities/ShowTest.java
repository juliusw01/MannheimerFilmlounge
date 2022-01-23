package de.wi2020sebgruppe4.KinoTicketRes.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import de.wi2020sebgruppe4.KinoTicketRes.model.Layout;
import de.wi2020sebgruppe4.KinoTicketRes.model.Movie;
import de.wi2020sebgruppe4.KinoTicketRes.model.Room;
import de.wi2020sebgruppe4.KinoTicketRes.model.Seat;
import de.wi2020sebgruppe4.KinoTicketRes.model.Show;

@SpringBootTest
@TestPropertySource(locations="classpath:test.properties")
public class ShowTest {
	static UUID uuid;
	static Date date;
	static Time time;
	static Movie movie;
	static Room room;
	static List<Seat> seatList;

	@BeforeAll
	static void beforeAll() {
		Layout dummyLayout = new Layout();
		Show dummyShow = new Show();
    	Seat s = new Seat(1, 2, false, true, dummyLayout, dummyShow);
		uuid = new UUID(2, 2);
		date = new Date(2015-03-31);
		time = new Time(02-50-21);
		movie = new Movie();
		room = new Room();
		seatList = new ArrayList<>();
		seatList.add(new Seat());
		seatList.add(s);
	}
	
    @Test
    @DisplayName("constructor test")
    public void constructorTest() {
    	
    	Show show = new Show(date, time, movie, room, seatList);
       
    	assertEquals(date, show.getShowDate());
    	assertEquals(time, show.getStartTime());
    	assertEquals(movie, show.getMovie());
    	assertEquals(room, show.getRoom());

    }

    @Test
    @DisplayName("getter/setter Test")
    public void getterSetterTest() {
    	Show show = new Show();
    	
    	show.setId(uuid);
    	show.setShowDate(date);
    	show.setStartTime(time);
    	show.setMovie(movie);
    	show.setRoom(room);
    	
    	assertEquals(date, show.getShowDate());
    	assertEquals(time, show.getStartTime());
    	assertEquals(movie, show.getMovie());
    	assertEquals(room, show.getRoom());
    	assertEquals(uuid, show.getId());
    }

    @Test
    @DisplayName("Test hashCode")
    public void hashCodeTest() {
    	Show show = new Show(date, time, movie, room, seatList);
    	Show show3 = new Show(date, time, movie, room, seatList);
    	show.setId(uuid);
    	show3.setId(uuid);
    	Show show2 = new Show();
    	
    	assertNotEquals(show.hashCode(), show2.hashCode());
    	assertEquals(show.hashCode(), show3.hashCode());
    }
    
    @Test
    @DisplayName("Test equals")
    public void equalsTest() {
    	Layout dummyLayout = new Layout();
		Show dummyShow = new Show();
    	Seat s = new Seat(2, 2, false, false, dummyLayout, dummyShow);
    	List<Seat> seatList2 = new ArrayList<>();
    	seatList2.add(s);
    	Layout l = new Layout();
    	Date d = new Date(2015-03-31);
        Movie m = new Movie("Star Wars", "German", 120, "George Lucas", "Weltraum ist cool", d, "https://picute.link", "https://trailer-link", false, 12);
    	Show show = new Show(date, time, movie, room, seatList);
    	Show show2 = new Show(date, time, movie, room, seatList);
    	Show show3 = new Show(date, time, movie, room, seatList);
    	show3.setId(uuid);
    	Show show4 = new Show(date, time, movie, room, seatList);
    	show4.setId(new UUID(2, 3));
    	Show show5 = new Show(date, time, null, room, seatList);
    	Show show6 = new Show(date, time, null, room, seatList);
    	Show show7 = new Show(date, time, m, room, seatList);
    	Show show8 = new Show(date, time, movie, null, seatList);
    	Show show9 = new Show(date, time, movie, null, seatList);
    	Show show10 = new Show(date, time, movie, new Room(true, l), seatList);
    	Show show11 = new Show(date, time, movie, room, null);
    	Show show12 = new Show(date, time, movie, room, null);
    	Show show14 = new Show(null, time, movie, room, seatList);
    	Show show15 = new Show(null, time, movie, room, seatList);
    	Show show16 = new Show(new Date(2017-03-31), time, movie, room, seatList);
    	Show show17 = new Show(date, null, movie, room, seatList);
    	Show show18 = new Show(date, null, movie, room, seatList);
    	Show show19 = new Show(date, new Time(05-50-21), movie, room, seatList);
    	assertEquals(true, show.equals(show));
    	assertEquals(false, show.equals(null));
    	assertEquals(false, show.equals(date));
    	assertEquals(false, show3.equals(show));
    	assertEquals(false, show.equals(show3));
    	assertEquals(false, show3.equals(show4));
    	show3.setId(new UUID(2, 3));
    	assertEquals(true, show3.equals(show4));
    	assertEquals(false, show5.equals(show));
    	assertEquals(true, show5.equals(show6));
    	assertEquals(false, show.equals(show7));
    	assertEquals(false, show8.equals(show));
    	assertEquals(true, show8.equals(show9));
    	assertEquals(false, show.equals(show10));
    	assertEquals(true, show11.equals(show));
    	assertEquals(true, show11.equals(show12));
    	assertEquals(false, show14.equals(show));
    	assertEquals(true, show14.equals(show15));
    	assertEquals(false, show.equals(show16));
    	assertEquals(false, show17.equals(show));
    	assertEquals(true, show17.equals(show18));
    	assertEquals(false, show.equals(show19));
    	assertEquals(true, show.equals(show2));
    	
    }
}
