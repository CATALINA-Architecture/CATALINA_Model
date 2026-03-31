package com.Catalina_Model.Catalina_V_0_3;

import java.util.ArrayList;
import java.util.HashMap;

public class TGlobal_Workspace_Memory_Maintenance_Update_Belief_Function_Handler 
{
	
	@FunctionalInterface
//	public interface Global_Workspace_Memory_Maintenance_Update_Belief_Function
//    {
//		boolean apply(TStimulus Stimulus, HashMap<String, TBelief> Beliefs);
//    }
	public interface Global_Workspace_Memory_Maintenance_Update_Belief_Function
    {
		TGW_Memory_Maintenance_Update_Belief_Function_Result apply(TStimulus Stimulus, HashMap<String, TBelief> Beliefs);
    }
	
//	
	
	private final HashMap<String, 
					Global_Workspace_Memory_Maintenance_Update_Belief_Function> Registered_Functions;

    public TGlobal_Workspace_Memory_Maintenance_Update_Belief_Function_Handler() 
    {
    	this.Registered_Functions = new HashMap<String, 
    					Global_Workspace_Memory_Maintenance_Update_Belief_Function>();
    }
    
    public void Register_Function(String Stimulus_Type, 
    					Global_Workspace_Memory_Maintenance_Update_Belief_Function func) 
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
    
    public TGW_Memory_Maintenance_Update_Belief_Function_Result Execute_Function_For_Update_Belief(
    		TStimulus Stimulus, HashMap<String, TBelief> Beliefs) 
    {

    	TGW_Memory_Maintenance_Update_Belief_Function_Result result = new TGW_Memory_Maintenance_Update_Belief_Function_Result();
    	if ( Stimulus == null || Stimulus.Get_Type_Belief().trim().isEmpty() )
        {
            System.err.println("Error: the Stimulus provided for execution is null.");
            return result;
        }

    	Global_Workspace_Memory_Maintenance_Update_Belief_Function func = 
    						this.Registered_Functions.get( Stimulus.Get_Type_Belief() );

        if (func != null) 
        {
        	TGW_Memory_Maintenance_Update_Belief_Function_Result temp_result = func.apply( Stimulus, Beliefs);
        	if( temp_result != null)
        	{
        		result = temp_result;
        	}
        	return result;
        } 
        else 
        {
//        	System.out.println("\nWARN GW: No Stimulus Function registered for the Stimulus Type: '" + Stimulus.Get_Type_Belief() + "'.");
            return result;
        }
    }

}
