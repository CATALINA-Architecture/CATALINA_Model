package com.Catalina_Model.Catalina_V_0_3;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.Catalina_Model.Catalina_V_0_3.TBelief_Inhibition_Function_Handler.TBelief_Inhibition_Function;
import com.Catalina_Model.Catalina_V_0_3.TRegion_Inhibition_Function_Handler.TRegion_Inhibition_Function;

public class TGlobal_Workspace implements IGW_Data{
	
	@FunctionalInterface
    public interface TIRecall_Beliefs
    {
		HashMap<String, TBelief> apply(HashSet<String> Beliefs_Names);
    }
	
	private TAgent Agent;
	public TGLITTER_Trie Plan_Library;
	private TAttention_Selection Attention_Modulation_Component;
	private TDesire_Handler Desire_Promotion_Component;
	private TExecutive_Inhibition_Function Executive_Inhibition_Function;
	private TExecutive_Perception_Function Executive_Perception_Function;
	private TExecutive_Memory_Maintenance_Function Executive_Memory_Maintenance_Function;
	private TExecutive_Reasoner_Function Executive_Reasoner_Function;
	private TExecutive_Resource_Allocation_Function Executive_Resource_Allocation_Function;
	private TAction_Control Action_Control; 
	private TExecutive_Switching_Function Executive_Switching_Function;
	public Integer Contatore_di_Broadcast = 0;

	public final TBase_Message_Handler Message_Handler;
	private HashMap<TType_Update_Contract, HashSet<Object>> Update_Contracts;

	// Predicates Properties
	private ArrayList<TPredicate> Predicates;
	
	private TConscious_Data Uninhibited_Consious_Data;

	// Beliefs Properties
	private volatile TGeneric_Protected_List<TBelief> List_Uninhibited_Beliefs;
	public volatile HashMap<String, TBelief> Map_Uninhibited_Beliefs;
	
	private TGeneric_Protected_List<TBelief> List_Preconditions;
	private TGeneric_Protected_List<TBelief> List_Important_Beliefs;

	// Stimuli Properties
	private TGeneric_Protected_List<TStimulus> List_Stimuli;
	private TGeneric_Protected_List<TStimulus> List_Filtered_Stimuli;

	private TDouble_Protected_Object Saliency_Threshold;
	private TDouble_Protected_Object Attention_Threshold;
	private TDouble_Protected_Object Default_Saliency_Threshold;
	private TDouble_Protected_Object Default_Attention_Threshold;
	
	private Integer Inc_Intention_Number = 0; 
	
	public TBoolean_Protected_Object Is_Focused = new TBoolean_Protected_Object(false);

	// Desires Properties
	//All Attentional Desires
	private TGeneric_Protected_List<TAttentional_Desire> List_Attentional_Desires;
	//Only Unhinibited Attentional Desires
	private TGeneric_Protected_List<TAttentional_Desire> List_Uninhibited_Desires;

	// Desires Properties
	private TGeneric_Protected_List<TIntention> List_Intentions;
//	private TGeneric_Protected_List<TIntention> List_Selected_Intentions;
	private TGeneric_Auto_Named_and_Protected_List<TIntention> List_Selected_Intentions;
	
	// Regions Properties
	private TGeneric_Protected_List<TRegion> List_Uninhibited_Regions;
	public HashMap<String, TRegion> Map_Uninhibited_Rgions;

	// Locks Properties
	private final ReentrantReadWriteLock Lock_Update_Thresholds = new ReentrantReadWriteLock();
	private final ReentrantReadWriteLock.ReadLock Read_Lock_Update_Thresholds = Lock_Update_Thresholds.readLock();
	private final ReentrantReadWriteLock.WriteLock Write_Lock_Update_Thresholds = Lock_Update_Thresholds.writeLock();

	private final ReentrantReadWriteLock Lock_Unhinibited_Desires = new ReentrantReadWriteLock();
	private final ReentrantReadWriteLock.ReadLock Read_Lock_Unhinibited_Desires = Lock_Unhinibited_Desires.readLock();
	private final ReentrantReadWriteLock.WriteLock Write_Lock_Unhinibited_Desires = Lock_Unhinibited_Desires
			.writeLock();
	
	private final ReentrantReadWriteLock Lock_Uninhibited_Data = new ReentrantReadWriteLock();
	private final ReentrantReadWriteLock.ReadLock Read_Lock_Uninhibited_Data = Lock_Uninhibited_Data.readLock();
	private final ReentrantReadWriteLock.WriteLock Write_Lock_Uninhibited_Data = Lock_Uninhibited_Data.writeLock();
	
	private final ReentrantReadWriteLock Lock_Uninhibited_Preconditions = new ReentrantReadWriteLock();
	private final ReentrantReadWriteLock.ReadLock Read_Lock_Uninhibited_Preconditions = Lock_Uninhibited_Preconditions.readLock();
	private final ReentrantReadWriteLock.WriteLock Write_Lock_Uninhibited_Preconditions = Lock_Uninhibited_Preconditions.writeLock();

	public TGlobal_Workspace(TAgent agent) {
		this.Agent = agent;

		this.Plan_Library = new TGLITTER_Trie();
		

		this.Update_Contracts = new HashMap<TType_Update_Contract, HashSet<Object>>();
		for(TType_Update_Contract Type_Update_Contract: TType_Update_Contract.values())
		{
			this.Update_Contracts.put( Type_Update_Contract , new HashSet<Object>());
		} 

		this.Message_Handler = new TBase_Message_Handler();
		this.Default_Saliency_Threshold = new TDouble_Protected_Object(0.3);
		this.Default_Attention_Threshold = new TDouble_Protected_Object(this.Default_Saliency_Threshold.Read());
		this.Saliency_Threshold = new TDouble_Protected_Object(this.Default_Saliency_Threshold.Read());
		this.Attention_Threshold = new TDouble_Protected_Object(this.Default_Saliency_Threshold.Read());

		this.Uninhibited_Consious_Data = new TConscious_Data();

		this.List_Uninhibited_Beliefs = new TGeneric_Protected_List<TBelief>();
		this.Map_Uninhibited_Beliefs = new HashMap<String, TBelief>();
		this.List_Preconditions = new TGeneric_Protected_List<TBelief>();
		this.List_Important_Beliefs = new TGeneric_Protected_List<TBelief>();
		
		this.List_Uninhibited_Regions = new TGeneric_Protected_List<TRegion>();
		this.Map_Uninhibited_Rgions = new HashMap<String, TRegion>();
		

		this.List_Stimuli = new TGeneric_Protected_List<TStimulus>();
		this.List_Filtered_Stimuli = new TGeneric_Protected_List<TStimulus>();

		this.List_Attentional_Desires = new TGeneric_Protected_List<TAttentional_Desire>();
		this.List_Uninhibited_Desires = new TGeneric_Protected_List<TAttentional_Desire>();

		this.List_Intentions = new TGeneric_Protected_List<TIntention>();
		this.List_Selected_Intentions = new TGeneric_Auto_Named_and_Protected_List<TIntention>();

	}
	
	public void Acquire_Agent_Components()
	{
		this.Attention_Modulation_Component = this.Agent.Get_Attention_Modulation_Component();
		this.Desire_Promotion_Component = this.Agent.Get_Desire_Handler_Component();
		this.Executive_Inhibition_Function = this.Agent.Get_Executive_Inhibition_Function();
		this.Executive_Perception_Function = this.Agent.Get_Executive_PerceptionFunction();
		this.Action_Control = this.Agent.Get_Action_Control();
		
		this.Executive_Memory_Maintenance_Function = this.Agent.Get_Executive_Memory_Maintenance_Function();
		this.Executive_Reasoner_Function = this.Agent.Get_Executive_Reasoner_Function();
		this.Executive_Resource_Allocation_Function = this.Agent.Get_Executive_Resource_Allocation_Function();
		
		this.Executive_Switching_Function = this.Agent.Get_Executive_Switching_Function();
		
	}

	public void Insert_for_Update_Contract(TType_Update_Contract Type_Update_Contract, Object Who) 
	{
		HashSet<Object> Objects;
		Objects = this.Update_Contracts.get(Type_Update_Contract);
		if (Objects == null) {
			Objects = new HashSet<Object>();
		}
		if (Objects.contains(Who) == false) {
			Objects.add(Who);
		}
		this.Update_Contracts.put(Type_Update_Contract, Objects);
//		if( Type_Update_Contract == TType_Update_Contract.All_Updates)
//		{
//			for(TType_Update_Contract Type_Contract: TType_Update_Contract.values())
//			{
//				Objects = this.Update_Contracts.get(Type_Update_Contract);
//				if (Objects == null) {
//					Objects = new HashSet<Object>();
//				}
//				if (Objects.contains(Who) == false) {
//					Objects.add(Who);
//				}
//				this.Update_Contracts.put(Type_Update_Contract, Objects);
//			}
//		}
	}

	private Double Get_Attention_Threshold() {
		return this.Attention_Threshold.Read();
	}

	private Double Get_Saliency_Threshold() {
		return this.Saliency_Threshold.Read();

	}
	
	public void Add_Intentions(ArrayList<TIntention> intentions)
	{
		this.List_Selected_Intentions.modifyList(list -> { // 'list' è la lista interna protetta
            if (intentions != null && !intentions.isEmpty()) 
            {
                for (TIntention intention : intentions) 
                {
                    long newId = this.List_Selected_Intentions.Inc_Number.incrementAndGet();

                    if (intention.Get_Name() == null || intention.Get_Name().isEmpty() ||
                    		intention.Get_Name().equals("")) 
                    {
                    	intention.Set_Name("Intention_" + newId);
                    }

                    list.add(intention);
                }
            }
        });
	}
	
	public void Add_Intention(TIntention intention)
	{
		this.List_Selected_Intentions.modifyList(list -> { // 'list' è la lista interna protetta
            {
                    long newId = this.List_Selected_Intentions.Inc_Number.incrementAndGet();

                    if (intention.Get_Name() == null || intention.Get_Name().isEmpty() ||
                    		intention.Get_Name().equals("")) 
                    {
                    	intention.Set_Name("Intention_" + newId);
                    }

                    list.add(intention);
            }
        });
	}
	
	public void Set_Intentions(ArrayList<TIntention> intentions)
	{
		this.List_Selected_Intentions.modifyList(list -> { // 'list' è la lista interna protetta
			
			list.clear();
            if (intentions != null && !intentions.isEmpty()) 
            {
                for (TIntention intention : intentions) 
                {
                    long newId = this.List_Selected_Intentions.Inc_Number.incrementAndGet();

                    if (intention.Get_Name() == null || intention.Get_Name().isEmpty() ||
                    		intention.Get_Name().equals("")) 
                    {
                    	intention.Set_Name("Intention_" + newId);
                    }
                    list.add(intention);
                }
            }
        });
	}
	
	

	/**
	 * 
	 * @return a TDouble_Object: The First_Object is the Saliency Threshold The
	 *         Second_Object is the Attention Threshold
	 */
	public TDouble_Object Get_Saliency_and_Attention_Thresholds() {
		Read_Lock_Update_Thresholds.lock();
		try {
			TDouble_Object result = new TDouble_Object();
			result.Set_Object_First(this.Get_Saliency_Threshold());
			result.Set_Object_Second(this.Get_Attention_Threshold());
			return result;
		} finally {
			Read_Lock_Update_Thresholds.unlock();
		}
	}

	private void Update_Attention_Threshold(Double Value) {
		this.Attention_Threshold.Write(Value);
	}

	private void Update_Saliency_Threshold(Double Value) {
		this.Saliency_Threshold.Write(Value);
	}

	public void Update_Saliency_and_Attention_Thresholds(Double Saliency, Double Attention) {
		Write_Lock_Update_Thresholds.lock();
		try 
		{
			this.Update_Saliency_Threshold(Saliency);
			this.Update_Attention_Threshold(Attention);
//			this.Message_Handler.Write_Updated_Thresholds();
//			this.Broadcast();
			this.Broadcast_Signal(TType_Update_Contract.Updated_Thresholds);
		} 
		finally 
		{
			Write_Lock_Update_Thresholds.unlock();
		}

	}

	public void Update_Selected_Intentions(ArrayList<TIntention> selected_Intentions)
	{
		

	}

	public Double Get_Default_Saliency_Thresholds() {
		return this.Default_Saliency_Threshold.Read();
	}

	public Double Get_Default_Attention_Thresholds() {
		return this.Default_Attention_Threshold.Read();
	}

	public void Set_Default_Saliency_Thresholds(Double Value) {
		this.Default_Saliency_Threshold.Write(Value);
	}

	public void Set_Default_Attention_Thresholds(Double Value) {
		this.Default_Attention_Threshold.Write(Value);
	}

	public ArrayList<TAttentional_Desire> Get_Unhinibited_Active_Desires()
	{
		ArrayList<TAttentional_Desire> Desires = new ArrayList<TAttentional_Desire>();
		for(TAttentional_Desire Desire: this.List_Uninhibited_Desires.Read())
		{
			if ( Desire.Get_Status_Desire() == TType_Status_Desire.Active)
			{
				Desires.add( Desire );
			}
		}
		return Desires;
	}
	
	public ArrayList<TAttentional_Desire> Get_Unhinibited_Desires() 
	{
		return this.List_Uninhibited_Desires.Read();
	}

	public ArrayList<TPractical_Desire> Get_Active_Practical_Desires() 
	{
		ArrayList<TPractical_Desire> Practical_Desires = new ArrayList<TPractical_Desire>();
		for (TAttentional_Desire Desire : this.List_Uninhibited_Desires.Read()) {
			if (Desire instanceof TPractical_Desire) {
				Practical_Desires.add((TPractical_Desire) Desire);
			}
		}
		return Practical_Desires;
	}

	public ArrayList<TAttentional_Desire> Get_Attentional_Desires() 
	{
		if (this.List_Attentional_Desires.isEmpty()) {
			this.List_Attentional_Desires.Add_All(this.Executive_Memory_Maintenance_Function
					.Get_LT_Memory_Maintenance().Get_Attentional_Desires());
		}
		return this.List_Attentional_Desires.Read();
	}

	public ArrayList<TPractical_Desire> Get_All_Practical_Desires() {
//		if (this.List_Attentional_Desires.isEmpty())
//		{
//			this.List_Attentional_Desires.Add_All(this.Executive_Memory_Maintenance_Function
//					.Get_LT_Memory_Maintenance().Get_Attentional_Desires());
//		}

		ArrayList<TPractical_Desire> Practical_Desires = new ArrayList<TPractical_Desire>();
		for (TAttentional_Desire Desire : this.Executive_Memory_Maintenance_Function
				.Get_LT_Memory_Maintenance().Get_Attentional_Desires()) {
			if (Desire instanceof TPractical_Desire) {
				Practical_Desires.add((TPractical_Desire) Desire);
			}
		}
		return Practical_Desires;
	}
	
	public ArrayList<TPractical_Desire> Get_All_Practical_Desires_old() {
		if (this.List_Attentional_Desires.isEmpty())
		{
			this.List_Attentional_Desires.Add_All(this.Executive_Memory_Maintenance_Function
					.Get_LT_Memory_Maintenance().Get_Attentional_Desires());
		}

		ArrayList<TPractical_Desire> Practical_Desires = new ArrayList<TPractical_Desire>();
		for (TAttentional_Desire Desire : this.List_Attentional_Desires.Read()) {
			if (Desire instanceof TPractical_Desire) {
				Practical_Desires.add((TPractical_Desire) Desire);
			}
		}
		return Practical_Desires;
	}
	
	public ArrayList<TPractical_Desire> Get_All_Practical_Desires_from_LTM() 
	{
		this.List_Attentional_Desires.Add_All(this.Executive_Memory_Maintenance_Function
				.Get_LT_Memory_Maintenance().Get_Attentional_Desires());

		ArrayList<TPractical_Desire> Practical_Desires = new ArrayList<TPractical_Desire>();
		for (TAttentional_Desire Desire : this.List_Attentional_Desires.Read()) 
		{
			if (Desire instanceof TPractical_Desire) 
			{
				Practical_Desires.add((TPractical_Desire) Desire);
			}
		}
		return Practical_Desires;
	}
	
	public ArrayList<TEpistemic_Desire> Get_All_Epistemic_Desires() {
		if (this.List_Attentional_Desires.isEmpty()) {
			this.List_Attentional_Desires.Add_All(this.Executive_Memory_Maintenance_Function
					.Get_LT_Memory_Maintenance().Get_Attentional_Desires());
		}

		ArrayList<TEpistemic_Desire> Practical_Desires = new ArrayList<TEpistemic_Desire>();
		for (TAttentional_Desire Desire : this.List_Attentional_Desires.Read()) {
			if (Desire instanceof TEpistemic_Desire) {
				Practical_Desires.add((TEpistemic_Desire) Desire);
			}
		}
		return Practical_Desires;
	}

	public ArrayList<TEpistemic_Desire> Get_Epistemic_Desires() {
		if (this.List_Attentional_Desires.isEmpty()) {
			this.List_Attentional_Desires.Add_All(this.Executive_Memory_Maintenance_Function
					.Get_LT_Memory_Maintenance().Get_Attentional_Desires());
		}

		ArrayList<TEpistemic_Desire> Epistemic_Desires = new ArrayList<TEpistemic_Desire>();
		for (TAttentional_Desire Desire : this.List_Attentional_Desires.Read()) {
			if (Desire instanceof TEpistemic_Desire) {
				Epistemic_Desires.add((TEpistemic_Desire) Desire);
			}
		}
		return Epistemic_Desires;
	}

	public ArrayList<TPractical_Desire> Get_Inhibited_Practical_Desires_from_LT() {

		ArrayList<TPractical_Desire> Inhibited_Practical_Desires = new ArrayList<TPractical_Desire>();
		
		for (TAttentional_Desire Desire : this.Executive_Memory_Maintenance_Function
				.Get_LT_Memory_Maintenance().Get_Inhibited_Attentional_Desires()) {
			if (Desire instanceof TPractical_Desire) {
				Inhibited_Practical_Desires.add((TPractical_Desire) Desire);
			}
		}
		return Inhibited_Practical_Desires;
	}
	
	public ArrayList<TAttentional_Desire> Get_Inhibited_Attentional_Desires_from_LT()
	{
		return this.Executive_Memory_Maintenance_Function
				.Get_LT_Memory_Maintenance().Get_Inhibited_Attentional_Desires();
	}

	public ArrayList<TEpistemic_Desire> Get_Inhibited_Epistemic_Desires() {

		ArrayList<TEpistemic_Desire> Inhibited_Epistemic_Desires = new ArrayList<TEpistemic_Desire>();
		for (TAttentional_Desire Desire : this.Executive_Memory_Maintenance_Function
				.Get_LT_Memory_Maintenance().Get_Inhibited_Attentional_Desires()) 
		{
			if (Desire instanceof TEpistemic_Desire) {
				Inhibited_Epistemic_Desires.add((TEpistemic_Desire) Desire);
			}
		}
		return Inhibited_Epistemic_Desires;
	}


//	public Boolean Delete_Intentions(TIntention intention) 
//	{
//		intention.Set_Desire(null);
//		Boolean result = this.List_Selected_Intentions.Remove(intention);
//		this.Broadcast_Message(TType_Update_Contract.Selected_Intentions);
//		return result;
//	}
	
	public Boolean Remove_Intentions(ArrayList<TIntention> intentions) 
	{

		Boolean result = this.List_Selected_Intentions.Remove_All( intentions);
//		this.Broadcast_Message(TType_Update_Contract.Selected_Intentions);
		for(TIntention intention: intentions )
		{
			intention.Set_Desire( null );
		}
		return result;
	}

	// I have to understand when this method will be called, And the correctness
	public void Mark_as_Active_Desires(ArrayList<TAttentional_Desire> Uninhibited_Desires,
			ArrayList<TAttentional_Desire> Inhibited_Desires)
			//ArrayList<TAttentional_Desire> Inhibited_Desires_To_Promote) // *** not copied
	{
		
		HashSet<TAttentional_Desire> All_Desires_to_Promote = new HashSet<TAttentional_Desire>();
		
		All_Desires_to_Promote.addAll( Inhibited_Desires );

		/**
		 *  The following line ensures that the data for Uninhibited Desires 
		 *  is maintained. 
		 */
		All_Desires_to_Promote.addAll( Uninhibited_Desires );
		
		ArrayList<TAttentional_Desire> Desires = new ArrayList<>();
		TDesire_Data Inhibited_Desires_Data = null;
		
		if( All_Desires_to_Promote.size() > 0)
		{
			Inhibited_Desires_Data = 
					this.Recall_Inhibited_Data_for_Activated_Inhibited_Desires
								( All_Desires_to_Promote);
			
			Desires.addAll( All_Desires_to_Promote );
		}
		
		
		this.Write_Lock_Uninhibited_Data.lock();
		try
		{
			this.Write_Lock_Unhinibited_Desires.lock();
			try 
			{
				if( All_Desires_to_Promote.size() > 0)
				{
					
					for (TAttentional_Desire Desire : Desires) 
					{
						Desire.Set_Status_Desire(TType_Status_Desire.Active);
					}

					this.Add_Uninhibited_Conscious_Data( 
							Inhibited_Desires_Data.Get_Preconditions(), 
							Inhibited_Desires_Data.Get_Beliefs(), 
							Desires,
							Inhibited_Desires_Data.Get_Regions());
					
					this.Broadcast_Signal(TType_Update_Contract.Updated_Active_Desires);
				}
			} 
			finally 
			{
				this.Write_Lock_Unhinibited_Desires.unlock();
			}
		}
		finally
		{
			this.Write_Lock_Uninhibited_Data.unlock();
		}
		
	}
	
	public void Mark_as_Active_Desires_old(ArrayList<TAttentional_Desire> desires,
			TQuadruple_Object Inhibited_Data_of_Inhibited_Desires)
			//ArrayList<TAttentional_Desire> Inhibited_Desires_To_Promote) // *** not copied
	{
		this.Write_Lock_Uninhibited_Data.lock();
		try
		{
			this.Write_Lock_Unhinibited_Desires.lock();
			try {
				// In this time, I order goals to saliency order in higher order and In
				// deliberation process
				// I select the first desire as selected intention

//				this.List_Active_Desires.addAll(desires);
				for (TAttentional_Desire Desire : desires) 
				{
					Desire.Set_Status_Desire(TType_Status_Desire.Active);
					this.List_Uninhibited_Desires.Add(Desire);
				}

				/**
				 * If the function is called by the Endogenous Desire Promotion and
				 * Inhibited_Data_of_Inhibited_Desires is not null
				 */
				
				if(Inhibited_Data_of_Inhibited_Desires != null)
				{
					/**
					 * Inhibited_Pre_conditions
					 */
					ArrayList<TBelief> Inhibited_Pre_conditions = new ArrayList<TBelief>();
					
					Object Temp_Inhibited_Pre_conditions = Inhibited_Data_of_Inhibited_Desires.Get_Object_First();
//					Inhibited_Pre_conditions.addAll(
//							(ArrayList<TBelief> ) Inhibited_Data_of_Inhibited_Desires.Get_Object_First());
					
					if (Temp_Inhibited_Pre_conditions != null)
					{
						Inhibited_Pre_conditions.addAll(
								(HashSet<TBelief> ) Temp_Inhibited_Pre_conditions);						
					}
					
					/**
					 * Inhibited_Beliefs
					 */
					ArrayList<TBelief> Inhibited_Beliefs = new ArrayList<TBelief>();
					Object Temp_Inhibited_Beliefs = Inhibited_Data_of_Inhibited_Desires.Get_Object_Second(); 
//					Inhibited_Beliefs.addAll(
//							(ArrayList<TBelief> ) Inhibited_Data_of_Inhibited_Desires.Get_Object_Second());
					
					if (Temp_Inhibited_Beliefs != null)
					{
						Inhibited_Beliefs.addAll(
								(HashSet<TBelief> ) Temp_Inhibited_Beliefs);						
					}
					
					/**
					 * Inhibited_Desires_To_Promote
					 */
					ArrayList<TAttentional_Desire> Inhibited_Desires_To_Promote = new ArrayList<TAttentional_Desire>();
					Object Temp_Inhibited_Desires_To_Promote =  Inhibited_Data_of_Inhibited_Desires.Get_Object_Third();
//					Inhibited_Desires_To_Promote.addAll(
//							(ArrayList<TAttentional_Desire> ) Inhibited_Data_of_Inhibited_Desires.Get_Object_Third());
					if ( Temp_Inhibited_Desires_To_Promote != null )
					{
						Inhibited_Desires_To_Promote.addAll(
								(ArrayList<TAttentional_Desire> ) Temp_Inhibited_Desires_To_Promote);
					}

					/**
					 * Inhibited_Regions
					 */
					ArrayList<TRegion> Inhibited_Regions = new ArrayList<TRegion>();
					Object Temp_Inhibited_Regions = Inhibited_Data_of_Inhibited_Desires.Get_Object_Fourth(); 
//					Inhibited_Regions.addAll(
//							(ArrayList<TRegion> ) Inhibited_Data_of_Inhibited_Desires.Get_Object_Fourth());
					if ( Temp_Inhibited_Regions != null )
					{
						Inhibited_Regions.addAll(
								(ArrayList<TRegion> ) Temp_Inhibited_Regions); 						
					}
					
					
					for (TAttentional_Desire Desire : Inhibited_Desires_To_Promote) 
					{
						Desire.Set_Status_Desire(TType_Status_Desire.Active);
					}
					this.Add_Uninhibited_Conscious_Data( Inhibited_Pre_conditions, Inhibited_Beliefs, 
														 Inhibited_Desires_To_Promote, Inhibited_Regions);
				}
				
				
//				this.Message_Handler.Write_Updated_Active_Desires();
				if (( desires.size() > 0) || (Inhibited_Data_of_Inhibited_Desires != null) )
				{
					this.Broadcast_Signal(TType_Update_Contract.Updated_Active_Desires);
				}
			} 
			finally 
			{
				this.Write_Lock_Unhinibited_Desires.unlock();
			}
		}
		finally
		{
			this.Write_Lock_Uninhibited_Data.unlock();
		}
		
	}

	public void Mark_as_Standing_Desires(ArrayList<TAttentional_Desire> desires) // *** not copied
	{
		Write_Lock_Unhinibited_Desires.lock();
		try 
		{
			// In this time, I order goals to saliency order in higher order and In
			// deliberation process
			// I select the first desire as selected intention

			TIntention Intention = null;
			ArrayList<TIntention> Intentions_to_Remove = new ArrayList<TIntention>();

//			ArrayList<TAttentional_Desire> Active_Desires = this.List_Active_Desires.Read();
			for (TAttentional_Desire Desire : desires)
			{
				Desire.Set_Status_Desire(TType_Status_Desire.Standing);
				Desire.Clear_Options();

				Intention = Desire.Get_Related_Intention();
				if (Intention != null) 
				{
					Desire.Set_Related_Intention(null);
					Intentions_to_Remove.add( Intention );
					Intention.Set_Desire( null );
					
//					this.Delete_Intentions(Intention_to_Delete);
				}
//				Active_Desires.remove(Desire);
			}
//			this.List_Active_Desires.Clear();
//			this.List_Active_Desires.Add_All(Active_Desires);
//			this.List_Unhinibited_Desires.Remove_All(desires);

			this.Remove_Intentions( Intentions_to_Remove );
//			this.Broadcast_Message(TType_Update_Contract.Selected_Intentions);
			
//			this.Broadcast_Message(TType_Update_Contract.Standing_Desires);
		} 
		finally 
		{
			Write_Lock_Unhinibited_Desires.unlock();
		}
	}

	public void Mark_as_Satisfied_Desires(ArrayList<TAttentional_Desire> desires) // *** not copied
	{
		Write_Lock_Unhinibited_Desires.lock();
		try 
		{
			// In this time, I order goals to saliency order in higher order and In
			// deliberation process
			// I select the first desire as selected intention

			TIntention Intention = null;
			ArrayList<TIntention> Intentions_to_Remove = new ArrayList<TIntention>();

			for (TAttentional_Desire Desire : desires) 
			{
				Desire.Set_Status_Desire(TType_Status_Desire.Satisfied);
				Desire.Clear_Options();
				Intention = Desire.Get_Related_Intention();
				Desire.Set_Related_Intention(null);
				
				if (Intention != null) 
				{
//					Desire.Set_Related_Intention(null);
					Intention.Set_Desire( null );
					//this.Delete_Intentions(Intention_to_Delete);
					Intentions_to_Remove.add( Intention );
				}
				
				
			}
			this.List_Uninhibited_Desires.Remove_All( desires );
			this.List_Attentional_Desires.Remove_All( desires );
			this.Executive_Memory_Maintenance_Function.Get_LT_Memory_Maintenance().Remove_Attentional_Desires( desires );
			this.Executive_Memory_Maintenance_Function.Get_LT_Memory_Maintenance().Add_Satisfied_Attentional_Desires( desires );
			
			this.Remove_Intentions( Intentions_to_Remove );
			this.Broadcast_Signal(TType_Update_Contract.Updated_Selected_Intentions);
//			this.Broadcast_Message(TType_Update_Contract.Selected_Intentions);
			
			//Insert a code to send the satisfied desires in Long Memory
//			this.Broadcast_Message(TType_Update_Contract.Selected_Intentions);
//			this.Broadcast_Message(TType_Update_Contract.Satisfied_Desires);
//			this.Broadcast_Message(TType_Update_Contract.Active_Desires);
			
		} 
		finally 
		{
			Write_Lock_Unhinibited_Desires.unlock();
		}
	}

	// public void Broadcast(TType_Update_Contract Update_Contract)
	public void Broadcast() 
	{

		// Update message for PREDICATES for any Executive Function that wants to be
		// updated
		if (Message_Handler.Read_Value_And_Clear_Updated_Predicates()) {
			HashSet<Object> Executive_Functions = this.Update_Contracts.get(TType_Update_Contract.Updated_Predicates);
			TAgent_Base_Thread Generic_Executive_Function = null;
			if (Executive_Functions.size() > 0) {
				for (Object who : Executive_Functions) {
					Generic_Executive_Function = (TAgent_Base_Thread) who;
					Generic_Executive_Function.Message_Handler.Write_Value_Updated_Predicates();
				}
			}
			Generic_Executive_Function = null;
		}

		// Update message for STANDING DESIRES for any Executive Function that wants to
		// be updated
		if (Message_Handler.Read_Value_And_Clear_Updated_Standing_Desires()) {
			HashSet<Object> Executive_Functions = this.Update_Contracts.get(TType_Update_Contract.Updated_Standing_Desires);
			TAgent_Base_Thread Generic_Executive_Function = null;
			if (Executive_Functions.size() > 0) {
				for (Object who : Executive_Functions) {
					Generic_Executive_Function = (TAgent_Base_Thread) who;
					Generic_Executive_Function.Message_Handler.Write_Value_Updated_Standing_Desires();
				}
			}
			Generic_Executive_Function = null;
		}

		// Update message for BELIEFS for any Executive Function that wants to be
		// updated
		if (Message_Handler.Read_Value_And_Clear_Updated_Beliefs()) {
			HashSet<Object> Executive_Functions = this.Update_Contracts.get(TType_Update_Contract.Updated_Beliefs);
			TAgent_Base_Thread Generic_Executive_Function = null;
			if (Executive_Functions.size() > 0) {
				for (Object who : Executive_Functions) {
					Generic_Executive_Function = (TAgent_Base_Thread) who;
					Generic_Executive_Function.Message_Handler.Write_Value_Updated_Beliefs();
				}
			}
			Generic_Executive_Function = null;
		}

		if (Message_Handler.Read_Value_And_Clear_Updated_Unhinibited_Data()) {
			HashSet<Object> Executive_Functions = this.Update_Contracts
					.get(TType_Update_Contract.Updated_Uninhibited_Data);
			TAgent_Base_Thread Generic_Executive_Function = null;
			if (Executive_Functions.size() > 0) {
				for (Object who : Executive_Functions) {
					Generic_Executive_Function = (TAgent_Base_Thread) who;
					Generic_Executive_Function.Message_Handler.Write_Value_Updated_Unhinibited_Beliefs();
				}
			}
			Generic_Executive_Function = null;
		}

		// Update message for ACTIVE DESIRES for any Executive Function that wants to be
		// updated
		if (Message_Handler.Read_Value_And_Clear_Updated_Active_Desires()) {
			HashSet<Object> Executive_Functions = this.Update_Contracts.get(TType_Update_Contract.Updated_Active_Desires);
			TAgent_Base_Thread Generic_Executive_Function = null;
			if (Executive_Functions.size() > 0) {
				for (Object who : Executive_Functions) {
					Generic_Executive_Function = (TAgent_Base_Thread) who;
					Generic_Executive_Function.Message_Handler.Write_Value_Updated_Active_Desires();
				}
			}
			Generic_Executive_Function = null;
		}

		// Update message for ACTIVE DESIRES WITH OPTIONS for any Executive Function
		// that wants to be updated
		if (Message_Handler.Read_Value_And_Clear_Updated_Active_Desires_With_Options()) {
			HashSet<Object> Executive_Functions = this.Update_Contracts
					.get(TType_Update_Contract.Updated_Active_Desires_with_Options);
			TAgent_Base_Thread Generic_Executive_Function = null;
			if (Executive_Functions.size() > 0) {
				for (Object who : Executive_Functions) {
					Generic_Executive_Function = (TAgent_Base_Thread) who;
					Generic_Executive_Function.Message_Handler.Write_Value_Updated_Active_Desires_With_Options();
				}
			}
			Generic_Executive_Function = null;
		}

		// Update message for SELECTED INTENTIONS for any Executive Function that wants
		// to be updated
		if (Message_Handler.Read_Value_And_Clear_Updated_Selected_Intentions()) {
			HashSet<Object> Executive_Functions = this.Update_Contracts
					.get(TType_Update_Contract.Updated_Selected_Intentions);
			TAgent_Base_Thread Generic_Executive_Function = null;
			if (Executive_Functions.size() > 0) {
				for (Object who : Executive_Functions) {
					Generic_Executive_Function = (TAgent_Base_Thread) who;
					Generic_Executive_Function.Message_Handler.Write_Value_Updated_Selected_Intentions();
				}
			}
			Generic_Executive_Function = null;
		}

		// Update message for STIMULI for any Executive Function that wants to be
		// updated
		if (Message_Handler.Read_Value_And_Clear_Updated_Stimuli()) {
			HashSet<Object> Executive_Functions = this.Update_Contracts.get(TType_Update_Contract.Updated_Stimuli);
			TAgent_Base_Thread Generic_Executive_Function = null;
			if (Executive_Functions.size() > 0) {
				for (Object who : Executive_Functions) {
					Generic_Executive_Function = (TAgent_Base_Thread) who;
					Generic_Executive_Function.Message_Handler.Write_Value_Updated_Stimuli();
				}
			}
			Generic_Executive_Function = null;
		}
		
		// Update message for FILTERED STIMULI for any Executive Function that wants to be
				// updated
		if (Message_Handler.Read_Value_And_Clear_Updated_Filtered_Stimuli()) {
			HashSet<Object> Executive_Functions = this.Update_Contracts.get(TType_Update_Contract.Updated_Filtered_Stimuli);
			TAgent_Base_Thread Generic_Executive_Function = null;
			if (Executive_Functions.size() > 0) {
				for (Object who : Executive_Functions) {
					Generic_Executive_Function = (TAgent_Base_Thread) who;
					Generic_Executive_Function.Message_Handler.Write_Value_Updated_Filtered_Stimuli();
				}
			}
			Generic_Executive_Function = null;
		}
		
		if (Message_Handler.Read_Value_And_Clear_Updated_Action_to_Execute()) {
			HashSet<Object> Executive_Functions = this.Update_Contracts.get(TType_Update_Contract.Updated_Action_to_Execute);
			TAgent_Base_Thread Generic_Executive_Function = null;
			if (Executive_Functions.size() > 0) {
				for (Object who : Executive_Functions) {
					Generic_Executive_Function = (TAgent_Base_Thread) who;
					Generic_Executive_Function.Message_Handler.Write_Value_Updated_Action_to_Execute();
				}
			}
			Generic_Executive_Function = null;
		}
		
		if (Message_Handler.Read_Value_And_Clear_Updated_Executed_Action()) {
			HashSet<Object> Executive_Functions = this.Update_Contracts.get(TType_Update_Contract.Updated_Executed_Action);
			TAgent_Base_Thread Generic_Executive_Function = null;
			if (Executive_Functions.size() > 0) {
				for (Object who : Executive_Functions) {
					Generic_Executive_Function = (TAgent_Base_Thread) who;
					Generic_Executive_Function.Message_Handler.Write_Value_Updated_Executed_Action();
				}
			}
			Generic_Executive_Function = null;
		}
		

		// Update message for THRESHOLDS for any Executive Function that wants to be
		// updated
		if (Message_Handler.Read_Value_And_Clear_Updated_Thresholds()) {
			HashSet<Object> Executive_Functions = this.Update_Contracts
					.get(TType_Update_Contract.Updated_Thresholds);
			TAgent_Base_Thread Generic_Executive_Function = null;
			if (Executive_Functions.size() > 0) {
				StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
//				System.out.println("Metodo chiamante: " + stackTrace[3].getMethodName());
//				System.out.println("Metodo chiamante: " + stackTrace[3].getLineNumber());
//				System.out.println("Metodo chiamante: " + stackTrace[3].getClassName());
//				System.out.println("Thresholds viene chiamato");
//				System.out.println("Executive_Functions for Thresholds: "+Executive_Functions.size());
				for (Object who : Executive_Functions) {
					Generic_Executive_Function = (TAgent_Base_Thread) who;
					Generic_Executive_Function.Message_Handler.Write_Value_Updated_Thresholds();
				}
			}
			Generic_Executive_Function = null;
		}

		// Update message for INTERNAL SIGNALS for any Executive Function that wants to
		// be updated
		if (Message_Handler.Read_Value_And_Clear_Updated_Internal_Signals()) {
			HashSet<Object> Executive_Functions = this.Update_Contracts.get(TType_Update_Contract.Internal_Signals);
			TAgent_Base_Thread Generic_Executive_Function = null;
			if (Executive_Functions.size() > 0) {
				for (Object who : Executive_Functions) {
					Generic_Executive_Function = (TAgent_Base_Thread) who;
					Generic_Executive_Function.Message_Handler.Write_Value_Updated_Internal_Signals();
				}
			}
			Generic_Executive_Function = null;
		}

		// Update message for PERCEPTIONS for any Executive Function that wants to be
		// updated
		if (Message_Handler.Read_Value_And_Clear_Updated_Perceptions()) {
			HashSet<Object> Executive_Functions = this.Update_Contracts.get(TType_Update_Contract.Updated_Perceptions);
			TAgent_Base_Thread Generic_Executive_Function = null;
			if (Executive_Functions.size() > 0) {
				for (Object who : Executive_Functions) {
					Generic_Executive_Function = (TAgent_Base_Thread) who;
					Generic_Executive_Function.Message_Handler.Write_Value_Updated_Perceptions();
				}
			}
			Generic_Executive_Function = null;
		}
		
		// Update message for SATISFIED DESIRES for any Executive Function that wants to be
		// updated
		if (Message_Handler.Read_Value_And_Clear_Updated_Satisfied_Desires()) {
			HashSet<Object> Executive_Functions = this.Update_Contracts.get(TType_Update_Contract.Updated_Satisfied_Desires);
			TAgent_Base_Thread Generic_Executive_Function = null;
			if (Executive_Functions.size() > 0) {
				for (Object who : Executive_Functions) {
					Generic_Executive_Function = (TAgent_Base_Thread) who;
					Generic_Executive_Function.Message_Handler.Write_Value_Updated_Satisfied_Desires();
				}
			}
			Generic_Executive_Function = null;
		}
		
		// Update message for INTERNAL SIGNALS SATISFIED INTENTION MESSAGES DESIRES for any Executive Function that wants to be
		// updated
		ArrayList<TBase_Message> Internal_Signals_Delete = new ArrayList<TBase_Message>();
		Internal_Signals_Delete.addAll( Message_Handler.Read_And_Clear_Updated_Internal_Signals_Delete_Intention_Messages() );
		//if (Message_Handler.Read_Updated_Internal_Signals_Detede_Intention_Messages().size() > 0) {
		if (Internal_Signals_Delete.size() > 0 ) {
			HashSet<Object> Executive_Functions = this.Update_Contracts.get(TType_Update_Contract.Internal_Signals_Delete_Intention);
			TAgent_Base_Thread Generic_Executive_Function = null;
			if (Executive_Functions.size() > 0) {
				for (Object who : Executive_Functions) {
					Generic_Executive_Function = (TAgent_Base_Thread) who;
					for(TBase_Message Internal_Signal_Delete: Internal_Signals_Delete)
					{
						Generic_Executive_Function.Message_Handler.
						Add_Value_Updated_Internal_Signals_Delete_Intention_Message(
								Internal_Signal_Delete.Read_Sender(), Internal_Signal_Delete.Read_Data_and_Clean());
					}
				}
			}
			Generic_Executive_Function = null;
		}
		
		// Update message for INTERNAL SIGNALS SATISFIED INTENTION MESSAGES DESIRES for any Executive Function that wants to be
				// updated
				ArrayList<TBase_Message> Internal_Signals_Satisfied = new ArrayList<TBase_Message>();
				Internal_Signals_Satisfied.addAll( Message_Handler.Read_And_Clear_Updated_Internal_Signals_Satisfied_Intention_Messages() );
				//if (Message_Handler.Read_Updated_Internal_Signals_Satisfied_Intention_Messages().size() > 0) {
				if (Internal_Signals_Satisfied.size() > 0 ) {
					HashSet<Object> Executive_Functions = this.Update_Contracts.get(TType_Update_Contract.Internal_Signals_Satisfied_Intention);
					TAgent_Base_Thread Generic_Executive_Function = null;
					if (Executive_Functions.size() > 0) {
						for (Object who : Executive_Functions) {
							Generic_Executive_Function = (TAgent_Base_Thread) who;
							for(TBase_Message Internal_Signal_Satisfied: Internal_Signals_Satisfied)
							{
								Generic_Executive_Function.Message_Handler.
								Add_Value_Updated_Internal_Signals_Satisfied_Intention_Message(
										Internal_Signal_Satisfied.Read_Sender(), Internal_Signal_Satisfied.Read_Data_and_Clean());
							}
						}
					}
					Generic_Executive_Function = null;
				}
		
		// Update message for SATISFIED DESIRES for any Executive Function that wants to be
		// updated
		if (Message_Handler.Read_Value_And_Clear_Updated_Important_Beliefs()) {
			HashSet<Object> Executive_Functions = this.Update_Contracts.get(TType_Update_Contract.Updated_Critical_Beliefs);
			TAgent_Base_Thread Generic_Executive_Function = null;
			if (Executive_Functions.size() > 0) {
				for (Object who : Executive_Functions) {
					Generic_Executive_Function = (TAgent_Base_Thread) who;
					Generic_Executive_Function.Message_Handler.Write_Value_Updated_Important_Beliefs();
				}
			}
			Generic_Executive_Function = null;
		}
		
		// Update message for PRECONDITIONS for any Executive Function that wants to be
				// updated
				if (Message_Handler.Read_Value_And_Clear_Updated_Preconditions()) {
					HashSet<Object> Executive_Functions = this.Update_Contracts.get(TType_Update_Contract.Updated_Preconditions);
					TAgent_Base_Thread Generic_Executive_Function = null;
					if (Executive_Functions.size() > 0) {
						for (Object who : Executive_Functions) {
							Generic_Executive_Function = (TAgent_Base_Thread) who;
							Generic_Executive_Function.Message_Handler.Write_Value_Updated_Preconditions();
						}
					}
					Generic_Executive_Function = null;
				}

	}

	public ArrayList<TBelief> Get_UnInhibited_Beliefs() 
	{

//		if (this.List_Uninhibited_Beliefs.isEmpty())
//		{
//			this.List_Uninhibited_Beliefs.Add_All(
//					this.Executive_Memory_Maintenance_Function.Get_LT_Memory_Maintenance().Get_Beliefs());
//			this.Broadcast_Message( TType_Update_Contract.Uninhibited_Beliefs);
//		}
		return this.List_Uninhibited_Beliefs.Read();
	}
	
	public ArrayList<TRegion> Get_UnInhibited_Regions() 
	{
		return this.List_Uninhibited_Regions.Read();
	}
	
	public ArrayList<TAttentional_Desire> Get_All_Attentional_Desires_from_MM() 
	{
		return this.Executive_Memory_Maintenance_Function.Get_LT_Memory_Maintenance().Get_Attentional_Desires();
	}
	
	public ArrayList<TAttentional_Desire> Get_All_Not_Satisfied_Attentional_Desire_from_MM()
	{
		return this.Executive_Memory_Maintenance_Function.Get_LT_Memory_Maintenance().Get_All_Not_Satisfied_Attentional_Desires();
	}

	public ArrayList<TBelief> Get_All_Beliefs_from_MM() 
	{
		return this.Executive_Memory_Maintenance_Function.Get_LT_Memory_Maintenance().Get_All_Beliefs();
	}
	
	public HashMap<String, TBelief> Get_All_Map_Beliefs_from_MM() 
	{
		return this.Executive_Memory_Maintenance_Function.Get_LT_Memory_Maintenance().Get_All_Map_Beliefs();
	}
	
	public ArrayList<TRegion> Get_All_Regions_from_MM() 
	{
		return this.Executive_Memory_Maintenance_Function.Get_LT_Memory_Maintenance().Get_Regions();
	}

	public void Set_UnInhibited_Beliefs(ArrayList<TBelief> beliefs) {
		
		
		this.List_Uninhibited_Beliefs.Set_List(beliefs);
//		for(TBelief Belief: this.List_Uninhibited_Beliefs.Read())
		this.Map_Uninhibited_Beliefs.clear();
		for(TBelief Belief: beliefs)
			
		{
			this.Map_Uninhibited_Beliefs.put(Belief.Get_Name(), Belief);
		}
//		this.Message_Handler.Write_Updated_Beliefs();
//		this.Broadcast();
		this.Broadcast_Signal(TType_Update_Contract.Updated_Uninhibited_Data);
	}
	
	protected TAction_Control Get_Action_Control()
	{
		return this.Action_Control;
	}

	public ArrayList<TBelief> Get_Inhibited_Beliefs_from_MM() {

		return this.Executive_Memory_Maintenance_Function.Get_LT_Memory_Maintenance()
				.Get_Inhibited_Beliefs();
	}
	
	public ArrayList<TRegion> Get_Inhibited_Regions_from_MM() {

		return this.Executive_Memory_Maintenance_Function.Get_LT_Memory_Maintenance()
				.Get_Inhibited_Regions();
	}

	public void Set_Beliefs(ArrayList<TBelief> beliefs) {
		this.Executive_Memory_Maintenance_Function.Get_LT_Memory_Maintenance().Set_Beliefs(beliefs);

		this.Broadcast_Signal(TType_Update_Contract.Updated_Beliefs);
	}

//	public void Set_Inhibited_Beliefs(ArrayList<TBelief> inhibited_beliefs) {
//		this.Executive_Memory_Maintenance_Function.Get_LT_Memory_Maintenance()
//				.Set_Inhibited_Beliefs(inhibited_beliefs);
//	}

	public ArrayList<TStimulus> Get_Stimuli() {
		return this.List_Stimuli.Read_And_Clear();
	}
	
	public ArrayList<TStimulus> Get_Filtered_Stimuli() {
		return this.List_Filtered_Stimuli.Read_And_Clear();
	}

	public void Set_Stimuli(ArrayList<TStimulus> stimuli) {
		this.List_Stimuli.Set_List(stimuli);
//		this.Message_Handler.Write_Updated_Stimuli();
//		this.Broadcast();
		this.Broadcast_Signal(TType_Update_Contract.Updated_Stimuli);
	}
	
	public void Set_Filtered_Stimuli(ArrayList<TStimulus> filtered_stimuli) {
		this.List_Filtered_Stimuli.Set_List(filtered_stimuli);
//		this.Message_Handler.Write_Updated_Stimuli();
//		this.Broadcast();
		this.Broadcast_Signal(TType_Update_Contract.Updated_Filtered_Stimuli);
	}

	public void Add_Attentional_Desires(ArrayList<TAttentional_Desire> Desires) 
	{
		/**
		 * Before, I insert the Attentional_Desire in Long Memory
		 */
		this.Executive_Memory_Maintenance_Function.Get_LT_Memory_Maintenance()
				.Add_Attentional_Desires(Desires);
		/**
		 * Later, I insert the Attentional_Desire in Global Workspace
		 */
		this.List_Attentional_Desires.Add_All(Desires);
//		this.Message_Handler.Write_Updated_Standing_Desires();
//		this.Broadcast();
		this.Broadcast_Signal(TType_Update_Contract.Updated_Standing_Desires);
	}
	
	public void Add_Inhibited_Practical_Attentional_Desires_old(ArrayList<TAttentional_Desire> Desires, HashMap<String,ArrayList<TBelief>> Preconditions,
			HashMap<String,ArrayList<TBelief>> Useful_Beliefs_for_Desires, HashMap<String,ArrayList<TRegion>> Useful_Regions_for_Desires)

	{
//		/**
//		 * Before, I compute the precondition for any new Desires
//		 */
//		if(Preconditions == null)
//		{
//			Preconditions = new ArrayList<TBelief>();
//		}

		 
		/**
		 * Before, I insert the Attentional_Desire in Long Memory
		 */
		this.Executive_Memory_Maintenance_Function.Get_LT_Memory_Maintenance()
				.Add_Attentional_Desires(Desires);
		/**
		 * Before, I insert the Inhibited Desires in list of Inhibited Desires in Long Memory
		 */
		this.Executive_Memory_Maintenance_Function.Get_LT_Memory_Maintenance()
				.Add_Inhibited_Desire(Desires);
		/**
		 * Later, I insert the Attentional_Desire in Global Workspace
		 */
		this.List_Attentional_Desires.Add_All(Desires);
		//Qui
//		this.Add_Uninhibited_Preconditions(Preconditions);
//		this.Message_Handler.Write_Updated_Standing_Desires();
//		this.Broadcast();
		this.Broadcast_Signal(TType_Update_Contract.Updated_Standing_Desires);
	}
	
	public void Add_Inhibited_Practical_Attentional_Desires(
					ArrayList<TPractical_Desire_Data> Practical_Desires_Data )

	{
//		/**
//		 * Before, I compute the precondition for any new Desires
//		 */
//		if(Preconditions == null)
//		{
//			Preconditions = new ArrayList<TBelief>();
//		}
//		for(TPractical_Desire_Data Attentional_Desire_or_Stimulus_Data: Practical_Desires_Data)
//		{
//			this.Executive_Inhibition_Function.Register_Practical_Belief_Inhibition_Function(null, null);
//		}
		
		ArrayList<TAttentional_Desire> Desires = new ArrayList<TAttentional_Desire>();
		for(TPractical_Desire_Data Practical_Desire: Practical_Desires_Data)
		{
			Desires.add(Practical_Desire.Get_Practical_Desire() );
			
		}
		
		/**
		 * Before, I insert the Attentional_Desire in Long Memory
		 */
		this.Executive_Memory_Maintenance_Function.Get_LT_Memory_Maintenance()
				.Add_Inhibited_Practical_Desires(Practical_Desires_Data );
		/**
		 * Before, I insert the Inhibited Desires in list of Inhibited Desires in Long Memory
		 */
		this.Executive_Memory_Maintenance_Function.Get_LT_Memory_Maintenance()
				.Add_Inhibited_Desire(Desires);
		/**
		 * Later, I insert the Attentional_Desire in Global Workspace
		 */
//		this.List_Attentional_Desires.Add_All(Desires);
		//Qui
//		this.Add_Uninhibited_Preconditions(Preconditions);
//		this.Message_Handler.Write_Updated_Standing_Desires();
//		this.Broadcast();
		this.Broadcast_Signal(TType_Update_Contract.Updated_Standing_Desires);
	}

	public ArrayList<TBelief> Get_Preconditions()
	{
		return this.List_Preconditions.Read();
	}

	public void Broadcast_Signal(TType_Update_Contract Update_Message)
	{
		if (Update_Message != null) 
		{
			

		    // ATTENZIONE AGLI INDICI:
		    // [0] = getStackTrace() (il metodo interno che sta raccogliendo i dati)
		    // [1] = ChiSono() (questa funzione stessa)
		    // [2] = Chi ti ha chiamato (Il "Padre")
		    // [3] = Chi ha chiamato tuo padre (Il "Nonno")
			Contatore_di_Broadcast++;
			if ( this.General_CallBack != null)
			{
				this.General_CallBack.Execute( Update_Message, this);
			}
//			System.out.println("--------- Chianata: "+Contatore_di_Broadcast);
//			System.out.println(Update_Message);
//			StackTraceElement[] stack = Thread.currentThread().getStackTrace();
//		    if (stack.length > 2) {
//		        StackTraceElement chiamante = stack[2];
//		        System.out.println("2 - Mi ha chiamato il metodo: " + chiamante.getMethodName());
//		        System.out.println("Della classe: " + chiamante.getClassName());
//		        System.out.println("Alla riga: " + chiamante.getLineNumber());
//		    }
//
//		    if (stack.length > 3) {
//		        StackTraceElement nonno = stack[3];
//		        System.out.println("3 - Che a sua volta è stato chiamato da: " + nonno.getMethodName());
//		        System.out.println("Della classe: " + nonno.getClassName());
//		        System.out.println("Alla riga: " + nonno.getLineNumber());
//		    }
//		    if (stack.length > 4) {
//		        StackTraceElement nonno = stack[4];
//		        System.out.println("4 - Che a sua volta è stato chiamato da: " + nonno.getMethodName());
//		        System.out.println("Della classe: " + nonno.getClassName());
//		        System.out.println("Alla riga: " + nonno.getLineNumber());
//		    }
			switch (Update_Message) 
			{
				case Updated_Predicates:
					this.Message_Handler.Write_Value_Updated_Predicates();
					break;
				case Updated_Standing_Desires:
					this.Message_Handler.Write_Value_Updated_Standing_Desires();
					break;
				case Updated_Beliefs:
					this.Message_Handler.Write_Value_Updated_Beliefs();
					break;
				case Updated_Uninhibited_Data:
					this.Message_Handler.Write_Value_Updated_Unhinibited_Beliefs();
					break;
				case Updated_Active_Desires:
					this.Message_Handler.Write_Value_Updated_Active_Desires();
					break;
				case Updated_Active_Desires_with_Options:
					this.Message_Handler.Write_Value_Updated_Active_Desires_With_Options();
					break;
				case Updated_Selected_Intentions:
					this.Message_Handler.Write_Value_Updated_Selected_Intentions();
					break;
				case Updated_Stimuli:
					this.Message_Handler.Write_Value_Updated_Stimuli();
					break;
				case Updated_Thresholds:
					this.Message_Handler.Write_Value_Updated_Thresholds();
					break;
				case Internal_Signals:
					this.Message_Handler.Write_Value_Updated_Internal_Signals();
					break;
				case Updated_Perceptions:
					this.Message_Handler.Write_Value_Updated_Perceptions();
					break;
				case Updated_Satisfied_Desires:
					this.Message_Handler.Write_Value_Updated_Perceptions();
					break;
				case Updated_Critical_Beliefs:
					this.Message_Handler.Write_Value_Updated_Important_Beliefs();
					break;
				case Updated_Filtered_Stimuli:
					this.Message_Handler.Write_Value_Updated_Filtered_Stimuli();
					break;
				case Updated_Action_to_Execute:
					this.Message_Handler.Write_Value_Updated_Action_to_Execute();
					break;
				case Updated_Executed_Action:
					this.Message_Handler.Write_Value_Updated_Executed_Action();
					break;
			}
			this.Broadcast();
		}
	}
	
	public void Broadcast_Internal_Signals_Messages(TType_Update_Contract Update_Message,
			String Sender_Name, ArrayList<Object> data)
	{
		if (Update_Message != null) 
		{
			Contatore_di_Broadcast++;
//			System.out.println("--------- Chianata: "+Contatore_di_Broadcast);
//			System.out.println(Update_Message);
//			StackTraceElement[] stack = Thread.currentThread().getStackTrace();
//		    if (stack.length > 2) {
//		        StackTraceElement chiamante = stack[2];
//		        System.out.println("2 - Mi ha chiamato il metodo: " + chiamante.getMethodName());
//		        System.out.println("Della classe: " + chiamante.getClassName());
//		        System.out.println("Alla riga: " + chiamante.getLineNumber());
//		    }
//
//		    if (stack.length > 3) {
//		        StackTraceElement nonno = stack[3];
//		        System.out.println("3 - Che a sua volta è stato chiamato da: " + nonno.getMethodName());
//		        System.out.println("Della classe: " + nonno.getClassName());
//		        System.out.println("Alla riga: " + nonno.getLineNumber());
//		    }
//		    if (stack.length > 4) {
//		        StackTraceElement nonno = stack[4];
//		        System.out.println("4 - Che a sua volta è stato chiamato da: " + nonno.getMethodName());
//		        System.out.println("Della classe: " + nonno.getClassName());
//		        System.out.println("Alla riga: " + nonno.getLineNumber());
//		    }
			switch (Update_Message) 
			{
				case Internal_Signals_Abort_Option:
					this.Message_Handler.
						Add_Value_Updated_Internal_Signals_Abort_Option_Message(Sender_Name, data);
					break;
				case Internal_Signals_Delete_Intention:
//					this.Message_Handler.Write_Value_Updated_Standing_Desires();
					this.Message_Handler.
						Add_Value_Updated_Internal_Signals_Delete_Intention_Message(Sender_Name, data);
					break;
				case Internal_Signals_Satisfied_Intention:
//					this.Message_Handler.Write_Value_Updated_Beliefs();
//					this.Message_Handler.Write_Value_Updated_Satisfied_Desires();
					this.Message_Handler.
						Add_Value_Updated_Internal_Signals_Satisfied_Intention_Message(Sender_Name, data);
					break;
			}
			this.Broadcast();
//			System.out.println("Satisfied message sended");
//			System.out.println(this.Get_Satisfied_Attentional_Desires_from_MM());
		}
	}

	public ArrayList<TIntention> Get_Selected_Intentions() 
	{
		return this.List_Selected_Intentions.Read();
	}

	public void Set_Selected_Intentions(ArrayList<TIntention> selected_intentions) 
	{
//		this.List_Selected_Intentions.Set_List(selected_intentions);
		this.Set_Intentions( selected_intentions );
		this.Broadcast_Signal(TType_Update_Contract.Updated_Selected_Intentions);
	}
	
	public ArrayList<TBelief> Get_Important_Beliefs()
	{
		return this.List_Important_Beliefs.Read();
	}
	
	public void Set_Important_Beliefs( ArrayList<TBelief> beliefs )
	{
		this.List_Important_Beliefs.Set_List( beliefs );
	}
	
	public void Set_Preconditions( ArrayList<TBelief> beliefs )
	{
		this.List_Preconditions.Set_List( beliefs );
	}
	
	public void Set_Uninhibited_Conscious_Data(ArrayList<TBelief> Pre_Conditions, ArrayList<TBelief> Uninhibited_Beliefs,
			ArrayList<TAttentional_Desire> Uninhibited_Desires,
			ArrayList<TRegion> Uninhibited_Regions ) 
	{
		this.Write_Lock_Uninhibited_Data.lock();
		try 
		{
			
			this.List_Uninhibited_Beliefs.Clear();
			this.List_Uninhibited_Regions.Clear();
			
			this.Write_Lock_Uninhibited_Preconditions.lock();
			try
			{
				this.List_Preconditions.Clear();
				this.List_Preconditions.Add_All( Pre_Conditions );
			}
			finally 
			{
				Write_Lock_Uninhibited_Preconditions.unlock();
			}
//			this.List_Preconditions.Clear();
//			this.List_Preconditions.Add_All( Pre_Conditions );
			this.List_Uninhibited_Beliefs.Add_All( Uninhibited_Beliefs );
			this.List_Uninhibited_Regions.Add_All( Uninhibited_Regions );
			
			this.Map_Uninhibited_Rgions.clear();
			for(TRegion Region: Uninhibited_Regions)
			{
				this.Map_Uninhibited_Rgions.put(Region.Get_Name(), Region);
			}
			
//			for(TBelief Belief: this.List_Uninhibited_Beliefs.Read())
			this.Map_Uninhibited_Beliefs.clear();
			for(TBelief Belief: Uninhibited_Beliefs)
			{
				this.Map_Uninhibited_Beliefs.put(Belief.Get_Name(), Belief);
			}
			
//			if ( this.Call_Back != null)
//			{
//				this.Call_Back.Execute(
//						Pre_Conditions, Uninhibited_Beliefs,
//						Uninhibited_Desires, Uninhibited_Regions, this);
//			}
			
			/**
			 * I write the List_Uninhibited_Desires. It uses Write_Lock_Unhinibited_Desires each time
			 * it has to change this list, because in other section of the code the ReentrantReadWriteLock
			 * "Write_Lock_Unhinibited_Desires" is used.
			 */
			this.Write_Lock_Unhinibited_Desires.lock();
			///
			try 
			{
				this.List_Uninhibited_Desires.Clear();
				this.List_Uninhibited_Desires.Add_All( Uninhibited_Desires );
			} 
			finally 
			{
				this.Write_Lock_Unhinibited_Desires.unlock();
			}
			this.Uninhibited_Consious_Data.Write( Pre_Conditions, Uninhibited_Beliefs, 
													Uninhibited_Desires, Uninhibited_Regions);
			
			if ( this.Call_Back != null)
			{
				this.Call_Back.Execute(
						Pre_Conditions, Uninhibited_Beliefs,
						Uninhibited_Desires, Uninhibited_Regions, this);
			}

			this.Broadcast_Signal(TType_Update_Contract.Updated_Uninhibited_Data);
		} 
		finally 
		{
			Write_Lock_Uninhibited_Data.unlock();
		}
	}
	
	private void Add_Uninhibited_Conscious_Data(
			ArrayList<TBelief> Pre_Conditions, 
			ArrayList<TBelief> Uninhibited_Beliefs,
			ArrayList<TAttentional_Desire> Uninhibited_Desires,
			ArrayList<TRegion> Uninhibited_Regions ) 
	{
		//This is already setted by the function caller "Mark_as_Active_Desires"
//		this.Write_Lock_Uninhibited_Data.lock();
		try 
		{
			this.Write_Lock_Uninhibited_Preconditions.lock();
			try
			{
				this.List_Preconditions.Add_All( Pre_Conditions );
			}
			finally 
			{
				Write_Lock_Uninhibited_Preconditions.unlock();
			}
					
			this.List_Uninhibited_Beliefs.Add_All( Uninhibited_Beliefs );
			this.List_Uninhibited_Desires.Add_All( Uninhibited_Desires );
			this.List_Uninhibited_Regions.Add_All( Uninhibited_Regions );
			
			this.Map_Uninhibited_Rgions.clear();
			for(TRegion Region: Uninhibited_Regions)
			{
				this.Map_Uninhibited_Rgions.put(Region.Get_Name(), Region);
			}
			
			this.Map_Uninhibited_Beliefs.clear();
//			this.Map_Uninhibited_Beliefs = this.List_Uninhibited_Beliefs.Read().stream()
//				    .collect(Collectors.toMap(
//				        TBelief::Get_Name,          // 1. Chiave
//				        belief -> belief,           // 2. Valore
//				        (esistente, nuovo) -> nuovo, // 3. Gestione duplicati (l'ultimo vince)
//				        HashMap::new                // 4. FORZA la creazione di una HashMap
//				    ));
//			for(TBelief Belief: this.List_Uninhibited_Beliefs.Read())
			for(TBelief Belief: Uninhibited_Beliefs)
			{
				this.Map_Uninhibited_Beliefs.put(Belief.Get_Name(), Belief);
			}
			
//			this.Map_Uninhibited_Rgions.clear();
//			for(TRegion Region: this.List_Uninhibited_Regions.Read())
//			{
//				this.Map_Uninhibited_Rgions.put(Region.Get_Name(), Belief);
//			}
			
			this.Uninhibited_Consious_Data.Write( Pre_Conditions, Uninhibited_Beliefs, 
					Uninhibited_Desires, Uninhibited_Regions);
			
			this.Executive_Memory_Maintenance_Function.Get_LT_Memory_Maintenance().
				Remove_Inhibited_Data( Uninhibited_Desires, Uninhibited_Beliefs, Uninhibited_Regions);
			
			this.Broadcast_Signal(TType_Update_Contract.Updated_Uninhibited_Data);
		} 
		finally 
		{
//			Write_Lock_Uninhibited_Data.unlock();
		}
	}
	
	/**
	 * this is used inside the Mark_as_Satisfied_Desires is called
	 * @param Pre_Conditions
	 * @param Uninhibited_Beliefs
	 * @param Uninhibited_Desires
	 * @param Uninhibited_Regions
	 */
	private void Remove_Uninhibited_Conscious_Data(ArrayList<TBelief> Pre_Conditions, ArrayList<TBelief> Uninhibited_Beliefs,
			ArrayList<TAttentional_Desire> Uninhibited_Desires,
			ArrayList<TRegion> Uninhibited_Regions ) 
	{
		this.Write_Lock_Uninhibited_Data.lock();
		try 
		{
			this.Write_Lock_Uninhibited_Preconditions.lock();
			try
			{
				this.List_Preconditions.Remove_All( Pre_Conditions );
			}
			finally 
			{
				Write_Lock_Uninhibited_Preconditions.unlock();
			}
			
//			this.List_Preconditions.Remove_All( Pre_Conditions );
			this.List_Uninhibited_Beliefs.Remove_All( Uninhibited_Beliefs );
			this.List_Uninhibited_Regions.Remove_All( Uninhibited_Regions );
			
			this.Map_Uninhibited_Rgions.clear();
			for(TRegion Region: Uninhibited_Regions)
			{
				this.Map_Uninhibited_Rgions.put(Region.Get_Name(), Region);
			}
			
			this.Map_Uninhibited_Beliefs.clear();
//			for(TBelief Belief: this.List_Uninhibited_Beliefs.Read())
			for(TBelief Belief: Uninhibited_Beliefs)
			{
				this.Map_Uninhibited_Beliefs.put(Belief.Get_Name(), Belief);
			}
			
			/**
			 * I write the List_Uninhibited_Desires. It uses Write_Lock_Unhinibited_Desires each time
			 * it has to change this list, because in other section of the code the ReentrantReadWriteLock
			 * "Write_Lock_Unhinibited_Desires" is used.
			 */
			this.Write_Lock_Unhinibited_Desires.lock();
			this.List_Uninhibited_Desires.Clear();
			///
			try 
			{
				this.List_Uninhibited_Desires.Add_All( Uninhibited_Desires );
			} 
			finally 
			{
				this.Write_Lock_Unhinibited_Desires.unlock();
			}
			
			this.Broadcast_Signal(TType_Update_Contract.Updated_Uninhibited_Data);
		} 
		finally 
		{
			Write_Lock_Uninhibited_Data.unlock();
		}
	}
	
	public void Set_Inhibited_Conscious_Data(ArrayList<TBelief> Inhibited_Beliefs,
			ArrayList<TAttentional_Desire> Inhibited_Desires,
			ArrayList<TRegion> Inhibited_Regions ) 
	{
		this.Executive_Memory_Maintenance_Function.Get_LT_Memory_Maintenance().Set_Inhibited_Data(
				Inhibited_Desires, Inhibited_Beliefs, Inhibited_Regions);
	}
	
	public ArrayList<TGreen_Desire> Get_All_Green_Desires_from_MM()
	{
		return this.Executive_Memory_Maintenance_Function.Get_LT_Memory_Maintenance().Get_All_Green_Desires();
	}
	
	public ArrayList<TGreen_Desire> Get_Green_Desires_by_Names_from_MM(ArrayList<String> Green_Names)
	{
		return this.Executive_Memory_Maintenance_Function.Get_LT_Memory_Maintenance().Get_Green_Desires_by_Names( Green_Names );
	}
	
	public ArrayList<TQuality_Desire> Get_All_Quality_Desiresfrom_MM()
	{
		return this.Executive_Memory_Maintenance_Function.Get_LT_Memory_Maintenance().Get_All_Quality_Desires();	
	}
	
	public ArrayList<TQuality_Desire> Get_Quality_Desires_by_Names_from_MM(ArrayList<String> Quality_Names)
	{
		return this.Executive_Memory_Maintenance_Function.Get_LT_Memory_Maintenance().Get_Quality_Desires_by_Names( Quality_Names );	
	}
			
	public ArrayList<TAttentional_Desire> Get_Satisfied_Attentional_Desires_from_LTM()
	{
		return this.Executive_Memory_Maintenance_Function.Get_LT_Memory_Maintenance().Get_Satisfied_Attentional_Desires();
		
		
	}
	
	/**
	 * 
	 * @param Beliefs_to_Change
	 */
	public void Update_Uninhibited_Conscious_Beliefs(
			HashMap<String, Object> Beliefs_to_Change) 
	{
		if( Beliefs_to_Change.size() > 0)
		{
			this.Write_Lock_Uninhibited_Data.lock();
			Boolean In_List_Importan_Belief = false;
			try 
			{
				ArrayList<TBelief> Temp_Important_Belief = this.List_Important_Beliefs.Read();
				/**
				 * HashMap<String, Object> Beliefs_To_Change = new HashMap<String, Object>();
				 * Beliefs_To_Change.put(Belief_Name,
				 *  			new ArrayList<>(Arrays.asList("Subject or Object_Complement", Object_Value)));
				 */
				HashSet<String> temp_Belief_Name = new HashSet<String>();
				temp_Belief_Name.addAll(Beliefs_to_Change.keySet());
				HashMap<String, TBelief> temp_Map_Belief= new HashMap<String, TBelief>();
				temp_Map_Belief.putAll( this.Get_Selected_Beliefs_from_LTM( temp_Belief_Name ) );
				this.Map_Uninhibited_Beliefs.putAll( temp_Map_Belief );
				
				
				for(String Belief_Name: Beliefs_to_Change.keySet())
				{
					TBelief Belief = this.Map_Uninhibited_Beliefs.get( Belief_Name );
	//				if( Type == "Subject")
					if(Belief ==null)
					{
						System.out.println("Error when Update_Uninhibited_Conscious_Beliefs on Belief_Name: ");
						System.out.println("Belief_Name: "+Belief_Name);
					}
					else
					{
						ArrayList<Object> Object_Value_and_Type = 
								(ArrayList<Object>) Beliefs_to_Change.get( Belief_Name ) ;
						String Type = (String) Object_Value_and_Type.get( 0 );
						Object Value = Object_Value_and_Type.get( 1 );
						if( Type.equals("Subject") )
						{
							Belief.Get_Predicate().Set_Subject(Object_Value_and_Type.get( 1 ));
						}
						else
						// Type = "Object_Complement"
						{
	//						if(Belief ==null)
	//						{
	//							System.out.println("Error when Update_Uninhibited_Conscious_Beliefs on Belief_Name: ");
	//							System.out.println("Belief_Name: "+Belief_Name);
	//						}
							Belief.Get_Predicate().set_Object_Complement( Object_Value_and_Type.get( 1 ) );
						}
						Belief.Set_Truth(true);
						Belief.Set_Time_Stamp( LocalDateTime.now() ); 
						In_List_Importan_Belief = 
								In_List_Importan_Belief || Temp_Important_Belief.contains( Belief );
					}
				}
				
	//			this.Uninhibited_Consious_Data.Write( 
	//				this.List_Preconditions.Read(), this.List_Uninhibited_Beliefs.Read(), 
	//				this.List_Uninhibited_Desires.Read(), this.List_Uninhibited_Regions.Read() );
				ArrayList<TBelief> temp_belief_2 = new ArrayList<TBelief>();
				temp_belief_2.addAll( this.Map_Uninhibited_Beliefs.values() );
				this.Uninhibited_Consious_Data.Write( 
						this.List_Preconditions.Read(), temp_belief_2, 
						this.List_Uninhibited_Desires.Read(), this.List_Uninhibited_Regions.Read() );
	
				
				this.Broadcast_Signal(TType_Update_Contract.Updated_Beliefs);
				if( In_List_Importan_Belief )
				{
					this.Broadcast_Signal(TType_Update_Contract.Updated_Critical_Beliefs);				
				}
				
			} 
			finally 
			{
				Write_Lock_Uninhibited_Data.unlock();
			}
		}
	}
	
	public HashMap<String, TBelief> Get_Map_Uninhibited_Beliefs()
	{
		HashMap<String, TBelief> result = new HashMap<String, TBelief>();
//		for(TBelief Belief: this.Map_Uninhibited_Beliefs.values())
//		{
////			TBelief Clone_Belief = Belief.Clone();
////			result.put(Clone_Belief.Get_Name(), Clone_Belief);
//			result.put(Belief.Get_Name(), Belief);
//		}
		if(this.Map_Uninhibited_Beliefs ==null)
		{
			int oo = 2;
		}
		if(this.Map_Uninhibited_Beliefs.containsKey(null))
		{
			int oo = 2;
		}
		if(this.Map_Uninhibited_Beliefs.containsValue(null))
		{
			int oo = 2;
		}
		result.putAll( this.Map_Uninhibited_Beliefs );
		return result;
	}
	
	public HashMap<String, TRegion> Get_Map_Uninhibited_Regions()
	{
		HashMap<String, TRegion> result = new HashMap<String, TRegion>();
//		for(TRegion Region: this.Map_Uninhibited_Rgions.values())
//		{
////			TRegion Clone_Region= Region.Clone();
////			result.put(Clone_Region.Get_Name(), Clone_Region);
//			result.put(Belief.Get_Name(), Belief);
//		}
		result.putAll( this.Map_Uninhibited_Rgions );
		return result;
	}
	
	@FunctionalInterface
    public interface TCallBack_Set_Generate_Unhinibited_Regions_for_Intention
    {
		void Execute( 
				ArrayList<TBelief> Pre_Conditions, 
				ArrayList<TBelief> Uninhibited_Beliefs,
				ArrayList<TAttentional_Desire> Uninhibited_Desires,
				ArrayList<TRegion> Uninhibited_Regions,
				TGlobal_Workspace Global_Workspace );
    }
	
	@FunctionalInterface
    public interface TGeneral_CallBack
    {
		void Execute( TType_Update_Contract Update_Message,  
				TGlobal_Workspace Global_Workspace );
    }
	
    private TCallBack_Set_Generate_Unhinibited_Regions_for_Intention Call_Back = null;
    private TGeneral_CallBack General_CallBack = null;
    
    public void Set_CallBack_Set_Generate_Unhinibited_Regions_for_Intention( 
    		TCallBack_Set_Generate_Unhinibited_Regions_for_Intention Func )
	{
		this.Call_Back = Func;
	}
    
    public void Set_General_CallBack( 
    		TGeneral_CallBack Func )
	{
		this.General_CallBack = Func;
	}
    
    public void Add_Uninhibited_Preconditions(HashSet<TBelief> Pre_Conditions)
    {
    	if(Pre_Conditions != null) 
    	{
    		if(Pre_Conditions.size()>0)
        	{
        		this.Write_Lock_Uninhibited_Preconditions.lock();
        		try
        		{
        			this.List_Preconditions.Add_All( Pre_Conditions );
        		}
        		finally 
        		{
        			Write_Lock_Uninhibited_Preconditions.unlock();
        		}
        		this.Broadcast_Signal(TType_Update_Contract.Updated_Preconditions);
        	}    		
    	}
    	
    }
    
    public HashMap<String, TBelief> Get_Selected_Beliefs_from_LTM(HashSet<String> Beliefs_Names)
	{
    	return this.Executive_Memory_Maintenance_Function.Get_LT_Memory_Maintenance().
    				Get_Selected_Beliefs( Beliefs_Names );
	}
    
    public HashMap<String, TRegion> Get_Selected_Regions_from_LTM(HashSet<String> Regions_Names)
	{
    	return this.Executive_Memory_Maintenance_Function.Get_LT_Memory_Maintenance().
    				Get_Selected_Regions( Regions_Names );
	}
    
    public HashMap<String, TRegion>  Get_All_Map_Regions_from_LTM()
	{
		return this.Executive_Memory_Maintenance_Function.Get_LT_Memory_Maintenance().Get_All_Map_Regions();
	}
    
    private TDesire_Data Recall_Inhibited_Data_for_Activated_Inhibited_Desires(
    		HashSet<TAttentional_Desire> Desires_To_Promote)
	{
    	TDesire_Data result = new TDesire_Data();
    	HashSet<String> Precondition_Names = new HashSet<String>();
    	HashSet<String> Beliefs_Names = new HashSet<String>();
    	HashSet<String> Regions_Names = new HashSet<String>();
    	
		HashSet<TBelief> Uninhibited_Beliefs = new HashSet<TBelief>();
		HashSet<TBelief> Pre_conditions= new HashSet<TBelief>();

		for (TAttentional_Desire Attentional_Desire: Desires_To_Promote)
		{
			if(Attentional_Desire==null)
			{
				int oo =2;
			}
			switch(Attentional_Desire)
			{
				// PRACTICAL DESIRES
				case TPractical_Desire Practical_Desire ->
				{
					Precondition_Names.addAll( Practical_Desire.Get_Trigger_Condition().Get_Beliefs_Names() );
				}
				//EPISTEMIC DESIRES
				case TEpistemic_Desire Epistemic_Desire ->
				{
					Precondition_Names.add( Epistemic_Desire.Get_Belief_Name() );
				}
				default -> throw new IllegalArgumentException("Unexpected value: " + Attentional_Desire);
			}
			
			Beliefs_Names.addAll( Attentional_Desire.Get_Beliefs_Name_for_Reasoner() );
			Regions_Names.addAll( Attentional_Desire.Get_Regions_Name_for_Reasoner() );
			

//			//I insert the Constraint of each Green Desires of the Practical Desire
//			for(TGreen_Desire Green_Desire: Attentional_Desire.Get_List_Green_Standing_Desire())
//			{
//				Uninhibited_Beliefs.add( Green_Desire.Get_Constraint().Get_Linked_Belief());
//			}
//			
//			//I insert the Constraint of each Quality Desires of the Practical Desire
//			for(TQuality_Desire Quality_Desire: Attentional_Desire.Get_List_Quality_Standing_Desire())
//			{
//				Uninhibited_Beliefs.add( Quality_Desire.Get_Constraint().Get_Linked_Belief());
//			}
			
		}
		HashSet<TBelief> Temp_Beliefs = new HashSet<TBelief>();
		Temp_Beliefs.addAll(this.Get_Selected_Beliefs_from_LTM( Precondition_Names ).values());
		result.Set_Preconditions( Temp_Beliefs );
		
		Temp_Beliefs.clear();
		Temp_Beliefs.addAll( this.Get_Selected_Beliefs_from_LTM( Beliefs_Names ).values() );
		result.Set_Beliefs( Temp_Beliefs);
		
		//result.Set_Regions(null);
		
		return result;
	}
    
    public void Associate_Functions_to_Inhibited_Desires(
    		ArrayList<TPractical_Desire_Data> Desires_Data)
    {
    	for(TPractical_Desire_Data Practical_Desire_Data: Desires_Data)
    	{
    		/**
			 * Preliminary Actions for each Practical Desire:
			 * 1) I have to associate functions:
			 * 		- Means-End Reasoner function for the Active Desires
			 * 		- A Inhibition Function to compute Inhibited Beliefs
			 * 		- A Inhibition Function to compute Inhibited Regions
			 */
    		
    		/*
    		 * Means-End Reasoner function for the Active Desires
    		 * 
    		 */
    		TPractical_Desire Practical_Desire  = Practical_Desire_Data.Get_Practical_Desire();
    		this.Executive_Reasoner_Function.Register_Practical_Function(
    				Practical_Desire.Get_Name(), 
    						Practical_Desire_Data.Get_Means_End_Reasoner_Function() );
    		
    		/*
    		 * Inhibition Function to compute Inhibited Beliefs.
    		 * In this Version 0.3.2, we have only one TBelief_Inhibition_Function
    		 * for each Attentional Desire
    		 * 
    		 */
    		for(TBelief_Inhibition_Function func: Practical_Desire_Data.Get_Beliefs_Inhibition_Functions())
    		{
    			this.Executive_Inhibition_Function.Register_Practical_Belief_Inhibition_Function(
        				Practical_Desire.Get_Name(), func);
    		}
    		
    		/*
    		 * Inhibition Function to compute Inhibited Beliefs.
    		 * In this Version 0.3.2, we have only one TRegion_Inhibition_Function
    		 * for each Attentional Desire.
    		 * 
    		 */
    		for(TRegion_Inhibition_Function func: Practical_Desire_Data.Get_Regions_Inhibition_Functions() )
    		{
    			this.Executive_Inhibition_Function.Register_Practical_Region_Inhibition_Function(
    					Practical_Desire.Get_Name(), func);
    		}
    	}
    }
    
    public Boolean Insert_Plan_in_Plan_Library(TIntention Intention)
	{
    	return this.Plan_Library.Create_Plan( Intention );
	}
    
    public ArrayList<TAction> Get_Plans_from_Plan_Library(TAttentional_Desire Attentional_Desire,
			ArrayList<TPredicate> Preconditions 
			)
	{
    	return this.Plan_Library.Get_Plans( Attentional_Desire, Preconditions );
	}
    
    public HashMap<String, TBelief> Recal_Beliefs(HashSet<String> Beliefs_Names)
    {
    	
    	HashMap<String, TBelief> result = new HashMap<String, TBelief>();
    	this.Write_Lock_Uninhibited_Data.lock();
		try 
		{
			
//			this.Write_Lock_Uninhibited_Preconditions.lock();
//			try
//			{
//				this.List_Preconditions.Add_All( Pre_Conditions );
//			}
//			finally 
//			{
//				Write_Lock_Uninhibited_Preconditions.unlock();
//			}
			ArrayList<TBelief> Uninhibited_Beliefs = new ArrayList<TBelief>();
			Uninhibited_Beliefs.addAll(
					this.Get_Selected_Beliefs_from_LTM( Beliefs_Names ).values() );
			
					
			this.List_Uninhibited_Beliefs.Add_All( Uninhibited_Beliefs );
//			this.List_Uninhibited_Desires.Add_All( Uninhibited_Desires );
//			this.List_Uninhibited_Regions.Add_All( Uninhibited_Regions );
			
//			this.Map_Uninhibited_Rgions.clear();
//			for(TRegion Region: Uninhibited_Regions)
//			{
//				this.Map_Uninhibited_Rgions.put(Region.Get_Name(), Region);
//			}
			
			this.Map_Uninhibited_Beliefs.clear();
//			this.Map_Uninhibited_Beliefs = this.List_Uninhibited_Beliefs.Read().stream()
//				    .collect(Collectors.toMap(
//				        TBelief::Get_Name,          // 1. Chiave
//				        belief -> belief,           // 2. Valore
//				        (esistente, nuovo) -> nuovo, // 3. Gestione duplicati (l'ultimo vince)
//				        HashMap::new                // 4. FORZA la creazione di una HashMap
//				    ));
//			for(TBelief Belief: this.List_Uninhibited_Beliefs.Read())
			for(TBelief Belief: this.List_Uninhibited_Beliefs.Read())
			{
				this.Map_Uninhibited_Beliefs.put(Belief.Get_Name(), Belief);
			}
			
//			this.Map_Uninhibited_Rgions.clear();
//			for(TRegion Region: this.List_Uninhibited_Regions.Read())
//			{
//				this.Map_Uninhibited_Rgions.put(Region.Get_Name(), Belief);
//			}
			
			this.Uninhibited_Consious_Data.Write( 
					this.List_Preconditions.Read(), 
					this.List_Uninhibited_Beliefs.Read(), 
					this.List_Uninhibited_Desires.Read(), 
					this.List_Uninhibited_Regions.Read());
			
			this.Executive_Memory_Maintenance_Function.Get_LT_Memory_Maintenance().
				Remove_Inhibited_Data( 
						this.List_Uninhibited_Desires.Read(), 
						this.List_Uninhibited_Beliefs.Read(), 
						this.List_Uninhibited_Regions.Read());
			
			
			result.putAll( this.Map_Uninhibited_Beliefs );
			
//			this.Broadcast_Signal(TType_Update_Contract.Updated_Uninhibited_Data);
		} 
		finally 
		{
			Write_Lock_Uninhibited_Data.unlock();
		}
    	
		return result;
    	
    }
	
}
