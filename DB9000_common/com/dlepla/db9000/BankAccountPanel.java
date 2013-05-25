package com.dlepla.db9000;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.joda.time.DateTime;

import com.dlepla.db9000.lib.BankAccountTableModel;
import com.dlepla.db9000.lib.GUIManager;
import com.dlepla.db9000.lib.NumberRenderer;
import com.dlepla.db9000.lib.Reference;

public class BankAccountPanel extends JPanel
{

    /**
     * 
     */
    private static final long serialVersionUID = -6111736561700918653L;
   
   
    
    JScrollPane scrollPane = null;
    JButton addUserButton;
    JButton logoutButton;
    JButton overviewButton;
    JButton debtButton;
    JButton billButton;
    JButton saveButton;
    JButton newButton;
    JButton delButton;
    
    BankAccountTableModel accountTableModel = null;
    
    
    
    public BankAccountPanel()
    {
        
       this.setLayout(new GridBagLayout());
       this.setBackground(Reference.CENTER_BACKGROUND_COLOR);
       this.setOpaque(true);
       
       
       debtButton = GUIManager.createCustomButton("Loans & Credit");
       billButton = GUIManager.createCustomButton("Monthly Bills");
       logoutButton = GUIManager.createCustomButton("Logout");
       overviewButton =  GUIManager.createCustomButton("Overview");
       saveButton =  GUIManager.createCustomButton("Save");
       newButton =  GUIManager.createCustomButton("New Account");
       delButton =  GUIManager.createCustomButton("Remove Account");
       
       AccountButtonListener abl = new AccountButtonListener();
       debtButton.addActionListener(abl);
       billButton.addActionListener(abl);
       logoutButton.addActionListener(abl);
       overviewButton.addActionListener(abl);
       saveButton.addActionListener(abl);
       newButton.addActionListener(abl);
       delButton.addActionListener(abl);

       
       accountTableModel = new BankAccountTableModel(Reference.bankAccounts);
      
       Reference.bankAccountTable = new JTable(accountTableModel);
       
       
       
       Reference.bankTableColumnModel = Reference.bankAccountTable.getColumnModel();
       Reference.bankTableColumnModel.getColumn(2).setCellRenderer(NumberRenderer.getCurrencyRenderer());
       Reference.bankTableColumnModel.getColumn(3).setCellRenderer(NumberRenderer.getCurrencyRenderer());
       
       Reference.typeColumn = Reference.bankAccountTable.getColumnModel().getColumn(1);
     
      
       Reference.BATableComboBox.addItem("Checking");
       Reference.BATableComboBox.addItem("Savings");

       Reference.typeColumn.setCellEditor(new DefaultCellEditor(Reference.BATableComboBox));
      
       /*
       Reference.bankAccountTable.setFillsViewportHeight(true);
       Reference.bankAccountTable.setGridColor(Reference.HEADER_BORDER_COLOR);
       Reference.bankAccountTable.setBackground(Reference.FOOTER_BACKGROUND_COLOR);
       Reference.bankAccountTable.setAlignmentY(CENTER_ALIGNMENT);
       Reference.bankAccountTable.setAlignmentX(CENTER_ALIGNMENT);
       Reference.bankAccountTable.getTableHeader().setBackground(Reference.HEADER_BACKGROUD_COLOR);
       Reference.bankAccountTable.getTableHeader().setForeground(Reference.HEADER_TEXT_COLOR);
       Reference.bankAccountTable.getTableHeader().setFont(Reference.HEADER_FONT); */
       
       Reference.bankAccountTable = GUIManager.createCustomTable(Reference.bankAccountTable);
    
       
       try{  
           javax.swing.UIManager.put("ScrollBar.thumb", new javax.swing.plaf.ColorUIResource(33,129,176));  
           javax.swing.UIManager.put("Button.foreground", new javax.swing.plaf.ColorUIResource(0,0,0));  
           }catch(Exception e){  
           e.printStackTrace();  
           }  
       
       scrollPane = new JScrollPane(Reference.bankAccountTable);
       scrollPane.setBorder(Reference.DB_Line);
       
       Box headerBox = GUIManager.createHeaderBox("Bank Accounts");
       Reference.addItem(this, headerBox, 0, 0, 1, 1,
               GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL);
       
       Box centerItems = Box.createVerticalBox();
       centerItems.add(scrollPane);
       centerItems.add(Box.createVerticalStrut(10));
       
       
       Box addsaveBox = Box.createHorizontalBox();
       
       addsaveBox.add(newButton);
       addsaveBox.add(Box.createRigidArea(new Dimension(10, 0)));
       addsaveBox.add(delButton);
       addsaveBox.add(Box.createHorizontalGlue());
       addsaveBox.add(saveButton);
       
       
       centerItems.add(addsaveBox);
       
       Box centerBox = GUIManager.createCenterBox(centerItems);
       Reference.addItem(this, centerBox, 0, 1, 1, 1,
               GridBagConstraints.CENTER, GridBagConstraints.BOTH);
       
       Box footerItems = Box.createHorizontalBox();
       footerItems.add(overviewButton);
       footerItems.add(Box.createRigidArea(new Dimension(10, 0)));
       footerItems.add(debtButton);
       footerItems.add(Box.createRigidArea(new Dimension(10, 0)));
       footerItems.add(billButton);
       footerItems.add(Box.createHorizontalGlue());
       footerItems.add(logoutButton);
       
       Box footerBox = GUIManager.createFooterBox(footerItems);
       Reference.addItem(this, footerBox, 0, 2, 1, 1,
               GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL);
    }
    
    private class AccountButtonListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            
            Reference.lastActionMinute = new DateTime().getMinuteOfDay();
            
            if (e.getSource() == overviewButton)
            {
                
                Reference.overPanel = new OverPanel();
                Reference.saveAndChangePanel(Reference.overPanel, Reference.bankAccountPanel, Reference.BANK_ACCOUNT);
                
            }
            else if (e.getSource() == debtButton)
                Reference.saveAndChangePanel(Reference.debtAccountPanel, Reference.bankAccountPanel, Reference.BANK_ACCOUNT);
            else if (e.getSource() == billButton)
                Reference.saveAndChangePanel(Reference.billAccountPanel, Reference.bankAccountPanel, Reference.BANK_ACCOUNT);
            else if (e.getSource() == logoutButton)
            {
                
                Reference.loginPanel = new LoginPanel();
                Reference.ex.shutdownNow();
                Reference.saveAndChangePanel(Reference.loginPanel, Reference.bankAccountPanel, Reference.BANK_ACCOUNT);
                 
            }
            else if (e.getSource() == saveButton)
                Reference.saveAccounts(Reference.BANKACCOUNT_DATABASE_FILE.toString(), Reference.BANK_ACCOUNT);
            else if (e.getSource() == newButton)
                accountTableModel.addNewAccount();
            else if (e.getSource() == delButton)
            {
                
                if ( Reference.bankAccountTable.getSelectedRow() == -1)
                {
                    
                    JOptionPane
                    .showMessageDialog(
                            getRootPane(),
                            "No account selected: Please click on an account to highlight it and then click the Remove account button.",
                            "No account selected",
                            JOptionPane.INFORMATION_MESSAGE);
                    
                }else
                    accountTableModel.removeAccount(Reference.bankAccountTable.getSelectedRow());
                
            }
        }
    }   
}
