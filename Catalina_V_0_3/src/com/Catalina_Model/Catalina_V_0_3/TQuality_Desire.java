package com.Catalina_Model.Catalina_V_0_3;


public class TQuality_Desire extends TDesire{

	private volatile String Type_Quality_Desire;
	private volatile TPredicate Constraint;
	private volatile String Constraint_Name;
	
	public TQuality_Desire(String name, TPredicate constraint, String type_Quality_Goal, Double saliency, Double reward,
			Double relax_Preference)
	{
		
		super(name, saliency, reward, relax_Preference);
		
		this.Constraint = constraint;
		this.Constraint_Name = constraint.Get_Name();
		this.Type_Quality_Desire = type_Quality_Goal;
	}
	
	public TQuality_Desire(String name, String constraint_name, String type_Quality_Goal, Double saliency, Double reward,
			Double relax_Preference)
	{
		super(name, saliency, reward, relax_Preference);
		
		this.Constraint = null;
		this.Constraint_Name = constraint_name;
		this.Type_Quality_Desire = type_Quality_Goal;
	}
	
	public String Get_Type_Quality_Goal() {
		return this.Type_Quality_Desire;
	}

	public TPredicate Get_Constraint() {
		return this.Constraint;
	}
	
	public void set_Constraint(TPredicate constraint)
	{
		this.Constraint = constraint;
		this.Constraint_Name = constraint.Get_Name();
	}
	
	public String Get_Constraint_Name()
	{
		return this.Constraint_Name;
	}
}