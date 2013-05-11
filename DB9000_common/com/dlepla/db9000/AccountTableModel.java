package com.dlepla.db9000;


import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;
import com.dlepla.db9000.lib.Reference;

public class AccountTableModel extends AbstractTableModel {

    /**
     * 
     */
    private static final long serialVersionUID = -1787961292270867038L;
    
    public ArrayList<Account> accounts;
    
    

    public AccountTableModel(ArrayList<Account> accounts) {
        this.accounts = accounts;
    }
    
    public void addNewAccount()
    {
        
        Account newBlankAccount = new Account("Enter description", 0, 0);
        this.accounts.add(newBlankAccount);
        this.fireTableStructureChanged();
        Reference.m.getColumn(1).setCellRenderer(NumberRenderer.getCurrencyRenderer());
        Reference.isSaved = false;
        
    }

    public int getColumnCount() {
        return 3;
    }

    public int getRowCount() {
        return this.accounts.size();
    }
    
    public boolean isCellEditable(int rowIndex, int columIndex)
    {
        return true;
    }

    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
        case 0: return "Account Name";
        case 1: return "balance";
        case 2: return "APR";
        default: return "Unknown";
        
        }
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        Account account = this.accounts.get(rowIndex);

        switch (columnIndex) {
        case 0: return account.accountName;
        case 1: return account.balance;
        case 2: return  account.apr;
        default: return null;
        }
    }
    
    public ArrayList<Account> getAccountsAll()
    {
        
        return this.accounts;
        
    }
   
    
    public void removeAccount(int row)
    {
        
        if(this.accounts.size() <= 1)
        {
            
            Account newBlankAccount = new Account("Enter description", 0, 0);
            this.accounts.add(newBlankAccount);
            this.accounts.remove(0);
               
        }else
        {
            
            this.accounts.remove(row);
            
        
        }
        
        Reference.accounts = this.accounts;
        this.fireTableStructureChanged();
        Reference.m.getColumn(1).setCellRenderer(NumberRenderer.getCurrencyRenderer());
        
        Reference.isSaved = false;
        
    }
    
    public void setValueAt(Object value, int rowIndex, int columnIndex)
    {
        
        
        
        Reference.isSaved = false;
        Account account = accounts.get(rowIndex);
        switch (columnIndex) {
            case 0:
            {
               
                account.accountName = (String) value;
                accounts.remove(rowIndex);
                accounts.add(rowIndex, account);
                    
                
                    Reference.accounts = accounts;
                    break;
            }   
            case 1: 
            {
                
                account.balance = Float.parseFloat((String) value);
                accounts.remove(rowIndex);
                accounts.add(rowIndex, account);
                
                Reference.accounts = accounts;
                break;
            }
            case 2: 
            {
               
                account.apr = Float.parseFloat((String) value);
                accounts.remove(rowIndex);
                accounts.add(rowIndex, account);
                
                Reference.accounts = accounts;
                break;
            }
            default: break;
        
        } 
    }
}
