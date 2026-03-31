package com.Catalina_Model.Catalina_V_0_3;

public class TBoolean_Protected_Object extends TGeneric_Protected_Object<Boolean> {

    /**
     * Costruttore che inizializza il valore booleano protetto.
     *
     * @param initialValue Il valore booleano iniziale.
     */
    public TBoolean_Protected_Object(Boolean initialValue) 
    {
        super(initialValue); // Chiama il costruttore della superclasse GenericProtectedObject<Boolean>
    }
    
    public void Set_True()
    {
    	this.Write(true);
    }
    
    public void Set_False()
    {
    	this.Write(false);
    	
    }
    
    public Boolean Read_And_Clear()
    {
    	return this.Read_And_Clear( false );
    }

}
