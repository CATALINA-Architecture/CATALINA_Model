package Cara_Simulation;

import java.io.Serializable;
import java.time.LocalDateTime;

public class TBelief implements Serializable{
	
	private LocalDateTime Time_stamp;
	private Object Information_Source; 
	private Integer Belief_ID;
	private String Name;
	private TType_Beliefs Type_Belief; 
	
	protected transient TPredicate Predicate;
	private String Predicate_name;
	private boolean Truth;
	
	
	
	public TBelief(String name, TPredicate predicate, Boolean truth, Object information_Source,
			LocalDateTime time_stamp, TType_Beliefs type_Belief)
	{
		this.Name = name;
////		this.Predicate = new TPredicate(predicate.get_Subject(), predicate.get_Relationship(), 
//										predicate.get_Object_Complement());
		this.Predicate = predicate;
		this.Predicate.set_Linked_Belief(this);
		this.Predicate_name = predicate.get_Name();
		this.Truth = truth;
		this.Time_stamp = time_stamp;
		this.Information_Source = information_Source;
		this.Type_Belief = type_Belief;
	}
	
	public TBelief(String name, String predicate_Name, Boolean truth, Object information_Source,
			LocalDateTime time_stamp, TType_Beliefs type_Belief)
	{
		this.Name = name;
////		this.Predicate = new TPredicate(predicate.get_Subject(), predicate.get_Relationship(), 
//										predicate.get_Object_Complement());
		this.Predicate = null;
		//this.Predicate.set_Linked_Belief(this);
		this.Predicate_name = predicate_Name;
		this.Truth = truth;
		this.Time_stamp = time_stamp;
		this.Information_Source = information_Source;
		this.Type_Belief = type_Belief;
	}
	
	public TPredicate get_Predicate() {
		return this.Predicate;
	}

	public Boolean is_Truth() {
		return this.Truth;
	}

	public void set_Truth(boolean truth) {
		this.Truth = truth;
	}

	public Integer get_Belief_ID() {
		return Belief_ID;
	}

	public String get_Name() {
		return Name;
	}

	public void set_Belief_ID(Integer belief_ID) {
		Belief_ID = belief_ID;
	}	
	
	
	public TType_Beliefs get_Type_Belief() {
		return this.Type_Belief;
	}
	
	public void set_Predicate(TPredicate predicate)
	{
		this.Predicate = predicate;
		this.Predicate_name = predicate.get_Name();
		this.Predicate.set_Linked_Belief(this);
		
	}
	
	
//	public void set_Predicate_Name(String name)
//	{
//		this.Predicate_name = name;
//	
//	}
	
	public String get_Predicate_name()
	{
		return this.Predicate_name;
	}
	
	public LocalDateTime get_Time_stamp()
	{
		return this.Time_stamp;
	}
	
	public Object get_Information_Source()
	{
		return this.Information_Source;
	}
	
	public void set_Information_Source(Object information_Source)
	{
		 this.Information_Source = information_Source;
	}
	
	public void set_Time_stamp(LocalDateTime time_stamp)
	{
		 this.Time_stamp = time_stamp;
	}
	
	public Object get_Truth()
	{
		return this.Truth;
	}
	
	public void Update_Belief(TPredicate predicate, Boolean truth)
	{
		this.Predicate = predicate;
		this.Truth = truth;
	}
	
}
