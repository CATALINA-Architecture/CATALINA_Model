package com.Catalina_Model.Catalina_V_0_3;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Function;

public class TConscious_Data 
{

    private TQuadruple_Object data; // L'oggetto generico che vogliamo proteggere
    private HashSet<TBelief> Pre_conditions;
    private HashSet<TBelief> Beliefs;
    private HashSet<TAttentional_Desire> Attentional_Desires;
    private HashSet<TRegion> Regions;
    private final ReentrantReadWriteLock Lock = new ReentrantReadWriteLock();
    private final ReentrantReadWriteLock.ReadLock Read_Lock = Lock.readLock();
    private final ReentrantReadWriteLock.WriteLock Write_Lock = Lock.writeLock();

    /**
     * Costruttore che inizializza l'oggetto protetto.
     *
     * @param initialData L'oggetto iniziale di tipo T.
     */
    public TConscious_Data(TQuadruple_Object initialData) 
    {
        this.data = initialData;
        this.Beliefs = new HashSet<TBelief>();
        this.Attentional_Desires = new HashSet<TAttentional_Desire>();
        this.Regions = new HashSet<TRegion>();
        this.Pre_conditions = new HashSet<TBelief>();
    }
    
    public TConscious_Data() 
    {
        this.data = new TQuadruple_Object();
        HashSet<TBelief> preconditions = new HashSet<TBelief>();
        HashSet<TBelief> beliefs = new HashSet<TBelief>();
        HashSet<TAttentional_Desire> attentional_desires = new HashSet<TAttentional_Desire>();
        HashSet<TRegion> regions = new HashSet<TRegion>();
        
        this.data.Set_Object_First( preconditions );
        this.data.Set_Object_Second( beliefs );
        this.data.Set_Object_Third( attentional_desires );
        this.data.Set_Object_Fourth( regions );
        
        this.Beliefs = new HashSet<TBelief>();
        this.Attentional_Desires = new HashSet<TAttentional_Desire>();
        this.Regions = new HashSet<TRegion>();
        this.Pre_conditions = new HashSet<TBelief>();
    }

    /**
     * Legge l'oggetto protetto.
     * Acquisirà un blocco di lettura, consentendo letture concorrenti.
     *
     * @return L'oggetto di tipo T, incapsulato in un Optional per gestire il caso di null (anche se in questa
     * implementazione data non sarà mai null una volta inizializzato).
     */
    public TQuadruple_Object Read() 
    {
        Read_Lock.lock(); // Acquisisci il blocco di lettura
        try 
        {
            // Potresti aggiungere una piccola pausa per simulare un'operazione di lettura lunga
            // try { Thread.sleep(50); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            return this.data;
        } 
        finally 
        {
            Read_Lock.unlock(); // Rilascia il blocco di lettura
        }
    }
    
    public ArrayList<TBelief> Read_Beliefs() 
    {
        Read_Lock.lock(); // Acquisisci il blocco di lettura
        try 
        {
            // Potresti aggiungere una piccola pausa per simulare un'operazione di lettura lunga
            // try { Thread.sleep(50); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        	ArrayList<TBelief> result = new ArrayList<TBelief>(this.Beliefs);
        	return result;
        } 
        finally 
        {
            Read_Lock.unlock(); // Rilascia il blocco di lettura
        }
    }
    
    public ArrayList<TBelief> Read_Pre_Conditions() 
    {
        Read_Lock.lock(); // Acquisisci il blocco di lettura
        try 
        {
            // Potresti aggiungere una piccola pausa per simulare un'operazione di lettura lunga
            // try { Thread.sleep(50); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        	ArrayList<TBelief> result = new ArrayList<TBelief>(this.Pre_conditions);
        	return result;
        } 
        finally 
        {
            Read_Lock.unlock(); // Rilascia il blocco di lettura
        }
    }
    
    public ArrayList<TAttentional_Desire> Read_Attentional_Desires() 
    {
        Read_Lock.lock(); // Acquisisci il blocco di lettura
        try 
        {
            // Potresti aggiungere una piccola pausa per simulare un'operazione di lettura lunga
            // try { Thread.sleep(50); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        	ArrayList<TAttentional_Desire> result = 
        			new ArrayList<TAttentional_Desire>(this.Attentional_Desires);
        	return result;
        } 
        finally 
        {
            Read_Lock.unlock(); // Rilascia il blocco di lettura
        }
    }

    public ArrayList<TRegion> Read_Regions() 
    {
        Read_Lock.lock(); // Acquisisci il blocco di lettura
        try 
        {
            // Potresti aggiungere una piccola pausa per simulare un'operazione di lettura lunga
            // try { Thread.sleep(50); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        	ArrayList<TRegion> result = new ArrayList<TRegion>(this.Regions);
        	return result;
        } 
        finally 
        {
            Read_Lock.unlock(); // Rilascia il blocco di lettura
        }
    }
    
    /**
     * Scrive (aggiorna) l'oggetto protetto.
     * Acquisirà un blocco di scrittura, bloccando tutte le altre operazioni (letture e scritture).
     *
     * @param newData Il nuovo oggetto di tipo T con cui aggiornare.
     */
    public void Write(TQuadruple_Object newData) 
    {
        Write_Lock.lock(); // Acquisisci il blocco di scrittura
        try 
        {
//            this.data = newData;
            
            this.Pre_conditions.clear();
            this.Pre_conditions.addAll( (ArrayList<TBelief>) newData.Get_Object_First() );
            
            this.Beliefs.clear();
            this.Beliefs.addAll( (ArrayList<TBelief>) newData.Get_Object_Second() );
            
            this.Attentional_Desires.clear();
            this.Attentional_Desires.addAll( (ArrayList<TAttentional_Desire>) newData.Get_Object_Third() );
            
            this.Regions.clear();
            this.Regions.addAll( (ArrayList<TRegion>) newData.Get_Object_Fourth());
            
            ( (HashSet) this.data.Get_Object_First() ).clear();
        	( (HashSet) this.data.Get_Object_Second() ).clear();
        	( (HashSet) this.data.Get_Object_Third() ).clear();
        	( (HashSet) this.data.Get_Object_Fourth() ).clear();
        	
        	this.data.Set_Object_First(  this.Pre_conditions );
        	this.data.Set_Object_Second( this.Beliefs );
        	this.data.Set_Object_Third(  this.Attentional_Desires );
        	this.data.Set_Object_Fourth( this.Regions );
            
            
        } 
        finally 
        {
            Write_Lock.unlock(); // Rilascia il blocco di scrittura
        }
    }
    
    public void Add_Data(	ArrayList<TBelief> Pre_Conditions, ArrayList<TBelief> Beliefs,
			ArrayList<TAttentional_Desire> Attentional_Desires,
			ArrayList<TRegion> Regions)
    {
    	Write_Lock.lock(); // Acquisisci il blocco di scrittura
        try 
        {
        	           
//            this.Pre_conditions.clear();
            this.Pre_conditions.addAll( Pre_Conditions );
            
//            this.Beliefs.clear();
            this.Beliefs.addAll( Beliefs );
            
//            this.Attentional_Desires.clear();
            this.Attentional_Desires.addAll( Attentional_Desires );
            
//            this.Regions.clear();
            this.Regions.addAll( Regions);
            
            ( (HashSet) this.data.Get_Object_First() ).clear();
        	( (HashSet) this.data.Get_Object_Second() ).clear();
        	( (HashSet) this.data.Get_Object_Third() ).clear();
        	( (HashSet) this.data.Get_Object_Fourth() ).clear();
        	
        	this.data.Set_Object_First(  this.Pre_conditions );
        	this.data.Set_Object_Second( this.Beliefs );
        	this.data.Set_Object_Third(  this.Attentional_Desires );
        	this.data.Set_Object_Fourth( this.Regions );
        } 
        finally 
        {
            Write_Lock.unlock(); // Rilascia il blocco di scrittura
        }
    }
    
    public void Add_Desires(	ArrayList<TBelief> Pre_Conditions, ArrayList<TBelief> Beliefs)
    {
    	Write_Lock.lock(); // Acquisisci il blocco di scrittura
        try 
        {
            this.Attentional_Desires.addAll( Attentional_Desires );
//          
        	( (HashSet) this.data.Get_Object_Third() ).clear();
        	
        	this.data.Set_Object_Third(  this.Attentional_Desires );
        } 
        finally 
        {
            Write_Lock.unlock(); // Rilascia il blocco di scrittura
        }
    }
    
    public void Write(	ArrayList<TBelief> Pre_Conditions, ArrayList<TBelief> Beliefs,
    					ArrayList<TAttentional_Desire> Attentional_Desires,
    					ArrayList<TRegion> Regions)
    {
        Write_Lock.lock(); // Acquisisci il blocco di scrittura
        try 
        {
			if (Pre_Conditions != null)
			{
				this.Pre_conditions.clear();
	            this.Pre_conditions.addAll( Pre_Conditions );
			}
            this.Beliefs.clear();
            this.Beliefs.addAll( Beliefs );
            this.Attentional_Desires.clear();
            this.Attentional_Desires.addAll( Attentional_Desires );
            this.Regions.clear();
            this.Regions.addAll( Regions);
            
            ( (HashSet) this.data.Get_Object_First() ).clear();
        	( (HashSet) this.data.Get_Object_Second() ).clear();
        	( (HashSet) this.data.Get_Object_Third() ).clear();
        	( (HashSet) this.data.Get_Object_Fourth() ).clear();
        	
        	this.data.Set_Object_First(  this.Pre_conditions );
        	this.data.Set_Object_Second( this.Beliefs );
        	this.data.Set_Object_Third(  this.Attentional_Desires );
        	this.data.Set_Object_Fourth( this.Regions );
        	
        } 
        finally 
        {
            Write_Lock.unlock(); // Rilascia il blocco di scrittura
        }
    }
    
    
    
    
    
    protected TQuadruple_Object Read_And_Clear(TQuadruple_Object newData) 
    {
        Write_Lock.lock(); // Acquisisci il blocco di scrittura
        try 
        {
        	TQuadruple_Object result = this.data;
        	this.data = newData;
        	return result;
        } 
        finally 
        {
            Write_Lock.unlock(); // Rilascia il blocco di scrittura

        }
    }

    /**
     * Metodo per eseguire un'azione con l'oggetto in modalità lettura.
     * Utile per incapsulare la logica di lettura senza esporre direttamente il lock.
     *
     * @param action Un Consumer che riceve l'oggetto protetto per l'operazione di lettura.
     */
    public void readWithAction(java.util.function.Consumer<TQuadruple_Object> action) 
    {
        Read_Lock.lock();
        try 
        {
            action.accept(data);
        } 
        finally 
        {
            Read_Lock.unlock();
        }
    }

    /**
     * Metodo per eseguire un'azione che modifica l'oggetto in modalità scrittura.
     * Utile per incapsulare la logica di scrittura senza esporre direttamente il lock.
     *
     * @param action Un Consumer che riceve l'oggetto protetto e lo modifica.
     */
    public void writeWithAction(java.util.function.Consumer<TQuadruple_Object> action) 
    {
        Write_Lock.lock();
        try 
        {
            action.accept(data); // L'azione modifica direttamente 'data'
        } 
        finally
        {
            Write_Lock.unlock();
        }
    }
    
    public TQuadruple_Object updateAndGet(Function<TQuadruple_Object, TQuadruple_Object> updateFunction) 
    {
    	Write_Lock.lock();
        try 
        {
        	TQuadruple_Object newValue = updateFunction.apply(this.data); // Applica la trasformazione
            this.data = newValue; // Riassegna il campo con il nuovo oggetto
            return newValue;
        } 
        finally 
        {
        	Write_Lock.unlock();
        }
    }
    
}