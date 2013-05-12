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
       
       
       addUserButton = Reference.createCustomButton("Users");
       logoutButton = Reference.createCustomButton("Logout");
       overviewButton =  Reference.createCustomButton("Overview");
       saveButton =  Reference.createCustomButton("Save");
       newButton =  Reference.createCustomButton("New Account");
       delButton =  Reference.createCustomButton("Remove Account");
       
       AccountButtonListener abl = new AccountButtonListener();
       addUserButton.addActionListener(abl);
       logoutButton.addActionListener(abl);
       overviewButton.addActionListener(abl);
       saveButton.addActionListener(abl);
       newButton.addActionListener(abl);
       delButton.addActionListener(abl);

       
       accountTableModel = new AccountTableModel(Reference.accounts);
      
       Reference.accountTable = new JTable(accountTableModel);
       
       
       
       Reference.m = Reference.accountTable.getColumnModel();
       Reference.m.getColumn(1).setCellRenderer(NumberRenderer.getCurrencyRenderer());
      
       
       scrollPane = new JScrollPane(Reference.accountTable);
       
       scrollPane.setBorder(Reference.LINE_BORDER);
       
       
       
       Reference.accountTable.setFillsViewportHeight(true);
       Reference.accountTable.setGridColor(Reference.HEADER_BORDER_COLOR);
       Reference.accountTable.setBackground(Reference.FOOTER_BACKGROUND_COLOR);
       Reference.accountTable.setAlignmentY(CENTER_ALIGNMENT);
       Reference.accountTable.setAlignmentX(CENTER_ALIGNMENT);
    
       
       Box headerBox = Reference.createHeaderBox("Account Data");
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
       
       Box centerBox = Reference.createCenterBox(centerItems);
       Reference.addItem(this, centerBox, 0, 1, 1, 1,
               GridBagConstraints.CENTER, GridBagConstraints.BOTH);
       
       Box footerItems = Box.createHorizontalBox();
       footerItems.add(Box.createHorizontalStrut(20));
       footerItems.add(overviewButton);
       footerItems.add(Box.createRigidArea(new Dimension(10, 0)));
       footerItems.add(addUserButton);
       footerItems.add(Box.createHorizontalGlue());
       footerItems.add(logoutButton);
       footerItems.add(Box.createHorizontalStrut(20));
       
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
                
                Reference.saveAndChangePanel(Reference.overPanel, Reference.accountPanel);
            }
            else if (e.getSource() == addUserButton)
            {
                
                
            }
            else if (e.getSource() == logoutButton)
            {
                
                
                Reference.saveAndChangePanel(Reference.loginPanel, Reference.accountPanel);
                
                
                
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
                
                if ( Reference.accountTable.getSelectedRow() == -1)
                {
                    
                    JOptionPane
                    .showMessageDialog(
                            getRootPane(),
                            "No account selected: Please click on an account to highlight it and then click the Remove account button.",
                            "No account selected",
                            JOptionPane.INFORMATION_MESSAGE);
                    
                }else
                {
                    
                    accountTableModel.removeAccount(Reference.accountTable.getSelectedRow());
                    
                }
                
               
              
            }
        }
    }   
}
