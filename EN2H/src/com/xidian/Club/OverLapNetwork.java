package com.xidian.Club;

import java.util.ArrayList;
import java.util.Collections;
/*
 * Function: Modeling the process of 
 */
public class OverLapNetwork extends AbstractCommon
{
	/*
	 * Function: Variable Defination
	 * networkset: the network set containing the network being extended
	 * corenodes: the corenodes of a network
	 * resultnodes: the set of nodes after overlaping of a network
	 * subover: the set of resultnodes
	 */
	ArrayList<Network> networkset;
	ArrayList<Node> corenodes;
	ArrayList<Node> resultnodes;
	public ArrayList<Network> subover;
	/*
	 * Function: Constructor
	 * Input: name: the name of the network
	 *        set: the set of network
	 *        outputpath: the file path of output file
	 *        algoname: the name of algorithm
	 * Output: None
	 */
	public OverLapNetwork(String name,ArrayList<Network> set,String outputpath,String algoname)
	{
		super(algoname+"_"+name,".txt",outputpath);
		networkset=set;
		corenodes=new ArrayList<Node>();
		resultnodes=new ArrayList<Node>();
		subover=new ArrayList<Network>();
	}
	
	/*
	 * Function: Perform the algorithm of overlapping
	 * Input: None
	 * Output: None
	 */
	public void run()
	{
		String content="";
		for(Network net:networkset)
		{
			corenodes=new ArrayList<Node>();
			resultnodes=new ArrayList<Node>();
			Network orig=Network.orig;
			ArrayList<Node> nodes=net.nodelist;
			for(Node n:nodes)
			{
				int index=orig.name2index.get(n.name);
				corenodes.add(orig.nodelist.get(index));
			}
			ArrayList<Node> candidate=new ArrayList<Node>();
			//Get the candidate nodes
			for(Node n:corenodes)
				candidate=Toolbox.Union(candidate, n.neighbors);
			//Delete the nodes less connecting with the corenodes
			for(int i=0;i<candidate.size();++i)
			{
				if(Toolbox.Intersection(candidate.get(i).neighbors, corenodes).size()<=corenodes.size()*0.5)
				{
					candidate.remove(i);
					i--;
				}			
			}
			Collections.sort(candidate, new NodeCompareByIntersection(corenodes));
			for(int i=0;i<corenodes.size();++i)
			{
				candidate=Toolbox.KickoutElement(candidate, corenodes.get(i));
			}
     		//Add the candidate node that can form 2-CLUB into the corenodes
			
			for(int i=0;i<candidate.size();++i)
			{
				ArrayList<Node> reachable=new ArrayList<Node>();
				Node curnode=candidate.get(i);
				reachable.addAll(Toolbox.Intersection(curnode.neighbors, corenodes));
				int size=reachable.size();
				for(int j=0;j<size;++j)
				{
					reachable=Toolbox.Union(reachable, Toolbox.Intersection(reachable.get(j).neighbors, corenodes));
				}
				if(reachable.size()==corenodes.size())
				{
					corenodes.add(curnode);
				}
			}
			//获得所有节点
//			resultnodes.addAll(corenodes);
//			resultnodes=Toolbox.Union(resultnodes, candidate);	
			for(Node n:corenodes)
				content+=n.name+"\t";
			content+="\n";
			subover.add(new Network(corenodes));
		}
		
		Output("overlap",content,false);
	}
}
