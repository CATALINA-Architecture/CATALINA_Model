package com.Catalina_Model.Catalina_V_0_3;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * This module handle the generation of the internal signals abort option to send to TExecutive_Switching_Function.
 * For each belief, if it is true it send an internal signal abort option to stop the option of a 
 * Selected Intention.
 * In future, it could send other internal signals.
 * It has a Resource_Managment_Practical_Functions_Handler:
 * 
 * (Important Belief --> Function)
 * 
 * it is useful to associate each type important belief to a function to create a Triple Object:
 * 1-a Practical Desire, to satisfy
 * 2-an internal signal (by now only abort option)
 * 3-An ArrayList of the data: (0) the selected intention and (1) its option to abort
 */

public class TResource_Managment 
{
	private TExecutive_Resource_Allocation_Function Parent;
	private TResource_Managment_Practical_Functions_Handler Practical_Functions_Handler;
	private TGlobal_Workspace Global_Workspace;

	private ArrayList<TAttentional_Desire> New_Practical_Desires;
	private ArrayList<TType_Update_Contract> New_Internal_Signals;
	private ArrayList<Object> New_Data;

	
	
	public TResource_Managment(TExecutive_Resource_Allocation_Function  owner,
			TGlobal_Workspace global_workspace) 
	{
		this.Parent = owner;
		this.Global_Workspace = global_workspace;
		
		this.Practical_Functions_Handler = new TResource_Managment_Practical_Functions_Handler();

		this.New_Practical_Desires = new ArrayList<TAttentional_Desire>();
		this.New_Internal_Signals = new ArrayList<TType_Update_Contract>();
		this.New_Data = new ArrayList<Object>();
	}

	public void Execute(ArrayList<TBelief> Critical_Beliefs, ArrayList<TBelief> Uninhibited_Beliefs,
			ArrayList<TIntention> Selected_Intentions, HashSet<TBelief> Selected_Final_States)
	{
		{
			
			/**
			 * These 3 rows are for sending a group of internal signals
			 */
//			this.New_Practical_Desires.clear();
//			this.New_Internal_Signals.clear();
//			this.New_Data.clear();
			
			//Now, for each Important Belief
			for(TBelief Belief: Critical_Beliefs)
			{
				/**
				 * These 3 rows are for sending one internal signal at a time
				 */
				this.New_Practical_Desires.clear();
				this.New_Internal_Signals.clear();
				this.New_Data.clear();
				
				// If the Belief is true, I have to generate a Practical Desire and an Internal Signal Abort Option
//				if( Belief.Is_Truth() )
				{
					if( !Selected_Final_States.contains( Belief ))
					{
						/**
						 * TTriple_Object contains two objects:
						 * 	1 - a Practical Desire (TPractical_desire)
						 *  2 - an Internal Signal to send (TType_Update_Contract)
						 *  3 - ArrayList of Object: Data of Selected Intention to Abort 
						 *  							[0- Selected Intention; 1- Option_Id] 
						 */
						TTriple_Object Results = this.Practical_Functions_Handler.Execute_Function_For_Desire(Belief, Uninhibited_Beliefs, Selected_Intentions );
						
						this.New_Practical_Desires.add( (TPractical_Desire) Results.Get_Object_First() );
						this.New_Internal_Signals.add( (TType_Update_Contract) Results.Get_Object_Second() );
						this.New_Data.addAll( (ArrayList<Object>) Results.Get_Object_Third());
						
						/**
						 * I don't compute all uninhibited beliefs and regions for the new Practical Desire
						 * because, it will be 
						 */
//						this.Global_Workspace.Add_Attentional_Desires( this.New_Practical_Desires );
//						this.Global_Workspace.Add_Inhibited_Practical_Attentional_Desires( this.New_Practical_Desires, null );
						
						
						
						this.Global_Workspace.Broadcast_Internal_Signals_Messages
						(
								//TType_Update_Contract.Internal_Signals_Abort_Option,
								/**
								 *  By now, it is Internal_Signals_Abort_Option.
								 *  In Future it can be any type of internal signal
								 */
//								this.New_Internal_Signals.getFirst(),
								TType_Update_Contract.Internal_Signals_Abort_Option,
								this.Parent.getName(), this.New_Data
						);
					}
				}
			}
		}
	}
	
	public void Add_Funtion_to_Belief(String Type_Stimulus, TResource_Managment_Practical_Functions_Handler.Resource_Managment_Practical_Function Func )
	{
		this.Practical_Functions_Handler.Register_Function( Type_Stimulus , Func );
	}
	
	public void Add_Funtion_to_Belief(TBelief Belief, TResource_Managment_Practical_Functions_Handler.Resource_Managment_Practical_Function Func )
	{
		this.Add_Funtion_to_Belief( Belief.Get_Type_Belief(), Func);
	}
	
	public void Remove_Funtion_to_Belief(String Type_Stimulus)
	{
		this.Practical_Functions_Handler.Unregister_Function( Type_Stimulus );
	}
	
	public void Remove_Funtion_to_Belief(TBelief Belief)
	{
		this.Practical_Functions_Handler.Unregister_Function( Belief.Get_Type_Belief() );
	}

}
