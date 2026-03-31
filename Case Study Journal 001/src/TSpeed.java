public enum TSpeed 
{
	Small,
	Medium,
	High;
	
	/**
	 * It returns the integer value associated to the speed enumeration
	 * @param speed
	 * @return
	 */
	public int Get_Value(TSpeed speed) { 
		int result = 0;
		switch (speed) {
		case Small:
			result = 1;
				break;
		case Medium:
			result = 2;
				break;
		case High:
			result = 3;
				break;
		}
		return result;
	}

}
