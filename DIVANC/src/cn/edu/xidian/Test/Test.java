package cn.edu.xidian.Test;

import java.io.File;

import cn.edu.xidian.Club.*;

public class Test {
	public static void main(String args[]) {
//		String file[]={"SceDIP","HsaHPRD"};
//		for(String f:file)
//		{
			String f="";
			String networkpath = "";
			String outputpath = "";
			long begin = System.currentTimeMillis();
			TwoClubDistribution test = new TwoClubDistribution(networkpath,	outputpath,"DIVANC");
			test.run();
			long end = System.currentTimeMillis();
			new NetworkClassify(new File(networkpath).getName().split("\\.")[0],test.subnetworkset, outputpath,"DIVANC").run();
			OverLapNetwork olnetwork=new OverLapNetwork(new File(networkpath).getName().split("\\.")[0],test.subnetworkset,outputpath,"DIVANC");
			olnetwork.run();
			new NetworkClassify(new File(networkpath).getName().split("\\.")[0]+"_overlap",olnetwork.subover, outputpath,"DIVANC").run();
			System.out.println("Time:" + (end - begin) + "ms");
//		}
	}
}
