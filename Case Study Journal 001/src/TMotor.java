/**
 * It represents the account of CO2 for any step in a route.
 */
public enum TMotor 
{
	Electric,
	Hybrid,
	Diesel;
	
	/**
	 * Get corresponding value to a specific value of enumeration Locomotive 
	 * @param motor	A specific Locomotive value
	 * @return				A double value
	 */
	public double Get_Value(TMotor motor) { 
		double Result = 0.0;
		switch (motor) {
		case Electric:
			//Result = 0.1;
			/**
			 * Electric Euro6: 3.8 KG per 1 hour (about 60 km) 
			 */
			Result = 12.5;
				break;
		case Hybrid:
//			Result = 0.5;
			/**
			 * Hybrid Euro6: 11.2 KG per 1 hour (about 60 km) 
			 */
			Result = 75;
				break;
		case Diesel:
//			Result = 1;
			/**
			 * Diesel Euro6: 14 KG per 1 hour (about 60 km) 
			 */
			Result = 125; 
				break;
		}
		return Result;
	}

}
