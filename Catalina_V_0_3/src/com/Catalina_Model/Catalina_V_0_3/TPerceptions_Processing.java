package com.Catalina_Model.Catalina_V_0_3;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.Catalina_Model.Catalina_V_0_3.TPerception_Processing_Sensor_Function_Handler.Memory_Maintenance_Sensor_Function;

public class TPerceptions_Processing 
{
	private TGeneric_Protected_List<TPerception> Acquired_Perceptions;
	private TExecutive_Perception_Function Parent;
	
	private final ReentrantReadWriteLock Perceptions_Lock = new ReentrantReadWriteLock();
    private final ReentrantReadWriteLock.ReadLock Perceptions_Read_Lock = Perceptions_Lock.readLock();
    private final ReentrantReadWriteLock.WriteLock Perceptions_Write_Lock = Perceptions_Lock.writeLock();
    
    private HashSet<String> Sensors; 
    private TPerception_Processing_Sensor_Function_Handler 
    					Perception_Processing_Sensor_Function_Handler;
    private TBoolean_Protected_Object Has_New_Perceptions;
	
	public TPerceptions_Processing(TExecutive_Perception_Function owner)
	{
		this.Parent = owner;
		this.Acquired_Perceptions = new TGeneric_Protected_List<TPerception>();
		this.Sensors = new HashSet<String>();
		this.Perception_Processing_Sensor_Function_Handler = new 
				TPerception_Processing_Sensor_Function_Handler();
		
		this.Has_New_Perceptions = new TBoolean_Protected_Object(false);
	}
	
	public void Insert_Perception(LocalDateTime Time, ArrayList<Object> Data, 
									String source)
	{
		Perceptions_Write_Lock.lock(); // Acquisisce il lock di scrittura
        try
        {
//    		LocalDateTime Time = this.Exec_Working_Memory.Get_Agent().Get_GW().Get_Current_Time();
        	if(Data != null)
        	{
        		TPerception Perception= new TPerception(Time, Data, source);
        		this.Acquired_Perceptions.Add(Perception);
        		this.Has_New_Perceptions.Set_True();
        	}
        }
        finally 
        {
            Perceptions_Write_Lock.unlock(); // Rilascia il lock di scrittura
        }
		
	}

	
	public void Insert_Perceptions(LocalDateTime Time, 
					ArrayList< ArrayList<Object> > Data_Matrix, String source) 
	{
		Perceptions_Write_Lock.lock(); // Acquisisce il lock di scrittura
        try
        {
//    		LocalDateTime Time = this.Exec_Working_Memory.Get_Agent().Get_GW().Get_Current_Time();
        	if( Data_Matrix != null )
        	{
        		if(Data_Matrix.size() > 0 )
        		{
            		ArrayList<TPerception> Perceptions = new ArrayList<TPerception>();
                	for(ArrayList<Object> Data: Data_Matrix)
                	{
                		TPerception Perception= new TPerception(Time, Data, source);
                		Perceptions.add(Perception);
                	}
            		this.Acquired_Perceptions.Add_All(Perceptions);
            		this.Has_New_Perceptions.Set_True();
        		}
        	}
        }
        finally 
        {
            Perceptions_Write_Lock.unlock(); // Rilascia il lock di scrittura
        }
	}
	
	public ArrayList<TPerception> Get_Last_Acquired_Perceptions()
	{
		Perceptions_Write_Lock.lock();
		try 
		{
			this.Has_New_Perceptions.Set_False();
			return this.Acquired_Perceptions.Read_And_Clear();
		} 
		finally
		{
			Perceptions_Write_Lock.unlock();
		}
		
	}
	
	public void Execute()
	{
		this.Acquired_Perceptions.Clear();
		
//		ArrayList<TPerception> Perceptions = new ArrayList<TPerception>();
		
		for(String Sensor: this.Sensors)
		{
			this.Acquired_Perceptions.Add_All( 
							this.Perception_Processing_Sensor_Function_Handler.
									Execute_Function_For_Sensor( Sensor ) );
			
//			Perceptions = this.Perception_Processing_Sensor_Function_Handler.
//					Execute_Function_For_Sensor( Sensor );
//			if (Perceptions != null)
//			{
//				this.Acquired_Perceptions.Add_All( Perceptions );
//			}
		}
	}
	
	public void Add_Sensor(String Sensor)
	{
		this.Sensors.add(Sensor);
	}
	
	public void Add_Sensors(ArrayList<String> sensors)
	{
		this.Sensors.addAll( sensors );
	}
	
	public void Remove_Sensor_by_Object(String Sensor)
	{
		this.Sensors.remove(Sensor);
		this.Perception_Processing_Sensor_Function_Handler.Unregister_Function(
				Sensor);
	}
	
	public Boolean Has_New_Perceptions()
	{
		return this.Has_New_Perceptions.Read();
	}
	 public void Register_Perception_Function(String Sensor, Memory_Maintenance_Sensor_Function func) 
    {
		 if( this.Sensors.contains( Sensor ) )
		 {
			 this.Perception_Processing_Sensor_Function_Handler.Register_Function(
		        		Sensor, func );
		 }
		 else
		 {
//			 System.out.println("\nWARN: No Sensor registered for the Sensor: '" + Sensor + "'.");
		 }
    }
    
    public boolean Unregister_Perception_Function(String Sensor) 
    {
    	if( this.Sensors.contains( Sensor ) )
		{
    		return this.Perception_Processing_Sensor_Function_Handler.Unregister_Function(
	        		Sensor );
		}
    	else
    	{
//    		System.out.println("\nWARN: No Sensor registered for the Sensor: '" + Sensor + "'.");
    		return false;
    	}
        
    }

}
