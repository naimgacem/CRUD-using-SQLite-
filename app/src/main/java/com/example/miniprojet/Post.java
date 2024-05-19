package com.example.miniprojet;

public class Post {
    private String username;
    private String postContent;

    public Post(String username, String postContent) {
        this.username = username;
        this.postContent = postContent;

    }
    public String getUsername() {
        return username;
    }
    public String getPostContent() {
        return postContent;
    }

}