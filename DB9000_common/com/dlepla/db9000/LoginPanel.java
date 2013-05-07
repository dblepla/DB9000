package com.dlepla.db9000;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.dlepla.db9000.lib.Reference;
// Login Panel class which sets up a JPanel with several Boxes to display the initial login screen.

class LoginPanel extends JPanel
{
    /**
     * 
     */
    private static final long serialVersionUID = -7284737752069591276L;
    
    JTextField username;
    JPasswordField password;
    JButton loginButton;
    JLabel headerTitle;
    JLabel overTitle;

    // Login Panel constructor which initializes and builds the layout of the
    // LoginPanel window.
    public LoginPanel()
    {

        this.setLayout(new GridBagLayout());
        this.setBackground(Reference.CENTER_BACKGROUND_COLOR);
        this.setOpaque(true);
        
        
        // create default header box with passed title text.
        Box headerBox = Reference.createHeaderBox("Debt Blaster 9000");
        // Add header Box to JFrame using gridbag layout.
        Reference.addItem(this, headerBox, 0, 0, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL);
        // Create Box and items to house the username and password JLabels
        Box upLabelBox = Box.createVerticalBox();
        upLabelBox.add(new JLabel("Username: "));
        upLabelBox.add(Box.createRigidArea(new Dimension(6, 8)));
        upLabelBox.add(new JLabel("Password: "));
        upLabelBox.add(Box.createRigidArea(new Dimension(75, 7)));
        // Create Box and Items to house the username and password JFields and
        // login buttons
        Box plBox = Box.createVerticalBox();
        username = new JTextField(22);
        username.setMaximumSize(username.getPreferredSize());
        password = new JPasswordField(22);
        password.setMaximumSize(password.getPreferredSize());
        JLabel plLabel = new JLabel("Please Login");
        plLabel.setFont(new Font("Elephant", Font.PLAIN, 16));
        loginButton = new JButton("Login");
        LoginButtonListener bll = new LoginButtonListener();
        loginButton.addActionListener(bll);
        // plBox.add(Box.createVerticalStrut(75));
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
        centerItems.add(upLabelBox);
        centerItems.add(Box.createRigidArea(new Dimension(5, 7)));
        centerItems.add(plBox);
        // Create centerBox and pass centerItems Box to be added to centerBox
        Box centerBox = Reference.createCenterBox(centerItems);
        // Add centerBox to JFrame using gridbag layout
        Reference.addItem(this, centerBox, 0, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        Box footerItems = Box.createHorizontalBox();
        footerItems.add(Box.createHorizontalStrut(110));
        Box footerBox = Reference.createFooterBox(footerItems);
        Reference.addItem(this, footerBox, 0, 2, 1, 1,
                GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL);
    }

    private class LoginButtonListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {

            if (e.getSource() == loginButton)
            {
                User user = new User(username.getText(), password.getPassword());
                boolean loginAuthorized = true;
                loginAuthorized = Reference.authLogin(user);
                System.out.print("loginAuthorized = " + loginAuthorized);
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
                    OverPanel overPanel = new OverPanel();
                    Reference.mainWindow.getContentPane().removeAll();
                    Reference.mainWindow.getContentPane().add(overPanel);
                    Reference.mainWindow.getContentPane().doLayout();
                    update(Reference.mainWindow.getGraphics());
                    Reference.mainWindow.pack();
                    // mainWindow.setVisible(true);
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
        }
    }
}
