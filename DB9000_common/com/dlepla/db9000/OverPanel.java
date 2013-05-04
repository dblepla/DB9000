package com.dlepla.db9000;

import java.awt.GridBagLayout;
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

    public OverPanel()
    {

        this.setLayout(new GridBagLayout());
        this.setBackground(Reference.CENTER_BACKGROUND_COLOR);
        this.setOpaque(true);
        this.setSize(700, 450);
        overTitle = new JLabel("Overview Panel Test");
        this.add(overTitle);

    }
}
