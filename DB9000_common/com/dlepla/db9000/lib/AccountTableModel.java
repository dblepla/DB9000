package com.dlepla.db9000.lib;


import java.util.ArrayList;

import javax.swing.DefaultCellEditor;
import javax.swing.table.AbstractTableModel;
import com.dlepla.db9000.BankAccount;

public class AccountTableModel extends AbstractTableModel {

    /**
     * 
     */
    private static final long serialVersionUID = -1787961292270867038L;
    
    public ArrayList<BankAccount> accounts;
    
    

    public AccountTableModel(ArrayList<BankAccount> accounts) {
        this.accounts = accounts;
        
        
        
    }
    
    public void addNewAccount()
    {
        
        BankAccount newBlankAccount = new BankAccount();
        this.accounts.add(newBlankAccount);
        this.fireTableStructureChanged();
        Reference.bankTableColumnModel.getColumn(2).setCellRenderer(NumberRenderer.getCurrencyRenderer());
        Reference.bankTableColumnModel.getColumn(3).setCellRenderer(NumberRenderer.getCurrencyRenderer());
        Reference.bankTableColumnModel.getColumn(4).setCellRenderer(NumberRenderer.getCurrencyRenderer());
        Reference.typeColumn = Reference.bankAccountTable.getColumnModel().getColumn(1);
        Reference.typeColumn.setCellEditor(new DefaultCellEditor(Reference.BATableComboBox));
        Reference.isSaved = false;
        
    }

    public int getColumnCount() {
        return 5;
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
        case 1: return "Account Type";
        case 2: return "Balance";
        case 3: return "Monthly Income";
        case 4: return "Monthly Bills";
        default: return "Unknown";
        
        }
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        BankAccount account = this.accounts.get(rowIndex);

        switch (columnIndex) {
        case 0: return account.accountName;
        case 1: 
        {
            
            if (account.bankOrChecking == 1)
                return "Checking";
            else if (account.bankOrChecking == 2)
                return "Savings";
            else
                return account.bankOrChecking;
            
        }
        case 2: return  account.balance;
        case 3: return account.monthlyIncome;
        case 4: return account.monthlyBills;
        default: return null;
        }
    }
    
    public ArrayList<BankAccount> getAccountsAll()
    {
        
        return this.accounts;
        
    }
   
    
    public void removeAccount(int row)
    {
        
        if(this.accounts.size() <= 1)
        {
            
            BankAccount newBlankAccount = new BankAccount();
            this.accounts.add(newBlankAccount);
            this.accounts.remove(0);
               
        }else
        {
            
            this.accounts.remove(row);
            
        
        }
        
        Reference.bankAccounts = this.accounts;
        this.fireTableStructureChanged();
        Reference.bankTableColumnModel.getColumn(2).setCellRenderer(NumberRenderer.getCurrencyRenderer());
        Reference.bankTableColumnModel.getColumn(3).setCellRenderer(NumberRenderer.getCurrencyRenderer());
        Reference.bankTableColumnModel.getColumn(4).setCellRenderer(NumberRenderer.getCurrencyRenderer());
        Reference.typeColumn = Reference.bankAccountTable.getColumnModel().getColumn(1);
        Reference.typeColumn.setCellEditor(new DefaultCellEditor(Reference.BATableComboBox));
        
        Reference.isSaved = false;
        
    }
    
    public void setValueAt(Object value, int rowIndex, int columnIndex)
    {
        
        
        
        Reference.isSaved = false;
        BankAccount account = accounts.get(rowIndex);
        switch (columnIndex) {
            case 0:
            {
               
                account.accountName = (String) value;
                accounts.remove(rowIndex);
                accounts.add(rowIndex, account);
                    
                
                    Reference.bankAccounts = accounts;
                    break;
            }   
            case 1: 
            {
                
                
                if(value.equals("Checking"))
                    account.bankOrChecking = 1;
                else if (value.equals("Savings"))
                    account.bankOrChecking = 2;
                else
                    account.bankOrChecking = 1;
                    
                accounts.remove(rowIndex);
                accounts.add(rowIndex, account);
                
                Reference.bankAccounts = accounts;
                break;
            }
            case 2: 
            {
                account.balance = Float.parseFloat((String) value);
                accounts.remove(rowIndex);
                accounts.add(rowIndex, account);
                
                Reference.bankAccounts = accounts;
                break;
            }
            case 3:
            {
                
                account.monthlyIncome = Float.parseFloat((String) value);
                accounts.remove(rowIndex);
                accounts.add(rowIndex, account);
                
                Reference.bankAccounts = accounts;
                break;
                
            }
            case 4:
            {
                
                account.monthlyBills = Float.parseFloat((String) value);
                accounts.remove(rowIndex);
                accounts.add(rowIndex, account);
                
                Reference.bankAccounts = accounts;
                break;
                
            }
            default: break;
        
        } 
    }
}
