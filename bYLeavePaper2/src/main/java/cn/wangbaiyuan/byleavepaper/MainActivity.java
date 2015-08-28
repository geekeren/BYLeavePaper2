package cn.wangbaiyuan.byleavepaper;

import com.baidu.android.pushservice.CustomPushNotificationBuilder;
import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;

import cn.wangbaiyuan.byleavepaper.R;
import android.app.Notification;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Audio;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioButton;

public class MainActivity extends FragmentActivity implements OnClickListener{
	public static final int TAB_HOME = 0;
	public static final int TAB_CATAGORY = 1;
	public static final int TAB_CAR = 2;
	public static final int TAB_ME = 3;

	private ViewPager viewPager;
	private RadioButton main_tab_home, main_tab_catagory, main_tab_car,
			main_tab_me;
		@Override  
	    public boolean onKeyDown(int keyCode, KeyEvent event) {  
	    if (keyCode == KeyEvent.KEYCODE_BACK) {  
	    	//moveTaskToBack(true);
	        return true;  
	    }  
	    return super.onKeyDown(keyCode, event);  
		}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragmentmain);
		getIntent().getStringExtra("userId");
		initView();
		addListener();
		
	}

	private void initView() {
		viewPager = (ViewPager) findViewById(R.id.viewpager);
		main_tab_home = (RadioButton) findViewById(R.id.main_tab_home);
		main_tab_catagory = (RadioButton) findViewById(R.id.main_tab_catagory);
		main_tab_car = (RadioButton) findViewById(R.id.main_tab_car);
		main_tab_me = (RadioButton) findViewById(R.id.main_tab_more);
		main_tab_home.setOnClickListener(this);
		main_tab_catagory.setOnClickListener(this);
		main_tab_car.setOnClickListener(this);
		main_tab_me.setOnClickListener(this);
		
		FragmentAdapter adapter = new FragmentAdapter(
				getSupportFragmentManager());
		viewPager.setAdapter(adapter);
	}

	private void addListener() {
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int id) {
				switch (id) {
				case TAB_HOME:
					main_tab_home.setChecked(true);
					break;
				case TAB_CATAGORY:
					main_tab_catagory.setChecked(true);
					break;
				case TAB_CAR:
					main_tab_car.setChecked(true);
					break;
				case TAB_ME:
					main_tab_me.setChecked(true);
					break;

				default:
					break;
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.main_tab_home:
			viewPager.setCurrentItem(TAB_HOME);
			break;
		case R.id.main_tab_catagory:
			viewPager.setCurrentItem(TAB_CATAGORY);
			break;
		case R.id.main_tab_car:
			viewPager.setCurrentItem(TAB_CAR);
			break;
		case R.id.main_tab_more:
			viewPager.setCurrentItem(TAB_ME);
			break;

		default:
			break;
		}		
	}
	 @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        
	        // Inflate the menu; this adds items to the action bar if it is present.
	        getMenuInflater().inflate(R.menu.main, menu);
			if(BYLeavePaper.getUserTypeId().equals("0")){
	        getMenuInflater().inflate(R.menu.add, menu);
			}
	        return true;
	    }

	    @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	        // Handle action bar item clicks here. The action bar will
	        // automatically handle clicks on the Home/Up button, so long
	        // as you specify a parent activity in AndroidManifest.xml.
	        int id = item.getItemId();
	        switch(id){
	        case R.id.action_exit:
	        	finish();
	        	break;
	        case R.id.me_bypaper:
	        	Intent intent=new Intent(MainActivity.this,Page_about.class);
	        	startActivity(intent);
	        	break;
	        case R.id.action_add:
	        	Intent intent1=new Intent(MainActivity.this,Addpaper.class);
	        	startActivity(intent1);
	        	break;
	        }
	      
	        return super.onOptionsItemSelected(item);
	    }
}
