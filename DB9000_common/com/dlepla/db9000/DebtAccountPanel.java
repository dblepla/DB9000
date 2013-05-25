package com.dlepla.db9000;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.joda.time.DateTime;

import com.dlepla.db9000.lib.DebtAccountTableModel;
import com.dlepla.db9000.lib.GUIManager;
import com.dlepla.db9000.lib.NumberRenderer;
import com.dlepla.db9000.lib.Reference;

public class DebtAccountPanel extends JPanel
{

    /**
     * 
     */
    private static final long serialVersionUID = -6111736561700918653L;
   
   
    
    JScrollPane scrollPane = null;
    JButton addUserButton;
    JButton logoutButton;
    JButton overviewButton;
    JButton bankButton;
    JButton billButton;
    JButton saveButton;
    JButton newButton;
    JButton delButton;
    
    DebtAccountTableModel debtAccountTableModel = null;
    
    
    
    public DebtAccountPanel()
    {
        
       this.setLayout(new GridBagLayout());
       this.setBackground(Reference.CENTER_BACKGROUND_COLOR);
       this.setOpaque(true);
       
       
       addUserButton = GUIManager.createCustomButton("Users");
       logoutButton = GUIManager.createCustomButton("Logout");
       overviewButton =  GUIManager.createCustomButton("Overview");
       bankButton =  GUIManager.createCustomButton("Bank Accounts");
       billButton =  GUIManager.createCustomButton("Monthly Bills");
       saveButton =  GUIManager.createCustomButton("Save");
       newButton =  GUIManager.createCustomButton("New Account");
       delButton =  GUIManager.createCustomButton("Remove Account");
       
       AccountButtonListener abl = new AccountButtonListener();
       addUserButton.addActionListener(abl);
       logoutButton.addActionListener(abl);
       overviewButton.addActionListener(abl);
       bankButton.addActionListener(abl);
       billButton.addActionListener(abl);
       saveButton.addActionListener(abl);
       newButton.addActionListener(abl);
       delButton.addActionListener(abl);

       
       debtAccountTableModel = new DebtAccountTableModel(Reference.debtAccounts);
      
       Reference.debtAccountTable = new JTable(debtAccountTableModel);
       
       
       
       Reference.debtTableColumnModel = Reference.bankAccountTable.getColumnModel();
       Reference.debtTableColumnModel.getColumn(1).setCellRenderer(NumberRenderer.getCurrencyRenderer());
       Reference.debtTableColumnModel.getColumn(2).setCellRenderer(NumberRenderer.getCurrencyRenderer());
     
       scrollPane = new JScrollPane(Reference.debtAccountTable);
       
       scrollPane.setBorder(Reference.LINE_BORDER);
       
       scrollPane.getVerticalScrollBar().setBackground(Reference.HEADER_BACKGROUD_COLOR);
       scrollPane.getVerticalScrollBar().setForeground(Reference.CENTER_BACKGROUND_COLOR);

           
       
       
       Reference.debtAccountTable = GUIManager.createCustomTable(Reference.debtAccountTable);
       
       Box headerBox = GUIManager.createHeaderBox("Loans & Credit");
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
       footerItems.add(bankButton);
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
                Reference.saveAndChangePanel(Reference.overPanel, Reference.debtAccountPanel, Reference.DEBT_ACCOUNT);
                
            }
            else if (e.getSource() == bankButton)
                Reference.saveAndChangePanel(Reference.bankAccountPanel, Reference.debtAccountPanel, Reference.DEBT_ACCOUNT);
            else if (e.getSource() == billButton)
                Reference.saveAndChangePanel(Reference.billAccountPanel, Reference.debtAccountPanel, Reference.DEBT_ACCOUNT);
            else if (e.getSource() == logoutButton)
            {
                
                Reference.loginPanel = new LoginPanel();
                Reference.ex.shutdownNow();
                Reference.saveAndChangePanel(Reference.loginPanel, Reference.debtAccountPanel, Reference.DEBT_ACCOUNT);
                
            }
            else if (e.getSource() == saveButton)
                Reference.saveAccounts(Reference.DEBTACCOUNT_DATABASE_FILE.toString(), Reference.DEBT_ACCOUNT);
            else if (e.getSource() == newButton)
                debtAccountTableModel.addNewAccount();
            else if (e.getSource() == delButton)
            {
                
                if ( Reference.debtAccountTable.getSelectedRow() == -1)
                {
                    
                    JOptionPane
                    .showMessageDialog(
                            getRootPane(),
                            "No account selected: Please click on an account to highlight it and then click the Remove account button.",
                            "No account selected",
                            JOptionPane.INFORMATION_MESSAGE);
                    
                }else
                    debtAccountTableModel.removeAccount(Reference.debtAccountTable.getSelectedRow());
                       
            }
        }
    }   
}