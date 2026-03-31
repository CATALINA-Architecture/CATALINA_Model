package com.Catalina_Model.Catalina_V_0_3;

import java.time.LocalDateTime;

public class TPlan 
{
    private String Name;
    
    public TPlan(String name ) 
    {
    	this.Name = "";
    	if ( name != null)
    	{
    		this.Name = name;	
    	}
    }
    
    public TPlan() {
        this.Name = "";
    }
    
    public String Get_Name()
    {
    	return this.Name;
    }
    
    public void Set_Name(String value)
    {
    	this.Name = value;
    }
    
    @Override
    public String toString() {
        return "Plan: " + Name;
    }
}