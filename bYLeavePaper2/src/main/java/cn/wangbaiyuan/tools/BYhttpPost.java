package cn.wangbaiyuan.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;

import android.util.Log;
import android.widget.Toast;

@SuppressWarnings("deprecation")
/**
 * 一个httppost的获取网络数据工具类，完成提交post请求并获取服务器应答的字符串
 * @author 王柏元
 *
 */
public class BYhttpPost {
private String hostUrl="http://wangbaiyuan.cn/";
private String encode;
	/**
	 *
	 * @param iurl 根地址，你的服务器加你的项目文件夹地址
	 * @param iencode 编码格式
	 */
public BYhttpPost(String iurl,String iencode){
	hostUrl=iurl;
	encode=iencode;
}
	/**
	 *
	 * @param path  相对于根地址的文件地址，你向哪个URL发起请求
	 * @param params MAP格式的键值对
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
public String sendHttpClientPOSTRequest(String path, Map<String, String> params) throws ClientProtocolException, IOException {
	  List<NameValuePair> pairs = new ArrayList<NameValuePair>();//鐎涙ɑ鏂佺拠閿嬬湴閸欏倹鏆�
	  BufferedReader in = null;
	  Log.e("bypaper", hostUrl+"/"+path);
	  for(Map.Entry<String, String> entry:params.entrySet()){
	   pairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
	  }
	//防止客户端传递过去的参数发生乱码，需要对此重新编码成UTF-8
	  UrlEncodedFormEntity entity = new UrlEncodedFormEntity(pairs,encode);
	  HttpPost httpPost = new HttpPost(hostUrl+"/"+path);
	  Log.e("bypaper", hostUrl+"/"+path);

	  httpPost.setEntity(entity);

	 HttpClient client = new DefaultHttpClient();
	 client.getParams().setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 15000);
	  HttpResponse response = client.execute(httpPost);
	  Log.e("bypaper","client.execute(httpPost)");
	String result="服务器没有应答";
	  if(response.getStatusLine().getStatusCode() == 200){
		  in = new BufferedReader(new InputStreamReader(response.getEntity()
                  .getContent()));
		  StringBuffer sb = new StringBuffer("");
          String line = "";
          while ((line = in.readLine()) != null) {
              sb.append(line);
          }
          in.close();
          result = sb.toString();

	  }
	  else if(response.getStatusLine().getStatusCode() ==404){
		  Toast.makeText(null, "服务器获取数据失败", Toast.LENGTH_SHORT);
	  }
	  Log.e("bypaper",result);
	  return result;
	 }


}
