package com.dlepla.db9000;

import java.io.Serializable;
import java.text.NumberFormat;

public class BillAccount extends Account implements Serializable 
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

   
    
    public float monthlyPayment = 0;
    public int paymentDay = 0;
    
     static NumberFormat cf = NumberFormat.getCurrencyInstance();
     
     
     public BillAccount() {}
     
     public BillAccount(String name, float payment, int paymentDay)
     {
         
         this.accountName = name;
         this.monthlyPayment = payment;
         this.paymentDay = paymentDay;
         
     }
     
     public String toString()
     {
         
         String msg;
         cf = NumberFormat.getCurrencyInstance();
         msg = "Name: " + this.accountName;
         msg += ", Monthly Payment: "+ cf.format(this.monthlyPayment);
         msg += ", Payment Day: " + paymentDay;
         return msg;
         
     }
     
     
     
}
