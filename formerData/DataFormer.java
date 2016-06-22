package formerData;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;

public class DataFormer {

	public DataFormer() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		String inputFile="E:/WorkSpace_Data_LAB/LAB_DATA/WAIM2015/POI/London/london_300K.txt";
		BufferedReader reader=new BufferedReader(new FileReader(inputFile));
		HashMap<Integer, HashMap<Integer, String>> docList=new HashMap<Integer, HashMap<Integer,String>>();
		String line;
		while((line=reader.readLine())!=null)
		{
			String datas[]=line.split(" ");
			
			if(datas.length>3)
			{
				int kwdNum=(datas.length-3)/2;
				if(!docList.containsKey(kwdNum))
				{
					docList.put(kwdNum, new HashMap<Integer, String>());
				}
				int id=docList.get(kwdNum).size()+1;
				
				docList.get(kwdNum).put(id, line);
			}
		}
		
		for(int i=1;i<6;i++)
		{
			String outFile="E:/WorkSpace_Data_LAB/LAB_DATA/WAIM2015/Query/300K/NEW_DataSize_300K_kwdNum_"+i+".txt";
			
			BufferedWriter wr=new BufferedWriter(new FileWriter(outFile));
			
			for(int j=1;j<=docList.get(i).size();j++)
			{

				if(docList.get(i).containsKey(j))
				{
					if(j==docList.get(i).size())
					{
						wr.write(docList.get(i).get(j));
					}else
					{
						wr.write(docList.get(i).get(j)+"\r\n");
					}
				}
			}
			wr.close();
			
			
		}
		
		
		

	}

}
