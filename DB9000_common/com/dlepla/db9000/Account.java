package com.dlepla.db9000;

import java.text.NumberFormat;

public class Account
{
    
    static NumberFormat cf = NumberFormat.getCurrencyInstance();
    
    // Int to keep track of number of accounts.
    static int numberOfAccounts = 0;
    
    
    // Declare account generic variables.
    String accountName = " ";
    float balance = 0;
    float apr = 0;
    
    
    public Account(String name, float bal, float apr)
    {
        
        numberOfAccounts++;
        
        this.accountName = name;
        this.balance = bal;
        this.apr = apr;
        
    }
    
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
