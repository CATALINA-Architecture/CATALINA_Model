package Cara_Simulation;

public class Route{
	
	private Double Path_Time; 
	public Double Get_Path_Time()
	{
		return this.Path_Time;
	}
	
	protected int Numero_Rotta;
	public int getNumero_Rotta() 
	{
		return this.Numero_Rotta;
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
	
	
	private int Pieces_Number;
	public int getPieces_Number() 
	{
		return this.Pieces_Number;
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
	public void setFredned(Boolean fredned) {
		this.Fredned = fredned;
	}

	private int Pieces_Interrupted;
	public int getPieces_Interrupted() {
		return this.Pieces_Interrupted;
	}
	public void setPieces_Interrupted(int pieces_Interrupted) {
		this.Pieces_Interrupted = pieces_Interrupted;
	}
	
	private Locomotive Locomotiva;
	public Locomotive getLocomotiva() {
		return this.Locomotiva;
	}
	
	private Panorama Panorama_;
	public Panorama getPanorama_() {
		return this.Panorama_;
	}
	private Speed Velocita;
	public Speed getVelocita() {
		return this.Velocita;
	}
	
	private double Peso_Locomotiva;
	public double getPeso_Locomotiva() {
		return this.Peso_Locomotiva;
	}
	private double Peso_Panorama;
	public double getPeso_Panorama() {
		return this.Peso_Panorama;
	}
	
	private double Peso_Velocita;
	public double getPeso_Velocita() {
		return this.Peso_Velocita;
	}
	
	private double Peso_Velocita_per_rettangolo;
	public double getPeso_Velocita_per_rettangolo() {
		return this.Peso_Velocita_per_rettangolo;
	}
	
	private int Tratta_Usata_da_Agente;


	public int getTratta_Usata_da_Agente() {
		return Tratta_Usata_da_Agente;
	}
	public void setTratta_Usata_da_Agente(int tratta_Usata_da_Agente) {
		Tratta_Usata_da_Agente = tratta_Usata_da_Agente;
	}
	public Route(Station Departure, Station Destination, Color Color, int Pieces_Number, Locomotive Locomotiva,
			Panorama Panorama_, Speed Velocita, int Numero_rotta)
	{
		this.Departure = Departure;
		this.Destination = Destination;
		this.Color = Color;
		this.Pieces_Number = Pieces_Number;
		this.Pieces_Interrupted = 0;
		this.Interrupted = false;
		this.Busy = false;
		this.Locomotiva = Locomotiva;
		this.Panorama_ = Panorama_;
		this.Velocita = Velocita;
		this.Numero_Rotta = Numero_rotta;
		
//		int moltiplicatore = Pieces_Number;
		//Per ora impongo che a prescindere dal numero dei pezzi, i pesi siano calcolati singolarmente
		//Quindi per il calcolo dei pesi impongi che Pieces_Number sia uguale a 1
		int moltiplicatore = 1;
		
		
		this.Peso_Locomotiva =  this.Locomotiva.Get_Value(this.Locomotiva) * moltiplicatore;
		this.Peso_Panorama =  this.Panorama_.Get_Value(this.Panorama_) * moltiplicatore;
		this.Peso_Velocita_per_rettangolo = this.Velocita.Get_Value(this.Velocita);
		this.Peso_Velocita =  this.Peso_Velocita_per_rettangolo * moltiplicatore;
		//this.Path_Time = this.Peso_Velocita_per_rettangolo / this.Pieces_Number;
		this.Path_Time = this.Pieces_Number / this.Peso_Velocita;
		
		
				//Collezione.Locomotive_Values.get(Colore_rotta.Locomotiva.get(i)) * Numero_Pezzi;

		//this.Locomotive = Locomotive;
		
		
	}


	

	

}
