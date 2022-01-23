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

import de.wi2020sebgruppe4.KinoTicketRes.model.Layout;
import de.wi2020sebgruppe4.KinoTicketRes.model.LayoutRequestObject;
import de.wi2020sebgruppe4.KinoTicketRes.model.Room;
import de.wi2020sebgruppe4.KinoTicketRes.repositories.LayoutRepository;
import de.wi2020sebgruppe4.KinoTicketRes.repositories.RoomRepository;

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
@RequestMapping("/layouts")
public class LayoutController {
	
	@Autowired
	LayoutRepository repo;
	
	@Autowired
	RoomRepository roomRepository;
	
	
	@PutMapping("/add")
	@Transactional
	public ResponseEntity<Object> addSeatingPlan(@RequestBody LayoutRequestObject lro){
		Layout layout = new Layout();
		Room room = new Room();
		if(lro.roomID != null) {
			try {
				Optional<Room> cinemaRoomSearch = roomRepository.findById(lro.roomID);
				room = cinemaRoomSearch.get();
				layout.setRoom(room);
			}
			catch(NoSuchElementException e) {
				return new ResponseEntity<Object>("Room "+lro.roomID+" not found!", HttpStatus.NOT_FOUND );
			}
		}
		layout.setTotalSeats(lro.totalSeats);
		layout.setRowCount(lro.rowCount);
		room.setLayout(layout);
		return new ResponseEntity<Object>(repo.save(layout), HttpStatus.CREATED);
	}
	
	
	@PutMapping("/update/{id}")
	public ResponseEntity<Object> updateSeatingPlan(@PathVariable UUID id, @RequestBody LayoutRequestObject lro){
		Optional<Layout> oldSeatingPlan = repo.findById(id);
		
		try {
			Layout layout = new Layout();
			layout.setId(oldSeatingPlan.get().getId());

			if(lro.roomID != null) {
				try {
					Optional<Room> cinemaRoom =  roomRepository.findById(lro.roomID);
					layout.setRoom(cinemaRoom.get());
				}
				catch(NoSuchElementException e) {
					return new ResponseEntity<Object>("Room "+lro.roomID+" not found!", HttpStatus.NOT_FOUND );
				}
			}
			layout.setTotalSeats(lro.totalSeats);
			layout.setRowCount(lro.rowCount);
			return new ResponseEntity<Object>(repo.save(layout), HttpStatus.OK);
			
		} catch(NoSuchElementException e) {
			return new ResponseEntity<Object>("Layout "+id+" not found!", 
					HttpStatus.NOT_FOUND );
		}
	}
	
	
	@GetMapping("")
	public ResponseEntity<Object> getAll(){
		return new ResponseEntity<Object>(repo.findAll(), HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Object> getById(@PathVariable UUID id){
		Optional<Layout> seatingPlan = repo.findById(id);
		try {
			return new ResponseEntity<Object>(seatingPlan.get(), HttpStatus.OK);
		}catch(NoSuchElementException e) {
			return new ResponseEntity<Object>("Layout "+id+" not found!", HttpStatus.NOT_FOUND );
		}
	}
	
	
	@Transactional
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteById(@PathVariable UUID id){
		Optional<Layout> o = repo.findById(id);
		try {
			repo.deleteById(o.get().getId());
			return new ResponseEntity<Object>(new String("Layout with id \"" + id + "\" deleted!"),HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<Object>("Layout "+id+" not found!", HttpStatus.NOT_FOUND);
		}
	}

}
