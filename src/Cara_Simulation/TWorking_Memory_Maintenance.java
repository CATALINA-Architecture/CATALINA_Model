package Cara_Simulation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;




public class TWorking_Memory_Maintenance {
	
	private TLong_Memory Long_Memory;
	private TSensor_Managment Sensor;
	private TAgent Agent;
	
	public TWorking_Memory_Maintenance(TAgent agent)
	{
		this.Agent = agent;
		this.Long_Memory = new TLong_Memory(this);
		this.Sensor = new TSensor_Managment();
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
			//Game.Print("Prima -"+belief.get_Name()+" - "+Predicato.get_Name()+" - "+belief.get_Type_Belief());
			belief.set_Predicate(Predicato);
			//Game.Print("Dopo- "+belief.get_Name()+" - "+Predicato.get_Name()+" - "+belief.get_Type_Belief());
			
			ArrayList<TPredicate> Predicates = new ArrayList<TPredicate>();
			Predicates.add(Predicato);
			Map_Beliefs_and_Predicates.put(belief.get_Name(), Predicates);
		}
//		Game.Print(this.Long_Memory.Get_Inc_Beliefs_Number()+" - I finished the set_Beliefs Function: "+Beliefs.size()+ " - "+Beliefs.getFirst().get_Name()
//				+ " - "+Beliefs.getLast().get_Name());
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
			
			//
			Map_Attentional_Goal_and_Predicates.put(Functional_Goal.get_Name(), List_Predicates);
			Map_Attentional_Goal_and_Beliefs.put(Functional_Goal.get_Name(), List_Beliefs);
		}
	}
	
	//TO DO: I must to check this method	
	public void Set_Epistemic_Goals(ArrayList<TEpistemic_Goal> epistemic_Goals)
	{
		
//		int epistemic_goals_nuber = Epistemic_Goals.size(); 
//		this.Goals.addAll(Epistemic_Goals);
//		//this.Goals_Number += epistemic_goals_nuber;
//		this.Goals_Number = this.Goals.size();
		
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
//			this.Belief_List.Add_Belief(belief);
//			TPredicate Predicato = null;
//			Predicato = this.Map_Predicates.get(belief.get_Predicate_name());
//			belief.set_Predicate(Predicato);
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
		
		for(TAttentional_Goal Goal: Goals)
		{
			if(Goal instanceof TFunctional_Goal)
			{
				TFunctional_Goal Functional_Goal = (TFunctional_Goal) Goal;
				Inhibited_Goals.add(Functional_Goal.get_Name());
			}
		}
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
	
	public Boolean Get_Status_Route(TPosition_Train_Coords Position_Train_Coords)
	{
		Boolean Result = false;
	
		TTriple_Object Request = new TTriple_Object();
		//First Object is a String for asking something
		Request.Object_First = "Status Route";
		//Second Object is the route's integer id
		Request.Object_Second = Position_Train_Coords.get_Route();
		//Third Object is the route's step id
		Request.Object_Third = Position_Train_Coords.get_Step();
		TTriple_Object Response = this.Agent.Get_TCS().Response(Request);
		String Response_String = (String) Response.Object_First;
		switch(Response_String)
		{
		case "Route is Temporary Closed":
			
			Game.Print("Route is Temporary Closed");
			
			TSalient_Belief Salient_Belief = (TSalient_Belief) this.Get_Inhibited_Beliefs_From_Type_Belief(TType_Beliefs.Stimulus_Temporary_Closed_Route).getFirst();
			//Game.Print(this.Get_Inhibited_Beliefs_From_Type_Belief(TType_Beliefs.Stimulus_Temporary_Closed_Route).size());
			TPredicate Predicate = Salient_Belief.get_Predicate();
			Predicate.Set_Subject(Response.Object_Second);
			//This is the Saslient_Belief for Epistemic_Goal
			Salient_Belief.Update_Saliency(0.9);
			
			this.Agent.get_GW().Update_Belief_by_Stimulus(Salient_Belief);
			
			//Game.Print(Belief_Base);
			Result = false;
			break;
		default:
			Game.Print("I cannot to handle Response in WWM at function Get_Status_Route: "+Response_String);
		}
		return Result;
	}
	
	public Boolean Get_Time_for_Temporary_Closed_Route(Integer Route_Number)
	{
		Boolean Result = false;
		
		TTriple_Object Request = new TTriple_Object();
		//First Object is a String for asking something
		Request.Object_First = "How long will the route be closed?";
		//Second Object is the route's integer id
		Request.Object_Second = Route_Number;
		//Third Object is the route's step id
		Request.Object_Third = null;
		TTriple_Object Response = this.Agent.Get_TCS().Response(Request);
		String Response_String = (String) Response.Object_First;
		
		switch(Response_String)
		{
		case "Time for Temporary Closed Route":
			int time = (int) Response.Object_Second;
			Game.Print("Time Temporary Closed Route is: "+time);
			Game.Print("Scenario 1 & 2 finished!");
			Game.End_Game();
			Result = false;
			break;
		default:
			Game.Print("I cannot to handle Response in WWM at function Get_Status_Route: "+Response_String);
		}
		return Result;
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
	
	public boolean  Perception_Processing(int i)
	{
		Game.Print("Perception_Processing: "+i);
		boolean result = true;
		if(i>0)
		{
			result = true;
			Game.Print("***********************************");
			Game.Print("***********************************");
			Game.Print("***********************************");
			Game.Print("***********************************");
			Game.Print("***********************************");
			Game.Print("***********************************");
			Game.Print("***********************************");
			Game.Print("*************  CHANGE OF SCENARIO  **********************");
			Game.Print("***********************************");
			Game.Print("***********************************");
			Game.Print("***********************************");
			Game.Print("***********************************");
			Game.Print("***********************************");
			Game.Print("***********************************");
			Game.Print("***********************************");
			
			TPosition_Train_Coords Position_Train_Coords = new TPosition_Train_Coords(128, 0);
			
			TSalient_Belief Salient_Belief = (TSalient_Belief) this.Get_Inhibited_Beliefs_From_Type_Belief(TType_Beliefs.Stimulus_Temporary_Closed_Route).getFirst();
			//Game.Print(this.Get_Inhibited_Beliefs_From_Type_Belief(TType_Beliefs.Stimulus_Temporary_Closed_Route).size());
			TPredicate Predicate = Salient_Belief.get_Predicate();
			Predicate.Set_Subject(Position_Train_Coords.get_Route());
			//This is the Saslient_Belief for Epistemic_Goal
			Salient_Belief.Update_Saliency(0.9);
			
			this.Agent.get_GW().Update_Belief_by_Stimulus(Salient_Belief);
		}
		try 
		{
			
		}
		catch (Exception e) {
	      Game.Print("Something went wrongin method: Insert_New_Desires.");
	      Game.Print("Message Error: "+e.getMessage());
	      result = false;
	    }
		return result;
		
	}


}
