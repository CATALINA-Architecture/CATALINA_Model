package com.Catalina_Model.Catalina_V_0_3;

import java.util.ArrayList;

public interface IPlanner
{
	public ArrayList<TAction> Get_Plans(TAttentional_Desire Attentional_Desire,
			ArrayList<TPredicate> Preconditions 
			);
}
