package c.cx900;
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.*;
import android.app.*;
import android.os.*;

import androidx.core.app.ActivityCompat;

public class ac5 extends Activity
{
	TextView t,t2;SeekBar s;VideoView v2;GestureDetector d;LinearLayout l;
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
		r.addView(v2=new VideoView(this),-1,-1);
		RelativeLayout.LayoutParams p=new RelativeLayout.LayoutParams(-1,-2);p.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		l=new LinearLayout(this);r.addView(l,p);
		LinearLayout.LayoutParams p2=new LinearLayout.LayoutParams(0,-2,1);
		
		l.addView(t=new TextView(this),p2);
		l.addView(s=new SeekBar(this),new LinearLayout.LayoutParams(0,-2,6));
		l.addView(t=new TextView(this),p2);
		l.setVisibility(View.INVISIBLE);
		
		v2.setVideoPath(Environment.getExternalStorageDirectory().getPath() + "/0/0.mp4");
		v2.start();
		d=new GestureDetector(this,new GestureDetector.SimpleOnGestureListener()
		{
			public boolean onSingleTapUp(MotionEvent e)
			{
				
				return super.onSingleTapUp(e);
			}
			@Override
			public void onLongPress(MotionEvent e) {
				super.onLongPress(e);
			}
			
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
			
			@Override
			public boolean onDoubleTapEvent(MotionEvent e) {
				return super.onDoubleTapEvent(e);
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
		return d.onTouchEvent(e);
	}
}
abstract class On2ClickListener implements View.OnClickListener
{
	long l,l2;
	public void onClick(View v)
	{
		l2=System.currentTimeMillis();
		if(l2-l<300)on2Click(v);else on1Click(v);
		l=l2;
	}
	void on1Click(View v){}
	abstract void on2Click(View v);
}
