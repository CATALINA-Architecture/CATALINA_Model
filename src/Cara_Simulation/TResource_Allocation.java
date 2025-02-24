package Cara_Simulation;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.concurrent.TimeUnit;


class TIntention_Compare implements Comparator<TIntention> {
    @Override
    public int compare(TIntention Intention0, TIntention Intention1) {

    	double w0 = Intention0.get_Desire().get_Attentional_Goal().get_Saliency();
    	double w1 = Intention1.get_Desire().get_Attentional_Goal().get_Saliency();        

        return (w0 > w1? -1 : (w0 == w1) ? 0 : 1);
    }
}


public class TResource_Allocation {
	
	private TAgent Agent;
	private boolean Updated_Beliefs;
	private boolean Updated_Intentions;
	
	public TResource_Allocation(TAgent agent)
	{
		this.Agent = agent;
		this.Updated_Beliefs = true;
		this.Updated_Intentions = true;
		
	}
	
	public TAgent get_Agent() {
		return Agent;
	}
	
	public boolean Plan_Advanc_Eval()
	{
		
		boolean result = true;
		try 
		{
			this.get_Agent().Get_GW().Print_Data(0, 0);
			ArrayList<TIntention> Intentions = this.Agent.Get_GW().Get_Intentions();
			if(Intentions.size() > 0)
			{
				TIntention Intention = Intentions.getFirst();
				TDesire Desire = Intention.get_Desire();
				Game.Print("Agent acts an action for the intention with Functional Name: "+
						Desire.get_Attentional_Goal().get_Name());
				
				TOption Selected_Option = Desire.get_Option_List().get(Intention.get_Seleted_Option_Id());
				
				int Current_Action_Id = Selected_Option.Get_Action_To_Do_ID();
				
				ArrayList<TAction> Actions = new ArrayList<TAction>();
				Actions.addAll(Selected_Option.get_Plan_Actions());
				if(Actions.size()>0)
				{
	
					TAction Action = Actions.get(Current_Action_Id);
					
					TPredicate Precondition =  Action.Get_Precondition();
					TPosition_Train_Coords Precondition_Position = 
										(TPosition_Train_Coords) Precondition.get_Object_Complement();
	
					TBelief_Base Current_Route;
					TBelief_Base Current_Step;
					TBelief_Base Current_Station;
					
					ArrayList<TBelief_Base> Unhibited_Beliefs;
					//I update Current_Route Beliefs
					Unhibited_Beliefs = this.Agent.Get_GW().Get_Beliefs_From_Type_Belief(TType_Beliefs.Belief_Current_Route);
					Current_Route = Unhibited_Beliefs.getFirst();
	
					//I update Current_Step Beliefs
					Unhibited_Beliefs = this.Agent.Get_GW().Get_Beliefs_From_Type_Belief(TType_Beliefs.Belief_Current_Step);
					Current_Step = Unhibited_Beliefs.getFirst();
	
					//I update Current_Stationv Beliefs
					Unhibited_Beliefs = this.Agent.Get_GW().Get_Beliefs_From_Type_Belief(TType_Beliefs.Belief_Current_Station);
					Current_Station = Unhibited_Beliefs.getFirst();
					Game.Print("Agent checks if preconditions for the actions are correct:");
					Game.Print("Current Station is: "+Current_Station.Predicate.get_Object_Complement());
					Game.Print("Current Route is: "+Current_Route.Predicate.get_Object_Complement());
					Game.Print("Current Step is: "+Current_Step.Predicate.get_Object_Complement());
					
					Game.Print("Precondition Station is: "+Precondition_Position.Get_Station());
					Game.Print("Precondition Route is: "+Precondition_Position.Get_Route());
					Game.Print("Precondition Stepis: "+Precondition_Position.Get_Step());
					
					if((int)Current_Route.Predicate.get_Object_Complement() == (int)Precondition_Position.Get_Route() &&
							(int)Current_Step.Predicate.get_Object_Complement() == (int)Precondition_Position.Get_Step() &&
						Current_Station.Predicate.get_Object_Complement() == Precondition_Position.Get_Station())
					{
						Game.Print("Yes, Precondition of the action are correct!");
						Game.Print("I execute the action");
						this.Plan_Exec(Action);
					}
					else
					{
						Game.Print("No, Precondition of the action aren't correct!");
						Game.Print("I cannot to execute the action");
					}
				}
				else
				{
					Game.Print("I haven't action for this Intention. Related Functional Name: "+
							Desire.get_Attentional_Goal().get_Name());
				}
			}
			this.get_Agent().Get_GW().Print_Data(1, 0);
		}
		catch (Exception e) {
	      Game.Print("Something went wrongin method: Plan_Advanc_Eval.");
	      Game.Print("Message Error: "+e.getMessage());
	      Game.PrintLn();
	      e.printStackTrace();
	      result = false;
	    }
	    return result;
	}
	
	private boolean Plan_Exec(TAction Action)
	{
		boolean result = true;
		try 
		{
			this.get_Agent().Get_GW().Print_Data(0, 0);
			Game.Print("Agent executes an action:");
			Game.Print("Invoked Function for the Action: "+Action.Get_Action_Name());
			TTriple_Object Request = new TTriple_Object();
			//FUNCTION TO DO
			Request.Set_Object_First(Action.Get_Action_Name()); 
			//PostCondition Train Coords
			Request.Set_Object_Second(Action.Get_Params().getFirst()); 
			this.Agent.Get_TCS().Execute_Player_Action(Request, this.Agent);
			this.get_Agent().Get_GW().Print_Data(1, 0);
		}
		catch (Exception e) {
	      Game.Print("Something went wrongin method: Plan_Advanc_Eval.");
	      Game.Print("Message Error: "+e.getMessage());
	      Game.PrintLn();
	      e.printStackTrace();
	      result = false;
	    }
	    return result;
	}
	
	public void Updated_Beliefs() 
	{
		this.Updated_Beliefs = true;
	}
	
	public void Updated_Intentions() 
	{
		this.Updated_Intentions = true;
	}

}
