import java.io.IOException;

public class Test {

	public static void main(String[] args) throws IOException {
		Counter ctr=new Counter(args);
		for(int i=0;i<ctr.ipfiles.size();i++)
		{
			Stat fstat=new Stat(ctr.ipfiles.get(i));
			ctr.write(fstat);
		}
	}

}
