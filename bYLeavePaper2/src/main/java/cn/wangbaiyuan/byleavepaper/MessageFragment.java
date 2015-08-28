package cn.wangbaiyuan.byleavepaper;

import cn.wangbaiyuan.byleavepaper.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MessageFragment extends Fragment{
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		System.out.println("CarFragment");
		return inflater.inflate(R.layout.main_message, container, false);
	}
}
