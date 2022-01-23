package de.wi2020sebgruppe4.KinoTicketRes.model;

import java.util.UUID;

import javax.validation.constraints.NotNull;

public class RoomRequestObject { 
	
	public boolean canShow3D;
	public UUID layoutID;
	
	public RoomRequestObject(@NotNull boolean canShow3D, UUID layoutID) {
		this.canShow3D = canShow3D;
		this.layoutID = layoutID != null ? layoutID : null;
	}

}
