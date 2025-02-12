package Cara_Simulation;

public class TPosition_Train_Coords {
	
	private Integer Route;
	private Integer Step;
	
	public TPosition_Train_Coords(Integer route, Integer step)
	{
		this.Route = route;
		this.Step = step;
	}

	public Integer get_Route() {
		return Route;
	}

	public void set_Route(Integer route) {
		Route = route;
	}

	public Integer get_Step() {
		return Step;
	}

	public void set_Step(Integer step) {
		Step = step;
	}
	
	

}
