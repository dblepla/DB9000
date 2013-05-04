package com.dlepla.db9000.lib;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import javax.swing.JComponent;
import javax.swing.JPanel;

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
    
    public static final Path X16_ICON_LOCATION = Paths.get("C:\\Development\\source\\DB9000\\resources\\Icon16x16.gif");
    
    
    
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
    
    public static boolean authLogin(String un, char[] pw)
    {

        System.out.print("Entered Password: ");
        for (char c : pw)
        {
            System.out.print(c);
        }
        System.out.print("\n");
        System.out.println();
        boolean isCorrect = true;
        isCorrect = Arrays.equals(pw, DEFAULT_PASSWORD);
        System.out.print(isCorrect);
        if (un.equals(DEFAULT_USERNAME))
            return isCorrect;
        else
            return false;
    }
    
}
   
