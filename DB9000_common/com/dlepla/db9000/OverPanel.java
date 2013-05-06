package com.dlepla.db9000;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.dlepla.db9000.lib.Reference;

public class OverPanel extends JPanel
{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    JLabel overTitle;
    
    JButton manageUser;

    public OverPanel()
    {

        this.setLayout(new GridBagLayout());
        this.setBackground(Reference.CENTER_BACKGROUND_COLOR);
        this.setOpaque(true);
        this.setSize(700, 450);
        manageUser = new JButton("Manage Users");
        
        Box headerBox = Reference.createHeader("Overview");
        Reference.addItem(this, headerBox, 0, 0, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        
        this.add(manageUser);

    }
}
