import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import com.Catalina_Model.Catalina_V_0_3.TBelief;

public class TCommon_Functions 
{
	public Autonomous_Vehicle_Demo Demo;
	
	public TCommon_Functions(Autonomous_Vehicle_Demo demo)
	{
		this.Demo = demo;
		
	}
	
	public HashMap<String, TBelief> Get_Map_Beliefs_by_Type(ArrayList<TBelief> Beliefs, String Belief_Type)
	{
		HashMap<String, TBelief> result = new HashMap<String, TBelief>();
		for (TBelief Belief :Beliefs)
		{
			if ( Belief.Get_Type_Belief().equals(Belief_Type))
			{
				result.put(Belief.Get_Name(), Belief);
			}
		}
		return result;
	}
	
	public ArrayList<TBelief> Get_Array_Beliefs_by_Type(ArrayList<TBelief> Beliefs, String Belief_Type)
	{
		ArrayList<TBelief> result = new ArrayList<TBelief>();
		for (TBelief Belief :Beliefs)
		{
			if ( Belief.Get_Type_Belief().equals(Belief_Type))
			{
				result.add(Belief);
			}
		}
		return result;
	}
	
	public HashMap<String, TBelief> Get_Beliefs_by_Name(ArrayList<TBelief> Beliefs, String Belief_Name)
	{
		HashMap<String, TBelief> result = new HashMap<String, TBelief>();
		for (TBelief Belief :Beliefs)
		{
			if ( Belief.Get_Name().equals(Belief_Name))
			{
				result.put(Belief.Get_Name(), Belief);
			}
		}
		return result;
	}
	
	public String Get_Preset_Input(String Text, String Preset_Input, int color, 
			boolean print_preset_input)
	{
//		Game.Print(ANSI_BOLD + Color + Text + ANSI_RESET);
		
		 Print_Colored_Text(Text, color) ;
		 if (print_preset_input)
			 System.out.print(Preset_Input);
//		 String Answer = Get_Input(null) ;
		
		 Scanner myObj = new Scanner(System.in);  // Create a Scanner object
		 String Answer = myObj.nextLine();  // Read user input
		 if(Answer == "")
		 {
			 Answer = Preset_Input;
		 }
		 return Answer;
	}
	/**
	 * Colors:
	 * 1- ANSI_BLACK 
	 * 2- ANSI_RED
	 * 3- ANSI_GREEN 
	 * 4- ANSI_YELLOW
	 * 5- ANSI_BLUE 
	 * 6- ANSI_PURPLE 
	 * 7- ANSI_CYAN
	 * 8- ANSI_WHITE 
	 * @param Text
	 * @param color
	 */
	public void Print_Colored_Text(String Text, int color)
	{
		String ANSI_RESET = "\u001B[0m";	//0
		String ANSI_BOLD = "\u001B[1m";	//0
		String ANSI_BLACK = "\u001B[30m";	//1
		String ANSI_RED = "\u001B[31m";		//2
		String ANSI_GREEN = "\u001B[32m";	//3
		String ANSI_YELLOW = "\u001B[33m";	//4
		String ANSI_BLUE = "\u001B[34m";	//5
		String ANSI_PURPLE = "\u001B[35m";	//6
		String ANSI_CYAN = "\u001B[36m";	//7
		String ANSI_WHITE = "\u001B[37m";	//8
		
		String Color = ANSI_BLACK;
		if(Text == null)
		{
			Text = "";
		}
		switch(color)
		{
		case 1:
			Color = ANSI_BLACK;
			break;
		case 2:
			Color = ANSI_RED;
			break;
		case 3:
			Color = ANSI_GREEN;
			break;
		case 4:
			Color = ANSI_YELLOW;
			break;
		case 5:
			Color = ANSI_BLUE;
			break;
		case 6:
			Color = ANSI_PURPLE;
			break;
		case 7:
			Color = ANSI_CYAN;
			break;
		case 8:
			Color = ANSI_WHITE;
			break;
		}
		
//		System.out.println(ANSI_BOLD + Color + Text + ANSI_RESET);
//		this.Print(ANSI_BOLD + Color + Text + ANSI_RESET);
		this.Print(Text );
	}

	public void Print(Object Text)
	{
//		System.out.println(Text);
		PrintLn(Text);
//		
	}
	
	public void PrintLn(Object Text)
	{
//		System.out.println("");
		
		this.Demo.Gui_Map.Log_Memo.append(Text.toString()+"\n");
		this.Demo.Gui_Map.Log_Memo.setCaretPosition(
				this.Demo.Gui_Map.Log_Memo.getDocument().getLength());
		
		
	}
}
