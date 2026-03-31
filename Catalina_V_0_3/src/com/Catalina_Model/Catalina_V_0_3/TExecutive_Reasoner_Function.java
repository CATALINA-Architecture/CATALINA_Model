package com.Catalina_Model.Catalina_V_0_3;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import com.Catalina_Model.Catalina_V_0_3.TExecutive_Reasoner_Function.TDeliberation_Process_Function;
import com.Catalina_Model.Catalina_V_0_3.TExecutive_Reasoner_Function.TMeans_End_Reasoner_Generate_Options_for_Practical_Desire;
import com.Catalina_Model.Catalina_V_0_3.TExecutive_Reasoner_Function.TQuality_Desires_Filter_Function;
import com.Catalina_Model.Catalina_V_0_3.TGreen_Desires_Filter_Function_Handler.Green_Desires_Filter_Function;
import com.Catalina_Model.Catalina_V_0_3.TMeans_End_Epistemic_Functions_Handler.Means_End_Epistemic_Function;
import com.Catalina_Model.Catalina_V_0_3.TMeans_End_Practical_Functions_Handler.Means_End_Practical_Function;

class TDesire_Compare implements Comparator<TAttentional_Desire> 
{
    @Override
    public int compare(TAttentional_Desire Desire0, TAttentional_Desire Desire1) 
    {
    	double w0 = Desire0.Get_Saliency();
    	double w1 = Desire1.Get_Saliency();        

        return (w0 > w1? -1 : (w0 == w1) ? 0 : 1);
    }
}

/**
 * The Reasoner is the equivalent or the Practical Reasoner of Bratman.
 * It uses 3 modules: the Means_end_Reasoner, the Filtering_Process and the Deliberation_Process.
 * It creates options for each active desire, and filters this option in accordance to
 * green and quality desires related to the considered active desire. Finally, it deliberates for
 * the selected intentions to pursue.
 * .........................
 * The MEANS_END_REASONER
 * .........................
 * The Means_end_Reasoner generates options for each attentional desires (practical and epistemic) 
 * It uses the function "Generate_Options_for_Practical_Desire"
 * to generate options for Practical Desire. For each practical desire, this function will be called.
 * this function need to be set by using "Set_Generate_Options_for_Practical_Desire".
 * 
 * Otherwise, to generate options for epistemic desires, the Means_end_Reasoner uses an handler
 * "TMeans_End_Reasoner_Generate_Options_for_Practical_Desire". For each Epistemic Desire, to compute
 * all options, this handles use a function associated to the type of belief of the Epistemic Desire.
 * Each function associated to a type of belief needs to set by using "Register_Epistemic_Function" 
 * and "Unregister_Epistemic_Function" to remove the function. 
 * 
 * .........................
 * The FILTERING_PROCESS
 * .........................
 * The Filtering_Process filters the options of current active desire by using green desires and quality desires related to
 * the current active desire, for each desire.
 * It use the handler "Green_Desires_Filter_Function_Handler" to apply the filters of the related list of
 * green desires. this handler has a function associated to each green desire, and execute this function
 * each time a green desire filters the options of the active desire. To set a function, it needs to
 * use Register_Green_Filtering_Function and Unregister_Green_Filtering_Function.
 * So, it use a function for a green desire: one to one way.
 * 
 * Instead, to filter option by quality desires, this module uses the function
 * "Quality_Desires_Filter_Function". It is called one time for each active desire, and it needs to be 
 * set by using  "Set_Quality_Desire_Filtering_Process". This function will be called for each
 * active desire and generates ordered options in accordance with the quality desires related to the 
 * current active desire.
 * 
 * .........................
 * The DELIBERATION PROCESS
 * .........................
 * 
 * The Deliberation_Process deliberates the selected intentions to pursue.
 * It uses a function "Deliberation_Process_Function" to deliberate. It acquires all active desires,
 * with filtered options. This function needs to be set by using "Set_Deliberation_Process_Function".
 */

public class TExecutive_Reasoner_Function extends TAgent_Base_Thread
{
	private TGlobal_Workspace Global_Workspace;
	
	private TFiltering_Process Filtering_Process;
	private TFinally_Operator Finally_Operator;
	private TMeans_End_Reasoner Means_End_Reasoner;
	private TDeliberation_Process Deliberation_Process;
	
	private ArrayList<TAttentional_Desire> Active_Desires;
	private ArrayList<TAttentional_Desire> Desires_To_Filter;
	private ArrayList<TBelief> Beliefs;
	private HashMap<String, TBelief> Map_Beliefs;
	private ArrayList<TRegion> Regions;
	private HashMap<String, TRegion> Map_Regions;
	private ArrayList<TIntention> Intentions;
	
	private HashMap<String, HashSet<String>> List_Beliefs_Names_for_Desires;
	private HashMap<String, HashSet<String>> List_Regions_Names_for_Desires;
	
	/**
	 * TQuality_Desires_Filter_Function is used in TFiltering Process
	 */
	@FunctionalInterface
    public interface TQuality_Desires_Filter_Function
    {
		ArrayList<TOption> Execute(TAttentional_Desire Desire, ArrayList<TBelief> beliefs, 
        								ArrayList<TRegion> regions, ArrayList<TIntention> intentions);
    }
	
	/**
	 * TQuality_Desires_Filter_Function is used in TMeans_End_Reasoner
	 */
	@FunctionalInterface
    public interface TMeans_End_Reasoner_Generate_Options_for_Practical_Desire
    {
		ArrayList<TOption> Execute( TPractical_Desire Practical_Desire, HashMap<String, TBelief> beliefs, 
				HashMap<String, TRegion> regions, ArrayList<TIntention> intentions,
									TMeans_End_Reasoner_Data_Getter Means_End_Reasoner_Data_Getter);
    }
	
	/**
	 * TDeliberation_Process_Function is used in TDeliberation_Process
	 */
	@FunctionalInterface
    public interface TDeliberation_Process_Function
    {
		ArrayList<TIntention> Execute( ArrayList<TAttentional_Desire> Active_Desires, HashMap<String, TBelief> beliefs, 
				HashMap<String, TRegion> regions, ArrayList<TIntention> intentions);
    }
	
	public TExecutive_Reasoner_Function(TAgent agent) 
	{
	
		super(agent, "Executive Reasoner Function");
		
		this.Global_Workspace = agent.Get_Global_WorkSpace();
		
		this.Filtering_Process = new TFiltering_Process( this );
		this.Finally_Operator = new TFinally_Operator( this );
		this.Means_End_Reasoner = new TMeans_End_Reasoner( this );
		this.Deliberation_Process = new TDeliberation_Process( this );
		
		this.Active_Desires = new ArrayList<TAttentional_Desire>();
		this.Desires_To_Filter = new ArrayList<TAttentional_Desire>();
		this.Beliefs = new ArrayList<TBelief>();
		this.Map_Beliefs = new HashMap<String, TBelief>();
		this.Intentions = new ArrayList<TIntention>();
		this.Regions = new ArrayList<TRegion>();
		this.Map_Regions = new HashMap<String, TRegion>(); 
		
		this.List_Beliefs_Names_for_Desires = new HashMap<String, HashSet<String>>();
		this.List_Regions_Names_for_Desires = new HashMap<String, HashSet<String>>();
	}

	@Override
	public void Insert_in_List_Update_Contract()
	{
		this.Agent.Get_Global_WorkSpace().Insert_for_Update_Contract(TType_Update_Contract.Updated_Active_Desires, this);
		this.Agent.Get_Global_WorkSpace().Insert_for_Update_Contract(TType_Update_Contract.Updated_Beliefs, this);
		this.Agent.Get_Global_WorkSpace().Insert_for_Update_Contract(TType_Update_Contract.Updated_Uninhibited_Data, this);
		this.Agent.Get_Global_WorkSpace().Insert_for_Update_Contract(TType_Update_Contract.Updated_Selected_Intentions, this);
	}
	
	@Override
	public void Execute()
	{
//		while (this.Read_Continue_Thread() )
		{
			Boolean Change_Active_Desires = this.Message_Handler.Read_Value_And_Clear_Updated_Active_Desires();
			
			
			if (Change_Active_Desires)
			{
				ArrayList<TIntention> Selected_Intentions = new ArrayList<TIntention>();
				
				//I clear and update some lists
				Selected_Intentions.clear();
				this.Desires_To_Filter.clear();
				
				this.Active_Desires.clear();
				this.Active_Desires.addAll( Global_Workspace.Get_Unhinibited_Active_Desires() );
//				this.Active_Desires.addAll( Global_Workspace.Get_Unhinibited_Desires() );
				
				if (this.Message_Handler.Read_Value_And_Clear_Updated_Beliefs() ||
						this.Message_Handler.Read_Value_And_Clear_Updated_Unhinibited_Data())
				{
					this.Beliefs.clear();
					//this.Beliefs.addAll( this.Global_Workspace.Get_All_Beliefs_from_MM());
					this.Beliefs.addAll( this.Global_Workspace.Get_UnInhibited_Beliefs());
					
					this.Map_Beliefs.clear();
					this.Map_Beliefs.putAll( this.Global_Workspace.Get_Map_Uninhibited_Beliefs() );
					
					/**
					 * This ensures all beliefs for each Desires.
					 * The developer is responsible for each lists 
					 */
					this.Map_Beliefs.putAll( this.Ensures_Beliefs_for_Desires( Active_Desires ));
					
					
					this.Regions.clear();
					this.Regions.addAll( this.Global_Workspace.Get_UnInhibited_Regions());
					
					this.Map_Regions.clear();
					this.Map_Regions.putAll( this.Global_Workspace.Get_Map_Uninhibited_Regions());
					/**
					 * This ensures all regions for each Desires.
					 * The developer is responsible for each lists 
					 */
					this.Map_Regions.putAll( this.Ensures_Regions_for_Desires( Active_Desires ));
				}
				
				if (this.Message_Handler.Read_Value_And_Clear_Updated_Selected_Intentions())
				{
					this.Intentions.clear();
					this.Intentions = this.Global_Workspace.Get_Selected_Intentions();
				}
				
				// MEANS-END Reasoner
				this.Means_End_Reasoner.Execute( Active_Desires );

				this.Desires_To_Filter.addAll( this.Means_End_Reasoner.Get_Desires_To_Filter() );

				if( this.Desires_To_Filter.size() > 0)
				{
					// Apply FINALLY OPERATOR
					this.Finally_Operator.Execute( Desires_To_Filter );
					
					// FILTERING PROCESS
					this.Filtering_Process.Execute( Desires_To_Filter );
					
//					this.Global_Workspace.Broadcast_Message(TType_Update_Contract.Active_Desires_with_Options);
					// DELIBERATION PROCESS
					this.Deliberation_Process.Execute( Desires_To_Filter );
					
					Selected_Intentions.addAll( this.Deliberation_Process.Get_Selected_Intentions() );
				}
				/**
				 * I always have to change selected intentions even when they don't exist,
				 * Even if the agent does not focus his attention
				 */
				
				if ( Selected_Intentions.size() > 0)
				{
					Selected_Intentions = this.Sort_Selected_Intentions( Selected_Intentions );
					this.Global_Workspace.Set_Selected_Intentions(Selected_Intentions);
				}
			}
		}
		
	}
	
	protected ArrayList<TBelief> Get_Beliefs()
	{
		return this.Beliefs;
	}
	
	protected HashMap<String, TBelief> Get_Map_Beliefs()
	{
		return this.Map_Beliefs;
	}
	
	protected ArrayList<TRegion> Get_Regions()
	{
		return this.Regions;
	}
	
	protected HashMap<String, TRegion> Get_Map_Regions()
	{
		return this.Map_Regions;
	}
	
	protected ArrayList<TIntention> Get_Intentions()
	{
		return this.Intentions;
	}
	
	public void Register_Green_Filtering_Function(String Green_Type, Green_Desires_Filter_Function func) 
    {
		this.Filtering_Process.Register_Green_Filtering_Function(Green_Type, func);
    }
	
	public boolean Unregister_Green_Filtering_Function(String Green_Name) 
    {
        
        return this.Filtering_Process.Unregister_Green_Filtering_Function(Green_Name);
    }
	
	public void Set_Generate_Options_for_Practical_Desire( 
			TMeans_End_Reasoner_Generate_Options_for_Practical_Desire Func )
	{
		this.Means_End_Reasoner.Set_Generate_Options_for_Practical_Desire( Func );
	}
	
	public void  Set_Deliberation_Process_Function( TDeliberation_Process_Function Func)
	{
		this.Deliberation_Process.Set_Deliberation_Process_Function( Func );
	}
	
	public void Set_Quality_Desire_Filtering_Process(TQuality_Desires_Filter_Function Func)
	{
		this.Filtering_Process.Set_Quality_Desire_Filtering_Process(Func);
	}
	
	protected TGlobal_Workspace Get_Global_Workspace()
	{
		return this.Global_Workspace;
	}
	
	public void Register_Epistemic_Function(String belief_Type, Means_End_Epistemic_Function func) 
    {
		this.Means_End_Reasoner.Register_Epistemic_Function( belief_Type, func );
    }
    
    public boolean Unregister_Epistemic_Function(String belief_Type) 
    {
    	return this.Means_End_Reasoner.Unregister_Epistemic_Function( belief_Type );
    }
    
    public void Register_Practical_Function(String Desire_Name, Means_End_Practical_Function func) 
    {
		this.Means_End_Reasoner.Register_Practical_Function( Desire_Name, func );
    }
    
    public boolean Unregister_Practical_Function(String Desire_Name) 
    {
    	return this.Means_End_Reasoner.Unregister_Practical_Function( Desire_Name );
    }
    
//    public void Register_List_Beliefs_Names_for_Desires(String Desire_Name, Set<String> Beliefs_Names)
//    {
//    	this.Means_End_Reasoner.Register_List_Beliefs_Names_for_Desires(Desire_Name, Beliefs_Names);
//    }
    
    public void Register_List_Beliefs_Names_for_Desires(String Desire_Name, HashSet<String> Beliefs_Names)
    {
    	if(Desire_Name != null && !Desire_Name.isEmpty() && Beliefs_Names != null)
    	{
    		this.List_Beliefs_Names_for_Desires.put(Desire_Name, Beliefs_Names);
    	}
    }
    
    public void Register_List_Regions_Names_for_Desires(String Desire_Name, HashSet<String> Regions_Names)
    {
    	if(Desire_Name != null && !Desire_Name.isEmpty() && Regions_Names != null)
    	{
    		this.List_Regions_Names_for_Desires.put(Desire_Name, Regions_Names);
    	}
    }
    
    public void Unregister_List_Beliefs_Names_for_Desires(String Desire_Name)
    {
    	if(Desire_Name != null && !Desire_Name.isEmpty())
    	{
    		this.List_Beliefs_Names_for_Desires.remove( Desire_Name );
    	}
    }
    
    public void Unregister_List_Beliefs_Names_for_Regions(String Desire_Name)
    {
    	if(Desire_Name != null && !Desire_Name.isEmpty())
    	{
    		this.List_Regions_Names_for_Desires.remove( Desire_Name );
    	}
    }
    
    private HashMap<String, TBelief> Ensures_Beliefs_for_Desires(ArrayList<TAttentional_Desire> Active_Desires)
    {
    	HashMap<String, TBelief> result = new HashMap<String, TBelief>();
    	HashSet<String> Beliefs_Names = new HashSet<String>();
    	
    	for(TAttentional_Desire Active_Desire: Active_Desires)
    	{
//    		Beliefs_Names.addAll( this.List_Beliefs_Names_for_Desires.get( Active_Desire.Get_Name() ));
    		Beliefs_Names.addAll( Active_Desire.Get_Beliefs_Name_for_Reasoner() );
    	}
    	result.putAll( this.Global_Workspace.Get_Selected_Beliefs_from_LTM( Beliefs_Names ));
    	result.remove( null );
    	return result;
    }
    
    private HashMap<String, TRegion> Ensures_Regions_for_Desires(ArrayList<TAttentional_Desire> Active_Desires)
    {
    	HashMap<String, TRegion> result = new HashMap<String, TRegion>();
    	HashSet<String> Regions_Names = new HashSet<String>();
    	for(TAttentional_Desire Active_Desire: Active_Desires)
    	{
//    		Regions_Names.addAll( this.List_Regions_Names_for_Desires.get( Active_Desire.Get_Name() ));
    		Regions_Names.addAll( Active_Desire.Get_Regions_Name_for_Reasoner() );
    	}
    	result.putAll( this.Global_Workspace.Get_Selected_Regions_from_LTM( Regions_Names ));
    	result.remove( null );
    	return result;
    }
    
    private ArrayList<TIntention> Sort_Selected_Intentions_old(ArrayList<TIntention> Intentions)
    {
//    		Intentions.sort((i1, i2) -> {
//			    // Gestione di base per TDesire nulli (li mette in fondo)
//			    if (i1.Get_Active_Desire() == null && i2.Get_Active_Desire() == null) {
//			        return 0; // Sono uguali
//			    }
//			    if (i1.Get_Active_Desire() == null) {
//			        return 1; // i1 è nullo, va dopo i2 (in fondo)
//			    }
//			    if (i2.Get_Active_Desire() == null) {
//			        return -1; // i2 è nullo, va dopo i1 (in fondo)
//			    }
//
//			    // *** MODIFICA QUI SOTTO! ***
//			    // Sostituisci 'getSaliency()' con il nome del tuo metodo REALE 
//			    // per ottenere la Saliency dalla classe TDesire.
//			    // Uso Double come esempio, usa Integer.compare se è un int, ecc.
//			    Double saliency1 = i1.Get_Active_Desire().Get_Saliency(); 
//			    Double saliency2 = i2.Get_Active_Desire().Get_Saliency();
//			    
//			     
//
//			    // Ordine DECRESCENTE: confronta il secondo con il primo
//			    int response =Double.compare(saliency2, saliency1);
//			    
//			    return response;
//			});
    		TIntention temp_Intention = null;
    		ArrayList<TIntention> new_Intentions = new ArrayList<TIntention>();
    		double saliency = 0;
    		String nomi = "";
    		for(TIntention Intention: Intentions)
    		{
    			TAttentional_Desire Desire = (TAttentional_Desire) Intention.Get_Active_Desire();
    			if(Desire.Get_List_Options().size() > 0)
    			{
    				if(Desire.Get_Saliency() > saliency )
    				{
    					saliency = Desire.Get_Saliency();
    					temp_Intention = Intention;
    				}
    				
    			}
    			else
    			{
    				nomi +=Intention.Get_Active_Desire().Get_Name()+"\n";
    				new_Intentions.add(Intention);
    				int o = 0;
    			}
    		}
    		if(temp_Intention != null)
    		{
    			new_Intentions.addFirst(temp_Intention);
    			nomi = temp_Intention.Get_Active_Desire().Get_Name()+"\n"+nomi;
    		}
    		return new_Intentions;
    }
    
    private ArrayList<TIntention> Sort_Selected_Intentions(ArrayList<TIntention> Intentions)
    {
    		Intentions.sort((i1, i2) -> {
        // Gestione di base per TDesire nulli
        if (i1.Get_Active_Desire() == null && i2.Get_Active_Desire() == null) return 0;
        if (i1.Get_Active_Desire() == null) return 1;
        if (i2.Get_Active_Desire() == null) return -1;

        Double saliency1 = i1.Get_Active_Desire().Get_Saliency(); 
        Double saliency2 = i2.Get_Active_Desire().Get_Saliency();
        
        // Ordine DECRESCENTE
        return Double.compare(saliency2, saliency1);
    });

    TIntention temp_Intention = null;
    ArrayList<TIntention> new_Intentions = new ArrayList<TIntention>();

    for(TIntention Intention: Intentions) 
    {
        TAttentional_Desire Desire = (TAttentional_Desire) Intention.Get_Active_Desire();
        if(Desire != null)
        {
	        if(Desire.Get_List_Options().size() > 0) 
	        {
	            // Prendo solo LA PRIMA che trovo (che è la più alta, essendo già ordinata).
	            // Le successive con opzioni > 0 vengono semplicemente ignorate.
	            if (temp_Intention == null) {
	                temp_Intention = Intention;
	            }
	        } else {
	            // Quelle con 0 opzioni le metto nella nuova lista
	            new_Intentions.add(Intention);
	        }	
        }
    }

    // Alla fine, metto in cima la migliore (se l'ho trovata)
    if(temp_Intention != null) {
        // Niente remove(), non serve!
        new_Intentions.addFirst(temp_Intention);
    }

    return new_Intentions;
    }
    
}

