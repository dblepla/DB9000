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
    JButton transButton;
    DateTime CurrentDate;
    Dimension proBarDim = new Dimension(200, 30);
    
    
    NumberFormat cf = NumberFormat.getCurrencyInstance();
    
    public OverPanel()
    {

        this.setLayout(new GridBagLayout());
        this.setBackground(Reference.CENTER_BACKGROUND_COLOR);
        this.setOpaque(true);
        
         
        addUserButton = Reference.createCustomButton("Users");
        logoutButton = Reference.createCustomButton("Logout");
        bankAccountsButton = Reference.createCustomButton("Bank Accounts");
        debtAccountsButton = Reference.createCustomButton("Debt Accounts");
        Reference.monthlyIncomeLabel = new JLabel("Monthly Income: " + cf.format(AccountManager.getTotalMonthlyIncome()));
        Reference.monthlyBillsLabel = new JLabel("Monthly Bills: " + cf.format(AccountManager.getTotalMonthlyBills()));
        Reference.availibleCashLabel = new JLabel("Availible Cash: " + cf.format(AccountManager.getTotalCash()));
        
        OverviewButtonListener obl = new OverviewButtonListener();
        addUserButton.addActionListener(obl);
        logoutButton.addActionListener(obl);
        bankAccountsButton.addActionListener(obl);
        debtAccountsButton.addActionListener(obl);
        
        currentDateLabel = new JLabel("Current Date: " + Reference.currentDate.toString("MM/dd/yyyy"));
        
        Account progressAccount = AccountManager.getBestAccount();
        
        accountName = new JLabel(progressAccount.accountName + " Balance");
        initBalance = new JLabel(cf.format(progressAccount.startingBalance));
        currentBalance = new JLabel(cf.format(progressAccount.balance));
        minBalance = new JLabel(cf.format(0));
        
        Reference.payOffBar = new JProgressBar(0, 100);
        
        if( Reference.bankAccounts.size() >= 1 && Reference.debtAccounts.size() >= 1)
            Reference.payOffBar.setValue(AccountManager.getPercentCompleted(progressAccount));
        else 
            Reference.payOffBar.setValue(0);
        
        Reference.payOffBar.setStringPainted(false);
        Reference.payOffBar.setPreferredSize(proBarDim);
        Reference.payOffBar.setBorder(Reference.DB_Line);
        Reference.payOffBar.setBackground(Reference.FOOTER_BACKGROUND_COLOR);
        Reference.payOffBar.setForeground(Reference.HEADER_BACKGROUD_COLOR);
        
        Box headerBox = Reference.createHeaderBox("Debt Overview");
        Reference.addItem(this, headerBox, 0, 0, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL);
        
        
        
        
        Box topLabelsBox = Box.createHorizontalBox();
        topLabelsBox.add(Box.createHorizontalStrut(10));
        topLabelsBox.add(Reference.monthlyIncomeLabel);
        topLabelsBox.add(Box.createRigidArea(new Dimension(10, 0)));
        topLabelsBox.add(Reference.monthlyBillsLabel);
        topLabelsBox.add(Box.createRigidArea(new Dimension(10, 0)));
        topLabelsBox.add(Reference.availibleCashLabel);
        topLabelsBox.add(Box.createHorizontalGlue());
        topLabelsBox.add(currentDateLabel);
        topLabelsBox.add(Box.createHorizontalStrut(10));
        
        
        Reference.addItem(this, topLabelsBox, 0, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL);
        
        
        Box proTitle = Box.createHorizontalBox();
        proTitle.add(accountName);
        
        Box proText = Box.createHorizontalBox();
        proText.add(Box.createHorizontalStrut(10));
        proText.add(initBalance);
        proText.add(Box.createHorizontalGlue());
        proText.add(currentBalance);
        proText.add(Box.createHorizontalGlue());
        proText.add(minBalance);
        proText.add(Box.createHorizontalStrut(10));
        
        
        Box centerItems = Box.createVerticalBox();
        
        centerItems.add(Box.createVerticalStrut(20));
        centerItems.add(proTitle);
        centerItems.add(Box.createVerticalStrut(10));
        centerItems.add(Reference.payOffBar);
        centerItems.add(proText);
        Box centerBox = Reference.createCenterBox(centerItems);
        
        
        Reference.addItem(this, centerBox, 0, 2, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL);
        
        Box footerItems = Box.createHorizontalBox();
        footerItems.add(Box.createHorizontalStrut(20));
        footerItems.add(bankAccountsButton);
        footerItems.add(Box.createRigidArea(new Dimension(10, 5)));
        footerItems.add(debtAccountsButton);
        footerItems.add(Box.createRigidArea(new Dimension(10, 5)));
        //footerItems.add(addUserButton);
        footerItems.add(Box.createHorizontalGlue());
        footerItems.add(logoutButton);
        footerItems.add(Box.createHorizontalStrut(20));
        
        Box footerBox = Reference.createFooterBox(footerItems);
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
            else if (e.getSource() == logoutButton)
            {
                
               
              
                Reference.changePanelView(Reference.loginPanel, Reference.overPanel);
                
            }
        }
    }
}
