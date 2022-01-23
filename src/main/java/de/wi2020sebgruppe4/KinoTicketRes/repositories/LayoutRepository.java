package de.wi2020sebgruppe4.KinoTicketRes.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import de.wi2020sebgruppe4.KinoTicketRes.model.Room;
import de.wi2020sebgruppe4.KinoTicketRes.model.Layout;

public interface LayoutRepository extends CrudRepository<Layout, UUID> {
	
	Optional<Layout> findByRoom(Room room);
	
}
