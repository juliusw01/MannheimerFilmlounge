package de.wi2020sebgruppe4.KinoTicketRes.rest;

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

import de.wi2020sebgruppe4.KinoTicketRes.SendingTicketsViaMail.JavaMail;
import de.wi2020sebgruppe4.KinoTicketRes.model.Movie;
import de.wi2020sebgruppe4.KinoTicketRes.model.Seat;
import de.wi2020sebgruppe4.KinoTicketRes.model.Ticket;
import de.wi2020sebgruppe4.KinoTicketRes.model.TicketRequestObject;
import de.wi2020sebgruppe4.KinoTicketRes.model.User;
import de.wi2020sebgruppe4.KinoTicketRes.repositories.SeatRepository;
import de.wi2020sebgruppe4.KinoTicketRes.repositories.ShowRepository;
import de.wi2020sebgruppe4.KinoTicketRes.repositories.TicketRepository;
import de.wi2020sebgruppe4.KinoTicketRes.repositories.UserRepository;

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
@RequestMapping("/tickets")
public class TicketController {
	
	@Autowired
	TicketRepository repo;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	SeatRepository seatRepository;
	
	@Autowired
	ShowRepository showRepository;
	
	@Autowired
	JavaMail mail;
	
	@GetMapping("")
	public ResponseEntity<Object> getAll(){
		return new ResponseEntity<Object>(repo.findAll(), HttpStatus.OK);
	}
	
	
	@PutMapping("/add")
	@Transactional
	public ResponseEntity<Object> addTicket(@RequestBody TicketRequestObject tro) {
		UUID seatID = tro.seatID;
		Seat toBook = new Seat();
		Optional<Seat> seat = seatRepository.findById(seatID);
		try {
			toBook = seat.get();
		} catch(NoSuchElementException e) {
			return new ResponseEntity<Object>("Seat " + seatID + " not found!", HttpStatus.NOT_FOUND);
		}
		
		Boolean blocked = toBook.isBlocked();
		Boolean booked = toBook.isBooked();
		if(blocked) {
			return new ResponseEntity<Object>("Seat "+seatID+" is currently blocked!", HttpStatus.NOT_ACCEPTABLE);
		}
		
		if(booked) {
			return new ResponseEntity<Object>("Seat "+seatID+" is already booked!", HttpStatus.NOT_ACCEPTABLE);
		}
		toBook.setBooked(true);
		
		Ticket toAdd = new Ticket();
		toAdd.setSeat(toBook);
		toAdd.setPaymentMethod(tro.paymentMethod);
		toAdd.setPrice(tro.price);
		try {
			toAdd.setShow(showRepository.findById(tro.showID).get());
		}catch(NoSuchElementException e) {
			return new ResponseEntity<Object>("Show "+tro.showID+" not found!",
					HttpStatus.NOT_FOUND);
		}
		
		try {
			toAdd.setUser(userRepository.findById(tro.userID).get());
		}catch(NoSuchElementException e) {
			return new ResponseEntity<Object>("User "+tro.userID+" not found!",
					HttpStatus.NOT_FOUND);
		}

		User user = userRepository.findById(tro.userID).get();
		Movie movie = showRepository.findById(tro.showID).get().getMovie();
		
		mail.sendTicketConformationMail(user.getEmail().trim(), movie.getTitel(), user.getFirstName());
		
		seatRepository.save(toBook);
		return new ResponseEntity<Object>(repo.save(toAdd), HttpStatus.CREATED);
	}
	
	@PutMapping("/cancel/{id}")
	public ResponseEntity<Object> cancelTicket(@PathVariable UUID id) {
		Ticket ticket = new Ticket();
		Seat seat = new Seat();
		try {
			ticket = repo.findById(id).get();
			seat = seatRepository.findById(ticket.getSeat().getId()).get();
			seat.setBlocked(false);
			seat.setBooked(false);
			ticket.setCanceled(true);
			seatRepository.save(seat);
			repo.save(ticket);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<Object>("Ticket "+ id +" not found! / No seats blocked for this ticket", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Object>("Ticket canceled", HttpStatus.OK);
	}
	
	@PutMapping("/uncancel/{id}")
	public ResponseEntity<Object> uncancelTicket(@PathVariable UUID id) {
		Ticket ticket = new Ticket();
		try {
			ticket = repo.findById(id).get();
			ticket.setCanceled(false);
			repo.save(ticket);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<Object>("Ticket "+ id +" not found!", HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Object>("Ticket uncanceled", HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Object> getSpecific(@PathVariable UUID id){
		try {
			return new ResponseEntity<Object>(repo.findById(id).get(), HttpStatus.OK);
		}
		catch(NoSuchElementException e) {
			return new ResponseEntity<Object>("Ticket "+id+" not found!",
					HttpStatus.NOT_FOUND);
		}
	}
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteTicket(@PathVariable UUID id){
		
		try {
			Ticket ticket = repo.findById(id).get();
			try {
				Seat seat = seatRepository.findById(ticket.getSeat().getId()).get();
				seat.setBlocked(false);
				seatRepository.save(seat);
			}
			catch(NoSuchElementException e) {
				return new ResponseEntity<Object>("Seat "+ticket.getSeat().getId()+" not found!", HttpStatus.NOT_FOUND);
			}
			ticket.setPaid(false);
			ticket.setSeat(null);
			return new ResponseEntity<Object>(repo.save(ticket), HttpStatus.OK);
			
		}
		catch(NoSuchElementException e) {
			return new ResponseEntity<Object>("Ticket "+id+" not found!",
					HttpStatus.NOT_FOUND);
		}
		
	}
	
	@DeleteMapping("/all")
	public ResponseEntity<Object> deleteAllTickets(){
		repo.deleteAll();
		return new ResponseEntity<Object>("All Tickets gone", HttpStatus.OK);
	}

}
