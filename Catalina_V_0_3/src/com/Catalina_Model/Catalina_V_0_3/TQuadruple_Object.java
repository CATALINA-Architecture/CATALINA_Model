package com.Catalina_Model.Catalina_V_0_3;

public class TQuadruple_Object {

	private volatile Object Object_First;
	private volatile Object Object_Second;
	private volatile Object Object_Third;
	private volatile Object Object_Fourth;
	
	public TQuadruple_Object()
	{
		this.Object_First = null;
		this.Object_Second = null;
		this.Object_Third = null;
		this.Object_Fourth = null;
	}
	
	public Object Get_Object_First()
	{
		return this.Object_First;
	}
	
	public Object Get_Object_Second()
	{
		return this.Object_Second;
	}
	
	public Object Get_Object_Third()
	{
		return this.Object_Third;
	}
	
	public Object Get_Object_Fourth()
	{
		return this.Object_Fourth;
	}
	
	public void Set_Object_First(Object object)
	{
		this.Object_First = object; 
	}
	
	public void Set_Object_Second(Object object)
	{
		this.Object_Second = object; 
	}
	
	public void Set_Object_Third(Object object)
	{
		this.Object_Third = object; 
	}
	
	public void Set_Object_Fourth(Object object)
	{
		this.Object_Fourth = object; 
	}
	
	public void Clear()
	{
		this.Object_First = null;
		this.Object_Second = null;
		this.Object_Third = null;
		this.Object_Fourth = null;
	}
}
