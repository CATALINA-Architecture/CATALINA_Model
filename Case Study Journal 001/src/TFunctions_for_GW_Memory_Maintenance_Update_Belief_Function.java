import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.stream.Collectors;

import com.Catalina_Model.Catalina_V_0_3.TBelief;
import com.Catalina_Model.Catalina_V_0_3.TExecutive_Memory_Maintenance_Function;
import com.Catalina_Model.Catalina_V_0_3.TGW_Memory_Maintenance_Update_Belief_Function_Result;
import com.Catalina_Model.Catalina_V_0_3.TPerception;
import com.Catalina_Model.Catalina_V_0_3.TPredicate;
import com.Catalina_Model.Catalina_V_0_3.TRegion;
import com.Catalina_Model.Catalina_V_0_3.TStimulus;
import com.Catalina_Model.Catalina_V_0_3.TGlobal_Workspace.TIRecall_Beliefs;

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
	public TGW_Memory_Maintenance_Update_Belief_Function_Result Update_Normal_Belief_from_BLTS_Stimulus_Danger_on_the_Route(TStimulus Stimulus, 
			HashMap<String, TBelief> Map_Beliefs, TIRecall_Beliefs Recall_Belief )
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
	
	public TGW_Memory_Maintenance_Update_Belief_Function_Result Update_Normal_Beliefs_from_Danger_Data_from_TCS(TStimulus Stimulus, 
			HashMap<String, TBelief> Map_Beliefs, TIRecall_Beliefs Recall_Belief)
	{
		TGW_Memory_Maintenance_Update_Belief_Function_Result result = new TGW_Memory_Maintenance_Update_Belief_Function_Result();
//		TStimulus Stimulus = (TStimulus) Map_Uninhibited_Beliefs.get( "ST_Stimulus_Danger_Data_from_TCS" );
		TPredicate Predicate = Stimulus.Get_Predicate();
		
		
		
		
		/**
		 * Danger_Data Index:
		 * 0 - Information = "Danger_Data_Acquired!"
		 * 1 - City_position
		 * 2 - Route_position
		 * 3 - Step_position
		 * 4 - Dangeroues_Route_position
		 * 5 - Type_Danger.ordinal()
		 * 6 - Duration
		 */
		TPerception Perception = (TPerception) Predicate.Get_Object_Complement();
				
		ArrayList<Object> Perceived_Data = Perception.Get_Perceived_Data();
		
		Integer City_position =  (Integer) Perceived_Data.get(1);
		Integer Route_position =  (Integer) Perceived_Data.get(2);
		Integer Step_position =  (Integer) Perceived_Data.get(3);
		Integer Dangeroues_Route_position =  (Integer) Perceived_Data.get(4);
		
		Integer Duration =  (Integer) Perceived_Data.get(6);
		
		
		/**
		 * I Recall some beliefs
		 */
		HashSet<String> Beliefs_Names = new HashSet<String>();
		Beliefs_Names.add("BL_Danger_on_the_Route");
		Beliefs_Names.add("BL_Route_Status_"+Dangeroues_Route_position);
		Beliefs_Names.add("BL_Dangerous_Position_Route");
		Beliefs_Names.add("BL_Come_Back_to_City");
		Beliefs_Names.add("BL_Current_Time");
		Beliefs_Names.add("BL_Temporary_Closed_Routes");
		Beliefs_Names.add("BL_Temporary_Closed_Duration");
		
		HashMap<String, TBelief> Map_Beliefs_Recalled = new HashMap<String, TBelief>();
		
		Map_Beliefs_Recalled.putAll( Recall_Belief.apply(Beliefs_Names));
		
		
		Map_Beliefs_Recalled.putAll(Map_Beliefs);
		
		
		
		TType_Danger Type_Danger = TType_Danger.values()[ (Integer) Perceived_Data.get(5) ];
		
		
		
		HashMap<String, Object> Beliefs_To_Change = new HashMap<String, Object>();
		
		Beliefs_To_Change.put("BL_Danger_on_the_Route", new ArrayList<>(Arrays.asList("Subject", Dangeroues_Route_position, "Me")));
		Beliefs_To_Change.put("BL_Danger_on_the_Route", new ArrayList<>(Arrays.asList("Object_Complement", Type_Danger, "Me")));
		Beliefs_To_Change.put("BL_Route_Status_"+Dangeroues_Route_position, new ArrayList<>(Arrays.asList("Object_Complement", "Under_maintenance", "Me")));
		Beliefs_To_Change.put("BL_Dangerous_Position_Route", new ArrayList<>(Arrays.asList("Object_Complement", Dangeroues_Route_position, "Me")));
		Beliefs_To_Change.put("BL_Come_Back_to_City", new ArrayList<>(Arrays.asList("Object_Complement", City_position, "Me")));
		Beliefs_To_Change.put("BL_Temporary_Closed_Duration", new ArrayList<>(Arrays.asList("Object_Complement", Duration, "Me")));
		
		/**
		 * 
		 * 
		 */
//		TBelief BL_Temporary_Closed_Routes = Map_Beliefs.get("BL_Temporary_Closed_Routes");
		TBelief BL_Temporary_Closed_Routes = Map_Beliefs_Recalled.get("BL_Temporary_Closed_Routes");
		
		
		HashMap<Integer, HashMap<Integer, Integer >>  
							Temporary_Closed_Routes = null;
		HashMap<Integer, Integer > Steps_Closed = null;
		if( BL_Temporary_Closed_Routes.Get_Predicate().Get_Object_Complement() 
				== null)
		{
			Temporary_Closed_Routes = new HashMap<Integer, HashMap<Integer,Integer>>();
			Steps_Closed = new HashMap<Integer, Integer>();
			Steps_Closed.put(Step_position, Duration);
			Temporary_Closed_Routes.put(Dangeroues_Route_position, Steps_Closed);
		}
		else
		{
			Temporary_Closed_Routes = (HashMap<Integer, HashMap<Integer, Integer >>)
					BL_Temporary_Closed_Routes.Get_Predicate().Get_Object_Complement();
			Steps_Closed = Temporary_Closed_Routes.get( Dangeroues_Route_position );
			if(Steps_Closed == null)
			{
				Steps_Closed = new HashMap<Integer, Integer>();
			}
			Steps_Closed.put(Step_position, Duration);
			Temporary_Closed_Routes.put(Dangeroues_Route_position, Steps_Closed);
		}
		Beliefs_To_Change.put("BL_Temporary_Closed_Routes", new ArrayList<>(Arrays.asList("Object_Complement", Temporary_Closed_Routes, "Me")));
		
		/**
		 * 
		 * 
		 */
		
		
		/**
		 * I update the predicate "PR_Temporary_Closed_Routes"
		 */
//		this.Common_Functions.Print_Colored_Text( Map_Beliefs.toString(), 7);
//		TBelief Belief_Route_Status = Map_Beliefs.get("BL_Temporary_Closed_Routes");
		TBelief Belief_Route_Status = Map_Beliefs_Recalled.get("BL_Temporary_Closed_Routes");
		
		if( Belief_Route_Status != null)
		{
			/**
			 * Predicate Format:
			 * ["Temporary Closed Routes", is, HashMap<int Route, HashMap<int Step, int duration>>].
			 */
			TPredicate Predicate_Belief_Route_Status = Belief_Route_Status.Get_Predicate();
			HashMap<Integer , HashMap<Integer , Integer >> Map_Temporary_Closed_Routes = null;

			//Object_Complement: HashMap<int Route, HashMap<int Step, int duration>>
			Map_Temporary_Closed_Routes = 
					(HashMap<Integer , HashMap<Integer , Integer >>) 
							Predicate_Belief_Route_Status.Get_Object_Complement();
			
			if ( Map_Temporary_Closed_Routes == null)
			{
				Map_Temporary_Closed_Routes = new HashMap<Integer, HashMap<Integer,Integer>>();
			}
			HashMap<Integer , Integer > List_Steps = null;
			Boolean No_Route = false;
			Boolean No_Step = false;
			List_Steps = Map_Temporary_Closed_Routes.get( Dangeroues_Route_position );
			if ( List_Steps == null)
			{
				List_Steps = new HashMap<Integer,Integer>();
				No_Route = true;
			}
			Integer Step_Duration = List_Steps.get( Step_position );
			if ( List_Steps == null)
			{
				List_Steps = new HashMap<Integer,Integer>();
				No_Step = true;
			}
			List_Steps.put( Step_position , Duration);
			Map_Temporary_Closed_Routes.put( Dangeroues_Route_position, List_Steps);
			Beliefs_To_Change.put("BL_Temporary_Closed_Routes", new ArrayList<>(Arrays.asList("Object_Complement", Map_Temporary_Closed_Routes)));
		}
		else
		{
			this.Common_Functions.Print_Colored_Text("The BL_Danger_on_the_Route is not Uninhibited!",2);
//			this.Demo.End_Simulation();
		}
		
		result.Set_Beliefs_To_Change( Beliefs_To_Change );
		result.Set_Result( true );
		
		return result;
	}
	
	
	public TGW_Memory_Maintenance_Update_Belief_Function_Result 
			Update_Normal_Belief_from_BLTS_Stimulus_Low_Fuel(TStimulus Stimulus, 
			HashMap<String, TBelief> Map_Beliefs, TIRecall_Beliefs Recall_Belief)
	{
		TGW_Memory_Maintenance_Update_Belief_Function_Result result = new TGW_Memory_Maintenance_Update_Belief_Function_Result();
		
		
		TBelief BL_Fuel_Level = Map_Beliefs.get("BL_Fuel_Level");
		TBelief BL_Low_Fuel_Level = Map_Beliefs.get("BL_Low_Fuel_Level");
		
		
		TBelief BL_Low_Fuel_Level_Warning = Map_Beliefs.get("BL_Low_Fuel_Level_Warning");
		
		Integer Current_Fuel_Level = (Integer) BL_Low_Fuel_Level_Warning.Get_Predicate().Get_Object_Complement();
		
		
		HashMap<String, Object> Beliefs_To_Change = new HashMap<String, Object>();
		
		Beliefs_To_Change.put("BL_Fuel_Level", new ArrayList<>(Arrays.asList("Object_Complement", 
										Current_Fuel_Level, "Me")));
		
		Beliefs_To_Change.put("BL_Low_Fuel_Level", new ArrayList<>(Arrays.asList("Object_Complement", 
				BL_Low_Fuel_Level_Warning, "Me")));
		
		result.Set_Beliefs_To_Change( Beliefs_To_Change );
		result.Set_Result( true );
		return result;
		
		
		
	}
	
	public void Add_Function_To_GW_Memory_Maintenance_Function(TExecutive_Memory_Maintenance_Function MMF)
	{
										      
		MMF.Register_Update_Beliefs_Function("BLTS_Stimulus_Danger_on_the_Route", this::Update_Normal_Belief_from_BLTS_Stimulus_Danger_on_the_Route);
		MMF.Register_Update_Beliefs_Function("BLTS_Stimulus_Danger_Data_from_TCS", this::Update_Normal_Beliefs_from_Danger_Data_from_TCS);
		MMF.Register_Update_Beliefs_Function("BLTS_Stimulus_Low_Fuel", this::Update_Normal_Belief_from_BLTS_Stimulus_Low_Fuel);
		
		
	}
	
	
}
