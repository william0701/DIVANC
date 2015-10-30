package cn.edu.xidian.Club;
/*
 * function:modeling the output file
 */
public abstract class AbstractCommon
{
	/*
	 * Variable Definition:
	 * name: the name of output file;
	 * filesuffix: the suffix of output file, usually is ".txt";
	 * path: the location of output file
	 */
	public String name;
	public String filesuffix;
	protected String path;
	
	/*
	 * Constructor:
	 * Input: name, filesuffix, path;
	 * Output: none;
	 */
	public AbstractCommon(String name,String filesuffix,String path)
	{
		this.name=name;
		this.filesuffix=filesuffix;
		this.path=path;
	}
	
	/*
	 * Abstract Function used for every class
	 */
	abstract public void run();
	
	/*
	 * Function: write file according to the settings;
	 * Input: filename, content(the content of the output file);
	 * Output: none;
	 */
	public void Output(String filename,String content,boolean flag)
	{
		String filepath=path+name+"_"+filename+filesuffix;
		Toolbox.WriteFile(filepath, content, flag);
	}
}
