package com.Catalina_Model.Catalina_V_0_3;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import com.Catalina_Model.Catalina_V_0_3.TBelief_Inhibition_Function_Handler.TBelief_Inhibition_Function;
import com.Catalina_Model.Catalina_V_0_3.TMeans_End_Epistemic_Functions_Handler.Means_End_Epistemic_Function;
import com.Catalina_Model.Catalina_V_0_3.TMeans_End_Practical_Functions_Handler.Means_End_Practical_Function;
import com.Catalina_Model.Catalina_V_0_3.TRegion_Inhibition_Function_Handler.TRegion_Inhibition_Function;

public class TAgent 
{
	
	private static TAgent_Base_Thread threadA;
    private static TAgent_Base_Thread threadB;
    
    private TExecutive_Switching_Function Exec_Switching_Func;
	private TExecutive_Inhibition_Function Exec_Inhibition_Func;
	private TGlobal_Workspace Global_Worskspace;
	private TLong_Memory Long_Memory;
	private TExecutive_Reasoner_Function Exec_Reasoner;
	private TExecutive_Resource_Allocation_Function Exec_Resource_Allocation_Func;
	private TExecutive_Perception_Function Exec_Perception_Func;
	private TExecutive_Memory_Maintenance_Function Exec_Memomry_Maint_Func;
	private TAttention_Selection Attention_Selection_Component;
	private TDesire_Handler Desire_Handler_Component;
	private TAction_Control Action_Control;
	
	private TAgent_Status Status; 
    
	public TAgent() 
	{
        System.out.println("Agent: Initialization...");
//        this.threadA = new TExecutive_Function(this, "Reasoner");
//        this.threadB = new TExecutive_Function(this, "Switching Function");

        this.Status = TAgent_Status.Not_Active;
        // Agent has to create Executive Functions in this order:
        this.Long_Memory = new TLong_Memory(this);
        this.Long_Memory.Initialize(null);
        
        this.Global_Worskspace = new TGlobal_Workspace(this);
        
        
        this.Action_Control = new TAction_Control( this );
        this.Exec_Switching_Func = new TExecutive_Switching_Function(this);
        this.Exec_Inhibition_Func = new TExecutive_Inhibition_Function(this);
//        this.Global_Worskspace = new TGlobal_Workspace(this);
        
        
        this.Exec_Reasoner = new TExecutive_Reasoner_Function(this);
        this.Exec_Resource_Allocation_Func = new TExecutive_Resource_Allocation_Function(this);
        this.Exec_Perception_Func = new TExecutive_Perception_Function( this );
        this.Exec_Memomry_Maint_Func = new TExecutive_Memory_Maintenance_Function(this, this.Long_Memory);
        this.Attention_Selection_Component = new TAttention_Selection(this);
        this.Desire_Handler_Component = new TDesire_Handler(this);
        
        this.Long_Memory.Acquire_Agent_Components();
        this.Global_Worskspace.Acquire_Agent_Components();
        
        /**
         * Now each component have to stipulate a contract with the Message Engine of the
         * Global Workspace
         */
        
        
        /**
         * Now The Agent has to set several  
         */
        
        //Exemple to start an executive function
        //this.Exec_Switching_Func.Start_Executive_Function();
        //or
        // this.Exec_Switching_Func.start();
        //I must to understand better how to do...
    }
	
	public void avviaThreads() {
        System.out.println("Agente: Avvio dei Reasoner e Switching Function...");
        threadA.start();
        threadB.start();
        System.out.println("Agente: Reasoner e Switching FunctionB sono stati avviati e sono in attesa dell'attivazione iniziale.");
    }
	
	/**
     * Metodo main per dimostrare il funzionamento.
     */
    public void Prova_Funzionamento_Avvio_Threads(TAgent agente) {
//        TAgent agente = new TAgent();

        // 1. Avvia i thread (li mette in attesa dell'attivazione iniziale)
    	agente.avviaThreads();

        try 
        {
            System.out.println("\n--- Agente: Attivazione iniziale di entrambi i thread ---");
            agente.attivaThreadAIniziale();
            agente.attivaThreadBIniziale();

            System.out.println("\n--- Agente: Attendo il completamento di entrambi i thread ---");
            agente.threadA.join();
            agente.threadB.join();

        } 
        catch (Exception e) 
        {
            System.err.println("Main thread interrotto.");
            System.err.println("Captured Error: "+e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Attiva il Thread A, permettendogli di proseguire la sua esecuzione iniziale.
     */
    public void attivaThreadAIniziale() {
        System.out.println("Agente: Attivazione iniziale del Reasoner...");
        threadA.Start_Executive_Function();
    }

    /**
     * Attiva il Thread B, permettendogli di proseguire la sua esecuzione iniziale.
     */
    public void attivaThreadBIniziale() {
        System.out.println("Agente: Attivazione iniziale del Switching Function...");
        threadB.Start_Executive_Function();
    }

    public void Resume_Executive_Function(TAgent_Base_Thread Exec_Funct)
    {
    	Exec_Funct.Resume();
    }
    
    public void Suspend_Executive_Function(TAgent_Base_Thread Exec_Funct)
    {
    	Exec_Funct.Suspend();
    }

    public TGlobal_Workspace Get_Global_WorkSpace()
    {
    	return this.Global_Worskspace;
    }
    
    public TLong_Memory Get_Long_Memory()
    {
    	return this.Long_Memory;
    }
    
    public TExecutive_Perception_Function Get_Executive_PerceptionFunction()
    {
    	return this.Exec_Perception_Func;
    }

    public TExecutive_Memory_Maintenance_Function Get_Executive_Memory_Maintenance_Function()
    {
    	return this.Exec_Memomry_Maint_Func;
    }
    
    public TAttention_Selection Get_Attention_Modulation_Component()
    {
    	return this.Attention_Selection_Component;
    }
    
    public TAction_Control Get_Action_Control()
    {
    	return this.Action_Control;
    }
    
    public TDesire_Handler Get_Desire_Handler_Component()
    {
    	return this.Desire_Handler_Component;
    }
    
    public TExecutive_Inhibition_Function Get_Executive_Inhibition_Function()
    {
    	return this.Exec_Inhibition_Func;
    }
    
    public TExecutive_Switching_Function Get_Executive_Switching_Function()
    {
    	return this.Exec_Switching_Func;
    }
    
    public TExecutive_Reasoner_Function Get_Executive_Reasoner_Function()
    {
    	return this.Exec_Reasoner;
    }
    
    public TExecutive_Resource_Allocation_Function Get_Executive_Resource_Allocation_Function()
    {
    	return this.Exec_Resource_Allocation_Func;
    }
    
    private void Prova_Funzionamento_Boolean_Evaluetion(String[] args)
    {
    		System.out.println("ok");
    		TBoolean_Expression_Evaluetor eval = new TBoolean_Expression_Evaluetor();
    		eval.Set_Variable("a", true);
    		eval.Set_Variable("b", true);
    		eval.Set_Variable("c", false);
    		System.out.println(eval.Evaluate("a&(!c)"));
    		System.out.println(eval.Evaluate("!a|!b"));
	}
    
    public TAgent(String stringa)
    {
    	System.out.println("Agent: Initialization...");
//    	System.out.println("Data directory: "+stringa);
//      this.threadA = new TExecutive_Function(this, "Reasoner");
//      this.threadB = new TExecutive_Function(this, "Switching Function");

      this.Status = TAgent_Status.Not_Active;
      // Agent has to create Executive Functions in this order:
      this.Long_Memory = new TLong_Memory(this);
      this.Long_Memory.Initialize(stringa);
      
      this.Global_Worskspace = new TGlobal_Workspace(this);
      
//      this.Global_Worskspace = new TGlobal_Workspace(this);
      
//      this.Exec_Switching_Func = new TExecutive_Switching_Function(this);
//      this.Exec_Inhibition_Func = new TExecutive_Inhibition_Function(this);
//      this.Exec_Reasoner = new TExecutive_Reasoner_Function(this);
//      this.Exec_Resource_Allocation_Func = new TExecutive_Resource_Allocation_Function(this);
//      this.Exec_Working_Memomry_Maint_Func = new TExecutive_Memory_Maintenance_Function(this, this.Long_Memory);
//      this.Attention_Modulation_Component = new TAttention_Modulation(this);
//      this.Desire_Promotion_Component = new TDesire_Handler(this);
      
      //Order to create Component
      this.Attention_Selection_Component = new TAttention_Selection(this);
      this.Desire_Handler_Component = new TDesire_Handler(this);
      this.Exec_Reasoner = new TExecutive_Reasoner_Function(this);
      this.Exec_Inhibition_Func = new TExecutive_Inhibition_Function(this);
      this.Exec_Resource_Allocation_Func = new TExecutive_Resource_Allocation_Function(this);
      this.Action_Control = new TAction_Control( this );
      this.Exec_Switching_Func = new TExecutive_Switching_Function(this);
      this.Exec_Perception_Func = new TExecutive_Perception_Function( this );
      this.Exec_Memomry_Maint_Func = new TExecutive_Memory_Maintenance_Function(this, this.Long_Memory);
      
      this.Long_Memory.Acquire_Agent_Components();
      this.Global_Worskspace.Acquire_Agent_Components();
      
      /**
       * Now each component have to stipulate a contract with the Message Engine of the
       * Global Workspace
       */
      
      
      /**
       * Now The Agent has to set several  
       */
    }
    
    /**
     * This Function registers a list of Beliefs to associate to an Attentional Desire
     * to get when the Reasoner has to acquire Beliefs to compute the options for an
     * intention
     * @param Desire_Name
     * @param Beliefs_Names
     */
    public void Reasoner_Register_Beliefs_Names_for_Desires(String Desire_Name, HashSet<String> Beliefs_Names)
    {
    	this.Exec_Reasoner.Register_List_Beliefs_Names_for_Desires(Desire_Name, Beliefs_Names);
    }
    
    /**
     * This Function registers a list of Regions to associate to an Attentional Desire
     * to get when the Reasoner has to acquire Regions to compute the options for an
     * intention
     * @param Desire_Name
     * @param Beliefs_Names
     */
    public void Reasoner_Register_List_Regions_Names_for_Desires(String Desire_Name, HashSet<String> Regions_Names)
    {
    	this.Exec_Reasoner.Register_List_Regions_Names_for_Desires(Desire_Name, Regions_Names);
    }
    
    /**
     * This Function Registers the Function to invoke when the Reasoner has to analyze
     * an Epistemic Desire
     * @param belief_Type
     * @param func
     */
    public void Reasoner_Register_Epistemic_Function(String belief_Type, Means_End_Epistemic_Function func) 
    {
		this.Exec_Reasoner.Register_Epistemic_Function( belief_Type, func );
    }
    
    /**
     * This Function Unregisters the Function to invoke when the Reasoner has to analyze
     * an Epistemic Desire
     * @param belief_Type
     * @param func
     */
    public boolean Reasoner_Unregister_Epistemic_Function(String belief_Type) 
    {
    	return this.Exec_Reasoner.Unregister_Epistemic_Function( belief_Type );
    }
    
    /**
     * This Function Registers the Function to invoke when the Reasoner has to analyze
     * a Practical Desire
     * @param belief_Type
     * @param func
     */
    public void Reasoner_Register_Practical_Function(String Desire_Name, Means_End_Practical_Function func) 
    {
		this.Exec_Reasoner.Register_Practical_Function( Desire_Name, func );
    }
    
    /**
     * This Function Unregisters the Function to invoke when the Reasoner has to analyze
     * a Practical Desire
     * @param belief_Type
     * @param func
     */
    public boolean Reasoner_Unregister_Practical_Function(String Desire_Name) 
    {
    	return this.Exec_Reasoner.Unregister_Practical_Function( Desire_Name );
    }

    /**
     * For Practical Desire: This Function Registers the Function to invoke when the Inhibition Function 
     * has to compute the Beliefs to keep in Global Works Space 
     * @param Desire_Name
     * @param func
     */
    public void Inhibition_Register_Practical_Belief_Inhibition_Function(String Desire_Name, TBelief_Inhibition_Function func) 
    {
		this.Exec_Inhibition_Func.
				Register_Practical_Belief_Inhibition_Function(Desire_Name, func);
    }
    
    /**
     * For Practical Desire: This Function Unregisters the Function to invoke when the Inhibition Function 
     * has to compute the Beliefs to keep in Global Works Space 
     * @param Desire_Name
     * @param func
     */
    public boolean Inhibition_Unregister_Practical_Belief_Inhibition_Function(String Desire_Name) 
    {
    	return this.Exec_Inhibition_Func.
    			Unregister_Practical_Belief_Inhibition_Function(Desire_Name);
    }
    
    /**
     * For Epistemic Desire: This Function Registers the Function to invoke when the Inhibition Function 
     * has to compute the Beliefs to keep in Global Works Space 
     * @param Desire_Name
     * @param func
     */
	public void Inhibition_Register_Epistemic_Belief_Inhibition_Function(String Desire_Name, TBelief_Inhibition_Function func) 
    {
		this.Exec_Inhibition_Func.
			Register_Epistemic_Belief_Inhibition_Function(Desire_Name, func);
    }
    
	/**
     * For Epistemic Desire: This Function Unregisters the Function to invoke when the Inhibition Function 
     * has to compute the Beliefs to keep in Global Works Space 
     * @param Desire_Name
     * @param func
     */
    public boolean Inhibition_Unregister_Epistemic_Belief_Inhibition_Function(String Desire_Name) 
    {
    	return this.Exec_Inhibition_Func.
    			Unregister_Epistemic_Belief_Inhibition_Function(Desire_Name);
    }
    /**
     * 
     * 
     * 
     * 
     */
    
    public void Inhibition_Register_Practical_Region_Function(String Name, TRegion_Inhibition_Function func) 
    {
    	this.Exec_Inhibition_Func.Register_Practical_Region_Inhibition_Function(Name, func);
    }
    
    public boolean Inhibition_Unregister_Practical_Region_Function(String Name) 
    {
        return this.Exec_Inhibition_Func.Unregister_Practical_Region_Inhibition_Function(Name);
    }
    
    public void Inhibition_Register_Epistemic_Region_Function(String Name, TRegion_Inhibition_Function func) 
    {
        this.Exec_Inhibition_Func.Register_Epistemic_Region_Inhibition_Function(Name, func);
    }
    
    public boolean Inhibition_Unregister_Epistemic_Region_Function(String Name) 
    {
        return this.Exec_Inhibition_Func.Unregister_Epistemic_Region_Inhibition_Function(Name);
    }
    
    public ArrayList<TAttentional_Desire> Get_Current_Attentional_Desires()
    {
    	ArrayList<TAttentional_Desire> result = new ArrayList<TAttentional_Desire>();
    	result.addAll( this.Long_Memory.Get_All_Attentional_Desires() );
    	return result;
    }
    
}
