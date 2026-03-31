package com.Catalina_Model.Catalina_V_0_3;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class TPerception {

	private volatile ArrayList<Object> Perceived_Data;
	private volatile String Source;
	private volatile LocalDateTime Time_Stamp;
	
	public ArrayList<Object> Get_Perceived_Data() {

		return this.Perceived_Data;
	}

	public String Get_Source() {
		return Source;
	}

	public LocalDateTime Get_Time_Stamp() {
		return Time_Stamp;
	}
	
	protected void Set_Source(String source)
	{
		this.Source = source;
	}
	
	public TPerception(LocalDateTime Time, ArrayList<Object> perceived_Data, String source)
	{
		//this.Time_Stamp = LocalDateTime.now();
		this.Time_Stamp = Time;
		this.Perceived_Data = new ArrayList<Object>();
		this.Perceived_Data.addAll( perceived_Data );
		this.Source = source;
	}
}