package de.wi2020sebgruppe4.KinoTicketRes.model;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="c_layouts")
@JsonIgnoreProperties("room")
public class Layout {
	
	@Id
	@Column(columnDefinition= "VARBINARY(16)")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	
	@Column
	@NotNull
	private int totalSeats;
	
	@Column
	@NotNull
	private int rowCount;
	
	@OneToOne(mappedBy = "layout")
	private Room room;

	public Layout() {
		
	}

	public Layout(@NotNull int totalSeats, @NotNull int rowCount, Room room) {
		super();
		this.totalSeats = totalSeats;
		this.rowCount = rowCount;
		this.room = room;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public int getTotalSeats() {
		return totalSeats;
	}

	public void setTotalSeats(int totalSeats) {
		this.totalSeats = totalSeats;
	}

	public int getRowCount() {
		return rowCount;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	@Override
	public int hashCode() {
		int result = 1;
		result = result + ((id == null) ? 0 : id.hashCode());
		result = result + rowCount;
		result = result + totalSeats;
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
		Layout other = (Layout) obj;
		if (room == null) {
			if (other.room != null)
				return false;
		} else if (!room.equals(other.room))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (rowCount != other.rowCount)
			return false;
		if (totalSeats != other.totalSeats)
			return false;
		return true;
	}

}
