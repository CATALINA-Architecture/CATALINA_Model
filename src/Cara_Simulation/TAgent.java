package Cara_Simulation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

/** 
 * It represents the agent in according to the CATALINA Architecture 
 */
public class TAgent {
	
	private TE_Switching_Function E_Switching_Function;
	private TE_Inhibition_function E_Inhibition_function;
	private TGlobalWorkspace GW;
	private TReasoner_Function Reasoner;
	private TResource_Allocation E_Resource_A;
	private TAgent_Status Status;
	private TWorking_Memory_Maintenance WMM;
	private TTCS TCS;
	
	/**
	 * Constructor class
	 */
	public TAgent()
	{
		this.E_Switching_Function = new TE_Switching_Function(this);
		this.E_Inhibition_function = new TE_Inhibition_function(this);
		this.GW = new TGlobalWorkspace(this);
		this.Reasoner = new TReasoner_Function(this);
		this.E_Resource_A = new TResource_Allocation(this);
		this.WMM = new TWorking_Memory_Maintenance(this);

		this.Status = TAgent_Status.Not_Active;
	}
	
	/**
	 * It initializes the agent. Following these params are for case study
	 * @param TCS		The Traffic Control Station
	 * @param Map		The environment of the case study
	 * @param Init_Creation_Path_Travelled_for_Functional_Goal	
	 * @return			A boolean value 
	 */
	public boolean Initialization(TTCS TCS, Environment Map, int Init_Creation_Path_Travelled_for_Functional_Goal)
	{
		boolean result = true;
		try 
		{
			Game.Scenario_Number = 0;
			this.GW.Print_Data(0, 0);
			this.Status = TAgent_Status.Initializing;
			this.GW.Update_Saliency_Threshold(this.Get_E_Inhibition_function().Get_Default_Saliency_Threshold());
			this.GW.Update_Attention_Threshold(this.Get_E_Inhibition_function().Get_Default_Attention_Threshold());
			
			this.TCS = TCS;
			this.WMM.Set_Map(Map);
			
			this.WMM.Set_Regions();
			
			Game.Print("I read predicates, beliefs, functional, green and quality goals");
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
			
			//2 - Get All Beliefs (this normalize Beliefs with related Predicate
			this.Read_Green_Goals_From_File();
			
			//2 - Get All Beliefs (this normalize Beliefs with related Predicate
			this.Read_Quality_Goals_From_File();
			
			//2 - Get All Beliefs (this normalize Beliefs with related Predicate
			this.Read_Functional_Goals_From_File();
			
			//System.out.println("6 - Get All Epistemic Goals (this normalize Epistemic Goals with related data");
			//2 - Get All Beliefs (this normalize Beliefs with related Predicate
			//This function has a bug, I have to check it
			//this.Read_Epistemic_Goals_From_File();
			
			
			// Create the Belief_Path_Taken_For_Belief linked to Functional Goal
			this.Create_Belief_Path_Taken_For_Functional_Goal(Init_Creation_Path_Travelled_for_Functional_Goal);
	
			HashMap<String, TPredicate> Map_Predicates = this.WMM.Get_Map_Predicates();
			
			ArrayList<TType_Beliefs> Beliefs_Type_list = new ArrayList<TType_Beliefs>();
			for(TBelief_Base belief: this.WMM.Get_Beliefs())
			{
	
				TPredicate Predicato = null;
				Predicato = Map_Predicates.get(belief.get_Predicate_name());
				
				if (!Beliefs_Type_list.contains(belief.get_Type_Belief()))
				{
					Beliefs_Type_list.add(belief.get_Type_Belief());
				}
				
				belief.set_Predicate(Predicato);
			}
			
			this.GW.Print_Data(1, 0);
		}
		catch (Exception e) {
			Game.Print("Something went wrong in method: Insert_New_Desires.");
	      Game.Print("Message Error: "+e.getMessage());
	      Game.PrintLn();
	      e.printStackTrace();
	      result = false;
	    }
		return result;
	}

	/**
	 * It returns the Executive Switching Function of the agent
	 * @return
	 */
	public TE_Switching_Function Get_E_Switching_Function() {
		return this.E_Switching_Function;
	}
	
	/**
	 * It returns the Executive Inhibition Function of the agent
	 * @return
	 */
	public TE_Inhibition_function Get_E_Inhibition_function()
	{
		return this.E_Inhibition_function;
	}

	/**
	 * It returns the Global Workspace of the agent
	 * @return
	 */
	public TGlobalWorkspace Get_GW() {
		return this.GW;
	}
	
	/**
	 * It returns the Reasoner of the agent
	 * @return
	 */
	public TReasoner_Function Get_Reasoner() {
		return this.Reasoner;
	}

	/**
	 * It returns the Executive Resource Allocation Function of the agent
	 * @return
	 */
	public TResource_Allocation Get_E_Resource_A() {
		return this.E_Resource_A;
	}
	
	/**
	 * Starts the working cycles of the agent
	 */
	public void Start()
	{
		Game.Scenario_Number++;

		this.GW.Print_Data(0, 0);
		this.Status = TAgent_Status.Active;
		Game.Print("Now Agent Status is "+this.Status);
		boolean Res;
		boolean Post_OK;
		int Working_Cycle = 0;
		while(this.Status == TAgent_Status.Active)
		{
			Game.Print("Working_Cycle; "+ Working_Cycle);
			Res = this.WMM.Perception_Processing(Working_Cycle);
			// this is for case study only
			if(Working_Cycle == 4)
			{
				Game.End_Game();				
			}
			if(Res)
			{
				this.GW.Broadcast();
				Res = this.E_Switching_Function.AM_Exogenous_Module();
				if( Res && !this.GW.Get_Updated_Desires())
				{
					Res = this.E_Switching_Function.AM_Endogenous_Module();
					if( Res )
						GW.Broadcast();
				}
				if( Res )
				{
					Res = this.Reasoner.MeansEnd();
					if( Res )
					{
						GW.Broadcast();
						Res = this.Reasoner.Deliberate_Process();
						if( Res )
						{
							GW.Broadcast();
							Res = this.E_Inhibition_function.Focus_Attention();
							if( Res )
							{
								GW.Broadcast();
								Post_OK = this.E_Resource_A.Plan_Advanc_Eval();
								Working_Cycle++;
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
		
		ArrayList<TBelief_Base> Beliefs = null;
		Beliefs = Manager.Read_Beliefs();
		this.WMM.Set_Beliefs(Beliefs);
	}
	
	/**
	 * Reads the Epistemic Goals file written by File_Manager in this project
	 */
	public void Read_Epistemic_Goals_From_File()
	{
		TFile_Manager Manager = new TFile_Manager();
		
		ArrayList<TEpistemic_Goal> Epistemic_Goals = null;
		Epistemic_Goals = Manager.Read_Epistemic_Goals();
		this.WMM.Set_Epistemic_Goals(Epistemic_Goals);
	}
	
	/**
	 * Reads the Functional Goals file written by File_Manager in this project
	 */
	public void Read_Functional_Goals_From_File()
	{
		TFile_Manager Manager = new TFile_Manager();
		
		ArrayList<TFunctional_Goal> Functional_Goals = null;
		Functional_Goals = Manager.Read_Functional_Goals();
		this.WMM.Set_Functional_Goals(Functional_Goals);
	}
	
	/**
	 * Reads the Green Goals file written by File_Manager in this project
	 */
	public void Read_Green_Goals_From_File()
	{
		TFile_Manager Manager = new TFile_Manager();
		
		ArrayList<TGreen_Goal> Green_Goals = null;
		Green_Goals = Manager.Read_Green_Goals();
		this.WMM.Set_Green_Goals(Green_Goals);
	}
	
	/**
	 * Reads the Quality Goals file written by File_Manager in this project
	 */
	public void Read_Quality_Goals_From_File()
	{
		TFile_Manager Manager = new TFile_Manager();
		
		ArrayList<TQuality_Goal> Quality_Goals = null;
		Quality_Goals = Manager.Read_Quality_Goals();
		this.WMM.Set_Quality_Goals(Quality_Goals);
	}
	
	/** This Function creates, for each destination Station, a belief (linked to a Funtional Goal)
	 * of the path of travelled routes to arrive to Destination Station 
	 */
	public void Create_Belief_Path_Taken_For_Functional_Goal(int Init_Creation_Path_Travelled_for_Functional_Goal)
	{
		HashMap<String, ArrayList<TPredicate>> Map_Attentional_Goal_and_Predicates = 
				this.WMM.Get_Map_Attentional_Goals_and_Predicates();
		HashMap<String, ArrayList<TBelief_Base>> Map_Attentional_Goal_and_Beliefs = 
						this.WMM.Get_Map_Attentional_Goals_and_Beliefs();
		
		Game.Print("Loaded Goals : "+Map_Attentional_Goal_and_Beliefs.keySet());
		
		this.Get_WMM().Set_Beliefs_Number(Init_Creation_Path_Travelled_for_Functional_Goal);
		for(TFunctional_Goal Functional_Goal: this.WMM.Get_Functional_Goals())
		{
//			if (Goal instanceof TFunctional_Goal)
			{
				if (Functional_Goal.get_Final_State().get_Type_Belief() == TType_Beliefs.Belief_Destination_Station)
				{
					TPredicate Predicate = this.GW.Create_Predicate(Functional_Goal.get_Name(), TType_Relationship.has_traveled,
							new ArrayList<Route>());
					LocalDateTime now = LocalDateTime.now();

					//With Rosental...
					TBelief_Base Belief = this.Get_GW().Create_Beliefs(Predicate, true, TType_Subject.Me, now, 
							TType_Beliefs.Belief_Path_Taken_For_Belief);
					ArrayList<TBelief_Base> List_Beliefs = 
							Map_Attentional_Goal_and_Beliefs.get(Functional_Goal.get_Name());
					
					ArrayList<TPredicate> List_Predicates = 
							Map_Attentional_Goal_and_Predicates.get(Functional_Goal.get_Name());
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
			Game.Print("Predicato3.get_Name() "+Predicate.get_Name());
			Game.Print("Predicato3.get_Subject() "+Predicate.get_Subject());
			Game.Print("Predicato3.get_Relationship() "+Predicate.get_Relationship());
			Game.Print("Predicato3.get_Object_Complement() "+Predicate.get_Object_Complement());			
		}
	}
	
	/**
	 * Prints any Beliefs in Long Memory. For this project only
	 */
	private void Print_Beliefs()
	{
		int i=0;
		for (TBelief_Base Bel: this.WMM.Get_Beliefs())
		{
			i++;
			Game.Print("Beliefs "+i
					);
			TPredicate  Predicate = null;
			Predicate = Bel.get_Predicate();
			Game.Print("Bel.get_Name "+Bel.get_Name());
			Game.Print("Bel.get_Predicate_name "+Bel.get_Predicate_name());
			Game.Print("Bel.is_Truth() "+Bel.is_Truth());
			Game.Print("Bel.get_Information_Source() "+ Bel.get_Information_Source());
			Game.Print("Bel.get_Time_stamp() "+Bel.get_Time_stamp());
			Game.Print("Bel.get_Type_Belief() "+Bel.get_Type_Belief());
			Game.Print("Predicato3.get_Name() "+Predicate.get_Name());
			Game.Print("Predicato3.get_Subject() "+Predicate.get_Subject());
			Game.Print("Predicato3.get_Relationship() "+Predicate.get_Relationship());
			Game.Print("Predicato3.get_Object_Complement() "+Predicate.get_Object_Complement());
			Game.Print("-------------------");
		}
		
	}
	
	/**
	 * Prints any Green Goals in Long Memory. For this project only
	 */
	private void Print_Green_Goals()
	{
		for (TGreen_Goal Green: this.WMM.Get_Green_Goals())
		{
			TPredicate  Predicate = null;
			Predicate = Green.get_Constraint();
			Game.Print("Green.get_Name() "+Green.get_Name());
			Game.Print("Green.get_Constraint_Name() "+Green.get_Constraint_Name());
			Game.Print("Green.get_Type_Quality_Goal() "+Green.get_Type_Quality_Goal());
			Game.Print("Green.get_TType_Green_Goal() "+Green.get_TType_Green_Goal());
			Game.Print("Green.get_Fee() "+Green.get_Fee());
			Game.Print("Green.get_Saliency() "+Green.get_Saliency());
			Game.Print("Green.Check_Satisfation() "+Green.Check_Satisfation());
			Game.Print("Green.get_Reward() "+Green.get_Reward());
			Game.Print("Green.get_Relax_Preference() "+Green.get_Relax_Preference());
			
			Game.Print("Predicato3.get_Name() "+Predicate.get_Name());
			Game.Print("Predicato3.get_Subject() "+Predicate.get_Subject());
			Game.Print("Predicato3.get_Relationship() "+Predicate.get_Relationship());
			Game.Print("Predicato3.get_Object_Complement() "+Predicate.get_Object_Complement());
			Game.Print("-------------------");
		}
	}
	
	/**
	 * Prints any Quality Goals in Long Memory. For this project only
	 */
	private void Print_Quality_Goals()
	{
		for (TQuality_Goal Quality: this.WMM.Get_Quality_Goals())
		{
			 
			TPredicate  Predicate = null;
			Predicate = Quality.get_Constraint();
			Game.Print("Quality.get_Name() "+Quality.get_Name());
			Game.Print("Quality.get_Constraint_Name() "+Quality.get_Constraint_Name());
			Game.Print("Quality.get_Type_Quality_Goal() "+Quality.get_Type_Quality_Goal());
			Game.Print("Quality.get_Saliency() "+Quality.get_Saliency());
			Game.Print("Quality.Check_Satisfation() "+Quality.Check_Satisfation());
			Game.Print("Quality.get_Reward() "+Quality.get_Reward());
			Game.Print("Quality.get_Relax_Preference() "+Quality.get_Relax_Preference());
			
			Game.Print("Predicato3.get_Name() "+Predicate.get_Name());
			Game.Print("Predicato3.get_Subject() "+Predicate.get_Subject());
			Game.Print("Predicato3.get_Relationship() "+Predicate.get_Relationship());
			Game.Print("Predicato3.get_Object_Complement() "+Predicate.get_Object_Complement());
			Game.Print("-------------------");
		}
	}
	
	/**
	 * Prints any Functional Goals in Long Memory. For this project only
	 */
	private void Print_Functional_Goals()
	{
		int i =0;
		ArrayList<TFunctional_Goal> Functional_Goals = this.WMM.Get_Functional_Goals();
		for(TFunctional_Goal Functional_Goal: Functional_Goals)
		{
			i++;
			Game.Print("-------------------------------");
			Game.Print("Functional goal n."+i);
			Game.Print("Functional_Goal.get_Name() "+Functional_Goal.get_Name());
			Game.Print("Functional_Goal.get_Saliency() "+Functional_Goal.get_Saliency());
			Game.Print("Functional_Goal.Check_Satisfation() "+Functional_Goal.Check_Satisfation());
			Game.Print("Functional_Goal.Check_Precondition() "+Functional_Goal.Check_Precondition());
			Game.Print("Functional_Goal.get_Reward() "+Functional_Goal.get_Reward());
			Game.Print("Functional_Goal.get_Relax_Preference() "+Functional_Goal.get_Relax_Preference());
			Game.Print("Functional_Goal.get_Sum_for_Attention_Treshold() "+Functional_Goal.get_Sum_for_Attention_Treshold());
			Game.Print("Functional_Goal.get_Final_State_Name() "+Functional_Goal.get_Final_State_Name());
			Game.Print("Functional_Goal.get_Trigger_Condition_Name() "+Functional_Goal.get_Trigger_Condition_Name());
			
			
			TBelief_Base Final_State = null;
			Final_State = Functional_Goal.get_Final_State();
			Game.Print("- Final_State.get_Name() "+Final_State.get_Name());
			Game.Print("- Final_State.get_Time_stamp() "+Final_State.get_Time_stamp());
			Game.Print("- Final_State.get_Information_Source() "+Final_State.get_Information_Source());
			Game.Print("- Final_State.get_Belief_ID() "+Final_State.get_Belief_ID());
			Game.Print("- Final_State.get_Type_Belief() "+Final_State.get_Type_Belief());
			Game.Print("- Final_State.get_Predicate_name() "+Final_State.get_Predicate_name());
			Game.Print("- Final_State.get_Truth() "+Final_State.get_Truth());
			
			TPredicate Final_Predicate = Final_State.get_Predicate();
			Game.Print("- - Final_Predicate.get_Name() "+Final_Predicate.get_Name());
			Game.Print("- - Final_Predicate.get_Predicate_ID() "+Final_Predicate.get_Predicate_ID());
			Game.Print("- - Final_Predicate.get_Subject() "+Final_Predicate.get_Subject());
			Game.Print("- - Final_Predicate.get_Relationship() "+Final_Predicate.get_Relationship());
			Game.Print("- - Final_Predicate.get_Object_Complement() "+Final_Predicate.get_Object_Complement());
			Game.Print("- - Final_Predicate.get_Linked_Belief_Name() "+Final_Predicate.get_Linked_Belief_Name());
			
			TPredicate Functional_Predicate = Functional_Goal.get_Trigger_Condition();
			if(Functional_Predicate != null)
			{
				Game.Print("- Functional_Predicate.get_Name() "+Functional_Predicate.get_Name());
				Game.Print("- Functional_Predicate.get_Predicate_ID() "+Functional_Predicate.get_Predicate_ID());
				Game.Print("- Functional_Predicate.get_Subject() "+Functional_Predicate.get_Subject());
				Game.Print("- Functional_Predicate.get_Relationship() "+Functional_Predicate.get_Relationship());
				Game.Print("- Functional_Predicate.get_Object_Complement() "+Functional_Predicate.get_Object_Complement());
				Game.Print("- Functional_Predicate.get_Linked_Belief_Name() "+Functional_Predicate.get_Linked_Belief_Name());
			}
			
			if(Functional_Goal.get_List_Green_Goal() == null)
			{
				ArrayList<TGreen_Goal> List_Green_Goal = new ArrayList<TGreen_Goal>();
				Functional_Goal.set_List_Green_Goal(List_Green_Goal);
			}
			Game.Print("Functional Green Goals "+Functional_Goal.get_List_Green_Goal().size() );
			for (TGreen_Goal Green: Functional_Goal.get_List_Green_Goal())
			{
				 
				TPredicate Predicate = Green.get_Constraint();
				Game.Print("- Green.get_Name() "+Green.get_Name());
				Game.Print("- Green.get_Constraint_Name() "+Green.get_Constraint_Name());
				Game.Print("- Green.get_Type_Quality_Goal() "+Green.get_Type_Quality_Goal());
				Game.Print("- Green.get_TType_Green_Goal() "+Green.get_TType_Green_Goal());
				Game.Print("- Green.get_Fee() "+Green.get_Fee());
				Game.Print("- Green.get_Saliency() "+Green.get_Saliency());
				Game.Print("- Green.Check_Satisfation() "+Green.Check_Satisfation());
				Game.Print("- Green.get_Reward() "+Green.get_Reward());
				Game.Print("- Green.get_Relax_Preference() "+Green.get_Relax_Preference());
				
				Game.Print("- - Predicato3.get_Name() "+Predicate.get_Name());
				Game.Print("- - Predicato3.get_Subject() "+Predicate.get_Subject());
				Game.Print("- - Predicato3.get_Relationship() "+Predicate.get_Relationship());
				Game.Print("- - Predicato3.get_Object_Complement() "+Predicate.get_Object_Complement());
				Game.Print("-------------------");
			}
			if(Functional_Goal.get_List_Quality_Goal() == null)
			{
				ArrayList<TQuality_Goal> List_Quality_Goal = new ArrayList<TQuality_Goal>();
				Functional_Goal.set_List_Quality_Goal(List_Quality_Goal);
			}
			Game.Print("Functional Quality Goals "+Functional_Goal.get_List_Quality_Goal().size() );
			for (TQuality_Goal Quality: Functional_Goal.get_List_Quality_Goal())
			{
				 
				TPredicate  Predicate = null;
				Predicate = Quality.get_Constraint();
				Game.Print("Quality.get_Name() "+Quality.get_Name());
				Game.Print("Quality.get_Constraint_Name() "+Quality.get_Constraint_Name());
				Game.Print("Quality.get_Type_Quality_Goal() "+Quality.get_Type_Quality_Goal());
				Game.Print("Quality.get_Saliency() "+Quality.get_Saliency());
				Game.Print("Quality.Check_Satisfation() "+Quality.Check_Satisfation());
				Game.Print("Quality.get_Reward() "+Quality.get_Reward());
				Game.Print("Quality.get_Relax_Preference() "+Quality.get_Relax_Preference());
				
				Game.Print("Predicato3.get_Name() "+Predicate.get_Name());
				Game.Print("Predicato3.get_Subject() "+Predicate.get_Subject());
				Game.Print("Predicato3.get_Relationship() "+Predicate.get_Relationship());
				Game.Print("Predicato3.get_Object_Complement() "+Predicate.get_Object_Complement());
				Game.Print("-------------------");
			}			
		}
	}
	
	/**
	 * Prints any Epistemic Goals in Long Memory. For this project only
	 */
	private void Print_Epistemic_Goals()
	{
		int i =0;
		ArrayList<TEpistemic_Goal> Epistemic_Goals = this.WMM.Get_Epistemic_Goals();
		for(TEpistemic_Goal Functional_Goal: Epistemic_Goals)
		{
			i++;
			Game.Print("-------------------------------");
			Game.Print("Epistemic_Goals goal n."+i);
			Game.Print("Epistemic_Goals.get_Name() "+Functional_Goal.get_Name());
			Game.Print("Epistemic_Goals.get_Saliency() "+Functional_Goal.get_Saliency());
			Game.Print("Epistemic_Goals.Check_Satisfation() "+Functional_Goal.Check_Satisfation());
			Game.Print("Epistemic_Goals.Check_Precondition() "+Functional_Goal.Check_Precondition());
			Game.Print("Epistemic_Goals.get_Reward() "+Functional_Goal.get_Reward());
			Game.Print("Epistemic_Goals.get_Relax_Preference() "+Functional_Goal.get_Relax_Preference());
			Game.Print("Epistemic_Goals.get_Sum_for_Attention_Treshold() "+Functional_Goal.get_Sum_for_Attention_Treshold());
			Game.Print("Epistemic_Goals.get_Belief_Name() "+Functional_Goal.get_Belief_Name());
			
			
			TBelief_Base Final_State = null;
			Final_State = Functional_Goal.get_Belief();
			Game.Print("- Final_State.get_Name() "+Final_State.get_Name());
			Game.Print("- Final_State.get_Time_stamp() "+Final_State.get_Time_stamp());
			Game.Print("- Final_State.get_Information_Source() "+Final_State.get_Information_Source());
			Game.Print("- Final_State.get_Belief_ID() "+Final_State.get_Belief_ID());
			Game.Print("- Final_State.get_Type_Belief() "+Final_State.get_Type_Belief());
			Game.Print("- Final_State.get_Predicate_name() "+Final_State.get_Predicate_name());
			Game.Print("- Final_State.get_Truth() "+Final_State.get_Truth());
			
			TPredicate Final_Predicate = Final_State.get_Predicate();
			Game.Print("- - Final_Predicate.get_Name() "+Final_Predicate.get_Name());
			Game.Print("- - Final_Predicate.get_Predicate_ID() "+Final_Predicate.get_Predicate_ID());
			Game.Print("- - Final_Predicate.get_Subject() "+Final_Predicate.get_Subject());
			Game.Print("- - Final_Predicate.get_Relationship() "+Final_Predicate.get_Relationship());
			Game.Print("- - Final_Predicate.get_Object_Complement() "+Final_Predicate.get_Object_Complement());
			Game.Print("- - Final_Predicate.get_Linked_Belief_Name() "+Final_Predicate.get_Linked_Belief_Name());

			if(Functional_Goal.get_List_Green_Goal() == null)
			{
				ArrayList<TGreen_Goal> List_Green_Goal = new ArrayList<TGreen_Goal>();
				Functional_Goal.set_List_Green_Goal(List_Green_Goal);
			}
			Game.Print("Epistemic Green Goals "+Functional_Goal.get_List_Green_Goal().size() );
			for (TGreen_Goal Green: Functional_Goal.get_List_Green_Goal())
			{
				 
				TPredicate Predicate = Green.get_Constraint();
				Game.Print("- Green.get_Name() "+Green.get_Name());
				Game.Print("- Green.get_Constraint_Name() "+Green.get_Constraint_Name());
				Game.Print("- Green.get_Type_Quality_Goal() "+Green.get_Type_Quality_Goal());
				Game.Print("- Green.get_TType_Green_Goal() "+Green.get_TType_Green_Goal());
				Game.Print("- Green.get_Fee() "+Green.get_Fee());
				Game.Print("- Green.get_Saliency() "+Green.get_Saliency());
				Game.Print("- Green.Check_Satisfation() "+Green.Check_Satisfation());
				Game.Print("- Green.get_Reward() "+Green.get_Reward());
				Game.Print("- Green.get_Relax_Preference() "+Green.get_Relax_Preference());
				
				Game.Print("- - Predicato3.get_Name() "+Predicate.get_Name());
				Game.Print("- - Predicato3.get_Subject() "+Predicate.get_Subject());
				Game.Print("- - Predicato3.get_Relationship() "+Predicate.get_Relationship());
				Game.Print("- - Predicato3.get_Object_Complement() "+Predicate.get_Object_Complement());
				Game.Print("-------------------");
			}
			if(Functional_Goal.get_List_Quality_Goal() == null)
			{
				ArrayList<TQuality_Goal> List_Quality_Goal = new ArrayList<TQuality_Goal>();
				Functional_Goal.set_List_Quality_Goal(List_Quality_Goal);
			}
			Game.Print("Functional Quality Goals "+Functional_Goal.get_List_Quality_Goal().size() );
			for (TQuality_Goal Quality: Functional_Goal.get_List_Quality_Goal())
			{
				 
				TPredicate  Predicate = null;
				Predicate = Quality.get_Constraint();
				Game.Print("Quality.get_Name() "+Quality.get_Name());
				Game.Print("Quality.get_Constraint_Name() "+Quality.get_Constraint_Name());
				Game.Print("Quality.get_Type_Quality_Goal() "+Quality.get_Type_Quality_Goal());
				Game.Print("Quality.get_Saliency() "+Quality.get_Saliency());
				Game.Print("Quality.Check_Satisfation() "+Quality.Check_Satisfation());
				Game.Print("Quality.get_Reward() "+Quality.get_Reward());
				Game.Print("Quality.get_Relax_Preference() "+Quality.get_Relax_Preference());
				
				Game.Print("Predicato3.get_Name() "+Predicate.get_Name());
				Game.Print("Predicato3.get_Subject() "+Predicate.get_Subject());
				Game.Print("Predicato3.get_Relationship() "+Predicate.get_Relationship());
				Game.Print("Predicato3.get_Object_Complement() "+Predicate.get_Object_Complement());
				Game.Print("-------------------");
			}			
		}
	}
	
	/**
	 * Return Executive Working Memomry Maintenance of the agent
	 * @return
	 */
	public TWorking_Memory_Maintenance Get_WMM()
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
	 * @param list_Green_Goal_Name
	 * @param list_Quality_Goal_Name
	 * @param finally_Start
	 * @param finally_End
	 * @param global_Start
	 * @param global_End
	 * @param untill_Start
	 * @param until_End
	 */
	public void Insert_Functional_Goal(String name, String final_state_Name, String trigger_Condition_Name,
			Double saliency, Double reward, Double relax_Preference,
			ArrayList<String> list_Green_Goal_Name, ArrayList<String> list_Quality_Goal_Name,
			LocalDateTime finally_Start, LocalDateTime finally_End, LocalDateTime global_Start, 
			LocalDateTime global_End, LocalDateTime untill_Start, LocalDateTime until_End )
	{
		ArrayList<TFunctional_Goal> Functional_Goals = new ArrayList<TFunctional_Goal>();
		
		TFunctional_Goal Functional_Goal = new TFunctional_Goal(name, final_state_Name, 
				trigger_Condition_Name, saliency, reward, relax_Preference, list_Green_Goal_Name, 
				list_Quality_Goal_Name, finally_Start, finally_End, global_Start, global_End, 
				untill_Start, until_End);
		
		Functional_Goals.add(Functional_Goal);
		this.WMM.Set_Functional_Goals(Functional_Goals);
	}
	
	/**
	 * Inserts a Epistemic Goal in the agent. The params are the same of the constructor that creates
	 * the Epistemic Goal, and change the update_goals of Global workspace
	 * @param name
	 * @param belief_Name
	 * @param saliency
	 * @param reward
	 * @param relax_Preference
	 * @param list_Green_Goal_Name
	 * @param list_Quality_Goal_Name
	 */
	public void Insert_Epistemic_Goal(String name, String belief_Name, Double saliency, Double reward, 
			Double relax_Preference, ArrayList<String> list_Green_Goal_Name, 
			ArrayList<String> list_Quality_Goal_Name)
	{
		ArrayList<TEpistemic_Goal> Epistemic_Goals = new ArrayList<TEpistemic_Goal>();
		
		TEpistemic_Goal Epistemic_Goal = new TEpistemic_Goal(name, belief_Name, saliency, reward, 
				relax_Preference, list_Green_Goal_Name, list_Quality_Goal_Name);
		
		Epistemic_Goals.add(Epistemic_Goal);
		this.WMM.Set_Epistemic_Goals(Epistemic_Goals);
	}
	
	/**
	 * Inserts a Quality Goal in the agent. The params are the same of the constructor that creates
	 * the Quality Goal
	 * @param name
	 * @param constraint_name
	 * @param type_Quality_Goal
	 * @param saliency
	 * @param reward
	 * @param relax_Preference
	 */
	public void Insert_Quality_Goal(String name, String constraint_name, 
			TType_Quality_Goal type_Quality_Goal, Double saliency, Double reward,
			Double relax_Preference)
	{
		
		ArrayList<TQuality_Goal> Quality_Goals = new ArrayList<TQuality_Goal>();
		
		TQuality_Goal Quality_Goal = new TQuality_Goal(name, constraint_name, type_Quality_Goal, 
				saliency, reward, relax_Preference);
		
		Quality_Goals.add(Quality_Goal);
		this.WMM.Set_Quality_Goals(Quality_Goals);
	}
	
	/**
	 * Inserts a Green Goal in the agent. The params are the same of the constructor that creates
	 * the Green Goal
	 * @param name
	 * @param constraint_name
	 * @param type_Green_Goal
	 * @param saliency
	 * @param reward
	 * @param fee
	 */
	public void Insert_Green_Goal(String name, String constraint_name, 
			TType_Green_Goal type_Green_Goal, Double saliency, Double reward, Double fee)
	{
		ArrayList<TGreen_Goal> Green_Goals = new ArrayList<TGreen_Goal>();
		
		TGreen_Goal Green_Goal = new TGreen_Goal(name, constraint_name, type_Green_Goal, saliency, 
				reward, fee);
		
		Green_Goals.add(Green_Goal);
		this.WMM.Set_Green_Goals(Green_Goals);
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
		ArrayList<TBelief_Base> Beliefs = new ArrayList<TBelief_Base>();
		
		TBelief_Base Belief = new TBelief_Base(name, predicate_Name, truth, information_Source, 
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