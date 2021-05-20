package sk.martialhero.model;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;

public class Post implements Serializable {
    public String postId;
    public String postImage;
    public String description;
    public String category;
    public String publisher;

    public Post() {}

    public Post(String postid, String postimage, String description, String category, String publisher) {
        this.postId = postid;
        this.postImage = postimage;
        this.description = description;
        this.category = category;
        this.publisher = publisher;
    }

    @Exclude
    public String getPostId() {
        return postId;
    }
    @Exclude
    public String getPostImage() {
        return postImage;
    }
    @Exclude
    public String getDescription() {
        return description;
    }
    @Exclude
    public String getCategory() {
        return category;
    }
    @Exclude
    public String getPublisher() {
        return publisher;
    }
}
