package com.Catalina_Model.Catalina_V_0_3;

import java.util.ArrayList;
import java.util.HashMap;



public class TStimulus_Filtering_Manipulate_Stimulus_Function_Handler 
{
	
	@FunctionalInterface
    public interface Stimulus_Filtering_Manipulate_Stimulus_Function {
        TStimulus apply(TStimulus Stimulus, ArrayList<TRegion> Regions);
    }
	
	private final HashMap<String, Stimulus_Filtering_Manipulate_Stimulus_Function> Registered_Functions;

    public TStimulus_Filtering_Manipulate_Stimulus_Function_Handler() 
    {
    	this.Registered_Functions = new HashMap<String, Stimulus_Filtering_Manipulate_Stimulus_Function>();
    }
    
    public void Register_Function(String Stimulus_Type, Stimulus_Filtering_Manipulate_Stimulus_Function func) 
    {
        if (Stimulus_Type == null || Stimulus_Type.trim().isEmpty() || func == null)
        {
            throw new IllegalArgumentException("Invalid Stimulus Type or function "
            		+ "for registration.");
        }
        this.Registered_Functions.put( Stimulus_Type, func);
    }
    
    public boolean Unregister_Function(String Stimulus_Type) 
    {
    	if (Stimulus_Type == null || Stimulus_Type.trim().isEmpty())
        {
            return false;
        }
        return this.Registered_Functions.remove(Stimulus_Type) != null;
    }
    
    public TStimulus Execute_Function_For_Stimulus(
    		TStimulus Stimulus, ArrayList<TRegion> Regions) 
    {
    	TStimulus Generated_Stimulus = Stimulus;
    	
    	if (Generated_Stimulus == null || Generated_Stimulus.Get_Type_Belief() == null || 
    			Stimulus.Get_Type_Belief().trim().isEmpty() )
        {
            System.err.println("Error: Stimulus provided for execution is null.");
//            return null;
            return Generated_Stimulus;
        }

        Stimulus_Filtering_Manipulate_Stimulus_Function func = 
        					this.Registered_Functions.get( Stimulus.Get_Type_Belief() );

        if (func != null) 
        {
        	Generated_Stimulus = func.apply(Stimulus, Regions);
        	if (Generated_Stimulus == null)
        	{
        		Generated_Stimulus = Stimulus;
        	}
        	return Generated_Stimulus;
        } 
        else 
        {
//        	System.out.println("\nWARN: No Stimulus Filtering Manipulate Stimuls"
//        			+ "Function registered for the Stimulus Type: '" + Stimulus + "'.");
//            return null;
            return Generated_Stimulus;
        }
    }

}
