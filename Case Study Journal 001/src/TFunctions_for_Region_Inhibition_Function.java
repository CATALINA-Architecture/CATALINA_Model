import java.util.ArrayList;
import java.util.HashMap;

import com.Catalina_Model.Catalina_V_0_3.TAction;
import com.Catalina_Model.Catalina_V_0_3.TAttentional_Desire;
import com.Catalina_Model.Catalina_V_0_3.TBelief;
import com.Catalina_Model.Catalina_V_0_3.TEpistemic_Desire;
import com.Catalina_Model.Catalina_V_0_3.TExecutive_Inhibition_Function;
import com.Catalina_Model.Catalina_V_0_3.TIntention;
import com.Catalina_Model.Catalina_V_0_3.TPractical_Desire;
import com.Catalina_Model.Catalina_V_0_3.TRegion;

import com.Catalina_Model.Catalina_V_0_3.TOption;


public class TFunctions_for_Region_Inhibition_Function 
{
	private Autonomous_Vehicle_Demo Demo;
	private TCommon_Functions Common_Functions;
	private TExecutive_Inhibition_Function  Inhibition_Function;
	
	private ArrayList<String> List_For_Common_Regions;
	
	public TFunctions_for_Region_Inhibition_Function(Autonomous_Vehicle_Demo demo)
	{
		this.Demo = demo;
		this.Common_Functions = demo.Common_Functions;
		this.Inhibition_Function = null;
	}
	
	public void Set_Inhibition_Function (TExecutive_Inhibition_Function  inhibition_Function  )
	{
		this.Inhibition_Function = inhibition_Function;
	}
	
	public void Add_Inhibition_Functions()
	{
//		this.Inhibition_Function.Set_Generate_Unhinibited_Regions_for_Intention(this::Generate_Regions);
	}
	
	
	public HashMap<String, TRegion> Generate_Regions(
			TIntention Intention, HashMap<String, TBelief> Map_Belief,
			HashMap<String, TRegion> Map_Regions)
	{
		HashMap<String, TRegion> Result = new HashMap<String, TRegion>();
		TAttentional_Desire Attentional_Desire = (TAttentional_Desire)Intention.Get_Active_Desire();
		if( Attentional_Desire != null )
		{
			switch(Attentional_Desire)
			{
				// PRACTICAL DESIRES
				case TPractical_Desire Practical_Desire ->
				{
					Result.putAll(this.Generate_Regions_for_Destintion_City(
							Intention, Map_Belief, Map_Regions) );
				}
				
				case TEpistemic_Desire Epistemic_Desire ->
				{
					//for this case study we have not regions for Epistemic Desire
//					Result.addAll(null);
					Result.putAll(this.Generate_Regions_for_BL_Temporary_Closed_Routes(
							Intention, Map_Belief, Map_Regions) );
					
				}
			
				default -> throw new IllegalArgumentException("Unexpected value: " + Attentional_Desire);
			}
		}
		
		return Result;
		
	}
	
	public HashMap<String, TRegion> Generate_Regions_for_Destintion_City(
			TIntention Intention, HashMap<String, TBelief> Map_Beliefs,
			HashMap<String, TRegion> Regions)
	{
		HashMap<String, TRegion> Result = new HashMap<String, TRegion>();
		HashMap<Integer, TRegion> Map_Regions = new HashMap<Integer, TRegion>();
		
		for(TRegion Region: Regions.values())
		{
			TRegion_Simulation Region_Simulation = (TRegion_Simulation) Region;
			Integer Route_Number = Region_Simulation.Get_Route().Route_Number;
			Map_Regions.put(Route_Number, Region_Simulation);
		}
		
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
				
				TRegion_Simulation Region_Simulation  = 
						(TRegion_Simulation) Map_Regions.get( Route );
				TRegion_Simulation Specular_Region_Simulation  = 
						(TRegion_Simulation) Map_Regions.get( Specular_Route );
				
				if(Region_Simulation != null)
				{
					Result.put(Region_Simulation.Get_Name(), Region_Simulation);
				}
				if(Specular_Region_Simulation != null)
				{
					Result.put( Specular_Region_Simulation.Get_Name(), Specular_Region_Simulation);
				}
				
			}
		}
		
		return Result;
	}
	
	public HashMap<String, TRegion> Generate_Regions_for_BL_Temporary_Closed_Routes(
			TIntention Intention, HashMap<String, TBelief> Map_Beliefs,
			HashMap<String, TRegion> Map_Regions)
	{
		HashMap<String, TRegion> Result = new HashMap<String, TRegion>();
		return Result;
	}
	
	public HashMap<String, TRegion> Generate_Regions_for_Come_Back(
			TIntention Intention, HashMap<String, TBelief> Map_Beliefs,
			HashMap<String, TRegion> Map_Regions)
	{
		HashMap<String, TRegion> Result = new HashMap<String, TRegion>();
		return Result;
	}
	
	
	
	
}
