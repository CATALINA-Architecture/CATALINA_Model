package Cara_Simulation;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;


public class TFile_Manager {
	
	int Init_Creation_Trigger_Condition_Functional_Predicates = 1;
	int Init_Creation_Green_Goals_Predicates = 4;
	int Init_Creation_Quality_Goals_Predicates = 5;
	int Init_Creation_Visited_Station_Predicates = 6;
	int Initial_Number_Green_Goals;
	int Initial_Number_Quality_Goals;
	int Init_Creation_Path_Travelled_for_Functional_Goal;
	
	private Environment Map;
	
	public void Set_Map(Environment map)
	{
		if(this.Map == null)
		{
			this.Map = map.clone();
		}
	}
	
	public void Write_Predicates(ArrayList<TPredicate> Predicates)
	{
		String File_name = "Predicates.cara";
		//ArrayList<TPredicate> Predicates
		try (FileOutputStream fileOut = new FileOutputStream(File_name);
		     ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
		    //out.writeObject(book);
			out.writeObject(Predicates);
		} catch (IOException e) {
		    e.printStackTrace();
		}
	}
	
	public void Write_Beliefs(ArrayList<TBelief_Base> Beliefs)
	{
		String File_name = "Beliefs.cara";

		try (FileOutputStream fileOut = new FileOutputStream(File_name);
		     ObjectOutputStream out = new ObjectOutputStream(fileOut)) {

			out.writeObject(Beliefs);
			
		} catch (IOException e) {
		    e.printStackTrace();
		}
	}
	
	public void Write_Epistemic_Goals(ArrayList<TEpistemic_Goal> Epistemic_Goals)
	{
		String File_name = "Epistemic_Goals.cara";

		try (FileOutputStream fileOut = new FileOutputStream(File_name);
		     ObjectOutputStream out = new ObjectOutputStream(fileOut)) {

			out.writeObject(Epistemic_Goals);
			
		} catch (IOException e) {
		    e.printStackTrace();
		}
	}
	
	public void Write_Functional_Goals(ArrayList<TFunctional_Goal> Functional_Goals)
	{
		String File_name = "Functional_Goals.cara";

		try (FileOutputStream fileOut = new FileOutputStream(File_name);
		     ObjectOutputStream out = new ObjectOutputStream(fileOut)) {

			out.writeObject(Functional_Goals);
			
		} catch (IOException e) {
		    e.printStackTrace();
		}
	}
	
	public void Write_Green_Goals(ArrayList<TGreen_Goal> Green_Goals)
	{
		String File_name = "Green_Goals.cara";

		try (FileOutputStream fileOut = new FileOutputStream(File_name);
		     ObjectOutputStream out = new ObjectOutputStream(fileOut)) {

			out.writeObject(Green_Goals);
			
		} catch (IOException e) {
		    e.printStackTrace();
		}
	}
	
	public void Write_Quality_Goals(ArrayList<TQuality_Goal> Quality_Goals)
	{
		String File_name = "Quality_Goals.cara";

		try (FileOutputStream fileOut = new FileOutputStream(File_name);
		     ObjectOutputStream out = new ObjectOutputStream(fileOut)) {

			out.writeObject(Quality_Goals);
			
		} catch (IOException e) {
		    e.printStackTrace();
		}
	}
	
	
	
	
	//Read Methods
	
	public ArrayList<TPredicate> Read_Predicates()
	{
		String File_name = "Predicates.cara";
		ArrayList<TPredicate> Predicates = null;
		try (FileInputStream fileIn = new FileInputStream(File_name);
			     ObjectInputStream in = new ObjectInputStream(fileIn)) {
			Predicates = (ArrayList<TPredicate>) in.readObject();

			} catch (IOException | ClassNotFoundException e) {
			    e.printStackTrace();
			}
//		Game.Print("I print all predicates:");
//		for(TPredicate Predicate: Predicates)
//		{
//			Game.Print(Predicate.get_Name()+"  ---  ");
//		}

		return Predicates;
	}
	
	public ArrayList<TBelief_Base> Read_Beliefs()
	{
		String File_name = "Beliefs.cara";
		ArrayList<TBelief_Base> Beliefs = null;
		try (FileInputStream fileIn = new FileInputStream(File_name);
			     ObjectInputStream in = new ObjectInputStream(fileIn)) {
			Beliefs = (ArrayList<TBelief_Base>) in.readObject();

			} catch (IOException | ClassNotFoundException e) {
			    e.printStackTrace();
			}
//		Game.Print("I print all beliefs:");
//		for(TBelief_Base Belief: Beliefs)
////		TBelief_Base Belief = null;
////		for(int i=0; i<Beliefs.size();i++)
//		{
////			Belief = Beliefs.get(i);
////			Game.Print(Belief.get_Name()+"  ---  "+Belief.get_Predicate().get_Relationship()+"  ---  "+Belief.get_Type_Belief());
//			Game.Print(Belief.get_Name()+"  ---  "+Belief.get_Type_Belief());
////			i++;
//		}
		return Beliefs;
	}
	
	public ArrayList<TEpistemic_Goal> Read_Epistemic_Goals()
	{
		String File_name = "Epistemic_Goals.cara";
		ArrayList<TEpistemic_Goal> Epistemic_Goals = null;
		try (FileInputStream fileIn = new FileInputStream(File_name);
			     ObjectInputStream in = new ObjectInputStream(fileIn)) {
			Epistemic_Goals = (ArrayList<TEpistemic_Goal>) in.readObject();

			} catch (IOException | ClassNotFoundException e) {
			    e.printStackTrace();
			}
		return Epistemic_Goals;
	}
	
	public ArrayList<TFunctional_Goal> Read_Functional_Goals()
	{
		String File_name = "Functional_Goals.cara";
		ArrayList<TFunctional_Goal> Functional_Goals = null;
		try (FileInputStream fileIn = new FileInputStream(File_name);
			     ObjectInputStream in = new ObjectInputStream(fileIn)) {
			Functional_Goals = (ArrayList<TFunctional_Goal>) in.readObject();

			} catch (IOException | ClassNotFoundException e) {
			    e.printStackTrace();
			}
		return Functional_Goals;
	}
	
	public ArrayList<TGreen_Goal> Read_Green_Goals()
	{ 
		String File_name = "Green_Goals.cara";
		ArrayList<TGreen_Goal> Green_Goals = null;
		try (FileInputStream fileIn = new FileInputStream(File_name);
			     ObjectInputStream in = new ObjectInputStream(fileIn)) {
			Green_Goals = (ArrayList<TGreen_Goal>) in.readObject();

			} catch (IOException | ClassNotFoundException e) {
			    e.printStackTrace();
			}
		return Green_Goals;
	}
	
	public ArrayList<TQuality_Goal> Read_Quality_Goals()
	{
		String File_name = "Quality_Goals.cara";
		ArrayList<TQuality_Goal> Quality_Goals = null;
		try (FileInputStream fileIn = new FileInputStream(File_name);
			     ObjectInputStream in = new ObjectInputStream(fileIn)) {
			Quality_Goals = (ArrayList<TQuality_Goal>) in.readObject();

			} catch (IOException | ClassNotFoundException e) {
			    e.printStackTrace();
			}
		return Quality_Goals;
	}
	
	
	//Create Write Methods
	
	//Gets all predicates from file and normalize its data
	public void Create_Predicates()
	{
		ArrayList<TPredicate> Predicates = new ArrayList<TPredicate>();
		
		/////////////////
		//Create Current Time predicate
		/////////////////
//		LocalDateTime ldt= LocalDateTime.now().withNano(0);
		//LocalDateTime d = LocalDateTime.parse("  2016-10-31 23:59:59", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		LocalDateTime ldt = LocalDateTime.parse("2024-10-01 16:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		
		Predicates.add(new TPredicate( "p0", TType_Subject.Time,TType_Relationship.is, 
				ldt));
		Game.Print("Game starts at: "+ldt);
		
		/////////////////
		//Create Destionation predicates: 1-3
		/////////////////
		Predicates.add(new TPredicate( "p1", Station.Paris,TType_Relationship.visited_by, 
				TType_Object_Complement.Me));
		Predicates.add(new TPredicate( "p2", Station.Wien,TType_Relationship.visited_by, 
				TType_Object_Complement.Me));
		Predicates.add(new TPredicate( "p3", Station.Roma,TType_Relationship.visited_by, 
				TType_Object_Complement.Me));

		/////////////////
		//Create Trigger Condition Destionation predicates: 1-3
		/////////////////
		this.Init_Creation_Trigger_Condition_Functional_Predicates = Predicates.size();
		Predicates.add(new TPredicate( "p4", Station.Paris,TType_Relationship.visited_by, 
				TType_Object_Complement.Me));	
		
		/////////////////
		//Create Green predicates: Starting by 5
		/////////////////		
		this.Init_Creation_Green_Goals_Predicates = Predicates.size();//5;
		this.Initial_Number_Green_Goals = 1;
		int i = this.Init_Creation_Green_Goals_Predicates;
		Predicates.add(new TPredicate( "p"+i, TType_Green_Goal.TGG_Locomotive,TType_Relationship.minor_equal, 
				0.8));
		
		/////////////////
		//Create Quality predicates: Starting by 6
		/////////////////
		this.Init_Creation_Quality_Goals_Predicates = Init_Creation_Green_Goals_Predicates +
														Initial_Number_Green_Goals;
		this.Initial_Number_Quality_Goals = 1;
		i = this.Init_Creation_Quality_Goals_Predicates;
		Predicates.add(new TPredicate( "p"+i, TType_Quality_Goal.TGQ_Panorama,TType_Relationship.is, 
				"great"));
		
		
		/////////////////
		//Create visited station predicates: 4- length of station = n
		/////////////////
		this.Init_Creation_Visited_Station_Predicates = Init_Creation_Quality_Goals_Predicates +
														Initial_Number_Quality_Goals;
		i = this.Init_Creation_Visited_Station_Predicates;
		for(Station station: Station.values())
		{
			Predicates.add(new 
					TPredicate( "p"+i, station, TType_Relationship.visited_by, 
							TType_Object_Complement.Me));
			i++;
		}
		
		/////////////////
		//Create Current Station, Route and step predicates: n-n+3
		/////////////////
		//Station
		Predicates.add(new 
				TPredicate( "p"+i++, TType_Subject.Me, TType_Relationship.is_in, 
						Station.Lisboa));
		//Route
		//Route is -1 when Agent is in a station, otherwise the value is > 0 when Agent is in Route
		Predicates.add(new 
				TPredicate( "p"+i++, TType_Subject.Me, TType_Relationship.is_in, 
						-1));
		//Step
		//Step is 0 when Agent is in a station, otherwise the value is > 0 when Agent is in Route 
		Predicates.add(new 
				TPredicate( "p"+i++, TType_Subject.Me, TType_Relationship.is_in, 
						0));
		
		/////////////////
		//Create Belief_Temporary_Closed_Route, Belief_Closed_Route, Belief_Busy_Route	
		/////////////////
		ArrayList<Station> Stations = new ArrayList<Station>();
		ArrayList<Integer> Temp_Stations = new ArrayList<Integer>(Collections.nCopies(Station.values().length, 0));
		Predicates.add(new 
				TPredicate( "p"+i++, Stations, TType_Relationship.is_Busy, Temp_Stations));
		Predicates.add(new 
				TPredicate( "p"+i++, Stations.clone(), TType_Relationship.is_Closed, null));
		Predicates.add(new 
				TPredicate( "p"+i++, Stations.clone(), TType_Relationship.is_Temporary_Closed, Temp_Stations.clone()));
		
		/////////////////
		//Create Belief_Number_Players,	
		/////////////////
		Predicates.add(new 
				TPredicate( "p"+i++, "Number Players", TType_Relationship.is, 0));
		
		/////////////////
		//Create Stimulus_Beliefs,	
		/////////////////
		Predicates.add(new 
				TPredicate( "p"+i++, TType_Subject.Me, TType_Relationship.is_in, 0));
		Predicates.add(new 
				TPredicate( "p"+i++, TType_Subject.Me, TType_Relationship.is_Too_Close_To_The_Train, 0));
		Predicates.add(new 
				TPredicate( "p"+i++, -1, TType_Relationship.is_Closed, null));
		Predicates.add(new 
				TPredicate( "p"+i++, -1, TType_Relationship.is_Busy, new ArrayList<Integer>()));
		Predicates.add(new 
				TPredicate( "p"+i++, -1, TType_Relationship.is_Temporary_Closed, Temp_Stations));
		Predicates.add(new 
				TPredicate( "p"+i++, -1, TType_Relationship.is_Crowded, 0));
		//Stimulus_Status_Route
		Predicates.add(new 
				TPredicate( "p"+i++, -1, TType_Relationship.is, TType_Route_Status.Unknown));
		
		
		
		/////////////////
		//Create Predicates for Beliefs: Status_Route (one for any route)	
		/////////////////
		for(Route route: Map.All_Routes)
		{
			Predicates.add(new 
					TPredicate( "p"+i, route.Get_Numered_Route(), TType_Relationship.is, 
							TType_Route_Status.Unknown));
			i++;
		}
		
		this.Write_Predicates(Predicates);
	}
	
	public void Create_Beliefs()
	{
		ArrayList<TBelief_Base> Beliefs = new ArrayList<TBelief_Base>();
		ArrayList<TBelief_Base> Leggere_Beliefs = null;
		;
		
		/////////////////
		//Create Current Time predicate
		/////////////////
		Beliefs.add(new TBelief_Base("b0", "p0", true, TType_Object_Complement.Developer, 
				null, TType_Beliefs.Belief_Current_Time));
		
		/////////////////
		//Create Destionation Station: 1-3
		/////////////////
		Beliefs.add(new TBelief_Base("b1", "p1", true, TType_Object_Complement.Developer, 
				null, TType_Beliefs.Belief_Destination_Station));
		Beliefs.add(new TBelief_Base("b2", "p2", true, TType_Object_Complement.Developer, 
				null, TType_Beliefs.Belief_Destination_Station));
		Beliefs.add(new TBelief_Base("b3", "p3", true, TType_Object_Complement.Developer, 
				null, TType_Beliefs.Belief_Destination_Station));
		
		
//		/////////////////
//		//Create Trigger Condition Destionation 1
//		/////////////////
//		this.Init_Creation_Trigger_Condition_Functional_Predicates = Predicates.size();
//		Beliefs.add(new TBelief_Base("b3", "p4", true, TType_Object_Complement.Developer, 
//				null, TType_Beliefs.Belief_Destination_Station));
//		
//		Predicates.add(new TPredicate( "p4", Station.Paris,TType_Relationship.visited_by, 
//				TType_Object_Complement.Me));

		/////////////////
		//Create visited station Beliefs: 4- length of station = n
		/////////////////
//		this.Init_Creation_Visited_Station_Predicates = Beliefs.size();
		int i = this.Init_Creation_Visited_Station_Predicates;
		for(Station station: Station.values())
		{

			Beliefs.add(new TBelief_Base("b"+i, "p"+i, false, TType_Object_Complement.Developer, 
					null, TType_Beliefs.Belief_Visited_Station));
			i++;
		}
		
		
		/////////////////
		//Create Current Station, Route and step predicates: n-n+3
		/////////////////
		//Station
		Beliefs.add(new TBelief_Base("b"+i, "p"+i, false, TType_Object_Complement.Developer, 
				null, TType_Beliefs.Belief_Current_Station));
		i++;
		//Route
		Beliefs.add(new TBelief_Base("b"+i, "p"+i, false, TType_Object_Complement.Developer, 
				null, TType_Beliefs.Belief_Current_Route));
		i++;
		//Step
		Beliefs.add(new TBelief_Base("b"+i, "p"+i, false, TType_Object_Complement.Developer, 
				null, TType_Beliefs.Belief_Current_Step));
		i++;
		
		/////////////////
		//Create Belief_Temporary_Closed_Route, Belief_Closed_Route, Belief_Busy_Route	
		/////////////////
		Beliefs.add(new TBelief_Base("b"+i, "p"+i, false, TType_Object_Complement.Developer, 
				null, TType_Beliefs.Belief_Busy_Route));
		i++;
		Beliefs.add(new TBelief_Base("b"+i, "p"+i, false, TType_Object_Complement.Developer, 
				null, TType_Beliefs.Belief_Closed_Route));
		i++;
		Beliefs.add(new TBelief_Base("b"+i, "p"+i, false, TType_Object_Complement.Developer, 
				null, TType_Beliefs.Belief_Temporary_Closed_Route));
		i++;
		
		/////////////////
		//Create Belief_Number_Players,	
		/////////////////
		Beliefs.add(new TBelief_Base("b"+i, "p"+i, false, TType_Object_Complement.Developer, 
				null, TType_Beliefs.Belief_Number_Players));
		i++;
		
		/////////////////
		//Create Stimulus_Beliefs,	
		/////////////////
//		TSalient_Belief bbb = new TSalient_Belief("b"+i, "p"+i, 0.0, false, TType_Object_Complement.Developer, 
//				null, TType_Beliefs.Stimulus_Too_Close_To_The_Train);

		Beliefs.add(new TSalient_Belief("b"+i, "p"+i, 0.0, false, TType_Object_Complement.Developer, 
				null, TType_Beliefs.Stimulus_Ok_Correct_Movement));
		i++;
		
		Beliefs.add(new TSalient_Belief("b"+i, "p"+i, 0.0, false, TType_Object_Complement.Developer, 
				null, TType_Beliefs.Stimulus_Too_Close_To_The_Train));
		i++;
//		
		Beliefs.add(new TSalient_Belief("b"+i, "p"+i, 0.0, false, TType_Object_Complement.Developer, 
				null, TType_Beliefs.Stimulus_Closed_Route));
		i++;		
		
		Beliefs.add(new TSalient_Belief("b"+i, "p"+i, 0.0, false, TType_Object_Complement.Developer, 
				null, TType_Beliefs.Stimulus_Busy_Route));
		i++;		
		
		Beliefs.add(new TSalient_Belief("b"+i, "p"+i, 0.0, false, TType_Object_Complement.Developer, 
				null, TType_Beliefs.Stimulus_Temporary_Closed_Route));
		i++;		
		
		Beliefs.add(new TSalient_Belief("b"+i, "p"+i, 0.0, false, TType_Object_Complement.Developer, 
				null, TType_Beliefs.Stimulus_Crowded_Route));
		i++;
		Beliefs.add(new TSalient_Belief("b"+i, "p"+i, 0.0, false, TType_Object_Complement.Developer, 
				null, TType_Beliefs.Stimulus_Route_Status));
		i++;
		
		/////////////////
		//Create Predicates for Status_Route (one for any route)	
		/////////////////
		for(Route route: Map.All_Routes)
		{
			Beliefs.add(new TBelief_Base("b"+i, "p"+i, false, TType_Object_Complement.Developer, 
					null, TType_Beliefs.Belief_Route_Status));
			i++;
		}

		this.Init_Creation_Path_Travelled_for_Functional_Goal = i;
		this.Write_Beliefs(Beliefs);
	}
	
	public void Create_Epistemic_Goals()
	{
		ArrayList<TEpistemic_Goal> Epistemic_Goals = new ArrayList<TEpistemic_Goal>();
		ArrayList<TEpistemic_Goal> Leggere_Beliefs = null;
		
		ArrayList<String> Green_Goals_List = new ArrayList<String>();
		ArrayList<String> Quality_Goals_List = new ArrayList<String>();
		
//		Green_Goals_List.add("gg2");
		Green_Goals_List.add("gg1");
		
//		Quality_Goals_List.add("qg2");
//		Quality_Goals_List.add("qg1");
		
		
		TEpistemic_Goal epi1 = new TEpistemic_Goal("g3", "b1", 1.0, 2.0, 3.0, Green_Goals_List, 
				Quality_Goals_List);
		TEpistemic_Goal epi2 = new TEpistemic_Goal("g4", "b2", 1.0, 2.0, 3.0, Green_Goals_List, 
				Quality_Goals_List);
		
		Epistemic_Goals.add(epi1);
		Epistemic_Goals.add(epi2);
		//Todo create the Write_Epistemic_Goals
		this.Write_Epistemic_Goals(Epistemic_Goals);
	}
	
	public void Create_Functional_Goals()
	{
		ArrayList<TFunctional_Goal> Functional_Goals = new ArrayList<TFunctional_Goal>();
		ArrayList<TEpistemic_Goal> Leggere_Epistemic_Goals = null;
		
		ArrayList<String> Green_Goals_List = new ArrayList<String>();
		ArrayList<String> Quality_Goals_List = new ArrayList<String>();
		
		Green_Goals_List.add("gg1");
//		Green_Goals_List.add("gg2");
		
		Quality_Goals_List.add("qg1");
//		Quality_Goals_List.add("qg2");
		
		//Irrelevant_Time is a irrelevant time because it is inserted for Global and Until Operator.
		//These operators aren't developed in this project
		LocalDateTime Irrelevant_Time = LocalDateTime.parse("2024-10-01 21:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		
		LocalDateTime Fs1 = LocalDateTime.parse("2024-10-01 18:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		LocalDateTime Fe1 = LocalDateTime.parse("2024-10-01 22:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		Functional_Goals.add(new TFunctional_Goal("Visit_Paris", "b1", null,  0.5, 40.0, 0.1, Green_Goals_List, Quality_Goals_List,
				Fs1, Fe1, Irrelevant_Time, Irrelevant_Time, Irrelevant_Time, Irrelevant_Time));
		
		LocalDateTime Fs2 = LocalDateTime.parse("2024-10-01 10:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		LocalDateTime Fe2 = LocalDateTime.parse("2024-10-01 15:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		Functional_Goals.add(new TFunctional_Goal("Visit_Rome", "b2", "p4",  0.8, 40.0, 0.1, Green_Goals_List, Quality_Goals_List,
				Fs2, Fe2, Irrelevant_Time, Irrelevant_Time, Irrelevant_Time, Irrelevant_Time));
		
		LocalDateTime Fs3 = LocalDateTime.parse("2024-10-01 21:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		LocalDateTime Fe3 = LocalDateTime.parse("2024-10-01 23:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		Functional_Goals.add(new TFunctional_Goal("Visit_Frankfurt", "b3", null,  0.4, 40.0, 0.1, Green_Goals_List, Quality_Goals_List,
				Fs3, Fe3, Irrelevant_Time, Irrelevant_Time, Irrelevant_Time, Irrelevant_Time));
		
		//TFunctional_Goal func1 = new TFunctional_Goal("g1", "b1", null,  0.5, 40.0, 0.1, Green_Goals_List, Quality_Goals_List);
		//TFunctional_Goal func2 = new TFunctional_Goal("g2", "b2", "p1", 21.0, 22.0, 23.0, Green_Goals_List, Quality_Goals_List);
		
//		Functional_Goals.add(func1);
//		Functional_Goals.add(func2);
		
		this.Write_Functional_Goals(Functional_Goals);
	}
	
	public void Create_Green_Goals()
	{
		ArrayList<TGreen_Goal> Green_Goals = new ArrayList<TGreen_Goal>();
		Green_Goals.add(new TGreen_Goal("gg1", "p5", TType_Green_Goal.TGG_Locomotive, 0.0, 60.0, 150.0));
		
		this.Write_Green_Goals(Green_Goals);
		
	}
	
	public void Create_Quality_Goals()
	{
		ArrayList<TQuality_Goal> Quality_Goals = new ArrayList<TQuality_Goal>();
		
		TQuality_Goal Quality_Goal1 = new TQuality_Goal("qg1", "p6", TType_Quality_Goal.TGQ_Panorama, 
				0.4, 0.0, 0.3);
//		TQuality_Goal Quality_Goal2 = new TQuality_Goal("qg2", "p2", TType_Quality_Goal.TGQ_Velocity, 
//				0.11, 0.21, 0.31);
		
		Quality_Goals.add(Quality_Goal1);
//		Quality_Goals.add(Quality_Goal2);
		
		this.Write_Quality_Goals(Quality_Goals);
		
	}

}
