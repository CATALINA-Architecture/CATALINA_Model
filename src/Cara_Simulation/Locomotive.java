package Cara_Simulation;

public enum Locomotive {
	Electric,
	Hybrid,
	Diesel;
	
	public double Get_Value(Locomotive Locomotiva) { 
		double risultato = 0.0;
		switch (Locomotiva) {
		case Electric:
			//risultato = 0;
			risultato = 0.1;
				break;
		case Hybrid:
			//risultato = 2;
			risultato = 0.5;
				break;
		case Diesel:
			//risultato = 3;
			risultato = 1;
				break;

		}
		return risultato;
	}
}
