package com.example.changku.shangpin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.changku.R;
import com.example.changku.SqlHelpLiteOpenHelper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class Updateshangpin extends Activity {
	String pid[];
	String pname[];
	String pguige[];
	String pdanwei[];
	SqlHelpLiteOpenHelper db;
	SQLiteDatabase mDatabase=null;
	private ListView mListView;
	private List<Map<String, Object>> mList = new ArrayList<Map<String, Object>>();
	private SimpleAdapter madpter;
	AlertDialog.Builder builder;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.shangpinlist);
		builder = new AlertDialog.Builder(this);
		
		initView();
		
		
		initList();
		initEvent();
	}

	private void initEvent() {
		mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				for(int i=0;i<pid.length;i++){
					if(position == i){
						builder.setTitle("修改商品");
						builder.setMessage("是否要修改商品");
						final int j =i;
						builder.setPositiveButton("确定", new OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								Intent intent = new Intent(Updateshangpin.this,Updateshangpins.class);
								Bundle bundle = new Bundle();
								bundle.putString("pid", pid[j]);
								intent.putExtras(bundle);
								startActivity(intent);
								Updateshangpin.this.finish(); 
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

	private void initList() {
		String findStr = "select _id,pname,Pguige,pdanwei from products;";
		Cursor findCursor = mDatabase.rawQuery(findStr, null);
		findCursor.moveToFirst();
		int count = findCursor.getCount();
		pid = new String[count];
		pname = new String[count];
		pguige = new String[count];
		pdanwei = new String[count];
		int i = 0;
		do {
			if(count>0){
				try {
					pid[i] = findCursor.getString(0);
					pname[i] = findCursor.getString(1);
					pguige[i] = findCursor.getString(2);
					pdanwei[i] = findCursor.getString(3);
					i++;
				} catch (Exception e) {
					Toast.makeText(Updateshangpin.this,
							"Updateshangpin 数组获得SQL内容异常", Toast.LENGTH_SHORT)
							.show();
				}
			}else{
				Toast.makeText(Updateshangpin.this,
						"无数据", Toast.LENGTH_SHORT)
						.show();
			}
		} while (findCursor.moveToNext());

		for (int j = 0; j < pid.length; j++) {
			Map<String, Object> mMap = new HashMap<String, Object>();
			mMap.put("id", pid[j]);
			mMap.put("pname", pname[j]);
			mMap.put("pguige", pguige[j]);
			mMap.put("pdanwei", pdanwei[j]);
			mList.add(mMap);
		}

		madpter = new SimpleAdapter(getApplicationContext(), mList,
				R.layout.shangpinlistview, new String[] { "id", "pname",
						"pguige", "pdanwei" }, new int[] { R.id.products_id,
						R.id.products_pname, R.id.products_pguige,
						R.id.products_pdanwei });	
		mListView.setAdapter(madpter);
	}

	private void initView() {
		mListView = (ListView) findViewById(R.id.products_list);
		db = new SqlHelpLiteOpenHelper(Updateshangpin.this, "changku.db",
				null, 1);
		
		mDatabase = db.getWritableDatabase();
	}
	
}
