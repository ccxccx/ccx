package c.cx900;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;


public class sy5 extends Activity implements View.OnClickListener
{
	
	private ImageView playOrPauseIv;
	private VideoView v;
	private MediaPlayer mPlayer;
	private SeekBar mSeekBar;
	private String path;
	private RelativeLayout r;
	private LinearLayout l;
	private TextView startTime, endTime;
	private boolean isShow = false;
	
	public static final int UPDATE_TIME = 0x0001;
	public static final int HIDE_CONTROL = 0x0002;
	
	
	@SuppressLint("SourceLockedOrientationActivity")
	protected void onCreate(Bundle savedInstanceState)
	{try{
		super.onCreate(savedInstanceState);
		//隐藏状态栏
		getWindow().setFlags(1024,1024);
		//设置界面方向为传感器控制且为横屏
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
		r=new RelativeLayout(this);setContentView(r);
		v=new VideoView(this);r.addView(v);
		
		
		playOrPauseIv = new ImageView(this);r.addView(playOrPauseIv,-1,-1);
		playOrPauseIv.setImageResource(android.R.drawable.ic_media_play);
		
		l=new LinearLayout(this);
		RelativeLayout.LayoutParams p=new RelativeLayout.LayoutParams(-1,-2);p.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		r.addView(l,p);
		LinearLayout.LayoutParams p2=new LinearLayout.LayoutParams(0,-2,1);
		startTime = new TextView(this);l.addView(startTime,p2);
		mSeekBar =new SeekBar(this);l.addView(mSeekBar,new LinearLayout.LayoutParams(0,-2,6));
		endTime =new TextView(this);l.addView(endTime,p2);
		
		
		
		
		
		
		
		path = Environment.getExternalStorageDirectory().getPath() + "/0/0.mp4";//这里写上你的视频地址
		
		
		
		
		
		
//		mPlayer = new MediaPlayer();
//		mPlayer.setOnCompletionListener(this);
//		mPlayer.setOnErrorListener(this);
//		mPlayer.setOnInfoListener(this);
//		mPlayer.setOnPreparedListener(this);
//		mPlayer.setOnSeekCompleteListener(this);
//		mPlayer.setOnVideoSizeChangedListener(this);
		//使用手机本地视频
		v.setVideoPath(path);
		
		v.start();
		v.getHolder().setFixedSize(v.getWidth(),v.getHeight());
		playOrPauseIv.setOnClickListener(this);
		r.setOnClickListener(this);
//		mSeekBar.setOnSeekBarChangeListener(this);
	}catch(Exception e){e.printStackTrace();}}
	
	
	public void onClick(View v)
	{
	
	}
}
