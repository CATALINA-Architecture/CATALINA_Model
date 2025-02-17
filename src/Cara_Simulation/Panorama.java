package Cara_Simulation;

public enum Panorama {
	Limited,
	Fair,
	Spectacular;
	
	public double Get_Value(Panorama Panorama_) { 
		double risultato = 0.0;
		switch (Panorama_) {
		case Limited:
			risultato = 0.3;
				break;
		case Fair:
			risultato = 0.6;
				break;
		case Spectacular:
			risultato = 0.9;
				break;

		}
		return risultato;
	}
}
