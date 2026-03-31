package com.Catalina_Model.Catalina_V_0_3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class TBelief_Inhibition_Function_Handler 
{

	@FunctionalInterface
    public interface TBelief_Inhibition_Function 
    {
		HashSet<String> apply(
				  TAttentional_Desire Attentional_Desire, 
				  		HashMap<String, TBelief> All_Map_Beliefs);
    }
	
	private final HashMap<String, TBelief_Inhibition_Function> Registered_Functions;
	
	public TBelief_Inhibition_Function_Handler() 
	{
		this.Registered_Functions = new HashMap<String, TBelief_Inhibition_Function>();
	}
	
	public void Register_Belief_Inhibition_Function(String Name, TBelief_Inhibition_Function func) 
    {
        if (Name == null || Name.trim().isEmpty() || func == null) 
        {
            throw new IllegalArgumentException("Invalid beliefType or function for registration.");
        }
        this.Registered_Functions.put(Name, func);
    }
    
    public boolean Unregister_Belief_Inhibition_Function(String Name) 
    {
        if (Name == null || Name.trim().isEmpty())
        {
            return false;
        }
        return this.Registered_Functions.remove(Name) != null;
    }
    
    public HashSet<String> Execute_Function_For_Desire( String Desire_Name,
    		TAttentional_Desire Attentional_Desire, HashMap<String, TBelief> Map_Beliefs)
    {
    	HashSet<String> results = new HashSet<String>();
    	
        if (Desire_Name == null || Desire_Name.isEmpty()) 
        {
            System.err.println("Error: Name for Belief Inhibition Function provided for execution is null.");
            return results;
        }
//        System.out.print(Registered_Functions.keySet());
        TBelief_Inhibition_Function func = this.Registered_Functions.get(Desire_Name);
        
        HashSet<String> temp_results = new HashSet<String>();

        if (func != null) 
        {
        	temp_results = func.apply(Attentional_Desire, Map_Beliefs);
        	if (temp_results !=null)
        	{
//        		results.Set_Associated_Preconditions( temp_results.Get_Associated_Preconditions());
        		temp_results.remove( null );
        		results.addAll( temp_results );
        	}
        } 
        else 
        {
//            System.out.println("\nWARN BI: No Belief Inhibition Function  registered for the Belief Type: '" + Desire_Name + "'.");
        }
        return results;
    }
}
