package com.Catalina_Model.Catalina_V_0_3;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class TFinally_Operator 
{
	private TExecutive_Reasoner_Function Owner;

	public TFinally_Operator(TExecutive_Reasoner_Function owner)
	{
		this.Owner = owner;
	}
	
	
	public void Execute(ArrayList<TAttentional_Desire> Desires_To_Filter)
	{
		ArrayList<TOption> Options =  new ArrayList<TOption>();
		for(TAttentional_Desire Attentional_Desire: Desires_To_Filter)
		{
			if(Attentional_Desire instanceof TPractical_Desire)
			{ 
				TPractical_Desire Practical_Desire = (TPractical_Desire) Attentional_Desire;
				Options.clear();
				ArrayList<TOption> Survived_Options =  new ArrayList<TOption>();
				Options.addAll( Practical_Desire.Get_List_Options() );
				
				LocalDateTime Finally_Start_Interval = 
						Practical_Desire.Get_Final_State().Get_Temporal_Operator().Get_Start_Time();
				LocalDateTime Finally_End_Interval =
						Practical_Desire.Get_Final_State().Get_Temporal_Operator().Get_End_Time();
				
				for(TOption Option: Options)
				{
					LocalDateTime Temp_Time = Option.Get_Satisfied_Time();
					if( Temp_Time != null)
					{
						if((Temp_Time.compareTo(Finally_Start_Interval)>= 0) && 
								(Temp_Time.compareTo(Finally_End_Interval)<= 0))
						{
							Survived_Options.add(Option);
						}	
					}
				}
				Practical_Desire.Set_List_Options(Survived_Options);
				
				
			}
			
		}
	}
}
