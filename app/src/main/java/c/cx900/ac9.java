package c.cx900;
import android.media.MediaRecorder;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.*;
import android.app.*;
import android.os.*;

import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;

public class ac9 extends Activity implements View.OnClickListener
{
	Button b;ListView l2;MediaRecorder m;String f;
	protected void onCreate(Bundle bu)
	{
		super.onCreate(bu);
		LinearLayout l=new LinearLayout(this);setContentView(l);l.setOrientation(LinearLayout.VERTICAL);
		l.addView(b=new Button(this));b.setText("开始录音");b.setOnClickListener(this);
		l.addView(l2=new ListView(this));
		f=getExternalFilesDir("")+"/";
		
		
		
		

		
		
		
		
	}
	public void onClick(View v)
	{try{
		if(v==b)
		{
			if("开始录音".equals(b.getText()+""))
			{
				b.setText("停止录音");
				m=new MediaRecorder();
				//设置音源为麦克风
				m.setAudioSource(MediaRecorder.AudioSource.MIC);
				//设置输出文件的格式为MPEG_4
				m.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
				//设置音频文件的编码为AAC
				m.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
				m.setOutputFile(f+DateFormat.format("yyyy.MM.dd HH.mm.ss",Calendar.getInstance())+".mp3");
				m.prepare();
				m.start();
			}
			else
			{
				b.setText("开始录音");
				m.stop();
				m.release();
			}
		}
	}catch(Exception e){e.printStackTrace();}}
}