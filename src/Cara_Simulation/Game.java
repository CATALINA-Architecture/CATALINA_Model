package Cara_Simulation;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * It represents the simulation game for the CATALINA Architecture 
 */
public class Game {
	
	public static Environment Map;
	public static Integer Scenario_Number;
	public static TTCS TCS;
	public static int Init_Creation_Path_Travelled_for_Functional_Goal;
	
	/**
	 * Start Simulation
	 * @param args
	 */
	public static void main(String[] args) 
	{
		Scenario_Number = 0;
		
		// I create a list of Predicates, Beliefs, Functional, Epistemic, Green and Quality Goals.
        
		Map = new Environment();
		Create_Map();
		TFile_Manager mana = new TFile_Manager();
		mana.Set_Map(Map);
		mana.Create_Predicates();
		mana.Create_Beliefs();
		Init_Creation_Path_Travelled_for_Functional_Goal = mana.Init_Creation_Path_Travelled_for_Functional_Goal;
		mana.Create_Green_Goals();
		mana.Create_Quality_Goals();
		mana.Create_Functional_Goals();
		mana.Create_Epistemic_Goals();
		
		Game.Print("Creatiddddng Map");
		
		Instant start = Instant.now();
//		Map = new Environment();
//		Create_Map();
		Instant end = Instant.now();
		Duration timeElapsed = Duration.between(start, end);
		Game.Print("I created the map in Time: "+ timeElapsed.toMillis() +" milliseconds");
		
		TCS = new TTCS(Map);

		TAgent Agent = new TAgent();
		
		Game.Print("Agent initialization...");
		Agent.Initialization(TCS, Map, Init_Creation_Path_Travelled_for_Functional_Goal);
		
		// The Working Cycle of the Agent starts
		Agent.Start();
		End_Game();
	}
		
	/**
	 * This method creates all archs (routes) between any station.  
	 */
	public static void Create_Map()
	{
		//Creating all paths RED
		Map.Add_Arch(Station.Pamplona, Station.Marseille, Color.Red, 4);
		Map.Add_Arch(Station.Paris, Station.Bruxelles, Color.Red, 2);
		Map.Add_Arch(Station.Frankfurt, Station.Berlin, Color.Red, 3);
		Map.Add_Arch(Station.Wien, Station.Budapest, Color.Red, 1);
		Map.Add_Arch(Station.Zacrab, Station.Sarajevo, Color.Red, 3);
		Map.Add_Arch(Station.Warszawa, Station.Wilno, Color.Red, 3);
		Map.Add_Arch(Station.Kyiv, Station.Smolensk, Color.Red, 3);
		Map.Add_Arch(Station.Erzurum, Station.Sochi, Color.Red, 3);
		
		//Creating all paths GREEN
		Map.Add_Arch(Station.Pamplona, Station.Paris, Color.Green, 4);
		Map.Add_Arch(Station.Dieppe, Station.Bruxelles, Color.Green, 2);
		Map.Add_Arch(Station.Frankfurt, Station.Essen, Color.Green, 2);
		Map.Add_Arch(Station.Zurich, Station.Venice, Color.Green, 2);
		Map.Add_Arch(Station.Berlin, Station.Wien, Color.Green, 3);
		Map.Add_Arch(Station.Sarajevo, Station.Athina, Color.Green, 4);
		Map.Add_Arch(Station.Rica, Station.Wilno, Color.Green, 4);
		Map.Add_Arch(Station.Kharkov, Station.Rostow, Color.Green, 2);
		
		//Creating all paths YELLOW
		Map.Add_Arch(Station.Madrid, Station.Barcelona, Color.Yellow, 2);
		Map.Add_Arch(Station.Paris, Station.Bruxelles, Color.Yellow, 2);
		Map.Add_Arch(Station.Amsterdam, Station.Essen, Color.Yellow, 3);
		Map.Add_Arch(Station.Zurich, Station.Munchen, Color.Yellow, 2);
		Map.Add_Arch(Station.Kodenilavn, Station.Stockholm, Color.Yellow, 3);
		Map.Add_Arch(Station.Berlin, Station.Warszawa, Color.Yellow, 4);
		Map.Add_Arch(Station.Bucaresti, Station.Costantinople, Color.Yellow, 3);
		Map.Add_Arch(Station.Wilno, Station.Smolensk, Color.Yellow, 3);
		
		//Creating all paths ORANGE
		Map.Add_Arch(Station.Madrid, Station.Cadiz, Color.Orange, 3);
		Map.Add_Arch(Station.Brest, Station.Dieppe, Color.Orange, 2);
		Map.Add_Arch(Station.Edimburgh, Station.London, Color.Orange, 4);
		Map.Add_Arch(Station.Paris, Station.Frankfurt, Color.Orange, 3);
		Map.Add_Arch(Station.Munchen, Station.Wien, Color.Orange, 3);
		Map.Add_Arch(Station.Zacrab, Station.Budapest, Color.Orange, 2);
		Map.Add_Arch(Station.Smyrna, Station.Ancora, Color.Orange, 3);
		Map.Add_Arch(Station.Smolensk, Station.Moskva, Color.Orange, 2);
		
		//Creating all paths BLACK
		Map.Add_Arch(Station.Madrid, Station.Pamplona, Color.Black, 3);
		Map.Add_Arch(Station.Brest, Station.Paris, Color.Black, 3);
		Map.Add_Arch(Station.Edimburgh, Station.London, Color.Black, 4);
		Map.Add_Arch(Station.Bruxelles, Station.Amsterdam, Color.Black, 1);
		Map.Add_Arch(Station.Frankfurt, Station.Berlin, Color.Black, 3);
		Map.Add_Arch(Station.Venice, Station.Rome, Color.Black, 2);
		Map.Add_Arch(Station.Danzic, Station.Rica, Color.Black, 3);
		Map.Add_Arch(Station.Ancora, Station.Erzurum, Color.Black, 3);
		
		//Creating all paths BLU
		Map.Add_Arch(Station.Lisboa, Station.Cadiz, Color.Blue, 2);
		Map.Add_Arch(Station.Pamplona, Station.Paris, Color.Blue, 4);		
		Map.Add_Arch(Station.Bruxelles, Station.Frankfurt, Color.Blue, 2);
		Map.Add_Arch(Station.Essen, Station.Berlin, Color.Blue, 2);
		Map.Add_Arch(Station.Munchen, Station.Venice, Color.Blue, 2);		
		Map.Add_Arch(Station.Wien, Station.Warszawa, Color.Blue, 4);
		Map.Add_Arch(Station.Sofia, Station.Costantinople, Color.Blue, 3);
		Map.Add_Arch(Station.Wilno, Station.Petrocrad, Color.Blue, 4);
		
		//Creating all paths VIOLET
		Map.Add_Arch(Station.Lisboa, Station.Madrid, Color.Violet, 3);
		Map.Add_Arch(Station.Pamplona, Station.Brest, Color.Violet, 4);		
		Map.Add_Arch(Station.Dieppe, Station.Paris, Color.Violet, 1);
		Map.Add_Arch(Station.Marseille, Station.Zurich, Color.Violet, 2);
		Map.Add_Arch(Station.Frankfurt, Station.Munchen, Color.Violet, 2);		
		Map.Add_Arch(Station.Berlin, Station.Warszawa, Color.Violet, 4);
		Map.Add_Arch(Station.Budapest, Station.Sarajevo, Color.Violet, 3);
		Map.Add_Arch(Station.Sofia, Station.Athina, Color.Violet, 3);
		
		//Creating all paths WHITE
		Map.Add_Arch(Station.Madrid, Station.Pamplona, Color.White, 3);////aggiornare sulla mappa
		
		//Creating all paths GRIGI
		// -----BLU
		Map.Add_Arch(Station.Marseille, Station.Barcelona, Color.Blue, 4);
		Map.Add_Arch(Station.Dieppe, Station.London, Color.Blue, 2);

		// -----GREEN
		Map.Add_Arch(Station.Pamplona, Station.Barcelona, Color.Green, 2);
		Map.Add_Arch(Station.Marseille, Station.Rome, Color.Green, 4);
		
		// -----RED
		Map.Add_Arch(Station.Paris, Station.Zurich, Color.Red, 3);
		
		// -----YELLOW
		Map.Add_Arch(Station.Marseille, Station.Paris, Color.Yellow, 4);
		Map.Add_Arch(Station.Dieppe, Station.London, Color.Yellow, 2);
		
		//-----VIOLET
		Map.Add_Arch(Station.London, Station.Amsterdam, Color.Violet, 2);
		
		//-----ORANGE
		Map.Add_Arch(Station.Rome, Station.Palermo, Color.Orange, 4);
		
	}
	
	/**
	 * It ends the simulation
	 */
	public static void End_Game()
	{
		Game.Print("End Simulation.");
		System.exit(0);
	}
	
	/**
	 * It prints a text.
	 * @param Text	A string to print
	 */
	public static void Print(Object Text)
	{
		System.out.println(Text);
	}
	
	/**
	 * Prints a text and goes to a new line
	 * @param Text	A string to print
	 */
	public static void PrintLn(Object Text)
	{
		System.out.println(Text+"\n");
	}
	
	/**
	 * It insert a new line
	 */
	public static void PrintLn()
	{
		System.out.println();
	}
	
	/**
	 * It stops the simulation for a number of seconds
	 * @param seconds	An integer, represents the number of seconds
	 */
	public static void Sleep_Seconds(long seconds)
	{
		try {
			TimeUnit.SECONDS.sleep(seconds);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * It prints the Scenario number
	 */
	public static void Print_Scenario()
	{
		Game.Print("******* Scenario_Number:    --"+Scenario_Number+"-- *******");
	}
	
	/**
	 * It gets some input from user. It is used to stop the flow of the agent while it works. 
	 */
	public static String Get_Input(String Text)
	{
		 String ANSI_RESET = "\u001B[0m";
		 String ANSI_RED = "\u001B[31m";
		 
		 
		 
		 if(Text == null)
		 {
			 Text = "";
		 }
		 
		 Game.Print(ANSI_RED + Text + ANSI_RESET);
		 Scanner myObj = new Scanner(System.in);  // Create a Scanner object
		 String result = myObj.nextLine();  // Read user input
		 return result;
	}
	
	public static String Get_Preset_Input(String Text, String Preset_Input, int color)
	{
//		Game.Print(ANSI_BOLD + Color + Text + ANSI_RESET);
		
		
		 Print_Colored_Text(Text, color) ;
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
	
	
	
	public static String Press_Enter()
	{
		 String ANSI_RESET = "\u001B[0m";
		 String ANSI_RED = "\u001B[31m";
		 
		 Game.Print(ANSI_RED + "Press return to continue..." + ANSI_RESET);
		 Scanner myObj = new Scanner(System.in);  // Create a Scanner object
		 String result = myObj.nextLine();  // Read user input
		 return result;
	}
	
	
	
	public static void Print_Colored_Text(String Text, int color)
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
		 
		Game.Print(ANSI_BOLD + Color + Text + ANSI_RESET);
	}
}
	