package de.wi2020sebgruppe4.KinoTicketRes.model;

import java.sql.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="c_movies")
public class Movie {
	
	@Id
	@Column(columnDefinition= "VARBINARY(16)")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	
	@Column
	@NotNull
	private String titel;
	
	@Column
	@NotNull
	private String language;
	
	@Column
	@NotNull
	private double duration;
	
	@Column
	@NotNull
	private String director;
	
	@Column(columnDefinition = "TEXT")
	@NotNull
	private String description;
	
	@Column
	@NotNull
	private java.sql.Date release;
	
	@Column
	@NotNull
	private String pictureLink;
	
	@Column
	@NotNull
	private String trailerLink;
	
	@Column
	@NotNull
	private boolean isAvailableIn3D;
	
	@Column
	private int FSK;
	
	public Movie() {
		
	}

	public Movie(@NotNull String titel, @NotNull String language, @NotNull double duration,
			@NotNull String director, @NotNull String description, Date release, @NotNull String pictureLink,
			@NotNull String trailerLink, @NotNull boolean isAvailableIn3D, int fSK) {
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

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getTitel() {
		return titel;
	}

	public void setTitel(String titel) {
		this.titel = titel;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public double getDuration() {
		return duration;
	}

	public void setDuration(double duration) {
		this.duration = duration;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public java.sql.Date getRelease() {
		return release;
	}

	public void setRelease(java.sql.Date release) {
		this.release = release;
	}

	public String getPictureLink() {
		return pictureLink;
	}

	public void setPictureLink(String pictureLink) {
		this.pictureLink = pictureLink;
	}

	public String getTrailerLink() {
		return trailerLink;
	}

	public void setTrailerLink(String trailerLink) {
		this.trailerLink = trailerLink;
	}

	public boolean isAvailableIn3D() {
		return isAvailableIn3D;
	}

	public void setAvailableIn3D(boolean isAvailableIn3D) {
		this.isAvailableIn3D = isAvailableIn3D;
	}

	public int getFSK() {
		return FSK;
	}

	public void setFSK(int fSK) {
		FSK = fSK;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + FSK;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((director == null) ? 0 : director.hashCode());
		long temp;
		temp = Double.doubleToLongBits(duration);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + (isAvailableIn3D ? 1231 : 1237);
		result = prime * result + ((language == null) ? 0 : language.hashCode());
		result = prime * result + ((pictureLink == null) ? 0 : pictureLink.hashCode());
		result = prime * result + ((release == null) ? 0 : release.hashCode());
		result = prime * result + ((titel == null) ? 0 : titel.hashCode());
		result = prime * result + ((trailerLink == null) ? 0 : trailerLink.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Movie other = (Movie) obj;
		if (FSK != other.FSK)
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (director == null) {
			if (other.director != null)
				return false;
		} else if (!director.equals(other.director))
			return false;
		if (Double.doubleToLongBits(duration) != Double.doubleToLongBits(other.duration))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (isAvailableIn3D != other.isAvailableIn3D)
			return false;
		if (language == null) {
			if (other.language != null)
				return false;
		} else if (!language.equals(other.language))
			return false;
		if (pictureLink == null) {
			if (other.pictureLink != null)
				return false;
		} else if (!pictureLink.equals(other.pictureLink))
			return false;
		if (release == null) {
			if (other.release != null)
				return false;
		} else if (!release.equals(other.release))
			return false;
		if (titel == null) {
			if (other.titel != null)
				return false;
		} else if (!titel.equals(other.titel))
			return false;
		if (trailerLink == null) {
			if (other.trailerLink != null)
				return false;
		} else if (!trailerLink.equals(other.trailerLink))
			return false;
		return true;
	}

}
