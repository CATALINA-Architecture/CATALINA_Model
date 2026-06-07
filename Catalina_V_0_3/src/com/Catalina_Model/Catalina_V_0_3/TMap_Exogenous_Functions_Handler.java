package com.Catalina_Model.Catalina_V_0_3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import com.Catalina_Model.Catalina_V_0_3.TGlobal_Workspace_Memory_Maintenance_Update_Belief_Function_Handler.Global_Workspace_Memory_Maintenance_Update_Belief_Function;

/**
 * this class is useful to map a Stimulus to a Function. This function can be executed for general purpose:
 * for example to create Epistemic Desire from a Stimulus, or to manage and update Beliefs from a Stimulus, etc.
 */
public class TMap_Exogenous_Functions_Handler 
{
    private HashMap<String, Stimulus_To_Epistemic_Function> List_Functions;
    
    @FunctionalInterface
    public interface Stimulus_To_Epistemic_Function
    {
    	TEpistemic_Desire apply(TGlobal_Workspace Global_Workspace, TStimulus Stimulus);
    }


	public TMap_Exogenous_Functions_Handler() 
	{
		this.List_Functions = new HashMap<String, Stimulus_To_Epistemic_Function>();
	}
	
	public void Register_Epistemic_Function(String Stimulus_Type, 
			Stimulus_To_Epistemic_Function func) 
	{
		if (Stimulus_Type == null || Stimulus_Type.trim().isEmpty() || func == null)	
		{
			throw new IllegalArgumentException("Invalid key or function entered in 'TStimulus_to_Function_Manager.");
		}
		this.List_Functions.put( Stimulus_Type, func);
	}
	
	public void UnRegister_Epistemic_Function(String Stimulus_Type) 
	{
        if (Stimulus_Type == null || Stimulus_Type.trim().isEmpty()) 
        {
            throw new IllegalArgumentException("Invalid key or function entered in 'TStimulus_to_Function_Manager'.");
        }
//        List_Functions.put(Stimulus_Type, Function);
        List_Functions.remove( Stimulus_Type );
    }
	
	public TEpistemic_Desire Execute_Epistemic_Function(TGlobal_Workspace Global_Workspace, TStimulus Stimulus)
	{
		String Type_Belief = Stimulus.Get_Type_Belief();
		
        if (Type_Belief == null || Type_Belief.trim().isEmpty()) 
        {
            System.err.println("Error: The key provided for execution is null or empty.");
            return null;
        }

        Stimulus_To_Epistemic_Function func = this.List_Functions.get( Type_Belief );
        if (func != null)
        {

            TEpistemic_Desire result = func.apply(Global_Workspace, Stimulus); // Applica la funzione
            return result;
        } 
        else 
        {
//            System.out.println("No TEpistemic_Desire functions registered for the key '" +
//            		Type_Belief + "' in 'TStimulus_to_Function_Manager'.");
//            System.exit(0);
            return null;
        }
    }
	
	public HashMap<String, Stimulus_To_Epistemic_Function> Get_Mapped_Functions()
	{
		return this.List_Functions;
	}
	
	public void Clear()
	{
		this.List_Functions.clear();
	}

}