package com.xidian.Club.Test;

import com.xidian.Club.TwoClubDistribution;

public class TestSYS 
{
	public static void main(String args[])
	{
		for(int i=1;i<=20;++i)
		{
			String filepath="network//network_alpha=.1-"+i+".dat";
			String outputpath="result//";
			TwoClubDistribution test = new TwoClubDistribution(filepath,outputpath,"EN2H");
			test.run();
		}
	}
}
