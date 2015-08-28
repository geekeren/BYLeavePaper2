package cn.wangbaiyuan.byleavepaper;

import cn.wangbaiyuan.tools.BYupdate;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TableRow;
import android.widget.TextView;

public class Page_about extends Activity {
String updateUrl="http://wangbaiyuan.cn/others/update.php?id=2";
String current_version="http://wangbaiyuan.cn/others/update.php?id=2";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_page_about);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
		    Window window = getWindow();
		    //requestWindowFeature(Window.FEATURE_NO_TITLE);
		    window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
		    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		}
OnClickListener about=new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(Page_about.this, BYbrowserActivity.class);
			switch(v.getId()){

			case R.id.me_homepage:
				intent.putExtra("URL", "http://wangbaiyuan.cn");
				intent.putExtra("title", "王柏元的博客");
				startActivity(intent);
				break;
			case R.id.me_bypaper:
				intent.putExtra("URL", getResources().getString(R.string.introUrl));
				intent.putExtra("title", "BY请假条介绍与反馈页");
				startActivity(intent);
				break;
			case R.id.me_checkupdate:
//				intent.putExtra("URL", "http://wangbaiyuan.cn");
//				intent.putExtra("title", "意见反馈");
				new BYupdate(Page_about.this, updateUrl,current_version).checkupdate();
				break;
			case R.id.me_evaluation:
				Uri uri = Uri.parse("market://details?id="+getPackageName());  
				Intent intent2 = new Intent(Intent.ACTION_VIEW,uri);  
				intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  
				startActivity(intent2); 
				break;
			case R.id.me_suggestion:
				intent.putExtra("URL",getResources().getString(R.string.suggestionUrl));
				
				intent.putExtra("title", "意见反馈");
				startActivity(intent);
				break;
			}
			
			}
			
		};
		
		 PackageManager packageManager = getPackageManager();
	        // getPackageName()是你当前类的包名，0代表是获取版本信息
	        PackageInfo packInfo;
			try {
				packInfo = packageManager.getPackageInfo(getPackageName(),0);
		        ApplicationInfo applicationInfo = packageManager.getApplicationInfo(getPackageName(), 0); 
		        current_version = packInfo.versionName;
		        String applicationName = (String) packageManager.getApplicationLabel(applicationInfo);
			} catch (NameNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	        
		TableRow homepage=(TableRow)findViewById(R.id.me_homepage);
		TableRow checkupdate=(TableRow)findViewById(R.id.me_checkupdate);
		TableRow evaluation=(TableRow)findViewById(R.id.me_evaluation);
		TableRow suggestion=(TableRow)findViewById(R.id.me_suggestion);
		TableRow bypaper=(TableRow)findViewById(R.id.me_bypaper);
		TextView versionlabel=(TextView)findViewById(R.id.me_version);
		homepage.setOnClickListener(about);
		checkupdate.setOnClickListener(about);
		suggestion.setOnClickListener(about);
		bypaper.setOnClickListener(about);
		evaluation.setOnClickListener(about);
		versionlabel.setText("当前版本:"+current_version);
	}
}
