package c.cx900;

import android.app.Activity;
import android.app.Service;
import android.content.ContentResolver;
import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import c.cx900.R;

public class sy4 extends Activity implements VideoGestureRelativeLayout.VideoGestureListener
{
	private VideoGestureRelativeLayout r;
	private ShowChangeLayout scl;
	private AudioManager a;
	private int maxVolume = 0;
	private int oldVolume = 0;
	private int newProgress = 0, oldProgress = 0;
	private BrightnessHelper b;
	private float brightness = 1;
	private Window mWindow;
	private WindowManager.LayoutParams mLayoutParams;
	int w;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		w=getWindowManager().getDefaultDisplay().getHeight();
		setContentView(r=new VideoGestureRelativeLayout(this));
		
		r.setVideoGestureListener(this);
		RelativeLayout.LayoutParams p=new RelativeLayout.LayoutParams(w/3,w/3);p.addRule(RelativeLayout.CENTER_IN_PARENT);
		r.addView(scl=new ShowChangeLayout(this),p);
		
		//初始化获取音量属性
		a=(AudioManager)getSystemService(Service.AUDIO_SERVICE);
		maxVolume=a.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		//初始化亮度调节
		b=new BrightnessHelper(this);
		//下面这是设置当前APP亮度的方法配置
		mWindow = getWindow();
		mLayoutParams = mWindow.getAttributes();
		brightness = mLayoutParams.screenBrightness;
	}
	
	
	
	@Override
	public void onDown(MotionEvent e)
	{
		//每次按下的时候更新当前亮度和音量，还有进度
		oldProgress = newProgress;
		oldVolume = a.getStreamVolume(AudioManager.STREAM_MUSIC);
		brightness = mLayoutParams.screenBrightness;
		System.out.println("ccxccx："+brightness+" "+b.getBrightness());
		if (brightness == -1){
			//一开始是默认亮度的时候，获取系统亮度，计算比例值
			brightness = b.getBrightness() / 255f;
		}
	}
	
	@Override
	public void onEndFF_REW(MotionEvent e) {
		makeToast("设置进度为" + newProgress);
	}
	
	@Override
	public void onVolumeGesture(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY)
	{
		
		
		int value = r.getHeight()/maxVolume ;
		int newVolume = (int) ((e1.getY() - e2.getY())/value + oldVolume);
		
		a.setStreamVolume(AudioManager.STREAM_MUSIC,newVolume,AudioManager.FLAG_PLAY_SOUND);
		
		
		
		//要强行转Float类型才能算出小数点，不然结果一直为0
		int volumeProgress = (int) (newVolume/Float.valueOf(maxVolume) *100);
		if (volumeProgress >= 50){
			//scl.setImageResource(R.drawable.volume_higher_w);
		}else if (volumeProgress > 0){
			//scl.setImageResource(R.drawable.volume_lower_w);
		}else {
			//scl.setImageResource(R.drawable.volume_off_w);
		}
		scl.setProgress(volumeProgress);
		scl.show();
	}
	
	@Override
	public void onBrightnessGesture(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
		
		//下面这是设置当前APP亮度的方法
		
		float newBrightness = (e1.getY() - e2.getY()) / r.getHeight() ;
		newBrightness += brightness;
		
		if (newBrightness < 0){
			newBrightness = 0;
		}else if (newBrightness > 1){
			newBrightness = 1;
		}
		mLayoutParams.screenBrightness = newBrightness;
		mWindow.setAttributes(mLayoutParams);
		scl.setProgress((int) (newBrightness * 100));
		//scl.setImageResource(R.drawable.brightness_w);
		scl.show();
	}
	
	
	
	@Override
	public void onFF_REWGesture(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
		float offset = e2.getX() - e1.getX();
		
		//根据移动的正负决定快进还是快退
		if (offset > 0) {
			scl.setImageResource(android.R.drawable.ic_media_ff);
			newProgress = (int) (oldProgress + offset/r.getWidth() * 100);
			if (newProgress > 100){
				newProgress = 100;
			}
		}else {
			scl.setImageResource(android.R.drawable.ic_media_rew);
			newProgress = (int) (oldProgress + offset/r.getWidth() * 100);
			if (newProgress < 0){
				newProgress = 0;
			}
		}
		
		scl.setProgress(newProgress);
		scl.show();
	}
	
	@Override
	public void onSingleTapGesture(MotionEvent e) {
		
		makeToast("SingleTap");
	}
	
	@Override
	public void onDoubleTapGesture(MotionEvent e) {
		
		makeToast("DoubleTap");
	}
	
	private void makeToast(String str){
		Toast.makeText(this,str,Toast.LENGTH_SHORT).show();
	}
}



class BrightnessHelper {
	private ContentResolver resolver;
	private int maxBrightness = 255;
	
	public BrightnessHelper(Context context){
		resolver = context.getContentResolver();
	}
	
	/*
	 * 调整亮度范围
	 */
	private int adjustBrightnessNumber(int brightness){
		if (brightness < 0) {
			brightness = 0;
		} else if (brightness > 255) {
			brightness = 255;
		}
		return brightness;
	}
	
	
	
	/*
	 * 获取系统亮度
	 */
	public int getBrightness(){
		return Settings.System.getInt(resolver, Settings.System.SCREEN_BRIGHTNESS, 255);
	}
	
	/*
	 * 设置系统亮度，如果有设置了自动调节，请先调用offAutoBrightness()方法关闭自动调节，否则会设置失败
	 */
	public void setSystemBrightness(int newBrightness){
		Settings.System.putInt(resolver, Settings.System.SCREEN_BRIGHTNESS
				,adjustBrightnessNumber(newBrightness));
	}
	
	public int getMaxBrightness() {
		return maxBrightness;
	}
	
	//设置当前APP的亮度
	public void setAppBrightness(float brightnessPercent, Activity activity){
		Window window = activity.getWindow();
		WindowManager.LayoutParams layoutParams = window.getAttributes();
		layoutParams.screenBrightness = brightnessPercent;
		window.setAttributes(layoutParams);
	}
}





class ShowChangeLayout extends RelativeLayout
{
	private ImageView iv_center;
	private ProgressBar pb;
	private HideRunnable mHideRunnable;
	private int duration = 1000;
	
	ShowChangeLayout(Context c)
	{
		super(c);
		addView(iv_center=new ImageView(c));
		//LayoutInflater.from(c).inflate(R.layout.show_change_layout,this);
		
		addView(pb = new ProgressBar(c));
		
		mHideRunnable = new HideRunnable();
		setVisibility(GONE);
	}
	
	
	
	
	//显示
	public void show(){
		setVisibility(VISIBLE);
		removeCallbacks(mHideRunnable);
		postDelayed(mHideRunnable,duration);
	}
	
	//设置进度
	public void setProgress(int progress){
		pb.setProgress(progress);
		
	}
	
	
	
	//设置显示图片
	public void setImageResource(int resource){
		iv_center.setImageResource(resource);
	}
	
	//隐藏自己的Runnable
	private class HideRunnable implements Runnable{
		@Override
		public void run() {
			ShowChangeLayout.this.setVisibility(GONE);
		}
	}
}









class VideoGestureRelativeLayout extends RelativeLayout
{
	private static final String TAG = "gesturetest";
	private static final int NONE = 0, VOLUME = 1, BRIGHTNESS = 2, FF_REW = 3;
	private
	@ScrollMode
	int mScrollMode = NONE;
	
	@IntDef({NONE, VOLUME, BRIGHTNESS, FF_REW})
	@Retention(RetentionPolicy.SOURCE)
	private @interface ScrollMode {
	}
	
	private VideoPlayerOnGestureListener mOnGestureListener;
	private GestureDetector mGestureDetector;
	private VideoGestureListener mVideoGestureListener;
	//横向偏移检测，让快进快退不那么敏感
	private int offsetX = 1;
	private boolean hasFF_REW = false;
	
	public VideoGestureRelativeLayout(Context context) {
		super(context);
		init(context);
	}
	
	public VideoGestureRelativeLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}
	
	private void init(Context context) {
		mOnGestureListener = new VideoPlayerOnGestureListener(this);
		mGestureDetector = new GestureDetector(context, mOnGestureListener);
		//取消长按，不然会影响滑动
		mGestureDetector.setIsLongpressEnabled(false);
		setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v,MotionEvent event) {
//                Log.d(TAG, "onTouch: event:" + event.toString());
				if (event.getAction() == MotionEvent.ACTION_UP) {
					if (hasFF_REW) {
						if (mVideoGestureListener != null) {
							mVideoGestureListener.onEndFF_REW(event);
						}
						hasFF_REW = false;
					}
				}
				//监听触摸事件
				return mGestureDetector.onTouchEvent(event);
			}
		});

//        setOnGenericMotionListener(new OnGenericMotionListener() {
//            @Override
//            public boolean onGenericMotion(View v, MotionEvent event) {
//                Log.d(TAG, "onGenericMotion: " + event.toString());
//                //监听鼠标点击事件
//                return mGestureDetector.onGenericMotionEvent(event);
//            }
//        });
	}
	
	public class VideoPlayerOnGestureListener extends GestureDetector.SimpleOnGestureListener {
		
		private VideoGestureRelativeLayout videoGestureRelativeLayout;
		
		public VideoPlayerOnGestureListener(VideoGestureRelativeLayout videoGestureRelativeLayout) {
			this.videoGestureRelativeLayout = videoGestureRelativeLayout;
		}
		
		@Override
		public boolean onDown(MotionEvent e) {
			Log.d(TAG, "onDown: ");
			hasFF_REW = false;
			//每次按下都重置为NONE
			mScrollMode = NONE;
			if (mVideoGestureListener != null) {
				mVideoGestureListener.onDown(e);
			}
			return true;
		}
		
		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
			Log.d(TAG, "onScroll: e1:" + e1.getX() + "," + e1.getY());
			Log.d(TAG, "onScroll: e2:" + e2.getX() + "," + e2.getY());
			Log.d(TAG, "onScroll: X:" + distanceX + "  Y:" + distanceY);
			switch (mScrollMode) {
				case NONE:
					Log.d(TAG, "NONE: ");
					//offset是让快进快退不要那么敏感的值
					if (Math.abs(distanceX) - Math.abs(distanceY) > offsetX) {
						mScrollMode = FF_REW;
					} else {
						if (e1.getX() < getWidth() / 2) {
							mScrollMode = BRIGHTNESS;
						} else {
							mScrollMode = VOLUME;
						}
					}
					break;
				case VOLUME:
					if (mVideoGestureListener != null) {
						mVideoGestureListener.onVolumeGesture(e1, e2, distanceX, distanceY);
					}
					Log.d(TAG, "VOLUME: ");
					break;
				case BRIGHTNESS:
					if (mVideoGestureListener != null) {
						mVideoGestureListener.onBrightnessGesture(e1, e2, distanceX, distanceY);
					}
					Log.d(TAG, "BRIGHTNESS: ");
					break;
				case FF_REW:
					if (mVideoGestureListener != null) {
						mVideoGestureListener.onFF_REWGesture(e1, e2, distanceX, distanceY);
					}
					hasFF_REW = true;
					Log.d(TAG, "FF_REW: ");
					break;
			}
			return true;
		}
		
		
		@Override
		public boolean onContextClick(MotionEvent e) {
			Log.d(TAG, "onContextClick: ");
			return true;
		}
		
		@Override
		public boolean onDoubleTap(MotionEvent e) {
			Log.d(TAG, "onDoubleTap: ");
			if (mVideoGestureListener != null) {
				mVideoGestureListener.onDoubleTapGesture(e);
			}
			return super.onDoubleTap(e);
		}
		
		@Override
		public void onLongPress(MotionEvent e) {
			Log.d(TAG, "onLongPress: ");
			super.onLongPress(e);
		}
		
		@Override
		public boolean onDoubleTapEvent(MotionEvent e) {
			Log.d(TAG, "onDoubleTapEvent: ");
			return super.onDoubleTapEvent(e);
		}
		
		@Override
		public boolean onSingleTapUp(MotionEvent e) {
			Log.d(TAG, "onSingleTapUp: ");
			return super.onSingleTapUp(e);
		}
		
		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
			Log.d(TAG, "onFling: ");
			return super.onFling(e1, e2, velocityX, velocityY);
		}
		
		
		@Override
		public void onShowPress(MotionEvent e) {
			Log.d(TAG, "onShowPress: ");
			super.onShowPress(e);
		}
		
		
		@Override
		public boolean onSingleTapConfirmed(MotionEvent e) {
			Log.d(TAG, "onSingleTapConfirmed: ");
			if (mVideoGestureListener != null) {
				mVideoGestureListener.onSingleTapGesture(e);
			}
			return super.onSingleTapConfirmed(e);
		}
	}
	
	public void setVideoGestureListener(VideoGestureListener videoGestureListener) {
		mVideoGestureListener = videoGestureListener;
	}
	
	/**
	 * 用于提供给外部实现的视频手势处理接口
	 */
	
	public interface VideoGestureListener {
		//亮度手势，手指在Layout左半部上下滑动时候调用
		public void onBrightnessGesture(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY);
		
		//音量手势，手指在Layout右半部上下滑动时候调用
		public void onVolumeGesture(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY);
		
		//快进快退手势，手指在Layout左右滑动的时候调用
		public void onFF_REWGesture(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY);
		
		//单击手势，确认是单击的时候调用
		public void onSingleTapGesture(MotionEvent e);
		
		//双击手势，确认是双击的时候调用
		public void onDoubleTapGesture(MotionEvent e);
		
		//按下手势，第一根手指按下时候调用
		public void onDown(MotionEvent e);
		
		//快进快退执行后的松开时候调用
		public void onEndFF_REW(MotionEvent e);
	}
	
	
}

