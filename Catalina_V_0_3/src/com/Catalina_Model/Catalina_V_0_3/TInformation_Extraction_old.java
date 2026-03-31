package com.Catalina_Model.Catalina_V_0_3;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import com.Catalina_Model.Catalina_V_0_3.TInformation_Extraction_Function_Handler.Information_Extraction_Function;


/**
 * This class gets each perception acquired by Perception_Processing and analyze these perception
 * to extracts semantic information from raw data, generating stimuli (that means beliefs and 
 * their related saliency).
 * 
 * this class has a function handler to execute the associated function to a
 * specific Perception. This handler is an "Information_Extraction_Function_Handler".
 * To register a function, it needs to use "Register_Information_Extraction_Function", and
 * to unregister a function, it needs to use "Unregister_Information_Extraction_Function"
 * For each Perception, an associated function will be executed. The associated
 * function is associated to the Source of the Perception.
 * The Source of a Perception is a Sensor.
 * 
 * The function "Get_Last_Stimuli" gives last generated stimuli and clear the list
 * 
 */

public class TInformation_Extraction_old 
{
	private TExecutive_Memory_Maintenance_Function Parent;
	
	protected TGeneric_Protected_List<TStimulus> Last_Generated_Stimuli;
	private TInformation_Extraction_Function_Handler Information_Extraction_Function_Handler;
	
	public TInformation_Extraction_old(TExecutive_Memory_Maintenance_Function owner)
	{
		this.Parent = owner;
		this.Information_Extraction_Function_Handler = 
				new TInformation_Extraction_Function_Handler();
		
		this.Last_Generated_Stimuli = new TGeneric_Protected_List<TStimulus>();
	}
	
	
	public void Execute(ArrayList<TPerception> New_Perceptions, ArrayList<TBelief> Beliefs,
    		ArrayList<TRegion> Regions)
	{
		this.Last_Generated_Stimuli.Clear();
		for(TPerception Perception: New_Perceptions )
		{
			this.Last_Generated_Stimuli.Add_All
			(
				this.Information_Extraction_Function_Handler.
				Execute_Function_For_Raw_Data(Perception.Get_Source(), Perception, Beliefs, Regions)
			);
		}
	}
	
	public ArrayList<TStimulus> Get_Last_Stimuli()
	{
		return this.Last_Generated_Stimuli.Read_And_Clear();
	}
	
	public void Register_Information_Extraction_Function(String Sensor, Information_Extraction_Function func) 
    {
       this.Information_Extraction_Function_Handler.Register_Function(Sensor, func);
    }
    
    public boolean Unregister_Information_Extraction_Function(String Sensor) 
    {
        return this.Information_Extraction_Function_Handler.Unregister_Function(
        		Sensor);
    }
	
}
