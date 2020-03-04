package c.cx900;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.UserHandle;
import android.util.Log;
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

//如何在android studio里查看WebView的真正源码，百度了很久还是没查到，以后再想办法，小心!!!!!!!!!!!
public class ac6 extends Activity implements View.OnClickListener
{
	EditText e;Button b,b2,b3,b4,b5,b6;WebView w;
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		RelativeLayout r=new RelativeLayout(this);setContentView(r);
		LinearLayout l=new LinearLayout(this);r.addView(l);l.setOrientation(LinearLayout.VERTICAL);
		LinearLayout l2=new LinearLayout(this);l.addView(l2);
		LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(0,-2,1);
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
		
		
		
		
		
		
		
		
		
		
		
		
		w.setWebViewClient(new WebViewClient()
		{
			public boolean shouldOverrideUrlLoading(WebView w,String u)
			{
				e.setText(u);w.loadUrl(u);
				return true;
			}
			
			
			
			
			public void onLoadResource(WebView w,String u)
			{
				super.onLoadResource(w,u);
				
				//e.setText(u);
			}
		});
		
		
		
		
		
		
		
		//ListView l3=new ListView(this);
		
		
		
		
		
		e.setText("https://m.baidu.com/s?word=死神");onClick(b);
	}
	public void onClick(View v)
	{
		if(v==b)
		{
			w.loadUrl(e.getText()+"");
		}
		else if(v==b2)
		{

//			WebBackForwardList backForwardList = w.copyBackForwardList();
//			if (backForwardList != null && backForwardList.getSize() != 0) {
//				//当前页面在历史队列中的位置
//				int currentIndex = backForwardList.getCurrentIndex();
//				WebHistoryItem historyItem =
//						backForwardList.getItemAtIndex(currentIndex - 1);
//				if (historyItem != null) {
//					String backPageUrl = historyItem.getUrl();
//					//url拿到可以进行操作
//
			
			
			
			w.goBack();
		}
		else if(v==b3)
		{
			w.goForward();
		}
		else if(v==b4)
		{
		
		}
		else if(v==b5)
		{
		
		}
		else if(v==b6)
		{
		
		}
	}
	public void onBackPressed()
	{
		if(w.canGoBack())
		{
			w.goBack();
		}
		//使app按back键时返回桌面，而不是退出app
		else startActivity(new Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_HOME));
	}
}