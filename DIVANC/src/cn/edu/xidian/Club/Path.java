package cn.edu.xidian.Club;

/*
 * Function: Modeling the path among 4 connected nodes
 */
class Path
{
	/*
	 * Fucntion: Variable Definition
	 * n1,n2,n3,n4: the 4 nodes in order from left to right
	 * position: the position of the edge known
	 */
	protected Node n1;
	protected Node n2;
	protected Node n3;
	protected Node n4;
	protected int position;
	
	/*
	 * Function: Constructor
	 * Input: the two nodes known and the position
	 * OutputNone
	 */
	protected Path(Node x,Node y,int position)
	{
		/*
		 * position=1 表示x和y位于路径中央
		 * position=2表示x和y位于路径左侧
		 * position=3表示x和y位于路径右侧
		 */
		this.position=position;
		switch(position)
		{
			case 1:n2=x;n3=y;break;
			case 2:n1=x;n2=y;break;
			case 3:n3=x;n4=y;break;
		}
	}
	
	/*
	 * Function: Judge if the path is P4 
	 * Input: None
	 * Output: None
	 */
	protected boolean IsP4()
	{
		if(Node.IsNeighbor(n1, n3)||Node.IsNeighbor(n2, n4)||Node.IsNeighbor(n1, n4))
			return false;
		return true;
	}
	protected Node getN1() {
		return n1;
	}
	protected void setN1(Node n1) {
		this.n1 = n1;
	}
	protected Node getN2() {
		return n2;
	}
	protected void setN2(Node n2) {
		this.n2 = n2;
	}
	protected Node getN3() {
		return n3;
	}
	protected void setN3(Node n3) {
		this.n3 = n3;
	}
	protected Node getN4() {
		return n4;
	}
	protected void setN4(Node n4) {
		this.n4 = n4;
	}
	
}
