package Cara_Simulation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class TAgent {
	
	private TE_Switching_Function E_Switching_Function;
	private TE_Inhibition_function E_Inhibition_function;
	private TGlobalWorkspace GW;
	private TReasoner_Function Reasoner;
	private TResource_Allocation RA;
	private TAgent_Status Status;
	private TWorking_Memory_Maintenance WMM;
	private TTCS TCS;
	
	public TAgent()
	{
		this.E_Switching_Function = new TE_Switching_Function(this);
		this.E_Inhibition_function = new TE_Inhibition_function(this);
		this.GW = new TGlobalWorkspace(this);
		this.Reasoner = new TReasoner_Function(this);
		this.RA = new TResource_Allocation(this);
		this.WMM = new TWorking_Memory_Maintenance(this);

		this.Status = TAgent_Status.Not_Active;
	}
	
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
			
	//		this.GW.set_Map_Know(Mappa.clona());
			this.TCS = TCS;
			this.WMM.Set_Map(Map);
			this.WMM.Set_Regions();
			//System.out.println("Routes number = "+Map.Tutte_Le_Rotte.size());
			final StackTraceElement[] ste = Thread.currentThread().getStackTrace();
	
	
			// TODO --Agent's Initialization--  
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
			
			
			//System.out.println("1 - Get All Predicate");
			//1 - Get All Predicate
			this.Read_Predicates_From_File();
			
			
			//System.out.println("2 - Get All Beliefs (this normalize Beliefs with related Predicate");
			//2 - Get All Beliefs (this normalize Beliefs with related Predicate
			this.Read_Beliefs_From_File();
			
			//System.out.println("3 - Get All Green Goals (this normalize Green Goals with related data");
			//2 - Get All Beliefs (this normalize Beliefs with related Predicate
			this.Read_Green_Goals_From_File();
			
			//System.out.println("4 - Get All Quality Goals (this normalize Quality Goals with related data");
			//2 - Get All Beliefs (this normalize Beliefs with related Predicate
			this.Read_Quality_Goals_From_File();
			
			//System.out.println("5 - Get All Functional Goals (this normalize Functional Goals with related data");
			//2 - Get All Beliefs (this normalize Beliefs with related Predicate
			this.Read_Functional_Goals_From_File();
			
			//System.out.println("6 - Get All Epistemic Goals (this normalize Epistemic Goals with related data");
			//2 - Get All Beliefs (this normalize Beliefs with related Predicate
			//This function has a bug, I have to check it
			//this.Read_Epistemic_Goals_From_File();
			
			
			//Section to reading Predicate lists, Beliefs List, eetc..
			//this.GW.
		
			// Create the plan Library
	
			// Create the Belief_Path_Taken_For_Belief linked to Functional Goal
			this.Create_Belief_Path_Taken_For_Functional_Goal(Init_Creation_Path_Travelled_for_Functional_Goal);
	
			//Game.Print("sono Qua "+this.WMM.Get_Beliefs().size());
			HashMap<String, TPredicate> Map_Predicates = this.WMM.Get_Map_Predicates();
	//		Game.Print("All initial Belief Number in Agent WMM: "+this.WMM.Get_Beliefs().size());
	//		Game.Print("All initial Belief Types in Agent WMM:");
			
			ArrayList<TType_Beliefs> Beliefs_Type_list = new ArrayList<TType_Beliefs>();
			for(TBelief_Base belief: this.WMM.Get_Beliefs())
			{
	
				TPredicate Predicato = null;
				Predicato = Map_Predicates.get(belief.get_Predicate_name());
				
				if (!Beliefs_Type_list.contains(belief.get_Type_Belief()))
				{
					Beliefs_Type_list.add(belief.get_Type_Belief());
					//Game.Print("--- Belief Name: "+belief.get_Name()+" - related Predicate Name: "+Predicato.get_Name()+" - Belief Type: "+belief.get_Type_Belief());
	//				Game.Print("--- Belief Type: "+belief.get_Type_Belief());
				}
				
				
				belief.set_Predicate(Predicato);
				//Game.Print("Dopo- "+belief.get_Name()+" - "+Predicato.get_Name()+" - "+belief.get_Type_Belief());
				
	
			}
			
			this.GW.Print_Data(1, 0);
		}
		catch (Exception e) {
	      Game.Print("Something went wrongin method: Insert_New_Desires.");
	      Game.Print("Message Error: "+e.getMessage());
	      result = false;
	    }
		return result;
	}

	public TE_Switching_Function get_E_Switching_Function() {
		return this.E_Switching_Function;
	}
	
	public TE_Inhibition_function Get_E_Inhibition_function()
	{
		return this.E_Inhibition_function;
	}

	public TGlobalWorkspace get_GW() {
		return this.GW;
	}

	public TReasoner_Function get_Reasoner() {
		return this.Reasoner;
	}

	public TResource_Allocation get_RA() {
		return this.RA;
	}
	
	public void Start()
	{
		Game.Scenario_Number++;

		this.GW.Print_Data(0, 0);
		this.Status = TAgent_Status.Active;
		Game.Print("Now Agent Status is "+this.Status);
		boolean Res;
		boolean Post_OK;
		int Scenario_Cycle = 0;
		while(this.Status == TAgent_Status.Active)
		{
			Res = this.WMM.Perception_Processing(Scenario_Cycle);
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
								Post_OK = true;
								Game.Scenario_Number++;
								Scenario_Cycle++;
							}
						}
							
					}
						
				}
			}
			
			if(Scenario_Cycle == 2)
			{
				Game.End_Game();				
			}
		}
	}
	
	public void Read_Predicates_From_File()
	{
		TFile_Manager Manager = new TFile_Manager();

		ArrayList<TPredicate> Predicates = null;
		Predicates = Manager.Read_Predicates();
		//this.GW.set_Predicates(Predicates);
		this.WMM.Set_Predicates(Predicates);
		
//		this.Print_Predicates();
	}
	
	public void Read_Beliefs_From_File()
	{
		TFile_Manager Manager = new TFile_Manager();
		
		ArrayList<TBelief_Base> Beliefs = null;
		Beliefs = Manager.Read_Beliefs();
//		this.GW.set_Beliefs(Beliefs);
		this.WMM.Set_Beliefs(Beliefs);

//		this.Print_Beliefs();
	}
	
	public void Read_Epistemic_Goals_From_File()
	{
		TFile_Manager Manager = new TFile_Manager();
		
		ArrayList<TEpistemic_Goal> Epistemic_Goals = null;
		Epistemic_Goals = Manager.Read_Epistemic_Goals();
//		this.GW.set_Epistemic_Goals(Epistemic_Goals);
		this.WMM.Set_Epistemic_Goals(Epistemic_Goals);

//		this.Print_Epistemic_Goals();
	}
	
	public void Read_Functional_Goals_From_File()
	{
		TFile_Manager Manager = new TFile_Manager();
		
		ArrayList<TFunctional_Goal> Functional_Goals = null;
		Functional_Goals = Manager.Read_Functional_Goals();
//		this.GW.set_Functional_Goals(Functional_Goals);
		this.WMM.Set_Functional_Goals(Functional_Goals);
		
//		this.Print_Functional_Goals();
	}
	
	public void Read_Green_Goals_From_File()
	{
		TFile_Manager Manager = new TFile_Manager();
		
		ArrayList<TGreen_Goal> Green_Goals = null;
		Green_Goals = Manager.Read_Green_Goals();
//		this.GW.set_Green_Goals(Green_Goals);
		this.WMM.Set_Green_Goals(Green_Goals);
		
//		this.Print_Green_Goals();		
	}
	
	public void Read_Quality_Goals_From_File()
	{
		TFile_Manager Manager = new TFile_Manager();
		
		ArrayList<TQuality_Goal> Quality_Goals = null;
		Quality_Goals = Manager.Read_Quality_Goals();
		
//		this.GW.set_Quality_Goals(Quality_Goals);
		this.WMM.Set_Quality_Goals(Quality_Goals);
		
//		this.Print_Quality_Goals();
	}
	
	/** This Function creates, for each destination Station, a belief (linked to a Funtional Goal)
	 * of the path of travelled routes to arrive to Destination Station 
	 * 
	 */
	public void Create_Belief_Path_Taken_For_Functional_Goal(int Init_Creation_Path_Travelled_for_Functional_Goal)
	{
		HashMap<String, ArrayList<TPredicate>> Map_Attentional_Goal_and_Predicates = 
				this.WMM.Get_Map_Attentional_Goals_and_Predicates();
		HashMap<String, ArrayList<TBelief_Base>> Map_Attentional_Goal_and_Beliefs = 
						this.WMM.Get_Map_Attentional_Goals_and_Beliefs();
		
		Game.Print("Loaded Goals : "+Map_Attentional_Goal_and_Beliefs.keySet());
		
		//for(TAttentional_Goal Goal: this.GW.get_Goals())
		this.Get_WMM().Set_Beliefs_Number(Init_Creation_Path_Travelled_for_Functional_Goal);
		for(TFunctional_Goal Functional_Goal: this.WMM.Get_Functional_Goals())
		{
//			if (Goal instanceof TFunctional_Goal)
			{
//				TFunctional_Goal Functional_Goal = (TFunctional_Goal) Goal;
				if (Functional_Goal.get_Final_State().get_Type_Belief() == TType_Beliefs.Belief_Destination_Station)
				{
					

					TPredicate Predicate = this.GW.Create_Predicate(Functional_Goal.get_Name(), TType_Relationship.has_traveled,
							new ArrayList<Route>());
					LocalDateTime now = LocalDateTime.now();
					//With Rosental...
					//Game.Print("Sono qui dentro per Rosenthal");

					TBelief_Base Belief = this.get_GW().Create_Beliefs(Predicate, true, TType_Subject.Me, now, 
							TType_Beliefs.Belief_Path_Taken_For_Belief);
					
					
					//I insert Belief, and its Predicate, in the List_Beliefs and List_Predicates of
					//the Functional Goal
//					Game.Print("Functional_Goal.get_Name(): "+Functional_Goal.get_Name());
//					Game.Print("Functional_Goal.get_Name(): "+Functional_Goal.get_Final_State().get_Name());
//					Game.Print("Belief_Path_Taken_For_Belief.get_Name(): "+Belief.get_Name());
//					Game.Print("Belief_Path_Taken_For_Belief.get_Name(): "+Belief.get_Type_Belief());
					
					ArrayList<TBelief_Base> List_Beliefs = 
							Map_Attentional_Goal_and_Beliefs.get(Functional_Goal.get_Name());
					
					ArrayList<TPredicate> List_Predicates = 
							Map_Attentional_Goal_and_Predicates.get(Functional_Goal.get_Name());
					//Game.Print("List_Beliefs Prima "+List_Beliefs);
					if(!List_Beliefs.contains(Belief))
					{
						List_Beliefs.add(Belief);						
					}
					//Game.Print("List_Beliefs Dopo"+List_Beliefs);
					if(!List_Predicates.contains(Predicate))
					{
						List_Predicates.add(Predicate);						
					}
				}
			}
		}
	}
	
	public TAgent_Status Get_Status()
	{
		return this.Status;
	}
	
	private void Print_Predicates()
	{
		int i=0;
		for (TPredicate Predicato3: this.WMM.Get_Predicates())
		{
			System.out.println("Predicato3.get_Name() "+Predicato3.get_Name());
			System.out.println("Predicato3.get_Subject() "+Predicato3.get_Subject());
			System.out.println("Predicato3.get_Relationship() "+Predicato3.get_Relationship());
			System.out.println("Predicato3.get_Object_Complement() "+Predicato3.get_Object_Complement());			
		}
	}
	
	private void Print_Beliefs()
	{
		int i=0;
		for (TBelief_Base Bel: this.WMM.Get_Beliefs())
		{
			i++;
			Game.Print("Beliefs "+i
					);
			TPredicate  Predicato3 = null;
			Predicato3 = Bel.get_Predicate();
			System.out.println("Bel.get_Name "+Bel.get_Name());
			System.out.println("Bel.get_Predicate_name "+Bel.get_Predicate_name());
			System.out.println("Bel.is_Truth() "+Bel.is_Truth());
			System.out.println("Bel.get_Information_Source() "+ Bel.get_Information_Source());
			System.out.println("Bel.get_Time_stamp() "+Bel.get_Time_stamp());
			System.out.println("Bel.get_Type_Belief() "+Bel.get_Type_Belief());
			System.out.println("Predicato3.get_Name() "+Predicato3.get_Name());
			System.out.println("Predicato3.get_Subject() "+Predicato3.get_Subject());
			System.out.println("Predicato3.get_Relationship() "+Predicato3.get_Relationship());
			System.out.println("Predicato3.get_Object_Complement() "+Predicato3.get_Object_Complement());
			System.out.println("-------------------");
		}
		
	}
	
	private void Print_Green_Goals()
	{
		for (TGreen_Goal Green: this.WMM.Get_Green_Goals())
		{
			TPredicate  Predicato3 = null;
			Predicato3 = Green.get_Constraint();
			System.out.println("Green.get_Name() "+Green.get_Name());
			System.out.println("Green.get_Constraint_Name() "+Green.get_Constraint_Name());
			System.out.println("Green.get_Type_Quality_Goal() "+Green.get_Type_Quality_Goal());
			System.out.println("Green.get_TType_Green_Goal() "+Green.get_TType_Green_Goal());
			System.out.println("Green.get_Fee() "+Green.get_Fee());
			System.out.println("Green.get_Saliency() "+Green.get_Saliency());
			System.out.println("Green.Check_Satisfation() "+Green.Check_Satisfation());
			System.out.println("Green.get_Reward() "+Green.get_Reward());
			System.out.println("Green.get_Relax_Preference() "+Green.get_Relax_Preference());
			
			System.out.println("Predicato3.get_Name() "+Predicato3.get_Name());
			System.out.println("Predicato3.get_Subject() "+Predicato3.get_Subject());
			System.out.println("Predicato3.get_Relationship() "+Predicato3.get_Relationship());
			System.out.println("Predicato3.get_Object_Complement() "+Predicato3.get_Object_Complement());
			System.out.println("-------------------");
		}
	}
	private void Print_Quality_Goals()
	{
		for (TQuality_Goal Quality: this.WMM.Get_Quality_Goals())
		{
			 
			TPredicate  Predicato3 = null;
			Predicato3 = Quality.get_Constraint();
			System.out.println("Quality.get_Name() "+Quality.get_Name());
			System.out.println("Quality.get_Constraint_Name() "+Quality.get_Constraint_Name());
			System.out.println("Quality.get_Type_Quality_Goal() "+Quality.get_Type_Quality_Goal());
			System.out.println("Quality.get_Saliency() "+Quality.get_Saliency());
			System.out.println("Quality.Check_Satisfation() "+Quality.Check_Satisfation());
			System.out.println("Quality.get_Reward() "+Quality.get_Reward());
			System.out.println("Quality.get_Relax_Preference() "+Quality.get_Relax_Preference());
			
			System.out.println("Predicato3.get_Name() "+Predicato3.get_Name());
			System.out.println("Predicato3.get_Subject() "+Predicato3.get_Subject());
			System.out.println("Predicato3.get_Relationship() "+Predicato3.get_Relationship());
			System.out.println("Predicato3.get_Object_Complement() "+Predicato3.get_Object_Complement());
			System.out.println("-------------------");
		}
	}
	
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
				 
				TPredicate Predicato3 = Green.get_Constraint();
				System.out.println("- Green.get_Name() "+Green.get_Name());
				System.out.println("- Green.get_Constraint_Name() "+Green.get_Constraint_Name());
				System.out.println("- Green.get_Type_Quality_Goal() "+Green.get_Type_Quality_Goal());
				System.out.println("- Green.get_TType_Green_Goal() "+Green.get_TType_Green_Goal());
				System.out.println("- Green.get_Fee() "+Green.get_Fee());
				System.out.println("- Green.get_Saliency() "+Green.get_Saliency());
				System.out.println("- Green.Check_Satisfation() "+Green.Check_Satisfation());
				System.out.println("- Green.get_Reward() "+Green.get_Reward());
				System.out.println("- Green.get_Relax_Preference() "+Green.get_Relax_Preference());
				
				System.out.println("- - Predicato3.get_Name() "+Predicato3.get_Name());
				System.out.println("- - Predicato3.get_Subject() "+Predicato3.get_Subject());
				System.out.println("- - Predicato3.get_Relationship() "+Predicato3.get_Relationship());
				System.out.println("- - Predicato3.get_Object_Complement() "+Predicato3.get_Object_Complement());
				System.out.println("-------------------");
			}
			if(Functional_Goal.get_List_Quality_Goal() == null)
			{
				ArrayList<TQuality_Goal> List_Quality_Goal = new ArrayList<TQuality_Goal>();
				Functional_Goal.set_List_Quality_Goal(List_Quality_Goal);
			}
			Game.Print("Functional Quality Goals "+Functional_Goal.get_List_Quality_Goal().size() );
			for (TQuality_Goal Quality: Functional_Goal.get_List_Quality_Goal())
			{
				 
				TPredicate  Predicato3 = null;
				Predicato3 = Quality.get_Constraint();
				System.out.println("Quality.get_Name() "+Quality.get_Name());
				System.out.println("Quality.get_Constraint_Name() "+Quality.get_Constraint_Name());
				System.out.println("Quality.get_Type_Quality_Goal() "+Quality.get_Type_Quality_Goal());
				System.out.println("Quality.get_Saliency() "+Quality.get_Saliency());
				System.out.println("Quality.Check_Satisfation() "+Quality.Check_Satisfation());
				System.out.println("Quality.get_Reward() "+Quality.get_Reward());
				System.out.println("Quality.get_Relax_Preference() "+Quality.get_Relax_Preference());
				
				System.out.println("Predicato3.get_Name() "+Predicato3.get_Name());
				System.out.println("Predicato3.get_Subject() "+Predicato3.get_Subject());
				System.out.println("Predicato3.get_Relationship() "+Predicato3.get_Relationship());
				System.out.println("Predicato3.get_Object_Complement() "+Predicato3.get_Object_Complement());
				System.out.println("-------------------");
			}			
		}
	}
	
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
				 
				TPredicate Predicato3 = Green.get_Constraint();
				Game.Print("- Green.get_Name() "+Green.get_Name());
				Game.Print("- Green.get_Constraint_Name() "+Green.get_Constraint_Name());
				Game.Print("- Green.get_Type_Quality_Goal() "+Green.get_Type_Quality_Goal());
				Game.Print("- Green.get_TType_Green_Goal() "+Green.get_TType_Green_Goal());
				Game.Print("- Green.get_Fee() "+Green.get_Fee());
				Game.Print("- Green.get_Saliency() "+Green.get_Saliency());
				Game.Print("- Green.Check_Satisfation() "+Green.Check_Satisfation());
				Game.Print("- Green.get_Reward() "+Green.get_Reward());
				Game.Print("- Green.get_Relax_Preference() "+Green.get_Relax_Preference());
				
				Game.Print("- - Predicato3.get_Name() "+Predicato3.get_Name());
				Game.Print("- - Predicato3.get_Subject() "+Predicato3.get_Subject());
				Game.Print("- - Predicato3.get_Relationship() "+Predicato3.get_Relationship());
				Game.Print("- - Predicato3.get_Object_Complement() "+Predicato3.get_Object_Complement());
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
				 
				TPredicate  Predicato3 = null;
				Predicato3 = Quality.get_Constraint();
				Game.Print("Quality.get_Name() "+Quality.get_Name());
				Game.Print("Quality.get_Constraint_Name() "+Quality.get_Constraint_Name());
				Game.Print("Quality.get_Type_Quality_Goal() "+Quality.get_Type_Quality_Goal());
				Game.Print("Quality.get_Saliency() "+Quality.get_Saliency());
				Game.Print("Quality.Check_Satisfation() "+Quality.Check_Satisfation());
				Game.Print("Quality.get_Reward() "+Quality.get_Reward());
				Game.Print("Quality.get_Relax_Preference() "+Quality.get_Relax_Preference());
				
				Game.Print("Predicato3.get_Name() "+Predicato3.get_Name());
				Game.Print("Predicato3.get_Subject() "+Predicato3.get_Subject());
				Game.Print("Predicato3.get_Relationship() "+Predicato3.get_Relationship());
				Game.Print("Predicato3.get_Object_Complement() "+Predicato3.get_Object_Complement());
				Game.Print("-------------------");
			}			
		}
	}
	
	public TWorking_Memory_Maintenance Get_WMM()
	{
		return this.WMM;
	}
	
	public TTCS Get_TCS()
	{
		return this.TCS;
	}
	
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
	
	public void Insert_Green_Goal(String name, String constraint_name, 
			TType_Green_Goal type_Green_Goal, Double saliency, Double reward, Double fee)
	{
		ArrayList<TGreen_Goal> Green_Goals = new ArrayList<TGreen_Goal>();
		
		TGreen_Goal Green_Goal = new TGreen_Goal(name, constraint_name, type_Green_Goal, saliency, 
				reward, fee);
		
		Green_Goals.add(Green_Goal);
		this.WMM.Set_Green_Goals(Green_Goals);
	}
	
	public void Insert_Belief(String name, String predicate_Name, Boolean truth, 
			Object information_Source, LocalDateTime time_stamp, TType_Beliefs type_Belief)
	{
		ArrayList<TBelief_Base> Beliefs = new ArrayList<TBelief_Base>();
		
		TBelief_Base Belief = new TBelief_Base(name, predicate_Name, truth, information_Source, 
				time_stamp, type_Belief);
		
		Beliefs.add(Belief);
		this.WMM.Set_Beliefs(Beliefs);
	}
	
	public void Insert_Predicate(String name, Object subject, TType_Relationship relationship, 
			Object object_complement)
	{
		ArrayList<TPredicate> Predicates = new ArrayList<TPredicate>();
		
		TPredicate Predicate = new TPredicate(name, subject, relationship, object_complement);
		
		Predicates.add(Predicate);
		this.WMM.Set_Predicates(Predicates);
	}
}