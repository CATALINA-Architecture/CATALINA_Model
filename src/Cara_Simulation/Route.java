package Cara_Simulation;

public class Route{
	
	private Double Path_Time; 
	protected int Route_Number;
	private City Departure;
	private City Destination;
	private Color Color;
	private int Steps_Number;	
	private Boolean Interrupted;
	private Boolean Busy;
	private Boolean Fredned;
	private int Step_Interrupted;
	private Locomotive Locomotive;
	private Panorama The_Panorama;
	private Speed Speed;
	private double Route_Locomotive;
	private double Route_Panorama;
	private int Route_Speed;
	private int Route_used_by_agent;
	private int Total_Rounds;	
	private int Duration_Closed;
	
	
	/**
	 * It returns Path Time of the route
	 * @return		A double value
	 */
	public Double Get_Path_Time()
	{
		return this.Path_Time;
	}
	
	/**
	 * It returns the numered (index) of the route in Environment (Map)
	 * @return		An integer value
	 */
	public int Get_Numered_Route() 
	{
		return this.Route_Number;
	}
	
	/**
	 * It returns the departure station of the route
	 * @return		A station value
	 */
	public City Get_Departure() 
	{
		return this.Departure;
	}
	
	/**
	 * It returns the destination station of the route
	 * @return		A station value
	 */
	public City Get_Destination() 
	{
		return this.Destination;
	}

	
	/**
	 * It returns the color value of the route
	 * @return	A color value
	 */
	public Color Get_Color()
	{
		return this.Color;
	}
	
	
	/**
	 * It returns the number of steps of the route
	 * @return	An integer
	 */
	public int Get_Steps_Number() 
	{
		return this.Steps_Number;
	}
	
	
	/**
	 * It returns true if the route is interrupted, false if not
	 * @return	A boolean value
	 */
	public Boolean Get_Interrupted() {
		return this.Interrupted;
	}
	/**
	 * It updates the value of interrupted of the route
	 */
	public void Set_Interrupted(Boolean interrupted) {
		this.Interrupted = interrupted;
	}
	
	/**
	 * It returns the busy value of the route
	 * @return	A boolean value
	 */	
	public Boolean Get_Busy() {
		return this.Busy;
	}
	
	/**
	 * It updates the busy value of the route
	 */
	public void Set_Busy(Boolean Busy) {
		this.Interrupted = Busy;
	}
	
	/**
	 * It returns the fredned value of the route
	 * @return	A boolean value
	 */
	public Boolean Get_Fredned() {
		return this.Fredned;
	}
	
	/**
	 * It updates the fredned value of the route
	 */
	public void Set_Fredned(Boolean fredned) {
		this.Fredned = fredned;
	}

	/**
	 * It returns the number of steps interrupted of the route
	 * @return	An integer value
	 */
	public int Get_Steps_Interrupted() {
		return this.Step_Interrupted;
	}
	
	/**
	 * It updates the number of steps interrupted  of the route
	 */
	public void set_Pieces_Interrupted(int pieces_Interrupted) {
		this.Step_Interrupted = pieces_Interrupted;
	}
	
	/**
	 * It returns the locomotive value of the route
	 * @return	An locomotive value
	 */
	public Locomotive Get_Locomotive() {
		return this.Locomotive;
	}
	
	/**
	 * It returns the panorama value of the route
	 * @return	An panorama value
	 */
	public Panorama Get_Panorama_() {
		return this.The_Panorama;
	}
	/**
	 * It returns the speed value of the route
	 * @return	An speed value
	 */
	public Speed Get_Velocita() {
		return this.Speed;
	}
	
	/**
	 * It returns the average value of the route locomotive
	 * @return	An double value
	 */
	public double Get_Route_Locomotive() {
		return this.Route_Locomotive;
	}
	/**
	 * It returns the average value of the route panorama
	 * @return	An double value
	 */
	public double get_Route_Panorama() {
		return this.Route_Panorama;
	}
	
	/**
	 * It returns the average value of the route speed
	 * @return	An integer value
	 */
	public int get_Route_Speed() {
		return this.Route_Speed;
	}
	
	

	/**
	 * It returns the Route_used_by_agent of the route
	 * @return	An integer value
	 */
	public int get_Route_used_by_agent() {
		return this.Route_used_by_agent;
	}
	
	/**
	 * It returns the number of rounds to complete (travel on all steps) the route
	 * @return	An integer value
	 */
	public int Get_Total_Rounds()
	{
		return this.Total_Rounds;
	}
	/**
	 * It updates the Route_used_by_agent of the route
	 */
	public void set_Route_used_by_agent(int route_used_by_agent) {
		this.Route_used_by_agent = route_used_by_agent;
	}
	
	public void Set_Duration_Closed(Integer value)
	{
		this.Duration_Closed = value;
	}
	
	public Integer Get_Duration_Closed()
	{
		return this.Duration_Closed;
	}
	
	/**
	 * Constructor of the class
	 * @param departure			The departure station
	 * @param destination		The destination station
	 * @param color				the associated color to the route
	 * @param steps_Number		the
	 * @param locomotive
	 * @param panorama
	 * @param speed
	 * @param route_Number
	 */
	public Route(City departure, City destination, Color color, int steps_Number, 
			Locomotive locomotive, Panorama panorama, Speed speed, int route_Number)
	{
		this.Departure = departure;
		this.Destination = destination;
		this.Color = color;
		this.Steps_Number = steps_Number;
		this.Step_Interrupted = 0;
		this.Interrupted = false;
		this.Busy = false;
		this.Locomotive = locomotive;
		this.The_Panorama = panorama;
		this.Speed = speed;
		this.Route_Number = route_Number;
		this.Duration_Closed = 0;

		//Computed values
		this.Route_Locomotive =  this.Locomotive.Get_Value(this.Locomotive) * this.Steps_Number;
		this.Route_Panorama =  this.The_Panorama.Get_Value(this.The_Panorama);
		this.Route_Speed = this.Speed.Get_Value(speed);
		//I MUST TO INSERT "+1" because "this.Steps_Number / this.Speed_Route" is the time
		//to go to the last step, but to go in next station I have to do another action!
		this.Path_Time = (double) ((double)(this.Steps_Number+1) / this.Route_Speed);
		this.Total_Rounds = (int) Math.ceil(this.Path_Time);
	}
	
	public double Get_Path_Time_Starting_By_Step(int Starting_Step) 
	{
		return (double) ((double)(this.Steps_Number+1-Starting_Step) / this.Route_Speed);
	}
	
	public int Get_Total_Rounds_Starting_By_Step(int Starting_Step) 
	{
		double time = this.Get_Path_Time_Starting_By_Step(Starting_Step);
		return (int) Math.ceil(time);
	}

}
