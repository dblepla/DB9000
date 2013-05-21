package com.dlepla.db9000.lib;

import java.awt.Font;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;

public class GUIManager
{
    
    
    //**********************************************************************
    //
    //Defines a Method to create a customized button GUI for this program.
    //
    //**********************************************************************
    public static JButton createCustomButton(String text)
    {
        
        JButton newButton = new JButton(text);
        
        newButton.setBackground(Reference.HEADER_BACKGROUD_COLOR);
        newButton.setFont(new Font("Elephant", Font.PLAIN, 14));
        newButton.setForeground(Reference.FOOTER_BACKGROUND_COLOR);
        newButton.setBorder(Reference.BUTTON_BORDER);
        
        return newButton;
        
    }
    
    
    //**********************************************************************
    //
    // Defines and returns the standard Header Box for the DB9000 program.
    //
    //**********************************************************************
    public static Box createHeaderBox(String title)
    {

        Box htextBox = Box.createHorizontalBox();
        JLabel headerTitle = new JLabel(title);
        headerTitle.setFont(new Font("Elephant", Font.PLAIN, 31));
        headerTitle.setForeground(Reference.HEADER_TEXT_COLOR);
        
        htextBox.add(Box.createHorizontalStrut(10));
        htextBox.add(Box.createHorizontalGlue());
        htextBox.add(headerTitle);
        htextBox.add(Box.createHorizontalGlue());
        htextBox.add(Box.createHorizontalStrut(10));
        
        Box hbBox = Box.createHorizontalBox();
        hbBox.setOpaque(true);
        hbBox.setBackground(Reference.HEADER_BORDER_COLOR);
        hbBox.add(Box.createVerticalStrut(6));
        
        Box headerBox = Box.createVerticalBox();
        headerBox.setOpaque(true);
        headerBox.setBackground(Reference.HEADER_BACKGROUD_COLOR);
        headerBox.add(Box.createVerticalStrut(10));
        headerBox.add(htextBox);
        headerBox.add(Box.createVerticalStrut(10));
        headerBox.add(hbBox);
        
        return headerBox;
    }
    
    
    //**********************************************************************
    //
    // Defines and returns the standard Center Box for the DB9000 program.
    //
    //**********************************************************************
    public static Box createCenterBox(Box boxObject)
    {

        Box centerBox = Box.createVerticalBox();
        centerBox.setBorder(Reference.DB_Insets);
        //centerBox.add(Box.createVerticalStrut(5));
        centerBox.add(Box.createVerticalGlue());
        centerBox.add(boxObject);
        centerBox.add(Box.createVerticalGlue());
        //centerBox.add(Box.createVerticalStrut(10));
        return centerBox;
    }
    
    
    //**********************************************************************
    //
    // Defines and returns the standard Footer Box for the DB9000 program.
    //
    //**********************************************************************
    public static Box createFooterBox(Box boxObject)
    {

        Box footerBox = Box.createVerticalBox();
        footerBox.setBorder(Reference.DB_Insets);
        footerBox.setOpaque(true);
        footerBox.setBackground(Reference.FOOTER_BACKGROUND_COLOR);
        footerBox.add(Box.createVerticalStrut(27));
        footerBox.add(boxObject);
        footerBox.add(Box.createVerticalStrut(27));
        return footerBox;
    }
    
}
