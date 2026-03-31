package com.Catalina_Model.Catalina_V_0_3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import com.Catalina_Model.Catalina_V_0_3.TExecutive_Reasoner_Function.TMeans_End_Reasoner_Generate_Options_for_Practical_Desire;
import com.Catalina_Model.Catalina_V_0_3.TMeans_End_Epistemic_Functions_Handler.Means_End_Epistemic_Function;
import com.Catalina_Model.Catalina_V_0_3.TMeans_End_Practical_Functions_Handler.Means_End_Practical_Function;

public class TMeans_End_Reasoner 
{
	
	private TExecutive_Reasoner_Function Owner;
	private TGlobal_Workspace Global_Workspace;
	
	private TMeans_End_Epistemic_Functions_Handler Means_End_Epistemic_Functions_Handler;
	private TMeans_End_Practical_Functions_Handler Means_End_Practical_Functions_Handler;
	
	private ArrayList<TAttentional_Desire> Desires_To_Filter;
	private HashMap<String, Set<String>> List_Beliefs_Names_for_Desires;
	
	private TMeans_End_Reasoner_Generate_Options_for_Practical_Desire Generate_Options_for_Practical_Desire;

	public TMeans_End_Reasoner(TExecutive_Reasoner_Function Owner)
	{
		this.Owner = Owner;
		this.Global_Workspace = Global_Workspace;
		this.Means_End_Epistemic_Functions_Handler = new TMeans_End_Epistemic_Functions_Handler();
		this.Means_End_Practical_Functions_Handler = new TMeans_End_Practical_Functions_Handler();
		
		this.Desires_To_Filter = new ArrayList<TAttentional_Desire>();
//		this.List_Beliefs_Names_for_Desires = new HashMap<String, Set<String>>();
	}
	
	public void Execute( ArrayList<TAttentional_Desire> Active_Desires )
	{
		
		//Collections.sort(this.Active_Desires, new TDesire_Compare());
		this.Desires_To_Filter.clear();
		TMeans_End_Reasoner_Data_Getter Means_End_Reasoner_Data_Getter = 
				new TMeans_End_Reasoner_Data_Getter( this.Owner );
				
		
		boolean Can_Analyze_Desire = true;
		ArrayList<TOption> Options = new ArrayList<TOption>();
		
		for( TAttentional_Desire Desire: Active_Desires )
		{
			Can_Analyze_Desire = true;
			if (Desire.Get_Related_Intention() != null)
			{
				if (Desire.Get_Related_Intention().Get_Active_Desire() == Desire )
				{
					/** 
					 * It means that the Desire is in a Selected Intention and
					 * I don't analyze this desire
					 * 
					 */
					Can_Analyze_Desire = false;		
				}
			}
			if ( Can_Analyze_Desire )
			{
				Options.clear();
				switch( Desire )
				{
					case TPractical_Desire Practical_Desire ->
					{
//						if (Generate_Options_for_Practical_Desire != null)
//						{
//							Options.addAll(
//									this.Generate_Options_for_Practical_Desire.Execute(
//											Practical_Desire, 
////											this.Parent.Get_Beliefs(), 
//											this.Owner.Get_Map_Beliefs(),
//											this.Owner.Get_Map_Regions(), 
//											this.Owner.Get_Intentions(),
//											Means_End_Reasoner_Data_Getter
//											) );							
//						}
						Options.addAll(
								this.Means_End_Practical_Functions_Handler
								.Execute_Function_For_Desire(
										Practical_Desire, 
										this.Owner.Get_Map_Beliefs(), 
										this.Owner.Get_Map_Regions(), 
										this.Owner.Get_Intentions() ));
//										Means_End_Reasoner_Data_Getter) );
						break;
						
					}
					case TEpistemic_Desire Epistemic_Desire ->
					{
						Options.addAll(
								this.Means_End_Epistemic_Functions_Handler
								.Execute_Function_For_Desire(
										Epistemic_Desire, 
										this.Owner.Get_Map_Beliefs(), 
										this.Owner.Get_Map_Regions(), 
										this.Owner.Get_Intentions(),
										Means_End_Reasoner_Data_Getter
										) );
						break;
					}
					default -> throw new IllegalArgumentException("Unexpected value: " + Desire);
				}
				
				Desire.Set_List_Options( Options );
				this.Desires_To_Filter.add(Desire);
			}
			
		}
	}
	
	public ArrayList<TAttentional_Desire> Get_Desires_To_Filter()
	{
		ArrayList<TAttentional_Desire> result = new ArrayList<TAttentional_Desire>();
		result.addAll( this.Desires_To_Filter );
		this.Desires_To_Filter.clear();
		
		return result;
	}
	
	public void Set_Generate_Options_for_Practical_Desire( 
			TMeans_End_Reasoner_Generate_Options_for_Practical_Desire Func )
	{
		this.Generate_Options_for_Practical_Desire = Func;
	}
	
	public void Register_Epistemic_Function(String belief_Type, Means_End_Epistemic_Function func) 
    {
		this.Means_End_Epistemic_Functions_Handler.Register_Epistemic_Function( belief_Type, func );
    }
    
    public boolean Unregister_Epistemic_Function(String belief_Type) 
    {
    	return this.Means_End_Epistemic_Functions_Handler.Unregister_Epistemic_Function( belief_Type );
    }
    
    public void Register_Practical_Function(String Desire_Name, Means_End_Practical_Function func) 
    {
		this.Means_End_Practical_Functions_Handler.Register_Practical_Function( Desire_Name, func );
    }
    
    public boolean Unregister_Practical_Function(String Desire_Name) 
    {
    	return this.Means_End_Practical_Functions_Handler.Unregister_Practical_Function( Desire_Name );
    }
    
//    private HashMap<String, Set<String>> List_Beliefs_Names_for_Desires
//    public void Register_List_Beliefs_Names_for_Desires(String Desire_Name, Set<String> Beliefs_Names)
//    {
//    	if(Desire_Name != null && !Desire_Name.isEmpty() && Beliefs_Names != null)
//    	{
//    		this.List_Beliefs_Names_for_Desires.put(Desire_Name, Beliefs_Names);
//    	}
//    }
}
