import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.stream.Collectors;

import com.Catalina_Model.Catalina_V_0_3.TBelief;
import com.Catalina_Model.Catalina_V_0_3.TExecutive_Memory_Maintenance_Function;
import com.Catalina_Model.Catalina_V_0_3.TGW_Memory_Maintenance_Update_Belief_Function_Result;
import com.Catalina_Model.Catalina_V_0_3.TPerception;
import com.Catalina_Model.Catalina_V_0_3.TRegion;
import com.Catalina_Model.Catalina_V_0_3.TStimulus;

public class TFunctions_for_GW_Memory_Maintenance_Update_Belief_Function 
{
	public HashSet<String> Sensors;
	private Autonomous_Vehicle_Demo Demo;
	private TCommon_Functions Common_Functions;
	
	public TFunctions_for_GW_Memory_Maintenance_Update_Belief_Function( Autonomous_Vehicle_Demo demo )
	{
		this.Demo = demo;
		this.Common_Functions = demo.Common_Functions;
	}

	/**
	 * this Function Update "ST_Stimulus_Danger_on_the_Route" Stimulus in GW Memory Maintenance
	 * @param Stimulus
	 * @param Map_Beliefs
	 * @return
	 */
	public TGW_Memory_Maintenance_Update_Belief_Function_Result Update_Normal_Belief_from_BLTS_Stimulus_Danger_on_the_Route(TStimulus Stimulus, HashMap<String, TBelief> Map_Beliefs )
	{
		TGW_Memory_Maintenance_Update_Belief_Function_Result result = new TGW_Memory_Maintenance_Update_Belief_Function_Result();
		TBelief BL_Position_Route = Map_Beliefs.get("BL_Position_Route");
		Integer Route_Number = (Integer) BL_Position_Route.Get_Predicate().Get_Object_Complement();
		
		String Belief_Name = "BL_Route_Status_"+Route_Number.toString();
		TBelief BL_Route_Status = Map_Beliefs.get( Belief_Name);
		if(BL_Route_Status == null)
		{
			TBelief BL_Next_Position_Route = Map_Beliefs.get("BL_Next_Position_Route");
			Route_Number = (Integer) BL_Next_Position_Route.Get_Predicate().Get_Object_Complement();
			
			Belief_Name = "BL_Route_Status_"+Route_Number.toString();
			BL_Route_Status = Map_Beliefs.get( Belief_Name);
		}
		//I stop the travel of the AV
		BL_Route_Status.Get_Predicate().set_Object_Complement("Under_maintenance");
		
		TBelief BL_Position_City = Map_Beliefs.get("BL_Position_City");
		TBelief BL_Position_Step = Map_Beliefs.get("BL_Position_Step");
		Integer City_Number = (Integer) BL_Position_City.Get_Predicate().Get_Object_Complement();
		Integer Step_Number = (Integer) BL_Position_Step.Get_Predicate().Get_Object_Complement();
		
		TBelief ST_Stimulus_Danger_on_the_Route = Map_Beliefs.get("ST_Stimulus_Danger_on_the_Route");
		
		ArrayList<Integer> Stimulus_Data = new ArrayList<Integer>();
		Stimulus_Data.add( City_Number );
		Stimulus_Data.add( Route_Number );
		Stimulus_Data.add( Step_Number );
		
		ST_Stimulus_Danger_on_the_Route.Get_Predicate().Set_Subject(Stimulus_Data);
		
//		ArrayList<Object> Data = new ArrayList<Object>();
//		HashMap<String, Object> Beliefs_To_Change = new HashMap<String, Object>();
//		
		//Data to Update
//		Beliefs_To_Change.put(Belief_Name, new ArrayList<>(Arrays.asList("Object_Complement", "Red", "Me")));
//		Beliefs_To_Change.put("ST_Stimulus_Danger_on_the_Route", new ArrayList<>(Arrays.asList("Subject", Data, "Me")));
//		
//		Data.add( Beliefs_To_Change );
//		this.Global_Workspace.Update_Uninhibited_Conscious_Beliefs( 
//				Beliefs_To_Change);
		
//		Common_Functions.Print("Beliefs aggiornate!! :-)");
		result.Set_Result( true );
		return result;
	}
	
	public void Add_Function_To_GW_Memory_Maintenance_Function(TExecutive_Memory_Maintenance_Function MMF)
	{
		MMF.Register_Update_Beliefs_Function("BLTS_Stimulus_Danger_on_the_Route", this::Update_Normal_Belief_from_BLTS_Stimulus_Danger_on_the_Route);
		
	}
	
	
}
