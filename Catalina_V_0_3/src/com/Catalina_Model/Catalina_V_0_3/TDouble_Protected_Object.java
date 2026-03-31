package com.Catalina_Model.Catalina_V_0_3;

public class TDouble_Protected_Object extends TGeneric_Protected_Object<Double> 
{

    /**
     * Costruttore che inizializza il valore booleano protetto.
     *
     * @param l Il valore booleano iniziale.
     */
    public TDouble_Protected_Object(Double l) 
    {
        super(l); // Chiama il costruttore della superclasse GenericProtectedObject<Boolean>
    }
    
    public Double Increment_And_Get() 
    {
        return super.updateAndGet(currentValue -> 
        {
            return (currentValue != null ? currentValue + 1 : 1);
        });
    }

    public Double Add_And_Get(Double value) 
    {
        return super.updateAndGet(currentValue -> 
        {
            return (currentValue != null ? currentValue + value : value);
        });
    }
}