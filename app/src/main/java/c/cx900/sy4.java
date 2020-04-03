package c.cx900;
import android.widget.*;
import android.app.*;
import android.os.*;
public class sy4 extends Activity
{
	protected void onCreate(Bundle bu)
	{
		super.onCreate(bu);
		System.out.println("Create");
		
		LinearLayout l=new LinearLayout(this);setContentView(l);l.setOrientation(LinearLayout.VERTICAL);
		
	}
	protected void onStart()
	{
		super.onStart();
		System.out.println("Start");
	}
	protected void onRestart()
	{
		super.onRestart();
		System.out.println("Restart");
	}
	protected void onResume()
	{
		super.onResume();
		System.out.println("Resume");
	}
	protected void onPause()
	{
		super.onPause();
		System.out.println("Pause");
	}
	protected void onStop()
	{
		super.onStop();
		System.out.println("Stop");
	}
	protected void onDestroy()
	{
		super.onDestroy();
		System.out.println("Destroy");
	}
}