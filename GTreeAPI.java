
public  class GTreeAPI {
	
	 public native int getDistanceVertexToVertex(int queryNode,int targetNode);
	 public native VertexDist[] getAllCanditateDist(int queryNode,int[] targetCandNode);
	 public native VertexDist[] getAllCanditateDistWithExtraTimeReturn(int queryNode,int[] targetCandNode);
//	 public native  VertexDist[] getGtreeDistsAtnStepsOfkSteps(int queryNode,int n,int k);
	 public native VertexDist[] getTopkGtreeNodes(int queryNode,int k);
	 
	 
	 public native String getGtreeNodes(int queryNode,int catchTimes);
	 
	 public native VertexDist[] getNearestNumsVertex(int queryNode,int vertexNums);
	 
	 public native void InitGtree();
	 public native int InitGtreeTimes();
	 public native Vertex[] getVertexByNodeK(int queryNode,int catchTimes);
	 
	 
	
	 
	 
	 static {
	        System.loadLibrary("GTree");
	    }
	
	 public static void main(String[] args) {
		 long start=System.currentTimeMillis();
		GTreeAPI test=new GTreeAPI();
		test.InitGtree();
//		
//		
//		VertexDist[] tt=test.getNearestNumsVertex(21678 , 100);
//		System.out.println(tt.length);
//		for(int rre=0;rre<tt.length;rre++)
//		{
//			System.out.println("rre:\t"+rre+"\tVertexDist===\t"+tt[rre].vertexID+"\t"+tt[rre].distance);
//		}
//////		test.InitGtree();
//		double initTime=0;
//		long totalTime = 0; 
//		int initCount=3;
//		for(int i=0;i<initCount;i++)
//		{
//			totalTime+=test.InitGtreeTimes();
//		}
//		initTime=totalTime/initCount;
//		
//		
//		
//		int[] ss=new int[initCount];
//		for(int ssi=0;ssi<ss.length;ssi++)
//		{
//			ss[ssi]=12+5+ssi;
//		}
//		long ttStart=System.currentTimeMillis();
//		VertexDist[] tt=test.getAllCanditateDistWithExtraTimeReturn(12, ss);
//		long ttend=System.currentTimeMillis();
//		long extratime=0;
//		VertexDist extime=tt[0];
//		if(extime.vertexID==12)
//		{
//			extratime=extime.distance;
//			tt[0].distance=0;
//		}
////		System.out.println(tt.length);
//		for(int rre=0;rre<tt.length;rre++)
//		{
//			System.out.println("VertexDist==="+tt[rre].vertexID+"\t"+tt[rre].distance);
//		}
//		
		
//		String nodeString=test.getGtreeNodes(15, 0);
//		System.out.println(nodeString);
//		long end=System.currentTimeMillis();
//		Vertex[] diskList = test.getVertexByNodeK(555,2);
//        for (int i = 0; i < diskList.length; i++) {
//            System.out.println("nodeIDGtree:" + diskList[i].nodeIDGtree);
//            System.out.println("vertexID:" + diskList[i].vertexID);
//            System.out.println("dist:" + diskList[i].dist);
//        }
		
		

		
	 }
}
