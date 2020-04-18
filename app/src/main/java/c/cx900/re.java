package c.cx900;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;

public class re extends BroadcastReceiver
{
	public void onReceive(Context context,Intent intent)
	{
		if(((KeyEvent)intent.getParcelableExtra(Intent.EXTRA_KEY_EVENT)).getAction()==KeyEvent.ACTION_DOWN)ac10.b2();
	}
}