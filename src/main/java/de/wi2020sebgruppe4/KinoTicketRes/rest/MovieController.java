package de.wi2020sebgruppe4.KinoTicketRes.rest;

import java.util.List;
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
import de.wi2020sebgruppe4.KinoTicketRes.model.MovieRequestObject;
import de.wi2020sebgruppe4.KinoTicketRes.model.Show;
import de.wi2020sebgruppe4.KinoTicketRes.repositories.MovieRepository;
import de.wi2020sebgruppe4.KinoTicketRes.repositories.ShowRepository;

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
@RequestMapping("/movies")
public class MovieController {
	
	@Autowired
	MovieRepository repo;
	
	@Autowired
	ShowRepository showRepository;
	
	
	@GetMapping("")
	public ResponseEntity<Iterable<Movie>> getAll(){
		return new ResponseEntity<Iterable<Movie>>(repo.findAll(), HttpStatus.OK);	
	}
	
	@PutMapping("/add")
	public ResponseEntity<Object> addMovie(@RequestBody MovieRequestObject mro){
		
		Movie toAddMovie = new Movie();
		toAddMovie.setTitel(mro.titel);
		toAddMovie.setLanguage(mro.language);
		toAddMovie.setDuration(mro.duration);
		toAddMovie.setDirector(mro.director);
		toAddMovie.setDescription(mro.description);
		toAddMovie.setRelease(mro.release);
		toAddMovie.setPictureLink(mro.pictureLink);
		toAddMovie.setTrailerLink(mro.trailerLink);
		toAddMovie.setAvailableIn3D(mro.isAvailableIn3D);
		return new ResponseEntity<Object>(repo.save(toAddMovie), HttpStatus.CREATED);
		
	}
	
	
	@PutMapping("/update/{id}")
	public ResponseEntity<Object> updateMovie(@PathVariable UUID id,@RequestBody MovieRequestObject mro){
		
		Optional<Movie> toUpdate = repo.findById(id);
		
		try {
			toUpdate.get().setTitel(mro.titel);
			toUpdate.get().setLanguage(mro.language);
			toUpdate.get().setDuration(mro.duration);
			toUpdate.get().setDirector(mro.director);
			toUpdate.get().setDescription(mro.description);
			toUpdate.get().setRelease(mro.release);
			toUpdate.get().setPictureLink(mro.pictureLink);
			toUpdate.get().setTrailerLink(mro.trailerLink);
			toUpdate.get().setAvailableIn3D(mro.isAvailableIn3D);
			UUID currentMovieID = toUpdate.get().getId();
			toUpdate.get().setId(currentMovieID);
			repo.save(toUpdate.get());
			return new ResponseEntity<Object>(toUpdate.get(), HttpStatus.OK);
			
		}
		catch(NoSuchElementException e) {
			return new ResponseEntity<Object>("Movie "+id+" not found!", HttpStatus.NOT_FOUND);
		}
		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Object> getSpecific(@PathVariable UUID id){
		
		Optional<Movie> movie = repo.findById(id);
		
		try {
			Movie toReturn = movie.get();
			return new ResponseEntity<Object>(toReturn, HttpStatus.OK);
		}
		catch(NoSuchElementException e) {
			return new ResponseEntity<Object>("Movie "+id+" not found!", HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/{id}/shows")
	public ResponseEntity<Object> getShowsForMovie(@PathVariable UUID id){
		
		Optional<Movie> requested = repo.findById(id);
		
		try {
			Optional<List<Show>> shows = showRepository.findAllByMovie(requested.get());
			
			try {
				return new ResponseEntity<Object>(shows.get(), HttpStatus.OK);
			}
			catch (NoSuchElementException e) {
				return new ResponseEntity<Object>(new String("No Shows for Movie with id " + id + " found!"), HttpStatus.NOT_FOUND);
			}
		}
		catch(NoSuchElementException e) {
			return new ResponseEntity<Object>("Movie "+id+" not found!", HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteMovie(@PathVariable UUID id){
		Optional<Movie> o = repo.findById(id);
		try {
			repo.deleteById(o.get().getId());
			return new ResponseEntity<Object>(new String("Movie with id \"" + id + "\" deleted!"), HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<Object>("Movie "+id+" not found!", HttpStatus.NOT_FOUND);
		}
	}
}
