package Catalina_Simulation;

import java.awt.Point;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;


class TIntention_Compare implements Comparator<TIntention> {
    @Override
    public int compare(TIntention Intention0, TIntention Intention1) {

    	double w0 = Intention0.Get_Active_Desire().Get_Attentional_Standing_Desire().Get_Saliency();
    	double w1 = Intention1.Get_Active_Desire().Get_Attentional_Standing_Desire().Get_Saliency();        

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
//		try 
//		{
			this.get_Agent().Get_GW().Print_Data(2, 0);
			ArrayList<TIntention> Intentions = this.Agent.Get_GW().Get_Intentions();
			if(Intentions.size() > 0)
			{
				Game.Print_Colored_Text("Stop before calling Plan_Advanc_Eval method", 7);
//				Game.Press_Enter();
				Collections.sort(Intentions, new TIntention_Compare());
				
				TIntention Intention = Intentions.getFirst();
//				Game.Print("The saliency of the first intention is: "+Intention.get_Desire().get_Attentional_Goal().get_Saliency());
//				for(TIntention inten: Intentions)
//				{
//					
//					Game.Print("See: The saliency of the first intention is: "+inten.get_Desire().Get_Name()+"   -   "+inten.get_Desire().get_Attentional_Goal().get_Saliency());
//				}
				TActive_Desire Desire = Intention.Get_Active_Desire();
				
				if(Desire != null)
				{
					TAttentional_Standing_Desire Attentional_Goal = Desire.Get_Attentional_Standing_Desire();
					if(Desire.Get_Option_List().size()>0)
					{
						
					
						if (Attentional_Goal instanceof TFunctional_Standing_Desire) 
						{
							Game.Print("I act an action for the intention with Functional Name: "+
									Desire.Get_Attentional_Standing_Desire().Get_Name());
							
							TOption Selected_Option = Desire.Get_Option_List().get(Intention.Get_Seleted_Option_Id());
							
							int Current_Action_Id = Selected_Option.Get_Action_To_Do_ID();
							
							ArrayList<TAction> Actions = new ArrayList<TAction>();
							Actions.addAll(Selected_Option.Get_Plan_Actions());
							if(Actions.size()>0)
							{
				
								TAction Action = Actions.get(Current_Action_Id);
								
								TPredicate Precondition =  Action.Get_Precondition();
								TPosition_Train_Coords Precondition_Position = 
													(TPosition_Train_Coords) Precondition.Get_Object_Complement();
				
								TBelief Current_Route;
								TBelief Current_Step;
								TBelief Current_Station;
								
								TBelief Route_Status = null;
								
								ArrayList<TBelief> Unhibited_Beliefs;
								//I get Current_Route Belief
								Unhibited_Beliefs = this.Agent.Get_GW().Get_Beliefs_From_Type_Belief(TType_Beliefs.Belief_Current_Route);
								Current_Route = Unhibited_Beliefs.getFirst();
				
								//I get Current_Step Belief
								Unhibited_Beliefs = this.Agent.Get_GW().Get_Beliefs_From_Type_Belief(TType_Beliefs.Belief_Current_Step);
								Current_Step = Unhibited_Beliefs.getFirst();
				
								//I get Current_Station Belief
								Unhibited_Beliefs = this.Agent.Get_GW().Get_Beliefs_From_Type_Belief(TType_Beliefs.Belief_Current_City);
								Current_Station = Unhibited_Beliefs.getFirst();
								
								Game.Print("I check if current positions and precondition positions for the act current action are congruent (equal):");
								Game.Print("Current position Station is: "+Current_Station.Predicate.Get_Object_Complement());
								Game.Print("Current position Route is: "+Current_Route.Predicate.Get_Object_Complement());
								Game.Print("Current position Step is: "+Current_Step.Predicate.Get_Object_Complement());
								
								Game.Print("Precondition position Station is: "+Precondition_Position.Get_City());
								Game.Print("Precondition position Route is: "+Precondition_Position.Get_Route());
								Game.Print("Precondition position Stepis: "+Precondition_Position.Get_Step());
								
								if((int)Current_Route.Predicate.Get_Object_Complement() == (int)Precondition_Position.Get_Route() &&
										(int)Current_Step.Predicate.Get_Object_Complement() == (int)Precondition_Position.Get_Step() &&
									Current_Station.Predicate.Get_Object_Complement() == Precondition_Position.Get_City())
								{
		//							Game.Print("Yes, Position of Precondition of the action are correct!");
									Game.Print_Colored_Text("Yes, Position of Precondition of the action are correct!", 3);
									
									TPredicate Postcondition =  Action.Get_Post_Condition();
									TPosition_Train_Coords Post_Position = (TPosition_Train_Coords) Postcondition.Get_Object_Complement();
									String Data1 = ""; 
	
									switch(Action.Get_Action_Name())
									{
									
									case "GO_TO_Route":
										//The agent have to  know the status of the route before to move
										//I get Route_Status Belief
										
										Game.Print("Now, I check my knowledge of the route status");
	//									Game.End_Game();
										Unhibited_Beliefs = this.Agent.Get_GW().Get_Beliefs_From_Type_Belief(TType_Beliefs.Belief_Route_Status);
		//								TPredicate Postcondition =  Action.Get_Postcondition();
		//								TPosition_Train_Coords Post_Position = (TPosition_Train_Coords) Postcondition.get_Object_Complement();
										int Integer_Route = (int) Post_Position.Get_Route();
										
										TPosition_Train_Coords Postcondition_Train_Coords = (TPosition_Train_Coords) Action.Get_Params().get(0);
										
										int Postcondition_Train_Coords_Integer_Route = (int) Postcondition_Train_Coords.Get_Route();
										int The_possible_Route = 0;
	//									if (Action.Get_Params().size()>1)
	//									{
	//										The_possible_Route = (int) Action.Get_Params().get(1);
	//									}
										Integer_Route = (int) Action.Get_Params().get(1);
										
										
		
										for(TBelief Belief: Unhibited_Beliefs)
										{
											if((int) Belief.Get_Predicate().Get_Subject() == Integer_Route)
											{
												Route_Status = Belief;
											}
										}	
										if(Route_Status == null)
										{
											Game.Print("An error occur: Route_Status is null in Plan_Advanc_Eval in TResource_Allocation");
											Game.Print("Integer_Route: "+Integer_Route);
											Game.Print("Postcondition_Train_Coords_Integer_Route: "+Postcondition_Train_Coords_Integer_Route);
											Game.Print("The_possible_Route: "+The_possible_Route);
											
											Game.Print("Precondition_Position.Get_City(): "+Precondition_Position.Get_City());
											Game.Print("Precondition_Position.Get_Route(): "+Precondition_Position.Get_Route());
											Game.Print("Precondition_Position.Get_Step(): "+Precondition_Position.Get_Step());
											Game.Print("Post_Position.Get_City(): "+Post_Position.Get_City());
											Game.Print("Post_Position.Get_Route(): "+Post_Position.Get_Route());
											Game.Print("Post_Position.Get_Step(): "+Post_Position.Get_Step());
											
											
											Game.End_Game();
										}
										else
										{
											switch((TType_Route_Status) Route_Status.Get_Predicate().Get_Object_Complement())
											{
											case TType_Route_Status.Green:
	//											Data1 = Game.Get_Preset_Input("Before entering the route, Do I see any obstacles?","1",2);
												Game.Print("this.Agent.Working_Cycle: "+this.Agent.Working_Cycle);
												Game.Print_Colored_Text("Before entering the route, Do I detect any obstacles?", 2);
												Data1 = Game.Get_Preset_Input("Give me a number: 0 - for No, 1- for Yes.", "0", 2);
												Game.Print(Data1+ " - "+Data1.getClass());
												if(Data1.equals("1"))
												{
	//													TTriple_Object Response = new TTriple_Object();
													
													this.Insert_Perception_Get_Danger_Type_on_the_Road(Action);
													this.get_Agent().Get_GW().Print_Data(1, 0);
	//												Game.Print("Entro qua in GO_TO_Route");
													
													return result;
												}
												Game.Print_Colored_Text("Route status is Green! I can go to route: "+Integer_Route, 3);
												Game.Print_Colored_Text("I execute the action", 3);
		//										Game.Get_Input("Stop before calling Plan_Exec method");
												if(!Game.Disable_Show_Message)
												{
													Game.Gui_Map.Show_Message("Information...", "The preconditions are correct, I'm getting on the route!",
														JOptionPane.INFORMATION_MESSAGE);
												}
												this.Plan_Exec(Action);
												//Agent Move the Vehicle
		//										Integer Integer_Route = Post_Position.Get_Route();
												Integer Integer_Start_Step = Precondition_Position.Get_Step();
												Integer Integer_End_Step = Post_Position.Get_Step();
												
		//										Route the_route = this.Agent.Get_GW().Get_Map_Known().Get_Route(Integer_Route);
		//										Integer Max_Steps = the_route.get_Route_Speed();
												Float Time_to_Move = 1.0f ;
												ArrayList<Point> Points = Game.Gui_Map.Map_Panel.Ruotes_Coords.get(Integer_Route);
												if(Integer_Start_Step<Integer_End_Step)
												{
													while(Integer_Start_Step<Integer_End_Step)
													{
														Integer_Start_Step++;
														Point A_Point = Points.get(Integer_Start_Step-1);
														Game.Gui_Map.Map_Panel.Move_Car_Slowly(A_Point, Time_to_Move);
													}
												}
												else if(Integer_Start_Step == Integer_End_Step)
												{
													Route route = this.Agent.Get_GW().Get_Map_Known().All_Routes.get(Integer_Route);
													
													while(Integer_Start_Step<route.Get_Steps_Number())
													{
														Integer_Start_Step++;
														Point A_Point = Points.get(Integer_Start_Step-1);
														Game.Gui_Map.Map_Panel.Move_Car_Slowly(A_Point, Time_to_Move);
													}
													Integer_End_Step = route.Get_Steps_Number();
												}
												
												
												Game.Print("Integer_Start_Step: "+Integer_Start_Step);
												Game.Print("Integer_End_Step: "+Integer_End_Step);
												Point A_Point = Points.get(Integer_End_Step-1);
												Game.Gui_Map.Map_Panel.Move_Car_Slowly(A_Point, Time_to_Move);
												
												
												break;
												
											case TType_Route_Status.Red:
												Game.Print_Colored_Text("Route status is Red! I cannot go to route: "+Integer_Route, 2);
																																   
												Unhibited_Beliefs = this.Agent.Get_GW().Get_Beliefs_From_Type_Belief(TType_Beliefs.Belief_Temporary_Closed_Route);
												TBelief Bel = Unhibited_Beliefs.getFirst();  
												HashMap<Integer, Integer> Routes = (HashMap<Integer, Integer>) Bel.Predicate.Get_Subject();
		//										Game.Print(Routes.keySet());
		//										Game.Print(Routes.values());
												if(Routes.containsKey(Integer_Route))
												{
													Integer Duration = Routes.get(Integer_Route);
													Game.Print_Colored_Text("The Route "+Integer_Route+" will be closed for "+Duration+" time(s)", 2);
													Game.Print_Colored_Text("Maybe, I must to recompute my plans", 2);
		//											Game.End_Game();
												}
												else
												{
													Game.Print_Colored_Text("I need to check how long the route will be inaccessible.",2);
													this.Insert_Perception_Get_Closed_Route_Duration(Integer_Route);
												}
												
												
												
												break;
												
											case TType_Route_Status.Unknown:
												Game.Print_Colored_Text("The route status is unknown.",5);
												Game.Print_Colored_Text("I need to know if it is open or not.",5);
												Game.Print_Colored_Text("I raise a stimulus to create an epistemic goal to turn on my virtual camera",2);
												this.Insert_Perception_Get_Route_Status(Integer_Route);
												//Game.End_Game();
												break;
											}
										}
										break;
										
									case "Stay_in_Station":
										this.Plan_Exec(Action);
										Game.Gui_Map.Show_Message("Information...", "I stay where I am, and I change the path...",
												JOptionPane.INFORMATION_MESSAGE);
										
									case "GO_TO_Step", "Come_Back_to_City":
										if(Action.Get_Action_Name().equals("GO_TO_Step"))
										{
	//										Data1 = Game.Get_Preset_Input("Before I continue on my path, Do I see any obstacles?","1",2);
											Game.Print("this.Agent.Working_Cycle: "+this.Agent.Working_Cycle);
											Game.Print_Colored_Text("Before I continue on my path, Do I detect any obstacles?", 2);
											Data1 = Game.Get_Preset_Input("Give me a number: 0 - for No, 1- for Yes.", "0", 2);
	//										Game.Print(Data1+ " - "+Data1.getClass());
											if(Data1.equals("1"))
											{
	//												TTriple_Object Response = new TTriple_Object();
												Game.Gui_Map.Show_Message("Warning...", "I detect an obstacle on the route!\nI need to know what I'm detecting!",
														JOptionPane.WARNING_MESSAGE);
												this.Insert_Perception_Get_Danger_Type_on_the_Road(Action);
												this.get_Agent().Get_GW().Print_Data(1, 0);
												Game.Print("Entro qua in GO_TO_Step");
												
												return result;
											}
										}
									
										Game.Print("I execute the action");
		//								Game.Get_Input("Stop before calling Plan_Exec method");
										this.Plan_Exec(Action);
		//								TPredicate Postcondition2 =  Action.Get_Postcondition();
		//								TPosition_Train_Coords Post_Position2 = (TPosition_Train_Coords) Postcondition.get_Object_Complement();
										Integer Integer_Start_Route = Precondition_Position.Get_Route();
										Integer Integer_End_Route = Post_Position.Get_Route();
										Integer Integer_Start_Step = Precondition_Position.Get_Step();
										Integer Integer_End_Step = Post_Position.Get_Step();
										Integer Minus = 0;
										if(Integer_End_Route==-1)
										{
											Minus = -1;
											if(!Game.Disable_Show_Message)
											{
												Game.Gui_Map.Show_Message("Information...", "Now,I enter the next Town!",
													JOptionPane.INFORMATION_MESSAGE);
											}
										}
										else
										{
											if(!Game.Disable_Show_Message)
											{
												Game.Gui_Map.Show_Message("Information...", "I continue on my path...",
													JOptionPane.INFORMATION_MESSAGE);
											}
										}
										
										
										Float Time_to_Move = 1.0f ;
										ArrayList<Point> Points = Game.Gui_Map.Map_Panel.Ruotes_Coords.get(Integer_Start_Route); 
										while(Integer_Start_Step<Integer_End_Step+Minus)
										{
											Integer_Start_Step++;
											Point A_Point = Points.get(Integer_Start_Step-1);
											Game.Gui_Map.Map_Panel.Move_Car_Slowly(A_Point, Time_to_Move);
										}
										
										if(Integer_End_Route==-1)
										{
											
											City Next_Station = Post_Position.Get_City();
											Point A_Point = Game.Gui_Map.Map_Panel.Stations_Coords.get(Next_Station);
											Game.Gui_Map.Map_Panel.Move_Car_Slowly(A_Point, Time_to_Move);
										}
										break;
	//								case "Come_Back_to_City":
	//									
	//									break;
									}
								}
								else
								{
									Game.Print("No, Precondition of the action aren't correct!");
									Game.Print("I cannot to execute the action");
									Game.End_Game();
								}
							}
							else
							{
								Game.Print("I haven't action for this Intention. Related Functional Name: "+
										Desire.Get_Attentional_Standing_Desire().Get_Name());
							}
						}
						else
						//The Attentional Goal is an Epistemic Goal
						{
							Game.Print("I act an action for the intention with Epistemic Desire Name: "+
									Desire.Get_Attentional_Standing_Desire().Get_Name());
							
							TOption Selected_Option = Desire.Get_Option_List().get(Intention.Get_Seleted_Option_Id());
							
							int Current_Action_Id = Selected_Option.Get_Action_To_Do_ID();
							
							ArrayList<TAction> Actions = new ArrayList<TAction>();
							Actions.addAll(Selected_Option.Get_Plan_Actions());
							TAction Action = Actions.get(Current_Action_Id);
							
							//Game.End_Game();
							TEpistemic_Standing_Desire Epistemic_Goal = (TEpistemic_Standing_Desire) Attentional_Goal;
							Game.Print("Intention: "+Epistemic_Goal.Get_Belief().Get_Type_Belief());
							this.Plan_Exec(Action);
		
						}
					}
					else
					{
						Game.Print_Colored_Text("I haven't an option for this Intention: "+Intention.Get_Name(), 0);
						Game.Print_Colored_Text("Related to Desire: "+Intention.Get_Active_Desire().Get_Name(), 0);
						if(Intention.Get_Active_Desire().Get_Attentional_Standing_Desire() instanceof TFunctional_Standing_Desire)
						{
							TFunctional_Standing_Desire Functional_Goal = (TFunctional_Standing_Desire)Intention.Get_Active_Desire().Get_Attentional_Standing_Desire();
							Game.Print_Colored_Text("Related to Functional_Desire: "+Functional_Goal.Get_Name(), 0);
						}
						else
						{
							TEpistemic_Standing_Desire Epistemic_Goal = (TEpistemic_Standing_Desire)Intention.Get_Active_Desire().Get_Attentional_Standing_Desire();
							Game.Print_Colored_Text("Related to Epistemic_Desire: "+Epistemic_Goal.Get_Name(), 0);
						}
					}
					
				}
			}
			this.get_Agent().Get_GW().Print_Data(1, 0);
//		}
//		catch (Exception e) {
//	      Game.Print("Something went wrongin method: Plan_Advanc_Eval.");
//	      Game.Print("Message Error: "+e.getMessage());
//	      Game.PrintLn();
//	      e.printStackTrace();
//	      result = false;
//	    }
	    return result;
	}
	
	private boolean Plan_Exec(TAction Action)
	{
		boolean result = true;
//		try 
//		{
			this.get_Agent().Get_GW().Print_Data(2, 0);
			
			String Invoked_Function = Action.Get_Action_Name();
			Game.Print("I Invoke the method for execute the Action: "+Invoked_Function );
			switch(Invoked_Function)
			{
			case "GO_TO_Route", "GO_TO_Step", "Come_Back_to_City", "Stay_in_Station":
				this.Execute_Movement(Action);		
				break;
			case "See Route Status":
				if(!Game.Disable_Show_Message)
				{
					Game.Gui_Map.Show_Message("Attention!", "I don't know the route status, but I need to know route status!\n"
						+ "Now, I have to investigate route status...", JOptionPane.WARNING_MESSAGE);
				}
				
				this.Acquire_Route_Status(Action);
				break;
			case "Ask Closed Route Duration":
				if(!Game.Disable_Show_Message)
				{
					Game.Gui_Map.Show_Message("Attention!", "The route is temporarily closed! I need to know how long the route will be closed.\n"
						+ "Now, I have to investigate...", JOptionPane.WARNING_MESSAGE);
				}
				this.Execute_Movement(Action);
//				Game.End_Game();
				break;
			case "Ask Danger Type on the road":
				if(!Game.Disable_Show_Message)
				{
					Game.Gui_Map.Show_Message("Attention!", "I don't know the danger type on route , but I need to know the danger type!\n"
						+ "Now, I have to investigate danger type...", JOptionPane.WARNING_MESSAGE);
				}
				this.Acquire_Danger_Type_on_the_Road(Action);
				break;
			default:
				Game.Print("I cannot to handle -"+ Invoked_Function+"- in Plan_Exec in TTExecutive_Resource_Allocation");
				Game.End_Game();
			}

			this.get_Agent().Get_GW().Print_Data(1, 0);
//		}
//		catch (Exception e) {
//	      Game.Print("Something went wrongin method: Plan_Advanc_Eval.");
//	      Game.Print("Message Error: "+e.getMessage());
//	      Game.PrintLn();
//	      e.printStackTrace();
//	      result = false;
//	    }
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
	
	private void Insert_Perception_Get_Route_Status(int Integer_Route)
	{
		TTriple_Object Stimulus = new TTriple_Object();
		Stimulus.Set_Object_First("See Semaphore for Route Status");
		Stimulus.Set_Object_Second(Integer_Route);
		Stimulus.Set_Object_Third(null);
		this.Agent.Get_WMM().Get_Sensor().Insert_Perception(Stimulus, "ME");
	}
	
	private void Insert_Perception_Get_Danger_Type_on_the_Road(TAction Action)
	{
		
		TPredicate Precondition =  Action.Get_Precondition();
		TPosition_Train_Coords Precondition_Position = 
							(TPosition_Train_Coords) Precondition.Get_Object_Complement();

		TPredicate Postcondition =  Action.Get_Post_Condition();
		TPosition_Train_Coords Postcondition_Position = (TPosition_Train_Coords) Postcondition.Get_Object_Complement();
		TTriple_Object Stimulus = new TTriple_Object();
		Stimulus.Set_Object_First("Danger on the route!");
		Stimulus.Set_Object_Second(Precondition_Position);
		Stimulus.Set_Object_Third(Postcondition_Position);
		Game.Print("Precondition_Position: "+Precondition_Position.Get_City()+" - "+Precondition_Position.Get_Route()+ " - "+Precondition_Position.Get_Step());
		Game.Print("Postcondition_Position: "+Postcondition_Position.Get_City()+" - "+Postcondition_Position.Get_Route()+ " - "+Postcondition_Position.Get_Step());
		Game.Get_Input("Insert_Perception_Get_Danger_Type_on_the_Road");
		//Precondition_Position.Get_Route() == -1, it means Agent is already in station and it has not to move
//		if(Precondition_Position.Get_Route() == -1)
//		{
//			this.Plan_Exec(Action);
//		}
//		else
		{
			this.Agent.Get_WMM().Get_Sensor().Insert_Perception(Stimulus, "ME");
		}
		
	}
	
	private void Insert_Perception_Get_Closed_Route_Duration(int Integer_Route)
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
	
	private void Acquire_Danger_Type_on_the_Road(TAction Action)
	{
		Game.Print("I turn on my virtual camera to see the danger type on the road:");

		TTriple_Object Danger_Data =  (TTriple_Object) Action.Get_Params().getFirst();
		
		TPosition_Train_Coords Precondition_Position = (TPosition_Train_Coords) Danger_Data.Get_Object_First();
		TPosition_Train_Coords Postcondition_Position = (TPosition_Train_Coords) Danger_Data.Get_Object_Second();
		
		int route_number = Math.max(Precondition_Position.Get_Route(), Postcondition_Position.Get_Route());
		TType_Danger Type_Danger = null;
		String result ;
		do 
		{
			Game.Print("this.Agent.Working_Cycle: "+this.Agent.Working_Cycle);
			Game.Print_Colored_Text("what kind of danger do i see on the road for route "+route_number+"?", 7);
			result = Game.Get_Input("Give me a number: 0 - for Unknown, 1- for Rocks on the road,"
					+ "	2- for Car Accident on the road.");	
			switch(result)
			{
			case "0":
				Type_Danger = TType_Danger.Unknown;
				break;
			case "1":
				Type_Danger = TType_Danger.Roks_on_the_Road;
				break;
			case "2":
				Type_Danger = TType_Danger.Car_Accident_on_the_Road;
				break;
			}
		}
		while(Type_Danger == null);
		
		Random random = new Random();
		int min = 1;
		int max = 9;
        Integer number = random.nextInt(max - min + 1) + min;
        
//        String String_Duration = Game.Get_Preset_Input("Closed Route Duration?:  ",number.toString(),2);
        String String_Duration = Game.Get_Preset_Input("How long will the route "+route_number+" be closed?: ",number.toString(),2);
        
        Integer Duration = Integer.parseInt(String_Duration);
        Danger_Data.Set_Object_Third(Duration);        
		
		TTriple_Object Response = new TTriple_Object();
		Response.Set_Object_First("Acquired Danger Type on the road");
		Response.Set_Object_Second(Danger_Data);
		Response.Set_Object_Third(Type_Danger);
		this.Agent.Get_WMM().Get_Sensor().Insert_Perception(Response, "ME");
		
		TBelief Belief_Come_Back_to_City =this.Agent.Get_GW().Get_Beliefs_From_Type_Belief(TType_Beliefs.Belief_Come_Back_to_City).getFirst();
		Belief_Come_Back_to_City.Get_Predicate().set_Linked_Belief(Belief_Come_Back_to_City);
		Belief_Come_Back_to_City.Get_Predicate().Set_Subject(Precondition_Position.Get_City());
		
		TFunctional_Standing_Desire Functional_Goal = new TFunctional_Standing_Desire("", Belief_Come_Back_to_City.Get_Name(), "", 
				0.9, 40.0, 0.1, null, null, null, null, null, null, null, null);
		
		Game.Print("Functional_Desire size: "+this.Agent.Get_WMM().Get_Functional_Standing_Desires().size());
		this.Agent.Get_GW().Add_Goal(Functional_Goal);
		
		Game.Print("Functional_Desire: "+Functional_Goal.Get_Name());
		Game.Print("New Functional_Desire size: "+this.Agent.Get_WMM().Get_Functional_Standing_Desires().size());
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
//			Game.Print_Colored_Text("What color should I see for route "+route_number+"?", 7);
//			result = Game.Get_Input("Give me a number: 0 - for Red, 1- for Green.");
			Game.Print("this.Agent.Working_Cycle: "+this.Agent.Working_Cycle);
			Route route = this.Agent.Get_GW().Get_Map_Known().All_Routes.get(route_number);
			Game.Print("Route "+route_number);
			Game.Print("Departure: "+route.Get_Departure()+ " - Destination: "+route.Get_Destination()+" - Color: "+route.Get_Color());
			Game.Print("Locomotive: "+route.Get_Locomotive()+ " - Speed: "+route.Get_Route_Speed());
			Game.Print_Colored_Text("I don't know if I can travel the route "+route_number+". Can I travel the route  "+route_number+"?", 7);
//			result = Game.Get_Input("Give me a number: 0 - for No, 1- for Yes.");
			result = Game.Get_Preset_Input("Give me a number: 0 - for No, 1- for Yes.", "1", 2);
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
	}
}