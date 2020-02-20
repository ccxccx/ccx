package c.cx900;
import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.*;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.net.Uri;
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;
import android.preference.PreferenceManager;
import android.provider.DocumentsContract;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import android.app.*;
import android.os.*;

import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class sy3 extends Activity
{
	TextView t;
	protected void onCreate(Bundle bu)
	{
		super.onCreate(bu);
		ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE
				,Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS},0);
	}
	public void onRequestPermissionsResult(int requestCode,String[]permissions,int[]grantResults)
	{
		LinearLayout l=new LinearLayout(this);setContentView(l);l.setOrientation(LinearLayout.VERTICAL);
		l.addView(t=new TextView(this));
//		if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N){
//			StorageManager sm = (StorageManager)getSystemService(Context.STORAGE_SERVICE);
//			StorageVolume volume = sm.getPrimaryStorageVolume();
//			System.out.println(sm.getStorageVolumes().size());
//			volume = sm.getStorageVolumes().get(1);
//			Intent intent = volume.createAccessIntent(Environment.DIRECTORY_PICTURES);
////			Intent intent=new Intent("android.os.storage.action.OPEN_EXTERNAL_DIRECTORY");
////			intent.putExtra("android.os.storage.extra.STORAGE_VOLUME", volume);
//			startActivityForResult(intent, 0);
//		}
		
		startActivityForResult(new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE),0);
		
		







		

//		final Intent intent = new Intent();
//		if (Integer.valueOf(android.os.Build.VERSION.SDK_INT) >= 19) {
//			intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
//		}
//		else {
//			intent.setAction(Intent.ACTION_GET_CONTENT);
//		}
//		intent.addCategory(Intent.CATEGORY_OPENABLE);
//		intent.setType("application/msword,application/pdf,text/plain");  //Text, DOC, PDF
//		startActivityForResult(intent,0);
	
	
	
	
	
	
	
	}
	
	
		protected void onActivityResult(int requestCode, int resultCode, Intent data){
		if(resultCode==Activity.RESULT_OK&&data!=null){
			//Get and Store the URI in

			final int takeFlags=data.getFlags()
					&(Intent.FLAG_GRANT_READ_URI_PERMISSION
					|Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
			if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
				getContentResolver().takePersistableUriPermission(data.getData(),takeFlags);
			}
			String s="/storage/999D-BE20/Pictures/";
			//String s="/storage/sdcard1/0/";
			File f=new File(s+"5");
			t.setText(f.exists()+" "+f.canRead()+" "+f.canWrite()+" "+f.renameTo(new File(s+"6")));
		}
	}
	
}