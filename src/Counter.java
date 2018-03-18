import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class Counter {
	
	boolean[] commands;
	ArrayList<String> ipfiles;
	ArrayList<String> sl;
	String opfile;
	String slfile;
	String dir;
	String pattern;
	
	public Counter(String[] args) throws IOException
	{
		opfile="result.txt";
		commands = new boolean[7];
		ipfiles = new ArrayList<String>();
		sl=new ArrayList<String>();
		dir=".";
		parse(args);
		execCmds();
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
				case "-a":
					commands[4] = true;
					break;
				case "-s":
					commands[5] = true;
					break;
				case "-e":
					commands[6] = true;
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
				case "-e":
					slfile=args[i];
				}
			}
		}
		if(commands[5])
		{
			pattern=ipfiles.get(0);
			ipfiles.clear();
		}
	}
	
	private void execCmds() throws IOException
	{
		if(commands[6])
			parseStoplist();
		if(commands[5])
			parseDir(dir);
	}
	
	private void parseStoplist() throws IOException
	{
		File file=new File(slfile);
		BufferedReader br=new BufferedReader(new FileReader(file));
		String content="";
		String line;
		while((line=br.readLine())!=null)
			content+=line+' ';
		String word="";
		for(int i=0;i<content.length();i++)
		{
			char ch=content.charAt(i);
			if(ch==' ')
			{
				if(!word.isEmpty())
				{
					sl.add(word);
					word="";
				}
			}
			else
				word+=ch;
		}
		br.close();
		System.out.println(sl);
	}
	
	private void parseDir(String dir)
	{
		File f1 = new File(dir);
		if (f1.isDirectory())
		{
			String s[] = f1.list();
			for (int i = 0; i < s.length; i++)
			{
				File f = new File(dir + "/" + s[i]);
				if (f.isDirectory())
					parseDir(dir + "/" + s[i]);
				else if (Pattern.matches(pattern, dir + "/" + s[i])) 
				{
					System.out.println( dir+"/"+s[i]);
					ipfiles.add(dir + "/" + s[i]);
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
			writer.append(fstat.filepath + "," + "单词数：" + fstat.countWords(sl) + "\r\n");
		if (commands[2])
			writer.append(fstat.filepath + "," + "行数：" + fstat.countLines() + "\r\n");
		if (commands[4])
		{
			int lines[]=fstat.countDifferentLines();
			writer.append(fstat.filepath+","+"代码行/空行/注释行："+lines[0]+"/"+lines[1]+"/"+lines[2]+"\r\n");
		}
		writer.close();
		fop.close();
	}
}
