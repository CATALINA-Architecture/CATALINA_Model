import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import com.Catalina_Model.Catalina_V_0_3.TAction;
import com.Catalina_Model.Catalina_V_0_3.TAttention_Selection;
import com.Catalina_Model.Catalina_V_0_3.TAttentional_Desire;
import com.Catalina_Model.Catalina_V_0_3.TBelief;
import com.Catalina_Model.Catalina_V_0_3.TBeliefs_Names_Inhibition_Function_Result;
import com.Catalina_Model.Catalina_V_0_3.TDouble_Object;
import com.Catalina_Model.Catalina_V_0_3.TExecutive_Inhibition_Function;
import com.Catalina_Model.Catalina_V_0_3.TExecutive_Switching_Function;
import com.Catalina_Model.Catalina_V_0_3.TGlobal_Workspace;
import com.Catalina_Model.Catalina_V_0_3.TIntention;
import com.Catalina_Model.Catalina_V_0_3.TPractical_Desire;
import com.Catalina_Model.Catalina_V_0_3.TPredicate;
import com.Catalina_Model.Catalina_V_0_3.TRegion;
import com.Catalina_Model.Catalina_V_0_3.TType_Update_Contract;

public class TFunctions_for_Inhibition_Function 
{
	private Autonomous_Vehicle_Demo Demo;
	private TCommon_Functions Common_Functions;
	private TExecutive_Inhibition_Function  Inhibition_Function;
	public Integer Uninhibited_Data_Changes;
	private TDesires_Precondictions_Functions Desires_Preconditions_Functions;
	private TDesires_Beliefs_Functions Desires_Beliefs_Functions;
	private TDesires_Regions_Functions Desires_Regions_Functions;
	
	
	private ArrayList<String> List_For_Destination_City;
	
	public TFunctions_for_Inhibition_Function(Autonomous_Vehicle_Demo demo)
	{
		this.Demo = demo;
		this.Common_Functions = demo.Common_Functions;
		this.Inhibition_Function = null;
		this.Uninhibited_Data_Changes = 0;
		
		this.Desires_Preconditions_Functions = new TDesires_Precondictions_Functions();
		this.Desires_Beliefs_Functions = new TDesires_Beliefs_Functions();
		this.Desires_Regions_Functions = new TDesires_Regions_Functions();
		
		/**
		 * I create lists of common Beliefs useful for 
		 * Practical and Epistemic Desires
		 */
		this.List_For_Destination_City = new ArrayList<String>();
//		this.Create_List_For_Destination_City();
		
	}
	
	public TDesires_Beliefs_Functions Get_Desires_Beliefs_Functions()
	{
		return this.Desires_Beliefs_Functions;
	}
	
	public TDesires_Regions_Functions Get_Desires_Regions_Functions()
	{
		return this.Desires_Regions_Functions;
	}
	
	public TDesires_Precondictions_Functions Get_Desires_Preconditions_Functions()
	{
		return this.Desires_Preconditions_Functions;
	}
	
	
	
	public void Set_Inhibition_Function (TExecutive_Inhibition_Function  inhibition_Function  )
	{
		this.Inhibition_Function = inhibition_Function;
	}
	
	public void Add_Inhibition_Functions()
	{
		this.Add_Lists_for_Epistemic_Desires();
		this.Add_Lists_for_Practical_Desires();
		this.Add_Important_Beliefs();
		this.Set_CallBack();
		this.Set_General_CallBack();
		
	}
	
	public void Add_Lists_for_Epistemic_Desires()
	{
		if( this.Inhibition_Function != null)
		{
//			this.Inhibition_Function.
//				Register_Epistemic_Belief_Inhibition_Function(
//					"BLTS_Stimulus_Danger_on_the_Route", this::Add_Belief_List_BLTS_Stimulus_Danger_on_the_Route);
			/**
			 * Beliefs
			 */
			this.Inhibition_Function.
				Register_Epistemic_Belief_Inhibition_Function(
					"BLTS_Stimulus_Danger_on_the_Route", 
						this.Desires_Beliefs_Functions::Inhibition_Beliefs_for_Stimulus_Danger_on_the_Route);
			
			/**
			 * Regions
			 */
			this.Inhibition_Function.
				Register_Epistemic_Region_Inhibition_Function(
					"BLTS_Stimulus_Danger_on_the_Route",
						this.Desires_Regions_Functions::Get_Come_Back_Regions);
		}
	}
	
//	Add_Important_Belief_Type
	public void Add_Important_Beliefs()
	{
		if( this.Inhibition_Function != null)
		{
			ArrayList<String> Important_Beliefs = new ArrayList<String>();
			Important_Beliefs.add("BL_Current_Time");
			Important_Beliefs.add("BL_Danger_on_the_Route");
			Important_Beliefs.add("BL_Position_City");
			Important_Beliefs.add("BL_Position_Route");
			Important_Beliefs.add("BL_Position_Step");
			Important_Beliefs.add("BL_Temporary_Closed_Routes");
			Important_Beliefs.add("ST_Stimulus_Danger_on_the_Route");
			Important_Beliefs.add("ST_Stimulus_Irrelevant");
			Important_Beliefs.add("ST_Stimulus_Ok_Correct_Movement");
			Important_Beliefs.add("ST_Stimulus_Route_Status");
			Important_Beliefs.add("ST_Stimulus_Temporary_Closed_Route");
			Important_Beliefs.add("BL_Map");
			Important_Beliefs.add("BL_Numbered_City");
			this.Inhibition_Function.Add_Beliefs_to_Always_Consider("Important_Beliefs", Important_Beliefs);
		}
	}
	public void Add_Lists_for_Practical_Desires()
	{
		if( this.Inhibition_Function != null)
		{
//			/**
//			 * PRE_CONDITIONS
//			 * I associate functions 
//			 */
//			this.Inhibition_Function.
//			Register_Practical_Preconditions_Inhibition_Function(
//				"PD_Visit_Paris", this.Desires_Preconditions_Functions
//										::Get_Destination_City_Preconditions);
//			
//			this.Inhibition_Function.
//			Register_Practical_Preconditions_Inhibition_Function(
//				"PD_Visit_Frankfurt", this.Desires_Preconditions_Functions
//										::Get_Destination_City_Preconditions);
//			
//			this.Inhibition_Function.
//			Register_Practical_Preconditions_Inhibition_Function(
//				"PD_Visit_Rome", this.Desires_Preconditions_Functions
//										::Get_Destination_City_Preconditions);

			/**
			 * BELIEFS NOT FOR REASONER but TO EXECUTE ACTIONS
			 */
			this.Inhibition_Function.
			Register_Practical_Belief_Inhibition_Function(
					"Visit_Paris", this.Desires_Beliefs_Functions
											::Inhibition_Beliefs_for_Destination_City);
			
			this.Inhibition_Function.
			Register_Practical_Belief_Inhibition_Function(
					"Visit_Frankfurt", this.Desires_Beliefs_Functions
											::Inhibition_Beliefs_for_Destination_City);
			
			this.Inhibition_Function.
			Register_Practical_Belief_Inhibition_Function(
					"Visit_Rome", this.Desires_Beliefs_Functions
											::Inhibition_Beliefs_for_Destination_City);
			this.Inhibition_Function.
			Register_Practical_Belief_Inhibition_Function(
					"Visit_Cadiz", this.Desires_Beliefs_Functions
											::Inhibition_Beliefs_for_Destination_City);
			
			/**
			 * REGIONS NOT FOR REASONER but TO EXECUTE ACTIONS
			 */
			this.Inhibition_Function.
			Register_Practical_Region_Inhibition_Function(
					"Visit_Paris", this.Desires_Regions_Functions
											::Inhibition_Regions_for_Destintion_City);
			
			this.Inhibition_Function.
			Register_Practical_Region_Inhibition_Function(
					"Visit_Frankfurt", this.Desires_Regions_Functions
											::Inhibition_Regions_for_Destintion_City);
			
			this.Inhibition_Function.
			Register_Practical_Region_Inhibition_Function(
					"Visit_Rome", this.Desires_Regions_Functions
											::Inhibition_Regions_for_Destintion_City);
			
			this.Inhibition_Function.
			Register_Practical_Region_Inhibition_Function(
					"Visit_Cadiz", this.Desires_Regions_Functions
											::Inhibition_Regions_for_Destintion_City);
			
		}
	}
	
//	public TBeliefs_Names_Inhibition_Function_Result Add_Belief_List_BLTS_Stimulus_Danger_on_the_Route(
//			TAttentional_Desire Attentional_Desire, HashMap<String, TBelief> Map_Belief)
//	{
//		
//		TBeliefs_Names_Inhibition_Function_Result result = new TBeliefs_Names_Inhibition_Function_Result();
//		
//		HashSet<String> List_Beliefs_Names = new HashSet<String>();
//		if( this.Inhibition_Function != null)
//		{
//			String Stimulus_Name = "ST_Stimulus_Danger_on_the_Route";
//			List_Beliefs_Names.add(Stimulus_Name);
//			List_Beliefs_Names.add("BL_Danger_on_the_Route");
//			List_Beliefs_Names.add("BL_Come_Back_to_City");
//			List_Beliefs_Names.add("BL_Current_Time");
//			List_Beliefs_Names.add("BL_Map");
//			List_Beliefs_Names.add("BL_Temporary_Closed_Routes");
//			List_Beliefs_Names.add("BL_Position_City");
//			List_Beliefs_Names.add("BL_Position_Route");
//			List_Beliefs_Names.add("BL_Position_Step");
//			List_Beliefs_Names.add("BL_Next_Position_City");
//			List_Beliefs_Names.add("BL_Next_Position_Route");
//			List_Beliefs_Names.add("BL_Next_Position_Step");
//			
//			List_Beliefs_Names.addAll( this.List_For_Destination_City );
//			
//			TBelief Belief = Map_Belief.get( Stimulus_Name );
//			if( Belief != null )
//			{
//				TPredicate Predicate = Belief.Get_Predicate();
//				ArrayList<Integer> Positions = 
//						(ArrayList<Integer>) Predicate.Get_Subject();
//				Integer Route = Positions.get(1);
//				List_Beliefs_Names.add("BL_Route_Status_"+ Route);
//			}
//			else
//			{
//				this.Common_Functions.Print_Colored_Text(
//						"An error occours during registering Add_Belief_List_BLTS_Stimulus_Danger_on_the_Route Function in "
//						+ "TFunctions_for_Belief_Inhibition_Function", 2);
//			}
//		}
//		result.Set_Associated_Beliefs( List_Beliefs_Names );
//		return result;
//	}
	
//	public TBeliefs_Names_Inhibition_Function_Result Add_Belief_List_to_Destination_City(
//			TIntention Intention, HashMap<String, TBelief> Map_Belief)
//	{
//		TBeliefs_Names_Inhibition_Function_Result result = new TBeliefs_Names_Inhibition_Function_Result();
//		
//		
//		
//		HashSet<String> Beliefs_to_store = new HashSet<String>();
//		Beliefs_to_store.addAll( this.List_For_Destination_City );
//		
//		TEnvironment Map = (TEnvironment ) Map_Belief.get("BL_Map").
//				Get_Predicate().Get_Object_Complement();
//		
//		TAttentional_Desire Attentional_Desire = (TAttentional_Desire) 
//				Intention.Get_Active_Desire();
//		if( Attentional_Desire!= null)
//		{
//			Integer Selected_Option_Id = Intention.Get_Selected_Option_Id();
//
//			if (Selected_Option_Id >=0)
//			{
//				TOption_Simulation Option_Simulation =  
//						(TOption_Simulation) Attentional_Desire.Get_List_Options().
//								get( Selected_Option_Id );
//				
//				//I have to insert the Route Status and the Specular Route Status
//				//to come back in case of detecting dangers on the road
//				for (Integer Route: Option_Simulation.Path.Routes)
//				{
//					Integer Specular_Route = Map.Get_Specular_Route( Route );
//					Beliefs_to_store.add("BL_Route_Status_"+Route.toString() );
//					Beliefs_to_store.add("BL_Route_Status_"+Specular_Route.toString() );
//				}
//				for (TCity City: Option_Simulation.Path.Destinations)
//				{
//					Beliefs_to_store.add("BL_City_Visited_"+City.toString() );
//				}
//			}
//		}
//		
//		/**
//		 * Compute Preconditions of the Practical Desire
//		 */
//		HashSet<String> Preconditions_to_store = new HashSet<String>();
//		Preconditions_to_store.addAll( 
//				this.Sample_Compute_Preconditions( (TPractical_Desire) Attentional_Desire));
////		result.Set_Associated_Preconditions( Preconditions_to_store );
//				
//		result.Add_Associated_Beliefs( Beliefs_to_store );
//		return result;
//	}
	
	public void Set_CallBack()
	{
		this.Demo.Agent.Get_Global_WorkSpace().
		Set_CallBack_Set_Generate_Unhinibited_Regions_for_Intention(
				this::GlobalWorkspace_CallBack);
	}
	
	public void Set_General_CallBack()
	{
		this.Demo.Agent.Get_Global_WorkSpace().
		Set_General_CallBack( this::General_CallBack );
	}
	
	private void GlobalWorkspace_CallBack(ArrayList<TBelief> Pre_Conditions, 
				ArrayList<TBelief> Uninhibited_Beliefs,
				ArrayList<TAttentional_Desire> Uninhibited_Desires,
				ArrayList<TRegion> Uninhibited_Regions,
				TGlobal_Workspace Global_Workspace)
	{
		String Desires_Names = "";
		for(TAttentional_Desire Attentional_Desire: Uninhibited_Desires)
		{
			String Desire_Name ="erased";
			if (Attentional_Desire !=null)
			{
				Desire_Name = Attentional_Desire.Get_Name();
			}
			Desires_Names += Desire_Name+"\n";
			
		}
		this.Uninhibited_Data_Changes++;
		TDouble_Object Double_Object = Global_Workspace.Get_Saliency_and_Attention_Thresholds();
		Double Saliency_th = (Double) Double_Object.Get_Object_First();
		Double Attention_th = (Double) Double_Object.Get_Object_First();
		String Standing_Desire_Names = "";
		String Active_Desire_Names = "";
		String Active_Desire_Names_to_understand = "";
		String Precondiotion_Names = "";
		Integer N_Standing_Desire_Names = 0;
		
		ArrayList<TAttentional_Desire> Temp_Inhibited_Desires = new ArrayList<TAttentional_Desire>();
		Temp_Inhibited_Desires.addAll( Global_Workspace.Get_Inhibited_Attentional_Desires_from_LT() );
		
		Temp_Inhibited_Desires.removeAll( Global_Workspace.Get_Unhinibited_Desires() ); 
		
//		for(TAttentional_Desire Desire: Global_Workspace.Get_Inhibited_Attentional_Desires_from_LT())
		for(TAttentional_Desire Desire: Temp_Inhibited_Desires)
		{
			if(Desire != null)
			{
				Standing_Desire_Names +="--- "+Desire.Get_Name()+"\n";
				N_Standing_Desire_Names++;
			}
			
		}
		
//		for(TAttentional_Desire Desire: Uninhibited_Desires)
		
//		for(TAttentional_Desire Desire: Global_Workspace.Get_Unhinibited_Desires())
		for(TAttentional_Desire Desire: Global_Workspace.Get_Unhinibited_Desires())
		{
//			TAttentional_Desire Desire = (TAttentional_Desire) Intention.Get_Active_Desire();
//			Global_Workspace.Get_Unhinibited_Active_Desires();
			if(Desire != null)
			Active_Desire_Names +="- "+Desire.Get_Name()+": Saliency= "+Desire.Get_Saliency()+" - Intention= ";
			if(Desire.Get_Related_Intention() != null)
			{
				Active_Desire_Names +=Desire.Get_Related_Intention().Get_Name()+"\n";
			}
			else
			{
				Active_Desire_Names +="null\n";
			}
			
		}
		
//		for(TAttentional_Desire Desire: Global_Workspace.Get_Attentional_Desires())
//		{
////			TAttentional_Desire Desire = (TAttentional_Desire) Intention.Get_Active_Desire();
////			Global_Workspace.Get_Unhinibited_Active_Desires();
//			if(Desire != null)
//				Active_Desire_Names_to_understand +="- "+Desire.Get_Name()+": Saliency= "+Desire.Get_Saliency()+" - Intention= ";
//			if(Desire.Get_Related_Intention() != null)
//			{
//				Active_Desire_Names_to_understand +=Desire.Get_Related_Intention().Get_Name()+"\n";
//			}
//			else
//			{
//				Active_Desire_Names_to_understand +="null\n";
//			}
//			
//		}
		
		for(TBelief Belief: Pre_Conditions)
		{
			if(Pre_Conditions!= null)
			Precondiotion_Names +="--- "+Belief.Get_Name()+"\n";
		}
		String Selected_Intendion_Desire_Name = "";
		if(Global_Workspace.Get_Selected_Intentions().size()>0)
		{
			TIntention Intention = Global_Workspace.Get_Selected_Intentions().getFirst();
			if (((TAttentional_Desire) Intention.Get_Active_Desire()).Get_List_Options().size()>0)
			{
				Selected_Intendion_Desire_Name = "1\n--- "+
						Intention.Get_Active_Desire().Get_Name()
//							+" - Intntion: "+Intention.Get_Name()+"\n"
							;
			}
			else
			{
				Selected_Intendion_Desire_Name = "0\n";
			}
				
			
		}
		else
		{
			Selected_Intendion_Desire_Name = "0\n";
		}
		
		String Agent_Status ="";
		
		if( Saliency_th.equals(Attention_th))
		{
			Agent_Status="Unfocused";
		}
		else
		{
			Agent_Status="Focused";
		}
		this.Demo.Gui_Map.GlobaL_Workspace_View.Add_Text(
//		this.Demo.Gui_Map.GlobaL_Workspace_View.Add_Data(
				"After GW information update: n. "+this.Uninhibited_Data_Changes, true, Color.BLUE);
		this.Demo.Gui_Map.GlobaL_Workspace_View.Add_Text(
//				"Time Contatore: "+Global_Workspace.Contatore_di_Broadcast+"\n"+
//				"Agent is focused: "+!Double_Object.Get_Object_First().equals(Double_Object.Get_Object_Second())+"\n"+
				"\nAgent Status: "+ Agent_Status, false, Color.BLACK);
		this.Demo.Gui_Map.GlobaL_Workspace_View.Add_Text(
				"\nLong-term memory:", true, Color.BLACK);
		this.Demo.Gui_Map.GlobaL_Workspace_View.Add_Text(
//				"Standing Desires: "+ Global_Workspace.Get_Inhibited_Attentional_Desires_from_LT().size()+"\n"+
				"\nStanding Desires: "+ N_Standing_Desire_Names+"\n"+
				
							Standing_Desire_Names, false, Color.BLACK);
		this.Demo.Gui_Map.GlobaL_Workspace_View.Add_Text(					
				"Global Workspace:", true, Color.BLACK);
		this.Demo.Gui_Map.GlobaL_Workspace_View.Add_Text(
//				"Active Desires: "+Global_Workspace.Get_Unhinibited_Desires().size()+"\n"+
				"\nActive Desires: "+Global_Workspace.Get_Unhinibited_Desires().size()+"\n"+
							
							Active_Desire_Names+
//				"Active Active_Desire_Names_to_understand: "+Global_Workspace.Get_Attentional_Desires().size()+"\n"+
//							
//Active_Desire_Names_to_understand+
				"Active Desire of Selected Intention to pursue: "

				+Selected_Intendion_Desire_Name+
				"Desires' Pre_Conditions: "+Pre_Conditions.size()+"\n"+
							Precondiotion_Names+
				"Uninhibited Beliefs: "+Uninhibited_Beliefs.size()+
//				"Uninhibited_Desires: "+Uninhibited_Desires.size()+"\n"+
				"\nUninhibited Regions: "+Uninhibited_Regions.size()+
//				"Uninhibited_Desires Names: \n"+Desires_Names+"\n"+
				"\n---------------------------------\n", false, Color.BLACK);
		;
	}
	
	/**
	 * This is a sample functions to compute and acquire a list of string name of beliefs
	 * for a Practical Desire. 
	 * @param Practical_Desire
	 * @return
	 */
//	public HashSet<String> Sample_Compute_Preconditions(TPractical_Desire Practical_Desire)
//	{
//		HashSet<String> Pre_conditions = new HashSet<String>();
//		for (TBelief Belief: Practical_Desire.Get_Trigger_Condition().Get_Beliefs())
//		{
//			Pre_conditions.add(Belief.Get_Name());
//			/**
//			 * The Predicate of the Belief can be a TBelief, so
//			 * I insert this in Pre_conditions and in Unihibited_Beliefs
//			 */
//			if(Belief.Get_Predicate().Get_Subject() instanceof TBelief)
//			{
//				Pre_conditions.add( ((TBelief) Belief.Get_Predicate().Get_Subject()).Get_Name() );
//			}
//		}
//		return Pre_conditions;
//	}
	
	public void General_CallBack( TType_Update_Contract Update_Message,  
			TGlobal_Workspace GW )
	{
//		for(int i=0;i<10;i++)
//			this.Demo.Gui_Map.GlobaL_Workspace_View.Add_Data(Update_Message+" + "+
//					Global_Workspace.Contatore_di_Broadcast);
		String Stringa = "";
		String Stringa_Beliefs = "";
		for(TBelief belief: GW.Get_Map_Uninhibited_Beliefs().values())
		{
			Stringa_Beliefs +="\n-- "+belief.Get_Name();
		}
		
		String Stringa_Desires = "";
		for(TAttentional_Desire Desire: GW.Get_Unhinibited_Active_Desires())
		{
			Stringa_Desires +="\n-- "+Desire.Get_Name();
		}
		String Stringa_Intentions = "";
		for(TIntention Intention : GW.Get_Selected_Intentions())
		{
			Stringa_Intentions +="\n-- "+Intention.Get_Name();
		}
		Integer N_Selected_Intention = GW.Get_Selected_Intentions().size();
		String Stringa_Selected_Intention = "";
		if(GW.Get_Selected_Intentions().size() > 0)
		{
			TIntention Selected_Intention = GW.Get_Selected_Intentions().getFirst();
			if(((TAttentional_Desire) Selected_Intention.Get_Active_Desire()).
						Get_List_Options().size() >0)
			{
					Stringa_Selected_Intention = "\n-- "+Selected_Intention.Get_Name();
			}
		}
		TDouble_Object Double_Object = GW.Get_Saliency_and_Attention_Thresholds();
		
//		Stringa +="\nBroadcast signal: n. "+GW.Contatore_di_Broadcast;
		this.Demo.Gui_Map.GlobaL_Workspace_View.Add_Text(
				"Broadcast signal: n. "+GW.Contatore_di_Broadcast, true, Color.black);

				
				
		Stringa +="\nSignal Type: "+Update_Message;
		Stringa +="\nUninhibited Beliefs: "+GW.Get_Map_Uninhibited_Beliefs().size()+Stringa_Beliefs;
		Stringa +="\nUninhibited Desires: "+GW.Get_Unhinibited_Active_Desires().size()+Stringa_Desires;
		Stringa +="\nUninhibited Regions: "+GW.Get_Map_Uninhibited_Regions().size();
		Stringa +="\nUninhibited Intentions: "+N_Selected_Intention+Stringa_Intentions;
		Stringa +="\nUninhibited Selected Intention: "+GW.Get_Selected_Intentions().size()+ Stringa_Selected_Intention;
		Stringa +="\nSaliency Threshold: "+Double_Object.Get_Object_First();
		Stringa +="\nAttention Threshold: "+Double_Object.Get_Object_Second();
		
		Stringa +="\n--------------------------------------\n";
//		this.Demo.Gui_Map.GlobaL_Workspace_View.Add_Data( Stringa );
		this.Demo.Gui_Map.GlobaL_Workspace_View.Add_Simple_Text(Stringa);
		
	}

}
