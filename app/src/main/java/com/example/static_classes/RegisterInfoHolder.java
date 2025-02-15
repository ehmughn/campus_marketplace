package com.example.static_classes;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterInfoHolder {
    private static String email = "";
    private static String password = "";
    private static String firstName = "";
    private static String lastName = "";
    private static String username = "";

    public static void setEmail(String register_email) {
        RegisterInfoHolder.email = register_email;
    }

    public static String getEmail() {
        return email;
    }

    public static String getEmailName() {
        return email.substring(0, email.indexOf("@")).trim();
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        RegisterInfoHolder.password = password;
    }

    public static String getFirstName() {
        return firstName;
    }

    public static void setFirstName(String firstName) {
        RegisterInfoHolder.firstName = firstName;
    }

    public static String getLastName() {
        return lastName;
    }

    public static void setLastName(String lastName) {
        RegisterInfoHolder.lastName = lastName;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        RegisterInfoHolder.username = username;
    }
}
