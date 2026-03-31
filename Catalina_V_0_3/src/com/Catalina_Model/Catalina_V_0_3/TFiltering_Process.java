package com.Catalina_Model.Catalina_V_0_3;

import java.util.ArrayList;

import com.Catalina_Model.Catalina_V_0_3.TExecutive_Reasoner_Function.TQuality_Desires_Filter_Function;
import com.Catalina_Model.Catalina_V_0_3.TGreen_Desires_Filter_Function_Handler.Green_Desires_Filter_Function;

public class TFiltering_Process 
{
	private TExecutive_Reasoner_Function Parent;
	private TGlobal_Workspace Global_Workspace;
	public TGreen_Desires_Filter_Function_Handler Green_Desires_Filter_Function_Handler;
	private TQuality_Desires_Filter_Function Quality_Desires_Filter_Function;
	
	public  TFiltering_Process(TExecutive_Reasoner_Function Owner)
	{
		this.Parent = Owner;
		this.Global_Workspace = this.Parent.Get_Global_Workspace(); 
				//Global_Workspace;
		this.Green_Desires_Filter_Function_Handler = new TGreen_Desires_Filter_Function_Handler();
	}
	
	public void Execute(ArrayList<TAttentional_Desire> Desires_To_Filter)
	{
		ArrayList<TGreen_Desire> Green_Desires = new ArrayList<TGreen_Desire>();
		ArrayList<TQuality_Desire> Quality_Desires = new ArrayList<TQuality_Desire>();
		ArrayList<TOption> Filtered_Options = new ArrayList<TOption>();
		
		for(TAttentional_Desire Desire: Desires_To_Filter)
		{
			Green_Desires.clear();
			Quality_Desires.clear();
			Filtered_Options.clear();
			
			Green_Desires.addAll(Desire.Get_List_Green_Standing_Desire() );
			Quality_Desires.addAll(Desire.Get_List_Quality_Standing_Desire() );
			
			/**
			 * For each Green Desires associated I execute a filtering.
			 * This can reduce the options.
			 */
			for(TGreen_Desire Green_Desire: Green_Desires)
			{
				Filtered_Options = this.Green_Desires_Filter_Function_Handler
						.Execute_Function_For_Desire
						( Green_Desire , Desire, 
								this.Parent.Get_Beliefs(), this.Parent.Get_Regions(),
								this.Parent.Get_Intentions()
						);
				

				Desire.Set_List_Options( Filtered_Options );
			}
			
			/**
			 * I send the Desire to a specific custom Method of the developer
			 * because the quality desires can be over one, and a 
			 * multiple choice can be decided
			 */
			if ( this.Quality_Desires_Filter_Function != null)
			{
//				Filtered_Options.addAll( 
//						this.Quality_Desires_Filter_Function.Execute
//									(	Desire, this.Parent.Get_Beliefs(), 
//											this.Parent.Get_Regions(), this.Parent.Get_Intentions()
//									)	
//						);
				Filtered_Options = this.Quality_Desires_Filter_Function.Execute
						(	Desire, this.Parent.Get_Beliefs(), 
						this.Parent.Get_Regions(), this.Parent.Get_Intentions());
				if ( Filtered_Options != null)
				{
					Desire.Set_List_Options( Filtered_Options );
				}
//				)
			}
			else
			{
				System.out.println("A Quality Desires Filter Function must be associated"
						+ " to "+Desire.Get_Name());
			}
			
//			Desire.Set_List_Options( Filtered_Options );
			
		}
		this.Global_Workspace.Broadcast_Signal(TType_Update_Contract.Updated_Active_Desires_with_Options);
	}
	
	public void Set_Quality_Desire_Filtering_Process(TQuality_Desires_Filter_Function Func)
	{
		this.Quality_Desires_Filter_Function = Func;
	}
	
	public void Register_Green_Filtering_Function(String Green_Type, Green_Desires_Filter_Function func) 
    {
		this.Green_Desires_Filter_Function_Handler.Register_Function(Green_Type, func);
    }
	
	public boolean Unregister_Green_Filtering_Function(String Green_Type) 
    {
        
        return this.Green_Desires_Filter_Function_Handler.Unregister_Function(Green_Type);
    }
}
