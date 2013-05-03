package com.dlepla.db9000;
// Debt Buster 9000 Login Main Program

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

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
    JFrame mainWindow = this;
    
    JTextField username;
    JPasswordField password;
    JButton loginButton;
    JLabel headerTitle;
    JLabel overTitle;

    public DB9000()
    {

        this.setTitle("Debt Blaster 9000");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(700, 450);
        this.setIconImage(Toolkit.getDefaultToolkit().getImage((Reference.X16_ICON_LOCATION.toString())));
        
        /*
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        */
        
        LoginPanel loginPanel = new LoginPanel(mainWindow);
        this.getRootPane().setDefaultButton(loginPanel.loginButton);
        
        this.add(loginPanel);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        
    }
}
