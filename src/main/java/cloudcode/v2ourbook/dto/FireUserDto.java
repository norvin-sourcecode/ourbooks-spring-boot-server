package cloudcode.v2ourbook.dto;

import cloudcode.v2ourbook.models.User;

public class FireUserDto {

    Long id;

    String username;
    String firstname;
    String lastname;
    String email;
    boolean active;
    String token;

    public FireUserDto(){
        super();
    }

    public FireUserDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.firstname = user.getFirstname();
        this.lastname = user.getLastname();
        this.email = user.getEmail();
        this.active = user.isActive();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String user_username) {
        this.username = user_username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String user_vorname) {
        this.firstname = user_vorname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String user_nachname) {
        this.lastname = user_nachname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String user_email) {
        this.email = user_email;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return String.format(
                "User[id=%d, username='%s', password='%s', vorname='%s', nachname='%s', email='%s']",
                id, username, firstname, lastname, email);
    }

}
