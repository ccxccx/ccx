package c.cx900;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import android.app.*;
import android.os.*;

import java.util.List;

public class ac extends Activity
{
	Class[]a={  ac2.class,   ac3.class,   ac4.class,  ac5.class};
	String[]s={"文件浏览器","音乐播放器","视频播放器","发送/接收文件"};
	protected void onCreate(Bundle bu)
	{
		super.onCreate(bu);ListView l=new ListView(this);
		setContentView(l);l.setAdapter(new ba(this));
		
	}
	class ba extends BaseAdapter
	{
		Context c;
		ba(Context a){c=a;}
		public int getCount(){return a.length;}
		public Object getItem(int position){return null;}
		public long getItemId(int position){return 0;}
		public View getView(final int i,View v,ViewGroup g)
		{
			Button b=(Button)v;
			if(b==null)
			{
				b=new Button(c);
			}
			b.setText(s[i]);
			b.setOnClickListener(new View.OnClickListener()
			{
				public void onClick(View v)
				{
					startActivity(new Intent(c,a[i]));
				}
			});
			return b;
		}
	}
}