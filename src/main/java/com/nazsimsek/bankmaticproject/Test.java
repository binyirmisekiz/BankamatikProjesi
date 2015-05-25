package com.nazsimsek.bankmaticproject;

import java.io.IOException;

/**
 * Created by naz on 22.5.2015.
 */
public class Test {
    public static void main(String[] args) throws IOException {


        BankMatic b = new BankMatic();

        System.out.println("Sistemden cikis icin 0' basin");

        b.setMoney();
        int x = b.inputInt();


        while (x != 0) {
           b.account(x);
         x = b.inputInt();
        }

    }


}
