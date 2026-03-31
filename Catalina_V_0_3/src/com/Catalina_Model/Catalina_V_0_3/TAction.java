package com.Catalina_Model.Catalina_V_0_3;

import java.util.ArrayList;import java.util.Collection;
import java.util.HashSet;

public class TAction {

	private volatile String Action_Name;
	private ArrayList<Object> Params;
//	private TPredicate Pre_condition;
//	private TPredicate Post_Condition;
	private volatile ArrayList<TPredicate> Pre_conditions;
	private volatile ArrayList<TPredicate> Post_Conditions;
	private volatile ArrayList<TPredicate> Invariants;
	private volatile String Formula_Pre_Conditions;
	private volatile String Formula_Post_Conditions;
	private volatile String Formula_Invariants;
	private volatile Integer ID;
	private volatile HashSet<String> Beliefs;
	
	/**
	 * It clears any data in action
	 */
	public void Clear()
	{
		this.Action_Name = "";
		this.Params.clear();
//		this.Pre_condition.Clear();
//		this.Post_Condition.Clear();
		
		this.Pre_conditions.clear();
		this.Post_Conditions.clear();
		this.Invariants.clear();
		
		this.Formula_Pre_Conditions = "";
		this.Formula_Post_Conditions = "";
		this.Formula_Invariants = "";
		this.ID = -1;
		this.Beliefs.clear();
	}
	
	/**
	 * Constructor of the class
	 * @param params			Some information. If the belief associated to the Functional goal is Belief_Destination_Station, the first param is the position coords of the agent in Postcondition (after the executed action). This is useful to compare with Salient_belief in case it is a correct movement. 
	 * @param precondition		Position coords before the executed action by agent
	 * @param post_condition		Position coords before the executed action by agent
	 */
//	public TAction(ArrayList<Object> params, TPredicate precondition, TPredicate post_condition)
//	{
//		if (params == null)
//		{
//			this.Params = new ArrayList<Object>();
//		}
//		else
//		{
//			this.Params = params;
//		}
//		
//		this.Pre_condition = precondition;		
//		this.Post_Condition = post_condition;
//	}
	
	public TAction()
	{
		this.Params = new ArrayList<Object>();
		this.Pre_conditions = new ArrayList<TPredicate>();
		this.Post_Conditions = new ArrayList<TPredicate>();
		this.Invariants = new ArrayList<TPredicate>();
		this.Beliefs = new HashSet<String>();
		
		this.Formula_Pre_Conditions = "";
		this.Formula_Post_Conditions = "";
		this.Formula_Invariants = "";
	}
	
	public TAction(ArrayList<Object> params, ArrayList<TPredicate> preconditions,
			ArrayList<TPredicate> post_conditions, ArrayList<TPredicate> invariants,
			String formula_Pre_Conditions, String formula_Post_Conditions,
			String formula_Invariants)
	{
		this.Params = new ArrayList<Object>();
		this.Pre_conditions = new ArrayList<TPredicate>();
		this.Post_Conditions = new ArrayList<TPredicate>();
		this.Invariants = new ArrayList<TPredicate>();
		
		this.Formula_Pre_Conditions = "";
		this.Formula_Post_Conditions = "";
		this.Formula_Invariants = "";
		
		if (params != null)
		{
			this.Params.addAll( params ) ;
		}
		if (preconditions != null)
		{
			this.Pre_conditions.addAll( preconditions ) ;
		}
		if (post_conditions != null)
		{
			this.Post_Conditions.addAll( post_conditions ) ;
		}
		if (invariants != null)
		{
			this.Invariants.addAll( invariants ) ;
		}
		
		if (formula_Pre_Conditions != null)
		{
			this.Formula_Pre_Conditions = formula_Pre_Conditions ;
		}
		
		if (formula_Post_Conditions != null)
		{
			this.Formula_Post_Conditions = formula_Post_Conditions ;
		}
		
		if (formula_Invariants != null)
		{
			this.Formula_Invariants = formula_Invariants;
		}
		
		
		this.ID = 0;
		
		
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
		if( params != null)
		{
			this.Params.clear();
			this.Params.addAll( params );
		}
		else
		{
			System.out.println("Error when try to set param for Action:");
			System.out.println("inserted Params is null.");
		}
		
	}

//	/**
//	 * It returns the precondition to execute the action
//	 * @return	A predicate
//	 */
//	public TPredicate Get_Precondition() {
//		return Pre_condition;
//	}
//
//	/**
//	 * It updates the precondition to execute the action
//	 * @param precondition		A predicate
//	 */
//	public void Set_Precondition(TPredicate precondition) {
//		Pre_condition = precondition;
//	}

//	/**
//	 * It returns the postcondition to execute the action
//	 * @return		A predicate
//	 */
//	public TPredicate Get_Post_Condition() {
//		return Post_Condition;
//	}
//
//	/**
//	 * It updates the postcondition to execute the action
//	 * @param post_condition		A predicate
//	 */
//	public void Set_Postcondition(TPredicate post_condition) {
//		Post_Condition = post_condition;
//	}

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
	
	public ArrayList<TPredicate> Get_Pre_conditions()
	{
		return this.Pre_conditions;
	}
	
	public ArrayList<TPredicate> Get_Invariants()
	{
		return this.Invariants;
	}
	
	public ArrayList<TPredicate> Get_Post_conditions()
	{
		return this.Post_Conditions;
	}
	
	public void Set_Pre_conditions(ArrayList<TPredicate> pre_conditions)
	{
		this.Pre_conditions.clear();
		if ( pre_conditions != null)
		{
			this.Pre_conditions.addAll( pre_conditions );
		}
	}
	
	public void Set_Post_conditions(ArrayList<TPredicate> post_conditions)
	{
		this.Post_Conditions.clear();
		if ( post_conditions != null)
		{
			this.Post_Conditions.addAll( post_conditions );
		}
	}
	
	public void Set_Invariants(ArrayList<TPredicate> invariants)
	{
		this.Invariants.clear();
		if ( invariants != null)
		{
			this.Invariants.addAll( invariants );
		}
	}
	
	public void Add_Pre_condition(TPredicate pre_condition)
	{
		this.Pre_conditions.add( pre_condition );
	}
	
	public void Add_Post_condition(TPredicate post_condition)
	{
		this.Post_Conditions.add( post_condition );
	}
	
	public void Add_Invariant(TPredicate invariant)
	{
		this.Invariants.add( invariant );
	}

	@Override
    public String toString() {
        // Gestisce i campi che potrebbero essere null
        String name = (this.Action_Name == null) ? "null" : this.Action_Name;
        
        // Stampa i parametri direttamente
        String paramsStr = (this.Params == null) ? "null" : this.Params.toString();
        
        // Stampa il CONTEGGIO delle condizioni per evitare log-spam
        int preCount = (this.Pre_conditions == null) ? 0 : this.Pre_conditions.size();
        int postCount = (this.Post_Conditions == null) ? 0 : this.Post_Conditions.size();
        int invCount = (this.Invariants == null) ? 0 : this.Invariants.size();

        return "TAction[" +
               "Name='" + name + '\'' +
               ", Params=" + paramsStr +
               ", Pre_conditions_Count=" + preCount +
               ", Post_Conditions_Count=" + postCount +
               ", Invariants_Count=" + invCount +
               ']';
    }
	
	public void Set_ID(Integer Value)
	{
		this.ID = Value;
	}
	
	public Integer Get_ID()
	{
		return this.ID;
	}
	
	public HashSet<String> Get_Beliefs()
	{
		return this.Beliefs;
	}
	
	public void Set_Beliefs(HashSet<String> values)
	{
		this.Beliefs.clear();
		if (values != null)
		{
			this.Beliefs.addAll( values );
		}
	}
	
	public void Copy_Action(TAction action)
	{
		this.Clear();
		this.Action_Name = action.Action_Name;
		//Copy PArams
		for(Object obj: action.Params)
		{
			this.Params.add( obj );
		}
		
		//Copu Pre_conditions
		for(TPredicate Predicate: action.Pre_conditions)
		{
			this.Pre_conditions.add( Predicate );
		}
		
		//Copy Post_Conditions
		for(TPredicate Predicate: action.Post_Conditions)
		{
			this.Post_Conditions.add( Predicate );
		}
		
		//Copy Invariants
		for(TPredicate Predicate: action.Invariants)
		{
			this.Invariants.add( Predicate );
		}
		
		//copy Formulas
		this.Formula_Invariants = action.Formula_Invariants;
		this.Formula_Post_Conditions = action.Formula_Post_Conditions;
		this.Formula_Invariants = action.Formula_Invariants;
		
		this.ID = action.ID;
		this.Beliefs.addAll( action.Beliefs);
	}
	
	public TAction Clone()
	{
		TAction action = new TAction();
		action.Copy_Action( this );
		return action;
	}
	
}
