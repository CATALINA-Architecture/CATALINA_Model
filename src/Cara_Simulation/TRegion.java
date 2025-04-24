package Cara_Simulation;

import java.util.ArrayList;

public class TRegion {
	
	public ArrayList<City> Destinations;
	public ArrayList<Route> Routes;
	public ArrayList<Integer> Integer_Routes;
	
	
	public TRegion()
	{
		Destinations = new ArrayList<City>();
		Routes = new ArrayList<Route>();
		Integer_Routes = new ArrayList<Integer>();
	}
	
	public boolean Is_Empty()
	{
		boolean result = false;
		
		if(Destinations.isEmpty() && Routes.isEmpty() && Integer_Routes.isEmpty())
		{
			result = true;
		}
		return result;

	}

}
