package cn.edu.xidian.Club;

import java.util.ArrayList;
import java.util.Hashtable;

/*
 * Function: Modeling a edge in a network
 */
class Edge
{
	/*
	 * Function:Variable Definition
	 * n1:a node in an edge
	 * n2:the other node in an edge
	 * niche:the niche centrality
	 * p4centrality:the P4 centrality
	 * ecc:the edge clustering coefficient
	 */
	protected Node n1;
	protected Node n2;
	protected double niche=0;
	protected double p4centrality=0;
	protected double ecc=0;
	
	/*
	 * Function:Constructor
	 * Input:Two nodes of this edge
	 * Output:none
	 */
	protected Edge(Node n1,Node n2)
	{
		this.n1=n1;
		this.n2=n2;
	}
	/*
	 * Function:Constructor
	 * Input:none
	 * Output:none
	 */
	@SuppressWarnings("unused") Edge()
	{
		
	}
	/*
	 * Function:Compute the P4 centrality of this edge
	 * Input:none
	 * Output:none
	 */
	protected void CalP4Value()
	{
		int left[]=LeftP4();
		int right[]=RightP4();
		int middle[]=MiddleP4();
		p4centrality=left[1]+right[1]+middle[1];
	}
	
	/*
	 * Function:Compute the ecc of this edge
	 * Input:none
	 * Output:none
	 */
	protected double SharedNode()
	{
		return Toolbox.Intersection(n1.neighbors, n2.neighbors).size();
	}
	protected void CalECC()
	{
		double tringlenum=SharedNode();
		ecc= (tringlenum+1)/((n1.degree>n2.degree?n2.degree:n1.degree)-1);
	}
	
	/*
	 * Function:Compute the niche centrality of this edge
	 * Input:none
	 * Output:none
	 */
	protected void CalValue()
	{
		CalP4Value();
		CalECC();
		ecc=1/ecc;
		niche=p4centrality+ecc;
	}
	
	/*
	 * Function:Compute the P4 number of this edge as the left edge
	 * Input:none
	 * Output:return the P4 number of this edge as the left edge
	 */
	private int[] LeftP4()
	{
		Path path=new Path(n1,n2,2);
		int pnum=0;
		int num=0;
		for(int i=0;i<n2.degree;++i)
		{
			Node neighbor=n2.neighbors.get(i);
			if(neighbor!=n1)
			{
				pnum+=neighbor.degree-1;
				num+=neighbor.degree-1;
				path.setN3(neighbor);
				for(int j=0;j<neighbor.degree;++j)
				{
					Node nownode=neighbor.neighbors.get(j);
					if(nownode!=n2)
					{
						path.setN4(nownode);
						if(!path.IsP4())
							num--;
					}
				}
			}
		}
		int[] result={pnum,num};
		return result;
	}
	
	/*
	 * Function:Compute the P4 number of this edge as the right edge
	 * Input:none
	 * Output:return the P4 number of this edge as the right edge
	 */
	private int[] RightP4()
	{
		Path path=new Path(n1,n2,3);
		int pnum=0;
		int num=0;
		for(int i=0;i<n1.degree;++i)
		{
			Node neighbor=n1.neighbors.get(i);
			if(neighbor!=n2)
			{
				pnum+=neighbor.degree-1;
				num+=neighbor.degree-1;
				path.setN2(neighbor);
				for(int j=0;j<neighbor.degree;++j)
				{
					Node nownode=neighbor.neighbors.get(j);
					if(nownode!=n1)
					{
						path.setN1(nownode);
						if(!path.IsP4())
							num--;
					}
				}
			}
		}
		int result[]={pnum,num};
		return result;
	}
	
	/*
	 * Function:Compute the P4 number of this edge as the middle edge
	 * Input:none
	 * Output:return the P4 number of this edge as the middle edge
	 */
	private int[] MiddleP4()
	{
		Path path=new Path(n1,n2,1);
		int num=(n1.degree-1)*(n2.degree-1);
		int pnum=num;
		for(int i=0;i<n1.degree;++i)
		{
			for(int j=0;j<n2.degree;++j)
			{
				if(n1.neighbors.get(i)!=n2&&n2.neighbors.get(j)!=n1)
				{
					path.setN1(n1.neighbors.get(i));
					path.setN4(n2.neighbors.get(j));
					if(!path.IsP4())
						num--;
				}
			}
		}
		int result[]={pnum,num};
		return result;
	}
	
	/*
	 * Function:Overriding the equals() function
	 * Input:the other edge x
	 * Output:if the nodes if x have the same names corresponding to the node in this edge, return true; otherwise, return false
	 */
	public boolean equals(Edge x)
	{
		if((x.n1.name.equals(n1.name)&&x.n2.name.equals(n2.name))||x.n1.name.equals(n2.name)&&x.n2.name.equals(n1.name))
			return true;
		return false;
	}
	
	/*
	 * Function:Create an edge array according to the given node set;
	 * Input:The node set
	 * Output:the edge array
	 */
	static protected ArrayList<Edge> Copy(ArrayList<Node> nodelist)
	{
		ArrayList<Edge> edgelist=new ArrayList<Edge>();
		for(int i=0;i<nodelist.size();++i)
		{
			for(int j=0;j<nodelist.get(i).degree;++j)
			{
				Edge copyedge=new Edge(nodelist.get(i),nodelist.get(i).neighbors.get(j));
				copyedge.CalValue();
				edgelist.add(copyedge);
			}
		}
		return edgelist;
	}
	
	/*
	 * Function: Create an edge array according to the edge array given
	 * Input: Edge array: edgelist,
	 *        Node set: nodelist£¬
	 *        Mapping relation: name2index
	 * Output:the edge array
	 */
	static protected ArrayList<Edge> Copy(ArrayList<Edge> edgelist,ArrayList<Node> nodelist,Hashtable<String,Integer> name2index)
	{
		ArrayList<Edge> copy=new ArrayList<Edge>();
		for(int i=0;i<edgelist.size();++i)
		{
			Edge curedge=edgelist.get(i);
			Edge copyedge=new Edge(nodelist.get(name2index.get(curedge.n1.name)),nodelist.get(name2index.get(curedge.n2.name)));
			copyedge.ecc=curedge.ecc;
			copyedge.niche=curedge.niche;
			copyedge.p4centrality=curedge.p4centrality;
			copy.add(copyedge);
		}
		return copy;
	}
}
