package Catalina_Simulation;

public class TGreen_Standing_Desire extends TStanding_Desire{
	
	private TType_Quality_Desire Type_Quality_Goal; //I use this for previous coding
	private TType_Green_Desire Type_Green_Goal;
	private transient TPredicate Constraint;
	private String Constraint_Name;
	private Double Fee;
	
	public void Clear()
	{
		this.Constraint.Clear();		
	}
	
	public TType_Quality_Desire Get_Type_Quality_Standing_Desire() {
		return Type_Quality_Goal;
	}

	public TType_Green_Desire Get_TType_Green_Standing_Desire() {
		return Type_Green_Goal;
	}

	public Double Get_Fee() {
		return Fee;
	}

	public TGreen_Standing_Desire(String name, TPredicate constraint, TType_Green_Desire type_Green_Goal, Double saliency, Double reward, Double fee)
	{
		super(name, saliency, reward, null);
		this.Fee = fee;
		this.Type_Quality_Goal = TType_Quality_Desire.TGQ_Locomotive; //I use this for previous coding. I have to rearrange
		this.Type_Green_Goal = Type_Green_Goal.TGG_Locomotive; //now it is the only enumerated value;
		this.Constraint = constraint;
		this.Constraint_Name = constraint.Get_Name();
	}
	
	public TGreen_Standing_Desire(String name, String constraint_name, TType_Green_Desire type_Green_Goal, Double saliency, Double reward, Double fee)
	{
		super(name, saliency, reward, null);
		this.Fee = fee;
		
		this.Type_Quality_Goal = TType_Quality_Desire.TGQ_Locomotive; //I use this for previous coding. I have to rearrange
		this.Type_Green_Goal = Type_Green_Goal.TGG_Locomotive; //now it is the only enumerated value;
		this.Constraint = null;
		this.Constraint_Name = constraint_name;
	}
	

	public Boolean Check_Green_Level(Route rotta)
	{
		return true;
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