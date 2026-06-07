package com.Catalina_Model.Catalina_V_0_3;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;

public class TPractical_Desire extends TAttentional_Desire{

	
//	private volatile TPropositional_Formula Trigger_Condition;
//	/**
//	 * this is the list of the belief names within the Trigger Condition
//	 */
//	private volatile ArrayList<String> Trigger_Condition_Names;
//	/**
//	 * this is the formula of the Trigger Condition
//	 */
//	private volatile String Trigger_Condition_Formula;

	private volatile TTemporal_Propositional_Formula Final_State;
	private volatile ArrayList<String> Final_State_Names;
	private volatile String Final_State_Formula;
	
	private volatile LocalDateTime Finally_Start;
	private volatile LocalDateTime Finally_End;
	private volatile LocalDateTime Global_Start;
	private volatile LocalDateTime Global_End;
	private volatile LocalDateTime Until_Start;
	private volatile LocalDateTime Until_End;


	public TPractical_Desire(String name, 
			TTemporal_Propositional_Formula final_state, 
			TPropositional_Formula trigger_Condition, 
			Double saliency, Double reward, Double relax_Preference,
			ArrayList<TGreen_Desire> list_Green_Goal, 
			ArrayList<TQuality_Desire> list_Quality_Goal,
			LocalDateTime finally_Start, LocalDateTime finally_End, 
			LocalDateTime global_Start, LocalDateTime global_End, 
			LocalDateTime untill_Start, LocalDateTime until_End,
			ArrayList<String> beliefs_Reasoner, 
			ArrayList<String> regions_Reasoner)
	{
		super(name, saliency, trigger_Condition, reward, relax_Preference, 
				list_Green_Goal, list_Quality_Goal,
				beliefs_Reasoner, regions_Reasoner);
	
		//Initialize Final State Section
		this.Final_State = final_state;
		this.Final_State_Names = new ArrayList<String>();
		for(TBelief Belief: final_state.Get_Beliefs())
		{
			this.Final_State_Names.add( Belief.Get_Name() );
		}
		this.Final_State_Formula = this.Final_State.Get_Formula();
		
//		//Initialize Trigger Condition Section
//		this.Trigger_Condition = trigger_Condition;
//		if( this.Trigger_Condition == null)
//		{
//			this.Trigger_Condition = new TPropositional_Formula();
//		}
//		this.Trigger_Condition_Names = new ArrayList<String>();
//		for(TBelief Belief: this.Trigger_Condition.Get_Beliefs())
//		{
//			this.Trigger_Condition_Names.add( Belief.Get_Name() );
//		}
//		this.Trigger_Condition_Formula = this.Trigger_Condition.Get_Formula();
		
		
		this.Finally_Start = finally_Start;
		this.Finally_End = finally_End;
		this.Global_Start = global_Start;
		this.Global_End = global_End;
		this.Until_Start = global_End;
		this.Until_End = until_End;
	}
	
	public TPractical_Desire(String name, ArrayList<String>  final_state_Names, ArrayList<String> trigger_Condition_Names, 
			String final_state_formula, String trigger_condotion_formula,
			Double saliency, Double reward, Double relax_Preference,
			ArrayList<String> list_Green_Goal_Name, ArrayList<String> list_Quality_Goal_Name,
			LocalDateTime finally_Start, LocalDateTime finally_End, LocalDateTime global_Start, LocalDateTime global_End, 
			LocalDateTime untill_Start, LocalDateTime until_End,
			ArrayList<String> beliefs_Reasoner, ArrayList<String> regions_Reasoner)
	{
		super(name, trigger_condotion_formula, trigger_Condition_Names, 
				list_Green_Goal_Name, list_Quality_Goal_Name, saliency, 
				reward, relax_Preference,
				beliefs_Reasoner, regions_Reasoner );
		//Initialize Final State Section
		this.Final_State = new TTemporal_Propositional_Formula();
		this.Final_State.Get_Temporal_Operator().Set_Start_Time(finally_Start);
		this.Final_State.Get_Temporal_Operator().Set_End_Time(finally_End);
		this.Final_State_Names = new ArrayList<String>();
		this.Final_State_Names.addAll( final_state_Names );
		this.Final_State_Formula = final_state_formula;
		
//		//Initialize Trigger Condition Section
//		this.Trigger_Condition = new TPropositional_Formula();
//		this.Trigger_Condition_Names = new ArrayList<String>();
//		this.Trigger_Condition_Names.addAll( trigger_Condition_Names );
//		this.Trigger_Condition_Formula = trigger_condotion_formula;
		
		
		
		this.Finally_Start = finally_Start;
		this.Finally_End = finally_End;
		this.Global_Start = global_Start;
		this.Global_End = global_End;
		this.Until_Start = global_End;
		this.Until_End = until_End;
	}

	public TPropositional_Formula Get_Trigger_Condition() 
	{
		return this.Trigger_Condition;
	}
	
	public ArrayList<String> Get_Trigger_Condition_Names() 
	{
		return this.Trigger_Condition_Names;
	}
	
	public String Get_Trigger_Condition_Formula() 
	{
		return this.Trigger_Condition_Formula;
	}

	public void Set_Trigger_Condition(TPropositional_Formula trigger_Condition)
	{
//		this.Trigger_Condition = trigger_Condition;
		if( this.Trigger_Condition == null)
		{
			this.Trigger_Condition = new TPropositional_Formula();
		}
		this.Trigger_Condition.Set_Formula( trigger_Condition.Get_Formula() );
		this.Trigger_Condition.Set_Name( trigger_Condition.Get_Name());
		this.Trigger_Condition.Set_Beliefs( trigger_Condition.Get_Beliefs() );
		
		this.Trigger_Condition_Names.clear();
		for(TBelief Belief: trigger_Condition.Get_Beliefs())
		{
			this.Trigger_Condition_Names.add( Belief.Get_Name() );
		}
		this.Trigger_Condition_Formula = Trigger_Condition.Get_Formula();
	}

	public TTemporal_Propositional_Formula Get_Final_State() 
	{
		return this.Final_State;
	}
	
	public void Set_Final_State(TTemporal_Propositional_Formula final_state)
	{
//		 this.Final_State = final_state;
		if(this.Final_State == null)
		{
			this.Final_State = new TTemporal_Propositional_Formula();
		}
		this.Final_State.Set_Temporal_Operator( final_state.Get_Temporal_Operator() );
		this.Final_State.Set_Beliefs(final_state.Get_Beliefs());
		this.Final_State.Set_Formula(final_state.Get_Formula());
		this.Final_State.Set_Name(final_state.Get_Name());
		
		 this.Final_State_Names.clear();
		 
		 for(TBelief Belief: final_state.Get_Beliefs())
			{
				this.Final_State_Names.add( Belief.Get_Name() );
			}
		 //this.Final_State_Formula = this.Final_State.Get_Formula();
		 this.Final_State_Formula = final_state.Get_Formula();
		 
	}
	
	public ArrayList<String> Get_Final_State_Names() 
	{
		return this.Final_State_Names;
	}
	
	public String Get_Final_State_Formula() 
	{
		return this.Final_State_Formula;
	}
	
	public LocalDateTime Get_Finally_Start()
	{
		return this.Finally_Start;
	}
	
	public LocalDateTime Get_Finally_End()
	{
		return this.Finally_End;
	}
	public LocalDateTime Get_Global_Start()
	{
		return this.Global_Start;
	}
	
	public LocalDateTime Get_Global_End()
	{
		return this.Global_End;
	}
	
	public LocalDateTime Get_Until_End()
	{
		return this.Until_End;
	}
	
	
}