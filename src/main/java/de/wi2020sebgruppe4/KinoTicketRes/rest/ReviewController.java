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

import de.wi2020sebgruppe4.KinoTicketRes.model.Movie;
import de.wi2020sebgruppe4.KinoTicketRes.model.Review;
import de.wi2020sebgruppe4.KinoTicketRes.model.ReviewRequestObject;
import de.wi2020sebgruppe4.KinoTicketRes.model.User;
import de.wi2020sebgruppe4.KinoTicketRes.repositories.MovieRepository;
import de.wi2020sebgruppe4.KinoTicketRes.repositories.ReviewRepository;
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
@RequestMapping("/reviews")
public class ReviewController {
	
	@Autowired
	ReviewRepository repo;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	MovieRepository movieRepository;
	
	
	@GetMapping("")
	public ResponseEntity<Iterable<Review>> getAll() {
		return new ResponseEntity<Iterable<Review>>(repo.findAll(), HttpStatus.OK);
	}
	
	@PutMapping("/add")
	public ResponseEntity<Object> addReview(@RequestBody ReviewRequestObject rro) {
		Review toAddReview = new Review();
		if(rro.userID != null) {
			Optional<User> optionalUser = userRepository.findById(rro.userID);
			try {
				User u = optionalUser.get();
				toAddReview.setUser(u);
			} catch (NoSuchElementException e) {
				return new ResponseEntity<Object>("User " + rro.userID + "not found!", HttpStatus.NOT_FOUND);
			}
		}
		
		if(rro.movieID != null) {
			Optional<Movie> optionalMovie = movieRepository.findById(rro.movieID);
			try {
				Movie m = optionalMovie.get();
				toAddReview.setMovie(m);
			} catch (NoSuchElementException e) {
				return new ResponseEntity<Object>("Movie " + rro.movieID + "not found!", HttpStatus.NOT_FOUND);
			}
		}
		
		toAddReview.setTitel(rro.titel);
		toAddReview.setContent(rro.content);
		toAddReview.setStars(rro.stars);
		toAddReview.setDate(rro.date);
		return new ResponseEntity<Object>(repo.save(toAddReview), HttpStatus.CREATED);
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<Object> updateReview(@PathVariable UUID id, @RequestBody ReviewRequestObject rro) {
		Optional<Review> toUpdate = repo.findById(id);
		
		if(rro.userID != null) {
			Optional<User> optionalUser = userRepository.findById(rro.userID);
			try {
				User u = optionalUser.get();
				toUpdate.get().setUser(u);
			} catch (NoSuchElementException e) {
				return new ResponseEntity<Object>("User " + rro.userID + "not found!", HttpStatus.NOT_FOUND);
			}
		}
		
		if(rro.movieID != null) {
			Optional<Movie> optionalMovie = movieRepository.findById(rro.movieID);
			try {
				Movie m = optionalMovie.get();
				toUpdate.get().setMovie(m);
			} catch (NoSuchElementException e) {
				return new ResponseEntity<Object>("Movie " + rro.movieID + "not found!", HttpStatus.NOT_FOUND);
			}
		}
		
		toUpdate.get().setTitel(rro.titel);
		toUpdate.get().setContent(rro.content);
		toUpdate.get().setStars(rro.stars);
		toUpdate.get().setDate(rro.date);
		UUID currentReviewID = toUpdate.get().getId();
		toUpdate.get().setId(currentReviewID);
		repo.save(toUpdate.get());
		return new ResponseEntity<Object>(toUpdate.get(), HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Object> getSpecific(@PathVariable UUID id) {
		Optional<Review> r = repo.findById(id);
		
		try {
			Review toReturn = r.get();
			return new ResponseEntity<Object>(toReturn, HttpStatus.OK);
		}
		catch(NoSuchElementException e) {
				return new ResponseEntity<Object>("Review "+id+" not found!", HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteReview(@PathVariable UUID id) {
		Optional<Review> review = repo.findById(id);
		try {
			repo.deleteById(review.get().getId());
			return new ResponseEntity<Object>(new String("Review with id \"" + id + "\" deleted!"), HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<Object>("Review "+id+" not found!", HttpStatus.NOT_FOUND);
		}
	}
}
