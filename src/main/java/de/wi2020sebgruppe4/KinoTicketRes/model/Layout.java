package de.wi2020sebgruppe4.KinoTicketRes.model;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="layouts")
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

	public Layout() {
		
	}

	public Layout(@NotNull int totalSeats, @NotNull int rowCount) {
		super();
		this.totalSeats = totalSeats;
		this.rowCount = rowCount;
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
