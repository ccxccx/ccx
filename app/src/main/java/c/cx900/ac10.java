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
import android.content.ComponentName;
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
import android.media.AudioManager;
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
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.RemoteViews;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationManagerCompat;
import c.cx900.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

//若想用readObject()和writeObject()传输自定义的类，则：类必须放在最外面，且2个地方的类的定义必须相同（class前面可以不同，如：写public与不写public）
//，且所在的包也必须相同，否则会卡住，而且不会报错，易错超难发现!!!!!!!!!!!!小心!!!!!!!!!!!!
//必须加static final long serialVersionUID=...;，否则会卡住，而且不会报错，易错超难发现!!!!!!!!!!!!小心!!!!!!!!!!!!
class j implements Serializable
{
	static final long serialVersionUID=1;
	String s;List<String[]>l;List<Integer>l2;int j;
	j(String a,List<String[]>b,List<Integer>c,int d){s=a;l=b;l2=c;j=d;}
}
public class ac10 extends Activity implements View.OnClickListener
{
	List<String[]>l;List<Integer>l4;
	ListView l3,l10;byte[]d;int w;
	Button b,b3,b4,b5,b6,b7,b8;
	ba a2;Bitmap b0;static MediaPlayer m;
	TextView t,t2;TextView t4;
	SeekBar s;Thread t3=new Thread();
	
	LinearLayout l2,l8;
	MediaMetadataRetriever r2=new MediaMetadataRetriever();
	ImageView i2;
	
	static NotificationManager m2;static Notification n;static RemoteViews r3;
	
	static List<String>a;static int j;List<String>a4;BroadcastReceiver r4;
	
	LinearLayout.LayoutParams p;ba2 a3;List<j>l11=new LinkedList<>();AlertDialog d2;j k;static Button b2;
	public void onBackPressed()
	{
		finish();
		//释放MediaPlayer所占用的资源，使MediaPlayer停止播放音乐
		m.release();
		//清除通知
		m2.cancel(1);
		unregisterReceiver(r4);
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
		w=getWindowManager().getDefaultDisplay().getWidth();m=new MediaPlayer();
		RelativeLayout r=new RelativeLayout(this);setContentView(r);
		RelativeLayout.LayoutParams p3=new RelativeLayout.LayoutParams(-1,-2);p3.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		LinearLayout l5=new LinearLayout(this);r.addView(l5,p3);
		l5.setOrientation(LinearLayout.VERTICAL);
		p=new LinearLayout.LayoutParams(0,-2,1);
		LinearLayout l7=new LinearLayout(this);l5.addView(l7);
		l7.addView(t=new TextView(this),p);t.setGravity(Gravity.CENTER);
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
		l7.addView(t2=new TextView(this),p);t2.setGravity(Gravity.CENTER);
		LinearLayout l6=new LinearLayout(this);l5.addView(l6);
		l6.addView(b=new Button(this),p);b.setText("上一首");b.setOnClickListener(this);
		l6.addView(b2=new Button(this),p);b2.setText("暂停");b2.setOnClickListener(this);
		l6.addView(b3=new Button(this),p);b3.setText("下一首");b3.setOnClickListener(this);
		LinearLayout l9=new LinearLayout(this);r.addView(l9,-1,-2);
		l9.addView(b5=new Button(this),p);b5.setText("音乐详情页");b5.setOnClickListener(this);
		l9.addView(b4=new Button(this),p);b4.setOnClickListener(this);
		RelativeLayout.LayoutParams p5=new RelativeLayout.LayoutParams(-1,-2);int i=3;l9.setId(i);p5.addRule(RelativeLayout.BELOW,3);
		l9=new LinearLayout(this);r.addView(l9,p5);
		l9.addView(b8=new Button(this),p);b8.setText("+");b8.setOnClickListener(this);
		l9.addView(b6=new Button(this),p);b6.setOnClickListener(this);
		RelativeLayout.LayoutParams p2=new RelativeLayout.LayoutParams(-1,-1);l5.setId(i=1);p2.addRule(RelativeLayout.ABOVE,1);
		l9.setId(i=2);p2.addRule(RelativeLayout.BELOW,2);
		r.addView(l3=new ListView(this),p2);
		r.addView(l8=new LinearLayout(this),p2);l8.setOrientation(LinearLayout.VERTICAL);
		l8.addView(i2=new ImageView(this),-1,w);
		l8.addView(t4=new TextView(this));t4.setGravity(Gravity.CENTER);
		m.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
		{
			public void onCompletion(MediaPlayer m)
			{
				b3.callOnClick();
			}
		});
		String s="ccx";
		m2=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
		//注册按键的广播的接收器
		registerReceiver(r4=new BroadcastReceiver()
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
		//必须setSmallIcon()，否则会秒退，为啥？？？？？？？小心!!!!!!!!!!
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
						,new Intent(this,ac10.class).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP),PendingIntent.FLAG_UPDATE_CURRENT));
		//刷新通知
		m2.notify(1,n=b.build());
		b0=BitmapFactory.decodeResource(getResources(),R.drawable.i2);
		if(!new File(getFilesDir()+"/1.txt").exists())
		{
			l11=new ArrayList<>();l11.add(k=new j("默认的播放列表",l=new ArrayList<>(),l4=new ArrayList<>(),j=0));
			ObjectOutputStream o=new ObjectOutputStream(new FileOutputStream(getFilesDir()+"/1.txt"));
			o.writeObject(l11);o.writeObject(l);o.writeObject(l4);o.writeInt(j);
			o.close();
		}
		ObjectInputStream in=new ObjectInputStream(new FileInputStream(getFilesDir()+"/1.txt"));
		l11=(List<j>)in.readObject();
		Uri u=getIntent().getData();
		if(u!=null||a!=null)
		{
			if(u!=null)
			{
				a4=new ArrayList<>();a4.add(u.getPath());j=0;
			}
			else
			{
				a4=a;a=null;
			}
			l=new ArrayList<>();l4=new ArrayList<>();
			for(String t:a4)
			{
				r2.setDataSource(t);
				l4.add(i=new Integer(r2.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION))/1000);
				s=r2.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
				if(s==null)s=new File(t).getName();
				l.add(new String[]{s,r2.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST)
						,String.format("%02d:%02d",i/60,i%60),r2.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM),t});
			}
			k=l11.get(l11.size()-1);k.l=l;k.l2=l4;k.j=j;
		}
		else
		{
			l=(List<String[]>)in.readObject();l4=(List<Integer>)in.readObject();j=in.readInt();
			for(j k2:l11)if(k2.l==l){k=k2;break;}
		}
		in.close();
		l3.setAdapter(a2=new ba(this));
		onClick(b4);
		RelativeLayout.LayoutParams p4=new RelativeLayout.LayoutParams(-1,-2);p4.addRule(RelativeLayout.BELOW,2);
		r.addView(l2=new LinearLayout(this),p4);l2.setOrientation(LinearLayout.VERTICAL);l2.setVisibility(View.INVISIBLE);
		l2.addView(l10=new ListView(this));l10.setAdapter(a3=new ba2(this));
		AudioManager m=(AudioManager)getSystemService(AUDIO_SERVICE);
		m.registerMediaButtonEventReceiver(new ComponentName(this,re.class));
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		//j=1251;
		f();b6.setText(l11.size()+"");b4.setText(k.s);
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
		
		
		
		
		
		
		
		
		
		//onClick(b2);
		
		
		
		
		
		
		
		
	}catch(Exception e){e.printStackTrace();}}
	protected void onPause()
	{try{
		super.onPause();
		if(l!=null)
		{
			ObjectOutputStream o=new ObjectOutputStream(new FileOutputStream(getFilesDir()+"/1.txt"));
			o.writeObject(l11);o.writeObject(l);o.writeObject(l4);o.writeInt(j);
			o.close();
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
	static void b2()
	{
		if(b2.getText().equals("暂停"))
		{
			b2.setText("播放");
			r3.setTextViewText(R.id.b2,"播放");
			m.pause();
		}
		else
		{
			b2.setText("暂停");
			r3.setTextViewText(R.id.b2,"暂停");
			m.start();
		}
		m2.notify(1,n);
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
			b2();
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
			l3.setVisibility(View.VISIBLE);
			l8.setVisibility(View.INVISIBLE);
		}
		else if(v==b5)
		{
			b4.setBackgroundColor(0xffffffff);
			b5.setBackgroundColor(0x7700ff00);
			l3.setVisibility(View.INVISIBLE);
			l8.setVisibility(View.VISIBLE);
		}
		else if(v==b6)
		{
			l2.setVisibility(l2.getVisibility()==View.VISIBLE?View.INVISIBLE:View.VISIBLE);
		}
		else if(v==b7)
		{
			final EditText e=new EditText(this);
			new AlertDialog.Builder(this).setTitle("请输入播放列表的名称").setView(e)
					.setPositiveButton("确定",new DialogInterface.OnClickListener()
					{
						public void onClick(DialogInterface dialog,int which)
						{try{
							l11.add(0,new j(e.getText()+"",new ArrayList<String[]>(),new ArrayList<Integer>(),0));
							a3.notifyDataSetChanged();b6.setText(l11.size()+"");
							l2.setVisibility(View.INVISIBLE);
						}catch(Exception e){e.printStackTrace();}}
					}).show();
		}
		else if(v==b8)
		{
			LinearLayout l=new LinearLayout(this);l.setOrientation(LinearLayout.VERTICAL);
			l.addView(b7=new Button(this));b7.setText("+");b7.setOnClickListener(this);
			ListView l2=new ListView(this);l.addView(l2);l2.setAdapter(new ba3(this));
			d2=new AlertDialog.Builder(this).setTitle("请选择播放列表").setView(l).show();
		}
	}catch(Exception e){e.printStackTrace();}}
	void f()
	{try{
		//System.out.println("ccxccxccxccx："+j);
		String[]t=l.get(k.j=j);
		t3.interrupt();
		s.setProgress(0);
		s.setMax(l4.get(j));
		if(b2.getText().equals("播放"))b2.callOnClick();
		t2.setText(t[2]);
		m.reset();
		m.setDataSource(t[4]);
		m.prepare();
		m.start();
		a2.notifyDataSetChanged();
		t3=new Thread(new Runnable()
		{
			public void run()
			{try{
				for(;;Thread.sleep(200))
				{
					s.setProgress(m.getCurrentPosition()/1000);
				}
			}catch(Exception e)
			{
				if(!(e instanceof IllegalStateException)&&!(e instanceof InterruptedException))e.printStackTrace();
			}}
		});
		t3.start();
		r2.setDataSource(t[4]);
		d=r2.getEmbeddedPicture();
		Bitmap b=d!=null?b2o(BitmapFactory.decodeByteArray(d,0,d.length)):b0;
		i2.setImageBitmap(b);
		t4.setText("音乐名："+t[0]+"\n作者："+t[1]+"\n专辑名："+t[3]);
		r3.setImageViewBitmap(R.id.i,b);
		r3.setTextViewText(R.id.t,t[0]);
		r3.setTextViewText(R.id.t2,t[1]);
		m2.notify(1,n);
		l3.setSelection(j<3?0:j-3);
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}catch(Exception e)
	{
		if(e instanceof IndexOutOfBoundsException)xs("当前的播放列表里没音乐！");
		else e.printStackTrace();
	}}
	void xs(String s){Toast.makeText(this,s,Toast.LENGTH_SHORT).show();}
	class ba extends BaseAdapter
	{
		Context c;
		ba(Context a){c=a;}
		public int getCount(){return l.size();}
		public Object getItem(int position){return null;}
		public long getItemId(int position){return 0;}
		class i{TextView t,t2;Button b;i(TextView c,TextView d,Button e){t=c;t2=d;b=e;}}
		public View getView(final int i,View v,ViewGroup g)
		{
			LinearLayout l2=(LinearLayout)v;i b;
			if(l2==null)
			{
				l2=new LinearLayout(c);l2.setLayoutParams(new LinearLayout.LayoutParams(-1,w/7));
				l2.setTag(b=new i(new TextView(c),new TextView(c),new Button(c)));
				l2.addView(b.t,new LinearLayout.LayoutParams(0,-1,5));
				l2.addView(b.t2,new LinearLayout.LayoutParams(0,-1,3));b.t2.setGravity(Gravity.RIGHT);
				l2.addView(b.b,new LinearLayout.LayoutParams(0,-1,1));b.b.setText("✕");
			}
			else b=(i)l2.getTag();
			if(i==j)l2.setBackgroundColor(0xffff7777);
			else l2.setBackgroundColor(0xffffffff);
			String[]s=l.get(i);
			b.t.setText(i+"："+s[0]);
			b.t2.setText(s[1]);
			l2.setOnClickListener(new View.OnClickListener()
			{
				public void onClick(View v)
				{try{
					if(i==j)b2.callOnClick();
					else
					{
						j=i;
						f();
					}
				}catch(Exception e){e.printStackTrace();}}
			});
			b.b.setOnClickListener(new View.OnClickListener()
			{
				public void onClick(View v)
				{try{
					l.remove(i);l4.remove(i);notifyDataSetChanged();
					if(i==j)f();
				}catch(Exception e){e.printStackTrace();}}
			});
			return l2;
		}
	}
	class ba2 extends BaseAdapter
	{
		Context c;
		ba2(Context a){c=a;}
		public int getCount(){return l11.size();}
		public Object getItem(int position){return null;}
		public long getItemId(int position){return 0;}
		class i{Button b,b2;i(Button a,Button c){b=a;b2=c;}}
		public View getView(final int i,View v,ViewGroup g)
		{
			i a;LinearLayout l3=(LinearLayout)v;
			if(l3==null)
			{
				l3=new LinearLayout(c);
				l3.setTag(a=new i(new Button(c),new Button(c)));
				l3.addView(a.b,new LinearLayout.LayoutParams(0,-2,7));
				l3.addView(a.b2,p);a.b2.setText("✕");
			}
			else a=(i)l3.getTag();
			final j b=l11.get(i);final List<String[]>l5=b.l;
			if(l5==l)l3.setBackgroundColor(0xffff7777);
			else l3.setBackgroundColor(0xffcccccc);
			a.b.setText(b.s);
			a.b.setOnClickListener(new View.OnClickListener()
			{
				public void onClick(View v)
				{try{
					if(l5!=l)
					{
						k=b;l=l5;l4=b.l2;j=b.j;f();a2.notifyDataSetChanged();
						b4.setText(b.s);notifyDataSetChanged();
					}
					l2.setVisibility(View.INVISIBLE);
				}catch(Exception e){e.printStackTrace();}}
			});
			a.b2.setOnClickListener(new View.OnClickListener()
			{
				public void onClick(View v)
				{try{
					if(i==l11.size()-1)
					{
						xs("默认的播放列表不能删除！");
					}
					else
					{
						if(l5==l)
						{
							k=l11.get(i+1);l=k.l;l4=k.l2;j=k.j;b4.setText(k.s);f();
						}
						l11.remove(b);notifyDataSetChanged();b6.setText(l11.size()+"");
					}
				}catch(Exception e){e.printStackTrace();}}
			});
			return l3;
		}
	}
	class ba3 extends BaseAdapter
	{
		Context c;
		ba3(Context a){c=a;}
		public int getCount(){return l11.size();}
		public Object getItem(int position){return null;}
		public long getItemId(int position){return 0;}
		public View getView(final int i,View v,ViewGroup g)
		{
			Button b=(Button)v;
			if(b==null)
			{
				b=new Button(c);
			}
			final j d=l11.get(i);
			b.setText(d.s);
			b.setOnClickListener(new View.OnClickListener()
			{
				public void onClick(View v)
				{try{
					d.l.addAll(l);d.l2.addAll(l4);d2.dismiss();
				}catch(Exception e){e.printStackTrace();}}
			});
			return b;
		}
	}
}