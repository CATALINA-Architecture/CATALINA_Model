package Cara_Simulation;

public class TConfiguration {
	
	/***
	 * a propositional formula describing the initial state of the world (SoW)
	 */
	private TPropositional_Formula Initial_SoW; 
	/***
	 * a list of propositional formulas describing the assumptions adopted in the current execution of the system
	 */
	private TPropositional_Formula Assumptions; 
	
	public TConfiguration(TPropositional_Formula initial_SoW, TPropositional_Formula assumptions)
	{
		this.Initial_SoW = initial_SoW;
		this.Assumptions = assumptions;
	}

}
