package com.dlepla.db9000;

import java.io.Serializable;
import java.text.NumberFormat;

public class DebtAccount extends Account implements Serializable
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    static NumberFormat cf = NumberFormat.getCurrencyInstance();
    
    public float apr = 0;
    public float monthlyPayment = 0;
    
    
    public DebtAccount() {}
    
    public DebtAccount(String name, float bal, float payment, float apr)
    {
        
        this.accountName = name;
        this.balance = bal;
        this.monthlyPayment = payment;
        this.apr = apr;
    }
    
    
    public String toString()
    {
        
        String msg;
        cf = NumberFormat.getCurrencyInstance();
        msg = "Name: " + this.accountName;
        msg = "Balance: " + cf.format(this.balance);
        msg += ", Monthly Payment: "+ cf.format(this.monthlyPayment);
        msg += ", APR: " + apr;
        return msg;
        
    }
    
}
