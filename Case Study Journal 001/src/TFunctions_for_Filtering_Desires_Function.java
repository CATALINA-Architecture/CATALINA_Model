import java.util.ArrayList;
import java.util.Comparator;

import com.Catalina_Model.Catalina_V_0_3.TAttentional_Desire;
import com.Catalina_Model.Catalina_V_0_3.TBelief;
import com.Catalina_Model.Catalina_V_0_3.TEpistemic_Desire;
import com.Catalina_Model.Catalina_V_0_3.TExecutive_Inhibition_Function;
import com.Catalina_Model.Catalina_V_0_3.TExecutive_Reasoner_Function;
import com.Catalina_Model.Catalina_V_0_3.TExecutive_Reasoner_Function.TQuality_Desires_Filter_Function;
import com.Catalina_Model.Catalina_V_0_3.TGreen_Desire;
import com.Catalina_Model.Catalina_V_0_3.TIntention;
import com.Catalina_Model.Catalina_V_0_3.TOption;
import com.Catalina_Model.Catalina_V_0_3.TPredicate;
import com.Catalina_Model.Catalina_V_0_3.TQuality_Desire;
import com.Catalina_Model.Catalina_V_0_3.TRegion;
import com.Catalina_Model.Catalina_V_0_3.TType_Relationship;

public class TFunctions_for_Filtering_Desires_Function
{
	private Autonomous_Vehicle_Demo Demo;
	private TCommon_Functions Common_Functions;
	private TExecutive_Inhibition_Function  Inhibition_Function;
	
	public TFunctions_for_Filtering_Desires_Function(Autonomous_Vehicle_Demo demo)
	{
		this.Demo = demo;
		this.Common_Functions = demo.Common_Functions;
	}
	
	public void Add_Function_To_Filter_Process(TExecutive_Reasoner_Function ERF )
	{
		ERF.Register_Green_Filtering_Function("GDT_Limited_Pollution_to", this::Apply_Green_Filter_Limited_Pollution_to_0_8);
		ERF.Set_Quality_Desire_Filtering_Process(this::Apply_Quality_Filter);
	}
	
	public ArrayList<TOption> Apply_Green_Filter_Limited_Pollution_to_0_8
	(TGreen_Desire Green_Desire, TAttentional_Desire Desire, ArrayList<TBelief> beliefs,
     ArrayList<TRegion> regions, ArrayList<TIntention> intentions)
	{
		Double Green_Goal_Limit = 0.0;
		ArrayList<TOption> Current_Options = Desire.Get_List_Options();
		
		ArrayList<TOption> result = new ArrayList<TOption>();
		
		TPredicate Predicate = (TPredicate) Green_Desire.Get_Constraint();
		Green_Goal_Limit = (Double) Green_Desire.Get_Constraint().Get_Object_Complement();
		
		switch ( Predicate.Get_Relationship()) 
		{
			case minor: 
			{
				for(TOption Option: Current_Options)
				{
					TOption_Simulation Option_Simulation = (TOption_Simulation) Option;
					if(Option_Simulation.Path.Total_Weights.get(TType_Quality_Desire.Motor) < Green_Goal_Limit)
					{
						result.add( Option );
					}
					result.size();
				}
				break;
			}
			case minor_equal: 
			{
				for(TOption Option: Current_Options)
				{
					TOption_Simulation Option_Simulation = (TOption_Simulation) Option;
					if(Option_Simulation.Path.Total_Weights.get(TType_Quality_Desire.Motor) <= Green_Goal_Limit)
					{
						result.add( Option );
					}
				}
				break;
			}
		}
		return result;
	}
	
	/**
	 * This is a multi-criteria sorting
	 * @param Desire
	 * @param beliefs
	 * @param regions
	 * @param intentions
	 * @return
	 */
	public ArrayList<TOption> Apply_Quality_Filter(TAttentional_Desire Desire, ArrayList<TBelief> beliefs, 
			ArrayList<TRegion> regions, ArrayList<TIntention> intentions)
	{
		
		ArrayList<TOption> result = new ArrayList<TOption>();
//		ArrayList<TOption> result1 = new ArrayList<TOption>();
		result.addAll(Desire.Get_List_Options());
//		result1.addAll(Desire.Get_List_Options());
		
		ArrayList<TQuality_Desire> List_Quality_Desire = new ArrayList<TQuality_Desire>();
		List_Quality_Desire.addAll( Desire.Get_List_Quality_Standing_Desire());
		// 1 - I sort the quality desire list to apply the correct desires at each time
		List_Quality_Desire.sort(
			    (d1, d2) -> Double.compare(d2.Get_Saliency(), d1.Get_Saliency())
			);
		
		// 2 - I Create a comparator to append other Quality Desire
		Comparator<TOption> Multi_Citeria_Comparator = null;

		for (TQuality_Desire quality_desire : List_Quality_Desire) 
		{
		    // Il metodo helper crea il pezzo di logica
			String Type_Quality_Desire = quality_desire.Get_Type_Quality_Goal();
			Object  Value_Quality_Desire = quality_desire.Get_Constraint().Get_Object_Complement();
			
			Remove_options_that_exceed_quality_constraints(result, Type_Quality_Desire, 
																Value_Quality_Desire);
			
		    Comparator<TOption> current_comparator = 
		    		Create_Comparator_by_Quality( Type_Quality_Desire, Value_Quality_Desire);

		    if (Multi_Citeria_Comparator == null) {
		        Multi_Citeria_Comparator = current_comparator; // Il primo (motore)
		    } else {
		        // Accoda i successivi (panorama, poi velocità)
		        Multi_Citeria_Comparator = Multi_Citeria_Comparator.thenComparing(current_comparator);
		    }
		}

		// Applica l'ordinamento finale!
		if (Multi_Citeria_Comparator != null) {
			result.sort(Multi_Citeria_Comparator);
		}
		
		return result;
	}
	
	private Comparator<TOption> Create_Comparator_by_Quality(String Type_Quality_Desire, Object Value_Quality_Desire) 
	{
//		TOption_Simulation Option = (TOption_Simulation) TOption
//	    switch (nomeQualita) {
//	        case "Panorama":
//	            return Comparator.comparing(TOption::getPanorama).reversed();
//	        case "velocita":
//	            return Comparator.comparing(TOption::getVelocita).reversed();
//	        default:
//	            return (o1, o2) -> 0;
//	    }
		switch (Type_Quality_Desire) 
		{
	        case "QDT_Panorama_is_Great":
//	        case "Speed":
	            return (o1, o2) -> {
	                try {
	                    // 1. Cast (come discusso prima)
	                    TOption_Simulation Option1 = (TOption_Simulation) o1;
	                    TOption_Simulation Option2 = (TOption_Simulation) o2;

	                    // 2. Conversione della stringa in Enum
	                    // NOTA: Se 'nomeQualita' è "Speed" e l'enum è "SPEED",
	                    // potresti dover fare: .valueOf(nomeQualita.toUpperCase())
	                    
	                    TType_Quality_Desire Type_Quality = TType_Quality_Desire.valueOf("Panorama");
	                    
	                    // 3. Estrazione generica del valore dalla Map
	                    Double value_1 = Option1.get_Quality_List().get(Type_Quality);
	                    Double value_2 = Option2.get_Quality_List().get(Type_Quality);

	                    // 4. Gestione robusta di valori nulli (evita crash)
	                    if (value_1 == null) value_1 = 0.0; // O Double.MIN_VALUE
	                    if (value_2 == null) value_2 = 0.0; 

	                    // 5. Confronto (decrescente)
	                    return Double.compare(value_2, value_1);

	                } catch (Exception e) {
	                    // Qualcosa è andato storto (cast, valueOf, chiave non in mappa)
//	                    System.err.println("WARN: Impossible to compare for  '" + Type_Quality_Desire + "': " + e.getMessage());
	                    return 0; // In caso di errore, non ordinare
	                }
	            };  
	        case "QDT_Velocity_is_Moderate":
	        	/**
	        	 * MODERATE:
	        	 * Remember
	        	 * The average target value can be 2.0  (the min value is 1, the max value is 3)
	        	 *  
	        	 */
//	            final double AVERAGE_TARGET_VALUE = 2.0;
	        	final double AVERAGE_TARGET_VALUE = (Double) Value_Quality_Desire;
	            
	            return (o1, o2) -> {
	                try {
	                    TOption_Simulation Option1 = (TOption_Simulation) o1;
	                    TOption_Simulation Option2 = (TOption_Simulation) o2;
	                    TType_Quality_Desire tipoQualita = TType_Quality_Desire.valueOf("Speed");
	                    
	                    Double valore1 = Option1.get_Quality_List().get(tipoQualita);
	                    Double valore2 = Option2.get_Quality_List().get(tipoQualita);
	                    
	                    if (valore1 == null) valore1 = AVERAGE_TARGET_VALUE; // O una penalità
	                    if (valore2 == null) valore2 = AVERAGE_TARGET_VALUE; 

	                    // 1. Calcola la distanza assoluta dal target
	                    double distanza1 = Math.abs(valore1 - AVERAGE_TARGET_VALUE);
	                    double distanza2 = Math.abs(valore2 - AVERAGE_TARGET_VALUE);

	                    // 2. Ordina per distanza CRESCENTE (distanza1 vs distanza2)
	                    // (Chi ha la distanza minore, vince)
	                    return Double.compare(distanza1, distanza2);

	                } catch (Exception e) {
	                    return 0; // Errore, considerali uguali
	                }
	            };
	        	
	        default:
	            // Nessun ordinamento se la qualità non è riconosciuta
	            return (o1, o2) -> 0;
		}
	}
	
	private void Remove_options_that_exceed_quality_constraints(ArrayList<TOption> currentOptions, String Type_Quality_Desire, Object Value_Quality_Desire)
	{
	    TType_Quality_Desire Type_Quality_to_compare;// = TType_Quality_Desire.valueOf("Panorama");
	    switch (Type_Quality_Desire) 
		{
	        case "QDT_Panorama_is_Great":
	        	// The agent sorts only for the highest value, so each options will survive
	        	return;

	        case "QDT_Velocity_is_Moderate":
	        	/**
	        	 * MODERATE:
	        	 * Remember
	        	 * The average target value can be 2.0  (the min value is 1, the max value is 3)
	        	 *  
	        	 */
	        	Type_Quality_to_compare = TType_Quality_Desire.valueOf("Speed");
	        	/**
	        	 * To apply the correct value for our simulation (we use steps to move our
	        	 * agent) we divide the Value_to_compare with 39
	        	 */
	        	Double Value_to_compare = (Double) Value_Quality_Desire/39;
	        	
	        	currentOptions.removeIf(option -> 
		        	{
		                try {
		                    TOption_Simulation optSim = (TOption_Simulation) option;
		                    Double val = optSim.get_Quality_List().get( Type_Quality_to_compare );
		                    
		                    // LOGICA DEL FILTRO:
		                    // Se il valore esiste ED è maggiore del limite, restituisci TRUE (Rimuovi)
		                    return val != null && val > Value_to_compare;
		                    
		                } catch (Exception e) {
		                    return false; // Non rimuovere in caso di errore
		                }
		            });
	            
	        default:
	            // Nessuna rimozione se la qualità non è riconosciuta
	        	return;
		}
	    
	    
	}
	

}
