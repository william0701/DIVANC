package cn.edu.xidian.Club;

import java.util.Comparator;
/*
 * Function: Implement the compare of edges
 */

public class EdgeComparor implements Comparator<Edge> 
{

	public int compare(Edge e1, Edge e2) 
	{
		int en1[]={Integer.valueOf(e1.n1.name),Integer.valueOf(e1.n2.name)};
		int en2[]={Integer.valueOf(e2.n1.name),Integer.valueOf(e2.n2.name)};
		if(en1[0]>en2[0])
			return 1;
		else if(en1[0]<en2[0])
			return -1;
		else
		{
			if(en1[1]>en2[1])
				return 1;
			else if(en1[1]<en2[1])
				return -1;
			else return 0;
		}
	}
	
}
