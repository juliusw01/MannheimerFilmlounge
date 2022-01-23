package de.wi2020sebgruppe4.KinoTicketRes.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import de.wi2020sebgruppe4.KinoTicketRes.model.Seat;
import de.wi2020sebgruppe4.KinoTicketRes.model.Show;

public interface SeatRepository extends CrudRepository<Seat, UUID> {
	
	Optional<List<Seat>> findAllByShow(Show show);

}
	