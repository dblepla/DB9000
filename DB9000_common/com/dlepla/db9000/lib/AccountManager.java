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
       
        float percentComplete = 0;
        
        if(account.initialBalance == 0)
            return 0;
        else if (account.balance == 0)
            return 100;
        else
        {
            
            if (account instanceof BankAccount)
                percentComplete = ((getTotalSavings()) / Reference.MIN_SAVINGS) * 100;
            else
                percentComplete = ((account.initialBalance - account.balance) / account.initialBalance) * 100;
            
        }
         
        return (int)percentComplete;
            
    }
    
    
    //*************************************************************
    //
    // Method to get the best account to put money in.
    //
    //*************************************************************
    public static Account getBestAccount()
    {
        
        
        if(doesSavingsExist() || doesLoanExist())
        {
            
            if( (doesSavingsExist()) && (getTotalSavings() < Reference.MIN_SAVINGS))
                return getLowestSavingsAccount();
            else if(doesLoanExist())
                return getBestDebtAccount();
            else
                return Reference.bankAccounts.get(0);
                      
        }
        else
            return Reference.bankAccounts.get(0);
        
    }
    
    
    //*************************************************************
    //
    // Method to get the Debt Account with the highest APR
    //
    //*************************************************************
    public static DebtAccount getHighestAprAccount(ArrayList<DebtAccount> lowAccounts)
    {
        DebtAccount highestAPRAccount = lowAccounts.get(0);
        
        for( DebtAccount la : lowAccounts)
        {
           
            if (la.apr > highestAPRAccount.apr)
                highestAPRAccount = la;
            
        }
        
        return highestAPRAccount;
    
    }
    
    
    //*************************************************************
    //
    // Method to check if a Savings account exists.
    //
    //*************************************************************
    public static boolean doesSavingsExist()
    {
        
        boolean savingsExists = false;
        
        for( int i = 0; i < Reference.bankAccounts.size(); i++)
        {
            
            if( Reference.bankAccounts.get(i).bankOrChecking == BankAccount.SAVINGS_ACCOUNT)
                savingsExists = true;
                  
        }
    
        return savingsExists;
        
    }
    
    
    
    //*************************************************************
    //
    // Method to check if a Credit or Loan account exists.
    //
    //*************************************************************
    public static boolean doesLoanExist()
    {
        
        boolean loanExists = false;
        
        if ( Reference.debtAccounts.size() >= 1 && !Reference.debtAccounts.get(0).isNew)
            loanExists = true;
    
        return loanExists;
        
    }
    
    
    //*************************************************************
    //
    // Method to get the total amount in savings accounts.
    //
    //*************************************************************
    public static float getTotalSavings()
    {
        
        float currentSavings = 0;
        
        for( int i = 0; i < Reference.bankAccounts.size(); i++)
        {

            if( Reference.bankAccounts.get(i).bankOrChecking == Reference.BANK_ACCOUNT)
                currentSavings += Reference.bankAccounts.get(i).balance;
            
        }
        
    
        return currentSavings;
        
    }
    
    
    //**********************************************************************
    //
    // Method to get the two lowest debt accounts and if the difference 
    // between their balance is less then 1000 return the account with 
    // the highest APR otherwise return the account with the lowest balance.
    //
    //***********************************************************************
    public static DebtAccount getBestDebtAccount()
    {
        
        for ( DebtAccount da : Reference.debtAccounts)
            System.out.println(da);
        
        ArrayList<DebtAccount> tempDebtAccounts = cloneDebtAccounts(Reference.debtAccounts); 
        
        for ( DebtAccount da : tempDebtAccounts)
            System.out.println(da);
        
        ArrayList<DebtAccount> lowestDebtAccounts = new ArrayList<DebtAccount>(tempDebtAccounts.size());
        
        lowestDebtAccounts.add(tempDebtAccounts.get(0));
        
        if(tempDebtAccounts.size() > 1)
        {
            
            
            
            // cycles through all debt accounts and finds the account1 with the lowest balance that is not 0.
            for( int i = 0; i < tempDebtAccounts.size(); i++)
            {
                if( (tempDebtAccounts.get(i).balance < lowestDebtAccounts.get(0).balance) && (tempDebtAccounts.get(i).balance != 0) )
                {
                    
                    lowestDebtAccounts.add(0, tempDebtAccounts.get(i));
                    tempDebtAccounts.remove(i);
                    
                }
            }
            
            lowestDebtAccounts.add(tempDebtAccounts.get(0));
                
            for( int i = 0; i < tempDebtAccounts.size(); i++)
            {
                if(Math.abs(lowestDebtAccounts.get(0).balance - tempDebtAccounts.get(i).balance) <= 1000 ) 
                    lowestDebtAccounts.add(tempDebtAccounts.get(i));
        
            }
                
            return getHighestAprAccount(lowestDebtAccounts);
                
        }
         else
             return lowestDebtAccounts.get(0);
         
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
    
    //*************************************************************
    //
    // Defines a method which deep copies an arraylist of DebtAccount objects.
    //
    //*************************************************************
    public static ArrayList<DebtAccount> cloneDebtAccounts(ArrayList<DebtAccount> dbAccounts)
    {
        
        ArrayList<DebtAccount> clonedList = new ArrayList<DebtAccount>(dbAccounts.size());
        
        for ( DebtAccount da : dbAccounts )
        {
            
            clonedList.add(new DebtAccount(da));
            
        }
        return clonedList;
        
    }
    
}
