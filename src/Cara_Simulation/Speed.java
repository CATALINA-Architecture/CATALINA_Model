package Cara_Simulation;

public enum Speed {
	Small,
	Medium,
	High;
	
	public int Get_Value(Speed Velocita) { 
		int risultato = 0;
		switch (Velocita) {
		case Small:
			risultato = 1;
				break;
		case Medium:
			risultato = 2;
				break;
		case High:
			risultato = 3;
				break;

		}
		return risultato;
	}
}
