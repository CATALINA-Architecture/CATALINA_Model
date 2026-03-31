package com.Catalina_Model.Catalina_V_0_3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Function;

public class TResource_Managment_Practical_Functions_Handler 
{
	/**
	 * TTriple_Object contains two objects:
	 * 	1 - a Practical Desire (TPractical_desire)
	 *  2 - an Internal Signal to send (TType_Update_Contract)
	 *  3 - ArrayList of Object: Data of Selected Intention to Abort [0- Selected Intention; 1- Option_Id] 
	 */
		@FunctionalInterface
	    public interface Resource_Managment_Practical_Function {
			TTriple_Object apply(
//	        						 TPractical_Desire practical_Desire,
	        						 TBelief Critical_Belief,
	                                 ArrayList<TBelief> beliefs,
	                                 ArrayList<TIntention> intentions);
	    }
		
		private final HashMap<String, Resource_Managment_Practical_Function> Registered_Functions;

	    public TResource_Managment_Practical_Functions_Handler() 
	    {
	    	this.Registered_Functions = new HashMap<String, Resource_Managment_Practical_Function>();
	    }
	    
	    public void Register_Function(String belief_Type, Resource_Managment_Practical_Function func) 
	    {
	        if (belief_Type == null || belief_Type.trim().isEmpty() || func == null) 
	        {
	            throw new IllegalArgumentException("Invalid belief type or function for registration.");
	        }
	        this.Registered_Functions.put(belief_Type, func);
	    }
	    
	    public void Register_Function(TBelief belief, Resource_Managment_Practical_Function func) 
	    {
	    	if (belief == null)
	    	{
	            throw new IllegalArgumentException("Invalid belief or function for registration.");
	        }
	    	this.Register_Function(belief.Get_Type_Belief(), func);
	    }
	    
	    public boolean Unregister_Function(String belief_Type) 
	    {
	        if (belief_Type == null || belief_Type.trim().isEmpty())
	        {
	            return false;
	        }
	        return this.Registered_Functions.remove(belief_Type) != null;
	    }
	    
	    public TTriple_Object Execute_Function_For_Desire(TBelief important_belief,
//	            TPractical_Desire practical_desire,
	            ArrayList<TBelief> beliefs,
	            ArrayList<TIntention> intentions) 
	    {

	        if (important_belief == null) 
	        {
	            System.err.println("Error: Important Belief provided for execution is null.");
	            return null;
	        }

	        String beliefType = important_belief.Get_Type_Belief();

	        if (beliefType == null || beliefType.trim().isEmpty()) 
	        {
	            System.err.println("Error: Associated Belief Type is null or empty in important belief: " + important_belief);
	            return null;
	        }
	        
	        if (beliefs == null) 
	        {
	            System.err.println("Error: Beliefs provided for execution are null.");
	            return null;
	        }
	        
	        if (intentions == null) 
	        {
	            System.err.println("Error: Intentions provided for execution are null.");
	            return null;
	        }


	        Resource_Managment_Practical_Function func = this.Registered_Functions.get(beliefType);

	        if (func != null) 
	        {
//	            return func.apply(practical_desire, beliefs, regions, intentions);
	        	return func.apply( important_belief, beliefs, intentions);
	        } 
	        else 
	        {
//	            System.out.println("\nWARN: No Resource Managment Function registered for the Belief Type: '" + beliefType + "'.");
	            return null;
//	        }
	    }

	}
}