package cn.wangbaiyuan.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.format.DateFormat;
import android.util.Log;

public class BYLPtools {
	public static String statusToString(String status) {
		String result = "未知";
		switch (status) {
		case "0":
			result = "等待辅导员批准";
			break;
		case "1":
			result = "辅导员已批准";
			break;
		case "2":
			result = "任课老师已确认";
			break;
		}

		return result;

	}
	
	public static int statusColor(String status) {
		int result = 0;
		switch (status) {
		case "0":
			result =Color.rgb(255, 0, 0);
			break;
		case "1":
			result =Color.rgb(0, 255, 20);
			break;
		case "2":
			result =Color.rgb(255, 0, 255);
			break;
		}

		return result;

	}

	@SuppressWarnings("deprecation")
	public static String TimeToString(String time) {
		String timeString=time;
		try {
			SimpleDateFormat dateFormat;

			dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat dateFormat2 = new SimpleDateFormat("HH:mm");
			SimpleDateFormat dateFormat3 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			dateFormat.setLenient(false);
			Date timeDate = dateFormat.parse(time);
//			Long timeStamp = timeDate.getTime();
//			Long currentStamp = new Date().getTime();
//			Long timetogo = currentStamp - timeStamp;
			Calendar calDateA = Calendar.getInstance();
			Calendar calDateB = Calendar.getInstance();
			calDateB.setTime(timeDate);
			int year=calDateA.get(Calendar.YEAR);
			int mon=calDateA.get(Calendar.MONTH);
			int day=calDateA.get(Calendar.DATE);
			int currentyear=calDateB.get(Calendar.YEAR);
			int currentmon=calDateB.get(Calendar.MONTH);
			int currentday=calDateB.get(Calendar.DATE);
			Log.e("bypaper",currentyear+" "+currentmon+" "+year+" "+day+"　");
			if (year==currentyear&&mon==currentmon&&day==currentday) {
				timeString="今天"+dateFormat2.format(timeDate);
			}else if (year==currentyear&&mon==currentmon&&(day-currentday)==1) {
				timeString="昨天"+dateFormat2.format(timeDate);
			}else if (year==currentyear&&mon==currentmon&&(day-currentday)==-1) {
				timeString="明天"+dateFormat2.format(timeDate);
			}else {
				timeString=dateFormat3.format(timeDate);
			}
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}// util类型

		return timeString;

	}

	public static boolean isNetworkConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mNetworkInfo = mConnectivityManager
					.getActiveNetworkInfo();
			if (mNetworkInfo != null) {
				return mNetworkInfo.isAvailable();
			}
		}
		return false;
	}
}
