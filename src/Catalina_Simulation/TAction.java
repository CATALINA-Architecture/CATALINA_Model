package Catalina_Simulation;

import java.util.ArrayList;

/**
 * It represents a single action to do by the agent
 */
public class TAction {
	
	private String Action_Name;
	private ArrayList<Object> Params;
	private TPredicate Precondition;
	private TPredicate Post_Condition;
	
	/**
	 * It clears any data in action
	 */
	public void Clear()
	{
		this.Action_Name = "";
		this.Params.clear();
		this.Precondition.Clear();
		this.Post_Condition.Clear();
	}
	
	/**
	 * Constructor of the class
	 * @param params			Some information. If the belief associated to the Functional goal is Belief_Destination_Station, the first param is the position coords of the agent in Postcondition (after the executed action). This is useful to compare with Salient_belief in case it is a correct movement. 
	 * @param precondition		Position coords before the executed action by agent
	 * @param post_condition		Position coords before the executed action by agent
	 */
	public TAction(ArrayList<Object> params, TPredicate precondition, TPredicate post_condition)
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
		this.Post_Condition = post_condition;
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
	public TPredicate Get_Post_Condition() {
		return Post_Condition;
	}

	/**
	 * It updates the postcondition to execute the action
	 * @param post_condition		A predicate
	 */
	public void Set_Postcondition(TPredicate post_condition) {
		Post_Condition = post_condition;
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