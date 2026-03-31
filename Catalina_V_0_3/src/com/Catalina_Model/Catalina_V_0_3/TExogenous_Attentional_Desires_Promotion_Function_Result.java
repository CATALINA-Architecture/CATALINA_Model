package com.Catalina_Model.Catalina_V_0_3;

import java.util.ArrayList;

public class TExogenous_Attentional_Desires_Promotion_Function_Result
{
	private Boolean Result;
	private ArrayList<TPractical_Desire_Data> Practical_Desires_Data;
	private ArrayList<TStimulus> Stimuli;
	
	public TExogenous_Attentional_Desires_Promotion_Function_Result()
	{
		this.Result = false;
		this.Practical_Desires_Data = new ArrayList<TPractical_Desire_Data>();
		this.Stimuli = new ArrayList<TStimulus>();
	}
	
	public void Set_Result(Boolean value)
	{
		this.Result = value;
	}
	
	public void Set_Practical_Desires_Data(
			ArrayList<TPractical_Desire_Data> value)
	{
		this.Practical_Desires_Data.clear();
		if ((value != null) && (value.size() > 0))
		{
			this.Practical_Desires_Data.addAll( value );
		}
	}
	
	public void Add_Practical_Desires_Data(
			ArrayList<TPractical_Desire_Data> value)
	{
		if ((value != null) && (value.size() > 0))
		{
			this.Practical_Desires_Data.addAll( value );
		}
	}
	
	public ArrayList<TPractical_Desire_Data> Get_Practical_Desires_Data()
	{
		return this.Practical_Desires_Data;
	}
	
	public Boolean Get_Result()
	{
		return this.Result;
	}
	
	public ArrayList<TStimulus> Get_Stimuli()
	{
		return this.Stimuli;
	}
	
	public void Set_Stimuli(ArrayList<TStimulus> values)
	{
		this.Stimuli.clear();
		if ((values != null) && (values.size() > 0))
		{
			this.Stimuli.addAll( values );
		}
	}
	
	public void Add_Stimuli(
			ArrayList<TStimulus> values)
	{
		if ((values != null) && (values.size() > 0))
		{
			this.Stimuli.addAll( values );
		}
	}
	
	public void Clear()
	{
		this.Result = false;
		this.Practical_Desires_Data.clear();
		this.Stimuli.clear();
	}

}
