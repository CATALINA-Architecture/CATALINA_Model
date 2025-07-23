package Catalina_Simulation;

import java.util.ArrayList;

public class TPropositional_Formula {
	
	private ArrayList<TBelief> Beliefs;
	private String Formula;
	
	public TPropositional_Formula()
	{
		
	}

	public ArrayList<TBelief> get_Beliefs() {
		return Beliefs;
	}

	public void set_Beliefs(ArrayList<TBelief> beliefs) {
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
		for (TBelief Belief: this.Beliefs) 
		{
			Evaluetor.set_Variable(Belief.Get_Name(), Belief.Is_Truth());
		}
		Boolean result = Evaluetor.Evaluate(this.Formula);
		
		return result;
	}
}