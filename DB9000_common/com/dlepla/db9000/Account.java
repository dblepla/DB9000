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
   
    
    // Declare account generic variables.
    public String accountName = "Enter Account Name";
    public float balance = 0;
    public float apr = 0;

    public Account(){}
    
    public Account (String name, float bal, float apr)
    {
        
        this.accountName = name;
        this.balance = bal;
        this.apr = apr;
        
    }

    @Override
    public String toString()
    {

        String msg;
        cf = NumberFormat.getCurrencyInstance();
        msg = "Account Name: " + this.accountName;
        msg += ", Balance: " + cf.format(this.balance);
        //msg += ", APR: " + this.apr;
        return msg;
    }
}
