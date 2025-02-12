package Cara_Simulation;

import java.util.ArrayList;

public class TTCS {
	
	private Environment Map;
	private ArrayList<String> answers;

	
	
	public TTCS(Environment Map)
	{
		this.Map = Map;
	}
	
	public TTriple_Object Response(TTriple_Object Request)
	{
		TTriple_Object Response = new TTriple_Object();
		
		String Request_String = (String) Request.Object_First;
		Game.Print(" TTriple_Object Response(T): "+Request_String);
		
		switch(Request_String)
		{
		case "Status Route":								//Request
			Response.Object_First = "Route is Temporary Closed";		//Response
			Response.Object_Second = Request.Object_Second;
			Response.Object_Third = Request.Object_Third;
			
			break;
		case "How long will the route be closed?":			//Request
			Response.Object_First = "Time for Temporary Closed Route";	//Response
			Response.Object_Second = 3;							
			Response.Object_Third = null;
			break;
		default:
			Game.Print("I cannot handle in TCS in function Response: "+Request_String);
			Game.End_Game();
		}
//		How long will the route be closed?
		
		
		
		//I force Agent to rise an Epistemic Goal
		
		
		return Response;
	}
}
