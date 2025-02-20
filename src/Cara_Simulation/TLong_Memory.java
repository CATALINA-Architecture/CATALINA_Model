package Cara_Simulation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class TLong_Memory {
	
	TWorking_Memory_Maintenance WMM;
	private Environment Map_Known;
	
	private Integer Inc_Goals_Number = 0;
	private Integer Inc_Functional_Goals_Number = 0;
	private Integer Inc_Epistemic_Goals_Number = 0;
	private Integer Inc_Beliefs_Number = 0;
	private Integer Inc_Predicates_Number = 0;
	private Integer Inc_Green_Goals_Number = 0;
	private Integer Inc_Quality_Goals_Number = 0;
	//private TAgent Agent;
	//private Environment3 Map_Known;
	
	//Goal Properties
	private ArrayList<TAttentional_Goal> Goals;
	private ArrayList<TAttentional_Goal> Inhibited_Goals;
	private HashMap<String, TAttentional_Goal> Map_Goals;
	
	//Green and Quality Goals
	private ArrayList<TGreen_Goal> Green_Goals;
	private ArrayList<TQuality_Goal> Quality_Goals;
	private HashMap<String, TGreen_Goal> Map_Green_Goals;
	private HashMap<String, TQuality_Goal> Map_Quality_Goals;
	
	//Option Properties
	private ArrayList<TOption> Options;
	private ArrayList<TOption> Surviving_Options;
	private ArrayList<TIntention> Selected_Intentions;
	
	//Predicate Properties
	private ArrayList<TPredicate> Predicates;
	private HashMap<String, TPredicate> Map_Predicates;
	private HashMap<String, ArrayList<TPredicate>> Map_Beliefs_and_Predicates;
	private HashMap<String, ArrayList<TPredicate>> Map_Green_Goals_and_Predicates;
	private HashMap<String, ArrayList<TPredicate>> Map_Quality_Goals_and_Predicates;
	private HashMap<String, ArrayList<TPredicate>> Map_Attentional_Goals_and_Predicates;
	//private HashMap<Integer, ArrayList<Trpedicate>> Map__Beliefs_and_Predicates;
	
	//Belief Properties
	private ArrayList<TBelief_Base> Beliefs;
	private ArrayList<TBelief_Base> Inhibited_Beliefs;
	private HashMap<String, TBelief_Base> Map_Beliefs;
	private TRegion Regions;
	private TRegion Inhibition_Regions;
	private HashMap<String, ArrayList<TBelief_Base>> Map_Attentional_Goals_and_Beliefs;
	private HashMap<String, ArrayList<Object>> Map_Attentional_Goals_and_Regions;
	public TBelief_List Belief_List;
	
	//Desires
	private ArrayList<TDesire> Desires;
	
	public TLong_Memory(TWorking_Memory_Maintenance wmm)
	{
		this.WMM = wmm;
		
		this.Inc_Goals_Number = 0;
		this.Inc_Beliefs_Number = 0;
		this.Inc_Predicates_Number = 0;
		this.Inc_Functional_Goals_Number = 0;
		this.Inc_Epistemic_Goals_Number = 0;
		this.Inc_Green_Goals_Number = 0;
		this.Inc_Quality_Goals_Number = 0;
		
		//Goal Properties
		this.Goals = new ArrayList<TAttentional_Goal>();
		this.Inhibited_Goals = new ArrayList<TAttentional_Goal>();
		this.Map_Goals = new HashMap<String, TAttentional_Goal>();

		//Green and Quality Goals
		this.Green_Goals = new ArrayList<TGreen_Goal>();
		this.Quality_Goals = new ArrayList<TQuality_Goal>();
		this.Map_Green_Goals = new HashMap<String, TGreen_Goal>();
		this.Map_Quality_Goals = new HashMap<String, TQuality_Goal>();
		
		//Option Properties
		this.Options = new ArrayList<TOption>();
		this.Surviving_Options = new ArrayList<TOption>();
		this.Selected_Intentions = new ArrayList<TIntention>();
		
		//Predicate Properties
		this.Predicates = new ArrayList<TPredicate>();
		this.Map_Predicates = new HashMap<String, TPredicate>();
		
		this.Map_Beliefs_and_Predicates = new HashMap<String, ArrayList<TPredicate>>();
		this.Map_Green_Goals_and_Predicates = new HashMap<String, ArrayList<TPredicate>>();
		this.Map_Quality_Goals_and_Predicates = new HashMap<String, ArrayList<TPredicate>>();
		this.Map_Attentional_Goals_and_Predicates = new HashMap<String, ArrayList<TPredicate>>();
		
		
		
		
		//Belief Properties
		this.Beliefs = new ArrayList<TBelief_Base>();
		this.Inhibited_Beliefs = new ArrayList<TBelief_Base>();
		this.Map_Beliefs = new HashMap<String, TBelief_Base>();
		this.Regions = new TRegion();
		this.Inhibition_Regions = new TRegion();
		this.Map_Attentional_Goals_and_Beliefs = new HashMap<String, ArrayList<TBelief_Base>>();
		this.Map_Attentional_Goals_and_Regions = new HashMap<String, ArrayList<Object>>();
		this.Belief_List = new TBelief_List();
		
		//Desires
		this.Desires = new ArrayList<TDesire>();
	}
	
	///
	///
	/// PREDICATES
	///
	///
	
	public Integer Get_Inc_Predicates_Number()
	{
		return this.Inc_Predicates_Number;
	}
	protected void Add_Inc_Predicates_Number(Integer value)
	{
		this.Inc_Predicates_Number += value;
	}
	public Integer Get_Predicates_Number()
	{
		return this.Predicates.size();
	}	
	
	public HashMap<String, TPredicate> Get_Map_Predicates()
	{
		return this.Map_Predicates;
	}
	
	public ArrayList<TPredicate> Get_Predicates()
	{
		return this.Predicates;
	}
	
	public void AddAll_Predicates(ArrayList<TPredicate> predicates)
	{
		for(TPredicate Predicate: predicates)
		{
			this.Add_Predicate(Predicate);
		}
	}
	
	public void Add_Predicate(TPredicate predicate)
	{
//		this.Predicates.clear();
		this.Predicates.add(predicate);
		this.Inc_Predicates_Number++; 
	}
	
	///
	///
	/// BELIEFS
	///
	///
	public Integer Get_Inc_Beliefs_Number()
	{
		return this.Inc_Beliefs_Number;
	}
	protected void Add_Inc_Beliefs_Number(Integer value)
	{
		this.Inc_Beliefs_Number += value;
	}
	public Integer Get_Beliefs_Number()
	{
		return this.Beliefs.size();
	}
	
	public ArrayList<TBelief_Base> Get_Beliefs()
	{
		return this.Beliefs;
	}
	
//	public void Set_Beliefs(ArrayList<TBelief_Base> beliefs)
//	{
//		this.Beliefs.clear();
//		this.Beliefs.addAll(beliefs);
//		this.Beliefs_Number = this.Beliefs.size();
//	}
	
	public void AddAll_Beliefs(ArrayList<TBelief_Base> beliefs)
	{
		for(TBelief_Base Belief: beliefs)
		{
			this.Add_Belief(Belief);
		}
	}
	
	public void Add_Belief(TBelief_Base belief)
	{
		this.Beliefs.add(belief);
		this.Inc_Beliefs_Number++; 
	}
	
	public HashMap<String, TBelief_Base> Get_Map_Beliefs()
	{
		return this.Map_Beliefs;
	}
	
	///
	///
	/// GREEN GOALS
	///
	///
	public Integer Get_Inc_Green_Goals_Number()
	{
		return this.Inc_Green_Goals_Number;
	}
	public void Add_Inc_Green_Goals_Number(Integer value)
	{
		this.Inc_Green_Goals_Number += value;
	}
	public Integer Get_Green_Goals_Number()
	{
		return this.Green_Goals.size();
	}
	
	public ArrayList<TGreen_Goal> Get_Greens_Goals()
	{
	return this.Green_Goals;
	}
	
	public HashMap<String, TGreen_Goal> Get_Map_Green_Goals()
	{
	return this.Map_Green_Goals;
	}
	
	public void AddAll_Green_Goals(ArrayList<TGreen_Goal> green_Goals)
	{
		for(TGreen_Goal Green_Goal: green_Goals)
		{
			this.Add_Green_Goal(Green_Goal);
		}
	}
	
	public void Add_Green_Goal(TGreen_Goal Green_Goal)
	{
		this.Green_Goals.add(Green_Goal);
		this.Inc_Green_Goals_Number++; 
	}
	
	///
	///
	/// QUALITY GOALS
	///
	///
	public Integer Get_Inc_Quality_Goals_Number()
	{
		return this.Inc_Quality_Goals_Number;
	}
	protected void Add_Inc_Quality_Goals_Number(Integer value)
	{
		this.Inc_Quality_Goals_Number += value;
	}
	public Integer Get_Quality_Goals_Number()
	{
		return this.Quality_Goals.size();
	}
	
	public ArrayList<TQuality_Goal> Get_Quality_Goals()
	{
	return this.Quality_Goals;
	}
	
	public HashMap<String, TQuality_Goal> Get_Map_Quality_Goals()
	{
	return this.Map_Quality_Goals;
	}

	public void AddAll_Quality_Goals(ArrayList<TQuality_Goal> quality_Goals)
	{
		for(TQuality_Goal Quality_Goal: quality_Goals)
		{
			this.Add_Quality_Goal(Quality_Goal);
		}
	}
	
	public void Add_Quality_Goal(TQuality_Goal Quality_Goal)
	{
		this.Quality_Goals.add(Quality_Goal);
		this.Inc_Quality_Goals_Number++; 
	}
	
	///
	///
	/// ATTENTIONAL GOALS
	///
	///	
	
	public Integer Get_Inc_Goals_Number()
	{
		return this.Inc_Goals_Number;
	}
	protected void Add_Inc_Goals_Number(Integer value)
	{
		this.Inc_Goals_Number += value;
	}
	public Integer Get_Goals_Number()
	{
		return this.Goals.size();
	}
	
	public ArrayList<TAttentional_Goal> Get_Goals()
	{
		return this.Goals;
	}
	
	public ArrayList<TAttentional_Goal> Get_Inhibited_Goals()
	{
		return this.Inhibited_Goals;
	}
	
	
	
	public void AddAll_Attentional_Goals(ArrayList<TAttentional_Goal> attentional_Goals)
	{
		for(TAttentional_Goal Attentional_Goal: attentional_Goals)
		{
			this.Add_Attentional_Goal(Attentional_Goal);
		}
	}
	
	public void Add_Attentional_Goal(TAttentional_Goal attentional_Goal)
	{
		this.Goals.add(attentional_Goal);
		this.Inc_Goals_Number++; 
		if(attentional_Goal instanceof TFunctional_Goal)
		{
			this.Inc_Functional_Goals_Number++;
		}
		else
		{
			this.Inc_Epistemic_Goals_Number++;
		}
	}
	
	///
	///
	/// FUNCTIONAL GOALS
	///
	///	
	
	public Integer Get_Inc_Functional_Goals_Number()
	{
		return this.Inc_Functional_Goals_Number;
	}
	protected void Add_Inc_Functional_Goals_Number(Integer value)
	{
		this.Inc_Functional_Goals_Number += value;
	}
	public Integer Get_Functional_Number()
	{
		Integer result = 0;
		for(TAttentional_Goal Goal: this.Goals)
		{
			if(Goal instanceof TFunctional_Goal)
				result++;
		}
		return result;	
	}	

	public void AddAll_Functional_Goals(ArrayList<TFunctional_Goal> functional_Goals)
	{
		for(TFunctional_Goal Functional_Goal: functional_Goals)
		{
			this.Add_Functional_Goal(Functional_Goal);
		}
	}
	
	public void Add_Functional_Goal(TFunctional_Goal functional_Goal)
	{
		//In this case, Functional and Epistemic Goals are stored in a list of Attentional Goals
		// called "Goals"
		this.Add_Attentional_Goal(functional_Goal);
	}
	
	///
	///
	/// EPISTEMIC GOALS
	///
	///	
	
	public Integer Get_Inc_Epistemic_Goals_Number()
	{
		return this.Inc_Epistemic_Goals_Number;
	}
	protected void Add_Inc_Epistemic_Goals_Number(Integer value)
	{
		this.Inc_Epistemic_Goals_Number += value;
	}
	public Integer Get_Epistemic_Number()
	{
		Integer result = 0;
		for(TAttentional_Goal Goal: this.Goals)
		{
			if(Goal instanceof TEpistemic_Goal)
				result++;
		}
		return result;	
	}
	
	public void AddAll_Epistemic_Goals(ArrayList<TEpistemic_Goal> epistemic_Goals)
	{
		for(TEpistemic_Goal Epistemic_Goal: epistemic_Goals)
		{
			this.Add_Epistemic_Goal(Epistemic_Goal);
		}
	}
	
	public void Add_Epistemic_Goal(TEpistemic_Goal epistemic_Goal)
	{
		//In this case, Functional and Epistemic Goals are stored in a list of Attentional Goals
		// called "Goals"
		this.Add_Attentional_Goal(epistemic_Goal);
	}
	
	public Environment Get_Map()
	{
		return this.Map_Known;
	}
	
	public void Set_Map(Environment map)
	{
		this.Map_Known = map;
	}
	
	public HashMap<String, ArrayList<TPredicate>> Get_Map_Beliefs_and_Predicates()
	{
		return this.Map_Beliefs_and_Predicates;
	}
	
	public HashMap<String, ArrayList<TPredicate>> Get_Map_Green_Goals_and_Predicates ()
	{
		return this.Map_Green_Goals_and_Predicates;
	}
	
	public HashMap<String, ArrayList<TPredicate>> Get_Map_Quality_Goals_and_Predicates()
	{
		return this.Map_Quality_Goals_and_Predicates;
	}
	
	public HashMap<String, ArrayList<TPredicate>> Get_Map_Attentional_Goals_and_Predicates()
	{
		return this.Map_Attentional_Goals_and_Predicates;
	}
	
	public HashMap<String, ArrayList<TBelief_Base>> Get_Map_Attentional_Goals_and_Beliefs()
	{
		return this.Map_Attentional_Goals_and_Beliefs;
	}
	
	public HashMap<String, ArrayList<Object>> Get_Map_Attentional_Goals_and_Regions()
	{
		return this.Map_Attentional_Goals_and_Regions;
	}
	
	public ArrayList<TDesire> Get_Desires()
	{
		return this.Desires;
	}
	
	public void Add_Desire(TDesire desire)
	{
		this.Desires.add(desire);
	}
	
	public void Add_Desire(ArrayList<TDesire> desires)
	{
		for(TDesire desire: desires)
		{
			this.Desires.add(desire);
		}
	}
	
	public TPredicate Create_Predicate(Object obj1, TType_Relationship relationship, Object obj2)
	{
		String Predicate_Name = "";
		int Inc_Predicates_Number = this.Inc_Predicates_Number + 1;
//		if (obj1 == null ||  obj1 == "")
//		{
//			Predicate_Name = "p" + Inc_Predicates_Number;
//		}
		Predicate_Name = "p" + Inc_Predicates_Number;
		TPredicate Predicate = new TPredicate(Predicate_Name, obj1, relationship, obj2);
		this.Predicates.add(Predicate);
		this.Map_Predicates.put(Predicate.get_Name(), Predicate);
		this.Inc_Predicates_Number = Inc_Predicates_Number; 
		return Predicate;

	}
	
	public TBelief_Base Create_Belief(TPredicate Predicate, Boolean truth, Object information_Source,
			LocalDateTime time_stamp, TType_Beliefs type_Belief)
	{

		int incremental_id = this.Inc_Beliefs_Number + 1;
		String Belief_Name = "b" + incremental_id;

		TBelief_Base Belief = new TBelief_Base(Belief_Name, Predicate, truth, information_Source, time_stamp,
												type_Belief);

		this.Beliefs.addLast(Belief);
		this.Inc_Beliefs_Number = incremental_id;
		
		this.Belief_List.Add_Belief(Belief);
		this.Map_Beliefs.put(Belief.get_Name(), Belief);
		
		return Belief;
	}
	
	public void Set_Beliefs_Number(int value)
	{
		this.Inc_Beliefs_Number = value;
	}
	
	public void Set_Inhibited_Beliefs(ArrayList<TBelief_Base> inhibited_Beliefs)
	{
		this.Inhibited_Beliefs.clear();
		this.Inhibited_Beliefs.addAll(inhibited_Beliefs);
	}
	
	public void Set_Inhibited_Goals(ArrayList<TAttentional_Goal> Inhibited_Goals)
	{
		this.Inhibited_Goals.clear();
		this.Inhibited_Goals.addAll(Inhibited_Goals);
	}
	
	public TRegion Set_Regions()
	{
		this.Regions.Destinations.clear();
		this.Regions.Routes.clear();
		this.Regions.Integer_Routes.clear();
		
//		Environment3 Map = this.Get_Map()
		for(Route route: this.Map_Known.All_Routes)
		{
			this.Regions.Routes.add(route);
		}
		
		int i =0;
		for(i=0; i< this.Map_Known.Get_Routes_Number(); i++)
		{
			this.Regions.Integer_Routes.add(i);
		}

		//I insert all Stations in Inhibited_Regions
		for(i=0; i < Station.values().length; i++)
		{
			this.Regions.Destinations.add(Station.values()[i]);
		}
		return this.Regions;
	}
	
	public void Set_Inhibition_Regions(TRegion inhibition_Regions)
	{
		this.Inhibition_Regions.Destinations.clear();
		this.Inhibition_Regions.Routes.clear();
		this.Inhibition_Regions.Integer_Routes.clear();
		
		this.Inhibition_Regions.Destinations.addAll(inhibition_Regions.Destinations);
		this.Inhibition_Regions.Routes.addAll(inhibition_Regions.Routes);
		this.Inhibition_Regions.Integer_Routes.addAll(inhibition_Regions.Integer_Routes);

	}
	
	public TRegion Get_Inhibition_Regions()
	{
		return this.Inhibition_Regions;
	}
	
	public TRegion Get_Regions()
	{
		return this.Regions;
	}
	
	public ArrayList<TBelief_Base> Get_Inhibited_Beliefs()
	{
		return this.Inhibited_Beliefs;
	}
	
	public void Delete_Goal(TAttentional_Goal Goal)
	{
		this.Goals.remove(Goal);
	}

	
	
}
