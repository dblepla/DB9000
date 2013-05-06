package com.dlepla.db9000;

import java.io.Serializable;

public class User implements Serializable 
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    public String username;
    public char[] password;
    
    
    public User(String name, char[] pass)
    {
        
        this.username = name;
        this.password = pass;
        
    }
    
    public String toString()
    {
        
        String msg;
        String tempPass = " ";
        
        for (char c : password)
        {
            tempPass += c;
        }
        
        msg = "Username: " + username;
        msg += ", Password: " + tempPass;
        
        return msg;
               
    }
}
