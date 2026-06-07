import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

import com.Catalina_Model.Catalina_V_0_3.TExecutive_Memory_Maintenance_Function;
import com.Catalina_Model.Catalina_V_0_3.TExecutive_Perception_Function;
import com.Catalina_Model.Catalina_V_0_3.TPerception;
import com.Catalina_Model.Catalina_V_0_3.TTriple_Object;

public class TFunctions_for_Perception_Processing 
{
	public HashSet<String> Sensors;
	public HashMap<String, Boolean> Sensors_avaible;
	public TVirtual_GPS Virtual_GPS;
	private Autonomous_Vehicle_Demo Demo;
	private TCommon_Functions Common_Functions;
	private Boolean See_A_Danger;
	private Boolean Low_Fuel_Signal;
	public TTrafficControlService Virtual_TCS;
	
	public TFunctions_for_Perception_Processing(Autonomous_Vehicle_Demo demo)
	{
		this.Demo = demo;
		this.Common_Functions = demo.Common_Functions;
		
		this.Sensors = new HashSet();
		this.Sensors.add("Front_Virtual_Camera");
		this.Sensors.add("Traffic_Control_Service");
		this.Sensors.add("Fuel_Sensor");
		this.Sensors_avaible = new HashMap<String, Boolean>();
		this.Sensors_avaible.put("Front_Virtual_Camera", true);
		this.Sensors_avaible.put("Traffic_Control_Service", true);
		this.Sensors_avaible.put("Fuel_Sensor", true);
//		this.Sensors.add("Me");
		
		//I set some variable
		this.See_A_Danger = false;
		this.Low_Fuel_Signal = false;
		this.Virtual_GPS = new TVirtual_GPS(this.Demo.Agent);
		this.Virtual_TCS = new TTrafficControlService(demo);
	}
	
	private void Add_Sensor_to_Perception_Processing(TExecutive_Perception_Function MMF)
	{
		MMF.Add_Sensors( new ArrayList<>(this.Sensors) );
	}
	
	public ArrayList<TPerception> Get_Current_Position_from_GPS(String sensor)
	{
		ArrayList<TPerception> Perceptions = new ArrayList<TPerception>();
		
		ArrayList<Object> perceived_Data = new ArrayList<Object>();
		perceived_Data.addAll( this.Virtual_GPS.Get_Current_Position());
		if (perceived_Data.size() > 0)
		{
			String Information = "Correct_Movement!";
	    	perceived_Data.add( Information );
	    	TPerception Perception = new TPerception(LocalDateTime.now(), perceived_Data, sensor);
	    	
	    	Perceptions.add( Perception );
		}
		return Perceptions;
	}
	
	public ArrayList<TPerception> See_Road_in_Front(String sensor)
	{
		ArrayList<TPerception> Perceptions = new ArrayList<TPerception>();
		Boolean Can_Capture = this.Sensors_avaible.get("Front_Virtual_Camera");
		if( Can_Capture)
//		if(this.See_A_Danger)
			
		{
//			Scanner scanner = new Scanner(System.in);
//			String Answer = this.Common_Functions.Get_Preset_Input("Do I detect any obstacles? 'Y' for Yes, "
//					+ "otherwise any other input: ", "Y", 2, true);

//	        Common_Functions.Print("fffffffffffff");
//	        if(Answer.isEmpty() || Answer.equals("Y"))
			if (this.See_A_Danger)
	        {
				this.See_A_Danger = false;
				this.Reset_Sensor("Front_Virtual_Camera");
//				Common_Functions.Print("fffffffffffff");
	        	ArrayList<Object> perceived_Data = new ArrayList<Object>();
	        	String Information = "Danger on the route!";
	        	perceived_Data.add( Information );
	        	TPerception Perception = new TPerception(LocalDateTime.now(), perceived_Data, sensor);
	        	
	        	Perceptions.add( Perception );
	        	this.Sensors_avaible.put("Front_Virtual_Camera", false);
	        }
		}

        return Perceptions;
	}
	
	public ArrayList<TPerception> Get_Low_Fuel_Signal(String sensor)
	{
		ArrayList<TPerception> Perceptions = new ArrayList<TPerception>();
		
		if (this.Low_Fuel_Signal)
        {
			this.Low_Fuel_Signal = false;
			ArrayList<Object> perceived_Data = new ArrayList<Object>();
        	/**
        	 * We simulate the value signal for the fuel level.
        	 * Range Fuel Level: 0 to 100
        	 */
			Integer Low_Fuel = 24;
        	perceived_Data.add( Low_Fuel );
        	TPerception Perception = new TPerception(LocalDateTime.now(), perceived_Data, sensor);
        	
        	Perceptions.add( Perception );
        }
		
		return Perceptions;
	}
	
	
	public void Add_Function_To_Perception_Processing(TExecutive_Perception_Function MMF)
	{
		Add_Sensor_to_Perception_Processing( MMF );
//		System.out.print(MMF.Get_Sensors());
		MMF.Register_Perception_Function("Front_Virtual_Camera", this::See_Road_in_Front);
		MMF.Register_Perception_Function("GPS", this::Get_Current_Position_from_GPS);
		MMF.Register_Perception_Function("Traffic_Control_Service", this::Get_Danger_Data_from_TCS);
		MMF.Register_Perception_Function("Fuel_Sensor", this::Get_Low_Fuel_Signal);
		
		
	}
	
	public void Reset_Sensor(String Sensor_Name)
	{
		this.Sensors_avaible.put(Sensor_Name, true);
	}
	
	public void Send_A_Danger()
	{
		this.See_A_Danger = true;
	}
	
	public void Send_Low_Fuel_Signal()
	{
		this.Low_Fuel_Signal = true;
	}
	
	public ArrayList<TPerception> Get_Danger_Data_from_TCS(String sensor)
	{
		ArrayList<TPerception> Perceptions = new ArrayList<TPerception>();
		
		ArrayList<Object> perceived_Data = new ArrayList<Object>();
		perceived_Data.addAll( this.Virtual_TCS.Get_Danger_Data());
		if (perceived_Data.size() > 0)
		{
			
			String Information = "Danger_Data_Acquired!";
	    	perceived_Data.addFirst(Information);
	    	TPerception Perception = new TPerception(LocalDateTime.now(), perceived_Data, sensor);
	    	
	    	Perceptions.add( Perception );
		}
		return Perceptions;
	}

}
