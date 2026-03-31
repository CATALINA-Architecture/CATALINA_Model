package com.Catalina_Model.Catalina_V_0_3;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.stream.Collectors;

public class TPropositional_Formula {
	
//	private ArrayList<TBelief> Beliefs;
//	private ArrayList<String> Beliefs_Names;
	private volatile HashSet<TBelief> Beliefs;
	private volatile HashSet<String> Beliefs_Names;
	private volatile String Formula;
	private volatile String Propositional_Formula_Name;
	
	public TPropositional_Formula()
	{
		this.Beliefs = new HashSet<TBelief>();
		this.Beliefs_Names = new HashSet<String>();
		this.Formula = "";
		this.Propositional_Formula_Name = "";
	}

	public ArrayList<TBelief> Get_Beliefs() 
	{
		ArrayList<TBelief> result = new ArrayList<TBelief>(this.Beliefs);
		return result;
	}
	
	public ArrayList<String> Get_Beliefs_Names() 
	{
		ArrayList<String> result = new ArrayList<String>(this.Beliefs_Names);
		return result;
	}
	
	public void Set_Name(String name ) 
	{
		this.Propositional_Formula_Name = name; 
	}
	
	public String Get_Name( ) 
	{
		return this.Propositional_Formula_Name; 
	}

	public void Set_Beliefs(ArrayList<TBelief> beliefs) 
	{
		this.Beliefs.clear();
		this.Beliefs.addAll(beliefs);
		
		this.Beliefs_Names.clear();
		for(TBelief belief: this.Beliefs)
		{
			this.Beliefs_Names.add(belief.Get_Name());
		}
	}
	
	public void Set_Beliefs_By_Name(ArrayList<String> beliefs_name, 
			ArrayList<TBelief> beliefs) 
	{
		this.Beliefs.clear();
		
		HashSet<String> Set_beliefs_name = new HashSet<>(beliefs_name);
		this.Beliefs.addAll( beliefs.stream()
							.filter(belief -> Set_beliefs_name.contains(belief.Get_Name()))
							.collect(Collectors.toList()));
	}
	
	public void Set_Beliefs_Names(ArrayList<String> beliefs_names) 
	{
		this.Beliefs_Names.clear();
		this.Beliefs_Names.addAll(beliefs_names);
	}
	
	public String Get_Formula() 
	{
		return this.Formula;
	}

	public void Set_Formula(String formula) 
	{
		this.Formula = formula;
	}
	
	public Boolean Verify_Formula()
	{
		TBoolean_Expression_Evaluetor Evaluetor = new TBoolean_Expression_Evaluetor();
		for (TBelief Belief: this.Beliefs) 
		{
			Evaluetor.Set_Variable(Belief.Get_Name(), Belief.Is_Truth());
		}
		Boolean result = Evaluetor.Evaluate(this.Formula);
		return result;
	}
	
	@Override
    public String toString() {
        // Usa String.join per creare un elenco pulito dei nomi delle belief
        String nomiBeliefs = String.join(", ", Beliefs_Names);
        
        return "TPropositional_Formula[" +
               "Name='" + Propositional_Formula_Name + '\'' +
               ", Formula='" + Formula + '\'' +
               ", Beliefs_Names={" + nomiBeliefs + '}' +
               ']';
    }
}