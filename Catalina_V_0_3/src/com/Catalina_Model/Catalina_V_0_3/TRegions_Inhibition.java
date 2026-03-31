package com.Catalina_Model.Catalina_V_0_3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import com.Catalina_Model.Catalina_V_0_3.TRegion_Inhibition_Function_Handler.TRegion_Inhibition_Function;

public class TRegions_Inhibition
{
	
	@FunctionalInterface
    public interface Generate_Unhinibited_Regions
    {
        ArrayList<TRegion> Execute(TIntention Intention, 
        		HashMap<String, TBelief> Map_Belief,
        		HashMap<String, TRegion> Regions);
    }
	
	private TExecutive_Inhibition_Function Owner;
	private TGlobal_Workspace Global_Workspace;
	private HashSet<TRegion> Inhibited_Regions;
	private HashSet<TRegion> Uninhibited_Regions;
	
	private TRegion_Inhibition_Function_Handler Practical_Region_Inhibition_Function_Handler;
	private TRegion_Inhibition_Function_Handler Epistemic_Region_Inhibition_Function_Handler;
	
	private Generate_Unhinibited_Regions Generate_Unhinibited_Regions_for_Intention;
	
	public TRegions_Inhibition(TExecutive_Inhibition_Function owner)
	{
		this.Owner = owner;
		this.Global_Workspace = this.Owner.Get_Global_Workspace();
		
		this.Uninhibited_Regions = new HashSet<TRegion>();
		this.Inhibited_Regions = new HashSet<TRegion>();
		
		this.Practical_Region_Inhibition_Function_Handler = new TRegion_Inhibition_Function_Handler();
		this.Epistemic_Region_Inhibition_Function_Handler = new TRegion_Inhibition_Function_Handler();
	}
	
	public void Execute( ArrayList<TIntention> Intentions )
	{
		this.Uninhibited_Regions.clear();
		this.Inhibited_Regions.clear();
		
		HashSet<TRegion> inhibited_Regions = new HashSet<TRegion>();
		HashSet<TRegion> uninhibited_Regions = new HashSet<TRegion>();
		ArrayList<TRegion> Temp_Regions = new ArrayList<TRegion>();
		inhibited_Regions.addAll( this.Global_Workspace.Get_All_Regions_from_MM() );
		
		Temp_Regions.addAll(inhibited_Regions);
		HashMap<String, TBelief> All_Map_Beliefs = new HashMap<String, TBelief>(
				 this.Global_Workspace.Get_All_Map_Beliefs_from_MM() );
		
		HashMap<String, TRegion> All_Map_Regionss = new HashMap<String, TRegion>(
				 this.Global_Workspace.Get_All_Map_Regions_from_LTM() );
		

		ArrayList<String> Temp_Uninhibited_Regions = new ArrayList<String>();
		ArrayList<String> List_Regions_name = new ArrayList<String>();
		for(TIntention Selected_Intention: Intentions)
		{
			{
				Temp_Uninhibited_Regions.clear();
				
				TAttentional_Desire Attentional_Desire = (TAttentional_Desire) Selected_Intention.Get_Active_Desire();
				if(Attentional_Desire != null)
				{
					switch(Attentional_Desire)
					{
						// PRACTICAL DESIRES
						case TPractical_Desire Practical_Desire ->
						{
							
							List_Regions_name.addAll(
							 this.Practical_Region_Inhibition_Function_Handler.
										Execute_Function_For_Desire( Practical_Desire.Get_Name(), 
												Selected_Intention, All_Map_Beliefs, All_Map_Regionss) );
							
							
							
						}
						case TEpistemic_Desire Epistemic_Desire ->
						{
							List_Regions_name.addAll(
									 this.Epistemic_Region_Inhibition_Function_Handler.
												Execute_Function_For_Desire( Epistemic_Desire.Get_Belief().Get_Type_Belief(), 
														Selected_Intention, All_Map_Beliefs, All_Map_Regionss) );
							
						}
						default -> throw new IllegalArgumentException("Unexpected value: " + Attentional_Desire);
					}
				}
			}
		}
		List_Regions_name.remove( null );
		
		for(String Region_Name: List_Regions_name )
		{
			uninhibited_Regions.add( All_Map_Regionss.get( Region_Name ) );
		}
		
		inhibited_Regions.removeAll( uninhibited_Regions );
		
		this.Inhibited_Regions.addAll( inhibited_Regions );
		this.Uninhibited_Regions.addAll( uninhibited_Regions );
		this.Uninhibited_Regions.size();
	}
	
//	public void Execute_old( ArrayList<TIntention> Intentions )
//	{
//		this.Uninhibited_Regions.clear();
//		this.Inhibited_Regions.clear();
//		
//		HashSet<TRegion> inhibited_Regions = new HashSet<TRegion>();
//		HashSet<TRegion> uninhibited_Regions = new HashSet<TRegion>();
//		ArrayList<TRegion> Temp_Regions = new ArrayList<TRegion>();
//		inhibited_Regions.addAll( this.Global_Workspace.Get_All_Regions_from_MM() );
//		
//		Temp_Regions.addAll(inhibited_Regions);
//		HashMap<String, TBelief> All_Map_Beliefs = new HashMap<String, TBelief>(
//				 this.Global_Workspace.Get_All_Map_Beliefs_from_MM() );
//		
//
//		ArrayList<TRegion> Temp_Uninhibited_Regions = new ArrayList<TRegion>();
//		for(TIntention Intention: Intentions)
//		{
////			hgfhgf
//			if (this.Generate_Unhinibited_Regions_for_Intention != null )
//			{
//				Temp_Uninhibited_Regions.addAll(  
//						this.Generate_Unhinibited_Regions_for_Intention.Execute(
//								Intention, All_Map_Beliefs, Temp_Regions) );
////				if (Temp_Uninhibited_Regions.size() > 0)
//				if (Temp_Uninhibited_Regions != null)
//				{
//					uninhibited_Regions.addAll( Temp_Uninhibited_Regions );
//					Temp_Uninhibited_Regions.clear();
//				} 
//			}
//			else
//			{
//				System.out.println("No Functions to Generate Unhinibited Regions for all intention(s)");
//			}
//		}
//		inhibited_Regions.removeAll( uninhibited_Regions );
//		
//		this.Inhibited_Regions.addAll( inhibited_Regions );
//		this.Uninhibited_Regions.addAll( uninhibited_Regions );
//		this.Uninhibited_Regions.size();
//	}
	
//	public void Execute_old( ArrayList<TIntention> Intentions )
//	{
//		this.Uninhibited_Regions.clear();
//		this.Inhibited_Regions.clear();
//		
//		HashSet<TRegion> inhibited_Regions = new HashSet<TRegion>();
//		HashSet<TRegion> uninhibited_Regions = new HashSet<TRegion>();
//		ArrayList<TRegion> Temp_Regions = new ArrayList<TRegion>();
//		inhibited_Regions.addAll( this.Global_Workspace.Get_All_Regions_from_MM() );
//		
//		Temp_Regions.addAll(inhibited_Regions);
//		HashMap<String, TBelief> All_Map_Beliefs = new HashMap<String, TBelief>(
//				 this.Global_Workspace.Get_All_Map_Beliefs_from_MM() );
//		
//
//		HashMap<String, TRegion> Temp_Uninhibited_Regions = new ArrayList<TRegion>();
//		for(TIntention Intention: Intentions)
//		{
//			if (this.Generate_Unhinibited_Regions_for_Intention != null )
//			{
//				Temp_Uninhibited_Regions.addAll(  
//						this.Generate_Unhinibited_Regions_for_Intention.Execute(
//								Intention, All_Map_Beliefs, Temp_Regions) );
////				if (Temp_Uninhibited_Regions.size() > 0)
//				if (Temp_Uninhibited_Regions != null)
//				{
//					uninhibited_Regions.addAll( Temp_Uninhibited_Regions );
//					Temp_Uninhibited_Regions.clear();
//				} 
//			}
//			else
//			{
//				System.out.println("No Functions to Generate Unhinibited Regions for all intention(s)");
//			}
//		}
//		inhibited_Regions.removeAll( uninhibited_Regions );
//		
//		this.Inhibited_Regions.addAll( inhibited_Regions );
//		this.Uninhibited_Regions.addAll( uninhibited_Regions );
//		this.Uninhibited_Regions.size();
//	}
	
	public ArrayList<TRegion> Get_Uninhibited_Regions()
	{
		ArrayList<TRegion> result = new ArrayList<TRegion>();
		result.addAll( this.Uninhibited_Regions );
		this.Uninhibited_Regions.clear();
		
		return result;
	}
	
	public ArrayList<TRegion> Get_Inhibited_Regions()
	{
		ArrayList<TRegion> result = new ArrayList<TRegion>();
		result.addAll( this.Inhibited_Regions );
		this.Inhibited_Regions.clear();
		
		return result;
	}
	
	public void Set_Generate_Unhinibited_Regions_for_Intention(Generate_Unhinibited_Regions Func)
	{
		this.Generate_Unhinibited_Regions_for_Intention = Func;
	}
	
	public void Register_Practical_Region_Inhibition_Function(String Name, TRegion_Inhibition_Function func) 
    {
        this.Practical_Region_Inhibition_Function_Handler.Register_Region_Inhibition_Function( Name , func);
    }
    
    public boolean Unregister_Practical_Region_Inhibition_Function(String Name) 
    {
        return this.Practical_Region_Inhibition_Function_Handler.Unregister_Region_Inhibition_Function( Name );
    }
    
    public void Register_Epistemic_Region_Inhibition_Function(String Name, TRegion_Inhibition_Function func) 
    {
//        this.Epistemic_Region_Inhibition_Function_Handler.Register_Region_Inhibition_Function( Name , func);
    }
    
    public boolean Unregister_Epistemic_Region_Inhibition_Function(String Name) 
    {
        return this.Epistemic_Region_Inhibition_Function_Handler.Unregister_Region_Inhibition_Function( Name );
    }
    
    

}
