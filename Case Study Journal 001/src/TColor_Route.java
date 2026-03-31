import java.util.ArrayList;

/**
 * It represents the color of a route. This assigns many information to the route, in according to the
 * number of step of the route
 */
public class TColor_Route 
{
	
    public TColor Color;
    public TSpeed Speed;
    public ArrayList<TMotor> Motors = new ArrayList<TMotor>();
    public TPanorama Panorama;
    

    public TColor_Route(TColor color, TPanorama panorama, TSpeed speed)
    {
        this.Color = color;
        this.Speed = speed;      
        this.Panorama = panorama;
    }

}
