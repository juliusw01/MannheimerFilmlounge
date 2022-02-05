package de.wi2020sebgruppe4.KinoTicketRes.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.wi2020sebgruppe4.KinoTicketRes.model.Layout;
import de.wi2020sebgruppe4.KinoTicketRes.model.Movie;
import de.wi2020sebgruppe4.KinoTicketRes.model.Room;
import de.wi2020sebgruppe4.KinoTicketRes.model.Seat;
import de.wi2020sebgruppe4.KinoTicketRes.model.SeatRequestObject;
import de.wi2020sebgruppe4.KinoTicketRes.model.Show;
import de.wi2020sebgruppe4.KinoTicketRes.model.ShowRequestObject;
import de.wi2020sebgruppe4.KinoTicketRes.model.Ticket;
import de.wi2020sebgruppe4.KinoTicketRes.repositories.LayoutRepository;
import de.wi2020sebgruppe4.KinoTicketRes.repositories.MovieRepository;
import de.wi2020sebgruppe4.KinoTicketRes.repositories.RoomRepository;
import de.wi2020sebgruppe4.KinoTicketRes.repositories.SeatRepository;
import de.wi2020sebgruppe4.KinoTicketRes.repositories.ShowRepository;
import de.wi2020sebgruppe4.KinoTicketRes.repositories.TicketRepository;

@Controller
@RestController
@CrossOrigin(origins = 
{"https://kinoticketres.web.app",
	"https://localhost/",
	"https://localhost:3000/",
	"https://localhost:3001/",
	"https://localhost:3002/",
	"http://localhost/",
	"http://localhost:3000/",
	"http://localhost:3001/",
	"http://localhost:3002/",
	"http://localhost:4200/"})
@RequestMapping("/shows")
public class ShowController {
	
	@Autowired
	ShowRepository repo;
	
	@Autowired
	MovieRepository movieRepository;
	
	@Autowired
	RoomRepository roomRepository;
	
	@Autowired
	LayoutRepository layoutRepository;
	
	@Autowired
	SeatRepository seatRepository;
	
	@Autowired
	TicketRepository ticketRepository;
	
	@PutMapping("/add")
	public ResponseEntity<Object> addShow(@RequestBody ShowRequestObject sro){
		
		Show toAdd = new Show();
		toAdd.setStartTime(sro.start);
		toAdd.setShowDate(sro.date);
		
		if(sro.movieID != null) {
			Optional<Movie> movieSearch = movieRepository.findById(sro.movieID);
			try {
				Movie movie = movieSearch.get();
				toAdd.setMovie(movie);
			}
			catch(NoSuchElementException e)
			{
				return new ResponseEntity<Object>("",
						HttpStatus.NOT_FOUND);
			}
		}
		
		if(sro.roomID != null) {
			Optional<Room> roomSearch = roomRepository.findById(sro.roomID);
			try {
				Room room = roomSearch.get();
				toAdd.setRoom(room);
				
				Layout layout = room.getLayout();
				int seatsPerRow = layout.getTotalSeats() / layout.getRowCount();
				List<Seat> showSeats = new ArrayList<>();
				
				for(int i = 1; i <= layout.getRowCount(); i++) {
					for(int j = 1; j <= seatsPerRow; j++) {
						Seat newSeat = new Seat(i, j, false, false, false, layout, toAdd);
//						seatRepository.save(newSeat);
						showSeats.add(newSeat);
					}
				}
				seatRepository.saveAll(showSeats);
			}
			catch(NoSuchElementException e)
			{
				return new ResponseEntity<Object>("Layout or Room not found",
						HttpStatus.NOT_FOUND);
			}
			catch(Exception e) {
				e.printStackTrace();
				return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
			}
		}
		return new ResponseEntity<Object>(repo.save(toAdd), HttpStatus.CREATED);
		
	}
	
	@PutMapping("/bookSeat")
	public ResponseEntity<Object> blockSeat(@RequestBody SeatRequestObject sro){
		UUID seatID = sro.seatID;
		Seat toBook = new Seat();
		Optional<Seat> seat = seatRepository.findById(seatID);
		try {
			toBook = seat.get();
		} catch (NoSuchElementException e) {
			return new ResponseEntity<Object>("Seat " + seatID + " not found!", HttpStatus.NOT_FOUND);
		}
		
		if(sro.block == true && sro.deblock == false) {
			toBook.setBlocked(true);
		}else if(sro.block == false && sro.deblock == true) {
			toBook.setBlocked(false);
		}else {
			return new ResponseEntity<Object>("bock and deblock must have different values!", HttpStatus.NOT_ACCEPTABLE);
		}
		
		return new ResponseEntity<Object>(seatRepository.save(toBook), HttpStatus.OK);
	}
	
	@PutMapping("/cancel/{id}")
	public ResponseEntity<Object> cancelShow(@PathVariable UUID id){
		Show show = new Show();
		try {
			show = repo.findById(id).get();
			try {
				List<Ticket> ticketList = ticketRepository.findAllByShow(show).get();
				for(Ticket t: ticketList) {
					t.setCanceled(true);
				}
				ticketRepository.saveAll(ticketList);
			} catch (NoSuchElementException e) {
				return new ResponseEntity<Object>("Tickets for show "+ id +" not found!", HttpStatus.NOT_FOUND);
			}
			show.setCanceled(true);
			repo.save(show);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<Object>("Show "+ id +" not found!", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Object>("Show and corresponding tickets cancelled", HttpStatus.OK);
	}
	
	@PutMapping("/uncancel/{id}")
	public ResponseEntity<Object> uncancelTicket(@PathVariable UUID id) {
		Show show = new Show();
		try {
			show = repo.findById(id).get();
			try {
				List<Ticket> ticketList = ticketRepository.findAllByShow(show).get();
				for(Ticket t: ticketList) {
					t.setCanceled(false);
				}
				ticketRepository.saveAll(ticketList);
			} catch (NoSuchElementException e) {
				return new ResponseEntity<Object>("Tickets for show "+ id +" not found!", HttpStatus.NOT_FOUND);
			}
			show.setCanceled(false);
			repo.save(show);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<Object>("Show "+ id +" not found!", HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Object>("Show and corresponding tickets uncancelled", HttpStatus.OK);
	}
	
	@GetMapping("")
	public ResponseEntity<Object> getAll(){
		return new ResponseEntity<Object>(repo.findAll(), HttpStatus.OK);
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<Object> getSpecific(@PathVariable UUID id){
		
		Optional<Show> toSearch = repo.findById(id);
		try {
			return new ResponseEntity<Object>(toSearch.get(), HttpStatus.OK);
		}
		catch(NoSuchElementException e)
		{
			return new ResponseEntity<Object>("Show "+id+" not found!",
					HttpStatus.NOT_FOUND);
		}

	}
	
	
	@GetMapping("/{id}/seats")
	public ResponseEntity<Object> getSeatsForShow(@PathVariable UUID id){
		
		Optional<Show> showSearch = repo.findById(id);
		try {
			Optional<List<Seat>> seatsSearch = seatRepository.findAllByShow(showSearch.get());
			try {
				return new ResponseEntity<Object>(seatsSearch.get(), HttpStatus.OK);
			}
			catch(NoSuchElementException e)
			{
				return new ResponseEntity<Object>("Seats for Show "+id+" not found!",
						HttpStatus.NOT_FOUND);
			}
		}
		catch(NoSuchElementException e)
		{
			return new ResponseEntity<Object>("Show "+id+" not found!",
					HttpStatus.NOT_FOUND);
		}
	}
	
	
	@Transactional
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteShow(@PathVariable UUID id){
		Optional<Show> o = repo.findById(id);
		try {
			repo.deleteById(o.get().getId());
			return new ResponseEntity<Object>(new String("Show with id \"" + id + "\" deleted!"), HttpStatus.OK);
		}
		catch(NoSuchElementException e)
		{
			return new ResponseEntity<Object>("Show "+id+" not found!",
					HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/all")
	public ResponseEntity<Object> deleteAllShows(){
		repo.deleteAll();
		return new ResponseEntity<Object>("All Shows gone", HttpStatus.OK);
	}

}
