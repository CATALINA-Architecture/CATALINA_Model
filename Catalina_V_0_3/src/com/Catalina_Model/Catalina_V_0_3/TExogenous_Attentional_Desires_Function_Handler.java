package com.Catalina_Model.Catalina_V_0_3;

import java.util.HashMap;

import com.Catalina_Model.Catalina_V_0_3.TGlobal_Workspace.TIRecall_Beliefs;


/**
 * This Class executes a function associated to a Stimulus to arise a list od
 * Practical Desires and/or a list of Epistemic Desires
 */
public class TExogenous_Attentional_Desires_Function_Handler
{
	
	@FunctionalInterface
//	public interface Global_Workspace_Memory_Maintenance_Update_Belief_Function
//    {
//		boolean apply(TStimulus Stimulus, HashMap<String, TBelief> Beliefs);
//    }
	public interface Exogenous_Attentional_Desires_Promotion_Function
    {
		TExogenous_Attentional_Desires_Promotion_Function_Result apply(TStimulus Stimulus, 
				HashMap<String, TBelief> Beliefs, TIRecall_Beliefs Recall_Belief);
		
    }
	
//	
	
	private final HashMap<String, 
			Exogenous_Attentional_Desires_Promotion_Function> Registered_Functions;
			
	private TIRecall_Beliefs Recall_Belief_Function;

    public TExogenous_Attentional_Desires_Function_Handler() 
    {
    	this.Registered_Functions = new HashMap<String, 
    				Exogenous_Attentional_Desires_Promotion_Function>();
    }
    
    public void Register_Function(String Stimulus_Type, 
    				Exogenous_Attentional_Desires_Promotion_Function func) 
    {
        if (Stimulus_Type == null || Stimulus_Type.trim().isEmpty() || func == null)
        {
            throw new IllegalArgumentException("Invalid Sensor or function for registration.");
        }
        this.Registered_Functions.put( Stimulus_Type, func);
    }
    
    public boolean Unregister_Function(String Stimulus_Type) 
    {
        if ( Stimulus_Type == null || Stimulus_Type.trim().isEmpty() )
        {
            return false;
        }
        return this.Registered_Functions.remove(Stimulus_Type) != null;
    }
    
    public TExogenous_Attentional_Desires_Promotion_Function_Result Execute_Function_For_Desire_to_raise(
    		TStimulus Stimulus, HashMap<String, TBelief> Beliefs) 
    {

    	TExogenous_Attentional_Desires_Promotion_Function_Result result = new TExogenous_Attentional_Desires_Promotion_Function_Result();
    	if ( Stimulus == null || Stimulus.Get_Type_Belief().trim().isEmpty() )
        {
            System.err.println("Error: the Stimulus provided for execution is null.");
            return result;
        }

    	Exogenous_Attentional_Desires_Promotion_Function func = 
    						this.Registered_Functions.get( Stimulus.Get_Type_Belief() );

        if (func != null) 
        {
        	TExogenous_Attentional_Desires_Promotion_Function_Result temp_result = func.apply( Stimulus, Beliefs,
        			this.Recall_Belief_Function);
        	
        	if( temp_result != null)
        	{
        		result = temp_result;
        	}
        	return result;
        } 
        else 
        {
//        	System.out.println("\nWARN: No Stimulus Function registered for the Stimulus Type: '" + Stimulus.Get_Type_Belief() + "'.");
            return result;
        }
    }
    
    public void Set_Recall_Belief(TIRecall_Beliefs Recall_Belief)
    {
    	if(Recall_Belief != null)
    	{
    		this.Recall_Belief_Function = Recall_Belief;
    	}
    }

}
