package de.wi2020sebgruppe4.KinoTicketRes.model;

import java.sql.Date;

public class MovieRequestObject {
	
	public String titel;
	public String language;
	public double duration;
	public String director;
	public String description;
	public java.sql.Date release;
	public String pictureLink;
	public String trailerLink;
	public boolean isAvailableIn3D;
	public int FSK;
	
	public MovieRequestObject(String titel, String language, double duration, String director, String description,
			Date release, String pictureLink, String trailerLink, boolean isAvailableIn3D, int fSK) {
		super();
		this.titel = titel;
		this.language = language;
		this.duration = duration;
		this.director = director;
		this.description = description;
		this.release = release;
		this.pictureLink = pictureLink;
		this.trailerLink = trailerLink;
		this.isAvailableIn3D = isAvailableIn3D;
		FSK = fSK;
	}

}
