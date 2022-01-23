package de.wi2020sebgruppe4.KinoTicketRes.rest;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

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
import de.wi2020sebgruppe4.KinoTicketRes.model.Room;
import de.wi2020sebgruppe4.KinoTicketRes.model.RoomRequestObject;
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
@RequestMapping("/rooms")
public class RoomController {
	
	@Autowired
	RoomRepository repo;
	
	@Autowired
	LayoutRepository layoutRepository;
	
	
	@PutMapping("/add")
	public ResponseEntity<Object> addCinemaRoom(@RequestBody RoomRequestObject rro){
		
		Room toBuild = new Room();
		Layout toAddLayout = new Layout();
		
		if(rro.layoutID != null) {
			try {
				toAddLayout = layoutRepository.findById(rro.layoutID).get();
				toBuild.setLayout(toAddLayout);
			}
			catch(NoSuchElementException e) {
				return new ResponseEntity<Object>("Layout "+rro.layoutID+" not found!", HttpStatus.NOT_FOUND);
			}
		}
		
		toBuild.setCanShow3D(rro.canShow3D);
		
		return new ResponseEntity<Object>(repo.save(toBuild), HttpStatus.CREATED);
	}
	
	
	@PutMapping("/update/{id}")
	public ResponseEntity<Object> updateCinemaRoom(@PathVariable UUID id, @RequestBody RoomRequestObject rro2){
		
		Optional<Room> oldRoomSearch = repo.findById(id);
		Room toBuild = new Room();
		Layout toAddSeatingPlan = new Layout();
		
		try {
			Room oldRoom = oldRoomSearch.get(); 
			toBuild = new Room();
			toBuild.setId(oldRoom.getId());
			
			if(rro2.layoutID != null) {
				try {
					toAddSeatingPlan = layoutRepository.findById(rro2.layoutID).get();
					toBuild.setLayout(toAddSeatingPlan);
				}
				catch(NoSuchElementException e) {
					return new ResponseEntity<Object>("Layout "+rro2.layoutID+" not found!", HttpStatus.NOT_FOUND);
				}
			}
			
			toBuild.setCanShow3D(rro2.canShow3D);
			return new ResponseEntity<Object>(repo.save(toBuild), HttpStatus.OK);
			
		}
		catch(NoSuchElementException e) {
			return new ResponseEntity<Object>("Room "+id+" not found!", HttpStatus.NOT_FOUND);
		}
		
	}
	
	
	@GetMapping("")
	public ResponseEntity<Iterable<Room>> getAllCinemaRooms(){
		return new ResponseEntity<Iterable<Room>>(repo.findAll(), HttpStatus.OK);
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<Object> getCinemaRoom(@PathVariable UUID id){
		Optional<Room> search = repo.findById(id);
		
		try {
			Room searched = search.get();
			return new ResponseEntity<Object>(searched, HttpStatus.OK);
		}
		catch(NoSuchElementException e) {
			return new ResponseEntity<Object>("Room "+id+" not found!", HttpStatus.NOT_FOUND);
		}
		
	}
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteCinemaRoom(@PathVariable UUID id){
		Optional<Room> o = repo.findById(id);
		try {
			repo.deleteById(o.get().getId());
			return new ResponseEntity<Object>(new String("CinemaRoom with id \"" + id + "\" deleted!"),HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<Object>("Room "+id+" not found!", HttpStatus.NOT_FOUND);
		}
		
	}

}
