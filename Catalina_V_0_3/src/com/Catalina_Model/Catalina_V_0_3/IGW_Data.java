package com.Catalina_Model.Catalina_V_0_3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public interface IGW_Data
{

	public HashMap<String, TBelief> Get_Map_Uninhibited_Beliefs();
	public HashMap<String, TRegion> Get_Map_Uninhibited_Regions();
	public ArrayList<TAttentional_Desire> Get_Unhinibited_Active_Desires();
	public ArrayList<TIntention> Get_Selected_Intentions();
	public ArrayList<TAction> Get_Plans_from_Plan_Library(TAttentional_Desire Attentional_Desire,
			ArrayList<TPredicate> Preconditions);
	public HashMap<String, TBelief> Recal_Beliefs(HashSet<String> Beliefs_Names);
	public TDouble_Object Get_Saliency_and_Attention_Thresholds();
	
	
}
