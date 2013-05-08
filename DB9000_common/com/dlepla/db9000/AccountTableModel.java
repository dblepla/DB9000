package com.dlepla.db9000;


import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

public class AccountTableModel extends AbstractTableModel {

    /**
     * 
     */
    private static final long serialVersionUID = -1787961292270867038L;
    
    private ArrayList<Account> accounts;

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
}
