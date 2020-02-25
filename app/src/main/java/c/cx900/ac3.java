package c.cx900;
import android.Manifest;
import android.animation.ObjectAnimator;
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
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
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
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationManagerCompat;
import c.cx900.R;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ac3 extends Activity implements View.OnClickListener
{
	List<String[]>l=new ArrayList<>();List<Integer>l4=new ArrayList<>();
	ListView l3;byte[]d;int w,j;
	Button b,b2,b3,b4,b5,b6;
	ba a2;Bitmap b0;MediaPlayer m=new MediaPlayer();
	TextView t,t2;TextView t4;
	SeekBar s;Thread t3=new Thread();String s2;
	Handler h=new Handler()
	{
		public void handleMessage(Message m){t.setText(s2);}
	};
	
	LinearLayout l8;
	MediaMetadataRetriever r2=new MediaMetadataRetriever();
	ImageView i2;
	
	List<Integer>l10=new LinkedList<>();List<Integer>l12=new ArrayList<>();
	ListView l13;ba a3;ObjectAnimator a4;
	
	NotificationManager m2;Notification n;RemoteViews r3;
	
	SQLiteDatabase db;
	public void onBackPressed()
	{
		//使app按back键时返回桌面，而不是退出app
		startActivity(new Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_HOME));
	}
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
		RelativeLayout r=new RelativeLayout(this);setContentView(r);
		RelativeLayout.LayoutParams p=new RelativeLayout.LayoutParams(-1,-2);p.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		RelativeLayout.LayoutParams p2=new RelativeLayout.LayoutParams(-1,-1);p2.addRule(RelativeLayout.ABOVE,1);
		p2.addRule(RelativeLayout.BELOW,2);
		LinearLayout l5=new LinearLayout(this);r.addView(l5,p);int i=1;l5.setId(i);
		l5.setOrientation(LinearLayout.VERTICAL);
		LinearLayout.LayoutParams p3=new LinearLayout.LayoutParams(0,-2,1);
		LinearLayout l7=new LinearLayout(this);l5.addView(l7);
		l7.addView(t=new TextView(this),p3);t.setGravity(Gravity.CENTER);
		l7.addView(s=new SeekBar(this),new LinearLayout.LayoutParams(0,-2,4));
		s.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
		{
			public void onProgressChanged(SeekBar seekBar,int i,boolean b)
			{
				t.setText(String.format("%02d:%02d",i/60,i%60));
			}
			public void onStartTrackingTouch(SeekBar seekBar){}
			public void onStopTrackingTouch(SeekBar s)
			{
				m.seekTo(s.getProgress()*1000);
			}
		});
		l7.addView(t2=new TextView(this),p3);t2.setGravity(Gravity.CENTER);
		LinearLayout l6=new LinearLayout(this);l5.addView(l6);
		l6.addView(b=new Button(this),p3);b.setText("上一首");b.setOnClickListener(this);
		l6.addView(b2=new Button(this),p3);b2.setText("暂停");b2.setOnClickListener(this);
		l6.addView(b3=new Button(this),p3);b3.setText("下一首");b3.setOnClickListener(this);
		LinearLayout l9=new LinearLayout(this);r.addView(l9,-1,-2);l9.setId(i=2);
		l9.addView(b4=new Button(this),p3);b4.setText("歌曲列表");b4.setOnClickListener(this);
		l9.addView(b5=new Button(this),p3);b5.setText("歌曲详情页");b5.setOnClickListener(this);
		l9.addView(b6=new Button(this),p3);b6.setText("最近播放过的");b6.setOnClickListener(this);
		r.addView(l3=new ListView(this),p2);
		r.addView(l8=new LinearLayout(this),p2);l8.setOrientation(LinearLayout.VERTICAL);
		i2=new ImageView(this);l8.addView(i2,-1,w);
		a4=ObjectAnimator.ofFloat(i2,"rotation",0,360);
		a4.setDuration(10000);a4.setRepeatCount(-1);
		a4.setInterpolator(new LinearInterpolator());a4.start();
		l8.addView(t4=new TextView(this));t4.setGravity(Gravity.CENTER);
		m.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
		{
			public void onCompletion(MediaPlayer m)
			{
				b3.callOnClick();
			}
		});
		r.addView(l13=new ListView(this),p2);
		String s="ccx";
		m2=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
		//注册按键的广播的接收器
		registerReceiver(new BroadcastReceiver()
		{
			public void onReceive(Context c,Intent i)
			{
				int k=i.getIntExtra("i",0);
				if(k==1)b.callOnClick();
				else if(k==2)b2.callOnClick();
				else if(k==3)b3.callOnClick();
			}
		},new IntentFilter(s));
		Notification.Builder b;
		if(Build.VERSION.SDK_INT>=26)
		{
			//添加NotificationChannel，第1、2个参数都不能为""，小心!!!!!!!!!!!
			m2.createNotificationChannel(new NotificationChannel("1","1",NotificationManager.IMPORTANCE_HIGH));
			b=new Notification.Builder(this,"1");
		}
		else b=new Notification.Builder(this);
		//这个必须设置，否则会秒退，为啥？？？？？？？小心!!!!!!!!!!
		b.setSmallIcon(R.drawable.i2)
				//令通知显示到第1个位置
				.setOngoing(true);
		r3=new RemoteViews(getPackageName(),R.layout.l);
		Intent i3=new Intent(s);
		//PendingIntent.getBroadcast()：从系统取得一个用于向BroadcastReceiver发送广播的PendingIntent对象
		r3.setOnClickPendingIntent(R.id.b,PendingIntent.getBroadcast(this,1
				,i3.putExtra("i",1),PendingIntent.FLAG_UPDATE_CURRENT));
		r3.setOnClickPendingIntent(R.id.b2,PendingIntent.getBroadcast(this,2
				,i3.putExtra("i",2),PendingIntent.FLAG_UPDATE_CURRENT));
		r3.setOnClickPendingIntent(R.id.b3,PendingIntent.getBroadcast(this,3
				,i3.putExtra("i",3),PendingIntent.FLAG_UPDATE_CURRENT));
		//设置通知的视图
		b.setContent(r3)
				//设置,点击通知后,执行的PendingIntent
				.setContentIntent(PendingIntent.getActivity(this,4
						,new Intent(this,ac3.class).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP),PendingIntent.FLAG_UPDATE_CURRENT));
		//刷新通知
		m2.notify(1,n=b.build());
		Cursor c=getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,null,null,null,null);
		b0=BitmapFactory.decodeResource(getResources(),R.drawable.i2);
		int k=0;
		for(;c.moveToNext();)
		{
			i=c.getInt(c.getColumnIndex(MediaStore.Audio.Media.DURATION))/1000;
			l4.add(i);
			l.add(new String[]{c.getString(c.getColumnIndex(MediaStore.Audio.Media.DATA))
					,c.getString(c.getColumnIndex(MediaStore.Audio.Media.TITLE)),c.getString(c.getColumnIndex(MediaStore.Audio.Media.ARTIST))
					,String.format("%02d:%02d",i/60,i%60),c.getString(c.getColumnIndex(MediaStore.Audio.Media.ALBUM))});
			l12.add(k++);
		}
		l3.setAdapter(a2=new ba(this,l12));
		l13.setAdapter(a3=new ba(this,l10));
		b4.callOnClick();
		File f2=new File(getFilesDir(),"0.db");
		if(!f2.exists())
		{
			//必须加WRITE_EXTERNAL_STORAGE的权限，小心!!!!!!!!!!
			//adb pull /storage/emulated/0/0.db C:\Users\Administrator\Desktop\0.db
			db=SQLiteDatabase.openOrCreateDatabase(f2,null);
			db.execSQL("create table t(i txt)");
			db.execSQL("insert into t values(',')");
		}
		else db=SQLiteDatabase.openOrCreateDatabase(f2,null);
		c=db.rawQuery("select * from t",null);c.moveToNext();
		for(String t:c.getString(0).split(","))
		{
			i=new Integer(t);
			//必须判断i<l.size()，否则删除音乐文件后有时会秒退，小心!!!!!!!!!!!
			if(i<l.size())l10.add(i);
		}
		if(!l10.isEmpty())j=l10.get(0);
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		//j=1251;
		f();
		if(!NotificationManagerCompat.from(this).areNotificationsEnabled())
		{
			AlertDialog.Builder a=new AlertDialog.Builder(this).setMessage("请打开通知权限！")
					.setPositiveButton("确定",new DialogInterface.OnClickListener()
					{
						public void onClick(DialogInterface dialog,int which)
						{
							startActivityForResult(new Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
									.putExtra("app_package",getPackageName()).putExtra("app_uid",getApplicationInfo().uid),0);
						}
					});
			a.show();
		}
	}catch(Exception e){e.printStackTrace();}}
	protected void onActivityResult(int requestCode,int resultCode,Intent data)
	{
		//打开通知权限后要刷新通知
		m2.notify(1,n);
	}
	Bitmap b2o(Bitmap b)
	{
		int i=b.getWidth();Bitmap o=Bitmap.createBitmap(i,i,Bitmap.Config.ARGB_8888);
		Canvas c=new Canvas(o);Paint p=new Paint();c.drawCircle(i/2,i/2,i/2,p);
		p.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		c.drawBitmap(b,null,new Rect(0,0,i,i),p);return o;
	}
	public void onClick(View v)
	{try{
		int i=l.size();
		if(v==b)
		{
			j=(j+i-1)%i;
			f();
		}
		else if(v==b2)
		{
			if(b2.getText().equals("暂停"))
			{
				b2.setText("播放");
				r3.setTextViewText(R.id.b2,"播放");
				m.pause();
				a4.pause();
			}
			else
			{
				b2.setText("暂停");
				r3.setTextViewText(R.id.b2,"暂停");
				m.start();
				a4.resume();
			}
			m2.notify(1,n);
		}
		else if(v==b3)
		{
			j=(j+1)%i;
			f();
		}
		else if(v==b4)
		{
			b4.setBackgroundColor(0x7700ff00);
			b5.setBackgroundColor(0xffffffff);
			b6.setBackgroundColor(0xffffffff);
			l3.setVisibility(View.VISIBLE);
			l8.setVisibility(View.INVISIBLE);
			l13.setVisibility(View.INVISIBLE);
		}
		else if(v==b5)
		{
			b4.setBackgroundColor(0xffffffff);
			b5.setBackgroundColor(0x7700ff00);
			b6.setBackgroundColor(0xffffffff);
			l3.setVisibility(View.INVISIBLE);
			l8.setVisibility(View.VISIBLE);
			l13.setVisibility(View.INVISIBLE);
		}
		else if(v==b6)
		{
			b4.setBackgroundColor(0xffffffff);
			b5.setBackgroundColor(0xffffffff);
			b6.setBackgroundColor(0x7700ff00);
			l3.setVisibility(View.INVISIBLE);
			l8.setVisibility(View.INVISIBLE);
			l13.setVisibility(View.VISIBLE);
		}
	}catch(Exception e){e.printStackTrace();}}
	void f()
	{try{
		//System.out.println("ccxccxccxccx："+j);
		String[]a=l.get(j);
		t3.interrupt();
		s.setProgress(0);
		s.setMax(l4.get(j));
		if(b2.getText().equals("播放"))b2.callOnClick();
		t2.setText(a[3]);
		m.reset();
		m.setDataSource(a[0]);
		m.prepare();
		m.start();
		a2.notifyDataSetChanged();
		t3=new Thread(new Runnable()
		{
			public void run()
			{try{
				for(;;Thread.sleep(100))
				{
					s.setProgress(m.getCurrentPosition()/1000);
				}
			}catch(Exception e){e.printStackTrace();}}
		});
		t3.start();
		r2.setDataSource(a[0]);
		d=r2.getEmbeddedPicture();
		Bitmap b=d!=null?b2o(BitmapFactory.decodeByteArray(d,0,d.length)):b0;
		i2.setImageBitmap(b);
		t4.setText("歌名："+a[1]+"\n歌手："+a[2]+"\n专辑名："+a[4]);
		l10.remove((Integer)j);
		l10.add(0,j);
		a3.notifyDataSetChanged();
		r3.setImageViewBitmap(R.id.i,b);
		r3.setTextViewText(R.id.t,a[1]);
		r3.setTextViewText(R.id.t2,a[2]);
		m2.notify(1,n);
		l3.setSelection(j<3?0:j-3);
		StringBuilder u=new StringBuilder();
		for(int i:l10)u.append(i+",");
		db.execSQL("update t set i='"+u+"'");
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}catch(Exception e){e.printStackTrace();}}
	class ba extends BaseAdapter
	{
		Context c;List<Integer>l11;
		ba(Context a,List<Integer>b){c=a;l11=b;}
		public int getCount(){return l11.size();}
		public Object getItem(int position){return null;}
		public long getItemId(int position){return 0;}
		class i{ImageView i;TextView t,t2;i(ImageView c,TextView a,TextView b){i=c;t=a;t2=b;}}
		public View getView(int k,View v,ViewGroup g)
		{
			LinearLayout l2=(LinearLayout)v;i a;
			final int i=l11.get(k);
			if(l2==null)
			{
				l2=new LinearLayout(c);
				l2.setTag(a=new i(new ImageView(c),new TextView(c),new TextView(c)));
				l2.addView(a.i,new LinearLayout.LayoutParams(0,w/6,1));
				l2.addView(a.t,new LinearLayout.LayoutParams(0,w/6,3));
				l2.addView(a.t2,new LinearLayout.LayoutParams(0,w/6,2));
				a.t2.setGravity(Gravity.RIGHT);
			}
			else a=(i)l2.getTag();
			if(i==j)l2.setBackgroundColor(0x77ff0000);
			else l2.setBackgroundColor(0xffffffff);
			String[]s=l.get(i);
			a.t.setText(i+"："+s[1]);
			a.t2.setText(s[2]);
			r2.setDataSource(s[0]);
			d=r2.getEmbeddedPicture();
			a.i.setImageBitmap(d!=null?BitmapFactory.decodeByteArray(d,0,d.length):b0);
			l2.setOnClickListener(new View.OnClickListener()
			{
				public void onClick(View v)
				{try{
					if(i==j)
					{
						if(m.isPlaying())b5.callOnClick();
						else b2.callOnClick();
					}
					else
					{
						j=i;
						f();
					}
				}catch(Exception e){e.printStackTrace();}}
			});
			return l2;
		}
	}
}