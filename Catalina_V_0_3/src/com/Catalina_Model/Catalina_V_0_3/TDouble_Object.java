package com.Catalina_Model.Catalina_V_0_3;

public class TDouble_Object {

	private volatile Object Object_First;
	private volatile Object Object_Second;
	
	public Object Get_Object_First() {
		return Object_First;
	}
	public void Set_Object_First(Object object_First) {
		Object_First = object_First;
	}
	public Object Get_Object_Second() {
		return Object_Second;
	}
	public void Set_Object_Second(Object object_Second) {
		Object_Second = object_Second;
	}
	
	@Override
    public String toString() {
        // Stampa il risultato della .toString() di entrambi gli oggetti.
        // Gestisce automaticamente i 'null' (stampando "null").
        return "TDouble_Object[" +
               "Object_First=" + Object_First +
               ", Object_Second=" + Object_Second +
               ']';
    }
	
}