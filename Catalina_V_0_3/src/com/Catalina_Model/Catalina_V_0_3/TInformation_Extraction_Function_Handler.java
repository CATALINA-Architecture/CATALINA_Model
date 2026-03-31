
package com.Catalina_Model.Catalina_V_0_3;

import java.util.ArrayList;
import java.util.HashMap;

public class TInformation_Extraction_Function_Handler
{
	
	@FunctionalInterface
    public interface Information_Extraction_Function {
        ArrayList<TStimulus> apply( TPerception Perception, ArrayList<TBelief> Beliefs,
        		ArrayList<TRegion> Regions);
    }
	
	private final HashMap<String, Information_Extraction_Function> Registered_Functions;

    public TInformation_Extraction_Function_Handler() 
    {
    	this.Registered_Functions = new HashMap<String, Information_Extraction_Function>();
    }
    
    public void Register_Function(String Sensor, Information_Extraction_Function func) 
    {
        //if (belief_Type == null || belief_Type.trim().isEmpty() || func == null)
    	if ( Sensor == null || func == null)
        {
            throw new IllegalArgumentException("Invalid Sensor or function for registration.");
        }
        this.Registered_Functions.put( Sensor, func);
    }
    
    public boolean Unregister_Function(String Sensor) 
    {
        if ( Sensor == null )
        {
            return false;
        }
        return this.Registered_Functions.remove(Sensor) != null;
    }
    
    public ArrayList<TStimulus> Execute_Function_For_Raw_Data(
    		String Sensor, TPerception Perception, ArrayList<TBelief> Beliefs,
    		ArrayList<TRegion> Regions ) 
    {

    	ArrayList<TStimulus> Last_Stimuli = new ArrayList<TStimulus>();
    	ArrayList<TStimulus> Temp_Stimuli = new ArrayList<TStimulus>();
    	
        if (Sensor == null) 
        {
            System.err.println("Error: Sensor provided for execution is null.");
            //return null;
            return Last_Stimuli;
            
        }

        Information_Extraction_Function func = this.Registered_Functions.get(Sensor);

        if (func != null) 
        {
        	Temp_Stimuli = func.apply( Perception, Beliefs, Regions );
        	if(Temp_Stimuli != null)
        	{
        		Last_Stimuli.addAll( Temp_Stimuli );
        		Temp_Stimuli.clear();
        	}
        	
            return Last_Stimuli;
        } 
        else 
        {
//        	System.out.println("\nWARN: No Sensor for Information Extraction Function registered for the Sensor Type: '" + Sensor + "'.");
//            return null;
        	return Last_Stimuli;
        }
    }

}
