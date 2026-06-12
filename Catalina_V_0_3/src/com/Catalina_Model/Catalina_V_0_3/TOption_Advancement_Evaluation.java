package com.Catalina_Model.Catalina_V_0_3;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;

public class TOption_Advancement_Evaluation 
{
	private TExecutive_Switching_Function Parent;
	private int Max_Intentions_at_time;
	private int Active_Intentions;
	private TType_Intention_Evaluated Evaluation_Pre_Conditions_Result;
	private TType_Intention_Evaluated Evaluation_Post_Conditions_Result;
	private TType_Intention_Evaluated Evaluation_Desire_Satisfation;

	public TOption_Advancement_Evaluation(TExecutive_Switching_Function Owner)
	{
		this.Parent = Owner;
		
		this.Active_Intentions = 0;
		this.Max_Intentions_at_time = 1;
		this.Evaluation_Desire_Satisfation = TType_Intention_Evaluated.Not_Satisfied;
	}
	
	public void Initialize_Active_Intentions()
	{
		this.Active_Intentions = 0;
	}
	
	public void Evaluate_Pre_Conditions(TIntention Selected_Intention, 
						HashMap<String, TBelief> Uninhibited_Beliefs)
	{
		/**
		 * By now, we consider only one Selected Intention to pursue at time
		 */
		
//		int Active_Intentions = 0;
		
		TType_Intention_Evaluated Evaluated_Result = null;
		TType_Intention_Evaluated Evaluated_Post_Condition_Result = null;
		TType_Intention_Evaluated Evaluated_Pre_Condition_Result = null;
		
		this.Active_Intentions++;
		/**
		 * if the Active_Intentions is > Max_Intentions_at_time, it means that any other intentions
		 * will be not considered
		 */
		if( this.Active_Intentions > this.Max_Intentions_at_time)
		{
			Evaluated_Result = TType_Intention_Evaluated.Not_to_Execute ;
		}
		else
		{
			Evaluated_Pre_Condition_Result = this.Check_Pre_conditions(Selected_Intention, Uninhibited_Beliefs);
//			Evaluated_Post_Condition_Result = this.Check_Post_conditions(Selected_Intention, Uninhibited_Beliefs);
			
//			if(Evaluated_Post_Condition_Result == Evaluated_Pre_Condition_Result)
			/*
			 * ERRORE: TO DO
			 * non posso valutare prima la postcondizione.
			 * La posso valutare solo alla fine.
			 * Questo mi serve per dire che possono passare all'azione successiva?
			 * Per ora L'if qui sotto lo commento. In via di discussione
			 */
			
//			if( Evaluated_Post_Condition_Result )
//			{
//				Evaluated_Result = Evaluated_Pre_Condition_Result;
//			}
//			else
//			{
//				/**
//				 * It means that Post_conditions and Pre_conditions are different
//				 * so there is a problem in belief. The intention will have to be deleted
//				 */
//				Evaluated_Result = TType_Intention_Evaluated.To_Delete;
//			}
			Evaluated_Result = Evaluated_Pre_Condition_Result;

		}
		this.Evaluation_Pre_Conditions_Result = Evaluated_Result;
	}
	
	
	
	private TType_Intention_Evaluated Check_Pre_conditions(TIntention Selected_Intention,
			HashMap<String, TBelief> Uninhibited_Beliefs)
	{
		TType_Intention_Evaluated Result = null;
		
//		int Active_Intentions = 0;
		TAttentional_Desire Attentional_Desire = null;
		TOption Option = null;
		TAction Active_Action = null;
		TPlan Plan = null;
		Boolean Truth = true;
		int Active_Option = 0;
		int Active_Action_ID = 0;
		int Actions_Number = 0;
//		for(TIntention Intention: Selected_Intentions)
		{
//			Active_Intentions++;
			/**
			 * if the Active_Intentions is > Max_Intentions_at_time, it means that any other intentions
			 * will be not considered
			 */
			Attentional_Desire = (TAttentional_Desire) Selected_Intention.Get_Active_Desire();
			
			if (Attentional_Desire == null)
			{
				Result = TType_Intention_Evaluated.To_Delete ;
			}
			else
			{
				ArrayList<TOption> Options = Attentional_Desire.Get_List_Options();
				if( Options.size() >0 )
				{
					Active_Option = Selected_Intention.Get_Selected_Option_Id();
					
					Option = Attentional_Desire.Get_List_Options().get( Active_Option );
					
					Actions_Number = Option.Get_Plan_Actions().size();
					
					Active_Action_ID = Option.Get_ID_Current_Action();
					
					/**
					 * if Active_Action > Actions_Number - 1, it means that the Intention will be satisfied
					 */
					if( Active_Action_ID > Actions_Number - 1 )
					{
						Result = TType_Intention_Evaluated.Satisfied ;
					}
//					else if ( Active_Action_ID == 0)
//					{
//						Result = TType_Intention_Evaluated.To_Execute ;
//					}
					else
					{
						Active_Action = Option.Get_Plan_Actions().get( Active_Action_ID );
						
						// For each Precondition
						for(TPredicate Precondition: Active_Action.Get_Pre_conditions() )
						{
							//if Precondition has a related Belief, I check its truth
							if( Precondition.Get_Linked_Belief() != null)
							{
//								Truth = Truth && Precondition.Get_Linked_Belief().Is_Truth(); 
								/**
								 * This Precondition has:
								 * 1- a Subject (an Object): this is another Predicate, with subject and 
								 * 		object (its value)
								 * 2- a relationship between the subject and the object (the value to compare)
								 * 3- an Object (a value) to compare to the value of the subject 
								 */
//								if ( Precondition.Get_Linked_Belief().Is_Truth() )
//								{
//									Truth = Truth && this.Check_Condition_Truth(Precondition);
//								}
//								else
//								{
//									Truth = false;
//								}
								Truth = Truth && Precondition.Get_Linked_Belief().Is_Truth();
							}
							else
							{
								/**
								 * Else, This Precondition has:
								 * 1- a Subject (an Object): this is another Predicate, with subject and 
								 * 		object (its value)
								 * 2- a relationship between the subject and the object (the value to compare)
								 * 3- an Object (a value) to compare to the value of the subject 
								 */
								Truth = Truth && this.Check_Condition_Truth(Precondition);
							}
						}
						
						if( Truth )
						{
							Result = TType_Intention_Evaluated.To_Execute ;
						}
						else
						{
							Result = TType_Intention_Evaluated.To_Delete ;
						}
					}
				}
				else
				{
					/**
					 * It means that some thing was wrong, for example the deleting
					 * of the intention and the options from the desire while the 
					 * Executive Switching was almost performing the action
					 */
					Result = TType_Intention_Evaluated.To_Delete ; 
				}	
			}
			
			
		}
		
		return Result;
	}

	private TType_Intention_Evaluated Check_Post_conditions(TIntention Selected_Intention,
			HashMap<String, TBelief> Uninhibited_Beliefs)
	{
		TType_Intention_Evaluated Result = null;
		
//		int Active_Intentions = 0;
		TAttentional_Desire Attentional_Desire = null;
		TOption Option = null;
		TAction Active_Action = null;
		TPlan Plan = null;
		/***
		 * I preset the Truth to true, because if Action has not preconditions and postconditions,
		 * then it is true
		 */
		Boolean Truth = true;
		int Active_Option = 0;
		int Action_ID_to_Check = 0;
		int Actions_Number_of_Option = 0;
		
//		for(TIntention Intention: Selected_Intentions)
		{
//			Active_Intentions++;
			/**
			 * if the Active_Intentions is > Max_Intentions_at_time, it means that any other intentions
			 * will be not considered
			 */
			Attentional_Desire = (TAttentional_Desire) Selected_Intention.Get_Active_Desire();
			Active_Option = Selected_Intention.Get_Selected_Option_Id();
			if(Attentional_Desire == null || Active_Option <0 ) 
			{
				return TType_Intention_Evaluated.To_Delete ;
			}
			Option = Attentional_Desire.Get_List_Options().get( Active_Option );
			
			Actions_Number_of_Option = Option.Get_Plan_Actions().size();
			
			//Agent has to check last (the previous) action 
			Action_ID_to_Check = Option.Get_ID_Current_Action();// - 1;
			
			/**
			 * if Active_Action > Actions_Number - 1, it means that the Intention will be satisfied
			 */
//			if( Action_ID_to_Check  < 0 )
			if( Action_ID_to_Check - 1  < 0 )
//			if( Action_ID_to_Check  < 1 )
			{
				/**
				 * it means that the action does not exist(!), because the real action to execute is
				 * the first action of the plan and it have not a previous action
				 */
				Result = TType_Intention_Evaluated.Correct_Executed ;
			}
			else if ( Action_ID_to_Check  >= Actions_Number_of_Option )
			{
				/**
				 * da capire: è stata soddisfatta o deve controllare che le postconditions sono corrette?
				 * Questo perchè, la successiva chiamata a Check_Preconditions avrà un'azione da eseguire
				 * che non esiste
				 */
				
				/**
				 * it means that the real action does not exist, because the previous action was
				 * the last action of the plan and the intention was satisfied
				 */
				Result = TType_Intention_Evaluated.Full_Executed ;
			}
			else
			{
				Active_Action = Option.Get_Plan_Actions().get( Action_ID_to_Check - 1 );
				
				// For each Precondition
				for(TPredicate Postcondition: Active_Action.Get_Post_conditions() )
				{
					//if Precondition has a related Belief, I check its truth
					if( Postcondition.Get_Linked_Belief() != null)
					{
//						Truth = Truth && Postcondition.Get_Linked_Belief().Is_Truth();
						/**
						 * This Precondition has:
						 * 1- a Subject (an Object): this is another Predicate, with subject and 
						 * 		object (its value)
						 * 2- a relationship between the subject and the object (the value to compare)
						 * 3- an Object (a value) to compare to the value of the subject 
						 */
//						if ( Postcondition.Get_Linked_Belief().Is_Truth() )
//						{
//							Truth = Truth && this.Check_Condition_Truth(Postcondition);
//						}
//						else
//						{
//							Truth = false;
//						}
						Truth = Truth && Postcondition.Get_Linked_Belief().Is_Truth();
					}
					else
					{
						/**
						 * Else, This Precondition has:
						 * 1- a Subject (an Object): this is another Predicate, with subject and 
						 * 		object (its value)
						 * 2- a relationship between the subject and the object (the value to compare)
						 * 3- an Object (a value) to compare to the value of the subject 
						 */
						//POSTCONDITION
						Truth = Truth && this.Check_Condition_Truth(Postcondition);
					}
				}
				
				if( Truth )
				{
					Result = TType_Intention_Evaluated.Correct_Executed ;
				}
				else
				{
					/**
					 * It means that the post condition (the belief) is not correct
					 */
					Result = TType_Intention_Evaluated.To_Delete ;
				}
				
			}
		}
		
		return Result;
	}
	
	private Boolean Check_Condition_Truth(TPredicate Condition)
	{
//		Object Subject_Value = ((TPredicate) Condition.Get_Subject()).Get_Object_Complement();
		Boolean Truth = ((TBelief) Condition.Get_Subject()).Is_Truth();
		Object Subject_Value = 
				((TBelief) Condition.Get_Subject()).Get_Predicate().Get_Object_Complement();
		Object Value_to_Compare = null;
		switch(Condition.Get_Object_Complement())
		{
			case TBelief b ->
			{
//				Value_to_Compare = ((TBelief) Condition.Get_Object_Complement())
//						.Get_Predicate().Get_Object_Complement();
				Value_to_Compare = b.Get_Predicate().Get_Object_Complement();
			}
			case TPredicate p ->
			{
				Value_to_Compare = p.Get_Object_Complement();
			}
				
			default -> 
			{
				Value_to_Compare = Condition.Get_Object_Complement();
		    }
		}
//		if(Condition.Get_Object_Complement() instanceof TBelief)
//		{
//			Value_to_Compare = ((TBelief) Condition.Get_Object_Complement())
//						.Get_Predicate().Get_Object_Complement();
//			
//		}
//		else
//		{
//			Value_to_Compare = Condition.Get_Object_Complement();
//		}
		
		TType_Relationship Relationship = Condition.Get_Relationship();
		switch (Relationship) 
		{
	        case is:
	        case equal:
	        	Boolean result = Objects.equals(Subject_Value, Value_to_Compare); 
	            return result;
	        case is_not:
	            return !Objects.equals(Subject_Value, Value_to_Compare);
		}

	    // Se gli oggetti non sono null, non sono dello stesso tipo o non implementano Comparable,
	    // non possiamo eseguire confronti numerici.
	    if (Subject_Value == null || Value_to_Compare == null || 
	    	Subject_Value.getClass() != Value_to_Compare.getClass() || !(Subject_Value instanceof Comparable)) 
	    {
	        System.err.println("Error: Cannot perform numeric comparisons. Objects must be non-null, "
	        		+ "of the same type, and implement Comparable..");
	        return false;
	    }

	    // A questo punto, possiamo effettuare il confronto numerico
	    @SuppressWarnings("unchecked")
	    Comparable<Object> c1 = (Comparable<Object>) Subject_Value;
	    int result = c1.compareTo(Value_to_Compare);
	
	    switch (Relationship) 
	    {
		    case is_in:
	            // Controlla se obj2 è una collezione e se obj1 è contenuto in essa
	            if (Value_to_Compare instanceof Collection) {
	                Collection<?> collection = (Collection<?>) Value_to_Compare;
	                return collection.contains(Subject_Value);
	            } else {
	                System.err.println("Error: The relationship 'is_in' requires the second object to be a Collection.");
	                return false;
	            }
	        case is_out:
	            // Controlla se obj2 è una collezione e se obj1 non è contenuto in essa
	            if (Value_to_Compare instanceof Collection) {
	                Collection<?> collection = (Collection<?>) Value_to_Compare;
	                return !collection.contains(Subject_Value);
	            } else {
	                System.err.println("Error: The relationship 'is_out' requires the second object to be a Collection.");
	                return false;
	            }
	        case minor:
	            return result < 0;
	        case major:
	            return result > 0;
	        case minor_equal:
	            return result <= 0;
	        case major_equal:
	            return result >= 0;
	        default:
	            // Gestione dei casi non supportati
	            System.err.println("Error: Relationship type '" + Relationship + "' is not supported for this comparison.");
	            return false;
			
		}
	}
	
	public TType_Intention_Evaluated Get_Evaluation_Pre_Conditions_Result()
	{
		return this.Evaluation_Pre_Conditions_Result;
	}
	
	public TType_Intention_Evaluated Get_Evaluation_Post_Conditions_Result()
	{
		return this.Evaluation_Post_Conditions_Result;
	}
	
	public TType_Intention_Evaluated Get_Evaluation_Desire_Satisfation_Result()
	{
		return this.Evaluation_Desire_Satisfation;
	}
	
	public void Evaluate_Post_Conditions(TIntention Selected_Intention, 
			HashMap<String, TBelief> Uninhibited_Beliefs)
	{
	/**
	* By now, we consider only one Selected Intention to pursue at time
	*/
	
	//int Active_Intentions = 0;
	
	TType_Intention_Evaluated Evaluated_Result = null;
	TType_Intention_Evaluated Evaluated_Post_Condition_Result = null;
	TType_Intention_Evaluated Evaluated_Pre_Condition_Result = null;
	
//	this.Active_Intentions++;
	/**
	* if the Active_Intentions is > Max_Intentions_at_time, it means that any other intentions
	* will be not considered
	*/
	if( this.Active_Intentions > this.Max_Intentions_at_time)
	{
		Evaluated_Result = TType_Intention_Evaluated.Not_to_Execute ;
	}
	else
	{
		Evaluated_Post_Condition_Result = this.Check_Post_conditions(Selected_Intention, Uninhibited_Beliefs);
		Evaluated_Result = Evaluated_Post_Condition_Result;
	
	}
	this.Evaluation_Post_Conditions_Result = Evaluated_Result;
	}
	
	public void Evaluate_Desire_Satisfaction(TIntention Selected_Intention, 
			HashMap<String, TBelief> Uninhibited_Beliefs)
	{
		if(Selected_Intention.Get_Selected_Option().Actions_Completed())
		{
			if(Selected_Intention.Get_Attentional_Desire() instanceof 
					TEpistemic_Desire)
			{
				this.Evaluation_Desire_Satisfation = TType_Intention_Evaluated.Satisfied;
			}
				
		}
	}
	
}
