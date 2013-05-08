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

    public int getColumnCount() {
        return 3;
    }

    public int getRowCount() {
        return accounts.size();
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
        Account account = accounts.get(rowIndex);

        switch (columnIndex) {
        case 0: return account.accountName;
        case 1: return account.balance;
        case 2: return  account.apr;
        default: return null;
        }
    }
    
    public ArrayList<Account> getAccountsAll()
    {
        
        return accounts;
        
    }
    
    public void setValueAt(Object value, int rowIndex, int columnIndex)
    {
        
        System.out.println();
        System.out.println("ModelTable setValueAs invoked");
        System.out.println("Passed Value: " + (String) value);
        System.out.println("Passed Row Index: " + rowIndex);
        System.out.println("Passed column Index: " + columnIndex);
        
        
        Account account = accounts.get(rowIndex);
        switch (columnIndex) {
            case 0:
            {
                System.out.println(accounts.get(rowIndex).toString());
                account.accountName = (String) value;
                accounts.remove(rowIndex);
                accounts.add(rowIndex, account);
                    
                    System.out.println(accounts.get(rowIndex).toString());
                    Reference.accounts = accounts;
                    break;
            }   
            case 1: 
            {
                System.out.println(accounts.get(rowIndex).toString());
                account.balance = Float.parseFloat((String) value);
                accounts.remove(rowIndex);
                accounts.add(rowIndex, account);
                System.out.println(accounts.get(rowIndex).toString());
                Reference.accounts = accounts;
                break;
            }
            case 2: 
            {
                System.out.println(accounts.get(rowIndex).toString());
                account.apr = Float.parseFloat((String) value);
                accounts.remove(rowIndex);
                accounts.add(rowIndex, account);
                System.out.println(accounts.get(rowIndex).toString());
                Reference.accounts = accounts;
                break;
            }
            default: break;
        
        } 
    }
}
