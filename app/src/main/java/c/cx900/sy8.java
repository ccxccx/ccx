package c.cx900;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

public class sy8 extends BroadcastReceiver
{
	public void onReceive(Context context,Intent intent)
	{
		
		if(((KeyEvent)intent.getParcelableExtra(Intent.EXTRA_KEY_EVENT)).getAction()==KeyEvent.ACTION_DOWN)System.out.println("123456");
		
	}
}