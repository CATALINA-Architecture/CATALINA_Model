package com.Catalina_Model.Catalina_V_0_3;

import java.util.ArrayList;

public class TDesire {
	
	private volatile Double Saliency;
	private volatile Boolean Checked = false;
	private volatile Double Reward;
	private volatile Double Relax_Preference;
	private volatile String Name;

	private TType_Status_Desire Status_Desire;

	/**
	* Constructor
	*/
	public TDesire(String name, Double saliency, Double reward, Double relax_Preference )
	{
		this.Name = name;
		this.Saliency = saliency;
		this.Reward = reward;
		this.Relax_Preference = relax_Preference;
		this.Checked = false;
		
		this.Status_Desire = TType_Status_Desire.Standing;
	}
	
	/**
	* Get the Saliency of the Desire
	*/
	public Double Get_Saliency()
	{
		return this.Saliency;
	}
	
	/**
	* Set the Saliency of the Desire
	*/
	public void Set_Saliency(double saliency) 
	{
		this.Saliency = saliency;
	}
	
	/**
	* Get the Reward of the Desire
	*/
	public Double Get_Reward() 
	{
		return this.Reward;
	}
	
	/**
	* Set the Reward of the Desire
	*/
	public void Set_Reward(double reward) 
	{
		Reward = reward;
	}
	
	/**
	* Get the Relax Preference of the Desire
	*/
	public Double Get_Relax_Preference() 
	{
		return this.Relax_Preference;
	}
	
	/**
	* Set the Relax Preference of the Desire
	*/
	public void Set_Relax_Preference(double relax_Preference) {
		this.Relax_Preference = relax_Preference;
	}

	/**
	* Check if the Desire is satisfied
	*/
	public Boolean Check_Satisfation()
	{
		return this.Checked;
	}
	
	public void Set_Satisfation(Boolean value)
	{
		this.Checked = value;
	}
	
	/**
	* Get the Name of the Desire
	*/
	public String Get_Name() 
	{
		return this.Name;
	}

	/**
	* Set the Name of the Desire
	*/
	public void Set_Status_Desire(TType_Status_Desire Value) 
	{
		this.Status_Desire = Value;
	}
	
	/**
	* Get the Name of the Desire
	*/
	public TType_Status_Desire Get_Status_Desire() 
	{
		return this.Status_Desire;
	}

	/**
	* Set the Name of the Desire
	*/
	public void Set_Name(String name) 
	{
		this.Name = name;
	}
	
	@Override
    public String toString() {
        return "TDesire[" +
               "Name='" + Name + '\'' +
               ", Saliency=" + Saliency +
               ", Reward=" + Reward +
               ", Relax_Preference=" + Relax_Preference +
               ", Status=" + Status_Desire +
               ", Checked=" + Checked +
               ']';
    }
}
