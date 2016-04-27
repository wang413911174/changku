package com.example.changku.shangpinchuku;

import android.app.Activity;
import java.security.Signer;
import java.util.Calendar;

import com.example.changku.DialogDemo;
import com.example.changku.R;
import com.example.changku.SqlHelpLiteOpenHelper;

import android.app.AlertDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Addchuku extends Activity {

	/*
	 * 联系人
	 */
	private EditText etDutyPerson;
	/*
	 * 商品规格
	 */
	private EditText etPGuige;
	/*
	 * 联系人电话
	 */
	private EditText etTelephone;
	/*
	 * 商品单位
	 */
	private EditText etPDanwei;
	/*
	 * 进货价格
	 */
	private EditText etcost;
	/*
	 * 进货数量
	 */
	private EditText etamount;
	/*
	 * 公司名称
	 */
	private Spinner sCompanyName;
	/*
	 * 商品名称
	 */
	private Spinner sPName;
	/*
	 * 日期
	 */
	private DatePicker date;
	private SqlHelpLiteOpenHelper db;
	private SQLiteDatabase mDatabase = null;
	int year;
	int month;
	int day;
	String datestyle;
	String companyList[];
	String companyid[];
	String productList[];
	String productid[];
	String comid;
	String comname;
	String proname;
	String proid;
	Calendar calendar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chukuadd);
		db = new SqlHelpLiteOpenHelper(getApplicationContext(), "changku.db",
				null, 1);
		mDatabase = db.getWritableDatabase();
		initView();
		initDate();
		initEvent();
	}

	private void initEvent() {
		sCompanyName.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				comid = companyid[arg2];
				comname = companyList[arg2];
				Log.i("comid=", comid);
				String selectStr = "select dutyperson,telephone from customers where _id='"
						+ comid + "';";
				Cursor cursor1 = mDatabase.rawQuery(selectStr, null);
				cursor1.moveToFirst();
				int m = 0;
				String personname = null;
				String tel = null;
				do {
					if (cursor1.getCount() > 0) {
						try {
							personname = cursor1.getString(0);
							tel = cursor1.getString(1);
							Log.i("personname=", personname);
						} catch (Exception e) {
							Toast.makeText(Addchuku.this,
									"Addchuku initEvent Exception",
									Toast.LENGTH_SHORT).show();
						}
					}
				} while (cursor1.moveToNext());
				etDutyPerson.setText(personname);
				etTelephone.setText(tel);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});

		sPName.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				proid = productid[arg2];
				proname = productList[arg2];
				String selectStr2 = "select pguige,pdanwei from products where _id='"
						+ proid + "';";
				Cursor cursor2 = mDatabase.rawQuery(selectStr2, null);
				cursor2.moveToFirst();
				int n = 0;
				String proguige = null;
				String prodanwei = null;
				do {
					if (cursor2.getCount() > 0) {
						try {
							proguige = cursor2.getString(0);
							prodanwei = cursor2.getString(1);
						} catch (Exception e) {
							Toast.makeText(Addchuku.this,
									"Addchuku initEvent Exception",
									Toast.LENGTH_SHORT).show();
						}
					}
				} while (cursor2.moveToNext());
				etPGuige.setText(proguige);
				etPDanwei.setText(prodanwei);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
	}

	private void initDate() {
		String selectComname = "select _id,companyname from customers;";
		Cursor comnameCursor = mDatabase.rawQuery(selectComname, null);
		comnameCursor.moveToFirst();
		int comnamecount = comnameCursor.getCount();
		int i = 0;
		companyList = new String[comnamecount];
		companyid = new String[comnamecount];
		do {
			if (comnamecount > 0) {
				try {
					companyid[i] = comnameCursor.getInt(0) + "";
					companyList[i] = comnameCursor.getString(1);
					i++;
				} catch (Exception e) {
					Toast.makeText(Addchuku.this,
							"Addchuku initDate Exception", Toast.LENGTH_SHORT)
							.show();
				}
			} else {
				DialogDemo.buidler(this, "提示消息", "无客户消息");
			}
		} while (comnameCursor.moveToNext());
		if (comnameCursor != null) {
			comnameCursor.close();
		}

		BaseAdapter comAdapter = new BaseAdapter() {
			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return companyList.length;
			}

			@Override
			public long getItemId(int position) {
				// TODO Auto-generated method stub
				return position;
			}

			@Override
			public Object getItem(int position) {
				// TODO Auto-generated method stub
				return companyList[position];
			}

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				// TODO Auto-generated method stub
				TextView text = new TextView(Addchuku.this);
				text.setText(companyList[position]);
				text.setTextSize(20);
				text.setTextColor(Color.BLACK);
				return text;
			}
		};
		sCompanyName.setAdapter(comAdapter);

		String selectPname = "select _id,pname from products;";
		Cursor pnameCursor = mDatabase.rawQuery(selectPname, null);
		pnameCursor.moveToFirst();
		int j = 0;
		int productcount = pnameCursor.getCount();
		productList = new String[productcount];
		productid = new String[productcount];
		do {
			if (productcount > 0) {
				try {
					productid[j] = pnameCursor.getInt(0) + "";
					productList[j] = pnameCursor.getString(1);
					j++;
				} catch (Exception e) {
					Toast.makeText(Addchuku.this, "Addchuku Exception",
							Toast.LENGTH_SHORT).show();
				}
			} else {
				DialogDemo.buidler(this, "提示消息", "无商品消息");
			}
		} while (pnameCursor.moveToNext());
		if (pnameCursor != null) {
			pnameCursor.close();
		}

		BaseAdapter productAdapter = new BaseAdapter() {

			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return productList.length;
			}

			@Override
			public long getItemId(int position) {
				// TODO Auto-generated method stub
				return position;
			}

			@Override
			public Object getItem(int position) {
				// TODO Auto-generated method stub
				return productList[position];
			}

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				// TODO Auto-generated method stub
				TextView text = new TextView(Addchuku.this);
				text.setText(productList[position]);
				text.setTextSize(20);
				text.setTextColor(Color.BLACK);
				return text;
			}
		};
		sPName.setAdapter(productAdapter);

	}

	private void initView() {
		etDutyPerson = (EditText) findViewById(R.id.addc_et_dutyperson);
		etPGuige = (EditText) findViewById(R.id.addc_et_pguige);
		etTelephone = (EditText) findViewById(R.id.addc_et_telephone);
		etPDanwei = (EditText) findViewById(R.id.addc_et_pdanwei);
		etcost = (EditText) findViewById(R.id.addc_et_cost);
		etamount = (EditText) findViewById(R.id.addc_et_amount);
		sCompanyName = (Spinner) findViewById(R.id.addc_companyname);
		sPName = (Spinner) findViewById(R.id.addc_pname);
		date = (DatePicker) findViewById(R.id.addc_date);
		calendar = Calendar.getInstance();
		year = calendar.get(Calendar.YEAR);
		month = calendar.get(Calendar.MONTH + 1);
		day = calendar.get(Calendar.DAY_OF_MONTH);
		// 显示当前日期、时间
		datestyle = year + "年" + (month + 1) + "月" + day + "日";

		date.init(year, month, day, new OnDateChangedListener() {
			@Override
			public void onDateChanged(DatePicker view, int year,
					int monthOfYear, int dayOfMonth) {
				Addchuku.this.year = year;
				Addchuku.this.month = monthOfYear;
				Addchuku.this.day = dayOfMonth;
			}
		});
	}

	public void doSubmit(View v) {
		if (etcost.getText().toString().equals("")
				&& etamount.getText().toString().equals("")) {
			DialogDemo.buidler(Addchuku.this, "错误提示", "请输入单价和数量");
		} else {
			String dutyperson = etDutyPerson.getText().toString();
			String telephone = etTelephone.getText().toString();
			String guige = etPGuige.getText().toString();
			String danwei = etPDanwei.getText().toString();
			String cost = etcost.getText().toString();
			String amount = etamount.getText().toString();

			int id = 1;
			String selectid = "select max(_id) from outbounds;";
			Cursor cursorid = mDatabase.rawQuery(selectid, null);

			try {
				cursorid.moveToFirst();
				id = cursorid.getInt(0);
				id += 1;
			} catch (Exception e) {
				Toast.makeText(Addchuku.this, "Addchuku doSubmit Exception",
						Toast.LENGTH_SHORT).show();
			}

			String insertStr = "insert into outbounds values('" + id + "','"
					+ comname + "','" + dutyperson + "','" + telephone + "','"
					+ proname + "','" + guige + "','" + danwei + "','" + cost
					+ "','" + amount + "','" + datestyle + "');";
			mDatabase.execSQL(insertStr);
			Toast.makeText(Addchuku.this, "添加成功", Toast.LENGTH_SHORT).show();
		}
	}

	public void doCancel(View v) {
		Addchuku.this.finish();
	}
}
