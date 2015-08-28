package cn.wangbaiyuan.byleavepaper;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;

import cn.wangbaiyuan.tools.BYhttpPost;
import cn.wangbaiyuan.tools.ByNoticeDialog;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class Addpaper extends Activity {
	TextView start;
	TextView End;
	EditText editteacherid;
	EditText editcontent;
	EditText editcourseid;
	String message;
	private ByNoticeDialog notice;
	Handler hander = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (msg.what == 3121) {
				notice.setTitle("请假条提交成功");
				notice.setMessage(message);

			} else if (msg.what == 3120) {
				notice.dismiss();
				Toast.makeText(Addpaper.this, "添加失败！", Toast.LENGTH_SHORT)
						.show();

			} else if (msg.what == 3123) {
				
				notice.setMessage("正在等待服务器反馈信息……");
				// Toast.makeText(Addpaper.this, "", Toast.LENGTH_SHORT).show();

			}else if (msg.what == 3124) {
				notice.dismiss();

				Addpaper.this.finish();

			}else if (msg.what == 3122) {
				notice.setTitle("请假条提交异常");
				notice.setMessage(message);
				
			} 

		}
	};
	final Runnable addpaper = new Runnable() {

		@Override
		public void run() {

			Map<String, String> params = new HashMap<String, String>();

			params.put("userid", BYLeavePaper.getUserId());
			Log.e("bypaper", BYLeavePaper.getUserId());
			params.put("TeacherId", editteacherid.getText().toString());
			Log.e("bypaper", editteacherid.getText().toString());
			params.put("courseid", editcourseid.getText().toString());
			Log.e("bypaper", editcourseid.getText().toString());
			params.put("Content", editcontent.getText().toString());
			Log.e("bypaper", editcontent.getText().toString());
			params.put("StartTime", start.getText().toString());
			params.put("EndTime", End.getText().toString());
			Log.e("bypaper", BYLeavePaper.getUserId() + " "
					+ editteacherid.getText().toString() + " " + ""
					+ editcontent.getText().toString() + "");
			hander.sendEmptyMessage(3123);
			BYhttpPost addPaperClient = new BYhttpPost(
					BYLeavePaper.getHosturl(), "utf-8");
			try {
				
				String reply = addPaperClient.sendHttpClientPOSTRequest(
						"addPaper.php", params);
				JSONObject Json = new JSONObject(reply);
				String code = (Json.has("code")? Json.getString("code"):null);
				if (code.equals("1")) {
					message = Json.getString("email");
					hander.sendEmptyMessage(3121);
					Thread.sleep(2000);
					hander.sendEmptyMessage(3124);
				} else if (code.equals("0"))
					hander.sendEmptyMessage(3120);
				else
					hander.sendEmptyMessage(3122);
			
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				// hander.sendEmptyMessage(0120);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			} catch (JSONException e) {
				
				hander.sendEmptyMessage(3120);
			
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_addpaper);
		start = (TextView) findViewById(R.id.timeStart);
		End = (TextView) findViewById(R.id.timeEnd);
		editteacherid = (EditText) findViewById(R.id.paper_teacherid);

		editcontent = (EditText) findViewById(R.id.paper_editcontent);
		editcourseid = (EditText) findViewById(R.id.paper_courseid);
		final Calendar c = Calendar.getInstance();
		start.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				final TimePickerDialog time = new TimePickerDialog(
						Addpaper.this,
						new TimePickerDialog.OnTimeSetListener() {
							@Override
							public void onTimeSet(TimePicker view,
									int hourOfDay, int minute) {
								// TODO Auto-generated method stub
								Log.e("bypaper", Calendar.YEAR + " "
										+ Calendar.MONTH + " "
										+ Calendar.DAY_OF_MONTH + "");
								c.set(c.get(Calendar.YEAR),
										c.get(Calendar.MONTH),
										c.get(Calendar.DAY_OF_MONTH),
										hourOfDay, minute);
								start.setText(start.getText() + " "
										+ DateFormat.format("HH:mm:ss", c));
							}
						}, c.get(Calendar.HOUR), c.get(Calendar.MINUTE), false);

				DatePickerDialog dialog = new DatePickerDialog(Addpaper.this,
						new DatePickerDialog.OnDateSetListener() {

							@Override
							public void onDateSet(DatePicker view, int year,
									int monthOfYear, int dayOfMonth) {
								c.set(year, monthOfYear, dayOfMonth);
								start.setText(DateFormat.format("yyy-MM-dd", c));
								time.show();
							}
						}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c
								.get(Calendar.DAY_OF_MONTH));
				dialog.show();
			}

		});

		End.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				final TimePickerDialog time = new TimePickerDialog(
						Addpaper.this,
						new TimePickerDialog.OnTimeSetListener() {
							@Override
							public void onTimeSet(TimePicker view,
									int hourOfDay, int minute) {
								// TODO Auto-generated method stub
								Log.e("bypaper", Calendar.YEAR + " "
										+ Calendar.MONTH + " "
										+ Calendar.DAY_OF_MONTH + "");
								c.set(c.get(Calendar.YEAR),
										c.get(Calendar.MONTH),
										c.get(Calendar.DAY_OF_MONTH),
										hourOfDay, minute);
								End.setText(End.getText() + " "
										+ DateFormat.format("HH:mm:ss", c));
							}
						}, c.get(Calendar.HOUR), c.get(Calendar.MINUTE), false);

				DatePickerDialog dialog = new DatePickerDialog(Addpaper.this,
						new DatePickerDialog.OnDateSetListener() {

							@Override
							public void onDateSet(DatePicker view, int year,
									int monthOfYear, int dayOfMonth) {
								c.set(year, monthOfYear, dayOfMonth);
								End.setText(DateFormat.format("yyy-MM-dd", c));
								time.show();
							}
						}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c
								.get(Calendar.DAY_OF_MONTH));
				dialog.show();
			}

		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_paper, menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		switch (id) {
		case R.id.action_yes:
			notice = new ByNoticeDialog(Addpaper.this, "提交请假条",
					"正在BY请假条系统服务器提交……");
			// notice=new ByNoticeDialog(Addpaper.this);
			notice.show();
			new Thread(addpaper).start();
			break;
		}

		return super.onOptionsItemSelected(item);
	}
}
