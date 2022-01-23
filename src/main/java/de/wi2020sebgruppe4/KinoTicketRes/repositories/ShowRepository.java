package de.wi2020sebgruppe4.KinoTicketRes.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import de.wi2020sebgruppe4.KinoTicketRes.model.Movie;
import de.wi2020sebgruppe4.KinoTicketRes.model.Show;

public interface ShowRepository extends CrudRepository<Show, UUID> {
	
	Optional<List<Show>> findAllByMovie(Movie movie);

}
