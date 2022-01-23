package de.wi2020sebgruppe4.KinoTicketRes.model;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name="c_shows")
public class Show {
	
	@Id
	@Column(columnDefinition= "VARBINARY(16)")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	
	@Column
	@NotNull
	private java.sql.Date showDate;
	
	@Column
	@NotNull
	private java.sql.Time startTime;
	
	@ManyToOne(cascade= CascadeType.ALL ,fetch=FetchType.LAZY)
	@NotFound(action=NotFoundAction.IGNORE)
	@JoinColumn(name = "movie_id", referencedColumnName = "id")
	private Movie movie;
	
	@ManyToOne(cascade= CascadeType.ALL ,fetch=FetchType.LAZY)
	@NotFound(action=NotFoundAction.IGNORE)
	@JoinColumn(name = "room_id", referencedColumnName = "id")
	private Room room;
	
	@OneToMany(mappedBy="show")
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private List<Seat> seats = new ArrayList<>();
	
	public Show() {
		
	}

	public Show(Date showDate, Time startTime, Movie movie, Room room, List<Seat> seats) {
		super();
		this.showDate = showDate;
		this.startTime = startTime;
		this.movie = movie;
		this.room = room;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public java.sql.Date getShowDate() {
		return showDate;
	}

	public void setShowDate(java.sql.Date showDate) {
		this.showDate = showDate;
	}

	public java.sql.Time getStartTime() {
		return startTime;
	}

	public void setStartTime(java.sql.Time startTime) {
		this.startTime = startTime;
	}

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((movie == null) ? 0 : movie.hashCode());
		result = prime * result + ((room == null) ? 0 : room.hashCode());
		// result = prime * result + ((seats == null) ? 0 : seats.hashCode());
		result = prime * result + ((showDate == null) ? 0 : showDate.hashCode());
		result = prime * result + ((startTime == null) ? 0 : startTime.hashCode());
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
		Show other = (Show) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (movie == null) {
			if (other.movie != null)
				return false;
		} else if (!movie.equals(other.movie))
			return false;
		if (room == null) {
			if (other.room != null)
				return false;
		} else if (!room.equals(other.room))
			return false;
		/*
		if (seats == null) {
			if (other.seats != null)
				return false;
		} else if (!seats.equals(other.seats))
			return false;
		*/
		if (showDate == null) {
			if (other.showDate != null)
				return false;
		} else if (!showDate.equals(other.showDate))
			return false;
		if (startTime == null) {
			if (other.startTime != null)
				return false;
		} else if (!startTime.equals(other.startTime))
			return false;
		return true;
	}
	
}
