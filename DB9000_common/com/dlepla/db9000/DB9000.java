package com.dlepla.db9000;

// Debt Buster 9000 Login Main Program
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
        
        // Checks to see if password file exists and creates a new file if
        // needed.
        if (!Reference.doesFileExist(Reference.PASSWORD_FILE))
        {
            
            
            User drew = new User("dblepla", Reference.DEFAULT_PASSWORD);
            User amber = new User("alepla", Reference.AMBER_PASSWORD);
            User nullUser = null;
            try
            {
                SealedObject sealedUser = new SealedObject(drew,
                        Reference.cipher);
                Reference.writeToFile(Reference.PASSWORD_FILE.toString(),
                        sealedUser, false);
                sealedUser = new SealedObject(amber, Reference.cipher);
                Reference.writeToFile(Reference.PASSWORD_FILE.toString(),
                        sealedUser, true);
                sealedUser = new SealedObject(nullUser, Reference.cipher);
                Reference.writeToFile(Reference.PASSWORD_FILE.toString(),
                        sealedUser, true);
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        
        // Checks to see if BankAccount database file exists and creates a new file
        // if needed.
        if (!Reference.doesFileExist(Reference.BANKACCOUNT_DATABASE_FILE))
        {
            
            
            
            BankAccount placeHolderAccount = new BankAccount();
            BankAccount nullSavingsAccount = null;
            
            try
            {
                
                 SealedObject sealedUser = new SealedObject(placeHolderAccount,
                    Reference.cipher);
                 Reference.writeToFile(Reference.BANKACCOUNT_DATABASE_FILE.toString(),
                    sealedUser, false);
                 sealedUser = new SealedObject(nullSavingsAccount, Reference.cipher);
                 Reference.writeToFile(Reference.BANKACCOUNT_DATABASE_FILE.toString(),
                    sealedUser, true);
                 
                 Reference.bankAccounts = Reference.readBankAccounts(Reference.BANKACCOUNT_DATABASE_FILE.toString());
                 
            } catch (Exception e)
            {
                e.printStackTrace();
            }
           
            
        } else
        {
            
            Reference.bankAccounts = Reference.readBankAccounts(Reference.BANKACCOUNT_DATABASE_FILE.toString());
            
        }
        
        
        // Checks to see if Debt account database file exists and creates a new file
        // if needed.
        if (!Reference.doesFileExist(Reference.DEBTACCOUNT_DATABASE_FILE))
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
           
            
        } else
        {
            
            Reference.debtAccounts = Reference.readDebtAccounts(Reference.DEBTACCOUNT_DATABASE_FILE.toString());
            
        }
        
        
     // Checks to see if Bill account database file exists and creates a new file
        // if needed.
        if (!Reference.doesFileExist(Reference.BILLACCOUNT_DATABASE_FILE))
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
           
            
        } else
        {
           
            Reference.billAccounts = Reference.readBillAccounts(Reference.BILLACCOUNT_DATABASE_FILE.toString());
            
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
        
        // getting reference to JFrame object instance.
        Reference.mainWindow = this;
        Reference.bankAccountPanel = new BankAccountPanel();
        Reference.overPanel = new OverPanel();
        Reference.loginPanel = new LoginPanel();
        Reference.debtAccountPanel = new DebtAccountPanel();
        Reference.billAccountPanel = new BillAccountPanel();
        
        // sets Button select on click color
        UIManager.put("Button.select",new ColorUIResource(Reference.HEADER_BORDER_COLOR));
        
        this.setTitle("Debt Blaster 9000");
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setSize(700, 450);
        this.setMinimumSize(new Dimension(715, 482));
        this.setIconImages(icons);
        this.add(Reference.loginPanel);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        
        
        
    }
    
    
}
