package de.wi2020sebgruppe4.KinoTicketRes.model;

import java.util.UUID;

import javax.validation.constraints.NotNull;

public class LayoutRequestObject {
	
	public int totalSeats;
	public int rowCount;
	
	public LayoutRequestObject(@NotNull int totalSeats, @NotNull int rowCount) {
		this.totalSeats = totalSeats;
		this.rowCount = rowCount;
	}

}
