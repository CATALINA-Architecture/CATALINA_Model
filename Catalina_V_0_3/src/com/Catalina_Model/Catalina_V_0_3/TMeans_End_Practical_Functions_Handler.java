package com.Catalina_Model.Catalina_V_0_3;

import java.util.ArrayList;
import java.util.HashMap;


public class TMeans_End_Practical_Functions_Handler 
{
	@FunctionalInterface
    public interface Means_End_Practical_Function {
        ArrayList<TOption> apply(TPractical_Desire practical_Desire,
                                 HashMap<String, TBelief> beliefs,
                                 HashMap<String, TRegion> regions,
                                 ArrayList<TIntention> intentions,
                                 IPlanner Planner);
//                                 TMeans_End_Reasoner_Data_Getter Means_End_Reasoner_Data_Getter);
    }
	
	private final HashMap<String, Means_End_Practical_Function> Registered_Functions;

    public TMeans_End_Practical_Functions_Handler() 
    {
    	this.Registered_Functions = new HashMap<String, Means_End_Practical_Function>();
    }
    
    public void Register_Practical_Function(String Desire_Name, Means_End_Practical_Function func) 
    {
        if (Desire_Name == null || Desire_Name.trim().isEmpty() || func == null) 
        {
            throw new IllegalArgumentException("Invalid beliefType or function for registration.");
        }
        this.Registered_Functions.put(Desire_Name, func);
    }
    
    public boolean Unregister_Practical_Function(String Desire_Name) 
    {
        if (Desire_Name == null || Desire_Name.trim().isEmpty())
        {
            return false;
        }
        return this.Registered_Functions.remove(Desire_Name) != null;
    }
    
    public ArrayList<TOption> Execute_Function_For_Desire(
            TPractical_Desire practical_desire,
            HashMap<String, TBelief> beliefs,
            HashMap<String, TRegion> regions,
            ArrayList<TIntention> intentions,
            IPlanner Planner) 
    {

    	ArrayList<TOption> result = new ArrayList<TOption>();
        if (practical_desire == null) 
        {
            System.err.println("Error: TEpistemic_Desire provided for execution is null.");
            return result;
        }

        String Desire_Name = practical_desire.Get_Name();

        if (Desire_Name == null || Desire_Name.trim().isEmpty()) 
        {
            System.err.println("Error: Associated Belief Type is null or empty in TEpistemic_Desire: " + practical_desire);
            return result;
        }

        Means_End_Practical_Function func = this.Registered_Functions.get(Desire_Name);

        if (func != null) 
        {
        	ArrayList<TOption> temp_result = func.apply(practical_desire, beliefs, regions,
        													intentions, Planner);	
//        													intentions, Means_End_Reasoner_Data_Getter);
        	if( temp_result != null)
        	{
        		result.addAll( temp_result );
        	}
            return result;
        } 
        else 
        {
//            System.out.println("\nWARN: No Means_End Epistemic Function registered for the Belief Type: '" + Desire_Name + "'.");
            return result;
        }
    }

}
