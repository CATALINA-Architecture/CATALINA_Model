package com.Catalina_Model.Catalina_V_0_3;


public class TPredicate 
{

	private volatile String Name;
	private volatile Long Predicate_ID;
	private volatile Object Subject;
	private volatile TType_Relationship Relationship;
	//private Object Object_Complement;
	private volatile TGeneric_Protected_Object<Object> Object_Complement;
	private volatile TBelief Linked_Belief;
	private volatile String Linked_Belief_Name;
	
	public void Clear()
	{
		this.Subject = null;
		this.Object_Complement = null;
	}
	
	public Object Get_Subject() 
	{
		return this.Subject;
	}

	public TType_Relationship Get_Relationship() 
	{
		return this.Relationship;
	}

	public Object Get_Object_Complement() 
	{
		return this.Object_Complement.Read();
	}
	public void set_Object_Complement(Object object_complement) 
	{
//		this.Object_Complement = object_complement;
		this.Object_Complement.Write( object_complement );
	}
	
	public TPredicate(String name, Object subject, TType_Relationship relationship, Object object_complement)
	{
		this.Name = name;
		this.Subject = subject;
		this.Relationship = relationship;
		//this.Object_Complement = object_complement;
		this.Object_Complement = new TGeneric_Protected_Object<Object>( object_complement );
		this.Linked_Belief = null;
		this.Linked_Belief_Name = "";
	}
	
	public Long Get_Predicate_ID() 
	{
		return this.Predicate_ID;
	}

	public String Get_Name() 
	{
		return this.Name;
	}

	public void set_Predicate_ID(Long predicate_ID) {
		this.Predicate_ID = predicate_ID;
	}
	
	/**
	 * This function is not always invoked, Only when we want to have 1-to-1 Predicate-to-Belief
	 * For esample: 
	 * @param belief
	 */
	public void set_Linked_Belief(TBelief belief) {
		this.Linked_Belief = belief;
		this.Linked_Belief_Name = belief.Get_Name();
	}
	
	public void set_Linked_Belief_Name(String Linked_Belief_Name) {
		this.Linked_Belief_Name = Linked_Belief_Name;
	}

	public String Get_Linked_Belief_Name() {
		return Linked_Belief_Name;
	}
	
	public TBelief Get_Linked_Belief()
	{
		return this.Linked_Belief;
	}
	
	public void Set_Subject(Object subject) {
		this.Subject = subject;
	}
	
	@Override
	public String toString() {
		// Controlla Object_Complement prima di chiamare .Read() 
		// perché il metodo Clear() può impostarlo a null.
		Object complementValue = (this.Object_Complement != null) 
								 ? this.Object_Complement.Read() 
								 : "null";

		return "OID@" + System.identityHashCode(this) + " " + 
		   "TPredicate[" +
		   "Name='" + Name + '\'' +
		   ", Predicate_ID=" + Predicate_ID +
		   ", Subject=" + Subject +
		   ", Relationship=" + Relationship +
		   ", Object_Complement=" + complementValue +
		   ", Linked_Belief_Name='" + Linked_Belief_Name + '\'' +
		   ']';
	}

}
