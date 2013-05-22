package com.dlepla.db9000.lib;

import java.util.ArrayList;

import com.dlepla.db9000.Account;
import com.dlepla.db9000.BankAccount;
import com.dlepla.db9000.DebtAccount;

public class AccountManager
{
    
    
    //*************************************************************
    //
    // Method to get the get the percent of payoff completed.
    //
    //*************************************************************
    public static int getPercentCompleted(Account account)
    {
        
        
        System.out.println("Starting Balance: " + account.startingBalance);
        System.out.println("Current Balance: " + account.balance);
        float percentComplete = 0;
        
        if(account.startingBalance == 0)
            return 0;
        else if (account.balance == 0)
            return 100;
        else
        {
            
            if (account instanceof BankAccount)
                percentComplete = ((account.balance) / Reference.MIN_SAVINGS) * 100;
            else
                percentComplete = ((account.startingBalance - account.balance) / account.startingBalance) * 100;
            
            
        }
           
        
        
        System.out.println("Percent Completed: " + percentComplete);
        
        return (int)percentComplete;
            
    }
    
    
    //*************************************************************
    //
    // Method to get the best account to put money in.
    //
    //*************************************************************
    public static Account getBestAccount()
    {
        
        
        //ArrayList<DebtAccount> tempDebtAccounts = Reference.debtAccounts;
        DebtAccount account1 = new DebtAccount();
        DebtAccount account2 = new DebtAccount();
        int account1Index = 0;
        
        if(Reference.debtAccounts.size() > 1)
        {
            
            if( (getTotalSavings() < Reference.MIN_SAVINGS) && !(Reference.bankAccounts.get(0).accountName.equals("Enter Account Name") ))
                return getLowestSavingsAccount();
            else
            {
                
                account1 = Reference.debtAccounts.get(0);
            
                for( int i = 0; i < Reference.debtAccounts.size(); i++)
                {
                    if( (Reference.debtAccounts.get(i).balance < account1.balance) && (Reference.debtAccounts.get(i).balance != 0) )
                    {
                        
                        account1Index = i;
                        account1 = Reference.debtAccounts.get(i);
                        System.out.println("Account1: " + account1.toString());
                        
                    }
                }
                       
                    
                if(account1Index + 1 > Reference.debtAccounts.size() - 1)
                    account2 = Reference.debtAccounts.get(account1Index - 1);
                else if(account1Index - 1 < 0)
                    account2 = Reference.debtAccounts.get(account1Index + 1);
                    
                for( int i = 0; i < Reference.debtAccounts.size(); i++)
                {
                    if( (i != account1Index) && (Reference.debtAccounts.get(i).balance < account2.balance) && ( Reference.debtAccounts.get(i).balance != 0) )
                    {
                            
                        account2 = Reference.debtAccounts.get(i);
                        System.out.println("Account2: " + account2.toString());
                            
                    }
                }
                    
                    
                    
                    
                    
                    if( Math.abs(account1.balance - account2.balance) <= 1000)
                    {
                        
                        System.out.println("Difference Between Accounts: " + (account1.balance - account2.balance));
                        
                        System.out.println("Highest APR Account: " + getHighestAprAccount(account1, account2).toString());
                        return getHighestAprAccount(account1, account2);
                        
                    }
                    else
                        return account1;
                    
                
            }
                
        }
        else
            return Reference.debtAccounts.get(0);
        
    }
    
    
    //*************************************************************
    //
    // Method to get the Debt Account with the highest APR
    //
    //*************************************************************
    public static DebtAccount getHighestAprAccount(DebtAccount account1, DebtAccount account2)
    {
        
        
        if (account1.apr < account2.apr)
            return account2;
        else
            return account1;
    
    
    }
    
    
    //*************************************************************
    //
    // Method to get the total amount in savings accounts.
    //
    //*************************************************************
    public static float getTotalSavings()
    {
        
        
        float currentSavings = 0;
        System.out.println("Total Bank Accounts: " + Reference.bankAccounts.size());
        
        for( int i = 0; i < Reference.bankAccounts.size(); i++)
        {
            
            
            System.out.println("bankOrChecking: " + Reference.bankAccounts.get(i).bankOrChecking);
            if( Reference.bankAccounts.get(i).bankOrChecking == Reference.BANK_ACCOUNT)
                currentSavings += Reference.bankAccounts.get(i).balance;
            
        }
        
        System.out.println("getTotalSavings: " + currentSavings);
        
        return currentSavings;
        
    }
    
    
    //*************************************************************
    //
    // Method to get the savings account with lowest balance .
    //
    //*************************************************************
    public static BankAccount getLowestSavingsAccount()
    {
        
        
        BankAccount lowestSavingsAccount = new BankAccount();
        
        ArrayList<BankAccount> bankAccounts = new ArrayList<BankAccount>();
        
        
        for( int a = 0; a < Reference.bankAccounts.size(); a++)
            if( Reference.bankAccounts.get(a).bankOrChecking == Reference.BANK_ACCOUNT)
                bankAccounts.add(Reference.bankAccounts.get(a));
        
        if (bankAccounts.size() > 0 )
            lowestSavingsAccount = bankAccounts.get(0);
        
        for( int b = 0; b < bankAccounts.size(); b++)
            if( bankAccounts.get(b).balance < lowestSavingsAccount.balance)
                lowestSavingsAccount = bankAccounts.get(b);
            
        
        return lowestSavingsAccount;
        
    }
    
    
    //*************************************************************
    //
    // Defines a method which calculates the total available cash given the total income and bills.
    //
    //*************************************************************
    public static float getTotalCash()
    {
        
        float totalIncome = getTotalMonthlyIncome();
        float totalBills = getTotalMonthlyBills();
        
        return totalIncome - totalBills;
        
    }
    
    
    //*************************************************************
    //
    // Defines a method which calculates the total Monthly bills.
    //
    //*************************************************************
    public static float getTotalMonthlyBills()
    {
        
        float totalPaymentBills = 0;
        float totalBills = 0;
        
        
        for ( int i = 0; i <= Reference.debtAccounts.size() - 1; i++)
            totalPaymentBills += Reference.debtAccounts.get(i).monthlyPayment;
        
        for ( int i = 0; i <= Reference.billAccounts.size() - 1; i++)
            totalBills += Reference.billAccounts.get(i).monthlyPayment;
        
        return totalPaymentBills + totalBills;
        
    }
    
    
    //*************************************************************
    //
    // Defines a method which calculates the total Monthly Income.
    //
    //*************************************************************
    public static float getTotalMonthlyIncome()
    {
        
        float totalIncome = 0;
        
        
        for ( int i = 0; i <= Reference.bankAccounts.size() - 1; i++)
            totalIncome += Reference.bankAccounts.get(i).monthlyIncome;
        
        return totalIncome;
        
    }
}
