package Cara_Simulation;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class TSensor_Managment {
	
	private ArrayList<TPerception> Acquired_Perceptions;
	private TExecutive_Working_Memory_Maintenance WMM;
	
	
	public TSensor_Managment(TExecutive_Working_Memory_Maintenance wmm)
	{

		this.Acquired_Perceptions = new ArrayList<TPerception>();
		this.WMM = wmm;
	}
	
	public void Insert_Perception(TTriple_Object Data, Object source)
	{
		LocalDateTime Time = this.WMM.Get_Agent().Get_GW().Get_Current_Time();
		TPerception Perception= new TPerception(Time, Data, source);
		this.Acquired_Perceptions.add(Perception);
	}
	
	public TPerception Get_Last_Acquired_Perception()
	{
		Game.Print("My sensor is attempting to acquire the most recent Stimulus (if any).");
		if( this.Acquired_Perceptions.size() > 0)
		{
			Game.Print("I feel a Stimulus.");
			Game.Print("Stimulus acquired correctly.");
			return this.Acquired_Perceptions.getLast();
		}
		else
		{
			Game.Print("I didn't feel any Stimulus.");
			return null;			
		}
	}
}
