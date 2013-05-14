package com.dlepla.db9000.lib;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.io.DataInputStream;
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
import javax.swing.JPanel;

import javax.crypto.Cipher;
import javax.crypto.SealedObject;
import javax.crypto.SecretKey;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
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
import com.dlepla.db9000.DebtAccount;
import com.dlepla.db9000.User;


// General reference class which holds all constant values and helper methods for the program.
public class Reference
{
    
    
    // Initializes and sets static color objects for the DB9000 program.
    public static final Color HEADER_TEXT_COLOR = new Color(232, 255, 210);
    public static final Color HEADER_BACKGROUD_COLOR = new Color(34, 177, 76);
    public static final Color HEADER_BORDER_COLOR = new Color(17, 89, 38);
    public static final Color CENTER_BACKGROUND_COLOR = new Color(226, 255, 198);
    public static final Color FOOTER_BACKGROUND_COLOR = new Color(236, 255, 217);
    
    
    // Initializes and sets the default password and usernames.
    public static final char[] DEFAULT_PASSWORD = new char[] { 'A', 'q', '2',
            '3', 'e', 's', 'a', 'Q', '@', '#', 'E', 'S' };
    public static final char[] AMBER_PASSWORD = new char[] { 'n', 'i', 'g',
            'e', 'l', '4', '2', '3' };
    public static final String DEFAULT_USERNAME = "dblepla";
    public static Cipher cipher = null;
    
    
    // Initializes and sets file paths for image files used by the DB9000 program.
    public static final String X16_ICON_LOCATION = "C:\\Development\\source\\DB9000\\resources\\Icon16x16.gif";
    public static final String X32_ICON_LOCATION = "C:\\Development\\source\\DB9000\\resources\\Icon32x32.gif";
    public static final String X64_ICON_LOCATION = "C:\\Development\\source\\DB9000\\resources\\Icon64x64.gif";
    
    
    //Initializes and sets the file paths for the save files used by the program
    public static final Path PASSWORD_FILE = Paths
            .get("C:\\Development\\source\\DB9000\\resources\\bin\\dbaccess.dat");
    public static final Path KEY_FILE = Paths
            .get("C:\\Development\\source\\DB9000\\resources\\bin\\keyfile");
    public static final Path BANKACCOUNT_DATABASE_FILE = Paths
            .get("C:\\Development\\source\\DB9000\\resources\\bin\\BADB.dat");
    public static final Path DEBTACCOUNT_DATABASE_FILE = Paths
            .get("C:\\Development\\source\\DB9000\\resources\\bin\\DADB.dat");
    
    
    // Defines static ArrayList variables to hold loaded bank and debt account objects.
    public static ArrayList<BankAccount> bankAccounts = null;
    public static ArrayList<DebtAccount> debtAccounts = null;
    
    
    // Define static variables to main JFrame and all JPanels.
    public static JFrame mainWindow;
    public static JPanel loginPanel;
    public static JPanel bankAccountPanel;
    public static JPanel debtAccountPanel;
    public static JPanel overPanel;
    
    
    // define static JTable variables used for displaying bank and debt account data in table format.
    public static JTable bankAccountTable;
    public static JTable debtAccountTable;
    
    
    // defines static JLabels for the Overview Panel.
    public static JLabel monthlyIncomeLabel;
    public static JLabel monthlyBillsLabel;
    public static JLabel availibleCashLabel;
    
    
    // Defines static TableColumeModel variables to hold custom
    public static TableColumnModel bankTableColumnModel;
    public static TableColumnModel debtTableColumnModel;
    
    
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
    
    
    //Defines and initializes variables for easier code reading when selecting to save to the bank or debt account file. 
    public static final int BANK_ACCOUNT = 1;
    public static final int DEBT_ACCOUNT = 2;
    
    //Get a reference to the current local date and time
    public static final DateTime currentDate = new DateTime();

    
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
        
        mainWindow.update(mainWindow.getGraphics());
        mainWindow.pack();
        
        mainWindow.setSize(currentSize);
        mainWindow.setExtendedState(currentWindowState);
        
        
    }
    
    
    // Defines a Save and exit method
    public static void saveAndExit()
    {
        
        if (isSaved == false)
        {
            
            int result = JOptionPane.showConfirmDialog(null, "Account changes are not saved, if you exit the program without saving your data will be lost. Would you like to save changes?", "Save before exiting:", JOptionPane.YES_NO_OPTION );
            
            if (result == JOptionPane.YES_OPTION)
            {
                
                System.out.print("Saving the following accounts to file:");
                
                for( int i = 0; i < bankAccounts.size(); i++)
                    System.out.println(bankAccounts.get(i).toString());
                
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
                {
                    
                    System.out.print("Saving the following accounts to file:");
                    
                    for( int i = 0; i < bankAccounts.size(); i++)
                        System.out.println(bankAccounts.get(i).toString());
                    
                    saveAccounts(Reference.BANKACCOUNT_DATABASE_FILE.toString(), BANK_ACCOUNT);
                    
                }
                else
                {
                    
                    System.out.print("Saving the following accounts to file:");
                    
                    for( int i = 0; i < debtAccounts.size(); i++)
                        System.out.println(debtAccounts.get(i).toString());
                    
                    saveAccounts(Reference.DEBTACCOUNT_DATABASE_FILE.toString(), DEBT_ACCOUNT);
                    
                }
                
                changePanelView(viewPanel, removePanel);
                
            }
            else
            {
                
                changePanelView(viewPanel, removePanel);
                
            }
            
            
        }
        else
        {
            
            changePanelView(viewPanel, removePanel);
            
        }
            
    }

    // Defines a helper method for Authorizing usernames and passwords
    public static boolean authLogin(User testUser)
    {

        User[] authorizedUsers = readUser(PASSWORD_FILE.toString());
        
        boolean isCorrect = false;
        for (User u : authorizedUsers)
        {
            isCorrect = compareUsers(testUser, u);
            if (isCorrect)
            {
                break;
            }
        }
        System.out.print(isCorrect);
        return isCorrect;
    }

    public static boolean compareUsers(User u1, User u2)
    {
             
        if (u1.username.equals(u2.username)
                && Arrays.equals(u1.password, u2.password))
            return true;
        else
            return false;
    }

    // Defines a helper methods to read account data from and write to an binary
    // data file.
    public static Account readBankAccount(DataInputStream in)
    {

        String name = " ";
        float bal = 0;
        float apr = 0;
        try
        {
            name = in.readUTF();
            bal = in.readFloat();
            apr = in.readFloat();
        } catch (EOFException e)
        {
            return null;
        } catch (IOException e)
        {
            System.out.println("I/O Error");
            System.exit(0);
        }
        return new Account(name, bal, apr);
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
    
    public static JButton createCustomButton(String text)
    {
        
        JButton newButton = new JButton(text);
        
        newButton.setBackground(Reference.HEADER_BACKGROUD_COLOR);
        newButton.setFont(new Font("Elephant", Font.PLAIN, 14));
        newButton.setForeground(Reference.FOOTER_BACKGROUND_COLOR);
        newButton.setBorder(BUTTON_BORDER);
        
        return newButton;
        
    }

    public static User[] readUser(String filename)
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
                ArrayList<User> userList = new ArrayList<User>();
                ois = new ObjectInputStream(new FileInputStream(filename));
                User tempUser = null;
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
                    tempUser = (User) tempSealed.getObject(objCipher);
                    if (tempUser == null)
                    {
                        eof = true;
                        break;
                    }
                    
                    userList.add(tempUser);
                }
                User[] users = userList.toArray(new User[userList.size()]);
                return users;
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
       else
           tempAccounts = debtAccounts;
       
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
    
    public static void saveDebtAccounts(String filename)
    {
        
       File deleteAccountFile = new File(filename);
       
       if(deleteAccountFile.exists())
           deleteAccountFile.delete();
       
       try
       {
           
           SealedObject sealedAccount = new SealedObject(bankAccounts.get(0),
               Reference.cipher);
           writeToFile(filename, sealedAccount, false );
           
       }catch (Exception e)
       {
           
           e.printStackTrace();
       }
       
       
       if(bankAccounts.size() > 1)
       {
           
           for ( int i = 1; i <= bankAccounts.size() -1; i++)
           {
               
               try
               {
                   
                   SealedObject sealedAccount = new SealedObject(bankAccounts.get(i),
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
    
    
    // Defines a method which calculates the total Monthly Income.
    public static float getTotalMonthlyIncome()
    {
        
        float totalIncome = 0;
        
        
        for ( int i = 0; i <= bankAccounts.size() - 1; i++)
            totalIncome += bankAccounts.get(i).monthlyIncome;
        
        return totalIncome;
        
    }
    
    
   // Defines a method which calculates the total Monthly bills.
    public static float getTotalMonthlyBills()
    {
        
        float totalPaymentBills = 0;
        float totalBills = 0;
        
        
        for ( int i = 0; i <= debtAccounts.size() - 1; i++)
            totalPaymentBills += debtAccounts.get(i).monthlyPayment;
        
        for ( int i = 0; i <= bankAccounts.size() - 1; i++)
            totalBills += bankAccounts.get(i).monthlyBills;
        
        return totalPaymentBills + totalBills;
        
    }
    
    
    // Defines a method which calculates the total available cash given the total income and bills.
    public static float getTotalCash()
    {
        
        float totalIncome = getTotalMonthlyIncome();
        float totalBills = getTotalMonthlyBills();
        
        return totalIncome - totalBills;
        
    }
    
    
    // Defines a method user to get best Debt account to pay off.

    // Defines and returns the standard Header Box for the DB9000 program.
    public static Box createHeaderBox(String title)
    {

        Box htextBox = Box.createHorizontalBox();
        JLabel headerTitle = new JLabel(title);
        headerTitle.setFont(new Font("Elephant", Font.PLAIN, 31));
        headerTitle.setForeground(Reference.HEADER_TEXT_COLOR);
        htextBox.add(Box.createHorizontalStrut(10));
        htextBox.add(Box.createHorizontalGlue());
        htextBox.add(headerTitle);
        htextBox.add(Box.createHorizontalGlue());
        htextBox.add(Box.createHorizontalStrut(10));
        Box hbBox = Box.createHorizontalBox();
        hbBox.setOpaque(true);
        hbBox.setBackground(Reference.HEADER_BORDER_COLOR);
        hbBox.add(Box.createVerticalStrut(6));
        Box headerBox = Box.createVerticalBox();
        headerBox.setOpaque(true);
        headerBox.setBackground(Reference.HEADER_BACKGROUD_COLOR);
        headerBox.add(Box.createVerticalStrut(10));
        headerBox.add(htextBox);
        headerBox.add(Box.createVerticalStrut(10));
        headerBox.add(hbBox);
        return headerBox;
    }

    // Defines and returns the standard Center Box for the DB9000 program.
    public static Box createCenterBox(Box boxObject)
    {

        Box centerBox = Box.createHorizontalBox();
        centerBox.add(Box.createHorizontalStrut(10));
        centerBox.add(Box.createHorizontalGlue());
        centerBox.add(boxObject);
        centerBox.add(Box.createHorizontalGlue());
        centerBox.add(Box.createHorizontalStrut(10));
        return centerBox;
    }

    // Defines and returns the standard Footer Box for the DB9000 program.
    public static Box createFooterBox(Box boxObject)
    {

        Box footerBox = Box.createVerticalBox();
        footerBox.setOpaque(true);
        footerBox.setBackground(Reference.FOOTER_BACKGROUND_COLOR);
        footerBox.add(Box.createVerticalStrut(30));
        footerBox.add(boxObject);
        footerBox.add(Box.createVerticalStrut(30));
        return footerBox;
    }
}
