package com.example.changku.shangpin;

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
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class Findshangpin extends Activity {
	private String id[];
	private String pname[];
	private String pguige[];
	private String pdanwei[];
	SqlHelpLiteOpenHelper db;
	SQLiteDatabase mDatabase = null;
	private ListView mListView;
	private SimpleAdapter mAdapter;
	List<Map<String, Object>> mlist = new ArrayList<Map<String, Object>>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shangpinlist);
		initView();
		
		String findstr = "select _id,pname,pguige,pdanwei from products;";
		Cursor findCursor = mDatabase.rawQuery(findstr, null);
		findCursor.moveToFirst();
		int count = findCursor.getCount();
		
		id = new String[count];
		pname = new String[count];
		pguige = new String[count];
		pdanwei = new String[count];
		int i = 0;
		do {
			if(count>0){
				try {
					id[i] = findCursor.getInt(0)+"";
					pname[i] = findCursor.getString(1);
					pguige[i] = findCursor.getString(2);
					pdanwei[i] = findCursor.getString(3);
					i++;
				} catch (Exception e) {
					Log.i("i=",i+"");
					/*id[i] = "";
					pname[i] = "";
					pguige[i] = "";
					pdanwei[i] = "";*/
				}
			}else{
					Toast.makeText(getApplicationContext(), "ÎÞÊý¾Ý", Toast.LENGTH_SHORT).show();
				}
		} while (findCursor.moveToNext());
		findCursor.close();
		
		for (int j = 0; j < id.length; j++) {
			Map<String, Object> mMap = new HashMap<String, Object>();
			mMap.put("id", id[j]);
			mMap.put("pname", pname[j]);
			mMap.put("pguige", pguige[j]);
			mMap.put("pdanwei", pdanwei[j]);
			mlist.add(mMap);
		}

		mAdapter = new SimpleAdapter(getApplicationContext(), mlist,
				R.layout.shangpinlistview, new String[] { "id", "pname",
						"pguige", "pdanwei" }, new int[] { R.id.products_id,
						R.id.products_pname, R.id.products_pguige,
						R.id.products_pdanwei });
		mListView.setAdapter(mAdapter);
	}

	private void initView() {
		mListView = (ListView) findViewById(R.id.products_list);
		db = new SqlHelpLiteOpenHelper(getApplicationContext(), "changku.db",
				null, 1);
		
		mDatabase = db.getWritableDatabase();
	}
}
