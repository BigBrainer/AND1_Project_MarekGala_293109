package sk.martialhero.model;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;

public class User implements Serializable {

    public String uid;
    public String firstName;
    public String lastName;
    public String email;

    //Must have empty constructor
    public User(){}


    public User(String uid, String firstName, String lastName, String email) {
        this.uid = uid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    @Exclude
    public String getFirstName() {
        return firstName;
    }
    @Exclude
    public String getUid() {
        return uid;
    }
    @Exclude
    public String getLastName() {
        return lastName;
    }
    @Exclude
    public String getEmail(){
        return email;
    }

}
