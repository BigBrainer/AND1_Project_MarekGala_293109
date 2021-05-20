package sk.martialhero.model;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;

public class Group implements Serializable {

    public String uid;
    public String group_name;
    public String group_image;
    public String group_admin;
    public String group_location;
    public String group_description;

    public Group(){}

    public Group(String uid, String group_name, String group_image, String group_admin, String group_location, String group_description) {
        this.uid = uid;
        this.group_name = group_name;
        this.group_image = group_image;
        this.group_admin = group_admin;
        this.group_description = group_description;
        this.group_location = group_location;
    }

    public Group(String group_name, String group_location, String group_description) {
        this.group_name = group_name;
        this.group_location = group_location;
        this.group_description = group_description;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public void setGroup_image(String group_image) {
        this.group_image = group_image;
    }

    public void setGroup_admin(String group_admin) {
        this.group_admin = group_admin;
    }

    public void setGroup_location(String group_location) {
        this.group_location = group_location;
    }

    public void setGroup_description(String group_description) {
        this.group_description = group_description;
    }
}
