package com.dlepla.db9000;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import com.dlepla.db9000.lib.GUIManager;
import com.dlepla.db9000.lib.LogoutTask;
import com.dlepla.db9000.lib.Reference;
// Login Panel class which sets up a JPanel with several Boxes to display the initial login screen.

public class LoginPanel extends JPanel
{
    /**
     * 
     */
    private static final long serialVersionUID = -7284737752069591276L;
    
    JTextField username;
    JPasswordField password;
    JButton_Default loginButton;
    JLabel headerTitle;
    JLabel overTitle;
    JButton closeButton;
    

    // Login Panel constructor which initializes and builds the layout of the
    // LoginPanel window.
    public LoginPanel()
    {

        this.setLayout(new GridBagLayout());
        this.setBackground(Reference.CENTER_BACKGROUND_COLOR);
        this.setOpaque(true);
    
      
        // create default header box with passed title text.
        Box headerBox = GUIManager.createHeaderBox("Debt Blaster 9000");
        // Add header Box to JFrame using gridbag layout.
        Reference.addItem(this, headerBox, 0, 0, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL);
        // Create Box and items to house the username and password JLabels
        Box upLabelBox = Box.createVerticalBox();
        upLabelBox.add(GUIManager.createCustomLabel("Username: "));
        upLabelBox.add(Box.createRigidArea(new Dimension(6, 8)));
        upLabelBox.add(GUIManager.createCustomLabel("Password: "));
        upLabelBox.add(Box.createRigidArea(new Dimension(75, 7)));
        // Create Box and Items to house the username and password JFields and
        // login buttons
        Box plBox = Box.createVerticalBox();
        username = new JTextField(22);
        GUIManager.formatTextField(username);
        
        password = new JPasswordField(22);
        GUIManager.formatPasswordField(password);
        
        JLabel plLabel = GUIManager.createCustomLabel("Please Login");
        plLabel.setFont(new Font("Elephant", Font.PLAIN, 16));
        
        // Defines customized JButton for program.
        EmptyBorder DB_Insets = new EmptyBorder(3,10,3,10);
        LineBorder DB_Line = new LineBorder(Reference.HEADER_BORDER_COLOR, 2);
        Border DB_Border = BorderFactory.createCompoundBorder( DB_Line, DB_Insets);
        
        loginButton = new JButton_Default();
        loginButton.setText("Login");
        loginButton.setBackground(Reference.HEADER_BACKGROUD_COLOR);
        loginButton.setFont(new Font("Elephant", Font.PLAIN, 14));
        loginButton.setForeground(Reference.FOOTER_BACKGROUND_COLOR);
        loginButton.setBorder(DB_Border);
        
        closeButton = GUIManager.createCustomButton("Exit");
        
        
        
        
        
        LoginButtonListener bll = new LoginButtonListener();
        loginButton.addActionListener(bll);
        closeButton.addActionListener(bll);
        
        plBox.add(plLabel);
        plBox.add(Box.createRigidArea(new Dimension(0, 5)));
        plBox.add(username);
        plBox.add(Box.createRigidArea(new Dimension(0, 5)));
        plBox.add(password);
        plBox.add(Box.createRigidArea(new Dimension(0, 10)));
        plBox.add(loginButton);
        // plBox.add(Box.createVerticalStrut(75));
        // Create centerItems box to house all Boxes that will go into the
        // centerBox.
        Box centerItems = Box.createHorizontalBox();
        centerItems.add(Box.createHorizontalStrut(10));
        centerItems.add(upLabelBox);
        centerItems.add(Box.createRigidArea(new Dimension(5, 7)));
        centerItems.add(plBox);
        centerItems.add(Box.createHorizontalStrut(10));
        // Create centerBox and pass centerItems Box to be added to centerBox
        Box centerBox = GUIManager.createCenterBox(centerItems);
        // Add centerBox to JFrame using gridbag layout
        Reference.addItem(this, centerBox, 0, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        
        Box footerItems = Box.createHorizontalBox();
        footerItems.add(Box.createHorizontalStrut(1));
        footerItems.add(Box.createHorizontalGlue());
        footerItems.add(closeButton);
        Box footerBox = GUIManager.createFooterBox(footerItems);
        Reference.addItem(this, footerBox, 0, 2, 1, 1,
                GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL);
    }
    
    
    // Extends JButton so we can set the JButton_Default as the default JFrame button when adding to JPanel.
    public class JButton_Default extends JButton
    {

        /**
         * 
         */
        private static final long serialVersionUID = 1L;

        @Override
        public void addNotify() {  // Upon being added to a window, make this JButton the default button of the window.
            super.addNotify();
            SwingUtilities.getRootPane( this ).setDefaultButton( this );
        }
    }

    private class LoginButtonListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {

            if (e.getSource() == loginButton)
            {
                
                UserAccount user = new UserAccount(username.getText(), password.getPassword());
                boolean loginAuthorized = true;
                loginAuthorized = Reference.authLogin(user);
              
                if (user.username.length() <= 0)
                {
                    JOptionPane
                            .showMessageDialog(
                                    getRootPane(),
                                    "Username is blank. You must enter a username and password.",
                                    "Invalid Login",
                                    JOptionPane.INFORMATION_MESSAGE);
                } else if (user.password.length == 0)
                {
                    JOptionPane
                            .showMessageDialog(
                                    getRootPane(),
                                    "Password is blank. You must enter a username and password.",
                                    "Invalid Login",
                                    JOptionPane.INFORMATION_MESSAGE);
                } else if (loginAuthorized == true)
                {
                    Reference.overPanel = new OverPanel();
                    Reference.changePanelView(Reference.overPanel, Reference.loginPanel);
                    Reference.ex = Executors.newSingleThreadScheduledExecutor();
                    Reference.ex.scheduleAtFixedRate(new LogoutTask(), 0, 1, TimeUnit.MINUTES);
                 
                } else
                {
                    JOptionPane
                            .showMessageDialog(
                                    getRootPane(),
                                    "Username or password is not found, please try again or contact administrator",
                                    "Invalid Login",
                                    JOptionPane.INFORMATION_MESSAGE);
                }
            } 
            else if(e.getSource() == closeButton)
            {
                
                Reference.mainWindow.dispatchEvent(new WindowEvent(Reference.mainWindow, WindowEvent.WINDOW_CLOSING));
                
            }
        }
    }
}
