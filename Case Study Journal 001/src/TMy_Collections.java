import java.util.HashMap;


public class TMy_Collections 
{
	public HashMap<TColor, TColor_Route> Color_Ruotes = new HashMap<TColor, TColor_Route>();
	public HashMap<TMotor, Float> Locomotive_Values = new HashMap<TMotor, Float>();
	public HashMap<TPanorama, Float> Panorama_Values = new HashMap<TPanorama, Float>();
	public HashMap<TSpeed, Float> Velocity_Values = new HashMap<TSpeed, Float>();
	public HashMap<TCity, Integer> Station_Values = new HashMap<TCity, Integer>();
	
	public TMy_Collections()
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
		this.Locomotive_Values.put(TMotor.Electric,  0.1f);
		this.Locomotive_Values.put(TMotor.Hybrid,  0.5f);
		this.Locomotive_Values.put(TMotor.Diesel,  1.0f);
	}
	
	/**
	 * It initializes Panorama Values
	 */
	private void Initialize_Panorama_Values() 
	{
		this.Panorama_Values.put(TPanorama.Limited,  0.3f);
		this.Panorama_Values.put(TPanorama.Fair, 0.6f);	
		this.Panorama_Values.put(TPanorama.Spectacular,   0.9f);	
	}
	
	/**
	 * It initializes Speed Values
	 */
	private void Initialize_Speed_Values() 
	{
		this.Velocity_Values.put(TSpeed.Small,  0.3f);
		this.Velocity_Values.put(TSpeed.Medium, 0.6f);	
		this.Velocity_Values.put(TSpeed.High,   0.9f);
	}	
	
	/**
	 * It initializes Station Values
	 */
	private void Initialize_Station_Values() 
	{
		int i = 0;
		for (TCity A_City : TCity.values())
		{
            this.Station_Values.put(A_City, i);
            i++;
		}
	}	

	/**
	 * It initializes Color Ruotes for determining any property for any route
	 */
	private void Initialize_Color_Ruotes() 
	{
		//Red
		TColor_Route Route_Red = new 
						TColor_Route(TColor.Red, TPanorama.Limited, TSpeed.High);
		Route_Red.Motors.add(TMotor.Diesel);
		this.Color_Ruotes.put(TColor.Red, Route_Red);	
		
		//Green
		TColor_Route Route_Green = new 
						TColor_Route(TColor.Green, TPanorama.Fair, TSpeed.Medium);
		Route_Green.Motors.add(TMotor.Electric);
		Route_Green.Motors.add(TMotor.Hybrid);
		this.Color_Ruotes.put(TColor.Green, Route_Green);
		
		//Yellow
		TColor_Route Route_Yellow = new 
				TColor_Route(TColor.Yellow, TPanorama.Spectacular, TSpeed.Small);
		Route_Yellow.Motors.add(TMotor.Electric);
		Route_Yellow.Motors.add(TMotor.Hybrid);
		this.Color_Ruotes.put(TColor.Yellow, Route_Yellow);
		
		//Orange
		TColor_Route Route_Orange = new 
				TColor_Route(TColor.Orange, TPanorama.Fair, TSpeed.Medium);
		Route_Orange.Motors.add(TMotor.Diesel);
		this.Color_Ruotes.put(TColor.Orange, Route_Orange);
		
		//Black
		TColor_Route Route_Black = new 
				TColor_Route(TColor.Black, TPanorama.Limited, TSpeed.Small);
		Route_Black.Motors.add(TMotor.Diesel);
		this.Color_Ruotes.put(TColor.Black, Route_Black);
		
		//Blue
		TColor_Route Route_Blue = new 
				TColor_Route(TColor.Blue, TPanorama.Limited, TSpeed.Medium);
		Route_Blue.Motors.add(TMotor.Electric);
		this.Color_Ruotes.put(TColor.Blue, Route_Blue);
		
		//Violet
		TColor_Route Route_Violet = new 
				TColor_Route(TColor.Violet, TPanorama.Fair, TSpeed.High);
		Route_Violet.Motors.add(TMotor.Hybrid);
		this.Color_Ruotes.put(TColor.Violet, Route_Violet);
		
		//White
		TColor_Route Route_White = new 
				TColor_Route(TColor.White, TPanorama.Fair, TSpeed.Medium);
//		Route_White.Locomotiva.add(Locomotive.Hybrid);
		Route_White.Motors.add(TMotor.Electric);
		this.Color_Ruotes.put(TColor.White, Route_White);
	}
}
