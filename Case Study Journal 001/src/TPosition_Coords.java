public class TPosition_Coords
{
	//If agent is in a station => station = a station, Route = -1, step = 0
		//If agent is in a station => station = a station, Route => 0 , step => 0
		private Integer City;
		private Integer Route;
		private Integer Step;
		
		public TPosition_Coords( Integer city, Integer route, Integer step)
		{
			this.City = city;
			this.Route = route;
			this.Step = step;
		}

		public Integer Get_Route() {
			return Route;
		}

		public void Set_Route(Integer route) {
			Route = route;
		}

		public void Set_Step(Integer step) {
			Step = step;
		}
		
		public Integer Get_Step()
		{
			return this.Step;
		}
		
		public void Set_City(Integer station) {
			this.City = station;
		}
		
		public Integer Get_City()
		{
			return this.City;
		}

}
