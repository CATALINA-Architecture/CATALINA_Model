package Catalina_Simulation;
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
    

    public Color_Route(Color color, Panorama panorama, Speed speed)
    {
        this.The_Color = color;
        this.The_Speed = speed;      
        this.The_Panorama = panorama;
    }
}