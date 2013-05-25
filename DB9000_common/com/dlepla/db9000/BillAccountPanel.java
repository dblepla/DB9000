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

import com.dlepla.db9000.lib.BillAccountTableModel;
import com.dlepla.db9000.lib.GUIManager;
import com.dlepla.db9000.lib.NumberRenderer;
import com.dlepla.db9000.lib.Reference;

public class BillAccountPanel extends JPanel
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
    JButton bankButton;
    JButton saveButton;
    JButton newButton;
    JButton delButton;
    
    BillAccountTableModel accountTableModel = null;
    
    
    
    public BillAccountPanel()
    {
        
       this.setLayout(new GridBagLayout());
       this.setBackground(Reference.CENTER_BACKGROUND_COLOR);
       this.setOpaque(true);
       
       
       debtButton = GUIManager.createCustomButton("Loans & Credit");
       bankButton = GUIManager.createCustomButton("Bank Accounts");
       logoutButton = GUIManager.createCustomButton("Logout");
       overviewButton =  GUIManager.createCustomButton("Overview");
       saveButton =  GUIManager.createCustomButton("Save");
       newButton =  GUIManager.createCustomButton("New Bill");
       delButton =  GUIManager.createCustomButton("Remove Bill");
       
       AccountButtonListener abl = new AccountButtonListener();
       debtButton.addActionListener(abl);
       bankButton.addActionListener(abl);
       logoutButton.addActionListener(abl);
       overviewButton.addActionListener(abl);
       saveButton.addActionListener(abl);
       newButton.addActionListener(abl);
       delButton.addActionListener(abl);

       
       accountTableModel = new BillAccountTableModel(Reference.billAccounts);
      
       Reference.billAccountTable = new JTable(accountTableModel);
       
       
       
       Reference.billTableColumnModel = Reference.billAccountTable.getColumnModel();
       Reference.billTableColumnModel.getColumn(1).setCellRenderer(NumberRenderer.getCurrencyRenderer());
       
       scrollPane = new JScrollPane(Reference.billAccountTable);
       
       scrollPane.setBorder(Reference.LINE_BORDER);
       
       
       Reference.billAccountTable = GUIManager.createCustomTable(Reference.billAccountTable);
       /*Reference.billAccountTable.setFillsViewportHeight(true);
       Reference.billAccountTable.setGridColor(Reference.HEADER_BORDER_COLOR);
       Reference.billAccountTable.setBackground(Reference.FOOTER_BACKGROUND_COLOR);
       Reference.billAccountTable.setAlignmentY(CENTER_ALIGNMENT);
       Reference.billAccountTable.setAlignmentX(CENTER_ALIGNMENT);*/
    
       
       Box headerBox = GUIManager.createHeaderBox("Monthly Bills");
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
       footerItems.add(bankButton);
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
                Reference.saveAndChangePanel(Reference.overPanel, Reference.billAccountPanel, Reference.BILL_ACCOUNT);
                
            }
            else if (e.getSource() == debtButton)
                Reference.saveAndChangePanel(Reference.debtAccountPanel, Reference.billAccountPanel, Reference.BILL_ACCOUNT);
            else if (e.getSource() == bankButton)
                Reference.saveAndChangePanel(Reference.bankAccountPanel, Reference.billAccountPanel, Reference.BILL_ACCOUNT);
            else if (e.getSource() == logoutButton)
            {
                
                Reference.loginPanel = new LoginPanel();
                Reference.ex.shutdownNow();
                Reference.saveAndChangePanel(Reference.loginPanel, Reference.billAccountPanel, Reference.BILL_ACCOUNT);
                
            }
            else if (e.getSource() == saveButton)
                Reference.saveAccounts(Reference.BILLACCOUNT_DATABASE_FILE.toString(), Reference.BILL_ACCOUNT);
            else if (e.getSource() == newButton)
                accountTableModel.addNewAccount();
            else if (e.getSource() == delButton)
            {
                
                if ( Reference.billAccountTable.getSelectedRow() == -1)
                {
                    
                    JOptionPane
                    .showMessageDialog(
                            getRootPane(),
                            "No account selected: Please click on an account to highlight it and then click the Remove account button.",
                            "No account selected",
                            JOptionPane.INFORMATION_MESSAGE);
                    
                }
                else
                    accountTableModel.removeAccount(Reference.billAccountTable.getSelectedRow());
                
            }
        }
    }   
}
