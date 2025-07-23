package Catalina_Simulation;

public class TPosition_Train_Coords {
	//If agent is in a station => station = a station, Route = -1, step = 0
	//If agent is in a station => station = a station, Route => 0 , step => 0
	private City The_City;
	private Integer Route;
	private Integer Step;
	
	public TPosition_Train_Coords(City city, Integer route, Integer step)
	{
		this.The_City = city;
		this.Route = route;
		this.Step = step;
	}

	public Integer Get_Route() {
		return Route;
	}

	public void Set_Route(Integer route) {
		Route = route;
	}

	public void Set_Step(Integer step) {
		Step = step;
	}
	
	public Integer Get_Step()
	{
		return this.Step;
	}
	
	public void Set_Station(City station) {
		this.The_City = station;
	}
	
	public City Get_City()
	{
		return this.The_City;
	}
}