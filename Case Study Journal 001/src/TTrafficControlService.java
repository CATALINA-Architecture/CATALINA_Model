public class TTrafficControlService 
{
	private Autonomous_Vehicle_Demo Demo;
	private TCommon_Functions Common_Functions;
	
	public TTrafficControlService(Autonomous_Vehicle_Demo demo)
	{
		this.Demo = demo;
		this.Common_Functions = demo.Common_Functions;
	}

}
