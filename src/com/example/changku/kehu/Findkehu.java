package com.example.changku.kehu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.changku.DialogDemo;
import com.example.changku.R;
import com.example.changku.SqlHelpLiteOpenHelper;
import com.example.changku.shangpin.Deleteshangpin;

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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class Findkehu extends Activity {
	private String id[];
	private String companyName[];
	private String dutyPerson[];
	private String companyAddress[];
	private String city[];
	private String email[];
	private String postalcode[];
	private String telephone[];
	private String fax[];
	private String homePageUrl[];

	SqlHelpLiteOpenHelper db;
	SQLiteDatabase mDatabase = null;

	private SimpleAdapter mAdapter;

	private ListView mListView;

	private List<Map<String, Object>> mList = new ArrayList<Map<String, Object>>();
	AlertDialog.Builder builder;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.kehulist);
		initView();
		initdata();
		mAdapter = new SimpleAdapter(Findkehu.this, getData(),
				R.layout.kehulistview, new String[] { "id", "companyName",
						"dutyPerson", "companyAddress", "city", "email",
						"postalcode", "telephone", "fax", "homePageUrl" },
				new int[] { R.id.customers_id, R.id.customers_companyname,
						R.id.customers_dutyperson, R.id.customers_companyaddress,
						R.id.customers_city, R.id.customers_email,
						R.id.customers_postalcode, R.id.customers_telephone,
						R.id.customers_fax, R.id.customers_homePageUrl});
		mListView.setAdapter(mAdapter);
		initEvent();
		builder = new AlertDialog.Builder(this);
	}

	private void initdata() {
		String selectStr = "select * from customers;";
		Cursor mCursor = mDatabase.rawQuery(selectStr, null);
		mCursor.moveToFirst();
		int count = mCursor.getCount();
		int i = 0;
		id = new String[count];
		companyName = new String[count];
		dutyPerson = new String[count];
		companyAddress = new String[count];
		city = new String[count];
		email = new String[count];
		postalcode = new String[count];
		telephone = new String[count];
		fax = new String[count];
		homePageUrl = new String[count];
		do {
			if (count > 0) {
				try {
					id[i] = mCursor.getInt(0) + "";
					companyName[i] = mCursor.getString(1);
					dutyPerson[i] = mCursor.getString(2);
					companyAddress[i] = mCursor.getString(3);
					city[i] = mCursor.getString(4) ;
					email[i] = mCursor.getString(5);
					postalcode[i] = mCursor.getString(6);
					telephone[i] = mCursor.getString(7);
					fax[i] = mCursor.getString(8);
					homePageUrl[i] = mCursor.getString(9);
					i++;
				} catch (Exception e) {
					Toast.makeText(Findkehu.this, "Findkehu Exception",
							Toast.LENGTH_SHORT).show();
				}
			} else {
				Toast.makeText(Findkehu.this, "无数据", Toast.LENGTH_SHORT).show();
			}
		} while (mCursor.moveToNext());
		mCursor.close();
	}

	private List<Map<String, Object>> getData() {
		for (int j = 0; j < id.length; j++) {
			Map<String, Object> mMap = new HashMap<String, Object>();
			mMap.put("id", id[j]);
			mMap.put("companyName", companyName[j]);
			mMap.put("dutyPerson", dutyPerson[j]);
			mMap.put("companyAddress", companyAddress[j]);
			mMap.put("city", city[j]);
			mMap.put("email", email[j]);
			mMap.put("postalcode", postalcode[j]);
			mMap.put("telephone", telephone[j]);
			mMap.put("fax", fax[j]);
			mMap.put("homePageUrl", homePageUrl[j]);
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
						builder.setPositiveButton("修改", new OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								Intent intent = new Intent(Findkehu.this,Updatekehu.class);
								Bundle bundle = new Bundle();
								bundle.putString("id", id[m]);
								intent.putExtras(bundle);
								startActivity(intent);
							}
						});
						builder.setNeutralButton("删除", new OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								//删除语句 需要刷新页面
								String deleteStr="delete from customers where _id= '"+ id[m]+"';";
								mDatabase.execSQL(deleteStr);
								
								Intent intent = new Intent(Findkehu.this,Findkehu.class);
								startActivity(intent);
							}
						});
						builder.setNegativeButton("取消", new OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								
							}
						});
						
						builder.create().show();
					}
				}
				
			}
		});
	}

	private void initView() {
		mListView = (ListView) findViewById(R.id.customers_list);
		db = new SqlHelpLiteOpenHelper(Findkehu.this, "changku.db", null, 1);
		mDatabase = db.getWritableDatabase();
	}
}
