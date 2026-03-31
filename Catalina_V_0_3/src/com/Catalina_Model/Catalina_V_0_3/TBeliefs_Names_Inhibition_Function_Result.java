package com.Catalina_Model.Catalina_V_0_3;

import java.util.ArrayList;
import java.util.HashSet;

public class TBeliefs_Names_Inhibition_Function_Result
{
	
//	private HashSet<String> Associated_Preconditions;
	private HashSet<String> Associated_Beliefs;
	
	public TBeliefs_Names_Inhibition_Function_Result()
	{
//		this.Associated_Preconditions = new HashSet<String>();
		this.Associated_Beliefs = new HashSet<String>();
	}
	
//	public void Set_Associated_Preconditions(HashSet<String> values)
//	{
//		this.Associated_Preconditions.clear();
//		this.Associated_Preconditions.addAll(values);
//	}
	
	public void Set_Associated_Beliefs(HashSet<String> values)
	{
		this.Associated_Beliefs.clear();
		this.Associated_Beliefs.addAll(values);
	}
	
//	public void Add_Associated_Preconditions(HashSet<String> values)
//	{
//		this.Associated_Preconditions.addAll(values);
//	}
	
	public void Add_Associated_Beliefs(HashSet<String> values)
	{
		this.Associated_Beliefs.addAll(values);
	}
	
//	public HashSet<String> Get_Associated_Preconditions()
//	{
//		return this.Associated_Preconditions;
//	}
	
	public HashSet<String> Get_Associated_Beliefs()
	{
		return this.Associated_Beliefs;
	}
	
	public void Clear()
	{
//		this.Associated_Preconditions.clear();
		this.Associated_Beliefs.clear();
	}
	
	
	

}
