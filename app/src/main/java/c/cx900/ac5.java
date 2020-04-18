package c.cx900;
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.*;
import android.app.*;
import android.os.*;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

//当打开app时：Create（v2=null）->Start->Resume->Pause->Resume（v2!=null）->Pause->Stop->Destroy->Create（v2=null）->Start->Resume->Pause->Resume（v2!=null）
//当按home键使app进入后台再重进时：Pause（v2!=null）->Stop->Restart->Start->Resume

//当打开app时：Create->Start->Resume
//当上面的代码加入ActivityCompat.requestPermissions()再打开app时：Create->Start->Resume->Pause->RequestPermissionsResult->Resume
//当上面的代码加入setRequestedOrientation()再打开app时：Create->Start->Resume->Pause->RequestPermissionsResult->Resume->Pause->Stop->Destroy->Create->Start->Resume->Pause->RequestPermissionsResult->Resume
//当上面的代码在AndroidManifest.xml里加入configChanges="orientation|screenSize"再打开app时：Create->Start->Resume->Pause->RequestPermissionsResult->Resume
public class ac5 extends Activity
{
	TextView t,t2,t3;SeekBar s;VideoView v2;GestureDetector g;LinearLayout l;
	int w,h,j,f,m;float f2;boolean d;WindowManager.LayoutParams p;Window w2;
	AudioManager a;MediaMetadataRetriever r2=new MediaMetadataRetriever();
	protected void onCreate(Bundle bu)
	{
		super.onCreate(bu);
		//必须<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>和requestPermissions()一起使用才行
		//请求权限不是permission，而是uses-permission，易错难发现!!!!!!!!!小心!!!!!!!!!
		ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},0);
	}
	@SuppressLint("SourceLockedOrientationActivity")
	public void onRequestPermissionsResult(int requestCode,String[]permissions,int[]grantResults)
	{
		Display d=getWindowManager().getDefaultDisplay();w=d.getWidth();h=d.getHeight();
		String f3=getIntent().getData().getPath();
		r2.setDataSource(f3);
		if(new Integer(r2.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH))<new Integer(r2.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT)))
		{
			//将屏幕设置为竖屏
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			w=h;h=d.getWidth();
		}
		//隐藏状态栏
		getWindow().setFlags(1024,1024);
		RelativeLayout r=new RelativeLayout(this);setContentView(r);
		//必须addRule(RelativeLayout.CENTER_IN_PARENT);才能使视频显示在中间，小心!!!!!!!!!!!
		RelativeLayout.LayoutParams p3=new RelativeLayout.LayoutParams(-1,-1);p3.addRule(RelativeLayout.CENTER_IN_PARENT);
		r.addView(v2=new VideoView(this),p3);
		RelativeLayout.LayoutParams p5=new RelativeLayout.LayoutParams(-1,-2);p5.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		r.addView(l=new LinearLayout(this),p5);l.setBackgroundColor(0xffffffff);
		LinearLayout.LayoutParams p2=new LinearLayout.LayoutParams(0,-2,1);
		l.addView(t=new TextView(this),p2);t.setGravity(Gravity.CENTER);
		l.addView(s=new SeekBar(this),new LinearLayout.LayoutParams(0,-2,6));
		l.addView(t2=new TextView(this),p2);t2.setGravity(Gravity.CENTER);
		RelativeLayout.LayoutParams p4=new RelativeLayout.LayoutParams(h/3,h/3);p4.addRule(RelativeLayout.CENTER_IN_PARENT);
		r.addView(t3=new TextView(this),p4);t3.setBackgroundColor(0xddffffff);t3.setGravity(Gravity.CENTER);
		w2=getWindow();p=w2.getAttributes();p.screenBrightness=0.75f;w2.setAttributes(p);
		a=(AudioManager)getSystemService(Service.AUDIO_SERVICE);m=a.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		//竖屏时必须加v2.setBackgroundColor(0);，否则用setVisibility(View.INVISIBLE)后再用setVisibility(View.VISIBLE)会没效果，为啥？？？？？？？？？？易错超难发现!!!!!!!!!!!!!小心!!!!!!!!!!!!!
		v2.setBackgroundColor(0);l.setVisibility(View.INVISIBLE);t3.setVisibility(View.INVISIBLE);
		
		
		
		
		
		
		
		
		
		
		
		
		
		v2.setVideoPath(f3);
		v2.start();
		v2.setOnPreparedListener(new MediaPlayer.OnPreparedListener()
		{
			public void onPrepared(MediaPlayer m)
			{
				//必须等视频加载完成后才能获取视频时长，小心!!!!!!!!!!!
				int i=v2.getDuration()/1000;
				s.setMax(i);
				t2.setText(t(i));
			}
		});
		v2.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
		{
			public void onCompletion(MediaPlayer m)
			{
				s.setProgress(s.getMax());
				v2.start();
			}
		});
		s.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
		{
			public void onProgressChanged(SeekBar seekBar,int i,boolean b)
			{
				t.setText(t(i));
			}
			public void onStartTrackingTouch(SeekBar seekBar){}
			public void onStopTrackingTouch(SeekBar s)
			{
				v2.seekTo(s.getProgress()*1000);
			}
		});
		new Thread(new Runnable()
		{
			public void run()
			{try{
				for(;;Thread.sleep(200))
				{
					s.setProgress(v2.getCurrentPosition()/1000);
				}
			}catch(Exception e){e.printStackTrace();}}
		}).start();
		g=new GestureDetector(this,new GestureDetector.SimpleOnGestureListener()
		{
			public boolean onScroll(MotionEvent e,MotionEvent e2,float x,float y)
			{
				//System.out.println("ccxccx："+e.getX()+" "+e2.getX()+" "+x+" "+y);
				//用f是为了保证每次滑动都只执行1个功能，小心!!!!!!!!!!!!!
				if(f==0)
				{
					t3.setVisibility(View.VISIBLE);
					//当快进或快退时
					if(Math.abs(x)>Math.abs(y))
					{
						f=1;f2=s.getProgress();
					}
					else
					{
						//当调整亮度时
						if(e.getX()<w/2)
						{
							f=2;f2=p.screenBrightness;
						}
						//当调整声音大小时
						else
						{
							f=3;f2=a.getStreamVolume(AudioManager.STREAM_MUSIC);
						}
					}
				}
				//当快进或快退时
				else if(f==1)
				{
					f2-=150*x/w;f2=f2<0?0:f2>s.getMax()?s.getMax():f2;
					t3.setText("当前位置："+t(s.getProgress())+"\n目标位置："+t((int)f2));
				}
				//当调整亮度时
				else if(f==2)
				{
					f2+=1.5f*y/h;p.screenBrightness=f2=f2<0?0:f2>1?1:f2;
					w2.setAttributes(p);
					t3.setText("亮度："+(int)(f2*100)+"%");
				}
				//当调整声音大小时
				else if(f==3)
				{
					f2+=60*y/h;f2=f2<0?0:f2>m?m:f2;
					int i=(int)f2;
					a.setStreamVolume(AudioManager.STREAM_MUSIC,i,AudioManager.FLAG_PLAY_SOUND);
					t3.setText("音量："+i+"/"+m);
				}
				return true;
			}
			//当双击（300ms内连续单击2次）时自动调用
			public boolean onDoubleTap(MotionEvent e)
			{
				if(v2.isPlaying())v2.pause();else v2.start();
				return true;
			}
			//当单击后的300ms内没有再次单击时自动调用
			public boolean onSingleTapConfirmed(MotionEvent e)
			{
				l.setVisibility(l.getVisibility()==View.VISIBLE?View.INVISIBLE:View.VISIBLE);
				return true;
			}
		});
	}
	String t(int i){return String.format("%02d:%02d",i/60,i%60);}
	public boolean onTouchEvent(MotionEvent e)
	{
		int i=e.getAction();
		if(i==MotionEvent.ACTION_DOWN)f=0;
		else if(i==MotionEvent.ACTION_UP)
		{
			t3.setVisibility(View.INVISIBLE);
			if(f==1)
			{
				v2.seekTo((int)(f2*1000));
			}
		}
		return g.onTouchEvent(e);
	}
	protected void onPause()
	{
		super.onPause();
		if(v2!=null)
		{
			j=v2.getCurrentPosition();d=v2.isPlaying();
		}
	}
	protected void onRestart()
	{
		super.onRestart();
		v2.seekTo(j);if(d)v2.start();
	}
}