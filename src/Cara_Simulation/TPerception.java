package Cara_Simulation;

import java.time.Instant;
import java.time.LocalDateTime;

public class TPerception {
	
	private TTriple_Object Perceived_Data;
	private Object Source;
	private LocalDateTime Time_Stamp;
	
	public TTriple_Object get_Perceived_Data() {

		return this.Perceived_Data;
	}

	public Object get_Source() {
		return Source;
	}

	public LocalDateTime get_Time_Stamp() {
		return Time_Stamp;
	}
	
	public TPerception(LocalDateTime Time, TTriple_Object perceived_Data, Object source)
	{
		//this.Time_Stamp = LocalDateTime.now();
		this.Time_Stamp = Time;
		this.Perceived_Data = perceived_Data;
		this.Source = source;
	}
}
