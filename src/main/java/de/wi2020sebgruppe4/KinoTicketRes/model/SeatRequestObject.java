package de.wi2020sebgruppe4.KinoTicketRes.model;

import java.util.UUID;

import javax.validation.constraints.NotNull;

public class SeatRequestObject {
	
	public UUID seatID;
	public boolean block;
	public boolean deblock;
	
	public SeatRequestObject(@NotNull UUID seatID, @NotNull boolean block, @NotNull boolean deblock) {
		this.seatID = seatID;
		this.block = block;
		this.deblock = deblock;
	}

}
