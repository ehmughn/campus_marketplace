package com.example.static_classes;

import com.example.objects.Account;
import com.example.temporary_values.TemporaryAccountList;

public class ShowCurrentProfile {

    private static Account account;

    public static Account getAccount() {
        return account;
    }

    public static void setAccount(Account account) {
        ShowCurrentProfile.account = account;
    }

    public static void setAccount(int id) {
        ShowCurrentProfile.account = TemporaryAccountList.getAccount(id);
    }
}
