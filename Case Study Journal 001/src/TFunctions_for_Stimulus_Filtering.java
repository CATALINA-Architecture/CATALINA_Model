import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import com.Catalina_Model.Catalina_V_0_3.TBelief;
import com.Catalina_Model.Catalina_V_0_3.TExecutive_Memory_Maintenance_Function;
import com.Catalina_Model.Catalina_V_0_3.TExecutive_Perception_Function;
import com.Catalina_Model.Catalina_V_0_3.TPerception;
import com.Catalina_Model.Catalina_V_0_3.TRegion;
import com.Catalina_Model.Catalina_V_0_3.TStimulus;

public class TFunctions_for_Stimulus_Filtering 
{
	public HashSet<String> Sensors;
	private Autonomous_Vehicle_Demo Demo;
	private TCommon_Functions Common_Functions;
	
	public TFunctions_for_Stimulus_Filtering(Autonomous_Vehicle_Demo demo)
	{
		this.Demo = demo;
		this.Common_Functions = demo.Common_Functions;
	}
	
	public TStimulus Check_Danger_on_the_Road( TStimulus Stimulus, ArrayList<TRegion> Regions)
	{
		
		Stimulus.Update_Saliency( 0.9 );
		return Stimulus;
	}
	
	public TStimulus Check_Danger_Data_from_TCS( TStimulus Stimulus, ArrayList<TRegion> Regions)
	{
		
		Stimulus.Update_Saliency( 0.92 );
		return Stimulus;
	}
	
	public TStimulus Check_Low_Fuel_Level_Warning( TStimulus Stimulus, ArrayList<TRegion> Regions)
	{
		
		Stimulus.Update_Saliency( 0.9 );
		return Stimulus;
	}
	
	public void Add_Function_To_Stimulus_Filtering(TExecutive_Perception_Function MMF)
	{
		MMF.Load_Saliencies_for_Stimuli( this.Demo.Data_Directory );
		MMF.Register_Stimulus_Filtering_Function("BLTS_Stimulus_Danger_on_the_Route", this::Check_Danger_on_the_Road);
		MMF.Register_Stimulus_Filtering_Function("BLTS_Stimulus_Danger_Data_from_TCS", this::Check_Danger_Data_from_TCS);
		MMF.Register_Stimulus_Filtering_Function("BLTS_Stimulus_Low_Fuel", this::Check_Low_Fuel_Level_Warning);
	}

}
