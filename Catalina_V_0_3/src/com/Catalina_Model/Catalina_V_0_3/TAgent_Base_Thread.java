package com.Catalina_Model.Catalina_V_0_3;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public abstract class TAgent_Base_Thread extends Thread
{

	protected TAgent Agent = null;
	protected final TBase_Message_Handler Message_Handler;
	
	private Boolean Continue_Thread = true;

	// Locks for Update Messages
	private final ReentrantReadWriteLock Lock_Continue_Thread = new ReentrantReadWriteLock();
	
	// Locks for Read Update Messages
	private final ReentrantReadWriteLock.ReadLock Read_Lock_Continue_Thread = Lock_Continue_Thread.readLock();
	
	// Locks for Write Update Messages
	private final ReentrantReadWriteLock.WriteLock Write_Lock_Continue_Thread = Lock_Continue_Thread.writeLock();

		// Methods to Executive Function Init
	private final CountDownLatch Initial_Start_Signal;// = new CountDownLatch(1);
	private final ReentrantLock Exec_Func_Pause_Lock;// = new ReentrantLock();
	private final Condition Exec_Func_UnPaused;// = Exec_Func_Pause_Lock.newCondition();
	private volatile boolean Is_Paused = false;

	
	
	/////////
	/////////
	/////////
	
	
	
	public TAgent_Base_Thread(TAgent agent, String Executive_Function_Name) 
	{
		this.Agent = agent;
		setName(Executive_Function_Name);
		
		this.Message_Handler = new TBase_Message_Handler();
		
		//Executive_Function starts in suspended state
		this.Initial_Start_Signal = new CountDownLatch(1);
		this.Exec_Func_Pause_Lock = new ReentrantLock();
		this.Exec_Func_UnPaused = Exec_Func_Pause_Lock.newCondition();
		
		this.Insert_in_List_Update_Contract();
	}
	
	public abstract void Execute();

	@Override
	public void run() 
	{
		try 
		{
//			System.out.println(getName() + ": Creato e in attesa di attivazione iniziale...");
			// Wait for the initial start signal
//			Initial_Start_Signal.await();

			// Suspension checkpoint
			Is_In_Pause();
//			System.out.println("I enter in loop");
			while (this.Read_Continue_Thread() )
			{
				/**
				 * This checks if the Thread is in pause or not
				 */
				Is_In_Pause();
				/**
				 * This permits any correct updating data in all threads
				 */
				this.Wait_A_Time( 50 );
				this.Execute();
//				System.out.println("I reloop: "+getName());
			}
			System.out.println("I exit from loop");
			

		} 
		//catch (InterruptedException e)
		catch (Exception e)
		{
			
			System.out.println(getName() + ": Interrupted while waiting or running.");
			System.out.println("Captured Error: " + e.getMessage());
			e.printStackTrace();
			Thread.currentThread().interrupt();
		} 
		finally 
		{
			// we have to be sure to release the lock if the thread terminates while paused.
			if (Exec_Func_Pause_Lock.isHeldByCurrentThread()) 
			{
				Exec_Func_Pause_Lock.unlock();
			}
		}
	}

	protected void Is_In_Pause() throws InterruptedException {
		Exec_Func_Pause_Lock.lock();
		try 
		{
			// If Is_Paused is true, the thread waits
			while (Is_Paused) 
			{
				// Wait until the condition is indicated
				this.Exec_Func_UnPaused.await();
			}
		} 
		finally 
		{
			Exec_Func_Pause_Lock.unlock();
		}
	}

	public void Suspend() 
	{
		Exec_Func_Pause_Lock.lock();
		try 
		{
			Is_Paused = true;
		} 
		finally 
		{
			Exec_Func_Pause_Lock.unlock();
		}
	}

	public void Resume() 
	{
		Exec_Func_Pause_Lock.lock();
		try 
		{
			Is_Paused = false;
			Exec_Func_UnPaused.signalAll();
		} 
		finally 
		{
			Exec_Func_Pause_Lock.unlock();
		}
	}

	public void Start_Executive_Function() 
	{
		this.Initial_Start_Signal.countDown();
	}

	public void Wait_A_Time(int time) 
	{
		try 
		{
			// wait a time
			Thread.sleep(time);
		} 
		catch (InterruptedException e) 
		{
			System.out.println(getName() + ": Interrupted while waiting.");
			System.out.println("Captured Error: " + e.getMessage());
			Thread.currentThread().interrupt();
		} 
//		finally 
//		{
//			// we have to be sure to release the lock if the thread terminates while paused.
//			if (Exec_Func_Pause_Lock.isHeldByCurrentThread()) {
//				Exec_Func_Pause_Lock.unlock();
//			}
//		}
	}

	
	
	/////////
	/////////
	// READ Update Messages Area
	/////////
	/////////
	
	
	
	public Boolean Read_Continue_Thread() 
	{
		Boolean Result = false;
		Read_Lock_Continue_Thread.lock();

		try 
		{
			Result = this.Continue_Thread;
		} 
		catch (Exception e) 
		{
			Thread.currentThread().interrupt();
		} 
		finally 
		{
			Read_Lock_Continue_Thread.unlock();
		}

		return Result;
	}
	
	//////////////////////
	//////////////////////
	//// Read_And_Clean (It means: Read and Write)
	//////////////////////
	//////////////////////
	
	
	
	public Boolean Read_And_Clean_Continue_Thread() 
	{
		Boolean Result = false;
		Write_Lock_Continue_Thread.lock();

		try 
		{
			Result = this.Continue_Thread;
			this.Continue_Thread = false;
		} 
		catch (Exception e) 
		{
			Thread.currentThread().interrupt();
		} 
		finally 
		{
			Write_Lock_Continue_Thread.unlock();
		}

		return Result;
	}	
	
	/////////
	/////////
	// Write Update Messages Area
	/////////
	/////////
	
	
	
	public void Write_Continue_Thread() 
	{
		Write_Lock_Continue_Thread.lock();
		try 
		{
			this.Continue_Thread = true;
		} 
		catch (Exception e)
		{
			Thread.currentThread().interrupt();
		} 
		finally 
		{
			Write_Lock_Continue_Thread.unlock();
		}
	}
	
	/////////
	/////////
	// Other methods
	/////////
	/////////
	
	public TAgent Get_Agent() 
	{
		return this.Agent;
	}
	
	public void Set_Continue(Boolean Value)
	{
		
	}
	
	public abstract void Insert_in_List_Update_Contract();
}
