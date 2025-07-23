package Catalina_Simulation;

import java.io.Serializable;
import java.time.LocalDateTime;

public class TBelief implements Serializable{
	
	private LocalDateTime Time_Stamp;
	private Object Information_Source; 
	private Integer Belief_ID;
	private String Name;
	private TType_Beliefs Type_Belief; 
	
	protected transient TPredicate Predicate;
	private String Predicate_Name;
	private boolean Truth;
	
	public TBelief(String name, TPredicate predicate, Boolean truth, Object information_Source,
			LocalDateTime time_stamp, TType_Beliefs type_Belief)
	{
		this.Name = name;
////		this.Predicate = new TPredicate(predicate.get_Subject(), predicate.get_Relationship(), 
//										predicate.get_Object_Complement());
		this.Predicate = predicate;
		this.Predicate.set_Linked_Belief(this);
		this.Predicate_Name = predicate.Get_Name();
		this.Truth = truth;
		this.Time_Stamp = time_stamp;
		this.Information_Source = information_Source;
		this.Type_Belief = type_Belief;
	}
	
	public TBelief(String name, String predicate_Name, Boolean truth, Object information_Source,
			LocalDateTime time_stamp, TType_Beliefs type_Belief)
	{
		this.Name = name;
		this.Predicate = null;
		this.Predicate_Name = predicate_Name;
		this.Truth = truth;
		this.Time_Stamp = time_stamp;
		this.Information_Source = information_Source;
		this.Type_Belief = type_Belief;
	}
	
	public TPredicate Get_Predicate() {
		return this.Predicate;
	}

	public Boolean Is_Truth() {
		return this.Truth;
	}

	public void Set_Truth(boolean truth) {
		this.Truth = truth;
	}

	public Integer Get_Belief_ID() {
		return Belief_ID;
	}

	public String Get_Name() {
		return Name;
	}

	public void Set_Belief_ID(Integer belief_ID) {
		Belief_ID = belief_ID;
	}	
	
	
	public TType_Beliefs Get_Type_Belief() {
		return this.Type_Belief;
	}
	
	public void Set_Predicate(TPredicate predicate)
	{
		this.Predicate = predicate;
		this.Predicate_Name = predicate.Get_Name();
		this.Predicate.set_Linked_Belief(this);
		
	}
	
	public String Get_Predicate_name()
	{
		return this.Predicate_Name;
	}
	
	public LocalDateTime Get_Time_stamp()
	{
		return this.Time_Stamp;
	}
	
	public Object Get_Information_Source()
	{
		return this.Information_Source;
	}
	
	public void Set_Information_Source(Object information_Source)
	{
		 this.Information_Source = information_Source;
	}
	
	public void Set_Time_Stamp(LocalDateTime time_stamp)
	{
		 this.Time_Stamp = time_stamp;
	}
	
	public Object Get_Truth()
	{
		return this.Truth;
	}
	
	public void Update_Belief(TPredicate predicate, Boolean truth)
	{
		this.Predicate = predicate;
		this.Truth = truth;
	}
}