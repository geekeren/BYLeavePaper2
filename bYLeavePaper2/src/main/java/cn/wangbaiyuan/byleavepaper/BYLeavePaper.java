package cn.wangbaiyuan.byleavepaper;

import cn.wangbaiyuan.tools.ByNoticeDialog;
import android.app.Application;

public class BYLeavePaper extends Application{
private static String hosturl="http://wangbaiyuan.cn/others/BYLeaverPaper";
private static String userId="";
private static String name;
private static String typeid;
public static ByNoticeDialog notice;


public static String getHosturl(){
	return hosturl;
	
}

public void setUserId(String iuserId){
	userId=iuserId;
}

public static String getUserId(){
	return userId;
}

public void setName(String iname) {
	name=iname;
	
}
public static String getUserName(){
	return name;
}

public void setTypeId(String itypeid) {
	typeid=itypeid;
	
}
public static String getUserTypeId(){
	return typeid;
}
public static String getUserType(){
	String UserType="";
	if(typeid.equals("0"))
		UserType="学生";
	else if(typeid.equals("1"))
	UserType="辅导员";
	else if(typeid.equals("2"))
	UserType="教师";
	return UserType;
}

}
