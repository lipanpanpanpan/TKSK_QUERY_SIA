package element.query_processing;

public class CandidateDoc {
	public int id;
	public double textScore;
	public int vertexID;
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getTextScore() {
		return textScore;
	}

	public void setTextScore(int textScore) {
		this.textScore = textScore;
	}

	public CandidateDoc(int id,double score,int vertexID) {
		// TODO Auto-generated constructor stub
		this.id=id;
		this.vertexID=vertexID;
		this.textScore=score;
	}

	
	public void addScore(double score)
	{
		this.textScore+=score;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
