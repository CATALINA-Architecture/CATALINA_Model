package Cara_Simulation;

public class TGreen_Goal extends TGoal{
	
	private TType_Quality_Goal Type_Quality_Goal; //I use this for previous coding
	private TType_Green_Goal Type_Green_Goal;
	private transient TPredicate Constraint;
	private String Constraint_Name;
	private Double Fee;
	
	public void Clear()
	{
		this.Constraint.Clear();		
	}
	
	
	
	public TType_Quality_Goal get_Type_Quality_Goal() {
		return Type_Quality_Goal;
	}



	public TType_Green_Goal get_TType_Green_Goal() {
		return Type_Green_Goal;
	}



	public Double get_Fee() {
		return Fee;
	}

	public TGreen_Goal(String name, TPredicate constraint, TType_Green_Goal type_Green_Goal, Double saliency, Double reward, Double fee)
	{
		super(name, saliency, reward, null);
		this.Fee = fee;
		this.Type_Quality_Goal = TType_Quality_Goal.TGQ_Locomotive; //I use this for previous coding. I have to rearrange
		this.Type_Green_Goal = Type_Green_Goal.TGG_Locomotive; //now it is the only enumerated value;
		this.Constraint = constraint;
		this.Constraint_Name = constraint.get_Name();
	}
	
	public TGreen_Goal(String name, String constraint_name, TType_Green_Goal type_Green_Goal, Double saliency, Double reward, Double fee)
	{
		super(name, saliency, reward, null);
		this.Fee = fee;
		
		this.Type_Quality_Goal = TType_Quality_Goal.TGQ_Locomotive; //I use this for previous coding. I have to rearrange
		this.Type_Green_Goal = Type_Green_Goal.TGG_Locomotive; //now it is the only enumerated value;
		this.Constraint = null;
		this.Constraint_Name = constraint_name;
	}
	
	/**
	 * TODO DEVELOPMENT
	 * @param rotta
	 * @return
	 */
	public Boolean Check_Green_Level(Route rotta)
	{
		return true;
	}
	
	public void set_Constraint(TPredicate constraint)
	{
		this.Constraint = constraint;
		this.Constraint_Name = constraint.get_Name();
	}
	
	public String get_Constraint_Name()
	{
		return this.Constraint_Name;
	}
	
	public TPredicate get_Constraint()
	{
		return this.Constraint;
	}

}