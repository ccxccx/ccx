package c.cx900;
import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethod;
import android.webkit.MimeTypeMap;
import android.widget.*;
import android.app.*;
import android.os.*;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import org.w3c.dom.Text;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

public class sy extends Activity implements View.OnClickListener
{
	TextView t;EditText e;Button b,b2,b3;
	public void onBackPressed()
	{
		//使app按back键时返回桌面，而不是退出app
		startActivity(new Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_HOME));
	}
	protected void onCreate(Bundle bu)
	{
		super.onCreate(bu);
		//若想获取文件列表，则必须要有以下的权限：<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
		//而且READ_EXTERNAL_STORAGE的权限必须动态申请才行，记!!!!!!!!!!!!小心!!!!!!!!!!!
		//若想重命名文件，则必须要有以下的权限：<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
		//而且WRITE_EXTERNAL_STORAGE的权限必须动态申请才行，记!!!!!!!!!!!!小心!!!!!!!!!!!
		ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},0);
	}
	public void onRequestPermissionsResult(int requestCode,String[]permissions,int[]grantResults)
	{try{
		LinearLayout l=new LinearLayout(this);setContentView(l);l.setOrientation(LinearLayout.VERTICAL);
		//l.addView(t=new TextView(this));
		l.addView(b=new Button(this));b.setOnClickListener(this);
		l.addView(b2=new Button(this));b2.setOnClickListener(this);
		l.addView(b3=new Button(this));b3.setOnClickListener(this);
		
		Notification.Builder b;
	}catch(Exception e){e.printStackTrace();}}
	public void onClick(View v)
	{
		if(v==b)
		{
			startActivity(new Intent(this,sy.class).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
		}
		else if(v==b2)
		{
		
		}
		else if(v==b3)
		{
		
		}
	}
}