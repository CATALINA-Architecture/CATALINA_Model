package com.Catalina_Model.Catalina_V_0_3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import com.Catalina_Model.Catalina_V_0_3.TBelief_Inhibition_Function_Handler.TBelief_Inhibition_Function;

public class TRegion_Inhibition_Function_Handler 
{
	@FunctionalInterface
    public interface TRegion_Inhibition_Function 
    {
        HashSet<String> apply(TIntention Intention, HashMap<String, TBelief> All_Map_Beliefs, 
        				HashMap<String, TRegion> All_Map_Regions);
    }
	
	private final HashMap<String, TRegion_Inhibition_Function> Registered_Functions;
	
	public TRegion_Inhibition_Function_Handler() 
	{
		this.Registered_Functions = new HashMap<String, TRegion_Inhibition_Function>();
	}
	
	public void Register_Region_Inhibition_Function(String Name, TRegion_Inhibition_Function func) 
    {
        if (Name == null || Name.trim().isEmpty() || func == null) 
        {
            throw new IllegalArgumentException("Invalid beliefType or function for registration.");
        }
        this.Registered_Functions.put(Name, func);
    }
    
    public boolean Unregister_Region_Inhibition_Function(String Name) 
    {
        if (Name == null || Name.trim().isEmpty())
        {
            return false;
        }
        return this.Registered_Functions.remove(Name) != null;
    }
    
    public HashSet<String> Execute_Function_For_Desire(String Name, TIntention Intention, 
    		HashMap<String, TBelief> Map_Beliefs, HashMap<String, TRegion> Map_Regions)
    {
    	if (Name == null || Name.isEmpty()) 
        {
            System.err.println("Error: Name for Belief Inhibition Function provided for execution is null.");
            return null;
        }
//        System.out.print(Registered_Functions.keySet());
    	TRegion_Inhibition_Function func = this.Registered_Functions.get(Name);
    	HashSet<String> results = new HashSet<String>();
        HashSet<String> temp_results = new HashSet<String>();

        if (func != null) 
        {
        	temp_results = func.apply(Intention, Map_Beliefs, Map_Regions);
        	if (temp_results !=null)
        	{
        		results.addAll( temp_results );
        	}
        } 
        else 
        {
//            System.out.println("\nWARN RI: No Belief Inhibition Function  registered for the Belief Type: '" + Name + "'.");
        }
        return results;
    }
	

}
