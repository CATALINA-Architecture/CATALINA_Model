package Cara_Simulation;

import java.time.Instant;
import java.time.LocalDateTime;

public class TPerception {
	
	private TPredicate Predicate;
	private Object Source;
	private LocalDateTime Time_Stamp;
	
	public TPredicate get_Predicate() {
		//TPredicate Temp_Predicate = new TPredicate(Predicate.get_Subject(), Predicate.get_Relationship(), 
		//		Predicate.get_Object_Complement());
		return this.Predicate;
	}

	public Object get_Source() {
		return Source;
	}

	public LocalDateTime get_Time_Stamp() {
		return Time_Stamp;
	}
	
	public TPerception(TPredicate predicate, Object source)
	{
		this.Time_Stamp = LocalDateTime.now();
		this.Predicate = predicate;
		this.Source = source;
//		this.Predicate = new TPredicate(predicate.get_Subject(), predicate.get_Relationship(), 
//				predicate.get_Object_Complement());
		
	}
}
