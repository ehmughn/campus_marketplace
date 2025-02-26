package com.example.temporary_values;

import com.example.objects.Account;
import com.example.testproject2.R;

import java.util.ArrayList;

public class TemporaryAccountList {

    private static ArrayList<Account> list;

    public static void init() {
        list = new ArrayList<>();
//        addAccount(new Account(10, R.drawable.temp_pfp, "Ulysses Simpson", "I'm so useless, but not depressed."));
//        addAccount(new Account(10, R.drawable.temp_pfp1, "Jichael Mackson", "Hee hee"));
//        addAccount(new Account(12, R.drawable.temp_pfp, "Freddy Fazbear", "was that the bite of '87?"));
//        addAccount(new Account(13, R.drawable.temp_pfp2, "Freddy Mars", "I'm a rocket ship on my way to Mercury on a collision course. I am a satellite, I'm out of control"));
//        addAccount(new Account(14, R.drawable.temp_pfp3, "Bruno Mercury", "Talking to the moon, tryna get to you"));
//        addAccount(new Account(15, R.drawable.temp_product_cookies, "Cookie?", "Cookie?"));
//        addAccount(new Account(16, R.drawable.temp_pfp4, "Mama mo", "Nasaan ka na daw?"));
//        addAccount(new Account(17, R.drawable.temp_pfp5, "José Rizal", "Ang kabataan ang pag-asa ng bayan"));
//        addAccount(new Account(18, R.drawable.temp_pfp, "Crush", "Hello cutie"));
//        addAccount(new Account(19, R.drawable.temp_pfp1, "Paimon", "How about we explore the area ahead of us later?"));
//        addAccount(new Account(20, R.drawable.temp_pfp2, "Basta kilala mo", "Random account"));
//        addAccount(new Account(21, R.drawable.temp_pfp1, "Hindi mo kilala", "Random account"));
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
