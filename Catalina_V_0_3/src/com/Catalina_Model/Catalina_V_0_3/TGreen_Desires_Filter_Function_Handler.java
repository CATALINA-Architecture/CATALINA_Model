package com.Catalina_Model.Catalina_V_0_3;

import java.util.ArrayList;
import java.util.HashMap;

import com.Catalina_Model.Catalina_V_0_3.TMeans_End_Epistemic_Functions_Handler.Means_End_Epistemic_Function;

public class TGreen_Desires_Filter_Function_Handler 
{

	@FunctionalInterface
    public interface Green_Desires_Filter_Function 
    {
        ArrayList<TOption> apply(TGreen_Desire Green_Desire, TAttentional_Desire Desire,
                                 ArrayList<TBelief> beliefs,
                                 ArrayList<TRegion> regions,
                                 ArrayList<TIntention> intentions);
    }
	
	private final HashMap<String, Green_Desires_Filter_Function> Registered_Functions;
	
	public TGreen_Desires_Filter_Function_Handler() 
	{
		this.Registered_Functions = new HashMap<String, TGreen_Desires_Filter_Function_Handler.Green_Desires_Filter_Function>();
	}
	
	public void Register_Function(String Green_Name, Green_Desires_Filter_Function func) 
    {
        if (Green_Name == null || Green_Name.trim().isEmpty() || func == null) 
        {
            throw new IllegalArgumentException("Invalid beliefType or function for registration.");
        }
        this.Registered_Functions.put(Green_Name, func);
    }
    
    public boolean Unregister_Function(String Green_Name) 
    {
        if (Green_Name == null || Green_Name.trim().isEmpty())
        {
            return false;
        }
        return this.Registered_Functions.remove(Green_Name) != null;
    }
    
    public ArrayList<TOption> Execute_Function_For_Desire(TGreen_Desire Green_Desire, 
    		TAttentional_Desire Desire,
            ArrayList<TBelief> beliefs,
            ArrayList<TRegion> regions,
            ArrayList<TIntention> intentions)
    {
    	ArrayList<TOption> result = new ArrayList<TOption>();
    	result.addAll( Desire.Get_List_Options());
    	
        if (Green_Desire == null) 
        {
            System.err.println("Error: Green Desire provided for execution is null.");
            return result;
        }

        String Green_Desire_Type = Green_Desire.Get_Type_Green_Standing_Desire();
//        System.out.println("Green_Desire.Get_Type_Green_Standing_Desire(): "+Green_Desire.Get_Type_Green_Standing_Desire());

        if (Green_Desire_Type == null || Green_Desire_Type.trim().isEmpty()) 
        {
            System.err.println("Error: Associated Green Desire Name is null or empty in TEpistemic_Desire: " + Desire);
            return result;
        }
//        System.out.println(this.Registered_Functions);
        Green_Desires_Filter_Function func = this.Registered_Functions.get(Green_Desire_Type);
//        ArrayList<TOption> results = new ArrayList<TOption>();
        ArrayList<TOption> temp_results = new ArrayList<TOption>();

        if (func != null) 
        {
        	temp_results = func.apply(Green_Desire, Desire, beliefs, regions, intentions);
        	if (temp_results !=null)
        	{
        		result.clear();
        		result.addAll( temp_results );
        	}
        } 
        else 
        {
//            System.out.println("\nWARN: No Green Desires Filter_Function  registered for the Belief Type: '" + Green_Desire_Type + "'.");
        }
        return result;
    }

}
