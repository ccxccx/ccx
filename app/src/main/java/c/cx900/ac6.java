package c.cx900;
import android.app.Activity;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.UserHandle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebBackForwardList;
import android.webkit.WebChromeClient;
import android.webkit.WebHistoryItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

//如何在android studio里查看WebView的真正源码，百度了很久还是失败了，以后再想办法，小心!!!!!!!!!!!
//用System.out.println("123");或Log.d("ccx","123");输出到Logcat时，某些手机会显示不全，如："小辣椒"手机（当只将手机换成vivo时就没问题了，所以肯定是手机有问题），以后再想办法，小心!!!!!!!!!!!

//当加载"https://m.baidu.com/s?word=死神"时：onPageStarted（https://m.baidu.com/s?word=%E6%AD%BB%E7%A5%9E）->非常多个onLoadResource->onPageCommitVisible->非常多个onLoadResource
//->onPageFinished（https://m.baidu.com/s?word=%E6%AD%BB%E7%A5%9E）->非常多个onLoadResource
//当点击"https://wapbaike.baidu.com/item/久保带人/530466"时：shouldOverrideUrlLoading（https://wapbaike.baidu.com/item/久保带人/530466）->onPageStarted->onPageCommitVisible->onPageFinished
//，但有时候点击链接时不会自动调用这些函数（shouldOverrideUrlLoading()、onPageStarted()、onPageCommitVisible()、onPageFinished()），如：点击死神的百度百科时，小心!!!!!!!!!!!!!

//用onProgressChanged()、onReceivedTitle()有时无法获取到正确的标题，如：点击死神的百度百科时，小心!!!!!!!!!!!!!
//每3s用1次w.getTitle()有时也还是无法获取到正确的标题
//用WebHistoryItem h=w.copyBackForwardList().getCurrentItem();if(h!=null)System.out.println(h.getTitle());有时也还是无法获取到正确的标题
//百度了很久，还是无法获取到正确的标题，以后再想办法，小心!!!!!!!!!!!

//试了很久，还是无法实现从下到上且不遮挡Button的ListView，以后再想办法，小心!!!!!!!!!!!

//WebView点击百度的输入框时会白屏，百度了很久还是无法解决，以后再想办法，小心!!!!!!!!!!!
public class ac6 extends Activity implements View.OnClickListener
{
	EditText e,e2;Button b,b2,b3,b4,b5,b6,b7,b8,b9,b10,b11,b12,b13,b14;WebView w;TextView t;
	ClipboardManager c;int w2;LinearLayout l4,l5,l9;ListView l6;ba a;List<WebView>l7=new LinkedList<>();
	View l8;RelativeLayout r;RelativeLayout.LayoutParams p6;LinearLayout.LayoutParams p;
	//	Handler h=new Handler()
//	{
//		public void handleMessage(Message m)
//		{
//
//		}
//	};
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		w2=getWindowManager().getDefaultDisplay().getWidth();
		c=(ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
		setContentView(r=new RelativeLayout(this));
		LinearLayout l=new LinearLayout(this);r.addView(l,-1,-2);l.setOrientation(LinearLayout.VERTICAL);
		LinearLayout l2=new LinearLayout(this);l.addView(l2,new LinearLayout.LayoutParams(-1,w2/7));
		p=new LinearLayout.LayoutParams(0,-2,1);LinearLayout.LayoutParams p3=new LinearLayout.LayoutParams(0,-1,1);
		l2.addView(t=new TextView(this),new LinearLayout.LayoutParams(0,-1,5));t.setGravity(Gravity.CENTER);
		l2.addView(b7=new Button(this),p3);b7.setText("复制链接");b7.setOnClickListener(this);
		l2.addView(b8=new Button(this),p3);b8.setText("刷新");b8.setOnClickListener(this);
		l.addView(l2=new LinearLayout(this));
		l2.addView(e=new EditText(this),new LinearLayout.LayoutParams(0,-2,5));
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
		l2.addView(b14=new Button(this),p);b14.setOnClickListener(this);
		RelativeLayout.LayoutParams p2=new RelativeLayout.LayoutParams(-1,-2);p2.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		LinearLayout l3=new LinearLayout(this);r.addView(l3,p2);
		l3.addView(b2=new Button(this),p);b2.setText("<");b2.setOnClickListener(this);
		l3.addView(b3=new Button(this),p);b3.setText(">");b3.setOnClickListener(this);
		l3.addView(b4=new Button(this),p);b4.setText("主页");b4.setOnClickListener(this);
		l3.addView(b5=new Button(this),p);b5.setText("");b5.setOnClickListener(this);
		l3.addView(b6=new Button(this),p);b6.setText("其他");b6.setOnClickListener(this);
		p6=new RelativeLayout.LayoutParams(-1,-2);int i=1;l3.setId(i);p6.addRule(RelativeLayout.ABOVE,1);
		l.setId(i=2);p6.addRule(RelativeLayout.BELOW,2);
		RelativeLayout.LayoutParams p4=new RelativeLayout.LayoutParams(-1,-2);p4.addRule(RelativeLayout.ABOVE,1);
		r.addView(l8=l4=new LinearLayout(this),p4);l4.setVisibility(View.INVISIBLE);
		l4.addView(b9=new Button(this),p);b9.setText("页内查找");b9.setOnClickListener(this);
		r.addView(l5=new LinearLayout(this),p4);l5.setVisibility(View.INVISIBLE);
		l5.addView(e2=new EditText(this),new LinearLayout.LayoutParams(0,-2,6.5f));e2.setHint("请输入您要查找的字符串");
		l5.addView(b10=new Button(this),new LinearLayout.LayoutParams(0,-2,1.5f));b10.setText("查找");b10.setOnClickListener(this);
		l5.addView(b11=new Button(this),p);b11.setText("<");b11.setOnClickListener(this);
		l5.addView(b12=new Button(this),p);b12.setText(">");b12.setOnClickListener(this);
		RelativeLayout.LayoutParams p7=new RelativeLayout.LayoutParams(-1,-2);p7.addRule(RelativeLayout.BELOW,2);
		r.addView(l9=new LinearLayout(this),p7);l9.setOrientation(LinearLayout.VERTICAL);l9.setVisibility(View.INVISIBLE);
		l9.addView(b13=new Button(this));b13.setText("+");b13.setOnClickListener(this);
		l9.addView(l6=new ListView(this));l6.setAdapter(a=new ba(this));
		f();
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		//		new Thread()
//		{
//			public void run()
//			{try{
//				for(;;Thread.sleep(3000))
//				{
//					h.sendEmptyMessage(1);
//				}
//			}catch(Exception e){e.printStackTrace();}}
//		}.start();
		
		
		
		
		
		
		
		
		
		//w.loadData("<h1>123死神</h1>","","");
		
		
		
		
		
		
//		w.loadUrl("https://m.baidu.com/s?word=死神");
	
	
	
	
	
	
		
	}
	void f()
	{
		//第2个参数为绘制View的顺序，0为最先绘制的视图（最底下的视图），所以必须把第2个参数设置为0才能使其他视图不被遮挡，记方法!!!!!!!!!!!!!
		r.addView(w=new WebView(this),0,p6);
		l7.add(0,w);a.notifyDataSetChanged();b5.setText(l7.size()+"");b14.setText(l7.size()+"");
		WebSettings s=w.getSettings();
		//设置允许网页运行JavaScript，必须加这个，小心!!!!!!!!!!!!!
		s.setJavaScriptEnabled(true);
		//设置自适应屏幕
		s.setUseWideViewPort(true);s.setLoadWithOverviewMode(true);
		//设置允许访问文件
		s.setAllowFileAccess(true);
		//设置允许通过JavaScript打开新窗口
		s.setJavaScriptCanOpenWindowsAutomatically(true);
		//设置自动加载图片
		s.setLoadsImagesAutomatically(true);
		//设置编码格式
		s.setDefaultTextEncodingName("utf8");
		//设置使用WebView加载网页，而不是打开默认浏览器再加载网页
		w.setWebViewClient(new WebViewClient());
		w.loadUrl("https://m.baidu.com");
		
		
		
		
		
		
		
		
		
		
		
		
		w.setWebChromeClient(new WebChromeClient()
		{
			//当页面改变时，只有onProgressChanged()一定会自动调用，会自动调用>=1次
			public void onProgressChanged(WebView w,int i)
			{
				t.setText(URLDecoder.decode(w.getUrl()));a.notifyDataSetChanged();
				
				
				
				//System.out.println("onProgressChanged："+i+"："+URLDecoder.decode(w.getUrl()));
//				System.out.println("onProgressChanged："+i+"："+w.getTitle());
			
			}
			public void onReceivedTitle(WebView view,String s)
			{
//				System.out.println("onReceivedTitle："+s);
			
			}
		});
		
		
		
		
		
		
		
		

		
		
		
		
	}
	public void onClick(View v)
	{
		if(v==b)
		{
			w.loadUrl(e.getText()+"");
		}
		else if(v==b2)
		{
			w.goBack();
		}
		else if(v==b3)
		{
			w.goForward();
		}
		else if(v==b4)
		{
			w.loadUrl("https://m.baidu.com");
		}
		else if(v==b5||v==b14)
		{
			if(l8.getVisibility()==View.VISIBLE)l8.setVisibility(View.INVISIBLE);
			else
			{
				l9.setVisibility(View.VISIBLE);l8=l9;
			}
		}
		else if(v==b6)
		{
			if(l8.getVisibility()==View.VISIBLE)l8.setVisibility(View.INVISIBLE);
			else
			{
				l4.setVisibility(View.VISIBLE);l8=l4;
			}
		}
		else if(v==b7)
		{
			c.setText(t.getText());
			Toast.makeText(this,t.getText(),Toast.LENGTH_SHORT).show();
		}
		else if(v==b8)
		{
			w.reload();
		}
		else if(v==b9)
		{
			l4.setVisibility(View.INVISIBLE);l5.setVisibility(View.VISIBLE);l8=l5;
		}
		else if(v==b10)
		{
			w.findAllAsync(e2.getText()+"");
		}
		else if(v==b11)
		{
			w.findNext(false);
		}
		else if(v==b12)
		{
			w.findNext(true);
		}
		else if(v==b13)
		{
			w.setVisibility(View.INVISIBLE);f();
			l9.setVisibility(View.INVISIBLE);
		}
	}
	class ba extends BaseAdapter
	{
		Context c;
		ba(Context a){c=a;}
		public int getCount(){return l7.size();}
		public Object getItem(int position){return null;}
		public long getItemId(int position){return 0;}
		class i{Button b,b2;i(Button a,Button c){b=a;b2=c;}}
		public View getView(final int i,View v,ViewGroup g)
		{
			i a;LinearLayout l=(LinearLayout)v;
			if(l==null)
			{
				l=new LinearLayout(c);
				l.setTag(a=new i(new Button(c),new Button(c)));
				l.addView(a.b,new LinearLayout.LayoutParams(0,-2,7));
				l.addView(a.b2,p);a.b2.setText("✕");
			}
			else a=(i)l.getTag();
			final WebView w2=l7.get(i);
			if(w2==w)l.setBackgroundColor(0xffff7777);
			else l.setBackgroundColor(0xffcccccc);
			a.b.setText(URLDecoder.decode(w2.getUrl()));
			a.b.setOnClickListener(new View.OnClickListener()
			{
				public void onClick(View v)
				{try{
					if(w2!=w)
					{
						w.setVisibility(View.INVISIBLE);w=w2;
						w.setVisibility(View.VISIBLE);
						t.setText(URLDecoder.decode(w.getUrl()));notifyDataSetChanged();
					}
					l9.setVisibility(View.INVISIBLE);
				}catch(Exception e){e.printStackTrace();}}
			});
			a.b2.setOnClickListener(new View.OnClickListener()
			{
				public void onClick(View v)
				{try{
					if(l7.size()==1)
					{
						Toast.makeText(c,"最后1个不能删除！",Toast.LENGTH_SHORT).show();
					}
					else
					{
						if(w2==w)
						{
							w=l7.get(i==l7.size()-1?i-1:i+1);
							w.setVisibility(View.VISIBLE);
						}
						l7.remove(w2);r.removeView(w2);notifyDataSetChanged();b5.setText(l7.size()+"");b14.setText(l7.size()+"");
					}
				}catch(Exception e){e.printStackTrace();}}
			});
			return l;
		}
	}
}