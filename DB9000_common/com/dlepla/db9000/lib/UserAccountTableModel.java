package com.dlepla.db9000.lib;

import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

//import com.dlepla.db9000.UserAccount;
import com.dlepla.db9000.UserAccount;

public class UserAccountTableModel extends AbstractTableModel
{
    
    /**
     * 
     */
    private static final long serialVersionUID = -1787961292270867038L;
    
    public ArrayList<UserAccount> userAccounts;

    public UserAccountTableModel(ArrayList<UserAccount> userAccounts) {
        this.userAccounts = userAccounts;
        
        
        
    }
    
    public void addNewAccount()
    {
        
        UserAccount newBlankAccount = new UserAccount();
        this.userAccounts.add(newBlankAccount);
        this.fireTableStructureChanged();
        Reference.isSaved = false;
        
    }

    public int getColumnCount() {
        return 2;
    }

    public int getRowCount() {
        return this.userAccounts.size();
    }
    
    public boolean isCellEditable(int rowIndex, int columIndex)
    {
        return true;
    }

    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
        case 0: return "User Name";
        case 1: return "Password";
        default: return "Unknown";
        
        }
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        UserAccount account = this.userAccounts.get(rowIndex);

        switch (columnIndex) {
        case 0: return account.username;
        case 1: 
            {
                
                String pass = new String(account.password);
                return  pass;
                
            }
        default: return null;
        }
    }
    
    public ArrayList<UserAccount> getAccountsAll()
    {
        
        return this.userAccounts;
        
    }
   
    
    public void removeAccount(int row)
    {
        
        if(this.userAccounts.size() <= 1)
        {
            
            JOptionPane
            .showMessageDialog(
                    null,
                    "There must be at least one user account, continuing without removing the selected account.",
                    "Minumin User Account Reached",
                    JOptionPane.INFORMATION_MESSAGE);
               
        }else
        {
            
            this.userAccounts.remove(row);
            
        
        }
        
        Reference.userAccounts = this.userAccounts;
        this.fireTableStructureChanged();
        
        Reference.isSaved = false;
        
    }
    
    public void setValueAt(Object value, int rowIndex, int columnIndex)
    {
        
        
        
        Reference.isSaved = false;
        UserAccount account = userAccounts.get(rowIndex);
        switch (columnIndex) {
            case 0:
            {
               
                account.username = (String) value;
                userAccounts.remove(rowIndex);
                userAccounts.add(rowIndex, account);
                    
                Reference.userAccounts = userAccounts;
                break;
                
            }   
            case 1: 
            {
                
                String tempPass = (String) value;
                account.password = tempPass.toCharArray();
                userAccounts.remove(rowIndex);
                userAccounts.add(rowIndex, account);
                
                Reference.userAccounts = userAccounts;
                break;
                
            }
            default: break;
        
        } 
    }
}
