package Cara_Simulation;

import java.util.ArrayList;

public class TExecutive_Switching_Function {
	
	private TAgent Agent;
	private Boolean Updated_Belief;
	private Boolean Updated_Goals;
	private Boolean Updated_Desires;
	private Boolean Updated_Stimuli;
	
	public TExecutive_Switching_Function(TAgent agent)
	{
		this.Agent = agent;
		this.Updated_Belief = true;
		//this property is only for ALL FUNCTIONAL GOAL (both inhibited and uninhibited goals)
		this.Updated_Goals = true;
		this.Updated_Desires = true;
		this.Updated_Stimuli = false;
	}
	
	public void Insert_in_List_Update_Contract()
	{
		this.Agent.Get_GW().Insert_for_Update_Contract(TType_Update_Contract.Beliefs, this);
		this.Agent.Get_GW().Insert_for_Update_Contract(TType_Update_Contract.Goals, this);
		this.Agent.Get_GW().Insert_for_Update_Contract(TType_Update_Contract.Desires, this);
		this.Agent.Get_GW().Insert_for_Update_Contract(TType_Update_Contract.Stimuli, this);
	}

	public TAgent get_Agent() {
		return Agent;
	}

	public boolean AM_Endogenous_Module()
	{
		boolean result = true;
		try 
		{
			//Game.Print("************  Func/tion:  AM_Endogenous_Module  *********: TEI_Function");
			//I a Functional Goal is inserted or deleted
			
			
			if (this.Updated_Goals == true)
			{
				this.Agent.Get_GW().Updated_Goals_for_print = true;
				this.Updated_Goals = false;
				Game.Print_Colored_Text("Stop before calling AM_Endogenous_Module method", 7);
				Game.Press_Enter();
				
				
				Game.Print("Now, I check if I have some Goals to promote to Desire.");
				
				TGlobalWorkspace GW = this.Agent.Get_GW();
				ArrayList<TAttentional_Goal> Goals_To_Promote = new ArrayList<TAttentional_Goal>();
		
//				this.Agent.Get_GW().Print_Data(0, 0);
				Goals_To_Promote.clear();
		
				//We said that these are only Functional Goals
				ArrayList<TFunctional_Goal> Functional_Goals = GW.Get_Functional_Goals();

				ArrayList<TFunctional_Goal> Inhibited_Functional_Goals = GW.Get_Inhibited_Functional_Goals();
				//A goal is inhibited when its conditions are not met, or when its saliency is
				//lower than the Saliency or Attention Threshold of the Agent(see figure 3 of the journal)
				
				ArrayList<TFunctional_Goal> Uninhibited_Functional_Goals = new ArrayList<TFunctional_Goal>() ;//GW.Get_UnInhibited_Goals();
				Uninhibited_Functional_Goals.addAll(Functional_Goals);
				
				//Compute Functional_Goals - Inhibited_Functional_Goals
				Uninhibited_Functional_Goals.removeAll(Inhibited_Functional_Goals);
				this.Agent.Get_GW().Print_Data(1, 0);
				
				ArrayList<TDesire> Desires = GW.Get_Desires();
				
				// for all d in current_Desires
				for (TDesire Desire: Desires) 
				{
					if (Uninhibited_Functional_Goals.contains(Desire.get_Attentional_Goal()) )
					{
						if (Desire.get_Attentional_Goal().get_Saliency() < this.Agent.Get_E_Inhibition_function().get_Saliency_Threshold())
						{
							Game.Print("I Delete a Desire. Desire name: "+Desire.Get_Name() + " - related Functional Goal: "+Desire.get_Attentional_Goal().get_Name());
							Game.Print("Desire Saliency is: "+Desire.get_Attentional_Goal().get_Saliency()+ " - while Saliency Threshold is: "+this.Agent.Get_E_Inhibition_function().get_Saliency_Threshold());
							GW.Delete_Desire(Desire);
						}
					}
					else
					{
						if (Desire.get_Attentional_Goal().get_Saliency() < this.Agent.Get_E_Inhibition_function().get_Attention_Threshold())
						{
							Game.Print("I Delete a Desire. Desire name: "+Desire.Get_Name() + " - related Functional Goal: "+Desire.get_Attentional_Goal().get_Name());
							Game.Print("Desire Saliency is: "+Desire.get_Attentional_Goal().get_Saliency()+ " - while Attention Threshold is: "+this.Agent.Get_E_Inhibition_function().get_Attention_Threshold());
							GW.Delete_Desire(Desire);
						}
					}
				}
		
				for (TFunctional_Goal goal : Uninhibited_Functional_Goals) 
				{
					boolean pass=true;
					for(TDesire Desire: Desires)
					{
						if(goal == Desire.get_Attentional_Goal())
						{
							pass = false;
						}
					}
					if(pass)
					{
						if(Verify_Precondition(goal) )
						{
							if (goal.get_Saliency() >= this.Agent.Get_E_Inhibition_function().get_Saliency_Threshold())
							{
								// I promote g as a desire
								Game.Print("The goal -- "+goal.get_Name()+" -- is in Uninhibited Goal and it has a saliency greater than Saliency Threshold: "+goal.get_Saliency()+" > "+this.Agent.Get_E_Inhibition_function().get_Saliency_Threshold());
								Game.Print("I'm promoting the goal to Desire, inside --(Uninhibited Goals)-- section (see Listing 1). Goal Name: "+goal.get_Name());
								Goals_To_Promote.add(goal);
							}
							else
							{
								Game.Print("Not Promoted Goal Name: "+goal.get_Name()+ ", because the Saliency Functional Goal is minor then the my Saliency threshold .");
							}
						}
						else
						{
							Game.Print("I do Not Promote Goal Name: "+goal.get_Name()+ ", because the Functional Goal's precondition is not true.");
						}
					}
					else
					{
						Game.Print("I do Not Promote Goal Name: "+goal.get_Name()+ ", because it is already a Desire");
					}
					Game.PrintLn();
				}
				
				for (TFunctional_Goal goal : Inhibited_Functional_Goals) 
				{
					boolean pass=true;
					for(TDesire Desire: Desires)
					{
						if(goal == Desire.get_Attentional_Goal())
						{
							pass = false;
						}
					}
					if(pass)
					{
						if(Verify_Precondition(goal))
						{
							if (goal.get_Saliency() >= this.Agent.Get_E_Inhibition_function().get_Attention_Threshold())
							{
								Game.Print("The goal -- "+goal.get_Name()+" -- is in Inhibited Goal and it has a saliency greater than Attention Threshold: "+goal.get_Saliency()+" > "+this.Agent.Get_E_Inhibition_function().get_Attention_Threshold());
								Game.Print("I'm promoting the goal to Desire, inside --(Inhibited goals)-- section (see Listing 1). Goal Name: "+goal.get_Name());
								Game.PrintLn();
								Goals_To_Promote.add(goal);
							}
						}
					}
					else
					{
						Game.Print("I do Not Promote Goal Name: "+goal.get_Name()+ ", because it is already a Desire");
						Game.PrintLn();
					}
					Game.PrintLn();
				}
				
				//Now I promote all goal (in Goals_To_Promote) to Desire
				ArrayList<TDesire> New_Desires = new ArrayList<TDesire>();
				Integer Desire_Number = this.Agent.Get_GW().Get_Desire_Number();
				for (TAttentional_Goal Goal: Goals_To_Promote)
				{
					Desire_Number++;
					TDesire Desire = this.Promote_to_Desire(Desire_Number, Goal, null);
					
					// //Any time I create a desire, I have to insert it in Long Memory before to insert it in GW
					// this.Agent.Get_WMM().Add_Desire(Desire);
					//I insert the Desire in GW
					New_Desires.add(Desire);
					Game.Print("I promoted the goal: "+Goal.get_Name()+" to the Desire: "+Desire.Get_Name()+ " with Saliency: "+Goal.get_Saliency());
					Game.PrintLn();
				}
				
				GW.Insert_New_Desires(New_Desires);
				
				this.Agent.Get_GW().Updated_Beliefs_for_print = true;
				
				this.Agent.Get_GW().Print_Data(1, 0);
			}
		}
		catch (Exception e) {
	      Game.Print("Something went wrongin method: Insert_New_Desires.");
	      Game.Print("Message Error: "+e.getMessage());
	      Game.PrintLn();
	      e.printStackTrace();
	      result = false;
	    }
		return result;
	}

	
//	public boolean AM_Exogenous_Module(TSalient_Belief Stimulus)
	public boolean AM_Exogenous_Module()
	{
		boolean result = true;
		try
		{
			
			//the following lines of code are to use the iterator way in the Start Agent method
			if (this.Updated_Stimuli == true)
			{
				this.Agent.Get_GW().Updated_Stimuli_for_print = true;
				this.Updated_Stimuli = false;
			
				TSalient_Belief Stimulus = this.Agent.Get_GW().Get_Temp_Salient_Belief();
				if(Stimulus==null )
				{
					Game.Print("I have NOT a stimulus.");
					this.Agent.Get_GW().Print_Data(1, 0);
					return result;
				}
	//			else if (Stimulus.get_Type_Belief() == TType_Beliefs.Stimulus_Ok_Correct_Movement)
	//			{
	//				return result;
	//			}
				Game.Print_Colored_Text("Stop before calling AM_Exogenous_Module method", 7);
				Game.Press_Enter();
				this.Agent.Get_GW().Print_Data(2, 0);
				
				//the previous lines of code are to use the iterator way in the Start Agent method
				
				Game.Print("I have a stimulus and I check if it has a saliency greater than "+
							"my Saliency/Attention threholds.");
				
				TGlobalWorkspace GW = this.Agent.Get_GW();
		
				TRegion Inhi_regions = this.Agent.Get_GW().Get_Inhibition_Regions();
				
				TSalient_Belief relevant_Stimulus = this.Apply_Filter( Stimulus , Inhi_regions );
				
				if ((Inhi_regions != null) && (relevant_Stimulus != null))
				{
					if ( relevant_Stimulus.Get_Saliency() > this.Agent.Get_E_Inhibition_function().get_Saliency_Threshold() )
					{
						// The Shifting Attention module finds the goal
				    	// corresponding to the relevant_Stimulus ( if it exists )
						Game.Print("This stimulus is greater than Saliency Threshold.");
						Game.Print("I create an Epistemic Goal and I promote it to Desire.");
						TEpistemic_Goal New_Goal = GW.Stimulus_to_Goal(relevant_Stimulus);
						
						Integer Desire_Number = this.Agent.Get_GW().Get_Desire_Number();
						Desire_Number++;
						TDesire Desire = this.Promote_to_Desire(Desire_Number, New_Goal, null);
						ArrayList<TDesire> New_Desires = new ArrayList<TDesire>();
						New_Desires.add(Desire);
						
						this.Agent.Get_GW().Update_Goals();
											
						GW.Insert_New_Desires(New_Desires);
					}
				}
				else
				{
					if (Stimulus.Get_Saliency() > this.Agent.Get_E_Inhibition_function().get_Attention_Threshold()) 
					{
				    	// The Shifting Attention module finds the goal
				    	// corresponding to the relevant_Stimulus ( if it exists )
						
						Game.Print("This stimulus is greater than Attention Threshold.");
						Game.Print("I create an Epistemic Goal and I promote it to Desire.");
				    	TEpistemic_Goal New_Goal = GW.Stimulus_to_Goal(Stimulus);
				    	
				    	Integer Desire_Number = this.Agent.Get_GW().Get_Desire_Number();
						Desire_Number++;
						TDesire Desire = this.Promote_to_Desire(Desire_Number, New_Goal, null);
						ArrayList<TDesire> New_Desires = new ArrayList<TDesire>();
						New_Desires.add(Desire);
				    	
						this.Agent.Get_GW().Update_Goals();
						
				    	GW.Insert_New_Desires(New_Desires);
					}
				}
				this.Agent.Get_GW().Print_Data(1, 1);
			}
			else
			{
				Game.Print("I have NOT a stimulus. I am not entering here ");
				this.Agent.Get_GW().Print_Data(1, 0);
				return result;
			}
		}
		catch (Exception e) {
	      Game.Print("Something went wrongin method: Insert_New_Desires.");
	      Game.Print("Message Error: "+e.getMessage());
	      Game.PrintLn();
	      e.printStackTrace();
	      result = false;
	    }
		return result;
	}
	
	private TSalient_Belief Apply_Filter(TSalient_Belief Stimulus, TRegion Inhi_regions )
	{
		TSalient_Belief Relevant_Stimulus = null;

		//if the inhibition regions do NOT contain the route from which the stimulus comes
		//it means that the route from which the stimulus comes is to be paid attention to
		switch (Stimulus.get_Type_Belief())
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
				/**
				 * Protocol Predicate:
				 * Subject => Route Number (Integer)
				 * Relationship => is_Busy | is_Closed | is_Temporary_Closed
				 *  if Relationship = is_Temporary_Closed
				 * 		Object = => Time amount (Integer)
				 */
				{
					Integer Route_Number = (Integer) Stimulus.get_Predicate().get_Subject();
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
			case TType_Beliefs.Stimulus_Busy_Route:
			case TType_Beliefs.Stimulus_Temporary_Closed_Route:
				/**
				 * Protocol Predicate:
				 * Subject => Route Number (Integer)
				 * Relationship => is_Busy | is_Closed | is_Temporary_Closed
				 *  if Relationship = is_Temporary_Closed
				 * 		Object = => Time amount (Integer)
				 */
				{
					Integer Route_Number = (Integer) Stimulus.get_Predicate().get_Subject();
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
					Integer Route_Number = (Integer) Stimulus.get_Predicate().get_Subject();
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
				Game.Print("(Stimulus.get_Type_Belief(): "+(Stimulus.get_Type_Belief()));
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
	
	private Boolean Verify_Precondition(TFunctional_Goal Goal)
	{	
		Boolean result = false;
		
		TPredicate Predicate = null;
		TBelief_Base Belief = null;
		//TFunctional_Goal This_Goal = null;
		
//		if(Goal instanceof TFunctional_Goal)
//		{
//			Game.Print("this Goal is an instance of TFunctional_Goal");
//			This_Goal = (TFunctional_Goal) Goal;
			Predicate = Goal.get_Trigger_Condition();
			Belief = Goal.get_Final_State();
			if(Predicate != null)
			{
				switch(Belief.get_Type_Belief() )
				{
				case TType_Beliefs.Belief_Destination_Station:
					result = Check_Precondition_Visited_City_for_Belief_Destination_Station(Goal);
					
					break;
				default:
					Game.Print("Verify_Precondition cannot to check the type of Belief of Goal: ");
					Game.Print("Goal Name: "+Goal.get_Name());
					Game.Print("Goal Belief Final State Name: "+Goal.get_Final_State().get_Name());
					Game.Print("Goal Belief Final State Type: "+Goal.get_Final_State().get_Type_Belief());
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
	
	private Boolean Check_Precondition_Visited_City_for_Belief_Destination_Station(TFunctional_Goal Goal)
	{
		Boolean result = false;
		TPredicate Predicate = Goal.get_Trigger_Condition();
		Station Visited_Station_Goal = (Station) Predicate.get_Subject();
//		ArrayList<TBelief_Base> Beliefs = this.Agent.Get_WMM().Get_Beliefs();
		
		ArrayList<TBelief_Base> Beliefs = this.Agent.Get_GW().Get_Beliefs();
		this.Agent.Get_GW().Get_UnInhibited_Beliefs();
		for (TBelief_Base Belief: Beliefs)
		{
			if(Belief.get_Type_Belief() == TType_Beliefs.Belief_Visited_Station)
			{
				Station Visited_Station_Belief = (Station) Belief.get_Predicate().get_Subject();
				
				if(		Visited_Station_Belief == Visited_Station_Goal
					&& 	Belief.get_Predicate().get_Object_Complement() == TType_Object_Complement.Me
					&&	Belief.is_Truth() == true)
				{
					result = true;
					break;
				}
			}
		}
		
		return result;
	}
	
	private TDesire Promote_to_Desire(int Desire_Number, TAttentional_Goal attentional_Goal, ArrayList<TOption> option_List )
	{
		String Desire_Name= "D" + Desire_Number;
		TDesire Desire = new TDesire(Desire_Name, attentional_Goal, option_List);
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

}
