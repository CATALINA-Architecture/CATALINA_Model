package com.Catalina_Model.Catalina_V_0_3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import com.Catalina_Model.Catalina_V_0_3.TBelief_Inhibition_Function_Handler.TBelief_Inhibition_Function;
import com.Catalina_Model.Catalina_V_0_3.TExecutive_Reasoner_Function.TMeans_End_Reasoner_Generate_Options_for_Practical_Desire;
import com.Catalina_Model.Catalina_V_0_3.TRegion_Inhibition_Function_Handler.TRegion_Inhibition_Function;
import com.Catalina_Model.Catalina_V_0_3.TRegions_Inhibition.Generate_Unhinibited_Regions;

/**
 * In accordance with Selected Intentions, this component creates inhibited beliefs, regions and desires
 * and uninhibited preconditions, beliefs, regions and desire.
 * 
 * The modules, Belief_Inhibition and Desires_Inhibition, are general purpose
 * 
 * Otherwise, the Regions_Inhibition module is dependent on the case study. So to computes the inhibited and
 * uninhibited regions, the Regions_Inhibition module uses 
 * 
 * the function "Generate_Unhinibited_Regions_for_Intention" to generate the un/inhibited regions for
 * the intentions. In this function, each selected intentions will be used to compute the regions. 
 * This function needs to be set using "Set_Generate_Unhinibited_Regions_for_Intention". 
 * 
 */

public class TExecutive_Inhibition_Function extends TAgent_Base_Thread
{

	
	private TGlobal_Workspace Global_Workspace;
	private TBelief_Inhibition Belief_Inhibition;
	private TDesires_Inhibition Desires_Inhibition;
	private TRegions_Inhibition Regions_Inhibition;
	
	private ArrayList<TBelief> All_Beliefs;
	
	private ArrayList<TAttentional_Desire> Attentional_Desires;
	private ArrayList<TIntention> Selected_Intentions; 
	private ArrayList<TRegion> All_Regions; 
	private HashSet<String> List_Important_Belief_Type;
//	public Generate_Unhinibited_Regions Generate_Unhinibited_Regions_for_Intention;
	
	
//	private ArrayList<TAttentional_Desire> Uninhibited_Desires;
//	private ArrayList<TBelief> Uninhibited_Beliefs;
//	private ArrayList<TRegion> Uninhibited_Regions;
//	private ArrayList<TBelief> Computed_Pre_Conditions;
	
	public TExecutive_Inhibition_Function(TAgent agent) 
	{
		super(agent, "Executive Inhibition Function");

		this.Global_Workspace = agent.Get_Global_WorkSpace();
		this.Belief_Inhibition = new TBelief_Inhibition( this );
		this.Desires_Inhibition = new TDesires_Inhibition( this );
		this.Regions_Inhibition = new TRegions_Inhibition( this );
		
		this.All_Beliefs = new ArrayList<TBelief>();
		this.Attentional_Desires = new ArrayList<TAttentional_Desire>();
		this.Selected_Intentions = new ArrayList<TIntention>();
		this.All_Regions = new ArrayList<TRegion>();
		
		this.List_Important_Belief_Type = new HashSet<String>();
		
//		this.Uninhibited_Desires = new ArrayList<TAttentional_Desire>();
//		this.Uninhibited_Beliefs = new ArrayList<TBelief>();
//		this.Uninhibited_Regions = new ArrayList<TRegion>();
//		this.Computed_Pre_Conditions = new ArrayList<TBelief>();
	}

	@Override
	public void Insert_in_List_Update_Contract() 
	{
		
		this.Agent.Get_Global_WorkSpace().Insert_for_Update_Contract(TType_Update_Contract.Updated_Selected_Intentions, this);
		this.Agent.Get_Global_WorkSpace().Insert_for_Update_Contract(TType_Update_Contract.Updated_Thresholds, this);
	}
	
	@Override
	public void Execute()
	{
		
//		ArrayList<TAttentional_Desire> Uninhibited_Desires = new ArrayList<TAttentional_Desire>();
//		ArrayList<TBelief> Uninhibited_Beliefs = new ArrayList<TBelief>();
//		ArrayList<TRegion> Uninhibited_Regions = new ArrayList<TRegion>();
		
//		while (this.Read_Continue_Thread() )
		{
			if ( this.Message_Handler.Read_Value_And_Clear_Updated_Thresholds() )
			{
				if (this.Message_Handler.Read_Value_And_Clear_Updated_Selected_Intentions())
				{
					this.Selected_Intentions.clear();
					this.Selected_Intentions.addAll( this.Global_Workspace.Get_Selected_Intentions() );
				}
				
				/**
				 * Uninhibited Data
				 */
				ArrayList<TBelief> Inhibited_Beliefs = new ArrayList<TBelief>();
				ArrayList<TBelief> Uninhibited_Beliefs = new ArrayList<TBelief>();
				ArrayList<TBelief> Pre_Conditions= new ArrayList<TBelief>();
				
				ArrayList<TAttentional_Desire> Inhibited_Attentional_Desires = new ArrayList<TAttentional_Desire>();
				ArrayList<TAttentional_Desire> Uninhibited_Attentional_Desires = new ArrayList<TAttentional_Desire>();
				
				ArrayList<TRegion> inhibited_Regions = new ArrayList<TRegion>();
				ArrayList<TRegion> uninhibited_Regions = new ArrayList<TRegion>();
				
				
				
				
				/**
				 * If Selected_Intentions.size() > 0, then I divide data ion inhibited and uninhibited
				 * It means that the Agent is focused
				 */
				if (this.Selected_Intentions.size() > 0)
				{
					
					/**
					 * Create Inhibited Beliefs
					 */
					this.Belief_Inhibition.Execute( this.Selected_Intentions );
					Uninhibited_Beliefs.addAll( this.Belief_Inhibition.Get_Uninhibited_Beliefs() );
					Pre_Conditions.addAll(this.Belief_Inhibition.Get_Pre_Conditions() );
					Inhibited_Beliefs.addAll( this.Belief_Inhibition.Get_Inhibited_Beliefs() );
					
					/**
					 * I assume that the desires that should not be inhibited are all the desires connected to selected intentions (the active desire of selected intentions and the desires of options? and plans?)
					 */
					this.Desires_Inhibition.Execute( Selected_Intentions );
					Uninhibited_Attentional_Desires.addAll( this.Desires_Inhibition.Get_Uninhibited_Desires() );
					Inhibited_Attentional_Desires.addAll( this.Desires_Inhibition.Get_Inhibited_Desires() );
				
					/**
					 * I assume that the desires that should not be inhibited are all the desires connected to selected intentions (the active desire of selected intentions and the desires of options? and plans?)
					 */
					this.Regions_Inhibition.Execute( Selected_Intentions );
					uninhibited_Regions.addAll( this.Regions_Inhibition.Get_Uninhibited_Regions() );
					inhibited_Regions.addAll( this.Regions_Inhibition.Get_Inhibited_Regions() );
				}
				
				/**
				 * Otherwise, Selected_Intentions.size() = 0, then I group each data in GW
				 * it means that the agent is Unfocus and I insert each data in Global Workspace
				 */
				else
				{
					/**
					 * Create Inhibited Beliefs
					 */
//					Uninhibited_Beliefs.addAll( this.Global_Workspace.Get_All_Beliefs_from_MM() );
					Uninhibited_Beliefs.clear();
//					Pre_Conditions.addAll( Uninhibited_Beliefs );
					for(TPractical_Desire Practical_Desire : this.Global_Workspace.Get_All_Practical_Desires())
//					for(TPractical_Desire Practical_Desire : this.Global_Workspace.Get_All_Practical_Desires_from_LTM())
					{
						Pre_Conditions.addAll( Practical_Desire.Get_Trigger_Condition().Get_Beliefs() );						
					}
					Inhibited_Beliefs.clear();
					
					/**
					 * I assume that the desires that should not be inhibited are all the desires connected to selected intentions (the active desire of selected intentions and the desires of options? and plans?)
					 */
//					2026.02.13 Uninhibited_Attentional_Desires.addAll( this.Global_Workspace.Get_Attentional_Desires() );
					this.Global_Workspace.Mark_as_Standing_Desires(
							this.Global_Workspace.Get_All_Not_Satisfied_Attentional_Desire_from_MM() );
//							this.Global_Workspace.Get_Unhinibited_Desires());
					
//					this.Global_Workspace.Get_Unhinibited_Active_Desires();
//							this.Global_Workspace.Get_Inhibited_Attentional_Desires());
							
					Uninhibited_Attentional_Desires.clear();
//					Inhibited_Attentional_Desires.clear();
					Inhibited_Attentional_Desires.addAll(
//							this.Global_Workspace.Get_Unhinibited_Desires());
					this.Global_Workspace.Get_All_Not_Satisfied_Attentional_Desire_from_MM() );
				
					/**
					 * I assume that the desires that should not be inhibited are all the desires connected to selected intentions (the active desire of selected intentions and the desires of options? and plans?)
					 */
//					uninhibited_Regions.addAll( this.Global_Workspace.Get_All_Regions_from_MM() );
					uninhibited_Regions.clear();
					inhibited_Regions.clear();
				}
				
				/**
				 * I insert the Inhibited Data into Long Memory
				 */
				this.Global_Workspace.Set_Inhibited_Conscious_Data(
						Inhibited_Beliefs, Inhibited_Attentional_Desires, inhibited_Regions);
				
				/**
				 * I insert the Uninhibited Data and Pre_Conditions into GW
				 */
//				this.Agent
				this.Global_Workspace.Set_Uninhibited_Conscious_Data(
						Pre_Conditions, Uninhibited_Beliefs, Uninhibited_Attentional_Desires, 
						uninhibited_Regions);
			}
//			if(this.Selected_Intentions.size() == 0 && )
//			else
//			{
//			}
		}

		
		
	}
	
	public void Add_Important_Belief_Type(String Belief_Type)
	{
		if ( !this.List_Important_Belief_Type.contains( Belief_Type ))
		{
			this.List_Important_Belief_Type.add(Belief_Type);
		}
	}
	
	public ArrayList<String> Get_List_Important_Belief_Type()
	{
		ArrayList<String> result = new ArrayList<String>(this.List_Important_Belief_Type);
		return result;
	}
	
	public void Clear_List_Important_Belief_Type()
	{
		this.List_Important_Belief_Type.clear();
	}
	
	public void Set_Generate_Unhinibited_Regions_for_Intention(Generate_Unhinibited_Regions Func)
	{
		this.Regions_Inhibition.Set_Generate_Unhinibited_Regions_for_Intention( Func );
	}
	
	protected TGlobal_Workspace Get_Global_Workspace()
	{
		return this.Global_Workspace;
	}
	
	public void Add_Beliefs_to_Always_Consider(String List_Name, ArrayList<String> Beliefs_Names)
	{
		this.Belief_Inhibition.Add_Beliefs_to_Always_Consider( List_Name , Beliefs_Names );
	}
	
	public void Register_Practical_Preconditions_Inhibition_Function(String Name, TBelief_Inhibition_Function func) 
    {
		this.Belief_Inhibition.
					Register_Practical_Preconditions_Inhibition_Function(Name, func);
    }
    
    public boolean Unregister_Practical_Preconditions_Inhibition_Function(String Name) 
    {
        return this.Belief_Inhibition.
        			Unregister_Practical_Preconditions_Inhibition_Function(Name);
    }
	
	public void Register_Practical_Belief_Inhibition_Function(String Name, TBelief_Inhibition_Function func) 
    {
		
		this.Belief_Inhibition.
					Register_Practical_Belief_Inhibition_Function(Name, func);
    }
    
    public boolean Unregister_Practical_Belief_Inhibition_Function(String Name) 
    {
        return this.Belief_Inhibition.
        			Unregister_Practical_Belief_Inhibition_Function(Name);
    }
    
	public void Register_Epistemic_Belief_Inhibition_Function(String Name, TBelief_Inhibition_Function func) 
    {
		
		this.Belief_Inhibition.
					Register_Epistemic_Belief_Inhibition_Function(Name, func);
    }
    
    public boolean Unregister_Epistemic_Belief_Inhibition_Function(String Name) 
    {
        return this.Belief_Inhibition.
        			Unregister_Epistemic_Belief_Inhibition_Function(Name);
    }
    
    public void Register_Practical_Region_Inhibition_Function(String Name, TRegion_Inhibition_Function func) 
    {
    	this.Regions_Inhibition.Register_Practical_Region_Inhibition_Function(Name, func);
    }
    
    public boolean Unregister_Practical_Region_Inhibition_Function(String Name) 
    {
        return this.Regions_Inhibition.Unregister_Practical_Region_Inhibition_Function(Name);
    }
    
    public void Register_Epistemic_Region_Inhibition_Function(String Name, TRegion_Inhibition_Function func) 
    {
        this.Regions_Inhibition.Register_Epistemic_Region_Inhibition_Function(Name, func);
        int ind=0;
    }
    
    public boolean Unregister_Epistemic_Region_Inhibition_Function(String Name) 
    {
        return this.Regions_Inhibition.Unregister_Epistemic_Region_Inhibition_Function(Name);
    }
    
   

}
