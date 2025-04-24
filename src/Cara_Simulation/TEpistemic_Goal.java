package Cara_Simulation;

import java.util.ArrayList;

public class TEpistemic_Goal extends TAttentional_Goal{
	
	private TBelief Belief;
	private String Belief_Name;
	
	public TEpistemic_Goal(String name, TBelief belief, Double saliency, Double reward, Double relax_Preference,
			ArrayList<TGreen_Goal> list_Green_Goal, ArrayList<TQuality_Goal> list_Quality_Goal)
	{
		super(name, saliency, reward, relax_Preference, list_Green_Goal, list_Quality_Goal);
		this.Belief = belief;
		this.Belief_Name = belief.get_Name();
		
	}
	
	public TEpistemic_Goal(String name, String belief_Name, Double saliency, Double reward, Double relax_Preference,
			ArrayList<String> list_Green_Goal_Name, ArrayList<String> list_Quality_Goal_Name)
	{
		super(name, list_Green_Goal_Name, list_Quality_Goal_Name, saliency, reward, relax_Preference );
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
	
	public TBelief get_Belief() {
		return Belief;
	}
	
	public void set_Belief(TBelief belief) {
		this.Belief = belief;
		this.Belief_Name = belief.get_Name();
	}
	
	public String get_Belief_Name()
	{
		return this.Belief_Name;
	}
	
	

}