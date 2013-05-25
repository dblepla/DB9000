package com.dlepla.db9000.lib;


import java.text.NumberFormat;
import java.util.ArrayList;

import javax.swing.DefaultCellEditor;
import javax.swing.JLabel;
import javax.swing.table.AbstractTableModel;
import com.dlepla.db9000.BankAccount;

public class BankAccountTableModel extends AbstractTableModel {

    /**
     * 
     */
    private static final long serialVersionUID = -1787961292270867038L;
    
    public ArrayList<BankAccount> bankAccounts;
    
    NumberFormat cf = NumberFormat.getCurrencyInstance();
    
    

    public BankAccountTableModel(ArrayList<BankAccount> bankAccounts) {
        this.bankAccounts = bankAccounts;
        
        
        
    }
    
    public void addNewAccount()
    {
        
        BankAccount newBlankAccount = new BankAccount();
        this.bankAccounts.add(newBlankAccount);
        this.fireTableStructureChanged();
        Reference.bankTableColumnModel.getColumn(2).setCellRenderer(NumberRenderer.getCurrencyRenderer());
        Reference.bankTableColumnModel.getColumn(3).setCellRenderer(NumberRenderer.getCurrencyRenderer());
        Reference.typeColumn = Reference.bankAccountTable.getColumnModel().getColumn(1);
        Reference.typeColumn.setCellEditor(new DefaultCellEditor(Reference.BATableComboBox));
        Reference.isSaved = false;
        
    }

    public int getColumnCount() {
        return 4;
    }

    public int getRowCount() {
        return this.bankAccounts.size();
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
        default: return "Unknown";
        
        }
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        BankAccount account = this.bankAccounts.get(rowIndex);

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
        default: return null;
        }
    }
    
    public ArrayList<BankAccount> getAccountsAll()
    {
        
        return this.bankAccounts;
        
    }
   
    
    public void removeAccount(int row)
    {
        
        if(this.bankAccounts.size() <= 1)
        {
            
            BankAccount newBlankAccount = new BankAccount();
            this.bankAccounts.add(newBlankAccount);
            this.bankAccounts.remove(0);
               
        }else
        {
            
            this.bankAccounts.remove(row);
            
        
        }
        
        Reference.bankAccounts = this.bankAccounts;
        this.fireTableStructureChanged();
        Reference.bankTableColumnModel.getColumn(2).setCellRenderer(NumberRenderer.getCurrencyRenderer());
        Reference.bankTableColumnModel.getColumn(3).setCellRenderer(NumberRenderer.getCurrencyRenderer());
        Reference.typeColumn = Reference.bankAccountTable.getColumnModel().getColumn(1);
        Reference.typeColumn.setCellEditor(new DefaultCellEditor(Reference.BATableComboBox));
        
        Reference.isSaved = false;
        
    }
    
    public void setValueAt(Object value, int rowIndex, int columnIndex)
    {
        
        
        
        Reference.isSaved = false;
        BankAccount account = bankAccounts.get(rowIndex);
        switch (columnIndex) {
            case 0:
            {
               
                account.accountName = (String) value;
                bankAccounts.remove(rowIndex);
                bankAccounts.add(rowIndex, account);
                    
                
                    Reference.bankAccounts = bankAccounts;
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
                    
                bankAccounts.remove(rowIndex);
                bankAccounts.add(rowIndex, account);
                
                Reference.bankAccounts = bankAccounts;
                break;
            }
            case 2: 
            {

                account.balance = Float.parseFloat((String) value);
                account.isNew = false;
             
                bankAccounts.remove(rowIndex);
                bankAccounts.add(rowIndex, account);
                
                Reference.bankAccounts = bankAccounts;
                break;
            }
            case 3:
            {
                
                account.monthlyIncome = Float.parseFloat((String) value);
                bankAccounts.remove(rowIndex);
                bankAccounts.add(rowIndex, account);
                
                Reference.bankAccounts = bankAccounts;
                Reference.monthlyIncomeLabel = new JLabel("Monthly Income: " + cf.format(AccountManager.getTotalMonthlyIncome()));
                break;
                
            }
            default: break;
        
        } 
    }
}
