package cn.wangbaiyuan.byleavepaper;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

public class BYbrowserActivity extends Activity {
	private WebView webView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bybrowser);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
		    Window window = getWindow();
		    //requestWindowFeature(Window.FEATURE_NO_TITLE);
		    window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
		    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		   // getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
		}else{
			TextView top=(TextView)findViewById(R.id.top_relative);
			top.setVisibility(View.INVISIBLE);
		}
		String URL=getIntent().getExtras().getString("URL");
		String title=getIntent().getExtras().getString("title");
		setTitle(title);
    	webView = (WebView)findViewById(R.id.webView1);
    	WebSettings settings=webView.getSettings();
    	settings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new webViewClient()); 
        this.webView.loadUrl(URL); 
	}
	   class webViewClient extends WebViewClient{ 
	       //重写shouldOverrideUrlLoading方法，使点击链接后不使用其他的浏览器打开。 
	    @Override 
	    public boolean shouldOverrideUrlLoading(WebView view, String url) { 
	        view.loadUrl(url); 
	        //如果不需要其他对点击链接事件的处理返回true，否则返回false 
	        return true; 
	    } 
	   } 
	}



