package de.wi2020sebgruppe4.KinoTicketRes.model;

import java.util.UUID;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="rooms")
public class Room {
	
	@Id
	@Column(columnDefinition= "VARBINARY(16)")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	
	@Column
	@NotNull
	private boolean canShow3D;
	
	@OneToOne
	@JoinColumn(name = "layout_id", referencedColumnName = "id")
	private Layout layout;
	
	public Room() {
		
	}

	public Room(@NotNull boolean canShow3D, Layout layout) {
		super();
		this.canShow3D = canShow3D;
		this.layout = layout;
	}

	public Room(@NotNull boolean canShow3D) {
		super();
		this.canShow3D = canShow3D;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public boolean isCanShow3D() {
		return canShow3D;
	}

	public void setCanShow3D(boolean canShow3D) {
		this.canShow3D = canShow3D;
	}

	public Layout getLayout() {
		return layout;
	}

	public void setLayout(Layout layout) {
		this.layout = layout;
	}

	@Override
	public int hashCode() {
		int result = 1;
		result = result + (canShow3D ? 1231 : 1237);
		result = result + ((id == null) ? 0 : id.hashCode());
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
		Room other = (Room) obj;
		if (canShow3D != other.canShow3D)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (layout == null) {
			if (other.layout != null)
				return false;
		} else if (!layout.equals(other.layout))
			return false;
		return true;
	}
	
}
