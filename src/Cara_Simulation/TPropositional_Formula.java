package Cara_Simulation;

import java.util.ArrayList;

public class TPropositional_Formula {
	
	private ArrayList<TBelief_Base> Beliefs;
	private String Formula;
	
	public TPropositional_Formula()
	{
		
	}

	public ArrayList<TBelief_Base> get_Beliefs() {
		return Beliefs;
	}

	public void set_Beliefs(ArrayList<TBelief_Base> beliefs) {
		Beliefs = beliefs;
	}

	public String get_Formula() {
		return Formula;
	}

	public void set_Formula(String formula) {
		Formula = formula;
	}
	
	public Boolean Verify_Formula()
	{
		TBoolean_Expression_Evaluetor Evaluetor = new TBoolean_Expression_Evaluetor();
		for (TBelief_Base Belief: this.Beliefs) 
		{
			Evaluetor.set_Variable(Belief.get_Name(), Belief.is_Truth());
		}
		Boolean result = Evaluetor.evaluate(this.Formula);
		
		return result;
	}
	
	

}
