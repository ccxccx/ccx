package c.cx900;
import android.app.Activity;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.UserHandle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebBackForwardList;
import android.webkit.WebChromeClient;
import android.webkit.WebHistoryItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URL;
import java.net.URLDecoder;

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
public class ac6 extends Activity implements View.OnClickListener
{
	EditText e,e2;Button b,b2,b3,b4,b5,b6,b7,b8,b9,b10,b11,b12;WebView w;TextView t;
	Handler h=new Handler()
	{
		public void handleMessage(Message m)
		{
		
		}
	};
	ClipboardManager c;int w2;LinearLayout l4,l5;
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		w2=getWindowManager().getDefaultDisplay().getWidth();
		c=(ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
		RelativeLayout r=new RelativeLayout(this);setContentView(r);
		LinearLayout l=new LinearLayout(this);r.addView(l);l.setOrientation(LinearLayout.VERTICAL);
		LinearLayout l2=new LinearLayout(this);l.addView(l2,new LinearLayout.LayoutParams(-1,w2/7));
		LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(0,-2,1),p3=new LinearLayout.LayoutParams(0,-1,1);
		l2.addView(t=new TextView(this),new LinearLayout.LayoutParams(0,-1,5));t.setGravity(Gravity.CENTER);
		l2.addView(b7=new Button(this),p3);b7.setText("复制链接");b7.setOnClickListener(this);
		l2.addView(b8=new Button(this),p3);b8.setText("刷新");b8.setOnClickListener(this);
		l2=new LinearLayout(this);l.addView(l2);
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
		l.addView(w=new WebView(this));
		RelativeLayout.LayoutParams p2=new RelativeLayout.LayoutParams(-1,-2);p2.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		LinearLayout l3=new LinearLayout(this);r.addView(l3,p2);
		l3.addView(b2=new Button(this),p);b2.setText("<");b2.setOnClickListener(this);
		l3.addView(b3=new Button(this),p);b3.setText(">");b3.setOnClickListener(this);
		l3.addView(b4=new Button(this),p);b4.setText("主页");b4.setOnClickListener(this);
		l3.addView(b5=new Button(this),p);b5.setText("1");b5.setOnClickListener(this);
		l3.addView(b6=new Button(this),p);b6.setText("其他");b6.setOnClickListener(this);
		RelativeLayout.LayoutParams p4=new RelativeLayout.LayoutParams(-1,-2);int i=1;l3.setId(i);p4.addRule(RelativeLayout.ABOVE,1);
		r.addView(l4=new LinearLayout(this),p4);l4.setVisibility(View.INVISIBLE);
		l4.addView(b9=new Button(this),p);b9.setText("页内查找");b9.setOnClickListener(this);
		r.addView(l5=new LinearLayout(this),p4);l5.setVisibility(View.INVISIBLE);
		l5.addView(e2=new EditText(this),new LinearLayout.LayoutParams(0,-2,6.5f));e2.setHint("请输入您要查找的字符串");
		l5.addView(b10=new Button(this),new LinearLayout.LayoutParams(0,-2,1.5f));b10.setText("查找");b10.setOnClickListener(this);
		l5.addView(b11=new Button(this),p);b11.setText("<");b11.setOnClickListener(this);
		l5.addView(b12=new Button(this),p);b12.setText(">");b12.setOnClickListener(this);
		
		
		
		
		
		
		
		
		
		
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
		w.setWebChromeClient(new WebChromeClient()
		{
			//当页面改变时，只有onProgressChanged()一定会自动调用，会自动调用>=1次
			public void onProgressChanged(WebView w,int i)
			{
				//System.out.println("onProgressChanged："+i+"："+URLDecoder.decode(w.getUrl()));
//				System.out.println("onProgressChanged："+i+"："+w.getTitle());
				t.setText(URLDecoder.decode(w.getUrl()));
			}
			public void onReceivedTitle(WebView view,String s)
			{
//				System.out.println("onReceivedTitle："+s);
				
			}
		});
		
		
		
		
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
			
			
			
			
			
			
		//ListView l3=new ListView(this);
		
		
		
		
		
		w.loadUrl("https://m.baidu.com/s?word=死神");
		
		
		
		
		
		
		//w.loadData("<h1>123死神</h1>","","");
		
		
		
		
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
			w.loadUrl("https://www.baidu.com");
		}
		else if(v==b5)
		{
		
		}
		else if(v==b6)
		{
			if(l4.getVisibility()==View.VISIBLE)l4.setVisibility(View.INVISIBLE);
			else if(l5.getVisibility()==View.VISIBLE)l5.setVisibility(View.INVISIBLE);
			else l4.setVisibility(View.VISIBLE);
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
			l4.setVisibility(View.INVISIBLE);l5.setVisibility(View.VISIBLE);
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
	}
}