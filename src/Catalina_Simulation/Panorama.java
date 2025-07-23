package Catalina_Simulation;

public enum Panorama {
	Limited,
	Fair,
	Spectacular;
	
	/**
	 * Get corresponding value to a specific value of enumeration Panorama 
	 * @param Locomotive	A specific Panorama value
	 * @return				A double value
	 */
	public double Get_Value(Panorama Panorama_) { 
		double Result = 0.0;
		switch (Panorama_) {
		case Limited:
			Result = 0.3;
				break;
		case Fair:
			Result = 0.6;
				break;
		case Spectacular:
			Result = 0.9;
				break;
		}
		return Result;
	}
}