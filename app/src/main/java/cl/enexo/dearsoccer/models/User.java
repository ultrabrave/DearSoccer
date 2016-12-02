package cl.enexo.dearsoccer.models;

/**
 * Created by Kevin on 29-11-2016.
 */

public class User {
    public String name, email, user_id;

    public User() {
    }

    public User(String name, String email, String user_id) {
        this.name = name;
        this.email = email;
        this.user_id = user_id;
    }
}
