package Cara_Simulation;

import java.util.ArrayList;

public class TTCS {
	
	private Environment Map;
	private ArrayList<String> answers;
	private ArrayList<TTriple_Object> Answer_Objects;
	private int Response_Number;

	
	
	public TTCS(Environment Map)
	{
		this.Map = Map;
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
		
		switch(Request_String)
		{
		case "Status Route":								
			//Response.Object_First = "Route is Temporary Closed";		//Response
//			Response.Object_First = ;
//			Response.Object_Second = Request.Object_Second;
//			Response.Object_Third = Request.Object_Third;
			Response.Set_Object_First(Answer_Objects.get(Response_Number).Get_Object_First());
			Response.Set_Object_Second(Request.Get_Object_Second());
			Response.Set_Object_Third(Request.Get_Object_Third());
			
			break;
		case "GO_TO_Route":								
			//Response.Object_First = Answer_Objects.get(Response_Number).Object_First;
			Response.Set_Object_First(Answer_Objects.get(Response_Number).Get_Object_First());
			Response.Set_Object_Second(Request.Get_Object_Second());
			Response.Set_Object_Third(Request.Get_Object_Third());
			
			break;
		case "GO_TO_Step":								
			//Response.Object_First = Answer_Objects.get(Response_Number).Object_First;
			Response.Set_Object_First(Answer_Objects.get(Response_Number).Get_Object_First());
			Response.Set_Object_Second(Request.Get_Object_Second());
			Response.Set_Object_Third(Request.Get_Object_Third());
			
			break;
		case "How long will the route be closed?":			//Request
			//Response.Object_First = Answer_Objects.get(Response_Number).Object_First;
			Response.Set_Object_First(Answer_Objects.get(Response_Number).Get_Object_First());
			Response.Set_Object_Second(Request.Get_Object_Second());
			Response.Set_Object_Third(Request.Get_Object_Third());
			break;
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
