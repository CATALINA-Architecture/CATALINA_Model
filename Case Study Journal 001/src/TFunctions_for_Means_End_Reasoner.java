import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import com.Catalina_Model.Catalina_V_0_3.IPlanner;
import com.Catalina_Model.Catalina_V_0_3.TAction;
import com.Catalina_Model.Catalina_V_0_3.TAttentional_Desire;
import com.Catalina_Model.Catalina_V_0_3.TBelief;
import com.Catalina_Model.Catalina_V_0_3.TEpistemic_Desire;
import com.Catalina_Model.Catalina_V_0_3.TExecutive_Reasoner_Function;
import com.Catalina_Model.Catalina_V_0_3.TIntention;
import com.Catalina_Model.Catalina_V_0_3.TMeans_End_Reasoner_Data_Getter;
import com.Catalina_Model.Catalina_V_0_3.TOption;
import com.Catalina_Model.Catalina_V_0_3.TPractical_Desire;
import com.Catalina_Model.Catalina_V_0_3.TPredicate;
import com.Catalina_Model.Catalina_V_0_3.TRegion;
import com.Catalina_Model.Catalina_V_0_3.TStimulus;
import com.Catalina_Model.Catalina_V_0_3.TType_Relationship;

public class TFunctions_for_Means_End_Reasoner {

	private Autonomous_Vehicle_Demo Demo;
	private TCommon_Functions Common_Functions;
	private TDesires_Executive_Funtions Desires_Executive_Funtions;

	public TFunctions_for_Means_End_Reasoner(Autonomous_Vehicle_Demo demo) {
		this.Demo = demo;
		this.Common_Functions = demo.Common_Functions;
		this.Desires_Executive_Funtions = new TDesires_Executive_Funtions();
	}

	public TDesires_Executive_Funtions Get_Desires_Executive_Funtions() {
		return this.Desires_Executive_Funtions;
	}

//	public ArrayList<TOption> Generate_Options_for_Practical_Desires( 
//			TPractical_Desire Practical_Desire, HashMap<String, TBelief> beliefs, 
//			HashMap<String, TRegion> regions, ArrayList<TIntention> intentions,
//			TMeans_End_Reasoner_Data_Getter Means_End_Reasoner_Data_Getter)
//	{
//				ArrayList<TOption> result = new ArrayList<TOption>();
//				switch(Practical_Desire.Get_Name())
//				{
//					case "PD_Visit_Paris":
//					case "PD_Visit_Frankfurt":
//					case "PD_Visit_Rome":
//						result.addAll
//						(
//							this.Means_End_For_Belief_Destination_City(
//									Practical_Desire, beliefs, Means_End_Reasoner_Data_Getter)
////							this.Means_End_For_Belief_Destination_City_Prova_1(
////									Practical_Desire, beliefs)
//							
//						);
//					break;
//					default:
//						this.Common_Functions.Print(
//								"No function to generate options for Practical Name: "+
//										Practical_Desire.Get_Name());
////						this.Demo.End_Simulation();
//				}
//				
//				return result;
//	}

	public ArrayList<TOption> Reasoner_Function_for_Stimulus_Danger_on_the_Route(TEpistemic_Desire epistemic_Desire,
			HashMap<String, TBelief> beliefs, HashMap<String, TRegion> regions, 
			ArrayList<TIntention> intentions, IPlanner Planner)
//								TMeans_End_Reasoner_Data_Getter Means_End_Reasoner_Data_Getter
	{
		/**
		 * In this method, We create an action plan with two actions: 
		 * 1) the action to acquire epistemic information, 
		 * 2) to decide whether to continue along the path or go back and 
		 *    raise the epistemic desire "Come_Back_to_City"
		 */
		ArrayList<TOption> result = new ArrayList<TOption>();

		TStimulus Stimulus = (TStimulus) epistemic_Desire.Get_Belief();
		TPredicate Predicate = Stimulus.Get_Predicate();

		Planner.Get_Plans(epistemic_Desire, null);
		// In this case, agent create one only option with two actions
		ArrayList<TOption> List_Options = new ArrayList<TOption>();

		ArrayList<TAction> List_Actions = new ArrayList<TAction>();
		
		/**
		 * First Action
		 */
		
		TAction First_Action = new TAction();
		String First_Function_To_Invoke = "Ask Danger Type on the road";
		First_Action.Set_Action_Name(First_Function_To_Invoke);

		/**
		 * Predicate Format: [ [Integer City, Integer, Route, Integer Step], is, String
		 * "Damaged"]
		 */
		First_Action.Get_Params().add(Predicate.Get_Subject());

		List_Actions.add(First_Action);
		
		/**
		 * Second Action
		 */
		
		TAction Second_Action = new TAction();
		String Second_Function_To_Invoke = "Decide_To_Continue_or_Not";
		Second_Action.Set_Action_Name(Second_Function_To_Invoke);

		List_Actions.add(Second_Action);
		
//		TOption An_Option = new TOption(List_Actions);
		TOption_Simulation An_Option = new TOption_Simulation(List_Actions, Predicate, null, null);
		List_Options.add(An_Option);

		result.add(An_Option);

		return result;
	}

	public void Add_Functions_To_Means_End_Reasoner(TExecutive_Reasoner_Function ERF)
	{
		/**
		 * Epistemic Desires You can associate a Means-End Reasoner function to an
		 * Epistemic Desire here (so you must to associate a Means-End Reasoner function
		 * to each type of Epistemic Desire) or you can associate the Means-End Reasoner
		 * function when you rise the Epistemic Desire in Exogenous Desire Promotion.
		 * You can choose
		 */
		this.Demo.Agent.Reasoner_Register_Epistemic_Function("BLTS_Stimulus_Danger_on_the_Route",
				this::Reasoner_Function_for_Stimulus_Danger_on_the_Route);
		/**
		 * Practical Desires
		 */
		this.Demo.Agent.Reasoner_Register_Practical_Function("Visit_Paris",
				this::Reasoner_Function_for_Destination_City);

		this.Demo.Agent.Reasoner_Register_Practical_Function("Visit_Frankfurt",
				this::Reasoner_Function_for_Destination_City);

		this.Demo.Agent.Reasoner_Register_Practical_Function("Visit_Rome",
				this::Reasoner_Function_for_Destination_City);
		
		this.Demo.Agent.Reasoner_Register_Practical_Function("Visit_Cadiz",
				this::Reasoner_Function_for_Destination_City);
		
////		Reasoner_Function_for_Refuel
//		this.Demo.Agent.Reasoner_Register_Practical_Function("Refuel",
//				this::Reasoner_Function_for_Refuel);

//		ERF.Register_Epistemic_Function("BLTS_Stimulus_Danger_on_the_Route", this::Compute_Options_for_Stimulus_Danger_on_the_Route);
//		ERF.Set_Generate_Options_for_Practical_Desire( this::Generate_Options_for_Practical_Desires );
	}

	public void Add_Beliefs_To_Means_End_Reasoner(TExecutive_Reasoner_Function ERF) {
		/**
		 * Epistemic Desires I inserted
		 */
//		this.Demo.Agent.Reasoner_Register_List_Beliefs_Names_for_Desires(
//				"BLTS_Stimulus_Danger_on_the_Route", 
//					);
//		this.Demo.Functions_for_Belief_Inhibition_Function.
//			Get_Desires_Beliefs_Functions().Reasoner_Beliefs_For_Destination_City(null)

		/**
		 * Practical Desires You can insert the list of beleifs for Means-End Resoner by
		 * using the tool or by inserting it here. In the tool you can save several
		 * lists and use them to insert one or more lists one time.
		 * 
		 */
//		for(TAttentional_Desire Attentional_Desire: this.Demo.Agent.Get_Current_Attentional_Desires())
//		{
//			
//			if(Attentional_Desire instanceof TPractical_Desire)
//			{
////				this.Demo.Agent.Reasoner_Register_Beliefs_Names_for_Desires(
////						Attentional_Desire.Get_Name(),
////						this.Demo.Functions_for_Belief_Inhibition_Function.
////							Get_Desires_Beliefs_Functions().
////								Reasoner_Beliefs_For_Destination_City( 
////										(TPractical_Desire) Attentional_Desire ));	
//				Attentional_Desire.Set_Beliefs_Name_for_Reasoner(
//						this.Demo.Functions_for_Belief_Inhibition_Function.
//						Get_Desires_Beliefs_Functions().
//							Reasoner_Beliefs_For_Destination_City( 
//									(TPractical_Desire) Attentional_Desire ) );
//			}
//			
//		}
	}

//	}


	public ArrayList<TOption> Reasoner_Function_for_Destination_City(
//				TPractical_Desire  Practical_Desire, HashMap<String, TBelief> Beliefs,
//				TMeans_End_Reasoner_Data_Getter Means_End_Reasoner_Data_Getter)
			TPractical_Desire Practical_Desire, HashMap<String, TBelief> Beliefs, HashMap<String, TRegion> Regions,
			ArrayList<TIntention> intentions, IPlanner Planner)// ,
//            TMeans_End_Reasoner_Data_Getter Means_End_Reasoner_Data_Getter)
	{
		/*
		 * Beliefs useful: BLT_Destination_City, BL_Position_City, BL_Map,
		 * PR_Next_Position_City, PR_Next_Position_Route, PR_Next_Position_Step
		 * PR_Position_City, PR_Position_Route, PR_Position_Step
		 * PR_Previous_Position_City, PR_Previous_Position_Route,
		 * PR_Previous_Position_Step
		 */
		// I get the beliefs of the final state to get the City Destination
		ArrayList<TBelief> Temp_Beliefs = new ArrayList<TBelief>();
		Temp_Beliefs.addAll(Practical_Desire.Get_Final_State().Get_Beliefs());

		// I get the Destination City to compute the path
		TBelief Belief_Destination = this.Common_Functions
				.Get_Array_Beliefs_by_Type(Temp_Beliefs, "BLT_Destination_City").getFirst();

		String City_Name = (String) Belief_Destination.Get_Predicate().Get_Object_Complement().toString();
		TCity Destination_City = TCity.valueOf(City_Name);

		// I get the Current City to compute the path
		TBelief BL_Position_City = Beliefs.get("BL_Position_City");
		Integer City_Number = (Integer) BL_Position_City.Get_Predicate().Get_Object_Complement();
		TCity Current_City = TCity.values()[City_Number];
//		Common_Functions.Print("Start city for " + Practical_Desire.Get_Name() + ": " + Current_City.toString());

		// I get the Map
		TBelief BL_Map = Beliefs.get("BL_Map");
		TEnvironment Map = (TEnvironment) BL_Map.Get_Predicate().Get_Object_Complement();

		// I get the Current Time
		TBelief BL_Current_Time = Beliefs.get("BL_Current_Time");
		LocalDateTime Current_Time = (LocalDateTime) BL_Current_Time.Get_Predicate().Get_Object_Complement();

		TBelief BL_Position_Route = Beliefs.get("BL_Position_Route");
		Integer Temp_Route_Number = (Integer) BL_Position_Route.Get_Predicate().Get_Object_Complement();

		TBelief BL_Position_Step = Beliefs.get("BL_Position_Step");
		Integer Temp_Step_Number = (Integer) BL_Position_Step.Get_Predicate().Get_Object_Complement();
		Integer Temp_Specular_Step_Number = 0;
		Integer Temp_Specular_Route_Number = 0;
		ArrayList<TPlan_Simulation> Paths = null;
		
		TBelief BL_Next_Position_Route = Beliefs.get("BL_Next_Position_Route");
		Integer Temp_Next_Route_Number = (Integer) BL_Next_Position_Route.Get_Predicate().Get_Object_Complement();
		
		
		Boolean Start_New_Travel = true;
//		if(Temp_Route_Number < 0)
		if (Temp_Step_Number == 0) 
//		if(Temp_Route_Number <0)
		{
			Temp_Step_Number = 0;
			Paths = Map.Find_All_Paths(Current_City, Destination_City);
		} else {
			Start_New_Travel = false;
			Temp_Specular_Step_Number = Map.Get_Specular_Step_in_Route(Temp_Route_Number, Temp_Step_Number);
			Temp_Specular_Route_Number = Map.Get_Specular_Route(Temp_Route_Number);
			Paths = Map.Find_All_Paths_From_A_Damaged_Route(Temp_Route_Number, Temp_Step_Number, Destination_City);
		}

		/*
		 * In this version we have only one Temporal Operator (the Finally temporal
		 * Operator), so I'm sure the Start and End Times are related to Finally
		 * Temporal Operator
		 */
		LocalDateTime Finally_Start_Time = Practical_Desire.Get_Final_State().Get_Temporal_Operator().Get_Start_Time();

		LocalDateTime Finally_End_Time = Practical_Desire.Get_Final_State().Get_Temporal_Operator().Get_End_Time();

		int Minute_of_Hours = 0;
		int Minutes = 0;
//		LocalDateTime Temp_Time = Actual_Time.plusMinutes(Minutes);

//		Paths = Finally_Operator(Paths, Current_Time, 
//					Finally_Start_Time, Finally_End_Time );

		ArrayList<TOption> Option_List = new ArrayList<TOption>();
		ArrayList<TAction> Plans = new ArrayList<TAction>();

//		TBelief BL_Position_Route = Beliefs.get("BL_Position_Route");
//		TBelief BL_Position_Step = Beliefs.get("BL_Position_Step");
		Boolean First_Action = true;

		int i = 0;
		for (TPlan_Simulation path : Paths) 
		{
			First_Action = true;

			ArrayList<TAction> Actions = new ArrayList<TAction>();

			// I Create plans for any options
			// For Any Route
			int Action_ID = 0;
			for (Integer Route_Number : path.Routes) {
				// Now, Action stores only a Route at a time
				// An Action => A step for a Route

				// Now, I create any action in plan option
				// I get the rounds time to go from Station A to Station B
				TRoute A_Route = Map.All_Routes.get(Route_Number);

				int Rounds_Time = A_Route.Get_Total_Rounds();

				// I get the correct Station
				TCity A_Departure_Station;
				TCity A_Destination_Station;
				TCity A_Destination_Station_in_PostCondition;

				A_Departure_Station = A_Route.Get_Departure();
				A_Destination_Station = A_Route.Get_Destination();
//				int Position_Start_Step = 0;
				int Position_Start_Step = Temp_Step_Number;

				int Position_Start_Route = 0;
				int Position_Start_City = 0;
				int Position_End_Step = 0;
				int Position_End_Route = 0;
				int Position_End_City = 0;

				int step_position = 0;
				int Start_route_position = Route_Number;
				int End_route_position = Route_Number;
				int Route_Speed = A_Route.Get_Route_Speed();
				int Steps_Number_on_Route = A_Route.Get_Steps_Number();
				Integer Other_Step = 0;
				if (!Start_New_Travel) {
					Other_Step = 1;
				}
				for (Integer Step = 1; Step <= Rounds_Time; Step++) {
					Other_Step++;
					TPosition_Coords Precondition_Position_AV_Coords;
					TPosition_Coords Postcondition_Position_AV_Coords;
					TPredicate Precondition_City;
					TPredicate Precondition_Route;
					TPredicate Precondition_Step;
					TPredicate Precondition_Route_Status;
					TPredicate Postcondition_City;
					TPredicate Postcondition_Route;
					TPredicate Postcondition_Step;
					TPredicate Postcondition_Route_Status;

					TBelief BL_Precondition_Route_Status;
					TBelief BL_Precondition_Specular_Route_Status;
					TBelief BL_Postcondition_Route_Status;
					TBelief BL_Postcondition_Specular_Route_Status;

					String Function_To_Invoke;

					// I set starting precondition data
//					if (Step == 1)
//					if ( Temp_Step_Number == 0)
					if (Other_Step == 1) {
						Function_To_Invoke = "GO_TO_Route";
						Start_route_position = -1;
						End_route_position = Route_Number;

						Position_Start_City = A_Departure_Station.ordinal();
						Position_Start_Route = -1;
						Position_Start_Step = 0;

						Position_End_City = A_Departure_Station.ordinal();
						Position_End_Route = Route_Number;
						Position_End_Step = Position_Start_Step + Route_Speed;
					} else {
						Function_To_Invoke = "GO_TO_Step";
						Start_route_position = Route_Number;

						Position_Start_City = A_Departure_Station.ordinal();
						Position_Start_Route = Route_Number;
						Position_Start_Step += Route_Speed;
						if ((Other_Step == 2) & (Start_New_Travel == false)) {
							if (Temp_Specular_Route_Number == Position_Start_Route) {
								Position_Start_Step = Temp_Specular_Step_Number;
							}

						}

						Position_End_City = A_Departure_Station.ordinal();
						Position_End_Route = Route_Number;
						Position_End_Step = Position_Start_Step + Route_Speed;
					}

					Precondition_Position_AV_Coords = new TPosition_Coords(A_Departure_Station.ordinal(),
							Start_route_position, step_position);

					step_position = step_position + A_Route.Get_Route_Speed();

					// I set ending precondition data
					A_Destination_Station_in_PostCondition = A_Departure_Station;

					if (step_position > A_Route.Get_Steps_Number()) {
						A_Destination_Station_in_PostCondition = A_Destination_Station;
						// If I arrive in next Station, I set the Route to -1 and the
						End_route_position = -1;
						step_position = 0;
					} else {
						End_route_position = Start_route_position;
					}

					if (Position_End_Step > Steps_Number_on_Route) {
						Position_End_City = A_Destination_Station.ordinal();
						Position_End_Route = -1;
						Position_End_Step = 0;
					} else {
						Position_End_Route = Route_Number;
					}

					Postcondition_Position_AV_Coords = new TPosition_Coords(
							A_Destination_Station_in_PostCondition.ordinal(), End_route_position, step_position);

					Precondition_City = new TPredicate("Position_City", BL_Position_City, TType_Relationship.is,
							Position_Start_City);
					Precondition_Route = new TPredicate("Position_Route", BL_Position_Route, TType_Relationship.is,
							Position_Start_Route);
					Precondition_Step = new TPredicate("Position_Step", BL_Position_Step, TType_Relationship.is,
							Position_Start_Step);

					Integer Route_Precondition = Precondition_Position_AV_Coords.Get_Route();
					BL_Precondition_Route_Status = Beliefs.get("BL_Route_Status_" + Position_Start_Route);

					Postcondition_City = new TPredicate(null, BL_Position_City, TType_Relationship.is,
							Position_End_City);
					Postcondition_Route = new TPredicate(null, BL_Position_Route, TType_Relationship.is,
							Position_End_Route);
					Postcondition_Step = new TPredicate(null, BL_Position_Step, TType_Relationship.is,
							Position_End_Step);

					Integer Route_Postcondition = Postcondition_Position_AV_Coords.Get_Route();
					BL_Postcondition_Route_Status = Beliefs.get("BL_Route_Status_" + Position_End_Route);

					Action_ID++;
					ArrayList<Object> Params = new ArrayList<Object>();
					if (Function_To_Invoke.equals("GO_TO_Route")) 
					{
						TAction An_Action = new TAction();
						/**
						 * In first action, I need to insert the preconditions.
						 * Tehy are useful to create a correct plan to insert in
						 * Plan Library
						 */
						
						An_Action.Set_Action_Name("Initialize_Way");
						An_Action.Set_ID(Action_ID);
						Params.add(Position_End_City);
						Params.add(Position_End_Route);
						Params.add(Position_End_Step);
						
						
						if(First_Action == true)
						{
							First_Action = false;
							TPredicate first_Precondition_City = new TPredicate("Position_City", BL_Position_City, TType_Relationship.is,
									Position_Start_City);
							TPredicate first_Precondition_Route = new TPredicate("Position_Route", BL_Position_Route, TType_Relationship.is,
									Position_Start_Route);
							TPredicate first_Precondition_Step = new TPredicate("Position_Step", BL_Position_Step, TType_Relationship.is,
									Position_Start_Step);
							An_Action.Add_Pre_condition(first_Precondition_City);
							An_Action.Add_Pre_condition(first_Precondition_Route);
							An_Action.Add_Pre_condition(first_Precondition_Step);

							Params.add(Practical_Desire.Get_Name());
							Params.add(Practical_Desire.Get_Final_State().
											Get_Temporal_Operator().Get_End_Time());
						}
						An_Action.Set_Params(Params);
						
						Actions.add(An_Action);

						Action_ID++;
						Params.clear();
					}
					TAction An_Action = new TAction();
					An_Action.Set_ID(Action_ID);
					Params.add(Route_Number);
					An_Action.Set_Params(Params);
					An_Action.Add_Pre_condition(Precondition_City);
					An_Action.Add_Pre_condition(Precondition_Route);
					An_Action.Add_Pre_condition(Precondition_Step);
					if (BL_Precondition_Route_Status != null) {
						Precondition_Route_Status = new TPredicate("BL_Route_Status_" + Position_Start_Route,
								BL_Precondition_Route_Status, TType_Relationship.is, "Green");
						An_Action.Add_Pre_condition(Precondition_Route_Status);
					}

					An_Action.Add_Post_condition(Postcondition_City);
					An_Action.Add_Post_condition(Postcondition_Route);
					An_Action.Add_Post_condition(Postcondition_Step);
					if (BL_Postcondition_Route_Status != null) {
						Postcondition_Route_Status = new TPredicate("BL_Route_Status_" + Position_End_Route,
								BL_Postcondition_Route_Status, TType_Relationship.is, "Green");
						An_Action.Add_Post_condition(Postcondition_Route_Status);
					}

					// I define "Use_Route" as a function to go from a departure to a destination
					// station
					An_Action.Set_Action_Name(Function_To_Invoke);
					Actions.add(An_Action);
				}
			}
			TOption_Simulation An_Option = new TOption_Simulation(Actions, null, 0.0, path.Total_Weights);
			An_Option.Path.Copy_Plan(path);

			// I update the time to satisfy the plan option
			Minute_of_Hours = path.Path_Time.intValue();
			Minutes = (int) ((path.Path_Time - Minute_of_Hours) * 60) + Minute_of_Hours * 60;
//			LocalDateTime Temp_Time = Current_Time.plusMinutes(Minutes);
			An_Option.Set_Satisfied_Time(Current_Time.plusMinutes(Minutes));
			
			An_Option.Get_Plan_Actions().getFirst().Get_Params().add( An_Option.Get_Satisfied_Time());

			Option_List.add(An_Option);

			i++;
		}
		return Option_List;
	}

	public ArrayList<TOption> Reasoner_Function_for_Come_Back_to_City
	(		TPractical_Desire Practical_Desire, HashMap<String, TBelief> Beliefs,
            HashMap<String, TRegion> Regions, ArrayList<TIntention> intentions, 
            IPlanner Planner)

	{
//		ArrayList<TBelief> Temp_Beliefs = new ArrayList<TBelief>();
//		Temp_Beliefs.addAll( Practical_Desire.Get_Final_State().Get_Beliefs());
//		
//		//I get the Destination City to compute the path
////		this.Common_Functions.Print(Temp_Beliefs.ke);
//		Temp_Beliefs.size();
//		TBelief BL_Come_Back_to_City = 
//				this.Common_Functions.Get_Array_Beliefs_by_Type(
//						Temp_Beliefs, "BLT_Come_Back_to_City").getFirst();
		
		
		
		//I get the Current City to compute the path
		TBelief BL_Position_City = Beliefs.get("BL_Position_City");
		
		Integer City_Position = (Integer) BL_Position_City.Get_Predicate()
				.Get_Object_Complement();
		TCity City_Name = TCity.values()[City_Position];
//		String City_Name = (String) BL_Come_Back_to_City.Get_Predicate()
//				.Get_Object_Complement().toString();
		TCity Destination_City = TCity.values()[City_Position];
		
		
		
		Integer City_Number = (Integer) BL_Position_City.Get_Predicate()
				.Get_Object_Complement();
		TCity Current_City = TCity.values()[City_Number];
//		Common_Functions.Print("Start city for "+Practical_Desire.Get_Name()+": "+Current_City.toString());
		
		//I get the Map 
		TBelief BL_Map = Beliefs.get("BL_Map");
		TEnvironment Map = (TEnvironment) BL_Map.Get_Predicate().Get_Object_Complement();
		
		//I get the Current Time
		TBelief BL_Current_Time = Beliefs.get("BL_Current_Time");
		LocalDateTime Current_Time = (LocalDateTime) BL_Current_Time.Get_Predicate()
				.Get_Object_Complement();
		
		TBelief BL_Position_Route = Beliefs.get("BL_Position_Route");
		Integer Temp_Route_Number = (Integer) BL_Position_Route.Get_Predicate()
				.Get_Object_Complement();
		
		TBelief BL_Position_Step = Beliefs.get("BL_Position_Step");
		Integer Temp_Step_Number = (Integer) BL_Position_Step.Get_Predicate()
				.Get_Object_Complement();
		
		
		Integer Specular_Route_Number = Map.Get_Specular_Route( Temp_Route_Number );
		Integer Specular_Step_Number = Map.Get_Specular_Step_in_Route( 
												Temp_Route_Number, Temp_Step_Number );
		
		ArrayList<TPlan_Simulation> Paths = null;
		Boolean Start_New_Travel = true;
		
		LocalDateTime Finally_Start_Time = 
				Practical_Desire.Get_Final_State().
					Get_Temporal_Operator().Get_Start_Time();
		
		LocalDateTime Finally_End_Time = 
				Practical_Desire.Get_Final_State().
					Get_Temporal_Operator().Get_End_Time();
		
		int Minute_of_Hours = 0;
		int Minutes = 0;
//		LocalDateTime Temp_Time = Actual_Time.plusMinutes(Minutes);
		
//		Paths = Finally_Operator(Paths, Current_Time, 
//					Finally_Start_Time, Finally_End_Time );
		
		ArrayList<TOption> Option_List =  new ArrayList<TOption>();
		ArrayList<TAction> Plans = new ArrayList<TAction>();
		

		//Now, Action stores only a Route at a time
		// An Action => A step for a Route

		//Now, I create any action in plan option
		//I get the rounds time to go from Station A to Station B
		if(Temp_Route_Number == -1)
		{
			Temp_Route_Number = -1;
		}
//		System.out.println("Temp_Route_Number: "+Temp_Route_Number);
		TRoute A_Route = Map.All_Routes.get(Temp_Route_Number);
		
		
		int Route_Speed = A_Route.Get_Route_Speed();
		int Steps_Number_on_Route = A_Route.Get_Steps_Number();
		
		//Total actions + the action to reverse the direction of the route
		int Rounds_Time = (int) Math.ceil( 
				(double) ( (Steps_Number_on_Route - Temp_Step_Number )) / Route_Speed );
//		 
		
		//I get the correct Station
		TCity A_Departure_Station;
		TCity A_Destination_Station;
		TCity A_Destination_Station_in_PostCondition;

		A_Departure_Station = City_Name; //A_Route.Get_Departure();
		A_Destination_Station = City_Name; //A_Route.Get_Destination();
		
		int Position_Start_Step = Temp_Step_Number;
		int Position_Start_Route = Temp_Route_Number;
		int Position_Start_City = City_Number;
		
		int Position_End_Step = Specular_Step_Number;
		int Position_End_Route = Specular_Route_Number;
		int Position_End_City = City_Number;
		
		int Action_ID = 0;
		ArrayList<TAction> Actions = new ArrayList<TAction>();
//		int Route_Number = Specular_Route_Number;
		
		// I create the first action to reverse the direction of the route.
		{
			TPredicate Precondition_City;
			TPredicate Precondition_Route;
			TPredicate Precondition_Step;
			TPredicate Precondition_Route_Status;
			TPredicate Postcondition_City;
			TPredicate Postcondition_Route;
			TPredicate Postcondition_Step;
			String Function_To_Invoke = "Reverse_Direction";
			
			// PRECONDITIONS
			Precondition_City = new TPredicate("Position_City", BL_Position_City, 
					TType_Relationship.is, Position_Start_City);
			Precondition_Route = new TPredicate("Position_Route", BL_Position_Route, 
					TType_Relationship.is, Position_Start_Route);
			Precondition_Step = new TPredicate("Position_Step", BL_Position_Step, 
					TType_Relationship.is, Position_Start_Step);
			
			// POSTCONDITIONS
			Postcondition_City = new TPredicate(null, BL_Position_City, 
					TType_Relationship.is, Position_End_City);
			Postcondition_Route = new TPredicate(null, BL_Position_Route, 
					TType_Relationship.is, Position_End_Route);
			Postcondition_Step = new TPredicate(null, BL_Position_Step, 
					TType_Relationship.is, Position_End_Step);
			
			ArrayList<Object> Params = new ArrayList<Object>();
			
			Action_ID++;
			
			TAction An_Action = new TAction();
			An_Action.Set_Action_Name( Function_To_Invoke );
			An_Action.Set_ID( Action_ID);
			
			Params.add( Specular_Route_Number );
			Params.add(Position_End_City);
			Params.add(Position_End_Route);
			Params.add(Position_End_Step);
			
			An_Action.Set_Params( Params );
			An_Action.Add_Pre_condition(Precondition_City);
			An_Action.Add_Pre_condition(Precondition_Route);
			An_Action.Add_Pre_condition(Precondition_Step);
			
			An_Action.Add_Post_condition(Postcondition_City);
			An_Action.Add_Post_condition(Postcondition_Route);
			An_Action.Add_Post_condition(Postcondition_Step);
			
			Actions.add(An_Action);
		}
		

		int step_position = Position_End_Step;
		int Start_route_position = Position_End_Route;
		int End_route_position = -1;
		
		Integer Other_Step = 0;
		if (!Start_New_Travel)
		{
			Other_Step =1;
		}
		//this line is useful to adjust the calculi
		Position_Start_Step = Specular_Step_Number - Route_Speed;
	 	for(Integer Step = 1; Step <= Rounds_Time; Step++)
		{
	 		Other_Step++;
			TPosition_Coords Precondition_Position_AV_Coords;
			TPosition_Coords Postcondition_Position_AV_Coords;
			TPredicate Precondition_City;
			TPredicate Precondition_Route;
			TPredicate Precondition_Step;
			TPredicate Precondition_Route_Status;
			TPredicate Postcondition_City;
			TPredicate Postcondition_Route;
			TPredicate Postcondition_Step;
			TPredicate Postcondition_Route_Status;

//			TBelief BL_Precondition_Route_Status;
//			TBelief BL_Precondition_Specular_Route_Status;
//			TBelief BL_Postcondition_Route_Status;
//			TBelief BL_Postcondition_Specular_Route_Status;
			
			String Function_To_Invoke;
			
			//First I reverse the direction of the AV
			{
				Function_To_Invoke = "GO_TO_Step";
				Start_route_position = Position_End_Route;
				
				Position_Start_City = City_Position;
				Position_Start_Route = Specular_Route_Number;
				Position_Start_Step += Route_Speed;
//				if ((Other_Step == 2) & (Start_New_Travel == false))
//				{
//					if (Specular_Route_Number == Position_Start_Route)
//					{
//						Position_Start_Step = Specular_Step_Number;
//					}
//					
//				}
				
				Position_End_City = City_Position;
				Position_End_Route = Specular_Route_Number;
				Position_End_Step = Position_Start_Step + Route_Speed;
			}
			
			Precondition_Position_AV_Coords = new TPosition_Coords( City_Position, 
					Start_route_position, step_position);
			
			step_position = step_position + A_Route.Get_Route_Speed();
			
			//I set ending precondition data
			A_Destination_Station_in_PostCondition = A_Departure_Station;
			
			if (step_position > A_Route.Get_Steps_Number())
			{
				A_Destination_Station_in_PostCondition = A_Destination_Station;
				//If I arrive in next Station, I set the Route to -1 and the
				End_route_position = -1;
				step_position = 0;
			}
			else
			{
				End_route_position = Start_route_position;
			}
			
			if( Position_End_Step > Steps_Number_on_Route)
			{
				Position_End_City = City_Position;
				Position_End_Route = -1;
				Position_End_Step = 0;
			}
			else
			{
				Position_End_Route = Specular_Route_Number;
			}

			Postcondition_Position_AV_Coords = new TPosition_Coords(
					A_Destination_Station_in_PostCondition.ordinal(), End_route_position, step_position);
			
			Precondition_City = new TPredicate("Position_City", BL_Position_City, 
					TType_Relationship.is, Position_Start_City);
			Precondition_Route = new TPredicate("Position_Route", BL_Position_Route, 
					TType_Relationship.is, Position_Start_Route);
			Precondition_Step = new TPredicate("Position_Step", BL_Position_Step, 
					TType_Relationship.is, Position_Start_Step);
			
			Integer Route_Precondition = Precondition_Position_AV_Coords.Get_Route();
//			BL_Precondition_Route_Status = Beliefs.get("BL_Route_Status_"+ Position_Start_Route);
			
			Postcondition_City = new TPredicate(null, BL_Position_City, 
					TType_Relationship.is, Position_End_City);
			Postcondition_Route = new TPredicate(null, BL_Position_Route, 
					TType_Relationship.is, Position_End_Route);
			Postcondition_Step = new TPredicate(null, BL_Position_Step, 
					TType_Relationship.is, Position_End_Step);
			
			Integer Route_Postcondition = Postcondition_Position_AV_Coords.Get_Route();
//			BL_Postcondition_Route_Status = Beliefs.get("BL_Route_Status_"+Position_End_Route);
			
			Action_ID++;
			ArrayList<Object> Params = new ArrayList<Object>();
//			if(Function_To_Invoke.equals("GO_TO_Route"))
//			{
//				TAction An_Action = new TAction();
//				An_Action.Set_Action_Name("Initialize_Way");
//				An_Action.Set_ID( Action_ID);
//				Params.add(Position_End_City);
//				Params.add(Position_End_Route);
//				Params.add(Position_End_Step);
//				An_Action.Set_Params( Params );
//				Actions.add(An_Action);
//				
//				Action_ID++;
//				Params.clear();
//			}
			TAction An_Action = new TAction();
			An_Action.Set_ID( Action_ID);
			Params.add(Specular_Route_Number);
//			Params.add(Position_End_City);
//			Params.add(Position_End_Route);
//			Params.add(Position_End_Step);
			
			An_Action.Set_Params( Params );
			An_Action.Add_Pre_condition(Precondition_City);
			An_Action.Add_Pre_condition(Precondition_Route);
			An_Action.Add_Pre_condition(Precondition_Step);
//			if( BL_Precondition_Route_Status != null)
//			{
//				Precondition_Route_Status = new TPredicate(
//						"BL_Route_Status_"+Position_Start_Route, BL_Precondition_Route_Status, 
//						TType_Relationship.is, "Green");
//				An_Action.Add_Pre_condition(Precondition_Route_Status);
//			}
			
			An_Action.Add_Post_condition(Postcondition_City);
			An_Action.Add_Post_condition(Postcondition_Route);
			An_Action.Add_Post_condition(Postcondition_Step);
//			if( BL_Postcondition_Route_Status != null)
//			{
//				Postcondition_Route_Status = new TPredicate(
//						"BL_Route_Status_"+Position_End_Route, BL_Postcondition_Route_Status, 
//						TType_Relationship.is, "Green");
//				An_Action.Add_Post_condition(Postcondition_Route_Status);
//			}
			
			// I define "Use_Route" as a function to go from a departure to a destination station
			An_Action.Set_Action_Name(Function_To_Invoke);
			Actions.add(An_Action);
		}
	 	
//	}
		TPlan_Simulation plan = new TPlan_Simulation();
		
		EnumMap<TType_Quality_Desire, Double> quality_List = new EnumMap<>(TType_Quality_Desire.class);
		
		quality_List.put(TType_Quality_Desire.Motor, A_Route.Get_Route_Locomotive() );
		quality_List.put(TType_Quality_Desire.Panorama, A_Route.Get_Route_Panorama() );
		quality_List.put(TType_Quality_Desire.Speed, (double) A_Route.Get_Route_Speed() );
		
		TOption_Simulation An_Option = new TOption_Simulation(Actions, null, 0.0, quality_List);
		List<TCity> Destionations = new ArrayList<>();
		Destionations.add( City_Name );
		
		List<Integer> Numbered_Route = new ArrayList<>();
		Numbered_Route.add( Specular_Route_Number );
		Double path_Time = (double) Rounds_Time;
	
		plan.Insert_Path_by_Routes(Destionations, Numbered_Route, quality_List, path_Time);
		
		
		An_Option.Path.Copy_Plan(plan);
		
		// I update the time to satisfy the plan option
		Minute_of_Hours = plan.Path_Time.intValue();
		Minutes = (int)(Minute_of_Hours*60);
		
	//	LocalDateTime Temp_Time = Current_Time.plusMinutes(Minutes);
		An_Option.Set_Satisfied_Time( Current_Time.plusMinutes(Minutes) );
		
		Option_List.add(An_Option);
	
		return Option_List;
	}
	
	public ArrayList<TOption> Reasoner_Function_for_Refuel
	(		TPractical_Desire Practical_Desire, HashMap<String, TBelief> Beliefs,
            HashMap<String, TRegion> Regions, ArrayList<TIntention> intentions, 
            IPlanner Planner)

	{
//		ArrayList<TBelief> Temp_Beliefs = new ArrayList<TBelief>();
//		Temp_Beliefs.addAll( Practical_Desire.Get_Final_State().Get_Beliefs());
//		
//		//I get the Destination City to compute the path
////		this.Common_Functions.Print(Temp_Beliefs.ke);
//		Temp_Beliefs.size();
//		TBelief BL_Come_Back_to_City = 
//				this.Common_Functions.Get_Array_Beliefs_by_Type(
//						Temp_Beliefs, "BLT_Come_Back_to_City").getFirst();
		
		
		
		//I get the Current City to compute the path
		TBelief BL_Position_City = Beliefs.get("BL_Position_City");
		
		Integer City_Position = (Integer) BL_Position_City.Get_Predicate()
				.Get_Object_Complement();
		TCity City_Name = TCity.values()[City_Position];
//		String City_Name = (String) BL_Come_Back_to_City.Get_Predicate()
//				.Get_Object_Complement().toString();
		TCity Destination_City = TCity.values()[City_Position];
		
		
		
		Integer City_Number = (Integer) BL_Position_City.Get_Predicate()
				.Get_Object_Complement();
		TCity Current_City = TCity.values()[City_Number];
//		Common_Functions.Print("Start city for "+Practical_Desire.Get_Name()+": "+Current_City.toString());
		
		//I get the Map 
		TBelief BL_Map = Beliefs.get("BL_Map");
		TEnvironment Map = (TEnvironment) BL_Map.Get_Predicate().Get_Object_Complement();
		
		//I get the Current Time
		TBelief BL_Current_Time = Beliefs.get("BL_Current_Time");
		LocalDateTime Current_Time = (LocalDateTime) BL_Current_Time.Get_Predicate()
				.Get_Object_Complement();
		
		TBelief BL_Position_Route = Beliefs.get("BL_Position_Route");
		Integer Temp_Route_Number = (Integer) BL_Position_Route.Get_Predicate()
				.Get_Object_Complement();
		
		TBelief BL_Position_Step = Beliefs.get("BL_Position_Step");
		Integer Original_Step_Number = (Integer) BL_Position_Step.Get_Predicate()
				.Get_Object_Complement();
		Integer Temp_Step_Number = Original_Step_Number;
		
		TBelief BL_Next_Position_Step = Beliefs.get("BL_Next_Position_Step");
		Integer Temp_Next_Step_Number = (Integer) BL_Next_Position_Step.Get_Predicate()
				.Get_Object_Complement();
;		Temp_Step_Number = Temp_Next_Step_Number;
		
		TBelief BL_Next_Position_Route = Beliefs.get("BL_Next_Position_Route");
		Integer Temp_Next_Route_Number = (Integer) BL_Next_Position_Route.Get_Predicate()
				.Get_Object_Complement();
		
		TBelief BL_Next_Position_City = Beliefs.get("BL_Next_Position_City");
		Integer Temp_Next_City_Number = (Integer) BL_Next_Position_City.Get_Predicate()
				.Get_Object_Complement();

		Integer Specular_Route_Number = Map.Get_Specular_Route( Temp_Route_Number );
		Integer Specular_Step_Number = Map.Get_Specular_Step_in_Route( 
												Temp_Route_Number, Temp_Step_Number );
		
		ArrayList<TPlan_Simulation> Paths = null;
		Boolean Start_New_Travel = true;
		
		LocalDateTime Finally_Start_Time = 
				Practical_Desire.Get_Final_State().
					Get_Temporal_Operator().Get_Start_Time();
		
		LocalDateTime Finally_End_Time = 
				Practical_Desire.Get_Final_State().
					Get_Temporal_Operator().Get_End_Time();
		
		int Minute_of_Hours = 0;
		int Minutes = 0;
		int Time = 0;
//		LocalDateTime Temp_Time = Actual_Time.plusMinutes(Minutes);
		
//		Paths = Finally_Operator(Paths, Current_Time, 
//					Finally_Start_Time, Finally_End_Time );
		
		ArrayList<TOption> Option_List =  new ArrayList<TOption>();
		ArrayList<TAction> Plans = new ArrayList<TAction>();
		

		//Now, Action stores only a Route at a time
		// An Action => A step for a Route

		//Now, I create any action in plan option
		//I get the rounds time to go from Station A to Station B
		if(Temp_Route_Number == -1)
		{
			Temp_Route_Number = -1;
		}
//		System.out.println("Temp_♣Route_Number: "+Temp_Route_Number);
		if(Temp_Route_Number == -1)
		{
			Temp_Route_Number = (Integer) BL_Next_Position_Route.Get_Predicate()
					.Get_Object_Complement();	
		}
		TRoute A_Route = Map.All_Routes.get(Temp_Route_Number);
		
		
		int Route_Speed = A_Route.Get_Route_Speed();
		int Steps_Number_on_Route = A_Route.Get_Steps_Number();
		
		//Total actions + the action to reverse the direction of the route
		int Rounds_Time = (int) Math.ceil( 
				(double) ( (Steps_Number_on_Route - Temp_Step_Number )) / Route_Speed );
		
		double middle_on_route = (double) ((double)Steps_Number_on_Route/(double)2);
		Boolean Make_inversion = true;
		if((double)Temp_Step_Number >= middle_on_route)
		{
			Make_inversion = false;
		}
//		 
		
		//I get the correct Station
		TCity A_Departure_Station;
		TCity A_Destination_Station;
		TCity A_Destination_Station_in_PostCondition;

		A_Departure_Station = City_Name; //A_Route.Get_Departure();
		A_Destination_Station = City_Name; //A_Route.Get_Destination();
		
		int Position_Start_Step = Temp_Step_Number;
		int Position_Start_Route = Temp_Route_Number;
		int Position_Start_City = City_Number;
		
		int Position_End_Step = Specular_Step_Number;
		int Position_End_Route = Specular_Route_Number;
		int Position_End_City = City_Number;
//		int Position_End_Step = Temp_Step_Number;
//		int Position_End_Route = Temp_Route_Number;
//		int Position_End_City = City_Number;
		
		int Action_ID = 0;
		ArrayList<TAction> Actions = new ArrayList<TAction>();
//		int Route_Number = Specular_Route_Number;
		
		// I create the first action to reverse the direction of the route.
		//Reverse_Direction
		{
			
			
			String Function_To_Invoke = "Declare_the_Refuel";
			
			
			ArrayList<Object> Params = new ArrayList<Object>();
			
			Action_ID++;
			
			TAction An_Action = new TAction();
			
			An_Action.Set_Action_Name( Function_To_Invoke );
			An_Action.Set_ID( Action_ID);
			
			
//			An_Action.Set_Action_Name("Initialize_Way");
			An_Action.Set_ID(Action_ID);
			
			Params.add(Destination_City.ordinal());
			Params.add(Position_Start_City);
			Params.add(Position_Start_Route);
			Params.add(Position_Start_Step);
			
			Params.add(Position_End_City);
			Params.add(Position_End_Route);
			Params.add(Position_End_Step);
			
			Params.add(Temp_Next_City_Number);
			Params.add(Temp_Next_Route_Number);
			Params.add(Temp_Next_Step_Number);
			
			
			
			
			
			
			An_Action.Set_Params( Params );
			
//			An_Action.Add_Pre_condition(Precondition_City);
//			An_Action.Add_Pre_condition(Precondition_Route);
//			An_Action.Add_Pre_condition(Precondition_Step);
			
//			An_Action.Add_Post_condition(Precondition_City);
//			An_Action.Add_Post_condition(Precondition_Route);
//			An_Action.Add_Post_condition(Precondition_Step);
			
			Actions.add(An_Action);
				
		}
		
//		if(Make_inversion)
		{
			Position_End_Step = Specular_Step_Number;
			Position_End_Route = Specular_Route_Number;
			Position_End_City = City_Number;
			
			TPredicate Precondition_City;
			TPredicate Precondition_Route;
			TPredicate Precondition_Step;
			TPredicate Precondition_Route_Status;
			TPredicate Postcondition_City;
			TPredicate Postcondition_Route;
			TPredicate Postcondition_Step;
			String Function_To_Invoke = "Reverse_Direction";
			
			// PRECONDITIONS
			Precondition_City = new TPredicate("Position_City", BL_Position_City, 
					TType_Relationship.is, Position_Start_City);
			Precondition_Route = new TPredicate("Position_Route", BL_Position_Route, 
					TType_Relationship.is, Position_Start_Route);
			Precondition_Step = new TPredicate("Position_Step", BL_Position_Step, 
					TType_Relationship.is, Position_Start_Step);
			
			// POSTCONDITIONS
			Postcondition_City = new TPredicate(null, BL_Position_City, 
					TType_Relationship.is, Position_End_City);
			Postcondition_Route = new TPredicate(null, BL_Position_Route, 
					TType_Relationship.is, Position_End_Route);
			Postcondition_Step = new TPredicate(null, BL_Position_Step, 
					TType_Relationship.is, Position_End_Step);
			
			ArrayList<Object> Params = new ArrayList<Object>();
			
			Action_ID++;
			
			TAction An_Action = new TAction();
			An_Action.Set_Action_Name( Function_To_Invoke );
			An_Action.Set_ID( Action_ID);
			
			Params.add( Specular_Route_Number );
			
			
			Params.add(Position_Start_City);
			Params.add(Position_Start_Route);
			Params.add(Position_Start_Step);
			
//			Params.add(Position_End_City);
//			Params.add(Position_End_Route);
//			Params.add(Position_End_Step);
			
			An_Action.Set_Params( Params );
//			An_Action.Add_Pre_condition(Precondition_City);
//			An_Action.Add_Pre_condition(Precondition_Route);
//			An_Action.Add_Pre_condition(Precondition_Step);
			
			An_Action.Add_Post_condition(Postcondition_City);
			An_Action.Add_Post_condition(Postcondition_Route);
			An_Action.Add_Post_condition(Postcondition_Step);
			
			Actions.add(An_Action);
		}
		

		int step_position = Position_End_Step;
		int Start_route_position = Position_End_Route;
		int End_route_position = -1;
		
		Integer Other_Step = 0;
		if (!Start_New_Travel)
		{
			Other_Step =1;
		}
		//this line is useful to adjust the calculi
		Position_Start_Step = Specular_Step_Number - Route_Speed;
		Integer Last_Step=0;
		//GO_TO_Step
	 	for(Integer Step = 1; Step <= Rounds_Time; Step++)
		{
	 		Time++;
	 		Other_Step++;
			TPosition_Coords Precondition_Position_AV_Coords;
			TPosition_Coords Postcondition_Position_AV_Coords;
			TPredicate Precondition_City;
			TPredicate Precondition_Route;
			TPredicate Precondition_Step;
			TPredicate Precondition_Route_Status;
			TPredicate Postcondition_City;
			TPredicate Postcondition_Route;
			TPredicate Postcondition_Step;
			TPredicate Postcondition_Route_Status;

//			TBelief BL_Precondition_Route_Status;
//			TBelief BL_Precondition_Specular_Route_Status;
//			TBelief BL_Postcondition_Route_Status;
//			TBelief BL_Postcondition_Specular_Route_Status;
			
			String Function_To_Invoke;
			
			//First I reverse the direction of the AV
			{
				Function_To_Invoke = "GO_TO_Step";
				Start_route_position = Position_End_Route;
				
				Position_Start_City = City_Position;
				Position_Start_Route = Specular_Route_Number;
				Position_Start_Step += Route_Speed;
//				if ((Other_Step == 2) & (Start_New_Travel == false))
//				{
//					if (Specular_Route_Number == Position_Start_Route)
//					{
//						Position_Start_Step = Specular_Step_Number;
//					}
//					
//				}
				
				Position_End_City = City_Position;
				Position_End_Route = Specular_Route_Number;
				Position_End_Step = Position_Start_Step + Route_Speed;
			}
			
			Precondition_Position_AV_Coords = new TPosition_Coords( City_Position, 
					Start_route_position, step_position);
			
			step_position = step_position + A_Route.Get_Route_Speed();
			
			//I set ending precondition data
			A_Destination_Station_in_PostCondition = A_Departure_Station;
			
			if (step_position > A_Route.Get_Steps_Number())
			{
				A_Destination_Station_in_PostCondition = A_Destination_Station;
				//If I arrive in next Station, I set the Route to -1 and the
				End_route_position = -1;
				step_position = 0;
			}
			else
			{
				End_route_position = Start_route_position;
			}
			
			if( Position_End_Step > Steps_Number_on_Route)
			{
				Position_End_City = City_Position;
				Position_End_Route = -1;
				Position_End_Step = 0;
			}
			else
			{
				Position_End_Route = Specular_Route_Number;
			}

			Postcondition_Position_AV_Coords = new TPosition_Coords(
					A_Destination_Station_in_PostCondition.ordinal(), End_route_position, step_position);
			
			Precondition_City = new TPredicate("Position_City", BL_Position_City, 
					TType_Relationship.is, Position_Start_City);
			Precondition_Route = new TPredicate("Position_Route", BL_Position_Route, 
					TType_Relationship.is, Position_Start_Route);
			Precondition_Step = new TPredicate("Position_Step", BL_Position_Step, 
					TType_Relationship.is, Position_Start_Step);
			
			Integer Route_Precondition = Precondition_Position_AV_Coords.Get_Route();
//			BL_Precondition_Route_Status = Beliefs.get("BL_Route_Status_"+ Position_Start_Route);
			
			Postcondition_City = new TPredicate(null, BL_Position_City, 
					TType_Relationship.is, Position_End_City);
			Postcondition_Route = new TPredicate(null, BL_Position_Route, 
					TType_Relationship.is, Position_End_Route);
			Postcondition_Step = new TPredicate(null, BL_Position_Step, 
					TType_Relationship.is, Position_End_Step);
			Last_Step = Position_End_Step;
			
			Integer Route_Postcondition = Postcondition_Position_AV_Coords.Get_Route();
//			BL_Postcondition_Route_Status = Beliefs.get("BL_Route_Status_"+Position_End_Route);
			
			Action_ID++;
			ArrayList<Object> Params = new ArrayList<Object>();
//			if(Function_To_Invoke.equals("GO_TO_Route"))
//			{
//				TAction An_Action = new TAction();
//				An_Action.Set_Action_Name("Initialize_Way");
//				An_Action.Set_ID( Action_ID);
//				Params.add(Position_End_City);
//				Params.add(Position_End_Route);
//				Params.add(Position_End_Step);
//				An_Action.Set_Params( Params );
//				Actions.add(An_Action);
//				
//				Action_ID++;
//				Params.clear();
//			}
			TAction An_Action = new TAction();
			An_Action.Set_ID( Action_ID);
			Params.add(Specular_Route_Number);
//			Params.add(Position_End_City);
//			Params.add(Position_End_Route);
//			Params.add(Position_End_Step);
			
			An_Action.Set_Params( Params );
			An_Action.Add_Pre_condition(Precondition_City);
			An_Action.Add_Pre_condition(Precondition_Route);
			An_Action.Add_Pre_condition(Precondition_Step);
//			if( BL_Precondition_Route_Status != null)
//			{
//				Precondition_Route_Status = new TPredicate(
//						"BL_Route_Status_"+Position_Start_Route, BL_Precondition_Route_Status, 
//						TType_Relationship.is, "Green");
//				An_Action.Add_Pre_condition(Precondition_Route_Status);
//			}
			
			An_Action.Add_Post_condition(Postcondition_City);
			An_Action.Add_Post_condition(Postcondition_Route);
			An_Action.Add_Post_condition(Postcondition_Step);
//			if( BL_Postcondition_Route_Status != null)
//			{
//				Postcondition_Route_Status = new TPredicate(
//						"BL_Route_Status_"+Position_End_Route, BL_Postcondition_Route_Status, 
//						TType_Relationship.is, "Green");
//				An_Action.Add_Post_condition(Postcondition_Route_Status);
//			}
			
			// I define "Use_Route" as a function to go from a departure to a destination station
			An_Action.Set_Action_Name(Function_To_Invoke);
			Actions.add(An_Action);
		}
	 	
	 	{
			TPredicate Precondition_City;
			TPredicate Precondition_Route;
			TPredicate Precondition_Step;
			TPredicate Precondition_Route_Status;
			TPredicate Postcondition_City;
			TPredicate Postcondition_Route;
			TPredicate Postcondition_Step;
			
			// PRECONDITIONS
//			Precondition_City = new TPredicate("Position_City", BL_Current_Position_City, 
//					TType_Relationship.is, Position_Start_City);
//			Precondition_Route = new TPredicate("Position_Route", BL_Current_Position_Route, 
//					TType_Relationship.is, Position_Start_Route);
//			Precondition_Step = new TPredicate("Position_Step", BL_Current_Position_Step, 
//					TType_Relationship.is, Position_Start_Step);
			Time++;
			String Function_To_Invoke = "Refuelling";
			
			
			ArrayList<Object> Params = new ArrayList<Object>();
			Integer Fuel_Level = 100;
			Params.add(Fuel_Level);
			
//			if(Last_Step > 0)
			{
				Params.add(A_Destination_Station.ordinal());
			}
			
			Action_ID++;
			
			TAction An_Action = new TAction();
			
			An_Action.Set_Action_Name( Function_To_Invoke );
			An_Action.Set_ID( Action_ID);
			
			An_Action.Set_Params( Params );
			
//			An_Action.Add_Pre_condition(Precondition_City);
//			An_Action.Add_Pre_condition(Precondition_Route);
//			An_Action.Add_Pre_condition(Precondition_Step);
			
//			An_Action.Add_Post_condition(Precondition_City);
//			An_Action.Add_Post_condition(Precondition_Route);
//			An_Action.Add_Post_condition(Precondition_Step);
			
			Actions.add(An_Action);
		}
	 	
	 	
	 	
//	}
		TPlan_Simulation plan = new TPlan_Simulation();
		
		EnumMap<TType_Quality_Desire, Double> quality_List = new EnumMap<>(TType_Quality_Desire.class);
		
		quality_List.put(TType_Quality_Desire.Motor, A_Route.Get_Route_Locomotive() );
		quality_List.put(TType_Quality_Desire.Panorama, A_Route.Get_Route_Panorama() );
		quality_List.put(TType_Quality_Desire.Speed, (double) A_Route.Get_Route_Speed() );
		
		TOption_Simulation An_Option = new TOption_Simulation(Actions, null, 0.0, quality_List);
		List<TCity> Destionations = new ArrayList<>();
		Destionations.add( City_Name );
		
		List<Integer> Numbered_Route = new ArrayList<>();
		Numbered_Route.add( Specular_Route_Number );
		Double path_Time = (double) Rounds_Time;
	
		plan.Insert_Path_by_Routes(Destionations, Numbered_Route, quality_List, path_Time);
		
		
		An_Option.Path.Copy_Plan(plan);
		
		// I update the time to satisfy the plan option
		//Minute_of_Hours = plan.Path_Time.intValue();
//		Minutes = (int)(Minute_of_Hours*60);
		Minutes = (int)(Time*60);
		
	//	LocalDateTime Temp_Time = Current_Time.plusMinutes(Minutes);
		An_Option.Set_Satisfied_Time( Current_Time.plusMinutes(Minutes) );
		
		Option_List.add(An_Option);
	
		return Option_List;
	}

	
}
