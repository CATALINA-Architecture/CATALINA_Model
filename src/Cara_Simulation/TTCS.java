package Cara_Simulation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;

public class TTCS {
	
	private Environment Map;
	private ArrayList<String> answers;
	private ArrayList<TTriple_Object> Answer_Objects;
	private int Response_Number;
	private int Min_Closed_Route_Duration;
	private int Max_Closed_Route_Duration;

	public TTCS(Environment Map)
	{
		this.Map = Map;
		
		this.Min_Closed_Route_Duration = 1;
		this.Max_Closed_Route_Duration = 5;
		
		answers = new ArrayList<String>();
		Answer_Objects = new ArrayList<TTriple_Object>();
		

		//Set answer for simulations
		TTriple_Object Answer_0K =  new TTriple_Object();
//		Answer_0K.Object_First = "Correct move!";
		Answer_0K.Set_Object_First("Correct move!");
		
		Answer_Objects.add(Answer_0K); //1 response
		Answer_Objects.add(Answer_0K); //2 response
		Answer_Objects.add(Answer_0K); //3 response
		Answer_Objects.add(Answer_0K); //4 response
		Answer_Objects.add(Answer_0K); //5 response
		Answer_Objects.add(Answer_0K); //6 response
		Answer_Objects.add(Answer_0K); //7 response
		Answer_Objects.add(Answer_0K); //1 response
		Answer_Objects.add(Answer_0K); //2 response
		Answer_Objects.add(Answer_0K); //3 response
		Answer_Objects.add(Answer_0K); //4 response
		Answer_Objects.add(Answer_0K); //5 response
		Answer_Objects.add(Answer_0K); //6 response
		Answer_Objects.add(Answer_0K); //7 response
		Answer_Objects.add(Answer_0K); //1 response
		Answer_Objects.add(Answer_0K); //2 response
		Answer_Objects.add(Answer_0K); //3 response
		Answer_Objects.add(Answer_0K); //4 response
		Answer_Objects.add(Answer_0K); //5 response
		Answer_Objects.add(Answer_0K); //6 response
		Answer_Objects.add(Answer_0K); //7 response
		
		
		
		
		TTriple_Object Answer_Temp_Closed =  new TTriple_Object();
//		Answer_Temp_Closed.Object_First = "Route is Temporary Closed";
		Answer_Temp_Closed.Set_Object_First("Route is Temporary Closed");
		Answer_Objects.add(Answer_Temp_Closed); //5 response
		Answer_Objects.add(Answer_Temp_Closed); //6 response
		
		TTriple_Object Answer_Duration_Temporary_Closed =  new TTriple_Object();
//		Answer_Duration_Temporary_Closed.Object_First = "Duration Time for Temporary Closed Route";
//		Answer_Duration_Temporary_Closed.Object_Second = 3; //Duration Time
		
		Answer_Duration_Temporary_Closed.Set_Object_First("Duration Time for Temporary Closed Route");
		Answer_Duration_Temporary_Closed.Set_Object_Second(3);//Duration Time
		
		Answer_Objects.add(Answer_Duration_Temporary_Closed); //7 response
		
		Response_Number = -1;
	}
	
	
	public Boolean Execute_Player_Action(TTriple_Object Request, TAgent Player)
	{
		boolean result = true;
		try
		{
			
		
		TTriple_Object Response = new TTriple_Object();
		
		Response_Number++;
		
		Game.Print("Response_Number: "+Response_Number);
		String Request_String = (String) Request.Get_Object_First();
		Game.Print("Request: "+Request_String);
		TTriple_Object Answer =  new TTriple_Object();
//		Answer.Set_Object_First("Correct move!");
		
		switch(Request_String)
		{
		case "GO_TO_Route":								
			//Response.Object_First = Answer_Objects.get(Response_Number).Object_First;
//			Response.Set_Object_First(Answer_Objects.get(Response_Number)Get_Object_First);
			
			Answer.Set_Object_First("Correct move!");
			Response.Set_Object_First(Answer.Get_Object_First());
			Response.Set_Object_Second(Request.Get_Object_Second());
			Response.Set_Object_Third(Request.Get_Object_Third());
			
			break;
		case "GO_TO_Step":								
			
			Answer.Set_Object_First("Correct move!");
			Response.Set_Object_First(Answer.Get_Object_First());	
			Response.Set_Object_Second(Request.Get_Object_Second());
			Response.Set_Object_Third(Request.Get_Object_Third());
			
			break;
		case "Come_Back_to_City":
			
			Answer.Set_Object_First("Correct move!");
			Response.Set_Object_First(Answer.Get_Object_First());	
			Response.Set_Object_Second(Request.Get_Object_Second());
			Response.Set_Object_Third(Request.Get_Object_Third());
			break;
			
		case "Ask Closed Route Duration":			//Request
			//Response.Object_First = Answer_Objects.get(Response_Number).Object_First;
			Game.Print(Request_String);
			TTriple_Object Answer_Temp_Closed =  new TTriple_Object();

			Answer_Temp_Closed.Set_Object_First("Acquired Closed Route Duration");
			Random random = new Random();
			int min = 1;
			int max = 9;
	        Integer number = random.nextInt(max - min + 1) + min;
	        
//	        String String_Duration = Game.Get_Preset_Input("Closed Route Duration?:  ",number.toString(),2);
	        String String_Duration = Game.Get_Preset_Input("How long will the route be closed?: ",number.toString(),2);
	        
	        Integer Duration = Integer.parseInt(String_Duration);
			Response.Set_Object_First(Answer_Temp_Closed.Get_Object_First());
			Response.Set_Object_Second(Request.Get_Object_Second());
			Response.Set_Object_Third( Duration);
			break;
//		case "Ask Danger Type on the road":
//			Game.Print(Request_String);
//
//			Answer.Set_Object_First("Acquired Danger Type on the road");
//			break;
		default:
			Game.Print("I cannot handle in TCS in function Response: "+Request_String);
			Game.End_Game();
		}

		//I insert a Perception in Sensor of the player (Agent)
		Player.Get_WMM().Get_Sensor().Insert_Perception(Response, "TCS");
		
		//I force Agent to rise an Epistemic Goal
		}
		catch (Exception e) {
	      Game.Print("Something went wrongin method: Insert_New_Desires.");
	      Game.Print("Message Error: "+e.getMessage());
	      Game.Print("Message Error: "+e.getMessage());
	      Game.PrintLn();
	      e.printStackTrace();
	      result = false;
	    }
	    return result;
	}
}