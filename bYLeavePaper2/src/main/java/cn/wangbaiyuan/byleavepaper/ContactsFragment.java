package cn.wangbaiyuan.byleavepaper;

import cn.wangbaiyuan.byleavepaper.R;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ExpandableListAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ContactsFragment extends Fragment{
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		System.out.println("CategoryFragment");
		//创建一个BaseExpandableListAdapter对象
				
		
		return inflater.inflate(R.layout.main_contacts, container, false);
		
		
		
	}
}
