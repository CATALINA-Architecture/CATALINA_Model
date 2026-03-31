import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import com.Catalina_Model.Catalina_V_0_3.TAttentional_Desire;
import com.Catalina_Model.Catalina_V_0_3.TBelief;
import com.Catalina_Model.Catalina_V_0_3.TIntention;
import com.Catalina_Model.Catalina_V_0_3.TRegion;
import com.Catalina_Model.Catalina_V_0_3.TRegion_Inhibition_Function_Handler.TRegion_Inhibition_Function;

public class TDesires_Regions_Functions 
{

	public TDesires_Regions_Functions()
	{
		
	}
	
	public HashSet<String> Get_Come_Back_Regions(TIntention Intention, 
			HashMap<String, TBelief> Map_Beliefs, 
			HashMap<String, TRegion> All_Map_Regions)
	{

		HashSet<String> result = new HashSet<String>();
		TBelief BL_Map = Map_Beliefs.get( "BL_Map");
		
		TEnvironment Map = (TEnvironment) BL_Map.Get_Predicate().Get_Object_Complement();
		
		TBelief BL_Dangerous_Route = Map_Beliefs.get( "BL_Dangerous_Position_Route");
		Integer Dangerous_Route_Value = (Integer) BL_Dangerous_Route.Get_Predicate().Get_Object_Complement();
		Integer Specular_Dangerous_Route_Value = Map.Get_Specular_Route( Dangerous_Route_Value );
		
		result.add("Route_"+Dangerous_Route_Value);
		result.add("Route_"+Specular_Dangerous_Route_Value);
		
		result.add(BL_Dangerous_Route.Get_Name());
		
		return result;
	}
	
	public HashSet<String> Inhibition_Regions_for_Destintion_City(
			TIntention Intention, 
			HashMap<String, TBelief> Map_Beliefs, 
			HashMap<String, TRegion> Map_Regions)
	{
		HashSet<String> Result = new HashSet<String>();
		
		TAttentional_Desire Attentional_Desire = (TAttentional_Desire) 
				Intention.Get_Active_Desire();
		Integer Selected_Option_Id = Intention.Get_Selected_Option_Id();
		
		if (Selected_Option_Id >= 0)
		{
			TOption_Simulation Option_Simulation =  
					(TOption_Simulation) Attentional_Desire.Get_List_Options().
							get( Selected_Option_Id );
			TEnvironment Map = (TEnvironment ) Map_Beliefs.get("BL_Map").
										Get_Predicate().Get_Object_Complement();

			for (Integer Route: Option_Simulation.Path.Routes)
			{
				Integer Specular_Route = Map.Get_Specular_Route( Route );
				
				if(Route > -1 )
				{
					Result.add("Route_"+Route);
				}
				if(Specular_Route > -1 )
				{
					Result.add("Route_"+Specular_Route);
				}
			}
		}
		
		return Result;
	}
	
	public HashSet<String> Reasoner_Get_Regions_for_Come_Back( HashMap<String, TBelief> Map_Beliefs)
	{

		HashSet<String> result = new HashSet<String>();
		TBelief BL_Map = Map_Beliefs.get( "BL_Map");
		
		TEnvironment Map = (TEnvironment) BL_Map.Get_Predicate().Get_Object_Complement();
		
		TBelief BL_Dangerous_Route = Map_Beliefs.get( "BL_Dangerous_Position_Route");
		
		TBelief BL_Position_City = Map_Beliefs.get("BL_Position_City");
//		this.Common_Functions.Print_Colored_Text(City_position+" - "+BL_Position_City.Get_Predicate().Get_Object_Complement(), 2);
		
		TBelief BL_Position_Route = Map_Beliefs.get("BL_Position_Route");
		TBelief BL_Next_Position_Route = Map_Beliefs.get("BL_Next_Position_Route");
//		this.Common_Functions.Print_Colored_Text(Route_position+" - "+BL_Position_Route.Get_Predicate().Get_Object_Complement(), 2);
		
		TBelief BL_Position_Step = Map_Beliefs.get("BL_Position_Step");
//		this.Common_Functions.Print_Colored_Text(Step_position+" - "+BL_Position_Step.Get_Predicate().Get_Object_Complement(), 2);
		
		Integer City_position = (Integer) BL_Position_City.Get_Predicate().Get_Object_Complement();
		Integer Route_position = (Integer) BL_Position_Route.Get_Predicate().Get_Object_Complement();
		Integer sStep_position = (Integer) BL_Position_Step.Get_Predicate().Get_Object_Complement();
		
		
//		Integer Dangerous_Route_Value = (Integer) BL_Dangerous_Route.Get_Predicate().Get_Object_Complement();
		Integer Dangerous_Route_Value = Route_position;
		Integer Specular_Dangerous_Route_Value = Map.Get_Specular_Route( Dangerous_Route_Value );
		
		result.add("Route_"+Dangerous_Route_Value);
		result.add("Route_"+Specular_Dangerous_Route_Value);
		
		result.add(BL_Dangerous_Route.Get_Name());
		
		return result;
	}
	
	public HashSet<String> Inhibition_Regions_for_Come_Back( 
			TIntention Intention, 
			HashMap<String, TBelief> Map_Beliefs, 
			HashMap<String, TRegion> All_Map_Regions)
	{
		HashSet<String> result = new HashSet<String>();
		TBelief BL_Map = Map_Beliefs.get( "BL_Map");
		
		TEnvironment Map = (TEnvironment) BL_Map.Get_Predicate().Get_Object_Complement();
		
		TBelief BL_Dangerous_Route = Map_Beliefs.get( "BL_Dangerous_Position_Route");
		Integer Dangerous_Route_Value = (Integer) BL_Dangerous_Route.Get_Predicate().Get_Object_Complement();
		Integer Specular_Dangerous_Route_Value = Map.Get_Specular_Route( Dangerous_Route_Value );
		
		result.add("Route_"+Dangerous_Route_Value);
		result.add("Route_"+Specular_Dangerous_Route_Value);
		
		result.add(BL_Dangerous_Route.Get_Name());
		
		return result;
	}
	
	
}
