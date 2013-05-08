package com.dlepla.db9000;

import java.io.Serializable;
import java.text.NumberFormat;

public class Account implements Serializable
{
 
    /**
     * 
     */
    private static final long serialVersionUID = 2L;
    static NumberFormat cf = NumberFormat.getCurrencyInstance();
    // Int to keep track of number of accounts.
    static int numberOfAccounts = 0;
    // Declare account generic variables.
    public String accountName = " ";
    public float balance = 0;
    public float apr = 0;

    public Account(String name, float bal, float apr)
    {

        numberOfAccounts++;
        this.accountName = name;
        this.balance = bal;
        this.apr = apr;
    }

    @Override
    public String toString()
    {

        String msg;
        cf = NumberFormat.getCurrencyInstance();
        msg = "Account Name: " + accountName;
        msg += ", Balance: " + cf.format(balance);
        msg += ", APR: " + cf.format(apr);
        return msg;
    }
}
