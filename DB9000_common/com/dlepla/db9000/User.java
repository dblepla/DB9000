package com.dlepla.db9000;

import java.io.Serializable;

//
// Defines User class and implements Serializable for encryption

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

        username = name;
        password = pass;
    }

    @Override
    public String toString()
    {

        String msg;
        String tempPass = " ";
        for (char c : this.password)
        {
            tempPass += c;
        }
        msg = "Username: " + this.username;
        msg += ", Password: " + tempPass;
        return msg;
    }
}
