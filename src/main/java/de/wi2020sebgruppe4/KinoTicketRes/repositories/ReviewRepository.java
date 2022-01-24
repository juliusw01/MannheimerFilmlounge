package de.wi2020sebgruppe4.KinoTicketRes.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import de.wi2020sebgruppe4.KinoTicketRes.model.Review;
import de.wi2020sebgruppe4.KinoTicketRes.model.User;

public interface ReviewRepository extends CrudRepository<Review, UUID> {
	Optional<List<Review>> findAllByUser(User user);
}
