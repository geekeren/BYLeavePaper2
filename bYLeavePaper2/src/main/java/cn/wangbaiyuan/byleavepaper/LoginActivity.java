package cn.wangbaiyuan.byleavepaper;
import java.io.IOException;
import java.net.MalformedURLException;



import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;

import com.baidu.android.pushservice.CustomPushNotificationBuilder;
import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;

import cn.wangbaiyuan.tools.BYhttpPost;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.app.Notification;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore.Audio;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {
	SharedPreferences preferences;
	SharedPreferences.Editor editor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loginpage);
		PushManager.startWork(getApplicationContext(),

                PushConstants.LOGIN_TYPE_API_KEY,"AISwINQrcxY8HAslVutBVLRR");
//	    Resources resource = this.getResources();
//	    String pkgName = this.getPackageName();
//		PushManager.startWork(getApplicationContext(),PushConstants.LOGIN_TYPE_API_KEY,"AISwINQrcxY8HAslVutBVLRR");
//		CustomPushNotificationBuilder cBuilder = new CustomPushNotificationBuilder(
//                resource.getIdentifier(
//                        "notification_custom_builder", "layout", pkgName),
//                resource.getIdentifier("notification_icon", "id", pkgName),
//                resource.getIdentifier("notification_title", "id", pkgName),
//                resource.getIdentifier("notification_text", "id", pkgName));
//        cBuilder.setNotificationFlags(Notification.FLAG_AUTO_CANCEL);
//        cBuilder.setNotificationDefaults(Notification.DEFAULT_VIBRATE);
//        cBuilder.setStatusbarIcon(this.getApplicationInfo().icon);
//       cBuilder.setLayoutDrawable(resource.getIdentifier(
//              "simple_notification_icon", "drawable", pkgName));
//        cBuilder.setNotificationSound(Uri.withAppendedPath(
//                Audio.Media.INTERNAL_CONTENT_URI, "6").toString());
//        // 推送高级设置，通知栏样式设置为下面的ID
//        PushManager.setNotificationBuilder(this, 1, cBuilder);
	
		
		Button login=(Button)findViewById(R.id.login_btn_login);
		Button register=(Button)findViewById(R.id.btn_register);
		preferences=getSharedPreferences("by_leavepaper",MODE_WORLD_READABLE);
		editor=preferences.edit();
		String iname=preferences.getString("userid", null);
		String ipassword=preferences.getString("password", null);
		final BYLeavePaper byleavepaper=(BYLeavePaper)getApplication();
		final EditText nameEdit=(EditText)findViewById(R.id.login_edit_account);
		nameEdit.setText(iname);
		final EditText passwordEdit=(EditText)findViewById(R.id.login_edit_pwd);
		passwordEdit.setText(ipassword);
		final CheckBox checksave=(CheckBox)findViewById(R.id.login_cb_savepwd);
		final BYhttpPost LoginClient=new BYhttpPost(byleavepaper.getHosturl(),"utf-8");
		final Handler hander=new Handler(){
			@Override
			public void handleMessage(Message msg){
				if(msg.what==012){
					Toast.makeText(LoginActivity.this, "登录成功！", Toast.LENGTH_SHORT).show();
					Intent intent=new Intent(LoginActivity.this,MainActivity.class);
					intent.putExtra("userId", byleavepaper.getUserId());
					startActivity(intent);
					LoginActivity.this.finish();
					}
				else if(msg.what==0123)
				Toast.makeText(LoginActivity.this, "用户名或密码错误！", Toast.LENGTH_SHORT).show();
				else if(msg.what==0120)
				Toast.makeText(LoginActivity.this, "登录超时，请检查网络设置", Toast.LENGTH_SHORT).show();	
				
		}};
		final Runnable loginRun = new Runnable(){ 
			  
			@Override  
			public void run() { 
				Map<String, String> params = new HashMap<String, String>();
				
				params.put("userid", nameEdit.getText().toString());
				params.put("password", passwordEdit.getText().toString());
 
					try {
						String reply=LoginClient.sendHttpClientPOSTRequest("login.php", params);
						JSONObject Json=new JSONObject(reply);
						String code=Json.getString("code");
						if(code.equals("1")&&Json.has("userId"))
						{String userId=Json.getString("userId");
						String name=Json.getString("name");
						String typeid=Json.getString("typeid");
							byleavepaper.setUserId(userId);
							byleavepaper.setName(name);
							byleavepaper.setTypeId(typeid);
							hander.sendEmptyMessage(012);}
						else if(code.equals("0"))
							hander.sendEmptyMessage(0123);
						else
							hander.sendEmptyMessage(0120);
							
					} catch (ClientProtocolException e) {
						// TODO Auto-generated catch block
						hander.sendEmptyMessage(0120);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					
				} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		};
		register.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
				startActivity(intent);
			}
		});
		login.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(TextUtils.isEmpty(nameEdit.getText()))
					Toast.makeText(LoginActivity.this, "用户名或密码为空！", Toast.LENGTH_SHORT).show();
				else{
					
					if(checksave.isChecked()==true){
						editor.putString("userid",nameEdit.getText().toString());
					editor.putString("password",passwordEdit.getText().toString());
					editor.commit();
					}
					Toast.makeText(LoginActivity.this, "正在登录……", Toast.LENGTH_SHORT).show();
				new Thread(loginRun).start();
				
				}
			}
		});
		
	}
}
