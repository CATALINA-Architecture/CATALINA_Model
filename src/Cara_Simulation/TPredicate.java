package Cara_Simulation;

import java.io.Serializable;

public class TPredicate implements Serializable{

	private static final long serialVersionUID = 1L;
	private String Name;
	private Integer Predicate_ID;
	private Object Subject;
	private TType_Relationship Relationship;
	private Object Object_Complement;
	private transient TBelief Linked_Belief;
	private String Linked_Belief_Name;
	
	public void Clear()
	{
		this.Subject = null;
		this.Object_Complement = null;
	}
	
	public Object get_Subject() {
		return this.Subject;
	}

	public TType_Relationship get_Relationship() {
		return this.Relationship;
	}

	public Object get_Object_Complement() {
		return this.Object_Complement;
	}
	public void set_Object_Complement(Object object_complement) {
		this.Object_Complement = object_complement;
	}
	
	public TPredicate(String name, Object subject, TType_Relationship relationship, Object object_complement)
	{
		this.Name = name;
		this.Subject = subject;
		this.Relationship = relationship;
		this.Object_Complement = object_complement;	
		this.Linked_Belief = null;
		this.Linked_Belief_Name = "";
	}
	
	public Integer get_Predicate_ID() {
		return Predicate_ID;
	}

	public String get_Name() {
		return Name;
	}

	public void set_Predicate_ID(Integer predicate_ID) {
		Predicate_ID = predicate_ID;
	}
	
	public void set_Linked_Belief(TBelief belief) {
		this.Linked_Belief = belief;
		this.Linked_Belief_Name = belief.get_Name();
	}
	
	public void set_Linked_Belief_Name(String Linked_Belief_Name) {
		this.Linked_Belief_Name = Linked_Belief_Name;
	}

	public String get_Linked_Belief_Name() {
		return Linked_Belief_Name;
	}
	
	public TBelief Get_Linked_Belief()
	{
		return this.Linked_Belief;
	}
	
	public void Set_Subject(Object subject) {
		this.Subject = subject;
	}
	
	
}
