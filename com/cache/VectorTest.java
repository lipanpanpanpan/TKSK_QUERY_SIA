package com.cache;

import java.util.Collection;
import java.util.Collections;
import java.util.Vector;

import element.ItemSpatialCurve;
import element.spatial.Point;

public class VectorTest {

	/**
	 * @date 2014-7-31
	 * @Editor BYRD
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Vector<ItemSpatialCurve> mylist=new Vector<ItemSpatialCurve>();
		ItemSpatialCurve it1=new ItemSpatialCurve(1, 12, new Point(0, 0), 0);
		ItemSpatialCurve it2=new ItemSpatialCurve(2, 12, new Point(0, 0), 0);
		ItemSpatialCurve it3=new ItemSpatialCurve(3, 12, new Point(0, 0), 0);
		ItemSpatialCurve it4=new ItemSpatialCurve(4, 12, new Point(0, 0), 0);
		ItemSpatialCurve it5=new ItemSpatialCurve(5, 12, new Point(0, 0), 0);
		ItemSpatialCurve it6=new ItemSpatialCurve(6, 12, new Point(0, 0), 0);
		mylist.add(it5);
		mylist.add(it6);
		mylist.add(it1);
		mylist.add(it4);
		mylist.add(it2);
		mylist.add(it3);
		
		System.out.println("未锟斤拷锟斤拷前");
		for(int i=0;i<mylist.size();i++)
		{
			System.out.println(mylist.get(i).docID);
		}
		Collections.sort(mylist);
//		System.out.println("锟斤拷锟斤拷锟�);
		for(int i=0;i<mylist.size();i++)
		{
			System.out.println(mylist.get(i).docID);
		}
		System.out.println("删锟斤拷docId为5锟斤拷");
		for(int i=0;i<mylist.size();)
		{
			if(mylist.get(i).docID==5)
			{
				mylist.removeElementAt(i);
			}
			else
			{
				i++;
			}
			System.out.println("锟斤拷前Mylist锟斤拷size锟斤拷"+mylist.size());
		}
//		System.out.println("删锟斤拷锟�);
		for(int i=0;i<mylist.size();i++)
		{
			System.out.println(mylist.get(i).docID);
		}
	}

}
