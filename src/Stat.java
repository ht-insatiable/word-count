import java.io.*;
import java.util.ArrayList;

public class Stat {
	
	String filepath;
	
	public Stat(String fpath)
	{
		filepath=fpath;
	}
	

	public int countChars() throws IOException
	{
		File file=new File(filepath);
		BufferedReader br=new BufferedReader(new FileReader(file));
		String content="";
		String line;
		while((line=br.readLine())!=null)
			content+=line+'\n';
		br.close();
		return content.length()-1;
	}
	
	public int countLines() throws IOException
	{
		int lines=0;
		File file=new File(filepath);
		BufferedReader br=new BufferedReader(new FileReader(file));
		while(br.readLine()!=null)
			lines++;
		br.close();
		return lines;
	}
	
	public int[] countDifferentLines() throws IOException
	{
		int[] lines=new int[3];
		File file=new File(filepath);
		BufferedReader br=new BufferedReader(new FileReader(file));
		while(br.readLine()!=null)
			;
		br.close();
		return lines;
	}
	
	private boolean beingStopped(String word,ArrayList<String> sl)
	{
		for(int i=0;i<sl.size();i++)
			if(word.equals(sl.get(i)))
				return true;
		return false;
	}
	
	public int countWords(ArrayList<String> sl) throws IOException
	{
		int words=0;
		File file=new File(filepath);
		BufferedReader br=new BufferedReader(new FileReader(file));
		String content="";
		String line;
		while((line=br.readLine())!=null)
			content+=line+' ';
		boolean inWord=false;
		String word="";
		for(int i=0;i<content.length();i++)
		{
			char ch=content.charAt(i);
			if(ch==' '||ch==','||ch=='£¬'||ch=='\t') {
				if(inWord) {
					if(!sl.isEmpty()) 
					{
						if(!beingStopped(word,sl))
							words++;
					}
					else
						words++;
					word="";
					inWord=false;
				}
			}
			else {
				if(!inWord)
					inWord=true;
				word+=ch;
			}
		}
		br.close();
		return words;
	}
	
}
