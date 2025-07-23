package Catalina_Simulation;

import java.awt.Point;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JOptionPane;

/** 
 * It represents the agent in according to the CATALINA Architecture 
 */
public class TAgent {
	
	private TExecutive_Switching_Function E_Switching_Function;
	private TExecutive_Inhibition_Function E_Inhibition_function;
	private TGlobal_Workspace GW;
	private TExecutive_Reasoner_Function Reasoner;
	private TExecutive_Resource_Allocation E_Resource_Allocation;
	private TAgent_Status Status;
	private TExecutive_Working_Memory_Maintenance WMM;
	private TTCS TCS;
	
	//for demonstration only
	public int Working_Cycle = 0;
	private int Work_Cycles_Number = 300;
	
	/**
	 * Constructor class
	 */
	public TAgent()
	{
		this.E_Switching_Function = new TExecutive_Switching_Function(this);
		this.E_Inhibition_function = new TExecutive_Inhibition_Function(this);
		this.GW = new TGlobal_Workspace(this);
		this.Reasoner = new TExecutive_Reasoner_Function(this);
		this.E_Resource_Allocation = new TExecutive_Resource_Allocation(this);
		this.WMM = new TExecutive_Working_Memory_Maintenance(this);
		
		//Insert Executive functions and Reasoner in list of update data
		this.E_Switching_Function.Insert_in_List_Update_Contract();
		this.E_Inhibition_function.Insert_in_List_Update_Contract();
		this.Reasoner.Insert_in_List_Update_Contract();
		this.E_Resource_Allocation.Insert_in_List_Update_Contract();

		this.Status = TAgent_Status.Not_Active;
	}
	
	public void Initialize_True_Beliefs()
	{
		//I initialize the Belief_visited_station I am in
		ArrayList<TBelief> Beliefs = this.WMM.Get_Beliefs();
		
		TBelief Belief_Visited_Start= null;
		TBelief Belief_Current_Station= null;
		TBelief Belief_Current_Step= null;
		TBelief Belief_Current_Route= null;
		TBelief Belief_Current_Time= null;
		City A_Station = null;
		LocalDateTime A_Time = null;
		
		for(TBelief Belief: Beliefs )
		{
			if((Belief.Get_Type_Belief() == TType_Beliefs.Belief_Current_City) &&
			   (Belief.Get_Predicate().Get_Subject() == TType_Subject.Me	))
			{
				A_Station = (City) Belief.Get_Predicate().Get_Object_Complement();
				Belief_Current_Station = Belief;
				if(A_Station != null)
				{
					Point Starting_Point = Game.Gui_Map.Map_Panel.Stations_Coords.get(A_Station);
					Game.Gui_Map.Map_Panel.Move_Car_Slowly(Starting_Point, 3.0f);
				}
			}
			if((Belief.Get_Type_Belief() == TType_Beliefs.Belief_Current_Step) &&
					   (Belief.Get_Predicate().Get_Subject() == TType_Subject.Me	))
			{
				Belief_Current_Step = Belief;
			}
			if((Belief.Get_Type_Belief() == TType_Beliefs.Belief_Current_Route) &&
					   (Belief.Get_Predicate().Get_Subject() == TType_Subject.Me	))
			{
				Belief_Current_Route = Belief;
			}
			//I get the current time for the Agent
			if((Belief.Get_Type_Belief() == TType_Beliefs.Belief_Current_Time))
			{
				Belief_Current_Time = Belief;
				A_Time = (LocalDateTime) Belief_Current_Time.Get_Predicate().Get_Object_Complement();
			}
		}
		Belief_Current_Time.Set_Time_Stamp(A_Time);
		Belief_Current_Station.Set_Time_Stamp(A_Time);
		Belief_Current_Step.Set_Time_Stamp(A_Time);
		Belief_Current_Route.Set_Time_Stamp(A_Time);
		Belief_Current_Station.Set_Truth(true);
		Belief_Current_Step.Set_Truth(true);
		Belief_Current_Route.Set_Truth(true);
		
		for(TBelief Belief: Beliefs )
		{
			if((Belief.Get_Type_Belief() == TType_Beliefs.Belief_Visited_City) &&
			   ((City) Belief.Get_Predicate().Get_Subject() == A_Station	) &&
			   (Belief.Get_Predicate().Get_Object_Complement() == TType_Object_Complement.Me	))
			{
				Belief_Visited_Start = Belief;
				Belief_Visited_Start.Set_Truth(true);
				Belief_Visited_Start.Set_Time_Stamp(A_Time);
			}
		}
	}
	
	/**
	 * It initializes the agent. Following these params are for case study
	 * @param TCS		The Traffic Control Station
	 * @param Map		The environment of the case study
	 * @param Init_Creation_Path_Travelled_for_Functional_Standing_Desire	
	 * @return			A boolean value 
	 */
	public boolean Initialization(TTCS TCS, Environment Map, int Init_Creation_Path_Travelled_for_Functional_Standing_Desire)
	{
		boolean result = true;
		try 
		{
			Game.Scenario_Number = 0;
			this.GW.Print_Data(0, 0);
			this.Status = TAgent_Status.Initializing;
			this.GW.Update_Saliency_Threshold(this.Get_E_Inhibition_Function().Get_Default_Saliency_Threshold());
			this.GW.Update_Attention_Threshold(this.Get_E_Inhibition_Function().Get_Default_Attention_Threshold());
			
			this.TCS = TCS;
			this.WMM.Set_Map(Map);
			
			this.WMM.Set_Regions();
			
			Game.Print("I read predicates, beliefs, green, quality and functional goals.");
			// --Agent's Initialization--  
			//This Section is to Create All Beliefs (Belief_Current_Station, Belief_Current_Route, etc...)
			/***
			 * Order to read data:
			 * 1- Predicates
			 * 2- Beliefs
			 * 3- Green Goals
			 * 4- Quality Goals
			 * 5- Functional Goals
			 * 6- Epistemic Goals
			 */
			
			//1 - Get All Predicate
			this.Read_Predicates_From_File();
			
			
			//2 - Get All Beliefs (this normalize Beliefs with related Predicate
			this.Read_Beliefs_From_File();
			
			//2 - Get All Green_Standing_Desires 
			this.Read_Green_Standing_Desires_From_File();
			
			//2 - Get All uality_Standing_Desires
			this.Read_Quality_Standing_Desires_From_File();
			
			//2 - Get All Beliefs Functional_Standing_Desires
			this.Read_Functional_Standing_Desires_From_File();
			
			//System.out.println("6 - Get All Epistemic Goals (this normalize Epistemic Goals with related data");
			//2 - Get All Beliefs (this normalize Beliefs with related Predicate
			//This function has a bug, I have to check it
			//this.Read_Epistemic_Goals_From_File();
			
			
			
			// Create the Belief_Path_Taken_For_Belief linked to Functional Goal
			this.Create_Belief_Path_Taken_For_Functional_Standing_Desire(Init_Creation_Path_Travelled_for_Functional_Standing_Desire);
			
			//Initialize some true Beliefs
			this.Initialize_True_Beliefs();			
	
			HashMap<String, TPredicate> Map_Predicates = this.WMM.Get_Map_Predicates();
			
			ArrayList<TType_Beliefs> Beliefs_Type_list = new ArrayList<TType_Beliefs>();
			for(TBelief belief: this.WMM.Get_Beliefs())
			{
	
				TPredicate Predicate = null;
				Predicate = Map_Predicates.get(belief.Get_Predicate_name());
				
				if (!Beliefs_Type_list.contains(belief.Get_Type_Belief()))
				{
					Beliefs_Type_list.add(belief.Get_Type_Belief());
				}
				
				belief.Set_Predicate(Predicate);
			}
			
			this.Make_True_Any_Information_To_Print();
			
			this.GW.Print_Data(1, 7);
		}
		catch (Exception e) 
		{
		  Game.Print("Something went wrong in method: Insert_New_Desires.");
	      Game.Print("Message Error: "+e.getMessage());
	      Game.PrintLn();
	      e.printStackTrace();
	      result = false;
	    }
		return result;
	}
	
	public void Make_True_Any_Information_To_Print()
	{
		this.GW.Updated_Beliefs_for_print = true;
		this.GW.Updated_Standing_Desires_for_print = true;
		this.GW.Updated_Acrive_Desires_for_print = true;
		this.GW.Updated_Active_Desires_with_options_for_print = true;
		this.GW.Updated_Intentions_for_print = true;
		this.GW.Updated_Regions_for_print = true;
	}

	/**
	 * It returns the Executive Switching Function of the agent
	 * @return
	 */
	public TExecutive_Switching_Function Get_E_Switching_Function() {
		return this.E_Switching_Function;
	}
	
	/**
	 * It returns the Executive Inhibition Function of the agent
	 * @return
	 */
	public TExecutive_Inhibition_Function Get_E_Inhibition_Function()
	{
		return this.E_Inhibition_function;
	}

	/**
	 * It returns the Global Workspace of the agent
	 * @return
	 */
	public TGlobal_Workspace Get_GW() {
		return this.GW;
	}
	
	/**
	 * It returns the Reasoner of the agent
	 * @return
	 */
	public TExecutive_Reasoner_Function Get_Reasoner() {
		return this.Reasoner;
	}

	/**
	 * It returns the Executive Resource Allocation Function of the agent
	 * @return
	 */
	public TExecutive_Resource_Allocation Get_E_Resource_Allocation() {
		return this.E_Resource_Allocation;
	}
	
	/**
	 * Starts the working cycles of the agent
	 */
	public void Start()
	{
		// Agent Initialization
		
		Game.Scenario_Number++;

//		this.GW.Print_Data(0, 0);
		this.Status = TAgent_Status.Active;
		Game.Print("Now Agent Status is "+this.Status);
		boolean Res;
		boolean Res1;
		boolean Post_OK;
		Working_Cycle = 0;
		Game.Print("Agent Work Cycle start...");
		Game.Print("*************************");
		Game.PrintLn();
		while(this.Status == TAgent_Status.Active)
		{
			Game.Print("*************************");
			Game.Print("*************************");
			Game.Print("Working Cycle number: "+ Working_Cycle);
			Game.Print_Colored_Text("Stop before calling Perception_Processing method", 7);
//			Game.Get_Input("");

			Res = this.WMM.Perception_Processing(Working_Cycle);
			// this is for case study only
			if(Working_Cycle == Work_Cycles_Number)
			{
				Game.Gui_Map.Show_Message("Information...", "I have finished my execution. See you soon!",
						JOptionPane.INFORMATION_MESSAGE);
				Game.End_Game();				
			}
			Res = this.E_Inhibition_function.Desire_Deletion();
			if(Res)
			{
				this.GW.Broadcast();
//				Game.Get_Input("");

				Res = this.E_Switching_Function.Switching_to_stimulus();
//				}
				if( Res && !this.GW.Get_Updated_Active_Desires())
				{
					Res = this.E_Switching_Function.Desires_Promotion();
				}
				if( Res )
				{
					GW.Broadcast();
//					Game.Get_Input("");
					Res = this.Reasoner.MeansEnd();
					if( Res )
					{
						GW.Broadcast();
						
						Res = this.Reasoner.Deliberate_Process();
						if( Res )
						{
							GW.Broadcast();
							
							//Res = this.E_Inhibition_function.Focus_Attention();
							Res = this.E_Inhibition_function.Focus_Agent();
							Res1 = this.E_Switching_Function.UnFocus_Agent();
							if( Res && Res1)
							//if ( Res )
							{
								GW.Broadcast();
								
								Post_OK = this.E_Resource_Allocation.Plan_Advanc_Eval();
								Working_Cycle++;
								Game.Print_Colored_Text("Stop before starting again...", 7);
							}
						}
					}
				}
			}
		}
	}
	
	/**
	 * Reads the predicates file written by File_Manager in this project
	 */
	public void Read_Predicates_From_File()
	{
		TFile_Manager Manager = new TFile_Manager();

		ArrayList<TPredicate> Predicates = null;
		Predicates = Manager.Read_Predicates();
		this.WMM.Set_Predicates(Predicates);
	}
	
	/**
	 * Reads the predicates file written by File_Manager in this project
	 */
	public void Read_Beliefs_From_File()
	{
		TFile_Manager Manager = new TFile_Manager();
		
		ArrayList<TBelief> Beliefs = null;
		Beliefs = Manager.Read_Beliefs();
		this.WMM.Set_Beliefs(Beliefs);
	}
	
	/**
	 * Reads the Epistemic Goals file written by File_Manager in this project
	 */
	public void Read_Epistemic_Standing_Desires_From_File()
	{
		TFile_Manager Manager = new TFile_Manager();
		
		ArrayList<TEpistemic_Standing_Desire> Epistemic_Standing_Desires = null;
		Epistemic_Standing_Desires = Manager.Read_Epistemic_Standing_Desires();
		this.WMM.Set_Epistemic_Goals(Epistemic_Standing_Desires);
	}
	
	/**
	 * Reads the Functional Goals file written by File_Manager in this project
	 */
	public void Read_Functional_Standing_Desires_From_File()
	{
		TFile_Manager Manager = new TFile_Manager();
		
		ArrayList<TFunctional_Standing_Desire> Functional_Standing_Desires = null;
		Functional_Standing_Desires = Manager.Read_Functional_Standing_Desires();
		this.WMM.Set_Functional_Standing_Desires(Functional_Standing_Desires);
	}
	
	/**
	 * Reads the Green Goals file written by File_Manager in this project
	 */
	public void Read_Green_Standing_Desires_From_File()
	{
		TFile_Manager Manager = new TFile_Manager();
		
		ArrayList<TGreen_Standing_Desire> Green_Standing_Desires = null;
		Green_Standing_Desires = Manager.Read_Green_Standing_Desires();
		this.WMM.Set_Green_Standing_Desires(Green_Standing_Desires);
	}
	
	/**
	 * Reads the Quality Goals file written by File_Manager in this project
	 */
	public void Read_Quality_Standing_Desires_From_File()
	{
		TFile_Manager Manager = new TFile_Manager();
		
		ArrayList<TQuality_Standing_Desire> Quality_Standing_Desires = null;
		Quality_Standing_Desires = Manager.Read_Quality_Standing_Desires();
		this.WMM.Set_Quality_Standing_Desires(Quality_Standing_Desires);
	}
	
	/** This Function creates, for each destination Station, a belief (linked to a Funtional Goal)
	 * of the path of travelled routes to arrive to Destination Station 
	 */
	public void Create_Belief_Path_Taken_For_Functional_Standing_Desire(int Init_Creation_Path_Travelled_for_Functional_Standing_Desire)
	{
		HashMap<String, ArrayList<TPredicate>> Map_Attentional_Standing_Desire_and_Predicates = 
				this.WMM.Get_Map_Attentional_Standing_Desires_and_Predicates();
		HashMap<String, ArrayList<TBelief>> Map_Attentional_Standing_Desire_and_Beliefs = 
						this.WMM.Get_Map_Attentional_Standing_Desires_and_Beliefs();
		
		Game.Print("Loaded Functional Standing_Desires : "+Map_Attentional_Standing_Desire_and_Beliefs.keySet());
		
		this.Get_WMM().Set_Beliefs_Number(Init_Creation_Path_Travelled_for_Functional_Standing_Desire);
		for(TFunctional_Standing_Desire Functional_Standing_Desire: this.WMM.Get_Functional_Standing_Desires())
		{
			{
				if (Functional_Standing_Desire.Get_Final_State().Get_Type_Belief() == TType_Beliefs.Belief_Destination_City)
				{
					//
					TPredicate Predicate = this.GW.Create_Predicate(Functional_Standing_Desire.Get_Name(), TType_Relationship.has_traveled,
							new ArrayList<Integer>());
					LocalDateTime now = null;//LocalDateTime.now();

					//With Rosental...
					TBelief Belief = this.Get_GW().Create_Beliefs(Predicate, true, TType_Subject.Me, now, 
							TType_Beliefs.Belief_Path_Taken_For_Belief);
					ArrayList<TBelief> List_Beliefs = 
							Map_Attentional_Standing_Desire_and_Beliefs.get(Functional_Standing_Desire.Get_Name());
					
					ArrayList<TPredicate> List_Predicates = 
							Map_Attentional_Standing_Desire_and_Predicates.get(Functional_Standing_Desire.Get_Name());
					if(!List_Beliefs.contains(Belief))
					{
						List_Beliefs.add(Belief);						
					}
					if(!List_Predicates.contains(Predicate))
					{
						List_Predicates.add(Predicate);						
					}
				}
			}
		}
	}
	
	/**
	 * Returns the status of the Agent
	 * @return A Status value
	 */
	public TAgent_Status Get_Status()
	{
		return this.Status;
	}
	
	/**
	 * Prints any Predicates in Long Memory. For this project only
	 */
	private void Print_Predicates()
	{
		for (TPredicate Predicate: this.WMM.Get_Predicates())
		{
			Game.Print("Predicato3.get_Name() "+Predicate.Get_Name());
			Game.Print("Predicato3.get_Subject() "+Predicate.Get_Subject());
			Game.Print("Predicato3.get_Relationship() "+Predicate.Get_Relationship());
			Game.Print("Predicato3.get_Object_Complement() "+Predicate.Get_Object_Complement());			
		}
	}
	
	/**
	 * Prints any Beliefs in Long Memory. For this project only
	 */
	private void Print_Beliefs()
	{
		int i=0;
		for (TBelief Bel: this.WMM.Get_Beliefs())
		{
			i++;
			Game.Print("Beliefs "+i
					);
			TPredicate  Predicate = null;
			Predicate = Bel.Get_Predicate();
			Game.Print("Bel.get_Name "+Bel.Get_Name());
			Game.Print("Bel.get_Predicate_name "+Bel.Get_Predicate_name());
			Game.Print("Bel.is_Truth() "+Bel.Is_Truth());
			Game.Print("Bel.get_Information_Source() "+ Bel.Get_Information_Source());
			Game.Print("Bel.get_Time_stamp() "+Bel.Get_Time_stamp());
			Game.Print("Bel.get_Type_Belief() "+Bel.Get_Type_Belief());
			Game.Print("Predicato3.get_Name() "+Predicate.Get_Name());
			Game.Print("Predicato3.get_Subject() "+Predicate.Get_Subject());
			Game.Print("Predicato3.get_Relationship() "+Predicate.Get_Relationship());
			Game.Print("Predicato3.get_Object_Complement() "+Predicate.Get_Object_Complement());
			Game.Print("-------------------");
		}
		
	}
	
	/**
	 * Prints any Green Goals in Long Memory. For this project only
	 */
	private void Print_Green_Standing_Desires()
	{
		for (TGreen_Standing_Desire Green: this.WMM.Get_Green_Standing_Desires())
		{
			TPredicate  Predicate = null;
			Predicate = Green.Get_Constraint();
			Game.Print("Green.get_Name() "+Green.Get_Name());
			Game.Print("Green.get_Constraint_Name() "+Green.Get_Constraint_Name());
			Game.Print("Green.get_Type_Quality_Standing_Desire() "+Green.Get_Type_Quality_Standing_Desire());
			Game.Print("Green.get_TType_Green_Standing_Desire() "+Green.Get_TType_Green_Standing_Desire());
			Game.Print("Green.get_Fee() "+Green.Get_Fee());
			Game.Print("Green.get_Saliency() "+Green.Get_Saliency());
			Game.Print("Green.Check_Satisfation() "+Green.Check_Satisfation());
			Game.Print("Green.get_Reward() "+Green.Get_Reward());
			Game.Print("Green.get_Relax_Preference() "+Green.Get_Relax_Preference());
			
			Game.Print("Predicato3.get_Name() "+Predicate.Get_Name());
			Game.Print("Predicato3.get_Subject() "+Predicate.Get_Subject());
			Game.Print("Predicato3.get_Relationship() "+Predicate.Get_Relationship());
			Game.Print("Predicato3.get_Object_Complement() "+Predicate.Get_Object_Complement());
			Game.Print("-------------------");
		}
	}
	
	/**
	 * Prints any Quality Goals in Long Memory. For this project only
	 */
	private void Print_Quality_Standing_Desires()
	{
		for (TQuality_Standing_Desire Quality: this.WMM.Get_Quality_Goals())
		{
			 
			TPredicate  Predicate = null;
			Predicate = Quality.Get_Constraint();
			Game.Print("Quality.get_Name() "+Quality.Get_Name());
			Game.Print("Quality.get_Constraint_Name() "+Quality.Get_Constraint_Name());
			Game.Print("Quality.get_Type_Quality_Goal() "+Quality.Get_Type_Quality_Goal());
			Game.Print("Quality.get_Saliency() "+Quality.Get_Saliency());
			Game.Print("Quality.Check_Satisfation() "+Quality.Check_Satisfation());
			Game.Print("Quality.get_Reward() "+Quality.Get_Reward());
			Game.Print("Quality.get_Relax_Preference() "+Quality.Get_Relax_Preference());
			
			Game.Print("Predicato3.get_Name() "+Predicate.Get_Name());
			Game.Print("Predicato3.get_Subject() "+Predicate.Get_Subject());
			Game.Print("Predicato3.get_Relationship() "+Predicate.Get_Relationship());
			Game.Print("Predicato3.get_Object_Complement() "+Predicate.Get_Object_Complement());
			Game.Print("-------------------");
		}
	}
	
	/**
	 * Prints any Functional Goals in Long Memory. For this project only
	 */
	private void Print_Functional_Standing_Desires()
	{
		int i =0;
		ArrayList<TFunctional_Standing_Desire> Functional_Standing_Desires = this.WMM.Get_Functional_Standing_Desires();
		for(TFunctional_Standing_Desire Functional_Standing_Desire: Functional_Standing_Desires)
		{
			i++;
			Game.Print("-------------------------------");
			Game.Print("Functional Standing Desire n."+i);
			Game.Print("Functional_Standing_Desire.get_Name() "+Functional_Standing_Desire.Get_Name());
			Game.Print("Functional_Standing_Desire.get_Saliency() "+Functional_Standing_Desire.Get_Saliency());
			Game.Print("Functional_Standing_Desire.Check_Satisfation() "+Functional_Standing_Desire.Check_Satisfation());
			Game.Print("Functional_Standing_Desire.Check_Precondition() "+Functional_Standing_Desire.Check_Precondition());
			Game.Print("Functional_Standing_Desire.get_Reward() "+Functional_Standing_Desire.Get_Reward());
			Game.Print("Functional_Standing_Desire.get_Relax_Preference() "+Functional_Standing_Desire.Get_Relax_Preference());
			Game.Print("Functional_Standing_Desire.get_Sum_for_Attention_Treshold() "+Functional_Standing_Desire.Get_Sum_for_Attention_Treshold());
			Game.Print("Functional_Standing_Desire.get_Final_State_Name() "+Functional_Standing_Desire.Get_Final_State_Name());
			Game.Print("Functional_Standing_Desire.get_Trigger_Condition_Name() "+Functional_Standing_Desire.Get_Trigger_Condition_Name());
			
			
			TBelief Final_State = null;
			Final_State = Functional_Standing_Desire.Get_Final_State();
			Game.Print("- Final_State.get_Name() "+Final_State.Get_Name());
			Game.Print("- Final_State.get_Time_stamp() "+Final_State.Get_Time_stamp());
			Game.Print("- Final_State.get_Information_Source() "+Final_State.Get_Information_Source());
			Game.Print("- Final_State.get_Belief_ID() "+Final_State.Get_Belief_ID());
			Game.Print("- Final_State.get_Type_Belief() "+Final_State.Get_Type_Belief());
			Game.Print("- Final_State.get_Predicate_name() "+Final_State.Get_Predicate_name());
			Game.Print("- Final_State.get_Truth() "+Final_State.Get_Truth());
			
			TPredicate Final_Predicate = Final_State.Get_Predicate();
			Game.Print("- - Final_Predicate.get_Name() "+Final_Predicate.Get_Name());
			Game.Print("- - Final_Predicate.get_Predicate_ID() "+Final_Predicate.Get_Predicate_ID());
			Game.Print("- - Final_Predicate.get_Subject() "+Final_Predicate.Get_Subject());
			Game.Print("- - Final_Predicate.get_Relationship() "+Final_Predicate.Get_Relationship());
			Game.Print("- - Final_Predicate.get_Object_Complement() "+Final_Predicate.Get_Object_Complement());
			Game.Print("- - Final_Predicate.get_Linked_Belief_Name() "+Final_Predicate.Get_Linked_Belief_Name());
			
			TPredicate Functional_Predicate = Functional_Standing_Desire.Get_Trigger_Condition();
			if(Functional_Predicate != null)
			{
				Game.Print("- Functional_Predicate.get_Name() "+Functional_Predicate.Get_Name());
				Game.Print("- Functional_Predicate.get_Predicate_ID() "+Functional_Predicate.Get_Predicate_ID());
				Game.Print("- Functional_Predicate.get_Subject() "+Functional_Predicate.Get_Subject());
				Game.Print("- Functional_Predicate.get_Relationship() "+Functional_Predicate.Get_Relationship());
				Game.Print("- Functional_Predicate.get_Object_Complement() "+Functional_Predicate.Get_Object_Complement());
				Game.Print("- Functional_Predicate.get_Linked_Belief_Name() "+Functional_Predicate.Get_Linked_Belief_Name());
			}
			
			if(Functional_Standing_Desire.Get_List_Green_Standing_Desire() == null)
			{
				ArrayList<TGreen_Standing_Desire> List_Green_Standing_Desire = new ArrayList<TGreen_Standing_Desire>();
				Functional_Standing_Desire.Set_List_Green_Standing_Desire(List_Green_Standing_Desire);
			}
			Game.Print("Functional Green Standing_Desires "+Functional_Standing_Desire.Get_List_Green_Standing_Desire().size() );
			for (TGreen_Standing_Desire Green: Functional_Standing_Desire.Get_List_Green_Standing_Desire())
			{
				 
				TPredicate Predicate = Green.Get_Constraint();
				Game.Print("- Green.get_Name() "+Green.Get_Name());
				Game.Print("- Green.get_Constraint_Name() "+Green.Get_Constraint_Name());
				Game.Print("- Green.get_Type_Quality_Goal() "+Green.Get_Type_Quality_Standing_Desire());
				Game.Print("- Green.get_TType_Green_Goal() "+Green.Get_TType_Green_Standing_Desire());
				Game.Print("- Green.get_Fee() "+Green.Get_Fee());
				Game.Print("- Green.get_Saliency() "+Green.Get_Saliency());
				Game.Print("- Green.Check_Satisfation() "+Green.Check_Satisfation());
				Game.Print("- Green.get_Reward() "+Green.Get_Reward());
				Game.Print("- Green.get_Relax_Preference() "+Green.Get_Relax_Preference());
				
				Game.Print("- - Predicato3.get_Name() "+Predicate.Get_Name());
				Game.Print("- - Predicato3.get_Subject() "+Predicate.Get_Subject());
				Game.Print("- - Predicato3.get_Relationship() "+Predicate.Get_Relationship());
				Game.Print("- - Predicato3.get_Object_Complement() "+Predicate.Get_Object_Complement());
				Game.Print("-------------------");
			}
			if(Functional_Standing_Desire.Get_List_Quality_Standing_Desire() == null)
			{
				ArrayList<TQuality_Standing_Desire> List_Quality_Standing_Desire = new ArrayList<TQuality_Standing_Desire>();
				Functional_Standing_Desire.Set_List_Quality_Standing_Desire(List_Quality_Standing_Desire);
			}
			Game.Print("Functional Quality Standing_Desires "+Functional_Standing_Desire.Get_List_Quality_Standing_Desire().size() );
			for (TQuality_Standing_Desire Quality: Functional_Standing_Desire.Get_List_Quality_Standing_Desire())
			{
				 
				TPredicate  Predicate = null;
				Predicate = Quality.Get_Constraint();
				Game.Print("Quality.get_Name() "+Quality.Get_Name());
				Game.Print("Quality.get_Constraint_Name() "+Quality.Get_Constraint_Name());
				Game.Print("Quality.get_Type_Quality_Goal() "+Quality.Get_Type_Quality_Goal());
				Game.Print("Quality.get_Saliency() "+Quality.Get_Saliency());
				Game.Print("Quality.Check_Satisfation() "+Quality.Check_Satisfation());
				Game.Print("Quality.get_Reward() "+Quality.Get_Reward());
				Game.Print("Quality.get_Relax_Preference() "+Quality.Get_Relax_Preference());
				
				Game.Print("Predicato3.get_Name() "+Predicate.Get_Name());
				Game.Print("Predicato3.get_Subject() "+Predicate.Get_Subject());
				Game.Print("Predicato3.get_Relationship() "+Predicate.Get_Relationship());
				Game.Print("Predicato3.get_Object_Complement() "+Predicate.Get_Object_Complement());
				Game.Print("-------------------");
			}			
		}
	}
	
	/**
	 * Prints any Epistemic Goals in Long Memory. For this project only
	 */
	private void Print_Epistemic_Standing_Desires()
	{
		int i =0;
		ArrayList<TEpistemic_Standing_Desire> Epistemic_Standing_Desires = this.WMM.Get_Epistemic_Standing_Desires();
		for(TEpistemic_Standing_Desire Functional_Standing_Desire: Epistemic_Standing_Desires)
		{
			i++;
			Game.Print("-------------------------------");
			Game.Print("Epistemic_Standing_Desire Standing_Desire n."+i);
			Game.Print("Epistemic_Standing_Desire.get_Name() "+Functional_Standing_Desire.Get_Name());
			Game.Print("Epistemic_Standing_Desire.get_Saliency() "+Functional_Standing_Desire.Get_Saliency());
			Game.Print("Epistemic_Standing_Desire.Check_Satisfation() "+Functional_Standing_Desire.Check_Satisfation());
			Game.Print("Epistemic_Standing_Desire.Check_Precondition() "+Functional_Standing_Desire.Check_Precondition());
			Game.Print("Epistemic_Standing_Desire.get_Reward() "+Functional_Standing_Desire.Get_Reward());
			Game.Print("Epistemic_Standing_Desire.get_Relax_Preference() "+Functional_Standing_Desire.Get_Relax_Preference());
			Game.Print("Epistemic_Standing_Desire.get_Sum_for_Attention_Treshold() "+Functional_Standing_Desire.Get_Sum_for_Attention_Treshold());
			Game.Print("Epistemic_Standing_Desire.get_Belief_Name() "+Functional_Standing_Desire.Get_Belief_Name());
			
			
			TBelief Final_State = null;
			Final_State = Functional_Standing_Desire.Get_Belief();
			Game.Print("- Final_State.get_Name() "+Final_State.Get_Name());
			Game.Print("- Final_State.get_Time_stamp() "+Final_State.Get_Time_stamp());
			Game.Print("- Final_State.get_Information_Source() "+Final_State.Get_Information_Source());
			Game.Print("- Final_State.get_Belief_ID() "+Final_State.Get_Belief_ID());
			Game.Print("- Final_State.get_Type_Belief() "+Final_State.Get_Type_Belief());
			Game.Print("- Final_State.get_Predicate_name() "+Final_State.Get_Predicate_name());
			Game.Print("- Final_State.get_Truth() "+Final_State.Get_Truth());
			
			TPredicate Final_Predicate = Final_State.Get_Predicate();
			Game.Print("- - Final_Predicate.get_Name() "+Final_Predicate.Get_Name());
			Game.Print("- - Final_Predicate.get_Predicate_ID() "+Final_Predicate.Get_Predicate_ID());
			Game.Print("- - Final_Predicate.get_Subject() "+Final_Predicate.Get_Subject());
			Game.Print("- - Final_Predicate.get_Relationship() "+Final_Predicate.Get_Relationship());
			Game.Print("- - Final_Predicate.get_Object_Complement() "+Final_Predicate.Get_Object_Complement());
			Game.Print("- - Final_Predicate.get_Linked_Belief_Name() "+Final_Predicate.Get_Linked_Belief_Name());

			if(Functional_Standing_Desire.Get_List_Green_Standing_Desire() == null)
			{
				ArrayList<TGreen_Standing_Desire> List_Green_Standing_Desire = new ArrayList<TGreen_Standing_Desire>();
				Functional_Standing_Desire.Set_List_Green_Standing_Desire(List_Green_Standing_Desire);
			}
			Game.Print("Epistemic Green Standing_Desires "+Functional_Standing_Desire.Get_List_Green_Standing_Desire().size() );
			for (TGreen_Standing_Desire Green: Functional_Standing_Desire.Get_List_Green_Standing_Desire())
			{
				 
				TPredicate Predicate = Green.Get_Constraint();
				Game.Print("- Green.get_Name() "+Green.Get_Name());
				Game.Print("- Green.get_Constraint_Name() "+Green.Get_Constraint_Name());
				Game.Print("- Green.get_Type_Quality_Goal() "+Green.Get_Type_Quality_Standing_Desire());
				Game.Print("- Green.get_TType_Green_Goal() "+Green.Get_TType_Green_Standing_Desire());
				Game.Print("- Green.get_Fee() "+Green.Get_Fee());
				Game.Print("- Green.get_Saliency() "+Green.Get_Saliency());
				Game.Print("- Green.Check_Satisfation() "+Green.Check_Satisfation());
				Game.Print("- Green.get_Reward() "+Green.Get_Reward());
				Game.Print("- Green.get_Relax_Preference() "+Green.Get_Relax_Preference());
				
				Game.Print("- - Predicato3.get_Name() "+Predicate.Get_Name());
				Game.Print("- - Predicato3.get_Subject() "+Predicate.Get_Subject());
				Game.Print("- - Predicato3.get_Relationship() "+Predicate.Get_Relationship());
				Game.Print("- - Predicato3.get_Object_Complement() "+Predicate.Get_Object_Complement());
				Game.Print("-------------------");
			}
			if(Functional_Standing_Desire.Get_List_Quality_Standing_Desire() == null)
			{
				ArrayList<TQuality_Standing_Desire> List_Quality_Standing_Desire = new ArrayList<TQuality_Standing_Desire>();
				Functional_Standing_Desire.Set_List_Quality_Standing_Desire(List_Quality_Standing_Desire);
			}
			Game.Print("Functional Quality Standing_Desires "+Functional_Standing_Desire.Get_List_Quality_Standing_Desire().size() );
			for (TQuality_Standing_Desire Quality: Functional_Standing_Desire.Get_List_Quality_Standing_Desire())
			{
				 
				TPredicate  Predicate = null;
				Predicate = Quality.Get_Constraint();
				Game.Print("Quality.get_Name() "+Quality.Get_Name());
				Game.Print("Quality.get_Constraint_Name() "+Quality.Get_Constraint_Name());
				Game.Print("Quality.get_Type_Quality_Goal() "+Quality.Get_Type_Quality_Goal());
				Game.Print("Quality.get_Saliency() "+Quality.Get_Saliency());
				Game.Print("Quality.Check_Satisfation() "+Quality.Check_Satisfation());
				Game.Print("Quality.get_Reward() "+Quality.Get_Reward());
				Game.Print("Quality.get_Relax_Preference() "+Quality.Get_Relax_Preference());
				
				Game.Print("Predicato3.get_Name() "+Predicate.Get_Name());
				Game.Print("Predicato3.get_Subject() "+Predicate.Get_Subject());
				Game.Print("Predicato3.get_Relationship() "+Predicate.Get_Relationship());
				Game.Print("Predicato3.get_Object_Complement() "+Predicate.Get_Object_Complement());
				Game.Print("-------------------");
			}			
		}
	}
	
	/**
	 * Return Executive Working Memomry Maintenance of the agent
	 * @return
	 */
	public TExecutive_Working_Memory_Maintenance Get_WMM()
	{
		return this.WMM;
	}
	
	/**
	 * Return Traffic Control Service of the agent
	 * @return
	 */
	public TTCS Get_TCS()
	{
		return this.TCS;
	}
	
	/**
	 * Inserts a Functional Goal in the agent. The params are the same of the constructor that creates
	 * the Functional Goal, and change the update_goals of Global workspace
	 * @param name
	 * @param final_state_Name
	 * @param trigger_Condition_Name
	 * @param saliency
	 * @param reward
	 * @param relax_Preference
	 * @param list_Green_Standing_Desire_Name
	 * @param list_Quality_Standing_Desire_Name
	 * @param finally_Start
	 * @param finally_End
	 * @param global_Start
	 * @param global_End
	 * @param untill_Start
	 * @param until_End
	 */
	public void Insert_Functional_Standing_Desire(String name, String final_state_Name, String trigger_Condition_Name,
			Double saliency, Double reward, Double relax_Preference,
			ArrayList<String> list_Green_Standing_Desire_Name, ArrayList<String> list_Quality_Standing_Desire_Name,
			LocalDateTime finally_Start, LocalDateTime finally_End, LocalDateTime global_Start, 
			LocalDateTime global_End, LocalDateTime untill_Start, LocalDateTime until_End )
	{
		ArrayList<TFunctional_Standing_Desire> Functional_Standing_Desires = new ArrayList<TFunctional_Standing_Desire>();
		
		TFunctional_Standing_Desire Functional_Standing_Desire = new TFunctional_Standing_Desire(name, final_state_Name, 
				trigger_Condition_Name, saliency, reward, relax_Preference, list_Green_Standing_Desire_Name, 
				list_Quality_Standing_Desire_Name, finally_Start, finally_End, global_Start, global_End, 
				untill_Start, until_End);
		
		Functional_Standing_Desires.add(Functional_Standing_Desire);
		this.WMM.Set_Functional_Standing_Desires(Functional_Standing_Desires);
	}
	
	/**
	 * Inserts a Epistemic Goal in the agent. The params are the same of the constructor that creates
	 * the Epistemic Goal, and change the update_goals of Global workspace
	 * @param name
	 * @param belief_Name
	 * @param saliency
	 * @param reward
	 * @param relax_Preference
	 * @param list_Green_Standing_Desire_Name
	 * @param list_Quality_Standing_Desire_Name
	 */
	public void Insert_Epistemic_Standing_Desire(String name, String belief_Name, Double saliency, Double reward, 
			Double relax_Preference, ArrayList<String> list_Green_Standing_Desire_Name, 
			ArrayList<String> list_Quality_Standing_Desire_Name)
	{
		ArrayList<TEpistemic_Standing_Desire> Epistemic_Standing_Desires = new ArrayList<TEpistemic_Standing_Desire>();
		
		TEpistemic_Standing_Desire Epistemic_Standing_Desire = new TEpistemic_Standing_Desire(name, belief_Name, saliency, reward, 
				relax_Preference, list_Green_Standing_Desire_Name, list_Quality_Standing_Desire_Name);
		
		Epistemic_Standing_Desires.add(Epistemic_Standing_Desire);
		this.WMM.Set_Epistemic_Goals(Epistemic_Standing_Desires);
	}
	
	/**
	 * Inserts a Quality Goal in the agent. The params are the same of the constructor that creates
	 * the Quality Goal
	 * @param name
	 * @param constraint_name
	 * @param type_Quality_Standing_Desire
	 * @param saliency
	 * @param reward
	 * @param relax_Preference
	 */
	public void Insert_Quality_Standing_Desire(String name, String constraint_name, 
			TType_Quality_Desire type_Quality_Standing_Desire, Double saliency, Double reward,
			Double relax_Preference)
	{
		
		ArrayList<TQuality_Standing_Desire> Quality_Standing_Desires = new ArrayList<TQuality_Standing_Desire>();
		
		TQuality_Standing_Desire Quality_Standing_Desire = new TQuality_Standing_Desire(name, constraint_name, type_Quality_Standing_Desire, 
				saliency, reward, relax_Preference);
		
		Quality_Standing_Desires.add(Quality_Standing_Desire);
		this.WMM.Set_Quality_Standing_Desires(Quality_Standing_Desires);
	}
	
	/**
	 * Inserts a Green Goal in the agent. The params are the same of the constructor that creates
	 * the Green Goal
	 * @param name
	 * @param constraint_name
	 * @param type_Green_Standing_Desire
	 * @param saliency
	 * @param reward
	 * @param fee
	 */
	public void Insert_Green_Standing_Desire(String name, String constraint_name, 
			TType_Green_Desire type_Green_Standing_Desire, Double saliency, Double reward, Double fee)
	{
		ArrayList<TGreen_Standing_Desire> Green_Standing_Desires = new ArrayList<TGreen_Standing_Desire>();
		
		TGreen_Standing_Desire Green_Standing_Desire = new TGreen_Standing_Desire(name, constraint_name, type_Green_Standing_Desire, saliency, 
				reward, fee);
		
		Green_Standing_Desires.add(Green_Standing_Desire);
		this.WMM.Set_Green_Standing_Desires(Green_Standing_Desires);
	}
	
	/**
	 * Inserts a Belief in the agent. The params are the same of the constructor that creates
	 * the Belief
	 * @param name
	 * @param predicate_Name
	 * @param truth
	 * @param information_Source
	 * @param time_stamp
	 * @param type_Belief
	 */
	public void Insert_Belief(String name, String predicate_Name, Boolean truth, 
			Object information_Source, LocalDateTime time_stamp, TType_Beliefs type_Belief)
	{
		ArrayList<TBelief> Beliefs = new ArrayList<TBelief>();
		
		TBelief Belief = new TBelief(name, predicate_Name, truth, information_Source, 
				time_stamp, type_Belief);
		
		Beliefs.add(Belief);
		this.WMM.Set_Beliefs(Beliefs);
	}
	
	/**
	 * Inserts a Predicate in the agent. The params are the same of the constructor that creates
	 * the Predicate
	 * @param name
	 * @param subject
	 * @param relationship
	 * @param object_complement
	 */
	public void Insert_Predicate(String name, Object subject, TType_Relationship relationship, 
			Object object_complement)
	{
		ArrayList<TPredicate> Predicates = new ArrayList<TPredicate>();
		
		TPredicate Predicate = new TPredicate(name, subject, relationship, object_complement);
		
		Predicates.add(Predicate);
		this.WMM.Set_Predicates(Predicates);
	}
}