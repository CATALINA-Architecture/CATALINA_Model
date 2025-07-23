package Catalina_Simulation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JOptionPane;

public class TExecutive_Working_Memory_Maintenance {
	
	private TLong_Memory Long_Memory;
	private TSensor_Managment Sensor;
	private TAgent Agent;
	
	private TStimulus Temporary_Stimulus;
	
	public TExecutive_Working_Memory_Maintenance(TAgent agent)
	{
		this.Agent = agent;
		this.Long_Memory = new TLong_Memory(this);
		this.Sensor = new TSensor_Managment(this);
		
		TStimulus Temporary_Stimulus = null;
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
			Map_Predicates.put(Predicate.Get_Name(), Predicate);
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
	
	public void Set_Beliefs(ArrayList<TBelief> beliefs)
	{
		this.Long_Memory.AddAll_Beliefs(beliefs);
		
		ArrayList<TBelief> Beliefs = this.Long_Memory.Get_Beliefs();
		HashMap<String, TBelief> Map_Beliefs = this.Long_Memory.Get_Map_Beliefs();
		HashMap<String, TPredicate> Map_Predicates = this.Long_Memory.Get_Map_Predicates();
		
		HashMap<String, ArrayList<TPredicate>> Map_Beliefs_and_Predicates = 
										this.Long_Memory.Get_Map_Beliefs_and_Predicates();
		
		for(TBelief belief: Beliefs)
		{
			this.Long_Memory.Belief_List.Add_Belief(belief);
			Map_Beliefs.put(belief.Get_Name(), belief);
			TPredicate Predicato = null;
			Predicato = Map_Predicates.get(belief.Get_Predicate_name());
			belief.Set_Predicate(Predicato);
			
			ArrayList<TPredicate> Predicates = new ArrayList<TPredicate>();
			Predicates.add(Predicato);
			Map_Beliefs_and_Predicates.put(belief.Get_Name(), Predicates);
		}
		
		this.Agent.Get_GW().Changed_Beliefs();
	}
	
	public void Set_Green_Standing_Desires(ArrayList<TGreen_Standing_Desire> green_Goals)
	{
		this.Long_Memory.AddAll_Green_Goals(green_Goals);
		
		ArrayList<TGreen_Standing_Desire> Green_Goals = this.Long_Memory.Get_Greens_Goals();
		HashMap<String, TGreen_Standing_Desire> Map_Green_Goals = this.Long_Memory.Get_Map_Green_Goals();
		HashMap<String, TPredicate> Map_Predicates = this.Long_Memory.Get_Map_Predicates();
		
		HashMap<String, ArrayList<TPredicate>> Map_Green_Goals_and_Predicates = 
				this.Long_Memory.Get_Map_Green_Goals_and_Predicates();
		
		for(TGreen_Standing_Desire Green_Goal: Green_Goals)
		{
			TPredicate constraint =null;
			constraint = Map_Predicates.get(Green_Goal.Get_Constraint_Name());
			Green_Goal.set_Constraint(constraint);
			
			Map_Green_Goals.put(Green_Goal.Get_Name(), Green_Goal);
			
			ArrayList<TPredicate> Predicates = new ArrayList<TPredicate>();
			Predicates.add(constraint);
			Map_Green_Goals_and_Predicates.put(Green_Goal.Get_Name(), Predicates);
		}
	}
	
	public void Set_Quality_Standing_Desires(ArrayList<TQuality_Standing_Desire> quality_Goals)
	{
		this.Long_Memory.AddAll_Quality_Goals(quality_Goals);
		
		ArrayList<TQuality_Standing_Desire> Quality_Goals = this.Long_Memory.Get_Quality_Goals();
		HashMap<String, TQuality_Standing_Desire> Map_Quality_Goals = this.Long_Memory.Get_Map_Quality_Goals();
		HashMap<String, TPredicate> Map_Predicates = this.Long_Memory.Get_Map_Predicates();
		
		HashMap<String, ArrayList<TPredicate>> Map_Quality_Goals_and_Predicates = 
				this.Long_Memory.Get_Map_Quality_Goals_and_Predicates();
		
		for(TQuality_Standing_Desire Quality_Goal: Quality_Goals)
		{
			TPredicate constraint =null;
			constraint = Map_Predicates.get(Quality_Goal.Get_Constraint_Name());
			Quality_Goal.set_Constraint(constraint);
			
			Map_Quality_Goals.put(Quality_Goal.Get_Name(), Quality_Goal);
			
			ArrayList<TPredicate> Predicates = new ArrayList<TPredicate>();
			Predicates.add(constraint);
			Map_Quality_Goals_and_Predicates.put(Quality_Goal.Get_Name(), Predicates);
		}
	}
	
	public void Set_Functional_Standing_Desires(ArrayList<TFunctional_Standing_Desire> functional_Goals)
	{
		this.Long_Memory.AddAll_Functional_Goals(functional_Goals);
		
		ArrayList<TFunctional_Standing_Desire> Functional_Goals = this.Get_Functional_Standing_Desires();
		
		HashMap<String, ArrayList<TPredicate>> Map_Attentional_Goal_and_Predicates = 
								this.Long_Memory.Get_Map_Attentional_Goals_and_Predicates();
		HashMap<String, ArrayList<TBelief>> Map_Attentional_Goal_and_Beliefs = 
								this.Long_Memory.Get_Map_Attentional_Goals_and_Beliefs();
		
		for(TFunctional_Standing_Desire Functional_Goal: Functional_Goals)
		{
			if(Functional_Goals.size() > 0)
			{
				
				//I create List of Beliefs and Predicates linked directly or indirectly to the Functional Goal
				ArrayList<TBelief> List_Beliefs = new ArrayList<TBelief>();
				ArrayList<TPredicate> List_Predicates = new ArrayList<TPredicate>();
				
				//Insert Final_State, it is a TBelief_Base type
				TBelief Final_State = this.Long_Memory.Get_Map_Beliefs().get(Functional_Goal.Get_Final_State_Name());
				Functional_Goal.set_Final_State(Final_State);
				
				//I insert Final_State, and its Predicate, in the List_Beliefs and List_Predicates
				List_Beliefs.add(Final_State);
				List_Predicates.add(Final_State.Predicate);
							
				//Insert Trigger_Condition, it is a TPredicate type
				TPredicate Trigger_Condition = this.Long_Memory.Get_Map_Predicates().get(Functional_Goal.Get_Trigger_Condition_Name());
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
				if (Functional_Goal.Get_List_Green_Standing_Desire() == null)
				{
					ArrayList<TGreen_Standing_Desire> List_Green_Goal = new ArrayList<TGreen_Standing_Desire>();
					Functional_Goal.Set_List_Green_Standing_Desire(List_Green_Goal);
				}
				if(Functional_Goal.Get_List_Green_Standing_Desire_Name() != null)
				{
					for(String Green_Goal_Name: Functional_Goal.Get_List_Green_Standing_Desire_Name())
					{
						TGreen_Standing_Desire Green_Goal = null;
						Green_Goal = this.Long_Memory.Get_Map_Green_Goals().get(Green_Goal_Name);
		
						Functional_Goal.Get_List_Green_Standing_Desire().add(Green_Goal);
						
						//I insert a Green_Goal.get_Constraint in the List_Predicates				
						if(!List_Predicates.contains(Green_Goal.Get_Constraint()))
						{
							List_Predicates.add(Green_Goal.Get_Constraint());				
						}	
					}
				}
				
				
				if (Functional_Goal.Get_List_Quality_Standing_Desire() == null)
				{
					ArrayList<TQuality_Standing_Desire> List_Quality_Goal = new ArrayList<TQuality_Standing_Desire>();
					Functional_Goal.Set_List_Quality_Standing_Desire(List_Quality_Goal);
				}
				if(Functional_Goal.Get_List_Quality_Standing_Desire_Name() != null)
				{
					for(String Quality_Goal_Name: Functional_Goal.Get_List_Quality_Standing_Desire_Name())
					{
						TQuality_Standing_Desire Quality_Goal = this.Long_Memory.Get_Map_Quality_Goals().get(Quality_Goal_Name);
						Functional_Goal.Get_List_Quality_Standing_Desire().add(Quality_Goal);
						
						//I insert a Quality_Goal.get_Constraint in the List_Predicates	
						if(!List_Predicates.contains(Quality_Goal.Get_Constraint()))
						{
							List_Predicates.add(Quality_Goal.Get_Constraint());				
						}
					}
				}
				
				
				Map_Attentional_Goal_and_Predicates.put(Functional_Goal.Get_Name(), List_Predicates);
				Map_Attentional_Goal_and_Beliefs.put(Functional_Goal.Get_Name(), List_Beliefs);
			}
			this.Agent.Get_GW().Changed_Goals();
		}
	}
	
	//TO DO: I must to check this method	
	public void Set_Epistemic_Goals(ArrayList<TEpistemic_Standing_Desire> epistemic_Goals)
	{
		if(epistemic_Goals.size() > 0)
		{
			this.Long_Memory.AddAll_Epistemic_Goals(epistemic_Goals);
			
			ArrayList<TEpistemic_Standing_Desire> Epistemic_Goals = this.Get_Epistemic_Standing_Desires();
			
			HashMap<String, ArrayList<TPredicate>> Map_Attentional_Goal_and_Predicates = 
									this.Long_Memory.Get_Map_Attentional_Goals_and_Predicates();
			HashMap<String, ArrayList<TBelief>> Map_Attentional_Goal_and_Beliefs = 
									this.Long_Memory.Get_Map_Attentional_Goals_and_Beliefs();
	
			
			for(TEpistemic_Standing_Desire Epistemic_Goal: Epistemic_Goals)
			{
				ArrayList<TBelief> List_Beliefs = new ArrayList<TBelief>();
				ArrayList<TPredicate> List_Predicates = new ArrayList<TPredicate>();
				
				TBelief belief = this.Long_Memory.Get_Map_Beliefs().get(Epistemic_Goal.Get_Belief_Name());
				Epistemic_Goal.Set_Belief(belief);
							Game.Print("belief: "+belief.Get_Name());
				
				List_Beliefs.add(belief);
				List_Predicates.add(belief.Predicate);
				//Now, I normalize green and quality goals name woth correct goals.
				//So, I must to insert green goals and later quality goals
				// List Green and Quality Goals must to created before Attentional_Goal
				
				if (Epistemic_Goal.Get_List_Green_Standing_Desire() == null)
				{
					ArrayList<TGreen_Standing_Desire> List_Green_Goal = new ArrayList<TGreen_Standing_Desire>();
					Epistemic_Goal.Set_List_Green_Standing_Desire(List_Green_Goal);
				}
				
				for(String Green_Goal_Name: Epistemic_Goal.Get_List_Green_Standing_Desire_Name())
				{
					TGreen_Standing_Desire Green_Goal = this.Long_Memory.Get_Map_Green_Goals().get(Green_Goal_Name);
					Epistemic_Goal.Get_List_Green_Standing_Desire().add(Green_Goal);
				}
				
				if (Epistemic_Goal.Get_List_Quality_Standing_Desire() == null)
				{
					ArrayList<TQuality_Standing_Desire> List_Quality_Goal = new ArrayList<TQuality_Standing_Desire>();
					Epistemic_Goal.Set_List_Quality_Standing_Desire(List_Quality_Goal);
				}
				for(String Quality_Goal_Name: Epistemic_Goal.Get_List_Quality_Standing_Desire_Name())
				{
					TQuality_Standing_Desire Quality_Goal = this.Long_Memory.Get_Map_Quality_Goals().get(Quality_Goal_Name);
					Epistemic_Goal.Get_List_Quality_Standing_Desire().add(Quality_Goal);
				}
			}
			
			this.Agent.Get_GW().Changed_Goals();
		}
	}
	
	public ArrayList<TFunctional_Standing_Desire> Get_Functional_Standing_Desires()
	{
		ArrayList<TFunctional_Standing_Desire> Functional_Goals = new ArrayList<TFunctional_Standing_Desire>();
		ArrayList<TAttentional_Standing_Desire> Goals = this.Long_Memory.Get_Goals();
		
		for(TAttentional_Standing_Desire Goal: Goals)
		{
			if(Goal instanceof TFunctional_Standing_Desire)
			{
				TFunctional_Standing_Desire Functional_Goal = (TFunctional_Standing_Desire) Goal;
				Functional_Goals.add(Functional_Goal);
			}
		}
		return Functional_Goals;
	}
	
	public ArrayList<TFunctional_Standing_Desire> Get_Inhibited_Functional_Goals()
	{
		ArrayList<TFunctional_Standing_Desire> Inhibited_Functional_Goals = new ArrayList<TFunctional_Standing_Desire>();
		ArrayList<TAttentional_Standing_Desire> Goals = this.Long_Memory.Get_Inhibited_Goals();
		
		for(TAttentional_Standing_Desire Goal: Goals)
		{
			if(Goal instanceof TFunctional_Standing_Desire)
			{
				TFunctional_Standing_Desire Functional_Goal = (TFunctional_Standing_Desire) Goal;
				Inhibited_Functional_Goals.add(Functional_Goal);
			}
		}
//		Inhibited_Functional_Goals = this.Long_Memory.Get_Inhibited_Goals() 
		return Inhibited_Functional_Goals;
	}
	
	public ArrayList<String> Get_Functional_Goals_Names()
	{
		ArrayList<String> Functional_Goals = new ArrayList<String>();
		ArrayList<TAttentional_Standing_Desire> Goals = this.Long_Memory.Get_Goals();
		
		for(TAttentional_Standing_Desire Goal: Goals)
		{
			if(Goal instanceof TFunctional_Standing_Desire)
			{
				TFunctional_Standing_Desire Functional_Goal = (TFunctional_Standing_Desire) Goal;
				Functional_Goals.add(Functional_Goal.Get_Name());
			}
		}
		return Functional_Goals;
	}
	
	public ArrayList<TEpistemic_Standing_Desire> Get_Epistemic_Standing_Desires()
	{
		ArrayList<TEpistemic_Standing_Desire> Epistemic_Goals = new ArrayList<TEpistemic_Standing_Desire>();
		ArrayList<TAttentional_Standing_Desire> Goals = this.Long_Memory.Get_Goals();
		
		for(TAttentional_Standing_Desire Goal: Goals)
		{
			if(Goal instanceof TEpistemic_Standing_Desire)
			{
				TEpistemic_Standing_Desire Epistemic_Goal = (TEpistemic_Standing_Desire) Goal;
				Epistemic_Goals.add(Epistemic_Goal);
			}
		}
		return Epistemic_Goals;
	}
	
	public ArrayList<String> Get_Epistemic_Goals_Names()
	{
		ArrayList<String> Epistemic_Goals = new ArrayList<String>();
		ArrayList<TAttentional_Standing_Desire> Goals = this.Long_Memory.Get_Goals();
		
		for(TAttentional_Standing_Desire Goal: Goals)
		{
			if(Goal instanceof TEpistemic_Standing_Desire)
			{
				TEpistemic_Standing_Desire Epistemic_Goal = (TEpistemic_Standing_Desire) Goal;
				Epistemic_Goals.add(Epistemic_Goal.Get_Name());
			}
		}
		return Epistemic_Goals;
	}
	
	public ArrayList<TBelief> Get_Beliefs()
	{
		return this.Long_Memory.Get_Beliefs();
	}
	
	public ArrayList<TPredicate> Get_Predicates()
	{
		return this.Long_Memory.Get_Predicates();
	}
	
	public ArrayList<TGreen_Standing_Desire> Get_Green_Standing_Desires()
	{
		return this.Long_Memory.Get_Greens_Goals();
	}
	
	public ArrayList<TQuality_Standing_Desire> Get_Quality_Goals()
	{
		return this.Long_Memory.Get_Quality_Goals();
	}
	
	public ArrayList<TAttentional_Standing_Desire> Get_Attentional_Goals()
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
	
	public HashMap<String, ArrayList<TPredicate>> Get_Map_Attentional_Standing_Desires_and_Predicates()
	{
		return this.Long_Memory.Get_Map_Attentional_Goals_and_Predicates();
	}
	
	public HashMap<String, ArrayList<TBelief>> Get_Map_Attentional_Standing_Desires_and_Beliefs()
	{
		return this.Long_Memory.Get_Map_Attentional_Goals_and_Beliefs();
	}
	
	public ArrayList<String> Get_Inhibited_Goals()
	{
		ArrayList<String> Inhibited_Goals = new ArrayList<String>();
		ArrayList<TAttentional_Standing_Desire> Goals = this.Long_Memory.Get_Inhibited_Goals();
		
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
	

	public void Add_Desire(TActive_Desire desire)
	{
		this.Long_Memory.Add_Desire(desire);	
	}
	
	public void Add_Desire(ArrayList<TActive_Desire> desires)
	{
		this.Long_Memory.Add_Desire(desires);
		
	}
	
	public TBelief Create_Belief(TPredicate Predicate, Boolean truth, Object information_Source,
			LocalDateTime time_stamp, TType_Beliefs type_Belief)
	{
		return this.Long_Memory.Create_Belief(Predicate, truth, information_Source, time_stamp, type_Belief);
	}
	
	public void Set_Beliefs_Number(int value)
	{
		this.Long_Memory.Set_Beliefs_Number(value);
	}
	
	public ArrayList<TBelief> Get_Inhibited_Beliefs_From_Type_Belief(TType_Beliefs Type_Beliefs)
	{
		ArrayList<TBelief> Beliefs = new ArrayList<TBelief>();
		for(TBelief Belief: this.Get_Beliefs())
		{
//			Game.Print(Belief.get_Name()+" - "+Belief.get_Type_Belief());
			if(Belief.Get_Type_Belief() == Type_Beliefs)
			{
				Beliefs.add(Belief);
			}
		}
	
		return Beliefs;
	}
	
	public ArrayList<TBelief> Get_Inhibited_Beliefs()
	{
		return this.Long_Memory.Get_Inhibited_Beliefs();
	}
	
	public void Set_Inhibited_Beliefs(ArrayList<TBelief> inhibited_Beliefs)
	{
		this.Long_Memory.Set_Inhibited_Beliefs(inhibited_Beliefs);
	}
	
	public void Set_Inhibited_Goals(ArrayList<TAttentional_Standing_Desire> Inhibited_Goals)
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

	public TEpistemic_Standing_Desire Stimulus_to_Goal(TStimulus Salient_Belief)
	{
		
		
		int Inc_Epistemic_Goals_Number = this.Long_Memory.Get_Inc_Epistemic_Goals_Number() + 1;
		String Epistemic_Goal_Name = "Eg" + Inc_Epistemic_Goals_Number;
		double reward = 0.0;			//da dove lo prendo?
		double relax_preference = 0.0;
		
		ArrayList<TGreen_Standing_Desire> Green_Goal_list = new ArrayList<TGreen_Standing_Desire>();
		ArrayList<TQuality_Standing_Desire> Quality_Goal_list = new ArrayList<TQuality_Standing_Desire>();
		
		TEpistemic_Standing_Desire Epistemic_Goal = new TEpistemic_Standing_Desire(Epistemic_Goal_Name, Salient_Belief, 
				Salient_Belief.Get_Saliency(), reward, relax_preference, Green_Goal_list, Quality_Goal_list);
		
		this.Long_Memory.Add_Epistemic_Goal(Epistemic_Goal);

		HashMap<String, ArrayList<TPredicate>> Map_Attentional_Goal_and_Predicates = 
						this.Long_Memory.Get_Map_Attentional_Goals_and_Predicates();
		HashMap<String, ArrayList<TBelief>> Map_Attentional_Goal_and_Beliefs = 
						this.Long_Memory.Get_Map_Attentional_Goals_and_Beliefs();

		//I create List of Beliefs and Predicates linked directly or indirectly to the Functional Goal
		ArrayList<TBelief> List_Beliefs = new ArrayList<TBelief>();
		ArrayList<TPredicate> List_Predicates = new ArrayList<TPredicate>();
			
		//I insert Salient_Belief in the List_Beliefs 
		List_Beliefs.add(Salient_Belief);

		//Now, I normalize green and quality goals name woth correct goals.
		//So, I must to insert green goals and later quality goals
		// List Green and Quality Goals must to created before Attentional_Goal

		for(TGreen_Standing_Desire Green_Goal: Green_Goal_list)
		{
			//I insert a Green_Goal.get_Constraint in the List_Predicates				
			if(!List_Predicates.contains(Green_Goal.Get_Constraint()))
			{
				List_Predicates.add(Green_Goal.Get_Constraint());				
			}	
		}
		
		for(TQuality_Standing_Desire Quality_Goal: Quality_Goal_list)
		{
			//I insert a Green_Goal.get_Constraint in the List_Predicates				
			if(!List_Predicates.contains(Quality_Goal.Get_Constraint()))
			{
				List_Predicates.add(Quality_Goal.Get_Constraint());				
			}	
		}

		Map_Attentional_Goal_and_Predicates.put(Epistemic_Goal.Get_Name(), List_Predicates);
		Map_Attentional_Goal_and_Beliefs.put(Epistemic_Goal.Get_Name(), List_Beliefs);
		
		return Epistemic_Goal;
	}
	
	public boolean Perception_Processing(int i)
	{
		this.Agent.Get_GW().Print_Data(2, 0);
		Game.Print("Perception Processing number: "+i);
		
		boolean result = true;
		TPerception Perception = this.Get_Perception();
		Information_Selection(Perception);
		Stimulus_Inhibition();
		
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
				case "Danger on the route!":
					this.Manage_Danger_on_the_Route(Perception);
					this.Agent.Get_GW().Updated_Salient_Beliefs_for_print = false;
					break;
				case "Acquired Danger Type on the road":
					this.Manage_Acquired_Danger_Type_on_the_Route(Perception);
					this.Agent.Get_GW().Updated_Salient_Beliefs_for_print = false;
					break;
			}
		}
		return result;
	}
	

	
	public void Manage_Ask_Closed_Route_Duration(TPerception Perception)
	{
		TRegion Inhibition_Regions = this.Agent.Get_GW().Get_Inhibition_Regions();
		ArrayList<TBelief> Unhinibited_Beliefs = this.Agent.Get_GW().Get_UnInhibited_Beliefs();
		TStimulus Salient_Belief;// = (TSalient_Belief) this.Get_Inhibited_Beliefs_From_Type_Belief(TType_Beliefs.Stimulus_Temporary_Closed_Route).getFirst();
		
		TTriple_Object Preceived_Data = Perception.get_Perceived_Data();
		
		int Integer_Route = (int) Preceived_Data.Get_Object_Second();
		
		ArrayList<TBelief> Beliefs2 = this.Agent.Get_GW().Get_Beliefs_From_Type_Belief(TType_Beliefs.Stimulus_Temporary_Closed_Route);
		Salient_Belief = (TStimulus) Beliefs2.getFirst();
		TPredicate Predicate = Salient_Belief.Get_Predicate();
		
		Predicate.Set_Subject(Integer_Route);
		Salient_Belief.Update_Saliency(0.9);
		
		Game.Print_Colored_Text("I want to ask to TCS: How long will the route be closed? "+Integer_Route, 5);
		
		//22/05/2025
//		Game.Get_Input("Manage_Ask_Closed_Route_Duration - TStimulus: "+Salient_Belief.get_Type_Belief()+
//				" - Saliency: "+Salient_Belief.Get_Saliency());>
		//this.Agent.Get_GW().Update_Belief_by_Stimulus(Salient_Belief);
		this.Temporary_Stimulus = Salient_Belief;
	}
	
	public void Manage_See_Semaphore_for_Route_Status(TPerception Perception)
	{
		TRegion Inhibition_Regions = this.Agent.Get_GW().Get_Inhibition_Regions();
		ArrayList<TBelief> Unhinibited_Beliefs = this.Agent.Get_GW().Get_UnInhibited_Beliefs();
		TStimulus Salient_Belief;// = (TSalient_Belief) this.Get_Inhibited_Beliefs_From_Type_Belief(TType_Beliefs.Stimulus_Temporary_Closed_Route).getFirst();
		
		TTriple_Object Preceived_Data = Perception.get_Perceived_Data();
		
		int Integer_Route = (int) Preceived_Data.Get_Object_Second();
		
		ArrayList<TBelief> Beliefs2 = this.Agent.Get_GW().Get_Beliefs_From_Type_Belief(TType_Beliefs.Stimulus_Route_Status);
		Salient_Belief = (TStimulus) Beliefs2.getFirst();
		TPredicate Predicate = Salient_Belief.Get_Predicate();
		
		Predicate.Set_Subject(Integer_Route);
		Salient_Belief.Update_Saliency(0.9);
		
		Game.Print_Colored_Text("I want to see the semaphore for get the route status for route: "+Integer_Route, 5);
		//22/05/2025
//		Game.Get_Input("Manage_See_Semaphore_for_Route_Status - TStimulus: "+Salient_Belief.get_Type_Belief()+
//				" - Saliency: "+Salient_Belief.Get_Saliency());
		//this.Agent.Get_GW().Update_Belief_by_Stimulus(Salient_Belief);
		this.Temporary_Stimulus = Salient_Belief;
	}
	
	public void Manage_Correct_Movement(TPerception Perception)
	{
		TRegion Inhibition_Regions = this.Agent.Get_GW().Get_Inhibition_Regions();
		ArrayList<TBelief> Unhinibited_Beliefs = this.Agent.Get_GW().Get_UnInhibited_Beliefs();
		TStimulus Salient_Belief;// = (TSalient_Belief) this.Get_Inhibited_Beliefs_From_Type_Belief(TType_Beliefs.Stimulus_Temporary_Closed_Route).getFirst();
		
		TTriple_Object Preceived_Data = Perception.get_Perceived_Data();
		Game.Print("I moved correctly.");
		Game.Print("Now, I update several information in my Beliefs and in plan actions.");
		ArrayList<TBelief> Beliefs2 = this.Agent.Get_GW().Get_Beliefs_From_Type_Belief(TType_Beliefs.Stimulus_Ok_Correct_Movement);
		Salient_Belief = (TStimulus) Beliefs2.getFirst();
		
		TPosition_Train_Coords Train_Coords = (TPosition_Train_Coords) Preceived_Data.Get_Object_Second(); 
		
		TBelief Belief_Current_Route;
		TBelief Belief_Current_Step;
		TBelief Current_Belief_Station;
		
		ArrayList<TBelief> Beliefs_Route = this.Agent.Get_GW().Get_Beliefs_From_Type_Belief(TType_Beliefs.Belief_Current_Route);
		ArrayList<TBelief> Beliefs_Step = this.Agent.Get_GW().Get_Beliefs_From_Type_Belief(TType_Beliefs.Belief_Current_Step);
		ArrayList<TBelief> Beliefs_Station = this.Agent.Get_GW().Get_Beliefs_From_Type_Belief(TType_Beliefs.Belief_Current_City);
		Belief_Current_Route = Beliefs_Route.getFirst();
		Belief_Current_Step = Beliefs_Step.getFirst();
		Current_Belief_Station = Beliefs_Station.getFirst();
		Game.Print("I Update Position Beliefs:");
		Boolean Neet_to_Change_Visited_Station_Belief = false;
		Game.Print("Before, the Previous Current Position Town was: "+Current_Belief_Station.Predicate.Get_Object_Complement());
		Game.Print("Before, the Previous Current Position Route was: "+Belief_Current_Route.Predicate.Get_Object_Complement());
		Game.Print("Before, the Previous Current Position Step was: "+Belief_Current_Step.Predicate.Get_Object_Complement());
		
		//I update Current_Route Beliefs
		Belief_Current_Route.Predicate.set_Object_Complement(Train_Coords.Get_Route());
		Integer Current_Route = Train_Coords.Get_Route();
		
		//I update Current_Step Beliefs
		Belief_Current_Step.Predicate.set_Object_Complement(Train_Coords.Get_Step());
		Integer Current_Step = Train_Coords.Get_Step();
		
		//I update the time for the Agent
		ArrayList<TBelief> Temp_Beliefs = this.Agent.Get_GW().Get_Beliefs_From_Type_Belief(TType_Beliefs.Belief_Current_Time);
		TBelief Temp_Belief = Temp_Beliefs.getFirst();
		
		LocalDateTime Current_Time = (LocalDateTime) Temp_Belief.Get_Predicate().Get_Object_Complement();
		int One_Hour = 60;
		LocalDateTime Temp_Time = Current_Time.plusMinutes(One_Hour);
		Temp_Belief.Get_Predicate().set_Object_Complement(Temp_Time);
				
		
		// I update Current_Stationv Beliefs and, if it is necessary, also the Belief_Visited_Station
		// in case it visited another Station
		//If start Station is different to arrive Station (it means I changed station)
		if(Current_Belief_Station.Predicate.Get_Object_Complement() != Train_Coords.Get_City())
		{
			
			Neet_to_Change_Visited_Station_Belief = true;
			
			ArrayList<TBelief> Belief_Visited_Stations = this.Agent.Get_GW().Get_Beliefs_From_Type_Belief(TType_Beliefs.Belief_Visited_City);
			for(TBelief Belief: Belief_Visited_Stations)
			{
				if((City) Belief.Get_Predicate().Get_Subject() == Train_Coords.Get_City() &&
				   (TType_Object_Complement) Belief.Get_Predicate().Get_Object_Complement() == 	TType_Object_Complement.Me)
				{
					Belief.Set_Truth(true);
					Belief.Set_Time_Stamp(Temp_Time);
					Belief.Set_Information_Source(TType_Object_Complement.TCS);
					this.Agent.Get_GW().Print_Belief(Belief);
					this.Agent.Get_GW().Update_Beliefs_for_Broadcast();
				}
			}
//			this.Agent.Get_GW().Update_Goals_for_Broadcast();
		}

		Current_Belief_Station.Predicate.set_Object_Complement(Train_Coords.Get_City());
		City Current_Station = Train_Coords.Get_City();
		
		Game.PrintLn();
		Game.Print("Now, the Updated Current Position Town is: "+Current_Belief_Station.Predicate.Get_Object_Complement());
		Game.Print("Now, the Updated Current Position Route is: "+Belief_Current_Route.Predicate.Get_Object_Complement());
		Game.Print("Now, the Updated Current Position Step is: "+Belief_Current_Step.Predicate.Get_Object_Complement());
		
		//Now I update the next action the agent should perform
		ArrayList<TIntention> Intentions = this.Agent.Get_GW().Get_Intentions();
		TIntention Intention = Intentions.getFirst();
		TActive_Desire Desire = Intention.Get_Active_Desire();

		TOption Selected_Option = Desire.Get_Option_List().get(Intention.Get_Seleted_Option_Id());
		Selected_Option.Inc_Action_To_Do_ID();
		TPredicate Predicate = Salient_Belief.Get_Predicate();
		
	
		
		//This is the Saslient_Belief for Epistemic_Goal
		Salient_Belief.Update_Saliency(0.1);
		
		
		TFunctional_Standing_Desire Functional_Goal =  (TFunctional_Standing_Desire)Intention.Get_Active_Desire().Get_Attentional_Standing_Desire();
		if(Functional_Goal.Get_Final_State().Get_Type_Belief() != TType_Beliefs.Belief_Come_Back_to_City)
		{
			Game.Print("Functional_Goal.get_Final_State().get_Type_Belief(): "+Functional_Goal.Get_Final_State().Get_Type_Belief());
			//Temp_Beliefs = this.Agent.Get_GW().Get_Beliefs_From_Type_Belief(TType_Beliefs.Belief_Path_Taken_For_Belief);
			Temp_Belief = this.Agent.Get_GW().Get_Belief_From_Type_Belief_and_Subject(TType_Beliefs.Belief_Path_Taken_For_Belief,
					Desire.Get_Attentional_Standing_Desire().Get_Name());
			
			
			
	//		this.Agent.Get_GW().Print_Belief(Temp_Belief);
			TPredicate Predicate_Temp_Belief = Temp_Belief.Get_Predicate();
			ArrayList<Integer> Route_List = (ArrayList<Integer>) Predicate_Temp_Belief.Get_Object_Complement();
			
			if((!Route_List.contains(Route_List)) && (Train_Coords.Get_Route()!=-1))
			{
				Route_List.add(Train_Coords.Get_Route());
				Predicate_Temp_Belief.set_Object_Complement(Route_List);
				Temp_Belief.Set_Predicate(Predicate_Temp_Belief);
				Temp_Belief.Set_Time_Stamp(Temp_Time);
				Temp_Belief.Set_Truth(true);
			}
		}
			
		
		
		//Now I have to update Other information
		//I update the temporrary closed ruotes (I decrease their time)
		this.Update_Temporary_Closed_Routes();
		
		
		
		//22/05/2025
//		Game.Get_Input("Manage_Correct_Movement - TStimulus: "+Salient_Belief.get_Type_Belief()+
//				" - Saliency: "+Salient_Belief.Get_Saliency());
		//this.Agent.Get_GW().Update_Belief_by_Stimulus(Salient_Belief);
		this.Temporary_Stimulus = Salient_Belief;
		
		
		
		
		//////////////////////////
////		Intention = Intentions.getFirst();
////		TDesire Desire = Intention.get_Desire();
//
//		TAttentional_Goal Attentional_Goal = Desire.get_Attentional_Goal();
//		
////		TOption Selected_Option = Desire.get_Option_List().get(Intention.get_Seleted_Option_Id());
////		
////		Selected_Option.Inc_Action_To_Do_ID();
//
//		if(Selected_Option.Get_Action_To_Do_ID() >= Selected_Option.get_Plan_Actions().size())
//		{
//			TDesire Desire_to_Delete = Intention.get_Desire();
//			Game.Print_Colored_Text("My intention to acquire the route status color has been fulfilled", 5);
//			Game.Print("Now I delete the Selected Intention, with its desire and I move the Epistemic Goal in my goal satisfied List");
//			Game.Print(Desire_to_Delete.Get_Name()+": "+Desire_to_Delete.get_Attentional_Goal().get_Saliency()+
//					" - Epistemic Goal: "+Desire_to_Delete.get_Attentional_Goal().get_Name());
//			this.Agent.Get_GW().Delete_Intention(Intention);
//			
//			this.Agent.Get_GW().Get_Goals().remove(Attentional_Goal);
//			this.Long_Memory.Delete_Goal(Attentional_Goal);
//			this.Long_Memory.Insert_Attentional_Goals_Satisfied(Attentional_Goal);
//			Game.Gui_Map.Show_Message("Information...", "Elimino il goal!",
//					JOptionPane.INFORMATION_MESSAGE);
//			
//		}
		
//		Functional_Goal = (TFunctional_Goal) Desire.get_Attentional_Goal();
		TBelief Final_State = Functional_Goal.Get_Final_State();
		Game.Print("Final_State.get_Predicate().get_Subject().getClass(): "+Final_State.Get_Predicate().Get_Subject().getClass());
		Game.Print("Final_State.get_Predicate().get_Subject(): "+Final_State.Get_Predicate().Get_Subject());
		Game.Print("Final_State.get_Predicate().Object_Complement(): "+Final_State.Get_Predicate().Get_Object_Complement());
		City Final_Station = (City) Final_State.Get_Predicate().Get_Subject();
		
		if(Current_Station == Final_Station)
			{
			TActive_Desire Desire_to_Delete = Intention.Get_Active_Desire();
			Game.Print_Colored_Text("My intention to arrive to "+Final_Station+ " has been realized!\n"
					+"I satisfied the Desire: "+Functional_Goal.Get_Name(), 5);
			
			
			//22/05/2025
//			Game.Get_Input("Manage_Correct_Movement - TStimulus: "+Salient_Belief.get_Type_Belief()+
//					" - Saliency: "+Salient_Belief.Get_Saliency());
			//this.Agent.Get_GW().Update_Belief_by_Stimulus(Salient_Belief);
			this.Temporary_Stimulus = null;
			
//			if(!Game.Disable_Show_Message)
			if(Functional_Goal.Get_Final_State().Get_Type_Belief() != TType_Beliefs.Belief_Come_Back_to_City)
//			{
			
				Game.Gui_Map.Show_Message("Success!", "My intention to arrive to "+Final_Station+ " has been realized!\n"
						+"I satisfied the Desire: "+Functional_Goal.Get_Name(),
						JOptionPane.INFORMATION_MESSAGE);
//			}
			Game.Print("Now I delete the Selected Intention, with its desire and I move the Functional Desire in my goal satisfied List");
			Game.Print(Desire_to_Delete.Get_Name()+": "+Desire_to_Delete.Get_Attentional_Standing_Desire().Get_Saliency()+
					" - Functional Goal: "+Desire_to_Delete.Get_Attentional_Standing_Desire().Get_Name());
			
			//I delete Intention and Desire
			if(Functional_Goal.Get_Final_State().Get_Type_Belief() 
					== TType_Beliefs.Belief_Come_Back_to_City)
			{
				Game.Print("Current_Station: "+Current_Station);
				Game.Print("Final_Station: "+Final_Station);
				
				Game.Print("Intention: "+Intention.Get_Name());
				Game.Print("Intention.get_Desire(): "+Intention.Get_Active_Desire().Get_Name());
				Game.Print("TFunctional_Desire Intention.get_Desire(): "+((TFunctional_Standing_Desire)Intention.Get_Active_Desire().Get_Attentional_Standing_Desire()).Get_Name());
				Game.Gui_Map.Show_Message("Warning!", "I returned to the previous station: "+Current_Station,
						JOptionPane.WARNING_MESSAGE);
			}
			this.Agent.Get_GW().Delete_Intention(Intention);
			this.Agent.Get_GW().Get_Goals().remove((TAttentional_Standing_Desire)Functional_Goal);
			this.Agent.Get_GW().Get_Functional_Standing_Desires().remove(Functional_Goal);
//			this.Long_Memory.Get_Goals().re
			this.Long_Memory.Delete_Goal((TAttentional_Standing_Desire)Functional_Goal);
			if(this.Long_Memory.Get_Goals().contains(Functional_Goal))
			{
//				Game.Gui_Map.Show_Message("errore", "e' presente", JOptionPane.ERROR_MESSAGE);
			}
			this.Long_Memory.Insert_Attentional_Goals_Satisfied((TAttentional_Standing_Desire)Functional_Goal);
			
			this.Agent.Get_GW().Update_Goals_for_Broadcast();
			this.Agent.Get_GW().Update_Desires_for_Broadcast();
			this.Agent.Get_GW().Update_Intention_for_Broadcast();
			if(!Game.Disable_Show_Message)
			{
				Game.Gui_Map.Show_Message("Information...", "I delete the Functional Desire!",
						JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}
	
	public TType_Route_Status Manage_Acquired_Route_Status(TPerception Perception)
	{
		TType_Route_Status Route_Status = null; 
		TRegion Inhibition_Regions = this.Agent.Get_GW().Get_Inhibition_Regions();
		ArrayList<TBelief> Unhinibited_Beliefs = this.Agent.Get_GW().Get_UnInhibited_Beliefs();
		TStimulus Salient_Belief;// = (TSalient_Belief) this.Get_Inhibited_Beliefs_From_Type_Belief(TType_Beliefs.Stimulus_Temporary_Closed_Route).getFirst();
		
		TTriple_Object Preceived_Data = Perception.get_Perceived_Data();
		
		int Integer_Route = (int) Preceived_Data.Get_Object_Second();
		Route_Status = (TType_Route_Status) Preceived_Data.Get_Object_Third();
		Game.Print("The aquired Route_Status (by my virtual camera) is: "+Route_Status);
		
		ArrayList<TBelief> Visited_Station = this.Agent.Get_GW().Get_Beliefs_From_Type_Belief(TType_Beliefs.Belief_Visited_City);

		//I update the belief for route status of route Integer_Route
		TBelief Belief = null;// = this.Agent.Get_GW().Get_UnInhibited_Beliefs_From_Type_Belief_and_Subject(TType_Beliefs.Belief_Route_Status, Integer_Route);
		ArrayList<TBelief> Belifs = this.Agent.Get_GW().Get_UnInhibited_Beliefs();
		LocalDateTime A_Time = this.Agent.Get_GW().Get_Current_Time();
		for(TBelief Bel: Belifs)
		{
			if(Bel.Get_Type_Belief() == TType_Beliefs.Belief_Route_Status)
			{
				if( (int) Bel.Predicate.Get_Subject() == Integer_Route)
				{
					Belief = Bel;
					Belief.Set_Truth(true);
					Belief.Set_Time_Stamp(A_Time);
				}
			}
		}
		
		Belief.Predicate.set_Object_Complement(Route_Status);
		
		//Now I Update next action 
		ArrayList<TIntention> Intentions = this.Agent.Get_GW().Get_Intentions();

		TIntention Intention = Intentions.getFirst();
		TActive_Desire Desire = Intention.Get_Active_Desire();

		TAttentional_Standing_Desire Attentional_Goal = Desire.Get_Attentional_Standing_Desire();
		
		TOption Selected_Option = Desire.Get_Option_List().get(Intention.Get_Seleted_Option_Id());
		
		Selected_Option.Inc_Action_To_Do_ID();

		if(Selected_Option.Get_Action_To_Do_ID() >= Selected_Option.Get_Plan_Actions().size())
		{
			TActive_Desire Desire_to_Delete = Intention.Get_Active_Desire();
			Game.Print_Colored_Text("My intention to acquire the route status color has been fulfilled", 5);
			Game.Print("Now I delete the Selected Intention, with its desire and I move the Epistemic Goal in my Desire satisfied List");
			Game.Print(Desire_to_Delete.Get_Name()+": "+Desire_to_Delete.Get_Attentional_Standing_Desire().Get_Saliency()+
					" - Epistemic Goal: "+Desire_to_Delete.Get_Attentional_Standing_Desire().Get_Name());
			this.Agent.Get_GW().Delete_Intention(Intention);
			
			this.Agent.Get_GW().Get_Goals().remove(Attentional_Goal);
			this.Long_Memory.Delete_Goal(Attentional_Goal);
			this.Long_Memory.Insert_Attentional_Goals_Satisfied(Attentional_Goal);
			
		}
		
		ArrayList<TBelief> Beliefs2 = this.Agent.Get_GW().Get_Beliefs_From_Type_Belief(TType_Beliefs.Stimulus_Route_Status);
		Salient_Belief = (TStimulus) Beliefs2.getFirst();
		Salient_Belief.Update_Saliency(0.1);
		
		
		//22/05/2025
//		Game.Get_Input("Manage_Acquired_Route_Status - TStimulus: "+Salient_Belief.get_Type_Belief()+
//				" - Saliency: "+Salient_Belief.Get_Saliency());
		//this.Agent.Get_GW().Update_Belief_by_Stimulus(Salient_Belief);
		this.Temporary_Stimulus = Salient_Belief;	
		
//		Game.End_Game();
		
		return Route_Status;

		//BISOGNA CANCELLARE IL DESIRE E IL SALIENT_BELIEF
		//INOLTRE BISOGNA CREARE UNO STIMOLO CON SALIENCY BASSISSIMA DI TIPO ROTTA ACQUISITA
		
	}
	
	public void Manage_Acquired_Danger_Type_on_the_Route(TPerception Perception)
	{
		/*Preceived_Data from Perception:
		 * First:	String "Acquired Danger Type on the road"
		 * Second:	TTriple_Object: Danger_Data composed by:
		 * 						First:	TPosition_Train_Coords Pre_condition_Position
		 * 						Second:	TPosition_Train_Coords Post_condition_Position
		 *						Third:	Integer Duration
		 * Third:	TType_Danger Type_Danger
		 * 
		 * Previous Intention is the intention with 
		 */
		TRegion Inhibition_Regions = this.Agent.Get_GW().Get_Inhibition_Regions();
		ArrayList<TBelief> Unhinibited_Beliefs = this.Agent.Get_GW().Get_UnInhibited_Beliefs();
		TStimulus Salient_Belief;// = (TSalient_Belief) this.Get_Inhibited_Beliefs_From_Type_Belief(TType_Beliefs.Stimulus_Temporary_Closed_Route).getFirst();
		
		TTriple_Object Preceived_Data = Perception.get_Perceived_Data();
		TTriple_Object Danger_Data = (TTriple_Object) Preceived_Data.Get_Object_Second();
		TType_Danger Type_Danger = (TType_Danger) Preceived_Data.Get_Object_Third();
		
		TPosition_Train_Coords Precondition_Position = (TPosition_Train_Coords) Danger_Data.Get_Object_First();
		TPosition_Train_Coords Postcondition_Position = (TPosition_Train_Coords) Danger_Data.Get_Object_Second();
		
		//Agent have to update its Belief_Temporary_Closed_Route
		int Duration = (int) Danger_Data.Get_Object_Third();
		/** 
		 * I use Max function because I want to come back to the departure City, so the route of precondition
		 * position it wil be always>=0, while the route of postcondition position can be equal to route
		 * of precondition position if agent is in same route, or -1 if the station of postcondition position
		 * is different from the station of precontidion position
		 */
		
		int Integer_Route = Math.max(Precondition_Position.Get_Route(), Postcondition_Position.Get_Route());
		
		int Specular_Integer_Route = this.Get_Map().Get_Specular_Route(Integer_Route);
		
		for(TBelief Bel: Unhinibited_Beliefs)
		{
//			Game.Print(Bel.get_Name()+": "+Bel.get_Type_Belief()+" - "+Bel.get_Predicate().get_Subject());
			if(Bel.Get_Type_Belief() == TType_Beliefs.Belief_Temporary_Closed_Route)
			{
				HashMap<Integer, Integer> Routes = (HashMap<Integer, Integer>) Bel.Get_Predicate().Get_Subject();
				
				if( !Routes.containsKey(Integer_Route) )
				{
					Routes.put(Integer_Route, Duration);
					Routes.put(Specular_Integer_Route, Duration);
					Route route = this.Get_Map().Get_Route(Integer_Route);
					route.Set_Duration_Closed(Duration);
					
					route = this.Get_Map().Get_Route(Specular_Integer_Route);
					route.Set_Duration_Closed(Duration);
				}
				Bel.Get_Predicate().Set_Subject(Routes);
			}
			
			
		}
		//I need to change the direction and step of the path the Agent is on, because now the Agent goes back
		//and the direction must be changed and Belief_Current_Step must be inverted for consistency
		TBelief Belief_Current_Route= this.Agent.Get_GW().Get_Beliefs_From_Type_Belief(TType_Beliefs.Belief_Current_Route).getFirst();
		int Current_Route = (int) Belief_Current_Route.Predicate.Get_Object_Complement();
		
		
		
		
		
		//if Agent is inside a route so Current_Route>-1, else Agent is in a city, so Current_Route = -1
		//Only if Current_Route>-1 the i must to change Belief_Current_Route and Belief_Current_Step
		if(Current_Route > -1)
		{
			TBelief Belief_Current_Step= this.Agent.Get_GW().Get_Beliefs_From_Type_Belief(TType_Beliefs.Belief_Current_Step).getFirst();
			int Current_Step = (int) Belief_Current_Step.Predicate.Get_Object_Complement();
			int Specular_Step_in_Route = this.Agent.Get_GW().Get_Map_Known().Get_Specular_Step_in_Route(Current_Route, Current_Step);
			int Specular_Current_Route =  this.Get_Map().Get_Specular_Route(Current_Route);
			
			Belief_Current_Route.Predicate.set_Object_Complement(Specular_Current_Route);
			Belief_Current_Step.Predicate.set_Object_Complement(Specular_Step_in_Route);
			
			TBelief Belief_Current_City= this.Agent.Get_GW().Get_Beliefs_From_Type_Belief(TType_Beliefs.Belief_Current_City).getFirst();
			City  Specular_Current_City= this.Agent.Get_GW().Get_Map_Known().All_Routes.get(Specular_Current_Route).Get_Departure();
			Belief_Current_City.Predicate.set_Object_Complement(Specular_Current_City);
			
		}
		
		
		
		//Now Agent has to delete previous intention to know the type of danger
		// This Intention has the Belief of its Epistemic Goal equal to "Stimulus_Danger_on_the_Route"
		TIntention Intention = null;
		TEpistemic_Standing_Desire Epistemic_Goal = null;
		for(TIntention Intent: this.Agent.Get_GW().Get_Intentions())
		{
			if(Intent.Get_Active_Desire().Get_Attentional_Standing_Desire() instanceof TEpistemic_Standing_Desire)
			{
				Epistemic_Goal = (TEpistemic_Standing_Desire) Intent.Get_Active_Desire().Get_Attentional_Standing_Desire();
				if(Epistemic_Goal.Get_Belief().Get_Type_Belief() == TType_Beliefs.Stimulus_Danger_on_the_Route)
				{
					Intention = Intent;
				}				
			}
			
		}
		if(Intention != null)
		{
			this.Remove_Intention(Intention);
		}
		
		ArrayList<TBelief> Beliefs_Irrelevant = this.Agent.Get_GW().Get_Beliefs_From_Type_Belief(TType_Beliefs.Stimulus_Irrelevant);
		Salient_Belief = (TStimulus) Beliefs_Irrelevant.getFirst();
		TPredicate Predicate = Salient_Belief.Get_Predicate();
		
		Predicate.Set_Subject(Danger_Data);
		Predicate.set_Object_Complement(Type_Danger);
//		Game.Print("Salient_Belief.Get_Saliency(): "+Salient_Belief.Get_Saliency());
		Salient_Belief.Update_Saliency(0.1);
//		Game.Print("Salient_Belief.Get_Saliency(): "+Salient_Belief.Get_Saliency());
		
		
		Game.Print("Now, I Know the Danger type on the Route: "+Type_Danger);
		Game.Print("The route will be closed for time: "+Duration);
		
		City city = Precondition_Position.Get_City();

		Game.Print("I to come back to previous city: "+city);
		
		
		//22/05/2025
//		Game.Get_Input("Manage_Acquired_Danger_Type_on_the_Route - TStimulus: "+Salient_Belief.get_Type_Belief()+
//				" - Saliency: "+Salient_Belief.Get_Saliency());
		//this.Agent.Get_GW().Update_Belief_by_Stimulus(Salient_Belief);
		this.Temporary_Stimulus = Salient_Belief;
		
		Game.Print("Working Cycle number: "+ this.Agent.Working_Cycle);
		Game.Print("Salient_Belief.Get_Saliency(): "+Salient_Belief.Get_Saliency());
//		Game.End_Game();
		
	}
	
	public void Remove_Intention(TIntention Intention)
	{
//		TIntention Intention = Intentions.getFirst();
		TActive_Desire Desire = Intention.Get_Active_Desire();

		TAttentional_Standing_Desire Attentional_Goal = Desire.Get_Attentional_Standing_Desire();
		
		TOption Selected_Option = Desire.Get_Option_List().get(Intention.Get_Seleted_Option_Id());
		
		Selected_Option.Inc_Action_To_Do_ID();

		if(Selected_Option.Get_Action_To_Do_ID() >= Selected_Option.Get_Plan_Actions().size())
		{
			TActive_Desire Desire_to_Delete = Intention.Get_Active_Desire();
			this.Agent.Get_GW().Delete_Intention(Intention);
			
			this.Agent.Get_GW().Get_Goals().remove(Attentional_Goal);
			this.Long_Memory.Delete_Goal(Attentional_Goal);
			this.Long_Memory.Insert_Attentional_Goals_Satisfied(Attentional_Goal);
		}
		this.Agent.Get_GW().Update_Intention_for_Broadcast();
//		this.Agent.Get_E_Inhibition_function().UnFocus();
//		this.Agent.Get_E_Inhibition_function().Focus_Attention();
	}
	
	public void Manage_Danger_on_the_Route(TPerception Perception)
	{
		TRegion Inhibition_Regions = this.Agent.Get_GW().Get_Inhibition_Regions();
		ArrayList<TBelief> Unhinibited_Beliefs = this.Agent.Get_GW().Get_UnInhibited_Beliefs();
		TStimulus Salient_Belief;// = (TSalient_Belief) this.Get_Inhibited_Beliefs_From_Type_Belief(TType_Beliefs.Stimulus_Temporary_Closed_Route).getFirst();
		
		TTriple_Object Preceived_Data = Perception.get_Perceived_Data();
		
//		Game.Print(Preceived_Data.Get_Object_Second());
		//from Perception, the positions are Second and Third!
		TPosition_Train_Coords Precondition_Position = (TPosition_Train_Coords) Preceived_Data.Get_Object_Second();
		TPosition_Train_Coords Postcondition_Position = (TPosition_Train_Coords) Preceived_Data.Get_Object_Third();
		
		TTriple_Object Position_Data = new TTriple_Object();
		//For Stimulus, the positions are: First and Second!
		Position_Data.Set_Object_First(Precondition_Position);
		Position_Data.Set_Object_Second(Postcondition_Position);
		
		//int Integer_Route = Position_Train_Coords.Get_Route();
		int Integer_Route = Math.max(Precondition_Position.Get_Route(),
				Postcondition_Position.Get_Route());
		
		ArrayList<TBelief> Beliefs2 = this.Agent.Get_GW().Get_Beliefs_From_Type_Belief(TType_Beliefs.Stimulus_Danger_on_the_Route);
		Salient_Belief = (TStimulus) Beliefs2.getFirst();
		TPredicate Predicate = Salient_Belief.Get_Predicate();
		
		Predicate.Set_Subject(Position_Data);
		Predicate.set_Object_Complement(TType_Danger.Unknown);
		Salient_Belief.Update_Saliency(0.9);
		
		Game.Print("I see a Danger on the Route.");
		Game.Print("I need to know where and what I'm seeing.");
		Game.Print_Colored_Text("I want to ask to TCS: What kind of danger is there in the route? "+Integer_Route, 5);
		
		//22/05/2025
//		Game.Get_Input("Manage_Danger_on_the_Route - TStimulus: "+Salient_Belief.get_Type_Belief()+
//				" - Saliency: "+Salient_Belief.Get_Saliency());
		//this.Agent.Get_GW().Update_Belief_by_Stimulus(Salient_Belief);
		this.Temporary_Stimulus = Salient_Belief;
		
		
	}
	
	public void Manage_Acquired_Closed_Route_Duration(TPerception Perception)
	{
//		Game.Print("1 Desires: "+this.Agent.Get_GW().Get_Desires().size());
		TRegion Inhibition_Regions = this.Agent.Get_GW().Get_Inhibition_Regions();
		ArrayList<TBelief> Unhinibited_Beliefs = this.Agent.Get_GW().Get_UnInhibited_Beliefs();
		TStimulus Salient_Belief;// = (TSalient_Belief) this.Get_Inhibited_Beliefs_From_Type_Belief(TType_Beliefs.Stimulus_Temporary_Closed_Route).getFirst();
		
		TTriple_Object Preceived_Data = Perception.get_Perceived_Data();
		Game.Print("I Acquired Closed _Route Duration.");
		Game.Print("Now, I update several information in my Beliefs and in plan actions.");
		ArrayList<TBelief> Beliefs2 = this.Agent.Get_GW().Get_Beliefs_From_Type_Belief(TType_Beliefs.Stimulus_Temporary_Closed_Route);
		Salient_Belief = (TStimulus) Beliefs2.getFirst();
		
		Salient_Belief.Update_Saliency(0.1);
		
		//22/05/2025
//		Game.Get_Input("Manage_Acquired_Closed_Route_Duration - TStimulus: "+Salient_Belief.get_Type_Belief()+
//				" - Saliency: "+Salient_Belief.Get_Saliency());
		//this.Agent.Get_GW().Update_Belief_by_Stimulus(Salient_Belief);
		this.Temporary_Stimulus = Salient_Belief;

		Integer Duration = (Integer) Preceived_Data.Get_Object_Third(); 
		Game.Print("Duration: "+Duration);
		
		int Integer_Route = (int) Preceived_Data.Get_Object_Second();
		int Specular_Integer_Route = this.Get_Map().Get_Specular_Route(Integer_Route);
		Game.Print("The duration time acquired for the temporarily closed route is: "+Duration);
		
		for(TBelief Bel: Unhinibited_Beliefs)
		{
//			Game.Print(Bel.get_Name()+": "+Bel.get_Type_Belief()+" - "+Bel.get_Predicate().get_Subject());
			if(Bel.Get_Type_Belief() == TType_Beliefs.Belief_Temporary_Closed_Route)
			{
				HashMap<Integer, Integer> Routes = (HashMap<Integer, Integer>) Bel.Get_Predicate().Get_Subject();
				
				if( !Routes.containsKey(Integer_Route) )
				{
					Routes.put(Integer_Route, Duration);
					Routes.put(Specular_Integer_Route, Duration);
					Route route = this.Get_Map().Get_Route(Integer_Route);
					route.Set_Duration_Closed(Duration);
					
					route = this.Get_Map().Get_Route(Specular_Integer_Route);
					route.Set_Duration_Closed(Duration);
				}
				Bel.Get_Predicate().Set_Subject(Routes);
			}
		}
		
		
		ArrayList<TIntention> Intentions = this.Agent.Get_GW().Get_Intentions();
//		Game.Print("2 Desires: "+this.Agent.Get_GW().Get_Desires().size());

		TIntention Intention = Intentions.getFirst();
		TActive_Desire Desire = Intention.Get_Active_Desire();

		TAttentional_Standing_Desire Attentional_Goal = Desire.Get_Attentional_Standing_Desire();
		if(Desire.Get_Option_List().size()>0)
		{
			TOption Selected_Option = Desire.Get_Option_List().get(Intention.Get_Seleted_Option_Id());
			
			Selected_Option.Inc_Action_To_Do_ID();

			if(Selected_Option.Get_Action_To_Do_ID() >= Selected_Option.Get_Plan_Actions().size())
			{
				TActive_Desire Desire_to_Delete = Intention.Get_Active_Desire();
				Game.Print_Colored_Text("My intention to acquire the Duration for the Closed Route has been fulfilled", 5);
				Game.Print("Now I delete the Selected Intention, with its desire and I move the Epistemic Desire in my goal satisfied List");
				Game.Print(Desire_to_Delete.Get_Name()+": "+Desire_to_Delete.Get_Attentional_Standing_Desire().Get_Saliency()+
						" - Epistemic Desire: "+Desire_to_Delete.Get_Attentional_Standing_Desire().Get_Name());
				this.Agent.Get_GW().Delete_Intention(Intention);
				
				this.Agent.Get_GW().Get_Goals().remove(Attentional_Goal);
				this.Long_Memory.Delete_Goal(Attentional_Goal);
				this.Long_Memory.Insert_Attentional_Goals_Satisfied(Attentional_Goal);

			}
		}
		
//		Game.Print("3 Desires: "+this.Agent.Get_GW().Get_Desires().size());
		
//		Beliefs2 = this.Agent.Get_GW().Get_Beliefs_From_Type_Belief(TType_Beliefs.Stimulus_Temporary_Closed_Route);
//		Salient_Belief = (TSalient_Belief) Beliefs2.getFirst();
//		Salient_Belief.Update_Saliency(0.1);
		
		
		
		//Forse conviene eliminare tutte le intention con la route che non è possibile usare???
		Intention = Intentions.getFirst();
		Intentions.remove(Intention);
		
		this.Agent.Get_GW().Update_Desires_for_Broadcast();
	}
	
	public TAgent Get_Agent()
	{
		return this.Agent;
	}
	
	public void Update_Temporary_Closed_Routes()
	{
		ArrayList<TBelief> Unhinibited_Beliefs = this.Agent.Get_GW().Get_UnInhibited_Beliefs();
		for(TBelief Bel: Unhinibited_Beliefs)
		{
			if(Bel.Get_Type_Belief() == TType_Beliefs.Belief_Temporary_Closed_Route)
			{
				HashMap<Integer, Integer> Routes = (HashMap<Integer, Integer>) Bel.Predicate.Get_Subject();
				ArrayList<Integer> KeysToDelete = new ArrayList<>();
				
				if(Routes!=null)
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
							//Routes.remove(Route_ID);
							KeysToDelete.add(Route_ID);
//							Route route = this.Get_Map().Get_Route(Route_ID);
//							route.Set_Duration_Closed(0);
						}
					}
				}
				
				for(Integer Route_ID: KeysToDelete)
				{
					Routes.remove(Route_ID);
					Route route = this.Get_Map().Get_Route(Route_ID);
					route.Set_Duration_Closed(0);
				}
				
				Bel.Predicate.Set_Subject(Routes);
			}
		}
	}
	
	public boolean Stimulus_Inhibition()
	{
		boolean result = true;
		
		
		if(this.Temporary_Stimulus == null )
		{
			Game.Print("I have NOT a stimulus.");
			this.Agent.Get_GW().Print_Data(1, 0);
			return result;
		}
		Game.Print_Colored_Text("Stop before calling AM_Exogenous_Module method", 7);
//		Game.Press_Enter();
		this.Agent.Get_GW().Print_Data(2, 0);
		
		
		Game.Print("I have a stimulus and I check if it has a saliency greater than "+
				"my Saliency/Attention threholds.");
		
		TRegion Inhi_regions = this.Get_Inhibition_Regions();
		
		TStimulus Relevant_Stimulus = this.Apply_Filter( this.Temporary_Stimulus , Inhi_regions );
		
		
		if ((Inhi_regions != null) && (Relevant_Stimulus != null))
		{
			//if ( relevant_Stimulus.Get_Saliency() > this.Agent.Get_E_Inhibition_function().get_Saliency_Threshold() )
			if ( Relevant_Stimulus.Get_Saliency() > this.Agent.Get_GW().Get_Saliency_Threshold() )
			{
				// The Shifting Attention module finds the goal
		    	// corresponding to the relevant_Stimulus ( if it exists )
				Game.Print("This stimulus is greater than Saliency Threshold.");
				Game.Print("I create an Epistemic Desire and I promote it to Desire.");
				Game.Print("The Saliency of Epistemic Desire is greater than Saliency Threshold.");
				Game.Print(Relevant_Stimulus.Get_Saliency()+ " >= "+ this.Agent.Get_E_Inhibition_Function().Get_Saliency_Threshold());
				Game.Print(Relevant_Stimulus.Get_Saliency()+ " >= "+ this.Agent.Get_GW().Get_Saliency_Threshold());
				Game.Print("relevant_Stimulus.get_Type_Belief(): "+Relevant_Stimulus.Get_Type_Belief());
//				Game.Get_Input("Press Enter");
				
				this.Agent.Get_GW().Update_Belief_by_Stimulus(Relevant_Stimulus);
			}
		}	
		else
		{
			//if (Stimulus.Get_Saliency() > this.Agent.Get_E_Inhibition_function().get_Attention_Threshold())
			if (this.Temporary_Stimulus.Get_Saliency() > this.Agent.Get_GW().Get_Attention_Threshold())
			{
				Game.Print("This stimulus is greater than Attention Threshold.");
				Game.Print("I create an Epistemic Desire and I promote it to Desire.");
				Game.Print("The Saliency of Epistemic Desire is greater than Attention Threshold.");
				Game.Print(this.Temporary_Stimulus.Get_Saliency()+ " >= "+ this.Agent.Get_E_Inhibition_Function().Get_Attention_Threshold());
				Game.Print(this.Temporary_Stimulus.Get_Saliency()+ " >= "+ this.Agent.Get_GW().Get_Attention_Threshold());
				
				this.Agent.Get_GW().Update_Belief_by_Stimulus(this.Temporary_Stimulus);
			}
		}
		this.Temporary_Stimulus = null;
		
		
		
		return result;
	}
	
	private TStimulus Apply_Filter(TStimulus Stimulus, TRegion Inhi_regions )
	{
		TStimulus Relevant_Stimulus = null;

		//if the inhibition regions do NOT contain the route from which the stimulus comes
		//it means that the route from which the stimulus comes is to be paid attention to
		switch (Stimulus.Get_Type_Belief())
		{
			case Stimulus_Ok_Correct_Movement:
				/**
				 * In this case, the prev movement of the Agent was OK
				 * So I only create a irrilevant sitmulus and I set its Saliency to a lower
				 * Saliency value
				 * This stimulus is not handled, beacusa it is only to update the correct
				 * position of the agent in the mind of the agent. This updating is handled
				 * by the WMM
				 */
				Relevant_Stimulus = Stimulus;
				Relevant_Stimulus.Update_Saliency(0.1);
			break;
			
			case TType_Beliefs.Stimulus_Closed_Route:
			
				break;
			case TType_Beliefs.Stimulus_Busy_Route:
				break;
			case TType_Beliefs.Stimulus_Irrelevant:
				break;
			case TType_Beliefs.Stimulus_Temporary_Closed_Route:
				/**
				 * Protocol Predicate:
				 * Subject => Route Number (Integer)
				 * Relationship => is_Busy | is_Closed | is_Temporary_Closed
				 *  if Relationship = is_Temporary_Closed
				 * 		Object = => Time amount (Integer)
				 */
				{
					Integer Route_Number = (Integer) Stimulus.Get_Predicate().Get_Subject();
//					Game.Print("Route_Number for Apply_Filter: "+Route_Number );
					
					//If Route Number NOT is in Inhi_regions: the route is in my path! The agent must be careful!
					if (! Inhi_regions.Integer_Routes.contains(Route_Number))
					{
						Relevant_Stimulus = Stimulus;
						Relevant_Stimulus.Update_Saliency(0.9);
					}
					else
					{
						Relevant_Stimulus = Stimulus;
						Relevant_Stimulus.Update_Saliency(0.1);
					}
				};
				break;
			case TType_Beliefs.Stimulus_Danger_on_the_Route:
				/**
				 * Protocol Predicate:
				 * Subject => Route Number (Integer)
				 * Relationship => has_a_Danger 
				 * Object = => TType_Danger
				 */
				{
					
					TTriple_Object Perception = (TTriple_Object) Stimulus.Get_Predicate().Get_Subject();
					
					TPosition_Train_Coords Precondition_Position = (TPosition_Train_Coords) Perception.Get_Object_First();
					TPosition_Train_Coords Postcondition_Position = (TPosition_Train_Coords) Perception.Get_Object_Second();
					
//					Integer Route_Number = (Integer) Stimulus.get_Predicate().get_Subject();
					Integer Route_Number = Math.max(Precondition_Position.Get_Route(), Postcondition_Position.Get_Route());
					
					if (! Inhi_regions.Integer_Routes.contains(Route_Number))
					{
						Relevant_Stimulus = Stimulus;
						Relevant_Stimulus.Update_Saliency(0.9);
					}
					else
					{
						Relevant_Stimulus = Stimulus;
						Relevant_Stimulus.Update_Saliency(0.1);
					}
				};
				break;
			case TType_Beliefs.Stimulus_Too_Close_To_The_Train:
				/**
				 * Protocol Predicate:
				 * Subject => Player Number
				 * Relationship => is_Too_Close_To_The_Train
				 * Object => Coords := (Route, Step)
				 */
				break;
			case TType_Beliefs.Stimulus_Route_Status:
				/**
				 * Protocol Predicate:
				 * Subject => Route Number (Integer)
				 * Relationship => is 
				 * Object = => TType_Route_Status
				 */
				{
					Integer Route_Number = (Integer) Stimulus.Get_Predicate().Get_Subject();
//					Game.Print("Route_Number for Apply_Filter: "+Route_Number );
					
					//If Route Number NOT is in Inhi_regions: the route is in my path! The agent must be careful!
					if (! Inhi_regions.Integer_Routes.contains(Route_Number))
					{
						Relevant_Stimulus = Stimulus;
//						Relevant_Stimulus.Update_Saliency(0.9);
					}
				}
				break;
			default:
				Game.Print("I cannot to handle Type Beliefs for Stimulus in method: Apply_Filter");
				Game.Print("(Stimulus.get_Type_Belief(): "+(Stimulus.Get_Type_Belief()));
				Game.End_Game();
		}

		return Relevant_Stimulus;
	}
}