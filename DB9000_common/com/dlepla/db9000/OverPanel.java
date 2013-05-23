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
    JButton transButton;
    DateTime currentDate;

    
    
    NumberFormat cf = NumberFormat.getCurrencyInstance();
    
    public OverPanel()
    {

        this.setLayout(new GridBagLayout());
        this.setBackground(Reference.CENTER_BACKGROUND_COLOR);
        this.setOpaque(true);
        
         
        addUserButton = GUIManager.createCustomButton("Users");
        logoutButton = GUIManager.createCustomButton("Logout");
        bankAccountsButton = GUIManager.createCustomButton("Bank Accounts");
        debtAccountsButton = GUIManager.createCustomButton("Loans & Credit");
        billAccountsButton = GUIManager.createCustomButton("Monthly Bills");
        Reference.monthlyIncomeLabel = new JLabel("Monthly Income: " + cf.format(AccountManager.getTotalMonthlyIncome()));
        Reference.monthlyBillsLabel = new JLabel("Monthly Bills: " + cf.format(AccountManager.getTotalMonthlyBills()));
        Reference.availibleCashLabel = new JLabel("Availible Cash: " + cf.format(AccountManager.getTotalCash()));
        
        OverviewButtonListener obl = new OverviewButtonListener();
        addUserButton.addActionListener(obl);
        logoutButton.addActionListener(obl);
        bankAccountsButton.addActionListener(obl);
        debtAccountsButton.addActionListener(obl);
        billAccountsButton.addActionListener(obl);
        
        currentDateLabel = new JLabel("Current Date: " + Reference.currentDate.toString("MM/dd/yyyy"));
        
        Account progressAccount = AccountManager.getBestAccount();
        
        
        
        if (progressAccount instanceof BankAccount)
        {
            accountName = new JLabel("Total Savings Balance");
            initBalance = new JLabel(cf.format(0));
            currentBalance = new JLabel(cf.format(AccountManager.getTotalSavings()));
            minBalance = new JLabel(cf.format(Reference.MIN_SAVINGS));
            
        }
        else
        {
            
            accountName = new JLabel(progressAccount.accountName + " Balance");
            initBalance = new JLabel(cf.format(progressAccount.startingBalance));
            currentBalance = new JLabel(cf.format(progressAccount.balance));
            minBalance = new JLabel(cf.format(0));
        
            
        }
        
        Reference.payOffBar = new JProgressBar(0, 100);
        
        if( Reference.bankAccounts.size() >= 1 && Reference.debtAccounts.size() >= 1)
            Reference.payOffBar.setValue(AccountManager.getPercentCompleted(progressAccount));
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
        
        
        
        
        Box topLabelsBox = Box.createHorizontalBox();
        topLabelsBox.setAlignmentY(TOP_ALIGNMENT);
        topLabelsBox.add(Box.createHorizontalStrut(10));
        topLabelsBox.add(Reference.monthlyIncomeLabel);
        topLabelsBox.add(Box.createRigidArea(new Dimension(10, 0)));
        topLabelsBox.add(Reference.monthlyBillsLabel);
        topLabelsBox.add(Box.createRigidArea(new Dimension(10, 0)));
        topLabelsBox.add(Reference.availibleCashLabel);
        topLabelsBox.add(Box.createHorizontalGlue());
        topLabelsBox.add(currentDateLabel);
        topLabelsBox.add(Box.createHorizontalStrut(10));
        
        Box topInfoBox = Box.createVerticalBox();
        topInfoBox.add(Box.createVerticalStrut(5));
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
            else if (e.getSource() == logoutButton)
            {
                
               
                Reference.loginPanel = new LoginPanel();
                Reference.changePanelView(Reference.loginPanel, Reference.overPanel);
                
            }
        }
    }
}
