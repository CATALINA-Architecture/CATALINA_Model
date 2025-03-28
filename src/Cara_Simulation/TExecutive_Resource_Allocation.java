package Cara_Simulation;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;


class TIntention_Compare implements Comparator<TIntention> {
    @Override
    public int compare(TIntention Intention0, TIntention Intention1) {

    	double w0 = Intention0.get_Desire().get_Attentional_Goal().get_Saliency();
    	double w1 = Intention1.get_Desire().get_Attentional_Goal().get_Saliency();        

        return (w0 > w1? -1 : (w0 == w1) ? 0 : 1);
    }
}


public class TExecutive_Resource_Allocation {
	
	private TAgent Agent;
	private boolean Updated_Beliefs;
	private boolean Updated_Intentions;
	
	public TExecutive_Resource_Allocation(TAgent agent)
	{
		this.Agent = agent;
		this.Updated_Beliefs = true;
		this.Updated_Intentions = true;
	}
	
	public void Insert_in_List_Update_Contract()
	{
		this.Agent.Get_GW().Insert_for_Update_Contract(TType_Update_Contract.Beliefs, this);
		this.Agent.Get_GW().Insert_for_Update_Contract(TType_Update_Contract.Intentions, this);
	}
	
	public TAgent get_Agent() {
		return Agent;
	}
	
	public boolean Plan_Advanc_Eval()
	{
		
		boolean result = true;
		try 
		{
			this.get_Agent().Get_GW().Print_Data(2, 0);
			ArrayList<TIntention> Intentions = this.Agent.Get_GW().Get_Intentions();
			if(Intentions.size() > 0)
			{
				Game.Print_Colored_Text("Stop before calling Plan_Advanc_Eval method", 7);
				Game.Press_Enter();
				Collections.sort(Intentions, new TIntention_Compare());
				
				TIntention Intention = Intentions.getFirst();
//				Game.Print("The saliency of the first intention is: "+Intention.get_Desire().get_Attentional_Goal().get_Saliency());
//				for(TIntention inten: Intentions)
//				{
//					
//					Game.Print("See: The saliency of the first intention is: "+inten.get_Desire().Get_Name()+"   -   "+inten.get_Desire().get_Attentional_Goal().get_Saliency());
//				}
				TDesire Desire = Intention.get_Desire();
				TAttentional_Goal Attentional_Goal = Desire.get_Attentional_Goal();
				
				if (Attentional_Goal instanceof TFunctional_Goal) 
				{
					Game.Print("I act an action for the intention with Functional Name: "+
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
						
						TBelief_Base Route_Status = null;
						
						ArrayList<TBelief_Base> Unhibited_Beliefs;
						//I get Current_Route Belief
						Unhibited_Beliefs = this.Agent.Get_GW().Get_Beliefs_From_Type_Belief(TType_Beliefs.Belief_Current_Route);
						Current_Route = Unhibited_Beliefs.getFirst();
		
						//I get Current_Step Belief
						Unhibited_Beliefs = this.Agent.Get_GW().Get_Beliefs_From_Type_Belief(TType_Beliefs.Belief_Current_Step);
						Current_Step = Unhibited_Beliefs.getFirst();
		
						//I get Current_Station Belief
						Unhibited_Beliefs = this.Agent.Get_GW().Get_Beliefs_From_Type_Belief(TType_Beliefs.Belief_Current_Station);
						Current_Station = Unhibited_Beliefs.getFirst();
						
						Game.Print("I check if current positions and precondition positions for the act current action are congruent (equal):");
						Game.Print("Current position Station is: "+Current_Station.Predicate.get_Object_Complement());
						Game.Print("Current position Route is: "+Current_Route.Predicate.get_Object_Complement());
						Game.Print("Current position Step is: "+Current_Step.Predicate.get_Object_Complement());
						
						Game.Print("Precondition position Station is: "+Precondition_Position.Get_Station());
						Game.Print("Precondition position Route is: "+Precondition_Position.Get_Route());
						Game.Print("Precondition position Stepis: "+Precondition_Position.Get_Step());
						
						if((int)Current_Route.Predicate.get_Object_Complement() == (int)Precondition_Position.Get_Route() &&
								(int)Current_Step.Predicate.get_Object_Complement() == (int)Precondition_Position.Get_Step() &&
							Current_Station.Predicate.get_Object_Complement() == Precondition_Position.Get_Station())
						{
//							Game.Print("Yes, Position of Precondition of the action are correct!");
							Game.Print_Colored_Text("Yes, Position of Precondition of the action are correct!", 3);
							Game.Print("Now, I check my knowledge of the route status");
							
							switch(Action.Get_Action_Name())
							{
							case "GO_TO_Route":
								//The agent have to  know the status of the route before to move
								//I get Route_Status Belief
								
								Unhibited_Beliefs = this.Agent.Get_GW().Get_Beliefs_From_Type_Belief(TType_Beliefs.Belief_Route_Status);
								TPredicate Postcondition =  Action.Get_Postcondition();
								TPosition_Train_Coords Post_Position = (TPosition_Train_Coords) Postcondition.get_Object_Complement();
								int Integer_Route = (int) Post_Position.Get_Route();
								

								for(TBelief_Base Belief: Unhibited_Beliefs)
								{
									if((int) Belief.get_Predicate().get_Subject() == Integer_Route)
									{
										Route_Status = Belief;
									}
								}	
								if(Route_Status == null)
								{
									Game.Print("An error occur: Route_Status is null ion Plan_Advanc_Eval in TResource_Allocation");
									Game.End_Game();
								}
								else
								{
									switch((TType_Route_Status) Route_Status.get_Predicate().get_Object_Complement())
									{
									case TType_Route_Status.Green:
										Game.Print_Colored_Text("Route status is Green! I can go to route: "+Integer_Route, 3);
										Game.Print_Colored_Text("I execute the action", 3);
//										Game.Get_Input("Stop before calling Plan_Exec method");
										this.Plan_Exec(Action);
										break;
										
									case TType_Route_Status.Red:
										Game.Print_Colored_Text("Route status is Red! I cannot go to route: "+Integer_Route, 2);
																														   
										Unhibited_Beliefs = this.Agent.Get_GW().Get_Beliefs_From_Type_Belief(TType_Beliefs.Belief_Temporary_Closed_Route);
										TBelief_Base Bel = Unhibited_Beliefs.getFirst();  
										HashMap<Integer, Integer> Routes = (HashMap<Integer, Integer>) Bel.Predicate.get_Subject();
//										Game.Print(Routes.keySet());
//										Game.Print(Routes.values());
										if(Routes.containsKey(Integer_Route))
										{
											Integer Duration = Routes.get(Integer_Route);
											Game.Print_Colored_Text("The Route "+Integer_Route+" will be closed for "+Duration+" time(s)", 2);
											Game.Print_Colored_Text("Maybe, I must to recompute my plans", 2);
											Game.End_Game();
										}
										else
										{
											Game.Print_Colored_Text("I need to check how long the route will be inaccessible.",2);
											this.Insert_Stimulus_Get_Closed_Route_Duration(Integer_Route);
										}
										
										
										
										break;
										
									case TType_Route_Status.Unknown:
										Game.Print_Colored_Text("The route status is unknown.",5);
										Game.Print_Colored_Text("I need to know if it is green or not.",5);
										Game.Print_Colored_Text("I raise a stimulus to create an epistemic goal to turn on my virtual camera",2);
										this.Insert_Stimulus_Get_Route_Status(Integer_Route);
										//Game.End_Game();
										break;
									}
								}
								break;
								
							case "GO_TO_Step":
								Game.Print("I execute the action");
//								Game.Get_Input("Stop before calling Plan_Exec method");
								this.Plan_Exec(Action);
								break;
//							if(Action.Get_Action_Name() == "GO_TO_Route")
//							{
//								
//							}
							}
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
				else
				//The Attentional Goal is an Epistemic Goal
				{
					Game.Print("I act an action for the intention with Epistemic Goal Name: "+
							Desire.get_Attentional_Goal().get_Name());
					
					TOption Selected_Option = Desire.get_Option_List().get(Intention.get_Seleted_Option_Id());
					
					int Current_Action_Id = Selected_Option.Get_Action_To_Do_ID();
					
					ArrayList<TAction> Actions = new ArrayList<TAction>();
					Actions.addAll(Selected_Option.get_Plan_Actions());
					TAction Action = Actions.get(Current_Action_Id);
					
					//Game.End_Game();
					this.Plan_Exec(Action);

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
			this.get_Agent().Get_GW().Print_Data(2, 0);
			
			String Invoked_Function = Action.Get_Action_Name();
			Game.Print("I Invoke the method for execute the Action: "+Invoked_Function );
			switch(Invoked_Function)
			{
			case "GO_TO_Route", "GO_TO_Step":
				this.Execute_Movement(Action);
				break;
			case "See Route Status":
				this.Acquire_Route_Status(Action);
				break;
			case "Ask Closed Route Duration":
				this.Execute_Movement(Action);
//				Game.End_Game();
				break;
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
	
	//How long is the route closed?
	private void How_Long_Is_The_Route_Closed(TAction Action)
	{
		
	}
	
	
	public void Updated_Beliefs() 
	{
		this.Updated_Beliefs = true;
	}
	
	public void Updated_Intentions() 
	{
		this.Updated_Intentions = true;
	}
	
	private void Insert_Stimulus_Get_Route_Status(int Integer_Route)
	{
		TTriple_Object Stimulus = new TTriple_Object();
		Stimulus.Set_Object_First("See Semaphore for Route Status");
		Stimulus.Set_Object_Second(Integer_Route);
		Stimulus.Set_Object_Third(null);
		this.Agent.Get_WMM().Get_Sensor().Insert_Perception(Stimulus, "ME");
	}
	
	private void Insert_Stimulus_Get_Closed_Route_Duration(int Integer_Route)
	{
		TTriple_Object Stimulus = new TTriple_Object();
		Stimulus.Set_Object_First("Ask how long the route is closed");
		Stimulus.Set_Object_Second(Integer_Route);
		Stimulus.Set_Object_Third(null);
		this.Agent.Get_WMM().Get_Sensor().Insert_Perception(Stimulus, "ME");
	}
	
	private void Execute_Movement(TAction Action)
	{
		Game.Print("I execute an action:");
		TTriple_Object Request = new TTriple_Object();
		//FUNCTION TO DO
		Request.Set_Object_First(Action.Get_Action_Name()); 
		//PostCondition Train Coords
		Request.Set_Object_Second(Action.Get_Params().getFirst()); 
		this.Agent.Get_TCS().Execute_Player_Action(Request, this.Agent);
	}
	
	private void Acquire_Route_Status(TAction Action)
	{
		Game.Print("I turn on my virtual camera to see the status of the route:");

		int route_number = (int) Action.Get_Params().getFirst();
		//Game.Print("What color should I see? 0 - Red, 1- Green...");
		TType_Route_Status Route_Status = null;
		String result ;
		do 
		{
			Game.Print_Colored_Text("What color should I see for route "+route_number+"?", 7);
			result = Game.Get_Input("Give me a number: 0 - for Red, 1- for Green.");	
			switch(result)
			{
			case "0":
				Route_Status = TType_Route_Status.Red;
				break;
			case "1":
				Route_Status = TType_Route_Status.Green;
				break;
			}
		}
		while(Route_Status == null);
		TTriple_Object Response = new TTriple_Object();
		Response.Set_Object_First("Acquired Route Status");
		Response.Set_Object_Second(route_number);
		Response.Set_Object_Third(Route_Status);
		this.Agent.Get_WMM().Get_Sensor().Insert_Perception(Response, "ME");

//		Game.End_Game();
		
	}

}
