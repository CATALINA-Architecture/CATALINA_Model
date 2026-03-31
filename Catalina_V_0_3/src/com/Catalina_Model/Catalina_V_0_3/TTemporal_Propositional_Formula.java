package com.Catalina_Model.Catalina_V_0_3;

import java.util.ArrayList;

public class TTemporal_Propositional_Formula extends TPropositional_Formula
{
	
	private volatile TTemporal_Operator Temporal_Operator;
	private volatile ArrayList<TTemporal_Propositional_Formula> List_Temporal_Propositional_Formula;
	private volatile ArrayList<String> List_Temporal_Propositional_Formula_Names;
	
	
	public TTemporal_Propositional_Formula()
	{
		super();
		this.Temporal_Operator = new TTemporal_Operator();
		this.List_Temporal_Propositional_Formula = new ArrayList<TTemporal_Propositional_Formula>();
		this.List_Temporal_Propositional_Formula_Names = new ArrayList<String>();
	}
	
	public TTemporal_Operator Get_Temporal_Operator()
	{
		return this.Temporal_Operator;
	}
	
	public void Set_Temporal_Operator(TTemporal_Operator Value)
	{
		this.Temporal_Operator.Set_Start_Time( Value.Get_Start_Time() );
		this.Temporal_Operator.Set_End_Time( Value.Get_End_Time());
		this.Temporal_Operator.Set_Type_Temporal_Operator( Value.Get_Type_Temporal_Operator() );
		this.Temporal_Operator.Set_Temporal_Operator_Name( Value.Get_Temporal_Operator_Name() );
	}
	
	/**
	 * this function gets only Names of the temporal Propositional Formulas.
	 * It doesn't get other information.
	 * @return
	 */
	public ArrayList<String> Get_List_Temporal_Propositional_Formula_Names()
	{
		ArrayList<String> result = new ArrayList<String>();
		result.addAll( this.List_Temporal_Propositional_Formula_Names );
		return result;
	}
	
	public void Set_List_Temporal_Propositional_Formula_Names(ArrayList<String> List_Elements)
	{
		this.List_Temporal_Propositional_Formula_Names.clear();
		if( List_Elements != null)
		{
			this.List_Temporal_Propositional_Formula_Names.addAll( List_Elements );
		}
		
	}
	
	public ArrayList<TTemporal_Propositional_Formula> Get_List_Temporal_Propositional_Formula()
	{
		ArrayList<TTemporal_Propositional_Formula> result = new ArrayList<TTemporal_Propositional_Formula>();
		result.addAll( this.List_Temporal_Propositional_Formula );
		return result;
	}
	
	public void Set_List_Temporal_Propositional_Formula(ArrayList<TTemporal_Propositional_Formula> List_Elements)
	{
		this.List_Temporal_Propositional_Formula.clear();
		this.List_Temporal_Propositional_Formula.addAll( List_Elements );
	}
	
	
}
