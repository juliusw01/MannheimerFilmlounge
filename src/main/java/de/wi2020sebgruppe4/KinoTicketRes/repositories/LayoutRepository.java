package de.wi2020sebgruppe4.KinoTicketRes.repositories;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import de.wi2020sebgruppe4.KinoTicketRes.model.Layout;

public interface LayoutRepository extends CrudRepository<Layout, UUID> {
	
}
