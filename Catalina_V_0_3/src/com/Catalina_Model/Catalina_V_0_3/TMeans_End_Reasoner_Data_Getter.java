package com.Catalina_Model.Catalina_V_0_3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class TMeans_End_Reasoner_Data_Getter 
{
	private TExecutive_Reasoner_Function Owner;
	
	public TMeans_End_Reasoner_Data_Getter(TExecutive_Reasoner_Function owner)
	{
		this.Owner = owner;
	}
	
	private HashMap<String, TBelief> Get_Selected_Beliefs(HashSet<String> Beliefs_Names)
	{
		HashMap<String, TBelief> result = new HashMap<String, TBelief>();
		result.putAll( this.Owner.Get_Global_Workspace().Get_Selected_Beliefs_from_LTM( Beliefs_Names ));
		
		return result;
	}
	
	public HashMap<String, TBelief> Get_Selected_Beliefs(ArrayList<String> Beliefs_Names)
	{
		HashMap<String, TBelief> result = new HashMap<String, TBelief>();
		HashSet<String> Set_Beliefs_Names = new HashSet<String>();
		Set_Beliefs_Names.addAll( Beliefs_Names );
		
		result.putAll( this.Get_Selected_Beliefs( Set_Beliefs_Names ));
		
		return result;
	}
	
	private HashMap<String, TRegion> Get_Selected_Regions(HashSet<String> Regions_Names)
	{
		HashMap<String, TRegion> result = new HashMap<String, TRegion>();
		result.putAll( this.Owner.Get_Global_Workspace().Get_Selected_Regions_from_LTM( Regions_Names ));
		
		return result;
	}
	
	public HashMap<String, TRegion> Get_Selected_Regions(ArrayList<String> Regions_Names)
	{
		HashMap<String, TRegion> result = new HashMap<String, TRegion>();
		HashSet<String> Set_Regions_Names = new HashSet<String>();
		Set_Regions_Names.addAll( Regions_Names );
		
		result.putAll( this.Get_Selected_Regions( Set_Regions_Names ));
		
		return result;
	}
	
	
	
}
