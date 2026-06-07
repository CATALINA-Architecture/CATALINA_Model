import java.awt.Point;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import com.Catalina_Model.Catalina_V_0_3.TAction;
import com.Catalina_Model.Catalina_V_0_3.TAction_Control;
import com.Catalina_Model.Catalina_V_0_3.TAttentional_Desire;
import com.Catalina_Model.Catalina_V_0_3.TBelief;
import com.Catalina_Model.Catalina_V_0_3.TDouble_Object;
import com.Catalina_Model.Catalina_V_0_3.TExecutive_Reasoner_Function;
import com.Catalina_Model.Catalina_V_0_3.TExecutive_Switching_Function;
import com.Catalina_Model.Catalina_V_0_3.TGlobal_Workspace;
import com.Catalina_Model.Catalina_V_0_3.TGreen_Desire;
import com.Catalina_Model.Catalina_V_0_3.TIntention;
import com.Catalina_Model.Catalina_V_0_3.TAction_Execution_Result;
import com.Catalina_Model.Catalina_V_0_3.TPerception;
import com.Catalina_Model.Catalina_V_0_3.TPractical_Desire;
import com.Catalina_Model.Catalina_V_0_3.TPractical_Desire_Data;
import com.Catalina_Model.Catalina_V_0_3.TPredicate;
import com.Catalina_Model.Catalina_V_0_3.TQuadruple_Object;
import com.Catalina_Model.Catalina_V_0_3.TQuality_Desire;
import com.Catalina_Model.Catalina_V_0_3.TTemporal_Operator;
import com.Catalina_Model.Catalina_V_0_3.TTemporal_Propositional_Formula;
import com.Catalina_Model.Catalina_V_0_3.TType_Temporal_Operator;
import com.Catalina_Model.Catalina_V_0_3.TType_Update_Contract;

public class TFunctions_for_Option_Execution
{
	private Autonomous_Vehicle_Demo Demo;
	private TCommon_Functions Common_Functions;
	private Float Global_Time_to_Move  ;
	private TFunctions_for_Inhibition_Function Belief_Inhibition_Functions;
	
	public Boolean Response_Sended;
	public String String_Response_Sended;
	
	private Integer Practical_Desire_Number;
	
	public TFunctions_for_Option_Execution(Autonomous_Vehicle_Demo demo)
	{
		this.Demo = demo;
		this.Common_Functions = demo.Common_Functions;
		this.Global_Time_to_Move = 1.0f;
		this.Response_Sended = false;
		this.String_Response_Sended = "";
		this.Belief_Inhibition_Functions = this.Demo.Functions_for_Belief_Inhibition_Function;
		
		this.Practical_Desire_Number = 0;
	}
	
	
	
//	public void Add_Function_To_Switching_Function(TExecutive_Switching_Function Switching_Function )
	public void Add_Function_To_Switching_Function(TAction_Control Action_Control )
	{
		Action_Control.Register_Plan_Execution_Funtion_to_Execute(
				"Ask Danger Type on the road", this::Execute_Ask_Danger_Type_on_the_road);
		Action_Control.Register_Plan_Execution_Funtion_to_Execute(
				"GO_TO_Route", this::Execute_Movement_Go_TO_Route);
		Action_Control.Register_Plan_Execution_Funtion_to_Execute(
				"GO_TO_Step", this::Execute_Movement_GO_TO_Step);
		Action_Control.Register_Plan_Execution_Funtion_to_Execute(
				"Initialize_Way", this::Execute_Initialize_Way);
		Action_Control.Register_Plan_Execution_Funtion_to_Execute(
				"Wait", this::Execute_Wait);
		Action_Control.Register_Plan_Execution_Funtion_to_Execute(
				"Change_Direction", this::Execute_Change_Direction);
		Action_Control.Register_Plan_Execution_Funtion_to_Execute(
				"Reverse_Direction", this::Execute_Reverse_Direction);
		
		Action_Control.Register_Plan_Execution_Funtion_to_Execute(
				"Decide_To_Continue_or_Not", this::Execute_Decision);
		Action_Control.Register_Plan_Execution_Funtion_to_Execute(
				"Refuelling", this::Execute_Refuelling);
		Action_Control.Register_Plan_Execution_Funtion_to_Execute(
				"Declare_the_Refuel", this::Execute_Declaration_of_Refuel);
		
		
		
//		Come_Back_to_City
		
	}
	
	public TAction_Execution_Result Execute_Initialize_Way(
			TGlobal_Workspace Global_Workspace,
			TAction Action, HashMap<String, TBelief> Beliefs_Cloned )
	{
		TAction_Execution_Result result = new TAction_Execution_Result();
		
		ArrayList<Object> Data = new ArrayList<Object>();

		HashMap<String, Object> Beliefs_To_Change = new HashMap<String, Object>();
		
		Integer Position_End_City = (Integer) Action.Get_Params().get(0);
		Integer Position_End_Route = (Integer) Action.Get_Params().get(1);
		Integer Position_End_Step = (Integer) Action.Get_Params().get(2);
		
		
		Beliefs_To_Change.put("BL_Next_Position_City", new ArrayList<>(Arrays.asList("Object_Complement", Position_End_City)));
		Beliefs_To_Change.put("BL_Next_Position_Route", new ArrayList<>(Arrays.asList("Object_Complement", Position_End_Route)));
		Beliefs_To_Change.put("BL_Next_Position_Step", new ArrayList<>(Arrays.asList("Object_Complement", Position_End_Step)));
		
		if(Action.Get_Params().size() > 3)
		{
			//It means that this is the first action when the Agent moves
			String Desire_Name = (String) Action.Get_Params().get(3);
			LocalDateTime Max_Satisfation_Time = (LocalDateTime) Action.Get_Params().get(4);
			
			Beliefs_To_Change.put("BL_Previous_Travel_Intention_Max_Satisfaction_Time", 
									new ArrayList<>(Arrays.asList("Subject", Desire_Name)));
			Beliefs_To_Change.put("BL_Previous_Travel_Intention_Max_Satisfaction_Time", 
									new ArrayList<>(Arrays.asList("Object_Complement", Max_Satisfation_Time)));
		}		
		
		Data.add( Beliefs_To_Change );
//		result.Set_Object_First( (Boolean) true);
		result.Set_Result( true );
		
//		result.Set_Object_Second( Data );
		result.Set_Beliefs_to_Change( Beliefs_To_Change );
		return result;
	}
	
	public TAction_Execution_Result Execute_Wait(
			TGlobal_Workspace Global_Workspace,
			TAction Action, HashMap<String, TBelief> Map_Beliefs )
	{
		TAction_Execution_Result result = new TAction_Execution_Result();
		
		ArrayList<Object> Data = new ArrayList<Object>();

		HashMap<String, Object> Beliefs_To_Change = new HashMap<String, Object>();
		
		Beliefs_To_Change.putAll( this.Update_Routes(Action, Map_Beliefs) );
		Beliefs_To_Change.putAll( this.Update_Current_Time(Action, Map_Beliefs) );
		
		Common_Functions.PrintLn("Execute_Wait Execute_Wait");
		Data.add( Beliefs_To_Change );
//		result.Set_Object_First( (Boolean) true);
		result.Set_Result( true );
		
//		result.Set_Object_Second( Data );
		result.Set_Beliefs_to_Change( Beliefs_To_Change );
		return result;
	}
	
	/**
	 * In this function we simulate the conversation 
	 * between the agent and the Traffic Control Service (TCS).
	 * @param Action
	 * @return
	 */
	public TAction_Execution_Result Execute_Ask_Danger_Type_on_the_road2(
			TGlobal_Workspace Global_Workspace,
			TAction Action, HashMap<String, TBelief> Map_Beliefs )
	{
		TAction_Execution_Result result = new TAction_Execution_Result();
		
		/*
		 * I Ask the Type of the Danger (for information only)
		 */
		this.Common_Functions.Print_Colored_Text(
				"----------------------", 3);
		this.Common_Functions.Print_Colored_Text(
				"+++ I found the route closed!! +++", 3);
		this.Common_Functions.Print_Colored_Text(
				"+++ I stop and ask for some information!! +++", 3);
		this.Common_Functions.Print_Colored_Text(
				"This is a simulation of a virtual Frontal Camera...", 3);
		
		ArrayList<Object> Action_Params = Action.Get_Params();
		ArrayList<Integer> Positions = ( ArrayList<Integer> ) Action_Params.getFirst();
		Integer City_position = Positions.get( 0 );
		Integer Route_position = Positions.get( 1 );
		Integer Step_position = Positions.get( 2 );
		
		TBelief BL_Position_City = Map_Beliefs.get("BL_Position_City");
//		this.Common_Functions.Print_Colored_Text(City_position+" - "+BL_Position_City.Get_Predicate().Get_Object_Complement(), 2);
		
		TBelief BL_Position_Route = Map_Beliefs.get("BL_Position_Route");
		TBelief BL_Next_Position_Route = Map_Beliefs.get("BL_Next_Position_Route");
//		this.Common_Functions.Print_Colored_Text(Route_position+" - "+BL_Position_Route.Get_Predicate().Get_Object_Complement(), 2);
		
		TBelief BL_Position_Step = Map_Beliefs.get("BL_Position_Step");
//		this.Common_Functions.Print_Colored_Text(Step_position+" - "+BL_Position_Step.Get_Predicate().Get_Object_Complement(), 2);
		
		City_position = (Integer) BL_Position_City.Get_Predicate().Get_Object_Complement();
		Route_position = (Integer) BL_Position_Route.Get_Predicate().Get_Object_Complement();
		Step_position = (Integer) BL_Position_Step.Get_Predicate().Get_Object_Complement();
		ArrayList<Integer> Real_Positions = new ArrayList<Integer>();
		Real_Positions.add( City_position);
		Real_Positions.add( Route_position);
		Real_Positions.add( Step_position);
		
		Integer Dangeroues_Route_position = Route_position;
		if(Route_position == -1)
		{
			Dangeroues_Route_position = (Integer) BL_Next_Position_Route.Get_Predicate().Get_Object_Complement();
		}
		Real_Positions.add( Dangeroues_Route_position );
//		this.Demo.Perception_Processing_functions.Virtual_TCS.Danger_Query_Simulation( Real_Positions );
////		String Question = "What is the type of the danger on the route "+Dangeroues_Route_position+" on Step position "+Step_position+"?";
////		

		if(this.Demo.Perception_Processing_functions.Virtual_TCS.Value_For_Sample_Execution)
		{
			this.Demo.Perception_Processing_functions.Virtual_TCS.Danger_Query_Simulation( Real_Positions );
		}
		else
		{
//		String Question = "- Why is the route closed?";
//		this.Common_Functions.Print_Colored_Text(Question, 2);
//		Question ="";
//		int counter = 0;
//		for(TType_Danger Type_Danger: TType_Danger.values() )
//		{
//			Question += counter + "- "+Type_Danger+"\n";
//			counter++;
//		}
//		
//		if (Question.length() > 0)
//		{
//		    Question = Question.substring(0, Question.length() - 1);
//		}
////		this.Common_Functions.Print_Colored_Text(Question, 2);
//		
//		String Answer = ""; 
//		Boolean Repeat = false;
//		int index = -1;
//		do
//		{
////			Answer = this.Common_Functions.Get_Preset_Input(Question, "1", 2, true);
//			this.Common_Functions.Print_Colored_Text(Question, 2);
//			while(!this.Response_Sended)
//			{
//				try {
//					Thread.sleep(100);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//			this.Response_Sended = false;
//			Answer = this.String_Response_Sended;
//			this.String_Response_Sended = "";
//			
//			this.Common_Functions.Print(" Your response was: "+Answer);
//			if (Answer == null | Answer.isEmpty()) 
//			{
//				Repeat =  true;
//		    }
//			else
//			{
//				try 
//				{
//			        // 1. Prova a convertire la stringa in un intero
//			        index = Integer.parseInt(Answer);
//
//			        // 2. Ottieni il numero totale di elementi nell'enum
//			        int totalDangers = TType_Danger.values().length;
//
//			        // 3. Controlla se l'indice è nel range valido (da 0 a total-1)
//			        Repeat =  !(index >= 0 && index < totalDangers);
//			    } 
//				catch (NumberFormatException e) 
//				{
//			        // Se la stringa non era un numero (es. "abc"), non è un indice.
//					Repeat = true;
//			    }
//			}
//			if ( Repeat )
//			{
//				this.Common_Functions.Print_Colored_Text(
//						"The Answer was wrong! Repeat please.", 2);
//			}
//		}
//		while( Repeat);
//		
//		TType_Danger Type_Danger = TType_Danger.values()[index];
//		/*
//		 * I Ask the duration of the Danger (for recompute other paths)
//		 */
//		
//		/**
//		 * this gives the same behaviour of the simulation for Catalina_V_0.2
//		 * min = 4 and max = 9
//		 */
//		Integer min = 1;
//		Integer max = 100;
//		Integer Random_Number = ThreadLocalRandom.current().nextInt(4, max + 1);
//		
//		Question = "\n- How long will the route be closed?\n"
//				+ "Enter the number of time slots as an integer between 1 and 100.(a time slot equals 15 minutes)";
////		+"Give me a number between "+min+" and "+max;
//		do
//		{
////			Answer = this.Common_Functions.Get_Preset_Input(
////						Question, Random_Number.toString(), 2, true);
//			this.Common_Functions.Print_Colored_Text(Question, 2);
//			while(!this.Response_Sended)
//			{
//				try {
//					Thread.sleep(100);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//			this.Response_Sended = false;
//			Answer = this.String_Response_Sended;
//			this.String_Response_Sended = "";
//			this.Common_Functions.Print(" Your response was: "+Answer);
//			
//			if (Answer == null | Answer.isEmpty()) 
//			{
//				Repeat =  true;
//		    }
//			else
//			{
//				try 
//				{
//			        // 1. Prova a convertire la stringa in un intero
//			        index = Integer.parseInt(Answer);
//
//			        // 2. Controlla se l'indice è nel range valido (da min a max)
//			        Repeat =  !(index >= min && index <= max);
//			    } 
//				catch (NumberFormatException e) 
//				{
//			        // Se la stringa non era un numero (es. "abc"), non è un indice.
//					Repeat = true;
//			    }
//			}
//			if ( Repeat)
//			{
//				this.Common_Functions.Print_Colored_Text(
//						"The Answer was wrong! Repeat please.", 2);
//			}
//		}
//		while( Repeat);
		
		//I simulate the acquisition of danger data
		this.Demo.Perception_Processing_functions.Virtual_TCS.Danger_Query_Simulation( Real_Positions );
		
		ArrayList<Integer> Danger_Data = new ArrayList<Integer>();
		Danger_Data.addAll(this.Demo.Perception_Processing_functions.Virtual_TCS.Danger_Data);
		/**
		 * Danger_Data Index:
		 * 0 - City_position
		 * 1 - Route_position
		 * 2 - Step_position
		 * 3 - Dangeroues_Route_position
		 * 4 - Type_Danger.ordinal()
		 * 5 - Duration
		 */
		
		TType_Danger Type_Danger = TType_Danger.values()[Danger_Data.get( 4 )];
		Integer Duration = Danger_Data.get( 5 );
		
		/**
		 * How, I create the list of beliefs to change
		 */
//		ArrayList<Object> Data = new ArrayList<Object>();

		HashMap<String, Object> Beliefs_To_Change = new HashMap<String, Object>();
		
//		Beliefs_To_Change.put("BL_Danger_on_the_Route", new ArrayList<>(Arrays.asList("Subject", Route_position, "Me")));
		Beliefs_To_Change.put("BL_Danger_on_the_Route", new ArrayList<>(Arrays.asList("Subject", Dangeroues_Route_position, "Me")));
		Beliefs_To_Change.put("BL_Danger_on_the_Route", new ArrayList<>(Arrays.asList("Object_Complement", Type_Danger, "Me")));
//		Beliefs_To_Change.put("BL_Route_Status_"+Route_position, new ArrayList<>(Arrays.asList("Object_Complement", "Under_maintenance", "Me")));
		Beliefs_To_Change.put("BL_Route_Status_"+Dangeroues_Route_position, new ArrayList<>(Arrays.asList("Object_Complement", "Under_maintenance", "Me")));
		Beliefs_To_Change.put("BL_Dangerous_Position_Route", new ArrayList<>(Arrays.asList("Object_Complement", Dangeroues_Route_position, "Me")));
		
		
		/**
		 * 
		 * 
		 */
		TBelief BL_Temporary_Closed_Routes = Map_Beliefs.get("BL_Temporary_Closed_Routes");
		
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
		TBelief Belief_Route_Status = Map_Beliefs.get("BL_Temporary_Closed_Routes");
		if( Belief_Route_Status != null)
		{
			/**
			 * Predicate Format:
			 * ["Temporary Closed Routes", is, HashMap<int Route, HashMap<int Step, int duration>>].
			 */
			TPredicate Predicate = Belief_Route_Status.Get_Predicate();
			HashMap<Integer , HashMap<Integer , Integer >> Map_Temporary_Closed_Routes = null;

			//Object_Complement: HashMap<int Route, HashMap<int Step, int duration>>
			Map_Temporary_Closed_Routes = 
					(HashMap<Integer , HashMap<Integer , Integer >>) Predicate.Get_Object_Complement();
			
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
		
//		Beliefs_To_Change.put("BL_Come_Back_to_City", new ArrayList<>(Arrays.asList("Object_Complement", City_position)));
		/**
		 * First Datum is a list of Beliefs to change
		 */
		
		/**
		 * Now I have to come back to the previous city
		 * 
		 */
		
		
//		ArrayList<Object> Practical_Desire_and_Data =
//				this.Rise_Come_Back_Desire( Positions, Map_Beliefs);
//		TPractical_Desire Come_Back_Desire = 
//				(TPractical_Desire) Practical_Desire_and_Data.getFirst();
		
		
		/**
		 * 
		 */
//		TPractical_Desire Come_Back_Desire = 
//					(TPractical_Desire) Practical_Desire_and_Data.getFirst();
////		System.out.println("Practical_Desire_and_Data: "+Practical_Desire_and_Data.get(1).getClass());
//		HashMap<String, Object> prova = ((HashMap<String, Object>) Practical_Desire_and_Data.get(1));
//		Beliefs_To_Change.putAll( prova );
		//If The agent is in a route
		TAttentional_Desire Cadiz = null;
		if ( Route_position > -1)
		{
			TIntention Intention = Global_Workspace.Get_Selected_Intentions().getFirst();
			
			LocalDateTime arrivalTime = (LocalDateTime) Intention.Get_Selected_Option().Get_Satisfied_Time();
			
//			Intention.Get_Attentional_Desire().te
//			Get_Temporal_Operator().Get_End_Time()

			// Calcoliamo la differenza tra Arrival_time e now
			
			long oreDifferenza = ChronoUnit.HOURS.between(LocalDateTime.now(), arrivalTime);
//			LocalDateTime mockTime = LocalDateTime.now().plusHours(5);
//			long oreDifferenza = ChronoUnit.HOURS.between(LocalDateTime.now(), mockTime);
//			Duration durata = Duration.
//			long oreDifferenza = 
			
			if ( oreDifferenza < Duration) 
			{
	            System.out.println("La differenza è minore di " + Duration + " ore.");
	        }
			TAction_Execution_Result Go_Back_Data = this.Rise_Come_Back_Desire(Real_Positions, Map_Beliefs);
			Beliefs_To_Change.putAll( Go_Back_Data.Get_Beliefs_to_Change() );
			result.Set_Practical_Desires( Go_Back_Data.Get_Practical_Desires() );
			
//			for(TAttentional_Desire Desire: this.Demo.Agent.Get_Current_Attentional_Desires())
//			{
//				if(Desire.Get_Name().equals("PD_Visit_Cadiz"))
//				{
//					Cadiz = Desire;
//				}
//			}
//			if(Cadiz != null)
//			{
//				TBelief BL_Current_Time = Map_Beliefs.get("BL_Current_Time");
//				
//				Cadiz.Set_Saliency(0.99);
//				this.Demo.Agent.Get_Global_WorkSpace().Broadcast_Message(TType_Update_Contract.Standing_Desires);
//			}
//			result.Set_Object_Third((TDouble_Object) Go_Back_Data.get(1) );
		}
//		Data.add( Beliefs_To_Change );
//		result.Set_Object_First( (Boolean) true);
		
		}
		HashMap<String, Object> Beliefs_To_Change = new HashMap<String, Object>();
		result.Set_Result( true );
//		result.Set_Object_Second( Data );
		result.Set_Beliefs_to_Change( Beliefs_To_Change );
		
		return result;
	}
	
	/**
	 * In this function we simulate the conversation 
	 * between the agent and the Traffic Control Service (TCS).
	 * @param Action
	 * @return
	 */
	public TAction_Execution_Result Execute_Ask_Danger_Type_on_the_road(
			TGlobal_Workspace Global_Workspace,
			TAction Action, HashMap<String, TBelief> Map_Beliefs )
	{
		TAction_Execution_Result result = new TAction_Execution_Result();
		
		/*
		 * I Ask the Type of the Danger (for information only)
		 */
		this.Common_Functions.Print_Colored_Text(
				"----------------------", 3);
		this.Common_Functions.Print_Colored_Text(
				"+++ I found the route closed!! +++", 3);
		this.Common_Functions.Print_Colored_Text(
				"+++ I stop and ask for some information!! +++", 3);
		this.Common_Functions.Print_Colored_Text(
				"This is a simulation of a virtual Frontal Camera...", 3);
		
		ArrayList<Object> Action_Params = Action.Get_Params();
		ArrayList<Integer> Positions = ( ArrayList<Integer> ) Action_Params.getFirst();
		Integer City_position = Positions.get( 0 );
		Integer Route_position = Positions.get( 1 );
		Integer Step_position = Positions.get( 2 );
		
		TBelief BL_Position_City = Map_Beliefs.get("BL_Position_City");
//		this.Common_Functions.Print_Colored_Text(City_position+" - "+BL_Position_City.Get_Predicate().Get_Object_Complement(), 2);
		
		TBelief BL_Position_Route = Map_Beliefs.get("BL_Position_Route");
		TBelief BL_Next_Position_Route = Map_Beliefs.get("BL_Next_Position_Route");
//		this.Common_Functions.Print_Colored_Text(Route_position+" - "+BL_Position_Route.Get_Predicate().Get_Object_Complement(), 2);
		
		TBelief BL_Position_Step = Map_Beliefs.get("BL_Position_Step");
//		this.Common_Functions.Print_Colored_Text(Step_position+" - "+BL_Position_Step.Get_Predicate().Get_Object_Complement(), 2);
		
		City_position = (Integer) BL_Position_City.Get_Predicate().Get_Object_Complement();
		Route_position = (Integer) BL_Position_Route.Get_Predicate().Get_Object_Complement();
		Step_position = (Integer) BL_Position_Step.Get_Predicate().Get_Object_Complement();
		ArrayList<Integer> Real_Positions = new ArrayList<Integer>();
		Real_Positions.add( City_position);
		Real_Positions.add( Route_position);
		Real_Positions.add( Step_position);
		
		Integer Dangeroues_Route_position = Route_position;
		if(Route_position == -1)
		{
			Dangeroues_Route_position = (Integer) BL_Next_Position_Route.Get_Predicate().Get_Object_Complement();
		}
		Real_Positions.add( Dangeroues_Route_position );
//		this.Demo.Perception_Processing_functions.Virtual_TCS.Danger_Query_Simulation( Real_Positions );
////		String Question = "What is the type of the danger on the route "+Dangeroues_Route_position+" on Step position "+Step_position+"?";
////		
///

		HashMap<String, LocalDateTime> Beliefs_To_Wait = new HashMap<String, LocalDateTime>();
		TBelief BL_Temporary_Closed_Duration = Map_Beliefs.get("BL_Temporary_Closed_Duration");
		Beliefs_To_Wait.put("BL_Temporary_Closed_Duration" , BL_Temporary_Closed_Duration.Get_Time_stamp());
		
		if(this.Demo.Perception_Processing_functions.Virtual_TCS.Value_For_Sample_Execution)
		{
			this.Demo.Perception_Processing_functions.Virtual_TCS.Danger_Query_Simulation( Real_Positions );
		}
		else
		{
		
		//I simulate the acquisition of danger data
		this.Demo.Perception_Processing_functions.Virtual_TCS.Danger_Query_Simulation( Real_Positions );
		
		ArrayList<Integer> Danger_Data = new ArrayList<Integer>();
		Danger_Data.addAll(this.Demo.Perception_Processing_functions.Virtual_TCS.Danger_Data);
		/**
		 * Danger_Data Index:
		 * 0 - City_position
		 * 1 - Route_position
		 * 2 - Step_position
		 * 3 - Dangeroues_Route_position
		 * 4 - Type_Danger.ordinal()
		 * 5 - Duration
		 */
		
		TType_Danger Type_Danger = TType_Danger.values()[Danger_Data.get( 4 )];
		Integer Duration = Danger_Data.get( 5 );
		
		/**
		 * How, I create the list of beliefs to change
		 */
//		ArrayList<Object> Data = new ArrayList<Object>();

		HashMap<String, Object> Beliefs_To_Change = new HashMap<String, Object>();
		
////		Beliefs_To_Change.put("BL_Danger_on_the_Route", new ArrayList<>(Arrays.asList("Subject", Route_position, "Me")));
//		Beliefs_To_Change.put("BL_Danger_on_the_Route", new ArrayList<>(Arrays.asList("Subject", Dangeroues_Route_position, "Me")));
//		Beliefs_To_Change.put("BL_Danger_on_the_Route", new ArrayList<>(Arrays.asList("Object_Complement", Type_Danger, "Me")));
////		Beliefs_To_Change.put("BL_Route_Status_"+Route_position, new ArrayList<>(Arrays.asList("Object_Complement", "Under_maintenance", "Me")));
//		Beliefs_To_Change.put("BL_Route_Status_"+Dangeroues_Route_position, new ArrayList<>(Arrays.asList("Object_Complement", "Under_maintenance", "Me")));
//		Beliefs_To_Change.put("BL_Dangerous_Position_Route", new ArrayList<>(Arrays.asList("Object_Complement", Dangeroues_Route_position, "Me")));
		
		
		/**
		 * 
		 * 
		 */
//		TBelief BL_Temporary_Closed_Routes = Map_Beliefs.get("BL_Temporary_Closed_Routes");
//		
//		HashMap<Integer, HashMap<Integer, Integer >>  
//							Temporary_Closed_Routes = null;
//		HashMap<Integer, Integer > Steps_Closed = null;
//		if( BL_Temporary_Closed_Routes.Get_Predicate().Get_Object_Complement() 
//				== null)
//		{
//			Temporary_Closed_Routes = new HashMap<Integer, HashMap<Integer,Integer>>();
//			Steps_Closed = new HashMap<Integer, Integer>();
//			Steps_Closed.put(Step_position, Duration);
//			Temporary_Closed_Routes.put(Dangeroues_Route_position, Steps_Closed);
//		}
//		else
//		{
//			Temporary_Closed_Routes = (HashMap<Integer, HashMap<Integer, Integer >>)
//					BL_Temporary_Closed_Routes.Get_Predicate().Get_Object_Complement();
//			Steps_Closed = Temporary_Closed_Routes.get( Dangeroues_Route_position );
//			if(Steps_Closed == null)
//			{
//				Steps_Closed = new HashMap<Integer, Integer>();
//			}
//			Steps_Closed.put(Step_position, Duration);
//			Temporary_Closed_Routes.put(Dangeroues_Route_position, Steps_Closed);
//		}
//		Beliefs_To_Change.put("BL_Temporary_Closed_Routes", new ArrayList<>(Arrays.asList("Object_Complement", Temporary_Closed_Routes, "Me")));
//		
//		/**
//		 * 
//		 * 
//		 */
//		
//		
//		/**
//		 * I update the predicate "PR_Temporary_Closed_Routes"
//		 */
////		this.Common_Functions.Print_Colored_Text( Map_Beliefs.toString(), 7);
//		TBelief Belief_Route_Status = Map_Beliefs.get("BL_Temporary_Closed_Routes");
//		if( Belief_Route_Status != null)
//		{
//			/**
//			 * Predicate Format:
//			 * ["Temporary Closed Routes", is, HashMap<int Route, HashMap<int Step, int duration>>].
//			 */
//			TPredicate Predicate = Belief_Route_Status.Get_Predicate();
//			HashMap<Integer , HashMap<Integer , Integer >> Map_Temporary_Closed_Routes = null;
//
//			//Object_Complement: HashMap<int Route, HashMap<int Step, int duration>>
//			Map_Temporary_Closed_Routes = 
//					(HashMap<Integer , HashMap<Integer , Integer >>) Predicate.Get_Object_Complement();
//			
//			if ( Map_Temporary_Closed_Routes == null)
//			{
//				Map_Temporary_Closed_Routes = new HashMap<Integer, HashMap<Integer,Integer>>();
//			}
//			HashMap<Integer , Integer > List_Steps = null;
//			Boolean No_Route = false;
//			Boolean No_Step = false;
//			List_Steps = Map_Temporary_Closed_Routes.get( Dangeroues_Route_position );
//			if ( List_Steps == null)
//			{
//				List_Steps = new HashMap<Integer,Integer>();
//				No_Route = true;
//			}
//			Integer Step_Duration = List_Steps.get( Step_position );
//			if ( List_Steps == null)
//			{
//				List_Steps = new HashMap<Integer,Integer>();
//				No_Step = true;
//			}
//			List_Steps.put( Step_position , Duration);
//			Map_Temporary_Closed_Routes.put( Dangeroues_Route_position, List_Steps);
//			Beliefs_To_Change.put("BL_Temporary_Closed_Routes", new ArrayList<>(Arrays.asList("Object_Complement", Map_Temporary_Closed_Routes)));
//			
//		}
//		else
//		{
//			this.Common_Functions.Print_Colored_Text("The BL_Danger_on_the_Route is not Uninhibited!",2);
////			this.Demo.End_Simulation();
//		}
		
//		Beliefs_To_Change.put("BL_Come_Back_to_City", new ArrayList<>(Arrays.asList("Object_Complement", City_position)));
		/**
		 * First Datum is a list of Beliefs to change
		 */
		
		/**
		 * Now I have to come back to the previous city
		 * 
		 */
		
		
//		ArrayList<Object> Practical_Desire_and_Data =
//				this.Rise_Come_Back_Desire( Positions, Map_Beliefs);
//		TPractical_Desire Come_Back_Desire = 
//				(TPractical_Desire) Practical_Desire_and_Data.getFirst();
		
		
		/**
		 * 
		 */
//		TPractical_Desire Come_Back_Desire = 
//					(TPractical_Desire) Practical_Desire_and_Data.getFirst();
////		System.out.println("Practical_Desire_and_Data: "+Practical_Desire_and_Data.get(1).getClass());
//		HashMap<String, Object> prova = ((HashMap<String, Object>) Practical_Desire_and_Data.get(1));
//		Beliefs_To_Change.putAll( prova );
		//If The agent is in a route

//		if ( Route_position > -1)
//		{
//			TIntention Intention = Global_Workspace.Get_Selected_Intentions().getFirst();
//			
//			LocalDateTime arrivalTime = (LocalDateTime) Intention.Get_Selected_Option().Get_Satisfied_Time();
//			
////			Intention.Get_Attentional_Desire().te
////			Get_Temporal_Operator().Get_End_Time()
//
//			// Calcoliamo la differenza tra Arrival_time e now
//			
//			long oreDifferenza = ChronoUnit.HOURS.between(LocalDateTime.now(), arrivalTime);
////			LocalDateTime mockTime = LocalDateTime.now().plusHours(5);
////			long oreDifferenza = ChronoUnit.HOURS.between(LocalDateTime.now(), mockTime);
////			Duration durata = Duration.
////			long oreDifferenza = 
//			
//			if ( oreDifferenza < Duration) 
//			{
//	            System.out.println("La differenza è minore di " + Duration + " ore.");
//	        }
//			TAction_Execution_Result Go_Back_Data = this.Rise_Come_Back_Desire(Real_Positions, Map_Beliefs);
//			Beliefs_To_Change.putAll( Go_Back_Data.Get_Beliefs_to_Change() );
//			result.Set_Practical_Desires( Go_Back_Data.Get_Practical_Desires() );
//			
////			for(TAttentional_Desire Desire: this.Demo.Agent.Get_Current_Attentional_Desires())
////			{
////				if(Desire.Get_Name().equals("PD_Visit_Cadiz"))
////				{
////					Cadiz = Desire;
////				}
////			}
////			if(Cadiz != null)
////			{
////				TBelief BL_Current_Time = Map_Beliefs.get("BL_Current_Time");
////				
////				Cadiz.Set_Saliency(0.99);
////				this.Demo.Agent.Get_Global_WorkSpace().Broadcast_Message(TType_Update_Contract.Standing_Desires);
////			}
////			result.Set_Object_Third((TDouble_Object) Go_Back_Data.get(1) );
//		}
//		Data.add( Beliefs_To_Change );
//		result.Set_Object_First( (Boolean) true);
		
		}
		HashMap<String, Object> Beliefs_To_Change = new HashMap<String, Object>();
		
		
		result.Set_Result( true );
//		result.Set_Object_Second( Data );
		result.Set_Beliefs_to_Change( Beliefs_To_Change );
		result.Set_Beliefs_to_Wait( Beliefs_To_Wait );
		
		return result;
	}
	
	public TAction_Execution_Result Execute_Reverse_Direction(
			TGlobal_Workspace Global_Workspace, TAction Action, HashMap<String, TBelief> Map_Beliefs )
	{
		TAction_Execution_Result result = new TAction_Execution_Result();
		
		TPredicate Precondition_City_Position = Action.Get_Pre_conditions().get(0);
		TPredicate Precondition_Route_Position = Action.Get_Pre_conditions().get(1);
		TPredicate Precondition_Step_Position = Action.Get_Pre_conditions().get(2);
		
		TPredicate Postcondition_City_Position = Action.Get_Post_conditions().get(0);
		TPredicate Postcondition_Route_Position = Action.Get_Post_conditions().get(1);
		TPredicate Postcondition_Step_Position = Action.Get_Post_conditions().get(2);
		
		Integer Integer_Start_City = (Integer) Precondition_City_Position.Get_Object_Complement();
		Integer Integer_End_City = (Integer) Postcondition_City_Position.Get_Object_Complement();
		
		Integer Integer_Start_Route = (Integer) Precondition_Route_Position.Get_Object_Complement();
		Integer Integer_End_Route = (Integer) Postcondition_Route_Position.Get_Object_Complement();
		
		Integer Integer_Start_Step = (Integer) Precondition_Step_Position.Get_Object_Complement();
		Integer Integer_End_Step = (Integer) Postcondition_Step_Position.Get_Object_Complement();
		
//		String City_Name = TCity.values()[Integer_End_City].toString();
		
//		Integer Minus = 0;
//		
//		if(Integer_End_Route==-1)
//		{
//			Minus = -1;
//		}
		
//		TEnvironment Map = (TEnvironment) Map_Beliefs.get("BL_Map").Get_Predicate()
//									.Get_Object_Complement();
		
//		TRoute the_route = Map.Get_Route(Integer_Start_Route);
//		Integer Max_Steps = the_route.Get_Route_Speed();
//		Float Time_to_Move = this.Global_Time_to_Move ;
		
//		ArrayList<Point> Points = this.Demo.Gui_Map.Map_Panel.
//								Ruotes_Coords.get(Integer_Start_Route);
		
		this.Common_Functions.PrintLn("I change direction of the route to go back");
		
		/**
		 * 
		 */
		ArrayList<Object> Data = new ArrayList<Object>();

		HashMap<String, Object> Beliefs_To_Change = new HashMap<String, Object>();

		Beliefs_To_Change.put("BL_Position_City", new ArrayList<>(Arrays.asList("Object_Complement", Integer_End_City, "Me")));
		Beliefs_To_Change.put("BL_Position_Route", new ArrayList<>(Arrays.asList("Object_Complement", Integer_End_Route, "Me")));
		Beliefs_To_Change.put("BL_Position_Step", new ArrayList<>(Arrays.asList("Object_Complement", Integer_End_Step, "Me")));
		
		Beliefs_To_Change.put("BL_Previous_Position_City", new ArrayList<>(Arrays.asList("Object_Complement", Integer_Start_City, "Me")));
		Beliefs_To_Change.put("BL_Previous_Position_Route", new ArrayList<>(Arrays.asList("Object_Complement", Integer_Start_Route, "Me")));
		Beliefs_To_Change.put("BL_Previous_Position_Step", new ArrayList<>(Arrays.asList("Object_Complement", Integer_Start_Step, "Me")));
		
		Data.add( Beliefs_To_Change );

		result.Set_Result( true );
		result.Set_Beliefs_to_Change( Beliefs_To_Change );
	
		return result;
	}
	
	public TAction_Execution_Result Execute_Movement_Go_TO_Route(
			TGlobal_Workspace Global_Workspace, 
			TAction Action, HashMap<String, TBelief> Map_Beliefs )
	{
		TAction_Execution_Result result = new TAction_Execution_Result();
		/**
		 * 
		 */
		//The Preconditions and Posconditionsare: 0-City, 1-Route, 2-Step
		TPredicate Precondition_City_Position = Action.Get_Pre_conditions().get(0);
		TPredicate Precondition_Route_Position = Action.Get_Pre_conditions().get(1);
		TPredicate Precondition_Step_Position = Action.Get_Pre_conditions().get(2);
		
		TPredicate Postcondition_City_Position = Action.Get_Post_conditions().get(0);
		TPredicate Postcondition_Route_Position = Action.Get_Post_conditions().get(1);
		TPredicate Postcondition_Step_Position = Action.Get_Post_conditions().get(2);
		
		Integer Integer_Start_City = (Integer) Precondition_City_Position.Get_Object_Complement();
		Integer Integer_Start_Route = (Integer) Precondition_Route_Position.Get_Object_Complement();
		Integer Integer_Start_Step = (Integer) Precondition_Step_Position.Get_Object_Complement();
		
		Integer Integer_End_City = (Integer) Postcondition_City_Position.Get_Object_Complement();
		Integer Integer_End_Route = (Integer) Postcondition_Route_Position.Get_Object_Complement();
		Integer Integer_End_Step = (Integer) Postcondition_Step_Position.Get_Object_Complement();
		
		TEnvironment Map = (TEnvironment) Map_Beliefs.get("BL_Map").Get_Predicate()
									.Get_Object_Complement();

		Integer Integer_Route_For_Map = 0;
		if (Integer_End_Route < 0)
		{
			Integer_Route_For_Map = (Integer) Action.Get_Params().getFirst();
		}
		else
		{
			Integer_Route_For_Map = Integer_End_Route;
		}
		Common_Functions.Print("I enter Route: "+Action.Get_Params());
		
		TRoute the_route = Map.Get_Route(Integer_Route_For_Map);
		Integer Max_Steps = the_route.Get_Route_Speed();

		Float Time_to_Move = this.Global_Time_to_Move ;
		
		ArrayList<Point> Points = this.Demo.Gui_Map.Map_Panel.
								Ruotes_Coords.get(Integer_Route_For_Map);
		if(Integer_Start_Step<Integer_End_Step)
		{
			while(Integer_Start_Step<Integer_End_Step)
			{
				Integer_Start_Step++;
				Point A_Point = Points.get(Integer_Start_Step-1);
				this.Demo.Gui_Map.Map_Panel.Move_Car_Slowly
								(A_Point, Time_to_Move);
			}
		}
		else if(Integer_Start_Step == Integer_End_Step)
		{
			TRoute route = Map.All_Routes.get(Integer_Route_For_Map);
			
			while(Integer_Start_Step<route.Get_Steps_Number())
			{
				Integer_Start_Step++;
				Point A_Point = Points.get(Integer_Start_Step-1);
				this.Demo.Gui_Map.Map_Panel.Move_Car_Slowly(A_Point, Time_to_Move);
			}
		}
		
		/**
		 * 
		 */
		ArrayList<Object> Data = new ArrayList<Object>();

		HashMap<String, Object> Beliefs_To_Change = new HashMap<String, Object>();

		Beliefs_To_Change.put("BL_Position_City", new ArrayList<>(Arrays.asList("Object_Complement", Integer_End_City, "Me")));
		Beliefs_To_Change.put("BL_Position_Route", new ArrayList<>(Arrays.asList("Object_Complement", Integer_End_Route, "Me")));
		Beliefs_To_Change.put("BL_Position_Step", new ArrayList<>(Arrays.asList("Object_Complement", Integer_End_Step, "Me")));
		
		Beliefs_To_Change.put("BL_Previous_Position_City", new ArrayList<>(Arrays.asList("Object_Complement", Integer_Start_City, "Me")));
		Beliefs_To_Change.put("BL_Previous_Position_Route", new ArrayList<>(Arrays.asList("Object_Complement", Integer_Start_Route, "Me")));
		Beliefs_To_Change.put("BL_Previous_Position_Step", new ArrayList<>(Arrays.asList("Object_Complement", Integer_Start_Step, "Me")));
		
		
		
////		"BL_Current_Time"		
//		TBelief BL_Current_Time = Map_Beliefs.get("BL_Current_Time");
//		LocalDateTime Current_Time = (LocalDateTime) BL_Current_Time.Get_Predicate().Get_Object_Complement();
//		Current_Time = Current_Time.plusHours(1);
//		Beliefs_To_Change.put("BL_Current_Time", new ArrayList<>(Arrays.asList("Object_Complement", Current_Time, "Me")));
		Beliefs_To_Change.putAll( this.Update_Routes(Action, Map_Beliefs) );
		Beliefs_To_Change.putAll( this.Update_Current_Time(Action, Map_Beliefs) );
		
		
		if(Integer_End_Route == -1)
		{
			String City_Name = TCity.values()[Integer_End_City].toString();
			String Belief_Name = "BL_City_Visited_"+City_Name;
			
			Beliefs_To_Change.put(Belief_Name, new ArrayList<>(Arrays.asList("Object_Complement", true, "Me")));
			Common_Functions.Print("I'm arrived in "+City_Name);
			for(TBelief Belief: Map_Beliefs.values())
			{
				if( Belief.Get_Type_Belief().equals( "BLT_Destination_City" ))
				{
					TPredicate Predicate =Belief.Get_Predicate();
					if( Predicate.Get_Object_Complement().equals( City_Name ))
					{
						Common_Functions.Print("Practical Desire satisfied! I arrived in: "+City_Name);
					}
				}
			}
		}
		Data.add( Beliefs_To_Change );
		
//		result.Set_Object_First( (Boolean) true);
		result.Set_Result( true );
//		result.Set_Object_Second( Data );
		result.Set_Beliefs_to_Change( Beliefs_To_Change );
		return result;
	}
	
	public TAction_Execution_Result Execute_Configure_Travel(
			TGlobal_Workspace Global_Workspace,TAction Action, HashMap<String, TBelief> Map_Beliefs )
	{
		TAction_Execution_Result result = new TAction_Execution_Result();
		
		TBelief BL_Previous_Travel_Intention_Max_Satisfaction_Time = 
				Map_Beliefs.get("BL_Previous_Travel_Intention_Max_Satisfaction_Time");
		
		
		
		return result;
		
	}
	
	public TAction_Execution_Result Execute_Movement_GO_TO_Step(
			TGlobal_Workspace Global_Workspace,TAction Action, HashMap<String, TBelief> Map_Beliefs )
	{
		TAction_Execution_Result result = new TAction_Execution_Result();
		/**
		 * 
		 */
		//The Preconditions and Posconditionsare: 0-City, 1-Route, 2-Step
		TPredicate Precondition_City_Position = Action.Get_Pre_conditions().get(0);
		TPredicate Precondition_Route_Position = Action.Get_Pre_conditions().get(1);
		TPredicate Precondition_Step_Position = Action.Get_Pre_conditions().get(2);
		
		TPredicate Postcondition_City_Position = Action.Get_Post_conditions().get(0);
		TPredicate Postcondition_Route_Position = Action.Get_Post_conditions().get(1);
		TPredicate Postcondition_Step_Position = Action.Get_Post_conditions().get(2);
		
		Integer Integer_Start_City = (Integer) Precondition_City_Position.Get_Object_Complement();
		Integer Integer_End_City = (Integer) Postcondition_City_Position.Get_Object_Complement();
		
		Integer Integer_Start_Route = (Integer) Precondition_Route_Position.Get_Object_Complement();
		Integer Integer_End_Route = (Integer) Postcondition_Route_Position.Get_Object_Complement();
		
		Integer Integer_Start_Step = (Integer) Precondition_Step_Position.Get_Object_Complement();
		Integer Integer_End_Step = (Integer) Postcondition_Step_Position.Get_Object_Complement();
		
		String City_Name = TCity.values()[Integer_End_City].toString();
		
		Integer Minus = 0;
		
		if(Integer_End_Route==-1)
		{
			Minus = -1;
		}
		
		TEnvironment Map = (TEnvironment) Map_Beliefs.get("BL_Map").Get_Predicate()
									.Get_Object_Complement();
		
		TRoute the_route = Map.Get_Route(Integer_Start_Route);
		Integer Max_Steps = the_route.Get_Route_Speed();
		Float Time_to_Move = this.Global_Time_to_Move ;
		
		ArrayList<Point> Points = this.Demo.Gui_Map.Map_Panel.
								Ruotes_Coords.get(Integer_Start_Route);
		
		while(Integer_Start_Step<Integer_End_Step+Minus)
		{
			Integer_Start_Step++;
			Point A_Point = Points.get(Integer_Start_Step-1);
			this.Demo.Gui_Map.Map_Panel.Move_Car_Slowly(A_Point, Time_to_Move);
		}
		
		if(Integer_End_Route==-1)
		{
			
			TCity Next_Station = TCity.values()[Integer_End_City];
			Point A_Point = this.Demo.Gui_Map.Map_Panel.Stations_Coords.get(Next_Station);
			this.Demo.Gui_Map.Map_Panel.Move_Car_Slowly(A_Point, Time_to_Move);
		}
		
		/**
		 * 
		 */
		ArrayList<Object> Data = new ArrayList<Object>();

		HashMap<String, Object> Beliefs_To_Change = new HashMap<String, Object>();

		Beliefs_To_Change.put("BL_Position_City", new ArrayList<>(Arrays.asList("Object_Complement", Integer_End_City, "Me")));
		Beliefs_To_Change.put("BL_Position_Route", new ArrayList<>(Arrays.asList("Object_Complement", Integer_End_Route, "Me")));
		Beliefs_To_Change.put("BL_Position_Step", new ArrayList<>(Arrays.asList("Object_Complement", Integer_End_Step, "Me")));
		
		Beliefs_To_Change.put("BL_Previous_Position_City", new ArrayList<>(Arrays.asList("Object_Complement", Integer_Start_City, "Me")));
		Beliefs_To_Change.put("BL_Previous_Position_Route", new ArrayList<>(Arrays.asList("Object_Complement", Integer_Start_Route, "Me")));
		Beliefs_To_Change.put("BL_Previous_Position_Step", new ArrayList<>(Arrays.asList("Object_Complement", Integer_Start_Step, "Me")));
		
////		"BL_Current_Time"		
//		TBelief BL_Current_Time = Map_Beliefs.get("BL_Current_Time");
//		LocalDateTime Current_Time = (LocalDateTime) BL_Current_Time.Get_Predicate().Get_Object_Complement();
//		Current_Time = Current_Time.plusHours(1);
		Beliefs_To_Change.putAll( this.Update_Routes(Action, Map_Beliefs) );
		Beliefs_To_Change.putAll( this.Update_Current_Time(Action, Map_Beliefs) );
		
		
		
//		Beliefs_To_Change.put("BL_Current_Time", new ArrayList<>(Arrays.asList("Object_Complement", Current_Time, "Me")));
		
		/**
		 * If Integer_End_Route=0, the Agent is in a City, so I have to updates any Beliefs
		 * that has type equals to "BLT_City_Visited" and I must to check if a Final Belief for a
		 * Practical Desire is true
		 */
		String Belief_Name = "BL_City_Visited_"+City_Name;
		
		if(Integer_End_Route == -1)
		{
			
			Beliefs_To_Change.put(Belief_Name, new ArrayList<>(Arrays.asList("Object_Complement", true, "Me")));
			Common_Functions.Print("I'm arrived in "+City_Name);
			for(TBelief Belief: Map_Beliefs.values())
			{
				if( Belief.Get_Type_Belief().equals( "BLT_Destination_City" ))
				{
					TPredicate Predicate =Belief.Get_Predicate();
					if( Predicate.Get_Object_Complement().equals( City_Name ))
					{
						Common_Functions.Print("Practical Desire satisfied! I arrived in: "+City_Name);
					}
				}
				
//				if( Belief.Get_Type_Belief().equals( "BLT_Come_Back_to_City" ))
//				{
////					TPredicate Predicate =Belief.Get_Predicate();
////					if( Predicate.Get_Object_Complement().equals( City_Name ))
//					{
//						Common_Functions.Print("+++++++++++");
//						Common_Functions.Print("Practical Desire satisfied! I'm back in: "+City_Name);
//					}
//				}
				
				
			}
		}
		Data.add( Beliefs_To_Change );

//		result.Set_Object_First( (Boolean) true);
//		result.Set_Object_Second( Data );
		result.Set_Result( true );
		result.Set_Beliefs_to_Change( Beliefs_To_Change );
	
		return result;
	}
	
	/**
	 * 
	 * @param Positions
	 * @param Map_Beliefs
	 * @return
	 * Array of Objecft:
	 * 0 - an HashMap<String, Object> Beliefs_To_Change: belief to change
	 * 1 - a TPractical_Desire_Data: a Practical Desire with its associated function to handle it
	 */
	public TAction_Execution_Result Rise_Come_Back_Desire( ArrayList<Integer> Positions,
			HashMap<String, TBelief> Map_Beliefs)
	{
		/**
		 * 1)PRECONDITIONS
		 * For Desire Handler
			2)BELIEFS FOR MEANS-END REASONER FUNCTION
					 * For Reasoner
			3)REGIONS FOR MEANS-END REASONER FUNCTION
					 * For Reasoner
			4)
			a) Executive Means-End Reasoner Function to Analyze the Practical Desire
			b) Beliefs for Executive Inhibition Function
			c) Regions for Executive Inhibition Function 
		 */
		TAction_Execution_Result  Result = new TAction_Execution_Result();
		
		/**
		 * I have to compute all data for the Practical Desire "Come_Back_to_City"
		 */
		
		Integer City_position = Positions.get( 0 );
		Integer Route_position = Positions.get( 1 );
		Integer Step_position = Positions.get( 2 );
		
		String Come_Back_Name = TCity.values()[City_position].toString();
		
//		TBelief BL_Come_Back_to_City = Map_Beliefs.get("BLT_City_to_Refuel");
		TBelief BL_Come_Back_to_City = Map_Beliefs.get("BL_Come_Back_to_City");

		
//		TBelief BL_Position_City = Map_Beliefs.get("BL_Position_City");
//		TBelief BL_Previous_Position_City = Map_Beliefs.get("BL_Previous_Position_City");
//		TBelief BL_Next_Position_City = Map_Beliefs.get("BL_Next_Position_City");
		
//		Integer a1 = (Integer) BL_Come_Back_to_City.Get_Predicate().Get_Object_Complement();
//		Integer a2 = (Integer) BL_Position_City.Get_Predicate().Get_Object_Complement();
//		Integer a3 = (Integer) BL_Previous_Position_City.Get_Predicate().Get_Object_Complement();
//		Integer a4 = (Integer) BL_Next_Position_City.Get_Predicate().Get_Object_Complement();
		
		ArrayList<TGreen_Desire> List_Green_Desires = new ArrayList<TGreen_Desire>();
		ArrayList<TQuality_Desire> List_Quality_Desires = new ArrayList<TQuality_Desire>();
		
		/**
		 * TTemporal_Propositional_Formula 
		 */
		
		Double Saliency = 0.9;
		Double Reward = 0.4;
		Double Relax_Preference = 0.4;
		
		TBelief BL_Current_Time = Map_Beliefs.get("BL_Current_Time");
		if(BL_Current_Time ==null)
		{
			int oo=2;
		}
		LocalDateTime finally_Start = (LocalDateTime) BL_Current_Time.Get_Predicate().Get_Object_Complement();
		LocalDateTime finally_End = finally_Start.plusHours(10);
		
		
		ArrayList<TBelief> Final_State_Beliefs = new ArrayList<TBelief>();
		Final_State_Beliefs.add(BL_Come_Back_to_City);
		
		/**
		 * I Create the Temporal_Operator (Finally temporal Operator)
		 */
		TTemporal_Operator Temporal_Operator = new TTemporal_Operator();
		Temporal_Operator.Set_Type_Temporal_Operator( TType_Temporal_Operator.Finally );
		Temporal_Operator.Set_Start_Time( finally_Start );
		Temporal_Operator.Set_End_Time( finally_End );
		
		/**
		 * I Create the TTemporal_Propositional_Formula (Final State)
		 */
		TTemporal_Propositional_Formula Temporal_Propositional_Formula = 
				new TTemporal_Propositional_Formula();
		Temporal_Propositional_Formula.Set_Temporal_Operator( Temporal_Operator );
		Temporal_Propositional_Formula.Set_Beliefs( Final_State_Beliefs );
		Temporal_Propositional_Formula.Set_Formula( BL_Come_Back_to_City.Get_Name() );
		Temporal_Propositional_Formula.Set_List_Temporal_Propositional_Formula_Names(null);
		
		this.Practical_Desire_Number++;
		TPractical_Desire Come_Back_to_City = 
//				new TPractical_Desire("PD_Come_Back_to_City_"+this.Practical_Desire_Number,
				new TPractical_Desire("Go_Back",
						Temporal_Propositional_Formula, null, Saliency, Reward, 
						Relax_Preference, List_Green_Desires, List_Quality_Desires, 
						finally_Start, finally_End, 
						null, null, null, null, null, null);
		
		
		
		/**
		 * PRECONDITIONS
		 * For Desire Handler
		 * 
		 * The "Come_Back" Practical Desire has not preconditions.
		 * However, when a Practical Desire is added to Agent, its Preconditions are
		 * computed by the Agent by the Trigger conditions of the Practical Desire
		 *  
		 */
		
		/*
		 *  The Practical_Desire Come_Back_to_City has not preconditions.
		 *  The Preconditions for a Practical Desire are the beliefs in Trigger_Condition.
		 *  You can define that while you create the Practical_Desire.
		 */
		
		/**
		 * BELIEFS FOR MEANS-END REASONER FUNCTION
		 * For Reasoner
		 * 
		 * The "Come_Back" Practical Desire has Beliefs for Mean-End Reasoner. 
		 * These Beliefs are useful to compute all possible option to pursue for an intention.  
		 * So I register this Beliefs for Mean-End Reasoner in the Agent.
		 */
		
		/*
		 * We calculate beliefs for the Reasoner only once
		 */
		
		HashSet<String> Reasoner_Beliefs_Names = new HashSet<String>();
		Reasoner_Beliefs_Names.addAll( this.Demo.Functions_for_Belief_Inhibition_Function.
										Get_Desires_Beliefs_Functions().
											Reasoner_Beliefs_For_Come_Back(Come_Back_to_City));
		Come_Back_to_City.Set_Beliefs_Name_for_Reasoner( Reasoner_Beliefs_Names );

		/**
		 * REGIONS FOR MEANS-END REASONER FUNCTION
		 * For Reasoner
		 * 
		 * * The "Come_Back" Practical Desire has Regions for Mean-End Reasoner. 
		 * These Regions are useful to compute all possible option to pursue for an intention.  
		 * So I register this Beliefs for Mean-End Reasoner in the Agent.
		 */
		/*
		 *  We calculate regions for the Reasoner only once
		 */
		HashSet<String> Reasoner_Regions_Names = new HashSet<String>();

		Reasoner_Regions_Names.addAll( this.Demo.Functions_for_Belief_Inhibition_Function.
				Get_Desires_Regions_Functions().Reasoner_Get_Regions_for_Come_Back(Map_Beliefs));
		Come_Back_to_City.Set_Regions_Name_for_Reasoner( Reasoner_Regions_Names );
		
		
		/**
		 * Now, we have to associate the Function for:
		 * 1) Executive Means-End Reasoner Function to Analyze the Practical Desire
		 * 2) Beliefs for Executive Inhibition Function
		 * 3) Regions for Executive Inhibition Function 
		 */
		
		/*
		 *  We create the store the Practical Desire Come_Back_to_City
		 */
		
//		ArrayList<TPractical_Desire> Practical_Desires = new ArrayList<TPractical_Desire>();
//		Practical_Desires.add( Come_Back_to_City );
		TPractical_Desire_Data Practical_Desire_Data = new TPractical_Desire_Data();
		Practical_Desire_Data.Set_Practical_Desire( Come_Back_to_City );
		
		/**
		 * Function to invoke by MEANS-END REASONER FUNCTION
		 * 
		 * The "Come_Back" Function to invoke to compute the option for the intention
		 * to go back in previous city (the last visited city )
		 */
		
		//I insert Means End Reasoner Function
		Practical_Desire_Data.Set_Means_End_Reasoner_Function(
				this.Demo.Means_End_Reasoner_Function::Reasoner_Function_for_Come_Back_to_City
//					Get_Desires_Executive_Funtions()::Means_End_Function_Come_Back
				);
		
		/**
		 * Function to BELIEFS for Executive Inhibition Function 
		 */
		
		//I insert BELIEFS for Executive Inhibition Function 
		Practical_Desire_Data.Add_Beliefs_Inhibition_Functions(
				this.Belief_Inhibition_Functions.
					Get_Desires_Beliefs_Functions()::Inhibition_Beliefs_for_Come_Back);
		
		/**
		 * Function to REGIONS for Executive Inhibition Function 
		 */
		//I insert REGIONS for Executive Inhibition Function
		Practical_Desire_Data.Add_Regions_Inhibition_Functions( 
				this.Belief_Inhibition_Functions.
					Get_Desires_Regions_Functions()::Inhibition_Regions_for_Come_Back);
		
		ArrayList<TPractical_Desire_Data> Practical_Desires_Data = new ArrayList<TPractical_Desire_Data>();
		Practical_Desires_Data.add( Practical_Desire_Data );
		
		Result.Set_Practical_Desires(Practical_Desires_Data);
		
		
		/**
		 * BELIEFS TO UPDATE
		 */
		BL_Come_Back_to_City.Get_Predicate().set_Object_Complement(City_position);
		HashMap<String, Object> Beliefs_To_Change = new HashMap<String, Object>();
		Beliefs_To_Change.put("BL_Come_Back_to_City", new ArrayList<>(Arrays.asList("Object_Complement", City_position, "Me")));
//		Data.add(Beliefs_To_Change);
//		Result.add( Beliefs_To_Change );
		Result.Set_Result( true );
		Result.Set_Beliefs_to_Change( Beliefs_To_Change );
		
		
		return Result;
	}
	
//	public TQuadruple_Object Execution_Wait(TAction Action, HashMap<String, TBelief> Map_Beliefs )
//	{
//		TQuadruple_Object result = new TQuadruple_Object();
//		ArrayList<Object> Data = new ArrayList<Object>();
//
//		HashMap<String, Object> Beliefs_To_Change = new HashMap<String, Object>();
//
//		Beliefs_To_Change.putAll( this.Update_Routes(Action, Map_Beliefs) );
//		Beliefs_To_Change.putAll( this.Update_Current_Time(Action, Map_Beliefs) );
//		
//		Data.add( Beliefs_To_Change );
//
//		result.Set_Object_First( (Boolean) true);
//		result.Set_Object_Second( Data );
//		
//		return result;
//	}
	
	public HashMap<String, Object> Update_Routes(TAction Action, HashMap<String, TBelief> Map_Beliefs)
	{
		HashMap<String, Object> Beliefs_To_Change = new HashMap<String, Object>();
		
		TBelief BL_Map = Map_Beliefs.get("BL_Map");
		TEnvironment Map = (TEnvironment) BL_Map.Get_Predicate().Get_Object_Complement();
		
		TBelief BLT_Temporary_Closed_Routes = Map_Beliefs.get("BL_Temporary_Closed_Routes");
		
		HashMap<Integer, HashMap<Integer, Integer >>  
				Temporary_Closed_Routes = (HashMap<Integer, HashMap<Integer, Integer >>)
						BLT_Temporary_Closed_Routes.Get_Predicate().Get_Object_Complement() ;
		if( Temporary_Closed_Routes == null)
		{
			Temporary_Closed_Routes = new HashMap<Integer, HashMap<Integer,Integer>>();
			BLT_Temporary_Closed_Routes.Get_Predicate().
						set_Object_Complement( Temporary_Closed_Routes );
		}
		else
		{
			Boolean Changed_Route = false;
			Boolean Changed_Routes = false;
			for(Integer Route_Number: Temporary_Closed_Routes.keySet())
			{
				Changed_Route = false;
				HashMap<Integer, Integer > Steps_closed = 
						Temporary_Closed_Routes.get( Route_Number );
				for (Integer Step: Steps_closed.keySet())
				{
					Integer duration = Steps_closed.get( Step );
					duration--;
					if( duration == 0)
					{
						Changed_Route = true;
						Changed_Routes = true;
						Steps_closed.remove( Step );
					}
				}
				if (Changed_Route )
				{
					//Temporary_Closed_Routes
					if (Steps_closed.size() == 0)
					{
						Temporary_Closed_Routes.remove( Route_Number );
						String Belief_Route_Status = 
								"BL_Route_Status_"+Route_Number.toString();
//						TBelief BL_Route_Status = Map_Beliefs.get(Belief_Route_Status);
						Beliefs_To_Change.put(Belief_Route_Status, 
								new ArrayList<>(Arrays.asList("Object_Complement", "Green", "Me")));
//						Map.Get_Route( Route_Number ).Set_Route_Status("Green");
						
					}
					else
					{
						Temporary_Closed_Routes.put(Route_Number, Steps_closed);
					}
				}
				
			}
			if( Changed_Routes )
			{
				Beliefs_To_Change.put("BLT_Temporary_Closed_Routes", 
						new ArrayList<>(Arrays.asList("Object_Complement", Temporary_Closed_Routes, "Me")));
			}
		}
			
		return Beliefs_To_Change;
	}
	
	public HashMap<String, Object> Update_Current_Time(TAction Action, HashMap<String, TBelief> Map_Beliefs)
	{
		HashMap<String, Object> Beliefs_To_Change = new HashMap<String, Object>();
//		"BL_Current_Time"		
		TBelief BL_Current_Time = Map_Beliefs.get("BL_Current_Time");
		LocalDateTime Current_Time = (LocalDateTime) BL_Current_Time.Get_Predicate().Get_Object_Complement();
		Current_Time = Current_Time.plusHours(1);
		Beliefs_To_Change.put("BL_Current_Time", new ArrayList<>(Arrays.asList("Object_Complement", Current_Time, "Me")));
		return Beliefs_To_Change;
	}
	
	public TAction_Execution_Result Execute_Change_Direction(
			TGlobal_Workspace Global_Workspace, TAction Action, HashMap<String, TBelief> Map_Beliefs )
	{
		TAction_Execution_Result result = new TAction_Execution_Result();
		
		return result;
	}
	
	public TAction_Execution_Result Execute_Movement_Come_Back(TAction Action, HashMap<String, TBelief> Map_Beliefs )
	{
		TAction_Execution_Result result = new TAction_Execution_Result();
		
		return result;
	}
	
	public TAction_Execution_Result Execute_Decision(
			TGlobal_Workspace Global_Workspace, 
			TAction Action, HashMap<String, TBelief> Map_Beliefs 
			)
	{
		TAction_Execution_Result result = new TAction_Execution_Result();

		
		TBelief BL_Danger_on_the_Route = Map_Beliefs.get("BL_Danger_on_the_Route");
		TBelief BL_Dangeroues_Route_position = Map_Beliefs.get("BL_Dangerous_Position_Route");
		TBelief BL_Come_Back_to_City = Map_Beliefs.get("BL_Come_Back_to_City");
		
		TBelief BL_Temporary_Closed_Routes = Map_Beliefs.get("BL_Temporary_Closed_Routes");
		
		TBelief BL_Temporary_Closed_Duration = 
				Map_Beliefs.get("BL_Temporary_Closed_Duration");
		
//		TBelief BL_Previous_Travel_Intention_Max_Satisfaction_Time = 
//				Map_Beliefs.get("BL_Previous_Travel_Intention_Max_Satisfaction_Time");
//
//		LocalDateTime Max_Satisfaction_Time = 
//				(LocalDateTime) BL_Previous_Travel_Intention_Max_Satisfaction_Time.
//						Get_Predicate().Get_Object_Complement();
		
		Integer Dangeroues_Route_position = 
							(Integer) BL_Dangeroues_Route_position.Get_Predicate().
									Get_Object_Complement();
		
		TBelief BL_Position_City = Map_Beliefs.get("BL_Position_City");
		TBelief BL_Position_Route = Map_Beliefs.get("BL_Position_Route");
		TBelief BL_Position_Step = Map_Beliefs.get("BL_Position_Step");
		
//		TType_Danger Type_Danger = null;

		Integer City_position =  (Integer) BL_Position_City.Get_Predicate().Get_Object_Complement();
		Integer Route_position =  (Integer) BL_Position_Route.Get_Predicate().Get_Object_Complement();
		Integer Step_position =  (Integer) BL_Position_Step.Get_Predicate().Get_Object_Complement();
		
//		try {
//			TimeUnit.SECONDS.sleep(1);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		Integer Duration = (Integer) BL_Temporary_Closed_Duration.Get_Predicate().Get_Object_Complement();
		
		
		ArrayList<Integer> Danger_Data = new ArrayList<Integer>();
//		Danger_Data.addAll(this.Demo.Perception_Processing_functions.Virtual_TCS.Danger_Data);
		/**
		 * Danger_Data Index:
		 * 0 - City_position
		 * 1 - Route_position
		 * 2 - Step_position
		 * 3 - Dangeroues_Route_position
		 * 4 - Type_Danger.ordinal()
		 * 5 - Duration
		 */
		
		
		

		HashMap<String, Object> Beliefs_To_Change = new HashMap<String, Object>();
		
		
		if ( Route_position > -1)
		{
			TBelief BL_Previous_Travel_Intention_Max_Satisfaction_Time = 
					Map_Beliefs.get("BL_Previous_Travel_Intention_Max_Satisfaction_Time");

			LocalDateTime Max_Satisfaction_Time = 
					(LocalDateTime) BL_Previous_Travel_Intention_Max_Satisfaction_Time.
							Get_Predicate().Get_Object_Complement();
			
			TBelief BL_Current_Time = 
					Map_Beliefs.get("BL_Current_Time");
			
			LocalDateTime Current_Time = 
					(LocalDateTime) BL_Current_Time.
							Get_Predicate().Get_Object_Complement();
			
			LocalDateTime New_Current_Time = Current_Time.plusHours(Duration);

//			if(New_Current_Time.isBefore(Max_Satisfaction_Time) ||
//					New_Current_Time.isEqual(Max_Satisfaction_Time))
			if(New_Current_Time.isAfter(Max_Satisfaction_Time))
			{
//	            System.out.println("La differenza è minore o uguale di " + Duration + " ore.");
//	        }
//			else
//			{
//				System.out.println("La differenza è maggiore di " + Duration + " ore.");
//				
//			}
				ArrayList<Integer> Real_Positions = new ArrayList<Integer>();
				Real_Positions.add(City_position);
				Real_Positions.add(Route_position);
				Real_Positions.add(Step_position);
				
				TAction_Execution_Result Go_Back_Data = this.Rise_Come_Back_Desire(Real_Positions, Map_Beliefs);
				Beliefs_To_Change.putAll( Go_Back_Data.Get_Beliefs_to_Change() );
				result.Set_Practical_Desires( Go_Back_Data.Get_Practical_Desires() );
//			
////			for(TAttentional_Desire Desire: this.Demo.Agent.Get_Current_Attentional_Desires())
////			{
////				if(Desire.Get_Name().equals("PD_Visit_Cadiz"))
////				{
////					Cadiz = Desire;
////				}
////			}
////			if(Cadiz != null)
////			{
////				TBelief BL_Current_Time = Map_Beliefs.get("BL_Current_Time");
////				
////				Cadiz.Set_Saliency(0.99);
////				this.Demo.Agent.Get_Global_WorkSpace().Broadcast_Message(TType_Update_Contract.Standing_Desires);
////			}
////			result.Set_Object_Third((TDouble_Object) Go_Back_Data.get(1) );
//		}
////		Data.add( Beliefs_To_Change );
////		result.Set_Object_First( (Boolean) true);
//				System.out.println("Rised Come_Back_to_City Desire!");
			}
//		
		}
		result.Set_Result( true );
		return result;
		
	}
	
	
	TAction_Execution_Result Execute_Refuelling(
			TGlobal_Workspace Global_Workspace,
			TAction Action, HashMap<String, TBelief> Beliefs_Cloned )
	{
		TAction_Execution_Result result = new TAction_Execution_Result();
		Common_Functions.Print("I've reached the nearest town!\nNow I'm going to get fuel!");
		Common_Functions.Print("Now, I'm refueling");
		this.Wait_A_Time(1);
		Common_Functions.Print("I refueled!");
		Common_Functions.Print("Now I continue to fulfill my desires, if there are any");
		result.Set_Result(true);
		return result;
	}
	
	TAction_Execution_Result Execute_Declaration_of_Refuel(
			TGlobal_Workspace Global_Workspace,
			TAction Action, HashMap<String, TBelief> Beliefs_Cloned )
	{
		TAction_Execution_Result result = new TAction_Execution_Result();
		String City_to_refuel = ((TCity) Action.Get_Params().getFirst()).name();
		Common_Functions.Print("My fuel is low! I need to refuel.\n"
				+ "I go in "+ City_to_refuel  + " to refuel!");
		result.Set_Result(true);
		return result;
	}
	
	public void Wait_A_Time(int time) 
	{
		try 
		{
			Thread.sleep(time);
		} 
		catch (InterruptedException e) 
		{
			Thread.currentThread().interrupt();
		} 
	}
	
}
