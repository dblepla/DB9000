package com.dlepla.db9000.lib;

import java.text.NumberFormat;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import com.dlepla.db9000.DebtAccount;


public class DebtAccountTableModel extends AbstractTableModel {

    /**
     * 
     */
    private static final long serialVersionUID = -1787961292270867038L;
    
    public ArrayList<DebtAccount> debtAccounts;
    
    NumberFormat cf = NumberFormat.getCurrencyInstance();

    public DebtAccountTableModel(ArrayList<DebtAccount> debtAccounts) {
        this.debtAccounts = debtAccounts;
        
        
        
    }
    
    public void addNewAccount()
    {
        
        DebtAccount newBlankAccount = new DebtAccount();
        this.debtAccounts.add(newBlankAccount);
        this.fireTableStructureChanged();
        Reference.debtTableColumnModel.getColumn(1).setCellRenderer(NumberRenderer.getCurrencyRenderer());
        Reference.debtTableColumnModel.getColumn(2).setCellRenderer(NumberRenderer.getCurrencyRenderer());
        Reference.isSaved = false;
        
    }

    public int getColumnCount() {
        return 4;
    }

    public int getRowCount() {
        return this.debtAccounts.size();
    }
    
    public boolean isCellEditable(int rowIndex, int columIndex)
    {
        return true;
    }

    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
        case 0: return "Account Name";
        case 1: return "Balance";
        case 2: return "Monthly Payment";
        case 3: return "APR";
        default: return "Unknown";
        
        }
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        DebtAccount account = this.debtAccounts.get(rowIndex);

        switch (columnIndex) {
        case 0: return account.accountName;
        case 1: return  account.balance;
        case 2: return  account.monthlyPayment;
        case 3: return account.apr;
        default: return null;
        }
    }
    
    public ArrayList<DebtAccount> getAccountsAll()
    {
        
        return this.debtAccounts;
        
    }
   
    
    public void removeAccount(int row)
    {
        
        if(this.debtAccounts.size() <= 1)
        {
            
            DebtAccount newBlankAccount = new DebtAccount();
            this.debtAccounts.add(newBlankAccount);
            this.debtAccounts.remove(0);
               
        }else
        {
            
            this.debtAccounts.remove(row);
            
        
        }
        
        Reference.debtAccounts = this.debtAccounts;
        this.fireTableStructureChanged();
        Reference.debtTableColumnModel.getColumn(1).setCellRenderer(NumberRenderer.getCurrencyRenderer());
        Reference.debtTableColumnModel.getColumn(2).setCellRenderer(NumberRenderer.getCurrencyRenderer());
        
        Reference.isSaved = false;
        
    }
    
    public void setValueAt(Object value, int rowIndex, int columnIndex)
    {
        
        
        
        Reference.isSaved = false;
        DebtAccount account = debtAccounts.get(rowIndex);
        switch (columnIndex) {
            case 0:
            {
               
                account.accountName = (String) value;
                debtAccounts.remove(rowIndex);
                debtAccounts.add(rowIndex, account);
                    
                
                    Reference.debtAccounts = debtAccounts;
                    break;
            }   
            case 1: 
            {
                
                
                if(account.isNew)
                {
                    
                    account.balance = Float.parseFloat((String) value);
                    account.startingBalance = Float.parseFloat((String) value);  
                    account.isNew = false;
                    
                }
                else
                    account.balance = Float.parseFloat((String) value);
                
                
                if(account.balance <= 0)
                    account.isNew = true;
                
                debtAccounts.remove(rowIndex);
                debtAccounts.add(rowIndex, account);
                
                Reference.debtAccounts = debtAccounts;
                break;
                
            }
            case 2: 
            {
                account.monthlyPayment = Float.parseFloat((String) value);
                debtAccounts.remove(rowIndex);
                debtAccounts.add(rowIndex, account);
                
                Reference.debtAccounts = debtAccounts;
                Reference.monthlyBillsLabel.setText("Monthly Bills: " + cf.format(AccountManager.getTotalMonthlyBills()));
                break;
            }
            case 3:
            {
                
                account.apr = Float.parseFloat((String) value);
                debtAccounts.remove(rowIndex);
                debtAccounts.add(rowIndex, account);
                
                Reference.debtAccounts = debtAccounts;
                break;
                
            }
            default: break;
        
        } 
    }
}
