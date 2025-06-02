package Cara_Simulation;

import java.util.HashMap;

/**
 * 	It represents a collection of the values ​​of each property that the routes can have
 */
public class My_Collections 
{
	
	public HashMap<Color, Color_Route> Color_Ruotes = new HashMap<Color, Color_Route>();
	public HashMap<Locomotive, Float> Locomotive_Values = new HashMap<Locomotive, Float>();
	public HashMap<Panorama, Float> Panorama_Values = new HashMap<Panorama, Float>();
	public HashMap<Speed, Float> Velocity_Values = new HashMap<Speed, Float>();
	public HashMap<City, Integer> Station_Values = new HashMap<City, Integer>();
	
	public My_Collections()
	{
		this.Initialize_Color_Ruotes();
		this.Initialize_Locomotive_Values();
		this.Initialize_Panorama_Values();
		this.Initialize_Speed_Values();
		this.Initialize_Station_Values();
	}
	
	/**
	 * It initializes Locomotive Values
	 */
	private void Initialize_Locomotive_Values() 
	{
		this.Locomotive_Values.put(Locomotive.Electric,  0.1f);
		this.Locomotive_Values.put(Locomotive.Hybrid,  0.5f);
		this.Locomotive_Values.put(Locomotive.Diesel,  1.0f);
	}
	
	/**
	 * It initializes Panorama Values
	 */
	private void Initialize_Panorama_Values() 
	{
		this.Panorama_Values.put(Panorama.Limited,  0.3f);
		this.Panorama_Values.put(Panorama.Fair, 0.6f);	
		this.Panorama_Values.put(Panorama.Spectacular,   0.9f);	
	}
	
	/**
	 * It initializes Speed Values
	 */
	private void Initialize_Speed_Values() 
	{
		this.Velocity_Values.put(Speed.Small,  0.3f);
		this.Velocity_Values.put(Speed.Medium, 0.6f);	
		this.Velocity_Values.put(Speed.High,   0.9f);
	}	
	
	/**
	 * It initializes Station Values
	 */
	private void Initialize_Station_Values() 
	{
		int i = 0;
		for (City A_Station : City.values())
		{
            this.Station_Values.put(A_Station, i);
            i++;
		}
	}	

	/**
	 * It initializes Color Ruotes for determining any property for any route
	 */
	private void Initialize_Color_Ruotes() 
	{
		//Red
		Color_Route Route_Red = new Color_Route(Color.Red, Panorama.Limited, Speed.High);
		Route_Red.Locomotives.add(Locomotive.Diesel);
		this.Color_Ruotes.put(Color.Red, Route_Red);	
		
		//Green
		Color_Route Route_Green = new Color_Route(Color.Green, Panorama.Fair, Speed.Medium);
		Route_Green.Locomotives.add(Locomotive.Electric);
		Route_Green.Locomotives.add(Locomotive.Hybrid);
		this.Color_Ruotes.put(Color.Green, Route_Green);
		
		//Yellow
		Color_Route Route_Yellow = new Color_Route(Color.Yellow, Panorama.Spectacular, Speed.Small);
		Route_Yellow.Locomotives.add(Locomotive.Electric);
		Route_Yellow.Locomotives.add(Locomotive.Hybrid);
		this.Color_Ruotes.put(Color.Yellow, Route_Yellow);
		
		//Orange
		Color_Route Route_Orange = new Color_Route(Color.Orange, Panorama.Fair, Speed.Medium);
		Route_Orange.Locomotives.add(Locomotive.Diesel);
		this.Color_Ruotes.put(Color.Orange, Route_Orange);
		
		//Black
		Color_Route Route_Black = new Color_Route(Color.Black, Panorama.Limited, Speed.Small);
		Route_Black.Locomotives.add(Locomotive.Diesel);
		this.Color_Ruotes.put(Color.Black, Route_Black);
		
		//Blue
		Color_Route Route_Blue = new Color_Route(Color.Blue, Panorama.Limited, Speed.Medium);
		Route_Blue.Locomotives.add(Locomotive.Electric);
		this.Color_Ruotes.put(Color.Blue, Route_Blue);
		
		//Violet
		Color_Route Route_Violet = new Color_Route(Color.Violet, Panorama.Fair, Speed.High);
		Route_Violet.Locomotives.add(Locomotive.Hybrid);
		this.Color_Ruotes.put(Color.Violet, Route_Violet);
		
		//White
		Color_Route Route_White = new Color_Route(Color.White, Panorama.Fair, Speed.Medium);
//		Route_White.Locomotiva.add(Locomotive.Hybrid);
		Route_White.Locomotives.add(Locomotive.Electric);
		this.Color_Ruotes.put(Color.White, Route_White);
	}
}