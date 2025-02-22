package com.example.static_classes;

public class DatabaseConnectionData {

    private static String host = "192.168.254.106";


    public static String getHost() {
        return host;
    }

    public static void setHost(String host) {
        DatabaseConnectionData.host = host;
    }
}
