package com.Catalina_Model.Catalina_V_0_3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.stream.Collectors;

import com.Catalina_Model.Catalina_V_0_3.TBelief_Inhibition_Function_Handler.TBelief_Inhibition_Function;

public class TBelief_Inhibition {

	private TExecutive_Inhibition_Function Owner;
	private TGlobal_Workspace Global_Workspace;
	private volatile ArrayList<TBelief> Inhibited_Beliefs;
	private volatile ArrayList<TBelief> Uninhibited_Beliefs;
	private volatile ArrayList<TBelief> Pre_Conditions;
//	private HashSet<String> List_Important_Belief_Type;
	private volatile HashMap<String, ArrayList<String>> List_Beliefs_to_Always_Consider;

	private TBelief_Inhibition_Function_Handler Epistemic_Belief_Inhibition_Handler;
	private TBelief_Inhibition_Function_Handler Practical_Belief_Inhibition_Handler;
	private TBelief_Inhibition_Function_Handler Practical_Precondition_Inhibition_Handler;

	public TBelief_Inhibition(TExecutive_Inhibition_Function Owner) {
		this.Owner = Owner;
		this.Global_Workspace = this.Owner.Get_Global_Workspace();

		this.Uninhibited_Beliefs = new ArrayList<TBelief>();
		this.Inhibited_Beliefs = new ArrayList<TBelief>();
		this.Pre_Conditions = new ArrayList<TBelief>();
		this.List_Beliefs_to_Always_Consider = new HashMap<String, ArrayList<String>>();
//		this.List_Beliefs_to_Always_Consider_for_Epistemic_Desire = new HashMap<String, ArrayList<String>>();
//		this.List_Beliefs_to_Always_Consider_for_Practical_Desire = new HashMap<String, ArrayList<String>>();

		this.Practical_Belief_Inhibition_Handler = new TBelief_Inhibition_Function_Handler();
		this.Practical_Precondition_Inhibition_Handler = new TBelief_Inhibition_Function_Handler();

		this.Epistemic_Belief_Inhibition_Handler = new TBelief_Inhibition_Function_Handler();
	}
	
	public void Compute_Preconditions()
	{
		ArrayList<TPractical_Desire> Practical_Desires = new ArrayList<TPractical_Desire>();
//		this.Global_Workspace.Get_All_Practical_Desires()
	}

	public void Execute(ArrayList<TIntention> Selected_Intentions) {
//		ArrayList<TBelief> All_Beliefs = new ArrayList<TBelief>();
//		All_Beliefs.addAll( this.Global_Workspace.Get_All_Beliefs_from_MM() );
//		HashMap<String, TBelief> Map_All_Beliefs = this.Global_Workspace.Get_All_Map_Beliefs_from_MM();

		HashMap<String, TBelief> All_Map_Beliefs = new HashMap<String, TBelief>(
				this.Global_Workspace.Get_All_Map_Beliefs_from_MM());

		HashSet<TBelief> List_Uninhibited_Beliefs = new HashSet<TBelief>();
		HashSet<TBelief> List_Pre_conditions = new HashSet<TBelief>();

		HashSet<TBelief> List_Inhibited_Beliefs = new HashSet<TBelief>();

		for (TIntention Selected_Intention : Selected_Intentions) 
		{
			TAttentional_Desire Attentional_Desire = (TAttentional_Desire) Selected_Intention.Get_Active_Desire();
			if (Attentional_Desire != null) {
				switch (Attentional_Desire) {
				// PRACTICAL DESIRES
				case TPractical_Desire Practical_Desire -> 
				{

					/**
					 * I get the list of Preconditions to use for the specific Practical Desire
					 */
//					HashSet<String> preconditions_name = new HashSet<String>();
//					preconditions_name
//							.addAll(this.Practical_Precondition_Inhibition_Handler.Execute_Function_For_Desire(
//									Practical_Desire.Get_Name(), Practical_Desire, All_Map_Beliefs));
//
//					if ((preconditions_name != null) && (preconditions_name.size() > 0)) {
//						HashSet<TBelief> precondition_to_save = new HashSet<TBelief>();
//						for (String Belief_Name : preconditions_name) {
//							TBelief Precondition = All_Map_Beliefs.get(Belief_Name);
//							if (Precondition != null) {
//								precondition_to_save.add(Precondition);
//							}
//						}
//						List_Pre_conditions.addAll(precondition_to_save);
//					}
					ArrayList<String> precondition_names = new ArrayList<String>();
					if(Practical_Desire.Get_Trigger_Condition() != null)
					{
//						List_Pre_conditions.addAll( 
//								Practical_Desire.Get_Trigger_Condition().Get_Beliefs() );
						precondition_names.addAll(
								Practical_Desire.Get_Trigger_Condition().
											Get_Beliefs_Names());
					}
					precondition_names.remove(null);
					
					if ( precondition_names.size() > 0 ) 
					{
						HashSet<TBelief> Beliefs_to_save = new HashSet<TBelief>();
						for (String Belief_Name : precondition_names) 
						{
							TBelief Belief = All_Map_Beliefs.get(Belief_Name);
							if (Belief != null) 
							{
								List_Pre_conditions.add(Belief);
							}
						}
						List_Uninhibited_Beliefs.addAll(List_Pre_conditions);
					}
					

					/**
					 * I get the list of beliefs to use for the specific Practical Desire
					 */

					ArrayList<String> beliefs_name = new ArrayList<String>();
					beliefs_name.addAll(
							this.Practical_Belief_Inhibition_Handler.
								Execute_Function_For_Desire(
									Practical_Desire.Get_Name(), 
									Practical_Desire, 
									All_Map_Beliefs) );

					if ((beliefs_name != null) && (beliefs_name.size() > 0)) 
					{
						HashSet<TBelief> Beliefs_to_save = new HashSet<TBelief>();
						for (String Belief_Name : beliefs_name) 
						{
							TBelief Belief = All_Map_Beliefs.get(Belief_Name);
							if (Belief != null) 
							{
								Beliefs_to_save.add(Belief);
							}
						}
						List_Uninhibited_Beliefs.addAll(Beliefs_to_save);
					}
				}

				// EPISTEMIC DESIRES
				case TEpistemic_Desire Epistemic_Desire -> {
					// I Insert the Stimulus
					List_Uninhibited_Beliefs.add(Epistemic_Desire.Get_Belief());

					/**
					 * I get the list of beliefs to use for the specific Epistemic Desire
					 */
					ArrayList<String> beliefs_name = new ArrayList<String>();
					beliefs_name.addAll(this.Epistemic_Belief_Inhibition_Handler.Execute_Function_For_Desire(
							Epistemic_Desire.Get_Belief().Get_Type_Belief(), Epistemic_Desire, All_Map_Beliefs));

					if ((beliefs_name != null) && (beliefs_name.size() > 0)) 
					{
						HashSet<TBelief> Beliefs_to_save = new HashSet<TBelief>();
						for (String Belief_Name : beliefs_name) 
						{
							TBelief Belief = All_Map_Beliefs.get(Belief_Name);
							if (Belief != null) 
							{
								Beliefs_to_save.add(Belief);
							}
						}
						List_Uninhibited_Beliefs.addAll(Beliefs_to_save);
					}
				}
				default -> throw new IllegalArgumentException("Unexpected value: " + Attentional_Desire);
				}
			}

		}

		/**
		 * Now, I insert The belief that are alway important to insert in Uninhibited
		 * Belief List
		 */
		// I get each important belief to keep in GlobalWorkspace
		HashSet<String> List_Critical_Beliefs_Type = new HashSet<String>();
		List_Critical_Beliefs_Type.addAll(this.Owner.Get_List_Important_Belief_Type());

//		for(String Belief_Name: List_Critical_Beliefs_Type)
//		{
//			TBelief belief = All_Map_Beliefs.get( Belief_Name );
//			Unihibited_Beliefs.add( belief );
//			if( belief.Get_Predicate().Get_Subject() instanceof TBelief)
//			{
//				Unihibited_Beliefs.add( (TBelief) belief.Get_Predicate().Get_Subject() );
//			}
//		}

		// This is changed with previous code
//		for(TBelief belief: All_Beliefs)
//		{
//			/**
//			 * If List_Important_Belief_Type contains belief.Get_Type_Belief, it means that
//			 * the belief must be included in Unihibited_Beliefs AND the object of this 
//			 * belief is another belief and must also be included in Unihibited_Beliefs
//			 */
//			if ( List_Critical_Beliefs_Type.contains( belief.Get_Type_Belief()))
//			{
//				Unihibited_Beliefs.add( belief );
//				if( belief.Get_Predicate().Get_Subject() instanceof TBelief)
//				{
//					Unihibited_Beliefs.add( (TBelief) belief.Get_Predicate().Get_Subject() );
//				}
//			}
//		}

		HashSet<String> Beliefs_to_Always_Consider = new HashSet<String>();
		List_Critical_Beliefs_Type.remove(null);
		Beliefs_to_Always_Consider.addAll(List_Critical_Beliefs_Type);

		for (String List_Name : this.List_Beliefs_to_Always_Consider.keySet()) {
			ArrayList<String> List_beliefs = this.List_Beliefs_to_Always_Consider.get(List_Name);
			Beliefs_to_Always_Consider.addAll(List_beliefs);
		}

		if (Beliefs_to_Always_Consider.size() > 0) {
//			HashSet<TBelief> Belief_to_save = Beliefs_to_Always_Consider.stream() // 1. Prende lo stream di String (i nomi)
//				    .map(All_Map_Beliefs::get)             // 2. Per ogni nome, cerca il TBelief in Map_Beliefs
//				    .filter(Objects::nonNull)          // 3. Rimuove eventuali 'null' (se un nome non c'era)
//				    .collect(Collectors.toCollection(HashSet::new)); // 4. Raccoglie i risultati in un HashSet
//			Unihibited_Beliefs.addAll( Belief_to_save );

			for (String Belief_Name : Beliefs_to_Always_Consider) {
				TBelief belief = All_Map_Beliefs.get(Belief_Name);
				List_Uninhibited_Beliefs.add(belief);
				if (belief.Get_Predicate().Get_Subject() instanceof TBelief) {
					List_Uninhibited_Beliefs.add((TBelief) belief.Get_Predicate().Get_Subject());
				}
			}
		}

		/**
		 * I don't be too sure of these three follow lines code. I must consider better.
		 * For now, I'm just commenting on them
		 */
//		HashSet<TBelief> All_Belief_to_Keep = new HashSet<TBelief>();
//		All_Belief_to_Keep.addAll(Pre_conditions);
//		All_Belief_to_Keep.addAll(Unihibited_Beliefs);

		List_Inhibited_Beliefs.addAll(All_Map_Beliefs.values());
		List_Inhibited_Beliefs.removeAll(List_Uninhibited_Beliefs);

		this.Inhibited_Beliefs.clear();
		this.Inhibited_Beliefs.addAll(List_Inhibited_Beliefs);
		
		this.Uninhibited_Beliefs.clear();
		this.Uninhibited_Beliefs.addAll(List_Uninhibited_Beliefs);

		this.Pre_Conditions.clear();
		this.Pre_Conditions.addAll(List_Pre_conditions);
	}

//	public void Execute_old(ArrayList<TIntention> Selected_Intentions)
//	{
//		ArrayList<TBelief> All_Beliefs = new ArrayList<TBelief>();
//		All_Beliefs.addAll( this.Global_Workspace.Get_All_Beliefs_from_MM() );
//		
//		HashMap<String, TBelief> All_Map_Beliefs = new HashMap<String, TBelief>(
//				 this.Global_Workspace.Get_All_Map_Beliefs_from_MM() );
//				
//		HashSet<TBelief> Unihibited_Beliefs = new HashSet<TBelief>();
//		HashSet<TBelief> Pre_conditions= new HashSet<TBelief>();
//
//		HashSet<TBelief> Ihibited_Beliefs = new HashSet<TBelief>();
//		Ihibited_Beliefs.addAll( All_Beliefs);
//		
//		for (TIntention Selected_Intention: Selected_Intentions)
//		{
//			TAttentional_Desire Attentional_Desire = (TAttentional_Desire) Selected_Intention.Get_Active_Desire();
//			if( Attentional_Desire != null)
//			{
//				switch(Attentional_Desire)
//				{
//					// PRACTICAL DESIRES
//					case TPractical_Desire Practical_Desire ->
//					{
//						//I insert each belief of the Trigger Condition of the Practical Desire
//						Unihibited_Beliefs.addAll( Practical_Desire.Get_Trigger_Condition().Get_Beliefs() );
//						for (TBelief Belief: Practical_Desire.Get_Trigger_Condition().Get_Beliefs())
//						{
//							Pre_conditions.add(Belief);
//							/**
//							 * The Predicate of the Belief can be a TBelief, so
//							 * I insert this in Pre_conditions and in Unihibited_Beliefs
//							 */
//							if(Belief.Predicate.Get_Subject() instanceof TBelief)
//							{
//								Pre_conditions.add( (TBelief) Belief.Predicate.Get_Subject());
//								Unihibited_Beliefs.add( (TBelief) Belief.Predicate.Get_Subject());
//							}
//						}
////						Pre_conditions.addAll( Practical_Desire.Get_Trigger_Condition().Get_Beliefs() );
//						
//						//I insert each belief of the Final State of the Practical Desire
//						Unihibited_Beliefs.addAll( Practical_Desire.Get_Final_State().Get_Beliefs());
//						
//						/**
//						 * For each Selected Intention I insert all Belief related to each 
//						 * Predicate of Pre_Conditions and Post_Conditions of each Action
//						 * of the selected Option for that Intention
//						 * 
//						 */
//						Integer Selected_Option_ID = Selected_Intention.Get_Selected_Option_Id();
//						if ( Selected_Option_ID >= 0)
//						{
//							TOption Selected_Option = 
//									Practical_Desire.Get_List_Options().get( Selected_Option_ID);
//							for(TAction Action: Selected_Option.Get_Plan_Actions())
//							{
//								for(TPredicate Predicate: Action.Get_Pre_conditions())
//								{
////									//Unihibited_Beliefs.add( (TBelief) Belief.Predicate.Get_Subject());
//									if( Predicate.Get_Linked_Belief() != null)
//									{
//										Unihibited_Beliefs.add( Predicate.Get_Linked_Belief() );
////										//qui
////										System.out.println("Pre_condition1: "+Predicate);
////										System.out.println("Pre_condition1: "+Predicate.Get_Linked_Belief());
//										
//									}
//									else if ( (Predicate.Get_Subject() != null) &&
//											 (Predicate.Get_Subject() instanceof TBelief))
//									{
//										Unihibited_Beliefs.add( (TBelief) Predicate.Get_Subject() );
////										//qui
////										System.out.println("Pre_condition2: "+Predicate);
////										System.out.println("Pre_condition2: "+Predicate.Get_Linked_Belief());
//									}
////										
//								}
//								for(TPredicate Predicate: Action.Get_Post_conditions())
//								{
////									//Unihibited_Beliefs.add( (TBelief) Belief.Predicate.Get_Subject());
//									if( Predicate.Get_Linked_Belief() != null)
//									{
//										Unihibited_Beliefs.add( Predicate.Get_Linked_Belief() );
////										//qui
////										System.out.println("Postcondition1: "+Predicate);
////										System.out.println("Postcondition1: "+Predicate.Get_Linked_Belief());
//										
//										
//									}
//									else if ( (Predicate.Get_Subject() != null) &&
//											 (Predicate.Get_Subject() instanceof TBelief))
//									{
//										Unihibited_Beliefs.add( (TBelief) Predicate.Get_Subject() );
////										//qui
////										System.out.println("Postcondition2: "+Predicate);
////										System.out.println("Postcondition2: "+Predicate.Get_Linked_Belief());
//									}
////										
//								}
//							}
//						}
//						
//						
//						/**
//						 * I get the list of beliefs to use for the specific Practical Desire
//						 */
//						ArrayList<String> List_beliefs_name = 
//								this.Practical_Belief_Inhibition_Handler.
//									Execute_Function_For_Desire( Practical_Desire.Get_Name(), 
//											Selected_Intention, All_Map_Beliefs);
//						
//						HashSet<TBelief> Belief_to_save = List_beliefs_name.stream() // 1. Prende lo stream di String (i nomi)
//							    .map(All_Map_Beliefs::get)             // 2. Per ogni nome, cerca il TBelief in Map_Beliefs
//							    .filter(Objects::nonNull)          // 3. Rimuove eventuali 'null' (se un nome non c'era)
//							    .collect(Collectors.toCollection(HashSet::new)); // 4. Raccoglie i risultati in un HashSet
//						Unihibited_Beliefs.addAll( Belief_to_save );
//						
//					}
//					
//					//EPISTEMIC DESIRES
//					case TEpistemic_Desire Epistemic_Desire ->
//					{
//						Unihibited_Beliefs.add( Epistemic_Desire.Get_Belief() );
//						
//						/**
//						 * I get the list of beliefs to use for the specific Epistemic Desire
//						 */
//						ArrayList<String> List_beliefs_name = 
//								this.Epistemic_Belief_Inhibition_Handler.
//									Execute_Function_For_Desire(
//											Epistemic_Desire.Get_Belief().Get_Type_Belief(),
//											Selected_Intention, All_Map_Beliefs);
//						
//						HashSet<TBelief> Belief_to_save = List_beliefs_name.stream() // 1. Prende lo stream di String (i nomi)
//							    .map(All_Map_Beliefs::get)             // 2. Per ogni nome, cerca il TBelief in Map_Beliefs
//							    .filter(Objects::nonNull)          // 3. Rimuove eventuali 'null' (se un nome non c'era)
//							    .collect(Collectors.toCollection(HashSet::new)); // 4. Raccoglie i risultati in un HashSet
//						
//						Unihibited_Beliefs.addAll( Belief_to_save );
//						
//					}
//					default -> throw new IllegalArgumentException("Unexpected value: " + Attentional_Desire);
//				}
//
//				//I insert the Constraint of each Green Desires of the Practical Desire
//				for(TGreen_Desire Green_Desire: Attentional_Desire.Get_List_Green_Standing_Desire())
//				{
//					Unihibited_Beliefs.add( Green_Desire.Get_Constraint().Get_Linked_Belief());
//				}
//				
//				//I insert the Constraint of each Quality Desires of the Practical Desire
//				for(TQuality_Desire Quality_Desire: Attentional_Desire.Get_List_Quality_Standing_Desire())
//				{
//					Unihibited_Beliefs.add( Quality_Desire.Get_Constraint().Get_Linked_Belief());
//				}
//				
//				/**
//				 *  I insert each belief related to precondition and postcondition of each action of the
//				 *  selected option for the desire of Intention 
//				 */
//				
////				TOption Selected_Option = null; 
////				if (Selected_Option_Id > -1)
//				if( Attentional_Desire.Get_List_Options().size() >0 )
//				{
//					int Selected_Option_Id = Selected_Intention.Get_Selected_Option_Id();
//					TOption Selected_Option = Attentional_Desire.Get_List_Options().get( Selected_Option_Id );	
//				
//					for(TAction Action: Selected_Option.Get_Plan_Actions())
//					{
//						
//		//				if(Action.Get_Precondition().Get_Linked_Belief() != null)
//		//				{
//		//					Unihibited_Beliefs.add( Action.Get_Precondition().Get_Linked_Belief());
//		//				}
//						
//						/**
//						 * Pre_conditions are predicates in which the object_complement is a value, 
//						 * while the subject is a predicate itself to be considered and it is this 
//						 * predicate that is connected to the belief to be kept in memory as 
//						 * an uninhibited belief.
//						 */
//						for(TPredicate Pre_condition: Action.Get_Pre_conditions())
//						{
//							if(Pre_condition.Get_Linked_Belief() != null)
//							{
//								Unihibited_Beliefs.add( 
//										((TPredicate) Pre_condition.Get_Subject()).Get_Linked_Belief()
//										);
//								;
//							}
//						}
//		
//		//				if(Action.Get_Post_Condition().Get_Linked_Belief() != null)
//		//				{
//		//					Unihibited_Beliefs.add( Action.Get_Post_Condition().Get_Linked_Belief());
//		//				}
//						/**
//						 * Post_conditions are predicates in which the object_complement is a value, 
//						 * while the subject is a predicate itself to be considered and it is this 
//						 * predicate that is connected to the belief to be kept in memory as 
//						 * an uninhibited belief.
//						 */
//						for(TPredicate Post_condition: Action.Get_Post_conditions())
//						{
//							if(Post_condition.Get_Linked_Belief() != null)
//							{
//								try {
//								Unihibited_Beliefs.add( 
//										((TPredicate) Post_condition.Get_Subject()).Get_Linked_Belief()
//										);
//								}
//								catch (Exception e)
//								{
//									
//									System.out.println(": Interrupted while waiting or running.");
//									System.out.println("Captured Error: " + e.getMessage());
//									e.printStackTrace();
//									Thread.currentThread().interrupt();
//								}
//							}
//						}
//					}
//				}
//			}
//			
//		}
//		
//		/**
//		 * Now, I insert The belief that are alway important to insert in Uninhibited Belief List
//		 */
//		//I get each important belief to keep in GlobalWorkspace
//		HashSet<String> List_Important_Belief_Type = new HashSet<String>();
//		List_Important_Belief_Type.addAll( this.Parent.Get_List_Important_Belief_Type() );
//		for(TBelief belief: All_Beliefs)
//		{
//			/**
//			 * If List_Important_Belief_Type contains belief.Get_Type_Belief, it means that
//			 * the belief must be included in Unihibited_Beliefs AND the object of this 
//			 * belief is another belief and must also be included in Unihibited_Beliefs
//			 */
//			if ( List_Important_Belief_Type.contains( belief.Get_Type_Belief()))
//			{
//				Unihibited_Beliefs.add( belief );
//				if( belief.Get_Predicate().Get_Subject() instanceof TBelief)
//				{
//					Unihibited_Beliefs.add( (TBelief) belief.Get_Predicate().Get_Subject() );
//				}
//			}
//		}
//		
//		for(String List_Name: this.List_Beliefs_to_Always_Consider.keySet())
//		{
//			ArrayList<String> List_beliefs = this.List_Beliefs_to_Always_Consider.get( List_Name );
//			
//			HashSet<TBelief> Belief_da_Salvare = List_beliefs.stream() // 1. Prende lo stream di String (i nomi)
//				    .map(All_Map_Beliefs::get)             // 2. Per ogni nome, cerca il TBelief in Map_Beliefs
//				    .filter(Objects::nonNull)          // 3. Rimuove eventuali 'null' (se un nome non c'era)
//				    .collect(Collectors.toCollection(HashSet::new)); // 4. Raccoglie i risultati in un HashSet
//		}
//		
//		/**
//		 * I don't be too sure of these three follow lines code.
//		 * I must consider better. 
//		 * For now, I'm just commenting on them
//		 */
////		HashSet<TBelief> All_Belief_to_Keep = new HashSet<TBelief>();
////		All_Belief_to_Keep.addAll(Pre_conditions);
////		All_Belief_to_Keep.addAll(Unihibited_Beliefs);
//		
//		Ihibited_Beliefs.removeAll( Unihibited_Beliefs );
//		
//		this.Uninhibited_Beliefs.clear();
//		this.Uninhibited_Beliefs.addAll( Unihibited_Beliefs );
//		
//		this.Inhibited_Beliefs.clear();
//		this.Inhibited_Beliefs.addAll( Ihibited_Beliefs );
//		
//		this.Pre_Conditions.clear();
//		this.Pre_Conditions.addAll(Pre_conditions);
//		this.Pre_Conditions.size();
//	}

	public ArrayList<TBelief> Get_Uninhibited_Beliefs() {
		ArrayList<TBelief> result = new ArrayList<TBelief>();
		result.addAll(this.Uninhibited_Beliefs);
		this.Uninhibited_Beliefs.clear();

		return result;
	}

	public ArrayList<TBelief> Get_Inhibited_Beliefs() {
		ArrayList<TBelief> result = new ArrayList<TBelief>();
		result.addAll(this.Inhibited_Beliefs);
		this.Inhibited_Beliefs.clear();

		return result;
	}

	public ArrayList<TBelief> Get_Pre_Conditions() {
		ArrayList<TBelief> result = new ArrayList<TBelief>();
		result.addAll(this.Pre_Conditions);
		this.Pre_Conditions.clear();

		return result;
	}
	// private ArrayList<TBelief> Inhibited_Beliefs;
//	private ArrayList<TBelief> Computed_Pre_Conditions;

	public void Add_Beliefs_to_Always_Consider(String List_Name, ArrayList<String> Beliefs_Names) {
		this.List_Beliefs_to_Always_Consider.put(List_Name, Beliefs_Names);
	}

	public void Register_Practical_Belief_Inhibition_Function(String Name, TBelief_Inhibition_Function func) {

		this.Practical_Belief_Inhibition_Handler.Register_Belief_Inhibition_Function(Name, func);
	}

	public boolean Unregister_Practical_Belief_Inhibition_Function(String Name) {
		return this.Practical_Belief_Inhibition_Handler.Unregister_Belief_Inhibition_Function(Name);
	}

	public void Register_Practical_Preconditions_Inhibition_Function(String Name, TBelief_Inhibition_Function func) {
		this.Practical_Precondition_Inhibition_Handler.Register_Belief_Inhibition_Function(Name, func);
	}

	public boolean Unregister_Practical_Preconditions_Inhibition_Function(String Name) {
		return this.Practical_Precondition_Inhibition_Handler.Unregister_Belief_Inhibition_Function(Name);
	}

	public void Register_Epistemic_Belief_Inhibition_Function(String Name, TBelief_Inhibition_Function func) {

		this.Epistemic_Belief_Inhibition_Handler.Register_Belief_Inhibition_Function(Name, func);
	}

	public boolean Unregister_Epistemic_Belief_Inhibition_Function(String Name) {
		return this.Epistemic_Belief_Inhibition_Handler.Unregister_Belief_Inhibition_Function(Name);
	}
}