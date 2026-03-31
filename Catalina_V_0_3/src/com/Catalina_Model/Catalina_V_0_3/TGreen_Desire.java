package com.Catalina_Model.Catalina_V_0_3;


public class TGreen_Desire extends TDesire {

	private volatile String Type_Green_Desire;
	private volatile TPredicate Constraint;
	private volatile String Constraint_Name;
	private volatile Double Fee;
	
	public void Clear()
	{
		this.Constraint.Clear();		
	}
	
	public String Get_Type_Green_Standing_Desire() {
		return Type_Green_Desire;
	}

	public Double Get_Fee() {
		return Fee;
	}

	public TGreen_Desire(String name, TPredicate constraint, String type_Green_Goal, 
			Double saliency, Double reward, Double Relax_Preference, Double fee)
	{
		super(name, saliency, reward, Relax_Preference);
		this.Fee = fee;
		this.Type_Green_Desire = type_Green_Goal; //now it is the only enumerated value;
		this.Constraint = constraint;
		this.Constraint_Name = constraint.Get_Name();
	}
	
	public TGreen_Desire(String name, String constraint_name, String type_Green_Goal, 
			Double saliency, Double reward, Double Relax_Preference, Double fee)
	{
		super(name, saliency, reward, Relax_Preference);
		this.Fee = fee;
		this.Type_Green_Desire = type_Green_Goal; //now it is the only enumerated value;
		this.Constraint = null;
		this.Constraint_Name = constraint_name;
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
	
	public TPredicate Get_Constraint()
	{
		return this.Constraint;
	}
}