package com.Catalina_Model.Catalina_V_0_3;

import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Function;
import java.util.Optional; // Utile per gestire ritorni che potrebbero essere nulli o non trovati

/**
 * Classe generica che incapsula un oggetto di tipo T
 * e ne gestisce l'accesso in lettura e scrittura
 * tramite ReentrantReadWriteLock per la sicurezza dei thread.
 *
 * @param <T> Il tipo dell'oggetto che questa classe proteggerà.
 */
public class TGeneric_Protected_Object<T> 
{

    private T data; // L'oggetto generico che vogliamo proteggere
    private final ReentrantReadWriteLock Lock = new ReentrantReadWriteLock();
    private final ReentrantReadWriteLock.ReadLock Read_Lock = Lock.readLock();
    private final ReentrantReadWriteLock.WriteLock Write_Lock = Lock.writeLock();

    /**
     * Costruttore che inizializza l'oggetto protetto.
     *
     * @param initialData L'oggetto iniziale di tipo T.
     */
    public TGeneric_Protected_Object(T initialData) 
    {
        this.data = initialData;
    }

    /**
     * Legge l'oggetto protetto.
     * Acquisirà un blocco di lettura, consentendo letture concorrenti.
     *
     * @return L'oggetto di tipo T, incapsulato in un Optional per gestire il caso di null (anche se in questa
     * implementazione data non sarà mai null una volta inizializzato).
     */
    public T Read() 
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

    /**
     * Scrive (aggiorna) l'oggetto protetto.
     * Acquisirà un blocco di scrittura, bloccando tutte le altre operazioni (letture e scritture).
     *
     * @param newData Il nuovo oggetto di tipo T con cui aggiornare.
     */
    public void Write(T newData) 
    {
        Write_Lock.lock(); // Acquisisci il blocco di scrittura
        try 
        {
            this.data = newData;
        } 
        finally 
        {
            Write_Lock.unlock(); // Rilascia il blocco di scrittura
        }
    }
    
    protected T Read_And_Clear(T newData) 
    {
        Write_Lock.lock(); // Acquisisci il blocco di scrittura
        try 
        {
            T result = this.data;
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
    public void readWithAction(java.util.function.Consumer<T> action) 
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
    public void writeWithAction(java.util.function.Consumer<T> action) 
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
    
    public T updateAndGet(Function<T, T> updateFunction) 
    {
    	Write_Lock.lock();
        try 
        {
            T newValue = updateFunction.apply(this.data); // Applica la trasformazione
            this.data = newValue; // Riassegna il campo con il nuovo oggetto
            return newValue;
        } 
        finally 
        {
        	Write_Lock.unlock();
        }
    }
    
}