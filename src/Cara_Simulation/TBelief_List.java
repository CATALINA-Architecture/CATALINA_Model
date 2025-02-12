package Cara_Simulation;

import java.util.ArrayList;

public class TBelief_List {
	
	private Integer Belief_Counter;
	
	public ArrayList<TBelief_Base> Beliefs;
	
	
	public TBelief_List()
	{
		ArrayList<TBelief_Base> Beliefs = new ArrayList<TBelief_Base>();
		this.Belief_Counter = 0;
	}
	
	public void Add_Belief(TBelief_Base belief)
	{
		//Belief_Counter++;
		Integer Temp_Belief_Counter = Belief_Counter;
		Temp_Belief_Counter++;
		belief.set_Belief_ID(Belief_Counter);
		if (this.Beliefs == null)
		{
			this.Beliefs = new ArrayList<TBelief_Base>();
		}
		Beliefs.add(belief);
		Belief_Counter = Temp_Belief_Counter;
	}
	
	public void Remove_Belief(TBelief_Base belief)
	{
		Beliefs.remove(belief);
		Belief_Counter--;
	}
	
	/*public void Update_Belief_Value(Integer belief_id, Object Value)
	{
		//Now, any Belief_Id is equal to the sequence of the isnerted beliefs,
		//but in future it can change and we must to search in all List
		TBelief_Base Belief = Beliefs.get(belief_id);
		Belief.getPredicate().set_Object_Complement(Value);
	}
	
	public void Update_Belief_Truth(Integer belief_id, boolean value)
	{
		//Now, any Belief_Id is equal to the sequence of the isnerted beliefs,
		//but in future it can change and we must to search in all List
		TBelief_Base Belief = Beliefs.get(belief_id);
	}*/
	/***
	 * Update_Belief_Truth updates the predicate and truth of a Beleif, and if the Beleif is an instance of Salient_Belief and if saliency is not null
	 * then it updates he saliency also.
	 * @param predicate
	 * @param truth
	 * @param saliency
	 */
	public void Update_Belief_Truth(TPredicate predicate, Boolean truth, Double saliency)
	{
		int Counter=0;
		TPredicate temp_predicate;
		while ( Counter > this.Beliefs.size()-1 )
		{
			temp_predicate = this.Beliefs.get( Counter ).get_Predicate();
			if( ( temp_predicate.get_Subject() == predicate.get_Subject() ) && 
					( temp_predicate.get_Relationship() == predicate.get_Relationship() ))
					{
						temp_predicate.set_Object_Complement( temp_predicate.get_Object_Complement() );
						if( truth != null )
						{
							this.Beliefs.get(Counter).set_Truth( truth );
						}
						if(( saliency != null ) && ( this.Beliefs.get( Counter ) instanceof TSalient_Belief) )
						{
							TSalient_Belief SBelief = (TSalient_Belief) this.Beliefs.get( Counter );
							SBelief.Update_Saliency(saliency);
						}
						
//						Counter = this.Beliefs.size();
						break;
					}
			Counter++;
					
		}
	}
	
	public Integer get_Belief_Counter()
	{
		return this.Belief_Counter;
	}

	
}
