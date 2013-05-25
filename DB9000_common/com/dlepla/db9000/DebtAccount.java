package com.dlepla.db9000;

import java.io.Serializable;
import java.text.NumberFormat;

public class DebtAccount extends Account implements Serializable, Cloneable
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
    
    public DebtAccount(String name, float bal, float payment, float apr, float initBal)
    {
        
        this.accountName = name;
        this.balance = bal;
        this.monthlyPayment = payment;
        this.apr = apr;
        this.initialBalance = initBal;
        
    }
    
    public DebtAccount(DebtAccount dAccount)
    {
        
        this.accountName = dAccount.accountName;
        this.balance = dAccount.balance;
        this.monthlyPayment = dAccount.monthlyPayment;
        this.apr = dAccount.apr;
        this.initialBalance = dAccount.initialBalance;
        
    }
    
    
    public String toString()
    {
        
        String msg;
        cf = NumberFormat.getCurrencyInstance();
        msg = "Name: " + this.accountName;
        msg += ", Init Balance: " + cf.format(this.initialBalance);
        msg += ", Balance: " + cf.format(this.balance);
        msg += ", Monthly Payment: "+ cf.format(this.monthlyPayment);
        msg += ", APR: " + apr;
        return msg;
        
    }
    
}
