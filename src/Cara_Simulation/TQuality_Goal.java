package Cara_Simulation;

public class TQuality_Goal extends TGoal{
	
	private TType_Quality_Goal Type_Quality_Goal;
	private transient TPredicate Constraint;
	private String Constraint_Name;
	//private String Name;
	
	public TQuality_Goal(String name, TPredicate constraint, TType_Quality_Goal type_Quality_Goal, Double saliency, Double reward,
			Double relax_Preference)
	{
		
		super(name, saliency, reward, relax_Preference);
		
		this.Constraint = constraint;
		this.Constraint_Name = constraint.get_Name();
		this.Type_Quality_Goal = type_Quality_Goal;
	}
	//Reward da cambiare in Priority
	//Considerare la Saliency come Priority
	public TQuality_Goal(String name, String constraint_name, TType_Quality_Goal type_Quality_Goal, Double saliency, Double reward,
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

	public TType_Quality_Goal get_Type_Quality_Goal() {
		return Type_Quality_Goal;
	}

	public TPredicate get_Constraint() {
		return Constraint;
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


	
}
