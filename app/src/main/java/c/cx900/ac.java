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
	class i
	{
		Class c;String s;
		i(Class a,String b){c=a;s=b;}
	}
	i[]a={new i(ac2.class,"文件浏览器"),new i(ac3.class,"音乐播放器"),new i(ac4.class,"视频播放器")
			,new i(ac6.class,"网页浏览器"),new i(ac7.class,""),new i(ac8.class,""),new i(ac9.class,"")};
	protected void onCreate(Bundle bu)
	{
		super.onCreate(bu);ListView l=new ListView(this);
		setContentView(l);l.setAdapter(new ba(this));
		
		
		
		
		startActivity(new Intent(this,ac6.class));
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
			b.setText(a[i].s);
			b.setOnClickListener(new View.OnClickListener()
			{
				public void onClick(View v)
				{
					startActivity(new Intent(c,a[i].c));
				}
			});
			return b;
		}
	}
}