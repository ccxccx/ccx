package c.cx900;
import android.widget.*;
import android.app.*;
import android.os.*;
public class sy4 extends Activity
{
	protected void onCreate(Bundle bu)
	{
		super.onCreate(bu);
		System.out.println("onCreate");
		
		LinearLayout l=new LinearLayout(this);setContentView(l);l.setOrientation(LinearLayout.VERTICAL);
		
	}
	protected void onStart()
	{
		super.onStart();
		System.out.println("onStart");
	}
	protected void onRestart()
	{
		super.onRestart();
		System.out.println("onRestart");
	}
	protected void onResume()
	{
		super.onResume();
		System.out.println("onResume");
	}
	protected void onPause()
	{
		super.onPause();
		System.out.println("onPause");
	}
	protected void onStop()
	{
		super.onStop();
		System.out.println("onStop");
	}
	protected void onDestroy()
	{
		super.onDestroy();
		System.out.println("onDestroy");
	}
}