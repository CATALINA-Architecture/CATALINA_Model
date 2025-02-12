package Cara_Simulation;

import java.util.ArrayList;

public class TAction {
	
	private String Action_Name;
	private ArrayList<Object> Params;
	private TPredicate Precondition;
	private TPredicate Postcondition;
	
	public void Clear()
	{
		this.Action_Name = "";
		this.Params.clear();
		this.Precondition.Clear();
		this.Postcondition.Clear();
	}
	
	public TAction(ArrayList<Object> params, TPredicate precondition, TPredicate postcondition)
	{
		if (params == null)
		{
			this.Params = new ArrayList<Object>();
		}
		else
		{
			this.Params = params;
		}
		
		this.Precondition = precondition;		
		this.Postcondition = postcondition;
	}

	public ArrayList<Object> get_Params() {
		return Params;
	}

	public void set_Params(ArrayList<Object> params) {
		Params = params;
	}

	public TPredicate get_Precondition() {
		return Precondition;
	}

	public void set_Precondition(TPredicate precondition) {
		Precondition = precondition;
	}

	public TPredicate get_Postcondition() {
		return Postcondition;
	}

	public void set_Postcondition(TPredicate postcondition) {
		Postcondition = postcondition;
	}

	public String get_Action_Name() {
		return Action_Name;
	}

	public void set_Action_Name(String name) {
		Action_Name = name;
	}
	
	
	
	
	
	

}
