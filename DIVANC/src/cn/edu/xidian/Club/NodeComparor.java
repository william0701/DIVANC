package cn.edu.xidian.Club;

import java.util.Comparator;
/*
 * Function: Implement the compare of Node
 */
public class NodeComparor implements Comparator<Node> 
{
	public int compare(Node n1,Node n2)
	{
		int nn1=Integer.valueOf(n1.name);
		int nn2=Integer.valueOf(n2.name);
		if(nn1>nn2)
			return 1;
		else if(nn1<nn2)
			return -1;
		else
			return 0;
	}
}
