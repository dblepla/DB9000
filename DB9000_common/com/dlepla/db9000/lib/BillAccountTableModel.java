package com.dlepla.db9000.lib;

import java.text.NumberFormat;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import com.dlepla.db9000.BillAccount;

public class BillAccountTableModel extends AbstractTableModel
{
    
    /**
     * 
     */
    private static final long serialVersionUID = -1787961292270867038L;
    
    public ArrayList<BillAccount> billAccounts;
    
    NumberFormat cf = NumberFormat.getCurrencyInstance();

    public BillAccountTableModel(ArrayList<BillAccount> billAccounts) {
        this.billAccounts = billAccounts;
        
        
        
    }
    
    public void addNewAccount()
    {
        
        BillAccount newBlankAccount = new BillAccount();
        this.billAccounts.add(newBlankAccount);
        this.fireTableStructureChanged();
        Reference.debtTableColumnModel.getColumn(1).setCellRenderer(NumberRenderer.getCurrencyRenderer());
        Reference.isSaved = false;
        
    }

    public int getColumnCount() {
        return 3;
    }

    public int getRowCount() {
        return this.billAccounts.size();
    }
    
    public boolean isCellEditable(int rowIndex, int columIndex)
    {
        return true;
    }

    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
        case 0: return "Account Name";
        case 1: return "Monthly Payment";
        case 2: return "Payment Day";
        default: return "Unknown";
        
        }
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        BillAccount account = this.billAccounts.get(rowIndex);

        switch (columnIndex) {
        case 0: return account.accountName;
        case 1: return  account.monthlyPayment;
        case 2: return account.paymentDay;
        default: return null;
        }
    }
    
    public ArrayList<BillAccount> getAccountsAll()
    {
        
        return this.billAccounts;
        
    }
   
    
    public void removeAccount(int row)
    {
        
        if(this.billAccounts.size() <= 1)
        {
            
            BillAccount newBlankAccount = new BillAccount();
            this.billAccounts.add(newBlankAccount);
            this.billAccounts.remove(0);
               
        }else
        {
            
            this.billAccounts.remove(row);
            
        
        }
        
        Reference.billAccounts = this.billAccounts;
        this.fireTableStructureChanged();
        Reference.debtTableColumnModel.getColumn(1).setCellRenderer(NumberRenderer.getCurrencyRenderer());
        
        Reference.isSaved = false;
        
    }
    
    public void setValueAt(Object value, int rowIndex, int columnIndex)
    {
        
        
        
        Reference.isSaved = false;
        BillAccount account = billAccounts.get(rowIndex);
        switch (columnIndex) {
            case 0:
            {
               
                account.accountName = (String) value;
                billAccounts.remove(rowIndex);
                billAccounts.add(rowIndex, account);
                    
                
                    Reference.billAccounts = billAccounts;
                    break;
            }   
            case 1: 
            {
                account.monthlyPayment = Float.parseFloat((String) value);
                billAccounts.remove(rowIndex);
                billAccounts.add(rowIndex, account);
                
                Reference.billAccounts = billAccounts;
                Reference.monthlyBillsLabel.setText("Monthly Bills: " + cf.format(AccountManager.getTotalMonthlyBills()));
                break;
            }
            case 2:
            {
                
                account.paymentDay = Integer.parseInt((String) value);
                billAccounts.remove(rowIndex);
                billAccounts.add(rowIndex, account);
                
                Reference.billAccounts = billAccounts;
                break;
                
            }
            default: break;
        
        } 
    }
}
