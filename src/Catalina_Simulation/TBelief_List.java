package Catalina_Simulation;

import java.util.ArrayList;

public class TBelief_List {
	
	private Integer Belief_Counter;
	
	public ArrayList<TBelief> Beliefs;
	
	
	public TBelief_List()
	{
		ArrayList<TBelief> Beliefs = new ArrayList<TBelief>();
		this.Belief_Counter = 0;
	}
	
	public void Add_Belief(TBelief belief)
	{
		Integer Temp_Belief_Counter = Belief_Counter;
		Temp_Belief_Counter++;
		belief.Set_Belief_ID(Belief_Counter);
		if (this.Beliefs == null)
		{
			this.Beliefs = new ArrayList<TBelief>();
		}
		Beliefs.add(belief);
		Belief_Counter = Temp_Belief_Counter;
	}
	
	public void Remove_Belief(TBelief belief)
	{
		Beliefs.remove(belief);
		Belief_Counter--;
	}
	
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
			temp_predicate = this.Beliefs.get( Counter ).Get_Predicate();
			if( ( temp_predicate.Get_Subject() == predicate.Get_Subject() ) && 
					( temp_predicate.Get_Relationship() == predicate.Get_Relationship() ))
					{
						temp_predicate.set_Object_Complement( temp_predicate.Get_Object_Complement() );
						if( truth != null )
						{
							this.Beliefs.get(Counter).Set_Truth( truth );
						}
						if(( saliency != null ) && ( this.Beliefs.get( Counter ) instanceof TStimulus) )
						{
							TStimulus SBelief = (TStimulus) this.Beliefs.get( Counter );
							SBelief.Update_Saliency(saliency);
						}
						break;
					}
			Counter++;
		}
	}
	
	public Integer Get_Belief_Counter()
	{
		return this.Belief_Counter;
	}
}