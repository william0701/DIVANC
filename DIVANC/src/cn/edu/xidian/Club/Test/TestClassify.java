package cn.edu.xidian.Club.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import cn.edu.xidian.Club.Network;
import cn.edu.xidian.Club.NetworkClassify;
import cn.edu.xidian.Club.Node;

public class TestClassify 
{
	public static void main(String args[]) throws Exception
	{
		Network net=new Network("D:\\TestData\\HsaHPRD.txt");
		ArrayList<Network> tnetset=new ArrayList<Network>();
		
		String filepath="D:\\final\\EN2H_HsaHPRD_Community.txt";
		File file=new File(filepath);
		FileReader in=new FileReader(file);
		BufferedReader br=new BufferedReader(in);
		String content=br.readLine();
		int line=1;
		while(content!=null)
		{
			System.out.println(line++);
			ArrayList<Node> tnodelist=new ArrayList<Node>();
			String ss[]=content.split("\t");
			for(String s:ss)
				tnodelist.add(Network.orig.nodelist.get(Network.orig.name2index.get(s)));
			Network tnetwork=new Network();
			tnetwork.nodelist=tnodelist;
			tnetset.add(tnetwork);
			content=br.readLine();
		}
		NetworkClassify test=new NetworkClassify("test",tnetset,"D:\\","EN2H");
		test.run();
	}
}
