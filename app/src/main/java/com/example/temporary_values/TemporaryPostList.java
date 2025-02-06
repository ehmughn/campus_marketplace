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

        addPost(new Post(R.drawable.temp_product_cookies, 25, "Cookies", 0, "Cookies! I want cookies! I want cookies! I want cookies! I want cookies! I want cookies! I want cookies! I want cookies! I want cookies! I want cookies! I want cookies! I want cookies! I want cookies! I want cookies! I want cookies! I want cookies! I want cookies! " , 50, random.nextInt(TemporaryAccountList.size()), example_reviews));
        addPost(new Post(R.drawable.temp_product_notes, 250, "Notes", 1, "We're no strangers to love. You know the rules and so do I. A full commitment's what I'm thinkin' of. You wouldn't get this from any other guy. I just wanna tell you how I'm feeling. Gotta make you understand. Never gonna give you up. Never gonna let you down. Never gonna run around and desert you. Never gonna make you cry. Never gonna say goodbye. Never gonna tell a lie and hurt you.", 5, random.nextInt(TemporaryAccountList.size()), example_reviews));
        addPost(new Post(R.drawable.temp_product_uniform, 400, "Uniform", 2, "Lagi daw kasi out of stock yung bulldogs exchange so ito binebenta ko na yung sakin", 10, random.nextInt(TemporaryAccountList.size()), example_reviews));
        addPost(new Post(R.drawable.temp_product_burger, 70, "Burger", 0, "I hate jollibee, I hate mcdonalds, I hate burger king, I hate zarks, I hate popeyes, I hate angels burger, Ikaw lang ang gusto ko",30,random.nextInt(TemporaryAccountList.size()), example_reviews));
        addPost(new Post(R.drawable.temp_product_1, 400, "Product 1", 2, "Lagi daw kasi out of stock yung bulldogs exchange so ito binebenta ko na yung sakin", 10,random.nextInt(TemporaryAccountList.size()), example_reviews));
        addPost(new Post(R.drawable.temp_product_2, 400, "Product 2", 2, "Lagi daw kasi out of stock yung bulldogs exchange so ito binebenta ko na yung sakin", 10,random.nextInt(TemporaryAccountList.size()), example_reviews));
        addPost(new Post(R.drawable.temp_product_uniform, 400, "Uniform", 2, "Lagi daw kasi out of stock yung bulldogs exchange so ito binebenta ko na yung sakin", 10,random.nextInt(TemporaryAccountList.size()), example_reviews));
        addPost(new Post(R.drawable.temp_product_3, 400, "Product 3", 2, "Lagi daw kasi out of stock yung bulldogs exchange so ito binebenta ko na yung sakin", 10,0, example_reviews));
        addPost(new Post(R.drawable.temp_product_cookies, 400, "Cookies", 2, "Cookies everyday", 10,0, example_reviews));
        addPost(new Post(R.drawable.temp_product_4, 400, "Product 4", 2, "Lagi daw kasi out of stock yung bulldogs exchange so ito binebenta ko na yung sakin", 10,random.nextInt(TemporaryAccountList.size()), example_reviews));
        addPost(new Post(R.drawable.temp_product_5, 400, "Product 5", 2, "Lagi daw kasi out of stock yung bulldogs exchange so ito binebenta ko na yung sakin", 10,random.nextInt(TemporaryAccountList.size()), example_reviews));
        addPost(new Post(R.drawable.temp_product_6, 400, "Product 6", 2, "Lagi daw kasi out of stock yung bulldogs exchange so ito binebenta ko na yung sakin", 10,random.nextInt(TemporaryAccountList.size()), example_reviews));
        addPost(new Post(R.drawable.temp_product_7, 400, "Product 7", 2, "Lagi daw kasi out of stock yung bulldogs exchange so ito binebenta ko na yung sakin", 10,random.nextInt(TemporaryAccountList.size()), example_reviews));
        addPost(new Post(R.drawable.temp_product_8, 400, "Product 8", 2, "Lagi daw kasi out of stock yung bulldogs exchange so ito binebenta ko na yung sakin", 10,random.nextInt(TemporaryAccountList.size()), example_reviews));
        addPost(new Post(R.drawable.temp_product_1, 400, "Product 1", 2, "Lagi daw kasi out of stock yung bulldogs exchange so ito binebenta ko na yung sakin", 10,random.nextInt(TemporaryAccountList.size()), example_reviews));
        addPost(new Post(R.drawable.temp_product_2, 400, "Product 2", 2, "Lagi daw kasi out of stock yung bulldogs exchange so ito binebenta ko na yung sakin", 10,random.nextInt(TemporaryAccountList.size()), example_reviews));
        addPost(new Post(R.drawable.temp_product_cookies, 25, "Cookies", 0, "Cookies! I want cookies! I want cookies! I want cookies! I want cookies! I want cookies! I want cookies! I want cookies! I want cookies! I want cookies! I want cookies! I want cookies! I want cookies! I want cookies! I want cookies! I want cookies! I want cookies! " , 50, random.nextInt(TemporaryAccountList.size()), example_reviews));
        addPost(new Post(R.drawable.temp_product_notes, 250, "Notes", 1, "We're no strangers to love. You know the rules and so do I. A full commitment's what I'm thinkin' of. You wouldn't get this from any other guy. I just wanna tell you how I'm feeling. Gotta make you understand. Never gonna give you up. Never gonna let you down. Never gonna run around and desert you. Never gonna make you cry. Never gonna say goodbye. Never gonna tell a lie and hurt you.", 5, random.nextInt(TemporaryAccountList.size()), example_reviews));
        addPost(new Post(R.drawable.temp_product_uniform, 400, "Uniform", 2, "Lagi daw kasi out of stock yung bulldogs exchange so ito binebenta ko na yung sakin", 10, random.nextInt(TemporaryAccountList.size()), example_reviews));
        addPost(new Post(R.drawable.temp_product_burger, 70, "Burger", 0, "I hate jollibee, I hate mcdonalds, I hate burger king, I hate zarks, I hate popeyes, I hate angels burger, Ikaw lang ang gusto ko",30,random.nextInt(TemporaryAccountList.size()), example_reviews));
        addPost(new Post(R.drawable.temp_product_1, 400, "Product 1", 2, "Lagi daw kasi out of stock yung bulldogs exchange so ito binebenta ko na yung sakin", 10,random.nextInt(TemporaryAccountList.size()), example_reviews));
        addPost(new Post(R.drawable.temp_product_2, 400, "Product 2", 2, "Lagi daw kasi out of stock yung bulldogs exchange so ito binebenta ko na yung sakin", 10,random.nextInt(TemporaryAccountList.size()), example_reviews));
        addPost(new Post(R.drawable.temp_product_uniform, 400, "Uniform", 2, "Lagi daw kasi out of stock yung bulldogs exchange so ito binebenta ko na yung sakin", 10,random.nextInt(TemporaryAccountList.size()), example_reviews));
        addPost(new Post(R.drawable.temp_product_3, 400, "Product 3", 2, "Lagi daw kasi out of stock yung bulldogs exchange so ito binebenta ko na yung sakin", 10,0, example_reviews));
        addPost(new Post(R.drawable.temp_product_cookies, 400, "Cookies", 2, "Cookies everyday", 10,0, example_reviews));
        addPost(new Post(R.drawable.temp_product_4, 400, "Product 4", 2, "Lagi daw kasi out of stock yung bulldogs exchange so ito binebenta ko na yung sakin", 10,random.nextInt(TemporaryAccountList.size()), example_reviews));
        addPost(new Post(R.drawable.temp_product_5, 400, "Product 5", 2, "Lagi daw kasi out of stock yung bulldogs exchange so ito binebenta ko na yung sakin", 10,random.nextInt(TemporaryAccountList.size()), example_reviews));
        addPost(new Post(R.drawable.temp_product_6, 400, "Product 6", 2, "Lagi daw kasi out of stock yung bulldogs exchange so ito binebenta ko na yung sakin", 10,random.nextInt(TemporaryAccountList.size()), example_reviews));
        addPost(new Post(R.drawable.temp_product_7, 400, "Product 7", 2, "Lagi daw kasi out of stock yung bulldogs exchange so ito binebenta ko na yung sakin", 10,random.nextInt(TemporaryAccountList.size()), example_reviews));
        addPost(new Post(R.drawable.temp_product_8, 400, "Product 8", 2, "Lagi daw kasi out of stock yung bulldogs exchange so ito binebenta ko na yung sakin", 10,random.nextInt(TemporaryAccountList.size()), example_reviews));
        addPost(new Post(R.drawable.temp_product_1, 400, "Product 1", 2, "Lagi daw kasi out of stock yung bulldogs exchange so ito binebenta ko na yung sakin", 10,random.nextInt(TemporaryAccountList.size()), example_reviews));
        addPost(new Post(R.drawable.temp_product_2, 400, "Product 2", 2, "Lagi daw kasi out of stock yung bulldogs exchange so ito binebenta ko na yung sakin", 10,random.nextInt(TemporaryAccountList.size()), example_reviews));
        addPost(new Post(R.drawable.temp_product_cookies, 25, "Cookies", 0, "Cookies! I want cookies! I want cookies! I want cookies! I want cookies! I want cookies! I want cookies! I want cookies! I want cookies! I want cookies! I want cookies! I want cookies! I want cookies! I want cookies! I want cookies! I want cookies! I want cookies! " , 50, random.nextInt(TemporaryAccountList.size()), example_reviews));
        addPost(new Post(R.drawable.temp_product_notes, 250, "Notes", 1, "We're no strangers to love. You know the rules and so do I. A full commitment's what I'm thinkin' of. You wouldn't get this from any other guy. I just wanna tell you how I'm feeling. Gotta make you understand. Never gonna give you up. Never gonna let you down. Never gonna run around and desert you. Never gonna make you cry. Never gonna say goodbye. Never gonna tell a lie and hurt you.", 5, random.nextInt(TemporaryAccountList.size()), example_reviews));
        addPost(new Post(R.drawable.temp_product_uniform, 400, "Uniform", 2, "Lagi daw kasi out of stock yung bulldogs exchange so ito binebenta ko na yung sakin", 10, random.nextInt(TemporaryAccountList.size()), example_reviews));
        addPost(new Post(R.drawable.temp_product_burger, 70, "Burger", 0, "I hate jollibee, I hate mcdonalds, I hate burger king, I hate zarks, I hate popeyes, I hate angels burger, Ikaw lang ang gusto ko",30,random.nextInt(TemporaryAccountList.size()), example_reviews));
        addPost(new Post(R.drawable.temp_product_1, 400, "Product 1", 2, "Lagi daw kasi out of stock yung bulldogs exchange so ito binebenta ko na yung sakin", 10,random.nextInt(TemporaryAccountList.size()), example_reviews));
        addPost(new Post(R.drawable.temp_product_2, 400, "Product 2", 2, "Lagi daw kasi out of stock yung bulldogs exchange so ito binebenta ko na yung sakin", 10,random.nextInt(TemporaryAccountList.size()), example_reviews));
        addPost(new Post(R.drawable.temp_product_uniform, 400, "Uniform", 2, "Lagi daw kasi out of stock yung bulldogs exchange so ito binebenta ko na yung sakin", 10,random.nextInt(TemporaryAccountList.size()), example_reviews));
        addPost(new Post(R.drawable.temp_product_3, 400, "Product 3", 2, "Lagi daw kasi out of stock yung bulldogs exchange so ito binebenta ko na yung sakin", 10,0, example_reviews));
        addPost(new Post(R.drawable.temp_product_cookies, 400, "Cookies", 2, "Cookies everyday", 10,0, example_reviews));
        addPost(new Post(R.drawable.temp_product_4, 400, "Product 4", 2, "Lagi daw kasi out of stock yung bulldogs exchange so ito binebenta ko na yung sakin", 10,random.nextInt(TemporaryAccountList.size()), example_reviews));
        addPost(new Post(R.drawable.temp_product_5, 400, "Product 5", 2, "Lagi daw kasi out of stock yung bulldogs exchange so ito binebenta ko na yung sakin", 10,random.nextInt(TemporaryAccountList.size()), example_reviews));
        addPost(new Post(R.drawable.temp_product_6, 400, "Product 6", 2, "Lagi daw kasi out of stock yung bulldogs exchange so ito binebenta ko na yung sakin", 10,random.nextInt(TemporaryAccountList.size()), example_reviews));
        addPost(new Post(R.drawable.temp_product_7, 400, "Product 7", 2, "Lagi daw kasi out of stock yung bulldogs exchange so ito binebenta ko na yung sakin", 10,random.nextInt(TemporaryAccountList.size()), example_reviews));
        addPost(new Post(R.drawable.temp_product_8, 400, "Product 8", 2, "Lagi daw kasi out of stock yung bulldogs exchange so ito binebenta ko na yung sakin", 10,random.nextInt(TemporaryAccountList.size()), example_reviews));
        addPost(new Post(R.drawable.temp_product_1, 400, "Product 1", 2, "Lagi daw kasi out of stock yung bulldogs exchange so ito binebenta ko na yung sakin", 10,random.nextInt(TemporaryAccountList.size()), example_reviews));
        addPost(new Post(R.drawable.temp_product_2, 400, "Product 2", 2, "Lagi daw kasi out of stock yung bulldogs exchange so ito binebenta ko na yung sakin", 10,random.nextInt(TemporaryAccountList.size()), example_reviews));
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
