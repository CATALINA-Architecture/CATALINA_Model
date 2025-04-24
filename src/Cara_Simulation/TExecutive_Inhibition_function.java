package Cara_Simulation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

public class TExecutive_Inhibition_function {
	
	private TAgent Agent;
	private Double Attention_Threshold;
	private Double Saliency_Threshold;
	private TRegion Region;
	private boolean Updated_Beliefs;
	private boolean Updated_Intentions;
	
	private Double Default_Saliency_Threshold = 0.3;
	private Double Default_Attention_Threshold = Default_Saliency_Threshold;
	
	
	public TExecutive_Inhibition_function(TAgent agent)
	{
		this.Agent = agent;
		this.Saliency_Threshold =Default_Saliency_Threshold;
		this.Attention_Threshold = this.Saliency_Threshold;
		this.Region = new TRegion();
		this.Updated_Beliefs = true;
		this.Updated_Intentions = true;
		
	}
	
	public void Insert_in_List_Update_Contract()
	{
		this.Agent.Get_GW().Insert_for_Update_Contract(TType_Update_Contract.Beliefs, this);
		this.Agent.Get_GW().Insert_for_Update_Contract(TType_Update_Contract.Intentions, this);
	}
	
	/***
	 * TODO DEVELOPMENT
	 */
	public void Updated_Intentions_old()
	{
//		Game.Print("************  Function:  Update_Intentions from Update_Intentions Function  *********: TShifting_Attention");
		Game.Print("Agent start to focus its attention");
		this.Agent.Get_GW().Print_Data(0, 0);
		this.Focus_Attention();
		this.Agent.Get_GW().Print_Data(1, 0);
		Game.Print("Agent focused its attention");
	}
	
	public void Updated_Intentions()
	{
		this.Updated_Intentions = true;
	}
	
	
	
	/***
	 * DEVELOPED! Completed!
	 */
	public boolean Focus_Attention()
	{
		boolean result = true;
//		try 
//		{
			if(this.Updated_Intentions)
			{
				this.Updated_Intentions = false;
				//For our purpose we pursue only the first Intention
				if(this.Agent.Get_GW().Get_Intentions().size() > 0)
				{
					Game.Print_Colored_Text("Stop before calling Focus_Attention method", 7);
					Game.Press_Enter();
					
					this.Agent.Get_GW().Print_Data(2, 0);
				
					ArrayList<TIntention> Intentions = this.Agent.Get_GW().Get_Intentions();
					Collections.sort(Intentions, new TIntention_Compare());
					
					TIntention Intention = Intentions.getFirst();
					Game.Print("Intention Saliency: "+Intention.get_Desire().get_Attentional_Goal().get_Saliency());
					//For our purpose we pursue only the first Intention
					//TIntention Intention = this.Agent.Get_GW().Get_Intentions().getFirst();
					//Game.Print("Functional Name for the Intention selected to pursue: "+Intention.get_Desire().get_Attentional_Goal().get_Name());
					
					this.Saliency_Threshold = Intention.get_Desire().get_Attentional_Goal().get_Saliency();
					this.Agent.Get_GW().Update_Saliency_Threshold(this.Saliency_Threshold);
					
					this.Attention_Threshold = Intention.get_Desire().get_Attentional_Goal().Saliency + 
							( (1-Intention.get_Desire().get_Attentional_Goal().Saliency) /2); //to avoid to exceed 1.00 unit
					this.Agent.Get_GW().Update_Attention_Threshold(this.Attention_Threshold);
					
					///
					/// CREATE INHIBITION REGIONS, INHIBITED GOALS AND BELIEFS-
					///
					Game.Print("I create the Inhibition Regions");
					TRegion Inhibition_Regions = this.Create_Inhibition_Regions(Intentions);
					this.Agent.Get_GW().Update_Inhibition_Regions(Inhibition_Regions);
					
					Game.Print("I create the Inhibited Goals");
					ArrayList<TAttentional_Goal> Inhibited_Goals = this.Create_Inhibited_Goals(Intentions);
					this.Agent.Get_GW().Update_Inhibited_Goals(Inhibited_Goals);
					
					//TODO development update inhibited belief
					Game.Print("I create the Inhibited Beliefs");
					ArrayList<TBelief> Inhibited_Beliefs = this.Create_Inhibited_Beliefs(Intentions); 
					this.Agent.Get_GW().Update_Inhibited_Beliefs(Inhibited_Beliefs);
	
					this.Agent.Get_GW().Print_Data(1, 1);
				}
			}
			else
			{
				
				this.Agent.Get_GW().Print_Data(0, 0);
				
				Game.Print("Agent unfocuses its attention and clear any inhibition regions.");
				this.UnFocus();
				this.Saliency_Threshold = this.Default_Saliency_Threshold;
				this.Agent.Get_GW().Update_Saliency_Threshold(this.Saliency_Threshold);
				
				this.Attention_Threshold = this.Default_Attention_Threshold;
				this.Agent.Get_GW().Update_Attention_Threshold(this.Attention_Threshold);
				
				///TO DO
				///
				
				this.Agent.Get_GW().Print_Data(1, 0);
			}
//		}
//		catch (Exception e) {
//	      Game.Print("Something went wrongin method: Focus_Attention.");
//	      Game.Print("Message Error: "+e.getMessage());
//	      Game.PrintLn();
//	      e.printStackTrace();
//	      result = false;
//	    }
	    return result;
		
	}
	
	public Boolean Agent_Focused()
	{
		return !(this.Saliency_Threshold == this.Attention_Threshold);
	}
	
	public void UnFocus()
	{
		double Un_Focused_Saliency_Threshold = this.Default_Saliency_Threshold;
		double Un_Focused_Attention_Threshold = this.Default_Attention_Threshold;
		
		this.Saliency_Threshold = Un_Focused_Saliency_Threshold;
		this.Attention_Threshold = Un_Focused_Attention_Threshold;
		this.Agent.Get_GW().Update_Saliency_Threshold(Un_Focused_Saliency_Threshold);
		this.Agent.Get_GW().Update_Attention_Threshold(Un_Focused_Attention_Threshold);
		this.Agent.Get_GW().Empty_Inhibition_Region();
		this.Agent.Get_GW().Empty_Inhibited_Goals();
		this.Agent.Get_GW().Empty_Inhibited_Beliefs();
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
			
		
			if (Intention.get_Desire().get_Attentional_Goal() instanceof TFunctional_Goal)
			{
				//
				//THIS IS A FUNCTIONAL GOAL
				// 
				
				//The goal is a Functional Goal, so it means Agent wants to use a specific Route
				//In this case Agent wants to inhibite all belief that aren't that specific Route
				
				TFunctional_Goal Functional_Goal = (TFunctional_Goal) Intention.get_Desire().get_Attentional_Goal();
				// If the functional belief is Belief_Destination_Station, it means that it is a Functional Goal
				// to go in a specific station, so I don't must to inhibit only specific beliefs
				switch(Functional_Goal.get_Final_State().get_Type_Belief())
				{
					case TType_Beliefs.Belief_Destination_Station,
						 TType_Beliefs.Belief_Come_Back_to_City:
						
						//I Remove path of the Intention of the Desire of the Functional Goal 
						//from Inhibited_Regions
						int Index_Option = Intention.get_Seleted_Option_Id();
						TDesire Desire = Intention.get_Desire();
						if(Desire.get_Option_List().size()>0)
						{
							TOption Option = Desire.get_Option_List().get(Index_Option);
							//GO_TO
							//I get integer_Route from the selected Option only if "Action_Name" is
							// "GO_TO"
							ArrayList<Integer> Integer_Routes_to_Remove = new ArrayList<Integer>();
							ArrayList<Route> Routes_to_Remove = new ArrayList<Route>();
							Route The_Route = null;
							int Integer_Route = -1;
							TPosition_Train_Coords Postcondition_Train_Coords = null;
							for(TAction Action: Option.get_Plan_Actions())
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
									
									
									
									if(Integer_Route>=0)
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
									
//									{
//										Integer_Routes_to_Remove.add(Integer_Route);
//										Integer_Routes_to_Remove.add(Specular_Integer_Route);
//										
//										
//										Routes_to_Remove.add(Map.Get_Route(Integer_Route));
//										Routes_to_Remove.add(Map.Get_Route(Specular_Integer_Route));
//										
//										//I Remove all destinations form inhibition region that is in Path to pursue
//										The_Route = Map.Get_Route(Integer_Route);
//										Inhibited_Regions.Destinations.remove(The_Route.Get_Departure());
//										Inhibited_Regions.Destinations.remove(The_Route.Get_Destination());
//									}
									
									break;
								case "Come_Back_to_City":
									
									Postcondition_Train_Coords = (TPosition_Train_Coords) Action.Get_Params().get(0);
									
									Integer_Route = (int) Postcondition_Train_Coords.Get_Route();
									if(Integer_Route>=0)
									{
								
									Integer_Routes_to_Remove.add(Integer_Route);
									
									Routes_to_Remove.add(Map.Get_Route(Integer_Route));
									
									//I Remove all destinations form inhibition region that is in Path to pursue
									The_Route = Map.Get_Route(Integer_Route);
									Inhibited_Regions.Destinations.remove(The_Route.Get_Destination());
									}
									
									break;
								}
//								if(Action.Get_Action_Name() == "GO_TO_Route")
//								{
//									//Game.Print(Action.get_Params().get(0).getClass());
//									TPosition_Train_Coords Postcondition_Train_Coords = (TPosition_Train_Coords) Action.Get_Params().get(0);
//									
//		//							int Integer_Route = (int) Action.get_Params().get(0);
//									int Integer_Route = (int) Postcondition_Train_Coords.Get_Route();
//									
//									
//									int Specular_Integer_Route = 0;
//									if (Integer_Route % 2 == 0)
//									{
//										Specular_Integer_Route = Integer_Route + 1;
//										
//									}
//									else
//									{
//										Specular_Integer_Route = Integer_Route - 1;
//									}
//									
//									Integer_Routes_to_Remove.add(Integer_Route);
//									Integer_Routes_to_Remove.add(Specular_Integer_Route);
//									
//									
//									Routes_to_Remove.add(Map.Get_Route(Integer_Route));
//									Routes_to_Remove.add(Map.Get_Route(Specular_Integer_Route));
//									
//									//I Remove all destinations form inhibition region that is in Path to pursue
//									Route The_Route = Map.Get_Route(Integer_Route);
//									Inhibited_Regions.Destinations.remove(The_Route.Get_Departure());
//									Inhibited_Regions.Destinations.remove(The_Route.Get_Destination());
//								}
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
				//THIS IS AN EPISTEMIC GOAL
				// 
				
				//The goal is a Functional Goal, so it means Agent wants to use a specific Route
				//In this case Agent wants to inhibite all belief that aren't that specific Route
				
				TEpistemic_Goal Epistemic_Goal = (TEpistemic_Goal) Intention.get_Desire().get_Attentional_Goal();
				// If the functional belief is Belief_Destination_Station, it means that it is a Functional Goal
				// to go in a specific station, so I don't must to inhibit only specific beliefs
				Game.Print("I cannot handle TEpistemic_Goal in Create_Inhibition_Regions Function");
				
				int Index_Option = 0;
				TDesire Desire = null;
				TOption Option = null;
				TAction Action = null;
				
				switch(Epistemic_Goal.get_Belief().get_Type_Belief())
				{
					case TType_Beliefs.Stimulus_Temporary_Closed_Route:
						
						//I Remove path of the Intention of the Desire of the Functional Goal 
						//from Inhibited_Regions
						Index_Option = Intention.get_Seleted_Option_Id();
						Desire = Intention.get_Desire();
						Option = Desire.get_Option_List().get(Index_Option);
						//Use_Route
						//I get integer_Route from the selected Option only if "Action_Name" is
						// "Ask_for_Temporary_Closed_Route"
						//We have only ONE Route, the first and only param in the first and only Action
						Action = Option.get_Plan_Actions().getFirst();
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
						Index_Option = Intention.get_Seleted_Option_Id();
						Desire = Intention.get_Desire();
						Option = Desire.get_Option_List().get(Index_Option);
						//Use_Route
						//I get integer_Route from the selected Option only if "Action_Name" is
						// "Ask_for_Temporary_Closed_Route"
						//We have only ONE Route, the first and only param in the first and only Action
						Action = Option.get_Plan_Actions().getFirst();
						//Ask_for_Temporary_Closed_Route
						
						if(Action.Get_Action_Name() == "Ask Danger Type on the road")
						{
//							int Integer_Route = (int) Action.Get_Params().getFirst();
							TTriple_Object Danger_Data =  (TTriple_Object) Action.Get_Params().getFirst();
							
							TPosition_Train_Coords Precondition_Position = (TPosition_Train_Coords) Danger_Data.Get_Object_First();
							TPosition_Train_Coords Postcondition_Position = (TPosition_Train_Coords) Danger_Data.Get_Object_Second();
							
							int Integer_Route = Math.max(Precondition_Position.Get_Route(), Postcondition_Position.Get_Route());
							
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
	
	/***
	 * Untill now, I create only the case in which I want to go in a destination station by Functional Goal
	 * Later, I must to create the cases to handle the cases of Epistemic Goals
	 * @return
	 */
	public ArrayList<TBelief> Create_Inhibition_Regions_old()
	{
		/*
		* as regions you should consider paths. So you should create all
		* paths where the start and destination are different? Or just the destination?
		*/
		
		//ArrayList<TBelief_Base> Inhibited_Beliefs = new ArrayList<TBelief_Base>();
		ArrayList<TBelief> Inhibited_Beliefs = null;
		
		
		TIntention Intention = this.Agent.Get_GW().Get_Intentions().getFirst();
		//TOption Option = Intention.get_Desire().get_Option_List().get(Intention.get_Seleted_Option_Id());
		
		if (Intention.get_Desire().get_Attentional_Goal() instanceof TFunctional_Goal)
		{
			//
			//THIS IS A FUNCTIONAL GOAL
			// 
			
			//The goal is a Functional Goal, so it means Agent wants to use a specific Route
			//In this case Agent wants to inhibite all belief that aren't that specific Route
			
			TFunctional_Goal Functional_Goal = (TFunctional_Goal) Intention.get_Desire().get_Attentional_Goal();
			// If the functional belief is Belief_Destination_Station, it means that it is a Functional Goal
			// to go in a specific station, so I don't must to inhibit only specific beliefs 
			if (Functional_Goal.get_Final_State().get_Type_Belief() == TType_Beliefs.Belief_Destination_Station)

			{
				Inhibited_Beliefs = Create_Inhibition_Regions_From_Destination_Station();
			}
			
		}
		
		return Inhibited_Beliefs;
	}

	public Double get_Attention_Threshold() {
		return Attention_Threshold;
	}


	public void set_Attention_Threshold(Double attention_Threshold) {
		Attention_Threshold = attention_Threshold;
	}


	public Double get_Saliency_Threshold() {
		return Saliency_Threshold;
	}


	public void set_Saliency_Threshold(Double saliency_Threshold) {
		Saliency_Threshold = saliency_Threshold;
	}
	
	private ArrayList<TAttentional_Goal> Create_Inhibited_Goals(ArrayList<TIntention> Intentions)
	{
		/*
		 * quali sarebbero in questi casi??
		 */
		ArrayList<TAttentional_Goal> Inhibited_Goals = new ArrayList<TAttentional_Goal>();
		
//	TIntention Intention = this.EI.get_Agent().get_GW().Get_Selected_Intentions().getFirst();
		//TFunctional_Goal Functional_Goal = (TFunctional_Goal) Intention.get_Desire().get_Attentional_Goal();
		
		Inhibited_Goals.addAll(this.Agent.Get_GW().Get_Goals());
//		
//		for(TIntention Intention: this.Agent.Get_GW().Get_Intentions())
		for(TIntention Intention: Intentions)
		{
			Inhibited_Goals.remove(Intention.get_Desire().get_Attentional_Goal());
		}

		return Inhibited_Goals;
		
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
		cases.add(TType_Beliefs.Belief_Next_Station);
		cases.add(TType_Beliefs.Belief_Next_Route);
		cases.add(TType_Beliefs.Belief_Current_City);
		
		while (i< Inhibited_Beliefs.size())
		{
			TBelief Belief_To_Not_Inhibit = Inhibited_Beliefs.get(i);
			if(cases.contains(Belief_To_Not_Inhibit.get_Type_Belief()))
			if(Belief_To_Not_Inhibit.get_Predicate().get_Subject() == TType_Subject.Me)
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
		
		//HashMap<TAttentional_Goal, ArrayList<TBelief_Base>> Map_Attentional_Goal_and_Beliefs = null;
		HashMap<String, ArrayList<TBelief>> Map_Attentional_Goal_and_Beliefs = null;
//		Map_Attentional_Goal_and_Beliefs = this.EI.get_Agent().get_GW().Get_Map_Attentional_Goal_and_Beliefs();
		Map_Attentional_Goal_and_Beliefs = this.Agent.Get_WMM().Get_Map_Attentional_Goals_and_Beliefs();

		
		//TIntention Intention = this.EI.get_Agent().get_GW().Get_Selected_Intentions().getFirst();
		//TOption Option = Intention.get_Desire().get_Option_List().get(Intention.get_Seleted_Option_Id());
		
//		for (TIntention Intention: this.Agent.Get_GW().Get_Intentions())
		for (TIntention Intention: Intentions)
		{
			String Attentional_Name = Intention.get_Desire().get_Attentional_Goal().get_Name();
			TAttentional_Goal Attentional_Goal =  Intention.get_Desire().get_Attentional_Goal();
			//HashMap<String, ArrayList<TBelief_Base>> Map_Attentional_Goal_and_Beliefs =this.EI.Agent.get_GW().Get_Map_Attentional_Goal_and_Beliefs();
			ArrayList<TBelief> Old_All_related_Beliefs_Of_Goal = Map_Attentional_Goal_and_Beliefs.get(Attentional_Name);
			if(Attentional_Goal instanceof TFunctional_Goal)
			{
				TFunctional_Goal Functional_Goal = (TFunctional_Goal) Attentional_Goal;
				TType_Beliefs Type_Belief = Functional_Goal.get_Final_State().get_Type_Belief();
				switch(Type_Belief)
				{
				case Belief_Destination_Station, Belief_Come_Back_to_City:
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
					for(TBelief Bel: Old_All_related_Beliefs_Of_Goal)
					{
//						Game.Print(Bel.get_Name()+ " - "+Bel.get_Type_Belief());
					}
//					Game.Print("New All_related_Beliefs_Of_Goal: "+Old_All_related_Beliefs_Of_Goal);
					ArrayList<TBelief> New_All_related_Beliefs_Of_Goal = new ArrayList<TBelief>();
					New_All_related_Beliefs_Of_Goal.addAll(Create_List_of_Belief__for_Destination_Station(Intention, Functional_Goal));
					Old_All_related_Beliefs_Of_Goal.clear();
					Old_All_related_Beliefs_Of_Goal.addAll(New_All_related_Beliefs_Of_Goal);
					
					
					break;
				default:
					Game.Print("I cannot handle type Functional Belief in Create_Inhibited_Beliefs: "+Type_Belief);
					Game.End_Game();
				}
				
			}
			else
			{
				TEpistemic_Goal Epistemic_Goal = (TEpistemic_Goal) Attentional_Goal;
				TType_Beliefs Type_Belief = Epistemic_Goal.get_Belief().get_Type_Belief();
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
			
			
			
//			Inhibited_Beliefs.removeAll(Map_Attentional_Goal_and_Beliefs.get(Attentional_Name));
			Inhibited_Beliefs.removeAll(Old_All_related_Beliefs_Of_Goal);
		}
		
//		this.Agent.get_GW().Print_Data(1, 0);
		return Inhibited_Beliefs;
	}
	
	private ArrayList<TBelief> Create_List_of_Belief__for_Destination_Station(TIntention Intention, 
			TFunctional_Goal Functional_Goal)
	{
		ArrayList<TBelief> New_All_related_Beliefs_Of_Goal = new ArrayList<TBelief>();
//		TBelief_Base Belief = null;
		int selected_option_Id = Intention.get_Seleted_Option_Id();
		TDesire Desire = Intention.get_Desire();
		ArrayList<Integer> Routes = new ArrayList<Integer>();
		ArrayList<City> Destinations = new ArrayList<City>();
		
		if(Desire.get_Option_List().size() > 0)
		{
			TOption Selected_Option = Desire.get_Option_List().get(selected_option_Id);
			Routes.addAll(Selected_Option.Path.Routes);
			Destinations.addAll(Selected_Option.Path.Destinations);
		}
		
		
		ArrayList<TBelief> All_Beliefs = this.Agent.Get_GW().Get_Beliefs_from_WMM();
		New_All_related_Beliefs_Of_Goal.add(Functional_Goal.get_Final_State());
		for(TBelief Belief: All_Beliefs)
		{
//			Game.Print(Belief.get_Name()+"  -*********  "+Belief.get_Type_Belief());
			switch(Belief.get_Type_Belief())
			{
			case Belief_Destination_Station, Belief_Come_Back_to_City:
				if(Functional_Goal.get_Final_State_Name() == Belief.get_Name())
				{
					if(!New_All_related_Beliefs_Of_Goal.contains(Belief))
					{
						New_All_related_Beliefs_Of_Goal.add(Belief);
					}
					
				}
				break;
			case Belief_Visited_Station:
				if(Destinations.contains(Belief.Predicate.get_Subject()))
				{
					if(!New_All_related_Beliefs_Of_Goal.contains(Belief))
					{
						New_All_related_Beliefs_Of_Goal.add(Belief);
					}
					
				} 
				break;
			case Belief_Route_Status:
				if(Routes.contains(Belief.Predicate.get_Subject()))
				{
					if(!New_All_related_Beliefs_Of_Goal.contains(Belief))
					{
						New_All_related_Beliefs_Of_Goal.add(Belief);
					}
					
				} 
				break;
			case 	Belief_Current_Time, Belief_Current_City, Belief_Current_Route, Belief_Current_Step,
				 	Belief_Number_Players, Belief_Next_Route, Belief_Next_Station, Belief_Next_Step,
				 	Belief_Prev_Route, Belief_Prev_Station, Belief_Prev_Step, Belief_Map,
				 	Belief_Temporary_Closed_Route, Belief_Busy_Route, Belief_Danger_on_the_Route:
				 	
					if(!New_All_related_Beliefs_Of_Goal.contains(Belief))
					{
						New_All_related_Beliefs_Of_Goal.add(Belief);
					}
//			Game.Print("Aggiunte multiple");
				break;
			case Belief_Path_Taken_For_Belief:
				if(Functional_Goal.get_Name() == (String) Belief.get_Predicate().get_Subject())
				{
					if(!New_All_related_Beliefs_Of_Goal.contains(Belief))
					{
						New_All_related_Beliefs_Of_Goal.add(Belief);
					}
				}
				break;
			//I insert also All Stimulus
			case Stimulus_Ok_Correct_Movement, Stimulus_Too_Close_To_The_Train, 
				Stimulus_Closed_Route, Stimulus_Busy_Route, Stimulus_Temporary_Closed_Route, 
				Stimulus_Crowded_Route, Stimulus_Route_Status, Stimulus_Danger_on_the_Route, 
				Stimulus_Irrelevant:
					if(!New_All_related_Beliefs_Of_Goal.contains(Belief))
					{
						New_All_related_Beliefs_Of_Goal.add(Belief);
					}
				break;	
				
			
			
//			default: 
//				Game.Print("I cannot to handle Type_Belief inside Create_List_of_Belief__for_Destination_Station "
//						+ "in TShifting_Attention: "+ Belief.get_Type_Belief());
				//Game.End_Game();
			}
			
		}
		
		//Game.Print(New_All_related_Beliefs_Of_Goal);
		return New_All_related_Beliefs_Of_Goal;
	}
	
	private ArrayList<TBelief> Create_List_of_Belief__for_Stimulus_Danger_on_the_Route(TEpistemic_Goal Epistemic_Goal)
	{
		ArrayList<TBelief> New_All_related_Beliefs_Of_Goal = new ArrayList<TBelief>();
//		TBelief_Base Belief = null;
		ArrayList<TBelief> All_Beliefs = this.Agent.Get_GW().Get_Beliefs_from_WMM();
		New_All_related_Beliefs_Of_Goal.add(Epistemic_Goal.get_Belief());
		for(TBelief Belief: All_Beliefs)
		{
//			Game.Print(Belief.get_Name()+"  -*********  "+Belief.get_Type_Belief());
			switch(Belief.get_Type_Belief())
			{
			case 	Belief_Current_Time, Belief_Current_City, Belief_Current_Route, Belief_Current_Step,
				 	Belief_Number_Players, Belief_Next_Route, Belief_Next_Station, Belief_Next_Step:
				 	New_All_related_Beliefs_Of_Goal.add(Belief);
//			Game.Print("Aggiunte multiple");
				break;
//			default: 
//				Game.Print("I cannot to handle Type_Belief inside Create_List_of_Belief__for_Destination_Station "
//						+ "in TShifting_Attention: "+ Belief.get_Type_Belief());
				//Game.End_Game();
			}
			
		}
//		Game.Print("Create_List_of_Belief__for_Stimulus_Temporary_Closed_Route");
//		Game.Print(New_All_related_Beliefs_Of_Goal);
//		New_All_related_Beliefs_Of_Goal.forEach(n -> Game.Print(n.get_Type_Belief()));
//		Game.Print("end Create_List_of_Belief__for_Stimulus_Temporary_Closed_Route");
		return New_All_related_Beliefs_Of_Goal;
	}
	
	private ArrayList<TBelief> Create_List_of_Belief__for_Stimulus_Temporary_Closed_Route(TEpistemic_Goal Epistemic_Goal)
	{
		ArrayList<TBelief> New_All_related_Beliefs_Of_Goal = new ArrayList<TBelief>();
//		TBelief_Base Belief = null;
		ArrayList<TBelief> All_Beliefs = this.Agent.Get_GW().Get_Beliefs_from_WMM();
		New_All_related_Beliefs_Of_Goal.add(Epistemic_Goal.get_Belief());
		for(TBelief Belief: All_Beliefs)
		{
//			Game.Print(Belief.get_Name()+"  -*********  "+Belief.get_Type_Belief());
			switch(Belief.get_Type_Belief())
			{
			case 	Belief_Current_Time, Belief_Current_City, Belief_Current_Route, Belief_Current_Step,
				 	Belief_Number_Players, Belief_Next_Route, Belief_Next_Station, Belief_Next_Step:
				 	New_All_related_Beliefs_Of_Goal.add(Belief);
//			Game.Print("Aggiunte multiple");
				break;
//			default: 
//				Game.Print("I cannot to handle Type_Belief inside Create_List_of_Belief__for_Destination_Station "
//						+ "in TShifting_Attention: "+ Belief.get_Type_Belief());
				//Game.End_Game();
			}
			
		}
//		Game.Print("Create_List_of_Belief__for_Stimulus_Temporary_Closed_Route");
//		Game.Print(New_All_related_Beliefs_Of_Goal);
//		New_All_related_Beliefs_Of_Goal.forEach(n -> Game.Print(n.get_Type_Belief()));
//		Game.Print("end Create_List_of_Belief__for_Stimulus_Temporary_Closed_Route");
		return New_All_related_Beliefs_Of_Goal;
	}
	
	public void Updated_Beliefs()
	{
		this.Updated_Beliefs = true;
	}
	
}
