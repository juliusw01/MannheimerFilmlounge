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

import de.wi2020sebgruppe4.KinoTicketRes.model.User;
import de.wi2020sebgruppe4.KinoTicketRes.model.UserRequestObject;
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
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserRepository repo;
	
	
	@GetMapping("")
	public ResponseEntity<Iterable<User>> getUsers(){
		return new ResponseEntity<>(repo.findAll(), HttpStatus.OK);
	}	
	
	
	@GetMapping("/{id}")
	public ResponseEntity<Object> getSpecific(@PathVariable UUID id){
		
		Optional<User> user = repo.findById(id);
		
		try {
			User toReturn = user.get();
			return new ResponseEntity<Object>(toReturn, HttpStatus.OK);
		}
		catch(NoSuchElementException e) {
			return new ResponseEntity<Object>("User "+id+" not found!", HttpStatus.NOT_FOUND);
		}
		
	}
	
	
	@PutMapping("/add")
	public ResponseEntity<Object> addUser(@RequestBody UserRequestObject uro){
		User toAddUser = new User();
		toAddUser.setUserName(uro.userName);
		toAddUser.setName(uro.name);
		toAddUser.setFirstName(uro.firstName);
		toAddUser.setEmail(uro.email);
		toAddUser.setPassword(uro.password);
		return new ResponseEntity<Object>( repo.save(toAddUser), HttpStatus.CREATED);
	}
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteUser(@PathVariable UUID id){
		Optional<User> o = repo.findById(id);
		try {
			repo.deleteById(o.get().getId());
			return new ResponseEntity<>(id, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>("User "+id+" not found!", HttpStatus.NOT_FOUND);
		}
	}

}
