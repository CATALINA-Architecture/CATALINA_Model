package com.Catalina_Model.Catalina_V_0_3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class TMeans_End_Epistemic_Functions_Handler 
{
	
	@FunctionalInterface
    public interface Means_End_Epistemic_Function {
        ArrayList<TOption> apply(TEpistemic_Desire epistemic_Desire,
				        		 HashMap<String, TBelief> beliefs,
				                 HashMap<String, TRegion> regions,
                                 ArrayList<TIntention> intentions,
                                 IPlanner Planner);
//                                 TMeans_End_Reasoner_Data_Getter Means_End_Reasoner_Data_Getter);
    }
	
	private final HashMap<String, Means_End_Epistemic_Function> Registered_Functions;

    public TMeans_End_Epistemic_Functions_Handler() 
    {
    	this.Registered_Functions = new HashMap<String, TMeans_End_Epistemic_Functions_Handler.Means_End_Epistemic_Function>();
    }
    
    public void Register_Epistemic_Function(String belief_Type, Means_End_Epistemic_Function func) 
    {
        if (belief_Type == null || belief_Type.trim().isEmpty() || func == null) 
        {
            throw new IllegalArgumentException("Invalid beliefType or function for registration.");
        }
        this.Registered_Functions.put(belief_Type, func);
    }
    
    public boolean Unregister_Epistemic_Function(String belief_Type) 
    {
        if (belief_Type == null || belief_Type.trim().isEmpty())
        {
            return false;
        }
        return this.Registered_Functions.remove(belief_Type) != null;
    }
    
    public ArrayList<TOption> Execute_Function_For_Desire(
            TEpistemic_Desire epistemic_desire,
            HashMap<String, TBelief> beliefs,
            HashMap<String, TRegion> regions,
            ArrayList<TIntention> intentions,
            IPlanner Planner,
            TMeans_End_Reasoner_Data_Getter Means_End_Reasoner_Data_Getter) 
    {

    	ArrayList<TOption> result = new ArrayList<TOption>();
        if (epistemic_desire == null) 
        {
            System.err.println("Error: TEpistemic_Desire provided for execution is null.");
            return result;
        }

        String beliefType = epistemic_desire.Get_Belief().Get_Type_Belief();

        if (beliefType == null || beliefType.trim().isEmpty()) 
        {
            System.err.println("Error: Associated Belief Type is null or empty in TEpistemic_Desire: " + epistemic_desire);
            return result;
        }

        Means_End_Epistemic_Function func = this.Registered_Functions.get(beliefType);

        if (func != null) 
        {
        	ArrayList<TOption> temp_result = func.apply(epistemic_desire, beliefs, regions, 
//        													intentions, Means_End_Reasoner_Data_Getter);
        													intentions, Planner);
        	if( temp_result != null)
        	{
        		result.addAll( temp_result );
        		result.remove(null);
        	}
            return result;
        } 
        else 
        {
//            System.out.println("\nWARN: No Means_End Epistemic Function registered for the Belief Type: '" + beliefType + "'.");
            return result;
        }
    }

}
