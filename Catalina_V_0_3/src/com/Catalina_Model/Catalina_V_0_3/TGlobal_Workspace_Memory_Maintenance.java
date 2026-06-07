package com.Catalina_Model.Catalina_V_0_3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

import com.Catalina_Model.Catalina_V_0_3.TGlobal_Workspace_Memory_Maintenance_Update_Belief_Function_Handler.Global_Workspace_Memory_Maintenance_Update_Belief_Function;

public class TGlobal_Workspace_Memory_Maintenance 
{
	private TExecutive_Memory_Maintenance_Function Parent;
	private TGlobal_Workspace Global_Workspace;
	
	private TGlobal_Workspace_Memory_Maintenance_Update_Belief_Function_Handler
				Global_Workspace_Memory_Maintenance_Update_Normal_Belief_Function_Handler;
	
	private TGlobal_Workspace_Memory_Maintenance_Update_Belief_Function_Handler
				Global_Workspace_Memory_Maintenance_Update_Critical_Belief_Function_Handler;
	
//	private TGlobal_Workspace_Memory_Maintenance_Update_Belief_Function_Handler
//				Global_Workspace_Memory_Maintenance_Update_Important_Belief_Function_Handler;
	
	public TGlobal_Workspace_Memory_Maintenance(
					TExecutive_Memory_Maintenance_Function owner,
					TGlobal_Workspace global_workspace)
	{
		this.Parent = owner;
		this.Global_Workspace = global_workspace;
		this.Global_Workspace_Memory_Maintenance_Update_Normal_Belief_Function_Handler = 
				new TGlobal_Workspace_Memory_Maintenance_Update_Belief_Function_Handler();
		this.Global_Workspace_Memory_Maintenance_Update_Normal_Belief_Function_Handler.Set_Recall_Belief(
				this.Global_Workspace::Recal_Beliefs);
		
		this.Global_Workspace_Memory_Maintenance_Update_Critical_Belief_Function_Handler = 
				new TGlobal_Workspace_Memory_Maintenance_Update_Belief_Function_Handler();
		
		this.Global_Workspace_Memory_Maintenance_Update_Critical_Belief_Function_Handler.Set_Recall_Belief(
				this.Global_Workspace::Recal_Beliefs);
	}
	/**
	 * This function executes two actions:
	 * 1- Update the uninhibited beliefs and important beliefs in Global Worskspace
	 * 2- Set the new stimuli in Global Worskspace
	 * 
	 * 1- Update the uninhibited beliefs and important beliefs in Global Worskspace
	 * for each stimulus, it executes a possible associated function to update
	 * uninhibited beliefs and important beliefs in Global Workspace, by using
	 * the "Global_Workspace_Memory_Maintenance_Update_Belief_Function_Handler"
	 * handler. In case of updating it send a communication to Global Workspace
	 * to broadcast an update message of uninhibited beliefs and/or
	 * important beliefs.
	 * 
	 * 2- It inserts new stimuli into GlobalWorkspace and broadcast an update
	 * message.
	 * 
	 * @param Stimuli
	 */
//	public void Set_Stimuli_in_GW_old(ArrayList<TStimulus> Stimuli)
//	{
//		Boolean Updated_Beliefs = false;
//		Boolean Updated_Critical_Beliefs = false;
//		
//		TGW_Memory_Maintenance_Update_Belief_Function_Result Temp_Updated_Beliefs;
//		TGW_Memory_Maintenance_Update_Belief_Function_Result Temp_Updated_Critical_Beliefs;
//		
//		HashMap<String, TBelief> Map_Beliefs = new HashMap<String, TBelief>();
//		Map_Beliefs.putAll( this.Global_Workspace.Get_Map_Uninhibited_Beliefs());
//		
//		ArrayList<TBelief> All_Beliefs = new ArrayList<TBelief>();
//		All_Beliefs.addAll( this.Global_Workspace.Get_UnInhibited_Beliefs());
//		
//		ArrayList<TBelief> Important_Beliefs = new ArrayList<TBelief>(); 
//		Important_Beliefs.addAll( this.Global_Workspace.Get_Important_Beliefs() );
//		
//		ArrayList<TPractical_Desire_Data> Practical_desires =  
//								new ArrayList<TPractical_Desire_Data>(); 
//								
//		ArrayList<TStimulus> All_Stimuli = new ArrayList<TStimulus>();
//		All_Stimuli.addAll( Stimuli );
//		
////		Beliefs.remove( Important_Beliefs );
////		for(TBelief Belief: Important_Beliefs)
////		{
////			Beliefs.remove( Important_Beliefs );
////		}
//		
//		Important_Beliefs.forEach(
//				belief -> Map_Beliefs.remove(belief.Get_Name()));
//		
//		HashMap<String, TBelief> Map_Important_Beliefs = new HashMap<String, TBelief>();
//				
//		Important_Beliefs.forEach(
//				belief -> Map_Important_Beliefs.put(belief.Get_Name(), belief));
//		
//		for(TStimulus Stimulus: Stimuli)
//		{
//			Temp_Updated_Beliefs = this.Global_Workspace_Memory_Maintenance_Update_Normal_Belief_Function_Handler.
//					Execute_Function_For_Update_Belief(Stimulus, Map_Beliefs);
//			
//			Updated_Beliefs = Updated_Beliefs | Temp_Updated_Beliefs.Get_Result();
//			
//			Practical_desires.addAll( Temp_Updated_Beliefs.Get_Practical_Desires_Data() );
//			
//			All_Stimuli.addAll( Temp_Updated_Beliefs.Get_Stimuli());
//			
//			
//			/**
//			 * 
//			 */
//						
//			Temp_Updated_Critical_Beliefs = this.Global_Workspace_Memory_Maintenance_Update_Critical_Belief_Function_Handler.
//					Execute_Function_For_Update_Belief(Stimulus, Map_Important_Beliefs);
//			
//			Updated_Critical_Beliefs = Updated_Critical_Beliefs | Temp_Updated_Critical_Beliefs.Get_Result();
//			Practical_desires.addAll( Temp_Updated_Critical_Beliefs.Get_Practical_Desires_Data() );
//			All_Stimuli.addAll( Temp_Updated_Critical_Beliefs.Get_Stimuli());
//					
//		}
//
//		if ( Updated_Beliefs )
//		{
//			this.Global_Workspace.Broadcast_Message(TType_Update_Contract.Beliefs);
//		}
//		
//		if ( Updated_Critical_Beliefs )
//		{
//			this.Global_Workspace.Broadcast_Message(TType_Update_Contract.Critical_Beliefs);
//		}
//		
//		if( Practical_desires.size() > 0)
//		{
//			this.Global_Workspace.Add_Inhibited_Practical_Attentional_Desires( Practical_desires );
//		}
//		
//		this.Global_Workspace.Set_Stimuli(All_Stimuli);
//	}
	
	public void Set_Stimuli_in_GW(ArrayList<TStimulus> Stimuli)
	{
		Boolean Updated_Beliefs = false;
		Boolean Updated_Critical_Beliefs = false;
		
		TGW_Memory_Maintenance_Update_Belief_Function_Result Temp_Updated_Beliefs;
		TGW_Memory_Maintenance_Update_Belief_Function_Result Temp_Updated_Critical_Beliefs;
		
		HashMap<String, TBelief> Map_Beliefs = new HashMap<String, TBelief>();
		Map_Beliefs.putAll( this.Global_Workspace.Get_Map_Uninhibited_Beliefs());
		
		ArrayList<TBelief> All_Beliefs = new ArrayList<TBelief>();
		All_Beliefs.addAll( this.Global_Workspace.Get_UnInhibited_Beliefs());
		
		ArrayList<TBelief> Important_Beliefs = new ArrayList<TBelief>(); 
		Important_Beliefs.addAll( this.Global_Workspace.Get_Important_Beliefs() );
		
		ArrayList<TPractical_Desire_Data> Practical_desires =  
								new ArrayList<TPractical_Desire_Data>(); 
								
//		ArrayList<TStimulus> All_Stimuli = new ArrayList<TStimulus>();
//		All_Stimuli.addAll( Stimuli );
		
//		Beliefs.remove( Important_Beliefs );
//		for(TBelief Belief: Important_Beliefs)
//		{
//			Beliefs.remove( Important_Beliefs );
//		}
		
		Important_Beliefs.forEach(
				belief -> Map_Beliefs.remove(belief.Get_Name()));
		
		HashMap<String, TBelief> Map_Important_Beliefs = new HashMap<String, TBelief>();
				
		Important_Beliefs.forEach(
				belief -> Map_Important_Beliefs.put(belief.Get_Name(), belief));
		
		for(TStimulus Stimulus: Stimuli)
		{
			Temp_Updated_Beliefs = this.Global_Workspace_Memory_Maintenance_Update_Normal_Belief_Function_Handler.
					Execute_Function_For_Update_Belief(Stimulus, Map_Beliefs);
			
			Updated_Beliefs = Updated_Beliefs | Temp_Updated_Beliefs.Get_Result();
			
			if(Temp_Updated_Beliefs.Get_Beliefs_To_Change().size() > 0)
			{
				this.Global_Workspace.Update_Uninhibited_Conscious_Beliefs
					(Temp_Updated_Beliefs.Get_Beliefs_To_Change());
			}
			
//			Practical_desires.addAll( Temp_Updated_Beliefs.Get_Practical_Desires_Data() );
			
//			All_Stimuli.addAll( Temp_Updated_Beliefs.Get_Stimuli());
			
			
			/**
			 * 
			 */
						
			Temp_Updated_Critical_Beliefs = this.Global_Workspace_Memory_Maintenance_Update_Critical_Belief_Function_Handler.
					Execute_Function_For_Update_Belief(Stimulus, Map_Important_Beliefs);
			
			if(Temp_Updated_Critical_Beliefs.Get_Beliefs_To_Change().size() > 0)
			{
				this.Global_Workspace.Update_Uninhibited_Conscious_Beliefs
					(Temp_Updated_Critical_Beliefs.Get_Beliefs_To_Change());
			}
			
			Updated_Critical_Beliefs = Updated_Critical_Beliefs | Temp_Updated_Critical_Beliefs.Get_Result();
//			Practical_desires.addAll( Temp_Updated_Critical_Beliefs.Get_Practical_Desires_Data() );
//			All_Stimuli.addAll( Temp_Updated_Critical_Beliefs.Get_Stimuli());
					
		}

		if ( Updated_Beliefs )
		{
			this.Global_Workspace.Broadcast_Signal(TType_Update_Contract.Updated_Beliefs);
		}
		
		if ( Updated_Critical_Beliefs )
		{
			this.Global_Workspace.Broadcast_Signal(TType_Update_Contract.Updated_Critical_Beliefs);
		}
		
//		if( Practical_desires.size() > 0)
//		{
//			this.Global_Workspace.Add_Inhibited_Practical_Attentional_Desires( Practical_desires );
//		}
		
//		this.Global_Workspace.Set_Stimuli(All_Stimuli);
		this.Global_Workspace.Set_Stimuli( Stimuli );
	}
	
//	HashMap<String, Object> Beliefs_To_Change = new HashMap<String, Object>();
//
//	Beliefs_To_Change.put("BL_Position_City", new ArrayList<>(Arrays.asList("Object_Complement", Integer_End_City, "Me")));
	
	public void Register_Update_Beliefs_Function(String Stimulus_Type, 
			Global_Workspace_Memory_Maintenance_Update_Belief_Function func) 
	{
		this.Global_Workspace_Memory_Maintenance_Update_Normal_Belief_Function_Handler.
								Register_Function( Stimulus_Type, func );
	}
	
	public boolean Unregister_Update_Beliefs_Function(String Stimulus_Type) 
	{
		return this.Global_Workspace_Memory_Maintenance_Update_Normal_Belief_Function_Handler.
							Unregister_Function( Stimulus_Type );
	}

	public void Add_Uninhibited_Preconditions(HashSet<TBelief> Pre_Conditions)
    {
		this.Global_Workspace.Add_Uninhibited_Preconditions( Pre_Conditions );
		
    }
	
	public void Associate_Functions_to_Inhibited_Desires(
    		ArrayList<TPractical_Desire_Data> Desires_Data)
    {
		this.Global_Workspace.Associate_Functions_to_Inhibited_Desires( Desires_Data );
    }
	
}
