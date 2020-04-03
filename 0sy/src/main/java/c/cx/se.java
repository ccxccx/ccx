package c.cx;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.MediaRecorder;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Environment;
import android.os.IBinder;
import android.text.format.DateFormat;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class se extends Service
{
	
	private int mScreenWidth;
	private int mScreenHeight;
	private int mScreenDensity;
	private int mResultCode;
	private Intent mResultData;
	/** 是否为标清视频 */
	private boolean isVideoSd;
	
	private MediaProjection p;
	private MediaRecorder r;
	private VirtualDisplay mVirtualDisplay;
	
	
	
	
	String f;
	public int onStartCommand(Intent intent, int flags, int startId)
	{try{
		new File(f=getExternalFilesDir("")+"/录屏/").mkdir();
		
		
		
		mResultCode = intent.getIntExtra("code", -1);
		mResultData = intent.getParcelableExtra("data");
		mScreenWidth = intent.getIntExtra("width", 720);
		mScreenHeight = intent.getIntExtra("height", 1280);
		mScreenDensity = intent.getIntExtra("density", 1);
		isVideoSd = intent.getBooleanExtra("quality", true);
		
		
		
		
	}catch(Exception e){e.printStackTrace();}return Service.START_NOT_STICKY;}
	
	
	
	
	
	
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if(mVirtualDisplay != null) {
			mVirtualDisplay.release();
			mVirtualDisplay = null;
		}
		if(r != null) {
			r.setOnErrorListener(null);
			p.stop();
			r.reset();
		}
		if(p != null) {
			p.stop();
			p = null;
		}
	}
	
	
	public IBinder onBind(Intent intent){return null;}
}
