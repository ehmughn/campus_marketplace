package com.example.static_classes;

import com.example.objects.Category;

import java.util.ArrayList;

public class Categories {

    private static ArrayList<Category> categories;

    public static void init() {
        categories = new ArrayList<>();
        categories.add(new Category("Popular"));
        categories.add(new Category("Following"));
    }

    public static void addCategory(String category) {
        categories.add(new Category(category));
    }

    public static ArrayList<Category> getCategories() {
        return categories;
    }

    public static void setCategories(ArrayList<Category> categories) {
        Categories.categories = categories;
    }
}
