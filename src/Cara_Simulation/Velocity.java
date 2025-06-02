package Cara_Simulation;

public enum Velocity {
	Small,
	Medium,
	High;
	
	public double Get_Value(Velocity Velocita) { 
		double risultato = 0.0;
		switch (Velocita) {
		case Small:
			risultato = 0.3;
				break;
		case Medium:
			risultato = 0.6;
				break;
		case High:
			risultato = 0.9;
				break;

		}
		return risultato;
	}
}