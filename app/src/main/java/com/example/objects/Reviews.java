package com.example.objects;

import com.example.testproject2.R;

import java.util.Random;

public class Reviews {

    private String name;
    private String image;
    private int stars;
    private String variation;
    private String comment;

    public Reviews(String name, String image, int stars, String variation, String comment) {
        this.name = name;
        this.image = image;
        this.stars = stars;
        this.variation = variation;
        this.comment = comment;
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public String getVariation() {
        return variation;
    }

    public void setVariation(String variation) {
        this.variation = variation;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
