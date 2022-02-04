package de.wi2020sebgruppe4.KinoTicketRes.model;

import java.util.UUID;

public class PasswordResetObject {

    public String password;
    public UUID tokenID;
    public UUID userID;

    public PasswordResetObject(String password, UUID tokenID, UUID userID) {
        super();
        this.password = password;
        this.tokenID = tokenID;
        this.userID = userID;
    }

}
