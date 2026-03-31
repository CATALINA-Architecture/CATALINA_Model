package com.Catalina_Model.Catalina_V_0_3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class TAction_Execution_Result
{
	private Boolean Result;
	private HashMap<String, Object> Beliefs_to_Change;
	private ArrayList<TPractical_Desire_Data> Practical_Desires;
	private ArrayList<TStimulus> Stimuli;
	
	public TAction_Execution_Result()
	{
		this.Result = false;
		this.Beliefs_to_Change = new HashMap<String, Object>();
		
		this.Practical_Desires = new ArrayList<TPractical_Desire_Data>();
		this.Stimuli = new ArrayList<TStimulus>();
		
	}
	
	public void Clear()
	{
		this.Result = false;
		this.Beliefs_to_Change.clear();

		this.Practical_Desires.clear();
		this.Stimuli.clear();
	}
	
	/**
	 * Setters
	 */
	
	public void Set_Result(Boolean value)
	{
		this.Result = value;
		 
	}
	
	public void Set_Beliefs_to_Change(HashMap<String, Object> value)
	{
		this.Beliefs_to_Change.clear();
		this.Beliefs_to_Change.putAll( value );
	}
	
	public void Set_Practical_Desires(ArrayList<TPractical_Desire_Data> value)
	{
		this.Practical_Desires.clear();
		this.Practical_Desires.addAll( value );
	}
	
	public void Set_Stimulu(ArrayList<TStimulus> value)
	{
		this.Stimuli.clear();
		this.Stimuli.addAll( value );
	}
	
	/**
	 * Getters
	 */
	
	public Boolean Get_Result()
	{
		return this.Result;
	}
	
	public HashMap<String, Object> Get_Beliefs_to_Change()
	{
		return this.Beliefs_to_Change;
	}
	
	public ArrayList<TPractical_Desire_Data> Get_Practical_Desires()
	{
		return this.Practical_Desires;
	}
	
	public ArrayList<TStimulus> Get_Stimuli()
	{
		return this.Stimuli;
	}
	
	
}
