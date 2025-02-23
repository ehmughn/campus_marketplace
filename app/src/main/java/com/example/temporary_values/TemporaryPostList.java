package com.example.temporary_values;

import com.example.objects.Post;
import com.example.objects.Reviews;
import com.example.testproject2.R;

import java.util.ArrayList;
import java.util.Random;

public class TemporaryPostList {

    private static ArrayList<Post> list;
    private static ArrayList<Reviews> example_reviews;

    public static void init() {
        list = new ArrayList<>();

        example_reviews = new ArrayList<>();
        example_reviews.add(new Reviews());
        example_reviews.add(new Reviews());
        example_reviews.add(new Reviews());
        example_reviews.add(new Reviews());
        example_reviews.add(new Reviews());
        example_reviews.add(new Reviews());
        example_reviews.add(new Reviews());

        Random random = new Random();
    }

    public static void addPost(Post post) {
        list.add(post);
    }

    public static Post getPost(int id) {
        return list.get(id);
    }

    public static int size() {
        return list.size();
    }
}
