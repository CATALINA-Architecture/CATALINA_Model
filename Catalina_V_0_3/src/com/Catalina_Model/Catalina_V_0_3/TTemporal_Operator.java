package com.Catalina_Model.Catalina_V_0_3;

import java.time.LocalDateTime;

public class TTemporal_Operator 
{
	private volatile LocalDateTime Start_Time;
	private volatile LocalDateTime End_Time;
	private volatile TType_Temporal_Operator Type_Temporal_Operator;
	private volatile String Temporal_Operator_Name;
	
	public TTemporal_Operator()
	{
		this.Start_Time = null;
		this.End_Time = null;
		this.Type_Temporal_Operator = TType_Temporal_Operator.None;
		this.Temporal_Operator_Name = "";
	}
	
	public LocalDateTime Get_Start_Time()
	{
		return this.Start_Time;
	}
	
	public LocalDateTime Get_End_Time()
	{
		return this.End_Time;
	}
	
	public void Set_Start_Time(LocalDateTime Value)
	{
		this.Start_Time = Value;
	}
	
	public void Set_End_Time(LocalDateTime Value)
	{
		this.End_Time = Value;
	}
	
	public TType_Temporal_Operator Get_Type_Temporal_Operator()
	{
		return this.Type_Temporal_Operator;
	}
	
	public void Set_Type_Temporal_Operator(TType_Temporal_Operator Value)
	{
		this.Type_Temporal_Operator = Value;
	}
	
	public String Get_Temporal_Operator_Name()
	{
		return this.Temporal_Operator_Name;
	}
	
	public void Set_Temporal_Operator_Name(String Value)
	{
		this.Temporal_Operator_Name = Value;
	}
}
