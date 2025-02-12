package Cara_Simulation;

import java.util.ArrayList;

public class TSensor_Managment {
	
	private ArrayList<String> Acquired_Perceptions; 	
	
	public TSensor_Managment()
	{
		this.Acquired_Perceptions = new ArrayList<String>();
	}
	
	public void Insert_Perception(String Data)
	{
		this.Acquired_Perceptions.add(Data);
	}
	
	public String Get_Last_Acquired_Perception()
	{
		return this.Acquired_Perceptions.getLast();
	}
}
