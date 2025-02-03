package com.example.static_classes;

public class RegisterInfoHolder {
    private static String email = "";
    private static String password = "";

    public static void setEmail(String register_email) {
        RegisterInfoHolder.email = register_email;
    }

    public static String getEmail() {
        return email;
    }

    public static void setPassword(String password) {
        RegisterInfoHolder.password = password;
    }

    public static String getPassword() {
        return password;
    }
}
