import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Vector;

import search.ScorerGCA;
import conf.Configure;
import element.ItemDocVertexDistance;
import element.ItemTextual;
import element.query_processing.CandidateDoc;


public class CAGTreeTOPK {
	private int kwdNum;
	private double upperBound;
	
	private int[] tIndex;
	private double[] maxTextScore;
	
	private float[] minTextScore;
	
	private Vector<Vector<ItemTextual>> textLists;
	private int topK;
	private int ItemAccessed=0;
	private int itorTimes=100;
	
	private Vector<Integer> candidatesVertex;
	private HashMap<Integer, CandidateDoc> docCandidates;
	private HashMap<Integer, Integer> FinalDocCandidates;
	private HashMap<Integer, Integer> vertexDistanceMap;
	private PriorityQueue<ItemDocVertexDistance> results;
	
	private GTreeAPI gtreeApi;
	
	private int queryVertex;
	
	private long extraTime=0;
	
	
	
	
	public CAGTreeTOPK(Vector<Vector<ItemTextual>> textLists2, int k, int queryVertexID) {
		// TODO Auto-generated constructor stub
		this.textLists=textLists2;
		this.kwdNum=textLists2.size();
		this.topK=k;
		this.maxTextScore=new double[kwdNum];
		this.tIndex = new int[kwdNum];
		this.minTextScore=new float[kwdNum];
		this.docCandidates=new HashMap<Integer, CandidateDoc>();
		this.candidatesVertex=new Vector<Integer>();
		this.queryVertex=queryVertexID;
		this.vertexDistanceMap=new HashMap<Integer, Integer>();
		this.FinalDocCandidates=new HashMap<Integer, Integer>();
		this.gtreeApi=new GTreeAPI();
		this.results=new PriorityQueue<ItemDocVertexDistance>();
//		for(int i=0;i<this.topK;i++)
//		{
//			results.add(new ItemDocVertexDistance());
//		}
		
	}
	public long run() {
		// TODO Auto-generated method stub
		long ss=System.currentTimeMillis();
		exploreTextList();
		long ee=System.currentTimeMillis();
//		System.out.println("TTT:"+(ee-ss));
		if(candidatesVertex.size()<1)
		{
			return 0;
		}
		//TODO  测试
		if(true)
		{
			return 0;
		}
		
		//get  candidates vertex
		int[] candidatesVertexSet=new int[candidatesVertex.size()];
		
		for(int i=0;i<candidatesVertex.size();i++)
		{
			candidatesVertexSet[i]=candidatesVertex.get(i);
		}
//		System.out.println(candidatesVertex.size());
		
		long sdsd=System.currentTimeMillis();
		VertexDist[] tt=gtreeApi.getAllCanditateDistWithExtraTimeReturn(this.queryVertex, candidatesVertexSet);
		long eded=System.currentTimeMillis();
		System.out.println("GGGG:"+(eded-sdsd));
		
		if(tt.length>2)
		{
			this.extraTime=tt[0].distance;//get the extraTime
			System.out.println("Extra Time:"+extraTime);
			System.out.println("DDDSSS"+(eded-sdsd-extraTime));
		}
		//取到TopK最近的vertex
		for(int i=1;i<tt.length;i++)
		{
			vertexDistanceMap.put(tt[i].vertexID, tt[i].distance);
		}
		for(Integer docID:FinalDocCandidates.keySet())
		{

			int dist=vertexDistanceMap.get(FinalDocCandidates.get(docID));
			//get score
			float score=ScorerGCA.getGtreeSpatialSocre(dist);
			//updateTopK?
			if(results.size()<topK||score>results.peek().distanceSoc)
			{
				ItemDocVertexDistance cand=new ItemDocVertexDistance(docID,score,FinalDocCandidates.get(docID));
				updateTopK(cand);
			}
		}
		
		return extraTime;
		
		
		
	}
	
	public void printTopKResult()
	{
		while(results.size()>0)
		{
			ItemDocVertexDistance cand=results.poll();
			System.out.println("DocID：\t"+cand.docID+"\tDistanceScore:\t"+cand.distanceSoc);
		}
	}
	
	
	public  void updateTopK(ItemDocVertexDistance cand)
	{
		if(results.contains(cand))
		{
			results.remove(cand);
			results.add(cand);
		}else
		{
			if(results.size()>=this.topK)
			{
				results.poll();
			}
			results.add(cand);
		}
	}
	
	
	//find those who text score>shrehod
	public void exploreTextList()
	{
		//getmaxIndex
		int maxItrTimes=0;
		for(int i=0;i<kwdNum;i++)
		{
			maxItrTimes=Math.max(maxItrTimes, textLists.get(i).size());
			tIndex[i]=0;
			maxTextScore[i]=textLists.get(i).get(0).textRelevance;
		}

		updateUpperBound();
		
		
		int indexP=0;
		while(maxItrTimes-indexP>0)
		{
			if(upperBound<Configure.QUERY_SHREHOD)
			{
				break;
			}
			for(int i=0;i<kwdNum;i++)
			{
				
				//防止越狱
				if(indexP>=textLists.get(i).size())
				{
					
				}else
				{
				
				
					//还有数据
//					System.out.println(i+" i  "+" indexp   "+indexP);
					ItemTextual doc = textLists.get(i).get(indexP);//get doc
					
					CandidateDoc cand=docCandidates.get(doc.docID);
					if(cand==null)
					{
						docCandidates.put(doc.docID,new CandidateDoc(doc.docID, doc.textRelevance,doc.vertexID));
						
					}else
					{
//						System.out.println("sss"+docCandidates.get(doc.docID).getTextScore());
						docCandidates.get(doc.docID).addScore(doc.textRelevance);
//						System.out.println("ddd"+docCandidates.get(doc.docID).getTextScore());
//						return;
					}
					
					
					maxTextScore[i]=doc.textRelevance;
				
				}
				
			}
			updateUpperBound();
			indexP++;
		}
		
		System.out.println("DOC  ACC "+docCandidates.size());
		this.ItemAccessed=docCandidates.size();
		//get final node and doc
		
		for(int dID:docCandidates.keySet())
		{
			if(docCandidates.get(dID).getTextScore()>Configure.QUERY_SHREHOD)
			{
				FinalDocCandidates.put(dID, docCandidates.get(dID).vertexID);

				if(!candidatesVertex.contains(docCandidates.get(dID).vertexID))
				{
					candidatesVertex.add(docCandidates.get(dID).vertexID);
				}

			}
		}
//		System.out.println("A:"+FinalDocCandidates.values());
//		System.out.println("B:"+candidatesVertex.toString());
		docCandidates.clear();
		
	}
	
	public void updateUpperBound()
	{
		upperBound=0;
		for(int i=0;i<kwdNum;i++)
		{
			upperBound+=maxTextScore[i];
		}
	}
	public void init()
	{
		for(int i=0;i<kwdNum;i++)
		{
			
		}
	}
	
	
	public void printGnodeInv()
	{
		
	}
	
	public void printVnodeInv()
	{
		
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	

	public long getAccessedItem() {
		// TODO Auto-generated method stub
		return ItemAccessed;
	}

}
