package com.Catalina_Model.Catalina_V_0_3;

public abstract class TRegion 
{
	private String Name;

	public TRegion() 
	{
		this.Name = null;
	}
	
	public TRegion(String name) 
	{
		this.Name = name;
	}
	
	public String Get_Name()
	{
		return this.Name;
	}
	
	public void Set_Name(String value)
	{
		this.Name = value;
	}
}

