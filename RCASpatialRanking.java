


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Vector;

import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;

import conf.Configure;

import search.Scorer;
import test.IOCounter;
import test.InputFileIO;
import element.ItemSpatialCurve;
import element.ItemSpatialCurveForCombined;
import element.ItemSpatialDoc;
import element.ItemTextual;
import element.SpatialDoc;
import element.spatial.Grid;
import element.spatial.Point;

/*
 * RCA Algorithm.
 */

public class RCASpatialRanking {
	Grid grid;												// To get the z-order
	final String DBName;
	DB db;
	
//	HTreeMap<String, Vector<ItemSpatialDoc>> docMap ;
	HTreeMap<String, Vector<ItemTextual>> textualMap;
//	HTreeMap<String, Vector<ItemSpatialCurve>> spatialMap;
	
//	HashMap<String, Vector<ItemSpatialDoc>> docLists;
	HashMap<String, Vector<ItemTextual>> textualLists;
//	HashMap<String, Vector<ItemSpatialCurve>> spatialLists;
	
	GTreeAPI gtreeAPI;
	
	long queryTime = 0;
	long indexLoadTime = 0;
	long itemAccessed = 0;
	long totalItem = 0;
	
	public RCASpatialRanking(String dbName)
	{
		DBName = dbName;
		grid = new Grid(0f, 10000f, 0f, 10000f, (short)2000);
		
		loadDB();
//		docMap = db.getHashMap("docMap");
		textualMap = db.getHashMap("textualMap");
//		spatialMap = db.getHashMap("spatialMap");
		gtreeAPI=new GTreeAPI();
//		docLists = new HashMap<String, Vector<ItemSpatialDoc>>();
		textualLists = new HashMap<String, Vector<ItemTextual>>();
//		spatialLists = new HashMap<String, Vector<ItemSpatialCurve>>();
	}
	
	
	
	public void query(String queryFile, int K) throws IOException
	{
		int queryNum = 0;
		
//		FileWriter fwRCA=null;
//		int keyWordNum=0;
		int termCount=0;
		
		try{
			InputFileIO fin = new InputFileIO(queryFile);	
			SpatialDoc doc = null;
		
			while( (doc=fin.nextSpatialDocument()) != null){
				
//					System.out.println(queryNum+"\t"+doc);
					long start = System.currentTimeMillis();
					int extraTime=query(doc.getLoc(), new Vector<String>(doc.getTerms().keySet()), K,doc.vertexID);
					long end = System.currentTimeMillis();
					queryTime += end - start-extraTime;
					termCount=doc.getTerms().size();
//					System.out.println(termCount+"TermCount");
					queryNum++;
		}

			
			
			
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		totalItem *= 2;
		System.out.println(itemAccessed);
		System.out.println(totalItem);
		//DXTimer.print(queryNum);
		//System.err.println(1.0*DXTimer.accessTime/queryNum);
		System.err.println((1.0*queryTime/queryNum) + "\t" + (1.0*indexLoadTime/queryNum) + "\t" + (1.0*itemAccessed)/totalItem+"\t"+(IOCounter.io*1.0)/queryNum) ;
		
//		FileWriter fw=new FileWriter(Configure.DATA_ROOT_FOLDER_PATH+Configure.DATA_QUERY_RESULT_FOLDER_PATH+Configure.DATA_QUERY_RESULT_DATA_RCA_FOLDER_PATH+Configure.LAB_TYPE+termCount+"_"+Configure.A_OF＿SPATIAL+"_"+Configure.TOP_K+"_"+Configure.DATA_NUM_OF_INDEX+"_20"+".txt");
		
		
		Double avgQueryTime=1.0*queryTime/queryNum;
		Double avgIndexLoaded=1.0*indexLoadTime/queryNum;
		Double avgItemAccessed=(1.0*itemAccessed)/totalItem;
		Double avgIO=(IOCounter.io*1.0)/queryNum;
		
//		fw.write("avgQueryTime\t"+avgQueryTime+"\r\n");
//		fw.write("avgIndexLoaded\t"+avgIndexLoaded+"\r\n");
//		fw.write("avgItemAccessed\t"+avgItemAccessed+"\r\n");
//		fw.write("avgIO"+avgIO+"\r\n");
//		fw.close();

	}
	
	
	/**
	 * ��ѯĳ����¼
	 * @param qLoc
	 * @param queryKwds
	 * @param K
	 * @throws IOException 
	 */
	@SuppressWarnings("unchecked")
	private int query(Point qLoc, Vector<String> queryKwds, int K,int queryVertexID) throws IOException {
		// Retrieve the bucket inveted lists
		long start = System.currentTimeMillis();
		int kwdNum = queryKwds.size();
//		Vector<Vector<ItemSpatialDoc>> docLists = new Vector<Vector<ItemSpatialDoc>>();
		Vector<Vector<ItemTextual>> textLists = new Vector<Vector<ItemTextual>>();
//		Vector<Vector<ItemSpatialCurve>> spatialLists = new Vector<Vector<ItemSpatialCurve>>();
		//Vertex distance From QueryLocation
		
		
		
		long loadStart=System.currentTimeMillis();
		//���ÿ��Num������ݼ���
		for(int i=0;i < kwdNum;i++){
			Vector<ItemTextual> list = textualMap.get(queryKwds.get(i));
			//ŵList���سɹ����ʾ�����������Ӧ��text��spatiallist
			if(list != null){
//				docLists.add(list);
//				textLists.add(textualMap.get(queryKwds.get(i)));
//				spatialLists.add(spatialMap.get(queryKwds.get(i)));
//				syso
				totalItem += textLists.lastElement().size();
			}else
			{
				System.out.println("no"+queryKwds.get(i));
			}
		}
		long loadEnd=System.currentTimeMillis();
		System.err.println("load Time:"+(loadEnd-loadStart));

//		// start CA algorithm
		RCASpatialTopK alg = new RCASpatialTopK(textLists, grid, qLoc, K);
		alg.run();
//		alg.printResult();
//		//System.out.println("item accessed "+ alg.getAccessedItem());
		itemAccessed += alg.getAccessedItem();
		int extraTime=0;
		return extraTime;
	}
	
	
	public void loadDB()
	{
		File dbFile = new File(DBName);
		db = DBMaker.newFileDB(dbFile)
				.transactionDisable()
				//.cacheDisable()
                .closeOnJvmShutdown()
                .make();
		
	}
	

	public static void main(String[] args) throws IOException
	{
//		if(args.length != 4){
//			System.err.println("please specify dbName, query_file, K and alpha");
//			System.exit(-1);
//		}
		
		
		String dbName =Configure.INDEX_PATH;
		String queryFile = Configure.QUERY_FILE_PATH;

		int K =Configure.TopK;
//		Scorer.alpha = (float) Configure.A_OF＿SPATIAL;

		
		RCASpatialRanking TATest = new RCASpatialRanking(dbName);
		TATest.query(queryFile, K);
	}
}
