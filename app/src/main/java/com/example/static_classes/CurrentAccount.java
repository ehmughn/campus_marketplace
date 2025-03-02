package com.example.static_classes;

import com.example.objects.Account;

public class CurrentAccount {
    private static Account account;

    public static Account getAccount() {
        return account;
    }

    public static void setAccount(Account account) {
        CurrentAccount.account = account;
    }
}
