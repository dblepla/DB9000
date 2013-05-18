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

import com.dlepla.db9000.lib.Reference;

public class OverPanel extends JPanel
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    //JLabel overTitle;
    JLabel currentDateLabel;
    JButton addUserButton;
    JButton logoutButton;
    JButton bankAccountsButton;
    JButton debtAccountsButton;
    JButton transButton;
    DateTime CurrentDate;
    
    
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
        Reference.monthlyIncomeLabel = new JLabel("Monthly Income: " + cf.format(Reference.getTotalMonthlyIncome()));
        Reference.monthlyBillsLabel = new JLabel("Monthly Bills: " + cf.format(Reference.getTotalMonthlyBills()));
        Reference.availibleCashLabel = new JLabel("Availible Cash: " + cf.format(Reference.getTotalCash()));
        
        OverviewButtonListener obl = new OverviewButtonListener();
        addUserButton.addActionListener(obl);
        logoutButton.addActionListener(obl);
        bankAccountsButton.addActionListener(obl);
        debtAccountsButton.addActionListener(obl);
        
        currentDateLabel = new JLabel("Current Date: " + Reference.currentDate.toString("MM/dd/yyyy"));
        
        Reference.payOffBar = new JProgressBar(0, 100);
        Reference.payOffBar.setValue(Reference.getPercentCompleted(Reference.debtAccounts.get(0)));
        Reference.payOffBar.setStringPainted(true);
        Reference.payOffBar.setBorder(Reference.DB_Line);
        Reference.payOffBar.setBackground(Reference.FOOTER_BACKGROUND_COLOR);
        Reference.payOffBar.setForeground(Reference.HEADER_BACKGROUD_COLOR);
        
        Box headerBox = Reference.createHeaderBox("Debt Overview");
        Reference.addItem(this, headerBox, 0, 0, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL);
        
        Box topTotalsBox = Box.createHorizontalBox();
        topTotalsBox.add(Box.createHorizontalStrut(20));
        topTotalsBox.add(Reference.monthlyIncomeLabel);
        topTotalsBox.add(Box.createRigidArea(new Dimension(10, 0)));
        topTotalsBox.add(Reference.monthlyBillsLabel);
        topTotalsBox.add(Box.createRigidArea(new Dimension(10, 0)));
        topTotalsBox.add(Reference.availibleCashLabel);
        topTotalsBox.add(Box.createHorizontalGlue());
        topTotalsBox.add(currentDateLabel);
        topTotalsBox.add(Box.createHorizontalStrut(20));
        
        Reference.addItem(this, topTotalsBox, 0, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.BOTH);
        
        Box centerItems = Box.createHorizontalBox();
        centerItems.add(Box.createVerticalStrut(20));
        centerItems.add(Reference.payOffBar);
        Box centerBox = Reference.createCenterBox(centerItems);
        Reference.addItem(this, centerBox, 0, 2, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        
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
