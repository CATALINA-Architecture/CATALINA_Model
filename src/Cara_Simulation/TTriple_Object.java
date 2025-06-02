package Cara_Simulation;

public class TTriple_Object {
	
	private Object Object_First;
	private Object Object_Second;
	private Object Object_Third;
	
	public TTriple_Object()
	{
		this.Object_First = null;
		this.Object_Second = null;
		this.Object_Third = null;
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
}