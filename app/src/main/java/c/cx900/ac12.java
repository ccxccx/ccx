package c.cx900;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.MediaRecorder;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.text.format.DateFormat;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.*;
import android.app.*;
import android.os.*;

import java.io.File;
import java.util.Calendar;

public class ac12 extends Activity implements View.OnClickListener
{
	Button b,b2;String f;MediaProjection p;MediaRecorder r;VirtualDisplay d;int w,h,d2;
	protected void onCreate(Bundle bu)
	{
		super.onCreate(bu);
		LinearLayout l=new LinearLayout(this);setContentView(l);l.setOrientation(LinearLayout.VERTICAL);
		l.addView(b=new Button(this));b.setText("开始录屏");b.setOnClickListener(this);
		l.addView(b2=new Button(this));b2.setText("查看录屏");b2.setOnClickListener(this);
		new File(f=getExternalFilesDir("")+"/录屏/").mkdir();
		DisplayMetrics m=new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(m);
		w=m.widthPixels;h=m.heightPixels;d2=m.densityDpi;
		
		
		
		
		
	}
	public void onClick(View v)
	{
		if(v==b)
		{
			if("开始录屏".equals(b.getText()+""))
			{
				b.setText("停止录屏");b2.setVisibility(View.INVISIBLE);
				startActivityForResult(((MediaProjectionManager)getSystemService(MEDIA_PROJECTION_SERVICE)).createScreenCaptureIntent(),0);
			}
			else
			{
				b.setText("开始录屏");b2.setVisibility(View.VISIBLE);
				d.release();p.stop();r.reset();
			}
		}
		else if(v==b2)
		{
			ac2.f=f;startActivity(new Intent(this,ac2.class));
		}
	}
	protected void onActivityResult(int requestCode,int resultCode,Intent data)
	{try{
		p=((MediaProjectionManager)getSystemService(MEDIA_PROJECTION_SERVICE)).getMediaProjection(resultCode,data);
		r=new MediaRecorder();
		r.setAudioSource(MediaRecorder.AudioSource.MIC);
		r.setVideoSource(MediaRecorder.VideoSource.SURFACE);
		r.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
		r.setOutputFile(f+DateFormat.format("yyyy.MM.dd HH.mm.ss",Calendar.getInstance())+".mp4");
		r.setVideoSize(w,h);
		r.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
		r.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
		r.setVideoEncodingBitRate(5*w*h);
		r.setVideoFrameRate(30);
		r.prepare();
		//必须先r.prepare()后再p.createVirtualDisplay()，否则会报错：fail to get surface
		//createVirtualDisplay()的第1个参数不能为""或null，为啥？？？？？？？？小心!!!!!!!!!!!!!
		d=p.createVirtualDisplay("1",w,h,d2,DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR,r.getSurface(),null,null);
		r.start();
	}catch(Exception e){e.printStackTrace();}}
}