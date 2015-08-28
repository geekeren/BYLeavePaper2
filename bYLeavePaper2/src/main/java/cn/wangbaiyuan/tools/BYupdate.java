package cn.wangbaiyuan.tools;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;
/**
 * ʵ�������Ϣ��չʾ��������¹���
 * @author ����Ԫ
 *
 */
public class BYupdate  {

	String sw_version;
	String current_version;
	String version_des;
	String url_path;
	Context context;
	String download_url;
	/**
	 * 
	 * @param icontext contextһ�������ô����activity
	 * @param url ������·������ַ
	 * @param version �����ǰ�汾��
	 */
	public BYupdate(Context icontext,String url, String version){
		context=icontext;
		url_path=url;
		current_version=version;
	}
	/**
	 * ����������·������ַ
	 * @param url
	 */
	public void setDownload_url(String url){
		url_path=url;
	}
	
	
	Handler hander=new Handler(){
		@Override
		public void handleMessage(Message msg){
			if(msg.what==0x123){
				//version_label.setText("���°汾��"+sw_version);
				Double latest_Version=Double.parseDouble(sw_version);
				if(Double.parseDouble(current_version)<latest_Version)
				{

				AlertDialog.Builder builder=new AlertDialog.Builder(context)
				.setTitle("��ǰ�汾:"+current_version+",���°汾:"+latest_Version)
				.setMessage(version_des);
				builder.setPositiveButton("���ظ���", download_listener);
				builder.setNegativeButton("�Ժ���˵", null);
				builder.create();
				builder.show();
				}
				else
					Toast.makeText(context, "��ǰ�����°汾", Toast.LENGTH_LONG).show();
			}
			else if(msg.what==012)
			Toast.makeText(context, "��װ��������ɣ����밲װ����", Toast.LENGTH_LONG).show();
			else if(msg.what==01244)
				Toast.makeText(context, "���ش��󡭡�", Toast.LENGTH_LONG).show();
	            //���toast���ļ�������Ϊ��������ǵ��̵߳ģ�����Ҫ�������ļ��Ժ�Ż�ִ����һ�䣬�м��ʱ���������������������̻߳�û��ѧ��
			
		}
	};
	/**
	 * ������
	 */
	public void checkupdate(){
	new Thread(checkupdate).start();
	Toast.makeText(context, "���ڼ����¡���", Toast.LENGTH_SHORT).show();
	}
	public OnClickListener download_listener =new OnClickListener() {
		
		@Override
		public void onClick(DialogInterface dialog, int which) { 
			new Thread(download).start();

			Toast.makeText(context, "��ʼ���ء���", Toast.LENGTH_LONG).show();
		}
	};
	/**
	 * �����߳�
	 */
	Runnable download = new Runnable(){ 
		  
			@Override  
			public void run() {  
				String sdcard=Environment.getExternalStorageDirectory()+"/";
				String filepath=sdcard+"BYLeavePaper/";
				
				download_url=(download_url.startsWith("http://"))?download_url:"http://"+download_url;
			    try {
				  URL url = new URL(download_url);
	               //�򿪵�url������
	             HttpURLConnection connection = (HttpURLConnection)url.openConnection();
	              //����Ϊjava IO���֣�������˵�����ȼ���ļ����Ƿ���ڣ��������򴴽�,Ȼ����ļ����ظ����⣬û�п���
	             InputStream istream=connection.getInputStream();
	               String filename=download_url.substring(download_url.lastIndexOf("/")+1);
	         
	               File dir=new File(filepath);
	                if (!dir.exists()) {
	                    dir.mkdir();
	               }
	                File file=new File(filepath+filename);
	               file.createNewFile();	
	               
	               OutputStream output=new FileOutputStream(file);
	           	byte[] buff = new byte[1024];
				int hasRead = 0;
				// ��URL��Ӧ����Դ���ص�����
				while((hasRead = istream.read(buff)) > 0)
				{
					output.write(buff, 0 , hasRead);
				}
	                output.flush();
	                output.close();
	                istream.close();
	                hander.sendEmptyMessage(012);
	                Log.e("log_tag",file.toString());

	                openFile(file);
	            } catch (Exception e) {
	           	 Log.e("log_tag","���ش���:"+e.toString());
	           	hander.sendEmptyMessage(01244);
	           }

				
				
				
       } 
	};
	/**
	 * �������߳�
	 */
	Runnable checkupdate = new Runnable(){  
		  
		@Override  
		public void run() {  
	        String jsonString = BYHttpGet.getJsonContent(url_path);
	        Log.e("bypaperup",jsonString);
	        
	        try {
				JSONObject Json=new JSONObject(jsonString);
				sw_version=Json.getString("sw_version");
				 version_des=Json.getString("sw_description");
				 download_url=Json.getString("sw_url");
				

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				 Log.e("log_tag",e.toString());
			}
	        hander.sendEmptyMessage(0x123);	
		}  
		
		  }; 
/**
 * ��ָ��APK
 * @param file APK·��
 */
private void openFile(File file) {
		        Log.e("OpenFile", file.getName());
		        Intent intent = new Intent();
		        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		        intent.setAction(android.content.Intent.ACTION_VIEW);
		        intent.setDataAndType(Uri.fromFile(file),
		                "application/vnd.android.package-archive");
		        context.startActivity(intent);
  }
}
