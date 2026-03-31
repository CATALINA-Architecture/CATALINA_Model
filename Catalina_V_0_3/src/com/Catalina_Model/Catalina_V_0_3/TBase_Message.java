package com.Catalina_Model.Catalina_V_0_3;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Consumer;

public class TBase_Message
{
    private volatile Boolean Value; // L'oggetto generico che vogliamo proteggere
    private volatile String Sender;
    private volatile String Message_Type;
    private volatile ArrayList<Object> Data;
    private final ReentrantReadWriteLock Lock = new ReentrantReadWriteLock();
    private final ReentrantReadWriteLock.ReadLock Read_Lock = Lock.readLock();
    private final ReentrantReadWriteLock.WriteLock Write_Lock = Lock.writeLock();
    
    @FunctionalInterface
    public interface QuadConsumer<B, O, S, A> 
    {
        void accept(B b, O o, S s, A a);
    }

	public TBase_Message() 
    {
		this.Value = false;
		this.Sender = "";
		this.Message_Type = "";
		this.Data = new ArrayList<Object>();
		
    }
	
	public Boolean Read_Value() 
    {
        Read_Lock.lock(); 
        try 
        {
            return this.Value;
        } 
        finally 
        {
            Read_Lock.unlock(); 
        }
    }
	
	public String Read_Sender() 
    {
        Read_Lock.lock(); 
        try 
        {
            return this.Sender;
        } 
        finally 
        {
            Read_Lock.unlock();
        }
    }
	
	public String Read_Message_Type() 
    {
        Read_Lock.lock(); 
        try 
        {
            return this.Message_Type;
        } 
        finally 
        {
            Read_Lock.unlock(); 
        }
    }
	
	public ArrayList<Object> Read_Data() 
    {
        Read_Lock.lock(); 
        try 
        {
        	ArrayList<Object> elements = new ArrayList<>(); 
        	elements.addAll( Data );
        	Data.clear();
            return elements;
        } 
        finally 
        {
            Read_Lock.unlock(); // Rilascia il blocco di lettura
        }
    }
	
	public ArrayList<Object> Read_Data_and_Clean() 
    {
        Write_Lock.lock(); 
        try 
        {
        	ArrayList<Object> elements = new ArrayList<>(this.Data); 
        	this.Value = false;
        	this.Message_Type = "";
        	this.Sender = "";
        	this.Data.clear();
        	
            return elements;
        } 
        finally 
        {
        	Write_Lock.unlock(); // Rilascia il blocco di lettura
        }
    }
	
	public Boolean Read_Value_and_Clear() 
    {
        Write_Lock.lock(); 
        try 
        {
        	Boolean result = this.Value;
        	this.Value = false;
        	this.Message_Type = "";
        	this.Sender = "";
        	this.Data.clear();
        	
            return result;
        } 
        finally 
        {
        	Write_Lock.unlock(); // Rilascia il blocco di lettura
        }
    }
	
	public TQuadruple_Object Read_Message() 
    {
        Read_Lock.lock(); 
        try 
        {
        	TQuadruple_Object Message = new TQuadruple_Object();
        	
        	ArrayList<Object> elements = new ArrayList<>(this.Data); 
        	
        	Message.Set_Object_First( this.Value );
        	Message.Set_Object_Second( this.Sender );
        	Message.Set_Object_Third( this.Message_Type );
        	Message.Set_Object_Fourth( elements );
        	
            return Message;
        } 
        finally 
        {
            Read_Lock.unlock(); // Rilascia il blocco di lettura
        }
    }
	
	public TQuadruple_Object Read_and_Clean_All_Message()
	{
        Write_Lock.lock(); 
        try 
        {
        	TQuadruple_Object Message = new TQuadruple_Object();
        	
        	ArrayList<Object> elements = new ArrayList<>(this.Data); 
        	
        	Message.Set_Object_First( this.Value );
        	Message.Set_Object_Second( this.Sender );
        	Message.Set_Object_Third( this.Message_Type );
        	Message.Set_Object_Fourth( elements );
        	
        	this.Value = false;
        	this.Sender = "";
        	this.Message_Type = "";
        	this.Data.clear();
        	
            return Message;
        } 
        finally 
        {
        	Write_Lock.unlock(); // Rilascia il blocco di lettura
        }
	}
	
	public void  Write_Internal_Signal(String sender, 
									   String message_type, ArrayList<Object> data)
	{
        Write_Lock.lock(); 
        try 
        {
        	
        	this.Value = true;
        	this.Sender = sender;
        	this.Message_Type = message_type;
        	this.Data.clear();
        	this.Data.addAll(data);
        	
        } 
        finally 
        {
        	Write_Lock.unlock(); // Rilascia il blocco di lettura
        }
	}
	
	public void Clean()
	{
        Write_Lock.lock(); 
        try 
        {
        	this.Value = false;
        	this.Sender = "";
        	this.Message_Type = "";
        	this.Data.clear();
        } 
        finally 
        {
        	Write_Lock.unlock(); // Rilascia il blocco di lettura
        }
	}
	
	public void Write_Value()
	{
		Write_Lock.lock(); 
        try 
        {
        	this.Value = true;
        	this.Sender = "";
        	this.Message_Type = "";
        	this.Data.clear();
        } 
        finally 
        {
        	Write_Lock.unlock(); // Rilascia il blocco di lettura
        }
	}
	
	public void modifyList(boolean value, Object sender, String message_type, ArrayList<Object> data,
            QuadConsumer<Boolean, Object, String, ArrayList<Object>> action) 
	{
		Write_Lock.lock();
		try 
		{
			action.accept(value, sender, message_type, data);
		} 
		finally 
		{
			Write_Lock.unlock();
		}

	}

}
