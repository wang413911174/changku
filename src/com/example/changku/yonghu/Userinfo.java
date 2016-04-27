package com.example.changku.yonghu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.changku.R;
import com.example.changku.SqlHelpLiteOpenHelper;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Userinfo extends Activity {
	private String id[];
	private String username[];
	private String password[];
	private String telephone[];
	SqlHelpLiteOpenHelper db;
	SQLiteDatabase mDatabase = null;
	private SimpleAdapter mAdapter;
	private ListView mListView;
	private List<Map<String, Object>> mList = new ArrayList<Map<String, Object>>();
	AlertDialog.Builder builder;
	Intent intent=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.userinfolist);
		initView();
		initdata();
		mAdapter = new SimpleAdapter(Userinfo.this, getData(),
				R.layout.userinfolistview, new String[] { "id", "username",
						"password", "telephone" },
				new int[] { R.id.userinfo_id, R.id.userinfo_username,
						R.id.userinfo_password, R.id.userinfo_telephone,
						});
		mListView.setAdapter(mAdapter);
		initEvent();
		builder = new AlertDialog.Builder(this);
	}

	private void initdata() {
		String selectStr = "select * from user_info;";
		Cursor mCursor = mDatabase.rawQuery(selectStr, null);
		mCursor.moveToFirst();
		int count = mCursor.getCount();
		int i = 0;
		id = new String[count];
		username = new String[count];
		password = new String[count];
		telephone = new String[count];
		do {
			if (count > 0) {
				try {
					id[i] = mCursor.getInt(0) + "";
					username[i] = mCursor.getString(1);
					password[i] = mCursor.getString(2);
					telephone[i] = mCursor.getString(3);
					i++;
				} catch (Exception e) {
					Toast.makeText(Userinfo.this, "Userinfo Exception",
							Toast.LENGTH_SHORT).show();
				}
			} else {
				Toast.makeText(Userinfo.this, "无数据", Toast.LENGTH_SHORT).show();
			}
		} while (mCursor.moveToNext());
		mCursor.close();
	}

	private List<Map<String, Object>> getData() {
		for (int j = 0; j < id.length; j++) {
			Map<String, Object> mMap = new HashMap<String, Object>();
			mMap.put("id", id[j]);
			mMap.put("username", username[j]);
			mMap.put("password", password[j]);
			mMap.put("telephone", telephone[j]);
			mList.add(mMap);
		}
		return mList;
	}
	private void initEvent() {
		mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				for(int i=0;i<id.length;i++){
					if(arg2 == i){
						
						builder.setTitle("操作提示！");
						builder.setMessage("请选择操作！");
						final int m =i;
						builder.setPositiveButton("重置", new OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								String updateStr="update user_info set  password='"+ 123456 +"' where _id= '"+ id[m]+"';";
								mDatabase.execSQL(updateStr);
								Toast.makeText(Userinfo.this, "重置成功", Toast.LENGTH_SHORT).show();
								intent= new Intent(Userinfo.this,Userinfo.class);
							}
						});
						builder.setNeutralButton("删除", new OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								//删除语句 需要刷新页面
								String deleteStr="delete from user_info where _id= '"+ id[m]+"';";
								mDatabase.execSQL(deleteStr);
								intent = new Intent(Userinfo.this,Userinfo.class);
							}
						});
						builder.setNegativeButton("取消", new OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
							}
						});
						if(intent!=null){
							startActivity(intent);
							Userinfo.this.finish();
						}
						builder.create().show();
					}
				}
			}
		});
	}

	private void initView() {
		mListView = (ListView) findViewById(R.id.userinfo_list);
		db = new SqlHelpLiteOpenHelper(Userinfo.this, "changku.db", null, 1);
		mDatabase = db.getWritableDatabase();
	}
}
