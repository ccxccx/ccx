package c.cx900;
import android.Manifest;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.RemoteViews;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationManagerCompat;
import c.cx900.R;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ac4 extends Activity
{
	List<String[]>l=new ArrayList<>();ListView l2;int w;ba a;
	MediaMetadataRetriever r=new MediaMetadataRetriever();
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		//必须<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>和requestPermissions()一起使用才行
		//请求权限不是permission，而是uses-permission，易错难发现!!!!!!!!!小心!!!!!!!!!
		ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},0);
	}
	public void onRequestPermissionsResult(int requestCode,String[]permissions,int[]grantResults)
	{try{
		w=getWindowManager().getDefaultDisplay().getWidth();
		setContentView(l2=new ListView(this));
		Cursor c=getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,null,null,null,null);
		int i;
		for(;c.moveToNext();)
		{
			i=c.getInt(c.getColumnIndex(MediaStore.Video.Media.DURATION))/1000;
			l.add(new String[]{c.getString(c.getColumnIndex(MediaStore.Video.Media.DATA))
					,c.getString(c.getColumnIndex(MediaStore.Video.Media.DISPLAY_NAME))
					,String.format("%02d:%02d",i/60,i%60)});
		}
		l2.setAdapter(a=new ba(this));
	}catch(Exception e){e.printStackTrace();}}
	class ba extends BaseAdapter
	{
		Context c;
		ba(Context a){c=a;}
		public int getCount(){return l.size();}
		public Object getItem(int position){return null;}
		public long getItemId(int position){return 0;}
		class i{ImageView i;TextView t,t2;i(ImageView c,TextView a,TextView b){i=c;t=a;t2=b;}}
		public View getView(int i,View v,ViewGroup g)
		{
			LinearLayout l2=(LinearLayout)v;i a;
			if(l2==null)
			{
				l2=new LinearLayout(c);
				l2.setTag(a=new i(new ImageView(c),new TextView(c),new TextView(c)));
				l2.addView(a.i,new LinearLayout.LayoutParams(0,w*9/32,3));
				l2.addView(a.t,new LinearLayout.LayoutParams(0,w*9/32,2.25f));
				l2.addView(a.t2,new LinearLayout.LayoutParams(0,w*9/32,0.75f));
				a.t2.setGravity(Gravity.RIGHT);
			}
			else a=(i)l2.getTag();
			final String[]s=l.get(i);
			a.t.setText(i+"："+s[1]);
			a.t2.setText(s[2]);
			r.setDataSource(s[0]);
			Bitmap b=r.getFrameAtTime();
			a.i.setImageBitmap(b);
			l2.setOnClickListener(new View.OnClickListener()
			{
				public void onClick(View v)
				{try{
					startActivity(new Intent(c,ac5.class).setData(Uri.parse("file://"+s[0])));
				}catch(Exception e){e.printStackTrace();}}
			});
			return l2;
		}
	}
}