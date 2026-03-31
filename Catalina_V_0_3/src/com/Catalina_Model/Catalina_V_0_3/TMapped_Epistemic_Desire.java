package com.Catalina_Model.Catalina_V_0_3;

import java.util.ArrayList;

public class TMapped_Epistemic_Desire {
	
	public String Type_Stimulus;
	public Double Saliency;
	public Double Reward;
	public Double Relax_Preference;
	public String Trigger_condotion_formula;
	public ArrayList<String> Trigger_Condition_Names;
	public ArrayList<String> List_Green_Desires_Name;
	public ArrayList<String> List_Quality_Desires_Name;
	
	public ArrayList<String> List_Beliefs_for_Reasoner;
	public ArrayList<String> List_Regions_for_Reasoner;
	

	public TMapped_Epistemic_Desire(String type_Stimulus, Double saliency, 
			String trigger_condotion_formula, ArrayList<String> trigger_Condition_Names, 
			Double reward, 
			Double relax_Preference, ArrayList<String> list_Green_Desires_Name, 
			ArrayList<String> list_Quality_Desires_Name,
			ArrayList<String> list_Beliefs_for_Reasoner,
			ArrayList<String> list_Regions_for_Reasoner) 
	{
		this.Type_Stimulus = type_Stimulus;
		this.Saliency = saliency;
		this.Trigger_condotion_formula = trigger_condotion_formula;
		
		this.Reward = reward;
		this.Relax_Preference = relax_Preference;
		this.Trigger_Condition_Names = new ArrayList<String>();
		if( trigger_Condition_Names != null)
		{
			this.Trigger_Condition_Names.addAll( trigger_Condition_Names );
		}
		
		this.List_Green_Desires_Name = new ArrayList<String>();
        if (list_Green_Desires_Name != null) 
        {
            this.List_Green_Desires_Name.addAll(list_Green_Desires_Name);
        }
		
        this.List_Quality_Desires_Name = new ArrayList<>();
        if (list_Quality_Desires_Name != null) 
        {
            this.List_Quality_Desires_Name.addAll(list_Quality_Desires_Name);
        }
        
        this.List_Beliefs_for_Reasoner = new ArrayList<>();
        if (list_Beliefs_for_Reasoner != null) 
        {
            this.List_Beliefs_for_Reasoner.addAll(list_Beliefs_for_Reasoner);
        }
        
        this.List_Regions_for_Reasoner = new ArrayList<>();
        if (list_Regions_for_Reasoner != null) 
        {
            this.List_Regions_for_Reasoner.addAll(list_Regions_for_Reasoner);
        }
	}
	
	@Override
    public String toString() {
        return "TMapped_Epistemic_Desire{" +
               "Type_Stimulus='" + Type_Stimulus + '\'' +
               ", Saliency=" + Saliency +
               ", Reward=" + Reward +
               ", Relax_Preference=" + Relax_Preference +
               ", Green_Desires=" + List_Green_Desires_Name +
               ", Quality_Desires=" + List_Quality_Desires_Name +
               "}";
    }
}
