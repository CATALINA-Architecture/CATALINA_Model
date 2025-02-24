package Cara_Simulation;

public class TPosition_Train_Coords {
	//If agent is in a station => station = a station, Route = -1, step = 0
	//If agent is in a station => station = a station, Route => 0 , step => 0
	private Station The_Station;
	private Integer Route;
	private Integer Step;
	
	public TPosition_Train_Coords(Station station, Integer route, Integer step)
	{
		this.The_Station = station;
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
	
	public void Set_Station(Station station) {
		this.The_Station = station;
	}
	
	public Station Get_Station()
	{
		return this.The_Station;
	}
	
	
	
	

}
