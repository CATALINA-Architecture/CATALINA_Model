package com.Catalina_Model.Catalina_V_0_3;

import java.util.ArrayList;
import java.util.Collections;

public class TAttention_Selection extends TAgent_Base_Thread
{
	private TGlobal_Workspace Global_Workspace;
	
	private TAgent_Focus Agent_Focus;
	private TAgent_Unfocus Agent_Unfocus;
	
	private final Double Default_Saliency_Threshold = 0.3;
	private final Double Default_Attention_Threshold = this.Default_Saliency_Threshold;
	
	private TDouble_Protected_Object Saliency_Threshold = new TDouble_Protected_Object(Default_Saliency_Threshold);
	private TDouble_Protected_Object Attention_Threshold = new TDouble_Protected_Object(Default_Attention_Threshold);

	public TBoolean_Protected_Object Is_Focused = new TBoolean_Protected_Object(false);
	private ArrayList<TIntention> Selected_Intentions;
	
	public TAttention_Selection(TAgent agent) 
	{
		
		super(agent, "Executive Attention Modulation Function");
		//Qui
		
		this.Global_Workspace = this.Agent.Get_Global_WorkSpace();
		this.Agent_Focus = new TAgent_Focus( this );
		this.Agent_Unfocus = new TAgent_Unfocus( this );
		this.Selected_Intentions = new ArrayList<TIntention>();
	}

	@Override
	public void Insert_in_List_Update_Contract()
	{
		this.Agent.Get_Global_WorkSpace().Insert_for_Update_Contract(TType_Update_Contract.Updated_Selected_Intentions, this);
	}
	
//	@Override
//	public void run()
//	{
//		try 
//		{
//			while(this.Read_Continue_Thread())
//			{
//				this.Is_In_Pause();
//				
////				if (this.Read_Updated_Selected_Intentions())
//				if (this.Message_Handler.Read_Value_And_Clear_Updated_Selected_Intentions())
//				{
//					this.Execute();
//				}
//			}
//			
//		} 
//		catch (Exception e) 
//		{
//			System.out.println("Something went wrongin method: in Focusing Attention.");
////			System.out.println("Captured Message Error: "+e.getMessage());
////			System.out.println("Localized Message Error: "+e.getLocalizedMessage());
//		}
//		finally 
//		{
//			
//		}
//	}
	
	@Override
	public void Execute()
	{
//		while (this.Read_Continue_Thread() )
		{
//			//Qui
//			System.out.println("Messaggio Selected Intentions cambiate");
			if (this.Message_Handler.Read_Value_And_Clear_Updated_Selected_Intentions())
			{
				this.Selected_Intentions = this.Global_Workspace.Get_Selected_Intentions();
				if ( this.Selected_Intentions.size() > 0 &&
						((TAttentional_Desire) this.Selected_Intentions.getFirst().
								Get_Active_Desire()).Get_List_Options().size() >0 )
				{
					this.Agent_Focus.Execute( Selected_Intentions );
					this.Saliency_Threshold.Write( this.Agent_Focus.Get_Saliency_Threshold());
					this.Attention_Threshold.Write( this.Agent_Focus.Get_Attention_Threshold());
				}
				else
				{
					this.Agent_Unfocus.Execute();
					this.Saliency_Threshold.Write( this.Agent_Unfocus.Get_Un_Focused_Saliency_Threshold());
					this.Attention_Threshold.Write( this.Agent_Unfocus.Get_Un_Focused_Attention_Threshold());
					
				}
				
				this.Is_Focused.Write( (this.Saliency_Threshold.Read() == this.Attention_Threshold.Read()));
				this.Global_Workspace.Is_Focused.Write( this.Saliency_Threshold.Read() == this.Attention_Threshold.Read() );
				this.Global_Workspace.Update_Saliency_and_Attention_Thresholds(
						this.Saliency_Threshold.Read(), this.Attention_Threshold.Read());
				
			}
//			else
//			{
//			}
		}
	}
	
	protected TGlobal_Workspace Get_Global_Global_Workspace()
	{
		return this.Global_Workspace;
	}
}
