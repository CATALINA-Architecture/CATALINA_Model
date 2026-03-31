package com.Catalina_Model.Catalina_V_0_3;

import java.util.ArrayList;
import java.util.HashSet;

import com.Catalina_Model.Catalina_V_0_3.TBelief_Inhibition_Function_Handler.TBelief_Inhibition_Function;
import com.Catalina_Model.Catalina_V_0_3.TMeans_End_Practical_Functions_Handler.Means_End_Practical_Function;
import com.Catalina_Model.Catalina_V_0_3.TRegion_Inhibition_Function_Handler.TRegion_Inhibition_Function;

public class TPractical_Desire_Data 
{
	private TPractical_Desire Practical_Desire;
//	private TStimulus Stimulus;
	private HashSet<String> Associated_Preconditions;
//	private HashSet<TOption_Execution_Result> Precondition_FUnction;
	private HashSet<TBelief_Inhibition_Function> Associated_Beliefs_Inhibition_Functions;
	private HashSet<TRegion_Inhibition_Function> Associated_Regions_Inhibition_Functions;
	//Means_End_Practical_Function func
	private Means_End_Practical_Function Associated_Means_End_Reasoner_Function;
	
//	private HashSet<TOption_Execution_Result> Option_Execution_Functions;

	public TPractical_Desire_Data() 
	{
		this.Practical_Desire = null;
//		this.Stimulus = null;
		this.Associated_Preconditions = new HashSet<String>();
//		this.Precondition_FUnction = new HashSet<TOption_Execution_Result>();
		this.Associated_Beliefs_Inhibition_Functions = new HashSet<TBelief_Inhibition_Function>();
		this.Associated_Regions_Inhibition_Functions = new HashSet<TRegion_Inhibition_Function>();
		this.Associated_Means_End_Reasoner_Function = null;
//		this.Option_Execution_Functions = new HashSet<TOption_Execution_Result>();
	}
	
	public void Clear()
	{
		this.Practical_Desire = null;
//		this.Stimulus = null;
		this.Associated_Preconditions.clear();
		this.Associated_Beliefs_Inhibition_Functions.clear();
		this.Associated_Regions_Inhibition_Functions.clear();
//		this.Option_Execution_Functions.clear();
	}

	/**
	 * Getters
	 */
	public TPractical_Desire Get_Practical_Desire() 
	{
		return this.Practical_Desire;
	}

	public HashSet<String> Get_Preconditions() 
	{
		return this.Associated_Preconditions;
	}

	public HashSet<TBelief_Inhibition_Function> Get_Beliefs_Inhibition_Functions() 
	{
		return this.Associated_Beliefs_Inhibition_Functions;
	}

	public HashSet<TRegion_Inhibition_Function> Get_Regions_Inhibition_Functions() 
	{
		return this.Associated_Regions_Inhibition_Functions;
	}
	
//	public HashSet<TOption_Execution_Result> Get_Option_Execution_Functions() 
//	{
//		return this.Option_Execution_Functions;
//	}

	/**
	 * Setters
	 */
	public void Set_Practical_Desire(TPractical_Desire attentional_Desire) 
	{
		this.Practical_Desire = attentional_Desire;
	}

//	public void Set_Stimulus(TStimulus value)
//	{
//		this.Stimulus = value;	
//	}

	public void Set_Preconditions_Functions(HashSet<String> preconditions)
	{
		this.Associated_Preconditions.clear();
		if ((preconditions != null) && (preconditions.size() > 0))
		{
			this.Associated_Preconditions.addAll(preconditions);
		}
		
	}
	
	public void Add_Preconditions(HashSet<String> preconditions)
	{
		if (preconditions != null)
		{
			this.Associated_Preconditions.addAll( preconditions );
		}
		
	}
	
	public void Add_Beliefs_Inhibition_Functions(TBelief_Inhibition_Function belief)
	{
		if (belief != null)
		{
			this.Associated_Beliefs_Inhibition_Functions.add(belief);
		}
		
	}
	
	public void Add_Regions_Inhibition_Functions(TRegion_Inhibition_Function region)
	{
		if (region != null)
		{
			this.Associated_Regions_Inhibition_Functions.add(region);
		}
		
	}
	
//	public void Add_Option_Execution_Functions(TOption_Execution_Result value)
//	{
//		if (value != null)
//		{
//			this.Option_Execution_Functions.add(value);
//		}
//		
//	}
	
	public void Set_Beliefs_Functions(HashSet<TBelief_Inhibition_Function> beliefs_to_Use) {
		this.Associated_Beliefs_Inhibition_Functions.clear();
		this.Associated_Beliefs_Inhibition_Functions.addAll(beliefs_to_Use);
	}

	public void Set_Regions_Functions(HashSet<TRegion_Inhibition_Function> regions) {
		this.Associated_Regions_Inhibition_Functions.clear();
		this.Associated_Regions_Inhibition_Functions.addAll(regions);
	}
	
//	public void Set_Option_Execution_Functions(HashSet<TOption_Execution_Result> value) 
//	{
//			this.Option_Execution_Functions.clear();
//			this.Option_Execution_Functions.addAll( value );
//	}
	
	public void Set_Means_End_Reasoner_Function(Means_End_Practical_Function value)
	{
		this.Associated_Means_End_Reasoner_Function = value;
	}
	
	public Means_End_Practical_Function Get_Means_End_Reasoner_Function()
	{
		return this.Associated_Means_End_Reasoner_Function;
	}

}
