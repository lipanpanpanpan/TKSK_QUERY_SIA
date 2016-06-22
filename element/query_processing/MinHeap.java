package element.query_processing;



public class MinHeap
{
	// �ѵĴ洢�ṹ - ����
	private HeapItem[] data;
	//private Candidate tmp;
	
	// ��һ�����鴫�빹�췽������ת����һ��С����
	public MinHeap(HeapItem[] data)
	{
		this.data = data;
		//tmp = new Candidate(-1);
		buildHeap();
	}
	
	// ������ת������С��
	private void buildHeap()
	{
		// ��ȫ������ֻ�������±�С�ڻ���� (data.length) / 2 - 1 ��Ԫ���к��ӽ�㣬������Щ��㡣
		// *���������ͼ�У�������10��Ԫ�أ� (data.length) / 2 - 1��ֵΪ4��a[4]�к��ӽ�㣬��a[5]û��*
        for (int i = (data.length) / 2 - 1; i >= 0; i--) 
        {
        	// ���к��ӽ���Ԫ��heapify
            heapify(i);
        }
    }
	
	private void heapify(int i)
	{
		// ��ȡ���ҽ��������±�
        int l = left(i);  
        int r = right(i);
        
        // ����һ����ʱ��������ʾ ����㡢���㡢�ҽ������С��ֵ�Ľ����±�
        int smallest = i;
        
        // �������㣬�������ֵС�ڸ�����ֵ
        if (l < data.length && data[l].getScore() < data[i].getScore())  
        	smallest = l;  
        
        // �����ҽ�㣬���ҽ���ֵС�����ϱȽϵĽ�Сֵ
        if (r < data.length && data[r].getScore() < data[smallest].getScore())  
        	smallest = r;  
        
        // ���ҽ���ֵ�����ڸ��ڵ㣬ֱ��return�������κβ���
        if (i == smallest)  
            return;  
        
        // �������ڵ�����ҽ������С���Ǹ�ֵ���Ѹ��ڵ��ֵ�滻��ȥ
        swap(i, smallest);
        
        // �����滻�����������ᱻӰ�죬����Ҫ����Ӱ��������ٽ���heapify
        heapify(smallest);
    }
	
	// ��ȡ�ҽ��������±�
	private int right(int i)
	{  
        return (i + 1) << 1;  
    }   

	// ��ȡ����������±�
    private int left(int i) 
    {  
        return ((i + 1) << 1) - 1;  
    }
    
    // ����Ԫ��λ��
    private void swap(int i, int j) 
    {  
    	HeapItem tmp = data[i];
    	data[i] = data[j];
    	data[j] = tmp;
    }
    
    // ��ȡ���е���С��Ԫ�أ���Ԫ��
    public HeapItem getRoot()
    {
    	return data[0];
    }

    // �滻��Ԫ�أ�������heapify
	public void setRoot(HeapItem root)
	{
		data[0] = root;
		heapify(0);
	}
	
	public boolean valid()
	{
		return valid(0);
		
	}
	
	
	
	public boolean valid(int i)
	{
		int flag = 1;
		int l = left(i);  
		int r = right(i);
	        
	        
		if(l < data.length){
			if(data[l].getScore() < data[i].getScore()){
				flag = 0;
			}else{
				if(!valid(l)){
					flag = 0;
				}
			}
        }
		
		if(r < data.length){
			if(data[r].getScore() < data[i].getScore()){
				flag = 0;
			}else{
				if(!valid(r)){
					flag = 0;
				}
			}
        }
		
		return flag == 1;
	}
	
}
