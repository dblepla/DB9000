package com.dlepla.db9000;

// Debt Buster 9000 Login Main Program
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SealedObject;
import javax.crypto.SecretKey;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;

import com.dlepla.db9000.lib.Reference;

public class DB9000 extends JFrame
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    SecretKey key = null;

    // Runs the DB9000 program in new Runnable queue
    public static void main(String[] args)
    {

        EventQueue.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {

                new DB9000();
            }
        });
    }

    public DB9000()
    {

                
        
        // Checks to see if keyfile exists and creates a new file if needed.
        if (!Reference.doesFileExist(Reference.KEY_FILE))
        {
            try
            {
                
                //
                // Generating a temporary key and store it in a file.
                //
                key = KeyGenerator.getInstance("DES").generateKey();
                Reference
                        .writeToFile(Reference.KEY_FILE.toString(), key, false);
                
                Runtime.getRuntime().exec("attrib +H " + Reference.KEY_FILE.toString());
                
                //
                // Preparing Cipher object for encryption.
                //
                Reference.cipher = Cipher.getInstance("DES");
                Reference.cipher.init(Cipher.ENCRYPT_MODE, key);
                
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        } else
        {
            
            try
            {
                
                //
                // Preparing Cipher object for encryption.
                //
                key = (SecretKey) Reference.readFromFile(Reference.KEY_FILE.toString());
                Reference.cipher = Cipher.getInstance("DES");
                Reference.cipher.init(Cipher.ENCRYPT_MODE, key);
                
            }catch(Exception e)
            {
                
                e.printStackTrace();
                
            }
            
            
            
        }
        
        
        
        // Checks to see if BankAccount database file exists and creates a new file
        // if needed.
        if (!Reference.doesFileExist(Reference.BANKACCOUNT_DATABASE_FILE))
        {
            
            
            int result = JOptionPane.showConfirmDialog(null, "Warning! The Bank Account file has been deleted or is corrupted.\nIf you have a backup please click on \"No\" to exit and restore this file to the path (" + Reference.BANKACCOUNT_DATABASE_FILE.toString() + ") and restart the program.\nOtherwise you can select \"Yes\" to generate a new bank account database file  but all previously saved bank account data will be lost in the process.", "Continue or Exit: ", JOptionPane.YES_NO_OPTION );
            
            if (result == JOptionPane.YES_OPTION)
            {
                
                
                BankAccount placeHolderBankAccount = new BankAccount();
                BankAccount nullBankAccount = null;
                
                try
                {
                    
                     SealedObject sealedUser = new SealedObject(placeHolderBankAccount,
                        Reference.cipher);
                     Reference.writeToFile(Reference.BANKACCOUNT_DATABASE_FILE.toString(),
                        sealedUser, false);
                     sealedUser = new SealedObject(nullBankAccount, Reference.cipher);
                     Reference.writeToFile(Reference.BANKACCOUNT_DATABASE_FILE.toString(),
                        sealedUser, true);
                     
                     Reference.bankAccounts = Reference.readBankAccounts(Reference.BANKACCOUNT_DATABASE_FILE.toString());
                     
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
            
            }else
                System.exit(0);
            
            
        } 
        
        
        // Checks to see if Debt account database file exists and creates a new file
        // if needed.
        if (!Reference.doesFileExist(Reference.DEBTACCOUNT_DATABASE_FILE))
        {
            
            
            int result = JOptionPane.showConfirmDialog(null, "Warning! The Debt Account file has been deleted or is corrupted.\nIf you have a backup please click on \"No\" to exit and restore this file to the path (" + Reference.DEBTACCOUNT_DATABASE_FILE.toString() + ") and restart the program.\nOtherwise you can select \"Yes\" to generate a new debt account database file but all previously saved debt account data will be lost in the process.", "Continue or Exit: ", JOptionPane.YES_NO_OPTION );
            
            if (result == JOptionPane.YES_OPTION)
            {
                
                
                DebtAccount placeHolderAccount = new DebtAccount();
                DebtAccount nullAccount = null;
                
                try
                {
                    
                     SealedObject sealedUser = new SealedObject(placeHolderAccount,
                        Reference.cipher);
                     Reference.writeToFile(Reference.DEBTACCOUNT_DATABASE_FILE.toString(),
                        sealedUser, false);
                     sealedUser = new SealedObject(nullAccount, Reference.cipher);
                     Reference.writeToFile(Reference.DEBTACCOUNT_DATABASE_FILE.toString(),
                        sealedUser, true);
                     
                     Reference.debtAccounts = Reference.readDebtAccounts(Reference.DEBTACCOUNT_DATABASE_FILE.toString());
                     
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
            
            }else
                System.exit(0);
           
           
            
        } 
        
        
     // Checks to see if Bill account database file exists and creates a new file
        // if needed.
        if (!Reference.doesFileExist(Reference.BILLACCOUNT_DATABASE_FILE))
        {
            
            
            
            
            int result = JOptionPane.showConfirmDialog(null, "Warning! The Bill Account file has been deleted or is corrupted.\nIf you have a backup please click on \"No\" to exit and restore this file to the path (" + Reference.BILLACCOUNT_DATABASE_FILE.toString() + ") and restart the program.\nOtherwise you can select \"Yes\" to generate a new bill account database file but all previously saved bill account data will be lost in the process.", "Continue or Exit: ", JOptionPane.YES_NO_OPTION );
            
            if (result == JOptionPane.YES_OPTION)
            {
                
                
                BillAccount placeHolderAccount = new BillAccount();
                BillAccount nullAccount = null;
                
                try
                {
                    
                     SealedObject sealedUser = new SealedObject(placeHolderAccount,
                        Reference.cipher);
                     Reference.writeToFile(Reference.BILLACCOUNT_DATABASE_FILE.toString(),
                        sealedUser, false);
                     sealedUser = new SealedObject(nullAccount, Reference.cipher);
                     Reference.writeToFile(Reference.BILLACCOUNT_DATABASE_FILE.toString(),
                        sealedUser, true);
                     
                     Reference.billAccounts = Reference.readBillAccounts(Reference.BILLACCOUNT_DATABASE_FILE.toString());
                     
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
            
            }else
                System.exit(0);
           
        } 
        
        System.out.print("\n\n");
        
        ArrayList<Image> icons = new ArrayList<Image>();
        icons.add(new ImageIcon(Reference.X16_ICON_LOCATION.toString()).getImage());
        icons.add(new ImageIcon(Reference.X32_ICON_LOCATION.toString()).getImage());
        icons.add(new ImageIcon(Reference.X64_ICON_LOCATION.toString()).getImage());
        
        addWindowListener(new WindowAdapter()
        {
            
            public void windowClosing(WindowEvent e)
            {
                
                if (Reference.isSaved == false)
                {
                    
                    int result = JOptionPane.showConfirmDialog(null, "Account changes are not saved, if you exit the program without saving your data will be lost. Would you like to save changes?", "Save before exiting:", JOptionPane.YES_NO_OPTION );
                    
                    if (result == JOptionPane.YES_OPTION)
                    {
                        
                        System.out.print("Saving the following accounts to file:");
                        
                        for( int i = 0; i < Reference.bankAccounts.size(); i++)
                            System.out.println(Reference.bankAccounts.get(i).toString());
                        
                        Reference.saveAccounts(Reference.BANKACCOUNT_DATABASE_FILE.toString(), Reference.BANK_ACCOUNT);
                        
                        System.out.print("Saving the following accounts to file:");
                        
                        for( int i = 0; i < Reference.debtAccounts.size(); i++)
                            System.out.println(Reference.debtAccounts.get(i).toString());
                        
                        Reference.saveAccounts(Reference.DEBTACCOUNT_DATABASE_FILE.toString(), Reference.DEBT_ACCOUNT);
                        
                        System.exit(0);
                        
                    }
                    else
                    {
                        
                        System.exit(0);
                        
                    }
                }
                else
                {
                    
                    System.exit(0);
                    
                }
            }   
        } );
        
        
        
        // sets Button select on click color
        UIManager.put("Button.select",new ColorUIResource(Reference.HEADER_BORDER_COLOR));
        
        this.setTitle("Debt Blaster 9000");
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setSize(700, 450);
        this.setMinimumSize(new Dimension(715, 482));
        this.setIconImages(icons);
        
        // getting reference to JFrame object instance.
        
        Reference.mainWindow = this;
        
        // Checks to see if password file exists and creates a new file if
        // needed.
        if (!Reference.doesFileExist(Reference.PASSWORD_FILE))
        {
            
            int result = JOptionPane.showConfirmDialog(null, "Warning! The User Account file has been deleted or is corrupted.\nIf you have a backup please click on \"No\" to exit and restore this file to the path (" + Reference.PASSWORD_FILE.toString() + ") and restart the program.\nOtherwise you can select \"Yes\" to generate a new admin account but all previously saved data will be lost in the process.", "Continue or Exit: ", JOptionPane.YES_NO_OPTION );
            
            if (result == JOptionPane.YES_OPTION)
            {
                
                File bankFile = new File(Reference.BANKACCOUNT_DATABASE_FILE.toString());
                bankFile.delete();
                
                BankAccount placeHolderBankAccount = new BankAccount();
                BankAccount nullBankAccount = null;
                
                try
                {
                    
                     SealedObject sealedUser = new SealedObject(placeHolderBankAccount,
                        Reference.cipher);
                     Reference.writeToFile(Reference.BANKACCOUNT_DATABASE_FILE.toString(),
                        sealedUser, false);
                     sealedUser = new SealedObject(nullBankAccount, Reference.cipher);
                     Reference.writeToFile(Reference.BANKACCOUNT_DATABASE_FILE.toString(),
                        sealedUser, true);
                     
                     Reference.bankAccounts = Reference.readBankAccounts(Reference.BANKACCOUNT_DATABASE_FILE.toString());
                     
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
                
                
                
                
                File billFile = new File(Reference.BILLACCOUNT_DATABASE_FILE.toString());
                billFile.delete();
                
                BillAccount placeHolderBillAccount = new BillAccount();
                BillAccount nullBillAccount = null;
                
                try
                {
                    
                     SealedObject sealedUser = new SealedObject(placeHolderBillAccount,
                        Reference.cipher);
                     Reference.writeToFile(Reference.BILLACCOUNT_DATABASE_FILE.toString(),
                        sealedUser, false);
                     sealedUser = new SealedObject(nullBillAccount, Reference.cipher);
                     Reference.writeToFile(Reference.BILLACCOUNT_DATABASE_FILE.toString(),
                        sealedUser, true);
                     
                     Reference.billAccounts = Reference.readBillAccounts(Reference.BILLACCOUNT_DATABASE_FILE.toString());
                     
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
                
                File debtFile = new File(Reference.DEBTACCOUNT_DATABASE_FILE.toString());
                debtFile.delete();
                
                DebtAccount placeHolderDebtAccount = new DebtAccount();
                DebtAccount nullDebtAccount = null;
                
                try
                {
                    
                     SealedObject sealedUser = new SealedObject(placeHolderDebtAccount,
                        Reference.cipher);
                     Reference.writeToFile(Reference.DEBTACCOUNT_DATABASE_FILE.toString(),
                        sealedUser, false);
                     sealedUser = new SealedObject(nullDebtAccount, Reference.cipher);
                     Reference.writeToFile(Reference.DEBTACCOUNT_DATABASE_FILE.toString(),
                        sealedUser, true);
                     
                     Reference.debtAccounts = Reference.readDebtAccounts(Reference.DEBTACCOUNT_DATABASE_FILE.toString());
                     
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
                
                Reference.bankAccounts = Reference.readBankAccounts(Reference.BANKACCOUNT_DATABASE_FILE.toString());
                Reference.debtAccounts = Reference.readDebtAccounts(Reference.DEBTACCOUNT_DATABASE_FILE.toString());
                Reference.billAccounts = Reference.readBillAccounts(Reference.BILLACCOUNT_DATABASE_FILE.toString());
                
                Reference.bankAccountPanel = new BankAccountPanel();
                Reference.loginPanel = new LoginPanel();
                Reference.debtAccountPanel = new DebtAccountPanel();
                Reference.billAccountPanel = new BillAccountPanel();
                Reference.adminPanel = new AdminPanel();
                
                this.add(Reference.adminPanel);
                this.pack();
                this.setLocationRelativeTo(null);
                this.setVisible(true);
                
            }
            else
            {
                
                System.exit(0);
                
            }
           
     
           
        }
        else
        {
           
            
            Reference.bankAccounts = Reference.readBankAccounts(Reference.BANKACCOUNT_DATABASE_FILE.toString());
            Reference.debtAccounts = Reference.readDebtAccounts(Reference.DEBTACCOUNT_DATABASE_FILE.toString());
            Reference.billAccounts = Reference.readBillAccounts(Reference.BILLACCOUNT_DATABASE_FILE.toString());
            
            // getting reference to JFrame object instance.
            Reference.bankAccountPanel = new BankAccountPanel();
            Reference.loginPanel = new LoginPanel();
            Reference.debtAccountPanel = new DebtAccountPanel();
            Reference.billAccountPanel = new BillAccountPanel();    
            
            this.add(Reference.loginPanel);
            
        }
        
       
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        
        
    }
}
