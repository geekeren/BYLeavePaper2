package cn.wangbaiyuan.byleavepaper;

import cn.wangbaiyuan.byleavepaper.R;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.TableRow;
import android.widget.TextView;

public class MeFragment extends Fragment{
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		System.out.println("MoreFragment");
		return inflater.inflate(R.layout.main_me, container, false);
	
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState); 
		TableRow about=(TableRow)getView().findViewById(R.id.me_about);
		TextView userName=(TextView)getView().findViewById(R.id.me_name);
		TextView userType=(TextView)getView().findViewById(R.id.me_type);
		userName.setText(BYLeavePaper.getUserName());
		userType.setText(BYLeavePaper.getUserType());
		about.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(getActivity(),Page_about.class);
				startActivity(intent);
				
			}
		});
	}
}
