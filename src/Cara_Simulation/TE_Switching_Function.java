package Cara_Simulation;

import java.util.ArrayList;

public class TE_Switching_Function {
	
	private TAgent Agent;
	private Boolean Update_Belief;
	
	public TE_Switching_Function(TAgent agent)
	{
		this.Agent = agent;
		this.Update_Belief = true;
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
			TGlobalWorkspace GW = this.Agent.get_GW();
			ArrayList<TAttentional_Goal> Goals_To_Promote = new ArrayList<TAttentional_Goal>();
	
			this.Agent.get_GW().Print_Data(0, 0);
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
			
			ArrayList<TDesire> Desires = GW.Get_Desires();
			
			// for all d in current_Desires
			for (TDesire Desire: Desires) 
			{
				if (Uninhibited_Functional_Goals.contains(Desire.get_Attentional_Goal()) )
				{
					Game.Print("Desire.get_Attentional_Goal().get_Saliency() "+Desire.get_Attentional_Goal().get_Saliency());
					if (Desire.get_Attentional_Goal().get_Saliency() < this.Agent.Get_E_Inhibition_function().get_Saliency_Threshold())
					{
						Game.Print("I Delete a Desire");
	
								GW.Delete_Desire(Desire);
								Game.Print(" - - - - - - Saliency: "+Desire.get_Attentional_Goal().get_Saliency()+ " - Saliency_Threshold: "+this.Agent.Get_E_Inhibition_function().get_Saliency_Threshold());
					}
				}
				else
				{
					if (Desire.get_Attentional_Goal().get_Saliency() < this.Agent.Get_E_Inhibition_function().get_Attention_Threshold())
					{
						Game.Print("I Delete a Desire");
						Game.Print(Desires+" - - - - - - Saliency: "+Desire.get_Attentional_Goal().get_Saliency()+ " - Saliency_Threshold: "+this.Agent.Get_E_Inhibition_function().get_Saliency_Threshold());
						GW.Delete_Desire(Desire);
					}
				}
			}
	
			for (TFunctional_Goal goal : Uninhibited_Functional_Goals) 
			{
				if(Verify_Precondition(goal))
				{
					if (goal.get_Saliency() >= this.Agent.Get_E_Inhibition_function().get_Saliency_Threshold())
					{
						// I promote g as a desire
						Game.Print("I'm promoting the goal to Desire, inside --(List_goals - Inhibited_goals)-- section (see Listing 1). Goal Name: "+goal.get_Name());
						Goals_To_Promote.add(goal);
					}
					else
					{
						Game.Print("not Promoted Goal Name: "+goal.get_Name()+ " because the Saliency Functional Goal is minor then the Saliency threshold of the Agent");
					}
				}
				else
				{
					Game.Print("not Promoted Goal Name: "+goal.get_Name()+ " because the precondition Functional Goal is not true");
				}
			}
			
			for (TFunctional_Goal goal : Inhibited_Functional_Goals) 
			{
				if(Verify_Precondition(goal))
				{
					if (goal.get_Saliency() >= this.Agent.Get_E_Inhibition_function().get_Attention_Threshold())
					{
						Game.Print("I'm inserting a goal to promote, inside --Inhibited_Goals-- section");
						Goals_To_Promote.add(goal);
					}
						
				}
			}
			
			this.Agent.get_GW().Print_Data(1, 0);
			
			//Now I promote all goal (in Goals_To_Promote) to Desire
			ArrayList<TDesire> New_Desires = new ArrayList<TDesire>();
			Integer Desire_Number = this.Agent.get_GW().Get_Desire_Number();
			for (TAttentional_Goal Goal: Goals_To_Promote)
			{
				Desire_Number++;
				TDesire Desire = this.Promote_to_Desire(Desire_Number, Goal, null);
				
				// //Any time I create a desire, I have to insert it in Long Memory before to insert it in GW
				// this.Agent.Get_WMM().Add_Desire(Desire);
				//I insert the Desire in GW
				New_Desires.add(Desire);
				Game.Print("I promoted the goal: "+Goal.get_Name()+" to the Desire: "+Desire.Get_Name());
			}
			
			GW.Insert_New_Desires(New_Desires);
			
			System.out.println("************   This is the end of the Endogenous Module");
		}
		catch (Exception e) {
	      Game.Print("Something went wrongin method: Insert_New_Desires.");
	      Game.Print("Message Error: "+e.getMessage());
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
			TSalient_Belief Stimulus = this.Agent.get_GW().Get_Temp_Salient_Belief();
			if(Stimulus==null)
			{
				return result;
			}
			//the previous lines of code are to use the iterator way in the Start Agent method
			
			this.get_Agent().get_GW().Print_Data(0, 0);
			TGlobalWorkspace GW = this.Agent.get_GW();
	
			TRegion Inhi_regions = this.Agent.get_GW().Get_Inhibition_Regions();
			
			TSalient_Belief relevant_Stimulus = this.Apply_Filter( Stimulus , Inhi_regions );
			
			if ((Inhi_regions != null) && (relevant_Stimulus != null))
			{
				if ( relevant_Stimulus.Get_Saliency() > this.Agent.Get_E_Inhibition_function().get_Saliency_Threshold() )
				{
					// The Shifting Attention module finds the goal
			    	// corresponding to the relevant_Stimulus ( if it exists )
					TEpistemic_Goal New_Goal = GW.Stimulus_to_Goal(relevant_Stimulus);  
					
					Integer Desire_Number = this.Agent.get_GW().Get_Desire_Number();
					Desire_Number++;
					TDesire Desire = this.Promote_to_Desire(Desire_Number, New_Goal, null);
					ArrayList<TDesire> New_Desires = new ArrayList<TDesire>();
					New_Desires.add(Desire);
					
					this.get_Agent().get_GW().Print_Data(1, 0);
	//				GW.Insert_new_desire( Desire );
					GW.Insert_New_Desires(New_Desires);
				}
			}
			else
			{
				if (Stimulus.Get_Saliency() > this.Agent.Get_E_Inhibition_function().get_Attention_Threshold()) 
				{
			    	// The Shifting Attention module finds the goal
			    	// corresponding to the relevant_Stimulus ( if it exists )
			    	TEpistemic_Goal New_Goal = GW.Stimulus_to_Goal(Stimulus);
			    	
			    	Integer Desire_Number = this.Agent.get_GW().Get_Desire_Number();
					Desire_Number++;
					TDesire Desire = this.Promote_to_Desire(Desire_Number, New_Goal, null);
					ArrayList<TDesire> New_Desires = new ArrayList<TDesire>();
					New_Desires.add(Desire);
			    	
			    	this.get_Agent().get_GW().Print_Data(1, 0);
	//		    	GW.Insert_new_desire( Desire );
			    	GW.Insert_New_Desires(New_Desires);
				}
			}
		}
		catch (Exception e) {
	      Game.Print("Something went wrongin method: Insert_New_Desires.");
	      Game.Print("Message Error: "+e.getMessage());
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
			case TType_Beliefs.Stimulus_Closed_Route:
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
					Game.Print("Route_Number for Aplly_Filter: "+Route_Number );
					
					//If Route Number NOT is in Inhi_regions: the route is in my path! The agent must be careful!
					if (! Inhi_regions.Integer_Routes.contains(Route_Number))
					{
						Relevant_Stimulus = Stimulus;
						Relevant_Stimulus.Update_Saliency(0.9);
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
			default:
				Game.Print("I cannot to handle Type Beliefs for Stimulus in method: Apply_Filter");
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
		ArrayList<TBelief_Base> Beliefs = this.Agent.Get_WMM().Get_Beliefs();
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
		this.Update_Belief = true;		
	}

}
