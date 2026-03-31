import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

import com.Catalina_Model.Catalina_V_0_3.TAction;
import com.Catalina_Model.Catalina_V_0_3.TAttentional_Desire;
import com.Catalina_Model.Catalina_V_0_3.TBelief;
import com.Catalina_Model.Catalina_V_0_3.TExecutive_Reasoner_Function;
import com.Catalina_Model.Catalina_V_0_3.TIntention;
import com.Catalina_Model.Catalina_V_0_3.TOption;
import com.Catalina_Model.Catalina_V_0_3.TPractical_Desire;
import com.Catalina_Model.Catalina_V_0_3.TPredicate;
import com.Catalina_Model.Catalina_V_0_3.TRegion;

public class TFunctions_for_Deliberation_Process 
{
	
	private Autonomous_Vehicle_Demo Demo;
	private TCommon_Functions Common_Functions;
	
	public TFunctions_for_Deliberation_Process(Autonomous_Vehicle_Demo demo)
	{
		this.Demo = demo;
		this.Common_Functions = demo.Common_Functions;
	}
	
	public void Add_Function_To_Means_End_Reasoner(TExecutive_Reasoner_Function ERF )
	{
		ERF.Set_Deliberation_Process_Function( this::Deliberation_Process_Function );
	}
	
	public ArrayList<TIntention> Deliberation_Process_Function(
			ArrayList<TAttentional_Desire> Active_Desires, HashMap<String, TBelief> beliefs, 
			HashMap<String, TRegion> regions, ArrayList<TIntention> List_intentions)
	{
		ArrayList<TIntention> Selected_Intentions = new ArrayList<TIntention>();
		ArrayList<TAttentional_Desire> Desires_to_Promote = new ArrayList<TAttentional_Desire>();
		ArrayList<TAttentional_Desire> Desires_in_List_Intentions = new ArrayList<TAttentional_Desire>();
		
		for(TIntention Intention: List_intentions)
		{
			Desires_in_List_Intentions.add( (TAttentional_Desire) Intention.Get_Active_Desire());
		}
		
		Desires_to_Promote.addAll( Active_Desires );
		Desires_to_Promote.removeAll( Desires_in_List_Intentions );
		
		//I order Desires by decreasing Saliency
		Desires_to_Promote.sort(Comparator.comparing(TAttentional_Desire::Get_Saliency).reversed());
		
		/**
		 * In this version Agent selects only one Intention, the First
		 */
		Integer Number_Selected_Intention_to_pursue = 1;
		Integer counter_Selected_Intention_to_pursue = 0;
		
		/**
		 * this "Loop" Selects only one Intention with almost 1 option and all Intentions with
		 * no options (count options = 0)
		 */
		TOption option = null;
		TOption_Simulation Option_Simulation = null;
		for(TAttentional_Desire Attentional_Desire: Desires_to_Promote)
		{
//			this.Common_Functions.Print(Attentional_Desire.Get_Name()+ "  -  "+Attentional_Desire.Get_Saliency());
			if(Attentional_Desire.Get_List_Options().size() < 1)
			{
				TIntention An_Intention = new TIntention("", Attentional_Desire, -1);
				Attentional_Desire.Set_Related_Intention( An_Intention );
				An_Intention.Set_Desire( Attentional_Desire );
				Selected_Intentions.add( An_Intention );
				Common_Functions.Print("No Options for Desire: "+Attentional_Desire.Get_Name());
			}
			else
			{
				/**
				 * Option Execution Module execute only one Selected Intentions, so we
				 * limit the selected intentions with option to 1 to save memory space
				 */
				
				if (counter_Selected_Intention_to_pursue < Number_Selected_Intention_to_pursue)
					{
//						TIntention An_Intention = new TIntention("", Attentional_Desire, 0);
						TIntention An_Intention = new TIntention("Int_"+Attentional_Desire.Get_Name(), Attentional_Desire, 0);
						Attentional_Desire.Set_Related_Intention( An_Intention );
						An_Intention.Set_Desire( Attentional_Desire );
						Selected_Intentions.add( An_Intention );
						Integer id = An_Intention.Get_Selected_Option_Id();
						option = null;
						Option_Simulation = null;
						/*TOption */option = Attentional_Desire.Get_List_Options().get(id);
						/*TOption_Simulation */Option_Simulation = (TOption_Simulation) option;
						
						/*TOption_Simulation Option_Simulation = (TOption_Simulation) Attentional_Desire.
														Get_List_Options().get(id);*/
						/**
						 * This is a block code to view the choose option to pursue 
						 */
//						{
//							Integer o = 0;
//							Integer i = 0;
//							Common_Functions.Print("I deliberate for: "+Attentional_Desire.Get_Name());
//							for(TAction Action: Option_Simulation.Get_Plan_Actions())
//							{
//								Common_Functions.Print("---------------- "+ o +" "+Action.Get_Action_Name());
//								Common_Functions.Print("---------------- PRECONDITIONS");
//								for(TPredicate Predicate: Action.Get_Pre_conditions())
//								{
//									Common_Functions.Print(Predicate.Get_Object_Complement()+" - "+
//											Predicate.Get_Object_Complement());
//								}
//								Common_Functions.Print("---------------- POSTCONDITIONS");
//								for(TPredicate Predicate: Action.Get_Post_conditions())
//								{
//									Common_Functions.Print(Predicate.Get_Object_Complement()+" - "+
//											Predicate.Get_Object_Complement());
//								}
//								o++;
//									
//							}
//						}
						
							
						counter_Selected_Intention_to_pursue++;
						
					}
			}
			
		}
//		counter++;
		
		Selected_Intentions.addAll( Sort_Selected_Intentions(List_intentions) );
		
//		if(Selected_Intentions != null )
//		{
//			TAttentional_Desire Desire = (TAttentional_Desire) Selected_Intentions.getFirst().Get_Active_Desire();
//			if(Desire.Get_List_Options().size() > 0)
//			{
//				
//			}
//			
//		}
		
		
		 return Selected_Intentions;
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
