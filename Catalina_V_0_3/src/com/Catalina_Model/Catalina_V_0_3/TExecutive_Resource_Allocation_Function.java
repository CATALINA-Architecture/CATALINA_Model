package com.Catalina_Model.Catalina_V_0_3;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Questo componente è il Resource Allocation Function.
 * Ha un modulo incapsulato Resource_Managment che gestisce tutti gli stimoli importanti da monitorare.
 * Prende in input:
 * 1-Le Important_Beliefs: una lista di beliefs importanti da monitorare continuamente
 * 2-Le Uninhibited_Beliefs: la lista di tutte le beliefs non inibite
 * 3-Le Selected_Intentions: Le selected intentions per ragionare su ciò che deve fare
 */
public class TExecutive_Resource_Allocation_Function extends TAgent_Base_Thread
{

	private TGlobal_Workspace Global_Workspace; 

	private ArrayList<TBelief> Critical_Beliefs;
	private ArrayList<TBelief> Uninhibited_Beliefs;
	private ArrayList<TIntention> Selected_Intentions;

	/**
	 * This list HashSet is useful to discover in O(1) time if an important belief is in a Selected Intention.
	 * It is useful to avoid to create a Practical Desire already created for an important belief.
	 */
	private HashSet<TBelief> Selected_Final_States;
	
	private TResource_Managment Resource_Managment; 
	
	public TExecutive_Resource_Allocation_Function(TAgent agent) 
	{

		super(agent, "Executive Resource Allocation Function");
		
		this.Global_Workspace = agent.Get_Global_WorkSpace();
		this.Resource_Managment = new TResource_Managment( this, Global_Workspace );
		
		this.Critical_Beliefs = new ArrayList<TBelief>();
		this.Uninhibited_Beliefs = new ArrayList<TBelief>();
		this.Selected_Intentions = new ArrayList<TIntention>();

		this.Selected_Final_States = new HashSet<TBelief>();
	}

	@Override
	public void Insert_in_List_Update_Contract()
	{
		this.Agent.Get_Global_WorkSpace().Insert_for_Update_Contract(TType_Update_Contract.Updated_Beliefs, this);
		this.Agent.Get_Global_WorkSpace().Insert_for_Update_Contract(TType_Update_Contract.Updated_Uninhibited_Data, this);
		this.Agent.Get_Global_WorkSpace().Insert_for_Update_Contract(TType_Update_Contract.Updated_Selected_Intentions, this);
		this.Agent.Get_Global_WorkSpace().Insert_for_Update_Contract(TType_Update_Contract.Updated_Critical_Beliefs, this);
		
	}
	
	@Override
	public void Execute()
	{
		TAttentional_Desire Attentional_Desire = null;
		while( this.Read_Continue_Thread() )
		{
			
			Attentional_Desire = null;
			
			this.Critical_Beliefs.clear();
			this.Critical_Beliefs.addAll( this.Global_Workspace.Get_Important_Beliefs() );
			
			// I reload the Uninhibited Beliefs and their content
			if( this.Message_Handler.Read_Value_And_Clear_Updated_Beliefs() || 
					this.Message_Handler.Read_Value_And_Clear_Updated_Unhinibited_Data())
			{
				this.Uninhibited_Beliefs.clear();
				this.Uninhibited_Beliefs.addAll( this.Global_Workspace.Get_UnInhibited_Beliefs() );
			}
			
			// I reload the Selected Intentions
			if( this.Message_Handler.Read_Value_And_Clear_Updated_Selected_Intentions() )
			{
				this.Selected_Intentions.clear();
				this.Selected_Intentions.addAll( this.Global_Workspace.Get_Selected_Intentions() );
				
				this.Selected_Final_States.clear();
				
				/**
				 *  I reload the Selected_Final_States to accelerate the search of important
				 *  beliefs in Selected Intentions to do not recreate another TPractical_Belief
				 *  with same finality
				 */
				for(TIntention Intention: this.Selected_Intentions)
				{
					Attentional_Desire = (TAttentional_Desire) Intention.Get_Active_Desire();
//					Selected_Final_States.addAll( Attentional_Desire.Get_Final_State().Get_Beliefs() );

					switch( Attentional_Desire )
					{
						case TPractical_Desire Practical_Desire ->
						{
							Selected_Final_States.addAll(
									Practical_Desire.Get_Final_State().Get_Beliefs() );
							
						}
						case TEpistemic_Desire Epistemic_Desire ->
						{
							Selected_Final_States.add( 
									Epistemic_Desire.Get_Belief() );
						}
					default -> throw new IllegalArgumentException("Unexpected value: " + Attentional_Desire);
					}
				
				}
			}
			
			this.Resource_Managment.Execute(Critical_Beliefs, Uninhibited_Beliefs, 
											Selected_Intentions, Selected_Final_States);
		}
	}
	
	public void Add_Funtion_to_Belief(String Type_Stimulus, TResource_Managment_Practical_Functions_Handler.Resource_Managment_Practical_Function Func )
	{
		this.Resource_Managment.Add_Funtion_to_Belief(Type_Stimulus, Func);
	}
	
	public void Add_Funtion_to_Belief(TBelief Belief, TResource_Managment_Practical_Functions_Handler.Resource_Managment_Practical_Function Func )
	{
		this.Add_Funtion_to_Belief( Belief.Get_Type_Belief(), Func);
	}
	
	public void Remove_Funtion_to_Belief(String Type_Stimulus)
	{
		this.Resource_Managment.Remove_Funtion_to_Belief(Type_Stimulus);
	}
	
	public void Remove_Funtion_to_Belief(TBelief Belief)
	{
		this.Remove_Funtion_to_Belief( Belief.Get_Type_Belief() );
	}
	
	protected TGlobal_Workspace Get_Global_Workspace()
	{
		return this.Global_Workspace;
	}
	
	public void Load_Importan_Beliefs()
	{
		
	}
	
	
}
