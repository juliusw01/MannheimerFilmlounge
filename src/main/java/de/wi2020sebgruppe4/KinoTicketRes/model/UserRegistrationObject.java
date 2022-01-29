package de.wi2020sebgruppe4.KinoTicketRes.model;

public class UserRegistrationObject {
	
	public String userName;
	public String name;
	public String firstName;
	public String email;
	public String password;
	public String confirmPassword;
	
	public UserRegistrationObject(String userName, String name, String firstName, String email, String password, String confirmPassword) {
		super();
		this.userName = userName;
		this.name = name;
		this.firstName = firstName;
		this.email = email;
		this.password = password;
		this.confirmPassword = confirmPassword;
	}
}
