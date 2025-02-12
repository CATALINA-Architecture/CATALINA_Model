package Cara_Simulation;

public enum Speed {
	Small,
	Medium,
	High;
	
	public double Get_Value(Speed Velocita) { 
		double risultato = 0.0;
		switch (Velocita) {
		case Small:
			risultato = 1.0;
				break;
		case Medium:
			risultato = 2.0;
				break;
		case High:
			risultato = 3.0;
				break;

		}
		return risultato;
	}
}
