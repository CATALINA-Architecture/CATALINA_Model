import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import com.Catalina_Model.Catalina_V_0_3.TAction;
import com.Catalina_Model.Catalina_V_0_3.TAttentional_Desire;
import com.Catalina_Model.Catalina_V_0_3.TBelief;
import com.Catalina_Model.Catalina_V_0_3.TEpistemic_Desire;
import com.Catalina_Model.Catalina_V_0_3.TGreen_Desire;
import com.Catalina_Model.Catalina_V_0_3.TIntention;
import com.Catalina_Model.Catalina_V_0_3.TOption;
import com.Catalina_Model.Catalina_V_0_3.TPractical_Desire;
import com.Catalina_Model.Catalina_V_0_3.TPredicate;
import com.Catalina_Model.Catalina_V_0_3.TQuality_Desire;

public class TDesires_Beliefs_Functions 
{
	private HashSet<String> List_For_Reasoner_Come_Back;
	private HashSet<String> List_For_Reasoner_Destination_City;
	private HashSet<String> List_For_Reasoner_Stimulus_Danger_on_the_Route;
	private HashSet<String> List_For_Reasoner_Stimulus_Danger_Data_from_TCS;
	private HashSet<String> List_For_Reasoner_Refuel;
	
	public TDesires_Beliefs_Functions()
	{
		this.List_For_Reasoner_Come_Back = new HashSet<String>();
		this.List_For_Reasoner_Destination_City = new HashSet<String>();
		this.List_For_Reasoner_Stimulus_Danger_on_the_Route = new HashSet<String>();
		this.List_For_Reasoner_Stimulus_Danger_Data_from_TCS = new HashSet<String>();
		this.List_For_Reasoner_Refuel = new HashSet<String>();
		this.Initialize_Lists_Beliefs();
	}
	
	private void Initialize_Lists_Beliefs()
	{
		this.Initialize_List_For_Destination_City();
		this.Initialize_List_For_Come_Back();
		this.Initialize_List_For_Stimulus_Danger_on_the_Route();
		this.Initialize_List_For_Refuel();
	}
	
	private void Initialize_List_For_Come_Back()
	{
		this.List_For_Reasoner_Come_Back.clear();
		this.List_For_Reasoner_Come_Back.add("BL_Closed_Routes");
		this.List_For_Reasoner_Come_Back.add("BL_Temporary_Closed_Routes");
		this.List_For_Reasoner_Come_Back.add("BL_Dangerous_Position_Route");
		this.List_For_Reasoner_Come_Back.add("BL_Map");
		this.List_For_Reasoner_Come_Back.add("BL_Come_Back_to_City");
		this.List_For_Reasoner_Come_Back.add("BL_Current_Time");
		this.List_For_Reasoner_Come_Back.add("BLT_Destination_City");
		
		this.List_For_Reasoner_Come_Back.add("BL_Danger_on_the_Route");
		this.List_For_Reasoner_Come_Back.add("BL_Next_Position_City");
		this.List_For_Reasoner_Come_Back.add("BL_Next_Position_Route");
		this.List_For_Reasoner_Come_Back.add("BL_Next_Position_Step");
		this.List_For_Reasoner_Come_Back.add("BL_Position_City");
		this.List_For_Reasoner_Come_Back.add("BL_Position_Route");
		this.List_For_Reasoner_Come_Back.add("BL_Position_Step");
		this.List_For_Reasoner_Come_Back.add("BL_Previous_Position_City");
		this.List_For_Reasoner_Come_Back.add("BL_Previous_Position_Route");
		this.List_For_Reasoner_Come_Back.add("BL_Previous_Position_Step");
		this.List_For_Reasoner_Come_Back.add("BL_Routes_performed_for_each_Practical_Desires");
		this.List_For_Reasoner_Come_Back.add("BL_Temporary_Closed_Routes");
		this.List_For_Reasoner_Come_Back.add("ST_Stimulus_Danger_on_the_Route");
		this.List_For_Reasoner_Come_Back.add("ST_Stimulus_Irrelevant");
		this.List_For_Reasoner_Come_Back.add("ST_Stimulus_Ok_Correct_Movement");
		this.List_For_Reasoner_Come_Back.add("ST_Stimulus_Route_Status");
		this.List_For_Reasoner_Come_Back.add("ST_Stimulus_Temporary_Closed_Route");
		
		this.List_For_Reasoner_Come_Back.add("BL_Fuel_Level");
		this.List_For_Reasoner_Come_Back.add("ST_Stimulus_Low_Fuel");
		this.List_For_Reasoner_Come_Back.add("BL_Low_Fuel_Level_Warning");
		this.List_For_Reasoner_Come_Back.add("BL_Refuel_to_City");
		this.List_For_Reasoner_Come_Back.add("BL_Low_Fuel_Level");
	}
	
	private void Initialize_List_For_Refuel()
	{
		this.List_For_Reasoner_Refuel.clear();
		this.List_For_Reasoner_Refuel.add("BL_Closed_Routes");
		this.List_For_Reasoner_Refuel.add("BL_Temporary_Closed_Routes");
		this.List_For_Reasoner_Refuel.add("BL_Dangerous_Position_Route");
		this.List_For_Reasoner_Refuel.add("BL_Map");
		this.List_For_Reasoner_Refuel.add("BL_Current_Time");
		
		this.List_For_Reasoner_Refuel.add("BL_Danger_on_the_Route");
		this.List_For_Reasoner_Refuel.add("BL_Next_Position_City");
		this.List_For_Reasoner_Refuel.add("BL_Next_Position_Route");
		this.List_For_Reasoner_Refuel.add("BL_Next_Position_Step");
		this.List_For_Reasoner_Refuel.add("BL_Position_City");
		this.List_For_Reasoner_Refuel.add("BL_Position_Route");
		this.List_For_Reasoner_Refuel.add("BL_Position_Step");
		this.List_For_Reasoner_Refuel.add("BL_Previous_Position_City");
		this.List_For_Reasoner_Refuel.add("BL_Previous_Position_Route");
		this.List_For_Reasoner_Refuel.add("BL_Previous_Position_Step");
		this.List_For_Reasoner_Refuel.add("BL_Routes_performed_for_each_Practical_Desires");
		this.List_For_Reasoner_Refuel.add("BL_Temporary_Closed_Routes");
		this.List_For_Reasoner_Refuel.add("ST_Stimulus_Danger_on_the_Route");
		this.List_For_Reasoner_Refuel.add("ST_Stimulus_Irrelevant");
		this.List_For_Reasoner_Refuel.add("ST_Stimulus_Ok_Correct_Movement");
		this.List_For_Reasoner_Refuel.add("ST_Stimulus_Route_Status");
		this.List_For_Reasoner_Refuel.add("ST_Stimulus_Temporary_Closed_Route");

		this.List_For_Reasoner_Refuel.add("BL_Fuel_Level");
		this.List_For_Reasoner_Refuel.add("ST_Stimulus_Low_Fuel");
		this.List_For_Reasoner_Refuel.add("BL_Low_Fuel_Level_Warning");
		this.List_For_Reasoner_Refuel.add("BLT_City_to_Refuel");
		this.List_For_Reasoner_Refuel.add("BL_Refuel_to_City");
		this.List_For_Reasoner_Refuel.add("BL_Low_Fuel_Level");
		
	}
	
	private void Initialize_List_For_Destination_City()
	{
		this.List_For_Reasoner_Destination_City.clear();
		this.List_For_Reasoner_Destination_City.add("BL_Closed_Routes");
		this.List_For_Reasoner_Destination_City.add("BL_Temporary_Closed_Routes");
		this.List_For_Reasoner_Destination_City.add("BL_Dangerous_Position_Route");
													 
		this.List_For_Reasoner_Destination_City.add("BL_Map");
		this.List_For_Reasoner_Destination_City.add("BL_Come_Back_to_City");
		this.List_For_Reasoner_Destination_City.add("BLT_Destination_City");
		
		this.List_For_Reasoner_Destination_City.add("BL_Current_Time");
		this.List_For_Reasoner_Destination_City.add("BL_Danger_on_the_Route");
		this.List_For_Reasoner_Destination_City.add("BL_Next_Position_City");
		this.List_For_Reasoner_Destination_City.add("BL_Next_Position_Route");
		this.List_For_Reasoner_Destination_City.add("BL_Next_Position_Step");
		this.List_For_Reasoner_Destination_City.add("BL_Position_City");
		this.List_For_Reasoner_Destination_City.add("BL_Position_Route");
		this.List_For_Reasoner_Destination_City.add("BL_Position_Step");
		this.List_For_Reasoner_Destination_City.add("BL_Previous_Position_City");
		this.List_For_Reasoner_Destination_City.add("BL_Previous_Position_Route");
		this.List_For_Reasoner_Destination_City.add("BL_Previous_Position_Step");
		this.List_For_Reasoner_Destination_City.add("BL_Routes_performed_for_each_Practical_Desires");
		this.List_For_Reasoner_Destination_City.add("BL_Temporary_Closed_Routes");
		this.List_For_Reasoner_Destination_City.add("ST_Stimulus_Danger_on_the_Route");
		this.List_For_Reasoner_Destination_City.add("ST_Stimulus_Irrelevant");
		this.List_For_Reasoner_Destination_City.add("ST_Stimulus_Ok_Correct_Movement");
		this.List_For_Reasoner_Destination_City.add("ST_Stimulus_Route_Status");
		this.List_For_Reasoner_Destination_City.add("ST_Stimulus_Temporary_Closed_Route");
		
		this.List_For_Reasoner_Destination_City.add("BL_Fuel_Level");
		this.List_For_Reasoner_Destination_City.add("ST_Stimulus_Low_Fuel");
		this.List_For_Reasoner_Destination_City.add("BL_Low_Fuel_Level_Warning");
		this.List_For_Reasoner_Destination_City.add("BL_Refuel_to_City");
		
		this.List_For_Reasoner_Destination_City.add("BL_Current_Travel_Intention_Satisfaction_Time");
		this.List_For_Reasoner_Destination_City.add("BL_Previous_Travel_Intention_Satisfaction_Time");
		this.List_For_Reasoner_Destination_City.add("BL_Previous_Travel_Intention_Max_Satisfaction_Time");
		this.List_For_Reasoner_Destination_City.add("BL_Temporary_Closed_Duration");
		this.List_For_Reasoner_Destination_City.add("BL_Low_Fuel_Level");
		
		
		
//		
//		
	}
	
	private void Initialize_List_For_Stimulus_Danger_on_the_Route()
	{
		String Stimulus_Name = "ST_Stimulus_Danger_on_the_Route";
		this.List_For_Reasoner_Stimulus_Danger_on_the_Route.add("BL_Danger_on_the_Route");
		this.List_For_Reasoner_Stimulus_Danger_on_the_Route.add("BL_Dangerous_Position_Route");
		this.List_For_Reasoner_Stimulus_Danger_on_the_Route.add("BL_Come_Back_to_City");
		this.List_For_Reasoner_Stimulus_Danger_on_the_Route.add("BLT_Destination_City");
		
		
		
		this.List_For_Reasoner_Stimulus_Danger_on_the_Route.add("BL_Current_Time");
		this.List_For_Reasoner_Stimulus_Danger_on_the_Route.add("BL_Map");
		this.List_For_Reasoner_Stimulus_Danger_on_the_Route.add("BL_Temporary_Closed_Routes");
		this.List_For_Reasoner_Stimulus_Danger_on_the_Route.add("BL_Position_City");
		this.List_For_Reasoner_Stimulus_Danger_on_the_Route.add("BL_Position_Route");
		this.List_For_Reasoner_Stimulus_Danger_on_the_Route.add("BL_Position_Step");
		this.List_For_Reasoner_Stimulus_Danger_on_the_Route.add("BL_Next_Position_City");
		this.List_For_Reasoner_Stimulus_Danger_on_the_Route.add("BL_Next_Position_Route");
		this.List_For_Reasoner_Stimulus_Danger_on_the_Route.add("BL_Next_Position_Step");
		this.List_For_Reasoner_Stimulus_Danger_on_the_Route.add(Stimulus_Name);
		this.List_For_Reasoner_Stimulus_Danger_on_the_Route.add("ST_Stimulus_Irrelevant");
		this.List_For_Reasoner_Stimulus_Danger_on_the_Route.add("ST_Stimulus_Ok_Correct_Movement");
		this.List_For_Reasoner_Stimulus_Danger_on_the_Route.add("ST_Stimulus_Route_Status");
		this.List_For_Reasoner_Stimulus_Danger_on_the_Route.add("ST_Stimulus_Temporary_Closed_Route");
		
		this.List_For_Reasoner_Stimulus_Danger_on_the_Route.add("ST_Stimulus_Danger_Data_from_TCS");
		
		this.List_For_Reasoner_Stimulus_Danger_on_the_Route.add("BL_Fuel_Level");
		this.List_For_Reasoner_Stimulus_Danger_on_the_Route.add("ST_Stimulus_Low_Fuel");
		this.List_For_Reasoner_Stimulus_Danger_on_the_Route.add("BL_Low_Fuel_Level_Warning");
		this.List_For_Reasoner_Stimulus_Danger_on_the_Route.add("BL_Refuel_to_City");
		this.List_For_Reasoner_Stimulus_Danger_on_the_Route.add("BL_Low_Fuel_Level");

		
		this.List_For_Reasoner_Stimulus_Danger_on_the_Route.add("BL_Current_Travel_Intention_Satisfaction_Time");
		this.List_For_Reasoner_Stimulus_Danger_on_the_Route.add("BL_Previous_Travel_Intention_Satisfaction_Time");
		
		this.List_For_Reasoner_Stimulus_Danger_on_the_Route.add("BL_Previous_Travel_Intention_Max_Satisfaction_Time");
		this.List_For_Reasoner_Stimulus_Danger_on_the_Route.add("BL_Temporary_Closed_Duration");
	}
	
	
	public HashSet<String> Inhibition_Beliefs_for_Destination_City( 
    		TAttentional_Desire Attentional_Desire, HashMap<String, TBelief> Map_Beliefs)
	{
		HashSet<String> result = new HashSet<String>();
		
		result.addAll( this.List_For_Reasoner_Destination_City );
		
		if( Attentional_Desire!= null)
		{
			TIntention Intention = Attentional_Desire.Get_Related_Intention();
			
			if( Intention != null)
			{
				Integer Selected_Option_Id = Intention.Get_Selected_Option_Id();

				if (Selected_Option_Id >=0)
				{
					TOption_Simulation Option_Simulation =  
							(TOption_Simulation) Attentional_Desire.Get_List_Options().
									get( Selected_Option_Id );
					
					TEnvironment Map = (TEnvironment ) Map_Beliefs.get("BL_Map").
							Get_Predicate().Get_Object_Complement();
					
					//I have to insert the Route Status and the Specular Route Status
					//to come back in case of detecting dangers on the road
					for (Integer Route: Option_Simulation.Path.Routes)
					{
						Integer Specular_Route = Map.Get_Specular_Route( Route );
						result.add("BL_Route_Status_"+Route.toString() );
						result.add("BL_Route_Status_"+Specular_Route.toString() );
					}
					for (TCity City: Option_Simulation.Path.Destinations)
					{
						result.add("BL_City_Visited_"+City.toString() );
					}
					result.addAll( this.Get_Beliefs_by_Selected_Option(
							 Option_Simulation ) );
				}
			}
			
		}
		
		return result;
	}
	
	public HashSet<String> Inhibition_Beliefs_for_Come_Back( 
    		TAttentional_Desire Attentional_Desire, HashMap<String, TBelief> Map_Beliefs)
	{
		HashSet<String> result = new HashSet<String>();
		
		result.addAll( this.List_For_Reasoner_Come_Back );
		
//		TBelief BL_Map = Map_Beliefs.get( "BL_Dangerous_Position_Route");
		TBelief BL_Map = Map_Beliefs.get( "BL_Map");
		TEnvironment Map = (TEnvironment) BL_Map.Get_Predicate().Get_Object_Complement();
		
		TBelief BL_Dangerous_Route = Map_Beliefs.get( "BL_Dangerous_Position_Route");
		Integer Dangerous_Route_Value = (Integer) BL_Dangerous_Route.Get_Predicate().Get_Object_Complement();
		Integer Specular_Dangerous_Route_Value = Map.Get_Specular_Route( Dangerous_Route_Value );
		
		result.add("BL_Route_Status_"+Dangerous_Route_Value);
		result.add("BL_Route_Status_"+Specular_Dangerous_Route_Value);
		
		result.add(BL_Dangerous_Route.Get_Name());
		
		return result;
	}
	
	public HashSet<String> Inhibition_Beliefs_for_Refuel( 
    		TAttentional_Desire Attentional_Desire, HashMap<String, TBelief> Map_Beliefs)
	{
		HashSet<String> result = new HashSet<String>();
		
		result.addAll( this.List_For_Reasoner_Refuel );
		
		TBelief BL_Map = Map_Beliefs.get( "BL_Map");
		TEnvironment Map = (TEnvironment) BL_Map.Get_Predicate().Get_Object_Complement();
		
		TBelief BL_Position_Route = Map_Beliefs.get( "BL_Position_Route");
		Integer Route_position = (Integer) BL_Position_Route.Get_Predicate().Get_Object_Complement();
		
		
		if(Route_position == -1)
		{
			TBelief BL_Next_Position_Route = Map_Beliefs.get( "BL_Next_Position_Route");
			Route_position = (Integer) BL_Next_Position_Route.Get_Predicate().Get_Object_Complement();
		}
		
		Integer Specular_Dangerous_Route_Value = Map.Get_Specular_Route( Route_position );
		
		result.add("BL_Route_Status_"+Route_position);
		result.add("BL_Route_Status_"+Specular_Dangerous_Route_Value);
		
		return result;
	}
	
	public HashSet<String> Inhibition_Beliefs_for_Stimulus_Danger_on_the_Route( 
    		TAttentional_Desire Attentional_Desire, HashMap<String, TBelief> Map_Beliefs)
	{
		HashSet<String> result = new HashSet<String>();
		
		result.addAll( this.List_For_Reasoner_Stimulus_Danger_on_the_Route );
		
		String Stimulus_Name = "ST_Stimulus_Danger_on_the_Route";
		TBelief Belief = Map_Beliefs.get( Stimulus_Name );
		if( Belief != null )
		{
			TPredicate Predicate = Belief.Get_Predicate();
			ArrayList<Integer> Positions = 
					(ArrayList<Integer>) Predicate.Get_Subject();
			Integer Route = Positions.get(1);
			result.add("BL_Route_Status_"+ Route);
		}
		return result;
	}
	
	public HashSet<String> Reasoner_Beliefs_For_Come_Back(TPractical_Desire Desire)
	{
		HashSet<String> result = new HashSet<String>();
		result.addAll( this.List_For_Reasoner_Come_Back);
		
		result.addAll( Get_Green_Quality_Beliefs_by_Desire( Desire ) );
		return result;
	}
	
	public HashSet<String> Reasoner_Beliefs_For_Refuel(TPractical_Desire Desire)
	{
		HashSet<String> result = new HashSet<String>();
		result.addAll( this.List_For_Reasoner_Come_Back);
		
		result.addAll( Get_Green_Quality_Beliefs_by_Desire( Desire ) );
		return result;
	}
	
	public HashSet<String> Reasoner_Beliefs_For_Destination_City(TPractical_Desire Desire)
	{
		HashSet<String> result = new HashSet<String>();
		result.addAll( this.List_For_Reasoner_Destination_City);
		
		result.addAll( Get_Green_Quality_Beliefs_by_Desire( Desire ) );
		
		return result;
	}
	
	private HashSet<String> Get_Green_Quality_Beliefs_by_Desire(TAttentional_Desire Desire)
	{
		//I insert the Constraint of each Green Desires of the Practical Desire
		HashSet<String> result = new HashSet<String>();
		for(TGreen_Desire Green_Desire: Desire.Get_List_Green_Standing_Desire())
		{
			result.add( Green_Desire.Get_Constraint().Get_Linked_Belief().Get_Name());
		}
		
		//I insert the Constraint of each Quality Desires of the Practical Desire
		for(TQuality_Desire Quality_Desire: Desire.Get_List_Quality_Standing_Desire())
		{
			result.add( Quality_Desire.Get_Constraint().Get_Linked_Belief().Get_Name());
		}
		return result;
	}
	
	private HashSet<String> Get_Beliefs_by_Selected_Option(TOption Option)
	{
		HashSet<String> result = new HashSet<String>();
		/**
		 * For each action in selected option, I get
		 * the name and insert in result
		 */
		for(TAction Action: Option.Get_Plan_Actions())
		{
			
			/**
			 * Pre_conditions are predicates in which the object_complement is a value, 
			 * while the subject is a predicate itself to be considered and it is this 
			 * predicate that is connected to the belief to be kept in memory as 
			 * an uninhibited belief.
			 */
			for(TPredicate Pre_condition: Action.Get_Pre_conditions())
			{
				if(Pre_condition.Get_Linked_Belief() != null)
				{
					result.add( 
							((TPredicate) Pre_condition.Get_Subject()).Get_Linked_Belief().Get_Name()
							);
					;
				}
			}

			/**
			 * Post_conditions are predicates in which the object_complement is a value, 
			 * while the subject is a predicate itself to be considered and it is this 
			 * predicate that is connected to the belief to be kept in memory as 
			 * an uninhibited belief.
			 */
			for(TPredicate Post_condition: Action.Get_Post_conditions())
			{
				if(Post_condition.Get_Linked_Belief() != null)
				{
					result.add( 
							((TPredicate) Post_condition.Get_Subject()).Get_Linked_Belief().Get_Name()
							);
				}
			}
		}
		return result;
	}
	
	public HashSet<String> Reasoner_Beliefs_For_Stimulus_Danger_on_the_Route(TEpistemic_Desire Desire)
	{
		HashSet<String> result = new HashSet<String>();
		result.addAll( this.List_For_Reasoner_Stimulus_Danger_on_the_Route);
		
		result.addAll( Get_Green_Quality_Beliefs_by_Desire( Desire ) );
		return result;
	}
}
