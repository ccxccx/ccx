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

public class sy2 extends Activity implements View.OnClickListener
{
	TextView t;EditText e;Button b,b2,b3;
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
		//openFile(this,Environment.getExternalStorageDirectory()+"/0/3");
		//openFile(this,Environment.getExternalStorageDirectory()+"/0/2.txt");
		
	}catch(Exception e){e.printStackTrace();}}
	public void onClick(View v)
	{
		if(v==b) openFile(this,Environment.getExternalStorageDirectory()+"/0/3");
		else if(v==b2)
		{
			File file = new File(Environment.getExternalStorageDirectory()+"/0/2.txt");
			Intent i=new Intent(Intent.ACTION_VIEW);
			i.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
			Uri contentUri = FileProvider.getUriForFile(this, "ccx900", file);
			//i.setDataAndType(contentUri,"*/*");
			i.setDataAndType(contentUri,null);
			startActivity(i);
		}
		else if(v==b3)
		{
			File file = new File(Environment.getExternalStorageDirectory()+"/0/2.txt");
			Intent i=new Intent(Intent.ACTION_VIEW);
			i.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
			Uri contentUri = FileProvider.getUriForFile(this, "ccx900", file);
			i.setDataAndType(contentUri,"*/*");
			//i.setDataAndType(contentUri,null);
			startActivity(i);
		}
	}
	
	
	
	
	void openFile(Context context,String path)
	{
		File file = new File(path);
		try {
			Intent i=new Intent(Intent.ACTION_VIEW);
			//Intent i=new Intent();
			//获取文件file的MIME类型
			String type = getMimeType(path);
			//if(Build.VERSION.SDK_INT>=24)
			{
				//i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				i.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
				Uri contentUri = FileProvider.getUriForFile(context, "ccx900", file);
//				i.setDataAndType(contentUri, type);
				//System.out.println(type);
				i.setDataAndType(contentUri,"*/*");
				//i.setData(contentUri);
			}
//			else
//			{
//				//i.setDataAndType(Uri.fromFile(file),type);
//
//				//i.setDataAndType(Uri.fromFile(file),"text/*;image/*;audio/*;video/*;application/*");
//				i.setDataAndType(Uri.fromFile(file),"video/*");
//
//			}
			
			
			
			
			
			
			//跳转
			startActivity(i);
		}catch(Exception e){e.printStackTrace();}}
	String getMimeType(String filePath)
	{
		String ext = MimeTypeMap.getFileExtensionFromUrl(filePath);
		return MimeTypeMap.getSingleton().getMimeTypeFromExtension(ext);
	}
}