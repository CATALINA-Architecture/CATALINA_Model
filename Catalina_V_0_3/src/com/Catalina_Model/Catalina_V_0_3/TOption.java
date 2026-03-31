package com.Catalina_Model.Catalina_V_0_3;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.EnumMap;

public class TOption 
{

	private volatile ArrayList<TAction> Plan_Actions;
	private volatile int ID_Current_Action; 
	private volatile LocalDateTime Satisfied_Time;
	
	public TOption() 
	{
		this.Plan_Actions = new ArrayList<TAction>();
		this.ID_Current_Action = 0;
		this.Satisfied_Time = null;
	}
	
	public TOption(ArrayList<TAction> plans)
	{
		this.Plan_Actions = new ArrayList<TAction>();
		this.Plan_Actions.addAll(plans);
		this.ID_Current_Action = 0;
	}
	
	public void Clear()
	{
		this.Plan_Actions.clear();
		this.ID_Current_Action = 0;
	}
	
	public ArrayList<TAction> Get_Plan_Actions() 
	{
		return this.Plan_Actions;
	}

	public void set_Plan_Actions(ArrayList<TAction> plan_actions) 
	{
		this.Plan_Actions.clear();
		this.Plan_Actions.addAll(plan_actions);
	}
	
	public int Get_ID_Current_Action()
	{
		return this.ID_Current_Action;
	}
	
	public void Next_ID_Current_Action()
	{
		this.ID_Current_Action++;
	}
	
	public Boolean is_Satisfied()
	{
//		Integer Size = this.Plan_Actions.size();
		return this.ID_Current_Action >= this.Plan_Actions.size();
	}

    public LocalDateTime Get_Satisfied_Time()
    {
    	return this.Satisfied_Time;
    }
    
    public void Set_Satisfied_Time(LocalDateTime value)
    {
    	this.Satisfied_Time = value;
    }
    
    public TAction Get_Current_Action()
    {
    	if ((this.ID_Current_Action <0) || (this.ID_Current_Action >= this.Plan_Actions.size()))
    	{
    		return null;
    	}
    	return this.Plan_Actions.get( this.ID_Current_Action );
    }
    
    public TAction Get_Previous_Action()
    {
    	if (this.ID_Current_Action ==0)
    	{
    		return null;
    	}
    	return this.Plan_Actions.get( this.ID_Current_Action );
    }

}
