package Cara_Simulation;

import java.util.HashMap;
//import java.util.ArrayList;

	
public class My_Collections {
	

	public HashMap<Color, Color_Route> Color_Ruotes = new HashMap<Color, Color_Route>();
	public HashMap<Locomotive, Float> Locomotive_Values = new HashMap<Locomotive, Float>();
	public HashMap<Panorama, Float> Panorama_Values = new HashMap<Panorama, Float>();
	public HashMap<Speed, Float> Velocity_Values = new HashMap<Speed, Float>();
	public HashMap<Station, Integer> Station_Values = new HashMap<Station, Integer>();
	
	public My_Collections()
	{
		//HashMap<Color, Color_Route> Color_Ruotes = new HashMap<Color, Color_Route>();
		this.Initialize_Color_Ruotes();
		this.Initialize_Locomotive_Values();
		this.Initialize_Panorama_Values();
		this.Initialize_Velocity_Values();
		this.Initialize_Station_Values();
	}
	
	private void Initialize_Locomotive_Values() {

//		this.Locomotive_Values.put(Locomotive.Electric,  0.3f);
//		this.Locomotive_Values.put(Locomotive.Hybrid,  0.6f);
//		this.Locomotive_Values.put(Locomotive.Diesel,  0.9f);
		this.Locomotive_Values.put(Locomotive.Electric,  0.1f);
		this.Locomotive_Values.put(Locomotive.Hybrid,  0.5f);
		this.Locomotive_Values.put(Locomotive.Diesel,  1.0f);
	}
	
	private void Initialize_Panorama_Values() {

		this.Panorama_Values.put(Panorama.Small,  0.3f);
		this.Panorama_Values.put(Panorama.Medium, 0.6f);	
		this.Panorama_Values.put(Panorama.High,   0.9f);	
	}
	
	private void Initialize_Velocity_Values() {

		this.Velocity_Values.put(Speed.Small,  0.3f);
		this.Velocity_Values.put(Speed.Medium, 0.6f);	
		this.Velocity_Values.put(Speed.High,   0.9f);
	}	
	
	private void Initialize_Station_Values() {
		
		int i = 0;
		for (Station Stazione : Station.values()) {
            this.Station_Values.put(Stazione, i);
            i++;
            
		}
	}	

	private void Initialize_Color_Ruotes() {

		//Red
		Color_Route Route_Red = new Color_Route(Color.Red, Panorama.Small, Speed.High);
		Route_Red.Locomotives.add(Locomotive.Diesel);
		this.Color_Ruotes.put(Color.Red, Route_Red);	
		
		//Green
		Color_Route Route_Green = new Color_Route(Color.Green, Panorama.Medium, Speed.Medium);
		Route_Green.Locomotives.add(Locomotive.Electric);
		Route_Green.Locomotives.add(Locomotive.Hybrid);
		this.Color_Ruotes.put(Color.Green, Route_Green);
		
		//Yellow
		Color_Route Route_Yellow = new Color_Route(Color.Yellow, Panorama.High, Speed.Small);
		Route_Yellow.Locomotives.add(Locomotive.Electric);
		Route_Yellow.Locomotives.add(Locomotive.Hybrid);
		this.Color_Ruotes.put(Color.Yellow, Route_Yellow);
		
		//Orange
		Color_Route Route_Orange = new Color_Route(Color.Orange, Panorama.Medium, Speed.Medium);
		Route_Orange.Locomotives.add(Locomotive.Diesel);
		this.Color_Ruotes.put(Color.Orange, Route_Orange);
		
		//Black
		Color_Route Route_Black = new Color_Route(Color.Black, Panorama.Small, Speed.Small);
		Route_Black.Locomotives.add(Locomotive.Diesel);
		this.Color_Ruotes.put(Color.Black, Route_Black);
		
		//Blue
		Color_Route Route_Blue = new Color_Route(Color.Blue, Panorama.Small, Speed.Medium);
		Route_Blue.Locomotives.add(Locomotive.Electric);
		this.Color_Ruotes.put(Color.Blue, Route_Blue);
		
		//Violet
		Color_Route Route_Violet = new Color_Route(Color.Violet, Panorama.Medium, Speed.High);
		Route_Violet.Locomotives.add(Locomotive.Hybrid);
		this.Color_Ruotes.put(Color.Violet, Route_Violet);
		
		//White
		Color_Route Route_White = new Color_Route(Color.White, Panorama.Medium, Speed.Medium);
//		Route_White.Locomotiva.add(Locomotive.Hybrid);
		Route_White.Locomotives.add(Locomotive.Electric);
		this.Color_Ruotes.put(Color.White, Route_White);

	}
			

}
