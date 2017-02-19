package com.sopra.agile.cardio.common.model;

public class User extends BaseLong {

    private String login;
    private String firstname;
    private String lastname;

    public User() {
        super();
    }

    public User(long id, final String login, final String firstname, final String lastname) {
        super(id);
        this.login = login;
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
