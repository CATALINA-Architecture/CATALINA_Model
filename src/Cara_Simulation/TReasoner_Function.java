package Cara_Simulation;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAmount;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


class TDesire_Compare implements Comparator<TDesire> {
    @Override
    public int compare(TDesire Desire0, TDesire Desire1) {

    	double w0 = Desire0.get_Attentional_Goal().Saliency;
    	double w1 = Desire1.get_Attentional_Goal().Saliency;        

        return (w0 > w1? -1 : (w0 == w1) ? 0 : 1);
    }
}

//class TGreen_Compare implements Comparator<Plan> {
//    @Override
//    public int compare(Plan Plan0, Plan Plan1) {
//
//    	double w0 = Plan0.Totale_Pesi.get(TType_Quality_Goal.TGQ_Locomotive);
//    	double w1 = Plan1.Totale_Pesi.get(TType_Quality_Goal.TGQ_Locomotive);        
//
//        return (w0 > w1? -1 : (w0 == w1) ? 0 : 1);
//    }
//}

class TGreen_Compare implements Comparator<TOption> {
    @Override
    public int compare(TOption Option0, TOption Option1) {

    	double w0 = Option0.Path.Total_Weights.get(TType_Quality_Goal.TGQ_Locomotive);
    	double w1 = Option1.Path.Total_Weights.get(TType_Quality_Goal.TGQ_Locomotive);        
        return (w0 > w1? -1 : (w0 == w1) ? 0 : 1);
    }
}

//class TQuality_Panorama_Compare implements Comparator<Plan> {
//    @Override
//    public int compare(Plan Plan0, Plan Plan1) {
//
//    	double w0 = Plan0.Totale_Pesi.get(TType_Quality_Goal.TGQ_Panorama);
//    	double w1 = Plan1.Totale_Pesi.get(TType_Quality_Goal.TGQ_Panorama);        
//
//        return (w0 > w1? -1 : (w0 == w1) ? 0 : 1);
//    }
//}

class TQuality_Panorama_Compare implements Comparator<TOption> {
    @Override
    public int compare(TOption Option0, TOption Option1) {

    	double w0 = Option0.Path.Total_Weights.get(TType_Quality_Goal.TGQ_Panorama);
    	double w1 = Option1.Path.Total_Weights.get(TType_Quality_Goal.TGQ_Panorama);        

        return (w0 > w1? -1 : (w0 == w1) ? 0 : 1);
    }
}

//class TQuality_Velocity_Compare implements Comparator<Plan> {
//    @Override
//    public int compare(Plan Plan0, Plan Plan1) {
//
//    	double w0 = Plan0.Totale_Pesi.get(TType_Quality_Goal.TGQ_Velocity);
//    	double w1 = Plan1.Totale_Pesi.get(TType_Quality_Goal.TGQ_Velocity);        
//
//        return (w0 > w1? -1 : (w0 == w1) ? 0 : 1);
//    }
//}

class TQuality_Velocity_Compare implements Comparator<TOption> {
    @Override
    public int compare(TOption Option0, TOption Option1) {

    	double w0 = Option0.Path.Total_Weights.get(TType_Quality_Goal.TGQ_Velocity);
    	double w1 = Option1.Path.Total_Weights.get(TType_Quality_Goal.TGQ_Velocity);        

        return (w0 > w1? -1 : (w0 == w1) ? 0 : 1);
    }
}

public class TReasoner_Function {
	
	private TAgent Agent;
	private boolean Updated_Desires;
	private boolean Updated_Desires_with_Options;
	private boolean Updated_Intentions;
	private boolean Updated_Beliefs;
	
	
	public TReasoner_Function(TAgent agent)
	{
		this.Agent = agent;
		this.Updated_Desires = true;
		this.Updated_Desires_with_Options = true;
		this.Updated_Intentions = true;
		this.Updated_Beliefs = true;
		
		
	}
	
	public void Insert_in_List_Update_Contract()
	{
		this.Agent.Get_GW().Insert_for_Update_Contract(TType_Update_Contract.Desires, this);
		this.Agent.Get_GW().Insert_for_Update_Contract(TType_Update_Contract.Desires_with_Option, this);
	}

	public TAgent get_Agent() {
		return Agent;
		
	}
	
	public boolean MeansEnd()
	{
		boolean result = true;
//		try 
//		{
//			Game.Print_Colored_Text("MeansEnd ", 6);
			if(this.Updated_Desires)
			{
//				Game.Print_Colored_Text("MeansEnd Entrato", 6);
				this.Updated_Desires = false;
				
				Game.Print_Colored_Text("Stop before calling MeansEnd method", 7);
				Game.Press_Enter();
					
				///the following lines of code are to use the iterator way in the Start Agent method
				//I get beliefs from Long Memory to send to Reasoner
				ArrayList<TDesire> Desires = this.Agent.Get_GW().Get_Desires();
				
				ArrayList<TDesire> Desires_To_Filter = new ArrayList<TDesire>();
				
				ArrayList<TBelief> Beliefs = new ArrayList<TBelief>();
				Beliefs.addAll(this.Agent.Get_GW().Get_UnInhibited_Beliefs());
				//When the Agent is not focused UnInhibited_Beliefs is empty so I have to get any beliefs
				if(Beliefs.isEmpty())
				{
					Beliefs.addAll(this.Agent.Get_GW().Get_Beliefs());
				}
	
				ArrayList<TIntention> Intentions = this.Agent.Get_GW().Get_Intentions();
				
				//the previous lines of code are to use the iterator way in the Start Agent method
				
				
				//In this time, I order goals to saliency order in higher order and In deliberation process
				//For our purpose, I select the first desire as selected intention.
				//So:
				this.Agent.Get_GW().Print_Data(2, 0);
				
				Collections.sort(Desires, new TDesire_Compare());
				
				Game.Print("Using my Meand-End reasoner, I try to find some options for each desire.");
				Game.PrintLn();
				int Des_Num = 0;
				
				boolean Created_Desires = false;
				for(TDesire Desire: Desires)
				{
					boolean Can_Analyze_Desire = true;
					for(TIntention Intention: Intentions)
					{
						if(Desire == Intention.get_Desire())
						{
							Can_Analyze_Desire = false;
						}
					}
					Des_Num++;
					if(Can_Analyze_Desire)
					{
						Created_Desires = true;
						TAttentional_Goal Goal = Desire.get_Attentional_Goal();
						Desires_To_Filter.add(Desire);

						//if the attentional_goal is a TFunctional_Goal
						if (Goal instanceof TFunctional_Goal) 
						{
							Game.Print("********* --------------- *************");
							Game.PrintLn();
							Game.Print(Des_Num+" - For the Desire --"+Desire.Get_Name()+"-- The Attentional Goal is a:  TFunctional_Goal");
							TFunctional_Goal Functional_Goal = (TFunctional_Goal) Goal;
							TBelief Functional_Belief = Functional_Goal.get_Final_State();
							switch(Functional_Belief.get_Type_Belief())
							{
									///
									///		Belief_Destination_Station
									///		
									///     It means The Agent have to create a plan to go to its destination
									///
								case TType_Beliefs.Belief_Destination_Station:
									Game.Print("Its Final belief type is: Belief_Destination_Station");
									Game.Print("I have to create some plans to go to my destination.");
									Means_End_For_Belief_Destination_Station(Desire, Beliefs);
									Game.PrintLn();
									break;
								case TType_Beliefs.Belief_Come_Back_to_City:
									Game.Print("Its Final belief type is: Belief_Come_Back_to_City");
									Game.Print("I have to make plans to return to the previous city before the route.");
									Means_End_For_Belief_Come_Back_to_City(Desire, Beliefs);
									break;
								default:
									Game.Print("For Means-End Reasoner, I cannot to handle the Type of Functional Belief: "+Functional_Belief.get_Type_Belief());
									Game.End_Game();
							}		
			
						}
					
						//the attentional_goal is an TEpistemic_Goal
						//TO DO DEVELOPMENT
						else //if (Goal instanceof TEpistemic_Goal) 
						{
							Game.Print("********* --------------- *************");
							Game.PrintLn();
							Game.Print(Des_Num+" - For the Desire --"+Desire.Get_Name()+"-- The Attentional Goal is a:  TEpistemic_Goal");
							TEpistemic_Goal Epistemic_Goal = (TEpistemic_Goal) Goal;
							TBelief Epistemic_Belief = Epistemic_Goal.get_Belief();
//							Game.Print("-- Type of Epistemic Belief: : "+Epistemic_Belief.get_Type_Belief());
							switch(Epistemic_Belief.get_Type_Belief())
							{
									///
									///		Belief_Destination_Station
									///		
									///     It means The Agent have to create a plan to go to its destination
									///
								case TType_Beliefs.Stimulus_Temporary_Closed_Route:
									Game.Print_Colored_Text("Stop before calling Deliberate_from_Stimulus_Temporary_Closed_Route method", 7);
									Game.Press_Enter();
									Deliberate_for_Stimulus_Temporary_Closed_Route(Desire);
									break;
									
								case TType_Beliefs.Stimulus_Route_Status:
									
									Game.Print_Colored_Text("Stop before calling Means_End_For_Stimulus_Status_Route method", 7);
									Game.Press_Enter();
									this.Means_End_For_Stimulus_Status_Route(Desire, Beliefs);
									Game.PrintLn();									
									///
									break;
								case TType_Beliefs.Stimulus_Danger_on_the_Route:
									Game.Print_Colored_Text("Stop before calling Means_End_For_Stimulus_Danger_on_the_Route method", 7);
									Game.Press_Enter();
									this.Means_End_For_Stimulus_Danger_on_the_Route(Desire, Beliefs);
									Game.PrintLn();	
									
									break;
								default:
									Game.Print("For Means-End Reasoner, I cannot to handle the Type of Epistemic Salient Belief: "+Epistemic_Belief.get_Type_Belief());
									Game.End_Game();
							}		
						}
					}
					
				}
				if(Created_Desires)
				{
					Game.Print("Now I will filter any desire.");
					Game.Print_Colored_Text("Stop before calling Filtering_Process method", 7);
					Game.Press_Enter();
//					this.Filtering_Process(Desires);
					this.Filtering_Process(Desires_To_Filter);
				}
				
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
	
	/***
	 * TO DO DEVELOPMENT
	 * Untill now, I Create only the Reasoner for search a path from a departure station to a destination station
	 */
	public boolean Deliberate_Process()	
	{
		boolean result = true;
//		try 
//		{
			if(this.Updated_Desires_with_Options)
			{
				this.Updated_Desires_with_Options = false;
				Game.Print_Colored_Text("Stop before calling Deliberate_Process method", 7);
				Game.Press_Enter();
				
				this.Agent.Get_GW().Print_Data(2, 0);
				
				
				ArrayList<TBelief> Beliefs = new ArrayList<TBelief>();
				Beliefs.addAll(this.Agent.Get_GW().Get_UnInhibited_Beliefs());
				//When the Agent is not focused UnInhibited_Beliefs is empty so I have to get any beliefs
				if(Beliefs.isEmpty())
				{
					Beliefs.addAll(this.Agent.Get_GW().Get_Beliefs());
				}
				
				ArrayList<TIntention> New_Intentions = new ArrayList<TIntention>();
				ArrayList<TIntention> Intentions = this.Agent.Get_GW().Get_Intentions();
				ArrayList<String> Attentional_Goals_in_Intentions = new ArrayList<String>();
				ArrayList<TDesire> Desires_in_Intentions = new ArrayList<TDesire>();
				ArrayList<TDesire> Desires_To_Promote = new ArrayList<TDesire>();
				for(TIntention Intention: Intentions)
				{
					Attentional_Goals_in_Intentions.add(Intention.get_Desire().get_Attentional_Goal().get_Name());
					Desires_in_Intentions.add(Intention.get_Desire());
				}
				
				ArrayList<TDesire> Desires =  this.Agent.Get_GW().Get_Desires();
				Desires_To_Promote.addAll(Desires);
				Desires_To_Promote.removeAll(Desires_in_Intentions);
				Game.Print("For each Desire, I select an option (if it exists) and I decide whether a Desire can become an intention to pursue.");
				for(TDesire Desire: Desires_To_Promote)
				{
					
					// I Deliberate an option (if it exists ad I create an Intention for any Desire and the first option of this Desire is the Option
					// selected to pursue (for our purpose)
					//If
					if(Desire.get_Attentional_Goal() instanceof TFunctional_Goal)
					{
						TFunctional_Goal Functional_Goal = (TFunctional_Goal) Desire.get_Attentional_Goal();
						
						switch(Functional_Goal.get_Final_State().get_Type_Belief())
						{
						case TType_Beliefs.Belief_Destination_Station:
							this.Deliberate_for_Belief_Destination_Station(Desire, Beliefs);	
							break;
						//Maybe it works
						case TType_Beliefs.Belief_Come_Back_to_City:
							this.Deliberate_for_Belief_Destination_Station(Desire, Beliefs);	
							break;
						default:
							//Other cases to handle 
							Game.Print("I have to handle other cases to deliberate this Desire");
							Game.End_Game();
						}
											
					}
					else
					{
						TEpistemic_Goal Epistemic_Goal = (TEpistemic_Goal) Desire.get_Attentional_Goal();
						
						switch(Epistemic_Goal.get_Belief().get_Type_Belief())
						{
						case TType_Beliefs.Stimulus_Route_Status:
							
							break;
						case TType_Beliefs.Stimulus_Temporary_Closed_Route:
							
							break;
						case TType_Beliefs.Stimulus_Danger_on_the_Route:
							
							break;
						default:
							//Other cases to handle 
							Game.Print("I have to handle other cases to deliberate this Desire");
							Game.End_Game();
						}
					}
					
					
					int Intention_Number = this.Agent.Get_GW().Get_Inc_Intention_Number() + 1;
					String Intention_Name = "I"+Intention_Number;
					TIntention An_Intention = new TIntention(Intention_Name, Desire, 0);
					Desire.Set_Realated_Intention(An_Intention);
					New_Intentions.add(An_Intention);
					this.Agent.Get_GW().Inc_Intention_Number();
					Game.Print("I create the Intention: "+An_Intention.Get_Name()+ " for the Desire: "+Desire.Get_Name());
					Game.Print("Desire Attentional Goal Name: "+Desire.get_Attentional_Goal().get_Name());
					Game.Print("N. Desire Options: " + Desire.get_Option_List().size());
					Game.PrintLn();
				}
				this.Agent.Get_GW().Update_Intentions(New_Intentions);
				Game.Print("I updated my intentions correctly.");
				
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
	
	protected boolean Deliberate_for_Belief_Destination_Station(TDesire Desire, ArrayList<TBelief> Beliefs)
	{
		boolean result = true;
		try 
		{
			TAttentional_Goal Goal = Desire.get_Attentional_Goal();
			TFunctional_Goal Functional_Goal = (TFunctional_Goal) Goal;
			TBelief Functional_Belief = Functional_Goal.get_Final_State();
	
			int i=0;
			
			LocalDateTime Current_Time = null;
					while (i< Beliefs.size())
					{
						TBelief Temp_Belief = Beliefs.get(i);
						// Agent must to understand in which station it is
						if (Temp_Belief.get_Type_Belief()  == TType_Beliefs.Belief_Current_Time) 
						{
							Current_Time = (LocalDateTime) Temp_Belief.get_Predicate().get_Object_Complement();
							break;
						}
						i++;
					}
			
			
			LocalDateTime Start_Time = Functional_Goal.Get_Finally_Start();
			LocalDateTime End_Time = Functional_Goal.Get_Finally_End();
			
			i = 0;
			
			ArrayList<TOption> option_List =  new ArrayList<TOption>();
			ArrayList<TAction> plans = new ArrayList<TAction>();
			Boolean Print_PAth = true;
			Game.PrintLn();
			Game.Print("Now, I have to deliberate for an option (if it exists) for the Desire: "+Desire.Get_Name());
			Game.Print("Linked to Functional Goal : "+Goal.get_Name());
			Game.Print("For each action plan for optios, I insert any actions to do (go to in some routes) in the desire.");

			option_List = Desire.get_Option_List();
			
			i=0;
			if (option_List.size()>0)
			{
				Game.Print("For this Desire, I have found n. options: "+option_List.size());
				Game.Print("and I deliberate the following Selected Path:");
				//         ///////////FirsOp    Actions    Action[0]
				//Game.Print(option_List.get(0).get_Plan_Actions().getFirst().Get_Params());
				
				// Print Data PAth
				Plan Path = option_List.get(0).Path;
				int Minute_of_Hours = Path.Path_Time.intValue();
				int Minutes = (int)((Path.Path_Time-Minute_of_Hours)*60) + Minute_of_Hours*60;
				LocalDateTime Temp_Time = Current_Time.plusMinutes(Minutes);
				
				//if(this.Agent.Get_GW().Print_Selected_Path)
				if(true)
				{
					Game.PrintLn();
					Game.Print_Colored_Text("******************** Selected Path **********************: TReasoner_Function", 3);
					Game.Print("********* PAth: "+i);	
				
					Game.Print("Stations: "+Path.Destinations);
					Game.Print("Routes Numbers: "+Path.Routes);
					Game.Print("Agent Start Time: "+Current_Time+ " - Finally Operator Data: Start_Interval: "+Start_Time+ " - End_Interval: "+End_Time);
					Game.Print("Panorama average: "+Path.Total_Weights.get(TType_Quality_Goal.TGQ_Panorama));
					Game.Print("Locomotive average ( CO2/Route ): "+Path.Total_Weights.get(TType_Quality_Goal.TGQ_Locomotive));
					Game.Print("Speed average: "+Path.Total_Weights.get(TType_Quality_Goal.TGQ_Velocity));
					
					Game.Print("Path Time: "+Path.Path_Time+" - Arrive time: "+Temp_Time);
					
					Game.Print("Path Data:");
					Game.Print("----------------------------");
				}
//				Game.Gui_Map.Show_Message("Stop", "Stop", 2);
				for (int ind: Path.Routes)
				{
					Route rotta = this.Agent.Get_WMM().Get_Map().Get_Route(ind);
					if(this.Agent.Get_GW().Print_Selected_Path)
					{
//						Game.Print("Route Number: "+rotta.Route_Number + " - Route_Time: "+rotta.Get_Path_Time());
						Game.Print_Colored_Text("Route Number: "+rotta.Route_Number + " - Route_Time: "+rotta.Get_Path_Time(),3);
						Game.Print("Route Color: "+rotta.Get_Color());
						Game.Print("Total Rounds Route: "+rotta.Get_Total_Rounds());
						Game.Print("Departure/Destination: "+ rotta.Get_Departure()+"-"+rotta.Get_Destination()+
								" - Locomotive: "+rotta.Get_Locomotive() + " - Panorama: "+rotta.Get_Panorama_()+
								" - Speed: "+rotta.Get_Velocita()+ " - N.pieces: "+rotta.Get_Steps_Number());
						Game.Print("Step Number: "+rotta.Get_Steps_Number());
						Game.Print("Locomotive value: "+rotta.Get_Locomotive().Get_Value(rotta.Get_Locomotive()));
						Game.Print("Locomotive Route value: "+rotta.Get_Route_Locomotive());
						Game.Print("Panorama value: "+rotta.Get_Panorama_().Get_Value(rotta.Get_Panorama_()));
						Game.Print("Speed value: "+rotta.Get_Velocita().Get_Value(rotta.Get_Velocita()));
						Game.Print("**********************");
					}
				}
			}
			else //Agent cannot deliberate, it hasn't any option
			{
				// It means that agent cannot to go in its destination because it hasn't a plan to pursue
				// In this case the agent stop this goal and desire and continue to iterate its goals in
				// AM_Endogenous_Module
				Game.Print("I have not found any option. I cannot deliberate. I will have an intention without option.");
	
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
	
	protected boolean Means_End_For_Belief_Come_Back_to_City(TDesire Desire, ArrayList<TBelief> Beliefs)
	{
		///
		///		Belief_Come_Back_to_City
		///		
		///     This means that the agent must create a plan to return/stay in the previous city before 
		///		entering the current route.
		///
		boolean result = true;
		try 
		{
			Game.Print("I use my Means_End_For_Belief_Destination_Station method to search some options");
			this.Agent.Get_GW().Print_Data(2, 0);
			
			TAttentional_Goal Goal = Desire.get_Attentional_Goal();
			TFunctional_Goal Functional_Goal = (TFunctional_Goal) Goal;
			TBelief Functional_Belief = Functional_Goal.get_Final_State();
			
			Game.PrintLn();
			Game.Print("The Functional_Goal Name is: "+Functional_Goal.get_Name());
			Game.Print("The Functional_Belief is a: "+Functional_Goal.get_Final_State().get_Type_Belief());
			
			City Current_City = null;
			int Current_Route = -2;
			int Current_Step = -1;
			int i = 0;
			while (i< Beliefs.size())
			{
				TBelief Temp_Belief = Beliefs.get(i);
				// Agent must to understand in which station it is
				if (Temp_Belief.get_Type_Belief()  == TType_Beliefs.Belief_Current_City) 
				{
					if (Temp_Belief.get_Predicate().get_Subject() == TType_Subject.Me)
					{
						Current_City = (City) Temp_Belief.Predicate.get_Object_Complement();
					}
//					continue;
				}
				if (Temp_Belief.get_Type_Belief()  == TType_Beliefs.Belief_Current_Route) 
				{
					if (Temp_Belief.get_Predicate().get_Subject() == TType_Subject.Me)
					{
						Current_Route = (int) Temp_Belief.Predicate.get_Object_Complement();
					}
//					break;
				}
				if (Temp_Belief.get_Type_Belief()  == TType_Beliefs.Belief_Current_Step) 
				{
					if (Temp_Belief.get_Predicate().get_Subject() == TType_Subject.Me)
					{
						Current_Step = (int) Temp_Belief.Predicate.get_Object_Complement();
					}
//					break;
				}
				i++;
			}
			// The perception "Acquired Danger Type on the road" has already changed and inverted
			// Current_Route and Current_Step to maintain a consistency with where beliefs need to change 
			Game.Print(Current_City+" - "+Current_Route+ " - "+Current_Step);
//			Game.End_Game();
			ArrayList<Plan> Paths = new ArrayList<Plan>();
			i = 0;
			
			
			Boolean Print_PAth = true;

			ArrayList<TOption> option_List =  new ArrayList<TOption>();
			ArrayList<TAction> plans = new ArrayList<TAction>();
			ArrayList<TAction> Actions = new ArrayList<TAction>();
			String Function_To_Invoke = "Come_Back_to_City";
			
			if(this.Agent.Get_GW().Print_steps_and_routes)
			{
				Game.Print("Start creating Path Plan:");
			}
			
			//if Current_Route < 0, Agent is already in city, it must not move
			Game.Print("Current_Route: "+Current_Route);
			Game.Print("Current_Step: "+Current_Step);
//			Game.End_Game();
			if(Current_Route < 0)
			{
				Game.Print("Agent is already in city: "+Current_City);
				TPosition_Train_Coords Precondition_Position_Train_Coords = new TPosition_Train_Coords(Current_City, Current_Route, Current_Step);
				TPosition_Train_Coords Postcondition_Position_Train_Coords = new TPosition_Train_Coords(Current_City, Current_Route, Current_Step);
				
				TPredicate Precondition = new TPredicate(null, TType_Subject.Me, 
						TType_Relationship.is_in, Precondition_Position_Train_Coords);
				
				TPredicate Postcondition = new TPredicate(null, TType_Subject.Me, 
						TType_Relationship.is_in, Postcondition_Position_Train_Coords);
				
				TAction An_Action = new TAction(null, Precondition, Postcondition);
				An_Action.Get_Params().add(Postcondition_Position_Train_Coords);
				
				An_Action.Set_Action_Name(Function_To_Invoke);
				Actions.add(An_Action);
				Game.Print("(Current_Route < 0)");
//				Game.End_Game();
			}
			else
			{
				Game.Print("(Current_Route >= 0)");
//				Game.End_Game();
				Route route = this.get_Agent().Get_WMM().Get_Map().All_Routes.get(Current_Route);
				
				Plan Action_plan = new Plan();
				Action_plan.Destinations.add(route.Get_Departure());
				Action_plan.Destinations.add(route.Get_Destination());
				Action_plan.Get_Routes().add(Current_Route);
				Action_plan.Total_Weights.putAll(this.get_Agent().Get_WMM().Get_Map().Compute_Sum_Weights_Routes(Action_plan.Get_Routes()));
				Action_plan.Path_Time  = route.Get_Path_Time_Starting_By_Step(Current_Step);
				
				Paths.add(Action_plan);
//				Paths = this.get_Agent().Get_WMM().Get_Map().Find_All_Paths(route.Get_Departure(), route.Get_Destination());
				
				Environment Map = this.Agent.Get_GW().Get_Map_Known();
				//For any path
				if(this.Agent.Get_GW().Print_steps_and_routes)
				{
					Game.Print("**** Printing Paths Steps ********");
				}
				
				
				
//				Game.End_Game();
				
				for (Plan path: Paths)
				{	
					Game.Print("path: "+path);
	//							i++;
					if(this.Agent.Get_GW().Print_steps_and_routes)
					{
						Game.PrintLn();
						Game.Print("****** Path: "+i);
						Game.Print("Route list of the path: "+path.Routes);
					}
					Actions = new ArrayList<TAction>();
					
					//I Create plans for any options
					//For Any Route
					for (Integer Route_Number: path.Routes )
					{
						
						//Now, Action stores only a Route at a time
						// An Action => A step for a Route
	
						//Now, I create any action in plan option
						//I get the rounds time to go from Station A to Station B
						Route A_Route = Map.All_Routes.get(Route_Number);
						if(this.Agent.Get_GW().Print_steps_and_routes)
						{
							Game.PrintLn();	
							Game.Print("******** New Route: "+Route_Number+ " - Departure: "+A_Route.Get_Departure()+
									" - Destination: "+A_Route.Get_Destination());
						}
						
						int Rounds_Time = A_Route.Get_Total_Rounds_Starting_By_Step(Current_Step);
						
						//I get the correct Station
						City A_Departure_Station;
						City A_Destination_Station;
						City A_Destination_Station_in_PostCondition;
	
						//A_Departure_Station = Current_City;//A_Route.Get_Departure();
						A_Departure_Station = A_Route.Get_Departure();
						A_Destination_Station = A_Route.Get_Destination();
						int step_position = Current_Step;
						Game.Print("step_position: "+step_position);
						int Start_route_position = Route_Number;
						int End_route_position = Route_Number;
						//For Any Step in Route
						if(this.Agent.Get_GW().Print_steps_and_routes)
						{
							Game.Print("Start creating Path Plan:");
						}
						
						for(Integer Step = Current_Step; Step <= Rounds_Time; Step++)
						{
							TPosition_Train_Coords Precondition_Position_Train_Coords;
							TPosition_Train_Coords Postcondition_Position_Train_Coords;
							TPredicate Precondition;
							TPredicate Postcondition;
							
							
							Function_To_Invoke = "Come_Back_to_City";
							Start_route_position = Route_Number;
	
							Precondition_Position_Train_Coords = new TPosition_Train_Coords(A_Departure_Station, 
									Start_route_position, step_position);
							
							step_position = step_position + A_Route.get_Route_Speed();
							
							//I set ending precondition data
							//A_Destination_Station_in_PostCondition = Current_City;//City; A_Departure_Station;
							A_Destination_Station_in_PostCondition = A_Departure_Station;
							
							if (step_position > A_Route.Get_Steps_Number())
							{
								//A_Destination_Station_in_PostCondition = Current_City;//A_Destination_Station;
								A_Destination_Station_in_PostCondition = A_Destination_Station;
								//If I arrive in next Station, I set the Route to -1 and the
								//step_position to 0
								End_route_position = -1;
								step_position = 0;
							}
	
							Postcondition_Position_Train_Coords = new TPosition_Train_Coords(
									A_Destination_Station_in_PostCondition, End_route_position, step_position);
							
							Precondition = new TPredicate(null, TType_Subject.Me, 
									TType_Relationship.is_in, Precondition_Position_Train_Coords);
							
							Postcondition = new TPredicate(null, TType_Subject.Me, 
									TType_Relationship.is_in, Postcondition_Position_Train_Coords);
							
							
							TAction An_Action = new TAction(null, Precondition, Postcondition);
							An_Action.Get_Params().add(Postcondition_Position_Train_Coords);
							An_Action.Get_Params().add(A_Route.Route_Number);
							
							
							// I define "Use_Route" as a function to go from a departure to a destination station
							An_Action.Set_Action_Name(Function_To_Invoke);
							Actions.add(An_Action);
							if(this.Agent.Get_GW().Print_steps_and_routes)
							{
								Game.Print("Precondition.  Station: "+Precondition_Position_Train_Coords.Get_City()+
										" - Route: "+Precondition_Position_Train_Coords.Get_Route()+
										" - Step: "+Precondition_Position_Train_Coords.Get_Step());
								Game.Print("Postcondition.  Station: "+Postcondition_Position_Train_Coords.Get_City()+
										" - Route: "+Postcondition_Position_Train_Coords.Get_Route()+
										" - Step: "+Postcondition_Position_Train_Coords.Get_Step());
								Game.Print("-------------- Next Step --------------");
							}
						}
					}
					
					TOption An_Option = new TOption(Actions, null, 0.0, path.Total_Weights);
					An_Option.Path.Copy_Plan(path);
					option_List.add(An_Option);
				}
					i++;
					Game.Print("....................................");
			}
				
			Desire.set_Option_List(option_List);
//			Game.End_Game();
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
	
	protected boolean Means_End_For_Belief_Destination_Station(TDesire Desire, ArrayList<TBelief> Beliefs)
	{
	
	///
	///		Belief_Destination_Station
	///		
	///     It means The Agent have to create a plan to go to its destination
	///
		
		boolean result = true;
		try 
		{
			Game.Print("I use my Means_End_For_Belief_Destination_Station method to search some options");
			this.Agent.Get_GW().Print_Data(2, 0);
			
			TAttentional_Goal Goal = Desire.get_Attentional_Goal();
			TFunctional_Goal Functional_Goal = (TFunctional_Goal) Goal;
			TBelief Functional_Belief = Functional_Goal.get_Final_State();
			
			Game.PrintLn();
			Game.Print("The Functional_Goal Name is: "+Functional_Goal.get_Name());
			Game.Print("The Functional_Belief is a:  Belief_Destination_Station");
			City Destination_Station = (City) Functional_Belief.Predicate.get_Subject();
			City Current_Station = null;
			int i = 0;
			while (i< Beliefs.size())
			{
				TBelief Temp_Belief = Beliefs.get(i);
				// Agent must to understand in which station it is
				if (Temp_Belief.get_Type_Belief()  == TType_Beliefs.Belief_Current_City) 
				{
					if (Temp_Belief.get_Predicate().get_Subject() == TType_Subject.Me)
					{
						Current_Station = (City) Temp_Belief.Predicate.get_Object_Complement();
					}
					break;
				}
				i++;
			}
			Game.Print("I search for several options for the Desire. I start my research from the station "+Current_Station);
			Instant start = Instant.now();
			
			ArrayList<Plan> Paths = this.get_Agent().Get_WMM().Get_Map().Find_All_Paths(Current_Station, Destination_Station);
		
			Instant end = Instant.now();
			Duration timeElapsed = Duration.between(start, end);
			Game.Print("I created/found some options in Time: "+ timeElapsed.toMillis() +" milliseconds");
			
			// Now, Agent must to sort the Plans in according to the Green Goal (We consider a green goal like a mandatory goal, but to avoid that the Agent
			// can block we relax the green goal using a "sorting method"
			Game.Print("I found paths: " + Paths.size());
			i = 0;
			LocalDateTime Current_Time = null;
			while (i< Beliefs.size())
			{
				TBelief Temp_Belief = Beliefs.get(i);
				// Agent must to understand in which station it is
				if (Temp_Belief.get_Type_Belief()  == TType_Beliefs.Belief_Current_Time) 
				{
					Current_Time = (LocalDateTime) Temp_Belief.get_Predicate().get_Object_Complement();
					break;
				}
				i++;
			}
			
					
			
			// Now, I send this plans to the Finally_Temporal_Function to filter and extract only
			// plans that satisfy the Final Temporal Function
			LocalDateTime Start_Time = Functional_Goal.Get_Finally_Start();
			LocalDateTime End_Time = Functional_Goal.Get_Finally_End();
			
			/////// I apply the Finally Operator
			
			Game.Print("I found n. paths Before Finally_Formula: " + Paths.size());
			Game.Print("I apply the Finally Operator");
			Paths = Finally_Operator(Paths, Current_Time, Start_Time, End_Time );
			Game.Print("I found n. paths After Finally_Formula: " + Paths.size());

			i = 0;
			
			ArrayList<TOption> option_List =  new ArrayList<TOption>();
			ArrayList<TAction> plans = new ArrayList<TAction>();
			Boolean Print_PAth = true;
			Game.Print("For each action plan for optios, I insert any actions to do (go to in some routes) in the desire.");
			Environment Map = this.Agent.Get_GW().Get_Map_Known();
			//For any path
			if(this.Agent.Get_GW().Print_steps_and_routes)
			{
				Game.Print("**** Printing Paths Steps ********");
			}
			
			for (Plan path: Paths)
			{	
			
//				i++;
				if(this.Agent.Get_GW().Print_steps_and_routes)
				{
					Game.PrintLn();
					Game.Print("****** Path: "+i);
					Game.Print("Routes list of the path: "+path.Routes);
					Game.Print("Destinations list of the path: "+path.Destinations);
				}
				ArrayList<TAction> Actions = new ArrayList<TAction>();
				
				//I Create plans for any options
				//For Any Route
				for (Integer Route_Number: path.Routes )
				{
					//Now, Action stores only a Route at a time
					// An Action => A step for a Route

					//Now, I create any action in plan option
					//I get the rounds time to go from Station A to Station B
					Route A_Route = Map.All_Routes.get(Route_Number);
					if(this.Agent.Get_GW().Print_steps_and_routes)
					{
						Game.PrintLn();	
						Game.Print("******** New Route: "+Route_Number+ " - Departure: "+A_Route.Get_Departure()+
								" - Destination: "+A_Route.Get_Destination());
					}
					
					int Rounds_Time = A_Route.Get_Total_Rounds();
					
					//I get the correct Station
					City A_Departure_Station;
					City A_Destination_Station;
					City A_Destination_Station_in_PostCondition;

					A_Departure_Station = A_Route.Get_Departure();
					A_Destination_Station = A_Route.Get_Destination();
					int step_position = 0;
					int Start_route_position = Route_Number;
					int End_route_position = Route_Number;
					//For Any Step in Route
					if(this.Agent.Get_GW().Print_steps_and_routes)
					{
						Game.Print("Start creating Path Plan:");
					}
					for(Integer Step = 1; Step <= Rounds_Time; Step++)
					{
						TPosition_Train_Coords Precondition_Position_Train_Coords;
						TPosition_Train_Coords Postcondition_Position_Train_Coords;
						TPredicate Precondition;
						TPredicate Postcondition;
						
						String Function_To_Invoke;
						
						//I set starting precondition data
						if (Step == 1)
						{
							Function_To_Invoke = "GO_TO_Route";
							Start_route_position = -1;
							End_route_position = Route_Number;
						}
						else 
						{
							Function_To_Invoke = "GO_TO_Step";
							Start_route_position = Route_Number;
						}

						Precondition_Position_Train_Coords = new TPosition_Train_Coords(A_Departure_Station, 
								Start_route_position, step_position);
						
						step_position = step_position + A_Route.get_Route_Speed();
						
						//I set ending precondition data
						A_Destination_Station_in_PostCondition = A_Departure_Station;
						
						if (step_position > A_Route.Get_Steps_Number())
						{
							A_Destination_Station_in_PostCondition = A_Destination_Station;
							//If I arrive in next Station, I set the Route to -1 and the
							//step_position to 0
							End_route_position = -1;
							step_position = 0;
						}

						Postcondition_Position_Train_Coords = new TPosition_Train_Coords(
								A_Destination_Station_in_PostCondition, End_route_position, step_position);
						
						Precondition = new TPredicate(null, TType_Subject.Me, 
								TType_Relationship.is_in, Precondition_Position_Train_Coords);
						
						Postcondition = new TPredicate(null, TType_Subject.Me, 
								TType_Relationship.is_in, Postcondition_Position_Train_Coords);
						
						
						TAction An_Action = new TAction(null, Precondition, Postcondition);
						An_Action.Get_Params().add(Postcondition_Position_Train_Coords);
						An_Action.Get_Params().add(A_Route.Route_Number);
						
						// I define "Use_Route" as a function to go from a departure to a destination station
						An_Action.Set_Action_Name(Function_To_Invoke);
						Actions.add(An_Action);
						if(this.Agent.Get_GW().Print_steps_and_routes)
						{
							Game.Print("Precondition.  Station: "+Precondition_Position_Train_Coords.Get_City()+
									" - Route: "+Precondition_Position_Train_Coords.Get_Route()+
									" - Step: "+Precondition_Position_Train_Coords.Get_Step());
							Game.Print("Postcondition.  Station: "+Postcondition_Position_Train_Coords.Get_City()+
									" - Route: "+Postcondition_Position_Train_Coords.Get_Route()+
									" - Step: "+Postcondition_Position_Train_Coords.Get_Step());
							Game.Print("-------------- Next Step --------------");
						}
					}
				}
				
				TOption An_Option = new TOption(Actions, null, 0.0, path.Total_Weights);
				An_Option.Path.Copy_Plan(path);
				option_List.add(An_Option);
			
				i++;
				Game.Print("....................................");
			}
			
			Desire.set_Option_List(option_List);
			Game.PrintLn();
			Game.Print("N. Options found : "+option_List.size());
			
			this.Agent.Get_GW().Print_Data(1, 0);
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
	
	private Boolean Filtering_Process(ArrayList<TDesire> desires)
	{
		boolean result = true;
//		try
//		{
			this.Agent.Get_GW().Print_Data(2, 0);
			for(TDesire Desire: desires)
			{
				TAttentional_Goal Attentional_Goal = Desire.get_Attentional_Goal();
				if (Attentional_Goal instanceof TFunctional_Goal)
				{
					Game.Print("I filter the options of the Desire with name: "+Desire.Get_Name());
//					TAttentional_Goal Goal = Desire.get_Attentional_Goal();
					TFunctional_Goal Functional_Goal = (TFunctional_Goal) Attentional_Goal;
					TBelief Functional_Belief = Functional_Goal.get_Final_State();
					
					Game.PrintLn();
					Game.Print("Now I apply Green and Quality Goal filters");
					Game.PrintLn();
					Game.Print("I apply Green filters");
					
					ArrayList<TOption> Filtered_Options = new ArrayList<TOption>();
					
					Instant start = Instant.now();
					/////// I apply Green Goal Filters
					Game.Print("N. Green Goal for this Desire: " +Functional_Goal.get_List_Green_Goal().size());
					if(Functional_Goal.get_List_Green_Goal().size()>0)
					{
						for(int i=0; i<Functional_Goal.get_List_Green_Goal().size(); i++)
						{
							Game.Print("I apply the Green Goal: "+Functional_Goal.get_List_Green_Goal().get(i).get_Name());
							Game.Print("with its Constraint Predicate Name: "+Functional_Goal.get_List_Green_Goal().get(i).get_Constraint_Name());
							Filtered_Options = Green_Filter(Desire.get_Option_List(), Functional_Goal.get_List_Green_Goal().get(i));
							Desire.set_Option_List(Filtered_Options);
						}
						
					}
					Game.Print("I found paths (options) After Green_Filter: " + Filtered_Options.size());
						
					///// I apply Quality Goal Filters
					Game.PrintLn();
					Game.Print("I apply Quality filters");
					Game.Print("N. Quality Goal for this desire: "+Functional_Goal.get_List_Quality_Goal().size());
					if(Functional_Goal.get_List_Quality_Goal().size()>0)
					{
						for(int i=0; i<Functional_Goal.get_List_Quality_Goal().size(); i++)
						{
							Game.Print("I apply the Quality Goal: "+Functional_Goal.get_List_Quality_Goal().get(i).get_Name());
							Game.Print("with its Constraint Predicate Name: "+Functional_Goal.get_List_Quality_Goal().get(i).get_Constraint_Name());
							Filtered_Options = Quality_Filter(Desire.get_Option_List(), Functional_Goal.get_List_Quality_Goal().get(i));
							Desire.set_Option_List(Filtered_Options);
						}
							
					}
					Instant end = Instant.now();
					Duration timeElapsed = Duration.between(start, end);
					
					Game.Print("I found paths (options) After Quality_Filter: " + Filtered_Options.size());
					Game.Print("Functional Goal Name for the Desire is : "+Desire.get_Attentional_Goal().get_Name());
					Game.Print("N. Survival Options of the Desire: " + Desire.get_Option_List().size());
					Game.Print("I filtered and ordered found options ( "+Filtered_Options.size()+" ) in Time: "+ 
					timeElapsed.toMillis() +" milliseconds");
					Game.Print("...............");
					Game.PrintLn();
				}
				else
				//The Attentional Goal is an Epistemic Goal
				{
					TEpistemic_Goal Epistemic_Goal = (TEpistemic_Goal) Attentional_Goal;
					switch(Epistemic_Goal.get_Belief().get_Type_Belief())
					{
					case TType_Beliefs.Stimulus_Route_Status:
						//Game.Print("Agent not filter this desire: "+Desire.Get_Name());
						
						break;
					case TType_Beliefs.Stimulus_Temporary_Closed_Route :
						//Game.Print("Agent not filter this desire: "+Desire.Get_Name());
						
						break;
					case TType_Beliefs.Stimulus_Danger_on_the_Route :
						//Game.Print("Agent not filter this desire: "+Desire.Get_Name());
						
						break;
					
					}
				}
			}
		
			this.Agent.Get_GW().Update_Desire_with_Options(desires);
			
			this.Agent.Get_GW().Print_Data(1, 0);
//		}
//		catch (Exception e) {
//	      Game.Print("Something went wrong in method: Insert_New_Desires.");
//	      Game.Print("Line§(s) Error: "+e.getStackTrace());
//	      Game.PrintLn();
//	      e.printStackTrace();
//	      result = false;
//	    }
	    return result;
	}
	

	private ArrayList<Plan> Finally_Operator(ArrayList<Plan> Paths, LocalDateTime Actual_Time,
			LocalDateTime Start_Interval, LocalDateTime End_Interval)
	{
		ArrayList<Plan> New_Paths = new ArrayList<Plan>();
		Game.Print("Start time in simulation: "+Actual_Time);
		Game.Print("Range time in Finally Operator - "+Start_Interval+ " - "+End_Interval);
		int i=0;
		for(Plan Path: Paths)
		{
			i++;
			int Minute_of_Hours = Path.Path_Time.intValue();
			int Minutes = (int)((Path.Path_Time-Minute_of_Hours)*60) + Minute_of_Hours*60;
			LocalDateTime Temp_Time = Actual_Time.plusMinutes(Minutes);
//			if(i>=964455 && i<=964464)
//			{
//				Game.Print("********* PAth: "+i);
//				Game.Print(i+" Path.Path_Time: "+Path.Path_Time +" - Path_Time.intValue(): "+Path.Path_Time.intValue() + " - Minute_of_Hours: " + Minute_of_Hours + " *** (Path.Path_Time-Minute_of_Hours)*60: "+(Path.Path_Time-Minute_of_Hours)*60);
//				Game.Print("Minutes: "+Minutes);
//				Game.Print(Path.Lista_Tratte_numerate);
//				for (int ind: Path.Lista_Tratte_numerate)
//				{
//					Route rotta = this.Agent.Get_WMM().Get_Map().get_Route(ind);
//					//Game.Print(New_Paths);
//					Game.Print("rotta: "+rotta.Numero_Rotta + " - "+rotta.Get_Path_Time());
//				}
//				Game.Print( Path.Path_Time.intValue()+ " - "+Minute_of_Hours+ " - "+Temp_Time+ " - "+Minutes+ " - "+Actual_Time+ " - "+Start_Interval+ " - "+End_Interval);
//			}
			
			
			//1 Temp_Time>Start_Interval
			//0 Temp_Time=Start_Interval
			//-1 Temp_Time<Start_Interval
			if((Temp_Time.compareTo(Start_Interval)>= 0) && (Temp_Time.compareTo(End_Interval)<= 0))
			{
				New_Paths.add(Path);
//				Game.Print("******************** Adding Path ***************************************");
//				Game.Print("********* PAth: "+i);
//				
//				Game.Print(i+" Path.Path_Time: "+Path.Path_Time +" - Path_Time.intValue(): "+Path.Path_Time.intValue() + " - Minute_of_Hours: " + Minute_of_Hours + " *** (Path.Path_Time-Minute_of_Hours)*60: "+(Path.Path_Time-Minute_of_Hours)*60);
//				Game.Print(i+" Actual_Time: "+Actual_Time+" ---- Path.Path_Time: "+Path.Path_Time +" - Path_Time.intValue(): "+Path.Path_Time.intValue() + " - Minute_of_Hours: " + Minute_of_Hours + " *** (Path.Path_Time-Minute_of_Hours)*60: "+(Path.Path_Time-Minute_of_Hours)*60);
//				Game.Print(Start_Interval + " - " + Temp_Time + " *** "+Minutes+" *** " + End_Interval );
//				Game.Print("Minutes Added to start: "+Minutes);
//				Game.Print(Path.Lista_Tratte_numerate);
//				for (int ind: Path.Lista_Tratte_numerate)
//				{
//					Route rotta = this.Agent.Get_WMM().Get_Map().get_Route(ind);
//					//Game.Print(New_Paths);
//					Game.Print("rotta: "+rotta.Numero_Rotta + " - "+rotta.Get_Path_Time());
//				}
			}

			
		}
		return New_Paths;
	}
	
//	private ArrayList<Plan> Green_Filter(ArrayList<Plan> Paths, TGreen_Goal Green_Goal)
	private ArrayList<TOption> Green_Filter(ArrayList<TOption> Options, TGreen_Goal Green_Goal)
	{
		ArrayList<TOption> New_Options = new ArrayList<TOption>();
//		Game.Print(Green_Goal.get_Constraint().get_Object_Complement().getClass());
//		Game.Print(Green_Goal.get_Constraint().get_Name());
//		Game.Print(Green_Goal.get_Constraint().get_Subject());
//		Game.Print(Green_Goal.get_Constraint().get_Relationship());
//		Game.Print(Green_Goal.get_Constraint().get_Object_Complement());
		Double Double_Value_Green_Goal;
		 
		//String Value_Green_Goal = Green_Goal.get_Constraint().get_Object_Complement();
		switch (Green_Goal.get_Constraint().get_Relationship())
		{
			case TType_Relationship.minor_equal:
				
				Game.Print("Before resizing: "+Options.size());
				Double_Value_Green_Goal = (Double) Green_Goal.get_Constraint().get_Object_Complement();
				Options.removeIf( n -> n.Path.Total_Weights.get(TType_Quality_Goal.TGQ_Locomotive) > Double_Value_Green_Goal );
				Game.Print("After resizing: "+Options.size());
				Collections.sort(Options, new TGreen_Compare());
				
				break;
			case TType_Relationship.minor:
				
				Game.Print("Before resizing: "+Options.size());
				Double_Value_Green_Goal = (Double) Green_Goal.get_Constraint().get_Object_Complement();
				Options.removeIf( n -> n.Path.Total_Weights.get(TType_Quality_Goal.TGQ_Locomotive) >= Double_Value_Green_Goal );
				Game.Print("After resizing: "+Options.size());
				Collections.sort(Options, new TGreen_Compare());
//				New_Options.addAll(Options);
				break;
			default:
				Game.Print("I cannot handle Green_Goal Complement_Object: "+Green_Goal.get_Constraint().get_Object_Complement());
		}
		
		for(int i=0;i<Options.size();i++)
		{
			New_Options.add(Options.get(i));
		}
		return New_Options;
	}
	
//	private ArrayList<Plan> Quality_Filter(ArrayList<Plan> Paths, TQuality_Goal Quality_Goal)
	private ArrayList<TOption> Quality_Filter(ArrayList<TOption> Options, TQuality_Goal Quality_Goal)
	{

		ArrayList<TOption> New_Options = new ArrayList<TOption>();
		String Value_Quality_Goal = (String) Quality_Goal.get_Constraint().get_Object_Complement();
		
//		Game.Print("--- Quality_Goal:"+Quality_Goal);
		Game.Print("--- I apply quality name: "+Quality_Goal.get_Name());
//		Game.Print("--- Quality_Goal.get_Constraint().get_Object_Complement(): "+Quality_Goal.get_Constraint().get_Object_Complement());
//		Game.Print("--- Quality_Goal.get_Constraint(): "+Quality_Goal.get_Constraint());
		
		Game.Print("--- Value_Quality_Goal: "+Value_Quality_Goal);
		switch(Quality_Goal.get_Type_Quality_Goal())
		{
		case TType_Quality_Goal.TGQ_Panorama:
			//Game.Print(Paths.get(0).Totale_Pesi.get(TType_Quality_Goal.TGQ_Panorama));
			switch (Value_Quality_Goal)
			{
			case "great":
				Collections.sort(Options, new TQuality_Panorama_Compare());
//				int start =0;
//				int end =0;	
//				//If the size list is > of 2 elements so I divide the list in 3 groups 
//				//Otherwise, I get the first element,
//				//	because "end" value is equal to 0 at this moment
//				if(Options.size()>2)
//				{
//					end = (int) Options.size()/3;
//				}
//				int i=0;
				for(int i=0;i<Options.size();i++)
				{
					New_Options.add(Options.get(i));
				}
				break;			
			default:
				Game.Print("I cannot handle Quality_Goal Complement_Objecy: "+Quality_Goal.get_Constraint().get_Object_Complement());
			}
			
			break;
		case TType_Quality_Goal.TGQ_Velocity:
			switch (Value_Quality_Goal)
			{
			case "moderate":
				Collections.sort(Options, new TQuality_Panorama_Compare());
//				int start =0;
//				int end =0;	
//				//If the size list is > of 2 elements so I divide the list in 3 groups 
//				//Otherwise, I get the first element,
//				//	because "end" value is equal to 0 at this moment
//				if(Options.size()>2)
//				{
//					end = (int) Options.size()/3;
//				}
//				int i=0;
//				for(int i=0;i<Options.size();i++)
//				{
//					New_Options.add(Options.get(i));
//				}
				New_Options.add(Options.get(Options.size()/2));
				break;			
			default:
				Game.Print("I cannot handle Quality_Goal Complement_Objecy: "+Quality_Goal.get_Constraint().get_Object_Complement());
			}
			break;
		default:
			Game.Print("I cannot handle Quality_Goal Type: "+Quality_Goal.get_Type_Quality_Goal());
			Game.End_Game();
			
		}
		return New_Options;
	}
	
	public Boolean Deliberate_for_Stimulus_Temporary_Closed_Route(TDesire Desire)
	{
		
		boolean result = true;
		try 
		{
			ArrayList<TOption> option_List =  new ArrayList<TOption>();
			ArrayList<TAction> plans = new ArrayList<TAction>();
	
			ArrayList<TAction> Actions = new ArrayList<TAction>();
			
			TEpistemic_Goal Epistemic_Goal = (TEpistemic_Goal) Desire.get_Attentional_Goal(); 
			TSalient_Belief Salient_Belief = (TSalient_Belief) Epistemic_Goal.get_Belief();
			
			int A_Route = (int) Salient_Belief.get_Predicate().get_Subject();
			Game.Print("The Route to ask: "+A_Route);
			
	
			//The Route is the first param
			TAction An_Action = new TAction(null, null, null);
			An_Action.Get_Params().add(A_Route);
							
			// I define "Use_Route" as a function to go from a departure to a destination station
			An_Action.Set_Action_Name("Ask Closed Route Duration");
			Actions.add(An_Action);
			TOption An_Option = new TOption(Actions, null, 0.0, null);
	
			option_List.add(An_Option);
			
			Desire.set_Option_List(option_List);
			Game.Print("option_List size: "+option_List.size()+ ", of course...");
			
			TIntention Selected_Intention = new TIntention("",Desire, 0);
			ArrayList<TIntention> Intentions = new ArrayList<TIntention>();
			Intentions.add(Selected_Intention);
			
			Game.Print("Agent update its intentions");
			this.Agent.Get_GW().Update_Intentions(Intentions);
			Game.Print("The Agent updated its intentions correctly");
			
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
	
	
	public void Updated_Desires() 
	{
		this.Updated_Desires = true;
	}
	
	public void Updated_Desires_with_Options() 
	{
		this.Updated_Desires_with_Options = true;
	}
	
	public void Updated_Beliefs() 
	{
		this.Updated_Beliefs = true;
	}
	
	public void Updated_Intentions() 
	{
		this.Updated_Intentions = true;
	}
	
	protected boolean Means_End_For_Stimulus_Danger_on_the_Route(TDesire Desire, ArrayList<TBelief> Beliefs)
	{
		boolean result = true;
		try 
		{
			Game.Print("I use my specific method (Means_End_For_Stimulus_Danger_on_the_Route) to get the danger type on the road.");
			this.Agent.Get_GW().Print_Data(2, 0);
			
			TAttentional_Goal Goal = Desire.get_Attentional_Goal();
			TEpistemic_Goal Epistemic_Goal = (TEpistemic_Goal) Goal;
			TSalient_Belief Salient_Belief = (TSalient_Belief) Epistemic_Goal.get_Belief();
			TPredicate Predicate = Salient_Belief.get_Predicate();
			
			//In this case, agent create one only option
			ArrayList<TOption> option_List =  new ArrayList<TOption>();
	
			ArrayList<TAction> Actions = new ArrayList<TAction>();
			TAction An_Action = new TAction(null, null, null);
			String Function_To_Invoke = "Ask Danger Type on the road";
			An_Action.Set_Action_Name(Function_To_Invoke);
			An_Action.Get_Params().add(Predicate.get_Subject());
			Actions.add(An_Action);
	
			TOption An_Option = new TOption(Actions, null, 0.0, null);
			option_List.add(An_Option);
			Desire.set_Option_List(option_List);
			this.Agent.Get_GW().Print_Data(1, 0);
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
	
	protected boolean Means_End_For_Stimulus_Status_Route(TDesire Desire, ArrayList<TBelief> Beliefs)
	{
		boolean result = true;
		try 
		{
			Game.Print("I use my specific method ( Means_End_For_Stimulus_Status_Route ) to get the route status");
			this.Agent.Get_GW().Print_Data(2, 0);
			
			TAttentional_Goal Goal = Desire.get_Attentional_Goal();
			TEpistemic_Goal Epistemic_Goal = (TEpistemic_Goal) Goal;
			TSalient_Belief Salient_Belief = (TSalient_Belief) Epistemic_Goal.get_Belief();
			TPredicate Predicate = Salient_Belief.get_Predicate();
			
			//In this case, agent create one only option
			ArrayList<TOption> option_List =  new ArrayList<TOption>();
	
			ArrayList<TAction> Actions = new ArrayList<TAction>();
			TAction An_Action = new TAction(null, null, null);
			String Function_To_Invoke = "See Route Status";
			An_Action.Set_Action_Name(Function_To_Invoke);
			An_Action.Get_Params().add(Predicate.get_Subject());
			Actions.add(An_Action);
	
			TOption An_Option = new TOption(Actions, null, 0.0, null);
			option_List.add(An_Option);
			Desire.set_Option_List(option_List);
			this.Agent.Get_GW().Print_Data(1, 0);
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
	


}
