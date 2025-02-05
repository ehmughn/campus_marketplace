package com.example.static_classes;

import com.example.objects.Account;
import com.example.temporary_values.TemporaryAccountList;

public class CurrentAccount {
    private static Account account;

    public static Account getAccount() {
        return account;
    }

    public static void setAccount(Account account) {
        CurrentAccount.account = account;
    }

    public static void setAccount(int id) {
        CurrentAccount.account = TemporaryAccountList.getAccount(id);
    }
}
