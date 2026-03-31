package com.Catalina_Model.Catalina_V_0_3;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Consumer;

/**
 * Classe generica che incapsula una HashMap di tipo <K, V>
 * e ne gestisce l'accesso in lettura e scrittura
 * tramite ReentrantReadWriteLock per la sicurezza dei thread.
 *
 * @param <K> Il tipo delle chiavi nella mappa.
 * @param <V> Il tipo dei valori nella mappa.
 */
public class TGeneric_Protected_Map<K, V> 
{

    private final HashMap<K, V> map; 
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private final ReentrantReadWriteLock.ReadLock readLock = lock.readLock();
    private final ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();

    /**
     * Costruttore che inizializza la mappa protetta con una HashMap vuota.
     */
    public TGeneric_Protected_Map() 
    {
        this.map = new HashMap<>();
    }

    /**
     * Costruttore che inizializza la mappa protetta con una mappa esistente.
     * Si noti che la mappa passata viene copiata per evitare riferimenti esterni mutabili.
     *
     * @param initialMap La mappa iniziale.
     */
    public TGeneric_Protected_Map(Map<K, V> initialMap) 
    {
        this.map = new HashMap<>(initialMap); // Copia la mappa per sicurezza
    }
    
    /**
     * Inserisce un elemento nella mappa in modo thread-safe (operazione di scrittura).
     * Se la chiave esiste già, il valore viene sovrascritto.
     *
     * @param key La chiave.
     * @param value Il valore.
     */
    public void Put(K key, V value)
    {
        writeLock.lock();
        try 
        {
        	if( key != null )
        	{
        		map.put(key, value);
        	}
        } 
        finally 
        {
            writeLock.unlock();
        }
    }
    
    /**
     * Rimuove un elemento dalla mappa in base alla chiave in modo thread-safe.
     * * @param key La chiave da rimuovere.
     * @return Il valore associato alla chiave rimossa, oppure null se la chiave non esisteva.
     */
    public V Remove(K key)
    {
        writeLock.lock();
        try 
        {
        	if( key != null)
        	{
        		return map.remove(key);
        	}
        	return null;
        } 
        finally 
        {
            writeLock.unlock();
        }
    }
    
    /**
     * Rimuove un elenco di chiavi dalla mappa.
     * * @param keys L'elenco delle chiavi da rimuovere.
     */
    public void Remove_Keys(ArrayList<K> keys)
    {
        writeLock.lock();
        try 
        {
        	if( keys != null)
        	{
        	    keys.removeAll(Collections.singleton(null));
        		for(K key : keys)
        		{
        			map.remove(key);
        		}
        	}
        } 
        finally 
        {
            writeLock.unlock();
        }
    }

    /**
     * Recupera un elemento dalla mappa in base alla chiave in modo thread-safe (operazione di lettura).
     *
     * @param key La chiave dell'elemento da recuperare.
     * @return Il valore associato, o null se non presente.
     */
    public V Get(K key) 
    {
        readLock.lock();
        try 
        {
            if (key != null) 
            {
                return map.get(key);
            }
            return null;
        } 
        finally 
        {
            readLock.unlock();
        }
    }
    
    /**
     * Restituisce la dimensione corrente della mappa in modo thread-safe (operazione di lettura).
     *
     * @return Il numero di coppie chiave-valore.
     */
    public int Size() 
    {
        readLock.lock();
        try 
        {
            return map.size();
        } 
        finally 
        {
            readLock.unlock();
        }
    }
    
    /**
     * Restituisce una copia della mappa corrente (Snapshot).
     * * @return Una nuova HashMap contenente i dati attuali.
     */
    public HashMap<K, V> Read() 
    {
        readLock.lock(); // Acquisiamo il blocco di lettura
        try 
        {
        	HashMap<K, V> elements = new HashMap<>(map); // Crea una copia della mappa corrente
            return elements;
        } 
        finally 
        {
        	readLock.unlock(); // Rilascia il blocco
        }
    }

    /**
     * Restituisce solo i valori presenti nella mappa come ArrayList.
     * Utile se si vuole iterare solo sui valori.
     */
    public ArrayList<V> Get_Values_List()
    {
        readLock.lock();
        try
        {
            return new ArrayList<>(map.values());
        }
        finally
        {
            readLock.unlock();
        }
    }
    
    /**
     * Restituisce solo i valori presenti nella mappa come ArrayList.
     * Utile se si vuole iterare solo sui valori.
     */
    public ArrayList<K> Get_Key_List()
    {
        readLock.lock();
        try
        {
            // Specifica <K> anche qui per essere sicuro
            return new ArrayList<K>(map.keySet());
        }
        finally
        {
            readLock.unlock();
        }
    }
    
    /**
     * Restituisce una copia della mappa corrente e poi pulisce la mappa originale.
     * Questa è un'operazione atomica che richiede un blocco di scrittura.
     *
     * @return Una copia della mappa prima che venisse pulita.
     */
    public HashMap<K, V> Read_And_Clear() 
    {
        writeLock.lock(); // Acquisiamo il blocco di scrittura per l'operazione atomica
        try 
        {
        	HashMap<K, V> elements = new HashMap<>(map); // Crea una copia
            map.clear(); // Pulisce la mappa originale
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
            map.clear(); // Pulisce la mappa originale
        } 
        finally 
        {
            writeLock.unlock(); // Rilascia il blocco di scrittura
        }
    }
    
    /**
     * Aggiunge tutti gli elementi di un'altra mappa a questa mappa in modo thread-safe.
     *
     * @param otherMap La mappa da cui prendere gli elementi.
     */
    public void Put_All(Map<K, V> otherMap) 
    {
        writeLock.lock();
        try 
        {
            if (otherMap != null) 
            {
                // Rimuove eventuali chiavi null dalla sorgente (se la mappa sorgente lo permette)
                // Nota: HashMap standard permette una chiave null, ma qui per sicurezza la evitiamo se necessario
                // o copiamo tutto come da standard map.
            	
            	for (Map.Entry<K, V> entry : otherMap.entrySet())
            	{
            		if(entry.getKey() != null)
            		{
            			map.put(entry.getKey(), entry.getValue());
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
     * Sostituisce l'intera mappa interna con gli elementi di una nuova mappa fornita.
     * Questa operazione è atomica.
     *
     * @param newMap La nuova mappa con cui sostituire quella corrente.
     */
    public void Set_Map(Map<K, V> newMap) 
    {
        writeLock.lock();
        try 
        {
            map.clear(); // Pulisce la mappa corrente
            if (newMap != null) 
            {
            	for (Map.Entry<K, V> entry : newMap.entrySet())
            	{
            		if(entry.getKey() != null)
            		{
            			map.put(entry.getKey(), entry.getValue());
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
     * Verifica se la mappa contiene una chiave specifica.
     */
    public boolean ContainsKey(K key)
    {
        readLock.lock();
        try
        {
            return map.containsKey(key);
        }
        finally
        {
            readLock.unlock();
        }
    }
    
    /**
     * Verifica se la mappa è vuota in modo thread-safe (operazione di lettura).
     *
     * @return true se la mappa non contiene elementi, false altrimenti.
     */
    public boolean isEmpty() 
    {
        readLock.lock(); // Acquisisce il blocco di lettura
        try 
        {
            return map.isEmpty(); 
        } 
        finally 
        {
            readLock.unlock(); // Rilascia il blocco di lettura
        }
    }
    
    /**
     * Esegue un'operazione che potrebbe modificare la mappa in modalità scrittura.
     * L'azione riceve la mappa interna protetta e può modificarla direttamente.
     *
     * @param action Un Consumer che riceve la mappa protetta.
     */
    public void modifyMap(Consumer<Map<K, V>> action) {
        writeLock.lock();
        try 
        {
            action.accept(map); 
        } 
        finally 
        {
            writeLock.unlock();
        }
    }
    
    public void forEachSnapshot(Consumer<Map<K, V>> action) {
        readLock.lock();
        try {
            // System.out.println(Thread.currentThread().getName() + " ha acquisito il blocco di lettura per snapshot.");
            // Passa una copia della mappa
            action.accept(new HashMap<>(map));
        } finally {
            readLock.unlock();
            // System.out.println(Thread.currentThread().getName() + " ha rilasciato il blocco di lettura per snapshot.");
        }
    }
    
    @Override 
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("TGeneric_Protected_Map { ");

        this.forEachSnapshot(currentMap -> {
            sb.append("Map: [");
            int i = 0;
            for (Map.Entry<K, V> entry : currentMap.entrySet()) {
                sb.append(entry.getKey()).append("=").append(entry.getValue());
                if (i < currentMap.size() - 1) {
                    sb.append(", ");
                }
                i++;
            }
            sb.append("]");
        });

        sb.append(" }");
        return sb.toString();
    }
}