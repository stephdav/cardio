package com.sopra.agile.cardio.back.model;

public class User extends Base {

	private String login;
	private String firstname;
	private String lastname;

	public User() {
		super();
	}

	public User(final String id, final String login) {
		super(id);
		this.login = login;
	}

	public User(final String id, final String login, final String firstname, final String lastname) {
		this(id, login);
		this.firstname = firstname;
		this.lastname = lastname;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
}
