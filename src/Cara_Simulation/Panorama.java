package Cara_Simulation;

public enum Panorama {
	Small,
	Medium,
	High;
	
	public double Get_Value(Panorama Panorama_) { 
		double risultato = 0.0;
		switch (Panorama_) {
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
