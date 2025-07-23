package Catalina_Simulation;

import java.util.ArrayList;

import javax.swing.JOptionPane;

public class TExecutive_Switching_Function {
	
	private TAgent Agent;
	private Boolean Updated_Belief;
	private Boolean Updated_Goals;
	private Boolean Updated_Desires;
	private Boolean Updated_Stimuli;
	private boolean Updated_Intentions;
	
	public TExecutive_Switching_Function(TAgent agent)
	{
		this.Agent = agent;
		this.Updated_Belief = true;
		//this property is only for ALL FUNCTIONAL GOAL (both inhibited and uninhibited goals)
		this.Updated_Goals = true;
		this.Updated_Desires = true;
		this.Updated_Stimuli = false;
		this.Updated_Intentions = true;
	}
	
	public void Insert_in_List_Update_Contract()
	{
		this.Agent.Get_GW().Insert_for_Update_Contract(TType_Update_Contract.Beliefs, this);
		this.Agent.Get_GW().Insert_for_Update_Contract(TType_Update_Contract.Standing_Desire, this);
		this.Agent.Get_GW().Insert_for_Update_Contract(TType_Update_Contract.Active_Desires, this);
		this.Agent.Get_GW().Insert_for_Update_Contract(TType_Update_Contract.Stimuli, this);
		this.Agent.Get_GW().Insert_for_Update_Contract(TType_Update_Contract.Intentions, this);
	}

	public TAgent get_Agent() {
		return Agent;
	}

	public boolean Desires_Promotion()
	{
		boolean result = true;
//		try 
//		{
			//Game.Print("************  Func/tion:  AM_Endogenous_Module  *********: TEI_Function");
			//I a Functional Goal is inserted or deleted

			Game.Print("1 this.Updated_Goals: "+this.Updated_Goals);
			if (this.Updated_Goals == true)
			{
				
				Game.Print("2 this.Updated_Goals: "+this.Updated_Goals);
				this.Agent.Get_GW().Updated_Standing_Desires_for_print = true;
				this.Updated_Goals = false;
				Game.Print_Colored_Text("Stop before calling AM_Endogenous_Module method", 7);
//				Game.Press_Enter();
				
				Game.Print("Now, I check if I have some Desires to promote to Desire.");
				
				TGlobal_Workspace GW = this.Agent.Get_GW();
				ArrayList<TAttentional_Standing_Desire> Goals_To_Promote = new ArrayList<TAttentional_Standing_Desire>();
		
//				this.Agent.Get_GW().Print_Data(0, 0);
				Goals_To_Promote.clear();
		
				//We said that these are only Functional Goals
				ArrayList<TFunctional_Standing_Desire> Functional_Goals = GW.Get_Functional_Standing_Desires();

				ArrayList<TFunctional_Standing_Desire> Inhibited_Functional_Goals = GW.Get_Inhibited_Functional_Standing_Desires();
				//A goal is inhibited when its conditions are not met, or when its saliency is
				//lower than the Saliency or Attention Threshold of the Agent(see figure 3 of the journal)
				
				ArrayList<TFunctional_Standing_Desire> Uninhibited_Functional_Goals = new ArrayList<TFunctional_Standing_Desire>() ;//GW.Get_UnInhibited_Goals();
				Uninhibited_Functional_Goals.addAll(Functional_Goals);
				
				//Compute Functional_Goals - Inhibited_Functional_Goals
				Uninhibited_Functional_Goals.removeAll(Inhibited_Functional_Goals);
				this.Agent.Get_GW().Print_Data(1, 0);
				
				ArrayList<TActive_Desire> Desires = GW.Get_Active_Desires();
				
				for (TFunctional_Standing_Desire goal : Uninhibited_Functional_Goals) 
				{
					boolean pass=true;
					for(TActive_Desire Desire: Desires)
					{
						if(goal == Desire.Get_Attentional_Standing_Desire())
						{
							pass = false;
						}
					}
					if(pass)
					{
						if(Verify_Precondition(goal) )
						{
							if (goal.Get_Saliency() >= this.Agent.Get_E_Inhibition_Function().Get_Saliency_Threshold())
//							if (goal.get_Saliency() >= this.Agent.Get_GW().Get_Saliency_Threshold())
							{
								if(goal.Get_Trigger_Condition() != null)
								{
									Game.Gui_Map.Show_Message("Information...", "Now, The precondition for Uninhibited "+goal.Get_Name()+ " is true.\n"
											+"I can promote this goal to a Desire!",
											JOptionPane.INFORMATION_MESSAGE);
								}
								// I promote g as a desire
								Game.Print("The desire -- "+goal.Get_Name()+" -- is in Uninhibited Desire and it has a saliency greater than Saliency Threshold: "+goal.Get_Saliency()+" >= "+this.Agent.Get_E_Inhibition_Function().Get_Saliency_Threshold());
								Game.Print("I'm promoting the standing desire to Active Desire, inside --(Uninhibited Desires )-- section (see Listing 1). Desire Name: "+goal.Get_Name());
								Game.Print("I'm in Endogenous module...");
								Game.Print("Goal to promote: "+goal.Get_Name());
								
								Game.Gui_Map.Show_Message("Agent Message", "The Desire -- "+goal.Get_Name()+" -- is in Uninhibited Desire and it has a saliency greater than Saliency Threshold: "+goal.Get_Saliency()+" >= "+this.Agent.Get_E_Inhibition_Function().Get_Saliency_Threshold()+
										"\n I promote the Uninhibited Desire "+goal.Get_Name()+" to desire", null);
//								Game.Gui_Map.Show_Message("Agent Message", "I'm promoting the goal to Desire, inside --(Uninhibited Goals)-- section (see Listing 1). Goal Name: "+goal.get_Name(), null);
								
//								Game.Get_Input("");
								
								Goals_To_Promote.add(goal);
							}
							else
							{
								Game.Print("Not Promoted Desire Name: "+goal.Get_Name()+ ", because the Saliency Functional Desire is minor then the my Saliency threshold .");
							}
						}
						else
						{
							Game.Print("I do Not Promote Desire Name: "+goal.Get_Name()+ ", because the Functional Desire's precondition is not true.");
						}
					}
					else
					{
						Game.Print("I do Not Promote Desire Name: "+goal.Get_Name()+ ", because it is already a Desire");
					}
					Game.PrintLn();
				}
				Game.Print("Inhibited_Functional_Desires: "+Inhibited_Functional_Goals);
				for (TFunctional_Standing_Desire goal : Inhibited_Functional_Goals) 
				{
					Game.Print("Inhibited_Functional_Desires: "+goal.Get_Name());
					boolean pass=true;
					for(TActive_Desire Desire: Desires)
					{
						if(goal == Desire.Get_Attentional_Standing_Desire())
						{
							pass = false;
						}
					}
					if(pass)
					{
						if(Verify_Precondition(goal))
						{
							if (goal.Get_Saliency() >= this.Agent.Get_E_Inhibition_Function().Get_Attention_Threshold())
//							if (goal.get_Saliency() >= this.Agent.Get_GW().Get_Attention_Threshold())
							{
								if(goal.Get_Trigger_Condition() != null)
								{
									Game.Gui_Map.Show_Message("Information...", "Now, The precondition for inhibited "+goal.Get_Name()+ " is true.\n"
											+"I can promote this goal to a Desire!",
											JOptionPane.INFORMATION_MESSAGE);
								}
								Game.Print("The Desire -- "+goal.Get_Name()+" -- is in Inhibited Desire and it has a saliency greater than Attention Threshold: "+goal.Get_Saliency()+" >= "+this.Agent.Get_E_Inhibition_Function().Get_Attention_Threshold());
								Game.Print("I'm promoting the standing Desire to active Desire, inside --(Inhibited Desires )-- section (see Listing 1). Desire Name: "+goal.Get_Name());
								Game.PrintLn();
								Game.Gui_Map.Show_Message("Agent Message", "The Desire -- "+goal.Get_Name()+" -- is in Inhibited Goal and it has a saliency greater than Attention Threshold: "+goal.Get_Saliency()+" >= "+this.Agent.Get_E_Inhibition_Function().Get_Attention_Threshold()+
										"\n I promote the Inhibited Desire "+goal.Get_Name()+" to desire", null);
//								Game.Gui_Map.Show_Message("Agent Message", "I'm promoting the goal to Desire, inside --(Inhibited goals )-- section (see Listing 1). Goal Name: "+goal.get_Name(), null);
								Goals_To_Promote.add(goal);
							}
						}
					}
					else
					{
						Game.Print("I do Not Promote Desire Name: "+goal.Get_Name()+ ", because it is already a Desire");
						Game.PrintLn();
					}
					Game.PrintLn();
				}
				
				//Now I promote all goal (in Goals_To_Promote) to Desire
				ArrayList<TActive_Desire> New_Desires = new ArrayList<TActive_Desire>();
				Integer Desire_Number = this.Agent.Get_GW().Get_Desire_Number();
				for (TAttentional_Standing_Desire Goal: Goals_To_Promote)
				{
					Desire_Number++;
					TActive_Desire Desire = this.Promote_to_Desire(Desire_Number, Goal, null);
					
					// //Any time I create a desire, I have to insert it in Long Memory before to insert it in GW
					// this.Agent.Get_WMM().Add_Desire(Desire);
					//I insert the Desire in GW
					New_Desires.add(Desire);
					Game.Print("I promoted the Desire: "+Goal.Get_Name()+" to the Desire: "+Desire.Get_Name()+ " with Saliency: "+Goal.Get_Saliency());
					Game.PrintLn();
				}
				
				GW.Insert_New_Desires(New_Desires);
				
				this.Agent.Get_GW().Updated_Beliefs_for_print = true;
				
				this.Agent.Get_GW().Print_Data(1, 0);
			}
//		}
//		catch (Exception e) {
//	      Game.Print("Something went wrongin method: Insert_New_Desires.");
//	      Game.Print("Message Error: "+e.getMessage());
//	      Game.PrintLn();
//	      e.printStackTrace();
//	      result = false;
//	    }
		return result;
	}

	
//	public boolean AM_Exogenous_Module(TSalient_Belief Stimulus)
	public boolean Switching_to_stimulus()
	{
		boolean result = true;
//		try
//		{
			
			//the following lines of code are to use the iterator way in the Start Agent method
			if (this.Updated_Stimuli == true)
			{
				this.Agent.Get_GW().Updated_Stimuli_for_print = true;
				this.Updated_Stimuli = false;
			
				TStimulus Stimulus = this.Agent.Get_GW().Get_Temp_Salient_Belief();
//				if(Stimulus==null )
//				{
//					Game.Print("I have NOT a stimulus.");
//					Game.Get_Input("");
//					this.Agent.Get_GW().Print_Data(1, 0);
//					return result;
//				}
	//			else if (Stimulus.get_Type_Belief() == TType_Beliefs.Stimulus_Ok_Correct_Movement)
	//			{
	//				return result;
	//			}
				Game.Print_Colored_Text("Stop before calling AM_Exogenous_Module method", 7);
//				Game.Press_Enter();
				this.Agent.Get_GW().Print_Data(2, 0);
				
				//the previous lines of code are to use the iterator way in the Start Agent method
				
				Game.Print("I have a stimulus and I check if it has a saliency greater than "+
							"my Saliency/Attention threholds.");
				
				

				//22/05/2025
				TGlobal_Workspace GW = this.Agent.Get_GW();
				TEpistemic_Standing_Desire New_Goal = GW.Stimulus_to_Goal(Stimulus);
		    	
		    	Integer Desire_Number = GW.Get_Desire_Number();
				Desire_Number++;
				TActive_Desire Desire = this.Promote_to_Desire(Desire_Number, New_Goal, null);
				ArrayList<TActive_Desire> New_Desires = new ArrayList<TActive_Desire>();
				New_Desires.add(Desire);
		    	
				GW.Update_Goals_for_Broadcast();
				
				GW.Insert_New_Desires(New_Desires);
				
				
				
//				TGlobalWorkspace GW = this.Agent.Get_GW();
//		
//				TRegion Inhi_regions = this.Agent.Get_GW().Get_Inhibition_Regions();
//				
//				TStimulus relevant_Stimulus = this.Apply_Filter( Stimulus , Inhi_regions );
				
//				if ((Inhi_regions != null) && (relevant_Stimulus != null))
//				{
//					//if ( relevant_Stimulus.Get_Saliency() > this.Agent.Get_E_Inhibition_function().get_Saliency_Threshold() )
//					if ( relevant_Stimulus.Get_Saliency() > this.Agent.Get_GW().Get_Saliency_Threshold() )
//					{
//						// The Shifting Attention module finds the goal
//				    	// corresponding to the relevant_Stimulus ( if it exists )
//						Game.Print("This stimulus is greater than Saliency Threshold.");
//						Game.Print("I create an Epistemic Goal and I promote it to Desire.");
//						Game.Print("The Saliency of Epistemic Goal is greater than Saliency Threshold.");
//						Game.Print(relevant_Stimulus.Get_Saliency()+ " >= "+ this.Agent.Get_E_Inhibition_function().get_Saliency_Threshold());
//						Game.Print(relevant_Stimulus.Get_Saliency()+ " >= "+ this.Agent.Get_GW().Get_Saliency_Threshold());
//						Game.Print("relevant_Stimulus.get_Type_Belief(): "+relevant_Stimulus.get_Type_Belief());
////						Game.Get_Input("Press Enter");
//						
//						TEpistemic_Goal New_Goal = GW.Stimulus_to_Goal(relevant_Stimulus);
//						
//						Integer Desire_Number = this.Agent.Get_GW().Get_Desire_Number();
//						Desire_Number++;
//						TDesire Desire = this.Promote_to_Desire(Desire_Number, New_Goal, null);
//						ArrayList<TDesire> New_Desires = new ArrayList<TDesire>();
//						New_Desires.add(Desire);
//						
////						this.Agent.Get_GW().Update_Goals_for_Broadcast();
////						this.Agent.Get_GW().Update_Goals_for_Broadcast();
//						
//											
//						GW.Insert_New_Desires(New_Desires);
//					}
//				}
//				else
//				{
//					//if (Stimulus.Get_Saliency() > this.Agent.Get_E_Inhibition_function().get_Attention_Threshold())
//					if (Stimulus.Get_Saliency() > this.Agent.Get_GW().Get_Attention_Threshold())
//					{
//				    	// The Shifting Attention module finds the goal
//				    	// corresponding to the relevant_Stimulus ( if it exists )
//						
//						Game.Print("This stimulus is greater than Attention Threshold.");
//						Game.Print("I create an Epistemic Goal and I promote it to Desire.");
//						Game.Print("The Saliency of Epistemic Goal is greater than Attention Threshold.");
//						Game.Print(relevant_Stimulus.Get_Saliency()+ " >= "+ this.Agent.Get_E_Inhibition_function().get_Attention_Threshold());
//						Game.Print(relevant_Stimulus.Get_Saliency()+ " >= "+ this.Agent.Get_GW().Get_Attention_Threshold());
//				    	TEpistemic_Goal New_Goal = GW.Stimulus_to_Goal(Stimulus);
//				    	
//				    	Integer Desire_Number = this.Agent.Get_GW().Get_Desire_Number();
//						Desire_Number++;
//						TDesire Desire = this.Promote_to_Desire(Desire_Number, New_Goal, null);
//						ArrayList<TDesire> New_Desires = new ArrayList<TDesire>();
//						New_Desires.add(Desire);
//				    	
//						this.Agent.Get_GW().Update_Goals_for_Broadcast();
//						
//				    	GW.Insert_New_Desires(New_Desires);
//					}
//				}
				this.Agent.Get_GW().Print_Data(1, 1);
			}
			else
			{
				Game.Print("I have NOT a stimulus. I am not entering here ");
				this.Agent.Get_GW().Print_Data(1, 0);
				return result;
			}
//		}
//		catch (Exception e) {
//	      Game.Print("Something went wrongin method: Insert_New_Desires.");
//	      Game.Print("Message Error: "+e.getMessage());
//	      Game.PrintLn();
//	      e.printStackTrace();
//	      result = false;
//	    }
		return result;
	}
	
	private TStimulus Apply_Filter(TStimulus Stimulus, TRegion Inhi_regions )
	{
		TStimulus Relevant_Stimulus = null;

		//if the inhibition regions do NOT contain the route from which the stimulus comes
		//it means that the route from which the stimulus comes is to be paid attention to
		switch (Stimulus.Get_Type_Belief())
		{
			case Stimulus_Ok_Correct_Movement:
				/**
				 * In this case, the prev movement of the Agent was OK
				 * So I only create a irrilevant sitmulus and I set its Saliency to a lower
				 * Saliency value
				 * This stimulus is not handled, beacusa it is only to update the correct
				 * position of the agent in the mind of the agent. This updating is handled
				 * by the WMM
				 */
				Relevant_Stimulus = Stimulus;
				Relevant_Stimulus.Update_Saliency(0.1);
			break;
			
			case TType_Beliefs.Stimulus_Closed_Route:
			
				break;
			case TType_Beliefs.Stimulus_Busy_Route:
				break;
			case TType_Beliefs.Stimulus_Irrelevant:
				break;
			case TType_Beliefs.Stimulus_Temporary_Closed_Route:
				/**
				 * Protocol Predicate:
				 * Subject => Route Number (Integer)
				 * Relationship => is_Busy | is_Closed | is_Temporary_Closed
				 *  if Relationship = is_Temporary_Closed
				 * 		Object = => Time amount (Integer)
				 */
				{
					Integer Route_Number = (Integer) Stimulus.Get_Predicate().Get_Subject();
//					Game.Print("Route_Number for Apply_Filter: "+Route_Number );
					
					//If Route Number NOT is in Inhi_regions: the route is in my path! The agent must be careful!
					if (! Inhi_regions.Integer_Routes.contains(Route_Number))
					{
						Relevant_Stimulus = Stimulus;
						Relevant_Stimulus.Update_Saliency(0.9);
					}
					else
					{
						Relevant_Stimulus = Stimulus;
						Relevant_Stimulus.Update_Saliency(0.2);
					}
				};
				break;
			case TType_Beliefs.Stimulus_Danger_on_the_Route:
				/**
				 * Protocol Predicate:
				 * Subject => Route Number (Integer)
				 * Relationship => has_a_Danger 
				 * Object = => TType_Danger
				 */
				{
					
					TTriple_Object Perception = (TTriple_Object) Stimulus.Get_Predicate().Get_Subject();
					
					TPosition_Train_Coords Precondition_Position = (TPosition_Train_Coords) Perception.Get_Object_First();
					TPosition_Train_Coords Postcondition_Position = (TPosition_Train_Coords) Perception.Get_Object_Second();
					
//					Integer Route_Number = (Integer) Stimulus.get_Predicate().get_Subject();
					Integer Route_Number = Math.max(Precondition_Position.Get_Route(), Postcondition_Position.Get_Route());
					
					if (! Inhi_regions.Integer_Routes.contains(Route_Number))
					{
						Relevant_Stimulus = Stimulus;
						Relevant_Stimulus.Update_Saliency(0.9);
					}
					else
					{
						Relevant_Stimulus = Stimulus;
						Relevant_Stimulus.Update_Saliency(0.2);
					}
				};
				break;
			case TType_Beliefs.Stimulus_Too_Close_To_The_Train:
				/**
				 * Protocol Predicate:
				 * Subject => Player Number
				 * Relationship => is_Too_Close_To_The_Train
				 * Object => Coords := (Route, Step)
				 */
				break;
			case TType_Beliefs.Stimulus_Route_Status:
				/**
				 * Protocol Predicate:
				 * Subject => Route Number (Integer)
				 * Relationship => is 
				 * Object = => TType_Route_Status
				 */
				{
					Integer Route_Number = (Integer) Stimulus.Get_Predicate().Get_Subject();
//					Game.Print("Route_Number for Apply_Filter: "+Route_Number );
					
					//If Route Number NOT is in Inhi_regions: the route is in my path! The agent must be careful!
					if (! Inhi_regions.Integer_Routes.contains(Route_Number))
					{
						Relevant_Stimulus = Stimulus;
//						Relevant_Stimulus.Update_Saliency(0.9);
					}
				}
				break;
			default:
				Game.Print("I cannot to handle Type Beliefs for Stimulus in method: Apply_Filter");
				Game.Print("(Stimulus.get_Type_Belief(): "+(Stimulus.Get_Type_Belief()));
				Game.End_Game();
		}

		return Relevant_Stimulus;
	}
	

	
//	protected TAttentional_Goal Create_Epistemic_Goal(Belief Stimulus, double Saliency,
//			double Reward, double Saliency)
//	{
//		//creare un Epistemic goal specifico per ogni tipo di stimolo:
//		//-Goal epistemico per investigare l'opponent strategy
//		//Func_Epis_Goal Goal = new Func_Epis_Goal();
//		
//		TEpistemic_Goal Goal = new TEpistemic_Goal(Saliency,  null, null, null, null)
//		Goal.Tipo_Goal = TTipi_Goals.TTG_Epistemic_Goal;
//		Goal.Destination = Stimulus.get_Prossima_Citta();
//		Goal.Type_Epistemic_Goal = Stimulus.getType_Epistemic_Goal();
//		Goal.Saliency = Saliency;
//		return Goal;
//	}
	
	private Boolean Verify_Precondition(TFunctional_Standing_Desire Goal)
	{	
		Boolean result = false;
		
		TPredicate Predicate = null;
		TBelief Belief = null;
		//TFunctional_Goal This_Goal = null;
		
//		if(Goal instanceof TFunctional_Goal)
//		{
//			Game.Print("this Goal is an instance of TFunctional_Goal");
//			This_Goal = (TFunctional_Goal) Goal;
			Predicate = Goal.Get_Trigger_Condition();
			Belief = Goal.Get_Final_State();
			if(Predicate != null)
			{
				switch(Belief.Get_Type_Belief() )
				{
				case TType_Beliefs.Belief_Destination_City:
					result = Check_Precondition_Visited_City_for_Belief_Destination_Station(Goal);
					
					break;
				default:
					Game.Print("Verify_Precondition cannot to check the type of Belief of Desire: ");
					Game.Print("Desire Name: "+Goal.Get_Name());
					Game.Print("Desire Belief Final State Name: "+Goal.Get_Final_State().Get_Name());
					Game.Print("Desire Belief Final State Type: "+Goal.Get_Final_State().Get_Type_Belief());
					Game.End_Game();
					
				}
			}
			//If the Functional Goal hasn't a Trigger_Condition, it means that it can be
			//selected any time.
			else
			{
				result = true;
			}
//		}
			return result;
	}
	
	private Boolean Check_Precondition_Visited_City_for_Belief_Destination_Station(TFunctional_Standing_Desire Goal)
	{
		Boolean result = false;
		TPredicate Predicate = Goal.Get_Trigger_Condition();
		City Visited_Station_Goal = (City) Predicate.Get_Subject();
//		ArrayList<TBelief_Base> Beliefs = this.Agent.Get_WMM().Get_Beliefs();
		
		ArrayList<TBelief> Beliefs = this.Agent.Get_GW().Get_Beliefs();
		this.Agent.Get_GW().Get_UnInhibited_Beliefs();
		for (TBelief Belief: Beliefs)
		{
			if(Belief.Get_Type_Belief() == TType_Beliefs.Belief_Visited_City)
			{
				City Visited_Station_Belief = (City) Belief.Get_Predicate().Get_Subject();
				
				if(		Visited_Station_Belief == Visited_Station_Goal
					&& 	Belief.Get_Predicate().Get_Object_Complement() == TType_Object_Complement.Me
					&&	Belief.Is_Truth() == true)
				{
					result = true;
					break;
				}
			}
		}
		
		return result;
	}
	
	private TActive_Desire Promote_to_Desire(int Desire_Number, TAttentional_Standing_Desire attentional_Goal, ArrayList<TOption> option_List )
	{
		String Desire_Name= "D" + Desire_Number;
		TActive_Desire Desire = new TActive_Desire(Desire_Name, attentional_Goal, option_List);
		return Desire;
	}
	
	public void Updated_Beliefs()
	{
		this.Updated_Belief = true;		
	}
	
	public void Updated_Stimuli()
	{
		this.Updated_Stimuli = true;		
	}
	
	public void Update_Goals()
	{
		this.Updated_Goals = true;
	}
	
	public void Update_Desires()
	{
		this.Updated_Desires = true;
	}
	
	public void Updated_Intentions()
	{
		this.Updated_Intentions = true;
	}
	
	public boolean UnFocus_Agent()
	{
		boolean result = true;
		try
		{
			if(this.Updated_Intentions)
			{
				//this.Updated_Intentions = false;
				//For our purpose we pursue only the first Intention
				if(this.Agent.Get_GW().Get_Intentions().size() == 0)
				{
					this.Updated_Intentions = false;
					this.Agent.Get_GW().Print_Data(0, 0);
					Game.Print_Colored_Text("Stop before calling UnFocus_Agent method", 7);
//					Game.Press_Enter();
					Game.Gui_Map.Show_Message("Agent Message", "Now, I Unfocus my attention", null);
					
					this.Agent.Get_GW().Print_Data(2, 0);
					
					Game.Print("Agent unfocuses its attention and clear any inhibition regions.");
					
					double Un_Focused_Saliency_Threshold = this.Agent.Get_GW().Get_Default_Saliency_Thresholds();
					double Un_Focused_Attention_Threshold = this.Agent.Get_GW().Get_Default_Attention_Thresholds();
					
					this.Agent.Get_E_Inhibition_Function().Set_Saliency_Threshold(Un_Focused_Saliency_Threshold);
					this.Agent.Get_E_Inhibition_Function().Set_Attention_Threshold(Un_Focused_Attention_Threshold);
					
					this.Agent.Get_GW().Update_Saliency_Threshold(Un_Focused_Saliency_Threshold);
					this.Agent.Get_GW().Update_Attention_Threshold(Un_Focused_Attention_Threshold);
					this.Agent.Get_GW().Empty_Inhibition_Region();
					this.Agent.Get_GW().Empty_Inhibited_Goals();
					this.Agent.Get_GW().Empty_Inhibited_Beliefs();
					
					this.Agent.Get_GW().Changed_Goals();
					
					this.Agent.Get_GW().Print_Data(1, 1);
				}
			}
		}
		catch (Exception e) {
	      Game.Print("Something went wrongin method: Focus_Attention.");
	      Game.Print("Message Error: "+e.getMessage());
	      Game.PrintLn();
	      e.printStackTrace();
	      result = false;
	    }
	
		return result;
	}
}