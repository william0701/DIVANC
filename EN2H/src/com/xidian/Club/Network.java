package com.xidian.Club;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;


/*
 * Function: Modeling a network
 */
public class Network
{
	/*
	 * Function: Variable Definition
	 * nodelist: Node set
	 * edgelist: Edge set
	 * name2index: Mapping from the name of node to the position of node set
	 * edgematrix: The adjacency matrix with edge as the elements
	 */
	public static Network orig;
	public ArrayList<Node> nodelist;
	public ArrayList<Edge> edgelist;
	public Hashtable<String,Integer> name2index;
	public Edge[][] edgematrix;
	public boolean flag=true;
	
	/*
	 * Function:Constructor
	 * Input: The network file path
	 * Output: None
	 */
	public Network(String filepath)
	{
		nodelist=new ArrayList<Node>();
		edgelist=new ArrayList<Edge>();
		name2index=new Hashtable<String,Integer>();
		readnetworkfile(filepath);
		orig=this.Clone();
	}
	
	/*
	 * Function:Constructor, used to create connected component
	 * Input: The beginning node: begin
	 * 		  The edge matrix: edges
	 * 		  The mapping relation: mapping
	 * Output: None
	 */
	public Network(Node begin,Edge[][] edges,Hashtable<String,Integer> mapping)
	{
		nodelist=new ArrayList<Node>();
		name2index=new Hashtable<String,Integer>();
		nodelist.add(begin);
		name2index.put(begin.name,nodelist.size()-1);
		for(int i=0;i<nodelist.size();++i)
		{
			Node curnode=nodelist.get(i);
			for(int j=0;j<curnode.degree;++j)
			{
				if(!curnode.neighbors.get(j).IsContained(nodelist))
				{
					nodelist.add(curnode.neighbors.get(j));
					name2index.put(curnode.neighbors.get(j).name, nodelist.size()-1);
				}
			}
		}
		InitialEdge(edges,mapping);
		Collections.sort(edgelist, new EdgeComparor());
	}
	
	/*
	 * Function: Constructor
	 * Input£º nodes: the set of node
	 * Output: None
	 */
	public Network(ArrayList<Node> nodes)
	{
		Network orig=Network.orig.Clone();
		nodelist=new ArrayList<Node>();
		for(Node n:nodes)
			nodelist.add(orig.nodelist.get(orig.name2index.get(n.name)));
		for(Node n:nodelist)
		{
			n.neighbors=Toolbox.Intersection(n.neighbors, nodelist);
			n.degree=n.neighbors.size();
		}
		name2index=new Hashtable<String,Integer>();
		for(int i=0;i<nodelist.size();++i)
			name2index.put(nodelist.get(i).name, i);
		edgelist=new ArrayList<Edge>();
		edgematrix=new Edge[nodelist.size()][nodelist.size()];
		for(Node n:nodelist)
		{
			int index1=name2index.get(n.name);
			for(Node nei:n.neighbors)
			{
				int index2=name2index.get(nei.name);
				if(index1<index2)
				{
					edgematrix[index1][index2]=new Edge(n,nei);
					edgematrix[index2][index1]=edgematrix[index1][index2];
					edgelist.add(edgematrix[index1][index2]);
				}
			}
		}
	}
	/*
	 * Function:Constructor
	 * Input: None
	 * Output : None
	 */
	public Network()
	{
		
	}
	/*
	 * Function: Reading network file
	 * Input: The network file path: filepath
	 * Output: None 
	 */
	private void readnetworkfile(String filepath)
	{
		try
		{
			File file=new File(filepath);
			FileReader in=new FileReader(file);
			BufferedReader br=new BufferedReader(in);
			System.out.println("Starting loading network!");
			Node n1,n2;
			String content=br.readLine();
			while(content!=null)
			{
				String name[]=content.split("\t");
				if(name2index.get(name[0])==null)
				{
					n1=new Node(name[0]);
					
					name2index.put(name[0], nodelist.size());
					nodelist.add(n1);
				}
				else 
				{
					n1=nodelist.get(name2index.get(name[0]));
				}
				if(name2index.get(name[1])==null)
				{
					n2=new Node(name[1]);				
					name2index.put(name[1],nodelist.size());
					nodelist.add(n2);
				}
				else
				{
					n2=nodelist.get(name2index.get(name[1]));
				}
				n1.AddNeighbor(n2);
				n2.AddNeighbor(n1);
				
				Edge edge=new Edge(n1,n2);
				edgelist.add(edge);
				content=br.readLine();
			}
			br.close();
			System.out.println("Network loading finished!");
			System.out.println("Starting Initializing Network!");
			for(int i=0;i<edgelist.size();++i)
			{
				edgelist.get(i).CalValue();
			}
			InitialEdgeMatrix();
			System.out.println("Network initializing finished!");
		}
		catch(Exception e)
		{
			System.out.println("File not found!");
		}
	}
	
	/*
	 * Function: Initial the edge matrix in constructor
	 * Input: None
	 * Output: None
	 */
	private void InitialEdgeMatrix()
	{
		edgematrix=new Edge[nodelist.size()][nodelist.size()];
		for(int i=0;i<edgelist.size();++i)
		{
			Edge curedge=edgelist.get(i);
			int index1=name2index.get(curedge.n1.name);
			int index2=name2index.get(curedge.n2.name);
			edgematrix[index1][index2]=curedge;
			edgematrix[index2][index1]=curedge;
		}
	}
	/*
	 * Function: Initial all the variable members associated with edges
	 * Input: The adjacency matrix with edge as the elements: edges
	 *        The mapping relation: mapping
	 * Output: None
	 */
	private void InitialEdge(Edge[][] edges,Hashtable<String,Integer> mapping)
	{
		edgematrix=new Edge[nodelist.size()][nodelist.size()];
		edgelist=new ArrayList<Edge>();
		for(int i=0;i<nodelist.size();++i)
		{
			Node curnode=nodelist.get(i);
			for(int j=0;j<curnode.neighbors.size();++j)
			{
				Node neighbor=curnode.neighbors.get(j);
				int index1=mapping.get(curnode.name);
				int index2=mapping.get(neighbor.name);
				if(index1>=index2)
					edgelist.add(edges[index1][index2]);
			}
		}
		InitialEdgeMatrix();
	}

	/*
	 * Function: Finding the index of the edge with the largest centrailty in current network
	 * Input: None
	 * Output: If¡¡the index is not 0, return the index of the edge in the edge set; otherwise return -1
	 */
	public int MaxValue()
	{
		double max=0;
		int index=-1;
		for(int i=0;i<edgelist.size();++i)
		{
			if(max<edgelist.get(i).niche)
			{
				max=edgelist.get(i).niche;
				index=i;
			}
		}
		return index;
	}
	
	/*
	 * Function: Produce the connected components in current network
	 * Input: None
	 * Output: None
	 */
	public ArrayList<Network> CollectComponent()
	{
		ArrayList<Network> networkset=new ArrayList<Network>();
		boolean isused[]=new boolean[nodelist.size()];
		
		for(int i=0;i<nodelist.size();++i)
		{
			if(!isused[i])
			{
				Network tmp=new Network(nodelist.get(i),edgematrix,name2index);
				for(int j=0;j<tmp.nodelist.size();++j)
				{
					isused[name2index.get(tmp.nodelist.get(j).name)]=true;
				}
				networkset.add(tmp);
			}
		}
		return networkset;
	}

	/*
	 * Function: Judge the diameter of current network is no more than 2
	 * Input: None
	 * Output: If the diameter of current network is no more than 2, return true; otherwise return false
	 */
	public boolean MaxD()
	{
		if(nodelist.size()==1)
			return true;
		if(flag)
		{
			int adj[][]=new int[nodelist.size()][nodelist.size()];
			int oneorder[][]=new int[nodelist.size()][nodelist.size()];
			@SuppressWarnings("unchecked")
			ArrayList<Integer> indexset[]=new ArrayList[nodelist.size()];
			for(int i=0;i<nodelist.size();++i)
				indexset[i]=new ArrayList<Integer>();
			for(int i=0;i<nodelist.size();++i)
			{
				Node n1=nodelist.get(i);
				for(int j=i;j<nodelist.size();++j)
				{
					Node n2=nodelist.get(j);
					int index1=orig.name2index.get(n1.name);
					int index2=orig.name2index.get(n2.name);
					if(orig.edgematrix[index1][index2]!=null)
					{
						adj[i][j]=1;
						adj[j][i]=1;
						oneorder[i][j]=1;
						oneorder[j][i]=1;
						indexset[i].add(j);
						indexset[j].add(i);
					}
					else
					{
						adj[i][j]=0;
						adj[j][i]=0;
						oneorder[i][j]=0;
						oneorder[j][i]=0;
					}
				}
			}
		
		
		for(int i=0;i<nodelist.size();++i)
		{
			for(int j=0;j<nodelist.size();++j)
			{
				if(oneorder[i][j]==0)
				{
					for(int k=0;k<indexset[i].size();++k)
					{
						if(adj[indexset[i].get(k)][j]==1)
						{
							oneorder[i][j]=1;
							break;
						}
					}
				}
			}
		}
		
		
		for(int i=0;i<nodelist.size();++i)
			for(int j=0;j<nodelist.size();++j)
				if(oneorder[i][j]==0)
					return false;
		return true;
		}
		return false;
	}
	/*
	 * Function: Delete the edge with the largest niche centrality until all the niche centralities of the edges are 0
	 * Input: None
	 * Output: None
	 */
	public void NetworkCluster()
	{
		int edgeindex;
		edgeindex=MaxValue();
		if(edgeindex!=-1)
		{
			ArrayList<Node> changednode=AddRelatedNode(edgelist.get(edgeindex));
			ClearEdge(edgeindex);
			CalValue(changednode);
		}
	}
	/*
	 * Function: Clear the edge with the index given in the edge set 
	 * Input: The edge index: edgeindex
	 * Output: None
	 */
	private void ClearEdge(int edgeindex)
	{
		Edge curedge=edgelist.get(edgeindex);
		int index1=name2index.get(curedge.n1.name);
		int index2=name2index.get(curedge.n2.name);
		edgematrix[index1][index2]=null;
		edgematrix[index2][index1]=null;
		curedge.niche=-1;
		curedge.n1.DeleteNeighbor(curedge.n2);
		curedge.n2.DeleteNeighbor(curedge.n1);
	}
	/*
	 * Function: Compute all nodes that are effected by the edge deleted.
	 * Input: The deleted edge: maxedge
	 * Output: A node set containing all affected nodes 
	 */
	private ArrayList<Node> AddRelatedNode(Edge maxedge)
	{
		ArrayList<Node> relatednodeset=new ArrayList<Node>();
		relatednodeset.addAll(maxedge.n1.neighbors);
		for(int i=0;i<maxedge.n2.neighbors.size();++i)
		{
			if(!maxedge.n2.neighbors.get(i).IsContained(relatednodeset))
				relatednodeset.add(maxedge.n2.neighbors.get(i));
		}
		int size=relatednodeset.size();
		for(int i=0;i<size;++i)
		{
			Node curnode=relatednodeset.get(i);
			for(int j=0;j<curnode.neighbors.size();++j)
				if(!curnode.neighbors.get(j).IsContained(relatednodeset))
					relatednodeset.add(curnode.neighbors.get(j));
		}
		return relatednodeset;
	}
	/*
	 * Function: Recompute the niche centrality of a set of node
	 * Input: The node set: nodes
	 * Output: None
	 */
	private void CalValue(ArrayList<Node> nodes)
	{
		for(int i=0;i<nodes.size();++i)
		{
			int index1=name2index.get(nodes.get(i).name);
			for(int j=0;j<nodes.get(i).neighbors.size();++j)
			{
				int index2=name2index.get(nodes.get(i).neighbors.get(j).name);
				edgematrix[index1][index2].CalValue();
			}
		}
	}

	/*
	 * Function: Display the nodes' names in this network
	 * Input: None
	 * Output: None
	 */
	public void Display()
	{
		for(int i=0;i<nodelist.size();++i)
			System.out.print(nodelist.get(i).name+"\t");
		System.out.println();
	}
	
	/*
	 * Function: Copy current network
	 * Input: None
	 * Output: The network cloned
	 */
	public Network Clone()
	{
		Network copy=new Network();
		copy.nodelist=Node.CloneArray(nodelist, name2index);
		copy.name2index=new Hashtable<String,Integer>();
		for(int i=0;i<nodelist.size();++i)
			copy.name2index.put(copy.nodelist.get(i).name, i);
		copy.edgelist=Edge.Copy(edgelist, copy.nodelist,copy.name2index);
		copy.edgematrix=new Edge[copy.nodelist.size()][copy.nodelist.size()];
		for(int i=0;i<copy.edgelist.size();++i)
		{
			Edge curedge=copy.edgelist.get(i);
			int index1=copy.name2index.get(curedge.n1.name);
			int index2=copy.name2index.get(curedge.n2.name);
			copy.edgematrix[index1][index2]=curedge;
			copy.edgematrix[index2][index1]=curedge;
		}
		return copy;
	}
}
