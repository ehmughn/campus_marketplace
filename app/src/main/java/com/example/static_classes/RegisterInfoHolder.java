package com.example.static_classes;

public class RegisterInfoHolder {
    private static String email = "";

    public static void setEmail(String register_email) {
        RegisterInfoHolder.email = register_email;
    }

    public static String getEmail() {
        return email;
    }
}
