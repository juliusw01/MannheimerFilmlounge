package de.wi2020sebgruppe4.KinoTicketRes.model;

import java.util.UUID;

public class TokenRequestObject {

    public boolean valid;
    public UUID userID;

    public TokenRequestObject(boolean valid, UUID userID) {
        super();
        this.valid = valid;
        this.userID = userID;
    }

}
