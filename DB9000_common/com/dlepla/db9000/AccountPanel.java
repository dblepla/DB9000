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
    JButton saveButton;
    JButton newButton;
    JButton delButton;
    
    AccountTableModel accountTableModel = null;
    
    
    
    public AccountPanel()
    {
        
       this.setLayout(new GridBagLayout());
       this.setBackground(Reference.CENTER_BACKGROUND_COLOR);
       this.setOpaque(true);
       this.setSize(700, 450);
       
       addUserButton = new JButton("Users");
       logoutButton = new JButton("Logout");
       overviewButton = new JButton("Overview");
       saveButton = new JButton("Save");
       newButton = new JButton("New Account");
       delButton = new JButton("Remove Account");
       
       AccountButtonListener abl = new AccountButtonListener();
       addUserButton.addActionListener(abl);
       logoutButton.addActionListener(abl);
       overviewButton.addActionListener(abl);
       saveButton.addActionListener(abl);
       newButton.addActionListener(abl);
       delButton.addActionListener(abl);

       
       accountTableModel = new AccountTableModel(Reference.accounts);
      
       accountTable = new JTable(accountTableModel);

       
       scrollPane = new JScrollPane(accountTable);
       accountTable.setFillsViewportHeight(true);
       
       Box headerBox = Reference.createHeaderBox("Account Data");
       Reference.addItem(this, headerBox, 0, 0, 1, 1,
               GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL);
       
       Box centerItems = Box.createVerticalBox();
       centerItems.add(accountTable);
       centerItems.add(Box.createVerticalStrut(10));
       Box addsaveBox = Box.createHorizontalBox();
       addsaveBox.add(Box.createRigidArea(new Dimension(10, 5)));
       addsaveBox.add(newButton);
       addsaveBox.add(Box.createRigidArea(new Dimension(10, 5)));
       addsaveBox.add(delButton);
       addsaveBox.add(Box.createHorizontalGlue());
       addsaveBox.add(saveButton);
       addsaveBox.add(Box.createRigidArea(new Dimension(10, 5)));
       
       centerItems.add(addsaveBox);
       
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
                Reference.changePanelView(overPanel);
            }
            else if (e.getSource() == addUserButton)
            {
                
                
            }
            else if (e.getSource() == logoutButton)
            {
                
                if (Reference.isSaved == false)
                {
                    
                    int result = JOptionPane.showConfirmDialog(null, "Account changes are not saved, if you exit the program without saving your data will be lost. Would you like to save changes?", "Save before loging out:", JOptionPane.YES_NO_OPTION );
                    
                    if (result == JOptionPane.YES_OPTION)
                    {
                        
                        System.out.print("Saving the following accounts to file:");
                        
                        for( int i = 0; i < Reference.accounts.size(); i++)
                            System.out.println(Reference.accounts.get(i).toString());
                        
                        Reference.saveAccounts(Reference.DBDB_FILE.toString());
                        
                        LoginPanel loginPanel = new LoginPanel();
                        Reference.changePanelView(loginPanel);
                        
                    }
                    else
                    {
                        
                        LoginPanel loginPanel = new LoginPanel();
                        Reference.changePanelView(loginPanel);
                        
                    }
                    
                    
                }
                else
                {
                    
                    LoginPanel loginPanel = new LoginPanel();
                    Reference.changePanelView(loginPanel);
                    
                }
                
                
                
            }
            else if (e.getSource() == saveButton)
            {
                
                System.out.print("Saving the following accounts to file:");
                
                for( int i = 0; i < Reference.accounts.size(); i++)
                    System.out.println(Reference.accounts.get(i).toString());
                
                Reference.saveAccounts(Reference.DBDB_FILE.toString());
              
                
            }
            else if (e.getSource() == newButton)
            {
                
                
                accountTableModel.addNewAccount();
               
              
            }
            else if (e.getSource() == delButton)
            {
                
                
                accountTableModel.removeAccount(accountTable.getSelectedRow());
               
              
            }
        }
    }   
}
