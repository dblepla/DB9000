package com.dlepla.db9000;

import java.io.Serializable;
import java.text.NumberFormat;

public class BankAccount extends Account implements Serializable
{
    
    static NumberFormat cf = NumberFormat.getCurrencyInstance();
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    public static final int CHECKING_ACCOUNT = 1;
    public static final int SAVINGS_ACCOUNT = 2;
    
    public float monthlyIncome = 0;
    public float monthlyBills = 0;
    public int bankOrChecking = CHECKING_ACCOUNT; 
    
    public BankAccount(){}
    
    public BankAccount(String name, float bal, float income, float bills, int bankOrChecking)
    {
        
        this.accountName = name;
        this.balance = bal;
        this.monthlyIncome = income;
        this.monthlyBills = bills;
        this.bankOrChecking = bankOrChecking;
        
    }
    
    public String toString()
    {
        
        String type = " ";
        
        if(bankOrChecking == CHECKING_ACCOUNT)
            type = "Checking";
        else
            type = "Savings";
        
        String msg;
        cf = NumberFormat.getCurrencyInstance();
        msg = "Account Name: " + this.accountName;
        msg += ", Account Type: " + type; 
        msg += ", Balance: " + cf.format(this.balance);
        msg += ", Monthly Income: "+ cf.format(this.monthlyIncome);
        msg += ", Monthly Bills: "+ cf.format(this.monthlyBills);
       
        return msg;
        
    }
}
