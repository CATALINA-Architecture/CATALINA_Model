package com.Catalina_Model.Catalina_V_0_3;

import java.util.ArrayList;
import java.util.HashMap;

public class TAction_Execution_Function_Handler 
{


	/**
	 * TTriple_Object contains two objects:
	 * 	1 - a Practical Desire (TPractical_desire)
	 *  2 - an Internal Signal to send (TType_Update_Contract)
	 *  3 - ArrayList of Object: Data of Selected Intention to Abort [0- Selected Intention; 1- Option_Id] 
	 */
		@FunctionalInterface
//	    public interface Plan_Executive_Function {
//			Boolean apply(	TAction Action
////							ArrayList<TBelief> Beliefs
//						);
//	    }
		public interface Plan_Executive_Function {
//			TDouble_Object apply(	TAction Action, HashMap<String, TBelief> Beliefs );
			TAction_Execution_Result apply(	TAction Action, HashMap<String, TBelief> Beliefs );
	    }
		
		private final HashMap<String, Plan_Executive_Function> Registered_Functions;

	    public TAction_Execution_Function_Handler() 
	    {
	    	this.Registered_Functions = new HashMap<String, Plan_Executive_Function>();
	    }
	    
	    public void Register_Function(String Action_Name, Plan_Executive_Function func) 
	    {
	        if (Action_Name == null || Action_Name.trim().isEmpty() || func == null) 
	        {
	            throw new IllegalArgumentException("Invalid beliefType or function for registration.");
	        }
	        this.Registered_Functions.put(Action_Name, func);
	    }
	    
	    public void Register_Function(TAction Action, Plan_Executive_Function func) 
	    {
	    	if (Action == null)
	    	{
	            throw new IllegalArgumentException("Invalid belief or function for registration.");
	        }
	    	this.Register_Function(Action.Get_Action_Name(), func);
	    }
	    
	    public boolean Unregister_Function(String Action_Name) 
	    {
	        if (Action_Name == null || Action_Name.trim().isEmpty())
	        {
	            return false;
	        }
	        return this.Registered_Functions.remove(Action_Name) != null;
	    }
	    
	    public TAction_Execution_Result Execute_Function_For_Desire( TAction Action,
//	    		TIntention Intention, 
	    		HashMap<String, TBelief> Beliefs
	           ) 
	    {

	    	TAction_Execution_Result result = new TAction_Execution_Result(); 

	    	if (Action == null) 
	        {
	            System.err.println("Error: Action provided for execution is null.");
	            return result;
	        }

	        String Action_Name = Action.Get_Action_Name();

	        if (Action_Name == null || Action_Name.trim().isEmpty()) 
	        {
	            System.err.println("Error: Associated Action Name is null or empty in Action Name: " + Action_Name);
	            return result;
	        }
	        
	        Plan_Executive_Function func = this.Registered_Functions.get(Action_Name);

	        if (func != null) 
	        {
//	            return func.apply(practical_desire, beliefs, regions, intentions);
//	        	return func.apply( Action, beliefs);
	        	TAction_Execution_Result Temp_result = func.apply( Action, Beliefs );
	        	if (Temp_result != null)
	        	{ 
	        		result = Temp_result;
	        	}
	        	return result;
	        } 
	        else 
	        {
//	            System.out.println("\nWARN: No Plan Execution Function registered for the Action Name: '" + Action_Name + "'.");
	            return result;
//	        }
	    }

	}
}