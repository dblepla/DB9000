package com.dlepla.db9000;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.dlepla.db9000.lib.Reference;

public class OverPanel extends JPanel
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    JLabel overTitle;
    JButton addUserButton;
    JButton logoutButton;
    JButton bankAccountsButton;
    JButton debtAccountsButton;
    JButton transButton;

    public OverPanel()
    {

        this.setLayout(new GridBagLayout());
        this.setBackground(Reference.CENTER_BACKGROUND_COLOR);
        this.setOpaque(true);
        
         
        addUserButton = Reference.createCustomButton("Users");
        logoutButton = Reference.createCustomButton("Logout");
        bankAccountsButton = Reference.createCustomButton("Bank Accounts");
        debtAccountsButton = Reference.createCustomButton("Debt Accounts");
        
        OverviewButtonListener obl = new OverviewButtonListener();
        addUserButton.addActionListener(obl);
        logoutButton.addActionListener(obl);
        bankAccountsButton.addActionListener(obl);
        debtAccountsButton.addActionListener(obl);
        
        
        
        Box headerBox = Reference.createHeaderBox("Debt Overview");
        Reference.addItem(this, headerBox, 0, 0, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL);
        
        Box centerItems = Box.createHorizontalBox();
        Box centerBox = Reference.createCenterBox(centerItems);
        Reference.addItem(this, centerBox, 0, 1, 1, 1,
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
        Reference.addItem(this, footerBox, 0, 2, 1, 1,
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
