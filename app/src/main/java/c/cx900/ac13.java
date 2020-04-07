package c.cx900;
import android.content.Intent;
import android.app.*;
import android.os.*;
public class ac13 extends Activity
{
	protected void onCreate(Bundle bu)
	{
		super.onCreate(bu);
		startActivityForResult(new Intent(Intent.ACTION_SET_WALLPAPER),0);
	}
	protected void onActivityResult(int requestCode,int resultCode,Intent data)
	{
		finish();
	}
}