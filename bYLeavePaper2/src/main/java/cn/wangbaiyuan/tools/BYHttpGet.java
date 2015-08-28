package cn.wangbaiyuan.tools;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.util.Log;
import android.widget.Toast;
 
 
/**
 * @author 王柏元
 *
 */

public class BYHttpGet {
  
    public BYHttpGet() {
        // TODO Auto-generated constructor stub
    }
      
    /**
     * @param url_path json的URL
     * @return
     */
    public static String getJsonContent(String url_path){
         
        String jsonString = "";
        try {
            URL url = new URL(url_path);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(3000);
            connection.setDoInput(true);  //从服务器获得数据
            connection.connect();
            int responseCode = connection.getResponseCode();            
            Log.d("log_tag",responseCode+"");
            if (200 == responseCode) {
                jsonString = changeInputStream(connection.getInputStream());
                
            }
//            else{
//            	 jsonString = responseCode+"";
//            }
         
             
        } catch (Exception e) {
        	Log.d("log_tag",e.toString());
        	jsonString ="获取服务器信息出错";
            // TODO: handle exception
        }
         
    //
        return jsonString;
    }
 
    private static String changeInputStream(InputStream inputStream){
        // TODO Auto-generated method stub
        String  jsonString ="";
         
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int len = 0;
        try {
			while((len=inputStream.read(data))!=-1){
			    outputStream.write(data, 0, len);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         
        jsonString = new String(outputStream.toByteArray());
        return jsonString;
    }
     
}
