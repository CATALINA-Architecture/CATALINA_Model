package Cara_Simulation;

public class Route{
	
	private Double Path_Time; 
	public Double Get_Path_Time()
	{
		return this.Path_Time;
	}
	
	protected int Route_Number;
	public int getNumero_Rotta() 
	{
		return this.Route_Number;
	}
	
	private Station Departure;
	public Station getDeparture() 
	{
		return this.Departure;
	}
	
	private Station Destination;
	public Station getDestination() 
	{
		return this.Destination;
	}

	
	private Color Color;
	public Color getColor()
	{
		return this.Color;
	}
	
	
	private int Steps_Number;
	public int get_Steps_Number() 
	{
		return this.Steps_Number;
	}
	
	
	private Boolean Interrupted;
	public Boolean getInterrupted() {
		return this.Interrupted;
	}
	public void setInterrupted(Boolean interrupted) {
		this.Interrupted = interrupted;
	}
	
	private Boolean Busy;
	public Boolean getBusy() {
		return this.Busy;
	}
	public void setBusy(Boolean Busy) {
		this.Interrupted = Busy;
	}
	private Boolean Fredned;
	public Boolean getFredned() {
		return this.Fredned;
	}
	public void set_Fredned(Boolean fredned) {
		this.Fredned = fredned;
	}

	private int Step_Interrupted;
	public int get_Pieces_Interrupted() {
		return this.Step_Interrupted;
	}
	public void set_Pieces_Interrupted(int pieces_Interrupted) {
		this.Step_Interrupted = pieces_Interrupted;
	}
	
	private Locomotive Locomotiva;
	public Locomotive getLocomotiva() {
		return this.Locomotiva;
	}
	
	private Panorama The_Panorama;
	public Panorama getPanorama_() {
		return this.The_Panorama;
	}
	private Speed Speed;
	public Speed getVelocita() {
		return this.Speed;
	}
	
	private double Locomotive_Route;
	public double get_Locomotive_Route() {
		return this.Locomotive_Route;
	}
	private double Panorama_Route;
	public double get_Panorama_Route() {
		return this.Panorama_Route;
	}
	
	private int Speed_Route;
	public int get_Speed_Route() {
		return this.Speed_Route;
	}
	
	private int Route_used_by_agent;


	public int get_Route_used_by_agent() {
		return Route_used_by_agent;
	}
	
	private int Total_Rounds;
	public int Get_Total_Rounds()
	{
		return this.Total_Rounds;
	}
	
	public void set_Route_used_by_agent(int route_used_by_agent) {
		Route_used_by_agent = route_used_by_agent;
	}
	public Route(Station Departure, Station Destination, Color Color, int Pieces_Number, Locomotive Locomotiva,
			Panorama Panorama_, Speed Velocita, int Numero_rotta)
	{
		this.Departure = Departure;
		this.Destination = Destination;
		this.Color = Color;
		this.Steps_Number = Pieces_Number;
		this.Step_Interrupted = 0;
		this.Interrupted = false;
		this.Busy = false;
		this.Locomotiva = Locomotiva;
		this.The_Panorama = Panorama_;
		this.Speed = Velocita;
		this.Route_Number = Numero_rotta;
		
//		int moltiplicatore = Pieces_Number;
		//Per ora impongo che a prescindere dal numero dei pezzi, i pesi siano calcolati singolarmente
		//Quindi per il calcolo dei pesi impongi che Pieces_Number sia uguale a 1
		int moltiplicatore = 1;
		
		
		this.Locomotive_Route =  this.Locomotiva.Get_Value(this.Locomotiva) * this.Steps_Number;
		this.Panorama_Route =  this.The_Panorama.Get_Value(this.The_Panorama);
		this.Speed_Route = this.Speed.Get_Value(Velocita);
		//I MUST TO INSERT "+1" because "this.Steps_Number / this.Speed_Route" is the time
		//to go to the last step, but to go in next station I have to do another action!
		//CAMBIAMENTO!!
		this.Path_Time = (double) ((double)(this.Steps_Number+1) / this.Speed_Route);
		this.Total_Rounds = (int) Math.ceil(this.Path_Time);
		if(Route_Number == 128)
		{
			Game.Print("Data for Route: 128");
			Game.Print("this.Steps_Number :"+this.Steps_Number );
			Game.Print("this.Speed_Route :"+this.Speed_Route );
			Game.Print("this.Steps_Number+1 :"+(this.Steps_Number+1) );
			Game.Print("((this.Steps_Number+1) / this.Speed_Route) :="+((this.Steps_Number+1) / this.Speed_Route));
			Game.Print("((this.Steps_Number+1) / this.Speed_Route) :="+((double)(this.Steps_Number+1) / this.Speed_Route));
			Game.Print("this.Path_Time :"+this.Path_Time );
			Game.Print("this.Total_Rounds :"+this.Total_Rounds );
			Game.Print("this.Total_Rounds :"+Math.floor(this.Path_Time ));
		}
		
		
				//Collezione.Locomotive_Values.get(Colore_rotta.Locomotiva.get(i)) * Numero_Pezzi;

		//this.Locomotive = Locomotive;
		
		
	}


	

	

}
