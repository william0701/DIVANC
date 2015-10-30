package cn.edu.xidian.Club;
import java.io.File;
import java.io.FileOutputStream;
import java.util.*;
/*
 * Function: Providing the function of set operations and file operations
 */
class Toolbox
{
	/*
	 * Function: Compute the intersection of two nodes set
	 * Input: set1,set2 are two nodes set
	 * Output: The intersection set of two nodes
	 */
	public static ArrayList<Node> Intersection(ArrayList<Node> set1,ArrayList<Node> set2)
	{
		ArrayList<Node> result=new ArrayList<Node>();
		for(int i=0;i<set1.size();++i)
			if(set1.get(i).IsContained(set2))
				result.add(set1.get(i));
		return result;
	}
	/*
	 * Function: Delete the element with index given
	 * Input: set: the set 
	 *        index: the index of element deleted in the set
	 * Output: The set without the deleted element
	 */
	public static ArrayList<Node> KickoutElement(ArrayList<Node> set,int index)
	{
		set.remove(index);
		return set;
	}
	
	/*
	 * Function: Delete the given element
	 * Input: set: the set 
	 *        node: the element deleted
	 * Output: The set without the deleted element
	 */
	public static ArrayList<Node> KickoutElement(ArrayList<Node> set,Node node)
	{
		for(int i=0;i<set.size();++i)
			if(set.get(i).equals(node))
				set.remove(i);
		return set;
	}
	
	/*
	 * Function: Compute the matrix multiple Approximately
	 * Input: m1: one matrix
	 *        m2: the other matrix
	 * Output: The result matrix of m1*m2
	 */
	public static int[][] AppMatMul(int m1[][],int m2[][])
	{
		int[][] result=new int[m1.length][m2.length];
		for(int i=0;i<m1.length;++i)
		{
			for(int j=i;j<m2.length;++j)
			{
				for(int k=0;k<m1[i].length;++k)
				{
					if(m1[i][k]*m2[k][j]>0)
					{
						result[i][j]=1;
						result[j][i]=1;
						break;
					}
				}
			}
		}
		return result;
	}
	
	/*
	 * Function: Compute the matrix multiple Approximately
	 * Input: m1: one matrix
	 *        m2: the other matrix
	 * Output: The result matrix of m1*m2
	 */
	public static int[][] AppMatAdd(int m1[][],int m2[][])
	{
		int[][] result=new int[m1.length][m2.length];
		for(int i=0;i<m1.length;++i)
		{
			for(int j=0;j<m1.length;++j)
			{
				result[i][j]=m1[i][j]+m2[i][j];
			}
		}
		return result;
	}

	/*
	 * Function: Write file
	 * Input: filepath: the path of output file
	 *        content: the content writen in the file
	 *        append: if the file is writen from the end or from the beginning
	 * Output: None
	 */
	public static void WriteFile(String filepath,String content,boolean append)
	{
		try
		{
			File file=new File(filepath);
			if(!file.exists())
			       file.createNewFile();
			FileOutputStream fop = new FileOutputStream(file.getAbsoluteFile(),append);
			byte[] contentinbyte=content.getBytes();
			fop.write(contentinbyte);
			fop.flush();
			fop.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/*
	 * Function: Compute the union of two nodes set
	 * Input: set1,set2 are two nodes set
	 * Output: The union set of two nodes
	 */
	public static ArrayList<Node> Union(ArrayList<Node> set1,ArrayList<Node> set2) 
	{
		ArrayList<Node> result=new ArrayList<Node>();
		for(int i=0;i<set1.size();++i)
			if(!set1.get(i).IsContained(result))
				result.add(set1.get(i));
		for(int i=0;i<set2.size();++i)
			if(!set2.get(i).IsContained(result))
				result.add(set2.get(i));
		return result;
	}
}