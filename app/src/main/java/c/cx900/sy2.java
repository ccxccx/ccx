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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class sy2 extends Activity
{
	//文件夹a，文件a2
	List<String> a,a2;
	Comparator<String> c2;
	Button b,b2;
	ClipboardManager c;ba d;
	protected void onCreate(Bundle bu)
	{
		super.onCreate(bu);
		//若想获取文件列表，则必须要有以下的权限：<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
		//而且READ_EXTERNAL_STORAGE的权限必须动态申请才行，记!!!!!!!!!!!!小心!!!!!!!!!!!
		ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},0);
	}
	public void onRequestPermissionsResult(int requestCode,String[]permissions,int[]grantResults)
	{
		c=(ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
		LinearLayout l=new LinearLayout(this);setContentView(l);
		l.setOrientation(LinearLayout.VERTICAL);final EditText e=new EditText(this);
		l.addView(e);
		e.setText(Environment.getExternalStorageDirectory()+"");
		e.setOnEditorActionListener(new TextView.OnEditorActionListener()
		{
			public boolean onEditorAction(TextView tv,int i,KeyEvent k)
			{
				b.callOnClick();
				return true;
			}
		});
//用e.clearFocus();无法清除默认焦点
//用InputMethodManager
//的hideSoftInputFromWindow()也不行!!!!!!
//用l.setFocusable(true);也不行!!!!!!!!!!
//小心!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//只有这样使父控件获取焦点
//才能防止EditText默认获得焦点
//对比!!!!!!!!!!!!!!!小心!!!!!!!!!!!!!!!!!!!!
		l.setFocusableInTouchMode(true);
		l.addView(b=new Button(this));
		b.setText("进入该文件夹");
		b.setOnClickListener(new View.OnClickListener()
		{public void onClick(View v){f(e.getText()+"");}});
		final Pattern p=Pattern.compile("^\\d+");
		c2=new Comparator<String>()
		{
			public int compare(String s,String t)
			{
//排序，精华!!!!!!!!!!!!!!记方法!!!!!!!!!!!!!
				int i=(int)1e9,j=(int)1e9;
				Matcher m=p.matcher(s);
				if(m.find())i=Integer.valueOf(m.group());
				m=p.matcher(t);if(m.find())j=Integer.valueOf(m.group());
				if(i==j)return s.compareTo(t);
//这里不能return i-j;因为可能会溢出!!!!!!
//超级易错难发现!!!!!!!!!!!!!!!小心!!!!!!!!!!!!!
//超级易错难发现!!!!!!!!!!!!!!!小心!!!!!!!!!!!!!
//超级易错难发现!!!!!!!!!!!!!!!小心!!!!!!!!!!!!!
				return i>j?1:-1;
			}
		};
		TextView t=new TextView(this);l.addView(t);
		t.setText("当前路径，文件2。点击都会复制路径"
				+"，点击文件夹会进入：");
		b2=new Button(this);l.addView(b2);
		b2.setOnClickListener(new View.OnClickListener()
		{public void onClick(View v){fz(b2.getText()+"");}});
		GridView g=new GridView(this);l.addView(g);
		g.setNumColumns(5);d=new ba(this);
//必须在b2,d初始化后才能调用f()初始化a,a2
//，然后才能setAdapter()，小心!!!!!!!!!!!!!!!!
		b.callOnClick();g.setAdapter(d);
	}
	//复制路径，进入文件夹
	void f(String s)
	{
//如果是根目录，则f.getParent()=null，所以需判断!!!!!!!
//小心!!!!!!!!!!!!!!!!!!!!!!
		if(s!=null)
		{
			//根目录下的某些"文件"既不是文件
			//也不是文件夹，所以千万别用isFile()，小心!!!!!!!!!
			fz(s);
			File f=new File(s);if(f.isDirectory())
		{
			b2.setText(s);a=new ArrayList<>();a2=new ArrayList<>();
//如果是/storage/emulated，则f.listFiles()会返回null
//，所以要判断防止秒退，小心!!!!!!!!!!!!!
			if(f.listFiles()!=null)for(File q:f.listFiles())
			{
				s=q.getName();
				if(q.isDirectory())a.add(s);else a2.add(s);
			}
			Collections.sort(a,c2);Collections.sort(a2,c2);
//更新数据后必须调用这个，否则控件不会立马更新
//，而是会在做某些动作后才会更新!!!!!
//，而且滑动还会秒退!!!!!!!!!!!易错!!!!!!!!!小心!!!!!!!!
			d.notifyDataSetChanged();
		}
		}
	}
	void fz(String s)
	{
		c.setText(s);Toast.makeText(this,s,Toast.LENGTH_SHORT).show();
	}
	class ba extends BaseAdapter
	{
		Context c;
		ba(Context a){c=a;}
		public int getCount(){return a.size()+a2.size();}
		public Object getItem(int i){return 0;}
		public long getItemId(int i){return 0;}
		public View getView(final int i,View v,ViewGroup g)
		{
			bb b=(bb)v;if(b==null)
		{
//本来各个控件会参差不齐，超极难看!!!!!!
//但利用setHeight(80)设置高度后控件就会变整齐!!!!!
//记方法!!!!!!!!!!!!小心!!!!!!!!!!!!!!
			b=new bb(c);b.setHeight(70);
			b.setGravity(Gravity.LEFT);b.setPadding(6,0,6,0);
		}
			final String s,s2;
			if(i<a.size()){s="\n";s2=a.get(i);b.b=true;}
			else{s="";s2=a2.get(i-a.size());b.b=false;}
			b.setText(s+s2);b.setOnClickListener(new View
				.OnClickListener(){public void onClick(View v)
		{f(b2.getText()+"/"+s2);}});
			return b;
		}
	}
	class bb extends Button
	{
		Paint p=new Paint();boolean b;
		bb(Context c){super(c);p.setColor(0xff0000ff);}
		protected void onDraw(Canvas c)
		{super.onDraw(c);if(b)c.drawRect(0,0,12,12,p);}
	}
	public boolean onKeyDown(int i,KeyEvent e)
	{
		if(i==4)f(new File(b2.getText()+"").getParent());
//若return true;则会把除了电源键和home键以外
//的其他所有按键(如音量+-和返回键等)的原来
//的作用都屏蔽了，小心!!!!!
//若return false;则只会把返回键屏蔽了
//，这说明返回键的作用写在了super.onKeyDown(i,e);里
//记原理!!!!!!!!!!!!!小心!!!!!!!!!!
//若return super.onKeyDown(i,e);
//则所有按键都会有原来的作用
//对比记忆!!!!!!!!!小心!!!!!!!!!!!
//所以这里要用return false;对比!!!!!!记原理!!!!!!!小心!!!!!!!
		return false;
	}
}