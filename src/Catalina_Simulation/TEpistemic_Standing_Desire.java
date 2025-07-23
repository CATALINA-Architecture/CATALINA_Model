package Catalina_Simulation;

import java.util.ArrayList;

public class TEpistemic_Standing_Desire extends TAttentional_Standing_Desire{
	
	private TBelief Belief;
	private String Belief_Name;
	
	public TEpistemic_Standing_Desire(String name, TBelief belief, Double saliency, Double reward, Double relax_Preference,
			ArrayList<TGreen_Standing_Desire> list_Green_Standing_Desire, ArrayList<TQuality_Standing_Desire> list_Quality_Standing_Desire)
	{
		super(name, saliency, reward, relax_Preference, list_Green_Standing_Desire, list_Quality_Standing_Desire);
		this.Belief = belief;
		this.Belief_Name = belief.Get_Name();
	}
	
	public TEpistemic_Standing_Desire(String name, String belief_Name, Double saliency, Double reward, Double relax_Preference,
			ArrayList<String> list_Green_Standing_Desire_Name, ArrayList<String> list_Quality_Standing_Desire_Name)
	{
		super(name, list_Green_Standing_Desire_Name, list_Quality_Standing_Desire_Name, saliency, reward, relax_Preference );
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
}