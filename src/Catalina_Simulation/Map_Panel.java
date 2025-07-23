package Catalina_Simulation;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;


public class Map_Panel extends JPanel {

    private BufferedImage Map_Image_Original; // Keeps the original map image
    public BufferedImage Map_Image;
    private BufferedImage Car_Image_Original; // Keeps the original image
    public BufferedImage Car_Image;
    private Point Car_Position;
    private List<Point> List_Defined_Positions;
    private int Index_Current_Position = 0;
    public HashMap<City, Point> Stations_Coords;
    public HashMap<Integer, ArrayList<Point>> Ruotes_Coords;
    public Timer Motion_Timer;
    private Point Final_Position;
    private long Start_Time;
    private long Motion_Duration;
    private Environment Map;

    public Map_Panel(String Map_Path, String Car_Path, List<Point> Positions) {
        try 
        {
        	Map = new Environment();
        	this.Stations_Coords = new HashMap<City, Point>();
        	this.Create_Stations_Coords();
        	
        	this.Ruotes_Coords = new HashMap<Integer, ArrayList<Point>>();
        	this.Create_Ruotes_Coords();

        	this.Map_Image_Original = ImageIO.read(new File(Map_Path));
        	this.Resize_Map(1.2); // Zooms in to 120%

            this.Car_Image_Original = ImageIO.read(new File(Car_Path));
            this.Resize_Car(0.2); // Resize to 20%
            this.List_Defined_Positions = Positions;
            if (!List_Defined_Positions.isEmpty()) {
                Car_Position = new Point(List_Defined_Positions.get(0));
            } else {
                Car_Position = new Point(0, 0);
            }

            // Add MouseListener to the panel
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                	//This function is useful to get positions where the car have to move on path
                	//Left Button
                    if (e.getButton() == MouseEvent.BUTTON1) 
                    { // Check if the left button was pressed
                        Point clickedPoint = e.getPoint();
                        // "1111" must to be an intenger number and it represents a route
                        Game.Print("this.Add_Ruote_Coords( 1111, new Point("+clickedPoint.x+", "+clickedPoint.y+")); ");
                        Move_Car_At(clickedPoint.x,clickedPoint.y);
                    }
                    //Middle Button
                    if (e.getButton() == MouseEvent.BUTTON2) 
                    {
                    	Game.PrintLn();
                    	
                    }
                    //Rigth Button
                    if (e.getButton() == MouseEvent.BUTTON3) 
                    {
                    	// "1111" must to be an intenger number and it represents a route
                    	Game.Print("this.Add_Specular_Route_Coords(1111);");
                    	Game.PrintLn();
                    	Game.PrintLn();
                    }
                }
            });

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading images: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
        setPreferredSize(new Dimension(Map_Image.getWidth(), Map_Image.getHeight()));
    }
    
    public int Get_Machine_Height()
    {
    	
    	return Car_Image.getHeight();
    	
    	
    }
    public int Get_Machine_Witdh()
    {
    	return Car_Image.getWidth();
    }
    
    private void Resize_Map(double Scale_Factor) {
        if (Map_Image_Original != null) {
            int New_Width = (int) (Map_Image_Original.getWidth() * Scale_Factor);
            int New_Height = (int) (Map_Image_Original.getHeight() * Scale_Factor);
            Image Temp_Image = Map_Image_Original.getScaledInstance(New_Width, New_Height, Image.SCALE_SMOOTH);
            // Use TYPE_INT_ARGB for the map
            Map_Image = new BufferedImage(New_Width, New_Height, BufferedImage.TYPE_INT_RGB); 
            Graphics2D g2d = Map_Image.createGraphics();
            g2d.drawImage(Temp_Image, 0, 0, null);
            g2d.dispose();
        } else {
            Map_Image = null;
        }
    }
    
    public void Move_Car_At(int x, int y) {
    	Car_Position.setLocation(x, y);
        repaint(); // Force Map Panel redraw
    }
    
    private void Resize_Car(double Scale_Factor) {
        if (Car_Image_Original != null) {
            int New_Width = (int) (Car_Image_Original.getWidth() * Scale_Factor);
            int New_Height = (int) (Car_Image_Original.getHeight() * Scale_Factor);
            Image temp = Car_Image_Original.getScaledInstance(New_Width, New_Height, Image.SCALE_SMOOTH);
            Car_Image = new BufferedImage(New_Width, New_Height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = Car_Image.createGraphics();
            g2d.drawImage(temp, 0, 0, null);
            g2d.dispose();
            
        }
    }

    public void Move_Car() {
        if (List_Defined_Positions != null && !List_Defined_Positions.isEmpty()) {
            Index_Current_Position = (Index_Current_Position + 1) % List_Defined_Positions.size();
            Car_Position.setLocation(List_Defined_Positions.get(Index_Current_Position));
            repaint();
        }
    }
    
    public void Move_Car_Slowly(Point Destination, float Seconds) {
//        posizioneFinale = destinazione;
        Start_Time = System.currentTimeMillis();
        // Convert seconds to milliseconds
        Motion_Duration = (long) (Seconds * 1000);         int width = this.Get_Machine_Witdh() / 2;
        int Height = this.Get_Machine_Height() / 2;
        Point Final_Position = new Point(Destination.x - width, Destination.y - Height);

        Motion_Timer = new Timer(30, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                long Current_Time = System.currentTimeMillis();
                double Progress = (double) (Current_Time - Start_Time) / Motion_Duration;

                if (Progress >= 1.0- 1e-6) {
                    Car_Position.setLocation(Final_Position);
                    repaint();
                    Motion_Timer.stop();
                } else {
                    int x = (int) (Car_Position.x + (Final_Position.x - Car_Position.x) * Progress);
                    int y = (int) (Car_Position.y + (Final_Position.y - Car_Position.y) * Progress);
                    Car_Position.setLocation(x, y);
                    repaint();
                }
            }
        });
        Motion_Timer.start();
        while(Motion_Timer.isRunning())
        {
        	
        }
    }

    // Method to add a new defined position by clicking
    public void Add_Position(Point p) {
        if (List_Defined_Positions != null) {
            List_Defined_Positions.add(p);
            System.out.println("Added new position: " + p);
        }
    }
    
    public void Create_Ruotes_Coords()
    {
    	//Route: Number - Departure - Destination - Color - Steps - Speed (steps per round)
    	//0 - Pamplona - Marseille - Red - 4 - 3
    	this.Add_Ruote_Coords( 0, new Point(372, 745)); 
    	this.Add_Ruote_Coords( 0, new Point(403, 714)); 
    	this.Add_Ruote_Coords( 0, new Point(448, 684)); 
    	this.Add_Ruote_Coords( 0, new Point(502, 698));
    	this.Add_Specular_Route_Coords(0);
    	
//    	//1 - Marseille - Pamplona - Red - 4 - 3
//    	this.Add_Ruote_Coords( 1, new Point(403, 714));
//    	this.Add_Ruote_Coords( 1, new Point(448, 684));
//    	this.Add_Ruote_Coords( 1, new Point(502, 698));
//    	this.Add_Ruote_Coords( 1, new Point(372, 745)); 
    	 
    	//2 - Paris - Bruxelles - Red - 2 - 3
    	this.Add_Ruote_Coords( 2, new Point(451, 467)); 
    	this.Add_Ruote_Coords( 2, new Point(476, 423));
    	this.Add_Specular_Route_Coords(2);
    	
//    	//3 - Bruxelles - Paris - Red - 2 - 3
//    	this.Add_Ruote_Coords( 3, new Point(476, 423));
//    	this.Add_Ruote_Coords( 3, new Point(451, 467)); 
    	
    	//4 - Frankfurt - Berlin - Red - 3 - 3
    	Add_Ruote_Coords( 4, new Point(648, 422)); 
    	this.Add_Ruote_Coords( 4, new Point(696, 399)); 
    	this.Add_Ruote_Coords( 4, new Point(746, 377));
    	this.Add_Specular_Route_Coords(4);
    	
    	//6 - Wien - Budapest - Red - 1 - 3
    	this.Add_Ruote_Coords( 6, new Point(894, 527));
    	this.Add_Specular_Route_Coords(6);
    	
    	//8 - Zacrab - Sarajevo - Red - 3 - 3
    	this.Add_Ruote_Coords( 8, new Point(844, 681)); 
    	this.Add_Ruote_Coords( 8, new Point(873, 729)); 
    	this.Add_Ruote_Coords( 8, new Point(927, 737));
    	this.Add_Specular_Route_Coords(8);
    	
    	//10 - Warszawa - Wilno - Red - 3 - 3
    	this.Add_Ruote_Coords( 10, new Point(1030, 302)); 
    	this.Add_Ruote_Coords( 10, new Point(1069, 272)); 
    	this.Add_Ruote_Coords( 10, new Point(1125, 280));
    	this.Add_Specular_Route_Coords(10);
    	
    	//12 - Kyiv - Smolensk - Red - 3 - 3
    	this.Add_Ruote_Coords( 12, new Point(1262, 408)); 
    	this.Add_Ruote_Coords( 12, new Point(1311, 386)); 
    	this.Add_Ruote_Coords( 12, new Point(1322, 335));
    	this.Add_Specular_Route_Coords(12);
    	
    	//14 - Erzurum - Sochi - Red - 3 - 3
    	this.Add_Ruote_Coords( 14, new Point(1464, 830)); 
    	this.Add_Ruote_Coords( 14, new Point(1469, 778)); 
    	this.Add_Ruote_Coords( 14, new Point(1474, 723));
    	this.Add_Specular_Route_Coords(14);
    	
    	//16 - Pamplona - Paris - Green - 4 - 2
    	this.Add_Ruote_Coords( 16, new Point(353, 700)); 
    	this.Add_Ruote_Coords( 16, new Point(381, 650)); 
    	this.Add_Ruote_Coords( 16, new Point(402, 597)); 
    	this.Add_Ruote_Coords( 16, new Point(411, 544)); 
    	this.Add_Specular_Route_Coords(16);
    	
    	//18 - Dieppe - Bruxelles - Green - 2 - 2
    	this.Add_Ruote_Coords( 18, new Point(394, 421)); 
    	this.Add_Ruote_Coords( 18, new Point(438, 389));
    	this.Add_Specular_Route_Coords(18);
    	
    	//20 - Frankfurt - Essen - Green - 2 - 2
    	this.Add_Ruote_Coords( 20, new Point(631, 390)); 
    	this.Add_Ruote_Coords( 20, new Point(658, 358)); 
    	this.Add_Specular_Route_Coords(20);
    	
    	//22 - Zurich - Venice - Green - 2 - 2
    	this.Add_Ruote_Coords( 22, new Point(632, 599)); 
    	this.Add_Ruote_Coords( 22, new Point(680, 625));
    	this.Add_Specular_Route_Coords(22);
    	
    	//24 - Berlin - Wien - Green - 3 - 2
    	this.Add_Ruote_Coords( 24, new Point(784, 387)); 
    	this.Add_Ruote_Coords( 24, new Point(806, 435)); 
    	this.Add_Ruote_Coords( 24, new Point(835, 478));
    	this.Add_Specular_Route_Coords(24);
    	
    	//26 - Sarajevo - Athina - Green - 4 - 2
    	this.Add_Ruote_Coords( 26, new Point(963, 766)); 
    	this.Add_Ruote_Coords( 26, new Point(963, 821)); 
    	this.Add_Ruote_Coords( 26, new Point(961, 874)); 
    	this.Add_Ruote_Coords( 26, new Point(997, 895)); 
    	this.Add_Specular_Route_Coords(26);
    	
    	//28 - Rica - Wilno - Green - 4 - 2
    	this.Add_Ruote_Coords( 28, new Point(1045, 145)); 
    	this.Add_Ruote_Coords( 28, new Point(1051, 197)); 
    	this.Add_Ruote_Coords( 28, new Point(1087, 237)); 
    	this.Add_Ruote_Coords( 28, new Point(1132, 264)); 
    	this.Add_Specular_Route_Coords(28);
    	
    	//30 - Kharkov - Rostow - Green - 2 - 2
    	this.Add_Ruote_Coords( 30, new Point(1460, 478)); 
    	this.Add_Ruote_Coords( 30, new Point(1479, 514)); 
    	this.Add_Specular_Route_Coords(30);
    	
    	//32 - Madrid - Barcelona - Yellow - 2 - 1
    	this.Add_Ruote_Coords( 32, new Point(243, 867)); 
    	this.Add_Ruote_Coords( 32, new Point(299, 865)); 
    	this.Add_Specular_Route_Coords(32);

    	//34 - Paris - Bruxelles - Yellow - 2 - 1
    	this.Add_Ruote_Coords( 34, new Point(432, 459)); 
    	this.Add_Ruote_Coords( 34, new Point(456, 411)); 
    	this.Add_Specular_Route_Coords(34);

    	//36 - Amsterdam - Essen - Yellow - 3 - 1
    	this.Add_Ruote_Coords( 36, new Point(514, 276)); 
    	this.Add_Ruote_Coords( 36, new Point(552, 265)); 
    	this.Add_Ruote_Coords( 36, new Point(602, 295)); 
    	this.Add_Specular_Route_Coords(36);

    	//38 - Zurich - Munchen - Yellow - 2 - 1
    	this.Add_Ruote_Coords( 38, new Point(624, 552)); 
    	this.Add_Ruote_Coords( 38, new Point(665, 516)); 
    	this.Add_Specular_Route_Coords(38);

    	//40 - Kodenilavn - Stockholm - Yellow - 3 - 1
    	this.Add_Ruote_Coords( 40, new Point(752, 142)); 
    	this.Add_Ruote_Coords( 40, new Point(788, 105)); 
    	this.Add_Ruote_Coords( 40, new Point(831, 77)); 
    	this.Add_Specular_Route_Coords(40);

    	//42 - Berlin - Warszawa - Yellow - 4 - 1
    	this.Add_Ruote_Coords( 42, new Point(811, 348)); 
    	this.Add_Ruote_Coords( 42, new Point(860, 336)); 
    	this.Add_Ruote_Coords( 42, new Point(912, 333)); 
    	this.Add_Ruote_Coords( 42, new Point(964, 337)); 
    	this.Add_Specular_Route_Coords(42);

    	//44 - Bucaresti - Costantinople - Yellow - 3 - 1
    	this.Add_Ruote_Coords( 44, new Point(1162, 683)); 
    	this.Add_Ruote_Coords( 44, new Point(1185, 731)); 
    	this.Add_Ruote_Coords( 44, new Point(1211, 781)); 
    	this.Add_Specular_Route_Coords(44);

    	//46 - Wilno - Smolensk - Yellow - 3 - 1
    	this.Add_Ruote_Coords( 46, new Point(1195, 271)); 
    	this.Add_Ruote_Coords( 46, new Point(1229, 252)); 
    	this.Add_Ruote_Coords( 46, new Point(1274, 281)); 
    	this.Add_Specular_Route_Coords(46);

    	//48 - Madrid - Cadiz - Orange - 3 - 2
    	this.Add_Ruote_Coords( 48, new Point(211, 885)); 
    	this.Add_Ruote_Coords( 48, new Point(240, 931)); 
    	this.Add_Ruote_Coords( 48, new Point(211, 961)); 
    	this.Add_Specular_Route_Coords(48);

    	//50 - Brest - Dieppe - Orange - 2 - 2
    	this.Add_Ruote_Coords( 50, new Point(252, 452)); 
    	this.Add_Ruote_Coords( 50, new Point(307, 437)); 
    	this.Add_Specular_Route_Coords(50);

    	//52 - Edimburgh - London - Orange - 4 - 2
    	this.Add_Ruote_Coords( 52, new Point(300, 127)); 
    	this.Add_Ruote_Coords( 52, new Point(320, 175)); 
    	this.Add_Ruote_Coords( 52, new Point(340, 222)); 
    	this.Add_Ruote_Coords( 52, new Point(361, 269)); 
    	this.Add_Specular_Route_Coords(52);

    	//54 - Paris - Frankfurt - Orange - 3 - 2
    	this.Add_Ruote_Coords( 54, new Point(486, 509)); 
    	this.Add_Ruote_Coords( 54, new Point(535, 486)); 
    	this.Add_Ruote_Coords( 54, new Point(580, 457)); 
    	this.Add_Specular_Route_Coords(54);

    	//56 - Munchen - Wien - Orange - 3 - 2
    	this.Add_Ruote_Coords( 56, new Point(721, 518)); 
    	this.Add_Ruote_Coords( 56, new Point(769, 543)); 
    	this.Add_Ruote_Coords( 56, new Point(820, 524)); 
    	this.Add_Specular_Route_Coords(56);

    	//58 - Zacrab - Budapest - Orange - 2 - 2
    	this.Add_Ruote_Coords( 58, new Point(872, 620)); 
    	this.Add_Ruote_Coords( 58, new Point(904, 576)); 
    	this.Add_Specular_Route_Coords(58);

    	//60 - Smyrna - Ancora - Orange - 3 - 2
    	this.Add_Ruote_Coords( 60, new Point(1205, 951)); 
    	this.Add_Ruote_Coords( 60, new Point(1260, 950)); 
    	this.Add_Ruote_Coords( 60, new Point(1314, 932)); 
    	this.Add_Specular_Route_Coords(60);

    	//62 - Smolensk - Moskva - Orange - 2 - 2
    	this.Add_Ruote_Coords( 62, new Point(1347, 303)); 
    	this.Add_Ruote_Coords( 62, new Point(1399, 287)); 
    	this.Add_Specular_Route_Coords(62);

    	//64 - Madrid - Pamplona - Black - 3 - 1
    	this.Add_Ruote_Coords( 64, new Point(198, 822)); 
    	this.Add_Ruote_Coords( 64, new Point(235, 778)); 
    	this.Add_Ruote_Coords( 64, new Point(280, 741)); 
    	this.Add_Specular_Route_Coords(64);

    	//66 - Brest - Paris - Black - 3 - 1
    	this.Add_Ruote_Coords( 66, new Point(260, 485)); 
    	this.Add_Ruote_Coords( 66, new Point(315, 490)); 
    	this.Add_Ruote_Coords( 66, new Point(370, 498)); 
    	this.Add_Specular_Route_Coords(66);

    	//68 - Edimburgh - London - Black - 4 - 1
    	this.Add_Ruote_Coords( 68, new Point(281, 135)); 
    	this.Add_Ruote_Coords( 68, new Point(301, 180)); 
    	this.Add_Ruote_Coords( 68, new Point(323, 229)); 
    	this.Add_Ruote_Coords( 68, new Point(342, 280)); 
    	this.Add_Specular_Route_Coords(68);

    	//70 - Bruxelles - Amsterdam - Black - 1 - 1
    	this.Add_Ruote_Coords( 70, new Point(494, 345)); 
    	this.Add_Specular_Route_Coords(70);

    	//72 - Frankfurt - Berlin - Black - 3 - 1
    	this.Add_Ruote_Coords( 72, new Point(641, 405)); 
    	this.Add_Ruote_Coords( 72, new Point(688, 382)); 
    	this.Add_Ruote_Coords( 72, new Point(736, 360)); 
    	this.Add_Specular_Route_Coords(72);

    	//74 - Venice - Rome - Black - 2 - 1
    	this.Add_Ruote_Coords( 74, new Point(723, 666)); 
    	this.Add_Ruote_Coords( 74, new Point(734, 718)); 
    	this.Add_Specular_Route_Coords(74);

    	//76 - Danzic - Rica - Black - 3 - 1
    	this.Add_Ruote_Coords( 76, new Point(941, 198)); 
    	this.Add_Ruote_Coords( 76, new Point(964, 150)); 
    	this.Add_Ruote_Coords( 76, new Point(1011, 116)); 
    	this.Add_Specular_Route_Coords(76);

    	//78 - Ancora - Erzurum - Black - 3 - 1
    	this.Add_Ruote_Coords( 78, new Point(1381, 925)); 
    	this.Add_Ruote_Coords( 78, new Point(1434, 935)); 
    	this.Add_Ruote_Coords( 78, new Point(1468, 909)); 
    	this.Add_Specular_Route_Coords(78);

    	//80 - Lisboa - Cadiz - Blue - 2 - 2
    	this.Add_Ruote_Coords( 80, new Point(86, 929)); 
    	this.Add_Ruote_Coords( 80, new Point(130, 964)); 
    	this.Add_Specular_Route_Coords(80);

    	//82 - Pamplona - Paris - Blue - 4 - 2
    	this.Add_Ruote_Coords( 82, new Point(333, 690)); 
    	this.Add_Ruote_Coords( 82, new Point(360, 640)); 
    	this.Add_Ruote_Coords( 82, new Point(379, 589)); 
    	this.Add_Ruote_Coords( 82, new Point(392, 535)); 
    	this.Add_Specular_Route_Coords(82);

    	//84 - Bruxelles - Frankfurt - Blue - 2 - 2
    	this.Add_Ruote_Coords( 84, new Point(512, 381)); 
    	this.Add_Ruote_Coords( 84, new Point(566, 395)); 
    	this.Add_Specular_Route_Coords(84);

    	//86 - Essen - Berlin - Blue - 2 - 2
    	this.Add_Ruote_Coords( 86, new Point(682, 320)); 
    	this.Add_Ruote_Coords( 86, new Point(736, 327)); 
    	this.Add_Specular_Route_Coords(86);

    	//88 - Munchen - Venice - Blue - 2 - 2
    	this.Add_Ruote_Coords( 88, new Point(698, 534)); 
    	this.Add_Ruote_Coords( 88, new Point(707, 586)); 
    	this.Add_Specular_Route_Coords(88);


    	//90 - Wien - Warszawa - Blue - 4 - 2
    	this.Add_Ruote_Coords( 90, new Point(884, 485)); 
    	this.Add_Ruote_Coords( 90, new Point(927, 454)); 
    	this.Add_Ruote_Coords( 90, new Point(963, 413)); 
    	this.Add_Ruote_Coords( 90, new Point(989, 368)); 
    	this.Add_Specular_Route_Coords(90);

    	//92 - Sofia - Costantinople - Blue - 3 - 2
    	this.Add_Ruote_Coords( 92, new Point(1092, 755)); 
    	this.Add_Ruote_Coords( 92, new Point(1140, 782)); 
    	this.Add_Ruote_Coords( 92, new Point(1189, 808)); 
    	this.Add_Specular_Route_Coords(92);

    	//94 - Wilno - Petrocrad - Blue - 4 - 2
    	this.Add_Ruote_Coords( 94, new Point(1179, 258)); 
    	this.Add_Ruote_Coords( 94, new Point(1209, 214)); 
    	this.Add_Ruote_Coords( 94, new Point(1238, 173)); 
    	this.Add_Ruote_Coords( 94, new Point(1268, 131)); 
    	this.Add_Specular_Route_Coords(94);

    	//96 - Lisboa - Madrid - Violet - 3 - 3
    	this.Add_Ruote_Coords( 96, new Point(69, 854)); 
    	this.Add_Ruote_Coords( 96, new Point(89, 815)); 
    	this.Add_Ruote_Coords( 96, new Point(146, 832)); 
    	this.Add_Specular_Route_Coords(96);

    	//98 - Pamplona - Brest - Violet - 4 - 3
    	this.Add_Ruote_Coords( 98, new Point(319, 649)); 
    	this.Add_Ruote_Coords( 98, new Point(318, 595)); 
    	this.Add_Ruote_Coords( 98, new Point(299, 545)); 
    	this.Add_Ruote_Coords( 98, new Point(255, 509)); 
    	this.Add_Specular_Route_Coords(98);

    	//100 - Dieppe - Paris - Violet - 1 - 3
    	this.Add_Ruote_Coords( 100, new Point(385, 466)); 
    	this.Add_Specular_Route_Coords(100);

    	//102 - Marseille - Zurich - Violet - 2 - 3
    	this.Add_Ruote_Coords( 102, new Point(575, 671)); 
    	this.Add_Ruote_Coords( 102, new Point(590, 620)); 
    	this.Add_Specular_Route_Coords(102);

    	//104 - Frankfurt - Munchen - Violet - 2 - 3
    	this.Add_Ruote_Coords( 104, new Point(621, 462)); 
    	this.Add_Ruote_Coords( 104, new Point(647, 492)); 
    	this.Add_Specular_Route_Coords(104);

    	//106 - Berlin - Warszawa - Violet - 4 - 3
    	this.Add_Ruote_Coords( 106, new Point(808, 328)); 
    	this.Add_Ruote_Coords( 106, new Point(859, 317)); 
    	this.Add_Ruote_Coords( 106, new Point(912, 313)); 
    	this.Add_Ruote_Coords( 106, new Point(964, 316)); 
    	this.Add_Specular_Route_Coords(106);

    	//108 - Budapest - Sarajevo - Violet - 3 - 3
    	this.Add_Ruote_Coords( 108, new Point(937, 583)); 
    	this.Add_Ruote_Coords( 108, new Point(945, 636)); 
    	this.Add_Ruote_Coords( 108, new Point(953, 689)); 
    	this.Add_Specular_Route_Coords(108);

    	//110 - Sofia - Athina - Violet - 3 - 3
    	this.Add_Ruote_Coords( 110, new Point(1034, 764)); 
    	this.Add_Ruote_Coords( 110, new Point(1012, 815)); 
    	this.Add_Ruote_Coords( 110, new Point(1026, 871)); 
    	this.Add_Specular_Route_Coords(110);

    	//112 - Madrid - Pamplona - White - 3 - 2
    	this.Add_Ruote_Coords( 112, new Point(212, 834)); 
    	this.Add_Ruote_Coords( 112, new Point(248, 790)); 
    	this.Add_Ruote_Coords( 112, new Point(293, 755)); 
    	this.Add_Specular_Route_Coords(112);

    	//114 - Marseille - Barcelona - Blue - 4 - 2
    	this.Add_Ruote_Coords( 114, new Point(513, 735));
    	this.Add_Ruote_Coords( 114, new Point(462, 759));
    	this.Add_Ruote_Coords( 114, new Point(415, 795));
    	this.Add_Ruote_Coords( 114, new Point(376, 835)); 
    	this.Add_Specular_Route_Coords(114);
    	

    	//116 - Dieppe - London - Blue - 2 - 2
    	this.Add_Ruote_Coords( 116, new Point(365, 400)); 
    	this.Add_Ruote_Coords( 116, new Point(371, 346)); 
    	this.Add_Specular_Route_Coords(116);

    	//118 - Pamplona - Barcelona - Green - 2 - 2
    	this.Add_Ruote_Coords( 118, new Point(335, 775)); 
    	this.Add_Ruote_Coords( 118, new Point(339, 829)); 
    	this.Add_Specular_Route_Coords(118);

    	//120 - Marseille - Rome - Green - 4 - 2
    	this.Add_Ruote_Coords( 120, new Point(593, 703)); 
    	this.Add_Ruote_Coords( 120, new Point(636, 669)); 
    	this.Add_Ruote_Coords( 120, new Point(673, 688)); 
    	this.Add_Ruote_Coords( 120, new Point(704, 729)); 
    	this.Add_Specular_Route_Coords(120);

    	//122 - Paris - Zurich - Red - 3 - 3
    	this.Add_Ruote_Coords( 122, new Point(465, 546)); 
    	this.Add_Ruote_Coords( 122, new Point(505, 580)); 
    	this.Add_Ruote_Coords( 122, new Point(557, 592)); 
    	this.Add_Specular_Route_Coords(122);

    	//124 - Marseille - Paris - Yellow - 4 - 1
    	this.Add_Ruote_Coords( 124, new Point(537, 684)); 
    	this.Add_Ruote_Coords( 124, new Point(493, 645)); 
    	this.Add_Ruote_Coords( 124, new Point(450, 609)); 
    	this.Add_Ruote_Coords( 124, new Point(432, 557)); 
    	this.Add_Specular_Route_Coords(124);

    	//126 - Dieppe - London - Yellow - 2 - 1
    	this.Add_Ruote_Coords( 126, new Point(343, 397)); 
    	this.Add_Ruote_Coords( 126, new Point(349, 347)); 
    	this.Add_Specular_Route_Coords(126);

    	//128 - London - Amsterdam - Violet - 2 - 3
    	this.Add_Ruote_Coords( 128, new Point(407, 306)); 
    	this.Add_Ruote_Coords( 128, new Point(462, 308)); 
    	this.Add_Specular_Route_Coords(128);

    	//130 - Rome - Palermo - Orange - 4 - 2
    	this.Add_Ruote_Coords( 130, new Point(761, 776)); 
    	this.Add_Ruote_Coords( 130, new Point(805, 813)); 
    	this.Add_Ruote_Coords( 130, new Point(822, 865)); 
    	this.Add_Ruote_Coords( 130, new Point(799, 922)); 
    	this.Add_Specular_Route_Coords(130);
    }
    
    public void Add_Specular_Route_Coords(Integer Ruote_Number)
    {
    	Integer Specular_Route = Map.Get_Specular_Route(Ruote_Number);
    	ArrayList<Point> Points_Ruote_Number = this.Ruotes_Coords.get(Ruote_Number);
    	ArrayList<Point> Points_Specular_Ruote_Number = new ArrayList<Point>();
    	for(Point point: Points_Ruote_Number)
    	{
    		Points_Specular_Ruote_Number.add(0, point);
    	}
    	this.Ruotes_Coords.put(Specular_Route, Points_Specular_Ruote_Number);
    }
    
    public void Add_Ruote_Coords(Integer Ruote_Number, Point Ruote_Point)
    {
    	ArrayList<Point> Points = Ruotes_Coords.get(Ruote_Number);
    	if(Points==null)
    	{
    		Points = new ArrayList<Point>();
    	}
    	if(!Points.contains(Ruote_Point))
    	{
    		Points.add(Ruote_Point);
    		Ruotes_Coords.put(Ruote_Number, Points);
    	}
    }
    
    public void Create_Stations_Coords()
    {
    	Stations_Coords.put(City.Lisboa, new Point(73, 894));
    	Stations_Coords.put(City.Cadiz, new Point(170, 972));
    	Stations_Coords.put(City.Madrid, new Point(176,859));
    	Stations_Coords.put(City.Barcelona, new Point(345, 870)); 
    	Stations_Coords.put(City.Pamplona, new Point(328, 731)); 
    	Stations_Coords.put(City.Paris, new Point(425, 503)); 
    	Stations_Coords.put(City.Brest, new Point(219, 482));
    	Stations_Coords.put(City.Dieppe, new Point(352, 438)); 
    	Stations_Coords.put(City.Edinburgh, new Point(273, 94));
    	Stations_Coords.put(City.London, new Point(369, 308)); 
    	Stations_Coords.put(City.Amsterdam, new Point(510, 311)); 
    	Stations_Coords.put(City.Bruxelles, new Point(479, 380)); 
    	Stations_Coords.put(City.Essen, new Point(629, 323)); 
    	Stations_Coords.put(City.Frankfurt, new Point(609, 425)); 
    	Stations_Coords.put(City.Zurich, new Point(595, 582)); 
    	Stations_Coords.put(City.Marseille, new Point(556, 719));
    	Stations_Coords.put(City.Kodenilavn, new Point(730, 174));
    	Stations_Coords.put(City.Berlin, new Point(773, 344)); 
    	Stations_Coords.put(City.Munchen, new Point(693, 488)); 
    	Stations_Coords.put(City.Venice, new Point(719, 627));
    	Stations_Coords.put(City.Rome, new Point(731, 760));
    	Stations_Coords.put(City.Palermo, new Point(788, 956)); 
    	Stations_Coords.put(City.Stockholm, new Point(877, 66));
    	Stations_Coords.put(City.Wien, new Point(857, 510)); 
    	Stations_Coords.put(City.Zacrab, new Point(841, 643));
    	Stations_Coords.put(City.Brindisi, new Point(857, 795));
    	Stations_Coords.put(City.Danzic, new Point(934, 234)); 
    	Stations_Coords.put(City.Budapest, new Point(927, 547));
    	Stations_Coords.put(City.Sarajevo, new Point(965, 727));
    	Stations_Coords.put(City.Rica, new Point(1047, 107));
    	Stations_Coords.put(City.Warszawa, new Point(1004, 331)); 
    	Stations_Coords.put(City.Sofia, new Point(1062, 739));
    	Stations_Coords.put(City.Athina, new Point(1039, 909));
    	Stations_Coords.put(City.Wilno, new Point(1163, 294)); 
    	Stations_Coords.put(City.Bucaresti, new Point(1147, 644));
    	Stations_Coords.put(City.Smyrna, new Point(1166, 945)); 
    	Stations_Coords.put(City.Kyiv, new Point(1223, 405));
    	Stations_Coords.put(City.Costantinople, new Point(1229, 827)); 
    	Stations_Coords.put(City.Petrocrad, new Point(1286, 97)); 
    	Stations_Coords.put(City.Smolensk, new Point(1309, 299));
    	Stations_Coords.put(City.Savastopol, new Point(1349, 663));
    	Stations_Coords.put(City.Ancora, new Point(1344, 907)); 
    	Stations_Coords.put(City.Moskva, new Point(1430, 263));
    	Stations_Coords.put(City.Kharkov, new Point(1420, 477)); 
    	Stations_Coords.put(City.Rostow, new Point(1482, 554)); 
    	Stations_Coords.put(City.Sochi, new Point(1480, 683)); 
    	Stations_Coords.put(City.Erzurum, new Point(1462, 870)); 
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (Map_Image != null) {
            g.drawImage(Map_Image, 0, 0, this);
        }
        if (Car_Image != null && Car_Position != null) {
            g.drawImage(Car_Image, Car_Position.x, Car_Position.y, this);
        }
    }
}