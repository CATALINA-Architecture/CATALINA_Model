package Catalina_Simulation;

public class TQuality_Standing_Desire extends TStanding_Desire{
	
	private TType_Quality_Desire Type_Quality_Goal;
	private transient TPredicate Constraint;
	private String Constraint_Name;
	//private String Name;
	
	public TQuality_Standing_Desire(String name, TPredicate constraint, TType_Quality_Desire type_Quality_Goal, Double saliency, Double reward,
			Double relax_Preference)
	{
		
		super(name, saliency, reward, relax_Preference);
		
		this.Constraint = constraint;
		this.Constraint_Name = constraint.Get_Name();
		this.Type_Quality_Goal = type_Quality_Goal;
	}
	//Reward da cambiare in Priority
	//Considerare la Saliency come Priority
	public TQuality_Standing_Desire(String name, String constraint_name, TType_Quality_Desire type_Quality_Goal, Double saliency, Double reward,
			Double relax_Preference)
	{
		super(name, saliency, reward, relax_Preference);
				this.Constraint = null;
		this.Constraint_Name = constraint_name;
		this.Type_Quality_Goal = type_Quality_Goal;
	}
	
	public Boolean Check_Quality_Level(Route rotta)
	{
		return true;
	}

	public TType_Quality_Desire Get_Type_Quality_Goal() {
		return Type_Quality_Goal;
	}

	public TPredicate Get_Constraint() {
		return Constraint;
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