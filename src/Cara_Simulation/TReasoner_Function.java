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
//	private boolean Updated_Desires_with_Options;
	private boolean Updated_Intentions;
	private boolean Updated_Beliefs;
	
	
	public TReasoner_Function(TAgent agent)
	{
		this.Agent = agent;
		this.Updated_Desires = false;
		this.Updated_Intentions = false;
		this.Updated_Beliefs = false;
	}

	public TAgent get_Agent() {
		return Agent;
		
	}
	
	public boolean MeansEnd()
	{
		boolean result = true;
		try 
		{
			///the following lines of code are to use the iterator way in the Start Agent method
			//I get beliefs from Long Memory to send to Reasoner
			ArrayList<TDesire> Desires = this.Agent.get_GW().Get_Desires();
			
			ArrayList<TBelief_Base> Beliefs = new ArrayList<TBelief_Base>();
			Beliefs.addAll(this.Agent.get_GW().Get_UnInhibited_Beliefs());
			//When the Agent is not focused UnInhibited_Beliefs is empty so I have to get any beliefs
			if(Beliefs.isEmpty())
			{
				Beliefs.addAll(this.Agent.get_GW().Get_Beliefs());
			}

			ArrayList<TIntention> Intentions = this.Agent.get_GW().Get_Intentions();
			
			//the previous lines of code are to use the iterator way in the Start Agent method
			
			
			//In this time, I order goals to saliency order in higher order and In deliberation process
			//For our purpose, I select the first desire as selected intention.
			//So:
			this.Agent.get_GW().Print_Data(0, 0);
			
			Collections.sort(Desires, new TDesire_Compare());
			Game.PrintLn();
			Game.Print("The agent tries to find some intentions");
			int Des_Num = 0;
			for(TDesire Desire: Desires)
			{
				Des_Num++;
				TAttentional_Goal Goal = Desire.get_Attentional_Goal();
				Game.Print("Fore the Desire --"+Des_Num+"-- The Attentional Goal is a:  "+Goal.getClass());
				//if the attentional_goal is a TFunctional_Goal
				if (Goal instanceof TFunctional_Goal) 
				{
					TFunctional_Goal Functional_Goal = (TFunctional_Goal) Goal;
					TBelief_Base Functional_Belief = Functional_Goal.get_Final_State();
					switch(Functional_Belief.get_Type_Belief())
					{
							///
							///		Belief_Destination_Station
							///		
							///     It means The Agent have to create a plan to go to its destination
							///
						case TType_Beliefs.Belief_Destination_Station:
							this.Agent.get_GW().Print_Data(1, 0);
							Means_End_For_Belief_Destination_Station(Desire, Beliefs);
							break;
						default:
							Game.Print("I cannot to handle the Type of Belief: "+Functional_Belief.get_Type_Belief());
							Game.End_Game();
					}		
	
				}
			
				//the attentional_goal is an TEpistemic_Goal
				//TO DO DEVELOPMENT
				else if (Goal instanceof TEpistemic_Goal) 
				{
					Game.Print("The Attentional Goal is an:  TEpistemic_Goal");
					TEpistemic_Goal Epistemic_Goal = (TEpistemic_Goal) Goal;
					TBelief_Base Epistemic_Belief = Epistemic_Goal.get_Belief();
					Game.Print("-- Type of Epistemic Belief: : "+Epistemic_Belief.get_Type_Belief());
	
					switch(Epistemic_Belief.get_Type_Belief())
					{
							///
							///		Belief_Destination_Station
							///		
							///     It means The Agent have to create a plan to go to its destination
							///
						case TType_Beliefs.Stimulus_Temporary_Closed_Route:
							//if (Functional_Belief instanceof TBelief_Destination_Station)
							this.Agent.get_GW().Print_Data(1, 0);
							Deliberate_from_Stimulus_Temporary_Closed_Route(Desire);
							
							break;
						default:
							Game.Print("I cannot to handle the Type of Epistemic Salient Belief: "+Epistemic_Belief.get_Type_Belief());
							Game.End_Game();
					}		
				}
			}
			this.Filtering_Process(Desires);
		}
		catch (Exception e) {
	      Game.Print("Something went wrongin method: Insert_New_Desires.");
	      Game.Print("Message Error: "+e.getMessage());
	      result = false;
	    }
	    return result;
	}
	
	/***
	 * TO DO DEVELOPMENT
	 * Untill now, I Create only the Reasoner for search a path from a departure station to a destination station
	 */
	//public Boolean Deliberate(TDesire Desire, ArrayList<TBelief_Base> Beliefs)
	//public Boolean Deliberate(ArrayList<TDesire> Desires, ArrayList<TBelief_Base> Beliefs)
	//public ArrayList<TDesire> Deliberate(ArrayList<TDesire> Desires, ArrayList<TBelief_Base> Beliefs, ArrayList<TIntention> Intentions)
	public boolean Deliberate_Process()	
	{
		boolean result = true;
		try 
		{
			this.Agent.get_GW().Print_Data(0, 0);
			
			ArrayList<TDesire> Desires =  this.Agent.get_GW().Get_Desires();
			
			ArrayList<TIntention> Intentions = new ArrayList<TIntention>();
			for(TDesire Desire: Desires)
			{
				//I create an Intention for any Desire and the first option of this Desire is the Option
				// selected to pursue
				TIntention An_Intention = new TIntention(Desire, 0);
				Intentions.add(An_Intention);
			}
			this.Agent.get_GW().Update_Intentions(Intentions);
			this.Agent.get_GW().Print_Data(1, 0);
			Game.Print("The Agent updated its intentions correctly");
		}
		catch (Exception e) {
	      Game.Print("Something went wrongin method: Insert_New_Desires.");
	      Game.Print("Message Error: "+e.getMessage());
	      result = false;
	    }
		return result;
	}
	
	protected boolean Means_End_For_Belief_Destination_Station(TDesire Desire, ArrayList<TBelief_Base> Beliefs)
	//TFunctional_Goal Functional_Goal,TBelief_Base Functional_Belief, 
	//ArrayList<TBelief_Base> Beliefs)
	{
	
	///
	///		Belief_Destination_Station
	///		
	///     It means The Agent have to create a plan to go to its destination
	///
		
		boolean result = true;
		try 
		{
			this.Agent.get_GW().Print_Data(0, 0);
			
			TAttentional_Goal Goal = Desire.get_Attentional_Goal();
			TFunctional_Goal Functional_Goal = (TFunctional_Goal) Goal;
			TBelief_Base Functional_Belief = Functional_Goal.get_Final_State();
			
			Game.PrintLn();
			Game.Print("The Functional_Belief is a:  Belief_Destination_Station");
			Station Destination_Station = (Station) Functional_Belief.Predicate.get_Subject();
			Station Current_Station = null;
			int i = 0;
			while (i< Beliefs.size())
			{
				TBelief_Base Temp_Belief = Beliefs.get(i);
				// Agent must to understand in which station it is
				if (Temp_Belief.get_Type_Belief()  == TType_Beliefs.Belief_Current_Station) 
				{
					if (Temp_Belief.get_Predicate().get_Subject() == TType_Subject.Me)
					{
						Current_Station = (Station) Temp_Belief.Predicate.get_Object_Complement();
					}
					break;
				}
				i++;
			}
			Game.Print("I search for a plan for the Desire");
			Instant start = Instant.now();
			
			ArrayList<Plan> Paths = this.get_Agent().Get_WMM().Get_Map().trovaTuttiIPercorsi(Current_Station, Destination_Station);
		
			Instant end = Instant.now();
			Duration timeElapsed = Duration.between(start, end);
			Game.Print("I created found some plan in Time: "+ timeElapsed.toMillis() +" milliseconds");
			
			// Now, Agent must to sort the Plans in according to the Green Goal (We consider a green goal like a mandatory goal, but to avoid that the Agent
			// can block we relax the green goal using a "sorting method"
			Game.Print("I found paths: " + Paths.size());
			i = 0;
			LocalDateTime Current_Time = null;
			while (i< Beliefs.size())
			{
				TBelief_Base Temp_Belief = Beliefs.get(i);
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
			//LocalDateTime d = LocalDateTime.parse("2016-10-31 23:59:59", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
//			LocalDateTime Start_Time = Current_Time.plusHours(2);
//			LocalDateTime End_Time = Current_Time.plusHours(5);
			
			LocalDateTime Start_Time = Functional_Goal.Get_Finally_Start();
			LocalDateTime End_Time = Functional_Goal.Get_Finally_End();
			
			
			/////// I apply the Finally Operator
			
			Game.Print("I found paths Before Finally_Formula: " + Paths.size());
			Game.Print("I Apply the Finally Operator");
			Paths = Finally_Operator(Paths, Current_Time, Start_Time, End_Time );
			Game.Print("I found paths After Finally_Formula: " + Paths.size());

			i = 0;
			
			ArrayList<TOption> option_List =  new ArrayList<TOption>();
			ArrayList<TAction> plans = new ArrayList<TAction>();
			Boolean Print_PAth = true;
			Game.Print("Agent inserts actions to do (go to in some routes)");
			for (Plan path: Paths)
			{	
			//	if (Print_PAth)
			//	{
			//		Game.Print("******* Path: "+i);
			//		Game.Print("-PAth: "+i+"  -  "+path.Path_Time +"  +  "+path.Totale_Pesi);
			//		Game.Print(path.Lista_Tratte_numerate.size()+": "+path.Lista_Tratte_numerate);	
			//	}
			
				ArrayList<TAction> Actions = new ArrayList<TAction>();
				
				for (Integer A_Route: path.Routes )
				{
					//Now, Action stores only a Route at a time
					// An Action => A Route
					if(i==0)
					{
			//			Game.Print(path.Destinazioni);					
					}
					
					TAction An_Action = new TAction(null, null, null);
					An_Action.get_Params().add(A_Route);
					
					// I define "Use_Route" as a function to go from a departure to a destination station
					An_Action.set_Action_Name("Use_Route");
					Actions.add(An_Action);
				}
				
				TOption An_Option = new TOption(Actions, null, 0.0, path.Total_Weights);
				An_Option.Path.Copia_Plan(path);
				option_List.add(An_Option);
			
				i++;
			}
			
			Game.Print("Agent inserts options in the desire. Theese can be null");
			Desire.set_Option_List(option_List);
			//Game.Print("option_List size: "+option_List.size());
			i=0;
			if (option_List.size()>0)
			{
				
				//TOption The_Option = option_List.getFirst(); // this is 0 index, always
				Game.Print("Agent found at least an option and it can deliberate");
				//         ///////////FirsOp    Actions    Action[0]
				Game.Print(option_List.get(0).get_Plan_Actions().getFirst().get_Params());
//				TIntention Selected_Intention = new TIntention(Desire, 0);
				
				// Print Data PAth
				Game.Print("******************** Selected Path **********************: TReasoner_Function");
				Game.Print("********* PAth: "+i);
				Plan Path = option_List.get(0).Path;
				int Minute_of_Hours = Path.Path_Time.intValue();
				int Minutes = (int)((Path.Path_Time-Minute_of_Hours)*60) + Minute_of_Hours*60;
				LocalDateTime Temp_Time = Current_Time.plusMinutes(Minutes);
				
			//	Game.Print(i+" Path.Path_Time: "+Path.Path_Time +" - Path_Time.intValue(): "+Path.Path_Time.intValue() + " - Minute_of_Hours: " + Minute_of_Hours + " *** (Path.Path_Time-Minute_of_Hours)*60: "+(Path.Path_Time-Minute_of_Hours)*60);
			//	Game.Print(i+" Actual_Time: "+Current_Time+" ---- Path.Path_Time: "+Path.Path_Time +" - Path_Time.intValue(): "+Path.Path_Time.intValue() + " - Minute_of_Hours: " + Minute_of_Hours + " *** (Path.Path_Time-Minute_of_Hours)*60: "+(Path.Path_Time-Minute_of_Hours)*60);
			//	Game.Print(Start_Interval + " - " + Temp_Time + " *** "+Minutes+" *** " + End_Interval );
			//	Game.Print("Minutes Added to start: "+Minutes);
				Game.Print("Stations: "+Path.Destinations);
				Game.Print("Routes Numbers: "+Path.Routes);
				Game.Print("Agent Start Time: "+Current_Time+ " - Finally Operator Data: Start_Interval: "+Start_Time+ " - End_Interval: "+End_Time);
				Game.Print("Panorama average: "+Path.Total_Weights.get(TType_Quality_Goal.TGQ_Panorama));
				Game.Print("Locomotive average ( CO2/Step ): "+Path.Total_Weights.get(TType_Quality_Goal.TGQ_Locomotive));
				Game.Print("Speed average: "+Path.Total_Weights.get(TType_Quality_Goal.TGQ_Velocity));
				
				Game.Print("Path Time: "+Path.Path_Time+" - Arrive time: "+Temp_Time);
				
				Game.Print("Path Data:");
				Game.Print("----------------------------");
				for (int ind: Path.Routes)
				{
					Route rotta = this.Agent.Get_WMM().Get_Map().get_Route(ind);
					//Game.Print(New_Paths);
					Game.Print("Departure/Destination: "+ rotta.getDeparture()+"-"+rotta.getDestination()+
							" - Locomotive: "+rotta.getLocomotiva() + " - Panorama: "+rotta.getPanorama_()+
							" - Speed: "+rotta.getVelocita()+ " - N.pieces: "+rotta.getPieces_Number());
					Game.Print("Route Color: "+rotta.getColor());
					Game.Print("Route Number: "+rotta.Numero_Rotta + " - Route_Time: "+rotta.Get_Path_Time());
					Game.Print("Locomotive value: "+rotta.getLocomotiva().Get_Value(rotta.getLocomotiva()));
					Game.Print("Panorama value: "+rotta.getPanorama_().Get_Value(rotta.getPanorama_()));
					Game.Print("Speed value: "+rotta.getVelocita().Get_Value(rotta.getVelocita()));
					Game.Print("**********************");
				}


			}
			else //Agent cannot deliberate, it hasn't any option
			{
				// It means that agent cannot to go in its destination because it hasn't a plan to pursue
				// In this case the agent stop this goal and desire and continue to iterate its goals in
				// AM_Endogenous_Module

			}
			this.Agent.get_GW().Print_Data(1, 0);
	}
	catch (Exception e) {
      Game.Print("Something went wrongin method: Insert_New_Desires.");
      Game.Print("Message Error: "+e.getMessage());
      result = false;
    }
    return result;
}
	
	private Boolean Filtering_Process(ArrayList<TDesire> desires)
	{
		boolean result = true;
		try
		{
			this.Agent.get_GW().Print_Data(0, 0);
			for(TDesire Desire: desires)
			{
				TAttentional_Goal Goal = Desire.get_Attentional_Goal();
				TFunctional_Goal Functional_Goal = (TFunctional_Goal) Goal;
				TBelief_Base Functional_Belief = Functional_Goal.get_Final_State();
				
				Game.PrintLn();
				Game.Print("Now I apply Green and Quality Goal filters");
				Game.PrintLn();
				Game.Print("I apply Green filters");
				
				ArrayList<TOption> Filtered_Options = new ArrayList<TOption>();
				
				Instant start = Instant.now();
				/////// I apply Green Goal Filters
				Game.Print("Green Goal list: " +Functional_Goal.get_List_Green_Goal().size());
				if(Functional_Goal.get_List_Green_Goal().size()>0)
				{
					for(int i=0; i<Functional_Goal.get_List_Green_Goal().size(); i++)
					{
						Filtered_Options = Green_Filter(Desire.get_Option_List(), Functional_Goal.get_List_Green_Goal().get(i));
						Desire.set_Option_List(Filtered_Options);
					}
					
				}
				Game.Print("I found paths (options) After Green_Filter: " + Filtered_Options.size());
					
				///// I apply Quality Goal Filters
				Game.PrintLn();
				Game.Print("I apply Quality filters");
				Game.Print("Agent has A number of quality filter for this desire: "+Functional_Goal.get_List_Quality_Goal().size());
				if(Functional_Goal.get_List_Quality_Goal().size()>0)
				{
					for(int i=0; i<Functional_Goal.get_List_Quality_Goal().size(); i++)
					{
						Filtered_Options = Quality_Filter(Desire.get_Option_List(), Functional_Goal.get_List_Quality_Goal().get(i));
						Desire.set_Option_List(Filtered_Options);
					}
						
				}
				Game.Print("I found paths (options) After Quality_Filter: " + Filtered_Options.size());
				
				Instant end = Instant.now();
				Duration timeElapsed = Duration.between(start, end);
	
				Game.Print("I filtered and ordered found options ( "+Filtered_Options.size()+" )in Time: "+ 
				timeElapsed.toMillis() +" milliseconds");
			}
		
			this.Agent.get_GW().Update_Desire_with_Options(desires);
			this.Agent.get_GW().Print_Data(1, 0);
		}
		catch (Exception e) {
	      Game.Print("Something went wrongin method: Insert_New_Desires.");
	      Game.Print("Message Error: "+e.getMessage());
	      result = false;
	    }
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
		String Value_Green_Goal = (String) Green_Goal.get_Constraint().get_Object_Complement();
		
		Game.Print("Value_Green_Goal Value_Green_Goal Value_Green_Goal");
		switch (Value_Green_Goal)
		{
		case "slow":
			Collections.sort(Options, new TGreen_Compare());
			int start =0;
			int end =0;	
			//If the size list is > of 2 elements so I divide the list in 3 groups 
			//Otherwise, I get the first element,
			//	because "end" value is equal to 0 at this moment
			if(Options.size()>2)
			{
				end = (int) Options.size()/3;
			}
			int i=0;
			for(i=0;i<end;i++)
			{
				New_Options.add(Options.get(i));
			}
			Game.Print("I divided the list of elements. New size is: "+New_Options.size());
			break;			
		default:
			Game.Print("I cannot handle Green_Goal Complement_Objecy: "+Green_Goal.get_Constraint().get_Object_Complement());
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
				int start =0;
				int end =0;	
				//If the size list is > of 2 elements so I divide the list in 3 groups 
				//Otherwise, I get the first element,
				//	because "end" value is equal to 0 at this moment
				if(Options.size()>2)
				{
					end = (int) Options.size()/3;
				}
				int i=0;
				for(i=0;i<end;i++)
				{
					New_Options.add(Options.get(i));
				}
				Game.Print("I divided the list of elements. New size is: "+New_Options.size());
				break;			
			default:
				Game.Print("I cannot handle Quality_Goal Complement_Objecy: "+Quality_Goal.get_Constraint().get_Object_Complement());
			}

			
			break;
		case TType_Quality_Goal.TGQ_Velocity:
			
			break;
		default:
			Game.Print("I cannot handle Quality_Goal Type: "+Quality_Goal.get_Type_Quality_Goal());
			Game.End_Game();
			
		}
		return New_Options;
	}
	
	public Boolean Deliberate_from_Stimulus_Temporary_Closed_Route(TDesire Desire)
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
			An_Action.get_Params().add(A_Route);
							
			// I define "Use_Route" as a function to go from a departure to a destination station
	//		An_Action.set_Action_Name("Ask_for_Temporary_Closed_Route");
			An_Action.set_Action_Name("How long will the route be closed?");
			Actions.add(An_Action);
			TOption An_Option = new TOption(Actions, null, 0.0, null);
	
			option_List.add(An_Option);
			
			Desire.set_Option_List(option_List);
			Game.Print("option_List size: "+option_List.size()+ ", of course...");
			
			TIntention Selected_Intention = new TIntention(Desire, 0);
			ArrayList<TIntention> Intentions = new ArrayList<TIntention>();
			Intentions.add(Selected_Intention);
			
			Game.Print("Agent update its intentions");
			this.Agent.get_GW().Update_Intentions(Intentions);
			Game.Print("The Agent updated its intentions correctly");

//			Result = true;
//			return Result;
		}
		catch (Exception e) {
	      Game.Print("Something went wrongin method: Insert_New_Desires.");
	      Game.Print("Message Error: "+e.getMessage());
	      result = false;
	    }
	    return result;
	}
	


}
