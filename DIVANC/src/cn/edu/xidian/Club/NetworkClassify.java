package cn.edu.xidian.Club;

import java.util.ArrayList;
import java.util.Hashtable;
import java.io.*;
/*
 * Function: Modeling the process of network classification
 */
public class NetworkClassify extends AbstractCommon
{
	/*
	 * Function: Variable Definition
	 * networkset: the set of network that are going to be classified
	 * coterie1: the set of network whose type is coterie1
	 * coterie2: the set of network whose type is coterie2
	 * coterie3: the set of network whose type is coterie3
	 * social1: the set of network whose type is social circle1
	 * social2: the set of network whose type is social circle2
	 * halmet: the set of network whose type is halmet
	 */
	protected ArrayList<Network> networkset;
	protected ArrayList<Network> coterie1;
	protected ArrayList<Network> coterie2;
	protected ArrayList<Network> coterie3;
	protected ArrayList<Network> coterie4;
	protected ArrayList<Network> social1;
	protected ArrayList<Network> social2;
	protected ArrayList<Network> halmet;
	
	/*
	 * Function: Constructor
	 * Input: name: the name of the network
	 * 		  set: the set of network to be classified
	 * 		  outputpath: the file path of output file
	 *        algoname: the name of the algorithm
	 * Output: None
	 */
	public NetworkClassify(String name,ArrayList<Network> set,String outputpath,String algoname)
	{
		super(algoname+"_"+name,".txt",outputpath);
		networkset=set;
		coterie1=new ArrayList<Network>();
		coterie2=new ArrayList<Network>();
		coterie3=new ArrayList<Network>();
		coterie4=new ArrayList<Network>();
		social1=new ArrayList<Network>();
		social2=new ArrayList<Network>();
		halmet=new ArrayList<Network>();
		
		
	}
	
	/*
	 * Function: Override Constructor
	 * Input: filepath: the file path of network
	 * 		  outputpath: the file path of output file
	 *        algoname: the name of the algorithm
	 * Output: None
	 */
	public NetworkClassify(String filepath,String outputpath,String algoname)
	{
		super(algoname+"_"+new File(filepath).getName().split("\\.")[0],".txt",outputpath);
		Network network=new Network(filepath);
		coterie1=new ArrayList<Network>();
		coterie2=new ArrayList<Network>();
		coterie3=new ArrayList<Network>();
		coterie4=new ArrayList<Network>();
		social1=new ArrayList<Network>();
		social2=new ArrayList<Network>();
		halmet=new ArrayList<Network>();
		networkset=new ArrayList<Network>();
		networkset.add(network);
	}
	
	/*
	 * Function: Run the process of network classification
	 * Input: None
	 * Output: None
	 */
	public void run()
	{
		for(int k=0;k<networkset.size();++k)
		{
			Network net=networkset.get(k);
//			System.gc();
			Network network=new Network();
			Network orig=Network.orig;
			ArrayList<Node> nodelist=new ArrayList<Node>();
			//Copy the nodelist in net without neighbors
			for(Node n:net.nodelist)
				nodelist.add(n.Copy());
			//New the mapping in current nodelist
			Hashtable<String,Integer> name2index=new Hashtable<String,Integer>();
			for(int i=0;i<nodelist.size();++i)
				name2index.put(nodelist.get(i).name, i);
			//Add neighbors to current nodelist
			for(int i=0;i<net.nodelist.size();++i)
			{
				Node curnode=orig.nodelist.get(orig.name2index.get(net.nodelist.get(i).name));
				for(int j=0;j<curnode.neighbors.size();++j)
				{
					if(curnode.neighbors.get(j).IsContained(net.nodelist))
					{
						nodelist.get(i).AddNeighbor(nodelist.get(name2index.get(curnode.neighbors.get(j).name)));
					}
				}
			}
			Edge[][] edgematrix=new Edge[nodelist.size()][nodelist.size()];
			for(int i=0;i<nodelist.size();++i)
			{
				for(int j=0;j<nodelist.size();++j)
				{
					if(nodelist.get(j).IsContained(nodelist.get(i).neighbors))
					{
						edgematrix[i][j]=new Edge(nodelist.get(i),nodelist.get(j));
						edgematrix[j][i]=edgematrix[i][j];
					}
				}
			}
			network.nodelist=nodelist;
			network.edgematrix=edgematrix;
			network.name2index=name2index;
			
			int type=IsCoterie(network);
			if(type==-1)
				type=IsSocial(network);
			switch(type){
			case 1:coterie1.add(network);break;
			case 2:coterie2.add(network);break;
			case 3:coterie3.add(network);break;
			case 4:coterie4.add(network);break;
			case 5:social1.add(network);break;
			case 6:social2.add(network);break;
			default:halmet.add(network);break;
			}
		}
		String content="";
		content+="Coterie1:\n";
		for(Network network:coterie1)
		{
			for(int i=0;i<network.nodelist.size();++i)
				content+=network.nodelist.get(i).name+"\t";
			content+="\n";
		}
		
		content+="Coterie2:\n";
		for(Network network:coterie2)
		{
			for(int i=0;i<network.nodelist.size();++i)
				content+=network.nodelist.get(i).name+"\t";
			content+="\n";
		}
		
		content+="Coterie3:\n";
		for(Network network:coterie3)
		{
			for(int i=0;i<network.nodelist.size();++i)
				content+=network.nodelist.get(i).name+"\t";
			content+="\n";
		}
		
		content+="Coterie4:\n";
		for(Network network:coterie4)
		{
			for(int i=0;i<network.nodelist.size();++i)
				content+=network.nodelist.get(i).name+"\t";
			content+="\n";
		}
		
		content+="Social Circle1:\n";
		for(Network network:social1)
		{
			for(int i=0;i<network.nodelist.size();++i)
				content+=network.nodelist.get(i).name+"\t";
			content+="\n";
		}
		
		content+="Social Circle2:\n";
		for(Network network:social2)
		{
			for(int i=0;i<network.nodelist.size();++i)
				content+=network.nodelist.get(i).name+"\t";
			content+="\n";
		}
		
		content+="Halmet:\n";
		for(Network network:halmet)
		{
			for(int i=0;i<network.nodelist.size();++i)
				content+=network.nodelist.get(i).name+"\t";
			content+="\n";
		}
		Output("Classify",content,false);
	}
	
	/*
	 * Function: Judge if the network belongs to Coterie
	 * Input: network: the network to be classified
	 * Output: If the network belongs to Coterie, then return the subtype number of Coterie, 1~3
	 *         Otherwise return -1
	 */
	private int IsCoterie(Network network)
	{
		int type;
		int nodenum=network.nodelist.size();
		for(int i=0;i<nodenum;++i)
		{
			if(network.nodelist.get(i).degree==nodenum-1)
			{
				if(nodenum==2)
				{
					type=3;
					return type;
				}
				else 
				{
					type=CoterieType(network,i);
					return type;
				}
			}
		}
		type=-1;
		return type;
	}
	
	/*
	 * Function: Judege the subtype of Coterie
	 * Input: network:the network to be classified
	 * 		  index: the index of the center node in the nodelist
	 * Output: The subtype nember of Coterie, 1~3
	 */
	private int CoterieType(Network network,int index)
	{
		int sum=0;
		for(int i=0;i<network.nodelist.size();++i)
			sum+=network.nodelist.get(i).neighbors.size();
		if(network.nodelist.size()-sum/2==1)
			return 1;
		Node corenode=network.nodelist.get(index);
		for(int i=0;i<network.nodelist.size();++i)
		{
			network.nodelist.get(i).neighbors=Toolbox.KickoutElement(network.nodelist.get(i).neighbors,corenode);
			network.nodelist.get(i).degree=network.nodelist.get(i).neighbors.size();
		}
//		network.nodelist=SetOperation.KickoutElement(network.nodelist, corenode);
		network.nodelist.get(index).neighbors=new ArrayList<Node>();
		network.nodelist.get(index).degree=0;
		ArrayList<Network> sub=network.CollectComponent();
		if(sub.size()==2)
			return 4;
		else return 2;
	}
	
	/*
	 * Function: Judge if the network belongs to Social Circle¿‡
	 * Input: network: the network to be classified
	 * Output: If the network belongs to social circle, return the subtype nmber of social circle, 4~5
	 *         Otherwise, return 6
	 */
	private int IsSocial(Network network)
	{
		int type;
		int nodenum=network.nodelist.size();
		int[][] antimatrix=new int[nodenum][nodenum];
		for(int i=0;i<nodenum;++i)
		{
			for(int j=0;j<nodenum;++j)
			{
				if(i==j)
					continue;
				if(network.edgematrix[i][j]==null)
					antimatrix[i][j]=1;
			}
		}
		
		int[][] antitwoorder=Toolbox.AppMatMul(antimatrix, antimatrix);
		antitwoorder=Toolbox.AppMatAdd(antitwoorder, antimatrix);
		for(int i=0;i<nodenum;++i)
		{
			for(int j=i;j<nodenum;++j)
			{
				if(antitwoorder[i][j]==0)
				{
					type=Socialtype(network);
					return type;
				}
			}
		}
		type=7;
		return type;
	}
	
	/*
	 * Function:Judege the subtype of Social Circle
	 * Input: network:the network to be classified
	 * Output: The subtype nember of Social Network, 1~3
	 */
	private int Socialtype(Network network)
	{
		int nodenum=network.nodelist.size();
		int adj[][]=new int[nodenum][nodenum];
		for(int i=0;i<nodenum;++i)
			for(int j=i;j<nodenum;++j)
				if(network.edgematrix[i][j]!=null)
				{
					adj[i][j]=1;
					adj[j][i]=1;
				}
		int threeorder[][]=Toolbox.AppMatMul(Toolbox.AppMatMul(adj, adj),adj);
		for(int i=0;i<nodenum;++i)
			if(threeorder[i][i]==1)
				return 5;
		return 6;
	}
}
