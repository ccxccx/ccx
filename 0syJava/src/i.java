import java.util.Arrays;
import java.util.Comparator;

public class i
{
	public static void main(String[]S)
	{
		String[]s={"1.1","012",""};
		Arrays.sort(s);
		for(String i:s)System.out.print(i+" ");
		System.out.println();
		Arrays.sort(s,new Comparator<String>()
		{
			public int compare(String i,String j)
			{
				return 0;
			}
		});
		for(String i:s)System.out.print(i+" ");
		System.out.println();
	}
}