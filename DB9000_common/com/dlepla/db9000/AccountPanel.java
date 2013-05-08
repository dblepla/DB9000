package com.dlepla.db9000;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;

import com.dlepla.db9000.lib.Reference;

public class AccountPanel extends JPanel
{

    /**
     * 
     */
    private static final long serialVersionUID = -6111736561700918653L;
   
    JTable accountTable = null;
    
    JScrollPane scrollPane = null;
    JButton addUserButton;
    JButton logoutButton;
    JButton overviewButton;
    
    
    
    public AccountPanel()
    {
        
       this.setLayout(new GridBagLayout());
       this.setBackground(Reference.CENTER_BACKGROUND_COLOR);
       this.setOpaque(true);
       this.setSize(700, 450);
       
       addUserButton = new JButton("Users");
       logoutButton = new JButton("Logout");
       overviewButton = new JButton("Overview");
       
       AccountButtonListener abl = new AccountButtonListener();
       addUserButton.addActionListener(abl);
       logoutButton.addActionListener(abl);
       overviewButton.addActionListener(abl);
       
        
       ArrayList<Account> accounts = Reference.readAccount(Reference.DBDB_FILE.toString());
       
       TableModel tableModel = new AccountTableModel(accounts);
      
       accountTable = new JTable(tableModel);
       
       scrollPane = new JScrollPane(accountTable);
       accountTable.setFillsViewportHeight(true);
       
       Box headerBox = Reference.createHeaderBox("Account Data");
       Reference.addItem(this, headerBox, 0, 0, 1, 1,
               GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL);
       
       Box centerItems = Box.createHorizontalBox();
       centerItems.add(accountTable);
       Box centerBox = Reference.createCenterBox(centerItems);
       Reference.addItem(this, centerBox, 0, 1, 1, 1,
               GridBagConstraints.CENTER, GridBagConstraints.BOTH);
       
       Box footerItems = Box.createHorizontalBox();
       footerItems.add(Box.createRigidArea(new Dimension(10, 5)));
       footerItems.add(overviewButton);
       footerItems.add(Box.createRigidArea(new Dimension(10, 5)));
       footerItems.add(addUserButton);
       footerItems.add(Box.createHorizontalGlue());
       footerItems.add(logoutButton);
       footerItems.add(Box.createRigidArea(new Dimension(10, 5)));
       
       Box footerBox = Reference.createFooterBox(footerItems);
       Reference.addItem(this, footerBox, 0, 2, 1, 1,
               GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL);
    }
    
    private class AccountButtonListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {

            if (e.getSource() == overviewButton)
            {
                OverPanel overPanel = new OverPanel();
                //Reference.mainWindow.getRootPane().setDefaultButton(loginPanel.loginButton);
                Reference.mainWindow.getContentPane().removeAll();
                Reference.mainWindow.getContentPane().add(overPanel);
                Reference.mainWindow.getContentPane().doLayout();
                update( Reference.mainWindow.getGraphics());
                Reference.mainWindow.pack();
            }
            else if (e.getSource() == addUserButton)
            {
                
                
            }
            else if (e.getSource() == logoutButton)
            {
                
                LoginPanel loginPanel = new LoginPanel();
                Reference.mainWindow.getRootPane().setDefaultButton(loginPanel.loginButton);
                Reference.mainWindow.getContentPane().removeAll();
                Reference.mainWindow.getContentPane().add(loginPanel);
                Reference.mainWindow.getContentPane().doLayout();
                update( Reference.mainWindow.getGraphics());
                Reference.mainWindow.pack();
                
            }
        }
    }
    
}
