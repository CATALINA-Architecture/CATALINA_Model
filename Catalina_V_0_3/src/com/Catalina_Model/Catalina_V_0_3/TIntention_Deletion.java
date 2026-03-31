package com.Catalina_Model.Catalina_V_0_3;

import java.util.ArrayList;
import java.util.HashSet;

public class TIntention_Deletion 
{
	private TDesire_Handler Parent;
	private TGlobal_Workspace Global_Workspace;
	
	public TIntention_Deletion(TDesire_Handler Owner)
	{
		this.Parent = Owner;
		this.Global_Workspace = this.Parent.Get_Global_Workspace();
	}
	
	public void Execute()
	{
		/**
		 * First I remove the wrong intentions.
		 * These are Intentions with errata preconditions or postconditions
		 */
		this.Remove_Errata_Intentions();
		
		/**
		 * I remove the fulfilled intentions.
		 * These are satisfied Intentions: the related desires are satisfied
		 */
		this.Remove_Satisfied_Intentions();
	}
	
	/**
	 * First I remove the wrong intentions
	 */
	public void Remove_Errata_Intentions()
	{
		///////////////////
		////////// First I remove the wrong intentions
		///////////////////

		/**
		 *  By now, the intentions to delete are stored in data property of the Base_Message, so
		 *  we have one only Base_Message with 1 or more Intentions to delete stored in data property
		 *  (a list of object
		 */
		ArrayList<TBase_Message> List_Delete_Intention_Signals = new ArrayList<TBase_Message>();
		List_Delete_Intention_Signals.addAll( 
				this.Parent.Message_Handler.Read_And_Clear_Updated_Internal_Signals_Delete_Intention_Messages() );
		
		if ( List_Delete_Intention_Signals.size() > 0 )
		{
			HashSet<TIntention> Set_Intentions_To_Handle = new HashSet<TIntention>();
			TAttentional_Desire Desire = null;
			ArrayList<TIntention> Intentions_To_Handle = new ArrayList<TIntention>();
			
			for(TBase_Message Message: List_Delete_Intention_Signals)
			{
				for(Object Generic_Intention: Message.Read_Data())
				{
					Set_Intentions_To_Handle.add( (TIntention) Generic_Intention);
				}
			}
			
			Desire = null;
			ArrayList<TAttentional_Desire> Attentional_Desires = 
					new ArrayList<TAttentional_Desire>();
			for(TIntention intention: Set_Intentions_To_Handle )
			{
				Desire = (TAttentional_Desire) intention.Get_Active_Desire();
				if( Desire != null)
				{
					Desire.Set_Related_Intention( null );
					Desire.Clear_Options();
					Attentional_Desires.add( Desire );
				}
				
				
				intention.Set_Desire( null );
			}
			
			Intentions_To_Handle.addAll( Set_Intentions_To_Handle );
			
			this.Global_Workspace.Remove_Intentions( Intentions_To_Handle );
			this.Global_Workspace.Mark_as_Standing_Desires(Attentional_Desires);
			this.Global_Workspace.Broadcast_Signal(TType_Update_Contract.Updated_Selected_Intentions);
//			this.Global_Workspace.Broadcast_Message(TType_Update_Contract.Standing_Desires);
		}
	}
	
	/**
	 * I remove the fulfilled intentions
	 */
	public void Remove_Satisfied_Intentions()
	{
		///////////////////
		////////// Second I remove the fulfilled intentions
		///////////////////
		
		ArrayList<TAttentional_Desire> Satisfied_Desires = new ArrayList<TAttentional_Desire>();
		
		ArrayList<TBase_Message> List_Satisfied_Intention_Signals = new ArrayList<TBase_Message>();
		List_Satisfied_Intention_Signals.addAll( 
				this.Parent.Message_Handler.Read_And_Clear_Updated_Internal_Signals_Satisfied_Intention_Messages() );
//		this.Parent.Message_Handler.
		List_Satisfied_Intention_Signals.size();
		if ( List_Satisfied_Intention_Signals.size() > 0 )
		{
			HashSet<TIntention> Set_Intentions_To_Handle = new HashSet<TIntention>();
			TAttentional_Desire Desire = null;
			ArrayList<TIntention> Intentions_To_Handle = new ArrayList<TIntention>();
			
			for(TBase_Message Message: List_Satisfied_Intention_Signals)
			{
				for(Object Generic_Intention: Message.Read_Data())
				{
					Set_Intentions_To_Handle.add( (TIntention) Generic_Intention);
				}
			}
			
			Desire = null;
			for(TIntention intention: Set_Intentions_To_Handle )
			{
				this.Global_Workspace.Insert_Plan_in_Plan_Library(intention);
				Desire = (TAttentional_Desire) intention.Get_Active_Desire();
				if (Desire != null)
				{
					Satisfied_Desires.add( Desire );
//					Desire.Set_Related_Intention( null );
//					Desire.Clear_Options();
				}
				
				
//				intention.Set_Desire( null );
			}
			
			Intentions_To_Handle.addAll( Set_Intentions_To_Handle );
		//	this.Global_Workspace.Remove_Intentions( Intentions_To_Handle );
			this.Global_Workspace.Mark_as_Satisfied_Desires( Satisfied_Desires );
		}
	}
	

	

}
