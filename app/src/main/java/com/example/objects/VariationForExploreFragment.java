package com.example.objects;

public class VariationForExploreFragment {
    private int post_id;
    private int variant_id;
    private String image;

    public VariationForExploreFragment(int post_id, int variant_id, String image) {
        this.post_id = post_id;
        this.variant_id = variant_id;
        this.image = image;
    }

    public int getPost_id() {
        return post_id;
    }

    public void setPost_id(int post_id) {
        this.post_id = post_id;
    }

    public int getVariant_id() {
        return variant_id;
    }

    public void setVariant_id(int variant_id) {
        this.variant_id = variant_id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
