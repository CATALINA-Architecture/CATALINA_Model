package com.Catalina_Model.Catalina_V_0_3;

import java.util.ArrayList;
import java.util.HashSet;


public class TAttentional_Desire extends TDesire{
	
	protected volatile TPropositional_Formula Trigger_Condition;
	/**
	 * this is the list of the belief names within the Trigger Condition
	 */
	protected volatile ArrayList<String> Trigger_Condition_Names;
	/**
	 * this is the formula of the Trigger Condition
	 */
	protected volatile String Trigger_Condition_Formula;

	private volatile TIntention Related_Intention;
//	private ArrayList<TOption> List_Options;
	private volatile TGeneric_Protected_List<TOption> List_Options;
	
	
	//Green Standing_Desire List
	private volatile ArrayList<TGreen_Desire> Green_Standing_Desires;
	
	//Quality Standing_Desire List
	private volatile ArrayList<TQuality_Desire> Quality_Desires;
	
	private volatile ArrayList<String> List_Green_Desire_Names;
	private volatile ArrayList<String> List_Quality_Desire_Names;
//	private TPropositional_Formula Final_State ;
	private volatile HashSet<String> Beliefs_Reasoner;
	private volatile HashSet<String> Regions_Reasoner;
	
	protected double Sum_for_Attention_Treshold = 0.15;

	/**
	 * Cleans any data
	 */
	public void Clear()
	{
		this.List_Options.Clear();
		this.Related_Intention = null;
		
		this.Green_Standing_Desires.clear();
		this.Quality_Desires.clear();
		
		this.List_Green_Desire_Names.clear();
		this.List_Quality_Desire_Names.clear();
	}
	
	/**
	 * Constructor
	 * @param name
	 * @param saliency
	 * @param reward
	 * @param relax_Preference
	 * @param list_Green_Standing_Desire
	 * @param list_Quality_Standing_Desire
	 */
	public TAttentional_Desire(String name, Double saliency, TPropositional_Formula trigger_Condition, Double reward, Double relax_Preference, ArrayList<TGreen_Desire> list_Green_Standing_Desire,
			ArrayList<TQuality_Desire> list_Quality_Standing_Desire,
			ArrayList<String> beliefs_Reasoner, 
			ArrayList<String> regions_Reasoner)
	{
		super(name, saliency, reward, relax_Preference);
		
		//Initialize Trigger Condition Section
		this.Trigger_Condition = trigger_Condition;
		if( this.Trigger_Condition == null)
		{
			this.Trigger_Condition = new TPropositional_Formula();
		}
		this.Trigger_Condition_Names = new ArrayList<String>();
		for(TBelief Belief: this.Trigger_Condition.Get_Beliefs())
		{
			this.Trigger_Condition_Names.add( Belief.Get_Name() );
		}
		this.Trigger_Condition_Formula = this.Trigger_Condition.Get_Formula();
		
		
		this.Related_Intention = null;
		this.List_Options = new TGeneric_Protected_List<TOption>();
		
		this.Green_Standing_Desires = new ArrayList<TGreen_Desire>();
		if(list_Green_Standing_Desire != null)
		{
			this.Green_Standing_Desires = list_Green_Standing_Desire;
		}
		
		this.Quality_Desires = new ArrayList<TQuality_Desire>();
		if(list_Quality_Standing_Desire != null)
		{
			this.Quality_Desires = list_Quality_Standing_Desire;
		}
		
		List_Green_Desire_Names = new ArrayList<String>();
		for (TGreen_Desire Green: Green_Standing_Desires)
		{
			List_Green_Desire_Names.add(Green.Get_Name());			
		}
		
		List_Quality_Desire_Names = new ArrayList<String>();
		for (TQuality_Desire Quality: Quality_Desires)
		{
			List_Quality_Desire_Names.add(Quality.Get_Name());			
		}
		
		this.Beliefs_Reasoner = new HashSet<String>();
		if (beliefs_Reasoner != null)
		{
			this.Beliefs_Reasoner.addAll( beliefs_Reasoner );
		}
		
		this.Regions_Reasoner = new HashSet<String>();
		if (regions_Reasoner != null)
		{
			this.Regions_Reasoner.addAll( regions_Reasoner );
		}
	
	}
	
	public TAttentional_Desire(String name, String trigger_condotion_formula, ArrayList<String> trigger_Condition_Names,
			ArrayList<String> list_Green_Standing_Desire_name, ArrayList<String> list_Quality_Standing_Desire_name, 
			Double saliency, Double reward, Double relax_Preference, 
			ArrayList<String> beliefs_Reasoner, ArrayList<String> regions_Reasoner)
	{
		super(name, saliency, reward, relax_Preference);
		
		//Initialize Trigger Condition Section
		this.Trigger_Condition = new TPropositional_Formula();
		this.Trigger_Condition_Names = new ArrayList<String>();
		this.Trigger_Condition_Names.addAll( trigger_Condition_Names );
		this.Trigger_Condition_Formula = trigger_condotion_formula;
		
		
		this.Related_Intention = null;
		this.List_Options = new TGeneric_Protected_List<TOption>();
		
		this.Green_Standing_Desires = new ArrayList<TGreen_Desire>();
		this.Quality_Desires = new ArrayList<TQuality_Desire>();
		
		this.List_Green_Desire_Names = list_Green_Standing_Desire_name;
		this.List_Quality_Desire_Names = list_Quality_Standing_Desire_name;
		this.Beliefs_Reasoner = new HashSet<String>();
		if (beliefs_Reasoner != null)
		{
			this.Beliefs_Reasoner.addAll( beliefs_Reasoner );
		}
		
		this.Regions_Reasoner = new HashSet<String>();
		if (regions_Reasoner != null)
		{
			this.Regions_Reasoner.addAll( regions_Reasoner );
		}
		
	}
	

	private Boolean Check_Precondition()
	{
		return true;
	}
	
	public ArrayList<TGreen_Desire> Get_List_Green_Standing_Desire() {
		return this.Green_Standing_Desires;
	}
	
	public ArrayList<String> Get_List_Green_Standing_Desire_Name() {
		return this.List_Green_Desire_Names;
	}


	public void Set_List_Green_Standing_Desire(ArrayList<TGreen_Desire> list_Green_Standing_Desire) 
	{
		this.Green_Standing_Desires.clear();
		this.Green_Standing_Desires.addAll( list_Green_Standing_Desire );
//		Green_Standing_Desires = list_Green_Standing_Desire;
	}

	public ArrayList<TQuality_Desire> Get_List_Quality_Standing_Desire() {
		return Quality_Desires;
	}
	
	public ArrayList<String> Get_List_Quality_Standing_Desire_Name() {
		return this.List_Quality_Desire_Names;
	}

	public void Set_List_Quality_Standing_Desire(ArrayList<TQuality_Desire> list_Quality_Standing_Desire) 
	{
		this.Quality_Desires.clear();
		this.Quality_Desires.addAll( list_Quality_Standing_Desire );
//		Quality_Desires = list_Quality_Standing_Desire;
	}
	

	public boolean Verify_Precondition()
	{
		boolean result = this.Check_Precondition();
		return result;
	}
	
	public double Get_Sum_for_Attention_Treshold() {
		return this.Sum_for_Attention_Treshold;
	}
	

	public TIntention Get_Related_Intention()
	{
		return this.Related_Intention;
	}
	
	public void Set_Related_Intention(TIntention Value)
	{
		this.Related_Intention = Value;
	}
	
	public ArrayList<TOption> Get_List_Options()
	{
		return this.List_Options.Read();
	}
	
	public void Set_List_Options(ArrayList<TOption> Values)
	{
		this.List_Options.Clear();
		this.List_Options.Add_All(Values);
	}
	
	public void Clear_Options()
	{
		this.List_Options.Clear();
	}
	
//	public TPropositional_Formula Get_Final_State()
//	{
//		return this.Final_State;
//	}
	
	@Override
    public String toString() {
        // Gestiamo i campi che potrebbero essere null
        
        // Il nome dell'intenzione (se c'è)
        String intentionName = (this.Related_Intention == null) ? 
                                 "null" : this.Related_Intention.Get_Name(); // (Assumendo che TIntention abbia Get_Name())
        
        // Il numero di opzioni (controllando se List_Options è null)
        int optionsCount = (this.List_Options == null) ? 
                                 0 : this.List_Options.Read().size();
        
        // Le liste dei nomi (che sono già stringhe)
        String greenNames = (this.List_Green_Desire_Names == null) ? 
                                 "[]" : this.List_Green_Desire_Names.toString();
        
        String qualityNames = (this.List_Quality_Desire_Names == null) ? 
                                 "[]" : this.List_Quality_Desire_Names.toString();

        // Chiamiamo super.toString() per ottenere i campi da TDesire (Nome, Saliency, ecc.)
        return "TAttentional_Desire[" +
        	   super.toString()+
               "Related_Intention='" + intentionName + '\'' +
               ", Options_Count=" + optionsCount +
               ", Green_Desires=" + greenNames +
               ", Quality_Desires=" + qualityNames +
               "] ";// + super.toString();
    }
	
	public  HashSet<String> Get_Beliefs_Name_for_Reasoner()
	{
		HashSet<String> result = new HashSet<String>();
		result.addAll( this.Beliefs_Reasoner);
		return result;
	}
	
	public  HashSet<String> Get_Regions_Name_for_Reasoner()
	{
		HashSet<String> result = new HashSet<String>();
		result.addAll( this.Regions_Reasoner);
		return result;
	}
	
	public void Set_Beliefs_Name_for_Reasoner( HashSet<String> Beliefs_Names)
	{
		this.Beliefs_Reasoner.clear();
		this.Beliefs_Reasoner.addAll( Beliefs_Names );
	}
	
	public void Set_Beliefs_Name_for_Reasoner( ArrayList<String> Beliefs_Names)
	{
		this.Beliefs_Reasoner.clear();
		this.Beliefs_Reasoner.addAll( Beliefs_Names );
	}
	
	public void Set_Regions_Name_for_Reasoner( HashSet<String> Regions_Names)
	{
		this.Regions_Reasoner.clear();
		this.Regions_Reasoner.addAll( Regions_Names );
	}
	
	public void Set_Regions_Name_for_Reasoner( ArrayList<String> Regions_Names)
	{
		this.Regions_Reasoner.clear();
		this.Regions_Reasoner.addAll( Regions_Names );
	}
}