package cn.wangbaiyuan.byleavepaper;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class FragmentAdapter extends FragmentPagerAdapter{
	public final static int TAB_COUNT = 4;
	public FragmentAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int id) {
		switch (id) {
		case MainActivity.TAB_HOME:
			LeavePaperFragment leavePaperFragment = new LeavePaperFragment();
			return leavePaperFragment;
		case MainActivity.TAB_CATAGORY:
			ContactsFragment contactsFragment = new ContactsFragment();
			return contactsFragment;
		case MainActivity.TAB_CAR:
			MessageFragment messageFragment = new MessageFragment();
			return messageFragment;
		case MainActivity.TAB_ME:
			MeFragment meFragment = new MeFragment();
			return meFragment;
		}
		return null;
	}

	@Override
	public int getCount() {
		return TAB_COUNT;
	}
}
