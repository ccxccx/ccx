package c.cx900;
import android.content.Intent;
import android.media.MediaRecorder;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.*;
import android.app.*;
import android.os.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ac9 extends Activity implements View.OnClickListener
{
	Button b,b2;MediaRecorder m;String f,f2;
	protected void onCreate(Bundle bu)
	{
		super.onCreate(bu);
		LinearLayout l=new LinearLayout(this);setContentView(l);l.setOrientation(LinearLayout.VERTICAL);
		l.addView(b=new Button(this));b.setText("开始录音");b.setOnClickListener(this);
		l.addView(b2=new Button(this));b2.setText("播放录音");b2.setOnClickListener(this);
		new File(f=getExternalFilesDir("")+"/录音/").mkdir();
		
		
		
		
		

		
		
		
		
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
				m.setOutputFile(f2=f+DateFormat.format("yyyy.MM.dd HH.mm.ss",Calendar.getInstance())+".mp3");
				m.prepare();
				m.start();
				b2.setVisibility(View.INVISIBLE);
			}
			else
			{
				b.setText("开始录音");
				m.stop();
				m.release();
				b2.setVisibility(View.VISIBLE);
			}
		}
		else if(v==b2)
		{
			List<String>a=new ArrayList<>();
			for(File f:new File(f).listFiles())a.add(f+"");
			ac10.a=a;ac10.j=a.indexOf(f2);if(ac10.j==-1)ac10.j=0;
			startActivity(new Intent(this,ac10.class));
		}
	}catch(Exception e){e.printStackTrace();}}
}