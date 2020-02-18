package c.cx900;
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import android.app.*;
import android.os.*;

import androidx.core.app.ActivityCompat;

import java.io.File;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class sy2 extends Activity
{
	//文件夹a，文件a2
	List<String>l=new ArrayList<>(),l2=new ArrayList<>();
	TextView t,t2;int j,w;ba a;
	void t(String s){t.setText(++j+"："+s);}
	protected void onCreate(Bundle bu)
	{
		super.onCreate(bu);
		//若想获取文件列表，则必须要有以下的权限：<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
		//而且READ_EXTERNAL_STORAGE的权限必须动态申请才行，记!!!!!!!!!!!!小心!!!!!!!!!!!
		ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},0);
	}
	public void onRequestPermissionsResult(int requestCode,String[]permissions,int[]grantResults)
	{
		w=getWindowManager().getDefaultDisplay().getWidth();
		LinearLayout l=new LinearLayout(this);setContentView(l);l.setOrientation(LinearLayout.VERTICAL);
		l.addView(t=new TextView(this));t.setGravity(Gravity.CENTER);t.setTextColor(0xffff0000);
		l.addView(t2=new TextView(this));t2.setGravity(Gravity.CENTER);
		GridView g=new GridView(this);l.addView(g);g.setAdapter(a=new ba(this));g.setNumColumns(5);
		f(Environment.getExternalStorageDirectory()+"");
	}
	//进入文件夹
	void f(String s)
	{try{
		//如果是根目录，则f.getParent()=null，所以需判断!!!!!!!
		//小心!!!!!!!!!!!!!!!!!!!!!!
		if(s!=null)
		{
			//根目录下的某些"文件"既不是文件，也不是文件夹，所以千万别用isFile()，小心!!!!!!!!!
			File f=new File(s);if(f.isDirectory())
			{
				t2.setText(s);l.clear();l2.clear();
				//如果是/storage/emulated，则f.listFiles()会返回null
				//，所以要判断防止秒退，小心!!!!!!!!!!!!!
				if(f.listFiles()!=null)for(File i:f.listFiles())
				{
					(i.isDirectory()?l:l2).add(i.getName());
				}
				Collections.sort(l);Collections.sort(l2);
				//更新数据后必须调用这个，否则控件不会立马更新
				//，而是会在做某些动作后才会更新!!!!!
				//，而且滑动还会秒退!!!!!!!!!!!易错!!!!!!!!!小心!!!!!!!!
				a.notifyDataSetChanged();
			}
		}
		else
		{
			t("根目录无法后退！");
		}
	}catch(Exception e){e.printStackTrace();}}
	public void onBackPressed()
	{
		f(new File(t2.getText()+"").getParent());
	}
	class ba extends BaseAdapter
	{
		Context c;
		ba(Context a){c=a;}
		public int getCount(){return l.size()+l2.size();}
		public Object getItem(int i){return 0;}
		public long getItemId(int i){return 0;}
		public View getView(final int i,View v,ViewGroup g)
		{
			Button b=(Button)v;if(b==null)
			{
				b=new Button(c);
				//本来各个控件会参差不齐，超级难看!!!!!!
				//但利用setHeight(180)设置高度后控件就会变整齐!!!!!!!
				//记方法!!!!!!!!!!!!小心!!!!!!!!!!!!!!
				b.setHeight(180);
				//本来里面的文本的空间利用率不高，而且有些字的下半部分看不见，超级难看!!!!!!
				//用setPadding(10,0,10,20);可使文本更好看!!!!!!记方法!!!!!!!!!!!!
				b.setPadding(10,0,10,20);
			}
			final String s;
			if(i<l.size()){s=l.get(i);b.setTextColor(0xff0000ff);}
			else{s=l2.get(i-l.size());b.setTextColor(0xff000000);}
			b.setText(s);
			b.setOnClickListener(new View.OnClickListener()
			{
				public void onClick(View v)
				{
					String s2=t2.getText()+"";
					f(s2+(s2.equals("/")?"":"/")+s);
				}
			});
			return b;
		}
	}
}