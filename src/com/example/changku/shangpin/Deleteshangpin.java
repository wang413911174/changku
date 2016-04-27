package com.example.changku.shangpin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.changku.R;
import com.example.changku.SqlHelpLiteOpenHelper;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class Deleteshangpin extends Activity {
	private String[] pid;
	private String[] pName;
	private String[] pGuige;
	private String[] pDanwei;
	private ListView mListView;
	private SimpleAdapter mAdapter;
	SqlHelpLiteOpenHelper db;
	SQLiteDatabase mDatabase = null;
	List<Map<String, Object>> mList = new ArrayList<Map<String, Object>>();
	AlertDialog.Builder builder;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shangpinlist);
		initView();
		initEvent();
		builder = new AlertDialog.Builder(this);
		initdata();
		mAdapter = new SimpleAdapter(this, getDate(), R.layout.shangpinlistview,
				new String[] { "pid", "pName", "pGuige", "pDanwei" },
				new int[] { R.id.products_id, R.id.products_pname,
						R.id.products_pguige, R.id.products_pdanwei });
		mListView.setAdapter(mAdapter);
	}

	private void initdata() {
		String selectStr = "select _id,pname,Pguige,pdanwei from products;";
		Cursor pCursor = mDatabase.rawQuery(selectStr, null);
		pCursor.moveToFirst();
		int count = pCursor.getCount();
		pid = new String[count];
		pName = new String[count];
		pGuige = new String[count];
		pDanwei = new String[count];
		int i = 0;
		do {
			if (count > 0) {
				try {
					pid[i] = pCursor.getInt(0) + "";
					pName[i] = pCursor.getString(1);
					pGuige[i] = pCursor.getString(2);
					pDanwei[i] = pCursor.getString(3);
					i++;
				} catch (Exception e) {
					Toast.makeText(getApplication(), "pCursor数据异常",
							Toast.LENGTH_SHORT).show();
				}
			} else {
				Toast.makeText(getApplication(), "无数据", Toast.LENGTH_SHORT)
						.show();
			}
		} while (pCursor.moveToNext());
		pCursor.close();
	}

	private List<Map<String, Object>> getDate(){
		for (int j = 0; j < pid.length; j++) {
			Map<String, Object> mMap = new HashMap<String, Object>();
			mMap.put("pid", pid[j]);
			mMap.put("pName", pName[j]);
			mMap.put("pGuige", pGuige[j]);
			mMap.put("pDanwei", pDanwei[j]);
			mList.add(mMap);
		}
		
		return mList;
	}
	
	private void initEvent() {
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				for(int n=0 ;n<pid.length;n++){
					if(arg2 == n){
						builder.setTitle("删除商品");
						builder.setMessage("是否删除商品？");
						final int m=n;
						builder.setPositiveButton("确定", new OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								String deleteStr ="delete from products where _id='"+pid[m]+"';";
								mDatabase.execSQL(deleteStr);
								//这里应该是刷新页面 ,有时间应该找办法修改一下
								Intent intent = new Intent(Deleteshangpin.this,Deleteshangpin.class);
								startActivity(intent);
								Deleteshangpin.this.finish();
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
		mListView = (ListView) findViewById(R.id.products_list);
		db = new SqlHelpLiteOpenHelper(getApplicationContext(), "changku.db",
				null, 1);
		mDatabase = db.getWritableDatabase();
	}
}
