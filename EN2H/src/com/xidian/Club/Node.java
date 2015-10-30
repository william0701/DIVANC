package com.xidian.Club;

import java.util.ArrayList;
import java.util.Hashtable;

/*
 * Function: Modeling the node in network
 */
public class Node 
{
	/*
	 * Function: Variable Definition
	 * id: the static no. of Node
	 * curid: the no. of the current node
	 * name: the name of node
	 * neighors: the first order neighbors
	 * degree:  the degree of the node
	 */
	protected static int id=0;
	protected int curid;
	protected String name;  
	protected ArrayList<Node> neighbors;  
	protected int degree; 
	
	/*
	 * Function: Constructor
	 * Input: name: the name of the node
	 * Output: None
	 */
	protected Node(String name)
	{
		curid=id++;
		this.name=name;
		neighbors=new ArrayList<Node>();
		degree=neighbors.size();
	}
	/*
	 * Function: Constructor
	 * Input: None
	 * Output: None
	 */
	private Node()
	{
		
	}
	/*
	 * Function: Add the neighbor the node
	 * Input: x: the neighbor added to the node
	 * Output: None
	 */
	protected void AddNeighbor(Node x)
	{
		neighbors.add(x);
		degree=neighbors.size();
	}
	
	/*
	 * Function: Delete the neighbor of the node
	 * Input: x: the node deleted
	 * Output: None
	 */
	protected void DeleteNeighbor(Node x)
	{
		for(int i=0;i<neighbors.size();++i)
		{
			if(neighbors.get(i)==x)
			{
				neighbors.remove(i);
				degree=neighbors.size();
				break;
			}
		}
	}

	/*
	 * Function: Display the infomation of the node
	 * Input: None
	 * Output: None
	 */
	protected void Display()
	{
		System.out.println(name+"("+curid+")");
	}
	
	/*
	 * Function: Judge if the node is contained in a set
	 * Input: x: the set of node
	 * Output: if the node is contained, return true;
	 *         otherwise£¬ return false
	 */
	protected boolean IsContained(ArrayList<Node> x)
	{
		for(int i=0;i<x.size();++i)
//			if(this==x.get(i))
//				return true;
			if(this.name.equals(x.get(i).name))
				return true;
		return false;
	}
	
	/*
	 * Function: Judge if two node are the neighbors of each other
	 * Input: x: one node
	 *        y: the other node
	 * Input: If they are neighbors of each other, return true;
	 *        otherwise return false;
	 */
	protected static boolean IsNeighbor(Node x,Node y)
	{
		for(int i=0;i<x.neighbors.size();++i)
		{
			if(x.neighbors.get(i)==y)
				return true;
		}
		return false;
	}
	
	/*
	 * Function: Copy current node
	 * Input: None
	 * Output: The copy of current node£¬without the neighbors and degree
	 */
	protected Node Copy()
	{
		Node copy=new Node();
		copy.curid=curid;
		copy.name=name;
		copy.neighbors=new ArrayList<Node>();
		return copy;
	}
	
	/*
	 * Function: Copy the node set
	 * Input: nodelist: the node set
	 *        name2index: the mapping between name and index
	 * Output: The copy of the nodelist
	 */
	static public ArrayList<Node> CloneArray(ArrayList<Node> nodelist,Hashtable<String,Integer> name2index)
	{
		ArrayList<Node> copy=new ArrayList<Node>();
		for(int i=0;i<nodelist.size();++i)
			copy.add(nodelist.get(i).Copy());
		for(int i=0;i<nodelist.size();++i)
		{
			for(int j=0;j<nodelist.get(i).degree;++j)
			{
				Node neighbor=nodelist.get(i).neighbors.get(j);
				int index=name2index.get(neighbor.name);
				copy.get(i).AddNeighbor(copy.get(index));
			}
			copy.get(i).degree=copy.get(i).neighbors.size();
		}
		return copy;
	}
}
