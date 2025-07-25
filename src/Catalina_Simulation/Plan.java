package Catalina_Simulation;

import java.util.ArrayList;
import java.util.EnumMap; 
import java.util.List;

public class Plan {
	
	 public ArrayList<City> Destinations;
	 public EnumMap<TType_Quality_Desire, Double> Total_Weights = new EnumMap<>(TType_Quality_Desire.class); 
	 public ArrayList<Integer> Routes;
	 public Double Path_Time;
	 public TTriple_Object Next_Position;

	 public Plan()
	 {
		 this.Destinations = new ArrayList<City>();
		 this.Routes = new ArrayList<Integer>();
		 this.Path_Time = 0.0;
		 this.Next_Position = new TTriple_Object();
	 }
	 /**
	  * It inserts a Path by Routes
	  * @param Destionations 	List of Destinations
	  * @param Numbered_Route	List of numered routes
	  * @param Weights			Weights of the path
	  * @param path_Time		Path Time
	  */
	 public void Insert_Path_by_Routes( List<City> Destionations, List<Integer> Numbered_Route, 
			 EnumMap<TType_Quality_Desire, Double> Weights, Double path_Time)
	 {
		 this.Destinations.addAll(Destionations);
		 this.Routes.addAll(Numbered_Route);
		 this.Total_Weights.putAll(Weights);
		 this.Path_Time = path_Time;
	 }
	 

	 /**
	  * It inserts a Path by Destination Stations
	  * @param Routes			List of Destinations
	  * @param Numbered_Destinations	List of numered routes
	  * @param Weights						Weights of the path
	  * @param path_Time				Path Time
	  */
	 public void Insert_Path_by_Stations( List<Route> Routes, List<Integer> Numbered_Destinations, 
			 EnumMap<TType_Quality_Desire, Double> Weights, Double path_Time)
	 {
		 List<City> Destinations = new ArrayList<City>();
		 if (Numbered_Destinations.size()>0)
		 {
			 int i=0;
			 Destinations.add(Routes.get(0).Get_Departure());
			 for (i=0;i<Numbered_Destinations.size();i++)
			 {
				 Destinations.add(Routes.get(i).Get_Destination());
			 }
		 }
		 
		 this.Routes.addAll(Numbered_Destinations);
		 this.Destinations.addAll(Destinations);
		 this.Total_Weights.putAll(Weights);
		 this.Path_Time = path_Time;
	 }
	 
	 /**
	  * It copies a plan path  
	  * @param Plan_Path
	  */
	 public void Copy_Plan(Plan Plan_Path)
	 {
		 this.Routes.addAll(Plan_Path.Routes);
		 this.Destinations.addAll(Plan_Path.Destinations);
		 this.Total_Weights.putAll(Plan_Path.Total_Weights);
		 this.Path_Time = Plan_Path.Path_Time;
	 }
	 
	 /**
	  * It returns next position to go in plan Path
	  * @return		A TTriple_Object object
	  */
	 public TTriple_Object Get_Next_Position()
	 {
		 return this.Next_Position;				 
	 }
	 
	 /**
	  * It updates next position in plan path
	  * @param next_Position
	  */
	 public void Set_Next_Position(TTriple_Object next_Position)
	 {
		 this.Next_Position.Set_Object_First(next_Position.Get_Object_First());
		 this.Next_Position.Set_Object_Second(next_Position.Get_Object_Second());
		 this.Next_Position.Set_Object_Third(next_Position.Get_Object_Third());
	 }
	 
	 public ArrayList<Integer> Get_Routes()
	 {
		 return this.Routes;
	 }
}