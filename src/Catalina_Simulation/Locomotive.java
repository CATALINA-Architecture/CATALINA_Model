package Catalina_Simulation;

/**
 * It represents the account of CO2 for any step in a route.
 */
public enum Locomotive {
	Electric,
	Hybrid,
	Diesel;
	
	/**
	 * Get corresponding value to a specific value of enumeration Locomotive 
	 * @param Locomotive	A specific Locomotive value
	 * @return				A double value
	 */
	public double Get_Value(Locomotive Locomotive) { 
		double Result = 0.0;
		switch (Locomotive) {
		case Electric:
			Result = 0.1;
				break;
		case Hybrid:
			Result = 0.5;
				break;
		case Diesel:
			Result = 1;
				break;
		}
		return Result;
	}
}