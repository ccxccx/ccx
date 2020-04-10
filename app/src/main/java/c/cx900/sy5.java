package c.cx900;
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.Image;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethod;
import android.webkit.MimeTypeMap;
import android.webkit.WebView;
import android.widget.*;
import android.app.*;
import android.os.*;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import org.w3c.dom.Text;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

//试了很久，还是无法实现从下到上且不遮挡Button的ListView，以后再想办法，小心!!!!!!!!!!!
public class sy5 extends Activity implements View.OnClickListener
{
	TextView t;ListView l9;ba a;
	List<String>l5=new ArrayList<>();
	EditText e,e2;Button b,b2,b3,b4,b5,b6,b7,b8,b9,b10,b11,b12,b13;WebView w;
	ClipboardManager c;int w2;LinearLayout l4,l7;
	View l8;RelativeLayout r,r2;RelativeLayout.LayoutParams p6;
	protected void onCreate(Bundle bu)
	{
		super.onCreate(bu);
//		RelativeLayout r=new RelativeLayout(this);setContentView(r);
//		r.addView(b=new Button(this),-1,-2);b.setOnClickListener(this);
//		RelativeLayout.LayoutParams p2=new RelativeLayout.LayoutParams(-1,-2);p2.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
//		LinearLayout l3=new LinearLayout(this);r.addView(l3,p2);
//		l3.addView(b2=new Button(this));b2.setText("<");b2.setOnClickListener(this);
//		l3.addView(b3=new Button(this));b3.setText(">");b3.setOnClickListener(this);
//		RelativeLayout.LayoutParams p4=new RelativeLayout.LayoutParams(-1,-2);int i=1;l3.setId(i);p4.addRule(RelativeLayout.ABOVE,1);
//		r.addView(r2=new RelativeLayout(this),p4);
//		r2.addView(l2=new ListView(this));l2.setAdapter(a=new ba(this));l5.add("123");l5.add("123");
//		RelativeLayout.LayoutParams p7=new RelativeLayout.LayoutParams(-1,-2);l2.setId(i=4);
//		p7.addRule(RelativeLayout.BELOW,4);
//		r2.addView(b13=new Button(this),p7);b13.setText("+");b13.setOnClickListener(this);
		
		
		
		
		w2=getWindowManager().getDefaultDisplay().getWidth();
		c=(ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
		setContentView(r=new RelativeLayout(this));
		LinearLayout l=new LinearLayout(this);r.addView(l,-1,-2);l.setOrientation(LinearLayout.VERTICAL);
		LinearLayout l2=new LinearLayout(this);l.addView(l2,new LinearLayout.LayoutParams(-1,w2/7));
		LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(0,-2,1),p3=new LinearLayout.LayoutParams(0,-1,1);
		l2.addView(t=new TextView(this),new LinearLayout.LayoutParams(0,-1,5));t.setGravity(Gravity.CENTER);
		l2.addView(b7=new Button(this),p3);b7.setText("复制链接");b7.setOnClickListener(this);
		l2.addView(b8=new Button(this),p3);b8.setText("刷新");b8.setOnClickListener(this);
		l.addView(l2=new LinearLayout(this));
		l2.addView(e=new EditText(this),new LinearLayout.LayoutParams(0,-2,6));
		e.setSingleLine();
		//当,在输入框内按下换行符时，执行自定义的代码
		e.setOnEditorActionListener(new TextView.OnEditorActionListener()
		{
			public boolean onEditorAction(TextView tv,int i,KeyEvent k)
			{
				onClick(b);
				return true;
			}
		});
		l2.addView(b=new Button(this),p);b.setText("浏览");b.setOnClickListener(this);
		
		//LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(0,-2,1);
		
		RelativeLayout.LayoutParams p2=new RelativeLayout.LayoutParams(-1,-2);p2.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		LinearLayout l3=new LinearLayout(this);r.addView(l3,p2);
		l3.addView(b2=new Button(this),p);b2.setText("<");b2.setOnClickListener(this);
		l3.addView(b3=new Button(this),p);b3.setText(">");b3.setOnClickListener(this);
		l3.addView(b4=new Button(this),p);b4.setText("主页");b4.setOnClickListener(this);
		l3.addView(b5=new Button(this),p);b5.setText("1");b5.setOnClickListener(this);
		l3.addView(b6=new Button(this),p);b6.setText("其他");b6.setOnClickListener(this);
		p6=new RelativeLayout.LayoutParams(-1,-2);int i=1;l3.setId(i);p6.addRule(RelativeLayout.ABOVE,1);
		l.setId(i=3);p6.addRule(RelativeLayout.BELOW,3);
		RelativeLayout.LayoutParams p4=new RelativeLayout.LayoutParams(-1,-2);p4.addRule(RelativeLayout.ABOVE,1);
		r.addView(l8=l4=new LinearLayout(this),p4);l4.setVisibility(View.INVISIBLE);
		l4.addView(b9=new Button(this),p);b9.setText("页内查找");b9.setOnClickListener(this);
		
		r.addView(l7=new LinearLayout(this),p4);l7.setVisibility(View.INVISIBLE);
		l7.addView(e2=new EditText(this),new LinearLayout.LayoutParams(0,-2,6.5f));e2.setHint("请输入您要查找的字符串");
		l7.addView(b10=new Button(this),new LinearLayout.LayoutParams(0,-2,1.5f));b10.setText("查找");b10.setOnClickListener(this);
		l7.addView(b11=new Button(this),p);b11.setText("<");b11.setOnClickListener(this);
		l7.addView(b12=new Button(this),p);b12.setText(">");b12.setOnClickListener(this);
		
		//p4=new RelativeLayout.LayoutParams(-1,-2);p4.addRule(RelativeLayout.ABOVE,1);
		
		r.addView(r2=new RelativeLayout(this),new RelativeLayout.LayoutParams(p4));//r2.setVisibility(View.INVISIBLE);
		
		
		
		
		
		RelativeLayout.LayoutParams p5=new RelativeLayout.LayoutParams(-1,-2);r2.setId(i=2);p5.addRule(RelativeLayout.ALIGN_BOTTOM,2);
		//l9.setId(i=5);p5.addRule(RelativeLayout.BELOW,5);
		r2.addView(b13=new Button(this),p5);b13.setText("+");b13.setOnClickListener(this);
		
		RelativeLayout.LayoutParams p7=new RelativeLayout.LayoutParams(-1,-2);b13.setId(i=4);//p7.addRule(RelativeLayout.ABOVE,4);
		p7.addRule(RelativeLayout.ALIGN_TOP,2);
		r2.addView(l9=new ListView(this),p7);l9.setAdapter(a=new ba(this));
		l9.setStackFromBottom(true);
		
//		f();w.loadUrl("https://m.baidu.com/s?word=死神");
		for(i=0;i<9;i++)l5.add("123");
		
		r2.setBackgroundColor(0xffff0000);
	}
	class ba extends BaseAdapter
	{
		Context c;
		ba(Context a){c=a;}
		public int getCount(){return l5.size();}
		public Object getItem(int position){return null;}
		public long getItemId(int position){return 0;}
		public View getView(int i,View v,ViewGroup g)
		{
			Button b=(Button)v;
			if(b==null)
			{
				b=new Button(c);
			}
			b.setText(l5.get(i));
			
			return b;
		}
	}
	public void onClick(View v)
	{
		if(v==b)
		{
			l5.add("123");a.notifyDataSetChanged();
		}
		else if(v==b2)
		{
		
		}
		else if(v==b3)
		{
		
		}
		else if(v==b13)
		{
			l5.add("123");a.notifyDataSetChanged();
		}
	}
}