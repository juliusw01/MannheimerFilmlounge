package de.wi2020sebgruppe4.KinoTicketRes.model;

import java.sql.Date;
import java.util.UUID;

public class ReviewRequestObject {
	
	public String titel;
	public String content;
	public UUID userID;
	public UUID movieID;
	public int stars;
	public java.sql.Date date;
	
	public ReviewRequestObject(String titel, String content, UUID userID, UUID movieID, int stars, Date date) {
		super();
		this.titel = titel;
		this.content = content;
		this.userID = userID;
		this.movieID = movieID;
		this.stars = stars;
		this.date = date;
	}
}
