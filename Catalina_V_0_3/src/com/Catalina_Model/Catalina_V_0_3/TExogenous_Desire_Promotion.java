package com.Catalina_Model.Catalina_V_0_3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.function.Function;

import com.Catalina_Model.Catalina_V_0_3.TExogenous_Attentional_Desires_Function_Handler.Exogenous_Attentional_Desires_Promotion_Function;
import com.Catalina_Model.Catalina_V_0_3.TMap_Exogenous_Functions_Handler.Stimulus_To_Epistemic_Function;

/**
 * It Generates an Epistemic Desire for each stimulus.
 * It uses a Map_Stimulus_To_Epistemic_Desires Map to generate the correct Epistemic
 * Desire (a type of stimulus will generate a specific epistemic desire).
 * @return
 */

/**
 *  It use a TMap_Stimulus_To_Epistemic_Desires to generate an Epistemic Desire from a Stimulus.
 * it is necessary to create for each type of stimulus a function that generates an 
 * Epistemic Desire by calling Register_Epistemic_Function and UnRegister_Epistemic_Function
 * of the TExogenous_Desire_Promotion class
 */



public class TExogenous_Desire_Promotion 
{
	private TDesire_Handler Owner;
	private TGlobal_Workspace Global_Workspace;
	private HashSet<TAttentional_Desire> Exogenous_Epistemic_Desire;
	private HashSet<TAttentional_Desire> Exogenous_Practical_Desire;
	private Boolean Computed_New_Exogenous_Desire;
	private TExogenous_Attentional_Desires_Function_Handler
				Exogenous_Attentional_Desires_Function_Handler;
	
	
	/***
	 * Map Stimulus to Functions that creates TEpistemic_Desires
	 */
	private TMap_Exogenous_Functions_Handler Map_Stimulus_To_Epistemic_Desires;
	
	public TExogenous_Desire_Promotion(TDesire_Handler Owner)
	{
		this.Owner = Owner;
		this.Global_Workspace = this.Owner.Get_Global_Workspace();
		this.Map_Stimulus_To_Epistemic_Desires = new TMap_Exogenous_Functions_Handler();
		this.Exogenous_Attentional_Desires_Function_Handler = new TExogenous_Attentional_Desires_Function_Handler();
		
		this.Exogenous_Attentional_Desires_Function_Handler.Set_Recall_Belief(
				this.Global_Workspace::Recal_Beliefs);
		
		
		this.Exogenous_Epistemic_Desire = new HashSet<TAttentional_Desire>();
		this.Exogenous_Practical_Desire = new HashSet<TAttentional_Desire>();
		this.Computed_New_Exogenous_Desire = false;
	}
	
	public Boolean Execute()
	{
		Boolean Update_Stimuli_Message = this.Owner.Message_Handler.Read_Value_And_Clear_Updated_Stimuli();
		Boolean result = false;
		this.Exogenous_Epistemic_Desire.clear();
		this.Exogenous_Practical_Desire.clear();
		
		if ( Update_Stimuli_Message == true)
		{
			HashMap<String, TBelief> Map_Beliefs = new HashMap<String, TBelief>();
			Map_Beliefs.putAll( this.Global_Workspace.Get_Map_Uninhibited_Beliefs());
		
//			TStimulus Stimulus = this.Agent.Get_GW().Get_Temp_Salient_Belief();
//			TGlobal_Workspace Global_Workspace = this.Agent.Get_Global_WorkSpace();
//			ArrayList<TStimulus> Stimuli = this.Global_Workspace.Get_Stimuli();
			TExogenous_Attentional_Desires_Promotion_Function_Result Desires_raised;
			
			
			HashSet<TStimulus> Stimuli = new HashSet<TStimulus>();
			HashSet<TStimulus> New_Stimuli = new HashSet<TStimulus>();
//			this.Global_Workspace.Get_Stimuli();
			
			ArrayList<TAttentional_Desire> Exogenous_Epistemic_Desires = new ArrayList<TAttentional_Desire>();
			ArrayList<TPractical_Desire_Data> Exogenous_Practical_desires =  
					new ArrayList<TPractical_Desire_Data>(); 
					
//			Epistemic_Desires_To_Promote.clear();
			
			Stimuli.addAll( this.Global_Workspace.Get_Stimuli( ));
			New_Stimuli.addAll( Stimuli);
			
			for(TStimulus Stimulus: New_Stimuli)
			{
				Desires_raised = this.Exogenous_Attentional_Desires_Function_Handler.
						Execute_Function_For_Desire_to_raise(Stimulus, Map_Beliefs);
				Stimuli.addAll( Desires_raised.Get_Stimuli() );

				Exogenous_Practical_desires.addAll( Desires_raised.Get_Practical_Desires_Data() );
			}
			
			if( Exogenous_Practical_desires.size() > 0)
				{
					this.Global_Workspace.Add_Inhibited_Practical_Attentional_Desires( Exogenous_Practical_desires );
				}
			
			for(TStimulus Stimulus: Stimuli)
			{
//				TEpistemic_Desire Epistemic_Desire = this.Stimulus_to_Desire(Stimulus);
				TEpistemic_Desire Epistemic_Desire = this.Map_Stimulus_To_Epistemic_Desires
								.Execute_Epistemic_Function(this.Global_Workspace, Stimulus);
				if (Epistemic_Desire != null)
				{
					Exogenous_Epistemic_Desires.add(Epistemic_Desire);
				}
				
			}
			
			this.Exogenous_Epistemic_Desire.addAll( Exogenous_Epistemic_Desires );
//			this.Computed_New_Exogenous_Desire = this.Exogenous_Desire.size() > 0;
//			this.Global_Workspace.Add_Attentional_Desires(Desires_To_Promote);
//			
//			this.Global_Workspace.Mark_as_Active_Desires(Desires_To_Promote, null);
//			if (Desires_To_Promote.size() > 0 )
//			{
//				result = true;
//			}
		}
		
		return result;
	}
	
	public void Register_Epistemic_Function(String Stimulus_Type, 
			Stimulus_To_Epistemic_Function func) 
	{
		this.Map_Stimulus_To_Epistemic_Desires.Register_Epistemic_Function( Stimulus_Type, func );
	}
	
	public void UnRegister_Epistemic_Function(String Stimulus_Type) 
	{
		this.Map_Stimulus_To_Epistemic_Desires.UnRegister_Epistemic_Function( Stimulus_Type ); 
    }
	
	public void Register_Practical_Function(String Stimulus_Type, 
			Exogenous_Attentional_Desires_Promotion_Function func) 
	{
		this.Exogenous_Attentional_Desires_Function_Handler.Register_Function(Stimulus_Type, func);
	}
	
	public void UnRegister_Practical_Function(String Stimulus_Type) 
	{
		this.Exogenous_Attentional_Desires_Function_Handler.Unregister_Function( Stimulus_Type ); 
    }
	
	public Boolean Has_New_Exogenous_Desire()
	{
		return this.Computed_New_Exogenous_Desire;
	}
	
	public ArrayList<TAttentional_Desire> Get_Exogenous_Epistemic_Desire()
	{
		ArrayList<TAttentional_Desire> result = new ArrayList<TAttentional_Desire>();
		result.addAll( this.Exogenous_Epistemic_Desire );
		this.Exogenous_Epistemic_Desire.clear();
		return result;
	}
	
	public ArrayList<TAttentional_Desire> Get_Exogenous_Practical_Desire()
	{
		ArrayList<TAttentional_Desire> result = new ArrayList<TAttentional_Desire>();
		result.addAll( this.Exogenous_Practical_Desire );
		this.Exogenous_Practical_Desire.clear();
		return result;
	}
	
}
