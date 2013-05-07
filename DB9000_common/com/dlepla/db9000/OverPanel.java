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
        Box headerBox = Reference.createHeaderBox("Account Overview");
        Reference.addItem(this, headerBox, 0, 0, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL);
        Box centerItems = Box.createHorizontalBox();
        centerItems.add(manageUser);
        Box centerBox = Reference.createCenterBox(centerItems);
        Reference.addItem(this, centerBox, 0, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        Box footerItems = Box.createHorizontalBox();
        footerItems.add(Box.createHorizontalStrut(110));
        Box footerBox = Reference.createFooterBox(footerItems);
        Reference.addItem(this, footerBox, 0, 2, 1, 1,
                GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL);
    }
}
