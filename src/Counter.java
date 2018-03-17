import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class Counter {
	
	boolean[] commands;
	ArrayList<String> ipfiles;
	String opfile;
	
	public Counter(String[] args)
	{
		opfile="result.txt";
		commands = new boolean[4];
		ipfiles = new ArrayList<String>();
		parse(args);
	}
	
	private void parse(String[] args)
	{
		if(args.length==0)
			System.out.println("缺少必要参数");
		String lastCommand=null;
		for (int i = 0; i < args.length; ++i) 
		{
			if (args[i].startsWith("-"))
			{
				lastCommand=args[i];
				switch (args[i])
				{
				case "-c":
					commands[0] = true;
					break;
				case "-w":
					commands[1] = true;
					break;
				case "-l":
					commands[2] = true;
					break;
				case "-o":
					commands[3] = true;
					break;
				}
			} 
			else
			{
				switch (lastCommand)
				{
				case "-c":case "-w":case "-l":
					ipfiles.add(args[i]);
					break;
				case "-o":
					opfile=args[i];
				}
			}
		}
	}
	
	public void write(Stat fstat) throws IOException
	{
		File f = new File(opfile);
		FileOutputStream fop = new FileOutputStream(f,true);
		OutputStreamWriter writer = new OutputStreamWriter(fop, "UTF-8");
		if (commands[0])
			writer.append(fstat.filepath + "," + "字符数：" + fstat.countChars() + "\r\n");
		if (commands[1])
			writer.append(fstat.filepath + "," + "单词数：" + fstat.countWords() + "\r\n");
		if (commands[2])
			writer.append(fstat.filepath + "," + "行数：" + fstat.countLines() + "\r\n");
		writer.close();
		fop.close();
	}
}
