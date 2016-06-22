package search;

import conf.Configure;

public class ScorerGCA {

	public ScorerGCA() {
		// TODO Auto-generated constructor stub
	}
	public static float getGtreeSpatialSocre(int distance)
	{
		
		
		if(distance > Configure.MAX_DISTANCE){
			return 0;
		}
		if(distance<0)
		{
			return 0;
		}
		return 1 - (float)(distance*1.0f/Configure.MAX_DISTANCE);
		
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
