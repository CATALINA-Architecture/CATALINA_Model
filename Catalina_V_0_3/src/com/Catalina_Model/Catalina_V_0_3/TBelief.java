package com.Catalina_Model.Catalina_V_0_3;

import java.time.LocalDateTime;

public class TBelief {

	private volatile LocalDateTime Time_Stamp;
	/**
	 * The Information_Source can be a String (e.g. "Me", "Developer", "User",..)
	 * or an Object (e.g. Sensor)  
	 */
	private volatile Object Information_Source; 
	private volatile Long Belief_ID;
	private volatile String Name;
	private volatile String Type_Belief;
	
	protected volatile TPredicate Predicate;
	private volatile String Predicate_Name;
	private volatile boolean Truth;
	
	public TBelief(String name, TPredicate predicate, Boolean truth, Object information_Source,
			LocalDateTime time_stamp, String type_Belief)
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
			LocalDateTime time_stamp, String type_Belief)
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

	public Long Get_Belief_ID() {
		return this.Belief_ID;
	}

	public String Get_Name() {
		return Name;
	}
	
	public String Set_Name(String Value) {
		return this.Name = Value;
	}

	public void Set_Belief_ID(Long belief_ID) {
		Belief_ID = belief_ID;
	}	
	
	
	public String Get_Type_Belief() {
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
	
	@Override
    public String toString() {
        return "OID@" + System.identityHashCode(this) + " " +
        	   "TBelief[" +
               "ID=" + Belief_ID +
               ", Name='" + Name + '\'' +
               ", Type='" + Type_Belief + '\'' +
               ", Predicate='" + Predicate_Name + '\'' +
               ", Truth=" + Truth +
               ", Time=" + Time_Stamp +
               ", Source=" + Information_Source +
               ']';
    }
	
	/**
	 * Costruttore di Copia (Copy Constructor).
	 * Crea una copia profonda di un TBelief esistente.
	 *
	 * @param original L'oggetto TBelief da clonare.
	 */
	public TBelief(TBelief original) 
	{
	    // 1. Copia tutti i campi immutabili e primitivi
	    this.Name = original.Name;
	    this.Belief_ID = original.Belief_ID; // (Vedi nota su ID unico)
	    this.Type_Belief = original.Type_Belief;
	    this.Truth = original.Truth;
	    this.Time_Stamp = original.Time_Stamp; 
	    this.Predicate_Name = original.Predicate_Name;
	    this.Information_Source = original.Information_Source; // (Vedi nota su shallow copy)
	    
	    // 2. Logica di "Deep Copy" per TPredicate
	    if (original.Predicate != null) 
	    {
	        // 3. CHIAMA IL COSTRUTTORE di TPredicate.
	        // Questo crea un *nuovo* TPredicate con un *nuovo* TGeneric_Protected_Object
	        // e un Linked_Belief nullo.
	        this.Predicate = new TPredicate(
	            original.Predicate.Get_Name(), 
	            original.Predicate.Get_Subject(), 
	            original.Predicate.Get_Relationship(), 
	            original.Predicate.Get_Object_Complement() // Chiama .Read() sull'originale
	        );
	        
	        // 4. Stabilisce il legame bi-direzionale con il *nuovo* clone (this)
	        this.Predicate.set_Linked_Belief(this); 
	    } 
	    else 
	    {
	        this.Predicate = null;
	    }
	}

	/**
	 * Crea e restituisce un clone (copia profonda) di questo TBelief.
	 *
	 * @return Un nuovo TBelief identico a questo, con il suo TPredicate slegato dall'originale.
	 */
	public TBelief Clone()
	{
	    return new TBelief(this);
	}
}