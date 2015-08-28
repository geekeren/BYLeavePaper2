package cn.wangbaiyuan.byleavepaper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.wangbaiyuan.byleavepaper.R;
import cn.wangbaiyuan.tools.BYLPtools;
import cn.wangbaiyuan.tools.BYhttpPost;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

public class LeavePaperFragment extends Fragment {

	private static ArrayList<HashMap<String, String>> msgarraylist = new ArrayList<HashMap<String, String>>();
	private static BaseAdapter adapter;
	private static ListView papers;
	private static final int ITEM_view = 0;
	private static final int ITEM_approve = 1;
	private static final int ITEM_Refuse = 2;
	private static final int ITEM_Share = 3;
	int position ;
	private String message;
	final Handler hander = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (msg.what == 1234) {
				adapter = new BaseAdapter() {
					private Context mContext = getActivity();

					public int getCount() {
						// 设置绘制数量
						return msgarraylist.size();
					}

					public Object getItem(int position) {
						return position;
					}

					public long getItemId(int position) {
						return position;
					}

					public View getView(int position, View convertView,
							ViewGroup parent) {
						TextView title;
						TextView teacher;

						TextView content;
						TextView time;
						TextView creatTime;
						TextView status;

						convertView = LayoutInflater.from(mContext).inflate(
								R.layout.paper_item, null);
						title = (TextView) convertView
								.findViewById(R.id.paper_title);
						content = (TextView) convertView
								.findViewById(R.id.paper_content);
						teacher = (TextView) convertView
								.findViewById(R.id.paper_teacher);
						time = (TextView) convertView
								.findViewById(R.id.paper_time);
						creatTime = (TextView) convertView
								.findViewById(R.id.creatTime);
						status = (TextView) convertView
								.findViewById(R.id.paper_status);
						creatTime.setText(msgarraylist.get(position).get(
								"creatTime") == null ? "未知" : BYLPtools.TimeToString(msgarraylist
								.get(position).get("creatTime")));
						// 标题
						title.setText(msgarraylist.get(position).get(
								"CourseName") == null ? "未知" : msgarraylist.get(position).get(
										"name")+":"+msgarraylist
								.get(position).get("CourseName"));
						teacher.setText(msgarraylist.get(position).get(
								"teachername") == null ? "未知" : msgarraylist
								.get(position).get("teachername"));
						// 原因
						content.setText(msgarraylist.get(position).get(
								"Content"));
						// 时间
						time.setText(BYLPtools.TimeToString(msgarraylist.get(position)
								.get("StartTime"))
								+ "到"
								+ BYLPtools.TimeToString(msgarraylist.get(position).get("EndTime")));
						status.setText(BYLPtools.statusToString(msgarraylist
								.get(position).get("status")));
						status.setTextColor(BYLPtools.statusColor(msgarraylist
								.get(position).get("status")));
						// Log.e("bypaper",
						// msgarraylist.get(position).get("EndTime"));
						return convertView;
					}

				};
				papers.setAdapter(adapter);
			} else if (msg.what == 12340) {
				Toast.makeText(getActivity(), "当前网络不可用", Toast.LENGTH_SHORT)
						.show();
			}else if (msg.what == 4001) {
				new Thread(requstRecords).start();
				Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT)
				.show();

	}

		}
	};
	final Runnable requstRecords = new Runnable() {

		@Override
		public void run() {
			Map<String, String> params = new HashMap<String, String>();
			params.put("userid", BYLeavePaper.getUserId());

			Log.e("bypaper", BYLeavePaper.getUserId());
			String reply;
			String code = "0";
			if (BYLPtools.isNetworkConnected(getActivity())) {
				try {
					final BYhttpPost LoginClient = new BYhttpPost(
							BYLeavePaper.getHosturl(), "utf-8");
					reply = LoginClient.sendHttpClientPOSTRequest(
							"requstRecord.php", params);

					JSONObject Json = new JSONObject(reply);

					code = Json.getString("code");
					Log.e("bypaper",reply);
					if (code.equals("1")) {

						JSONArray message = Json.getJSONArray("message");
						//Log.e("bypaper", message.length() + "");
						msgarraylist.clear();
						for (int i = 0; i < message.length(); i++) {
							JSONObject jsonObj = message.getJSONObject(i);
							HashMap<String, String> msgs = new HashMap<String, String>();
							msgs.put("name", jsonObj.getString("name"));
							Log.e("bypaper", jsonObj.getString("name"));
							msgs.put("CourseName",
									jsonObj.getString("CourseName"));
							Log.e("bypaper", jsonObj.getString("CourseName"));
							msgs.put("Content", jsonObj.getString("Content"));
							Log.e("bypaper", jsonObj.getString("Content"));
							msgs.put("StartTime",
									jsonObj.getString("StartTime"));
							Log.e("bypaper", jsonObj.getString("StartTime"));
							
							msgs.put("creatTime",
									jsonObj.getString("creatTime"));
							Log.e("bypaper", jsonObj.getString("creatTime"));
							
							msgs.put("EndTime", jsonObj.getString("EndTime"));
							Log.e("bypaper", jsonObj.getString("EndTime"));
							
							msgs.put("CourseName",
									jsonObj.getString("CourseName"));
							Log.e("bypaper", jsonObj.getString("CourseName"));
							
							
							msgs.put("status", jsonObj.getString("status"));
							Log.e("bypaper", jsonObj.getString("status"));
							
							msgs.put("teachername", jsonObj.getString("teachername"));
							Log.e("bypaper", jsonObj.getString("teachername"));
							
							msgs.put("Recordid", jsonObj.getString("Recordid"));
							Log.e("bypaper", jsonObj.getString("Recordid"));
							msgarraylist.add(msgs);
							
							// Log.e("bypaper", msgarraylist.size()+"");
						}
						hander.sendEmptyMessage(1234);
					}
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else {
				hander.sendEmptyMessage(12340);
			}

		}

	};
	public class approveLeave extends Thread{

		private int action; 
		public approveLeave(int iaction) 
		{ 
		this.action = iaction; 
		} 

		@Override
		public void run() {
			Map<String, String> params = new HashMap<String, String>();
			params.put("Recordid", msgarraylist.get(position).get("Recordid"));
			params.put("action", this.action+"");
			
			Log.e("bypaper",msgarraylist.get(position).get("Recordid"));
			Log.e("bypaper",position+"");
			final BYhttpPost LoginClient = new BYhttpPost(
					BYLeavePaper.getHosturl(), "utf-8");
			String reply;
			try {
				reply = LoginClient.sendHttpClientPOSTRequest(
						"acceptLeave.php", params);
				JSONObject Json = new JSONObject(reply);
				String code = Json.getString("code");
				
				if(code.equals("1")){
					message = Json.getString("email");
					hander.sendEmptyMessage(4001);
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
				

		}

	};
	
	// 长按时显示的菜单
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		menu.setHeaderTitle("请选择操作");

		menu.add(0, ITEM_view, 0, "查看详情");
		menu.add(0, ITEM_Share, 3, "分享假条");
		if(BYLeavePaper.getUserTypeId().equals("1")){
		menu.add(0, ITEM_approve, 1, "快速批准");
		menu.add(0, ITEM_Refuse, 2, "快捷拒绝");
	}
	}

	// 响应编辑和删除事件处理
	public boolean onContextItemSelected(MenuItem item) {
		ContextMenuInfo menuInfo = (ContextMenuInfo) item.getMenuInfo();       
	       AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();   
	  
		switch (item.getItemId()) {
		case ITEM_Refuse:
			position = (int)info.position;
			if(msgarraylist.get(position).get("status").equals("0")){
				Toast.makeText(getActivity(), "已拒绝，不要重复操作！", Toast.LENGTH_SHORT).show();
			}
			else 
			new approveLeave(0).start();
			break;
		case ITEM_approve:
			position = (int)info.position;
			if(msgarraylist.get(position).get("status").equals("1")){
				Toast.makeText(getActivity(), "已批准，不要重复操作！", Toast.LENGTH_SHORT).show();
			}
			else 
				new approveLeave(1).start();
			break;
			//分享请假条
		case ITEM_Share:
			position = (int)info.position;
			Intent shareIntent = new Intent();
	        shareIntent.setAction(Intent.ACTION_SEND);
	        HashMap<String,String> paperitem=new  HashMap<String,String>();
	        paperitem=msgarraylist.get(position);
	        String shareText="敬爱的辅导员：\n我是"+paperitem.get("name")
	        		+ "由于"+paperitem.get("Content")+",特向您提出请假要求，时间是"
	        		+paperitem.get("StartTime")+" 到  "+paperitem.get("EndTime")
	        		+"。期间会无法上"+paperitem.get("teachername")+"老师的课程：《"
	        		+paperitem.get("CourseName")
	        		+ "》，我保证在课后完成课程任务，望您批准！\n请假人："+paperitem.get("name")
	        		+"-"+paperitem.get("creatTime");
	        
	        shareIntent.putExtra(Intent.EXTRA_TEXT,shareText);
	        
	        
	        shareIntent.setType("text/plain");
	        //设置分享列表的标题，并且每次都显示分享列表
	        startActivity(Intent.createChooser(shareIntent, "分享请假条到"));
			break;
		}
	
			
		return false;
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		System.out.println("HomeFragment");

		return inflater.inflate(R.layout.main_home, container, false);

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		papers = (ListView) getView().findViewById(R.id.papers);
		// 设置长按事件
		registerForContextMenu(papers);
		// papers.setOpapersnItemLongClickListener(new OnItemLongClickListener()
		// {
		//
		// @Override
		// public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
		// int position, long arg3) {
		// if(BYLeavePaper.getUserTypeId().equals("1")){
		//
		// }
		//
		//
		// return false;
		// }
		//
		//
		// });
		new Thread(requstRecords).start();
	}

}
