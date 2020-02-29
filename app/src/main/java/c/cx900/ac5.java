package c.cx900;
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.*;
import android.app.*;
import android.os.*;

import androidx.core.app.ActivityCompat;

public class ac5 extends Activity
{
	TextView t,t2;SeekBar s;VideoView v2;GestureDetector g;LinearLayout l;int j;boolean d;
	//当打开app时：Create（v2=null）->Start->Resume->Pause->Resume（v2!=null）->Pause->Stop->Destroy->Create（v2=null）->Start->Resume->Pause->Resume（v2!=null）
	//当按home键使app进入后台再重进时：Pause（v2!=null）->Stop->Restart->Start->Resume
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
		//隐藏状态栏
		getWindow().setFlags(1024,1024);
		//设置界面方向为传感器控制且为横屏
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
		RelativeLayout r=new RelativeLayout(this);setContentView(r);
		//必须addRule(RelativeLayout.CENTER_IN_PARENT);才能使视频显示在中间，小心!!!!!!!!!!!
		RelativeLayout.LayoutParams p3=new RelativeLayout.LayoutParams(-1,-1);p3.addRule(RelativeLayout.CENTER_IN_PARENT);
		r.addView(v2=new VideoView(this),p3);
		RelativeLayout.LayoutParams p=new RelativeLayout.LayoutParams(-1,-2);p.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		l=new LinearLayout(this);r.addView(l,p);l.setBackgroundColor(0xffffffff);
		LinearLayout.LayoutParams p2=new LinearLayout.LayoutParams(0,-2,1);
		l.addView(t=new TextView(this),p2);t.setGravity(Gravity.CENTER);
		l.addView(s=new SeekBar(this),new LinearLayout.LayoutParams(0,-2,6));
		l.addView(t2=new TextView(this),p2);t2.setGravity(Gravity.CENTER);
		l.setVisibility(View.INVISIBLE);
		
		
		
		
		
		
		
		
		
		
		
		
		v2.setVideoPath(Environment.getExternalStorageDirectory().getPath() + "/0/0.mp4");
		v2.start();
		v2.setOnPreparedListener(new MediaPlayer.OnPreparedListener()
		{
			public void onPrepared(MediaPlayer m)
			{
				//必须等视频加载完成后才能获取视频时长，小心!!!!!!!!!!!
				int i=v2.getDuration()/1000;
				s.setMax(i);
				t2.setText(String.format("%02d:%02d",i/60,i%60));
			}
		});
		s.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
		{
			public void onProgressChanged(SeekBar seekBar,int i,boolean b)
			{
				t.setText(String.format("%02d:%02d",i/60,i%60));
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
			
			@Override
			public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
				return super.onScroll(e1, e2, distanceX, distanceY);
			}
			
			@Override
			public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
				return super.onFling(e1, e2, velocityX, velocityY);
			}
			
			@Override
			public void onShowPress(MotionEvent e) {
				super.onShowPress(e);
			}
			
			@Override
			public boolean onDown(MotionEvent e) {
				return super.onDown(e);
			}
			
			//当双击（300ms内连续单击2次）时自动调用
			public boolean onDoubleTap(MotionEvent e)
			{
				//System.out.println("ccxccx：双击");
				if(v2.isPlaying())v2.pause();else v2.start();
				return false;
			}
			
			
			//当单击后的300ms内没有再次单击时自动调用
			public boolean onSingleTapConfirmed(MotionEvent e)
			{
				//System.out.println("ccxccx：单击");
				l.setVisibility(l.getVisibility()==View.VISIBLE?View.INVISIBLE:View.VISIBLE);
				return false;
			}
			
			@Override
			public boolean onContextClick(MotionEvent e) {
				return super.onContextClick(e);
			}
		});
		
	}
	public boolean onTouchEvent(MotionEvent e)
	{
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
