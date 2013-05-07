package com.dlepla.db9000;

// Debt Buster 9000 Login Main Program

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import javax.swing.JFrame;
import com.dlepla.db9000.lib.Reference;

public class DB9000 extends JFrame
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

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

    // getting reference to JFrame object instance.

    JFrame mainWindow = this;

    public DB9000()
    {

        // Checks to see if password file exists and creates a new file if
        // needed.
        if (!Reference.doesFileExist(Reference.PASSWORD_FILE))
        {
            // Reference.createFile(Reference.PASSWORD_FILE);
            User drew = new User("dblepla", Reference.DEFAULT_PASSWORD);
            User amber = new User("alepla", Reference.DEFAULT_PASSWORD);
            User nullUser = null;

            Reference.saveUser(drew, Reference.PASSWORD_FILE.toString(), false);
            Reference.saveUser(amber, Reference.PASSWORD_FILE.toString(), true);
            Reference.saveUser(nullUser, Reference.PASSWORD_FILE.toString(),
                    true);
        } else
        {
            System.out
                    .println("Password file does exist no need to create new file. Skiping this step.");
        }

        // Checks to see if account database file exists and creates a new file
        // if needed.
        if (!Reference.doesFileExist(Reference.DBDB_FILE))
        {
            Reference.createFile(Reference.DBDB_FILE);
        } else
        {
            System.out
                    .println("Database file does exist no need to create new file. Skiping this step.");
        }

        // Checks to see if keyfile exists and creates a new file if needed.
        if (!Reference.doesFileExist(Reference.KEY_FILE))
        {
            Reference.createFile(Reference.KEY_FILE);
        } else
        {
            System.out
                    .println("Keyfile does exist no need to create new file. Skiping this step.");
        }

        this.setTitle("Debt Blaster 9000");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(700, 450);
        this.setMinimumSize(new Dimension(715, 482));
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(
                Reference.X16_ICON_LOCATION.toString()));

        LoginPanel loginPanel = new LoginPanel(mainWindow);
        this.getRootPane().setDefaultButton(loginPanel.loginButton);

        this.add(loginPanel);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);

    }
}
