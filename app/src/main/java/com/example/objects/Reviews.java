package com.example.objects;

import com.example.testproject2.R;

import java.util.Random;

public class Reviews {

    private String name;
    private int stars;
    private String variation;
    private String comment;
    private int image1;
    private int image2;
    private int helpful_likes;

    public Reviews(String name, int stars, String variation, String comment, int image1, int image2, int helpful_likes) {
        this.name = name;
        this.stars = stars;
        this.variation = variation;
        this.comment = comment;
        this.image1 = image1;
        this.image2 = image2;
        this.helpful_likes = helpful_likes;
    };

    public Reviews() {
        Random random1 = new Random();
        Random random2 = new Random();
        this.name = "Freddy Fazbear";
        this.stars = random1.nextInt(5) + 1;
        this.variation = "Chochlet";
        this.comment = "We're no strangers to love. You know the rules and so do I. A full commitment's what I'm thinkin' of. You wouldn't get this from any other guy. I just wanna tell you how I'm feeling. Gotta make you understand. Never gonna give you up. Never gonna let you down. Never gonna run around and desert you. Never gonna make you cry. Never gonna say goodbye. Never gonna tell a lie and hurt you.";
        this.image1 = R.drawable.temp_product_cookies;
        this.image2 = R.drawable.temp_product_burger;
        this.helpful_likes = random2.nextInt(999) + 1;
    }

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

    public int getImage1() {
        return image1;
    }

    public void setImage1(int image1) {
        this.image1 = image1;
    }

    public int getImage2() {
        return image2;
    }

    public void setImage2(int image2) {
        this.image2 = image2;
    }

    public int getHelpful_likes() {
        return helpful_likes;
    }

    public void setHelpful_likes(int helpful_likes) {
        this.helpful_likes = helpful_likes;
    }
}
