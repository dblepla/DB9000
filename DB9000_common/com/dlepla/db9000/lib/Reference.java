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
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import com.dlepla.db9000.Account;
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
    
    // Initializes and sets the default password and username.
    public static final char[] DEFAULT_PASSWORD = new char[] { 'A', 'q', '2',
            '3', 'e', 's', 'a', 'Q', '@', '#', 'E', 'S' };
    public static final char[] AMBER_PASSWORD = new char[] { 'n', 'i', 'g',
            'e', 'l', '4', '2', '3' };
    public static final String DEFAULT_USERNAME = "dblepla";
    public static Cipher cipher = null;
    
    // Initializes and sets file paths for image files used by the DB9000
    // program
    public static final String X16_ICON_LOCATION = "C:\\Development\\source\\DB9000\\resources\\Icon16x16.gif";
    public static final String X32_ICON_LOCATION = "C:\\Development\\source\\DB9000\\resources\\Icon32x32.gif";
    public static final String X64_ICON_LOCATION = "C:\\Development\\source\\DB9000\\resources\\Icon64x64.gif";
    
    
    //Initializes and sets the file paths for the save files used by the program
    public static final Path PASSWORD_FILE = Paths
            .get("C:\\Development\\source\\DB9000\\resources\\bin\\dbaccess.dat");
    public static final Path DBDB_FILE = Paths
            .get("C:\\Development\\source\\DB9000\\resources\\bin\\DBDB.dat");
    public static final Path KEY_FILE = Paths
            .get("C:\\Development\\source\\DB9000\\resources\\bin\\keyfile");
    
    public static ArrayList<Account> accounts = null;
    
    public static JFrame mainWindow;
    public static JPanel loginPanel;
    public static JPanel accountPanel;
    public static JPanel overPanel;
    
    
    public static boolean isSaved = true;

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
    
    public static void changePanelView(JPanel panel)
    {
        Dimension currentSize = mainWindow.getSize();
        mainWindow.getContentPane().removeAll();
        
        mainWindow.getContentPane().add(panel);
        mainWindow.getContentPane().doLayout();
        mainWindow.update(mainWindow.getGraphics());
        mainWindow.pack();
        mainWindow.setLocationRelativeTo(mainWindow);
        mainWindow.setSize(currentSize);
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
                
                for( int i = 0; i < accounts.size(); i++)
                    System.out.println(accounts.get(i).toString());
                
                saveAccounts(DBDB_FILE.toString());
                
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
    
    
    public static void saveAndChangePanel(JPanel panel)
    {
        
        if (isSaved == false)
        {
            
            int result = JOptionPane.showConfirmDialog(null, "Account changes are not saved, if you exit this program without saving, your data will be lost. Would you like to save changes?", "Save changes before leaving?", JOptionPane.YES_NO_OPTION );
            
            if (result == JOptionPane.YES_OPTION)
            {
                
                System.out.print("Saving the following accounts to file:");
                
                for( int i = 0; i < accounts.size(); i++)
                    System.out.println(accounts.get(i).toString());
                
                saveAccounts(Reference.DBDB_FILE.toString());
                
                changePanelView(panel);
                
            }
            else
            {
                
                changePanelView(panel);
                
            }
            
            
        }
        else
        {
            
            changePanelView(panel);
            
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
    public static Account readAccount(DataInputStream in)
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
            System.out.println("File does not exist.");
            return false;
        } else
            return true;
    }
    
    public static JButton createCustomButton(String text)
    {
        
      // Defines customized JButton for program.
        EmptyBorder DB_Insets = new EmptyBorder(3,10,3,10);
        LineBorder DB_Line = new LineBorder(Reference.HEADER_BORDER_COLOR, 2);
        Border DB_Border = BorderFactory.createCompoundBorder( DB_Line, DB_Insets);
        
        JButton newButton = new JButton(text);
        
        newButton.setBackground(Reference.HEADER_BACKGROUD_COLOR);
        newButton.setFont(new Font("Elephant", Font.PLAIN, 14));
        newButton.setForeground(Reference.FOOTER_BACKGROUND_COLOR);
        newButton.setBorder(DB_Border);
        
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
    
    public static ArrayList<Account> readAccount(String filename)
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
                ArrayList<Account> accountList = new ArrayList<Account>();
                ois = new ObjectInputStream(new FileInputStream(filename));
                Account tempAccount = null;
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
                    tempAccount = (Account) tempSealed.getObject(objCipher);
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
    
    public static void saveAccounts(String filename)
    {
        
       File deleteAccountFile = new File(filename);
       
       if(deleteAccountFile.exists())
           deleteAccountFile.delete();
       
       try
       {
           
           SealedObject sealedAccount = new SealedObject(Reference.accounts.get(0),
               Reference.cipher);
           writeToFile(filename, sealedAccount, false );
           
       }catch (Exception e)
       {
           
           e.printStackTrace();
       }
       
       
       if(Reference.accounts.size() > 1)
       {
           
           for ( int i = 1; i <= Reference.accounts.size() -1; i++)
           {
               
               try
               {
                   
                   SealedObject sealedAccount = new SealedObject(Reference.accounts.get(i),
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

    // Defines and returns the standard Header Box for the DB9000 program.
    public static Box createHeaderBox(String title)
    {

        Box htextBox = Box.createHorizontalBox();
        JLabel headerTitle = new JLabel(title);
        headerTitle.setFont(new Font("Elephant", Font.PLAIN, 31));
        headerTitle.setForeground(Reference.HEADER_TEXT_COLOR);
        htextBox.add(Box.createHorizontalStrut(198));
        htextBox.add(Box.createHorizontalGlue());
        htextBox.add(headerTitle);
        htextBox.add(Box.createHorizontalGlue());
        htextBox.add(Box.createHorizontalStrut(198));
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
