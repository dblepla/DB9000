package com.dlepla.db9000.lib;

import org.joda.time.DateTime;

import com.dlepla.db9000.LoginPanel;

//*****************************************************************************
//
// New Runnable logout Task that checks if the current times is greater then 
// the assigned inactivity time and sets the JPanel to the logout screen if so.
// 
//*****************************************************************************

public class LogoutTask implements Runnable
{
    
    int currentTime = 0;
    
    public void run()
    {
        
        currentTime = new DateTime().getMinuteOfDay();
        System.out.println("Last Action Time: " + Reference.lastActionMinute);
        System.out.println("Current Time: " + currentTime);
        
        if((currentTime - Reference.lastActionMinute ) > Reference.LOGOUT_MINUTES )
        {
            
            
            Reference.loginPanel = new LoginPanel();
            Reference.ex.shutdownNow();
            Reference.bankAccountPanel.setVisible(false);
            Reference.debtAccountPanel.setVisible(false);
            Reference.billAccountPanel.setVisible(false);
            Reference.changePanelView(Reference.loginPanel,Reference.overPanel);
            
        }
        
    }
    
}
