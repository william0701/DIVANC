package com.xidian.Club;

import java.util.ArrayList;
import java.util.Comparator;
/*
 * Function: Implement the compare of edges
 */

public class NodeCompareByIntersection implements Comparator<Node> 
{

	ArrayList<Node> standard;
	public NodeCompareByIntersection(ArrayList<Node> standard)
	{
		this.standard=standard;
	}
	public int compare(Node n1, Node n2) 
	{
		int num1=Toolbox.Intersection(standard, n1.neighbors).size();
		int num2=Toolbox.Intersection(standard, n2.neighbors).size();
		if(num1>num2)
			return 1;
		else if(num1<num2)
			return -1;
		else return 0;
	}
	
}