package c.cx900;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.*;
import android.app.*;
import android.os.*;

//WebView点击百度的输入框时会白屏，百度了很久还是无法解决，以后再想办法，小心!!!!!!!!!!!
public class sy6 extends Activity
{
	WebView w;
	protected void onCreate(Bundle bu)
	{
		super.onCreate(bu);LinearLayout l=new LinearLayout(this);
		setContentView(l);l.setOrientation(LinearLayout.VERTICAL);
		l.addView(w=new WebView(this));
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
		
		
		
		// 清缓存和记录，缓存引起的白屏
		w.clearCache(true);
		w.clearHistory();
		
		w.requestFocus();
		WebSettings webSettings = w.getSettings();
		webSettings.setDatabaseEnabled(true);
// 缓存白屏
		String appCachePath = getApplicationContext().getCacheDir()
				.getAbsolutePath() + "/webcache";
// 设置 Application Caches 缓存目录
		webSettings.setAppCachePath(appCachePath);
		webSettings.setDatabasePath(appCachePath);
		
		
		
		
		
		
		// 应用可以有缓存 true false 没有缓存
		webSettings.setAppCacheEnabled(false);
		
		
		
		// 解决对某些标签的不支持出现白屏
		webSettings.setDomStorageEnabled(true);
		
		
		
		
		
		
		w.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				super.onPageStarted(view, url, favicon);
			}
			
			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
			}
			
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// 重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不另跳浏览器
				// 在2.3上面不加这句话，可以加载出页面，在4.0上面必须要加入，不然出现白屏
				if (url.startsWith("http://") || url.startsWith("https://")) {
					view.loadUrl(url);
					w.stopLoading();
					return true;
				}
				return false;
			}
			
			@Override
			public void onReceivedError(WebView view, int errorCode,
										String description, String failingUrl) {
				super.onReceivedError(view, errorCode, description, failingUrl);
			}
		});
		
		
		
		
		
		
		w.setLayerType(View.LAYER_TYPE_SOFTWARE,null);
		w.setVerticalScrollBarEnabled(false);
		
		
		
		
		
		
		
		WebSettings settings = w.getSettings();
		settings.setJavaScriptEnabled(true);
		settings.setDomStorageEnabled(true);
		settings.setDatabaseEnabled(true);
		settings.setAppCacheEnabled(true);
//设置可以访问文件  
		settings.setAllowFileAccess(true);
		
		
		w.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageFinished(WebView view, String url) {
				
				w.setLayerType(View.LAYER_TYPE_HARDWARE,null);
				
			}
			
			
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				super.onPageStarted(view, url, favicon);
			}






/* @Override
public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
view.loadUrl(s);
return true;
}


@Override
public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
super.onReceivedSslError(view, handler, error);
handler.proceed();
}*/
		});
		
		
		
		
		
		
		w.setBackgroundColor(0);
		
		
		
		
		
		
		
		
		
		
		
		w.loadUrl("https://www.baidu.com");
	}
}