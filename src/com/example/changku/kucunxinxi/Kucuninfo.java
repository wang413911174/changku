package com.example.changku.kucunxinxi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.changku.R;
import com.example.changku.SqlHelpLiteOpenHelper;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class Kucuninfo extends Activity {
	private ListView mListView;
	private SimpleAdapter mAdapter;
	List<Map<String, Object>> mList = new ArrayList<Map<String, Object>>();
	SqlHelpLiteOpenHelper db;
	SQLiteDatabase mDatabase = null;
	
	String id[];
	String pname[];
	String pguige[];
	String pdanwei[];
	String amount[];
	String pname1[];
	String pguige1[];
	String pdanwei1[];

	String num[];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.kucun);
		db = new SqlHelpLiteOpenHelper(getApplicationContext(), "changku.db",
				null, 1);
		mDatabase = db.getWritableDatabase();
		mDatabase.execSQL("delete from inventory;");
		initView();

		String selectPro = "select pname,pguige,pdanwei from products;";
		Cursor cursor = mDatabase.rawQuery(selectPro, null);
		cursor.moveToFirst();

		int count = cursor.getCount();
		pname1 = new String[count];
		pguige1 = new String[count];
		pdanwei1 = new String[count];
		num = new String[count];
		int n = 0;
		do {
			if (count > 0) {
				try {
					pname1[n] = cursor.getString(0);
					pguige1[n] = cursor.getString(1);
					pdanwei1[n] = cursor.getString(2);
					n++;
				} catch (Exception e) {
					// TODO: handle exception
					Toast.makeText(Kucuninfo.this, "Kucuninfo Exception",
							Toast.LENGTH_SHORT).show();
				}
			} else {
				Toast.makeText(Kucuninfo.this, "无数据", Toast.LENGTH_SHORT)
						.show();
			}
		} while (cursor.moveToNext());
		if (cursor != null) {
			cursor.close();
		}
		for (int m = 0; m < pname1.length; m++) {
			String selectPro1 = "select amount from inbounds where pname= '"
					+ pname1[m] + "';";
			Cursor cursor1 = mDatabase.rawQuery(selectPro1, null);
			cursor1.moveToFirst();
			int count1 = cursor1.getCount();
			String num1[] = new String[count1];
			int s1 = 0;
			int sum1 = 0;
			String selectPro2 = "select amount from outbounds where pname= '"
					+ pname1[m] + "';";
			Cursor cursor2 = mDatabase.rawQuery(selectPro2, null);
			cursor2.moveToFirst();
			int count2 = cursor2.getCount();
			String num2[] = new String[count2];
			int s2 = 0;
			int sum2 = 0;
			do {
				if (count1 > 0) {
					try {
						num1[s1] = cursor1.getString(0);
						sum1 += Integer.parseInt(num1[s1]);
						s1++;

					} catch (Exception e) {
						// TODO: handle exception
						Toast.makeText(Kucuninfo.this,
								"cursor1.getString(0); Exception",
								Toast.LENGTH_SHORT).show();
					}
				} else {
				}
			} while (cursor1.moveToNext());
			do {
				if (count2 > 0) {
					try {

						num2[s2] = cursor2.getString(0);
						sum2 += Integer.parseInt(num2[s2]);
						s2++;

					} catch (Exception e) {
						// TODO: handle exception
						Toast.makeText(Kucuninfo.this,
								"cursor2.getString(0)  Exception",
								Toast.LENGTH_SHORT).show();
					}
				} else {
				}
			} while (cursor2.moveToNext());
			num[m] = (sum1 - sum2) + "";

		}
		for (int j = 0; j < pname1.length; j++) {
			int id = 0;
			String select = "select max(_id) from inventory";
			Cursor seCursor = mDatabase.rawQuery(select, null);
			try {
				seCursor.moveToFirst();
				id = Integer.parseInt(seCursor.getString(0));
				id += 1;
			} catch (Exception e) {
				// TODO: handle exception
				id = 0;
			}
			mDatabase.execSQL("insert into inventory values('" + id + "','"
					+ pname1[j] + "','" + pguige1[j] + "','" + pdanwei1[j]
					+ "','" + num[j] + "')");
			seCursor.close();
		}

		String selectStr3 = "select _id,pname,pguige,pdanwei,amount from inventory";
		Cursor cursor3 = mDatabase.rawQuery(selectStr3, null);

		cursor3.moveToFirst();
		int count3 = cursor3.getCount();
		id = new String[count3];
		pname = new String[count3];
		pguige = new String[count3];
		pdanwei = new String[count3];
		amount = new String[count3];
		int a = 0;
		do {
			if (count3 > 0) {
				try {
					id[a] = cursor3.getString(0);
					pname[a] = cursor3.getString(1);
					pguige[a] = cursor3.getString(2);
					pdanwei[a] = cursor3.getString(3);
					amount[a] = cursor3.getString(4);
					a++;
				} catch (Exception e) {
					Toast.makeText(Kucuninfo.this,
							"cursor3.getString(0)  Exception",
							Toast.LENGTH_SHORT).show();
				}
			} else {
				/*Toast.makeText(Kucuninfo.this, "无数据", Toast.LENGTH_SHORT)
						.show();*/
			}
		} while (cursor3.moveToNext());

		for (int i = 0; i < id.length; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", id[i]);
			map.put("pname", pname[i]);
			map.put("pguige", pguige[i]);
			map.put("pdanwei", pdanwei[i]);
			map.put("amount", amount[i]);
			mList.add(map);
		}
		mAdapter = new SimpleAdapter(this, mList, R.layout.kucunlistview,
				new String[] { "id", "pname", "pguige", "pdanwei", "amount" },
				new int[] { R.id.inventory_id, R.id.inventory_pname,
						R.id.inventory_pguige, R.id.inventory_pdanwei,
						R.id.inventory_amount });
		mListView.setAdapter(mAdapter);

	}

	private void initView() {
		// TODO Auto-generated method stub
		mListView = (ListView) findViewById(R.id.inventory_list);
	}
}
