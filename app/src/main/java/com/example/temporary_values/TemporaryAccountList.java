package com.example.temporary_values;

import com.example.objects.Account;
import com.example.testproject2.R;

import java.util.ArrayList;

public class TemporaryAccountList {

    private static ArrayList<Account> list;

    public static void init() {
        list = new ArrayList<>();
        addAccount(new Account(R.drawable.temp_pfp, "Ulysses Simpson", "I'm so useless, but not depressed.", true));
        addAccount(new Account(R.drawable.temp_pfp1, "Jichael Mackson", "Hee hee",false));
        addAccount(new Account(R.drawable.temp_pfp, "Freddy Fazbear", "was that the bite of '87?",false));
        addAccount(new Account(R.drawable.temp_pfp2, "Freddy Mars", "I'm a rocket ship on my way to Mercury on a collision course. I am a satellite, I'm out of control", true));
        addAccount(new Account(R.drawable.temp_pfp3, "Bruno Mercury", "Talking to the moon, tryna get to you",false));
        addAccount(new Account(R.drawable.temp_product_cookies, "Cookie?", "Cookie?", true));
        addAccount(new Account(R.drawable.temp_pfp4, "Mama mo", "Nasaan ka na daw?", true));
        addAccount(new Account(R.drawable.temp_pfp5, "José Rizal", "Ang kabataan ang pag-asa ng bayan", false));
        addAccount(new Account(R.drawable.temp_pfp, "Crush", "Hello cutie", true));
        addAccount(new Account(R.drawable.temp_pfp1, "Paimon", "How about we explore the area ahead of us later?", true));
        addAccount(new Account(R.drawable.temp_pfp2, "Basta kilala mo", "Random account", true));
        addAccount(new Account(R.drawable.temp_pfp3, "Basta kilala mo", "Random account", true));
        addAccount(new Account(R.drawable.temp_pfp4, "Basta kilala mo", "Random account", true));
        addAccount(new Account(R.drawable.temp_pfp5, "Basta kilala mo", "Random account", true));
        addAccount(new Account(R.drawable.temp_pfp, "Basta kilala mo", "Random account", true));
        addAccount(new Account(R.drawable.temp_pfp1, "Basta kilala mo", "Random account", true));
        addAccount(new Account(R.drawable.temp_pfp2, "Basta kilala mo", "Random account", true));
        addAccount(new Account(R.drawable.temp_pfp3, "Basta kilala mo", "Random account", true));
        addAccount(new Account(R.drawable.temp_pfp4, "Basta kilala mo", "Random account", true));
        addAccount(new Account(R.drawable.temp_pfp5, "Basta kilala mo", "Random account", true));
        addAccount(new Account(R.drawable.temp_pfp, "Basta kilala mo", "Random account", true));
        addAccount(new Account(R.drawable.temp_pfp1, "Hindi mo kilala", "Random account", false));
        addAccount(new Account(R.drawable.temp_pfp2, "Hindi mo kilala", "Random account", false));
        addAccount(new Account(R.drawable.temp_pfp3, "Hindi mo kilala", "Random account", false));
        addAccount(new Account(R.drawable.temp_pfp4, "Hindi mo kilala", "Random account", false));
        addAccount(new Account(R.drawable.temp_pfp5, "Hindi mo kilala", "Random account", false));
        addAccount(new Account(R.drawable.temp_pfp, "Hindi mo kilala", "Random account", false));
        addAccount(new Account(R.drawable.temp_pfp1, "Hindi mo kilala", "Random account", false));
        addAccount(new Account(R.drawable.temp_pfp2, "Hindi mo kilala", "Random account", false));
        addAccount(new Account(R.drawable.temp_pfp3, "Hindi mo kilala", "Random account", false));
        addAccount(new Account(R.drawable.temp_pfp4, "Hindi mo kilala", "Random account", false));
        addAccount(new Account(R.drawable.temp_pfp5, "Hindi mo kilala", "Random account", false));
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
