import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * It represents the environment in which the autonomous train moves during the simulation.
 */
public class TEnvironment 
{
	// the even number route is the inserted ruote and odd number route is the back
		// route.
		private int Routes_Number;
		protected TMy_Collections My_Collection;

		public Map<TCity, List<TRoute>> Neighbors;
		public ArrayList<TRoute> All_Routes;

		public TEnvironment() {
			this.Routes_Number = -1;// So, the even number route is the inserted ruote and odd number route is the
									// back route.
			My_Collection = new TMy_Collections();

			Neighbors = new HashMap<>();
			All_Routes = new ArrayList<>();
		}

		/**
		 * This Methid clones the Map, with all its data
		 */
		public TEnvironment clone() {
			TEnvironment Cloned_Map = new TEnvironment();
			Cloned_Map.Routes_Number = this.Routes_Number;
			Cloned_Map.All_Routes.addAll(this.All_Routes);
			Cloned_Map.Neighbors.putAll(this.Neighbors);
			return Cloned_Map;

		}

		/**
		 * This Method insert two arch between two stations (Departure and Destination),
		 * any arch go in a different direction
		 * 
		 * @param Departure
		 * @param Destination
		 * @param Assigned_Color This parameter assigns any property information to
		 *                       routes
		 * @param Steps          The number of steps that make up any route
		 */
		public void Add_Arch(TCity Departure, TCity Destination, TColor Assigned_Color, int Steps) {
			TColor_Route Color_Route = My_Collection.Color_Ruotes.get(Assigned_Color);

//			for (int i = 0; i < Color_Route.Locomotives.size(); i++) 
			{
				int i = 0;
				this.Routes_Number++;// increase the number of outbound routes
				TRoute Outward_Route = new TRoute(Departure, Destination, Assigned_Color, Steps,
						Color_Route.Motors.get(i), Color_Route.Panorama, Color_Route.Speed,
						this.Routes_Number);

				this.Routes_Number++;// increase the number of return trips
				TRoute Reverse_Route = new TRoute(Destination, Departure, Assigned_Color, Steps,
						Color_Route.Motors.get(i), Color_Route.Panorama, Color_Route.Speed,
						this.Routes_Number);
				Neighbors.computeIfAbsent(Departure, k -> new ArrayList<>()).add(Outward_Route);
				Neighbors.computeIfAbsent(Destination, k -> new ArrayList<>()).add(Reverse_Route); // Non orientato
				All_Routes.add(Outward_Route);
				All_Routes.add(Reverse_Route);
			}
		}

		/**
		 * This Method finds all paths in the Environment (The Map) between two
		 * stations: Departure and Destination
		 * 
		 * @param Departure   Departure Station
		 * @param Destination Destination Station
		 * @return A list of Plan
		 */
		public ArrayList<TPlan_Simulation> Find_All_Paths(TCity Departure, TCity Destination) {
			Set<TCity> Visited_Stations = new HashSet<>();
			List<TCity> Current_Path = new ArrayList<>();
			List<List<TCity>> All_Paths = new ArrayList<>();
			List<Integer> Current_Route_Sections = new ArrayList<>();
			List<List<Integer>> All_Routes_Sections = new ArrayList<>();

			Dfs(Departure, Destination, Visited_Stations, Current_Path, All_Paths, Current_Route_Sections,
					All_Routes_Sections, -1);

			ArrayList<TPlan_Simulation> Plans = new ArrayList<>();
			int i = 0;
			for (i = 0; i < All_Routes_Sections.size(); i++) {
				List<TCity> Stations_Path = All_Paths.get(i);
				List<Integer> Routes_Path = All_Routes_Sections.get(i);

				EnumMap<TType_Quality_Desire, Double> Totale_Pesi = Compute_Sum_Weights_Routes(Routes_Path);
				Double Path_Time = Compute_Path_Time_Routes(Routes_Path);
				Plans.add(new TPlan_Simulation());
				Plans.getLast().Insert_Path_by_Routes(Stations_Path, Routes_Path, Totale_Pesi, Path_Time);
			}
			return Plans;
		}
		
		public ArrayList<TPlan_Simulation> Find_All_Paths_From_A_Damaged_Route(
				Integer Route_Number_Departure, Integer Step_Departure,
				TCity Destination_City) 
		{
			Set<TCity> Visited_Stations = new HashSet<>();
			List<TCity> Current_Path = new ArrayList<>();
			List<List<TCity>> All_Paths = new ArrayList<>();
			List<Integer> Current_Route_Sections = new ArrayList<>();
			List<List<Integer>> All_Routes_Sections = new ArrayList<>();

			TRoute Route_Departure = this.Get_Route( Route_Number_Departure );
			Integer Specular_Route = this.Get_Specular_Route(Routes_Number);
			TRoute Specular_Route_Departure = this.Get_Route( Specular_Route );
			 
			
			Dfs_From_A_Damaged_Route( Route_Departure, Destination_City, Visited_Stations, Current_Path, All_Paths, Current_Route_Sections,
					All_Routes_Sections, -1);
			
//			Integer Specular_Step_Departure = 
//					this.Get_Specular_Step_in_Route(Route_Number_Departure, Step_Departure);
//			Dfs_From_A_Damaged_Route( Specular_Route_Departure, Destination_City, Visited_Stations, Current_Path, All_Paths, Current_Route_Sections,
//					All_Routes_Sections, -1);

			ArrayList<TPlan_Simulation> Plans = new ArrayList<>();
			int i = 0;
			for (i = 0; i < All_Routes_Sections.size(); i++) {
				List<TCity> Stations_Path = All_Paths.get(i);
				List<Integer> Routes_Path = All_Routes_Sections.get(i);

				EnumMap<TType_Quality_Desire, Double> Totale_Pesi = Compute_Sum_Weights_Routes(Routes_Path);
				Double Path_Time = Compute_Path_Time_Routes(Routes_Path);
				Plans.add(new TPlan_Simulation());
				Plans.getLast().Insert_Path_by_Routes(Stations_Path, Routes_Path, Totale_Pesi, Path_Time);
			}
			return Plans;
		}

		/**
		 * Compute All Weights for a Route
		 * 
		 * @param Path A list of Station
		 * @return A record of Locomotive, Panorama and Speed average of all Routes of
		 *         the path
		 */
		protected EnumMap<TType_Quality_Desire, Double> Compute_Sum_Weights(List<TCity> Path) {
			EnumMap<TType_Quality_Desire, Double> Total_Weights = new EnumMap<>(TType_Quality_Desire.class);

			double Motor_Route = 0;
			double Panorama_Route = 0;
			double Speed__Route = 0;

			for (int i = 0; i < Path.size() - 1; i++) {
				TCity Station1 = Path.get(i);
				TCity Station2 = Path.get(i + 1);
				for (TRoute arch : Neighbors.get(Station1)) {
					if (arch.Get_Destination() == Station2) {
						;
						Motor_Route += arch.Get_Route_Locomotive();
						Panorama_Route += arch.Get_Route_Panorama();
						Speed__Route += arch.Get_Route_Speed();
						break;
					}
				}
			}
			Total_Weights.put(TType_Quality_Desire.Motor, Motor_Route);
			Total_Weights.put(TType_Quality_Desire.Panorama, Panorama_Route);
			Total_Weights.put(TType_Quality_Desire.Speed, Speed__Route);
			return Total_Weights;
		}

		/**
		 * Compute All Weights for a Route
		 * 
		 * @param Routes_Path A list of integer. Any integers represents a route
		 * @return A record of Locomotive, Panorama and Speed average of all Routes of
		 *         the path
		 */
		protected EnumMap<TType_Quality_Desire, Double> Compute_Sum_Weights_Routes(List<Integer> Routes_Path) {
			EnumMap<TType_Quality_Desire, Double> Weights = new EnumMap<>(TType_Quality_Desire.class);

			double Motor_Route = 0;
			double Panorama_Route = 0;
			double Speed_Route = 0;
			Double Path_Time = 0.0;
			TRoute route;
			int index;
			int Routes_Number = Routes_Path.size();

			for (int i = 0; i < Routes_Number; i++) {
				index = Routes_Path.get(i);
				route = this.All_Routes.get(index);
				Motor_Route += route.Get_Route_Locomotive();
				Panorama_Route += route.Get_Route_Panorama();
				Speed_Route += route.Get_Route_Speed();
				Path_Time += route.Get_Path_Time();
			}

			Weights.put(TType_Quality_Desire.Motor, Motor_Route / Routes_Number);
			Weights.put(TType_Quality_Desire.Panorama, Panorama_Route / Routes_Number);
			Weights.put(TType_Quality_Desire.Speed, Speed_Route / Routes_Number);

			route = null;
			return Weights;
		}

		/**
		 * Compute the average Path Time of a Path.
		 * 
		 * @param Routes_Path A list of integers. Any integer represents a route
		 * @return A double value, represents the average Path time of the Path
		 */
		protected Double Compute_Path_Time_Routes(List<Integer> Routes_Path) {
			Double Path_Time = 0.0;
			TRoute route;
			Integer Under_Maintenance_Time_to_Add = 0;

			for (int index : Routes_Path) 
			{
				route = this.All_Routes.get(index);
				Path_Time += route.Get_Path_Time();
				Under_Maintenance_Time_to_Add += route.Get_Duration_Closed();
			}
			route = null;
			return Path_Time + Under_Maintenance_Time_to_Add;
		}

		/**
		 * A recursive Depth-First Search Algorithm version for search a path between
		 * two Station.
		 * 
		 * @param Current_Node
		 * @param Destination
		 * @param Visited_Station
		 * @param Current_Path
		 * @param All_Paths
		 * @param Current_Path_Integers
		 * @param All_Paths_Integers
		 * @param Arch_Index
		 */
		protected void Dfs(TCity Current_Node, TCity Destination, Set<TCity> Visited_Station,
				List<TCity> Current_Path, List<List<TCity>> All_Paths, List<Integer> Current_Path_Integers,
				List<List<Integer>> All_Paths_Integers, Integer Arch_Index) {
			Visited_Station.add(Current_Node);
			Current_Path.add(Current_Node);
			if (Arch_Index > -1) {
				Current_Path_Integers.add(Arch_Index);
			}

			if (Current_Node == Destination) {
				All_Paths.add(new ArrayList<>(Current_Path));
				All_Paths_Integers.add(new ArrayList<>(Current_Path_Integers));
			} else {
				for (TRoute arc : Neighbors.getOrDefault(Current_Node, Collections.emptyList())) {
					if (!Visited_Station.contains(arc.Get_Destination())) 
					{
						if ((arc.Get_Route_Status().equals("Green")) || (arc.Get_Route_Status().equals("Under_maintenance")))
//						if(arc.Get_Duration_Closed() == 0)
						{
							Arch_Index = arc.Route_Number;
							Dfs(arc.Get_Destination(), Destination, Visited_Station, Current_Path, All_Paths,
									Current_Path_Integers, All_Paths_Integers, Arch_Index);
							if (Arch_Index > -1) 
							{
								Current_Path_Integers.remove(Arch_Index);
							}
						}
					}
				}
			}

			Current_Path.remove(Current_Path.size() - 1);
			Visited_Station.remove(Current_Node);
		}
		
		protected void Dfs_From_A_Damaged_Route(
//				TCity Current_Node, TCity Destination,
				TRoute arc, TCity Destination,
				Set<TCity> Visited_Station,
				List<TCity> Current_Path, List<List<TCity>> All_Paths, List<Integer> Current_Path_Integers,
				List<List<Integer>> All_Paths_Integers, Integer Arch_Index) {
			TCity Current_Node = arc.Get_Departure();
//			TCity Destination = arc.Get_Destination();
			Visited_Station.add(Current_Node);
			Current_Path.add(Current_Node);
			if (Arch_Index > -1) {
				Current_Path_Integers.add(Arch_Index);
			}

//			if (Current_Node == Destination) {
//				All_Paths.add(new ArrayList<>(Current_Path));
//				All_Paths_Integers.add(new ArrayList<>(Current_Path_Integers));
//			} else 
			{
//				for (TRoute arc : Neighbors.getOrDefault(Current_Node, Collections.emptyList())) 
				{
					if (!Visited_Station.contains(arc.Get_Destination())) 
					{
						if ((arc.Get_Route_Status().equals("Green")) || (arc.Get_Route_Status().equals("Under_maintenance")))
//						if(arc.Get_Duration_Closed() == 0)
						{
							Arch_Index = arc.Route_Number;
							Dfs(arc.Get_Destination(), Destination, Visited_Station, Current_Path, All_Paths,
									Current_Path_Integers, All_Paths_Integers, Arch_Index);
							if (Arch_Index > -1) 
							{
								Current_Path_Integers.remove(Arch_Index);
							}
						}
					}
				}
			}

			Current_Path.remove(Current_Path.size() - 1);
			Visited_Station.remove(Current_Node);
		}

		/**
		 * Get Routes all routes starting from a Departure Station to a Destination Station
		 * 
		 * @param Departure		The Departure Station
		 * @param Destination	The Destination Station
		 * @return 				A List of routes
		 */
		public ArrayList<TRoute> Get_Routes(TCity Departure, TCity Destination) {
			ArrayList<TRoute> Routes = new ArrayList<TRoute>();

			if (Departure != Destination) {
				List<TRoute> Neighbors_Station = Neighbors.getOrDefault(Departure, Collections.emptyList());
				for (TRoute route : Neighbors_Station) {
					if (route.Get_Destination() == Destination) {
						Routes.add(route);
					}
				}
			}

			return Routes;
		}

		/**
		 * Get a route from Map
		 * @param index		An integer, represents the Route number index in Map 
		 * @return			The route
		 */
		public TRoute Get_Route(Integer index) {
			TRoute route = null;
			if (index < this.All_Routes.size()) {
				route = this.All_Routes.get(index);
			}

			return route;
		}
		/**
		 * Get a list represents all Neighbor Stations to a specific Station 
		 * @param A_Station		A specific Station  
		 * @return				A list of Stations
		 */
		public List<TCity> Get_Neighbor_Stations(TCity A_Station) {
			List<TCity> Neighbor_Stations = new ArrayList<>();

			List<TRoute> All_Neighbors = Neighbors.getOrDefault(A_Station, Collections.emptyList());

			for (TRoute route : All_Neighbors) {
				Neighbor_Stations.add(route.Get_Destination());
			}
			return Neighbor_Stations;
		}

		/**
		 * Get a list of routes linked to A specific Station 
		 * @param A_Station		A specific Station  
		 * @return				A list of routes
		 */
		public List<TRoute> Get_Routes_Nearby_Cities(TCity A_Station) {
			List<TRoute> Neighbor_Stations = Neighbors.getOrDefault(A_Station, Collections.emptyList());
			return Neighbor_Stations;
		}

		// this function return the routes number, in according to the index of route,
		// and it is "minus 1" to size of all routes list
		// because Numero_Tratte starts by "-1"
		/**
		 *  this function return the routes number, in according to the index of route, 
		 *  and it is "minus 1" to size of all routes list because Numero_Tratte starts by "-1"
		 * @return 	An integer
		 */
		public Integer Get_Routes_Number() {
			return Routes_Number;
		}

		/**
		 * Get Back  Route to a specific Route. 
		 * 
		 * @param A_Route	A specific route
		 * @return			An integer
		 */
		public int Get_Specular_Route(int A_Route) {
			int Specular_Integer_Route = 0;
			//if A_Route = -1 it means the agent is in a city, so Specular_Integer_Route must to be = -1
			if (A_Route<0)
			{
				Specular_Integer_Route = A_Route;
			}
			else
			{
				if (A_Route % 2 == 0) {
					Specular_Integer_Route = A_Route + 1;
		
				} else {
					Specular_Integer_Route = A_Route - 1;
				}
			}
			return Specular_Integer_Route;
		}
		
		public int Get_Specular_Step_in_Route(int A_Route, int A_Step)
		{
			int Specular_Step_in_Route = -1;
			int Specular_Route = this.Get_Specular_Route(A_Route);
			//If Agent is already in city, Specular_Step_in_Route must to be = 0
			if(Specular_Route == -1)
			{
				Specular_Step_in_Route = 0;
			}
			else
			{
				TRoute route = this.All_Routes.get(Specular_Route);
				int steps = route.Get_Steps_Number();
				Specular_Step_in_Route = steps - A_Step + 1;
			}
			
			return Specular_Step_in_Route;
		}

}
