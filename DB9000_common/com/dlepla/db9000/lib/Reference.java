package com.dlepla.db9000.lib;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import javax.swing.Box;
import javax.swing.JComponent;
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
    
    public static final char[] DEFAULT_PASSWORD = new char[] { 'A', 'q', '2', '3', 'e', 's',
                'a', 'Q', '@', '#', 'E', 'S' };
    public static final String DEFAULT_USERNAME = "dblepla";
    
    // Initializes and sets file paths for image files used by the DB9000 program
    
    public static final String X16_ICON_LOCATION = "C:\\Development\\source\\DB9000\\resources\\Icon16x16.gif";
    public static final Path PASSWORD_FILE = Paths.get("C:\\Development\\source\\DB9000\\resources\\bin\\dbaccess.dat");
    public static final Path DBDB_FILE = Paths.get("C:\\Development\\source\\DB9000\\resources\\bin\\DBDB.dat");
    
    
    
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
        
        User[] authorizedUsers;
        
        DataInputStream passFile = getStream("dbaccess.dat");
        
        System.out.print("Entered Password: ");
        for (char c : testUser.password)
        {
            System.out.print(c);
        }
        
        
        System.out.print("\n");
        System.out.println();
        
        boolean isCorrect = false;
        
        for ( User u : authorizedUsers)
        {
            
            isCorrect = compareUsers(testUser, u);
            
            if (isCorrect)
                break;
            
        }
        
        System.out.print(isCorrect);
       
        return isCorrect;
   
    }
    
    public static boolean compareUsers(User u1, User u2)
    {
        
        if ( u1.username ==  u2.username && Arrays.equals(u1.password, u2.password))
                return true;
        else
                return false;
        
    }
    
    
    // Defines a helper methods to read account data from and write to an binary data file.
    
    public static DataInputStream getStream(String name)
    {
        
        DataInputStream in = null;
        
        try
        {
            
            File file = new File(name);
            in = new DataInputStream(new BufferedInputStream(new FileInputStream(file)));
            
        }
        catch ( FileNotFoundException e)
        {
            
            System.out.println("The file was not found.");
            System.exit(0);
            
        }
        catch (IOException e)
        {
            
            System.out.println("I/O Error creating file.");
            System.exit(0);
            
        }
        return in;
        
    }
    
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
            
        }
        catch (EOFException e)
        {
            
            return null;
            
        }
        catch (IOException e)
        {
            
            System.out.println("I/O Error");
            System.exit(0);
            
        }
        
        return new Account(name, bal, apr);
        
    }
    
    public static User[] readUsers(DataInputStream in)
    {
        
        String name = " ";
        char[] pass;
        
        try
        {
            
            name = in.readUTF();
            bal = in.readFloat();
            apr = in.readFloat();
            
        }
        catch (EOFException e)
        {
            
            return null;
            
        }
        catch (IOException e)
        {
            
            System.out.println("I/O Error");
            System.exit(0);
            
        }
        
        return new Account(name, bal, apr);
        
    }
    
    public static void closeFile(DataInputStream in)
    {
        
        try
        {
            
            in.close();
            
        }
        catch (IOException e)
        {
            
            System.out.println("I/O Error closing file.");
            System.out.println();
            
        }
    }
    
    
    // Helper Method to check and see if file exists. 
    public static boolean doesFileExist(Path p)
    {
        
        if (!Files.exists(p))
        {
            
            System.out.println("File does not exist.");
            
            return false;
            
        }
        else
            return true;
            
            
    }
    
    // Helper method to create a file
    public static void createFile(Path p)
    {
        
        try
        {
            
            Files.createFile(p);
            System.out.println("File created!");
            
        }
        catch(Exception e)
        {
            
            System.out.println("Error: " + e.getMessage());
            
        }
    }
    
    
    public static void saveToFile(Object e, String file)
    {

       try
       {
          FileOutputStream fileOut =
          new FileOutputStream(file);
          ObjectOutputStream out =
                             new ObjectOutputStream(fileOut);
          out.writeObject(e);
          out.close();
           fileOut.close();
       }catch(IOException i)
       {
           i.printStackTrace();
       }
    }
    
    public static User[] readUser(String file)
    {
       
       User[] users = null;
        
       try
       {
          FileInputStream fileIn =
                           new FileInputStream(file);
          ObjectInputStream in = new ObjectInputStream(fileIn);
          
          boolean eof = false;
          
          int i = 0;
          
          while (!eof)
          {
              
              User tempUser = (User) in.readObject();
           
              if (tempUser == null)
                  eof = true;
              else
                  users[i] = tempUser;
             
              i++;
              
          }
          
          in.close();
          fileIn.close();
          
       }catch(IOException i)
       {
          i.printStackTrace();
          System.exit(0);
       }catch(ClassNotFoundException c)
       {
          System.out.println("User class not found");
          c.printStackTrace();
          System.exit(0);
       }
       
    return users;
      
    }
    
    
    // Defines and returns the standard Header Box for the DB9000 program.
    public static Box createHeader(String title)
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
        headerBox.add(Box.createVerticalStrut(16));
        headerBox.add(htextBox);
        headerBox.add(Box.createVerticalStrut(16));
        headerBox.add(hbBox);

        return headerBox;
        
    }
    
}
   
