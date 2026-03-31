package com.Catalina_Model.Catalina_V_0_3;

public class TLong_Protected_Object extends TGeneric_Protected_Object<Long> 
{

    /**
     * Costruttore che inizializza il valore booleano protetto.
     *
     * @param initialValue Il valore booleano iniziale.
     */
    public TLong_Protected_Object(Long initialValue) 
    {
        super(initialValue); // Chiama il costruttore della superclasse GenericProtectedObject<Boolean>
    }
    
    public Long Increment_And_Get() 
    {
        return super.updateAndGet(currentValue -> 
        {
            return (currentValue != null ? currentValue + 1L : 1L);
        });
    }

    public Long Add_And_Get(Long value) 
    {
        return super.updateAndGet(currentValue -> 
        {
            return (currentValue != null ? currentValue + value : value);
        });
    }
}