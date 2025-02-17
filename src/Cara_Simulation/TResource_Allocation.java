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
	
	public TResource_Allocation(TAgent agent)
	{
		this.Agent = agent;
	}
	
	// TODO to DEVELOPMENT
	/***
	 * In thjis function, Agent acts any actions to pursue its goal
	 */
	
	public void Updated_Intentions(ArrayList<TIntention> Selected_Intentions, ArrayList<TBelief_Base> Beliefs)
	{
		//in according to the type of action the agent to a task
		//In this time, Selected_Intentions has only one Selected Intention at a time
		this.get_Agent().get_GW().Print_Data(0, 0);
//		for (TIntention Intention: Selected_Intentions)
		TIntention Intention = null;
		{
			TAttentional_Goal Goal = Intention.get_Desire().get_Attentional_Goal();
			if (Goal instanceof TFunctional_Goal)
			{
				Game.Print("The Attentional goal in Selected Intention is a FUNCTIONAL GOAL");
				TFunctional_Goal Functional_Goal = (TFunctional_Goal) Goal;
				TBelief_Base Belief = Functional_Goal.get_Final_State();
				switch(Belief.get_Type_Belief()) 
				{
				case TType_Beliefs.Belief_Destination_Station: 
					Game.Print("I want to execute Go_To_Destination_Station");
					this.get_Agent().get_GW().Print_Data(1, 0);
					Go_To_Destination_Station(Intention);
//					LocalDateTime start = LocalDateTime.now();
//					Game.Print("I try to wait some seconds: "+start);
//					Game.Sleep_Seconds(1);
//					Duration timeElapsed = Duration.between(start, LocalDateTime.now());
//					Game.Print("I try to wait some seconds: "+timeElapsed);
					
					 
					break;
				
				}
				
			}
			else
			{
				Game.Print("The Attentional goal in Selected Intention is a EPISTEMIC GOAL");
				TEpistemic_Goal Epistemic_Goal = (TEpistemic_Goal) Goal;
				TBelief_Base Belief = Epistemic_Goal.get_Belief();
				switch(Belief.get_Type_Belief()) 
				{
				case TType_Beliefs.Stimulus_Temporary_Closed_Route: 
					Game.Print("I want to execute Go_To_Destination_Station");
					
					Get_Time_for_Temporary_Closed_Route(Intention);
					LocalDateTime start = LocalDateTime.now();
					Game.Print("I try to wait some seconds: "+start);
					Game.Sleep_Seconds(1);
					Duration timeElapsed = Duration.between(start, LocalDateTime.now());
					Game.Print("I try to wait some seconds: "+timeElapsed);
					
					 
					break;
				
				}
				
			}
			
			
			
			
			
		}
		
	}

	public TAgent get_Agent() {
		return Agent;
	}
	
	public void Go_To_Destination_Station(TIntention Intention)
	{
//		Game.Print("************  Function:  Go_To_Destination_Station from Updated_Intentions Function  *********: TResource_Allocation");
		this.get_Agent().get_GW().Print_Data(0, 0);
		TAttentional_Goal Goal = Intention.get_Desire().get_Attentional_Goal();
		TFunctional_Goal Functional_Goal = (TFunctional_Goal) Goal;
		TBelief_Base Belief = Functional_Goal.get_Final_State();
		
		ArrayList<TOption> Option_List = Intention.get_Desire().get_Option_List();
		
		TOption Option = Option_List.get(Intention.get_Seleted_Option_Id());
		ArrayList<TAction> Actions = Option.get_Plan_Actions();
		
		for(TAction Action: Actions)
		{
			String Function_Name_to_Execute = Action.get_Action_Name();
			Integer route = (Integer)Action.get_Params().getFirst();
			Game.Print("Action invoke Function: "+Action.get_Action_Name()+ " for route "+Action.get_Params().getFirst());
			Game.Print("I check for Precondition: Route "+route+" is unclosed)");
			
			///CHECK PRECONDITIONS
			//TBelief_Base Current_Route = this.Agent.get_GW().Get_Belief_From_Type_Belief_and_Subject(TType_Beliefs.Belief_Current_Route, TType_Subject.Me);
			//TBelief_Base Current_Station = this.Agent.get_GW().Get_Beliefs_From_Type_Belief(TType_Beliefs.Belief_Current_Station).getFirst();
			TBelief_Base Current_Step = this.Agent.get_GW().Get_Beliefs_From_Type_Belief(TType_Beliefs.Belief_Current_Step).getFirst();
			Integer step = (int) Current_Step.get_Predicate().get_Object_Complement();
			Game.Print(step);
			TPosition_Train_Coords Position_Train_Coords = new TPosition_Train_Coords(route, step);
			
			//If I get a false value, it means that the Route is RED, or the route has a problem,
			if (!this.Agent.Get_WMM().Get_Status_Route(Position_Train_Coords))
			{
				
				break;
			}
				
			
//			TTriple_Object Request = new TTriple_Object();
//			//First Object is a String for asking something
//			Request.Object_First = "Status Route";
//			//Second Object is the route's integer id
//			Request.Object_Second = route;
//			//Third Object is the route's step id
//			Request.Object_Second = step;
//			TTriple_Object Response = this.Agent.Get_TCS().Response(Request);
//			if (Response.Object_First == )
			
//			if(TCS.res)
			
		};
		Game.Print("Fine Azioni da Go_To_Destination_Station");
		this.get_Agent().get_GW().Print_Data(1, 0);
		Game.End_Game();
		
		
		
		
		TAction Action = Actions.getFirst();
		
		
//		for (TAction Action: Actions)
//		{
//			TAction Action = Actions.getFirst();
			String Function_Name_to_Execute = Action.get_Action_Name();
			Game.Print(Action.get_Params().getFirst().getClass());
			Integer route = (Integer)Action.get_Params().getFirst();
			Game.Print("Action invoke Function: "+Action.get_Action_Name()+ " for route "+Action.get_Params().getFirst());	
//		}
		ArrayList<TBelief_Base> Functional_Beliefs = null; 
		if(!this.Agent.get_GW().Get_Map_Attentional_Goal_and_Beliefs().containsKey(Goal))
		{
			Functional_Beliefs = new ArrayList<TBelief_Base>();
			this.Agent.get_GW().Get_Map_Attentional_Goal_and_Beliefs().put(Functional_Goal.get_Name(), 
					Functional_Beliefs);
		}
		Game.Print(Functional_Goal.get_Name());
		Functional_Beliefs = this.Agent.get_GW().Get_Map_Attentional_Goal_and_Beliefs().get(Functional_Goal);
		boolean found = false;
		ArrayList<TDouble_Object> List_Route_Time = null;
		for (TBelief_Base Functional_Belief: Functional_Beliefs)
		{
			//If I found a Belief_Path_Taken_For_Belief Belief 
			if (Functional_Belief.get_Type_Belief() == TType_Beliefs.Belief_Path_Taken_For_Belief)
			{
				found = true;
				
				List_Route_Time = (ArrayList<TDouble_Object>) Functional_Belief.Predicate.get_Object_Complement();
				boolean route_found = false;
				for (TDouble_Object Route_Time: List_Route_Time)
				{
					if(Route_Time.get_Object_First() == route)
						route_found = true;
				}
				//if I don't found the route I insert the route and time
				if (! route_found)
				{
					TDouble_Object Route_Time = new TDouble_Object();
					Route_Time.set_Object_First(route);
					Route_Time.set_Object_Second(LocalDateTime.now());
					List_Route_Time.add(Route_Time);
				}
			}
		}
		//If I don't found a Belief_Path_Taken_For_Belief Belief
		if (! found)
		{
			//I create and insert the Belief_Path_Taken_For_Belief Belief, with Route and Time, to
			//Beliefs linked to the Functional Goal
			TDouble_Object Route_Time = new TDouble_Object();
			Route_Time.set_Object_First(route);
			Route_Time.set_Object_Second(LocalDateTime.now());
			
			List_Route_Time = new ArrayList<TDouble_Object>();
			List_Route_Time.add(Route_Time);
			
			
			TPredicate Functional_Predicate = this.Agent.get_GW().Create_Predicate(Functional_Goal.get_Name(), 
					TType_Relationship.has_traveled, List_Route_Time);
			
			TBelief_Base Functional_Belief = this.Agent.get_GW().Create_Beliefs(Functional_Predicate, true, 
					TType_Subject.Me, LocalDateTime.now(), TType_Beliefs.Belief_Path_Taken_For_Belief);


			Functional_Beliefs.add(Functional_Belief);
			
		}
		
		
		//TBelief_Base Attentional_Belief = this.Agent.get_GW().Get_Map_Attentional_Goal_and_Beliefs().get(Goal).
		
	}
	
	public void Get_Time_for_Temporary_Closed_Route(TIntention Intention)
	{
		Game.Print("************  Function:  Get_Time_for_Temporary_Closed_Route from Updated_Intentions Function  *********: TResource_Allocation");
		Game.Print_Scenario();
		TAttentional_Goal Goal = Intention.get_Desire().get_Attentional_Goal();
		TEpistemic_Goal Functional_Goal = (TEpistemic_Goal) Goal;
		TBelief_Base Belief = Functional_Goal.get_Belief();
		
		ArrayList<TOption> Option_List = Intention.get_Desire().get_Option_List();
		
		TOption Option = Option_List.get(Intention.get_Seleted_Option_Id());
		ArrayList<TAction> Actions = Option.get_Plan_Actions();
		
		TAction Action = Actions.getFirst();
		
		String Function_Name_to_Execute = Action.get_Action_Name();
		Integer Route_Number = (Integer)Action.get_Params().getFirst();
		Game.Print("Action invoke Function: "+Action.get_Action_Name()+ " for route "+Action.get_Params().getFirst());
		Game.Print("I check for Precondition: Route "+Route_Number+" is temporary closed for time...)");


//		Get_How_much_Time_Route_is_Temporary_Closed
		//If I get a false value, it means that the Route is RED, or the route has a problem,
		if (!this.Agent.Get_WMM().Get_Time_for_Temporary_Closed_Route(Route_Number))
		{
			
			
		}
		Game.End_Game();
	}
	
	public boolean Plan_Advanc_Eval()
	{
		
		boolean result = true;
		try 
		{
			this.get_Agent().get_GW().Print_Data(0, 0);
			ArrayList<TIntention> Intentions = this.Agent.get_GW().Get_Intentions();
			TIntention Intention = Intentions.getFirst();
			TDesire Desire = Intention.get_Desire();

			TOption Selected_Option = Desire.get_Option_List().get(Intention.get_Seleted_Option_Id());
			
			int Current_Action_Id = Selected_Option.Get_Action_To_Do_ID();
			
			ArrayList<TAction> Actions = new ArrayList<TAction>();
			Actions.addAll(Selected_Option.get_Plan_Actions());
			if(Actions.size()>0)
			{

				TAction Action = Actions.get(Current_Action_Id);
				
				TPredicate Precondition =  Action.get_Precondition();
				TPosition_Train_Coords Precondition_Position = 
									(TPosition_Train_Coords) Precondition.get_Object_Complement();

				TBelief_Base Current_Route;
				TBelief_Base Current_Step;
				TBelief_Base Current_Station;
				
				ArrayList<TBelief_Base> Unhibited_Beliefs;
				//I update Current_Route Beliefs
				Unhibited_Beliefs = this.Agent.get_GW().Get_Beliefs_From_Type_Belief(TType_Beliefs.Belief_Current_Route);
				Current_Route = Unhibited_Beliefs.getFirst();

				//I update Current_Step Beliefs
				Unhibited_Beliefs = this.Agent.get_GW().Get_Beliefs_From_Type_Belief(TType_Beliefs.Belief_Current_Step);
				Current_Step = Unhibited_Beliefs.getFirst();

				//I update Current_Stationv Beliefs
				Unhibited_Beliefs = this.Agent.get_GW().Get_Beliefs_From_Type_Belief(TType_Beliefs.Belief_Current_Station);
				Current_Station = Unhibited_Beliefs.getFirst();
				if((int)Current_Route.Predicate.get_Object_Complement() == (int)Precondition_Position.Get_Route() &&
						(int)Current_Step.Predicate.get_Object_Complement() == (int)Precondition_Position.Get_Step() &&
					Current_Station.Predicate.get_Object_Complement() == Precondition_Position.Get_Station())
				{
					Game.Print("I execute the action");
					this.Plan_Exec(Action);
				}
				else
				{
					Game.Print(Current_Route.Predicate.get_Object_Complement());
					Game.Print(Precondition_Position.Get_Route());
					Game.Print(Current_Step.Predicate.get_Object_Complement());
					Game.Print(Precondition_Position.Get_Step());
					Game.Print(Current_Station.Predicate.get_Object_Complement());
					Game.Print(Precondition_Position.Get_Station());
				}
			}
			this.get_Agent().get_GW().Print_Data(1, 0);
			
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
			this.get_Agent().get_GW().Print_Data(0, 0);
			Game.Print("I execute an action");
			TTriple_Object Request = new TTriple_Object();
			//FUNCTION TO DO
			Request.Object_First = Action.get_Action_Name();
			//PostCondition Train Coords
			Request.Object_Second = Action.get_Params().getFirst();
			this.Agent.Get_TCS().Execute_Player_Action(Request, this.Agent);
			this.get_Agent().get_GW().Print_Data(1, 0);
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
	

}
