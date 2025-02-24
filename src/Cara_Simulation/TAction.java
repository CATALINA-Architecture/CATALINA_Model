package Cara_Simulation;

import java.util.ArrayList;

/**
 * It represents a single action to do by the agent
 */
public class TAction {
	
	private String Action_Name;
	private ArrayList<Object> Params;
	private TPredicate Precondition;
	private TPredicate Postcondition;
	
	/**
	 * It clears any data in action
	 */
	public void Clear()
	{
		this.Action_Name = "";
		this.Params.clear();
		this.Precondition.Clear();
		this.Postcondition.Clear();
	}
	
	/**
	 * Constructor of the class
	 * @param params			Some information. If the belief associated to the Functional goal is Belief_Destination_Station, the first param is the position coords of the agent in Postcondition (after the executed action). This is useful to compare with Salient_belief in case it is a correct movement. 
	 * @param precondition		Position coords before the executed action by agent
	 * @param postcondition		Position coords before the executed action by agent
	 */
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

	/**
	 * It returns params of the action
	 * @return	A list of objects
	 */
	public ArrayList<Object> Get_Params() {
		return Params;
	}

	/**
	 * It updates the the params of the action
	 * @param params	A list of objects
	 */
	public void Set_Params(ArrayList<Object> params) {
		Params = params;
	}

	/**
	 * It returns the precondition to execute the action
	 * @return	A predicate
	 */
	public TPredicate Get_Precondition() {
		return Precondition;
	}

	/**
	 * It updates the precondition to execute the action
	 * @param precondition		A predicate
	 */
	public void Set_Precondition(TPredicate precondition) {
		Precondition = precondition;
	}

	/**
	 * It returns the postcondition to execute the action
	 * @return		A predicate
	 */
	public TPredicate Get_Postcondition() {
		return Postcondition;
	}

	/**
	 * It updates the postcondition to execute the action
	 * @param postcondition		A predicate
	 */
	public void Set_Postcondition(TPredicate postcondition) {
		Postcondition = postcondition;
	}

	/**
	 * It returns the function to invoke to execute the action
	 * @return		A String
	 */
	public String Get_Action_Name() {
		return Action_Name;
	}

	/**
	 * It updates the function to invoke to execute the action
	 * @param name	A string
	 */
	public void Set_Action_Name(String name) {
		Action_Name = name;
	}
	
	
	
	
	
	

}
