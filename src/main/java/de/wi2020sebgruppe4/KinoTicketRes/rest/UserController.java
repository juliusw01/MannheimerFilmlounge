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
import de.wi2020sebgruppe4.KinoTicketRes.model.UserRegistrationObject;
import de.wi2020sebgruppe4.KinoTicketRes.model.UserRequestObject;
import de.wi2020sebgruppe4.KinoTicketRes.repositories.ReviewRepository;
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
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserRepository repo;
	
	@Autowired 
	private TicketRepository ticketRepository;
	
	@Autowired
	private ReviewRepository reviewRepository;
	
	
	@GetMapping("")
	public ResponseEntity<Iterable<User>> getUsers(){
		return new ResponseEntity<>(repo.findAll(), HttpStatus.OK);
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
	
	
	@PutMapping("/register")
	public ResponseEntity<Object> registerUser(@RequestBody UserRegistrationObject uro){
		if(!uro.password.equals(uro.confirmPassword)) {
			return new ResponseEntity<Object>("User passwords not equal! "+uro.password+" != " +uro.confirmPassword, HttpStatus.NOT_ACCEPTABLE);
		}
		
		User toAddUser = new User();
		
		try {
			//Username Taken
			repo.findByuserName(uro.userName).get();
			return new ResponseEntity<Object>("Username already exists!", HttpStatus.NOT_ACCEPTABLE);
			}
		catch (NoSuchElementException e) {}
		
		try {
			//Email Taken
			repo.findByemail(uro.email).get();
			return new ResponseEntity<Object>("Email already exists!", HttpStatus.NOT_ACCEPTABLE);
			}
		catch (NoSuchElementException e) {}
		
		toAddUser.setUserName(uro.userName);
		toAddUser.setName(uro.name);
		toAddUser.setFirstName(uro.firstName);
		toAddUser.setEmail(uro.email);
		toAddUser.setPassword(uro.password);
		
		return new ResponseEntity<Object>( repo.save(toAddUser), HttpStatus.CREATED);
	}
	
	@PutMapping("/login")
	public ResponseEntity<Object> loginUser(@RequestBody UserRequestObject uro){
		Optional<User> user = null;
		if(uro.email != null) {
			String email = uro.email;
			user = repo.findByemail(email);
		}else if(uro.userName != null) {
			String userName = uro.userName;
			user = repo.findByuserName(userName);
		}
		if(user == null) {
			return new ResponseEntity<Object>("username or email must be provided", HttpStatus.NOT_ACCEPTABLE);
		}
		User toCheck = null;
		try {
			toCheck = user.get();
		}
		catch (NoSuchElementException e) {
			if(uro.email != null) {
				return new ResponseEntity<Object>("User with Email: "+ uro.email +" not found :(", HttpStatus.NOT_FOUND);
			}else if(uro.userName != null) {
				return new ResponseEntity<Object>("User with username: "+ uro.userName +" not found :(", HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<Object>("User with username or email not found :(", HttpStatus.NOT_FOUND);
		}
		
		if(!uro.password.equals(toCheck.getPassword())) {
			return new ResponseEntity<Object>("Wrong password", HttpStatus.UNAUTHORIZED);
		}
		
		return new ResponseEntity<Object>("Password correct", HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/{id}/tickets")
	public ResponseEntity<Object> getUsersTickets(@PathVariable UUID id) {
		Optional<User> u = repo.findById(id);
		try {
			User user = u.get();
			return new ResponseEntity<Object>(ticketRepository.findAllByUser(user), HttpStatus.OK);
		}
		catch (NoSuchElementException e) {
			return new ResponseEntity<Object>("UserID: "+ id +" not found :(", HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/{id}/reviews")
	public ResponseEntity<Object> getusersReviews(@PathVariable UUID id) {
		Optional<User> u = repo.findById(id);
		try {
			User user = u.get();
			return new ResponseEntity<Object>(reviewRepository.findAllByUser(user), HttpStatus.OK);
		}
		catch (NoSuchElementException e) {
			return new ResponseEntity<Object>("UserID: "+ id +" not found :(", HttpStatus.NOT_FOUND);
		}
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
