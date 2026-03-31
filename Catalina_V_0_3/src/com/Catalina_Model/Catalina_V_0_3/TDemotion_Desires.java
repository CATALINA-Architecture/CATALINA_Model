package com.Catalina_Model.Catalina_V_0_3;

import java.util.ArrayList;

public class TDemotion_Desires 
{
	private TDesire_Handler Parent;
	private TGlobal_Workspace Global_Workspace;
//	private Double Saliency_Threshold;
//	private Double Attention_Threshold;
	
	public TDemotion_Desires(TDesire_Handler Owner)
	{
		this.Parent = Owner;
		this.Global_Workspace = this.Parent.Get_Global_Workspace();
//		this.Saliency_Threshold = 0.0;
//		this.Attention_Threshold = 0.0;
	}

	//TODO ripensare bene perchè non mi convince tutto il ragionamento
	public void Execute_old()
	{
//		Boolean Updated_Active_Desires = this.Parent.Message_Handler.Read_Value_And_Clear_Updated_Active_Desires();
//		Boolean Updated_Selected_Intentions= this.Parent.Message_Handler.Read_Value_And_Clear_Updated_Selected_Intentions();
//		Boolean Updated_Uninhibited_Data= this.Parent.Message_Handler.Read_Value_And_Clear_Updated_Unhinibited_Data();
		Boolean Updated_Uninhibited_Data = this.Parent.Get_Updated_Uninhibited_Data();
		
		if( Updated_Uninhibited_Data )
		{
			ArrayList<TPractical_Desire> Practical_Desires = this.Global_Workspace.Get_All_Practical_Desires();
			ArrayList<TPractical_Desire> Inhibited_Practical_Desires = this.Global_Workspace.Get_Inhibited_Practical_Desires_from_LT();
			
			ArrayList<TPractical_Desire> Uninhibited_Practical_Desires = new ArrayList<TPractical_Desire>(Practical_Desires);
//			Uninhibited_Practical_Desires.addAll( this.Global_Workspace.Get_All_Practical_Desires() );
			Uninhibited_Practical_Desires.removeAll(Inhibited_Practical_Desires);
			
			ArrayList<TAttentional_Desire> Active_Desires = this.Global_Workspace.Get_Unhinibited_Active_Desires();
			
			Active_Desires.removeIf(desire -> desire instanceof TEpistemic_Desire);
			
			ArrayList<TAttentional_Desire> Active_Desire_To_Mark_as_Standing = new ArrayList<TAttentional_Desire>();
			
			Double Saliency_Threshold = this.Parent.Get_Saliency_Threshold();
			Double Attention_Threshold = this.Parent.Get_Attention_Threshold();
			for (TAttentional_Desire Active_Desire: Active_Desires)
			{
				if( Uninhibited_Practical_Desires.contains(Active_Desire) )
				{
					if( Active_Desire.Get_Saliency() < Saliency_Threshold)
					{
						Active_Desire_To_Mark_as_Standing.add(Active_Desire);
					}
				}
				/**
				 * questo "else" non dovrebbe avvenire mai!! Perchè non sto considerando anche gli inibiti
				 * ma soltanto quelli che sono NON inibiti e che NON passano la Saliency.
				 * In questa funzione devo passare gli active desires in standing desires
				 * ...or not...because the following "else" executes, in practice, the next commented
				 * code lines...
				 * 
				 */
				else
				{
					if( Active_Desire.Get_Saliency() < Attention_Threshold)
					{
						Active_Desire_To_Mark_as_Standing.add(Active_Desire);
					}
				}
			}
			
//			// Now, I must to remove all intentions related to Desires in Inhibited_Practical_Desires
//			if (Inhibited_Practical_Desires.size() > 0)
//			{
//				ArrayList<TIntention> Intentions_to_Remove = new ArrayList<Intention>();
//				for(TAttentional_Desire Desire:  Inhibited_Practical_Desires)
//				{
//					Desire.Set_Status_Desire(TType_Status_Desire.Standing);
//					Desire.Clear_Options();
//
//					TIntention Intention = Desire.Get_Related_Intention();
//					if (Intention != null) 
//					{
//						Desire.Set_Related_Intention(null);
//						Intentions_to_Remove.add( Intention );
//						Intention.Set_Desire( null );
//					}
//				}
//				this.Global_Workspace.Remove_Intentions( Intentions_to_Remove );
//			}
			
			
			if(Active_Desire_To_Mark_as_Standing.size()>0)
			{
//				this.Global_Workspace.Delite_Active_Desires(Active_Desire_To_Remove);
				this.Global_Workspace.Mark_as_Standing_Desires(Active_Desire_To_Mark_as_Standing);
			}
		}
	}
	
	public void Execute()
	{
//		Boolean Updated_Active_Desires = this.Parent.Message_Handler.Read_Value_And_Clear_Updated_Active_Desires();
//		Boolean Updated_Selected_Intentions= this.Parent.Message_Handler.Read_Value_And_Clear_Updated_Selected_Intentions();
//		Boolean Updated_Uninhibited_Data= this.Parent.Message_Handler.Read_Value_And_Clear_Updated_Unhinibited_Data();
		Boolean Updated_Uninhibited_Data = this.Parent.Get_Updated_Uninhibited_Data();
		
		if( Updated_Uninhibited_Data )
		{
			ArrayList<TPractical_Desire> Uninhibited_Practical_Desires = new ArrayList<TPractical_Desire>();
			for(TAttentional_Desire Desire:Global_Workspace.Get_Unhinibited_Active_Desires())
			{
				if(Desire instanceof TPractical_Desire)
				{
					Uninhibited_Practical_Desires.add( (TPractical_Desire) Desire);
				}
			}
			
			ArrayList<TAttentional_Desire> Active_Desires = this.Global_Workspace.Get_Unhinibited_Desires();
			
//			ArrayList<TPractical_Desire> Practical_Desires = this.Global_Workspace.Get_All_Practical_Desires();
			ArrayList<TPractical_Desire> Inhibited_Practical_Desires = this.Global_Workspace.Get_Inhibited_Practical_Desires_from_LT();
			
//			ArrayList<TPractical_Desire> Uninhibited_Practical_Desires = new ArrayList<TPractical_Desire>(Practical_Desires);
//			Uninhibited_Practical_Desires.addAll( this.Global_Workspace.Get_All_Practical_Desires() );
			Uninhibited_Practical_Desires.removeAll(Inhibited_Practical_Desires);
			
			Active_Desires.removeIf(desire -> desire instanceof TEpistemic_Desire);
			
			ArrayList<TAttentional_Desire> Active_Desire_To_Mark_as_Standing = new ArrayList<TAttentional_Desire>();
			
			Double Saliency_Threshold = this.Parent.Get_Saliency_Threshold();
			Double Attention_Threshold = this.Parent.Get_Attention_Threshold();
			//questo codice commentato sotto è corretto
			/**
			 * FUNZIONA CON (ora pare di no)
			 * Uninhibited_Practical_Desires = Get_Unhinibited_Desires 
			 * Active_Desires = Get_Unhinibited_Desires
			 */
			/**
			 * NON FUNZIONA SE (ora pare di no, però torna indietro)
			 * Uninhibited_Practical_Desires = Get_Unhinibited_Desires E 
			 * Active_Desires = Get_Unhinibited_Active_Desires
			 * 
			 * OPPURE SE
			 * Uninhibited_Practical_Desires = Get_Unhinibited_Active_Desires E 
			 * Active_Desires = Get_Unhinibited_Active_Desires
			 * 
			 OPPURE SE
			 * Uninhibited_Practical_Desires = Get_Unhinibited_Active_Desires E 
			 * Active_Desires = Get_Unhinibited_Desires
			 * 
			 */
//			for (TAttentional_Desire Active_Desire: Active_Desires)
//			{
//				if( Uninhibited_Practical_Desires.contains(Active_Desire) )
//				{
//					if( Active_Desire.Get_Saliency() < Saliency_Threshold)
//					{
//						Active_Desire_To_Mark_as_Standing.add(Active_Desire);
//					}
//				}
//				else
//				{
//					if( Active_Desire.Get_Saliency() < Attention_Threshold)
//					{
//						Active_Desire_To_Mark_as_Standing.add(Active_Desire);
//					}
//				}
//			};
			//questo codice commentato sotto è corretto

			for (TAttentional_Desire Active_Desire: Active_Desires)
			{
				if( Active_Desire.Get_Saliency() < Saliency_Threshold)
				{
					Active_Desire_To_Mark_as_Standing.add(Active_Desire);
				}
			}
			/**
			 * 1 no FUNZIONA  
			 * Uninhibited_Practical_Desires = Get_Unhinibited_Desires 
			 * Active_Desires = Get_Unhinibited_Desires
			 *
			 * 2 no FUNZIONA SE (PERò TORNA INDIETRO)
			 * Uninhibited_Practical_Desires = Get_Unhinibited_Desires E 
			 * Active_Desires = Get_Unhinibited_Active_Desires
			 * 
			 * 3 no OPPURE SE (PERò TORNA INDIETRO)
			 * Uninhibited_Practical_Desires = Get_Unhinibited_Active_Desires E 
			 * Active_Desires = Get_Unhinibited_Active_Desires
			 * 
			 * 4 no OPPURE SE
			 * Uninhibited_Practical_Desires = Get_Unhinibited_Active_Desires E 
			 * Active_Desires = Get_Unhinibited_Desires
			 * 
			 */
//			for (TAttentional_Desire Desire: Uninhibited_Practical_Desires)
//			{
//				if(Active_Desires.contains( Desire))
//				{
//					if( Desire.Get_Saliency() < Saliency_Threshold)
//					{
//						Active_Desire_To_Mark_as_Standing.add(Desire);
//					}
//				}
//				else
//				{
//					if( Desire.Get_Saliency() < Attention_Threshold)
//					{
//						Active_Desire_To_Mark_as_Standing.add(Desire);
//					}
//				}
//				
//			}
			
//			// Now, I must to remove all intentions related to Desires in Inhibited_Practical_Desires
//			if (Inhibited_Practical_Desires.size() > 0)
//			{
//				ArrayList<TIntention> Intentions_to_Remove = new ArrayList<Intention>();
//				for(TAttentional_Desire Desire:  Inhibited_Practical_Desires)
//				{
//					Desire.Set_Status_Desire(TType_Status_Desire.Standing);
//					Desire.Clear_Options();
//
//					TIntention Intention = Desire.Get_Related_Intention();
//					if (Intention != null) 
//					{
//						Desire.Set_Related_Intention(null);
//						Intentions_to_Remove.add( Intention );
//						Intention.Set_Desire( null );
//					}
//				}
//				this.Global_Workspace.Remove_Intentions( Intentions_to_Remove );
//			}
			
			
			if(Active_Desire_To_Mark_as_Standing.size()>0)
			{
//				this.Global_Workspace.Delite_Active_Desires(Active_Desire_To_Remove);
				this.Global_Workspace.Mark_as_Standing_Desires(Active_Desire_To_Mark_as_Standing);
//				this.Global_Workspace.Broadcast_Message(TType_Update_Contract.Standing_Desires);
			}
		}
	}
	
}
