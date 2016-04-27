package com.example.changku.shangpinchuku;

import android.app.Activity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.changku.R;
import com.example.changku.SqlHelpLiteOpenHelper;
import com.example.changku.kehu.Findkehu;

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

public class Findchuku extends Activity {

	private String id[];
	private String companyname[];
	private String dutyperson[];
	private String telephone[];
	private String pname[];
	private String pguige[];
	private String pdanwei[];
	private String cost[];
	private String amount[];
	private String date[];
	SqlHelpLiteOpenHelper db;
	SQLiteDatabase mDatabase = null;
	private ListView mListView;
	private SimpleAdapter mAdapter;
	List<Map<String, Object>> mlist = new ArrayList<Map<String, Object>>();
	AlertDialog.Builder builder;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chukulist);
		initView();
		initDate();
		initEvent();
		builder = new AlertDialog.Builder(this);
	}

	private void initEvent() {
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				for (int i = 0; i < id.length; i++) {
					if (arg2 == i) {
						builder.setTitle("操作提示！");
						builder.setMessage("请选择操作！");
						final int m = i;
						builder.setPositiveButton("修改", new OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								Intent intent = new Intent(Findchuku.this,
										Updatechuku.class);
								Bundle bundle = new Bundle();
								bundle.putString("outboundsid", id[m]);
								Log.i("outboundsid", id[m]);
								intent.putExtras(bundle);
								startActivity(intent);
							}
						});
						builder.setNeutralButton("删除", new OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								String deleteStr = "delete from outbounds where _id='"
										+ id[m] + "';";
								mDatabase.execSQL(deleteStr);
								Toast.makeText(Findchuku.this, "删除成功",
										Toast.LENGTH_SHORT).show();
								Intent intent = new Intent(Findchuku.this,
										Findchuku.class);
								startActivity(intent);
								Findchuku.this.finish();
							}
						});
						builder.setNegativeButton("取消", new OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub

							}
						});
						builder.create().show();
					}
				}
			}
		});
	}

	private void initDate() {
		String selectStr = "select * from outbounds";
		Cursor cursor = mDatabase.rawQuery(selectStr, null);
		cursor.moveToFirst();
		int count = cursor.getCount();
		int i = 0;
		id = new String[count];
		companyname = new String[count];
		dutyperson = new String[count];
		telephone = new String[count];
		pname = new String[count];
		pguige = new String[count];
		pdanwei = new String[count];
		cost = new String[count];
		amount = new String[count];
		date = new String[count];
		do {
			if (count > 0) {
				try {
					id[i] = cursor.getInt(0) + "";
					companyname[i] = cursor.getString(1);
					dutyperson[i] = cursor.getString(2);
					telephone[i] = cursor.getString(3);
					pname[i] = cursor.getString(4);
					pguige[i] = cursor.getString(5);
					pdanwei[i] = cursor.getString(6);
					cost[i] = cursor.getString(7);
					amount[i] = cursor.getString(8);
					date[i] = cursor.getString(9);
					i++;
				} catch (Exception e) {
					Toast.makeText(Findchuku.this, "Findkehu Exception",
							Toast.LENGTH_SHORT).show();
				}
			} else {
				Toast.makeText(Findchuku.this, "无数据", Toast.LENGTH_SHORT)
						.show();
			}
		} while (cursor.moveToNext());
		if (cursor != null) {
			cursor.close();
		}

		for (int j = 0; j < id.length; j++) {
			Map<String, Object> mMap = new HashMap<String, Object>();
			mMap.put("id", id[j]);
			mMap.put("companyname", companyname[j]);
			mMap.put("dutyperson", dutyperson[j]);
			mMap.put("telephone", telephone[j]);
			mMap.put("pname", pname[j]);
			mMap.put("pguige", pguige[j]);
			mMap.put("pdanwei", pdanwei[j]);
			mMap.put("cost", cost[j]);
			mMap.put("amount", amount[j]);
			mMap.put("date", date[j]);
			mlist.add(mMap);
		}

		mAdapter = new SimpleAdapter(Findchuku.this, mlist,
				R.layout.chukulistview, new String[] { "id", "companyname",
						"companyname", "telephone", "pname", "pguige",
						"pdanwei", "cost", "amount", "date" }, new int[] {
						R.id.outbounds_id, R.id.outbounds_companyname,
						R.id.outbounds_dutyperson, R.id.outbounds_telephone,
						R.id.outbounds_pname, R.id.outbounds_pguige,
						R.id.outbounds_pdanwei, R.id.outbounds_cost,
						R.id.outbounds_amount, R.id.outbounds_date });
		mListView.setAdapter(mAdapter);
	}

	private void initView() {
		mListView = (ListView) findViewById(R.id.outbounds_list);
		db = new SqlHelpLiteOpenHelper(getApplicationContext(), "changku.db",
				null, 1);
		mDatabase = db.getWritableDatabase();
	}
}
