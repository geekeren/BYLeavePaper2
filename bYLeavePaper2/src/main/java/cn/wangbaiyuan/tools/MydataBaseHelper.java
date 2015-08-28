package cn.wangbaiyuan.tools;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MydataBaseHelper extends SQLiteOpenHelper{
final String creatPersonSQL="create table persons(name varchar(20) "
		+ "primary key,phone varchar(30))";
	public MydataBaseHelper(Context context, String name,
			int version) {
		super(context, name,null, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(creatPersonSQL);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
	

}
