package Catalina_Simulation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

public class TExecutive_Inhibition_Function {
	
	private TAgent Agent;
	private Double Attention_Threshold;
	private Double Saliency_Threshold;
	private TRegion Region;
	private boolean Updated_Beliefs;
	private boolean Updated_Intentions;
	private boolean Updated_Active_Desires;
	/**
	 * this is used only to show the focusing of the agent in Scenario 1 
	 */
	private boolean Focused_Message = false; //only to show Scenario 1
	
	private Double Default_Saliency_Threshold = 0.3;
	private Double Default_Attention_Threshold = Default_Saliency_Threshold;
	
	
	public TExecutive_Inhibition_Function(TAgent agent)
	{
		this.Agent = agent;
		this.Saliency_Threshold = Default_Saliency_Threshold;
		this.Attention_Threshold = this.Saliency_Threshold;
		this.Region = new TRegion();
		this.Updated_Beliefs = true;
		this.Updated_Intentions = true;
		this.Updated_Active_Desires = true;
		
	}
	
	public void Insert_in_List_Update_Contract()
	{
		this.Agent.Get_GW().Insert_for_Update_Contract(TType_Update_Contract.Beliefs, this);
		this.Agent.Get_GW().Insert_for_Update_Contract(TType_Update_Contract.Intentions, this);
		this.Agent.Get_GW().Insert_for_Update_Contract(TType_Update_Contract.Active_Desires, this);
	}
	
	public void Updated_Intentions()
	{
		this.Updated_Intentions = true;
	}
	
	public boolean Focus_Agent()
	{
		boolean result = true;
		try
		{
			if(this.Updated_Intentions)
			{
//				this.Updated_Intentions = false;
				//For our purpose we pursue only the first Intention
				if(this.Agent.Get_GW().Get_Intentions().size() > 0)
				{
					this.Updated_Intentions = false;
					Game.Print_Colored_Text("Stop before calling Focus_Agent method", 7);
//					Game.Press_Enter();
					
					this.Agent.Get_GW().Print_Data(2, 0);
				
					ArrayList<TIntention> Intentions = this.Agent.Get_GW().Get_Intentions();
					Collections.sort(Intentions, new TIntention_Compare());
					
					TIntention Intention = Intentions.getFirst();
					Game.Print("Intention Saliency: "+Intention.Get_Active_Desire().Get_Attentional_Standing_Desire().Get_Saliency());
					//this is used only to show the focusing of the agent in Scenario 1 
					if(Intention.Get_Active_Desire().Get_Attentional_Standing_Desire() instanceof TFunctional_Standing_Desire)
						{
							if(Intention.Get_Active_Desire().Get_Attentional_Standing_Desire().Get_Name().equals("Visit_Rome"))
							{
								if(! Focused_Message)
								{
									Game.Gui_Map.Show_Message("Agent Message", "Now, I focus my attention", null);
									Focused_Message = true;
								}
								
							}
							
						}
					//For our purpose we pursue only the first Intention
					//TIntention Intention = this.Agent.Get_GW().Get_Intentions().getFirst();
					//Game.Print("Functional Name for the Intention selected to pursue: "+Intention.get_Desire().get_Attentional_Goal().get_Name());
					
					this.Saliency_Threshold = Intention.Get_Active_Desire().Get_Attentional_Standing_Desire().Get_Saliency();
					this.Agent.Get_GW().Update_Saliency_Threshold(this.Saliency_Threshold);
					this.Agent.Get_E_Inhibition_Function().Set_Saliency_Threshold(this.Saliency_Threshold);
					
					this.Attention_Threshold = Intention.Get_Active_Desire().Get_Attentional_Standing_Desire().Saliency + 
							( (1-Intention.Get_Active_Desire().Get_Attentional_Standing_Desire().Saliency) /2); //to avoid to exceed 1.00 unit
					this.Agent.Get_GW().Update_Attention_Threshold(this.Attention_Threshold);
					this.Agent.Get_E_Inhibition_Function().Set_Attention_Threshold(this.Attention_Threshold);
					
					///
					/// CREATE INHIBITION REGIONS, INHIBITED GOALS AND BELIEFS-
					///
					Game.Print("I create the Inhibition Regions");
					TRegion Inhibition_Regions = this.Create_Inhibition_Regions(Intentions);
					this.Agent.Get_GW().Update_Inhibition_Regions(Inhibition_Regions);
					
					Game.Print("I create the Inhibited Standing_Desires");
					ArrayList<TAttentional_Standing_Desire> Inhibited_Standing_Desires = this.Create_Inhibited_Standing_Desires(Intentions);
					this.Agent.Get_GW().Update_Inhibited_Standing_Desires(Inhibited_Standing_Desires);
					

					Game.Print("I create the Inhibited Beliefs");
					ArrayList<TBelief> Inhibited_Beliefs = this.Create_Inhibited_Beliefs(Intentions); 
					this.Agent.Get_GW().Update_Inhibited_Beliefs(Inhibited_Beliefs);
	
					this.Agent.Get_GW().Print_Data(1, 1);
				}
			}
//			else
//			{
//				this.Agent.Get_E_Switching_Function().UnFocus_Agent();
//			}
		}
		catch (Exception e) 
		{
	      Game.Print("Something went wrongin method: Focus_Attention.");
	      Game.Print("Message Error: "+e.getMessage());
	      Game.PrintLn();
	      e.printStackTrace();
	      result = false;
	    }
		
		return result;
		
	}
	
	public Boolean Agent_Focused()
	{
		return !(this.Saliency_Threshold == this.Attention_Threshold);
	}
	
	/***
	 * Untill now, I create only the case in which I want to go in a destination station by Functional Goal
	 * Later, I must to create the cases to handle the cases of Epistemic Goals
	 * @return
	 */
	private TRegion Create_Inhibition_Regions(ArrayList<TIntention> Intentions)
	{
		/*
		* as regions you should consider paths. So you should create all
		* paths where the start and destination are different? Or just the destination?
		*/
		 
		//ArrayList<TBelief_Base> Inhibited_Beliefs = new ArrayList<TBelief_Base>();
		//ArrayList<TBelief_Base> Inhibited_Beliefs = null;

		//I create All region
		TRegion Inhibited_Regions = this.Agent.Get_GW().Compute_All_Regions();
		Environment Map = this.Agent.Get_WMM().Get_Map();
	
//		TIntention Intention = this.Agent.Get_GW().Get_Intentions().getFirst();
		//TOption Option = Intention.get_Desire().get_Option_List().get(Intention.get_Seleted_Option_Id());
		for(TIntention Intention: Intentions)
		{
			if (Intention.Get_Active_Desire().Get_Attentional_Standing_Desire() instanceof TFunctional_Standing_Desire)
			{
				//
				//THIS IS A FUNCTIONAL Standing_Desire
				// 
				
				//The Standing_Desire is a Functional Standing_Desire, so it means Agent wants to use a specific Route
				//In this case Agent wants to inhibite all belief that aren't that specific Route
				
				TFunctional_Standing_Desire Functional_Standing_Desire = (TFunctional_Standing_Desire) Intention.Get_Active_Desire().Get_Attentional_Standing_Desire();
				// If the functional belief is Belief_Destination_Station, it means that it is a Functional Goal
				// to go in a specific station, so I don't must to inhibit only specific beliefs
				switch(Functional_Standing_Desire.Get_Final_State().Get_Type_Belief())
				{
					case TType_Beliefs.Belief_Destination_City,
						 TType_Beliefs.Belief_Come_Back_to_City:
						
						//I Remove path of the Intention of the Desire of the Functional Goal 
						//from Inhibited_Regions
						int Index_Option = Intention.Get_Seleted_Option_Id();
						TActive_Desire Active_Desire = Intention.Get_Active_Desire();
						if(Active_Desire.Get_Option_List().size()>0)
						{
							TOption Option = Active_Desire.Get_Option_List().get(Index_Option);
							//GO_TO
							//I get integer_Route from the selected Option only if "Action_Name" is
							// "GO_TO"
							ArrayList<Integer> Integer_Routes_to_Remove = new ArrayList<Integer>();
							ArrayList<Route> Routes_to_Remove = new ArrayList<Route>();
							Route The_Route = null;
							int Integer_Route = -1;
							TPosition_Train_Coords Postcondition_Train_Coords = null;
							for(TAction Action: Option.Get_Plan_Actions())
							{
								switch(Action.Get_Action_Name())
								{
								case "GO_TO_Route":
									//Game.Print(Action.get_Params().get(0).getClass());
									Postcondition_Train_Coords = (TPosition_Train_Coords) Action.Get_Params().get(0);
									
		//							int Integer_Route = (int) Action.get_Params().get(0);
									Integer_Route = (int) Postcondition_Train_Coords.Get_Route();
									
									int Specular_Integer_Route = 0;
									if (Integer_Route % 2 == 0)
									{
										Specular_Integer_Route = Integer_Route + 1;
									}
									else
									{
										Specular_Integer_Route = Integer_Route - 1;
									}
									
									if(Integer_Route >= 0)
									{
										Integer_Routes_to_Remove.add(Integer_Route);
										Integer_Routes_to_Remove.add(Specular_Integer_Route);
										
										Routes_to_Remove.add(Map.Get_Route(Integer_Route));
										Routes_to_Remove.add(Map.Get_Route(Specular_Integer_Route));
										
										//I Remove all destinations form inhibition region that is in Path to pursue
										The_Route = Map.Get_Route(Integer_Route);
										Inhibited_Regions.Destinations.remove(The_Route.Get_Departure());
										Inhibited_Regions.Destinations.remove(The_Route.Get_Destination());
									}
									
									break;
								case "Come_Back_to_City":
									
									Postcondition_Train_Coords = (TPosition_Train_Coords) Action.Get_Params().get(0);
									
									Integer_Route = (int) Postcondition_Train_Coords.Get_Route();
									if(Integer_Route >= 0)
									{
										Integer_Routes_to_Remove.add(Integer_Route);
										
										Routes_to_Remove.add(Map.Get_Route(Integer_Route));
										
										//I Remove all destinations form inhibition region that is in Path to pursue
										The_Route = Map.Get_Route(Integer_Route);
										Inhibited_Regions.Destinations.remove(The_Route.Get_Destination());
									}
									
									break;
								}
							}
							Inhibited_Regions.Integer_Routes.removeAll(Integer_Routes_to_Remove);
							Inhibited_Regions.Routes.removeAll(Routes_to_Remove);
						}
						break;
					default:
						Game.Print("I cannot handle Belief Type of TFunctional_Goal in Create_Inhibition_Regions Function");
				}
			}
			else
			{
				//
				//THIS IS AN EPISTEMIC Standing_Desire
				// 
				
				//The goal is a EPISTEMIC Standing_Desire, so it means Agent wants to use a specific Route
				//In this case Agent wants to inhibite all belief that aren't that specific Route
				
				TEpistemic_Standing_Desire Epistemic_Standing_Desire = (TEpistemic_Standing_Desire) Intention.Get_Active_Desire().Get_Attentional_Standing_Desire();
				// If the functional belief is Belief_Destination_Station, it means that it is a Functional Goal
				// to go in a specific station, so I don't must to inhibit only specific beliefs
				Game.Print("I cannot handle TEpistemic_Standing_Desire in Create_Inhibition_Regions Function");
				
				int Index_Option = 0;
				TActive_Desire Active_Desire = null;
				TOption Option = null;
				TAction Action = null;
				
				switch(Epistemic_Standing_Desire.Get_Belief().Get_Type_Belief())
				{
					case TType_Beliefs.Stimulus_Temporary_Closed_Route:
						
						//I Remove path of the Intention of the Desire of the Functional Goal 
						//from Inhibited_Regions
						Index_Option = Intention.Get_Seleted_Option_Id();
						Active_Desire = Intention.Get_Active_Desire();
						Option = Active_Desire.Get_Option_List().get(Index_Option);
						//Use_Route
						//I get integer_Route from the selected Option only if "Action_Name" is
						// "Ask_for_Temporary_Closed_Route"
						//We have only ONE Route, the first and only param in the first and only Action
						Action = Option.Get_Plan_Actions().getFirst();
						//Ask_for_Temporary_Closed_Route
						
						if(Action.Get_Action_Name() == "Ask Closed Route Duration")
						{
							int Integer_Route = (int) Action.Get_Params().getFirst();
							int Specular_Integer_Route = Map.Get_Specular_Route(Integer_Route);
							Route The_Route = Map.Get_Route(Integer_Route);
							Route Specular_Route = Map.Get_Route(Specular_Integer_Route);
							Inhibited_Regions.Destinations.remove(The_Route.Get_Departure());
							Inhibited_Regions.Destinations.remove(The_Route.Get_Destination());

							Inhibited_Regions.Integer_Routes.remove(Integer_Route);
							Inhibited_Regions.Integer_Routes.remove(Specular_Integer_Route);
							
							Inhibited_Regions.Routes.remove(The_Route);
							Inhibited_Regions.Routes.remove(Specular_Route);
						}
						break;
					case TType_Beliefs.Stimulus_Danger_on_the_Route:
						//I Remove path of the Intention of the Desire of the Functional Goal 
						//from Inhibited_Regions
						Index_Option = Intention.Get_Seleted_Option_Id();
						Active_Desire = Intention.Get_Active_Desire();
						Option = Active_Desire.Get_Option_List().get(Index_Option);
						//Use_Route
						//I get integer_Route from the selected Option only if "Action_Name" is
						// "Ask_for_Temporary_Closed_Route"
						//We have only ONE Route, the first and only param in the first and only Action
						Action = Option.Get_Plan_Actions().getFirst();
						//Ask_for_Temporary_Closed_Route
						
						if(Action.Get_Action_Name() == "Ask Danger Type on the road")
						{
//							int Integer_Route = (int) Action.Get_Params().getFirst();
							TTriple_Object Danger_Data =  (TTriple_Object) Action.Get_Params().getFirst();
							
							TPosition_Train_Coords Pre_condition_Position = (TPosition_Train_Coords) Danger_Data.Get_Object_First();
							TPosition_Train_Coords Post_condition_Position = (TPosition_Train_Coords) Danger_Data.Get_Object_Second();
							
							int Integer_Route = Math.max(Pre_condition_Position.Get_Route(), Post_condition_Position.Get_Route());
							
							int Specular_Integer_Route = Map.Get_Specular_Route(Integer_Route);
							Route The_Route = Map.Get_Route(Integer_Route);
							Route Specular_Route = Map.Get_Route(Specular_Integer_Route);
							Inhibited_Regions.Destinations.remove(The_Route.Get_Departure());
							Inhibited_Regions.Destinations.remove(The_Route.Get_Destination());

							Inhibited_Regions.Integer_Routes.remove(Integer_Route);
							Inhibited_Regions.Integer_Routes.remove(Specular_Integer_Route);
							
							Inhibited_Regions.Routes.remove(The_Route);
							Inhibited_Regions.Routes.remove(Specular_Route);
						}
						break;
					default:
						Game.Print("I cannot handle Belief Type of TEpistemic_Goal in Create_Inhibition_Regions Function");
				}
			}
		}
		return Inhibited_Regions;
	}
	
	public Double Get_Attention_Threshold() {
		return Attention_Threshold;
	}


	public void Set_Attention_Threshold(Double attention_Threshold) {
		Attention_Threshold = attention_Threshold;
	}


	public Double Get_Saliency_Threshold() {
		return Saliency_Threshold;
	}


	public void Set_Saliency_Threshold(Double saliency_Threshold) {
		Saliency_Threshold = saliency_Threshold;
	}
	
	private ArrayList<TAttentional_Standing_Desire> Create_Inhibited_Standing_Desires(ArrayList<TIntention> Intentions)
	{
		ArrayList<TAttentional_Standing_Desire> Inhibited_Standing_Desires = new ArrayList<TAttentional_Standing_Desire>();
		
		Inhibited_Standing_Desires.addAll(this.Agent.Get_GW().Get_Goals());
//		
		for(TIntention Intention: Intentions)
		{
			Inhibited_Standing_Desires.remove(Intention.Get_Active_Desire().Get_Attentional_Standing_Desire());
		}

		return Inhibited_Standing_Desires;
	}
	
	private ArrayList<TBelief> Create_Inhibition_Regions_From_Destination_Station()
	{
		ArrayList<TBelief> Inhibited_Beliefs = new ArrayList<TBelief>();
		
		Inhibited_Beliefs.addAll(this.Agent.Get_GW().Get_Beliefs());
		int i=0;
		//Only three Beliefs can be saved: The current station, current route, current step, next station, next route, next step 
		//int Saved_Beliefs = 0;
		int Number_Of_Beliefs_to_Save = 6;

		ArrayList<TType_Beliefs> cases = new ArrayList<TType_Beliefs>();
		cases.add(TType_Beliefs.Belief_Current_City);
		cases.add(TType_Beliefs.Belief_Current_Route);
		cases.add(TType_Beliefs.Belief_Current_Step);
		cases.add(TType_Beliefs.Belief_Next_City);
		cases.add(TType_Beliefs.Belief_Next_Route);
		cases.add(TType_Beliefs.Belief_Current_City);
		
		while (i< Inhibited_Beliefs.size())
		{
			TBelief Belief_To_Not_Inhibit = Inhibited_Beliefs.get(i);
			if(cases.contains(Belief_To_Not_Inhibit.Get_Type_Belief()))
			if(Belief_To_Not_Inhibit.Get_Predicate().Get_Subject() == TType_Subject.Me)
				{
					Inhibited_Beliefs.remove(Belief_To_Not_Inhibit);
					Number_Of_Beliefs_to_Save--;
					//Game.Print("Number_Of_Beliefs_to_Save to "+Number_Of_Beliefs_to_Save);
				}
			
			if(Number_Of_Beliefs_to_Save == 0)
			{
				break;
			}								
			i++;
		}
		return Inhibited_Beliefs;
	}
	
	public Double Get_Default_Saliency_Threshold()
	{
		return this.Default_Saliency_Threshold;
	}
	
	public Double Get_Default_Attention_Threshold()
	{
		return this.Default_Attention_Threshold;
	}	
	
	private ArrayList<TBelief> Create_Inhibited_Beliefs(ArrayList<TIntention> Intentions)
	{
//		Game.Print("************  Function:  Create_Inhibited_Beliefs form Focus  *********: TShifting_Attention");
//		this.Agent.get_GW().Print_Data(0, 0);
		ArrayList<TBelief> Inhibited_Beliefs = new ArrayList<TBelief>();
		Inhibited_Beliefs.addAll(this.Agent.Get_GW().Get_Beliefs());
		
		HashMap<String, ArrayList<TBelief>> Map_Attentional_Standing_Desire_and_Beliefs = null;
		Map_Attentional_Standing_Desire_and_Beliefs = this.Agent.Get_WMM().Get_Map_Attentional_Standing_Desires_and_Beliefs();

		for (TIntention Intention: Intentions)
		{
			String Attentional_Name = Intention.Get_Active_Desire().Get_Attentional_Standing_Desire().Get_Name();
			TAttentional_Standing_Desire Attentional_Standing_Desire =  Intention.Get_Active_Desire().Get_Attentional_Standing_Desire();
			//HashMap<String, ArrayList<TBelief_Base>> Map_Attentional_Goal_and_Beliefs =this.EI.Agent.get_GW().Get_Map_Attentional_Goal_and_Beliefs();
			ArrayList<TBelief> Old_All_related_Beliefs_Of_Standing_Desire = Map_Attentional_Standing_Desire_and_Beliefs.get(Attentional_Name);
			if(Attentional_Standing_Desire instanceof TFunctional_Standing_Desire)
			{
				TFunctional_Standing_Desire Functional_Standing_Desire = (TFunctional_Standing_Desire) Attentional_Standing_Desire;
				TType_Beliefs Type_Belief = Functional_Standing_Desire.Get_Final_State().Get_Type_Belief();
				switch(Type_Belief)
				{
				case Belief_Destination_City, Belief_Come_Back_to_City:
					//Qui devo inserire tutte le beliefs collegate all'intention
					//Here, I insert in Map_Attentional_Goal_and_Beliefs only all Beliefs related to
					//the Functional Goal by zero, so:
					///Final_State of Functional_Goal, Belief_Path_Taken_For_Belief and
					///Belief of route
					//Belief_Current_Time
					//Belief_Path_Taken_For_Belief
					//Belief_Current_Station
					//Belief_Current_Route
					//Belief_Current_Step
					//Belief_Number_Players
					//Belief_Busy_Route
					//Belief_Closed_Route
					//Belief_Temporary_Closed_Route
//					Game.Print("All_related_Beliefs_Of_Goal: "+Old_All_related_Beliefs_Of_Goal);
					for(TBelief Bel: Old_All_related_Beliefs_Of_Standing_Desire)
					{
//						Game.Print(Bel.get_Name()+ " - "+Bel.get_Type_Belief());
					}
//					Game.Print("New All_related_Beliefs_Of_Goal: "+Old_All_related_Beliefs_Of_Goal);
					ArrayList<TBelief> New_All_related_Beliefs_Of_Standing_Desire = new ArrayList<TBelief>();
					New_All_related_Beliefs_Of_Standing_Desire.addAll(Create_List_of_Belief__for_Destination_Station(Intention, Functional_Standing_Desire));
					Old_All_related_Beliefs_Of_Standing_Desire.clear();
					Old_All_related_Beliefs_Of_Standing_Desire.addAll(New_All_related_Beliefs_Of_Standing_Desire);
					break;
				default:
					Game.Print("I cannot handle type Functional Belief in Create_Inhibited_Beliefs: "+Type_Belief);
					Game.End_Game();
				}
			}
			else
			{
				TEpistemic_Standing_Desire Epistemic_Standing_Desire = (TEpistemic_Standing_Desire) Attentional_Standing_Desire;
				TType_Beliefs Type_Belief = Epistemic_Standing_Desire.Get_Belief().Get_Type_Belief();
				switch(Type_Belief)
				{
				case Stimulus_Temporary_Closed_Route:
//					//Here, I insert in Map_Attentional_Goal_and_Beliefs only all Beliefs related to
//					//the Epistemic Goal by zero, so:
//					///()Salient)Belief of Functional_Goal, Belief_Path_Taken_For_Belief and
//					///Belief of route
////					TType_Beliefs.Belief_Current_Time
//					//Belief_Number_Players
//					//Belief_Temporary_Closed_Route
////					Game.Print("All_related_Beliefs_Of_Goal: "+Old_All_related_Beliefs_Of_Goal);
//					for(TBelief_Base Bel: Old_All_related_Beliefs_Of_Goal)
//					{
////						Game.Print(Bel.get_Name()+ " - "+Bel.get_Type_Belief());
//					}
////					Game.Print("New All_related_Beliefs_Of_Goal: "+Old_All_related_Beliefs_Of_Goal);
//					ArrayList<TBelief_Base> New_All_related_Beliefs_Of_Goal = new ArrayList<TBelief_Base>();
//					
//					New_All_related_Beliefs_Of_Goal.addAll(Create_List_of_Belief__for_Stimulus_Temporary_Closed_Route(Epistemic_Goal));
//					
//					Old_All_related_Beliefs_Of_Goal.clear();
//					Old_All_related_Beliefs_Of_Goal.addAll(New_All_related_Beliefs_Of_Goal);
//					//New_All_related_Beliefs_Of_Goal.addAll(Create_List_of_Belief__for_Destination_Station(Functional_Goal));
//					
//					Old_All_related_Beliefs_Of_Goal.clear();
//					Old_All_related_Beliefs_Of_Goal.addAll(New_All_related_Beliefs_Of_Goal);
					
					
					break;
				case Stimulus_Route_Status:
					break;
				case Stimulus_Danger_on_the_Route:
					break;
				case Stimulus_Irrelevant:
					break;
				default:
					Game.Print("I cannot handle type Epistemic Belief in Create_Inhibited_Beliefs: "+Type_Belief);
					Game.End_Game();
				}
			}
			Inhibited_Beliefs.removeAll(Old_All_related_Beliefs_Of_Standing_Desire);
		}
//		this.Agent.get_GW().Print_Data(1, 0);
		return Inhibited_Beliefs;
	}
	
	private ArrayList<TBelief> Create_List_of_Belief__for_Destination_Station(TIntention Intention, 
			TFunctional_Standing_Desire Functional_Standing_Desire)
	{
		ArrayList<TBelief> New_All_related_Beliefs_Of_Standing_Desire = new ArrayList<TBelief>();
//		TBelief_Base Belief = null;
		int Selected_option_Id = Intention.Get_Seleted_Option_Id();
		TActive_Desire Active_Desire = Intention.Get_Active_Desire();
		ArrayList<Integer> Routes = new ArrayList<Integer>();
		ArrayList<City> Destinations = new ArrayList<City>();
		
		if(Active_Desire.Get_Option_List().size() > 0)
		{
			TOption Selected_Option = Active_Desire.Get_Option_List().get(Selected_option_Id);
			Routes.addAll(Selected_Option.Path.Routes);
			Destinations.addAll(Selected_Option.Path.Destinations);
		}
		
		ArrayList<TBelief> All_Beliefs = this.Agent.Get_GW().Get_Beliefs_from_WMM();
		New_All_related_Beliefs_Of_Standing_Desire.add(Functional_Standing_Desire.Get_Final_State());
		for(TBelief Belief: All_Beliefs)
		{
//			Game.Print(Belief.get_Name()+"  -*********  "+Belief.get_Type_Belief());
			switch(Belief.Get_Type_Belief())
			{
			case Belief_Destination_City, Belief_Come_Back_to_City:
				if(Functional_Standing_Desire.Get_Final_State_Name() == Belief.Get_Name())
				{
					if(!New_All_related_Beliefs_Of_Standing_Desire.contains(Belief))
					{
						New_All_related_Beliefs_Of_Standing_Desire.add(Belief);
					}
				}
				break;
			case Belief_Visited_City:
				if(Destinations.contains(Belief.Predicate.Get_Subject()))
				{
					if(!New_All_related_Beliefs_Of_Standing_Desire.contains(Belief))
					{
						New_All_related_Beliefs_Of_Standing_Desire.add(Belief);
					}
				} 
				break;
			case Belief_Route_Status:
				if(Routes.contains(Belief.Predicate.Get_Subject()))
				{
					if(!New_All_related_Beliefs_Of_Standing_Desire.contains(Belief))
					{
						New_All_related_Beliefs_Of_Standing_Desire.add(Belief);
					}
				} 
				break;
			case 	Belief_Current_Time, Belief_Current_City, Belief_Current_Route, Belief_Current_Step,
				 	Belief_Number_Players, Belief_Next_Route, Belief_Next_City, Belief_Next_Step,
				 	Belief_Prev_Route, Belief_Prev_City, Belief_Prev_Step, Belief_Map,
				 	Belief_Temporary_Closed_Route, Belief_Busy_Route, Belief_Danger_on_the_Route:
				 	
					if(!New_All_related_Beliefs_Of_Standing_Desire.contains(Belief))
					{
						New_All_related_Beliefs_Of_Standing_Desire.add(Belief);
					}
				break;
			case Belief_Path_Taken_For_Belief:
				if(Functional_Standing_Desire.Get_Name() == (String) Belief.Get_Predicate().Get_Subject())
				{
					if(!New_All_related_Beliefs_Of_Standing_Desire.contains(Belief))
					{
						New_All_related_Beliefs_Of_Standing_Desire.add(Belief);
					}
				}
				break;
			//I insert also All Stimulus
			case Stimulus_Ok_Correct_Movement, Stimulus_Too_Close_To_The_Train, 
				Stimulus_Closed_Route, Stimulus_Busy_Route, Stimulus_Temporary_Closed_Route, 
				Stimulus_Crowded_Route, Stimulus_Route_Status, Stimulus_Danger_on_the_Route, 
				Stimulus_Irrelevant:
					if(!New_All_related_Beliefs_Of_Standing_Desire.contains(Belief))
					{
						New_All_related_Beliefs_Of_Standing_Desire.add(Belief);
					}
				break;	
//			default: 
//				Game.Print("I cannot to handle Type_Belief inside Create_List_of_Belief__for_Destination_Station "
//						+ "in TShifting_Attention: "+ Belief.get_Type_Belief());
				//Game.End_Game();
			}
		}
		//Game.Print(New_All_related_Beliefs_Of_Goal);
		return New_All_related_Beliefs_Of_Standing_Desire;
	}
	
	private ArrayList<TBelief> Create_List_of_Belief__for_Stimulus_Danger_on_the_Route(TEpistemic_Standing_Desire Epistemic_Standing_Desire)
	{
		ArrayList<TBelief> New_All_related_Beliefs_Of_Standing_Desire = new ArrayList<TBelief>();
//		TBelief_Base Belief = null;
		ArrayList<TBelief> All_Beliefs = this.Agent.Get_GW().Get_Beliefs_from_WMM();
		New_All_related_Beliefs_Of_Standing_Desire.add(Epistemic_Standing_Desire.Get_Belief());
		for(TBelief Belief: All_Beliefs)
		{
//			Game.Print(Belief.get_Name()+"  -*********  "+Belief.get_Type_Belief());
			switch(Belief.Get_Type_Belief())
			{
			case 	Belief_Current_Time, Belief_Current_City, Belief_Current_Route, Belief_Current_Step,
				 	Belief_Number_Players, Belief_Next_Route, Belief_Next_City, Belief_Next_Step:
				 	New_All_related_Beliefs_Of_Standing_Desire.add(Belief);
				break;
//			default: 
//				Game.Print("I cannot to handle Type_Belief inside Create_List_of_Belief__for_Destination_Station "
//						+ "in TShifting_Attention: "+ Belief.get_Type_Belief());
				//Game.End_Game();
			}
		}
		return New_All_related_Beliefs_Of_Standing_Desire;
	}
	
	private ArrayList<TBelief> Create_List_of_Belief__for_Stimulus_Temporary_Closed_Route(TEpistemic_Standing_Desire Epistemic_Standing_Desire)
	{
		ArrayList<TBelief> New_All_related_Beliefs_Of_Standing_Desire = new ArrayList<TBelief>();
//		TBelief_Base Belief = null;
		ArrayList<TBelief> All_Beliefs = this.Agent.Get_GW().Get_Beliefs_from_WMM();
		New_All_related_Beliefs_Of_Standing_Desire.add(Epistemic_Standing_Desire.Get_Belief());
		for(TBelief Belief: All_Beliefs)
		{
//			Game.Print(Belief.get_Name()+"  -*********  "+Belief.get_Type_Belief());
			switch(Belief.Get_Type_Belief())
			{
			case 	Belief_Current_Time, Belief_Current_City, Belief_Current_Route, Belief_Current_Step,
				 	Belief_Number_Players, Belief_Next_Route, Belief_Next_City, Belief_Next_Step:
				 	New_All_related_Beliefs_Of_Standing_Desire.add(Belief);
				break;
//			default: 
//				Game.Print("I cannot to handle Type_Belief inside Create_List_of_Belief__for_Destination_Station "
//						+ "in TShifting_Attention: "+ Belief.get_Type_Belief());
				//Game.End_Game();
			}
		}
		return New_All_related_Beliefs_Of_Standing_Desire;
	}
	
	public void Updated_Beliefs()
	{
		this.Updated_Beliefs = true;
	}
	
	public boolean Desire_Deletion()
	{
		boolean result = true;
		if(this.Updated_Active_Desires)
		{
			this.Updated_Active_Desires = false;
			TGlobal_Workspace GW = this.Agent.Get_GW();
			//We said that these are only Functional Standing_Desires
			ArrayList<TFunctional_Standing_Desire> Functional_Standing_Desires = GW.Get_Functional_Standing_Desires();

			ArrayList<TFunctional_Standing_Desire> Inhibited_Functional_Standing_Desires = GW.Get_Inhibited_Functional_Standing_Desires();
			//A goal is inhibited when its conditions are not met, or when its saliency is
			//lower than the Saliency or Attention Threshold of the Agent(see figure 3 of the journal)
			
			ArrayList<TFunctional_Standing_Desire> Uninhibited_Functional_Standing_Desires = new ArrayList<TFunctional_Standing_Desire>() ;
			Uninhibited_Functional_Standing_Desires.addAll(Functional_Standing_Desires);
			
			//Compute Functional_Goals - Inhibited_Functional_Standing_Desires
			Uninhibited_Functional_Standing_Desires.removeAll(Inhibited_Functional_Standing_Desires);
			this.Agent.Get_GW().Print_Data(1, 0);
			
			ArrayList<TActive_Desire> Active_Desires = GW.Get_Active_Desires();
			
			// for all d in current_Desires
			Game.Print("Active Desires: "+Active_Desires.size()+ "- "+Active_Desires);
			int i=0;
//			
			ArrayList<TActive_Desire> Active_Desire_To_Remove = new ArrayList<TActive_Desire>();
			
			for (TActive_Desire Active_Desire: Active_Desires)
			{
				i++;
				Game.Print(i);
//				Game.Print("I Delete a Desire. Desire name: "+Desire.Get_Name() + " - related Functional Goal: "+Desire.get_Attentional_Goal().get_Name());
//				Game.Print("Desire Saliency is: "+Desire.get_Attentional_Goal().get_Saliency()+ " - while Saliency Threshold is: "+this.Agent.Get_E_Inhibition_function().get_Saliency_Threshold());
				if (Uninhibited_Functional_Standing_Desires.contains(Active_Desire.Get_Attentional_Standing_Desire()) )
				{
					
					if (Active_Desire.Get_Attentional_Standing_Desire().Get_Saliency() < this.Agent.Get_E_Inhibition_Function().Get_Saliency_Threshold())
					{
						Game.Print("I Delete a Desire. Desire name: "+Active_Desire.Get_Name() + " - related Functional Desire: "+Active_Desire.Get_Attentional_Standing_Desire().Get_Name());
						Game.Print("Desire Saliency is: "+Active_Desire.Get_Attentional_Standing_Desire().Get_Saliency()+ " - while Saliency Threshold is: "+this.Agent.Get_E_Inhibition_Function().Get_Saliency_Threshold());
						Game.Print("Desire Saliency is: "+Active_Desire.Get_Attentional_Standing_Desire().Get_Saliency()+ " - while Saliency Threshold is: "+this.Agent.Get_GW().Get_Saliency_Threshold());
						Active_Desire_To_Remove.add(Active_Desire);
					}
				}
				else
				{
					
					if (Active_Desire.Get_Attentional_Standing_Desire().Get_Saliency() < this.Agent.Get_E_Inhibition_Function().Get_Attention_Threshold())
					{
						Game.Print("I Delete a Desire. Desire name: "+Active_Desire.Get_Name() + " - related Functional Desire: "+Active_Desire.Get_Attentional_Standing_Desire().Get_Name());
						Game.Print("Desire Saliency is: "+Active_Desire.Get_Attentional_Standing_Desire().Get_Saliency()+ " - while Attention Threshold is: "+this.Agent.Get_E_Inhibition_Function().Get_Attention_Threshold());
						Game.Print("Desire Saliency is: "+Active_Desire.Get_Attentional_Standing_Desire().Get_Saliency()+ " - while Attention Threshold is: "+this.Agent.Get_GW().Get_Attention_Threshold());
						Active_Desire_To_Remove.add(Active_Desire);
					}
				}
			}
			
			Game.Print("Active_Desire_To_Remove: "+Active_Desire_To_Remove.size()+" - "+Active_Desire_To_Remove);
			Game.Print("Active Desires: "+Active_Desires.size()+" - "+Active_Desires);
			if(Active_Desire_To_Remove.size()>0)
			{
				GW.Delete_Desires(Active_Desire_To_Remove);
			}

			Game.Print("New Active Desires: "+Active_Desires.size()+" - "+Active_Desires);
		}
		return result;
	}
	
	public void Updated_Active_Desires() 
	{
		this.Updated_Active_Desires = true;
	}
}