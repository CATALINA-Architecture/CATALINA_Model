package Cara_Simulation;

import java.util.ArrayList;

public class TSensor_Managment {
	
	private ArrayList<TPerception> Acquired_Perceptions;
	
	
	public TSensor_Managment()
	{

		this.Acquired_Perceptions = new ArrayList<TPerception>();
	}
	
	public void Insert_Perception(TTriple_Object Data, Object source)
	{
		TPerception Perception= new TPerception(Data, source);
		this.Acquired_Perceptions.add(Perception);
	}
	
	public TPerception Get_Last_Acquired_Perception()
	{
		Game.Print("Sensor acquires last Perception (if it exists).");
		if( this.Acquired_Perceptions.size() > 0)
		{
			Game.Print("I'm feeling some stimulus");
			Game.Print("Perception acquired a Perception correctly.");
			return this.Acquired_Perceptions.getLast();
		}
		else
		{
			Game.Print("The Agent did not perceive any stimulus");
			return null;			
		}
	}
}
