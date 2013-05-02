// Debt Buster 9000 Login window test

import javax.swing.*;
import java.awt.event.*;
import javax.swing.border.*;
import java.awt.*;
import java.util.*;

public class db9000 extends JFrame
{

	public static void main(String[] args)
	{

		EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                new db9000();
            }
        });
	}

	JTextField username;
	JPasswordField password;
	JButton loginButton;
	JLabel headerTitle;
	JLabel overTitle;
	Color htColor = new Color(232,255,210);
	Color hbgColor = new Color(34,177,76);
	Color hborColor = new Color(17,89,38);
	Color centerColor = new Color(226,255,198);
	Color footerColor = new Color(236,255,217);

	public db9000()
	{

		this.setTitle("Debt Blaster 9000");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(700,450);

		JPanel loginPanel = new JPanel();
		loginPanel.setLayout(new GridBagLayout());
		loginPanel.setBackground(centerColor);
		loginPanel.setOpaque(true);

		Box htextBox = Box.createHorizontalBox();

		headerTitle = new JLabel("Debt Blaster 9000");
		headerTitle.setFont(new Font("Elephant", Font.PLAIN, 31));
		headerTitle.setForeground(htColor);

		htextBox.add(Box.createHorizontalStrut(198));
		htextBox.add(Box.createHorizontalGlue());
		htextBox.add(headerTitle);
		htextBox.add(Box.createHorizontalGlue());
		htextBox.add(Box.createHorizontalStrut(198));


		Box hbBox = Box.createHorizontalBox();

		hbBox.setOpaque(true);
		hbBox.setBackground(hborColor);
		hbBox.add(Box.createVerticalStrut(6));


		Box headerBox = Box.createVerticalBox();

		headerBox.setOpaque(true);
		headerBox.setBackground(hbgColor);

		headerBox.add(Box.createVerticalStrut(16));
		headerBox.add(htextBox);
		headerBox.add(Box.createVerticalStrut(16));
		headerBox.add(hbBox);

		addItem(loginPanel, headerBox, 0, 0, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);



		Box upLabelBox = Box.createVerticalBox();


		upLabelBox.add(new JLabel("Username: "));
		upLabelBox.add(Box.createRigidArea(new Dimension(6,8)));
		upLabelBox.add(new JLabel("Password: "));
		upLabelBox.add(Box.createRigidArea(new Dimension(75,7)));
		


		Box plBox = Box.createVerticalBox();

		username = new JTextField(22);
		username.setMaximumSize(username.getPreferredSize());
		password = new JPasswordField(22);
		password.setMaximumSize(password.getPreferredSize());

		JLabel plLabel = new JLabel("Please Login");
		plLabel.setFont(new Font("Elephant", Font.PLAIN, 16));

		ButtonListener b1 = new ButtonListener();
		loginButton = new JButton("Login");
		loginButton.addActionListener(b1);
		db9000.this.getRootPane().setDefaultButton(loginButton);

		plBox.add(Box.createVerticalStrut(75));
		plBox.add(plLabel);
		plBox.add(Box.createRigidArea(new Dimension(0,5)));
		plBox.add(username);
		plBox.add(Box.createRigidArea(new Dimension(0,5)));
		plBox.add(password);
		plBox.add(Box.createRigidArea(new Dimension(0,10)));
		plBox.add(loginButton);
		plBox.add(Box.createVerticalStrut(75));


		Box centerBox = Box.createHorizontalBox();

		centerBox.add(Box.createHorizontalStrut(173));
		centerBox.add(Box.createHorizontalGlue());
		centerBox.add(upLabelBox);
		centerBox.add(Box.createRigidArea(new Dimension(5,7)));
		centerBox.add(plBox);
		centerBox.add(Box.createHorizontalGlue());
		centerBox.add(Box.createHorizontalStrut(173));

		addItem(loginPanel, centerBox, 0, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL);

		/* Box plBox = Box.createVerticalBox();

		JLabel plLabel = new JLabel("Please Login");
		plLabel.setFont(new Font("Elephant", Font.PLAIN, 16));

		plBox.add(Box.createVerticalStrut(75));
		plBox.add(plLabel);
		plBox.add(Box.createVerticalStrut(10));
	
		addItem(loginPanel, plBox, 0, 1, 1, 1, GridBagConstraints.SOUTH, GridBagConstraints.NONE);


		Box unBox = Box.createHorizontalBox();

		username = new JTextField(12);
		username.setMaximumSize(username.getPreferredSize());

		unBox.add(new JLabel("Username: "));
		unBox.add(Box.createRigidArea(new Dimension(7, 10)));
		unBox.add(username);
		unBox.add(Box.createHorizontalStrut(277));
		
		addItem(loginPanel, unBox, 0, 2, 1, 1, GridBagConstraints.EAST, GridBagConstraints.NONE);


		Box pwBox = Box.createHorizontalBox();
		
		password = new JTextField(12);
		password.setMaximumSize(password.getPreferredSize());

		pwBox.add(new JLabel("Password:"));
		pwBox.add(Box.createRigidArea(new Dimension(10, 10)));
		pwBox.add(password);
		pwBox.add(Box.createHorizontalStrut(277));
	
		addItem(loginPanel, pwBox, 0, 3, 1, 1, GridBagConstraints.EAST, GridBagConstraints.NONE);

		


		Box loginButtonBox = Box.createVerticalBox();

		loginButton = new JButton("Login");

		loginButtonBox.add(Box.createVerticalStrut(20));
		loginButtonBox.add(loginButton);
		loginButtonBox.add(Box.createVerticalStrut(100));
		
		addItem(loginPanel, loginButtonBox, 0, 2, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE);

		*/


		Box footerBox = Box.createHorizontalBox();

		footerBox.setOpaque(true);
		footerBox.setBackground(footerColor);
		footerBox.add(Box.createVerticalStrut(110));

		addItem(loginPanel, footerBox, 0, 2, 1, 1, GridBagConstraints.SOUTH, GridBagConstraints.BOTH);

		this.add(loginPanel);
		this.pack();
		this.setVisible(true);

	}


	// Creating a helper method that allows us to easily add JComponents to a JPanel using GridBagConstraints
	private void addItem(JPanel p, JComponent c, int x, int y, int width, int height, int align, int fill)
	{
		// Initializes a new GridBagConstraint veriable and object called gc
		GridBagConstraints gc = new GridBagConstraints();

		// Sets the default GridBagConstraint object veriables and uses passed veriables to set constraints that change
		gc.gridx = x;
		gc.gridy = y;
		gc.gridwidth = width;
		gc.gridheight = height;
		gc.weightx = 100.0;
		gc.weighty = 100.0;
		gc.insets = new Insets(0, 0, 0, 0);
		gc.anchor = align;
		gc.fill = fill;
	

		// Adds the passed JComponent c to the passed JPanel p using the GridBagConstraints set in gc 
		p.add(c, gc);

	}

	private class ButtonListener implements ActionListener
	{

		public void actionPerformed(ActionEvent e)
		{

			if (e.getSource() == loginButton)
			{

				String name = username.getText();
				char[] pass = password.getPassword();
				boolean loginAuthorized = true;
				loginAuthorized = authLogin(name, pass);

				System.out.print("loginAuthorized = " + loginAuthorized);

				if (name.length() <= 0)
					JOptionPane.showMessageDialog(db9000.this, "Username is blank. You must enter a username and password.", "Invalid Login", JOptionPane.INFORMATION_MESSAGE);
				else if (pass[1] == null)
					JOptionPane.showMessageDialog(db9000.this, "Password is blank. You must enter a username and password.", "Invalid Login", JOptionPane.INFORMATION_MESSAGE);
				else if (loginAuthorized == true)
				{

					JOptionPane.showMessageDialog(db9000.this, "Username and password correct, opening Overview window!");

					JPanel overPanel = new JPanel();
					
					overPanel.setLayout(new GridBagLayout());
					overPanel.setBackground(centerColor);
					overPanel.setOpaque(true);
					overPanel.setSize(700,450);

					overTitle = new JLabel("Overview Panel Test");
					overPanel.add(overTitle);

					getContentPane().removeAll();
    				getContentPane().add(overPanel);
    				getContentPane().doLayout();
    				update(getGraphics());
    				db9000.this.pack();

				}
				else
					JOptionPane.showMessageDialog(db9000.this, "Username or password is not found, please try again or contact administrator", "Invalid Login", JOptionPane.INFORMATION_MESSAGE);

			}
		}
	}

	private boolean authLogin(String un, char[] pw )
	{

		char[] correctPassword = new char[] { 'A', 'q', '2', '3', 'e', 's', 'a', 'Q', '@', '#', 'E', 'S' };

		System.out.print("Correct Password: ");

		for (char c : correctPassword)
			System.out.print(c);

		System.out.print("\n");

		System.out.print("Entered Password: ");

		for (char c : pw)
			System.out.print(c);

		System.out.print("\n");

		System.out.println();

		boolean isCorrect = true;

		isCorrect = Arrays.equals(pw, correctPassword);

		System.out.print(isCorrect);

		if (un.equals("dblepla"))
			return isCorrect;
		else
			return false;
	}
}
