package com.xidian.Club;

import java.util.ArrayList;
import java.util.Collections;
import java.io.*;
/*
 * Function: Modeling the Process of EN2H algorithm
 */
public class TwoClubDistribution extends AbstractCommon
{
	/*
	 * Function: Variable Definition
	 * outputpath: the path of output file
	 * result: the result of network division
	 * originalnetwork: the original network
	 */
	protected String outputpath;
	public ArrayList<Network> subnetworkset;
	protected Network originalnetwork;
	
	/*
	 * Function: Constructor
	 * Input: filepath: the path of network
	 *        outputpath: the path of network
	 *        algoname: the name of algorithm
	 * Output:None
	 */
	public TwoClubDistribution(String filepath,String outputpath,String algoname)
	{
		super(algoname+"_"+new File(filepath).getName().split("\\.")[0],".txt",outputpath);
		this.outputpath=outputpath;
		originalnetwork=new Network(filepath);
		path=outputpath;
	}
	
	/*
	 * Function: perform the EN2H algorithm
	 * Input:None
	 * Output:None
	 */
	public void run()
	{
		subnetworkset=new ArrayList<Network>();
		ArrayList<Network> candidatenetwork=new ArrayList<Network>();
		candidatenetwork.add(originalnetwork);
		System.out.print("Deleting edges\n");
		
		int line=0;
		long begin;
		long end;
		while(candidatenetwork.size()!=0)
		{
			begin=System.currentTimeMillis();
			Network curnetwork=candidatenetwork.get(0);
			candidatenetwork.remove(0);
			if(curnetwork.MaxD())
			{
				if(curnetwork.nodelist.size()>1)
					subnetworkset.add(curnetwork);
				continue;
			}
			curnetwork.NetworkCluster();
			ArrayList<Network> sub=curnetwork.CollectComponent();
			if(sub.size()==1)
				sub.get(0).flag=false;
			candidatenetwork.addAll(sub);
			Collections.sort(candidatenetwork,new NetworkComparor());
			end=System.currentTimeMillis();
			System.out.println(line+++" "+(end-begin)/1000+"s");
//			System.gc();
		}
		System.out.println("\nDeleting edges finished");
		String content="";
		for(int i=0;i<subnetworkset.size();++i)
		{
			Network curnet=subnetworkset.get(i);
			for(int j=0;j<curnet.nodelist.size();++j)
				content+=curnet.nodelist.get(j).name+"\t";
			content+="\n";
		}
		Output("Community",content,false);
	}
	/*
	 * Function: print the result of network devision on the console
	 * Input:None
	 * Output:None
	 */
	public void Show()
	{
		for(int i=0;i<subnetworkset.size();++i)
			subnetworkset.get(i).Display();
	}
}
