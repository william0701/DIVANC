package com.xidian.Test;

import java.io.File;

import com.xidian.Club.*;

public class Test {
	public static void main(String args[]) {
//		String file[]={"SceDIP","HsaHPRD"};
//		for(String f:file)
//		{
		String f="HsaHPRD";
			String networkpath = "C:\\Users\\JiaSongwei\\Desktop\\ceshi1.txt";
			String outputpath = "C:\\Users\\JiaSongwei\\Desktop\\";
			long begin = System.currentTimeMillis();
			TwoClubDistribution test = new TwoClubDistribution(networkpath,	outputpath,"EN2H");
			test.run();
			long end = System.currentTimeMillis();
			new NetworkClassify(new File(networkpath).getName().split("\\.")[0],test.subnetworkset, outputpath,"EN2H").run();
			OverLapNetwork olnetwork=new OverLapNetwork(new File(networkpath).getName().split("\\.")[0],test.subnetworkset,outputpath,"EN2H");
			olnetwork.run();
			new NetworkClassify(new File(networkpath).getName().split("\\.")[0]+"_overlap",olnetwork.subover, outputpath,"EN2H").run();
			System.out.println("Time:" + (end - begin) + "ms");
//		}
	}
}
