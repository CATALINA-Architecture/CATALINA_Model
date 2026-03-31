package com.Catalina_Model.Catalina_V_0_3;

import java.util.ArrayList;
import java.util.HashMap;

public class TPerception_Processing_Sensor_Function_Handler 
{
	
	@FunctionalInterface
    public interface Memory_Maintenance_Sensor_Function {
        ArrayList<TPerception> apply(String Sensor);
    }
	
	private final HashMap<String, Memory_Maintenance_Sensor_Function> Registered_Functions;

    public TPerception_Processing_Sensor_Function_Handler() 
    {
    	this.Registered_Functions = new HashMap<String, TPerception_Processing_Sensor_Function_Handler.Memory_Maintenance_Sensor_Function>();
    }
    
    public void Register_Function(String Sensor, Memory_Maintenance_Sensor_Function func) 
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
    
    public ArrayList<TPerception> Execute_Function_For_Sensor(
    		String Sensor) 
    {
    	ArrayList<TPerception> Last_Perceptions = new ArrayList<TPerception>();
    	ArrayList<TPerception> Temp_Perceptions = new ArrayList<TPerception>();
    	
        if (Sensor == null) 
        {
            System.err.println("Error: Sensor provided for execution is null.");
            //return null;
            return Last_Perceptions;
        }

        Memory_Maintenance_Sensor_Function func = this.Registered_Functions.get(Sensor);

        if (func != null) 
        {
        	Temp_Perceptions = func.apply(Sensor);
        	if (Temp_Perceptions != null)
        	{
	        	Last_Perceptions.addAll( Temp_Perceptions );
	        	for(TPerception Perception: Last_Perceptions)
	        	{
	        		Perception.Set_Source( Sensor );
	        	}
	        	Temp_Perceptions.clear();
        	}

        	/**
        	 *  I want to be sure that the Sensor is the same for Information
        	 *  Extraction handler when it has to analyze the raw data
        	 */
        	
//        	return func.apply(Sensor);
        	return Last_Perceptions;
        } 
        else 
        {
//        	System.out.println("\nWARN: No Memory_Maintenance Sensor Function registered for the Sensor Type: '" + Sensor + "'.");
            //return null;
        	return Last_Perceptions;
        }
    }

}
