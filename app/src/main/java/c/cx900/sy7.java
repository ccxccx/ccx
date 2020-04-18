package c.cx900;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.Bundle;

public class sy7 extends Activity
{
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		AudioManager m=(AudioManager)getSystemService(AUDIO_SERVICE);
		m.registerMediaButtonEventReceiver(new ComponentName(this,sy8.class));
		
	}
}