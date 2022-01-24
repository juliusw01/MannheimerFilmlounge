package de.wi2020sebgruppe4.KinoTicketRes.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import de.wi2020sebgruppe4.KinoTicketRes.model.Ticket;
import de.wi2020sebgruppe4.KinoTicketRes.model.User;

public interface TicketRepository extends CrudRepository<Ticket, UUID> {
	Optional<List<Ticket>> findAllByUser(User user);
}
