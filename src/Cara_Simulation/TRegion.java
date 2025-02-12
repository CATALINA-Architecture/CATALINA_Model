package Cara_Simulation;

import java.util.ArrayList;

public class TRegion {
	
	public ArrayList<Station> Destinations;
	public ArrayList<Route> Routes;
	public ArrayList<Integer> Integer_Routes;
	
	public TRegion()
	{
		Destinations = new ArrayList<Station>();
		Routes = new ArrayList<Route>();
		Integer_Routes = new ArrayList<Integer>();
	}

}
