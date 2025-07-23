package Catalina_Simulation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class TLong_Memory {
	
	TExecutive_Working_Memory_Maintenance WMM;
	private Environment Map_Known;
	
	private Integer Inc_Attentional_Desires_Number = 0;
	private Integer Inc_Practical_Desires_Number = 0;
	private Integer Inc_Epistemic_Desires_Number = 0;
	private Integer Inc_Beliefs_Number = 0;
	private Integer Inc_Predicates_Number = 0;
	private Integer Inc_Green_Desires_Number = 0;
	private Integer Inc_Quality_Desires_Number = 0;
	
	//Goal Properties
	private ArrayList<TAttentional_Standing_Desire> Attentional_Desires;
	private ArrayList<TAttentional_Standing_Desire> Inhibited_Desires;
	private HashMap<String, TAttentional_Standing_Desire> Map_Desires;
	private ArrayList<TAttentional_Standing_Desire>  Attentional_Desires_Satisfied;
	
	//Green and Quality Goals
	private ArrayList<TGreen_Standing_Desire> Green_Desires;
	private ArrayList<TQuality_Standing_Desire> Quality_Desires;
	private HashMap<String, TGreen_Standing_Desire> Map_Green_Desires;
	private HashMap<String, TQuality_Standing_Desire> Map_Quality_Desires;
	
	//Option Properties
	private ArrayList<TOption> Options;
	private ArrayList<TOption> Surviving_Options;
	private ArrayList<TIntention> Selected_Intentions;
	
	//Predicate Properties
	private ArrayList<TPredicate> Predicates;
	private HashMap<String, TPredicate> Map_Predicates;
	private HashMap<String, ArrayList<TPredicate>> Map_Beliefs_and_Predicates;
	private HashMap<String, ArrayList<TPredicate>> Map_Green_Desires_and_Predicates;
	private HashMap<String, ArrayList<TPredicate>> Map_Quality_Desires_and_Predicates;
	private HashMap<String, ArrayList<TPredicate>> Map_Attentional_Desires_and_Predicates;
	//private HashMap<Integer, ArrayList<Trpedicate>> Map__Beliefs_and_Predicates;
	
	//Belief Properties
	private ArrayList<TBelief> Beliefs;
	private ArrayList<TBelief> Inhibited_Beliefs;
	private HashMap<String, TBelief> Map_Beliefs;
	private HashMap<String, ArrayList<TBelief>> Map_Attentional_Desires_and_Beliefs;
	
	
	//Regions Properties
	private TRegion Regions;
	private TRegion Inhibition_Regions;
	
	private HashMap<String, ArrayList<Object>> Map_Attentional_Desires_and_Regions;
	public TBelief_List Belief_List;
	
	//Desires
	private ArrayList<TActive_Desire> Desires;
	
	public TLong_Memory(TExecutive_Working_Memory_Maintenance wmm)
	{
		this.WMM = wmm;
		
		this.Inc_Attentional_Desires_Number = 0;
		this.Inc_Beliefs_Number = 0;
		this.Inc_Predicates_Number = 0;
		this.Inc_Practical_Desires_Number = 0;
		this.Inc_Epistemic_Desires_Number = 0;
		this.Inc_Green_Desires_Number = 0;
		this.Inc_Quality_Desires_Number = 0;
		
		//Goal Properties
		this.Attentional_Desires = new ArrayList<TAttentional_Standing_Desire>();
		this.Inhibited_Desires = new ArrayList<TAttentional_Standing_Desire>();
		this.Map_Desires = new HashMap<String, TAttentional_Standing_Desire>();
		
		this.Attentional_Desires_Satisfied = new ArrayList<TAttentional_Standing_Desire>();

		//Green and Quality Goals
		this.Green_Desires = new ArrayList<TGreen_Standing_Desire>();
		this.Quality_Desires = new ArrayList<TQuality_Standing_Desire>();
		this.Map_Green_Desires = new HashMap<String, TGreen_Standing_Desire>();
		this.Map_Quality_Desires = new HashMap<String, TQuality_Standing_Desire>();
		
		//Option Properties
		this.Options = new ArrayList<TOption>();
		this.Surviving_Options = new ArrayList<TOption>();
		this.Selected_Intentions = new ArrayList<TIntention>();
		
		//Predicate Properties
		this.Predicates = new ArrayList<TPredicate>();
		this.Map_Predicates = new HashMap<String, TPredicate>();
		
		this.Map_Beliefs_and_Predicates = new HashMap<String, ArrayList<TPredicate>>();
		this.Map_Green_Desires_and_Predicates = new HashMap<String, ArrayList<TPredicate>>();
		this.Map_Quality_Desires_and_Predicates = new HashMap<String, ArrayList<TPredicate>>();
		this.Map_Attentional_Desires_and_Predicates = new HashMap<String, ArrayList<TPredicate>>();
		
		
		
		
		//Belief Properties
		this.Beliefs = new ArrayList<TBelief>();
		this.Inhibited_Beliefs = new ArrayList<TBelief>();
		this.Map_Beliefs = new HashMap<String, TBelief>();
		this.Regions = new TRegion();
		this.Inhibition_Regions = new TRegion();
		this.Map_Attentional_Desires_and_Beliefs = new HashMap<String, ArrayList<TBelief>>();
		this.Map_Attentional_Desires_and_Regions = new HashMap<String, ArrayList<Object>>();
		this.Belief_List = new TBelief_List();
		
		//Desires
		this.Desires = new ArrayList<TActive_Desire>();
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
	
	public ArrayList<TBelief> Get_Beliefs()
	{
		return this.Beliefs;
	}
	
//	public void Set_Beliefs(ArrayList<TBelief_Base> beliefs)
//	{
//		this.Beliefs.clear();
//		this.Beliefs.addAll(beliefs);
//		this.Beliefs_Number = this.Beliefs.size();
//	}
	
	public void AddAll_Beliefs(ArrayList<TBelief> beliefs)
	{
		for(TBelief Belief: beliefs)
		{
			this.Add_Belief(Belief);
		}
	}
	
	public void Add_Belief(TBelief belief)
	{
		this.Beliefs.add(belief);
		this.Inc_Beliefs_Number++; 
	}
	
	public HashMap<String, TBelief> Get_Map_Beliefs()
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
		return this.Inc_Green_Desires_Number;
	}
	public void Add_Inc_Green_Goals_Number(Integer value)
	{
		this.Inc_Green_Desires_Number += value;
	}
	public Integer Get_Green_Goals_Number()
	{
		return this.Green_Desires.size();
	}
	
	public ArrayList<TGreen_Standing_Desire> Get_Greens_Goals()
	{
	return this.Green_Desires;
	}
	
	public HashMap<String, TGreen_Standing_Desire> Get_Map_Green_Goals()
	{
	return this.Map_Green_Desires;
	}
	
	public void AddAll_Green_Goals(ArrayList<TGreen_Standing_Desire> green_Goals)
	{
		for(TGreen_Standing_Desire Green_Goal: green_Goals)
		{
			this.Add_Green_Goal(Green_Goal);
		}
	}
	
	public void Add_Green_Goal(TGreen_Standing_Desire Green_Goal)
	{
		this.Green_Desires.add(Green_Goal);
		this.Inc_Green_Desires_Number++; 
	}
	
	///
	///
	/// QUALITY GOALS
	///
	///
	public Integer Get_Inc_Quality_Goals_Number()
	{
		return this.Inc_Quality_Desires_Number;
	}
	protected void Add_Inc_Quality_Goals_Number(Integer value)
	{
		this.Inc_Quality_Desires_Number += value;
	}
	public Integer Get_Quality_Goals_Number()
	{
		return this.Quality_Desires.size();
	}
	
	public ArrayList<TQuality_Standing_Desire> Get_Quality_Goals()
	{
	return this.Quality_Desires;
	}
	
	public HashMap<String, TQuality_Standing_Desire> Get_Map_Quality_Goals()
	{
	return this.Map_Quality_Desires;
	}

	public void AddAll_Quality_Goals(ArrayList<TQuality_Standing_Desire> quality_Goals)
	{
		for(TQuality_Standing_Desire Quality_Goal: quality_Goals)
		{
			this.Add_Quality_Goal(Quality_Goal);
		}
	}
	
	public void Add_Quality_Goal(TQuality_Standing_Desire Quality_Goal)
	{
		this.Quality_Desires.add(Quality_Goal);
		this.Inc_Quality_Desires_Number++; 
	}
	
	///
	///
	/// ATTENTIONAL GOALS
	///
	///	
	
	public Integer Get_Inc_Goals_Number()
	{
		return this.Inc_Attentional_Desires_Number;
	}
	protected void Add_Inc_Goals_Number(Integer value)
	{
		this.Inc_Attentional_Desires_Number += value;
	}
	public Integer Get_Goals_Number()
	{
		return this.Attentional_Desires.size();
	}
	
	public ArrayList<TAttentional_Standing_Desire> Get_Goals()
	{
		return this.Attentional_Desires;
	}
	
	public ArrayList<TAttentional_Standing_Desire> Get_Inhibited_Goals()
	{
		return this.Inhibited_Desires;
	}
	
	
	
	public void AddAll_Attentional_Goals(ArrayList<TAttentional_Standing_Desire> attentional_Goals)
	{
		for(TAttentional_Standing_Desire Attentional_Goal: attentional_Goals)
		{
			this.Add_Attentional_Goal(Attentional_Goal);
		}
	}
	
	public void Add_Attentional_Goal(TAttentional_Standing_Desire attentional_Goal)
	{
		this.Attentional_Desires.add(attentional_Goal);
		this.Inc_Attentional_Desires_Number++; 
		
		if(attentional_Goal instanceof TFunctional_Standing_Desire)
		{
			this.Inc_Practical_Desires_Number++;
			TFunctional_Standing_Desire Functional_Goal = (TFunctional_Standing_Desire) attentional_Goal;
			if(Functional_Goal.Get_Name().equals(""))
			{
				Functional_Goal.set_Name("Fg"+this.Inc_Practical_Desires_Number);
			}
		}
		else
		{
			this.Inc_Epistemic_Desires_Number++;
			TEpistemic_Standing_Desire pistemic_Goal = (TEpistemic_Standing_Desire) attentional_Goal;
			if(pistemic_Goal.Get_Name().equals(""))
			{
				pistemic_Goal.set_Name("Eg"+this.Inc_Epistemic_Desires_Number);
			}
		}
	}
	
	///
	///
	/// FUNCTIONAL GOALS
	///
	///	
	
	public Integer Get_Inc_Functional_Goals_Number()
	{
		return this.Inc_Practical_Desires_Number;
	}
	protected void Add_Inc_Functional_Goals_Number(Integer value)
	{
		this.Inc_Practical_Desires_Number += value;
	}
	public Integer Get_Functional_Number()
	{
		Integer result = 0;
		for(TAttentional_Standing_Desire Goal: this.Attentional_Desires)
		{
			if(Goal instanceof TFunctional_Standing_Desire)
				result++;
		}
		return result;	
	}	

	public void AddAll_Functional_Goals(ArrayList<TFunctional_Standing_Desire> functional_Goals)
	{
		for(TFunctional_Standing_Desire Functional_Goal: functional_Goals)
		{
			this.Add_Functional_Goal(Functional_Goal);
		}
	}
	
	public void Add_Functional_Goal(TFunctional_Standing_Desire functional_Goal)
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
		return this.Inc_Epistemic_Desires_Number;
	}
	protected void Add_Inc_Epistemic_Goals_Number(Integer value)
	{
		this.Inc_Epistemic_Desires_Number += value;
	}
	public Integer Get_Epistemic_Number()
	{
		Integer result = 0;
		for(TAttentional_Standing_Desire Goal: this.Attentional_Desires)
		{
			if(Goal instanceof TEpistemic_Standing_Desire)
				result++;
		}
		return result;	
	}
	
	public void AddAll_Epistemic_Goals(ArrayList<TEpistemic_Standing_Desire> epistemic_Goals)
	{
		for(TEpistemic_Standing_Desire Epistemic_Goal: epistemic_Goals)
		{
			this.Add_Epistemic_Goal(Epistemic_Goal);
		}
	}
	
	public void Add_Epistemic_Goal(TEpistemic_Standing_Desire epistemic_Goal)
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
		return this.Map_Green_Desires_and_Predicates;
	}
	
	public HashMap<String, ArrayList<TPredicate>> Get_Map_Quality_Goals_and_Predicates()
	{
		return this.Map_Quality_Desires_and_Predicates;
	}
	
	public HashMap<String, ArrayList<TPredicate>> Get_Map_Attentional_Goals_and_Predicates()
	{
		return this.Map_Attentional_Desires_and_Predicates;
	}
	
	public HashMap<String, ArrayList<TBelief>> Get_Map_Attentional_Goals_and_Beliefs()
	{
		return this.Map_Attentional_Desires_and_Beliefs;
	}
	
	public HashMap<String, ArrayList<Object>> Get_Map_Attentional_Goals_and_Regions()
	{
		return this.Map_Attentional_Desires_and_Regions;
	}
	
	public ArrayList<TActive_Desire> Get_Desires()
	{
		return this.Desires;
	}
	
	public void Add_Desire(TActive_Desire desire)
	{
		this.Desires.add(desire);
	}
	
	public void Add_Desire(ArrayList<TActive_Desire> desires)
	{
		for(TActive_Desire desire: desires)
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
		this.Map_Predicates.put(Predicate.Get_Name(), Predicate);
		this.Inc_Predicates_Number = Inc_Predicates_Number; 
		return Predicate;

	}
	
	public TBelief Create_Belief(TPredicate Predicate, Boolean truth, Object information_Source,
			LocalDateTime time_stamp, TType_Beliefs type_Belief)
	{

		int incremental_id = this.Inc_Beliefs_Number + 1;
		String Belief_Name = "b" + incremental_id;

		TBelief Belief = new TBelief(Belief_Name, Predicate, truth, information_Source, time_stamp,
												type_Belief);

		this.Beliefs.addLast(Belief);
		this.Inc_Beliefs_Number = incremental_id;
		
		this.Belief_List.Add_Belief(Belief);
		this.Map_Beliefs.put(Belief.Get_Name(), Belief);
		
		return Belief;
	}
	
	public void Set_Beliefs_Number(int value)
	{
		this.Inc_Beliefs_Number = value;
	}
	
	public void Set_Inhibited_Beliefs(ArrayList<TBelief> inhibited_Beliefs)
	{
		this.Inhibited_Beliefs.clear();
		this.Inhibited_Beliefs.addAll(inhibited_Beliefs);
	}
	
	public void Set_Inhibited_Goals(ArrayList<TAttentional_Standing_Desire> Inhibited_Goals)
	{
		this.Inhibited_Desires.clear();
		this.Inhibited_Desires.addAll(Inhibited_Goals);
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
		for(i=0; i < City.values().length; i++)
		{
			this.Regions.Destinations.add(City.values()[i]);
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
	
	public ArrayList<TBelief> Get_Inhibited_Beliefs()
	{
		return this.Inhibited_Beliefs;
	}
	
	public void Delete_Goal(TAttentional_Standing_Desire Goal)
	{
		this.Attentional_Desires.remove(Goal);
	}
	
	public void Insert_Attentional_Goals_Satisfied(TAttentional_Standing_Desire Goal)
	{
		this.Attentional_Desires_Satisfied.add(Goal);
	}
}