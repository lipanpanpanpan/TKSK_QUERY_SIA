package element;

import java.io.Serializable;

public class ItemDocVertexDistance implements Comparable<ItemDocVertexDistance>,Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 217289746705609833L;
	public int docID;
	public float distanceSoc;
	public int vertexID;

	public ItemDocVertexDistance(int docID,float score,int vertexID) {
		// TODO Auto-generated constructor stub
		this.docID=docID;
		this.distanceSoc=score;
		this.vertexID=vertexID;
	}
	public ItemDocVertexDistance()
	{
		this.docID=-1;
		this.distanceSoc=0;
		this.vertexID=-1;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	@Override
	public int compareTo(ItemDocVertexDistance o) {
		// TODO Auto-generated method stub
		if(this.distanceSoc>o.distanceSoc)
		{
			return 1;
			
		}else if(this.distanceSoc==o.distanceSoc)
		{
			return 0;
		}
		else
		{
			return -1;
		}
		
	}

}
