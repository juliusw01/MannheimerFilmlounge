package de.wi2020sebgruppe4.KinoTicketRes.model;

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
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name="c_seats")
public class Seat {
	
	@Id
	@Column(columnDefinition= "VARBINARY(16)")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	
	@Column
	@NotNull
	private int rowLocation;
	
	@Column
	@NotNull
	private int seatLocation;
	
	@Column
	@NotNull
	private boolean isPremium;
	
	@Column
	@NotNull
	private boolean isBlocked;
	
	@ManyToOne(cascade= CascadeType.ALL ,fetch=FetchType.LAZY)
	@NotFound(action=NotFoundAction.IGNORE)
	@JoinColumn(name = "layout_id", referencedColumnName = "id")
	private Layout layout;
	
	@ManyToOne(cascade= CascadeType.ALL ,fetch=FetchType.LAZY)
	@NotFound(action=NotFoundAction.IGNORE)
	@JoinColumn(name = "show_id", referencedColumnName = "id")
	private Show show;
	
	public Seat() {
		
	}

	public Seat(@NotNull int rowLocation, @NotNull int seatLocation, @NotNull boolean isPremium,
			@NotNull boolean isBlocked, Layout layout, Show show) {
		super();
		this.rowLocation = rowLocation;
		this.seatLocation = seatLocation;
		this.isPremium = isPremium;
		this.isBlocked = isBlocked;
		this.layout = layout;
		this.show = show;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public int getRowLocation() {
		return rowLocation;
	}

	public void setRowLocation(int rowLocation) {
		this.rowLocation = rowLocation;
	}

	public int getSeatLocation() {
		return seatLocation;
	}

	public void setSeatLocation(int seatLocation) {
		this.seatLocation = seatLocation;
	}

	public boolean isPremium() {
		return isPremium;
	}

	public void setPremium(boolean isPremium) {
		this.isPremium = isPremium;
	}

	public boolean isBlocked() {
		return isBlocked;
	}

	public void setBlocked(boolean isBlocked) {
		this.isBlocked = isBlocked;
	}

	public Layout getLayout() {
		return layout;
	}

	public void setLayout(Layout layout) {
		this.layout = layout;
	}

	public Show getShow() {
		return show;
	}

	public void setShow(Show show) {
		this.show = show;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + (isBlocked ? 1231 : 1237);
		result = prime * result + (isPremium ? 1231 : 1237);
		result = prime * result + ((layout == null) ? 0 : layout.hashCode());
		result = prime * result + rowLocation;
		result = prime * result + seatLocation;
		result = prime * result + ((show == null) ? 0 : show.hashCode());
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
		Seat other = (Seat) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (isBlocked != other.isBlocked)
			return false;
		if (isPremium != other.isPremium)
			return false;
		if (layout == null) {
			if (other.layout != null)
				return false;
		} else if (!layout.equals(other.layout))
			return false;
		if (rowLocation != other.rowLocation)
			return false;
		if (seatLocation != other.seatLocation)
			return false;
		if (show == null) {
			if (other.show != null)
				return false;
		} else if (!show.equals(other.show))
			return false;
		return true;
	}

}
