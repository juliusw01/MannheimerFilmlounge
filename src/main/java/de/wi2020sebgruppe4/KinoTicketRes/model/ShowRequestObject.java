package de.wi2020sebgruppe4.KinoTicketRes.model;

import java.sql.Date;
import java.sql.Time;
import java.util.UUID;

import javax.validation.constraints.NotNull;

public class ShowRequestObject {
	
	public Date date;
	public Time start;
	public UUID movieID;
	public UUID roomID;
	
	public ShowRequestObject(@NotNull Date date, @NotNull Time start, @NotNull UUID movieID, @NotNull UUID roomID) {
		this.date = date;
		this.start = start;
		this.movieID = movieID != null ? movieID : null;
		this.roomID = roomID != null ? roomID : null;
	}
	

}
