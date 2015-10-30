package com.xidian.Club;

import java.util.Comparator;
/*
 * Function: Implement the compare of Network
 */
public class NetworkComparor implements Comparator<Network> 
{
	public int compare(Network n1,Network n2)
	{
		Node node1=n1.nodelist.get(0);
		Node node2=n2.nodelist.get(0);
		return new NodeComparor().compare(node1, node2);
	}
}
