package com.example.temporary_values;

import com.example.objects.Account;
import com.example.testproject2.R;

import java.util.ArrayList;

public class TemporaryAccountList {

    private static ArrayList<Account> list;

    public static void init() {
        list = new ArrayList<>();
        addAccount(new Account(R.drawable.display_picture_image, "Ulysses Simpson", "I'm so useless, but not depressed.", true));
        addAccount(new Account(R.drawable.display_picture_image, "Jichael Mackson", "Hee hee",false));
        addAccount(new Account(R.drawable.display_picture_image, "Freddy Fazbear", "was that the bite of '87?",false));
        addAccount(new Account(R.drawable.display_picture_image, "Freddy Mars", "I'm a rocket ship on my way to Mercury on a collision course. I am a satellite, I'm out of control", true));
        addAccount(new Account(R.drawable.display_picture_image, "Bruno Mercury", "Talking to the moon, tryna get to you",false));
        addAccount(new Account(R.drawable.cookies, "Cookie?", "Cookie?", true));
        addAccount(new Account(R.drawable.display_picture_image, "Mama mo", "Nasaan ka na daw?", true));
        addAccount(new Account(R.drawable.display_picture_image, "José Rizal", "Ang kabataan ang pag-asa ng bayan", false));
        addAccount(new Account(R.drawable.display_picture_image, "Crush", "Hello cutie", true));
        addAccount(new Account(R.drawable.display_picture_image, "Paimon", "How about we explore the area ahead of us later?", true));
        addAccount(new Account(R.drawable.display_picture_image, "Basta kilala mo", "Random account", true));
        addAccount(new Account(R.drawable.display_picture_image, "Basta kilala mo", "Random account", true));
        addAccount(new Account(R.drawable.display_picture_image, "Basta kilala mo", "Random account", true));
        addAccount(new Account(R.drawable.display_picture_image, "Basta kilala mo", "Random account", true));
        addAccount(new Account(R.drawable.display_picture_image, "Basta kilala mo", "Random account", true));
        addAccount(new Account(R.drawable.display_picture_image, "Basta kilala mo", "Random account", true));
        addAccount(new Account(R.drawable.display_picture_image, "Basta kilala mo", "Random account", true));
        addAccount(new Account(R.drawable.display_picture_image, "Basta kilala mo", "Random account", true));
        addAccount(new Account(R.drawable.display_picture_image, "Basta kilala mo", "Random account", true));
        addAccount(new Account(R.drawable.display_picture_image, "Basta kilala mo", "Random account", true));
        addAccount(new Account(R.drawable.display_picture_image, "Basta kilala mo", "Random account", true));
        addAccount(new Account(R.drawable.display_picture_image, "Hindi mo kilala", "Random account", false));
        addAccount(new Account(R.drawable.display_picture_image, "Hindi mo kilala", "Random account", false));
        addAccount(new Account(R.drawable.display_picture_image, "Hindi mo kilala", "Random account", false));
        addAccount(new Account(R.drawable.display_picture_image, "Hindi mo kilala", "Random account", false));
        addAccount(new Account(R.drawable.display_picture_image, "Hindi mo kilala", "Random account", false));
        addAccount(new Account(R.drawable.display_picture_image, "Hindi mo kilala", "Random account", false));
        addAccount(new Account(R.drawable.display_picture_image, "Hindi mo kilala", "Random account", false));
        addAccount(new Account(R.drawable.display_picture_image, "Hindi mo kilala", "Random account", false));
        addAccount(new Account(R.drawable.display_picture_image, "Hindi mo kilala", "Random account", false));
        addAccount(new Account(R.drawable.display_picture_image, "Hindi mo kilala", "Random account", false));
        addAccount(new Account(R.drawable.display_picture_image, "Hindi mo kilala", "Random account", false));
    }

    public static void addAccount(Account account) {
        list.add(account);
    }

    public static Account getAccount(int id) {
        return list.get(id);
    }

    public static int size() {
        return list.size();
    }
}
