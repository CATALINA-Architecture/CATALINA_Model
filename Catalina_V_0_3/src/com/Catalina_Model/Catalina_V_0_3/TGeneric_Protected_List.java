package com.Catalina_Model.Catalina_V_0_3;

import java.util.Collection;
import java.util.Collections;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Classe generica che incapsula una List di tipo T
 * e ne gestisce l'accesso in lettura e scrittura
 * tramite ReentrantReadWriteLock per la sicurezza dei thread.
 *
 * @param <T> Il tipo degli elementi nella lista.
 */
public class TGeneric_Protected_List<T> 
{

    private final ArrayList<T> list; 
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private final ReentrantReadWriteLock.ReadLock readLock = lock.readLock();
    private final ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();

    /**
     * Costruttore che inizializza la lista protetta con una ArrayList vuota.
     */
    public TGeneric_Protected_List() 
    {
        this.list = new ArrayList<>();
    }

    /**
     * Costruttore che inizializza la lista protetta con una lista esistente.
     * Si noti che la lista passata viene copiata per evitare riferimenti esterni mutabili.
     *
     * @param initialList La lista iniziale di tipo T.
     */
    public TGeneric_Protected_List(ArrayList<T> initialList) 
    {
        this.list = new ArrayList<>(initialList); // Copia la lista per sicurezza
    }
    
    /**
     * Aggiunge un elemento alla lista in modo thread-safe (operazione di scrittura).
     *
     * @param element L'elemento da aggiungere.
     */
    public void Add(T element)
    {
        writeLock.lock();
        try 
        {
        	if( element != null)
        	{
        		if (!list.contains(element))
            	{
            		list.add(element);
            	}
        	}
        } 
        finally 
        {
            writeLock.unlock();
        }
    }
    
    public Boolean Remove(T element)
    {
        writeLock.lock();
        try 
        {
        	if( element != null)
        	{
        		return list.remove(element);
        	}
        	else
        	{
        		return false;
        	}
        } 
        finally 
        {
            writeLock.unlock();
        }
    }
    
    public Boolean Remove_All(ArrayList<T> elements)
    {
        writeLock.lock();
        try 
        {
        	elements.removeAll(Collections.singleton(null));
        	if( elements != null)
        	{
        		return list.removeAll(elements);
        	}
        	else
        	{
        		return false;
        	}
        } 
        finally 
        {
            writeLock.unlock();
        }
    }
    
    /**
     * Rimuove un elemento dalla lista in base all'indice in modo thread-safe (operazione di scrittura).
     *
     * @param index L'indice dell'elemento da rimuovere.
     * @return Un Optional contenente l'elemento rimosso, se l'indice è valido; Optional.empty() altrimenti.
     */
    public T Remove(int index) 
    {
        writeLock.lock();
        try 
        {
            if (index >= 0 && index < list.size()) 
            {
                T removedElement = list.remove(index);
                return removedElement;
            }
            return null;
        } 
        finally 
        {
            writeLock.unlock();
        }
    }
    
    /**
     * Rimuove il primo elemento che soddisfa il predicato dato (operazione di scrittura).
     *
     * @param predicate Il predicato da usare per trovare l'elemento da rimuovere.
     * @return true se un elemento è stato rimosso, false altrimenti.
     */
    public boolean removeIf(Predicate<T> predicate) 
    {
        writeLock.lock();
        try 
        {
            boolean removed = list.removeIf(predicate);
            return removed;
        } 
        finally 
        {
            writeLock.unlock();
        }
    }
    
    /**
     * Recupera un elemento dalla lista in base all'indice in modo thread-safe (operazione di lettura).
     *
     * @param index L'indice dell'elemento da recuperare.
     * @return Un Optional contenente l'elemento, se l'indice è valido; Optional.empty() altrimenti.
     */
    public T Get(int index) 
    {
        readLock.lock();
        try 
        {
            if (index >= 0 && index < list.size()) 
            {
                T element = list.get(index);
                return element;
            }
            return null;
        } 
        finally 
        {
            readLock.unlock();
        }
    }
    
    /**
     * Restituisce la dimensione corrente della lista in modo thread-safe (operazione di lettura).
     *
     * @return La dimensione della lista.
     */
    public int Size() 
    {
        readLock.lock();
        try 
        {
            return list.size();
        } 
        finally 
        {
            readLock.unlock();
        }
    }
    
    public ArrayList<T> Read() 
    {
        readLock.lock(); // Acquisiamo il blocco di scrittura per l'operazione atomica
        try 
        {
        	ArrayList<T> elements = new ArrayList<>(list); // Crea una copia della lista corrente
            return elements;
        } 
        finally 
        {
        	readLock.unlock(); // Rilascia il blocco di scrittura
        }
    }
    
    /**
     * Restituisce una copia della lista corrente e poi pulisce la lista originale.
     * Questa è un'operazione atomica che richiede un blocco di scrittura.
     *
     * @return Una copia della lista prima che venisse pulita.
     */
    public ArrayList<T> Read_And_Clear() 
    {
        writeLock.lock(); // Acquisiamo il blocco di scrittura per l'operazione atomica
        try 
        {
        	ArrayList<T> elements = new ArrayList<>(); // Crea una copia della lista corrente
        	elements.addAll( list );
            list.clear(); // Pulisce la lista originale
            return elements;
        } 
        finally 
        {
            writeLock.unlock(); // Rilascia il blocco di scrittura
        }
    }
    
    public void Clear() 
    {
        writeLock.lock(); // Acquisiamo il blocco di scrittura per l'operazione atomica
        try 
        {
            list.clear(); // Pulisce la lista originale
        } 
        finally 
        {
            writeLock.unlock(); // Rilascia il blocco di scrittura
        }
    }
    
    /**
     * **NUOVO METODO:** Aggiunge tutti gli elementi di una collezione alla lista in modo thread-safe.
     *
     * @param elements La collezione di elementi da aggiungere.
     */
    public void Add_All(Collection<T> elements) 
    {
        writeLock.lock();
        try 
        {
        	elements.removeAll(Collections.singleton(null));
            if (elements != null) 
            {
//            	if (!list.contains(elements))
//            	{
//            		list.addAll(elements);
//            	}
            	for(T element: elements)
            	{
            		if (!list.contains(element))
                	{
                		list.add(element);
                	}
            	}
                
            }
        } 
        finally 
        {
            writeLock.unlock();
        }
    }
    
    /**
     * **NUOVO METODO:** Sostituisce l'intera lista interna con gli elementi di una nuova lista fornita.
     * Questa operazione è atomica.
     *
     * @param newList La nuova lista di elementi con cui sostituire quella corrente.
     */
    public void Set_List(ArrayList<T> newList) 
    {
        writeLock.lock();
        try 
        {
            list.clear(); // Pulisce la lista corrente
            if (newList != null) 
            {
            	for(T element: newList)
            	{
            		if(!list.contains(element))
            		{
            			list.add(element);
            		}
            	}
//                list.addAll(newList); // Aggiunge tutti i nuovi elementi
            	
            } 
        } 
        finally 
        {
            writeLock.unlock();
        }
    }
    
    
    /**
     * **NUOVO METODO:** Verifica se la lista è vuota in modo thread-safe (operazione di lettura).
     *
     * @return true se la lista non contiene elementi, false altrimenti.
     */
    public boolean isEmpty() 
    {
        readLock.lock(); // Acquisisce il blocco di lettura
        try 
        {
            return list.isEmpty(); // Delega a isEmpty() della lista interna
        } 
        finally 
        {
            readLock.unlock(); // Rilascia il blocco di lettura
        }
    }
    
    /**
     * Esegue un'operazione che potrebbe modificare la lista in modalità scrittura.
     * L'azione riceve la lista interna protetta e può modificarla direttamente.
     *
     * @param action Un Consumer che riceve la lista protetta per l'operazione di scrittura/modifica.
     */
    public void modifyList(Consumer<List<T>> action) {
        writeLock.lock();
        try 
        {
            action.accept(list); 
        } 
        finally 
        {
            writeLock.unlock();
        }
    }
    
    public void forEachSnapshot(Consumer<List<T>> action) {
        readLock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + " ha acquisito il blocco di lettura per snapshot.");
            // Passa una copia della lista per evitare ConcurrentModificationException
            // e per non permettere modifiche dirette esterne.
            action.accept(new ArrayList<>(list));
        } finally {
            readLock.unlock();
            System.out.println(Thread.currentThread().getName() + " ha rilasciato il blocco di lettura per snapshot.");
        }
    }
    
    @Override // <-- AGGIUNGI QUESTA ANNOTAZIONE
    public String toString() {
        // Poiché `forEachSnapshot` della superclasse acquisisce il readLock,
        // possiamo usarlo per ottenere una copia della lista in modo thread-safe
        // e combinarla con il valore di Inc_Number.
        StringBuilder sb = new StringBuilder();
        sb.append("TGeneric_Auto_Named_and_Protected_List { ");

        // Acquisiamo la snapshot della lista tramite forEachSnapshot,
        // che gestisce il readLock della superclasse per noi.
        this.forEachSnapshot(currentList -> {
            sb.append("List: [");
            for (int i = 0; i < currentList.size(); i++) {
                sb.append(currentList.get(i));
                if (i < currentList.size() - 1) {
                    sb.append(", ");
                }
            }
            sb.append("]");
        });

        // Il valore di Inc_Number è atomico (AtomicLong), quindi possiamo leggerlo direttamente.
//        sb.append(", Inc_Number: ").append(incNumber.get());
        sb.append(" }");
        return sb.toString();
    }
    
}