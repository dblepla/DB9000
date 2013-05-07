package com.dlepla.db9000.lib;

import java.awt.Color;
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

import javax.crypto.Cipher;
import javax.crypto.SealedObject;
import javax.crypto.SecretKey;
import javax.swing.Box;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

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
    
    public static JFrame mainWindow;

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
        
        System.out.println("Compairing two users:");
        System.out.println("Entered\t\tAuthorized");
        System.out.println(u1.username + "\t\t" + u2.username);
        for (char c : u1.password)
        {
            System.out.print(c);
        }
        System.out.print("\t\t");
        for (char c : u2.password)
        {
            System.out.print(c);
        }
        System.out.print("\n\n");
       
        
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

    public static void writeToFile(String filename, Object object,
            boolean append)
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
    private static Object readFromFile(String filename) throws Exception
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
        // centerBox.add(Box.createHorizontalStrut(100));
        centerBox.add(Box.createHorizontalGlue());
        centerBox.add(boxObject);
        centerBox.add(Box.createHorizontalGlue());
        // centerBox.add(Box.createHorizontalStrut(100));
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
