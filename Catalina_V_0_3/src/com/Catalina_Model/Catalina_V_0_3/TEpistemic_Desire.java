package com.Catalina_Model.Catalina_V_0_3;

import java.util.ArrayList;


public class TEpistemic_Desire extends TAttentional_Desire{

	private volatile TBelief Belief;
	private volatile String Belief_Name;
	
	public TEpistemic_Desire(String name, TBelief belief, TPropositional_Formula trigger_Condition, Double saliency, Double reward, 
			Double relax_Preference, ArrayList<TGreen_Desire> list_Green_Desire, 
			ArrayList<TQuality_Desire> list_Quality_Desire,
			ArrayList<String> beliefs_Reasoner, 
			ArrayList<String> regions_Reasoner)
	{
		super(name, saliency, trigger_Condition,reward, relax_Preference, list_Green_Desire, 
				list_Quality_Desire, beliefs_Reasoner, regions_Reasoner);
		this.Belief = belief;
		this.Belief_Name = belief.Get_Name();
	}
	
	public TEpistemic_Desire(String name, TBelief belief, TMapped_Epistemic_Desire Mapped_Epistemic_Desire )
	{
		super(name, Mapped_Epistemic_Desire.Trigger_condotion_formula,
				Mapped_Epistemic_Desire.Trigger_Condition_Names,
				Mapped_Epistemic_Desire.List_Green_Desires_Name, Mapped_Epistemic_Desire.List_Quality_Desires_Name, 
				Mapped_Epistemic_Desire.Saliency, Mapped_Epistemic_Desire.Reward, 
				Mapped_Epistemic_Desire.Relax_Preference,
				Mapped_Epistemic_Desire.List_Beliefs_for_Reasoner,
				Mapped_Epistemic_Desire.List_Regions_for_Reasoner);
		this.Belief = belief;
		this.Belief_Name = belief.Get_Name();
	}
	
	public TEpistemic_Desire(String name, String belief_Name, 
			String trigger_condotion_formula, ArrayList<String> trigger_Condition_Names,
			Double saliency, Double reward, Double relax_Preference,
			ArrayList<String> list_Green_Standing_Desire_Name, 
			ArrayList<String> list_Quality_Standing_Desire_Name,
			ArrayList<String> beliefs_Reasoner, 
			ArrayList<String> regions_Reasoner
			)
	{
		super(name, trigger_condotion_formula, trigger_Condition_Names,
				list_Green_Standing_Desire_Name, list_Quality_Standing_Desire_Name, saliency, reward, 
				relax_Preference, beliefs_Reasoner, regions_Reasoner );
		this.Belief_Name = belief_Name;
		this.Belief = null;
	}
	
	/**
	 * TO DO DEVELOPMENT, (WHAT IS ITS MEANING?
	 * @param Belief_Name
	 * @return
	 */
	public Boolean Discover(String Belief_Name)
	{
		return true;
	}
	
	public TBelief Get_Belief() {
		return Belief;
	}
	
	public void Set_Belief(TBelief belief) {
		this.Belief = belief;
		this.Belief_Name = belief.Get_Name();
	}
	
	public String Get_Belief_Name()
	{
		return this.Belief_Name;
	}
	
	@Override
    public String toString() {
        // Stampa il nome della Belief associata e poi
        // richiama la toString() della classe genitore TAttentional_Desire.
        return "TEpistemic_Desire[" +
        		super.toString()+
               "Belief_Name='" + Belief_Name + '\'' +
               "] ";// + super.toString();
    }
}