package Cara_Simulation;

import java.lang.reflect.Array;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;

public class TGlobalWorkspace {
	
	//Temporal Data to handle events
	private TSalient_Belief Temp_Salient_Belief;
	
	
	
	private Integer Goals_Number = 0;
	private Integer Beliefs_Number = 0;
	private Integer Predicates_Number = 0;
	private Integer Desire_Number = 0;
	private Integer Intention_Number = 0;
	private TAgent Agent;
	private Environment Map_Known;
	private HashMap<TType_Update_Contract, ArrayList<Object>> Update_Contracts;
	
	//Data Types Change:
	private boolean Updated_Desires = false;
	private boolean Updated_Desires_with_options = false;
	private boolean Updated_Goals = false;
	private boolean Updated_UnInhibited_Goals = false;
	private boolean Updated_Inhibited_Goals = false;
	
	private boolean Updated_Beliefs = false;
	private boolean Updated_Salient_Beliefs = false;
	private boolean Updated_UnInhibited_Beliefs = false;
	private boolean Updated_Inhibited_Beliefs = false;
	
	/**
	 * These properties are only used to print a log.
	 */
	public boolean Print_Selected_Path = true;
	public boolean Print_steps_and_routes = true;
	public boolean Updated_Beliefs_for_print = false;
	public boolean Updated_Salient_Beliefs_for_print = false;
	public boolean Updated_UnInhibited_Beliefs_for_print = false;
	public boolean Updated_Inhibited_Beliefs_for_print = false;
	public boolean Updated_Desires_for_print = false;
	public boolean Updated_Desires_with_options_for_print = false;

	public boolean Updated_Intentions_for_print = false;
	public boolean Updated_Regions_for_print = false;
	public boolean Updated_UnInhibited_Regions_for_print = false;
	public boolean Updated_Inhibited_Regions_for_print = false;
	public boolean Updated_Goals_for_print = false;
	public boolean Updated_UnInhibited_Goals_for_print = false;
	public boolean Updated_Inhibited_Goals_for_print = false;
	/**
	 * End properties used to print a log.
	 */
	
	private boolean Updated_Regions = false;
	private boolean Updated_UnInhibited_Regions = false;
	private boolean Updated_Inhibited_Regions = false;
	
	private boolean Updated_Intentions = false;
	
	
	//Goal Properties
	private ArrayList<TAttentional_Goal> Goals;
	private ArrayList<TAttentional_Goal> Inhibited_Goals;
	private ArrayList<TAttentional_Goal> UnInhibited_Goals;
	private HashMap<String, TAttentional_Goal> Map_Goals;
	
	//Goal Properties
	private ArrayList<TFunctional_Goal> Functional_Goals;
	private ArrayList<TEpistemic_Goal> Epistemic_Goals;
	private ArrayList<TFunctional_Goal> Inhibited_Functional_Goals;

	
	private ArrayList<String> Functional_Goals_Names;
	private ArrayList<String> Inhibited_Goals_Names;
	
	//Green and Quality Goals
	private ArrayList<TGreen_Goal> Green_Goals;
	private ArrayList<TQuality_Goal> Quality_Goals;
	private HashMap<String, TGreen_Goal> Map_Green_Goals;
	private HashMap<String, TQuality_Goal> Map_Quality_Goals;
	
	//Option Properties
	private ArrayList<TOption> Options;
	private ArrayList<TOption> Surviving_Options;
	private ArrayList<TIntention> Intentions;
	
	//Predicate Properties
	private ArrayList<TPredicate> Predicates;
	private HashMap<String, TPredicate> Map_Predicates;
	
	//Belief Properties
	private ArrayList<TBelief_Base> Beliefs;
	private HashMap<String, TBelief_Base> Map_Beliefs;
	private TRegion Regions;
	private TRegion Inhibition_Regions;
	private TRegion UnInhibition_Regions;
	private ArrayList<TBelief_Base> Inhibited_Beliefs;
	private ArrayList<TBelief_Base> UnInhibited_Beliefs;
	private HashMap<TAttentional_Goal, ArrayList<TBelief_Base>> Map_Attentional_Goal_and_Beliefs;
	private HashMap<TAttentional_Goal, ArrayList<Object>> Map_Attentional_Goal_and_Regions;
	private TBelief_List Belief_List;
	
	//Desire Properties
	private ArrayList<TDesire> Desires;
	
	private Double Saliency_Threshold;
	private Double Attention_Threshold;
	
	private HashMap<String, ArrayList<String>> Map_Data_Changed_and_Modules;
	
	
	public TGlobalWorkspace(TAgent agent)
	{
		//I create a Circular reference with the Agent, i.e. itself
		this.Agent = agent;
		
		//I create the list of all the Goals
		this.Goals = new ArrayList<TAttentional_Goal>();
		
		//I create the list of all the Goals
		this.Inhibited_Goals = new ArrayList<TAttentional_Goal>();
		this.UnInhibited_Goals = new ArrayList<TAttentional_Goal>();
		
		this.Functional_Goals = new ArrayList<TFunctional_Goal>();
		this.Epistemic_Goals = new ArrayList<TEpistemic_Goal>();
		
		this.Inhibited_Functional_Goals = new ArrayList<TFunctional_Goal>();
		
		this.Functional_Goals_Names = new ArrayList<String>();
		this.Inhibited_Goals_Names = new ArrayList<String>();
		
		this.Map_Goals = new HashMap<String, TAttentional_Goal>();
		this.Map_Green_Goals = new HashMap<String, TGreen_Goal>();
		this.Map_Quality_Goals = new HashMap<String, TQuality_Goal>();
		
		this.Green_Goals = new ArrayList<TGreen_Goal>();
		this.Quality_Goals = new ArrayList<TQuality_Goal>();
	
		//I create the Option type lists
		this.Options = new ArrayList<TOption>();
		this.Surviving_Options = new ArrayList<TOption>();
		
		//I create the Intention type lists
		this.Intentions = new ArrayList<TIntention>();
		
		//I create the Belief type lists
		this.Regions = new TRegion();
		this.Inhibition_Regions = new TRegion();
		this.UnInhibition_Regions = new TRegion();
		
		//I create the Desire type lists
		this.Desires = new ArrayList<TDesire>();
		
		//Set various properties
		this.Saliency_Threshold = this.Agent.Get_E_Inhibition_function().Get_Default_Saliency_Threshold();
		this.Attention_Threshold = this.Agent.Get_E_Inhibition_function().Get_Default_Attention_Threshold();
		this.Goals_Number = 0;
		
		this.Desire_Number = 0;
		this.Intention_Number = 0;
		
		this.Predicates = new ArrayList<TPredicate>();
		this.Map_Predicates = new HashMap<String, TPredicate>();
		
		this.Beliefs = new ArrayList<TBelief_Base>();
		this.Map_Beliefs = new HashMap<String, TBelief_Base>();
		this.Inhibited_Beliefs = new ArrayList<TBelief_Base>();
		this.UnInhibited_Beliefs = new ArrayList<TBelief_Base>();
		
		//I initialize the List Map between TAttentional_Goal and TBelief_Base 
		this.Map_Attentional_Goal_and_Beliefs = new HashMap<TAttentional_Goal, ArrayList<TBelief_Base>>();
		this.Belief_List = new TBelief_List();

		//this is the list in which any component can observe the data type change
		HashMap<String, ArrayList<String>> Map_Data_Changed_and_Modules = new HashMap<String, ArrayList<String>>();
		
		this.Updated_Desires = false;
		this.Updated_Goals = false;
		this.Updated_UnInhibited_Goals = false;
		this.Updated_Inhibited_Goals = false;
		
		this.Updated_Beliefs = false;
		this.Updated_UnInhibited_Beliefs = false;
		this.Updated_Inhibited_Beliefs = false;
		
		this.Updated_Regions = false;
		this.Updated_UnInhibited_Regions = false;
		this.Updated_Inhibited_Regions = false;
		
		this.Updated_Intentions = false;
		
		/**
		 * These properties are only used to print a log.
		 */
		this.Updated_Beliefs_for_print = true;
		this.Updated_Salient_Beliefs_for_print = true;
		this.Updated_UnInhibited_Beliefs_for_print = true;
		this.Updated_Inhibited_Beliefs_for_print = true;
		this.Updated_Desires_for_print = true;
		this.Updated_Desires_with_options_for_print = true;

		this.Updated_Intentions_for_print = true;
		this.Updated_Regions_for_print = true;
		this.Updated_UnInhibited_Regions_for_print = true;
		this.Updated_Inhibited_Regions_for_print = true;
		this.Updated_Goals_for_print = true;
		this.Updated_UnInhibited_Goals_for_print = true;
		this.Updated_Inhibited_Goals_for_print = true;
		/**
		 * End properties used to print a log.
		 */
		
		
		this.Update_Contracts = new HashMap<TType_Update_Contract, ArrayList<Object>>();
		for(TType_Update_Contract Type: TType_Update_Contract.values())
		{
			ArrayList<Object> Objects = new ArrayList<Object>();
			this.Update_Contracts.put(Type, Objects);
		}
	}
	
	public void Set_Map_Know(Environment Map)
	{
		this.Map_Known = Map;
	}
	
	public Boolean Delete_Desire(TDesire desire)					
	{
//		TAttentional_Goal Goal = desire.get_Attentional_Goal();
//		this.Goals.add(Goal);
//		desire.set_Attentional_Goal(null);
		
		//desire.get_Attentional_Goal().Clear();
		desire.get_Option_List().clear();
		this.Updated_Desires = true;
		this.Updated_Desires_for_print = true;
		return this.Desires.remove(desire);
	}
	
	//TODO to check
	public ArrayList<TAttentional_Goal> Get_Goals()
	{
		
		if (this.Goals.isEmpty())
		{
			this.Goals.addAll(this.Agent.Get_WMM().Get_Attentional_Goals());
		}
		return this.Goals;
	}
	
	//TODO to check
	public ArrayList<TFunctional_Goal> Get_Functional_Goals()
	{
		if (this.Functional_Goals.isEmpty())
		{
			this.Functional_Goals.addAll(this.Agent.Get_WMM().Get_Functional_Goals());
		}
		return this.Functional_Goals;
	}
	
	public ArrayList<TEpistemic_Goal> Get_Epistemic_Goals()
	{
		if (this.Epistemic_Goals.isEmpty())
		{
			this.Epistemic_Goals.addAll(this.Agent.Get_WMM().Get_Epistemic_Goals());
		}
		return this.Epistemic_Goals;
		
	}
	
	public ArrayList<String> Get_Functional_Goals_Names()
	{
		if (this.Functional_Goals_Names.isEmpty())
		{
			this.Functional_Goals_Names.addAll(this.Agent.Get_WMM().Get_Functional_Goals_Names());
		}
		return this.Functional_Goals_Names;
	}	
	
	public TPredicate Create_Predicate(Object obj1, TType_Relationship relationship, Object obj2)
	{
			return this.Agent.Get_WMM().Create_Predicate(obj1, relationship, obj2);
	}
	
	public TBelief_Base Create_Beliefs(TPredicate Predicate, Boolean truth, Object information_Source,
			LocalDateTime time_stamp, TType_Beliefs type_Belief)
	{
		return this.Agent.Get_WMM().Create_Belief(Predicate, truth, information_Source, time_stamp,
													type_Belief);
	}
	
	public ArrayList<String> Get_Inhibited_Goals_Names()
	{
		if (this.Inhibited_Goals_Names.isEmpty())
		{
			this.Inhibited_Goals_Names.addAll(this.Agent.Get_WMM().Get_Functional_Goals_Names());
		}
		return this.Inhibited_Goals_Names;
	}
	
	public ArrayList<TAttentional_Goal> Get_Inhibited_Goals()
	{

		return this.Inhibited_Goals;
	}
	
	public ArrayList<TFunctional_Goal> Get_Inhibited_Functional_Goals()
	{
		return this.Agent.Get_WMM().Get_Inhibited_Functional_Goals();
	}
	
//	public ArrayList<TAttentional_Goal> Get_UnInhibited_Goals()
//	{
////		ArrayList<TAttentional_Goal> i_goals = new ArrayList<TAttentional_Goal>();
////	 	for (TAttentional_Goal c: this.Goals)
////	    {
////	 		if (!c.Inhibited)
////	 		{
////	 			i_goals.add(c);
////	 		}
////	 		
////	    }
////		return i_goals;
//		ArrayList<TAttentional_Goal> UnInhibited_Goals = new ArrayList<TAttentional_Goal>();
//		UnInhibited_Goals.addAll(this.Goals);
//		UnInhibited_Goals.removeAll(this.Inhibited_Goals);
//		return UnInhibited_Goals;
//	}
	
	
	
	public ArrayList<TDesire> Get_Desires()
	{
		
//		ArrayList<Desire> i_Desideri = new ArrayList<Desire>();
////	 	for (Desire c: this.Desideri)
////	    {
////	 		i_Desideri.add(c);	 		
////	    }
////		return i_Desideri;
//		i_Desideri.addAll(this.Desideri);
//		return this.Desideri;
		return this.Desires;
	}
	
	public Double Get_Saliency_Threshold()
	{
		return this.Saliency_Threshold;
	}

	//public ArrayList<Region> Get_Regions()
	public TRegion Get_Regions()
	{
		return this.Inhibition_Regions;
	}
	
	public ArrayList<TOption> Get_Options()
	{
		return this.Options;
	}
	
//	public ArrayList<TOption> Get_Surviving_Options()
//	{
//		return this.Surviving_Options;
//	}
	
	public ArrayList<TIntention> Get_Intentions()
	{
		return this.Intentions;
	}
	
	public void Update_Attention_Threshold(Double value)
	{
		this.Attention_Threshold = value;
	}
	
	public Double Get_Attention_Threshold()
	{
		return this.Attention_Threshold;
	}
	
	public void Update_Saliency_Threshold(Double value)
	{
		this.Saliency_Threshold = value;
	}

	public HashMap<TAttentional_Goal, ArrayList<TBelief_Base>> Get_Map_Attentional_Goal_and_Beliefs2()
	{
		return this.Map_Attentional_Goal_and_Beliefs;
	}
	
	/***
	 * TODO to DEVELOPMENT
	 * @param Stimulus
	 * @return
	 */
	public Double Get_Saliency(TSalient_Belief Stimulus)
	{
		Double Saliency = 0.0;
		Saliency = Stimulus.Get_Saliency();
//		TIntention Sel_Inten = this.Agent.GW.Selected_Intentions.get(0);
//		Route Rotta_STimolo = Stimulus.Tratta_usata;
//		
//		//Valore di default
//		Saliency = 0.8;	
//		switch(Stimulus.getType_Epistemic_Goal())
//		{
//			case TEG_Busy_Route:
//				//Se il problema è in una tratta che sto considerando allora abbasso la
//				//Saliency per far scattare il goal epistemico
//				if (Sel_Inten.getPlan().Lista_Tratte_numerate.contains(Rotta_STimolo))
//				{
//					Saliency = 0.3;
//				}					
//				else
//				{
//					this.Attention_Threshold = 0.8;
//				}
//				break;
//			case TEG_Danger_Route:
//				//Se il problema è in una tratta che sto considerando allora abbasso la
//				//Saliency per far scattare il goal epistemico
//				if (Sel_Inten.getPlan().Lista_Tratte_numerate.contains(Rotta_STimolo))
//				{
//					Saliency = 0.3;
//				}					
//				else
//				{
//					this.Attention_Threshold = 0.8;
//				}
//				break;
//			case TEG_Monitor_Route:
//				//Se il problema è in una tratta che sto considerando allora abbasso la
//				//Saliency per far scattare il goal epistemico
//				if (Sel_Inten.getPlan().Lista_Tratte_numerate.contains(Rotta_STimolo))
//				{
//					Saliency = 0.3;
//				}					
//				else
//				{
//					this.Attention_Threshold = 0.8;
//				}
//				break;
//		}
		return Saliency;
	}
	
	
	/***
	 * TODO DEVELOPMENT
	 */
	public ArrayList<Route> Get_Inhibition_Regions_By_Stimulus(TBelief_Base Stimulus )
	{
		TGlobalWorkspace GW = this.Agent.Get_GW();
		ArrayList<Route> All_Regions = GW.Get_Map_Known().All_Routes;

		//ArrayList<TBelief_Base> Inhibition_Regions = new ArrayList<TBelief_Base>();

		//In according to the type of Belief, the Agent get a specific inhibited region
		switch (Stimulus.get_Type_Belief())
		{

		case TType_Beliefs.Stimulus_Closed_Route:
		case TType_Beliefs.Stimulus_Busy_Route:
		case TType_Beliefs.Stimulus_Temporary_Closed_Route:
			/**
			 * Protocol Predicate:
			 * Subject => Route Number (Integer)
			 * Relationship => is_Busy | is_Closed | is_Temporary_Closed
			 *  if Relationship = is_Temporary_Closed
			 * 		Object = => Time amount (Integer)
			 */
			{
				Integer Route_Number = (Integer) Stimulus.get_Predicate().get_Subject();
				Route route = GW.Get_Map_Known().Get_Route(Route_Number); 
				All_Regions.remove(route);
				
			}
			break;
		case TType_Beliefs.Stimulus_Too_Close_To_The_Train:
			/**
			 * Protocol Predicate:
			 * Subject => Player Number
			 * Relationship => is_Too_Close_To_The_Train
			 * Object => Coords := (Route, Step)
			 */
			{
				//Integer Player = (Integer) Stimulus.get_Predicate().get_Subject();			
				TDouble_Object Coords = (TDouble_Object) Stimulus.get_Predicate().get_Object_Complement();
				Integer Route_Number = (Integer) Coords.get_Object_First();
				Route route = GW.Get_Map_Known().Get_Route(Route_Number);
				
				All_Regions.remove(route);
			}
			break;
		}

		return All_Regions;
	}
	
	public ArrayList<TBelief_Base> Get_Inhibited_Beliefs()
	{
		return this.Inhibited_Beliefs;
	}
	
	public void Update_Inhibition_Regions(TRegion inhibition_Regions)
	{
		this.Agent.Get_WMM().Set_Inhibition_Regions(inhibition_Regions);
		
		this.Set_Inhibition_Regions(inhibition_Regions);
		
		
		Game.PrintLn();
		
		this.UnInhibition_Regions = this.Compute_All_Regions();

		this.UnInhibition_Regions.Routes.removeAll(inhibition_Regions.Routes);
		this.UnInhibition_Regions.Integer_Routes.removeAll(inhibition_Regions.Integer_Routes);
		this.UnInhibition_Regions.Destinations.removeAll(inhibition_Regions.Destinations);
		
		this.Updated_UnInhibited_Regions = true;
	}
	
	// TODO DEVELOPMENT
		public void Update_Inhibited_Goals(ArrayList<TAttentional_Goal> Inhibited_Goals)
		{
			
			this.Agent.Get_WMM().Set_Inhibited_Goals(Inhibited_Goals);
			this.Inhibited_Goals.clear();
			this.Inhibited_Goals.addAll(Inhibited_Goals);
			
			//this.Goals.clear();
			this.UnInhibited_Goals.clear();
			
			for(TAttentional_Goal Attentional_Goal: this.Get_Goals())
			{
				this.UnInhibited_Goals.add(Attentional_Goal);
			}
			this.UnInhibited_Goals.removeAll(Inhibited_Goals);

			this.Updated_Goals = true;			
		}
		
		
	// TODO DEVELOPMENT
	public void Update_Inhibited_Beliefs(ArrayList<TBelief_Base> inhibited_Beliefs)
	{
//		Game.Print("************  Function:  Update_Inhibited_Beliefs from Update_Inhibited_Beliefs Function  *********: TGlobalWorkspace");
//		this.Agent.get_GW().Print_Data(0, 0);
		
		this.Agent.Get_WMM().Set_Inhibited_Beliefs(inhibited_Beliefs);
		this.Inhibited_Beliefs.clear();
		this.Inhibited_Beliefs.addAll(inhibited_Beliefs);
		
		this.UnInhibited_Beliefs.clear();
		for(TBelief_Base Belief: this.Agent.Get_WMM().Get_Beliefs())
		{
			this.UnInhibited_Beliefs.add(Belief);
		}
		
		this.UnInhibited_Beliefs.removeAll(inhibited_Beliefs);
		
		this.Updated_Beliefs = true;
	}
	
	//From Reasoner Function: Update Intention
//	public void Update_Intentions(TIntention Sel_Inten)
	public void Update_Intentions(ArrayList<TIntention> Intentions)
	{
//		Game.Print("************  Function:  Update_Intentions from Deliberate Function  *********: TGlobalWorkspace");
		this.Agent.Get_GW().Print_Data(0, 0);
//		this.Selected_Intentions.clear();
		this.Intentions.addAll(Intentions);
		this.Updated_Intentions = true;
		this.Updated_Intentions_for_print = true;
		
		
		//Update Intentions (in Shift Attention) permits to agent to focus its attentions
//		Game.Print("Update_Intentions in TGlobalWorskspace --Before-- the call this.Agent.get_EI().get_SA().Updated_Intentions();");
//		this.Agent.Get_GW().Print_Data(1, 0);
		
		//this following method invokes the focus of Executive Inhibition Function
//		this.Agent.Get_E_Inhibition_function().Updated_Intentions();
		
		//Update Intentions (in Resource Allocator) permits to agent to execute its actions
//		Game.Print("Update_Intentions in TGlobalWorskspace --Before-- the call this.Agent.get_RA().Updated_Intentions(this.Selected_Intentions, this.Beliefs)();");
		this.Agent.Get_GW().Print_Data(1, 0);
//		this.Agent.get_RA().Updated_Intentions(this.Selected_Intentions, this.Beliefs);
	}
	
	
	
	public void Add_Attentional_Goal(TAttentional_Goal Goal)
	{
		this.Goals_Number++;
		Goal.set_Name("g" + this.Goals_Number);
		this.Goals.add(Goal);

	}
	

	
	public void Insert_New_Desires(ArrayList<TDesire> desires)			//*** not copied
	{
		try 
		{
			//In this time, I order goals to saliency order in higher order and In deliberation process
			//I select the first desire as selected intention
			
			this.Agent.Get_GW().Print_Data(0, 0);
	
			this.Desires.addAll(desires);
			this.Add_Desire_Number(desires.size());
			
			this.Updated_Desires = true;
			this.Updated_Desires_for_print = true;
			
			
			Game.Print("I inserted new Desires: "+desires.size());
			
//			//I get beliefs from Long Memory to send to Reasoner
//			if(Beliefs.isEmpty())
//			{
//				this.Beliefs.addAll(this.Agent.Get_WMM().Get_Beliefs());
//			}
//			
//			ArrayList<TBelief_Base> Temp_Beliefs = new ArrayList<TBelief_Base>();
//			//for first time or any time the UnInhibited_Beliefs is empty
//			if(this.UnInhibited_Beliefs.isEmpty())
//			{
//				Temp_Beliefs.addAll(this.Beliefs);
//			}
//			//for all other cases
//			else
//			{
//				Temp_Beliefs.addAll(this.UnInhibited_Beliefs);
//			}
	
			
			this.Agent.Get_GW().Print_Data(1, 0);
			
			
			//I send Desires and Beliefs to Reasoner for deliberation
//			ArrayList<TDesire> Desires_to_Delete = this.Agent.get_Reasoner().Deliberate(this.Desires, Temp_Beliefs, this.Selected_Intentions);
			
			// 2025.02.03
			//this.Desires.removeAll(Desires_to_Delete);
		}
		catch (Exception e) {
			Game.Print("Something went wrong in method: Insert_New_Desires.");
	      Game.Print("Message Error: "+e.getMessage());
	      Game.PrintLn();
	      e.printStackTrace();
	    }
	}
	

	public Integer Get_Goals_Number() {
		return Goals_Number;
	}

	public TAgent Get_Agent() {
		return this.Agent;
	}

	public Environment Get_Map_Known() {
		if (this.Map_Known == null)
		{
			this.Map_Known = this.Agent.Get_WMM().Get_Map();
		}
		return this.Map_Known;
	}

//	public ArrayList<TOption> get_Options() {
//		return this.Options;
//	}

//	public ArrayList<TOption> get_Surviving_Options() {
//		return this.Surviving_Options;
//	}

	public ArrayList<TBelief_Base> Get_Beliefs() {
		this.Beliefs = this.Agent.Get_WMM().Get_Beliefs();
		return this.Beliefs;
	}
	
	public ArrayList<TBelief_Base> Get_Beliefs_from_WMM() {
		return this.Agent.Get_WMM().Get_Beliefs();
	}
	
	public ArrayList<TGreen_Goal> Get_Green_Goals() {
		
		return this.Green_Goals;
	}

	public ArrayList<TQuality_Goal> Get_Quality_Goals() {
		
		return this.Quality_Goals;
	}	
	
	public TRegion Get_Inhibition_Regions() {
//		return this.Inhibition_Regions;
		return this.Agent.Get_WMM().Get_Inhibition_Regions();
	}

	public HashMap<TAttentional_Goal, ArrayList<TBelief_Base>> Get_List_Beliefs() {
		return this.Map_Attentional_Goal_and_Beliefs;
	}

//	public ArrayList<TDesire> get_Desires() {
//		return this.Desires;
//	}
	
	/***
	 * Get all predicates in Arraylist Predicates and in HashMap Map_Predicates
	 * 
	 * @param predicates
	 */
	public void Set_Predicates(ArrayList<TPredicate> predicates)
	{
		this.Predicates = predicates;
		if (this.Predicates == null)
		{
			this.Predicates = new ArrayList<TPredicate>();
		}
		for(TPredicate Predicate: predicates)
		{
			this.Map_Predicates.put(Predicate.get_Name(), Predicate);
		}
		this.Predicates_Number = this.Predicates.size(); 
	}
	
	public ArrayList<TPredicate> Get_Predicates() {
		return this.Predicates;
	}
	
	
	public void Set_Beliefs(ArrayList<TBelief_Base> beliefs)
	{
		this.Beliefs = beliefs;
		for(TBelief_Base belief: beliefs)
		{
			this.Belief_List.Add_Belief(belief);
			this.Map_Beliefs.put(belief.get_Name(), belief);
			TPredicate Predicato = null;
			Predicato = this.Map_Predicates.get(belief.get_Predicate_name());
			belief.set_Predicate(Predicato);
		}
	}
	
//	public void set_Epistemic_Goals(ArrayList<TEpistemic_Goal> Epistemic_Goals)
//	{
//		//To re develop
//		int epistemic_goals_nuber = Epistemic_Goals.size(); 
//		this.Goals.addAll(Epistemic_Goals);
//		//this.Goals_Number += epistemic_goals_nuber;
//		this.Goals_Number = this.Goals.size();
//
//		
//		for(TEpistemic_Goal Epistemic_Goal: Epistemic_Goals)
//		{
//			TBelief_Base belief = this.Map_Beliefs.get(Epistemic_Goal.get_Belief_Name());
//			Epistemic_Goal.set_Belief(belief);
//			//Now, I normalize green and quality goals name woth correct goals.
//			//So, I must to insert green goals and later quality goals
//			// List Green and Quality Goals must to created before Attentional_Goal
//			
//			if (Epistemic_Goal.get_List_Green_Goal() == null)
//			{
//				ArrayList<TGreen_Goal> List_Green_Goal = new ArrayList<TGreen_Goal>();
//				Epistemic_Goal.set_List_Green_Goal(List_Green_Goal);
//			}
//			
//			for(String Green_Goal_Name: Epistemic_Goal.get_List_Green_Goal_Name())
//			{
//				TGreen_Goal Green_Goal = this.Map_Green_Goals.get(Green_Goal_Name);
//				Epistemic_Goal.get_List_Green_Goal().add(Green_Goal);
//			}
//			
//			if (Epistemic_Goal.get_List_Quality_Goal() == null)
//			{
//				ArrayList<TQuality_Goal> List_Quality_Goal = new ArrayList<TQuality_Goal>();
//				Epistemic_Goal.set_List_Quality_Goal(List_Quality_Goal);
//			}
//			for(String Quality_Goal_Name: Epistemic_Goal.get_List_Quality_Goal_Name())
//			{
//				TQuality_Goal Quality_Goal = this.Map_Quality_Goals.get(Quality_Goal_Name);
//				Epistemic_Goal.get_List_Quality_Goal().add(Quality_Goal);
//			}
////			this.Belief_List.Add_Belief(belief);
////			TPredicate Predicato = null;
////			Predicato = this.Map_Predicates.get(belief.get_Predicate_name());
////			belief.set_Predicate(Predicato);
//		}
//	}
	
//	public void set_Functional_Goals(ArrayList<TFunctional_Goal> Functional_Goals)
//	{
//		int functional_goals_nuber = Functional_Goals.size(); 
//		this.Goals.addAll(Functional_Goals);
//		this.Goals_Number += functional_goals_nuber; 
//		for(TFunctional_Goal Functional_Goal: Functional_Goals)
//		{
//			//Insert Final_State, it is a TBelief_Base type
//			TBelief_Base Final_State = this.Map_Beliefs.get(Functional_Goal.get_Final_State_Name());
//			Functional_Goal.set_Final_State(Final_State);
//						
//			//Insert Trigger_Condition, it is a TPredicate type
//			TPredicate Trigger_Condition = this.Map_Predicates.get(Functional_Goal.get_Trigger_Condition_Name());
//			Functional_Goal.set_Trigger_Condition(Trigger_Condition);
//			
//			//Now, I normalize green and quality goals name woth correct goals.
//			//So, I must to insert green goals and later quality goals
//			// List Green and Quality Goals must to created before Attentional_Goal
//			if (Functional_Goal.get_List_Green_Goal() == null)
//			{
//				ArrayList<TGreen_Goal> List_Green_Goal = new ArrayList<TGreen_Goal>();
//				Functional_Goal.set_List_Green_Goal(List_Green_Goal);
//			}
//			for(String Green_Goal_Name: Functional_Goal.get_List_Green_Goal_Name())
//			{
//				TGreen_Goal Green_Goal = null;
//				Green_Goal = this.Map_Green_Goals.get(Green_Goal_Name);
//
//				Functional_Goal.get_List_Green_Goal().add(Green_Goal);
//			}
//			
//			if (Functional_Goal.get_List_Quality_Goal() == null)
//			{
//				ArrayList<TQuality_Goal> List_Quality_Goal = new ArrayList<TQuality_Goal>();
//				Functional_Goal.set_List_Quality_Goal(List_Quality_Goal);
//			}
//			for(String Quality_Goal_Name: Functional_Goal.get_List_Quality_Goal_Name())
//			{
//				TQuality_Goal Quality_Goal = this.Map_Quality_Goals.get(Quality_Goal_Name);
//				Functional_Goal.get_List_Quality_Goal().add(Quality_Goal);
//			}
//		}
//	}
	
	public void Set_Green_Goals(ArrayList<TGreen_Goal> Green_Goals)
	{

		for(TGreen_Goal Green_Goal: Green_Goals)
		{
			TPredicate constraint =null;
			constraint = this.Map_Predicates.get(Green_Goal.get_Constraint_Name());
			Green_Goal.set_Constraint(constraint);
			
			this.Map_Green_Goals.put(Green_Goal.get_Name(), Green_Goal);
			this.Green_Goals.add(Green_Goal);
			
		}
		
	}
	
	public void Set_Quality_Goals(ArrayList<TQuality_Goal> Quality_Goals)
	{
		for(TQuality_Goal Quality_Goal: Quality_Goals)
		{
			TPredicate constraint =null;
			constraint = this.Map_Predicates.get(Quality_Goal.get_Constraint_Name());
			Quality_Goal.set_Constraint(constraint);
			
			
			
			this.Map_Quality_Goals.put(Quality_Goal.get_Name(), Quality_Goal);
			this.Quality_Goals.add(Quality_Goal);
		}
	}
	
//	public TEpistemic_Goal Create_Epistemic_Goal() 
//	{
//		//Epistemic_Goal = new TEpistemic_Goal(Stimulus, Saliency, Reward, Relax_Preference,
//		//null, null);
//	}
	
	public HashMap<String, ArrayList<TBelief_Base>> Get_Map_Attentional_Goal_and_Beliefs()
	{
		return this.Agent.Get_WMM().Get_Map_Attentional_Goals_and_Beliefs();
	}
	
	public ArrayList<TBelief_Base> Get_Beliefs_From_Type_Belief(TType_Beliefs Type_Beliefs)
	{
		ArrayList<TBelief_Base> Beliefs = new ArrayList<TBelief_Base>();
		for(TBelief_Base Belief: this.Agent.Get_WMM().Get_Beliefs())
		{
			if(Belief.get_Type_Belief() == Type_Beliefs)
			{
				Beliefs.add(Belief);
			}
		}
	
		return Beliefs;
	}
	
	public TBelief_Base Get_Belief_From_Type_Belief_and_Subject(TType_Beliefs Type_Beliefs, Object Subject)
	{
		TBelief_Base Belief = null;
		for(TBelief_Base Temp_Belief: this.Agent.Get_WMM().Get_Beliefs())
		{
			if(Temp_Belief.get_Type_Belief() == Type_Beliefs && Temp_Belief.get_Predicate().get_Subject() == Subject)
			{
				Belief = Temp_Belief;
			}
		}
	
		return Belief;
	}
	
	public TBelief_Base Get_Belief_From_Type_Belief_and_Object_complement(TType_Beliefs Type_Beliefs, 
																Object Object_complement)
	{
		TBelief_Base Belief = null;
		for(TBelief_Base Temp_Belief: this.Agent.Get_WMM().Get_Beliefs())
		{
			if(Temp_Belief.get_Type_Belief() == Type_Beliefs && 
					Temp_Belief.get_Predicate().get_Object_Complement() == Object_complement)
			{
				Belief = Temp_Belief;
			}
		}
	
		return Belief;
	}
	
	public void Update_Belief_by_Stimulus(TSalient_Belief Salient_Belief)
	{
//		Game.Scenario_Number++;
		this.Get_Agent().Get_GW().Print_Data(0, 0);
		
		if(this.Beliefs.contains(Salient_Belief) == false)
		{
			this.Beliefs.add(Salient_Belief);
			this.Updated_Beliefs = true;
			this.Updated_Beliefs_for_print = true;
		}
		
		
		//the following lines of code are to use the iterator way in the Start Agent method
		this.Updated_Beliefs = true;
		this.Temp_Salient_Belief = Salient_Belief;
		//the previous lines of code are to use the iterator way in the Start Agent method
		
		
		this.Get_Agent().Get_GW().Print_Data(1, 0);
//		this.Agent.get_E_Switching_Function().AM_Exogenous_Module(Salient_Belief);
//		this.Agent.get_E_Switching_Function().AM_Exogenous_Module();
	}
	
	public TEpistemic_Goal Stimulus_to_Goal(TSalient_Belief Salient_Belief)
	{
		
		//TODO da dove prendo questi valori???
		double reward = 0;			//da dove lo prendo?
		double relax_preference = 0;//da dove lo prendo?
		//List green goals?			//da dove lo prendo?
		//List quality goals?		//da dove lo prendo?
		
		TEpistemic_Goal Epistemic_Goal = this.Agent.Get_WMM().Stimulus_to_Goal(Salient_Belief);
		
		//The correct name for the epistemic goal is setted below, in Add_Attentional_Goal.
//		Epistemic_Goal =new TEpistemic_Goal("", Salient_Belief, 
//				Salient_Belief.get_Saliency(), reward, relax_preference, null, null);
//		
//		this.Agent.Get_WMM().crea
//		
//		this.Agent.get_GW().Add_Attentional_Goal(Epistemic_Goal);
		
		/***
		 * FINE TODO
		 */
		
		return Epistemic_Goal;
	}
	
	/***
	 * This method is only for project purpose 
	 * @return
	 */
	public TRegion Compute_All_Regions()
	{
		Environment Map = this.Agent.Get_WMM().Get_Map();
		TRegion All_Regions = new TRegion();
		for(Route route: Map.All_Routes)
		{
			All_Regions.Routes.add(route);
		}
		
		int i =0;
		for(i=0; i< Map.Get_Routes_Number(); i++)
		{
			All_Regions.Integer_Routes.add(i);
		}

		//I insert all Stations in Inhibited_Regions
		for(i=0; i < Station.values().length; i++)
		{
			All_Regions.Destinations.add(Station.values()[i]);
		}
		return All_Regions;
	}
	
	public void Print_Data(int Start, int Synthesis)
	{
		boolean changed = false;
		final StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		String Function_Name = ste[2].getMethodName();
		String Class_Name = ste[2].getClassName();
		int index=Class_Name.indexOf(".");
		Class_Name=Class_Name.substring(index+1);
		Game.PrintLn();
		Game.Print("************  Method:  "+Function_Name+" *********: "+Class_Name);
		Game.Print_Scenario();
		switch(Start)
		{
		case 0://Start of the method
			Game.Print("Changed data at the BEGINNING of the method:");
			break;
		case 1://End of the method
			Game.Print("Changed data at the END of the method:");
			break;
		case 2://Continue in work flow of the method
			Game.Print("data changed in continuing the method workflow:");
			break;
		}
//		if(Start==0)
//		{
//			
//		}
//		else
//		{
//			
//		}
		Game.PrintLn();

		//BELIEFS
		if(this.Updated_Beliefs_for_print)
		{
			this.Updated_Beliefs_for_print = false;
			changed = true;
			
			Game.Print("Beliefs in GW:");
			Game.Print("N. UnInhibited Beliefs in GW: "+this.UnInhibited_Beliefs.size());
			if (Synthesis==1)
			{
				Game.Print("UnInhibited Belief Types:");
				for(TBelief_Base Belief: this.UnInhibited_Beliefs)
				{
					Game.Print(Belief.get_Type_Belief());
				}
			}
			Game.PrintLn();
			Game.Print("Beliefs in LONG MEMORY:");
			Game.Print("N. Belief in LONG MEMORY: "+this.Agent.Get_WMM().Get_Beliefs().size());
			Game.Print("N. Inhibited Beliefs in LONG MEMORY: "+this.Agent.Get_WMM().Get_Inhibited_Beliefs().size());
			if (Synthesis==1)
			{
				Game.Print("Inhibited Beliefs Types:");
				for(TBelief_Base Belief: this.UnInhibited_Beliefs)
				{
					Game.Print(Belief.get_Type_Belief());
				}
			}
			Game.PrintLn();
		}
		
		//	GOALS
		if(this.Updated_Goals_for_print)
		{
			this.Updated_Goals_for_print = false;
			changed = true;
			
			//GW
			Game.Print("Goals in GW");
			Game.Print("N. Goals in GW: "+this.Get_Goals().size());
			Game.Print("N. UnInhibited Goals in GW: "+this.UnInhibited_Goals.size());
			if (Synthesis==1)
			{
				Game.Print("Goal Names:");
				for(TAttentional_Goal Goal: this.UnInhibited_Goals)
				{
					Game.Print(Goal.get_Name()+ ": "+Goal.getClass());
				}
			}
			Game.PrintLn();
			//LONG MEMORY
			Game.Print("Goals in LONG MEMORY");
			Game.Print("N. Goals in LONG MEMORY: "+this.Agent.Get_WMM().Get_Attentional_Goals().size());
			Game.Print("N. Inhibited Goals in LONG MEMORY: "+this.Agent.Get_WMM().Get_Inhibited_Goals().size());
			if (Synthesis==1)
			{
				Game.Print("Inhibited Goal Names and Types:");
				for(TAttentional_Goal Goal: this.UnInhibited_Goals)
				{
					Game.Print(Goal.get_Name()+ ": "+Goal.getClass());
				}
			}
			Game.PrintLn();
		}
		
		// DESIRES
		if(this.Updated_Desires_for_print)
		{
			changed = true;
			
			Game.Print("Desires in GW");
			this.Updated_Desires_for_print = false;
			
			Game.Print("N. Desires in GW: "+this.Desires.size());
			int i=0;
			for(TDesire Desire: this.Desires)
			{
				i++;
				Game.Print("Desire N. -"+i+" - Named: "+Desire.Get_Name());
			}
			Game.PrintLn();
		}
			
		//
		if(this.Updated_Desires_with_options_for_print)
		{
			changed = true;
			
			Game.Print("Desires with Options in GW");
			this.Updated_Desires_with_options_for_print = false;
			
			int i=0;
			for(TDesire Desire: this.Desires)
			{
				i++;
				Game.Print("Desire N. -"+i+" - Named: "+Desire.Get_Name()+" has N. Options: "+Desire.get_Option_List().size());
			}
			Game.PrintLn();
		}
			
		//INTENTIONS
		if(this.Updated_Intentions_for_print)
		{
			changed = true;
			
			Game.Print("Intentions in GW");
			this.Updated_Intentions_for_print = false;
			
			Game.Print("N. Intentions in GW: "+this.Intentions.size());
			Game.PrintLn();
		}
		
		if(this.Updated_Regions_for_print)
		{
			changed = true;
			
			Game.Print("Regions in GW");
			this.Updated_Regions_for_print = false;
			
			//GW
			Game.Print("N. UnInhibited Routes Region in GW: "+this.UnInhibition_Regions.Routes.size()/2);
			Game.Print("N. UnInhibited Station Region in GW: "+this.UnInhibition_Regions.Destinations.size());

			Game.PrintLn();
			//LONG MEMORY
			Game.Print("Regions in LONG MEMORY");
			Game.Print("N. All Route Region in LONG MEMORY: "+this.Agent.Get_WMM().Get_Regions().Routes.size());
			Game.Print("N. Inhibited Route Region in LONG MEMORY: "+this.Agent.Get_WMM().Get_Inhibition_Regions().Routes.size());
			
			Game.Print("N. Station in LONG MEMORY: "+this.Agent.Get_WMM().Get_Regions().Destinations.size());
			Game.Print("N. Inhibited Station Region in LONG MEMORY: "+this.Agent.Get_WMM().Get_Inhibition_Regions().Destinations.size());
			Game.PrintLn();
		}
		if(!changed)
		{
			Game.Print("No data is changed.");
		}
		if(Start==0)
		{
			Game.Print("*************** Work flow in Method ***************");
		}
	}
	

	
	public Integer Get_Desire_Number()
	{
		return this.Desire_Number;
	}
	
	public void Inc_Desire_Number()
	{
		this.Desire_Number++;
	}
	
	public void Add_Desire_Number(int number)
	{
		this.Desire_Number += number;
	}
	
	public void Inc_Intention_Number()
	{
		this.Intention_Number++;
	}
	
	public void Broadcast()
	{
		/* 	Predicate,
			Beliefs,				***
			Desires,				***
			Desires_with_Option,	***
			Intentions,
			Stimuli
		 */
		if (this.Updated_Beliefs)
		{
			this.Updated_Beliefs = false;
			this.Updated_Beliefs_for_print = true;
			
			ArrayList<Object> Beliefs = this.Update_Contracts.get(TType_Update_Contract.Beliefs);
			if(Beliefs.size()>0)
			{
				for(Object who: Beliefs)
				{
					if(who instanceof TE_Switching_Function)
					{
						this.Agent.Get_E_Switching_Function().Updated_Beliefs();
					}
					if(who instanceof TReasoner_Function)
					{
						this.Agent.Get_Reasoner().Updated_Beliefs();
					}
					if(who instanceof TE_Inhibition_function)
					{
						this.Agent.Get_E_Inhibition_function().Updated_Beliefs();
					}
					if(who instanceof TResource_Allocation )
					{
						this.Agent.Get_E_Resource_A().Updated_Beliefs();
					}
				}
			}
		}
		
		if (this.Updated_Desires)
		{
			this.Updated_Desires = false;
			this.Updated_Desires_for_print = true;
			
			ArrayList<Object> Desires = this.Update_Contracts.get(TType_Update_Contract.Desires);
			if(Desires.size()>0)
			{
				for(Object who: Desires)
				{
					if(who instanceof TReasoner_Function)
					{
						this.Agent.Get_Reasoner().Updated_Desires();;
					}
				}
			}
		}
		if (this.Updated_Desires_with_options)
		{
			this.Updated_Desires_with_options = false;
			this.Updated_Desires_with_options_for_print = true;
			
			ArrayList<Object> Desires_with_Option = this.Update_Contracts.get(TType_Update_Contract.Desires_with_Option);
			if(Desires_with_Option.size()>0)
			{
				for(Object who: Desires_with_Option)
				{
					if(who instanceof TReasoner_Function)
					{
						this.Agent.Get_Reasoner().Updated_Desires_with_Options();;
					}
				}
			}
		}
		if (this.Updated_Intentions)
		{
			this.Updated_Intentions = false;
			this.Updated_Intentions_for_print = true;
			
			
			ArrayList<Object> Intentions = this.Update_Contracts.get(TType_Update_Contract.Intentions);
			if(Intentions.size()>0)
			{
				for(Object who: Intentions)
				{
					if(who instanceof TReasoner_Function)
					{
						this.Agent.Get_Reasoner().Updated_Intentions();
					}
					if(who instanceof TResource_Allocation)
					{
						//this.Agent.get_RA().Updated_Intentions(null, Beliefs);
						this.Agent.Get_E_Resource_A().Updated_Intentions();
					}
				}
			}
		}
		
		
		
		
	}
	
	public TSalient_Belief Get_Temp_Salient_Belief()
	{
		return this.Temp_Salient_Belief;
	}
	
	public  boolean Get_Updated_Desires()
	{
		return this.Updated_Desires;
	}

	public boolean Get_Updated_Goals()
	{
		return this.Updated_Goals;
	}
	
	public boolean Get_Updated_Unhinibited_Goals()
	{
		return this.Updated_UnInhibited_Goals;
	}
	
	public boolean Get_Updated_Inibited_Goals()
	{
		return this.Updated_Inhibited_Goals;
	}
	
	public boolean Get_Updated_Beliefs()
	{
		return this.Updated_Beliefs;
	}
	public boolean Get_Updated_Unhinibited_Beliefs()
	{
		return this.Updated_UnInhibited_Beliefs;
	}
	public boolean Get_Updated_Inibited_Beliefs()
	{
		return this.Updated_Inhibited_Beliefs;
	}
	
	public boolean Get_Updated_Regions()
	{
		return this.Updated_Regions;
	}
	public boolean Get_Updated_Unhinibited_Regions()
	{
		return this.Updated_UnInhibited_Regions;
	}
	public boolean Get_Updated_Inibited_Regions()
	{
		return this.Updated_Inhibited_Regions;
	}
	
	public boolean Get_Update_Intentions()
	{
		return this.Updated_Intentions;
	}
	
	public ArrayList<TBelief_Base> Get_UnInhibited_Beliefs()
	{
		if(UnInhibited_Beliefs.size() == 0)
		{
			this.UnInhibited_Beliefs.addAll(this.Get_Beliefs());
		}
		return this.UnInhibited_Beliefs;
	}
	
	public void Update_Desire_with_Options(ArrayList<TDesire> desires)
	{
		ArrayList<TDesire> Updated_Desires = new ArrayList<TDesire>();
		Updated_Desires.addAll(desires);
		this.Desires.clear();
		this.Desires.addAll(Updated_Desires);
		this.Updated_Desires = true;
		this.Updated_Desires_with_options = true;
		this.Updated_Desires_for_print = true;
		this.Updated_Desires_with_options_for_print = true;
	}
	public void Empty_Inhibition_Region()
	{
		//When the agent is not focused, Resions in Inhibition_Regions and UnInhibition_Regions are empty
		this.Inhibition_Regions.Destinations.clear();
		this.Inhibition_Regions.Routes.clear();
		this.Inhibition_Regions.Integer_Routes.clear();
		
		this.UnInhibition_Regions.Destinations.clear();
		this.UnInhibition_Regions.Routes.clear();
		this.UnInhibition_Regions.Integer_Routes.clear();
		
		this.Agent.Get_WMM().Set_Inhibition_Regions(Inhibition_Regions);
	}
	
	public void Empty_Inhibited_Beliefs()
	{
		//When the agent is not focused, Inhibited_Beliefs and UnInhibited_Beliefs are empty
		this.Inhibited_Beliefs.clear();
		this.UnInhibited_Beliefs.clear();
		
		this.Agent.Get_WMM().Set_Inhibited_Beliefs(Inhibited_Beliefs);
	}
	
	public void Empty_Inhibited_Goals()
	{
		//When the agent is not focused, Inhibited_Goals and UnInhibiteUnInhibited_Goalsd_Beliefs are empty
		this.Inhibited_Goals.clear();
		this.UnInhibited_Goals.clear();
		
		this.Agent.Get_WMM().Set_Inhibited_Goals(Inhibited_Goals);
	}

	private void Set_Inhibition_Regions(TRegion inhibition_Regions)
	{
		this.Inhibition_Regions.Destinations.clear();
		this.Inhibition_Regions.Routes.clear();
		this.Inhibition_Regions.Integer_Routes.clear();
		
		this.Inhibition_Regions.Destinations.addAll(inhibition_Regions.Destinations);
		this.Inhibition_Regions.Routes.addAll(inhibition_Regions.Routes);
		this.Inhibition_Regions.Integer_Routes.addAll(inhibition_Regions.Integer_Routes);

	}

	public Boolean Delete_Intention(TIntention intention)					
	{
		TDesire Desire = intention.get_Desire();
		
		this.Delete_Desire(Desire);
		//intention.get_Desire().get_Attentional_Goal().Clear();
		//intention.get_Desire().get_Option_List().clear();
		intention.set_Desire(null);
		this.Updated_Intentions = true;
		return this.Intentions.remove(intention);
	}
	
	public void Insert_for_Update_Contract(TType_Update_Contract Type_Update_Contract, Object Who )
	{
		ArrayList<Object> Objects;
		Objects = this.Update_Contracts.get(Type_Update_Contract);
		if(Objects == null)
		{
			Objects = new ArrayList<Object>();
		}
		if(Objects.contains(Who) == false)
		{
			Objects.add(Who);
		}
		this.Update_Contracts.put(Type_Update_Contract, Objects);
		
	}
	
	public void Changed_Beliefs()
	{
		this.Updated_Beliefs = true;
	}
	
	public void Changed_Goals()
	{
		this.Updated_Goals = true;
	}
}
