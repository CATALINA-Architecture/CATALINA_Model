package Catalina_Simulation;

import java.util.ArrayList;

public class TPredicate_List {
	
	private Integer Predicate_Counter;
	
	public ArrayList<TPredicate> Predicates;
	
	
	public TPredicate_List()
	{
		ArrayList<TPredicate> Predicates = new ArrayList<TPredicate>();
		this.Predicate_Counter = 0;
	}
	
	public void Add_Predicate(TPredicate predicate)
	{
		Integer Temp_Predicate_Counter = Predicate_Counter;
		Temp_Predicate_Counter++;
		predicate.set_Predicate_ID(Predicate_Counter);
		Predicates.add(predicate);
		Predicate_Counter = Temp_Predicate_Counter;
		
	}
	
	public void Remove_Predicate(TPredicate predicate)
	{
		Predicates.remove(predicate);
		Predicate_Counter--;
	}
}