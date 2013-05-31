package com.dlepla.db9000;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

import org.joda.time.DateTime;

import com.dlepla.db9000.lib.AccountManager;
import com.dlepla.db9000.lib.GUIManager;
import com.dlepla.db9000.lib.Reference;

public class OverPanel extends JPanel
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    //JLabel overTitle;
    JLabel currentDateLabel;
    JLabel initBalance;
    JLabel currentBalance;
    JLabel minBalance;
    JLabel accountName;
    JButton addUserButton;
    JButton logoutButton;
    JButton bankAccountsButton;
    JButton debtAccountsButton;
    JButton billAccountsButton;
    JButton userAccountsButton;
    JButton transButton;
    DateTime currentDate;

    
    
    NumberFormat cf = NumberFormat.getCurrencyInstance();
    
    public OverPanel()
    {

        this.setLayout(new GridBagLayout());
        this.setBackground(Reference.CENTER_BACKGROUND_COLOR);
        this.setOpaque(true);
        SwingUtilities.getRootPane( Reference.mainWindow ).setDefaultButton(null);
         
        addUserButton = GUIManager.createCustomButton("Users");
        logoutButton = GUIManager.createCustomButton("Logout");
        bankAccountsButton = GUIManager.createCustomButton("Bank Accounts");
        debtAccountsButton = GUIManager.createCustomButton("Loans & Credit");
        billAccountsButton = GUIManager.createCustomButton("Monthly Bills");
        userAccountsButton = GUIManager.createCustomButton("User Accounts");
        
        Reference.monthlyIncomeLabel = GUIManager.createCustomLabel(("Monthly Income: " + cf.format(AccountManager.getTotalMonthlyIncome())));
        Reference.monthlyBillsLabel = GUIManager.createCustomLabel(("Monthly Bills: " + cf.format(AccountManager.getTotalMonthlyBills())));
        Reference.availibleCashLabel = GUIManager.createCustomLabel(("Availible Cash: " + cf.format(AccountManager.getTotalCash())));
        
        OverviewButtonListener obl = new OverviewButtonListener();
        addUserButton.addActionListener(obl);
        logoutButton.addActionListener(obl);
        bankAccountsButton.addActionListener(obl);
        debtAccountsButton.addActionListener(obl);
        billAccountsButton.addActionListener(obl);
        userAccountsButton.addActionListener(obl);
        
        currentDateLabel = GUIManager.createCustomLabel(("Current Date: " + Reference.currentDate.toString("MM/dd/yyyy")));
        Reference.lastActionMinute = new DateTime().getMinuteOfDay();
        
        Account progressAccount = AccountManager.getBestAccount();
        
        
        
        if (progressAccount instanceof BankAccount)
        {
            accountName = GUIManager.createCustomLabel(("Total Savings Balance"));
            initBalance = GUIManager.createCustomLabel((cf.format(0)));
            currentBalance = GUIManager.createCustomLabel((cf.format(AccountManager.getTotalSavings())));
            
            if (AccountManager.getTotalMonthlyBills() < Reference.MIN_SAVINGS)
                minBalance = GUIManager.createCustomLabel((cf.format(Reference.MIN_SAVINGS)));
            else
                minBalance = GUIManager.createCustomLabel((cf.format(AccountManager.getTotalMonthlyBills())));
            
        }
        else
        {
            
            accountName = GUIManager.createCustomLabel((progressAccount.accountName + " Balance"));
            initBalance = GUIManager.createCustomLabel((cf.format(progressAccount.initialBalance)));
            currentBalance = GUIManager.createCustomLabel((cf.format(progressAccount.balance)));
            minBalance = GUIManager.createCustomLabel((cf.format(0)));
        
            
        }
        
        Reference.payOffBar = new JProgressBar(0, 100);
        
        System.out.println(Reference.bankAccounts.size());
        System.out.println(Reference.debtAccounts.size());
        
        if( Reference.bankAccounts.size() >= 1 && Reference.debtAccounts.size() >= 1)
        {
            System.out.println("Setting percent Complete: " + AccountManager.getPercentCompleted(progressAccount));
            Reference.payOffBar.setValue(AccountManager.getPercentCompleted(progressAccount));
            
        }
        else 
            Reference.payOffBar.setValue(0);
        
        Dimension proBarDim = Reference.payOffBar.getPreferredSize();
        
        proBarDim.width = 200;
        proBarDim.height = 30;
        
        Reference.payOffBar.setStringPainted(false);
        Reference.payOffBar.setPreferredSize(proBarDim);
        Reference.payOffBar.setBorder(Reference.DB_Line);
        Reference.payOffBar.setBackground(Reference.FOOTER_BACKGROUND_COLOR);
        Reference.payOffBar.setForeground(Reference.HEADER_BACKGROUD_COLOR);
        
        Box headerBox = GUIManager.createHeaderBox("Account Overview");
        Reference.addItem(this, headerBox, 0, 0, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL);
        
        Box topDateBox = Box.createHorizontalBox();
        topDateBox.add(Box.createHorizontalStrut(10));
        topDateBox.add(Box.createHorizontalGlue());
        topDateBox.add(currentDateLabel);
        topDateBox.add(Box.createHorizontalStrut(10));
        
        
        Box topLabelsBox = Box.createHorizontalBox();
        topLabelsBox.setAlignmentY(TOP_ALIGNMENT);
        topLabelsBox.add(Box.createHorizontalStrut(10));
        topLabelsBox.add(Reference.monthlyIncomeLabel);
        topLabelsBox.add(Box.createRigidArea(new Dimension(10, 0)));
        topLabelsBox.add(Reference.monthlyBillsLabel);
        topLabelsBox.add(Box.createRigidArea(new Dimension(10, 0)));
        topLabelsBox.add(Reference.availibleCashLabel);
        topLabelsBox.add(Box.createHorizontalGlue());
        //topLabelsBox.add(currentDateLabel);
        topLabelsBox.add(Box.createHorizontalStrut(10));
        
        Box topInfoBox = Box.createVerticalBox();
        topInfoBox.add(Box.createVerticalStrut(5));
        topInfoBox.add(topDateBox);
        topInfoBox.add(Box.createVerticalStrut(20));
        topInfoBox.add(topLabelsBox);
        topInfoBox.add(Box.createVerticalGlue());
        topInfoBox.add(Box.createVerticalStrut(5));
        
        
        Reference.addItem(this, topInfoBox, 0, 1, 1, 1,
               GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL);
        
        
        Box proTitle = Box.createHorizontalBox();
        proTitle.add(accountName);
        
        Box proText = Box.createHorizontalBox();
        proText.add(Box.createHorizontalStrut(1));
        proText.add(initBalance);
        proText.add(Box.createHorizontalGlue());
        proText.add(currentBalance);
        proText.add(Box.createHorizontalGlue());
        proText.add(minBalance);
        proText.add(Box.createHorizontalStrut(1));
        
        Box centerItems = Box.createVerticalBox();
        centerItems.add(proTitle);
        centerItems.add(Box.createRigidArea(new Dimension(0,10)));
        centerItems.add(Reference.payOffBar);
        centerItems.add(Box.createRigidArea(new Dimension(0,5)));
        centerItems.add(proText);
        Box centerBox = GUIManager.createCenterBox(centerItems);
        
        
        Reference.addItem(this, centerBox, 0, 2, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL);
        
        Box footerItems = Box.createHorizontalBox();
        footerItems.add(bankAccountsButton);
        footerItems.add(Box.createRigidArea(new Dimension(10, 5)));
        footerItems.add(debtAccountsButton);
        footerItems.add(Box.createRigidArea(new Dimension(10, 5)));
        footerItems.add(billAccountsButton);
        footerItems.add(Box.createRigidArea(new Dimension(10, 5)));
        footerItems.add(userAccountsButton);
        footerItems.add(Box.createHorizontalGlue());
        footerItems.add(logoutButton);
    
        
        Box footerBox = GUIManager.createFooterBox(footerItems);
        Reference.addItem(this, footerBox, 0, 3, 1, 1,
                GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL);
    }
    
    private class OverviewButtonListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            
            Reference.lastActionMinute = new DateTime().getMinuteOfDay();
            
            if (e.getSource() == bankAccountsButton)
            {
                
                Reference.changePanelView(Reference.bankAccountPanel,Reference.overPanel);
                
            }
            else if (e.getSource() == debtAccountsButton)
            {
                
      
                Reference.changePanelView(Reference.debtAccountPanel,Reference.overPanel);
                
            }
            else if (e.getSource() == billAccountsButton)
            {
                
           
                Reference.changePanelView(Reference.billAccountPanel,Reference.overPanel);
                
            }
            else if (e.getSource() == userAccountsButton)
            {
                
           
                Reference.changePanelView(Reference.userAccountPanel,Reference.overPanel);
                
            }
            else if (e.getSource() == logoutButton)
            {
                
             
                Reference.loginPanel = new LoginPanel();
                Reference.ex.shutdownNow();
                Reference.changePanelView(Reference.loginPanel, Reference.overPanel);
                
            }
        }
    }
}
