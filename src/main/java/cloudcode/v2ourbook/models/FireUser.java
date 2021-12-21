package cloudcode.v2ourbook.models;

import cloudcode.v2ourbook.controller.UserController;
import org.springframework.beans.factory.annotation.Autowired;

public class FireUser {

    public Long id;
    public String name;
    public String avatar;

    public FireUser() {
    }

    @Override
    public String toString() {
        return "FireUser{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", avatar='" + avatar + '\'' +
                '}';
    }
}
