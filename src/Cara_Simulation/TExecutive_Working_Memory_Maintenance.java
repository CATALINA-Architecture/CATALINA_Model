package Cara_Simulation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;




public class TExecutive_Working_Memory_Maintenance {
	
	private TLong_Memory Long_Memory;
	private TSensor_Managment Sensor;
	private TAgent Agent;
	
	public TExecutive_Working_Memory_Maintenance(TAgent agent)
	{
		this.Agent = agent;
		this.Long_Memory = new TLong_Memory(this);
		this.Sensor = new TSensor_Managment(this);
	}
	
	/**
	 * This Function is used only in Agent's initialization 
	 * @param predicates: ArrayList<TPredicate>
	 */
	public void Set_Predicates(ArrayList<TPredicate> predicates)
	{
		
		this.Long_Memory.AddAll_Predicates(predicates);
		HashMap<String, TPredicate> Map_Predicates = this.Long_Memory.Get_Map_Predicates();
		Map_Predicates.clear();
		for(TPredicate Predicate: predicates)
		{
			Map_Predicates.put(Predicate.get_Name(), Predicate);
		}
//		Game.Print(predicates.size());
	}
	
	public TPredicate Create_Predicate(Object obj1, TType_Relationship relationship, Object obj2)
	{
		return this.Long_Memory.Create_Predicate(obj1, relationship, obj2);
	}
	
	public HashMap<String, TPredicate> Get_Map_Predicates()
	{
		return this.Long_Memory.Get_Map_Predicates();
	}
	
	public void Set_Beliefs(ArrayList<TBelief_Base> beliefs)
	{
		this.Long_Memory.AddAll_Beliefs(beliefs);
		
		ArrayList<TBelief_Base> Beliefs = this.Long_Memory.Get_Beliefs();
		HashMap<String, TBelief_Base> Map_Beliefs = this.Long_Memory.Get_Map_Beliefs();
		HashMap<String, TPredicate> Map_Predicates = this.Long_Memory.Get_Map_Predicates();
		
		HashMap<String, ArrayList<TPredicate>> Map_Beliefs_and_Predicates = 
										this.Long_Memory.Get_Map_Beliefs_and_Predicates();
		
		for(TBelief_Base belief: Beliefs)
		{
			this.Long_Memory.Belief_List.Add_Belief(belief);
			Map_Beliefs.put(belief.get_Name(), belief);
			TPredicate Predicato = null;
			Predicato = Map_Predicates.get(belief.get_Predicate_name());
			belief.set_Predicate(Predicato);
			
			ArrayList<TPredicate> Predicates = new ArrayList<TPredicate>();
			Predicates.add(Predicato);
			Map_Beliefs_and_Predicates.put(belief.get_Name(), Predicates);
		}
		
		this.Agent.Get_GW().Changed_Beliefs();
	}
	
	public void Set_Green_Goals(ArrayList<TGreen_Goal> green_Goals)
	{
		this.Long_Memory.AddAll_Green_Goals(green_Goals);
		
		ArrayList<TGreen_Goal> Green_Goals = this.Long_Memory.Get_Greens_Goals();
		HashMap<String, TGreen_Goal> Map_Green_Goals = this.Long_Memory.Get_Map_Green_Goals();
		HashMap<String, TPredicate> Map_Predicates = this.Long_Memory.Get_Map_Predicates();
		
		HashMap<String, ArrayList<TPredicate>> Map_Green_Goals_and_Predicates = 
				this.Long_Memory.Get_Map_Green_Goals_and_Predicates();
		
		for(TGreen_Goal Green_Goal: Green_Goals)
		{
			TPredicate constraint =null;
			constraint = Map_Predicates.get(Green_Goal.get_Constraint_Name());
			Green_Goal.set_Constraint(constraint);
			
			Map_Green_Goals.put(Green_Goal.get_Name(), Green_Goal);
			
			ArrayList<TPredicate> Predicates = new ArrayList<TPredicate>();
			Predicates.add(constraint);
			Map_Green_Goals_and_Predicates.put(Green_Goal.get_Name(), Predicates);
		}
	}
	
	public void Set_Quality_Goals(ArrayList<TQuality_Goal> quality_Goals)
	{
		this.Long_Memory.AddAll_Quality_Goals(quality_Goals);
		
		ArrayList<TQuality_Goal> Quality_Goals = this.Long_Memory.Get_Quality_Goals();
		HashMap<String, TQuality_Goal> Map_Quality_Goals = this.Long_Memory.Get_Map_Quality_Goals();
		HashMap<String, TPredicate> Map_Predicates = this.Long_Memory.Get_Map_Predicates();
		
		HashMap<String, ArrayList<TPredicate>> Map_Quality_Goals_and_Predicates = 
				this.Long_Memory.Get_Map_Quality_Goals_and_Predicates();
		
		for(TQuality_Goal Quality_Goal: Quality_Goals)
		{
			TPredicate constraint =null;
			constraint = Map_Predicates.get(Quality_Goal.get_Constraint_Name());
			Quality_Goal.set_Constraint(constraint);
			
			Map_Quality_Goals.put(Quality_Goal.get_Name(), Quality_Goal);
			
			ArrayList<TPredicate> Predicates = new ArrayList<TPredicate>();
			Predicates.add(constraint);
			Map_Quality_Goals_and_Predicates.put(Quality_Goal.get_Name(), Predicates);
		}
	}
	
	public void Set_Functional_Goals(ArrayList<TFunctional_Goal> functional_Goals)
	{
		this.Long_Memory.AddAll_Functional_Goals(functional_Goals);
		
		ArrayList<TFunctional_Goal> Functional_Goals = this.Get_Functional_Goals();
		
		HashMap<String, ArrayList<TPredicate>> Map_Attentional_Goal_and_Predicates = 
								this.Long_Memory.Get_Map_Attentional_Goals_and_Predicates();
		HashMap<String, ArrayList<TBelief_Base>> Map_Attentional_Goal_and_Beliefs = 
								this.Long_Memory.Get_Map_Attentional_Goals_and_Beliefs();
		
		for(TFunctional_Goal Functional_Goal: Functional_Goals)
		{
			if(Functional_Goals.size() > 0)
			{
				
			
				//I create List of Beliefs and Predicates linked directly or indirectly to the Functional Goal
				ArrayList<TBelief_Base> List_Beliefs = new ArrayList<TBelief_Base>();
				ArrayList<TPredicate> List_Predicates = new ArrayList<TPredicate>();
				
				//Insert Final_State, it is a TBelief_Base type
				TBelief_Base Final_State = this.Long_Memory.Get_Map_Beliefs().get(Functional_Goal.get_Final_State_Name());
				Functional_Goal.set_Final_State(Final_State);
				
				//I insert Final_State, and its Predicate, in the List_Beliefs and List_Predicates
				List_Beliefs.add(Final_State);
				List_Predicates.add(Final_State.Predicate);
							
				//Insert Trigger_Condition, it is a TPredicate type
				TPredicate Trigger_Condition = this.Long_Memory.Get_Map_Predicates().get(Functional_Goal.get_Trigger_Condition_Name());
				Functional_Goal.set_Trigger_Condition(Trigger_Condition);
				
				//I insert a Trigger_Condition in the List_Predicates
				if(Trigger_Condition != null)
				{
					if(!List_Predicates.contains(Trigger_Condition))
					{
						List_Predicates.add(Trigger_Condition);				
					}	
				}
				
				
				//Now, I normalize green and quality goals name woth correct goals.
				//So, I must to insert green goals and later quality goals
				// List Green and Quality Goals must to created before Attentional_Goal
				if (Functional_Goal.get_List_Green_Goal() == null)
				{
					ArrayList<TGreen_Goal> List_Green_Goal = new ArrayList<TGreen_Goal>();
					Functional_Goal.set_List_Green_Goal(List_Green_Goal);
				}
				for(String Green_Goal_Name: Functional_Goal.get_List_Green_Goal_Name())
				{
					TGreen_Goal Green_Goal = null;
					Green_Goal = this.Long_Memory.Get_Map_Green_Goals().get(Green_Goal_Name);
	
					Functional_Goal.get_List_Green_Goal().add(Green_Goal);
					
					//I insert a Green_Goal.get_Constraint in the List_Predicates				
					if(!List_Predicates.contains(Green_Goal.get_Constraint()))
					{
						List_Predicates.add(Green_Goal.get_Constraint());				
					}	
				}
				
				if (Functional_Goal.get_List_Quality_Goal() == null)
				{
					ArrayList<TQuality_Goal> List_Quality_Goal = new ArrayList<TQuality_Goal>();
					Functional_Goal.set_List_Quality_Goal(List_Quality_Goal);
				}
				for(String Quality_Goal_Name: Functional_Goal.get_List_Quality_Goal_Name())
				{
					TQuality_Goal Quality_Goal = this.Long_Memory.Get_Map_Quality_Goals().get(Quality_Goal_Name);
					Functional_Goal.get_List_Quality_Goal().add(Quality_Goal);
					
					//I insert a Quality_Goal.get_Constraint in the List_Predicates	
					if(!List_Predicates.contains(Quality_Goal.get_Constraint()))
					{
						List_Predicates.add(Quality_Goal.get_Constraint());				
					}
				}
				
				Map_Attentional_Goal_and_Predicates.put(Functional_Goal.get_Name(), List_Predicates);
				Map_Attentional_Goal_and_Beliefs.put(Functional_Goal.get_Name(), List_Beliefs);
			}
			this.Agent.Get_GW().Changed_Goals();
		}
	}
	
	//TO DO: I must to check this method	
	public void Set_Epistemic_Goals(ArrayList<TEpistemic_Goal> epistemic_Goals)
	{
		if(epistemic_Goals.size() > 0)
		{
			this.Long_Memory.AddAll_Epistemic_Goals(epistemic_Goals);
			
			ArrayList<TEpistemic_Goal> Epistemic_Goals = this.Get_Epistemic_Goals();
			
			HashMap<String, ArrayList<TPredicate>> Map_Attentional_Goal_and_Predicates = 
									this.Long_Memory.Get_Map_Attentional_Goals_and_Predicates();
			HashMap<String, ArrayList<TBelief_Base>> Map_Attentional_Goal_and_Beliefs = 
									this.Long_Memory.Get_Map_Attentional_Goals_and_Beliefs();
	
			
			for(TEpistemic_Goal Epistemic_Goal: Epistemic_Goals)
			{
				ArrayList<TBelief_Base> List_Beliefs = new ArrayList<TBelief_Base>();
				ArrayList<TPredicate> List_Predicates = new ArrayList<TPredicate>();
				
				TBelief_Base belief = this.Long_Memory.Get_Map_Beliefs().get(Epistemic_Goal.get_Belief_Name());
				Epistemic_Goal.set_Belief(belief);
							Game.Print("belief: "+belief.get_Name());
				
				List_Beliefs.add(belief);
				List_Predicates.add(belief.Predicate);
				//Now, I normalize green and quality goals name woth correct goals.
				//So, I must to insert green goals and later quality goals
				// List Green and Quality Goals must to created before Attentional_Goal
				
				if (Epistemic_Goal.get_List_Green_Goal() == null)
				{
					ArrayList<TGreen_Goal> List_Green_Goal = new ArrayList<TGreen_Goal>();
					Epistemic_Goal.set_List_Green_Goal(List_Green_Goal);
				}
				
				for(String Green_Goal_Name: Epistemic_Goal.get_List_Green_Goal_Name())
				{
					TGreen_Goal Green_Goal = this.Long_Memory.Get_Map_Green_Goals().get(Green_Goal_Name);
					Epistemic_Goal.get_List_Green_Goal().add(Green_Goal);
				}
				
				if (Epistemic_Goal.get_List_Quality_Goal() == null)
				{
					ArrayList<TQuality_Goal> List_Quality_Goal = new ArrayList<TQuality_Goal>();
					Epistemic_Goal.set_List_Quality_Goal(List_Quality_Goal);
				}
				for(String Quality_Goal_Name: Epistemic_Goal.get_List_Quality_Goal_Name())
				{
					TQuality_Goal Quality_Goal = this.Long_Memory.Get_Map_Quality_Goals().get(Quality_Goal_Name);
					Epistemic_Goal.get_List_Quality_Goal().add(Quality_Goal);
				}
			}
			
			this.Agent.Get_GW().Changed_Goals();
		}
	}
	
	public ArrayList<TFunctional_Goal> Get_Functional_Goals()
	{
		ArrayList<TFunctional_Goal> Functional_Goals = new ArrayList<TFunctional_Goal>();
		ArrayList<TAttentional_Goal> Goals = this.Long_Memory.Get_Goals();
		
		for(TAttentional_Goal Goal: Goals)
		{
			if(Goal instanceof TFunctional_Goal)
			{
				TFunctional_Goal Functional_Goal = (TFunctional_Goal) Goal;
				Functional_Goals.add(Functional_Goal);
			}
		}
		return Functional_Goals;
	}
	
	public ArrayList<TFunctional_Goal> Get_Inhibited_Functional_Goals()
	{
		ArrayList<TFunctional_Goal> Inhibited_Functional_Goals = new ArrayList<TFunctional_Goal>();
		ArrayList<TAttentional_Goal> Goals = this.Long_Memory.Get_Inhibited_Goals();
		
		for(TAttentional_Goal Goal: Goals)
		{
			if(Goal instanceof TFunctional_Goal)
			{
				TFunctional_Goal Functional_Goal = (TFunctional_Goal) Goal;
				Inhibited_Functional_Goals.add(Functional_Goal);
			}
		}
//		Inhibited_Functional_Goals = this.Long_Memory.Get_Inhibited_Goals() 
		return Inhibited_Functional_Goals;
	}
	
	public ArrayList<String> Get_Functional_Goals_Names()
	{
		ArrayList<String> Functional_Goals = new ArrayList<String>();
		ArrayList<TAttentional_Goal> Goals = this.Long_Memory.Get_Goals();
		
		for(TAttentional_Goal Goal: Goals)
		{
			if(Goal instanceof TFunctional_Goal)
			{
				TFunctional_Goal Functional_Goal = (TFunctional_Goal) Goal;
				Functional_Goals.add(Functional_Goal.get_Name());
			}
		}
		return Functional_Goals;
	}
	
	public ArrayList<TEpistemic_Goal> Get_Epistemic_Goals()
	{
		ArrayList<TEpistemic_Goal> Epistemic_Goals = new ArrayList<TEpistemic_Goal>();
		ArrayList<TAttentional_Goal> Goals = this.Long_Memory.Get_Goals();
		
		for(TAttentional_Goal Goal: Goals)
		{
			if(Goal instanceof TEpistemic_Goal)
			{
				TEpistemic_Goal Epistemic_Goal = (TEpistemic_Goal) Goal;
				Epistemic_Goals.add(Epistemic_Goal);
			}
		}
		return Epistemic_Goals;
	}
	
	public ArrayList<String> Get_Epistemic_Goals_Names()
	{
		ArrayList<String> Epistemic_Goals = new ArrayList<String>();
		ArrayList<TAttentional_Goal> Goals = this.Long_Memory.Get_Goals();
		
		for(TAttentional_Goal Goal: Goals)
		{
			if(Goal instanceof TEpistemic_Goal)
			{
				TEpistemic_Goal Epistemic_Goal = (TEpistemic_Goal) Goal;
				Epistemic_Goals.add(Epistemic_Goal.get_Name());
			}
		}
		return Epistemic_Goals;
	}
	
	public ArrayList<TBelief_Base> Get_Beliefs()
	{
		return this.Long_Memory.Get_Beliefs();
	}
	
	public ArrayList<TPredicate> Get_Predicates()
	{
		return this.Long_Memory.Get_Predicates();
	}
	
	public ArrayList<TGreen_Goal> Get_Green_Goals()
	{
		return this.Long_Memory.Get_Greens_Goals();
	}
	
	public ArrayList<TQuality_Goal> Get_Quality_Goals()
	{
		return this.Long_Memory.Get_Quality_Goals();
	}
	
	public ArrayList<TAttentional_Goal> Get_Attentional_Goals()
	{
		return this.Long_Memory.Get_Goals();
	}
	
	///
	///
	///
	///REGION
	///
	///
	///
	
	public Environment Get_Map()
	{
		return this.Long_Memory.Get_Map();
	}
	
	public void Set_Map(Environment map)
	{
		this.Long_Memory.Set_Map(map);
	}
	
	public HashMap<String, ArrayList<TPredicate>> Get_Map_Attentional_Goals_and_Predicates()
	{
		return this.Long_Memory.Get_Map_Attentional_Goals_and_Predicates();
	}
	
	public HashMap<String, ArrayList<TBelief_Base>> Get_Map_Attentional_Goals_and_Beliefs()
	{
		return this.Long_Memory.Get_Map_Attentional_Goals_and_Beliefs();
	}
	
	public ArrayList<String> Get_Inhibited_Goals()
	{
		ArrayList<String> Inhibited_Goals = new ArrayList<String>();
		ArrayList<TAttentional_Goal> Goals = this.Long_Memory.Get_Inhibited_Goals();
		
//		for(TAttentional_Goal Goal: Goals)
//		{
//			if(Goal instanceof TFunctional_Goal)
//			{
//				TFunctional_Goal Functional_Goal = (TFunctional_Goal) Goal;
//				Inhibited_Goals.add(Functional_Goal.get_Name());
//			}
//		}
		return Inhibited_Goals;
	}
	

	public void Add_Desire(TDesire desire)
	{
		this.Long_Memory.Add_Desire(desire);	
	}
	
	public void Add_Desire(ArrayList<TDesire> desires)
	{
		this.Long_Memory.Add_Desire(desires);
		
	}
	
	public TBelief_Base Create_Belief(TPredicate Predicate, Boolean truth, Object information_Source,
			LocalDateTime time_stamp, TType_Beliefs type_Belief)
	{
		return this.Long_Memory.Create_Belief(Predicate, truth, information_Source, time_stamp, type_Belief);
	}
	
	public void Set_Beliefs_Number(int value)
	{
		this.Long_Memory.Set_Beliefs_Number(value);
	}
	
	public ArrayList<TBelief_Base> Get_Inhibited_Beliefs_From_Type_Belief(TType_Beliefs Type_Beliefs)
	{
		ArrayList<TBelief_Base> Beliefs = new ArrayList<TBelief_Base>();
		for(TBelief_Base Belief: this.Get_Beliefs())
		{
//			Game.Print(Belief.get_Name()+" - "+Belief.get_Type_Belief());
			if(Belief.get_Type_Belief() == Type_Beliefs)
			{
				Beliefs.add(Belief);
			}
		}
	
		return Beliefs;
	}
	
	public ArrayList<TBelief_Base> Get_Inhibited_Beliefs()
	{
		return this.Long_Memory.Get_Inhibited_Beliefs();
	}
	
	public void Set_Inhibited_Beliefs(ArrayList<TBelief_Base> inhibited_Beliefs)
	{
		this.Long_Memory.Set_Inhibited_Beliefs(inhibited_Beliefs);
	}
	
	public void Set_Inhibited_Goals(ArrayList<TAttentional_Goal> Inhibited_Goals)
	{
		this.Long_Memory.Set_Inhibited_Goals(Inhibited_Goals);
	}
	
	public void Set_Regions()
	{
		final StackTraceElement[] ste = Thread.currentThread().getStackTrace();

		  //System. out.println(ste[ste.length-depth].getClassName()+"#"+ste[ste.length-depth].getMethodName());
		  // return ste[ste.length - depth].getMethodName();  //Wrong, fails for depth = 0
//		  return ste[ste.length - 1 - depth].getMethodName();
		this.Long_Memory.Set_Regions();
	}
	
	public TRegion Get_Regions()
	{
		return this.Long_Memory.Get_Regions();
	}
	
	public void Set_Inhibition_Regions(TRegion inhibition_Regions)
	{
		this.Long_Memory.Set_Inhibition_Regions(inhibition_Regions);

	}
	
	public TRegion Get_Inhibition_Regions()
	{
		return this.Long_Memory.Get_Inhibition_Regions();
	}

	public TEpistemic_Goal Stimulus_to_Goal(TSalient_Belief Salient_Belief)
	{
		
		
		int Inc_Epistemic_Goals_Number = this.Long_Memory.Get_Inc_Epistemic_Goals_Number() + 1;
		String Epistemic_Goal_Name = "eg" + Inc_Epistemic_Goals_Number;
		double reward = 0.0;			//da dove lo prendo?
		double relax_preference = 0.0;
		
		ArrayList<TGreen_Goal> Green_Goal_list = new ArrayList<TGreen_Goal>();
		ArrayList<TQuality_Goal> Quality_Goal_list = new ArrayList<TQuality_Goal>();
		
		TEpistemic_Goal Epistemic_Goal = new TEpistemic_Goal(Epistemic_Goal_Name, Salient_Belief, 
				Salient_Belief.Get_Saliency(), reward, relax_preference, Green_Goal_list, Quality_Goal_list);
		
		this.Long_Memory.Add_Epistemic_Goal(Epistemic_Goal);

		HashMap<String, ArrayList<TPredicate>> Map_Attentional_Goal_and_Predicates = 
						this.Long_Memory.Get_Map_Attentional_Goals_and_Predicates();
		HashMap<String, ArrayList<TBelief_Base>> Map_Attentional_Goal_and_Beliefs = 
						this.Long_Memory.Get_Map_Attentional_Goals_and_Beliefs();

		//I create List of Beliefs and Predicates linked directly or indirectly to the Functional Goal
		ArrayList<TBelief_Base> List_Beliefs = new ArrayList<TBelief_Base>();
		ArrayList<TPredicate> List_Predicates = new ArrayList<TPredicate>();
			
		//I insert Salient_Belief in the List_Beliefs 
		List_Beliefs.add(Salient_Belief);

		//Now, I normalize green and quality goals name woth correct goals.
		//So, I must to insert green goals and later quality goals
		// List Green and Quality Goals must to created before Attentional_Goal

		for(TGreen_Goal Green_Goal: Green_Goal_list)
		{
			//I insert a Green_Goal.get_Constraint in the List_Predicates				
			if(!List_Predicates.contains(Green_Goal.get_Constraint()))
			{
				List_Predicates.add(Green_Goal.get_Constraint());				
			}	
		}
		
		for(TQuality_Goal Quality_Goal: Quality_Goal_list)
		{
			//I insert a Green_Goal.get_Constraint in the List_Predicates				
			if(!List_Predicates.contains(Quality_Goal.get_Constraint()))
			{
				List_Predicates.add(Quality_Goal.get_Constraint());				
			}	
		}

		Map_Attentional_Goal_and_Predicates.put(Epistemic_Goal.get_Name(), List_Predicates);
		Map_Attentional_Goal_and_Beliefs.put(Epistemic_Goal.get_Name(), List_Beliefs);
		
		return Epistemic_Goal;
	}
	
	public boolean Perception_Processing(int i)
	{
		this.Agent.Get_GW().Print_Data(2, 0);
		Game.Print("Perception Processing number: "+i);
		
		boolean result = true;
		TPerception Perception = this.Get_Perception();
		Information_Selection(Perception);
		
		//for demonstration purposes
		if (Perception != null)
		{
			TTriple_Object Preceived_Data = Perception.get_Perceived_Data();
			String String_TVS_Answer = (String) Preceived_Data.Get_Object_First();
			switch(String_TVS_Answer)
			{
			case "Correct move!", "See Semaphore for Route Status" :
				break;
			default:
				Game.Scenario_Number++;
				Game.Print("***********************************");
				Game.Print("***********************************");
				Game.Print("***********************************");
				Game.Print("***********************************");
				Game.Print("***********************************");
				Game.Print("***********************************");
				Game.Print("***********************************");
//				Game.Print("*********************************** "+ String_TVS_Answer);
				Game.Print("*******  CHANGE OF SCENARIO  ******");
				Game.Print("*******  SCENARIO Number "+ Game.Scenario_Number +" ********");
				Game.Print("***********************************");
				Game.Print("***********************************");
				Game.Print("***********************************");
				Game.Print("***********************************");
				Game.Print("***********************************");
				Game.Print("***********************************");
				Game.Print("***********************************");
			}
		}
		else
		{
			try 
			{
				
			}
			catch (Exception e) {
				Game.Print("Something went wrong in method: Insert_New_Desires.");
		      Game.Print("Message Error: "+e.getMessage());
		      result = false;
		    }
			
		}
		this.Agent.Get_GW().Print_Data(1, 0);
		return result;
	}
	
	public TPerception Get_Perception()
	{
		return this.Sensor.Get_Last_Acquired_Perception();
	}
	
	public TSensor_Managment Get_Sensor()
	{
		return this.Sensor;
	}
	
	private boolean Information_Selection(TPerception Perception)
	{
		boolean result = true;
		if(Perception == null)
		{
			return result;
				
		}
		else
		{
			
			Game.Print("My WMM's \"Information Selection\" analyzes the Stimulus.");
			TTriple_Object Preceived_Data = Perception.get_Perceived_Data();
			String String_TVS_Answer = (String) Preceived_Data.Get_Object_First();
			
			this.Agent.Get_GW().Updated_Salient_Beliefs_for_print = true;
			switch(String_TVS_Answer)
			{
				case "Correct move!":
					
					this.Manage_Correct_Movement(Perception);
					//In this case, I ahve a salient belief with saliency lower then 
					//the saliency/attention thresholds, so I don't print this
					this.Agent.Get_GW().Updated_Salient_Beliefs_for_print = false;
					break;
					
				case "See Semaphore for Route Status":
					this.Manage_See_Semaphore_for_Route_Status(Perception);
					this.Agent.Get_GW().Updated_Salient_Beliefs_for_print = false;
					break;
				
				case "Acquired Route Status":
					this.Manage_Acquired_Route_Status(Perception);
					this.Agent.Get_GW().Updated_Salient_Beliefs_for_print = false;
					break;
				case "Ask how long the route is closed"://to acquire the duration of closed route
					this.Manage_Ask_Closed_Route_Duration(Perception);
					this.Agent.Get_GW().Updated_Salient_Beliefs_for_print = false;
					break;
				case "Acquired Closed Route Duration"://to acquire the duration of closed route
					this.Manage_Acquired_Closed_Route_Duration(Perception);
					this.Agent.Get_GW().Updated_Salient_Beliefs_for_print = false;
					break;
			}
		}
		return result;
	}
	

	
	public void Manage_Ask_Closed_Route_Duration(TPerception Perception)
	{
		TRegion Inhibition_Regions = this.Agent.Get_GW().Get_Inhibition_Regions();
		ArrayList<TBelief_Base> Unhinibited_Beliefs = this.Agent.Get_GW().Get_UnInhibited_Beliefs();
		TSalient_Belief Salient_Belief;// = (TSalient_Belief) this.Get_Inhibited_Beliefs_From_Type_Belief(TType_Beliefs.Stimulus_Temporary_Closed_Route).getFirst();
		
		TTriple_Object Preceived_Data = Perception.get_Perceived_Data();
		
		int Integer_Route = (int) Preceived_Data.Get_Object_Second();
		
		ArrayList<TBelief_Base> Beliefs2 = this.Agent.Get_GW().Get_Beliefs_From_Type_Belief(TType_Beliefs.Stimulus_Temporary_Closed_Route);
		Salient_Belief = (TSalient_Belief) Beliefs2.getFirst();
		TPredicate Predicate = Salient_Belief.get_Predicate();
		
		Predicate.Set_Subject(Integer_Route);
		Salient_Belief.Update_Saliency(0.9);
		
		Game.Print_Colored_Text("I want to ask to TCS: How long will the route be closed? "+Integer_Route, 5);
		this.Agent.Get_GW().Update_Belief_by_Stimulus(Salient_Belief);	
	}
	
	public void Manage_See_Semaphore_for_Route_Status(TPerception Perception)
	{
		TRegion Inhibition_Regions = this.Agent.Get_GW().Get_Inhibition_Regions();
		ArrayList<TBelief_Base> Unhinibited_Beliefs = this.Agent.Get_GW().Get_UnInhibited_Beliefs();
		TSalient_Belief Salient_Belief;// = (TSalient_Belief) this.Get_Inhibited_Beliefs_From_Type_Belief(TType_Beliefs.Stimulus_Temporary_Closed_Route).getFirst();
		
		TTriple_Object Preceived_Data = Perception.get_Perceived_Data();
		
		int Integer_Route = (int) Preceived_Data.Get_Object_Second();
		
		ArrayList<TBelief_Base> Beliefs2 = this.Agent.Get_GW().Get_Beliefs_From_Type_Belief(TType_Beliefs.Stimulus_Route_Status);
		Salient_Belief = (TSalient_Belief) Beliefs2.getFirst();
		TPredicate Predicate = Salient_Belief.get_Predicate();
		
		Predicate.Set_Subject(Integer_Route);
		Salient_Belief.Update_Saliency(0.9);
		
		Game.Print_Colored_Text("I want to see the semaphore for get the route status for route: "+Integer_Route, 5);
		this.Agent.Get_GW().Update_Belief_by_Stimulus(Salient_Belief);	
	}
	
	public void Manage_Correct_Movement(TPerception Perception)
	{
		
		TRegion Inhibition_Regions = this.Agent.Get_GW().Get_Inhibition_Regions();
		ArrayList<TBelief_Base> Unhinibited_Beliefs = this.Agent.Get_GW().Get_UnInhibited_Beliefs();
		TSalient_Belief Salient_Belief;// = (TSalient_Belief) this.Get_Inhibited_Beliefs_From_Type_Belief(TType_Beliefs.Stimulus_Temporary_Closed_Route).getFirst();
		
		TTriple_Object Preceived_Data = Perception.get_Perceived_Data();
		Game.Print("I moved correctly.");
		Game.Print("Now, I update several information in my Beliefs and in plan actions.");
		ArrayList<TBelief_Base> Beliefs2 = this.Agent.Get_GW().Get_Beliefs_From_Type_Belief(TType_Beliefs.Stimulus_Ok_Correct_Movement);
		Salient_Belief = (TSalient_Belief) Beliefs2.getFirst();
		
		TPosition_Train_Coords Train_Coords = (TPosition_Train_Coords) Preceived_Data.Get_Object_Second(); 
		
		TBelief_Base Current_Route;
		TBelief_Base Current_Step;
		TBelief_Base Current_Station;
		
		ArrayList<TBelief_Base> Beliefs_Route = this.Agent.Get_GW().Get_Beliefs_From_Type_Belief(TType_Beliefs.Belief_Current_Route);
		ArrayList<TBelief_Base> Beliefs_Step = this.Agent.Get_GW().Get_Beliefs_From_Type_Belief(TType_Beliefs.Belief_Current_Step);
		ArrayList<TBelief_Base> Beliefs_Station = this.Agent.Get_GW().Get_Beliefs_From_Type_Belief(TType_Beliefs.Belief_Current_Station);
		Current_Route = Beliefs_Route.getFirst();
		Current_Step = Beliefs_Step.getFirst();
		Current_Station = Beliefs_Station.getFirst();
		Game.Print("I Update Position Beliefs:");
		Boolean Neet_to_Change_Visited_Station_Belief = false;
		Game.Print("Before, the Previous Current Position Station was: "+Current_Station.Predicate.get_Object_Complement());
		Game.Print("Before, the Previous Current Position Route was: "+Current_Route.Predicate.get_Object_Complement());
		Game.Print("Before, the Previous Current Position Step was: "+Current_Step.Predicate.get_Object_Complement());
		
		//I update Current_Route Beliefs
		Current_Route.Predicate.set_Object_Complement(Train_Coords.Get_Route());
		
		//I update Current_Step Beliefs
		Current_Step.Predicate.set_Object_Complement(Train_Coords.Get_Step());
		
		//I update the time for the Agent
		ArrayList<TBelief_Base> Temp_Beliefs = this.Agent.Get_GW().Get_Beliefs_From_Type_Belief(TType_Beliefs.Belief_Current_Time);
		TBelief_Base Temp_Belief = Temp_Beliefs.getFirst();
		
		LocalDateTime Current_Time = (LocalDateTime) Temp_Belief.get_Predicate().get_Object_Complement();
		int One_Hour = 60;
		LocalDateTime Temp_Time = Current_Time.plusMinutes(One_Hour);
		Temp_Belief.get_Predicate().set_Object_Complement(Temp_Time);
				
		
		// I update Current_Stationv Beliefs and, if it is necessary, also the Belief_Visited_Station
		// in case it visited another Station
		if(Current_Station.Predicate.get_Object_Complement() != Train_Coords.Get_Station())
		{
			
			Neet_to_Change_Visited_Station_Belief = true;
			
			ArrayList<TBelief_Base> Belief_Visited_Stations = this.Agent.Get_GW().Get_Beliefs_From_Type_Belief(TType_Beliefs.Belief_Visited_Station);
			for(TBelief_Base Belief: Belief_Visited_Stations)
			{
				if((Station) Belief.get_Predicate().get_Subject() == Train_Coords.Get_Station() &&
				   (TType_Object_Complement) Belief.get_Predicate().get_Object_Complement() == 	TType_Object_Complement.Me)
				{
					Belief.set_Truth(true);
					Belief.set_Time_stamp(Temp_Time);
					Belief.set_Information_Source(TType_Object_Complement.TCS);
					this.Agent.Get_GW().Print_Belief(Belief);
				}
			}
		}

		Current_Station.Predicate.set_Object_Complement(Train_Coords.Get_Station());
		
		Game.PrintLn();
		Game.Print("Now, the Updated Current Position Station is: "+Current_Station.Predicate.get_Object_Complement());
		Game.Print("Now, the Updated Current Position Route is: "+Current_Route.Predicate.get_Object_Complement());
		Game.Print("Now, the Updated Current Position Step is: "+Current_Step.Predicate.get_Object_Complement());
		
		//Now I update the next action the agent should perform
		ArrayList<TIntention> Intentions = this.Agent.Get_GW().Get_Intentions();
		TIntention Intention = Intentions.getFirst();
		TDesire Desire = Intention.get_Desire();

		TOption Selected_Option = Desire.get_Option_List().get(Intention.get_Seleted_Option_Id());
		Selected_Option.Inc_Action_To_Do_ID();
		TPredicate Predicate = Salient_Belief.get_Predicate();
		
	
		
		//This is the Saslient_Belief for Epistemic_Goal
		Salient_Belief.Update_Saliency(0.1);
		
		
		
		
		//Temp_Beliefs = this.Agent.Get_GW().Get_Beliefs_From_Type_Belief(TType_Beliefs.Belief_Path_Taken_For_Belief);
		Temp_Belief = this.Agent.Get_GW().Get_Belief_From_Type_Belief_and_Subject(TType_Beliefs.Belief_Path_Taken_For_Belief,
				Desire.get_Attentional_Goal().get_Name());
		
		
		
//		this.Agent.Get_GW().Print_Belief(Temp_Belief);
		TPredicate Predicate_Temp_Belief = Temp_Belief.get_Predicate();
		ArrayList<Integer> Route_List = (ArrayList<Integer>) Predicate_Temp_Belief.get_Object_Complement();
		
		if((!Route_List.contains(Route_List)) && (Train_Coords.Get_Route()!=-1))
		{
			Route_List.add(Train_Coords.Get_Route());
			Predicate_Temp_Belief.set_Object_Complement(Route_List);
			Temp_Belief.set_Predicate(Predicate_Temp_Belief);
			Temp_Belief.set_Time_stamp(Temp_Time);
			Temp_Belief.set_Truth(true);;
		}
		
		//Now I have to update Other information
		//I update the temporrary closed ruotes (I decrease their time)
		this.Update_Temporary_Closed_Routes();
		
		
		this.Agent.Get_GW().Update_Belief_by_Stimulus(Salient_Belief);		
	}
	
	public TType_Route_Status Manage_Acquired_Route_Status(TPerception Perception)
	{
		TType_Route_Status Route_Status = null; 
		TRegion Inhibition_Regions = this.Agent.Get_GW().Get_Inhibition_Regions();
		ArrayList<TBelief_Base> Unhinibited_Beliefs = this.Agent.Get_GW().Get_UnInhibited_Beliefs();
		TSalient_Belief Salient_Belief;// = (TSalient_Belief) this.Get_Inhibited_Beliefs_From_Type_Belief(TType_Beliefs.Stimulus_Temporary_Closed_Route).getFirst();
		
		TTriple_Object Preceived_Data = Perception.get_Perceived_Data();
		
		int Integer_Route = (int) Preceived_Data.Get_Object_Second();
		Route_Status = (TType_Route_Status) Preceived_Data.Get_Object_Third();
		Game.Print("The aquired Route_Status (by my virtual camera) is: "+Route_Status);
		
		ArrayList<TBelief_Base> Visited_Station = this.Agent.Get_GW().Get_Beliefs_From_Type_Belief(TType_Beliefs.Belief_Visited_Station);

		//I update the belief for route status of route Integer_Route
		TBelief_Base Belief = null;// = this.Agent.Get_GW().Get_UnInhibited_Beliefs_From_Type_Belief_and_Subject(TType_Beliefs.Belief_Route_Status, Integer_Route);
		ArrayList<TBelief_Base> Belifs = this.Agent.Get_GW().Get_UnInhibited_Beliefs();
		LocalDateTime A_Time = this.Agent.Get_GW().Get_Current_Time();
		for(TBelief_Base Bel: Belifs)
		{
			if(Bel.get_Type_Belief() == TType_Beliefs.Belief_Route_Status)
			{
				if( (int) Bel.Predicate.get_Subject() == Integer_Route)
				{
					Belief = Bel;
					Belief.set_Truth(true);
					Belief.set_Time_stamp(A_Time);
				}
			}
		}
		
		Belief.Predicate.set_Object_Complement(Route_Status);
		
		//Now I Update next action 
		ArrayList<TIntention> Intentions = this.Agent.Get_GW().Get_Intentions();

		TIntention Intention = Intentions.getFirst();
		TDesire Desire = Intention.get_Desire();

		TAttentional_Goal Attentional_Goal = Desire.get_Attentional_Goal();
		
		TOption Selected_Option = Desire.get_Option_List().get(Intention.get_Seleted_Option_Id());
		
		Selected_Option.Inc_Action_To_Do_ID();

		if(Selected_Option.Get_Action_To_Do_ID() >= Selected_Option.get_Plan_Actions().size())
		{
			TDesire Desire_to_Delete = Intention.get_Desire();
			Game.Print_Colored_Text("My intention to acquire the route status color has been fulfilled", 5);
			Game.Print("Now I delete the Selected Intention, with its desire and I move the Epistemic Goal in my goal satisfied List");
			Game.Print(Desire_to_Delete.Get_Name()+": "+Desire_to_Delete.get_Attentional_Goal().get_Saliency()+
					" - Epistemic Goal: "+Desire_to_Delete.get_Attentional_Goal().get_Name());
			this.Agent.Get_GW().Delete_Intention(Intention);
			
			this.Agent.Get_GW().Get_Goals().remove(Attentional_Goal);
			this.Long_Memory.Delete_Goal(Attentional_Goal);
			this.Long_Memory.Insert_Attentional_Goals_Satisfied(Attentional_Goal);
			
		}
		
		ArrayList<TBelief_Base> Beliefs2 = this.Agent.Get_GW().Get_Beliefs_From_Type_Belief(TType_Beliefs.Stimulus_Route_Status);
		Salient_Belief = (TSalient_Belief) Beliefs2.getFirst();
		Salient_Belief.Update_Saliency(0.1);
		
		this.Agent.Get_GW().Update_Belief_by_Stimulus(Salient_Belief);	
//		Game.End_Game();
		
		return Route_Status;

		//BISOGNA CANCELLARE IL DESIRE E IL SALIENT_BELIEF
		//INOLTRE BISOGNA CREARE UNO STIMOLO CON SALIENCY BASSISSIMA DI TIPO ROTTA ACQUISITA
		
	}
	
	public void Manage_Acquired_Closed_Route_Duration(TPerception Perception)
	{
		TRegion Inhibition_Regions = this.Agent.Get_GW().Get_Inhibition_Regions();
		ArrayList<TBelief_Base> Unhinibited_Beliefs = this.Agent.Get_GW().Get_UnInhibited_Beliefs();
		TSalient_Belief Salient_Belief;// = (TSalient_Belief) this.Get_Inhibited_Beliefs_From_Type_Belief(TType_Beliefs.Stimulus_Temporary_Closed_Route).getFirst();
		
		TTriple_Object Preceived_Data = Perception.get_Perceived_Data();
		Game.Print("I Acquired Closed _Route Duration.");
		Game.Print("Now, I update several information in my Beliefs and in plan actions.");
		ArrayList<TBelief_Base> Beliefs2 = this.Agent.Get_GW().Get_Beliefs_From_Type_Belief(TType_Beliefs.Stimulus_Temporary_Closed_Route);
		Salient_Belief = (TSalient_Belief) Beliefs2.getFirst();
		
		Salient_Belief.Update_Saliency(0.1);

		Integer Duration = (Integer) Preceived_Data.Get_Object_Third(); 
		Game.Print("Duration: "+Duration);
		
		int Integer_Route = (int) Preceived_Data.Get_Object_Second();
		int Specular_Integer_Route = this.Get_Map().Get_Specular_Route(Integer_Route);
		Game.Print("The duration time acquired for the temporarily closed route is: "+Duration);
		
		for(TBelief_Base Bel: Unhinibited_Beliefs)
		{
//			Game.Print(Bel.get_Name()+": "+Bel.get_Type_Belief()+" - "+Bel.get_Predicate().get_Subject());
			if(Bel.get_Type_Belief() == TType_Beliefs.Belief_Temporary_Closed_Route)
			{
				HashMap<Integer, Integer> Routes = (HashMap<Integer, Integer>) Bel.get_Predicate().get_Subject();
				
				if( !Routes.containsKey(Integer_Route) )
				{
					Routes.put(Integer_Route, Duration);
					Routes.put(Specular_Integer_Route, Duration);
					Route route = this.Get_Map().Get_Route(Integer_Route);
					route.Set_Duration_Closed(Duration);
					
					route = this.Get_Map().Get_Route(Specular_Integer_Route);
					route.Set_Duration_Closed(Duration);
				}
				Bel.get_Predicate().Set_Subject(Routes);
			}
		}
		
		
		ArrayList<TIntention> Intentions = this.Agent.Get_GW().Get_Intentions();

		TIntention Intention = Intentions.getFirst();
		TDesire Desire = Intention.get_Desire();

		TAttentional_Goal Attentional_Goal = Desire.get_Attentional_Goal();
		
		TOption Selected_Option = Desire.get_Option_List().get(Intention.get_Seleted_Option_Id());
		
		Selected_Option.Inc_Action_To_Do_ID();

		if(Selected_Option.Get_Action_To_Do_ID() >= Selected_Option.get_Plan_Actions().size())
		{
			TDesire Desire_to_Delete = Intention.get_Desire();
			Game.Print_Colored_Text("My intention to acquire the Duration for the Closed Route has been fulfilled", 5);
			Game.Print("Now I delete the Selected Intention, with its desire and I move the Epistemic Goal in my goal satisfied List");
			Game.Print(Desire_to_Delete.Get_Name()+": "+Desire_to_Delete.get_Attentional_Goal().get_Saliency()+
					" - Epistemic Goal: "+Desire_to_Delete.get_Attentional_Goal().get_Name());
			this.Agent.Get_GW().Delete_Intention(Intention);
			
			this.Agent.Get_GW().Get_Goals().remove(Attentional_Goal);
			this.Long_Memory.Delete_Goal(Attentional_Goal);
			this.Long_Memory.Insert_Attentional_Goals_Satisfied(Attentional_Goal);
			
		}
		
//		Beliefs2 = this.Agent.Get_GW().Get_Beliefs_From_Type_Belief(TType_Beliefs.Stimulus_Temporary_Closed_Route);
//		Salient_Belief = (TSalient_Belief) Beliefs2.getFirst();
//		Salient_Belief.Update_Saliency(0.1);
		
		this.Agent.Get_GW().Update_Belief_by_Stimulus(Salient_Belief);	
		
		
	}
	
	public TAgent Get_Agent()
	{
		return this.Agent;
	}
	
	public void Update_Temporary_Closed_Routes()
	{
		ArrayList<TBelief_Base> Unhinibited_Beliefs = this.Agent.Get_GW().Get_UnInhibited_Beliefs();
		for(TBelief_Base Bel: Unhinibited_Beliefs)
		{
			if(Bel.get_Type_Belief() == TType_Beliefs.Belief_Temporary_Closed_Route)
			{
				HashMap<Integer, Integer> Routes = (HashMap<Integer, Integer>) Bel.Predicate.get_Subject();
				for(Integer Route_ID: Routes.keySet())
				{
					Integer Value = Routes.get(Route_ID);
					Value = Value - 1;
					if(Value>0)
					{
						Routes.put(Route_ID, Value);	
						Route route = this.Get_Map().Get_Route(Route_ID);
						route.Set_Duration_Closed(Value);
					}
					else
					{
						if(Routes.containsKey(Route_ID))
						{
							Routes.remove(Route_ID);
							Route route = this.Get_Map().Get_Route(Route_ID);
							route.Set_Duration_Closed(0);
						}
					}
				}
				Bel.Predicate.Set_Subject(Routes);
			}
		}
	}

}
