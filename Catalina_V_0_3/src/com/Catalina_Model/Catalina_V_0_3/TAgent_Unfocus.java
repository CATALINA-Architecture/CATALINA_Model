package com.Catalina_Model.Catalina_V_0_3;

public class TAgent_Unfocus 
{
	private TAttention_Selection Parent;
	
	private Double Un_Focused_Saliency_Threshold;
	private Double Un_Focused_Attention_Threshold;
	
	public TAgent_Unfocus(TAttention_Selection Owner)
	{
		this.Parent = Owner;
		this.Un_Focused_Saliency_Threshold = 0.0;
		this.Un_Focused_Attention_Threshold = 0.0;
	}
	
	public void Execute()
	{
		Un_Focused_Saliency_Threshold = this.Parent.Get_Global_Global_Workspace().Get_Default_Saliency_Thresholds();
		Un_Focused_Attention_Threshold = this.Parent.Get_Global_Global_Workspace().Get_Default_Attention_Thresholds();
	}
	
	public Double Get_Un_Focused_Saliency_Threshold()
	{
		return this.Un_Focused_Saliency_Threshold;
	}
	
	public Double Get_Un_Focused_Attention_Threshold()
	{
		return this.Un_Focused_Attention_Threshold;
	}

}
