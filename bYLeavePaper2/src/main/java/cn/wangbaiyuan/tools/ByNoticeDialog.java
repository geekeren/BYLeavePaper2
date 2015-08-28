package cn.wangbaiyuan.tools;

import android.app.ProgressDialog;
import android.content.Context;

public class ByNoticeDialog extends ProgressDialog {

	public ByNoticeDialog(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	public ByNoticeDialog(Context context,String title,String noticecontent) {
		super(context);
		setTitle(title);
		setProgressStyle(ProgressDialog.STYLE_SPINNER);  
        setMessage(noticecontent);
        setCancelable(false);
		// TODO Auto-generated constructor stub
	}
}
