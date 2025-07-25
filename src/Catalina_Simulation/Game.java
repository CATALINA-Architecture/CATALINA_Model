package Catalina_Simulation;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

///////////////////////////////

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

class GUI extends JFrame 
{

    public Map_Panel Map_Panel;

    public GUI(String Map_PathPath, String Car_Path, List<Point> Positions) {
    	
        setTitle("Machine Movement Simulation");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Map_Panel = new Map_Panel(Map_PathPath, Car_Path, Positions);
        add(Map_Panel, BorderLayout.CENTER);

        JButton Move_Button = new JButton("Move Car");
        Move_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Map_Panel.Move_Car();
            }
        });
        add(Move_Button, BorderLayout.SOUTH);

        // Fit window size to components
        pack(); 
        // Center the window on the screen
        setLocationRelativeTo(null); 
        setVisible(true);
    }
    
    public void Show_Message(String Title, String Message, Integer Type_Message)
    {
    	if(Type_Message == null)
    	{
    		Type_Message = JOptionPane.INFORMATION_MESSAGE;
    	}
    	JLabel Message_Label = new JLabel(Message);

        // Set the font of the JLabel
        // "Arial" is the font name, 
    	// Font.PLAIN is the style (you can use Font.BOLD, Font.ITALIC),
    	// 12 is the text size
        Message_Label.setFont(new Font("Arial", Font.PLAIN, 12));
        
    	JOptionPane.showMessageDialog(this, Message_Label , Title, Type_Message);
    }

}

///////////////////////////////


/**
 * It represents the simulation game for the CATALINA Architecture 
 */
public class Game {
	
	public static Environment Map;
	public static Integer Scenario_Number;
	public static TTCS TCS;
	public static int Init_Creation_Path_Travelled_for_Functional_Desire;
	public static GUI Gui_Map;
	public static Boolean Disable_Input = true;
	public static Boolean Disable_Show_Message = true;
	
	/**
	 * Start Simulation
	 * @param args
	 */
	public static void main(String[] args) 
	{

		// String workingDirectory = System.getProperty("user.dir");
		// System.out.println("Directory di lavoro: " + workingDirectory);
        
        String Map_Path = "img/ticket to ride.jpeg";
        // String Car_Path = "img/m4.png";
        String Car_Path = "img/auto.png";

        // Define the default positions where to move the car
        List<Point> Positions = Arrays.asList(
                new Point(50, 50),
                new Point(200, 100),
                new Point(100, 250),
                new Point(300, 150)
        );

        // Create and launch the GUI
        Gui_Map = new GUI(Map_Path, Car_Path, Positions);
        //SwingUtilities.invokeLater(() -> Gui_Map);
        
		///////////////
		Scenario_Number = 0;
		
		// I create a list of Predicates, Beliefs, Functional, Epistemic, Green and Quality Goals.
        
		Map = new Environment();
		Create_Map();
		TFile_Manager mana = new TFile_Manager();
		mana.Set_Map(Map);
		mana.Create_Predicates();
		mana.Create_Beliefs();
		Init_Creation_Path_Travelled_for_Functional_Desire = mana.Init_Creation_Path_Travelled_for_Functional_Desire;
		mana.Create_Green_Desires();
		mana.Create_Quality_Desires();
		mana.Create_Functional_Desires();
		mana.Create_Epistemic_Desires();
		
		
		Instant start = Instant.now();
//		Map = new Environment();
//		Create_Map();
		Instant end = Instant.now();
		Duration timeElapsed = Duration.between(start, end);
		Game.Print("I created the map in Time: "+ timeElapsed.toMillis() +" milliseconds");
		
		TCS = new TTCS(Map);

		TAgent Agent = new TAgent();
		
		Game.Print("Agent initialization...");
		Agent.Initialization(TCS, Map, Init_Creation_Path_Travelled_for_Functional_Desire);
		
		// The Working Cycle of the Agent starts
		Agent.Start();
	}
		
	/**
	 * This method creates all archs (routes) between any station.  
	 */
	public static void Create_Map()
	{
		//Creating all paths RED
		Map.Add_Arch(City.Pamplona, City.Marseille, Color.Red, 4);
		Map.Add_Arch(City.Paris, City.Bruxelles, Color.Red, 2);
		Map.Add_Arch(City.Frankfurt, City.Berlin, Color.Red, 3);
		Map.Add_Arch(City.Wien, City.Budapest, Color.Red, 1);
		Map.Add_Arch(City.Zacrab, City.Sarajevo, Color.Red, 3);
		Map.Add_Arch(City.Warszawa, City.Wilno, Color.Red, 3);
		Map.Add_Arch(City.Kyiv, City.Smolensk, Color.Red, 3);
		Map.Add_Arch(City.Erzurum, City.Sochi, Color.Red, 3);
		
		//Creating all paths GREEN
		Map.Add_Arch(City.Pamplona, City.Paris, Color.Green, 4);
		Map.Add_Arch(City.Dieppe, City.Bruxelles, Color.Green, 2);
		Map.Add_Arch(City.Frankfurt, City.Essen, Color.Green, 2);
		Map.Add_Arch(City.Zurich, City.Venice, Color.Green, 2);
		Map.Add_Arch(City.Berlin, City.Wien, Color.Green, 3);
		Map.Add_Arch(City.Sarajevo, City.Athina, Color.Green, 4);
		Map.Add_Arch(City.Rica, City.Wilno, Color.Green, 4);
		Map.Add_Arch(City.Kharkov, City.Rostow, Color.Green, 2);
		
		//Creating all paths YELLOW
		Map.Add_Arch(City.Madrid, City.Barcelona, Color.Yellow, 2);
		Map.Add_Arch(City.Paris, City.Bruxelles, Color.Yellow, 2);
		Map.Add_Arch(City.Amsterdam, City.Essen, Color.Yellow, 3);
		Map.Add_Arch(City.Zurich, City.Munchen, Color.Yellow, 2);
		Map.Add_Arch(City.Kodenilavn, City.Stockholm, Color.Yellow, 3);
		Map.Add_Arch(City.Berlin, City.Warszawa, Color.Yellow, 4);
		Map.Add_Arch(City.Bucaresti, City.Costantinople, Color.Yellow, 3);
		Map.Add_Arch(City.Wilno, City.Smolensk, Color.Yellow, 3);
		
		//Creating all paths ORANGE
		Map.Add_Arch(City.Madrid, City.Cadiz, Color.Orange, 3);
		Map.Add_Arch(City.Brest, City.Dieppe, Color.Orange, 2);
		Map.Add_Arch(City.Edinburgh, City.London, Color.Orange, 4);
		Map.Add_Arch(City.Paris, City.Frankfurt, Color.Orange, 3);
		Map.Add_Arch(City.Munchen, City.Wien, Color.Orange, 3);
		Map.Add_Arch(City.Zacrab, City.Budapest, Color.Orange, 2);
		Map.Add_Arch(City.Smyrna, City.Ancora, Color.Orange, 3);
		Map.Add_Arch(City.Smolensk, City.Moskva, Color.Orange, 2);
		
		//Creating all paths BLACK
		Map.Add_Arch(City.Madrid, City.Pamplona, Color.Black, 3);
		Map.Add_Arch(City.Brest, City.Paris, Color.Black, 3);
		Map.Add_Arch(City.Edinburgh, City.London, Color.Black, 4);
		Map.Add_Arch(City.Bruxelles, City.Amsterdam, Color.Black, 1);
		Map.Add_Arch(City.Frankfurt, City.Berlin, Color.Black, 3);
		Map.Add_Arch(City.Venice, City.Rome, Color.Black, 2);
		Map.Add_Arch(City.Danzic, City.Rica, Color.Black, 3);
		Map.Add_Arch(City.Ancora, City.Erzurum, Color.Black, 3);
		
		//Creating all paths BLU
		Map.Add_Arch(City.Lisboa, City.Cadiz, Color.Blue, 2);
		Map.Add_Arch(City.Pamplona, City.Paris, Color.Blue, 4);		
		Map.Add_Arch(City.Bruxelles, City.Frankfurt, Color.Blue, 2);
		Map.Add_Arch(City.Essen, City.Berlin, Color.Blue, 2);
		Map.Add_Arch(City.Munchen, City.Venice, Color.Blue, 2);		
		Map.Add_Arch(City.Wien, City.Warszawa, Color.Blue, 4);
		Map.Add_Arch(City.Sofia, City.Costantinople, Color.Blue, 3);
		Map.Add_Arch(City.Wilno, City.Petrocrad, Color.Blue, 4);
		
		//Creating all paths VIOLET
		Map.Add_Arch(City.Lisboa, City.Madrid, Color.Violet, 3);
		Map.Add_Arch(City.Pamplona, City.Brest, Color.Violet, 4);		
		Map.Add_Arch(City.Dieppe, City.Paris, Color.Violet, 1);
		Map.Add_Arch(City.Marseille, City.Zurich, Color.Violet, 2);
		Map.Add_Arch(City.Frankfurt, City.Munchen, Color.Violet, 2);		
		Map.Add_Arch(City.Berlin, City.Warszawa, Color.Violet, 4);
		Map.Add_Arch(City.Budapest, City.Sarajevo, Color.Violet, 3);
		Map.Add_Arch(City.Sofia, City.Athina, Color.Violet, 3);
		
		//Creating all paths WHITE
		Map.Add_Arch(City.Madrid, City.Pamplona, Color.White, 3);////aggiornare sulla mappa
		
		//Creating all paths Grey
		// -----BLU
		Map.Add_Arch(City.Marseille, City.Barcelona, Color.Blue, 4);
		Map.Add_Arch(City.Dieppe, City.London, Color.Blue, 2);

		// -----GREEN
		Map.Add_Arch(City.Pamplona, City.Barcelona, Color.Green, 2);
		Map.Add_Arch(City.Marseille, City.Rome, Color.Green, 4);
		
		// -----RED
		Map.Add_Arch(City.Paris, City.Zurich, Color.Red, 3);
		
		// -----YELLOW
		Map.Add_Arch(City.Marseille, City.Paris, Color.Yellow, 4);
		Map.Add_Arch(City.Dieppe, City.London, Color.Yellow, 2);
		
		//-----VIOLET
		Map.Add_Arch(City.London, City.Amsterdam, Color.Violet, 2);
		
		//-----ORANGE
		Map.Add_Arch(City.Rome, City.Palermo, Color.Orange, 4);
		
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
		if(Disable_Input)
		{
			return "";
		}
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