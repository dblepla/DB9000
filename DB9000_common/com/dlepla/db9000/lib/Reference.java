package com.dlepla.db9000.lib;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ScheduledExecutorService;

import javax.swing.JPanel;

import javax.crypto.Cipher;
import javax.crypto.SealedObject;
import javax.crypto.SecretKey;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import org.joda.time.DateTime;
import com.dlepla.db9000.Account;
import com.dlepla.db9000.BankAccount;
import com.dlepla.db9000.BillAccount;
import com.dlepla.db9000.DebtAccount;
import com.dlepla.db9000.UserAccount;


// General reference class which holds all constant values and helper methods for the program.
public class Reference
{
    
    //Sets Minimum amount of cash to have in savings
    public static final float MIN_SAVINGS = 2000;
    
    // Initializes and sets static color objects for the DB9000 program.
    public static final Color HEADER_TEXT_COLOR = new Color(232, 255, 210);
    public static final Color HEADER_BACKGROUD_COLOR = new Color(34, 177, 76);
    public static final Color HEADER_BORDER_COLOR = new Color(17, 89, 38);
    public static final Color CENTER_BACKGROUND_COLOR = new Color(226, 255, 198);
    public static final Color FOOTER_BACKGROUND_COLOR = new Color(236, 255, 217);
    public static final Color TEXT_COLOR = new Color(11, 55, 24);
    
    
    public static final Font HEADER_FONT = new Font("Elephant", Font.PLAIN, 14);
    
    
    // Initializes and sets the default password and usernames.
    public static final char[] DEFAULT_PASSWORD = new char[] { 'a', 'd', 'm',
            'i', 'n'};
    public static final char[] AMBER_PASSWORD = new char[] { 'a', 'd', 'm',
            'i', 'n' };
    public static final String DEFAULT_USERNAME = "dblepla";
    public static Cipher cipher = null;
    
    
    // Initializes and sets file paths for image files used by the DB9000 program.
    public static final String X16_ICON_LOCATION = "C:\\Development\\source\\DB9000\\resources\\Icon16x16.gif";
    public static final String X32_ICON_LOCATION = "C:\\Development\\source\\DB9000\\resources\\Icon32x32.gif";
    public static final String X64_ICON_LOCATION = "C:\\Development\\source\\DB9000\\resources\\Icon64x64.gif";
    
    
    //Initializes and sets the file paths for the save files used by the program
    public static final Path PASSWORD_FILE = Paths
            .get(System.getenv("APPDATA") + "\\DB9000\\bin\\SCDB.dat");
    public static final Path KEY_FILE = Paths
            .get(System.getenv("APPDATA") + "\\DB9000\\bin\\keyfile");
    public static final Path BANKACCOUNT_DATABASE_FILE = Paths
            .get(System.getenv("APPDATA") + "\\DB9000\\bin\\BADB.dat");
    public static final Path DEBTACCOUNT_DATABASE_FILE = Paths
            .get(System.getenv("APPDATA") + "\\DB9000\\bin\\DADB.dat");
    public static final Path BILLACCOUNT_DATABASE_FILE = Paths
            .get(System.getenv("APPDATA") + "\\DB9000\\bin\\BIDB.dat");
    
    
    // Defines static ArrayList variables to hold loaded bank and debt account objects.
    public static ArrayList<BankAccount> bankAccounts = null;
    public static ArrayList<DebtAccount> debtAccounts = null;
    public static ArrayList<BillAccount> billAccounts = null;
    public static ArrayList<UserAccount> userAccounts = null;
    
    
    // Define static variables to main JFrame and all JPanels.
    public static JFrame mainWindow;
    public static JPanel loginPanel;
    public static JPanel adminPanel;
    public static JPanel bankAccountPanel;
    public static JPanel debtAccountPanel;
    public static JPanel billAccountPanel;
    public static JPanel userAccountPanel;
    public static JPanel overPanel;
  
    
    
    // define static JTable variables used for displaying bank and debt account data in table format.
    public static JTable bankAccountTable;
    public static JTable debtAccountTable;
    public static JTable billAccountTable;
    public static JTable userAccountTable;
    
    
    // defines static JLabels for the Overview Panel.
    public static JLabel monthlyIncomeLabel = new JLabel(" ");
    public static JLabel monthlyBillsLabel = new JLabel(" ");
    public static JLabel availibleCashLabel = new JLabel(" ");
    
    
    // Defines static TableColumeModel variables to hold custom
    public static TableColumnModel bankTableColumnModel;
    public static TableColumnModel debtTableColumnModel;
    public static TableColumnModel billTableColumnModel;
    public static TableColumnModel userTableColumnModel;
    
    
    public static TableColumn typeColumn;
    
    public static JComboBox<String> BATableComboBox = new JComboBox<String>();
    
    public static JProgressBar payOffBar;

    
    
    
    // Defines and initializes custom Borders used throughout the program.
    public static final EmptyBorder DB_Insets = new EmptyBorder(3,10,3,10);
    public static final LineBorder DB_Line = new LineBorder(Reference.HEADER_BORDER_COLOR, 2);
    public static final Border BUTTON_BORDER= BorderFactory.createCompoundBorder( DB_Line, DB_Insets);
    public static final LineBorder LINE_BORDER = new LineBorder(Reference.HEADER_BORDER_COLOR, 1);
    
    
    // Defines and initializes a variable used to track if data has been saved to file.
    public static boolean isSaved = true;
    
    
    //Defines and initializes variables for easier code reading when selecting to save to the bank, debt or bill account file.
    public static final int DEBT_ACCOUNT = 1;
    public static final int BANK_ACCOUNT = 2;
    public static final int BILL_ACCOUNT = 3;
    public static final int USER_ACCOUNT = 4;
    
    //Get a reference to the current local date and time
    public static final DateTime currentDate = new DateTime();
    
    //Sets a time field to track the last action from user
    public static int lastActionMinute = new DateTime().getMinuteOfDay(); 
    
    //Sets the logout timer in minutes.
    public static int LOGOUT_MINUTES = 5;
    
    // Reference to the ScheduleExecutorService variable.
    public static ScheduledExecutorService ex;
   
    
    // Creating a helper method that allows us to easily add JComponents to a
    // JPanel using GridBagConstraints
    public static void addItem(JPanel p, JComponent c, int x, int y, int width,
            int height, int align, int fill)
    {

        // Initializes a new GridBagConstraint variable and object called gc
        GridBagConstraints gc = new GridBagConstraints();
        // Sets the default GridBagConstraint object variables and uses passed
        // Variables to set constraints that change
        gc.gridx = x;
        gc.gridy = y;
        gc.gridwidth = width;
        gc.gridheight = height;
        gc.weightx = 100.0;
        gc.weighty = 100.0;
        gc.insets = new Insets(0, 0, 0, 0);
        gc.anchor = align;
        gc.fill = fill;
        // Adds the passed JComponent c to the passed JPanel p using the
        // GridBagConstraints set in gc
        p.add(c, gc);
    }
    
    
    public static void changePanelView(JPanel viewPanel, JPanel removePanel)
    {
        
        // Getting the current Dimension of the Window.
        Dimension currentSize = mainWindow.getSize();
        int currentWindowState = mainWindow.getExtendedState();
        
        mainWindow.getContentPane().add(viewPanel);
        viewPanel.setVisible(true);
        removePanel.setVisible(false);
        mainWindow.getContentPane().doLayout();
        mainWindow.setSize(currentSize);
        mainWindow.setExtendedState(currentWindowState);
        mainWindow.update(mainWindow.getGraphics());
        
    }
    
    
    // Defines a Save and exit method
    public static void saveAndExit()
    {
        
        if (isSaved == false)
        {
            
            int result = JOptionPane.showConfirmDialog(null, "Account changes are not saved, if you exit the program without saving your data will be lost. Would you like to save changes?", "Save before exiting:", JOptionPane.YES_NO_OPTION );
            
            if (result == JOptionPane.YES_OPTION)
            {
                
                saveAccounts(BANKACCOUNT_DATABASE_FILE.toString(), BANK_ACCOUNT);
                
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
    
    
    public static void saveAndChangePanel(JPanel viewPanel, JPanel removePanel, int type)
    {
        
        if (isSaved == false)
        {
            
            int result = JOptionPane.showConfirmDialog(null, "Account changes are not saved, if you exit this program without saving, your data will be lost. Would you like to save changes?", "Save changes before leaving?", JOptionPane.YES_NO_OPTION );
            
            if (result == JOptionPane.YES_OPTION)
            {
                
                if(type == BANK_ACCOUNT)
                    saveAccounts(Reference.BANKACCOUNT_DATABASE_FILE.toString(), BANK_ACCOUNT);
                else if (type == DEBT_ACCOUNT)
                    saveAccounts(Reference.DEBTACCOUNT_DATABASE_FILE.toString(), DEBT_ACCOUNT);
                else
                    saveAccounts(Reference.BILLACCOUNT_DATABASE_FILE.toString(), BILL_ACCOUNT);

                changePanelView(viewPanel, removePanel);
                
            }
            else
                changePanelView(viewPanel, removePanel);
                
        }
        else
            changePanelView(viewPanel, removePanel);
            
    }

    
    
    // Defines a helper method for Authorizing usernames and passwords
    public static boolean authLogin(UserAccount testUser)
    {

        ArrayList<UserAccount> authorizedUsers = userAccounts;
        
        boolean isCorrect = false;
        for (UserAccount u : authorizedUsers)
        {
            isCorrect = compareUsers(testUser, u);
            if (isCorrect)
            {
                break;
            }
        }
       
        return isCorrect;
    }
    
    
    
    public static boolean compareUsers(UserAccount u1, UserAccount u2)
    {
             
        if (u1.username.equals(u2.username)
                && Arrays.equals(u1.password, u2.password))
            return true;
        else
            return false;
    }

 

    // Helper Method to check and see if file exists.
    public static boolean doesFileExist(Path p)
    {

        if (!Files.exists(p))
        {
            return false;
        } else
            return true;
    }
    

    public static ArrayList<UserAccount> readUserAccounts(String filename)
    {

        File file = new File(filename);
        if (file.exists())
        {
            ObjectInputStream ois = null;
            try
            {
                //
                // Read the previously stored SecretKey.
                //
                SecretKey key = (SecretKey) readFromFile(KEY_FILE.toString());
                ArrayList<UserAccount> userList = new ArrayList<UserAccount>();
                ois = new ObjectInputStream(new FileInputStream(filename));
                UserAccount tempUser = null;
                SealedObject tempSealed = null;
                boolean eof = false;
                while (!eof)
                {
                    tempSealed = (SealedObject) ois.readObject();
                    //
                    // Preparing Cipher object from decryption.
                    //
                    String algorithmName = tempSealed.getAlgorithm();
                    Cipher objCipher = Cipher.getInstance(algorithmName);
                    objCipher.init(Cipher.DECRYPT_MODE, key);
                    tempUser = (UserAccount) tempSealed.getObject(objCipher);
                    if (tempUser == null)
                    {
                        eof = true;
                        break;
                    }
                    
                    userList.add(tempUser);
                }
              
                return userList;
                
            } catch (EOFException e)
            {
            } catch (Exception e)
            {
                e.printStackTrace();
            } finally
            {
                try
                {
                    if (ois != null)
                    {
                        ois.close();
                    }
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
    
    public static ArrayList<BankAccount> readBankAccounts(String filename)
    {

        File file = new File(filename);
        if (file.exists())
        {
            ObjectInputStream ois = null;
            try
            {
                //
                // Read the previously stored SecretKey.
                //
                SecretKey key = (SecretKey) readFromFile(KEY_FILE.toString());
                ArrayList<BankAccount> accountList = new ArrayList<BankAccount>();
                ois = new ObjectInputStream(new FileInputStream(filename));
                BankAccount tempAccount = null;
                SealedObject tempSealed = null;
                boolean eof = false;
                while (!eof)
                {
                    tempSealed = (SealedObject) ois.readObject();
                    //
                    // Preparing Cipher object from decryption.
                    //
                    String algorithmName = tempSealed.getAlgorithm();
                    Cipher objCipher = Cipher.getInstance(algorithmName);
                    objCipher.init(Cipher.DECRYPT_MODE, key);
                    tempAccount = (BankAccount) tempSealed.getObject(objCipher);
                    if (tempAccount == null)
                    {
                        eof = true;
                        break;
                    }
                    
                    accountList.add(tempAccount);
                }
                
                    
                    
                return accountList;
                
            } catch (EOFException e)
            {
            } catch (Exception e)
            {
                e.printStackTrace();
            } finally
            {
                try
                {
                    if (ois != null)
                    {
                        ois.close();
                    }
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
    
    
  
    public static ArrayList<BillAccount> readBillAccounts(String filename)
    {

        File file = new File(filename);
        if (file.exists())
        {
            ObjectInputStream ois = null;
            try
            {
                //
                // Read the previously stored SecretKey.
                //
                SecretKey key = (SecretKey) readFromFile(KEY_FILE.toString());
                ArrayList<BillAccount> accountList = new ArrayList<BillAccount>();
                ois = new ObjectInputStream(new FileInputStream(filename));
                BillAccount tempAccount = null;
                SealedObject tempSealed = null;
                boolean eof = false;
                while (!eof)
                {
                    tempSealed = (SealedObject) ois.readObject();
                    //
                    // Preparing Cipher object from decryption.
                    //
                    String algorithmName = tempSealed.getAlgorithm();
                    Cipher objCipher = Cipher.getInstance(algorithmName);
                    objCipher.init(Cipher.DECRYPT_MODE, key);
                    tempAccount = (BillAccount) tempSealed.getObject(objCipher);
                    if (tempAccount == null)
                    {
                        eof = true;
                        break;
                    }
                    
                    accountList.add(tempAccount);
                }
                
                    
                    
                return accountList;
                
            } catch (EOFException e)
            {
            } catch (Exception e)
            {
                e.printStackTrace();
            } finally
            {
                try
                {
                    if (ois != null)
                    {
                        ois.close();
                    }
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
    
    
    public static ArrayList<DebtAccount> readDebtAccounts(String filename)
    {

        File file = new File(filename);
        if (file.exists())
        {
            ObjectInputStream ois = null;
            try
            {
                //
                // Read the previously stored SecretKey.
                //
                SecretKey key = (SecretKey) readFromFile(KEY_FILE.toString());
                ArrayList<DebtAccount> accountList = new ArrayList<DebtAccount>();
                ois = new ObjectInputStream(new FileInputStream(filename));
                DebtAccount tempAccount = null;
                SealedObject tempSealed = null;
                boolean eof = false;
                while (!eof)
                {
                    tempSealed = (SealedObject) ois.readObject();
                    //
                    // Preparing Cipher object from decryption.
                    //
                    String algorithmName = tempSealed.getAlgorithm();
                    Cipher objCipher = Cipher.getInstance(algorithmName);
                    objCipher.init(Cipher.DECRYPT_MODE, key);
                    tempAccount = (DebtAccount) tempSealed.getObject(objCipher);
                    if (tempAccount == null)
                    {
                        eof = true;
                        break;
                    }
                    
                    accountList.add(tempAccount);
                }
                
                    
                    
                return accountList;
                
            } catch (EOFException e)
            {
            } catch (Exception e)
            {
                e.printStackTrace();
            } finally
            {
                try
                {
                    if (ois != null)
                    {
                        ois.close();
                    }
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
    
    
    
    
    public static void saveAccounts(String filename, int type)
    {
        
       File deleteAccountFile = new File(filename);
       ArrayList<?> tempAccounts;
       
       if(deleteAccountFile.exists())
           deleteAccountFile.delete();
       
       if(type == BANK_ACCOUNT)
           tempAccounts = bankAccounts;
       else if(type == DEBT_ACCOUNT)
           tempAccounts = debtAccounts;
       else if(type == BILL_ACCOUNT)
           tempAccounts = billAccounts;
       else
           tempAccounts = userAccounts;
       
       try
       {
           
           SealedObject sealedAccount = new SealedObject((Serializable) tempAccounts.get(0),
               Reference.cipher);
           writeToFile(filename, sealedAccount, false );
           
       }catch (Exception e)
       {
           
           e.printStackTrace();
       }
       
       
       if(tempAccounts.size() > 1)
       {
           
           for ( int i = 1; i <= tempAccounts.size() -1; i++)
           {
               
               try
               {
                   
                   SealedObject sealedAccount = new SealedObject((Serializable) tempAccounts.get(i),
                       Reference.cipher);
                   writeToFile(filename, sealedAccount, true );
                   
               }catch (Exception e)
               {
                   
                   e.printStackTrace();
               }
               
               
               
           }
             
           
       }
       
       
       try
       {
           
           Account nullAccount = null;
           SealedObject sealedAccount = new SealedObject(nullAccount,
               Reference.cipher);
            writeToFile(filename, sealedAccount, true);
            
            Reference.isSaved = true;
            
       }catch(Exception e)
       {
           
           e.printStackTrace();
       }
       
       
      
        
    }
    
 

    public static void writeToFile(String filename, Object object,boolean append)
    {

        FileOutputStream fos = null;
        ObjectOutputStream out = null;
        Path filePath = Paths.get(filename);
        try
        {
            if (!doesFileExist(filePath) || !append)
            {
                out = new ObjectOutputStream(new FileOutputStream(filename));
            } else
            {
                out = new AppendableObjectOutputStream(new FileOutputStream(
                        filename, append));
            }
            out.writeObject(object);
            out.flush();
        } catch (IOException i)
        {
            i.printStackTrace();
        } finally
        {
            try
            {
                if (out != null)
                {
                    out.close();
                }
                if (fos != null)
                {
                    fos.close();
                }
            } catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }
    }
    
     //
    // Method for reading object stored in a file.
    //
    public static Object readFromFile(String filename) throws Exception
    {

        FileInputStream fis = null;
        ObjectInputStream ois = null;
        Object object = null;
        try
        {
            fis = new FileInputStream(new File(filename));
            ois = new ObjectInputStream(fis);
            object = ois.readObject();
        } catch (Exception e)
        {
            e.printStackTrace();
        } finally
        {
            if (ois != null)
            {
                ois.close();
            }
            if (fis != null)
            {
                fis.close();
            }
        }
        return object;
    }

    private static class AppendableObjectOutputStream extends
            ObjectOutputStream
    {
        public AppendableObjectOutputStream(OutputStream out)
                throws IOException
        {

            super(out);
        }

        @Override
        protected void writeStreamHeader() throws IOException
        {

        }
    }
     
}
