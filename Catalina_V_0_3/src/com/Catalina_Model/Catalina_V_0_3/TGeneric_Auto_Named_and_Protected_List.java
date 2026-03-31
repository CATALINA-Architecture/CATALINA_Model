package com.Catalina_Model.Catalina_V_0_3;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicLong;

public class TGeneric_Auto_Named_and_Protected_List<T> extends TGeneric_Protected_List<T> 
{
	protected final AtomicLong Inc_Number = new AtomicLong(0);

	public TGeneric_Auto_Named_and_Protected_List() {
        super();
    }

    public TGeneric_Auto_Named_and_Protected_List(ArrayList<T> initialList)
    {
        super(initialList);
    }
    
    public long Get_Inc_Number() {
    	return this.Inc_Number.get();
    }
    
    @Override 
    public void Add(T element) 
    {
        super.modifyList(list -> 
        {
            list.add(element);
            this.Inc_Number.incrementAndGet();
        });
    }
    
    @Override 
    public void Add_All(Collection<T> elements) 
    {
        super.modifyList(list -> 
        {
            if (elements != null && !elements.isEmpty()) 
            {
                list.addAll(elements);
                this.Inc_Number.getAndAdd(elements.size());
            }
        });
    }
    
    @Override 
    public void Set_List(ArrayList<T> newList) 
    {
        super.modifyList(list -> 
        {
            list.clear();

            long elementsAdded = 0;
            if (newList != null && !newList.isEmpty()) 
            {
                list.addAll(newList);
                elementsAdded = newList.size();
            }

            this.Inc_Number.getAndAdd(elementsAdded);
        });
    }

}
