package com.example.firebasecrud;

import com.google.firebase.database.Exclude;

public class Character {

    private String key;
    private String name;
    private String anime;
    private String description;
    private int url;

    public Character() {
    }

    public Character(String name, String anime, String description, int url) {
        this.name = name;
        this.anime = anime;
        this.description = description;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAnime() {
        return anime;
    }

    public void setAnime(String anime) {
        this.anime = anime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getUrl() {
        return url;
    }

    public void setUrl(int url) {
        this.url = url;
    }

    @Exclude
    public String getKey() {
        return key;
    }

    @Exclude
    public void setKey(String key) {
        this.key = key;
    }
}
