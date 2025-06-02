package Cara_Simulation;
import java.util.ArrayList;

/**
 * It represents the color of a route. This assigns many information to the route, in according to the
 * number of step of the route
 */
public class Color_Route {
	
    public Color The_Color;
    public Speed The_Speed;
    public ArrayList<Locomotive> Locomotives = new ArrayList<Locomotive>();
    public Panorama The_Panorama;
    

    public Color_Route(Color Colore, Panorama Panorama, Speed Velocita)
    {
        this.The_Color = Colore;
        this.The_Speed = Velocita;      
        this.The_Panorama = Panorama;
    }
}