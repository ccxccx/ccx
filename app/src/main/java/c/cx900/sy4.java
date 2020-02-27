package c.cx900;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;




public class sy4 extends Activity implements SurfaceHolder.Callback,
		MediaPlayer.OnPreparedListener,
		MediaPlayer.OnCompletionListener,
		MediaPlayer.OnErrorListener,
		MediaPlayer.OnInfoListener, View.OnClickListener,
		MediaPlayer.OnSeekCompleteListener,
		MediaPlayer.OnVideoSizeChangedListener,
		SeekBar.OnSeekBarChangeListener
	
{
	
	private ImageView playOrPauseIv;
	private SurfaceView videoSuf;
	private MediaPlayer mPlayer;
	private SeekBar mSeekBar;
	private String path;
	private RelativeLayout r;
	private LinearLayout l;
	private TextView startTime, endTime;
	private boolean isShow = false;
	
	public static final int UPDATE_TIME = 0x0001;
	public static final int HIDE_CONTROL = 0x0002;
	
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case UPDATE_TIME:
					updateTime();
					mHandler.sendEmptyMessageDelayed(UPDATE_TIME, 500);
					break;
				case HIDE_CONTROL:
					hideControl();
					break;
			}
		}
	};
	@SuppressLint("SourceLockedOrientationActivity")
	protected void onCreate(Bundle savedInstanceState)
	{try{
		super.onCreate(savedInstanceState);
		//隐藏状态栏
		getWindow().setFlags(1024,1024);
		//设置界面方向为传感器控制且为横屏
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
		r=new RelativeLayout(this);setContentView(r);
		videoSuf=new SurfaceView(this);r.addView(videoSuf);
		videoSuf.setZOrderOnTop(false);
		videoSuf.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		videoSuf.getHolder().addCallback(this);
		
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
		
		
		
		
		
		
		mPlayer = new MediaPlayer();
		mPlayer.setOnCompletionListener(this);
		mPlayer.setOnErrorListener(this);
		mPlayer.setOnInfoListener(this);
		mPlayer.setOnPreparedListener(this);
		mPlayer.setOnSeekCompleteListener(this);
		mPlayer.setOnVideoSizeChangedListener(this);
			//使用手机本地视频
			mPlayer.setDataSource(path);
		
			
		
		playOrPauseIv.setOnClickListener(this);
		r.setOnClickListener(this);
		mSeekBar.setOnSeekBarChangeListener(this);
	}catch(Exception e){e.printStackTrace();}}
	
	
	
	
	

	
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		mPlayer.setDisplay(holder);
		mPlayer.prepareAsync();
	}
	
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
	
	}
	
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
	
	}
	@Override
	public void onPrepared(MediaPlayer mp) {
		//startTime.setText(FormatTimeUtil.formatLongToTimeStr(mp.getCurrentPosition()));
		//endTime.setText(FormatTimeUtil.formatLongToTimeStr(mp.getDuration()));
		mSeekBar.setMax(mp.getDuration());
		mSeekBar.setProgress(mp.getCurrentPosition());
	}
	@Override
	public void onCompletion(MediaPlayer mp) {
	
	}
	@Override
	public boolean onError(MediaPlayer mp, int what, int extra) {
		return false;
	}
	
	@Override
	public boolean onInfo(MediaPlayer mp, int what, int extra) {
		return false;
	}
	
	@Override
	public void onSeekComplete(MediaPlayer mp) {
		//TODO
	}
	
	@Override
	public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
	
	}
	@Override
	public void onClick(View v)
	{
		if(v==playOrPauseIv){
			if(mPlayer==null){
				return;
			}
			
			if(mPlayer.isPlaying()){
				mPlayer.pause();
				mHandler.removeMessages(UPDATE_TIME);
				mHandler.removeMessages(HIDE_CONTROL);
				playOrPauseIv.setVisibility(View.VISIBLE);
				playOrPauseIv.setImageResource(android.R.drawable.ic_media_play);
			}else{
				mPlayer.start();
				mHandler.sendEmptyMessageDelayed(UPDATE_TIME,500);
				mHandler.sendEmptyMessageDelayed(HIDE_CONTROL,5000);
				playOrPauseIv.setVisibility(View.INVISIBLE);
				playOrPauseIv.setImageResource(android.R.drawable.ic_media_pause);
			}
		}
		else if(v==r)
		{
			
			if (isShow) {
				onClick(playOrPauseIv);
			}
			isShow = true;
			mHandler.removeMessages(HIDE_CONTROL);
			mHandler.sendEmptyMessage(UPDATE_TIME);
			mHandler.sendEmptyMessageDelayed(HIDE_CONTROL, 5000);
			l.animate().setDuration(300).translationY(0);
		}
	}
	/**
	 * 更新播放时间
	 */
	private void updateTime() {
		
		//startTime.setText(FormatTimeUtil.formatLongToTimeStr(mPlayer.getCurrentPosition()));
		mSeekBar.setProgress(mPlayer.getCurrentPosition());
	}
	
	/**
	 * 隐藏进度条
	 */
	private void hideControl() {
		isShow = false;
		mHandler.removeMessages(UPDATE_TIME);
		l.animate().setDuration(300).translationY(l.getHeight());
	}
	
	
	
	
	
	//OnSeekBarChangeListener
	@Override
	public void onProgressChanged(SeekBar seekBar,int progress,boolean b) {
		if(mPlayer != null && b){
			mPlayer.seekTo(progress);
		}
	}
	
	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
	}
	
	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
	
	}
	
	
}
